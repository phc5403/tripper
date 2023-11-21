import { localAxios } from "@/util/http-commons";

const local = localAxios();

const url = "/trip";

// function listAttraction(param, success, fail) {
//     console.log(param);
//     local.get(`${url}/list`, { params: param }).then(success).catch(fail);
// }

function listAttraction(param, success, fail) {
    console.log("attraction.js =>");
    console.log(param.sido_code + "," + param.gugun_code);
    console.log(param);
    console.log(JSON.stringify(param));
    local.get(`${url}/list`, { params: param }).then(success).catch(fail);
    // local.post(`${url}/list`, JSON.stringify(param)).then(success).catch(fail);
    // sido_code: param.sido_code, gugun_code: param.gugun_code
}

export { listAttraction };
