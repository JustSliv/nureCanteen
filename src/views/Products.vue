<template>
    <v-app>
        <v-card style="margin-top: 8%" max-width="240px" max-height="100%">
            <v-card-title style="font-size: 20pt">Фильтры:</v-card-title>
            <FilterList :info="info"/>
        </v-card>
        <div style="position: absolute; top: 14%; left: 15%;" class="products">
            <ProductList :info="info"/>
        </div>
    </v-app>
</template>

<script>
    import FilterList from "../components/FilterList";
    import ProductList from "../components/ProductList";

    export default {
        name: "Products",
        components: {
            FilterList,
            ProductList
        },
        data: () => ({
            info: {
                user_info: {
                    login: false,
                    name: "tester",
                    university: ""
                },
                filters: [
                    {
                        id: 0,
                        name: "Test1"
                    },
                    {
                        id: 1,
                        name: "Test2"
                    },
                    {
                        id: 2,
                        name: "Test3"
                    },
                    {
                        id: 3,
                        name: "Test4"
                    }
                ],
                products: [
                    {
                        id: 0,
                        name: "Пирожок",
                        price: 54,
                        category: "Духовный",
                        description: "вкусный",
                        available_count: 20,
                        image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
                    },
                    {
                        id: 1,
                        name: "Пирожок1",
                        price: 54,
                        category: "Духовный",
                        description: "вкусный",
                        available_count: 20,
                        image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
                    },
                    {
                        id: 2,
                        name: "Пирожок2",
                        price: 54,
                        category: "Духовный",
                        description: "вкусный",
                        available_count: 20,
                        image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
                    },
                    {
                        id: 3,
                        name: "Пирожок3",
                        price: 54,
                        category: "Духовный",
                        description: "вкусный",
                        available_count: 20,
                        image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
                    },
                    {
                        id: 4,
                        name: "Пирожок4",
                        price: 54,
                        category: "Духовный",
                        description: "вкусный",
                        available_count: 20,
                        image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
                    },
                    {
                        id: 5,
                        name: "Пирожок5",
                        price: 54,
                        category: "Духовный",
                        description: "вкусный",
                        available_count: 20,
                        image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
                    },
                    {
                        id: 6,
                        name: "Пирожок6",
                        price: 54,
                        category: "Духовный",
                        description: "вкусный",
                        available_count: 20,
                        image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
                    },
                    {
                        id: 7,
                        name: "Пирожок7",
                        price: 54,
                        category: "Духовный",
                        description: "вкусный",
                        available_count: 20,
                        image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
                    },
                    {
                        id: 8,
                        name: "Пирожок8",
                        price: 54,
                        category: "Духовный",
                        description: "вкусный",
                        available_count: 20,
                        image: require("../assets/imgs/572f9a16875ed15491f1e81a.png")
                    }
                ]
            },
            drawer: false
        })
    }
</script>

<style scoped>
    @media screen and (min-width: 1200px) and (max-width: 1700px){
        .products {
            margin-top: 1.5%;
            margin-left: 1.5%;
        }
    }
</style>