<template>
  <v-list>
    <ProductInSaleItem
        v-for="(item, i) in products" :key="i"
        :product="item"
        :updater="updater"
        :locale="locale"
    />
  </v-list>
</template>

<script>
import ProductInSaleItem from "@/components/ProductInSaleItem";
export default {
  name: "ProductInSaleList",
  components: {ProductInSaleItem},
  props: ['products', 'updater', 'locale']
}
</script>

<style scoped>

</style>