import { localAxios } from "@/util/http-commons";

const local = localAxios();

const url = "/notice";

function listNotice(param, success, fail) {
  console.log("notice.js -> listNocie :: ", param);
  local.get(`${url}`, { params: param }).then(success).catch(fail);
}

function detailNotice(noticeno, success, fail) {
  local.get(`${url}/${noticeno}`).then(success).catch(fail);
}

function registNotice(notice, success, fail) {
  console.log("notice.js -> registNotice :: ", JSON.stringify(notice));
  local.post(`${url}`, JSON.stringify(notice)).then(success).catch(fail);
  console.log("notice.js -> 보여주나연?");
}

function getModifyNotice(noticeno, success, fail) {
  local.get(`${url}/modify/${noticeno}`).then(success).catch(fail);
}

function modifyNotice(notice, success, fail) {
  console.log("notice.js --> ", notice);
  local.put(`${url}`, JSON.stringify(notice)).then(success).catch(fail);
}

function deleteNotice(noticeno, success, fail) {
  local.delete(`${url}/${noticeno}`).then(success).catch(fail);
}

export {
  listNotice,
  detailNotice,
  registNotice,
  getModifyNotice,
  modifyNotice,
  deleteNotice,
};
