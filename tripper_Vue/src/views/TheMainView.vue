<script setup>
import TheHeadingNavbar from "@/components/layout/TheHeadingNavbar.vue";
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
                <div class="popular-attraction">
                    <h2>올해 최고의 인기 관광지</h2>
                    <div class="col-lg-12 mx-auto py-3">
                        <div class="row">
                            <div class="col-md-4 position-relative">
                                <div class="p-3 text-center">
                                    <img src="../assets/gwanganri.jpg" style="width: 300px; height: 200px; object-fit: cover" />
                                    <h3>광안리 해수욕장</h3>
                                    <h5 class="mt-3">경상남도 부산광역시</h5>
                                </div>
                                <hr class="vertical dark" />
                            </div>
                            <div class="col-md-4 position-relative">
                                <div class="p-3 text-center">
                                    <img src="../assets/mola.jpg" style="width: 300px; height: 200px; object-fit: cover" />
                                    <h3>여수</h3>
                                    <h5 class="mt-3">전라남도 여수시</h5>
                                </div>
                                <hr class="vertical dark" />
                            </div>
                            <div class="col-md-4 position-relative">
                                <div class="p-3 text-center">
                                    <img src="../assets/gangneung.jpg" style="width: 300px; height: 200px; object-fit: cover" />
                                    <h3>강릉</h3>
                                    <h5 class="mt-3">강원도 강릉시 전상혁</h5>
                                </div>
                                <hr class="vertical dark" />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
</template>

<style scoped>
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
    box-shadow: inset 0 0 1px 1px hlsa(0, 0%, 100%, 0.9), 0 20px 27px 0 rgba(0, 0, 0, 0.05) !important;
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

img {
    margin-bottom: 10px;
}
.popular-attraction {
    margin-top: 30px;
}
</style>
