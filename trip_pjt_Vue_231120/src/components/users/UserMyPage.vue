<!-- eslint-disable no-const-assign -->
<script setup>
import { ref } from "vue";

const userInfo = {
  user_id: "ssafy",
  user_name: "김싸피",
  user_pwd: "1234",
  user_email_id: "ssafy",
  user_email_domain: "ssafy.com",
};

const editedUser = ref({});
const editing = ref(false);

const toggleEditing = () => {
  editedUser.value = { ...userInfo };
  editing.value = true;
};

const saveChanges = () => {
  userInfo = { ...editedUser.value };
  editing.value = false;
};

const cancelEditing = () => {
  editing.value = false;
};
</script>

<template>
  <p class="fw-bold">내 정보</p>
  <div class="my-page border bg-white bg-gradient">
    <div class="item1">
      <img src="@/assets/cat.jpg" />
      <p>{{ userInfo.user_name }}님 반갑습니다 :)</p>
      <button class="btn btn-outline-primary btn-sm">이미지 업로드</button>
    </div>
    <div class="item2">
      <div class="user-info border">
        <div v-if="!editing">
          <label for="username" class="form-label">이름</label>
          <input type="text" class="form-control" v-model="userInfo.user_name" disabled />
          <label for="userid" class="form-label">아이디</label>
          <input type="text" class="form-control" v-model="userInfo.user_id" disabled />
          <label for="userpwd" class="form-label">비밀번호</label>
          <input type="password" class="form-control" v-model="userInfo.user_pwd" />
          <label for="useremail" class="form-label">이메일</label>
          <div class="input-group">
            <input type="text" class="form-control" v-model="userInfo.user_email_id" />
            <span class="input-group-text">@</span>
            <input type="text" class="form-control" v-model="userInfo.user_email_domain" />
          </div>
          <div>
            <button class="btn btn-outline-success me-2 btn-sm" @click="toggleEditing">수정</button>
            <button class="btn btn-outline-danger btn-sm">탈퇴</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.my-page {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  min-height: 60vh;
  border-radius: 0.75rem;
}

input[type="password"],
input[type="text"] {
  margin-bottom: 10px;
}
img {
  align-items: center;
  width: 200px;
  height: 200px;
  border-radius: 100%;
  margin-bottom: 10px;
}
.item1 {
  flex-grow: 1;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-align: center;
  margin: 10px;
}
.item2 {
  padding: 20px;
  flex-grow: 2;
}
.user-info {
  padding: 10px;
}

.input-group-text {
  height: 37.8px;
}
</style>
