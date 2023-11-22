import { localAxios } from "@/util/http-commons";

const local = localAxios();

const url = "/notice";

function listArticle(param, success, fail) {
  local.get(`${url}`, { params: param }).then(success).catch(fail);
}

function detailArticle(articleno, success, fail) {
  local.get(`${url}/${articleno}`).then(success).catch(fail);
}

function registArticle(article, success, fail) {
  console.log("notice.js -> registArticle :: ", JSON.stringify(article));
  local.post(`${url}`, JSON.stringify(article)).then(success).catch(fail);
  console.log("notice.js -> 보여주나연?");
}

function getModifyArticle(articleno, success, fail) {
  local.get(`${url}/modify/${articleno}`).then(success).catch(fail);
}

function modifyArticle(article, success, fail) {
  console.log("notice.js --> ", article);
  local.put(`${url}`, JSON.stringify(article)).then(success).catch(fail);
}

function deleteArticle(articleno, success, fail) {
  local.delete(`${url}/${articleno}`).then(success).catch(fail);
}

export {
  listArticle,
  detailArticle,
  registArticle,
  getModifyArticle,
  modifyArticle,
  deleteArticle,
};
