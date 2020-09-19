<template>
    <div>
        <v-card-title style="font-size: 20pt">Все продукты</v-card-title>
        <div id="grid">
            <Product
                    v-for="(product, i) in info.products" v-bind:key="i"
                    v-bind:product="product"
            />
        </div>
    </div>
</template>

<script>
    import Product from '@/components/Product';
    export default {
        name: "ProductList",
        props: ['info'],
        components: {
            Product
        }
    }
</script>

<style scoped>
    #grid {
        display: grid;
        grid-template-columns: repeat(8, auto);
        grid-gap: 8px;
    }
</style>