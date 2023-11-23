<script setup>
import { ref, onMounted } from "vue";
import { listPopular } from "../../api/attraction";

const populars = ref([]);

const descript = ref("");
const title = ref("");
const selected = ref(null);

onMounted(() => {
  getAttractionPopular();
});

const getAttractionPopular = () => {
  listPopular(
    ({ data }) => {
      console.log(data);
      populars.value = data;
      // console.log("넘어온 데이터", populars);
      // console.log(populars.value[0].overview);
    },
    (error) => {
      console.log(error);
    }
  );
};
const displayDescription = (index) => {
  console.log(index);
  descript.value = populars.value[index].overview;
  title.value = populars.value[index].title;
  // console.log(populars[index].overview);
  console.log(descript);
  selected.value = selected.value === index ? null : index;
};
</script>
<template>
  <link href="https://fonts.googleapis.com/css2?family=Dongle&display=swap" rel="stylesheet" />
  <div class="row justify-content-center">
    <div class="col-lg-12 mt-5">
      <p class="fw-bold" style="font-size: 40px">현재 가장 인기 있는 관광지 TOP 3</p>
      <hr width="100%" color="black" size="8" />
      <p></p>
    </div>

    <div class="col-lg-12 mx-auto py-3">
      <div class="row">
        <div class="col-md-4 position-relative" v-for="(popular, index) in populars" :key="index">
          <div class="p-3 text-center">
            <img
              :src="popular.first_image"
              style="width: 300px; height: 200px; object-fit: cover"
              @click="displayDescription(index)"
            />
            <p class="mt-3 fw-bold" style="font-size: 36px">{{ popular.title }}</p>
            <p class="mt-3" style="font-size: 28px">{{ popular.addr1 }}</p>
          </div>
        </div>
        <hr class="vertical dark mt-3" />
        <div v-show="selected !== null">
          <div>
            <p class="mt-3 fw-bold" style="font-size: 32px">&#128747; {{ title }}</p>
          </div>
          <div class="border p-2">
            <p style="font-size: 24px;">
              <em>
                {{ descript }}
              </em>
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<style scoped>
p {
  font-family: "Dongle", sans-serif;
}
</style>
