import { localAxios } from "@/util/http-commons";

const local = localAxios();

const url = "/trip";

// function listAttraction(param, success, fail) {
//     console.log(param);
//     local.get(`${url}/list`, { params: param }).then(success).catch(fail);
// }

function listAttraction(param, choiceContent, success, fail) {
  console.log("attraction.js =>");
  // console.log(param.sido_code + "," + param.gugun_code);
  console.log(param);
  console.log(typeof param);
  console.log(choiceContent);
  console.log(typeof choiceContent);
  console.log("-------------");

  const encodedParams = Object.entries(param).reduce((acc, [key, value]) => {
    acc[key] = encodeURIComponent(value);
    return acc;
  }, {});

  local
    .get(`${url}/list`, {
      params: { ...encodedParams, choiceContent: encodeURIComponent(choiceContent) },
    })
    .then(success)
    .catch(fail);
}

function listPopular(success, fail) {
  console.log("인기 검색할게요");
  local.get(`${url}/popular`).then(success).catch(fail);
}

function listStore(success, fail) {
  console.log("인기 음식점 검색할게요");
  local.get(`${url}/store`).then(success).catch(fail);
}

export { listAttraction, listPopular, listStore };

// { params: { param: param, choiceContent: encodeURIComponent(choiceContent) } }
