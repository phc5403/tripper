<script setup>
import VKakaoMap from "@/components/common/VKakaoMap.vue";
import VSelect from "@/components/common/VSelect.vue";
import { ref, onMounted } from "vue";
import { listSido, listGugun } from "@/api/map";
import { listAttraction } from "@/api/attraction";
import AttractionListItem from "@/components/attraction/item/AttractionListItem.vue";
import PageNavigation from "@/components/common/PageNavigation.vue";
import { useContentStore } from "@/stores/attractionInfo";
import AttractionDetailModal from "@/components/teleport/AttractionDetailModal.vue";

const sidoList = ref([]);
const gugunList = ref([{ text: "구군선택", value: "" }]);
const visitAttractions = ref([]); // 검색 결과에 따른 목록 저장될 곳
const selectAttraction = ref({}); // 시도, 구군 선택 가능 항목들

// 검색할 컨텐츠 선택
const store = useContentStore();
const contents = store.contents; // 저장소에서 컨텐츠 항목 정보 가져옴.
const choiceContent = ref([]); // 선택한 컨텐츠만 저장할 배열
const choiceAll = ref(false); // 전체 선택 버튼 활성화 여부
//

const checkAll = () => {
    if (choiceAll.value) {
        console.log("true");
        choiceContent.value = contents.map((content) => content.id);
    } else {
        console.log("false");
        choiceContent.value = [];
    }
};

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
    // getlistAttraction();
};

const getlistAttraction = () => {
    console.log("서버에서 여행지 목록 얻어오기");
    console.log(param.value);
    console.log(choiceContent.value);
    // param.value.contents = updateContent;
    // console.log(selectedContent);
    // console.log(selectedContent.value);
    // param.value.contents = updateContent;
    // console.log(param.value.contents);
    listAttraction(
        param.value,
        choiceContent.value,
        ({ data }) => {
            console.log("getlistAttraction() : listAttraction Success");
            console.log(data);
            // console.log(data.value);  // undefined
            console.log("--------------------");
            // visitAttractions.value = data.items[0].item;
            visitAttractions.value = data.attractions;
            console.log(visitAttractions.value);
            // console.log(typeof visitAttractions.value);
            currentPage.value = data.currentPage;
            totalPage.value = data.totalPageCount; // 1
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

const updateContent = (selectedContent) => {
    console.log("현재 선택된 컨텐츠");
    console.log(selectedContent); // true
    param.value.contents = selectedContent;
    console.log(typeof updateContent); // function
    console.log(typeof selectedContent); // boolean
};

// Modal
const mShow = ref(false);

const cId = ref(0);
const openModal = function (paramId) {
    // 모달 생성
    // mShow.value = true;
    cId.value = paramId;
    mShow.value = !mShow.value;

    // if (mShow.value === false) {
    //     mShow.value = true;
    // }

    console.log("openModal() : " + mShow.value);
};

const closeModal = function () {
    mShow.value = !mShow.value;
    console.log("closeModal() : " + mShow.value);
};
</script>

<template>
    <link href="https://fonts.googleapis.com/css2?family=Dongle&display=swap" rel="stylesheet" />
    <main>
        <div class="container">
            <div class="col-lg-12">
                <p class="fw-bold" style="font-size: 40px">&#127757; 지역별 관광 정보</p>
                <hr width="100%" color="black" size="8" />
            </div>

            <div class="row mb-2">
                <div class="col d-flex flex-row-reverse">
                    <VSelect :selectOption="sidoList" @onKeySelect="onChangeSido" />
                </div>
                <div class="col">
                    <VSelect :selectOption="gugunList" @onKeySelect="onChangeGugun" />
                </div>
            </div>

            <div>
                <div
                    class="form-check form-check-inline"
                    v-for="content in contents"
                    :key="content.id"
                >
                    <input
                        class="form-check-input"
                        type="checkbox"
                        :id="'check_' + content.id"
                        :value="content.id"
                        v-model="choiceContent"
                    />
                    <label class="form-check-label" :for="'check_' + content.id">{{
                        content.type
                    }}</label>
                </div>

                <div class="form-check form-check-inline">
                    <input
                        class="form-check-input"
                        type="checkbox"
                        id="check_all"
                        value="choiceAll"
                        v-model="choiceAll"
                        @change="checkAll"
                    />
                    <label class="form-check-label" for="check_all">전체</label>
                </div>

                <button class="btn btn-dark" type="button" @click="getlistAttraction">검색</button>
            </div>

            <div class="container d-flex flex-row mt-3">
                <div class="flex-grow-1">
                    <VKakaoMap
                        :attractions="visitAttractions"
                        :selectAttraction="selectAttraction"
                    />
                </div>

                <div class="flex-grow-2">
                    <Teleport to="body">
                        <AttractionDetailModal
                            :cId="cId"
                            :mShow="mShow"
                            @closeBtn="mShow = false"
                            :attractions="visitAttractions"
                        >
                        </AttractionDetailModal>
                    </Teleport>

                    <div style="overflow: auto; max-height: 500px" class="mb-3">
                        <table class="table table-hover table-bordered">
                            <thead>
                                <tr class="text-center">
                                    <th>대표이미지</th>
                                    <th>관광지명</th>
                                    <th>주소</th>
                                </tr>
                            </thead>
                            <tbody id="trip-list" style="height: auto">
                                <AttractionListItem
                                    v-for="attraction in visitAttractions"
                                    :key="attraction.countent_id"
                                    @click="viewAttraction(attraction)"
                                    :attraction="attraction"
                                    @handleImageClick="openModal"
                                >
                                </AttractionListItem>
                            </tbody>
                        </table>
                    </div>
                    <PageNavigation
                        :current-page="currentPage"
                        :total-page="totalPage"
                        @pageChange="onPageChange"
                    ></PageNavigation>
                </div>
            </div>
        </div>
    </main>
</template>

<style scoped>
.container {
    display: flex;
    flex-direction: column;
}

.flex-grow-2 {
    width: 400px;
    height: 600px;
}

::-webkit-scrollbar {
    display: none;
}
p {
    font-family: "Dongle", sans-serif;
}
th {
    font-family: "Dongle", sans-serif;
    font-size: 24px;
}
</style>
