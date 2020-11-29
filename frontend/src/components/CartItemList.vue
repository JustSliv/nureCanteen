<template>
  <v-list>
    <CartItem
        flat
        v-for="(item, i) in items"
        :key="i"
        :cart-item="item"
        :locales="locales"
        :all-cart="allCart"
        :updater="updater"
    />
  </v-list>
</template>

<script>
import CartItem from "@/components/CartItem";
export default {
  name: "CartItemList",
  props: ['items', 'allCart', 'locales', 'updater'],
  components: {CartItem}
}
</script>

<style scoped>

</style>