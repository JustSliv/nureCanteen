<template>
  <v-list-group
      append-icon="keyboard_arrow_right"
      @click="doRedirCategory"
  >
    <template v-slot:activator>
      <v-card-subtitle>
        {{item.category}}
      </v-card-subtitle>
    </template>
  </v-list-group>
</template>

<script>
export default {
  name: "CategorySubItem",
  props: ['item'],
  methods: {
    doRedirCategory() {
      localStorage['category'] = this.item.category
      window.location.href = '/products'
    }
  }
}
</script>

<style scoped>

</style>