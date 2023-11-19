import { ref, computed } from "vue";
import { defineStore } from "pinia";

// export default defineStore('counter', () => {
//   const count = ref(0)
//   const doubleCount = computed(() => count.value * 2)
//   function increment() {
//     count.value++
//   }

//   return { count, doubleCount, increment }
// })

// content_type_id별 실제 항목 종류
export const useContentStore = defineStore("contentId", () => {
    const contents = ref([
        { id: 12, type: "관광지" },
        { id: 14, type: "문화시설" },
        { id: 15, type: "축제공연행사" },
        { id: 25, type: "여행" },
        { id: 28, type: "레포츠" },
        { id: 32, type: "숙박" },
        { id: 38, type: "쇼핑" },
        { id: 39, type: "음식점" },
    ]);

    // content_type_id를 입력 받아, 해당하는 종류를 반환하는 함수
    const findType = function (contentId) {
        const findType = contents.value.find((element) => element.id == contentId);
        return findType.type;
    };

    return { contents, findType };
});

// 시도_코드별 실제 지역명
export const useSidoStore = defineStore("sidoCode", () => {
    const sidos = ref([
        { code: 1, name: "서울" },
        { code: 2, name: "인천" },
        { code: 3, name: "대전" },
        { code: 4, name: "대구" },
        { code: 5, name: "광주" },
        { code: 6, name: "부산" },
        { code: 7, name: "울산" },
        { code: 8, name: "세종특별자치시" },
        { code: 31, name: "경기도" },
        { code: 32, name: "강원도" },
        { code: 33, name: "충청북도" },
        { code: 34, name: "충청남도" },
        { code: 35, name: "경상북도" },
        { code: 36, name: "경상남도" },
        { code: 37, name: "전라북도" },
        { code: 38, name: "전라남도" },
        { code: 39, name: "제주도" },
    ]);

    // 시도_코드를 입력 받아, 실제 지역명을 반환하는 함수
    const findName = function (sidoCode) {
        const findName = sidos.value.find((element) => element.code == sidoCode);
        return findName.name;
    };

    return { sidos, findName };
});
