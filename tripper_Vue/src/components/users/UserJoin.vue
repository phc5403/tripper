<script setup>
import { ref, watch } from "vue";
import { useRouter } from "vue-router";
import { joinUser, userIdCheck } from "@/api/user";

const router = useRouter();

const user = ref({
    user_id: "",
    user_name: "",
    user_pwd: "",
    user_email_id: "",
    user_email_domain: "",
});
const pwdCheck = ref("");
const idErrMsg = ref("");
const pwdErrMsg = ref("");
const pwdCheckErrMsg = ref("");
const emailErrMsg = ref("");

const isId = ref(false);
const isPwd = ref(false);
const isEmail = ref(false);

watch(
    //아이디 체크
    () => user.value.user_id,
    (value) => {
        let len = value.length;
        if (len < 4 || len > 20) {
            idErrMsg.value = "아이디는 4 ~ 20자리이여야 합니다.";
            isId.value = false;
        } else {
            idErrMsg.value = "";
            idCheck();
        }
    }
    // { imediate: true }
);

watch(
    //비밀번호 체크
    () => user.value.user_pwd,
    (value) => {
        let len = value.length;
        if (len < 4 || len > 16) {
            pwdErrMsg.value = "비밀번호는 4 ~ 16자리여야 합니다.";
            isPwd.value = false;
        } else {
            pwdErrMsg.value = "";
            isPwd.value = true;
        }
    }
    // { immediate: true }
);

watch(
    //비밀번호 중복 체크
    () => pwdCheck.value,
    (value) => {
        if (!(value == user.value.user_pwd)) {
            pwdCheckErrMsg.value = "비밀번호가 일치하지 않습니다.";
            isPwd.value = false;
        } else {
            pwdCheckErrMsg.value = "";
            isPwd.value = true;
        }
    }
    // { immediate: true }
);

watch(
    //이메일 체크
    () => user.value.user_email_id,

    (value) => {
        let len = value.length;
        if (len < 4 || len > 20) {
            emailErrMsg.value = "이메일을 확인해주세요!!!";
            isEmail.value = false;
        } else {
            emailErrMsg.value = "";
            isEmail.value = true;
        }
    }
    // { immediate: true }
);

function idCheck() {
    console.log("아이디 중복 체크", user.value.user_id);
    userIdCheck(
        user.value.user_id,
        (response) => {
            console.log(response.data);
            if (response.data == 1) {
                idErrMsg.value = "이미 존재하는 아이디입니다.";
                isId.value = false;
            } else {
                idErrMsg.value = "";
                isId.value = true;
            }
        },
        (error) => {
            console.error(error);
        }
    );
}

function onSubmit() {
    if (!user.value.user_name) {
        alert("이름을 입력하세요!!");
    } else if (idErrMsg.value || !isId.value) {
        alert("아이디를 다시 입력하세요!!");
    } else if (pwdErrMsg.value || !isPwd.value) {
        alert("비밀번호를 다시 입력하세요!!");
    } else if (pwdCheckErrMsg.value || !isPwd.value || !pwdCheck.value) {
        alert("비밀번호가 일치하지 않습니다!!");
    } else if (emailErrMsg.value || !isEmail.value) {
        alert("이메일을 다시 입력하세요!!");
    } else {
        join();
    }
}

function join() {
    console.log("회원가입 고고", user.value);
    joinUser(
        user.value,
        (response) => {
            console.log(response.status);
            let msg = "회원가입 중 문제가 발생하였습니다.";
            if (response.status == 200) msg = "회원가입 완료되었습니다.";
            alert(msg);
            router.push({ name: "user-login" });
        },
        (error) => {
            console.error(error);
        }
    );
}
</script>

<template>
    <div class="container">
        <div class="main border">
            <div>
                <div>
                    <h4>
                        <p class="bi bi-person-plus-fill">회원가입</p>
                    </h4>
                    <br />
                </div>
                <form @submit.prevent="onSubmit">
                    <div class="mb-1">
                        <label for="input-name" class="form-label">이름</label><br />
                        <input type="text" class="form-control" v-model="user.user_name" placeholder="이름..." />
                    </div>
                    <div class="mb-1">
                        <label for="input-id" class="form-label">아이디</label>
                        <p v-show="idErrMsg" class="input-error float-end">
                            {{ idErrMsg }}
                        </p>
                        <br />
                        <input type="text" class="form-control" v-model="user.user_id" placeholder="아이디 입력" />
                    </div>
                    <div class="mb-1">
                        <label for="input-password" class="form-label">비밀번호</label>
                        <p v-show="pwdErrMsg" class="input-error float-end">
                            {{ pwdErrMsg }}
                        </p>
                        <br />
                        <input type="password" class="form-control" v-model="user.user_pwd" placeholder="비밀번호 입력" />
                    </div>
                    <div class="mb-2">
                        <label for="password-check" class="form-label">비밀번호 확인</label>
                        <p v-show="pwdCheckErrMsg" class="input-error float-end">
                            {{ pwdCheckErrMsg }}
                        </p>
                        <br />
                        <input type="password" class="form-control" v-model="pwdCheck" placeholder="비밀번호 재입력" />
                    </div>

                    <div class="mb-3">
                        <label for="input-email" class="form-label">이메일</label>
                        <p v-show="emailErrMsg" class="input-error float-end">
                            {{ emailErrMsg }}
                        </p>
                        <br />
                        <div class="input-group">
                            <input type="text" class="form-control" v-model="user.user_email_id" placeholder="이메일" />
                            <span class="input-group-text">@</span>
                            <select class="form-select" v-model="user.user_email_domain">
                                <option value="ssafy.com">ssafy.com</option>
                                <option value="google.com">google.com</option>
                                <option value="naver.com">naver.com</option>
                                <option value="kakao.com">kakao.com</option>
                            </select>
                        </div>
                    </div>

                    <div class="btns">
                        <br />
                        <button type="submit" class="btn btn-outline-primary">회원가입</button>

                        <button type="reset" class="btn btn-outline-success">초기화</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</template>

<style scoped>
.container {
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 100vh;
}
.container > div {
    width: 60%;

    padding: 10px;
    border-radius: 8px;
    box-shadow: inset 0 0 1px 1px hlsa(0, 0%, 100%, 0.9), 0 20px 27px 0 rgba(0, 0, 0, 0.05) !important;
}
.btns {
    display: flex;
    justify-content: center;
    margin: auto;
}
.main {
    align-items: center;
    background-color: whitesmoke;
}
.bi {
    color: rgb(206, 163, 255);
    text-align: center;
}
.input-error {
    color: red;
}
</style>
