<script setup>
import TheHeadingNavbar from "../components/layout/TheHeadingNavbar.vue";
</script>

<template>
  <header>
    <div class="page-header">
      <span class="mask bg-gradient-primary opacity-4"></span>
      <TheHeadingNavbar></TheHeadingNavbar>
      <div class="main-content">
        <h1 class="text-white pt-3 mt-n5">안녕하세요.</h1>
        <p class="lead text-white mt-3">
          SSAFY 10th <br />
          임시 화면입니다.
        </p>
      </div>
    </div>
  </header>
  <div class="card card-body blur shadow-blur mx-3 mx-3 mx-md-4 mt-n6">
    <section class="pt-3 pb-4" id="">
      <div class="container">
        <!-- router view 설정 -->
        <router-view></router-view>
      </div>
    </section>
  </div>
</template>

<style>
.page-header {
  padding: 0;
  position: relative;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  height: 600px;
  background-size: cover;
  background-position: 50%;
  background-image: url("@/assets/bg1.png");
}
.main-content {
  margin-top: 80px; /* 원하는 간격 설정 */
  z-index: 1; /* 다른 요소 위로 올리기 위해 z-index 설정 */
}

.text-white {
  z-index: 1; /* 다른 요소 위로 올리기 위해 z-index 설정 */
}
.blur {
  backdrop-filter: saturate(200%) blur(30px);
  background-color: hsla(0, 0%, 100%, 0.8) !important;
}
.shadow-blur {
  box-shadow: inset 0 0 1px 1px hlsa(0, 0%, 100%, 0.9),
    0 20px 27px 0 rgba(0, 0, 0, 0.05) !important;
}
.mx-md-4 {
  margin-right: 1.5rem !important;
  margin-left: 1.5rem !important;
}

.mt-n6 {
  margin-top: -4rem !important;
}
.card {
  --bs-card-border-radius: 0.75rem;
  display: flex;
  flex-direction: column;
}
</style>
