import { ref } from "vue";
import { useRouter } from "vue-router";
import { defineStore } from "pinia";
import { jwtDecode } from "jwt-decode";

import { userConfirm, findById, tokenRegeneration, logout } from "@/api/user";
import { httpStatusCode } from "@/util/http-status";

export const useMemberStore = defineStore("memberStore", () => {
  const router = useRouter();

  const isLogin = ref(false);
  const isLoginError = ref(false);
  const userInfo = ref(null);
  const isValidToken = ref(false);

  //로그인 요청
  const userLogin = async (loginUser) => {
    await userConfirm(
      loginUser,
      (response) => {
        console.log("login ok!!!!", response.status);
        console.log("login ok!!!!", httpStatusCode.CREATE);
        if (response.status === httpStatusCode.CREATE) {
          let { data } = response;
          console.log("data", data);
          //token 받기
          let accessToken = data["access-token"];
          let refreshToken = data["refresh-token"];

          console.log("accessToken", accessToken);
          console.log("refreshToken", refreshToken);
          //state에 필요한 정보 세팅
          isLogin.value = true;
          isLoginError.value = false;
          isValidToken.value = true;
          sessionStorage.setItem("accessToken", accessToken);
          sessionStorage.setItem("refreshToken", refreshToken);
          console.log("sessiontStorage에 담았다", isLogin.value);
        } else {
          console.log("로그인 실패했다");
          isLogin.value = false;
          isLoginError.value = true;
          isValidToken.value = false;
        }
      },
      (error) => {
        console.error(error);
      }
    );
  };
  //데이터 요청 with access token
  const getUserInfo = async (token) => {
    let decodeToken = jwtDecode(token);
    console.log("2. decodeToken", decodeToken);
    console.log("유저 정보는? ", decodeToken.userId);
    await findById(
      //token의 유효성 검증 후 user 정보 얻기
      decodeToken.userId,

      (response) => {
        if (response.status === httpStatusCode.OK) {
          userInfo.value = response.data.userInfo;
          console.log("3. getUserInfo data >> ", response.data);
        } else {
          console.log("유저 정보 없음!!!!");
        }
      },
      //사용 불가능 토큰일 경우 서버에서 HttpStatus, UNAUTHORIZE(401)전달
      async (error) => {
        console.error(
          "getUserInfo() error code [토큰 만료되어 사용 불가능.] ::: ",
          error.response.status
        );
        isValidToken.value = false;
        //access token 재발급
        await tokenRegenerate();
      }
    );
  };

  const tokenRegenerate = async () => {
    console.log("토큰 재발급 >> 기존 토큰 정보 : {}", sessionStorage.getItem("accessToken"));
    await tokenRegeneration(
      //서버로 access token 재발급 요청
      JSON.stringify(userInfo.value),
      (response) => {
        if (response.status === httpStatusCode.CREATE) {
          let accessToken = response.data["access-token"];
          console.log("재발급 완료 >> 새로운 토큰 : {}", accessToken);
          sessionStorage.setItem("accessToken", accessToken);
          isValidToken.value = true;
        }
      },
      async (error) => {
        //401 에러 응답이 온 경우 refresh token 유효기간이 만료된 것. 다시 로그인
        // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
        if (error.response.status === httpStatusCode.UNAUTHORIZED) {
          console.log("갱신 실패");
          // 다시 로그인 전 DB에 저장된 RefreshToken 제거.
          await logout(
            userInfo.value.userid,
            (response) => {
              if (response.status === httpStatusCode.OK) {
                console.log("리프레시 토큰 제거 성공");
              } else {
                console.log("리프레시 토큰 제거 실패");
              }
              alert("RefreshToken 기간 만료!!! 다시 로그인해 주세요.");
              isLogin.value = false;
              userInfo.value = null;
              isValidToken.value = false;
              router.push({ name: "user-login" });
            },
            (error) => {
              console.error(error);
              isLogin.value = false;
              userInfo.value = null;
            }
          );
        }
      }
    );
  };
  //DB에서 refresh token 삭제 요청
  const userLogout = async () => {
    console.log("로그아웃 아이디: " + userInfo.value.user_id);
    //server로 로그아웃 정보 전송
    await logout(
      // userid,
      userInfo.value.userId,
      (response) => {
        if (response.status === httpStatusCode.OK) {
          isLogin.value = false;
          userInfo.value = null;
          isValidToken.value = false;

          sessionStorage.removeItem("accessToken");
          sessionStorage.removeItem("refreshToken");
        } else {
          console.error("유저 정보 없음!!!!");
        }
      },
      (error) => {
        console.log(error);
      }
    );
  };

  return {
    isLogin,
    isLoginError,
    userInfo,
    isValidToken,
    userLogin,
    getUserInfo,
    tokenRegenerate,
    userLogout,
  };
});
