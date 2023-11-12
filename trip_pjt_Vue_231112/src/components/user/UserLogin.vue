<script setup></script>

<template>
    <main>
        <div class="container pt-5 pb-5">
            <div class="w-50 m-auto border mt-5 mb-5">
                <div class="container text-center pb-3 mt-5 mb-3 w-25 border-bottom border-4">
                    <div class="fs-2 fw-semibold">로그인</div>
                </div>

                <div class="w-75 m-auto">
                    <form class="form" method="post" action="${root}/user?action=login">
                        <div class="mb-3">
                            <label class="form-label" for="login-id">아이디</label>
                            <input class="form-control" type="text" id="login-id" name="id" value="${saveid}" />
                        </div>
                        <div class="mb-3">
                            <label class="form-label" for="login-password">비밀번호</label>
                            <input class="form-control" type="password" id="login-password" name="password" />
                        </div>
                        <div class="mb-1">
                            <input id="rememberme" class="mb-2" type="checkbox" value="ok" name="rememberme" ${rememberme} />
                            <label for="rememberme" class="form-label">아이디 저장</label>
                        </div>
                        <div class="d-flex justify-content-center mb-4">
                            <button class="btn col-4 text-secondary" type="button">아이디 찾기</button>
                            <button class="btn col-4 text-secondary" type="button">비밀번호 찾기</button>
                        </div>
                        <div class="mb-5">
                            <button id="login" class="btn w-100 btn-secondary" type="submit">로그인</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </main>
</template>

<style scoped></style>
