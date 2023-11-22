<script setup>
import NoticeFormItem from "./item/NoticeFormItem.vue";
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">글쓰기</mark>
        </h2>
      </div>
      <div class="col-lg-10 text-start">
        <NoticeFormItem type="regist" />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
