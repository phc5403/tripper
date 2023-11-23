<script setup>
import { ref, watch } from "vue";
import { findUserPwd } from "@/api/user";
import { useRouter } from "vue-router";

const router = useRouter();

const user = ref({
  user_name: "",
  user_id: "",
});

const nameErrMsg = ref("");
const idErrMsg = ref("");

watch(
  //유저 이름 체크
  () => user.value.user_name,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 20) {
      nameErrMsg.value = "성함을 다시 입력하세요.";
    } else {
      nameErrMsg.value = "";
    }
  }
);
watch(
  //유저 아이디 체크
  () => user.value.user_id,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 20) {
      idErrMsg.value = "아이디를 다시 입력하세요.";
    } else {
      idErrMsg.value = "";
    }
  }
);

function onSubmit() {
  console.log(nameErrMsg.value);
  console.log(idErrMsg.value);
  if (nameErrMsg.value) {
    alert(nameErrMsg.value);
  } else if (idErrMsg.value) {
    alert(idErrMsg.value);
  } else {
    findPwd();
  }
}
function findPwd() {
  console.log(
    "찾을 유저의 정보는? " + user.value.user_name,
    user.value.user_id
  );
  findUserPwd(
    user.value,
    ({ data }) => {
      // console.log(data);
      if (data) {
        alert(data);
        router.push("/user/login");
      } else {
        alert("일치하는 유저 정보가 없습니다.");
      }
    },
    (error) => {
      console.log(error);
    }
  );
}

function mvLogin(){
  router.push("/user/login")
}

// function findPwd() {
//   console.log("찾을 유저의 정보는? " + user.value.user_name, user.value.user_id);
//   findUserPwd(
//     user.value,
//     (response) => {
//       console.log(response);
//       alert(response.status);
//       router.push("/user/login");
//     },
//     (error) => {
//       console.log(error);
//     }
//   );
// }
</script>

<template>
  <link href="https://fonts.googleapis.com/css2?family=Dongle&display=swap" rel="stylesheet" />
  <div class="container">
    <div class="main border">
      <div>
        <div>
          <h4>
            <p class="bi bi-person-plus-fill" style="font-size: 40px;">비밀번호 찾기</p>
          </h4>
          <br />
        </div>

        <form @submit.prevent="onSubmit">
          <div class="mb-1">
            <label for="userid" class="form-label">이름</label><br />
            <input
              type="text"
              class="form-control"
              v-model="user.user_name"
              placeholder="이름을 입력하세요"
            />
          </div>
          <div class="mb-1">
            <label for="username" class="form-label">아이디</label><br />

            <input
              type="text"
              class="form-control"
              v-model="user.user_id"
              placeholder="아이디를 입력하세요"
            />
          </div>

          <div class="col-auto text-center mt-3">
            <button type="submit" class="btn btn-primary mb-3 me-2">찾기</button>
            <button type="button" class="btn btn-primary mb-3" @click="mvLogin">뒤로</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
}
.container > div {
  width: 30%;
  padding: 10px;
  border-radius: 8px;
  box-shadow: inset 0 0 1px 1px hlsa(0, 0%, 100%, 0.9),
    0 20px 27px 0 rgba(0, 0, 0, 0.05) !important;
}

.main {
  align-items: center;
  background-color: whitesmoke;
  flex-direction: row;
}
.bi {
  color: rgb(206, 163, 255);
  text-align: center;
}
.btn {
  background-color: rgb(206, 163, 255);
  border-color: white;
}
.btn-find {
  font-size: 12px;
  border: none;
  background-color: whitesmoke;
}
.col-auto {
  justify-content: space-between;
}
.col-auto {
    justify-content: space-between;
}
p, span{
    font-family: "Dongle", sans-serif;   
}
label{
    font-family: "Dongle", sans-serif;
    font-size: 30px;
}
input{
    font-family: "Dongle", sans-serif;
    font-size: 26px;
}
button{
    font-family: "Dongle", sans-serif;
    font-size: 24px;
    
}
</style>
