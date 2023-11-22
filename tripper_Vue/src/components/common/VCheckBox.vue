<script setup>
import { ref, watch } from "vue";
import { useContentStore } from "@/stores/attractionInfo";
const store = useContentStore();
const contents = store.contents;
const emit = defineEmits(["changeContent"]);

const choiceContent = ref([]);
const choiceAll = ref(false);
const checkAll = () => {
    if (choiceAll.value) {
        console.log("true");
        choiceContent.value = contents.map((content) => content.id);
    } else {
        console.log("false");
        choiceContent.value = [];
    }
};
const props = defineProps({ clickSearch: Boolean });

// console.log(props.clickSearch);

watch(
    () => props.clickSearch,
    (newContent, oldContent) => {
        console.log("watch -> ", props.clickSearch);
        if (props.clickSearch) {
            emit("changeContent", newContent, oldContent);
        }
    }
);
</script>

<template>
    <!-- Content Choice -->
    <div class="form-check form-check-inline" v-for="content in contents" :key="content.id">
        <input class="form-check-input" type="checkbox" :id="'check_' + content.id" :value="content.id" v-model="choiceContent" />
        <label class="form-check-label" :for="'check_' + content.id">{{ content.type }}</label>
    </div>

    <div class="form-check form-check-inline">
        <input class="form-check-input" type="checkbox" id="check_all" value="choiceAll" v-model="choiceAll" @change="checkAll" />
        <label class="form-check-label" for="check_all">전체</label>
    </div>
    <!-- <button class="btn btn-dark" type="button" @click="getlistAttraction">검색</button> -->

    <div>?? : {{ clickSearch }}</div>
    <!-- <div>{{ choiceContent }}</div> -->
    <!-- <div>{{ choiceAll }}</div> -->
</template>

<style scoped></style>
