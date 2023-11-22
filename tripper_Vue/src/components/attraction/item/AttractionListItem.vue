<script setup>
import { ref } from "vue";
import AttractionDetailModal from "@/components/attraction/item/AttractionDetailModal.vue";

defineProps({ attraction: Object });

// const modalShow = ref(false);

// const handleImageClick = () => {
//     console.log("handleImageClick ->");
//     modalShow.value = !modalShow.value;
//     // modalShow.value = true;
//     console.log(modalShow.value);
// };

const emit = defineEmits(["openModal"]);

const handleImageClick = function () {
    console.log("handleImageClick() !!");
    emit("openModal");
};

// <th @click.stop="handleImageClick()">
</script>

<template>
    <tr class="text-center">
        <th @click.stop="handleImageClick">
            <img class="attrImg" v-if="attraction.first_image.length == 0" src="https://www.verdantis.com/wp-content/uploads/2016/04/noimg.jpg" />
            <img class="attrImg" v-else :src="attraction.first_image" />
        </th>

        <th>{{ attraction.title }}</th>
        <td>{{ attraction.addr1 }}</td>
    </tr>
</template>

<style scoped>
.attrImg {
    width: 200px;
    height: 200px;
}
</style>
