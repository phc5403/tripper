<script setup>
import { ref, watch, onMounted } from "vue";
import { useContentStore } from "@/stores/attractionInfo";

var map;
const positions = ref([]);
const markers = ref([]);
const props = defineProps({ attractions: Array, selectAttraction: Object });
const store = useContentStore();

// 목록을 클릭하면 선택한 위치로 이동함.  //
watch(
    () => props.selectAttraction.value,
    () => {
        // console.log("목록을 클릭하면 선택한 위치로 이동함.");
        // console.log(props.selectAttraction.value);
        // 이동할 위도 경도 위치를 생성합니다
        var moveLatLon = new kakao.maps.LatLng(
            props.selectAttraction.latitude,
            props.selectAttraction.longitude
        );
        console.log(moveLatLon);

        // 지도 중심을 부드럽게 이동시킵니다
        // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
        map.panTo(moveLatLon);
    },
    { deep: true }
);

// 카카오 맵을 화면에 반영
onMounted(() => {
    // 카카오 라이브러리 정보 및 map을 확인
    if (window.kakao && window.kakao.maps) {
        initMap(); // 지도 초기화
    } else {
        // 카카오 Map 라이브러리 정보 세팅
        // script 태그를 생성하여 apikey를 세팅해야 함.
        const script = document.createElement("script");

        // API Key 세팅
        script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${
            import.meta.env.VITE_KAKAO_MAP_SERVICE_KEY
        }&libraries=services,clusterer`;
        /* global kakao */
        script.onload = () => kakao.maps.load(() => initMap());
        document.head.appendChild(script);
    }
});

watch(
    () => props.attractions.value,
    () => {
        positions.value = [];
        props.attractions.forEach((attractions) => {
            let obj = {};
            obj.latlng = new kakao.maps.LatLng(attractions.latitude, attractions.longitude);
            obj.title = attractions.title;
            obj.first_image = attractions.first_image;
            obj.addr1 = attractions.addr1;
            obj.zipcode = attractions.zipcode;

            positions.value.push(obj);
        });
        loadMarkers();
    },
    { deep: true }
);

// 카카오맵 초기 실행
// onMounted()에서 해당 메소드를 이용해서 지도를 생성
const initMap = () => {
    const container = document.getElementById("map");
    console.log("상혁아 가자");
    const options = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 3,
    };

    // 지도 객체 등록
    // 지도 객체는 반응형 관리 대상이 아니므로 본 함수에서 선언함.
    map = new kakao.maps.Map(container, options);

    // loadMarkers();
};

// 마커 로드
const loadMarkers = () => {
    // 현재 표시되어있는 marker들이 있다면 map에 등록된 marker를 제거한다.
    deleteMarkers();

    // 마커 이미지를 생성합니다
    // const imgSrc = require("@/assets/map/markerStar.png");
    // 마커 이미지의 이미지 크기 입니다
    // const imgSize = new kakao.maps.Size(24, 35);
    // const markerImage = new kakao.maps.MarkerImage(imgSrc, imgSize);

    // 마커를 생성합니다
    markers.value = [];
    // console.log(positions);
    positions.value.forEach((position, index) => {
        const marker = new kakao.maps.Marker({
            map: map, // 마커를 표시할 지도
            position: position.latlng, // 마커를 표시할 위치
            title: position.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됨.
            clickable: true, // // 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정합니다
            // image: markerImage, // 마커의 이미지
        });
        markers.value.push(marker);
        // console.log(index, position);

        let overlay = new kakao.maps.CustomOverlay({
            // content: content,
            map: map,
            position: marker.getPosition(),
        });

        // 오버레이 UI //
        let wrap = document.createElement("div");
        wrap.setAttribute("class", "wrap");

        let info = document.createElement("div");
        info.setAttribute("class", "info");

        let title = document.createElement("div");
        title.setAttribute("class", "title");
        title.innerHTML = props.attractions[index].title;

        let closeBtn = document.createElement("div");
        closeBtn.setAttribute("class", "close");
        closeBtn.setAttribute("title", "닫기");
        closeBtn.onclick = function () {
            overlay.setMap(null);
        };

        title.appendChild(closeBtn);
        info.appendChild(title);

        let body = document.createElement("div");
        body.setAttribute("class", "body");

        info.appendChild(body);

        let imgDiv = document.createElement("div");
        imgDiv.setAttribute("class", "img");

        let imgTag = document.createElement("img");
        if (props.attractions[index].first_image.length == 0) {
            imgTag.src = "https://www.verdantis.com/wp-content/uploads/2016/04/noimg.jpg";
            // imgTag.src = "@/assets/gangneung.jpg";
        } else {
            imgTag.src = props.attractions[index].first_image;
        }

        // console.log(typeof props.attractions[index].first_image); // string
        // console.log(props.attractions[index].first_image); // string
        // imgTag.src = props.attractions[index].first_image;
        imgTag.width = "73";
        imgTag.height = "70";
        // imgTag.alt = "ㅗㅗ";

        imgDiv.appendChild(imgTag);
        body.appendChild(imgDiv);

        let desc = document.createElement("div");
        desc.setAttribute("class", "desc");

        let ellipsis1 = document.createElement("div");
        ellipsis1.setAttribute("class", "ellipsis");
        ellipsis1.innerHTML = props.attractions[index].addr1;

        let ellipsis2 = document.createElement("div");
        ellipsis2.classList.add("jibun", "ellipsis");
        // ellipsis2.innerHTML = `(우) ${props.attractions[index].zipcode} (종류) ${props.attractions[index].content_type_id}`;
        ellipsis2.innerHTML = `(우) ${props.attractions[index].zipcode} (종류) ${store.findType(
            props.attractions[index].content_type_id
        )}`;
        desc.appendChild(ellipsis1);
        desc.appendChild(ellipsis2);
        body.appendChild(desc);

        wrap.appendChild(info);
        // console.log(wrap);
        overlay.setContent(wrap);
        ////////////

        overlay.setMap(null); // 최초 로딩시 오버레이 뜨지않게 하기.

        // 마커 클릭 이벤트 //
        kakao.maps.event.addListener(marker, "click", function () {
            console.log("클릭!");
            overlay.setMap(map); // 마커를 클릭하면 해당 정보를 담은 오버레이 띄우기
        });
    }); // forEach

    // 4. 지도를 이동시켜주기 -> 마커가 한 화면에 모두 나오는 범위로 잡음.
    // 배열.reduce( (누적값, 현재값, 인덱스, 요소)=>{ return 결과값}, 초기값);
    const bounds = positions.value.reduce(
        (bounds, position) => bounds.extend(position.latlng),
        new kakao.maps.LatLngBounds()
    );

    map.setBounds(bounds);
}; // loadMarkers()

const deleteMarkers = () => {
    if (markers.value.length > 0) {
        markers.value.forEach((marker) => marker.setMap(null));
    }
};

////////////////////////////////////////////////////////////
// 마커를 클릭했을 때 해당 장소의 상세정보를 보여줄 커스텀오버레이입니다
// var placeOverlay = new kakao.maps.CustomOverlay({ zIndex: 1 });
// var contentNode = document.createElement("div"); // 커스텀 오버레이의 컨텐츠 엘리먼트 입니다
// // markers = [], // 마커를 담을 배열입니다
// var currCategory = ""; // 현재 선택된 카테고리를 가지고 있을 변수입니다

// // var mapContainer = document.getElementById("map"), // 지도를 표시할 div
// //     mapOption = {
// //         center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
// //         level: 5, // 지도의 확대 레벨
// //     };

// // // 지도를 생성합니다
// // var map = new kakao.maps.Map(mapContainer, mapOption);

// // 장소 검색 객체를 생성합니다
// var ps = new kakao.maps.services.Places(map);

// // 지도에 idle 이벤트를 등록합니다
// kakao.maps.event.addListener(map, "idle", searchPlaces);

// // 커스텀 오버레이의 컨텐츠 노드에 css class를 추가합니다
// contentNode.className = "placeinfo_wrap";

// // 커스텀 오버레이의 컨텐츠 노드에 mousedown, touchstart 이벤트가 발생했을때
// // 지도 객체에 이벤트가 전달되지 않도록 이벤트 핸들러로 kakao.maps.event.preventMap 메소드를 등록합니다
// addEventHandle(contentNode, "mousedown", kakao.maps.event.preventMap);
// addEventHandle(contentNode, "touchstart", kakao.maps.event.preventMap);

// // 커스텀 오버레이 컨텐츠를 설정합니다
// placeOverlay.setContent(contentNode);

// // 각 카테고리에 클릭 이벤트를 등록합니다
// addCategoryClickEvent();

// // 엘리먼트에 이벤트 핸들러를 등록하는 함수입니다
// function addEventHandle(target, type, callback) {
//     if (target.addEventListener) {
//         target.addEventListener(type, callback);
//     } else {
//         target.attachEvent("on" + type, callback);
//     }
// }

// // 카테고리 검색을 요청하는 함수입니다
// function searchPlaces() {
//     if (!currCategory) {
//         return;
//     }

//     // 커스텀 오버레이를 숨깁니다
//     placeOverlay.setMap(null);

//     // 지도에 표시되고 있는 마커를 제거합니다
//     removeMarker();

//     ps.categorySearch(currCategory, placesSearchCB, { useMapBounds: true });
// }

// // 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
// function placesSearchCB(data, status, pagination) {
//     if (status === kakao.maps.services.Status.OK) {
//         // 정상적으로 검색이 완료됐으면 지도에 마커를 표출합니다
//         displayPlaces(data);
//     } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
//         // 검색결과가 없는경우 해야할 처리가 있다면 이곳에 작성해 주세요
//     } else if (status === kakao.maps.services.Status.ERROR) {
//         // 에러로 인해 검색결과가 나오지 않은 경우 해야할 처리가 있다면 이곳에 작성해 주세요
//     }
// }

// // 지도에 마커를 표출하는 함수입니다
// function displayPlaces(places) {
//     // 몇번째 카테고리가 선택되어 있는지 얻어옵니다
//     // 이 순서는 스프라이트 이미지에서의 위치를 계산하는데 사용됩니다
//     var order = document.getElementById(currCategory).getAttribute("data-order");

//     for (var i = 0; i < places.length; i++) {
//         // 마커를 생성하고 지도에 표시합니다
//         var marker = addMarker(new kakao.maps.LatLng(places[i].y, places[i].x), order);

//         // 마커와 검색결과 항목을 클릭 했을 때
//         // 장소정보를 표출하도록 클릭 이벤트를 등록합니다
//         (function (marker, place) {
//             kakao.maps.event.addListener(marker, "click", function () {
//                 displayPlaceInfo(place);
//             });
//         })(marker, places[i]);
//     }
// }

// // 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
// function addMarker(position, order) {
//     var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png", // 마커 이미지 url, 스프라이트 이미지를 씁니다
//         imageSize = new kakao.maps.Size(27, 28), // 마커 이미지의 크기
//         imgOptions = {
//             spriteSize: new kakao.maps.Size(72, 208), // 스프라이트 이미지의 크기
//             spriteOrigin: new kakao.maps.Point(46, order * 36), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
//             offset: new kakao.maps.Point(11, 28), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
//         },
//         markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
//         marker = new kakao.maps.Marker({
//             position: position, // 마커의 위치
//             image: markerImage,
//         });

//     marker.setMap(map); // 지도 위에 마커를 표출합니다
//     markers.push(marker); // 배열에 생성된 마커를 추가합니다

//     return marker;
// }

// // 지도 위에 표시되고 있는 마커를 모두 제거합니다
// function removeMarker() {
//     for (var i = 0; i < markers.length; i++) {
//         markers[i].setMap(null);
//     }
//     markers = [];
// }

// // 클릭한 마커에 대한 장소 상세정보를 커스텀 오버레이로 표시하는 함수입니다
// function displayPlaceInfo(place) {
//     var content =
//         '<div class="placeinfo">' +
//         '   <a class="title" href="' +
//         place.place_url +
//         '" target="_blank" title="' +
//         place.place_name +
//         '">' +
//         place.place_name +
//         "</a>";

//     if (place.road_address_name) {
//         content +=
//             '    <span title="' +
//             place.road_address_name +
//             '">' +
//             place.road_address_name +
//             "</span>" +
//             '  <span class="jibun" title="' +
//             place.address_name +
//             '">(지번 : ' +
//             place.address_name +
//             ")</span>";
//     } else {
//         content += '    <span title="' + place.address_name + '">' + place.address_name + "</span>";
//     }

//     content += '    <span class="tel">' + place.phone + "</span>" + "</div>" + '<div class="after"></div>';

//     contentNode.innerHTML = content;
//     placeOverlay.setPosition(new kakao.maps.LatLng(place.y, place.x));
//     placeOverlay.setMap(map);
// }

// // 각 카테고리에 클릭 이벤트를 등록합니다
// function addCategoryClickEvent() {
//     var category = document.getElementById("category"),
//         children = category.children;

//     for (var i = 0; i < children.length; i++) {
//         children[i].onclick = onClickCategory;
//     }
// }

// // 카테고리를 클릭했을 때 호출되는 함수입니다
// function onClickCategory() {
//     var id = this.id,
//         className = this.className;

//     placeOverlay.setMap(null);

//     if (className === "on") {
//         currCategory = "";
//         changeCategoryClass();
//         removeMarker();
//     } else {
//         currCategory = id;
//         changeCategoryClass(this);
//         searchPlaces();
//     }
// }

// // 클릭된 카테고리에만 클릭된 스타일을 적용하는 함수입니다
// function changeCategoryClass(el) {
//     var category = document.getElementById("category"),
//         children = category.children,
//         i;

//     for (i = 0; i < children.length; i++) {
//         children[i].className = "";
//     }

//     if (el) {
//         el.className = "on";
//     }
// }
////////////////////////////////////////////////////////////
</script>

<!-- <a href="https://apis.map.kakao.com/web/documentation/#CategoryCode">services 라이브러리의 categorySearch 에서 사용하는 코드 목록이다.</a> -->

<template>
    <!-- <div id="map"></div> -->
    <div class="map_wrap">
        <div id="map" style="position: relative; overflow: hidden"></div>
        <!-- <ul id="category">
            <li id="BK9" data-order="0">
                <span class="category_bg bank"></span>
                은행
            </li>
            <li id="MT1" data-order="1">
                <span class="category_bg mart"></span>
                마트
            </li>
            <li id="PM9" data-order="2">
                <span class="category_bg pharmacy"></span>
                약국
            </li>
            <li id="OL7" data-order="3">
                <span class="category_bg oil"></span>
                주유소
            </li>
            <li id="CE7" data-order="4">
                <span class="category_bg cafe"></span>
                카페
            </li>
            <li id="CS2" data-order="5">
                <span class="category_bg store"></span>
                편의점
            </li>
        </ul> -->
    </div>
</template>

<style>
#map {
    width: 100%;
    height: 600px;
}

.wrap {
    position: absolute;
    left: 0;
    bottom: 40px;
    width: 288px;
    height: 132px;
    margin-left: -144px;
    text-align: left;
    overflow: hidden;
    font-size: 12px;
    font-family: "Malgun Gothic", dotum, "돋움", sans-serif;
    line-height: 1.5;
}
.wrap * {
    padding: 0;
    margin: 0;
}
.wrap .info {
    width: 286px;
    height: 120px;
    border-radius: 5px;
    border-bottom: 2px solid #ccc;
    border-right: 1px solid #ccc;
    overflow: hidden;
    background: #fff;
}
.wrap .info:nth-child(1) {
    border: 0;
    box-shadow: 0px 1px 2px #888;
}
.info .title {
    padding: 5px 0 0 10px;
    height: 30px;
    background: #eee;
    border-bottom: 1px solid #ddd;
    font-size: 18px;
    font-weight: bold;
}
.info .close {
    position: absolute;
    top: 10px;
    right: 10px;
    color: #888;
    width: 17px;
    height: 17px;
    background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/overlay_close.png");
}
.info .close:hover {
    cursor: pointer;
}
.info .body {
    position: relative;
    overflow: hidden;
}
.info .desc {
    position: relative;
    margin: 13px 0 0 90px;
    height: 75px;
}
.desc .ellipsis {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
.desc .jibun {
    font-size: 11px;
    color: #888;
    margin-top: -2px;
}
.info .img {
    position: absolute;
    top: 6px;
    left: 5px;
    width: 73px;
    height: 71px;
    border: 1px solid #ddd;
    color: #888;
    overflow: hidden;
}
.info:after {
    content: "";
    position: absolute;
    margin-left: -12px;
    left: 50%;
    bottom: 0;
    width: 22px;
    height: 12px;
    background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png");
}
.info .link {
    color: #5085bb;
}
</style>
