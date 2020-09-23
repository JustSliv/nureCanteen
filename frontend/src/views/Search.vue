<template>
    <v-app>
        <v-card style="margin: 10% 20% 5% 20%" v-if="getSearchText">
            <v-card-title>Поиск по запросу: {{getSearchText}}</v-card-title>
            <v-divider></v-divider>
            <ProductList :info="getSearchItems" style="margin: 2%"/>
        </v-card>
        <v-card v-else style="margin: 10% 20% 5% 20%; padding: 10% 0 10% 0">
            <v-icon large style="text-align: center; display: block">warning</v-icon>
            <v-card-title style="justify-content: center">Ничего не найдено</v-card-title>
        </v-card>
    </v-app>
</template>

<script>
import ProductList from "@/components/ProductList";

export default {
  name: "Search",
  components: {
      ProductList
  },
  data() {
    return {}
  },
  computed: {
    getSearchItems() {
      return {
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
            id: 0,
            name: "Пирожок",
            price: 54,
            category: "Духовный",
            description: "вкусный",
            available_count: 20,
            image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
          },
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
            id: 0,
            name: "Пирожок",
            price: 54,
            category: "Духовный",
            description: "вкусный",
            available_count: 20,
            image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
          },
        ]
      }
    },
    getSearchText() {
        return localStorage['search_query'];
    }
  }
}
</script>

<style scoped>

</style>