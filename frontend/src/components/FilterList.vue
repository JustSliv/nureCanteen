<template>
  <v-radio-group v-on:update21="updateProducts">
    <FilterUn
        v-for="(filter, i) in info.filters" :key="i" :filter="filter" :products="info.products"
    />
  </v-radio-group>
</template>

<script>
    // import Filter from "@/components/Filter";
    import FilterUn from "@/components/FilterUn";
    export default {
      props: ['info'],
      components: {
        FilterUn
          // Filter
      },
      methods: {
        updateProducts(item) {
          console.log('heeeree')
          this.$emit('update-products', item)
        }
      },
      computed: {
        execFilter() {
          console.log(this.info.products)
          console.log('sda')
          return 0;
        }
      }
    }
</script>

<style scoped>
</style>