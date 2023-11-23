<script setup>
import { ref } from "vue";

const props = defineProps({ attractions: Object, mShow: Boolean, cId: Number });
defineEmits(["closeBtn"]);

console.log("상혁아 제발");
console.log(props.cId);

const printInfo = ref({
    printTitle: "",
    printOverview: "",
});
// const findCid = props.cId;
const findOverview = function (findCid, index) {
    console.log("함수 진입 ");
    props.attractions.forEach((element) => {
        console.log("이거 뭔데? " + element.content_id);
        // console.log(element.title);
        if (element.content_id === findCid) {
            console.log("응 찾았어" + element.content_id);
            printInfo.value.printTitle = element.title;
            printInfo.value.printOverview = element.overview;
            console.log("이거야! " + printInfo.printTitle);
            console.log(printInfo.printOverview);
            console.log(printInfo);
            return;
        }
    });
    console.log("응 못 찾았어");
};

// findOverview(props.cId);
</script>

<template>
    <link href="https://fonts.googleapis.com/css2?family=Dongle&display=swap" rel="stylesheet" />
    <Transition name="modal">
        <div v-if="mShow" class="modal-mask">
            <div
                class="modal-container"
                style="display: flex; flex-direction: column; align-items: center"
            >
                <div class="modal-header">
                    <div class="header-title">
                        <!-- <slot name="header"><h3>default header</h3></slot> -->
                        <div name="header">
                            <p style="font-size: 28px">상세 설명</p>
                        </div>
                    </div>
                </div>

                <div class="modal-body" style="border-top: solid 1px black">
                    <div>{{ findOverview(props.cId) }}</div>
                    <slot name="body"
                        ><p class="mt-3" style="font-size: 28px">
                            {{ printInfo.printTitle }}
                        </p></slot
                    >
                    <div class="test mt-3">
                        <p style="font-size: 24px">{{ printInfo.printOverview }}</p>
                    </div>
                </div>

                <div class="modal-footer">
                    <button id="close-btn" class="modal-default-button" @click="$emit('closeBtn')">
                        닫기
                    </button>

                    <!-- <slot name="footer">
                        default footer
                        <button class="modal-default-button" @click="$emit('closeBtn')">닫기</button>
                    </slot> -->
                </div>
            </div>
        </div>
    </Transition>
</template>

<style>
#close-btn {
    background-color: red;
    color: white;
    font-size: 16px;
    font-weight: bold;
    border-radius: 15px;
    border: none;
    margin: 3px;
}

#close-btn:hover {
    background-color: #a71d1d;
}

/* .modal-mask {
    position: fixed;
    z-index: 10000;
    background-color: white;
    color: black;
    width: 300px;
    height: 300px;
    top: 50%;
    left: 50%;
    display: block;
    transform: translate(-50%, -50%);
}

.modal-header {
    text-align: center;
}
.modal-body {
    text-align: center;
} */

/* */

.test {
    overflow: auto;
    max-height: 400px;
}

.modal-mask {
    position: fixed;
    z-index: 9998;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    display: flex;
    transition: opacity 0.3s ease;
    align-items: center;
    justify-content: center;
}

.modal-container {
    width: 500px;
    /* margin: auto; */
    padding: 20px 30px;
    background-color: #fff;
    border-radius: 2px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
    transition: all 0.3s ease;
}

.modal-header h3 {
    color: #42b983;
    margin-top: 0;
    text-align: center;
}
/* 
.header-title {
    display: flex;
    text-align: center;
} */

.modal-body {
    margin: 20px 0;
    text-align: center;
}

.modal-default-button {
    float: right;
}

/*
 * The following styles are auto-applied to elements with
 * transition="modal" when their visibility is toggled
 * by Vue.js.
 *
 * You can easily play with the modal transition by editing
 * these styles.
 */

.modal-enter-from {
    opacity: 0;
}

.modal-leave-to {
    opacity: 0;
}

.modal-enter-from .modal-container,
.modal-leave-to .modal-container {
    -webkit-transform: scale(1.1);
    transform: scale(1.1);
}
p,
button {
    font-family: "Dongle", sans-serif;
}
</style>
