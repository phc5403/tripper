<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { detailNotice, deleteNotice } from "@/api/notice";

// 유저 정보 받아오기
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";
const MemberStore = useMemberStore();
const { userInfo } = storeToRefs(MemberStore);

const route = useRoute();
const router = useRouter();

// const articleno = ref(route.params.articleno);
const { noticeno } = route.params;

// const article = ref({});
// console.log("유저정보????????????", userInfo.value.user_img);

const notice = ref({
  notice_no: 0,
  notice_title: "",
  notice_content: "",
  user_id: "",
  user_name: "",
  notice_hit: 0,
  notice_write_time: "",
  // fileInfos: "",
  notice_img: "",
});

onMounted(() => {
  getNotice();
});

const getNotice = () => {
  // const { articleno } = route.params;
  console.log(noticeno + "번 공지글 얻으러 가자!!!");
  // API 호출
  detailNotice(
    noticeno,
    ({ data }) => {
      console.log(data);
      notice.value.notice_no = data.notice_no;
      notice.value.notice_title = data.notice_title;
      notice.value.notice_content = data.notice_content;
      notice.value.user_id = data.user_id;
      notice.value.user_name = data.user_name;
      notice.value.notice_write_time = data.notice_write_time;
      notice.value.notice_hit = data.notice_hit;
      notice.value.notice_img = data.user_img; //글을 작성한 유저의 프로필 사진
      // article.value.fileInfos = data.fileInfos; //
      console.log(notice.value.notice_img);
    },
    (error) => {
      console.log(error);
    }
  );
};

function moveList() {
  router.push({ name: "notice-list" });
}

function moveModify() {
  console.log(notice.value.user_id);
  router.push({ name: "notice-modify", params: { noticeno } });
}

function onDeleteNotice() {
  // const { articleno } = route.params;
  console.log(noticeno + "번 공지글 삭제하러 가자!!!");
  // API 호출
  if (window.confirm("삭제하시겠습니까?")) {
    deleteNotice(
      noticeno,
      ({ data }) => {
        console.log("삭제요청");
        console.log(data);
        router.push({ name: "notice-list" });
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
  <link href="https://fonts.googleapis.com/css2?family=Dongle&display=swap" rel="stylesheet" />
  <div class="container" style="min-height: 100vh">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <p class="fw-bold" style="font-size: 40px">
          {{ notice.notice_no }}. {{ notice.notice_title }}
        </p>
        <hr width="100%" color="black" size="4" />
      </div>
      <div class="col-lg-10 text-start mt-3">
        <div class="row">
          <div class="col">
            <div class="col mb-3">
              <img class="avatar me-2" v-if="notice.notice_img" :src="notice.notice_img" />

              <span class="fw-bold p-3">{{ notice.user_name }}</span>
              <span class="text-secondary fw-light m-1"> {{ notice.notice_write_time }}</span>
              <div class="float-end">
                <svg xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 576 512">
                  <!--! Font Awesome Free 6.4.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2023 Fonticons, Inc. -->
                  <path
                    d="M288 80c-65.2 0-118.8 29.6-159.9 67.7C89.6 183.5 63 226 49.4 256c13.6 30 40.2 72.5 78.6 108.3C169.2 402.4 222.8 432 288 432s118.8-29.6 159.9-67.7C486.4 328.5 513 286 526.6 256c-13.6-30-40.2-72.5-78.6-108.3C406.8 109.6 353.2 80 288 80zM95.4 112.6C142.5 68.8 207.2 32 288 32s145.5 36.8 192.6 80.6c46.8 43.5 78.1 95.4 93 131.1c3.3 7.9 3.3 16.7 0 24.6c-14.9 35.7-46.2 87.7-93 131.1C433.5 443.2 368.8 480 288 480s-145.5-36.8-192.6-80.6C48.6 356 17.3 304 2.5 268.3c-3.3-7.9-3.3-16.7 0-24.6C17.3 208 48.6 156 95.4 112.6zM288 336c44.2 0 80-35.8 80-80s-35.8-80-80-80c-.7 0-1.3 0-2 0c1.3 5.1 2 10.5 2 16c0 35.3-28.7 64-64 64c-5.5 0-10.9-.7-16-2c0 .7 0 1.3 0 2c0 44.2 35.8 80 80 80zm0-208a128 128 0 1 1 0 256 128 128 0 1 1 0-256z"
                  />
                </svg>
                <span class="m-1">{{ notice.notice_hit }} </span>
              </div>
              <div class="float-end">
                <svg xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 512 512">
                  <!--! Font Awesome Free 6.4.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2023 Fonticons, Inc. -->
                  <path
                    d="M123.6 391.3c12.9-9.4 29.6-11.8 44.6-6.4c26.5 9.6 56.2 15.1 87.8 15.1c124.7 0 208-80.5 208-160s-83.3-160-208-160S48 160.5 48 240c0 32 12.4 62.8 35.7 89.2c8.6 9.7 12.8 22.5 11.8 35.5c-1.4 18.1-5.7 34.7-11.3 49.4c17-7.9 31.1-16.7 39.4-22.7zM21.2 431.9c1.8-2.7 3.5-5.4 5.1-8.1c10-16.6 19.5-38.4 21.4-62.9C17.7 326.8 0 285.1 0 240C0 125.1 114.6 32 256 32s256 93.1 256 208s-114.6 208-256 208c-37.1 0-72.3-6.4-104.1-17.9c-11.9 8.7-31.3 20.6-54.3 30.6c-15.1 6.6-32.3 12.6-50.1 16.1c-.8 .2-1.6 .3-2.4 .5c-4.4 .8-8.7 1.5-13.2 1.9c-.2 0-.5 .1-.7 .1c-5.1 .5-10.2 .8-15.3 .8c-6.5 0-12.3-3.9-14.8-9.9c-2.5-6-1.1-12.8 3.4-17.4c4.1-4.2 7.8-8.7 11.3-13.5c1.7-2.3 3.3-4.6 4.8-6.9c.1-.2 .2-.3 .3-.5z"
                  />
                </svg>
                <span class="m-1">17</span>
              </div>
            </div>
            <hr />
          </div>
          <div class="divider mb-3"></div>
          <div class="content" style="white-space: pre">
            {{ notice.notice_content }}
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
            <button type="button" class="btn btn-outline-primary mb-3" @click="moveList">
              글목록
            </button>
            <button
              type="button"
              class="btn btn-outline-success mb-3 ms-1"
              v-if="userInfo.user_id == 'admin'"
              @click="moveModify"
            >
              글수정
            </button>
            <button
              type="button"
              class="btn btn-outline-danger mb-3 ms-1"
              v-if="userInfo.user_id == 'admin'"
              @click="onDeleteNotice"
            >
              글삭제
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
img {
  align-items: center;
  width: 50px;
  height: 50px;
  border-radius: 100%;
}
p{
    font-family: "Dongle", sans-serif;   
}
label, span{
    font-family: "Dongle", sans-serif;
    font-size: 30px;
}
button{
    font-family: "Dongle", sans-serif;
    font-size: 24px;
}
.content{
  font-family: "Dongle", sans-serif;
    font-size: 26px;
}
</style>
