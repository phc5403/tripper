<script setup>
import { ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { registArticle, getModifyArticle, modifyArticle } from "@/api/board";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";

const router = useRouter();
const route = useRoute();

const props = defineProps({ type: String });
const isUseId = ref(true);
const { articleno } = route.params;

const MemberStore = useMemberStore();
const { userInfo } = storeToRefs(MemberStore);

const article = ref({
    board_no: 0,
    user_id: userInfo.value.user_id,
    user_name: "",
    board_title: "",
    board_content: "",
    board_hit: 0,
    board_write_time: "",
    // fileInfos: "",
});

console.log("====================");
console.log("글 작성자");
console.log(userInfo.value);
console.log(userInfo.value.user_id); // user_id 뽑기
console.log(article.user_id); // undefined

if (props.type === "modify") {
    console.log(articleno + "번글 얻어와서 수정할거야");
    // ???????
    // API 호출
    isUseId.value = true;
    console.log(userInfo.value.user_id); // ssafy != admin
    getModifyArticle(
        articleno,
        ({ data }) => {
            console.log(data); // 수정할 대상의 데이터
            article.value.board_no = articleno;
            article.value.user_id = data.user_id;
            article.value.board_title = data.board_title;
            article.value.board_content = data.board_content;
            // article.value.fileInfos = data.fileInfos;
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
        (response) => {
            let msg = "글등록 처리시 문제 발생했습니다.";
            if (response.status == 202) {
                msg = "부적절한 내용이 포함되어 글 등록에 실패했습니다.";
                console.log("------------------------");
                console.log(msg);
            }
            if (response.status == 201) msg = "글등록이 완료되었습니다.";
            alert(msg);
            // console.log(data);
            moveList();
        },
        (error) => {
            console.error(error);
        }
    );
}

function updateArticle() {
    console.log(articleno + "번글 수정하자!!", article.value);
    // API 호출
    modifyArticle(
        article.value,
        // article.value.board_no,
        (response) => {
            // console.log(data);
            let msg = "글 수정 처리시 문제 발생했습니다.";
            if (response.status == 202) {
                msg = "부적절한 내용이 포함되어 글 등록에 실패했습니다.";
                console.log("------------------------");
                console.log(msg);
            }
            if (response.status == 201) msg = "글 수정이 완료되었습니다.";
            alert(msg);
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
            <!-- <input type="text" class="form-control" v-model="article.user_id" :disabled="isUseId" placeholder="작성자ID..." /> -->
            <input type="text" class="form-control" v-model="article.user_id" :disabled="isUseId" />
        </div>
        <div class="mb-3">
            <label for="board_title" class="form-label">제목 : </label>
            <input type="text" class="form-control" v-model="article.board_title" placeholder="제목..." />
        </div>
        <div class="mb-3">
            <label for="board_content" class="form-label">내용 : </label>
            <textarea class="form-control" v-model="article.board_content" rows="10"></textarea>
        </div>

        <!-- <div class="mb-3">
            <label for="upfile" class="form-label">파일:</label>
            <input type="file" class="form-control border" id="upfile" name="upfile" multiple="multiple" />
        </div> -->
        <div class="col-auto text-center">
            <button type="submit" class="btn btn-outline-primary mb-3" v-if="type === 'regist'">글작성</button>
            <button type="submit" class="btn btn-outline-success mb-3" v-else>글수정</button>
            <button type="button" class="btn btn-outline-danger mb-3 ms-1" @click="moveList">목록으로이동...</button>
        </div>
    </form>
</template>

<style scoped></style>
