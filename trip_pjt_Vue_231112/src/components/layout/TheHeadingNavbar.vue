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
                        <router-link to="/trip" class="nav-link">지역별 여행지</router-link>
                    </li>

                    <li class="nav-item mx-2">
                        <router-link to="/" class="nav-link">나의여행계획</router-link>
                    </li>
                    <li class="nav-item mx-2">
                        <router-link to="/" class="nav-link">핫플자랑하기</router-link>
                    </li>

                    <li class="nav-item dropdown dropdown-hover mx-2">
                        <a
                            class="nav-link dropdown-toggle"
                            href="#"
                            role="button"
                            data-bs-toggle="dropdown"
                        >
                            커뮤니티
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <router-link :to="{ name: 'board' }" class="nav-link"
                                    >게시판</router-link
                                >
                            </li>
                            <li>
                                <router-link to="/board" class="nav-link">공지 사항</router-link>
                            </li>
                        </ul>
                    </li>
                </ul>

                <!-- 로그인, 로그아웃, MyPage 링크를 오른쪽으로 이동 -->
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <router-link to="/login" class="nav-link">로그인</router-link>
                    </li>

                    <li class="nav-item">
                        <router-link to="/logout" class="nav-link">로그아웃</router-link>
                    </li>

                    <li class="nav-item">
                        <router-link to="/mypage" class="nav-link">MyPage</router-link>
                    </li>
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
