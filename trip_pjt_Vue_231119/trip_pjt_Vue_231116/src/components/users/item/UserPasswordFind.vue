<script setup>
function recoverPassword() {
  // Add logic to handle password recovery
  console.log("Recovering password...");
  console.log("Email:", this.email);
  console.log("User ID:", this.userId);
  console.log("Save User ID:", this.saveUserId);
  // Implement password recovery functionality here
}
</script>

<template>
  <div class="container">
    <div class="main border">
      <div>
        <div>
          <h4>
            <p class="bi bi-person-plus-fill">비밀번호 찾기</p>
          </h4>
          <br />
        </div>

        <form>
          <div class="mb-1">
            <label for="userid" class="form-label">아이디</label><br />
            <input
              type="text"
              class="form-control"
              placeholder="아이디를 입력하세요"
            />
          </div>
          <div class="mb-1">
            <label for="username" class="form-label">이름</label><br />

            <input
              type="text"
              class="form-control"
              placeholder="이름을 입력하세요"
            />
          </div>

          <div class="col-auto text-center mt-3">
            <button type="button" class="btn btn-primary mb-3" @click="login">
              찾기
            </button>
            <!-- <button type="button" class="btn btn-outline-success ms-1 mb-3" @click="join">
              회원가입
            </button> -->
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
</style>
