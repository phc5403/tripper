<!-- eslint-disable no-const-assign -->
<script setup>
import { ref, watch } from "vue";
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";
import { getModifyUser, modifyUser, deleteUser } from "@/api/user";
import { useMemberStore } from "@/stores/member";
import { useMenuStore } from "@/stores/menu";

const router = useRouter();

const menuStore = useMenuStore();
const memberStore = useMemberStore();

const { changeMenuState } = menuStore;
const { userInfo } = storeToRefs(memberStore);
const { userLogout } = memberStore;

const type = ref("");

// const { userid } = route.params;

const user = ref({
    user_id: "",
    user_name: "",
    user_pwd: "",
    user_email_id: "",
    user_email_domain: "",
    user_img: "",
});
// console.log("가져온 유저 정보", userInfo);
console.log("가져온 유저정보의 아이디는?", userInfo.value.user_id);

getModifyUser(
    userInfo.value.user_id,
    ({ data }) => {
        console.log("가져온 데이터=", data);
        //수정할 대상의 데이터
        // console.log(data.user_name, "???");
        (user.value.user_id = userInfo.value.user_id), (user.value.user_name = data.user_name);
        user.value.user_pwd = data.user_pwd;
        user.value.user_email_id = data.user_email_id;
        user.value.user_email_domain = data.user_email_domain;
        user.value.user_img = data.user_img;
    },
    (error) => {
        console.log(error);
    }
);

const pwdErrMsg = ref("");
const emailErrMsg = ref("");
const emailDomainErrMsg = ref("");

watch(
    //비밀번호 체크
    () => user.value.user_pwd,
    (value) => {
        let len = value.length;
        if (len < 4 || len > 16) {
            pwdErrMsg.value = "비밀번호는 4~16자리여야 합니다.";
        } else {
            pwdErrMsg.value = "";
        }
    }
);

watch(
    //이메일 아이디 체크
    () => user.value.user_email_id,
    (value) => {
        let len = value.length;
        if (len == 0 || len > 20) {
            emailErrMsg.value = "이메일을 다시 입력하세요.";
        } else {
            emailErrMsg.value = "";
        }
    }
);

watch(
    //이메일 도메인 체크
    () => user.value.user_email_domain,
    (value) => {
        let len = value.length;
        if (len == 0 || len > 20) {
            emailDomainErrMsg.value = "이메일 주소를 다시 입력하세요.";
        } else {
            emailDomainErrMsg.value = "";
        }
    }
);

function onSubmit() {
    if (pwdErrMsg.value) {
        alert(pwdErrMsg.value);
    } else if (emailErrMsg.value) {
        alert(emailErrMsg.value);
    } else if (emailDomainErrMsg.value) {
        alert(emailDomainErrMsg.value);
    } else {
        // updateUser();
        type.value === "update" ? updateUser() : onDeleteUser();
    }
}
function updateUser() {
    console.log("수정할 유저의 아이디는 ? : " + user.value.user_id, user.value);
    modifyUser(
        user.value,
        ({ data }) => {
            console.log(data);
            alert("수정되었습니다!");
        },
        (error) => {
            console.log(error);
        }
    );
}

function onDeleteUser() {
    console.log(user.value.user_id + "의 유저 정보를 삭제합니다.");
    if (window.confirm("삭제하시겠습니까?")) {
        deleteUser(
            user.value.user_id,
            ({ data }) => {
                console.log("삭제요청");
                console.log(data);
                // console.log(memberStore.userInfo, "삭제할 것");
                // console.log(userInfo.value, "?");
                // memberStore.userInfo = null;
                userLogout();
                changeMenuState();
                router.push("/");
            },
            (error) => {
                console.log(error);
            }
        );
    } else {
        return false;
    }
}

function resizeImg(file) {
    const MAX_WIDTH = 800; // 이미지의 최대 너비
    const MAX_HEIGHT = 600; // 이미지의 최대 높이

    // FileReader 객체 생성
    const reader = new FileReader();
    // 파일 로드가 완료되면 실행되는 이벤트 핸들러
    reader.onload = function (event) {
        // 이미지 객체 생성
        const img = new Image();
        img.src = event.target.result;
        // 이미지 로드가 완료되면 실행되는 이벤트 핸들러
        img.onload = function () {
            let width = img.width; // 이미지의 너비
            let height = img.height; // 이미지의 높이

            // 이미지의 가로 세로 비율에 따라 리사이징 수행
            if (width > height) {
                // 이미지의 가로가 세로보다 큰 경우
                if (width > MAX_WIDTH) {
                    // 가로가 최대 너비보다 크면 비율에 맞게 조정
                    height *= MAX_WIDTH / width;
                    width = MAX_WIDTH;
                }
            } else {
                // 이미지의 세로가 가로보다 크거나 같은 경우
                if (height > MAX_HEIGHT) {
                    // 세로가 최대 높이보다 크면 비율에 맞게 조정
                    width *= MAX_HEIGHT / height;
                    height = MAX_HEIGHT;
                }
            }

            // 캔버스 생성하여 이미지 리사이징 및 압축
            const canvas = document.createElement("canvas");
            canvas.width = width;
            canvas.height = height;

            const ctx = canvas.getContext("2d");
            ctx.drawImage(img, 0, 0, width, height);

            // 리사이즈 및 압축된 이미지 데이터를 base64 형태로 가져옴
            const resizedImage = canvas.toDataURL("image/jpeg", 0.7); // 이미지 품질 조절

            // 'resizedImage'에 리사이즈/압축된 이미지 데이터가 포함됨
            // 이를 사용자 객체에 할당하거나 서버에 업로드할 수 있음
            user.value.user_img = resizedImage;
            console.log(user.value.user_img, "으악");
        };
    };
    // 파일을 Data URL로 변환하여 읽음
    reader.readAsDataURL(file);
}

function uploadImg(event) {
    const file = event.target.files[0]; // 선택된 파일 가져오기

    resizeImg(file);
    // const reader = new FileReader();
    // reader.onload = (e) => {
    //   user.value.user_img = e.target.result; // 이미지를 사용자 정보에 할당
    //   // console.log(user.value.user_img, "입니다아ㅏㅏㅏㅏ");
    // };
    // reader.readAsDataURL(file); // 파일을 읽어서 데이터 URL로 변환
}
</script>

<template>
    <link href="https://fonts.googleapis.com/css2?family=Dongle&display=swap" rel="stylesheet" />
    <form @submit.prevent="onSubmit">
        <p class="fw-bold" style="font-size: 40px">&#128161; 내 정보</p>
        <div class="my-page border bg-white bg-gradient">
            <div class="item1">
                <img v-if="user.user_img" :src="user.user_img" />
                <p style="font-size: 28px;">{{ user.user_name }}님 반갑습니다 👋</p>
                <!-- <button class="btn btn-outline-primary btn-sm">이미지 업로드</button> -->
                <label class="file-input-label">
                    <span style="font-size: 20px;">이미지 업로드</span>
                    <input type="file" @change="uploadImg" accept="image/*" class="file-input" />
                </label>
            </div>
            <div class="item2">
                <div class="user-info border">
                    <div>
                        <label for="username" class="form-label" >이름</label>
                        <input type="text" class="form-control" v-model="user.user_name" disabled />
                        <label for="userid" class="form-label">아이디</label>
                        <input type="text" class="form-control" v-model="user.user_id" disabled />
                        <label for="userpwd" class="form-label">비밀번호</label>
                        <input type="password" class="form-control" v-model="user.user_pwd" />
                        <label for="useremail" class="form-label">이메일</label>
                        <div class="input-group">
                            <input type="text" class="form-control" v-model="user.user_email_id" />
                            <span class="input-group-text" style="height: 52.33px;">@</span>
                            <input type="text" class="form-control" v-model="user.user_email_domain" />
                        </div>
                        <div>
                            <button class="btn btn-outline-success me-2 btn-sm" @click="type = 'update'">수정</button>
                            <button class="btn btn-outline-danger btn-sm" @click="type = ''">탈퇴</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</template>

<style scoped>
.my-page {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: center;
    min-height: 60vh;
    border-radius: 0.75rem;
}

input[type="password"],
input[type="text"] {
    margin-bottom: 10px;
}
img {
    align-items: center;
    width: 200px;
    height: 200px;
    border-radius: 100%;
    margin-bottom: 10px;
}
.item1 {
    flex-grow: 1;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    text-align: center;
    margin: 10px;
}
.item2 {
    padding: 20px;
    flex-grow: 2;
}
.user-info {
    padding: 10px;
}

.input-group-text {
    height: 37.8px;
}
.file-input-label {
    position: relative;
    display: inline-block;
}

.file-input {
    position: absolute;
    top: 0;
    left: 0;
    opacity: 0;
    width: 100%;
    height: 100%;
    cursor: pointer;
}

/* 파일 선택 버튼 디자인 */
.file-input-label span {
    /* 파일 선택 버튼 디자인을 적용하는 스타일 */
    display: inline-block;
    padding: 6px 12px;
    border: 1px solid #ccc;
    border-radius: 4px;
    background-color: #f5f5f5;
    color: #333;
    cursor: pointer;
}
p, span{
    font-family: "Dongle", sans-serif;   
}
label{
    font-family: "Dongle", sans-serif;
    font-size: 30px;
}
input{
    font-family: "Dongle", sans-serif;
    font-size: 26px;
}
button{
    font-family: "Dongle", sans-serif;
    font-size: 22px;
}
</style>
