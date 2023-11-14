<script setup>
import { ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { registArticle, getModifyArticle, modifyArticle } from "@/api/board";
import axios from "axios";

const router = useRouter();
const route = useRoute();

const props = defineProps({ type: String });

const isUseId = ref(false);

const { articleno } = route.params;

const article = ref({
    board_no: 0,
    board_title: "",
    board_content: "",
    user_id: "",
    user_name: "",
    board_hit: 0,
    board_wrtie_time: "",
});

if (props.type === "modify") {
    console.log(articleno + "번글 얻어와서 수정할거야");
    // API 호출
    isUseId.value = true;
    getModifyArticle(
        articleno,
        ({ data }) => {
            console.log(data); // 수정할 대상의 데이터
            article.value.board_no = articleno;
            article.value.user_id = data.user_id;
            article.value.board_title = data.board_title;
            article.value.board_content = data.board_content;
        },
        (error) => {
            console.log(error);
        }
    );
}

const titleErrMsg = ref("");
const contentErrMsg = ref("");
watch(
    () => article.value.board_title,
    (value) => {
        console.log(value);
        let len = value.length;
        if (len == 0 || len > 30) {
            titleErrMsg.value = "제목을 확인해 주세요!!!";
        } else titleErrMsg.value = "";
    },
    { immediate: true }
);
watch(
    () => article.value.board_content,
    (value) => {
        let len = value.length;
        if (len == 0 || len > 500) {
            contentErrMsg.value = "내용을 확인해 주세요!!!";
        } else contentErrMsg.value = "";
    },
    { immediate: true }
);

function onSubmit() {
    // event.preventDefault();

    if (titleErrMsg.value) {
        alert(titleErrMsg.value);
    } else if (contentErrMsg.value) {
        alert(contentErrMsg.value);
    } else {
        props.type === "regist" ? writeArticle() : updateArticle();
    }
}

function writeArticle() {
    console.log("글등록하자!!", article.value);
    // API 호출
    registArticle(
        article.value,
        ({ data }) => {
            console.log(data);
            moveList();
        },
        (error) => {
            console.log(error);
        }
    );
}

function updateArticle() {
    console.log(articleno + "번글 수정하자!!", article.value);
    // API 호출
    modifyArticle(
        article.value,
        // article.value.board_no,
        ({ data }) => {
            console.log(data);
            /* */
            moveList();
        },
        (error) => {
            console.log(error);
        }
    );
}

function moveList() {
    router.push({ name: "article-list" });
}
</script>

<template>
    <form @submit.prevent="onSubmit">
        <div class="mb-3">
            <label for="userid" class="form-label">작성자 ID : </label>
            <input type="text" class="form-control" v-model="article.user_id" :disabled="isUseId" placeholder="작성자ID..." />
        </div>
        <div class="mb-3">
            <label for="board_title" class="form-label">제목 : </label>
            <input type="text" class="form-control" v-model="article.board_title" placeholder="제목..." />
        </div>
        <div class="mb-3">
            <label for="board_content" class="form-label">내용 : </label>
            <textarea class="form-control" v-model="article.board_content" rows="10"></textarea>
        </div>
        <div class="col-auto text-center">
            <button type="submit" class="btn btn-outline-primary mb-3" v-if="type === 'regist'">글작성</button>
            <button type="submit" class="btn btn-outline-success mb-3" v-else>글수정</button>
            <button type="button" class="btn btn-outline-danger mb-3 ms-1" @click="moveList">목록으로이동...</button>
        </div>
    </form>
</template>

<style scoped></style>
