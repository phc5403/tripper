<script setup>
import { ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { registNotice, getModifyNotice, modifyNotice } from "@/api/notice";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";

const router = useRouter();
const route = useRoute();

const props = defineProps({ type: String });
const isUseId = ref(true);
const { noticeno } = route.params;

const MemberStore = useMemberStore();
const { userInfo } = storeToRefs(MemberStore);

const notice = ref({
  notice_no: 0,
  user_id: userInfo.value.user_id,
  user_name: "",
  notice_title: "",
  notice_content: "",
  notice_hit: 0,
  notice_write_time: "",
});

console.log("====================");
console.log("글 작성자");
console.log(userInfo.value);
console.log(userInfo.value.user_id); // user_id 뽑기
// console.log(article.user_id); // undefined

if (props.type === "modify") {
  console.log(noticeno + "번 공지글 얻어와서 수정할거야");
  // ???????
  // API 호출
  isUseId.value = true;
  console.log(userInfo.value.user_id); // ssafy != admin
  getModifyNotice(
    noticeno,
    ({ data }) => {
      console.log(data); // 수정할 대상의 데이터
      notice.value.notice_no = noticeno;
      notice.value.user_id = data.user_id;
      notice.value.notice_title = data.notice_title;
      notice.value.notice_content = data.notice_content;
      notice.value.fileInfos = data.fileInfos;
    },
    (error) => {
      console.log(error);
    }
  );
}

const titleErrMsg = ref("");
const contentErrMsg = ref("");
watch(
  () => notice.value.notice_title,
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
  () => notice.value.notice_content,
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
    props.type === "regist" ? writeNotice() : updateNotice();
  }
}

function writeNotice() {
  console.log("공지글 등록하자!!", notice.value);


  // const formData = new FormData(); // FormData 객체 생성
  // formData.append("user_id", article.value.user_id);
  // formData.append("board_title", article.value.board_title);
  // formData.append("board_content", article.value.board_content);

  // const upfileInput = document.getElementById("upfile"); // 파일 입력 요소 가져오기
  // const files = upfileInput.files; // 선택된 파일들

  // for (let i = 0; i < files.length; i++) {
  //   formData.append("upfile", files[i]); // FormData에 파일 추가
  // }

  // console.log("폼 보내자!!", formData);
  // console.log("이거맞나", formData.get("upfile"));
  // API 호출
  registNotice(
    notice.value,
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

function updateNotice() {
  console.log(noticeno + "번글 수정하자!!", notice.value);
  // API 호출
  modifyNotice(
    notice.value,
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
  router.push({ name: "notice-list" });
}
</script>

<template>
  <link href="https://fonts.googleapis.com/css2?family=Dongle&display=swap" rel="stylesheet" />
  <form @submit.prevent="onSubmit" enctype="multipart/form-data">
    <div class="mb-3">
      <label for="userid" class="form-label">작성자 ID : </label>
      <!-- <input type="text" class="form-control" v-model="article.user_id" :disabled="isUseId" placeholder="작성자ID..." /> -->
      <input type="text" class="form-control" v-model="notice.user_id" :disabled="isUseId" />
    </div>
    <div class="mb-3">
      <label for="notice_title" class="form-label">제목 : </label>
      <input type="text" class="form-control" v-model="notice.notice_title" placeholder="제목..." />
    </div>
    <div class="mb-3">
      <label for="notice_content" class="form-label">내용 : </label>
      <textarea class="form-control" v-model="notice.notice_content" rows="10"></textarea>
    </div>
    <!-- <div class="mb-3">
      <label for="upfile" class="form-label">파일 : </label>
      <input
        type="file"
        class="form-control border"
        id="upfile"
        name="upfile"
        multiple="multiple"
      />
    </div> -->

    <!-- <div class="mb-3">
            <label for="upfile" class="form-label">파일:</label>
            <input type="file" class="form-control border" id="upfile" name="upfile" multiple="multiple" />
        </div> -->
    <div class="col-auto text-center">
      <button type="submit" class="btn btn-outline-primary mb-3" v-if="type === 'regist'">
        글작성
      </button>
      <button type="submit" class="btn btn-outline-success mb-3" v-else>글수정</button>
      <button type="button" class="btn btn-outline-danger mb-3 ms-1" @click="moveList">
        목록으로이동...
      </button>
    </div>
  </form>
</template>

<style scoped>
p{
    font-family: "Dongle", sans-serif;   
}
label{
    font-family: "Dongle", sans-serif;
    font-size: 30px;
}
input, textarea{
    font-family: "Dongle", sans-serif;
    font-size: 26px;
}
button{
    font-family: "Dongle", sans-serif;
    font-size: 24px;
}
</style>
