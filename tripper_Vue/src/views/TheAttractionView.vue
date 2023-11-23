<script setup>
import TheHeadingNavbar from "@/components/layout/TheHeadingNavbar.vue";
import AttractionList from "@/components/attraction/AttractionList.vue";
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
    <div class="card card-body blur shadow-blur mx-3 mx-3 mx-md-4 mt-n6" style="min-height: 100vh">
        <section class="pt-3 pb-4" id="">
            <div class="container">
                <!-- router view 설정 -->
                <!-- <router-view></router-view> -->
                <AttractionList />
            </div>
        </section>
    </div>
</template>

<style scoped></style>
