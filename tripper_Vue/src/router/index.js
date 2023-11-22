import { createRouter, createWebHistory } from "vue-router";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "main",
      component: () => import("@/views/TheMainView.vue"),
    },
    {
      // User Board //

      path: "/board",
      name: "board",
      component: () => import("@/views/TheBoardView.vue"),
      redirect: { name: "article-list" },

      // children은 자동으로 "/"를 붙여줌 -> path에 적으면 안됨(에러)
      children: [
        {
          path: "list",
          name: "article-list",
          component: () => import("@/components/board/BoardList.vue"),
          meta: { require: true }, //특정 페이지 이동 전 로그인 여부 검사하는 상태 변수 설정
        },
        {
          path: "view/:articleno",
          name: "article-view",
          component: () => import("@/components/board/BoardDetail.vue"),
        },
        {
          path: "modify/:articleno",
          name: "article-modify",
          component: () => import("@/components/board/BoardModify.vue"),
        },
        {
          path: "write",
          name: "article-write",
          component: () => import("@/components/board/BoardWrite.vue"),
        },
      ],
    },
    {
      // Admin Board //

      path: "/notice",
      name: "notice",
      component: () => import("@/views/TheNoticeView.vue"),
      redirect: { name: "notice-list" },

      children: [
        {
          path: "noticelist",
          name: "notice-list",
          component: () => import("@/components/notice/NoticeList.vue"),
        },
        {
          path: "view/:noticeno",
          name: "notice-view",
          component: () => import("@/components/notice/NoticeDetail.vue"),
        },
        {
          path: "modify/:noticeno",
          name: "notice-modify",
          component: () => import("@/components/notice/NoticeModify.vue"),
        },
        {
          path: "write",
          name: "notice-write",
          component: () => import("@/components/notice/NoticeWrite.vue"),
        },
      ],
    },
    {
      // Attraction  //
      path: "/attraction",
      name: "attraction",
      component: () => import("@/views/TheAttractionView.vue"),
      // redirect: { name: "attraction" },
    },
    {
      // User //
      path: "/user",
      name: "user",
      component: () => import("@/views/TheUserView.vue"),
      children: [
        {
          path: "login",
          name: "user-login",
          component: () => import("@/components/users/UserLogin.vue"),
        },
        {
          path: "join",
          name: "user-join",
          component: () => import("@/components/users/UserJoin.vue"),
        },
        {
          path: "findpwd",
          name: "find-pwd",
          component: () =>
            import("@/components/users/item/UserPasswordFind.vue"),
        },
        // {
        //   path: "mypage",
        //   name: "user-mypage",
        //   component: () => import("@/components/users/UserMypage.vue"),
        // },
      ],
    },

    {
      // Mypage //
      path: "/mypage",
      name: "user-mypage",
      // beforeEnter: onlyAuthUser,
      component: () => import("@/views/TheMyPageView.vue"),
      redirect: { name: "mypage-info" },
      children: [
        {
          path: "info",
          name: "mypage-info",
          component: () => import("@/components/users/UserMyPage.vue"),
        },
      ],
    },
  ],
});
//전역 가드 설정
router.beforeEach(async (to, from, next) => {
  const memberStore = useMemberStore();
  const { userInfo } = storeToRefs(memberStore);
  const { getUserInfo } = memberStore;
  let token = sessionStorage.getItem("accessToken");
  if (token) {
    //router 돌기 전에 토큰 여부를 검사
    await getUserInfo(token); //토큰에 맞는 유저 정보를 가져옴
    console.log("현재 accessToken이 존재합니다.");
    console.log("현재 저장된 유저의 정보입니다.", userInfo.value);
    next();
  } else if (to.meta.require) {
    //로그인 되어있지 않다면 알림 보여주고 로그인 창으로 리디렉션
    alert("로그인 후 이용해주세요.");
    next("/user/login");
  } else {
    //아예 로그인 하지 않은 경우(맨 처음 페이지 띄웠을 때)
    console.log("현재 로그인 된 유저의 정보가 존재하지 않습니다.");
    next();
  }
});
export default router;