<script setup>
import { ref } from "vue";
// import AttractionDetailModal from "@/components/teleport/AttractionDetailModal.vue";

const props = defineProps({ attraction: Object });
const emit = defineEmits(["handleImageClick"]);

const emitArgs = function () {
    console.log("에밋 보내기 : " + props.attraction.content_id);
    emit("handleImageClick", props.attraction.content_id);
};

// <th @click.stop.prevent="$emit('handleImageClick')">'
</script>

<template>
    <tr class="text-center">
        <th @click.stop.prevent="emitArgs">
            <div class="image-container">
                <img
                    class="attrImg"
                    v-if="attraction.first_image.length == 0"
                    src="https://www.verdantis.com/wp-content/uploads/2016/04/noimg.jpg"
                />
                <img class="attrImg" v-else :src="attraction.first_image" />
            </div>
        </th>

        <th class="attrTitle" style="font-size: 10px">{{ attraction.title }}</th>
        <td class="attrAddress" style="font-size: 10px">{{ attraction.addr1 }}</td>
    </tr>
</template>

<style scoped>
.attrImg {
    width: 100%;
    height: 100%;

    object-fit: cover;
}

/* .modal {
    position: fixed;
    z-index: 999;
    top: 50%;
    left: 50%;
    width: 300px;
    height: 300px;
    background-color: blue;
    color: white;
    transform: translate(-50%, -50%);
} */
.image-container {
    width: 100px; /* 이미지의 너비 */
    height: 50px; /* 이미지의 높이 */
    overflow: hidden;
}
</style>
