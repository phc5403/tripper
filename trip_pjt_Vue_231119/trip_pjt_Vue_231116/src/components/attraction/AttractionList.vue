<script setup>
import VKakaoMap from "@/components/common/VKakaoMap.vue";
import VSelect from "@/components/common/VSelect.vue";
import { ref, onMounted } from "vue";
import { listSido, listGugun } from "@/api/map";
import { listAttraction } from "@/api/attraction";
import PageNavigation from "@/components/common/PageNavigation.vue";

const sidoList = ref([]);
const gugunList = ref([{ text: "구군선택", value: "" }]);
const visitAttractions = ref([]);
const selectAttraction = ref({});

// 페이지네이션
const currentPage = ref(1);
const totalPage = ref(0);
const { VITE_ARTICLE_LIST_SIZE } = import.meta.env;

const param = ref({
    // serviceKey: VITE_OPEN_API_SERVICE_KEY,
    // pageNo: 1,
    // numOfRows: 20,
    // zscode: 0,
    sido_code: 0,
    gugun_code: 0,

    // 페이지네이션
    pgno: currentPage.value,
    spp: VITE_ARTICLE_LIST_SIZE,
    key: "",
    word: "",
});

onMounted(() => {
    getSidoList();
});

// 목록에서 검색
// const changeKey = (val) => {
//     console.log("AttractionList에서 선택한 조건 : " + val);
//     param.value.key = val;
// };

const onPageChange = (val) => {
    console.log(val + "번 페이지로 이동 준비 끝!!!"); // NaN
    currentPage.value = val;
    param.value.pgno = val;
    getlistAttraction();
};

const getSidoList = () => {
    console.log("시도 리스트 얻어오기", param.value);
    listSido(
        param.value,
        ({ data }) => {
            let options = [];
            options.push({ text: "시도선택", value: "" });
            data.forEach((sido) => {
                options.push({ text: sido.sido_name, value: sido.sido_code });
            });
            sidoList.value = options;
            currentPage.value = data.currentPage;
            totalPage.value = data.totalPageCount;
        },
        (err) => {
            console.log(err);
        }
    );
};

const onChangeSido = (val) => {
    console.log("onChangeSido => ");
    console.log(val);
    param.value.sido_code = val;
    listGugun(
        // param.value,
        { sido_code: val },
        ({ data }) => {
            let options = [];
            options.push({ text: "구군선택", value: "" });
            data.forEach((gugun) => {
                options.push({ text: gugun.gugun_name, value: gugun.gugun_code });
            });
            gugunList.value = options;
            // currentPage.value = data.currentPage;
            // totalPage.value = data.totalPageCount;
        },
        (err) => {
            console.log(err);
        }
    );
};

// 파라미터 -> 구군 코드
const onChangeGugun = (val) => {
    // param.value.zscode = val;

    console.log("onChangeGugun =>");
    console.log(val);
    param.value.gugun_code = val;
    getlistAttraction();
};

// const getlistAttraction = () => {
//     listAttraction(
//         param.value,
//         ({ data }) => {
//             visitAttractions.value = data.items[0].item;
//         },
//         (err) => {
//             console.log(err);
//         }
//     );
// };

const getlistAttraction = () => {
    console.log("getlistAttraction");
    listAttraction(
        param.value,
        ({ data }) => {
            console.log("getlistAttraction() : listAttraction Success");
            console.log(data);
            console.log("--------------------");
            // visitAttractions.value = data.items[0].item;
            visitAttractions.value = data;
            currentPage.value = data.currentPage;
            totalPage.value = data.totalPageCount;
        },
        (err) => {
            console.log(err);
        }
    );
};

const viewAttraction = (attr) => {
    console.log("viewAttraction ->");
    console.log(attr);
    selectAttraction.value = attr;
};
</script>

<template>
    <main>
        <div class="continer">
            <!-- title -->
            <div class="container text-center pb-3 mt-5 mb-3 w-25 border-bottom border-4">
                <div class="fs-2 fw-semibold">지역별 관광정보</div>
            </div>

            <!-- search -->
            <!-- <div class="container pt-3">
                <form id="attraction-search" class="d-flex my-3 w-75 m-auto" method="post" role="search">
                    <select id="search-area" class="form-select me-2" name="sido-code">
                        <option value="0" selected>검색 할 지역 선택 -> 대분류(시도)</option>
                    </select>

                    <select id="search-area" class="form-select me-2" name="sido-code">
                        <option value="0" selected>검색 할 지역 선택 -> 서분류(구군)</option>
                    </select>

                    <select id="search-content-id" class="form-select me-2" name="content-type-id">
                        <option value="0" selected>관광지 유형</option>
                        <option value="12">관광지</option>
                        <option value="14">문화시설</option>
                        <option value="15">축제공연행사</option>
                        <option value="25">여행코스</option>
                        <option value="28">레포츠</option>
                        <option value="32">숙박</option>
                        <option value="38">쇼핑</option>
                        <option value="39">음식점</option>
                    </select>
                    <input id="search-keyword" class="form-control me-2" type="search" placeholder="검색어" aria-label="검색어" />
                    <button id="btn-search" class="btn btn-outline-dark w-50" type="button">검색</button>
                </form>
            </div> -->
            <div class="row mb-2">
                <div class="col d-flex flex-row-reverse">
                    <VSelect :selectOption="sidoList" @onKeySelect="onChangeSido" />
                </div>
                <div class="col">
                    <VSelect :selectOption="gugunList" @onKeySelect="onChangeGugun" />
                </div>
            </div>

            <div class="container">
                <div class="row">
                    <!-- map -->
                    <div>
                        <!-- <div id="map" class="container border w-100 shadow-sm"></div> -->
                        <VKakaoMap :attractions="visitAttractions" :selectAttraction="selectAttraction" />
                    </div>
                </div>

                <!-- 목록 리스트 -->
                <div class="row">
                    <!-- <form class="d-flex">
                        <VSelect :selectOption="selectOption" @onKeySelect="changeKey" />
                        <div class="input-group input-group-sm">
                            <input type="text" class="form-control" v-model="param.word" placeholder="검색어..." />
                            <button class="btn btn-dark" type="button" @click="getArticleList">검색</button>
                        </div>
                    </form> -->

                    <!-- search list -->
                    <!-- <div class="col-6 ms-auto mt-4 mb-5 overflow-scroll overflow-x-hidden shadow-sm"> -->
                    <div>
                        <div id="search">
                            <table class="table table-hover table-bordered">
                                <thead>
                                    <tr class="text-center">
                                        <th>대표이미지</th>
                                        <th>관광지명</th>
                                        <th>주소</th>
                                        <th></th>
                                    </tr>
                                </thead>
                                <tbody id="trip-list">
                                    <tr class="text-center" v-for="attr in visitAttractions" :key="attr.content_id" @click="viewAttraction(attr)">
                                        <!-- <th>{{ attr.first_image }}</th> -->
                                        <th>
                                            <img
                                                class="attrImg"
                                                v-if="attr.first_image.length == 0"
                                                src="https://www.verdantis.com/wp-content/uploads/2016/04/noimg.jpg"
                                            />
                                            <img class="attrImg" v-else :src="attr.first_image" />
                                        </th>
                                        <td>{{ attr.title }}</td>
                                        <td>{{ attr.addr1 }}</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

                <PageNavigation :current-page="currentPage" :total-page="totalPage" @pageChange="onPageChange"></PageNavigation>
            </div>
        </div>
    </main>
</template>

<style scoped>
.attrImg {
    width: 200px;
    height: 200px;
}
</style>
