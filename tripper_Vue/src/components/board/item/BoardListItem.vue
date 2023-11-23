<script setup>
defineProps({ article: Object });
</script>

<template>
  <link href="https://fonts.googleapis.com/css2?family=Dongle&display=swap" rel="stylesheet" />
  <tr class="text-center">
    <th scope="row">{{ article.board_no }}</th>
    <td class="text-start">
      <router-link
        :to="{ name: 'article-view', params: { articleno: article.board_no } }"
        class="article-title link-dark"
      >
        {{ article.board_title }}
      </router-link>
    </td>
    <td>{{ article.user_id }}</td>
    <td>{{ article.board_hit }}</td>
    <td>{{ article.board_write_time }}</td>
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
