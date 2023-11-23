<script setup>
import BoardFormItem from "./item/BoardFormItem.vue";
</script>

<template>
  <link href="https://fonts.googleapis.com/css2?family=Dongle&display=swap" rel="stylesheet" />
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <p class="fw-bold" style="font-size: 40px">글쓰기</p>
        <hr width="100%" color="black" size="4" />
      </div>
      <div class="col-lg-10 text-start">
        <BoardFormItem type="regist" />
      </div>
    </div>
  </div>
</template>

<style scoped>
p {
  font-family: "Dongle", sans-serif;
}
</style>
