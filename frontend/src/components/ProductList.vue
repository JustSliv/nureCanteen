<template>
    <v-container>
      <v-row>
        <v-col cols="2" v-for="(product, i) in info.products" v-bind:key="i">
          <Product
              v-bind:product="product"
              :alert-unauthorized="alertUnauthorized"
              :updater="updater"
          />
        </v-col>
      </v-row>
    </v-container>
</template>

<script>
  import Product from '@/components/Product';

  export default {
    name: "ProductList",
    props: ['info', 'alertUnauthorized', 'updater'],
    components: {
        Product
    }
  }
</script>

<style scoped>

</style>