<script setup>
import { ref } from "vue";
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";
import { useMenuStore } from "@/stores/menu";

const router = useRouter();

const memberStore = useMemberStore();

const { isLogin } = storeToRefs(memberStore);
const { userLogin, getUserInfo } = memberStore;
const { changeMenuState } = useMenuStore();

const loginUser = ref({
  user_id: localStorage.getItem("savedUserId") || "",
  user_pwd: "",
});

const login = async () => {
  console.log("로그인 중입니다.");
  await userLogin(loginUser.value);
  let token = sessionStorage.getItem("accessToken");
  console.log("1. ", token);
  console.log("isLogin: ", isLogin.value);
  if (isLogin.value) {
    console.log("들어왔어유");
    if (!document.getElementById("saveid").checked) {
      localStorage.removeItem("savedUserId");
    }
    getUserInfo(token);
    changeMenuState();

    router.push("/");
  } else {
    console.log("안들어왔어유");
  }
  //   if (!document.getElementById("saveid").checked) {
  //     localStorage.removeItem("savedUserId");
  //   }
};

const saveId = () => {
  if (document.getElementById("saveid").checked) {
    localStorage.setItem("savedUserId", loginUser.value.user_id);
  } else {
    localStorage.removeItem("savedUserId");
  }
};

const moveUserPasswordFind = () => {
  // 비밀번호 찾기 컴포넌트로 이동
  router.push("/user/findpwd");
};

// const join = function () {
//   router.push("/user/join");
// };
</script>

<template>
  <link href="https://fonts.googleapis.com/css2?family=Dongle&display=swap" rel="stylesheet" />
  <div class="container">
    <div class="main border">
      <div>
        <div>
          <h4>
            <p class="bi bi-person-plus-fill" style="font-size: 40px;">로그인</p>
          </h4>
          <br />
        </div>

        <form>
          <div class="form-check mb-3 float-end">
            <input class="form-check-input" type="checkbox" id="saveid" @change="saveId" />
            <label class="form-check-label" for="saveid"> 아이디저장 </label>
          </div>
          <div class="mb-1">
            <label for="userid" class="form-label">아이디</label><br />
            <input
              type="text"
              class="form-control"
              v-model="loginUser.user_id"
              placeholder="아이디..."
            />
          </div>
          <div class="mb-1">
            <label for="userpwd" class="form-label">비밀번호</label><br />

            <input
              type="password"
              class="form-control"
              v-model="loginUser.user_pwd"
              @keyup.enter="login"
              placeholder="비밀번호..."
            />
          </div>

          <div class="col-auto text-center mt-3">
            <button type="button" class="btn btn-primary mb-3" @click="login">로그인</button>
            <!-- <button type="button" class="btn btn-outline-success ms-1 mb-3" @click="join">
              회원가입
            </button> -->
          </div>
        </form>
      </div>
      <button class="btn-find float-end" @click="moveUserPasswordFind">비밀번호 찾기</button>
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
  box-shadow: inset 0 0 1px 1px hlsa(0, 0%, 100%, 0.9), 0 20px 27px 0 rgba(0, 0, 0, 0.05) !important;
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
  font-family: "Dongle", sans-serif;
  font-size: 24px;
  border: none;
  background-color: whitesmoke;
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
