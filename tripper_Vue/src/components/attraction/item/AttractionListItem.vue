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
</style>
