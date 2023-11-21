<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { detailArticle, deleteArticle } from "@/api/board";

// 유저 정보 받아오기
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";
const MemberStore = useMemberStore();
const { userInfo } = storeToRefs(MemberStore);

const route = useRoute();
const router = useRouter();

// const articleno = ref(route.params.articleno);
const { articleno } = route.params;

// const article = ref({});

const article = ref({
    board_no: 0,
    board_title: "",
    board_content: "",
    user_id: "",
    user_name: "",
    board_hit: 0,
    board_write_time: "",
    // fileInfos: "",
});

onMounted(() => {
    getArticle();
});

const getArticle = () => {
    // const { articleno } = route.params;
    console.log(articleno + "번글 얻으러 가자!!!");
    // API 호출
    detailArticle(
        articleno,
        ({ data }) => {
            console.log(data);
            article.value.board_no = data.board_no;
            article.value.board_title = data.board_title;
            article.value.board_content = data.board_content;
            article.value.user_id = data.user_id;
            article.value.user_name = data.user_name;
            article.value.board_write_time = data.board_write_time;
            article.value.board_hit = data.board_hit;
            // article.value.fileInfos = data.fileInfos; //
        },
        (error) => {
            console.log(error);
        }
    );
};

function moveList() {
    router.push({ name: "article-list" });
}

function moveModify() {
    console.log(article.value.user_id);
    router.push({ name: "article-modify", params: { articleno } });
}

function onDeleteArticle() {
    // const { articleno } = route.params;
    console.log(articleno + "번글 삭제하러 가자!!!");
    // API 호출
    if (window.confirm("삭제하시겠습니까?")) {
        deleteArticle(
            articleno,
            ({ data }) => {
                console.log("삭제요청");
                console.log(data);
                router.push({ name: "article-list" });
            },
            (error) => {
                console.log(error);
            }
        );
    } else {
        return false;
    }
}
</script>

<template>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-lg-10">
                <h2 class="my-3 py-3 shadow-sm bg-light text-center">
                    <mark class="sky">글보기</mark>
                </h2>
            </div>
            <div class="col-lg-10 text-start">
                <div class="row my-2">
                    <h2 class="text-secondary px-5">{{ article.board_no }}. {{ article.board_title }}</h2>
                </div>
                <div class="row">
                    <div class="col-md-8">
                        <div class="clearfix align-content-center">
                            <img
                                class="avatar me-2 float-md-start bg-light p-2"
                                src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
                            />
                            <p>
                                <span class="fw-bold">{{ article.user_name }}</span> <br />
                                <span class="text-secondary fw-light"> {{ article.board_write_time }}</span> <br />
                                <span>조회 : {{ article.board_hit }} </span>
                            </p>
                        </div>
                        <hr />
                    </div>
                    <div class="col-md-4 align-self-center text-end">댓글 : 17</div>
                    <div class="divider mb-3"></div>
                    <div class="text-secondary">
                        {{ article.board_content }}
                    </div>

                    <!-- <div v-if="article.fileInfos">
                        <ul v-for="file in article.fileInfos" :key="article.board_no">
                            <li>
                                {{ file.originalFile }}
                                <a href="#" class="filedown" :sfolder="file.saveFolder" :sfile="file.saveFile" :ofile="file.originalFile"
                                    >[다운로드]</a
                                >
                            </li>
                        </ul>
                    </div> -->

                    <div class="divider mt-3 mb-3"></div>
                    <div class="d-flex justify-content-end">
                        <button type="button" class="btn btn-outline-primary mb-3" @click="moveList">글목록</button>
                        <button type="button" class="btn btn-outline-success mb-3 ms-1" v-if="userInfo.user_id == 'admin'" @click="moveModify">
                            글수정
                        </button>
                        <button type="button" class="btn btn-outline-danger mb-3 ms-1" v-if="userInfo.user_id == 'admin'" @click="onDeleteArticle">
                            글삭제
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped></style>
