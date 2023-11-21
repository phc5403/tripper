<script setup>
import TheHeadingNavbar from "../components/layout/TheHeadingNavbar.vue";
</script>

<template>
  <div class="main">
    <TheHeadingNavbar></TheHeadingNavbar>
    <div>
      <router-view></router-view>
    </div>
  </div>
</template>

<style scoped>
.main {
  /* padding: 0; */
  /* position: relative;  */
  /* overflow: hidden; */
  /* display: flex; */
  flex-direction: column;
  /* align-items: center; */
  /* text-align: center; */
  height: auto;
  background-size: cover;
  background-position: 50%;
  background-image: url("@/assets/bg2.jpg");
}
</style>
