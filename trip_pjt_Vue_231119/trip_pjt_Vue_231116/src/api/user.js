import { localAxios } from "@/util/http-commons";

const local = localAxios();

function joinUser(user, success, fail) {
  console.log("joinUser", user);
  local.post(`/user/join`, JSON.stringify(user)).then(success).catch(fail);
}

function userIdCheck(userid, success, fail) {
  console.log("userid", userid);
  local.get(`/user/${userid}`).then(success).catch(fail);
}

async function userConfirm(param, success, fail) {
  console.log("param", param);
  await local.post(`/user/login`, param).then(success).catch(fail);
  console.log("userConfirm ok");
}

async function findById(userid, success, fail) {
  local.defaults.headers["Authorization"] =
    sessionStorage.getItem("accessToken");
  await local.get(`/user/info/${userid}`).then(success).catch(fail);
}

async function tokenRegeneration(user, success, fail) {
  local.defaults.headers["refreshToken"] =
    sessionStorage.getItem("refreshToken"); //axios header에 refresh-token 셋팅
  await local.post(`/user/refresh`, user).then(success).catch(fail);
}

async function logout(userid, success, fail) {
  await local.get(`/user/logout/${userid}`).then(success).catch(fail);
}

export {
  joinUser,
  userIdCheck,
  userConfirm,
  findById,
  tokenRegeneration,
  logout,
};
