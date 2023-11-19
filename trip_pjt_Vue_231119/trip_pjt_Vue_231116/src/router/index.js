import { createRouter, createWebHistory } from "vue-router";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";

const onlyAuthUser = async (to, from, next) => {
    const memberStore = useMemberStore();
    const { userInfo, isValidToken } = storeToRefs(memberStore);
    const { getUserInfo } = memberStore;
  
    let token = sessionStorage.getItem("accessToken");
  
    if (userInfo.value != null && token) {
      await getUserInfo(token);
    }
    if (!isValidToken.value || userInfo.value === null) {
      next({ name: "user-login" });
    } else {
      next();
    }
  };

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: "/",
            name: "main",
            component: () => import("@/views/TheMainView.vue"),
        },
        {
            // Board //

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
            beforeEnter: onlyAuthUser,
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

export default router;
