<template>
  <div>
    <v-radio
        :label="filter.name"
        :value="filter.category"
        @change="$emit('kek')"
        v-on:kek="keks"
    ></v-radio>
  </div>
</template>

<script>
    export default {
      name: 'FilterUn',
      data() {
        return {
          productS: []
        }
      },
      props: {
        filter: {
          type: Object,
          required: true
        },
        products: {
          type: Array,
          required: true
        }
      },
      mounted() {
        this.productS = this.products
      },
      methods: {
        keks() {
          console.log('dsdadas')
        },
        execFilter() {
          console.log(this.filter.category)
          this.productS = this.productS.filter(i => i.category === this.filter.category)
          this.$emit('kek')
          console.log(this.productS)
        }
      }
    }
</script>

<style scoped>

</style>