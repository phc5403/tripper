import { ref } from "vue";
import { defineStore } from "pinia";

export const useMenuStore = defineStore("menuStore", () => {
  //로그인하면 세션에 토큰이 있는지 체크, 있다면 네비게이션 바 show 값 고정
  const hasTokens = sessionStorage.getItem("accessToken") !== null;

  const initialMenuList = [
    { name: "회원가입", show: !hasTokens, routeName: "user-join" },
    { name: "로그인", show: !hasTokens, routeName: "user-login" },
    { name: "MyPage", show: hasTokens, routeName: "user-mypage" },
    { name: "로그아웃", show: hasTokens, routeName: "user-logout" },
  ];

  const menuList = ref(initialMenuList);

  const changeMenuState = () => {
    menuList.value = menuList.value.map((item) => ({
      ...item,
      show: !item.show,
    }));
  };
  return {
    menuList,
    changeMenuState,
  };
});
