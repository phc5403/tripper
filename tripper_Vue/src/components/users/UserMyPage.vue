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
</script>

<template>
    <form @submit.prevent="onSubmit">
        <p class="fw-bold">내 정보</p>
        <div class="my-page border bg-white bg-gradient">
            <div class="item1">
                <img src="@/assets/cat.jpg" />
                <p>{{ user.user_name }}님 반갑습니다 :)</p>
                <button class="btn btn-outline-primary btn-sm">이미지 업로드</button>
            </div>
            <div class="item2">
                <div class="user-info border">
                    <div>
                        <label for="username" class="form-label">이름</label>
                        <input type="text" class="form-control" v-model="user.user_name" disabled />
                        <label for="userid" class="form-label">아이디</label>
                        <input type="text" class="form-control" v-model="user.user_id" disabled />
                        <label for="userpwd" class="form-label">비밀번호</label>
                        <input type="password" class="form-control" v-model="user.user_pwd" />
                        <label for="useremail" class="form-label">이메일</label>
                        <div class="input-group">
                            <input type="text" class="form-control" v-model="user.user_email_id" />
                            <span class="input-group-text">@</span>
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
</style>
