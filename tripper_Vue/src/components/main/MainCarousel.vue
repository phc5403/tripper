<script setup>
import { ref, onMounted } from "vue";
import { listStore } from "../../api/attraction";

const shops = ref({});

onMounted(() => {
  getStorePopular();
});

const getStorePopular = () => {
  listStore(
    ({ data }) => {
      console.log(data);
      shops.value = data;
      console.log("넘어온 음식점 데이터", shops);
      console.log(shops.value[0].title);
    },
    (error) => {
      console.log(error);
    }
  );
};
</script>

<template>
  <div class="row justify-content-center">
    <div class="col-lg-12 mt-5 mb-5">
      <p class="fw-bold" style="font-size: 22px">조회수가 가장 높은 관광지 TOP 5</p>
      <hr width="100%" color="black" size="8" />
      <p></p>
    </div>
    <div
      id="carouselExampleCaptions"
      class="carousel slide"
      data-bs-ride="carousel"
      style="height: 20%"
    >
      <div class="carousel-indicators">
        <button
          v-for="(shop, index) in shops"
          :key="index"
          type="button"
          :data-bs-target="'#carouselExampleCaptions'"
          :data-bs-slide-to="index"
          :class="{ active: index === 0 }"
          :aria-current="index === 0"
          :aria-label="'Slide ' + (index + 1)"
        ></button>
      </div>
      <div class="carousel-inner">
        <div
          v-for="(shop, index) in shops"
          :key="index"
          class="carousel-item"
          data-bs-interval="10000"
          :class="{ active: index === 0 }"
        >
          <img :src="shop.first_image" class="d-block w-100" alt="가게 이미지" />
          <div class="carousel-caption d-none d-md-block">
            <h5>{{ shop.title }}</h5>
            <p>{{ shop.addr1 }}</p>
          </div>
        </div>
      </div>
      <button
        class="carousel-control-prev"
        type="button"
        data-bs-target="#carouselExampleCaptions"
        data-bs-slide="prev"
      >
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
      </button>
      <button
        class="carousel-control-next"
        type="button"
        data-bs-target="#carouselExampleCaptions"
        data-bs-slide="next"
      >
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
      </button>
    </div>
  </div>
</template>

<style scoped>
.carousel-item {
  object-fit: cover;
  max-height: 650px;
}
img {
  display: table-cell;
  max-width: 100%;
  max-height: 100%;
}
</style>
