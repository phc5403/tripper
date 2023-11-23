import { localAxios } from "@/util/http-commons";

const local = localAxios();

function joinUser(user, success, fail) {
    console.log("joinUser", user);
    local.post(`/user/join`, JSON.stringify(user)).then(success).catch(fail);
}

function userIdCheck(userid, success, fail) {
    console.log("userid", userid);
    local.get(`/user/idcheck/${userid}`).then(success).catch(fail);
}

function getModifyUser(userid, success, fail) {
    local.get(`/user/modify/${userid}`).then(success).catch(fail);
}

function modifyUser(user, success, fail) {
    local.put(`/user`, JSON.stringify(user)).then(success).catch(fail);
}

function deleteUser(userid, success, fail) {
    local.delete(`/user/${userid}`).then(success).catch(fail);
}

function findUserPwd(param, success, fail) {
    console.log("params", param);
    console.log("params json", JSON.stringify(param));
    local.get(`/user/findpwd`, { params: param }).then(success).catch(fail);
    console.log("?ㄴㄴ");
}

// function findUserPwd(param, success, fail) {
//   console.log("params", param);
//   console.log("params json", JSON.stringify(param));
//   local.get(`/user/findpwd`, { params: param }).then(success).catch(fail);
//   console.log("?ㄴㄴ");
// }

function findUserPwd(param, success, fail) {
    console.log("params", param);
    console.log("params json", JSON.stringify(param));
    local.get(`/user/findpwd`, { params: param }).then(success).catch(fail);
    console.log("?ㄴㄴ");
}

async function userConfirm(param, success, fail) {
    console.log("param", param);
    await local.post(`/user/login`, param).then(success).catch(fail);
    console.log("userConfirm ok");
}

async function findById(userid, success, fail) {
    local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
    await local.get(`/user/info/${userid}`).then(success).catch(fail);
}

async function tokenRegeneration(user, success, fail) {
    local.defaults.headers["refreshToken"] = sessionStorage.getItem("refreshToken"); //axios header에 refresh-token 셋팅
    await local.post(`/user/refresh`, user).then(success).catch(fail);
}

async function logout(userid, success, fail) {
    await local.get(`/user/logout/${userid}`).then(success).catch(fail);
}

export { joinUser, userIdCheck, userConfirm, getModifyUser, modifyUser, deleteUser, findUserPwd, findById, tokenRegeneration, logout };
