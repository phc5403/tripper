<script setup>
defineProps({ notice: Object });
</script>

<template>
  <link href="https://fonts.googleapis.com/css2?family=Dongle&display=swap" rel="stylesheet" />
  <tr class="text-center">
    <th scope="row">{{ notice.notice_no }}</th>
    <td class="text-start">
      <router-link
        :to="{ name: 'notice-view', params: { noticeno: notice.notice_no } }"
        class="notice-title link-dark"
      >
        {{ notice.notice_title }}
      </router-link>
    </td>
    <td>{{ notice.user_id }}</td>
    <td>{{ notice.notice_hit }}</td>
    <td>{{ notice.notice_write_time }}</td>
  </tr>
</template>

<style scoped>
a {
  text-decoration: none;
}
td{
  font-family: "Dongle", sans-serif;
  font-size: 24px;
}
</style>
