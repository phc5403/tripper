<script setup>
import { useMenuStore } from "@/stores/menu";
import { storeToRefs } from "pinia";
import { useMemberStore } from "../../stores/member";

const menuStore = useMenuStore();
const memberStore = useMemberStore();

//반응형을 유지하면서 스토어에서 속성을 추출하려면, storeToRefs()를 사용
const { menuList } = storeToRefs(menuStore);
const { changeMenuState } = menuStore;

const { userLogout } = memberStore;

const logout = () => {
    console.log("로그아웃!!");
    //server로 로그아웃 정보 보내고 access, refresh token 정보 삭제
    userLogout();
    changeMenuState();
};
</script>

<template>
    <nav class="navbar fixed-top navbar-expand-lg navbar-white bg-gradient-white z-index-3 py-3">
        <div class="container">
            <router-link to="/">
                <img src="@/assets/ssafy_logo.png" class="rounded mx-auto d-block w-75" alt="..." />
            </router-link>

            <button
                class="navbar-toggler shadow ms-2 border-radius-xl"
                type="button"
                data-bs-toggle="collapse"
                data-bs-target="#navigation"
                aria-controls="navigation"
                aria-expanded="false"
                aria-label="Toggle navigation"
            >
                <span class="navbar-toggler-icon mt-2">
                    <span class="navbar-toggler-bar bar1"></span>
                    <span class="navbar-toggler-bar bar2"></span>
                    <span class="navbar-toggler-bar bar3"></span>
                </span>
            </button>
            <div class="collapse navbar-collapse w-100 pt-3 pb-2 py-lg-0" id="navigation">
                <ul class="navbar-nav navbar-nav-hover mx-auto">
                    <li class="nav-item mx-2">
                        <router-link to="/attraction" class="nav-link">지역별 여행지</router-link>
                    </li>

                    <li class="nav-item mx-2">
                        <router-link to="/" class="nav-link">나의여행계획</router-link>
                    </li>
                    <li class="nav-item mx-2">
                        <router-link to="/" class="nav-link">핫플자랑하기</router-link>
                    </li>

                    <li class="nav-item dropdown dropdown-hover mx-2">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"> 커뮤니티 </a>
                        <ul class="dropdown-menu">
                            <li>
                                <router-link :to="{ name: 'board' }" class="nav-link">게시판</router-link>
                            </li>
                            <li>
                                <router-link to="/board" class="nav-link">공지사항</router-link>
                            </li>
                        </ul>
                    </li>
                    <!-- <li class="nav-item mx-2">
            <router-link to="/mypage">MyPage</router-link>
          </li> -->
                </ul>

                <!-- 로그인, 로그아웃, MyPage 링크를 오른쪽으로 이동 -->
                <ul class="navbar-nav ms-auto">
                    <template v-for="menu in menuList" :key="menu.routeName">
                        <template v-if="menu.show">
                            <template v-if="menu.routeName === 'user-logout'">
                                <li class="nav-item">
                                    <router-link to="/" @click.prevent="logout" class="nav-link">{{ menu.name }}</router-link>
                                </li>
                            </template>
                            <template v-else>
                                <li class="nav-item">
                                    <router-link :to="{ name: menu.routeName }" class="nav-link">{{ menu.name }}</router-link>
                                </li>
                            </template>
                        </template>
                    </template>
                </ul>
            </div>
        </div>
    </nav>
</template>

<style scoped>
.navbar-nav.ms-auto {
    margin-right: 10px;
}

.container {
    background-color: rgba(217, 219, 223, 0.4);
    border-radius: 0.5rem;
}
a {
    color: white;
}
.dropdown-menu {
    background-color: rgba(217, 219, 223, 0.4);
}
nav {
    margin-top: 1px;
    padding: 20px; /* 원하는 여백 추가 */
    width: 100%; /* 화면 너비에 맞게 설정 */
}
</style>
