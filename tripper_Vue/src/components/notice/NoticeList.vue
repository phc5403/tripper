<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { listNotice } from "@/api/notice";

import VSelect from "@/components/common/VSelect.vue";
import NoticeListItem from "@/components/notice/item/NoticeListItem.vue";
import PageNavigation from "@/components/common/PageNavigation.vue";

// 유저 정보 받아오기
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";
const MemberStore = useMemberStore();
const { userInfo } = storeToRefs(MemberStore);

const router = useRouter();

const selectOption = ref([
    { text: "검색조건", value: "" },
    { text: "글번호", value: "notice_no" },
    { text: "제목", value: "notice_title" },
    { text: "작성자아이디", value: "user_id" },
]);

const notices = ref([]);
const currentPage = ref(1);
const totalPage = ref(0);
const { VITE_ARTICLE_LIST_SIZE } = import.meta.env;
const param = ref({
    pgno: currentPage.value,
    spp: VITE_ARTICLE_LIST_SIZE,
    key: "",
    word: "",
});

onMounted(() => {
    getNoticeList();
});

const changeKey = (val) => {
    console.log("NoticeList에서 선택한 조건 : " + val);
    param.value.key = val;
};

const getNoticeList = () => {
    console.log("서버에서 공지글 목록 얻어오자!!!", param.value);
    console.log("검색한 내용은?", param.value.key, param.value.word);
    // API 호출
    listNotice(
        param.value,
        ({ data }) => {
            console.log(data);
            notices.value = data.notices;
            console.log(notices.value); // Proxy(Array)
            currentPage.value = data.currentPage;
            totalPage.value = data.totalPageCount;
        },
        (error) => {
            console.log(error);
        }
    );
};

const onPageChange = (val) => {
    console.log(val + "번 페이지로 이동 준비 끝!!!");
    currentPage.value = val;
    param.value.pgno = val;
    getNoticeList();
};

const moveWrite = () => {
    router.push({ name: "notice-write" });
};
</script>

<template>
    <link href="https://fonts.googleapis.com/css2?family=Dongle&display=swap" rel="stylesheet" />
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-lg-10">
                <p class="fw-bold" style="font-size: 40px">&#128203; 공지사항 게시판</p>
                <hr width="100%" color="black" size="8" />
                <p style="font-size: 28px;">중요한 공지사항을 보는 공간이에요</p>
            </div>
            <div></div>
            <div class="col-lg-10 mt-5">
                <div class="row align-self-center mb-2">
                    <div class="col-md-2 text-start">
                        <button type="button" class="btn btn-outline-primary btn-sm" @click="moveWrite" v-if="userInfo.user_id == 'admin'">글쓰기</button>
                    </div>
                    <div class="col-md-5 offset-5">
                        <form class="d-flex">
                            <VSelect :selectOption="selectOption" @onKeySelect="changeKey" />
                            <div class="input-group input-group-sm">
                                <input
                                    type="text"
                                    class="form-control"
                                    v-model="param.word"
                                    @keydown.enter.prevent="getNoticeList"
                                    placeholder="검색어..."
                                    style="font-size: 24px;"
                                />
                                <button class="btn_sch" type="button" @click="getNoticeList">
                                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512">
                                        <!--! Font Awesome Pro 6.4.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2023 Fonticons, Inc. -->
                                        <path
                                            d="M416 208c0 45.9-14.9 88.3-40 122.7L502.6 457.4c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L330.7 376c-34.4 25.2-76.8 40-122.7 40C93.1 416 0 322.9 0 208S93.1 0 208 0S416 93.1 416 208zM208 352a144 144 0 1 0 0-288 144 144 0 1 0 0 288z"
                                        />
                                    </svg>
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
                <table class="table table-hover">
                    <thead>
                        <tr class="text-center">
                            <th scope="col">글번호</th>
                            <th scope="col">제목</th>
                            <th scope="col">작성자</th>
                            <th scope="col">조회수</th>
                            <th scope="col">작성일</th>
                        </tr>
                    </thead>
                    <tbody>
                        <NoticeListItem v-for="notice in notices" :key="notice.notice_no" :notice="notice"></NoticeListItem>
                    </tbody>
                </table>
            </div>
            <PageNavigation :current-page="currentPage" :total-page="totalPage" @pageChange="onPageChange"></PageNavigation>
        </div>
    </div>
</template>

<style scoped>
.board-title {
    border-radius: 0.75rem;
}
.btn_sch {
    right: 0;
    width: 40px;
    height: 40px;
    border: 0;
    height: auto;

    background-size: 18px;
}
thead {
    background-color: gray;
}
p {
    font-family: "Dongle", sans-serif;
}
th{
    font-family: "Dongle", sans-serif;
    font-size: 28px;
}
button, input{
    font-family: "Dongle", sans-serif;
    font-size: 24px;
}
</style>
