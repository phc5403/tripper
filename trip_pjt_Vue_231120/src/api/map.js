import { localAxios } from "@/util/http-commons";

const local = localAxios();

function listSido(param, success, fail) {
    local.get(`/trip/sido`, { params: param }).then(success).catch(fail);
}

function listGugun(param, success, fail) {
    console.log(param);
    local.get(`/trip/gugun`, { params: param }).then(success).catch(fail);
}

export { listSido, listGugun };
