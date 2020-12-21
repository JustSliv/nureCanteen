<template>
  <v-card color="purple" dark>
    <v-list>
      <CategorySubItem
          v-for="(item, i) in info.filters"
          :key="i"
          :item="item"
      />
    </v-list>
  </v-card>
</template>

<script>
import CategorySubItem from "@/components/CategorySubItem";
export default {
  name: "CategorySubList",
  components: {CategorySubItem},
  props: ['info'],
}
</script>

<style scoped>

</style>