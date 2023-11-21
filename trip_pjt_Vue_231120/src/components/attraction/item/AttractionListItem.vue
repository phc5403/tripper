<script setup>
defineProps({ attraction: Object });
</script>

<template>
    <tr class="text-center">
        <th>
            <img class="attrImg" v-if="attraction.first_image.length == 0" src="https://www.verdantis.com/wp-content/uploads/2016/04/noimg.jpg" />
            <img class="attrImg" v-else :src="attraction.first_image" />
        </th>

        <th>{{ attraction.title }}</th>
        <td>{{ attraction.addr1 }}</td>

        <!-- <th scope="row">{{ attraction.title }}</th>
        <td class="text-start">
            {{ attraction.addr1 }}
        </td>
        <td>{{ attraction.first_image }}</td> -->
    </tr>
</template>

<style scoped>
a {
    text-decoration: none;
}

.attrImg {
    width: 200px;
    height: 200px;
}
</style>
