<script setup>
import { ref } from "vue";
defineProps({ selectOption: Array });
const emit = defineEmits(["onKeySelect"]);

const key = ref("");

const onSelect = () => {
    console.log(key.value + "선택!!!");
    emit("onKeySelect", key.value);
};
</script>

<template>
    <link href="https://fonts.googleapis.com/css2?family=Dongle&display=swap" rel="stylesheet" />
    <select v-model="key" class="form-select form-select-sm ms-5 me-1 w-50" @change="onSelect">
        <option
            v-for="option in selectOption"
            :key="option.value"
            :value="option.value"
            :disabled="option.value === '' ? true : false"
        >
            {{ option.text }}
        </option>
    </select>
</template>

<style scoped>
select, option{
    font-family: "Dongle", sans-serif;
    font-size: 24px;
}
</style>
