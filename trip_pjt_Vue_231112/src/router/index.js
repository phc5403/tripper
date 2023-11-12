import { createRouter, createWebHistory } from "vue-router";

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
      // Trip  //
      path: "/trip",
      name: "trip",
      component: () => import("@/views/TheTripView.vue"),
      // redirect: { name: "trip" },
    },
    {
      // User //
      path: "/user/login",
      name: "user-login",
      component: () => import("@/views/TheUserView.vue"),
    },
  ],
});

export default router;
