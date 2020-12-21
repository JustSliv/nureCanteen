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
    return {
      products: [
        {
          id: 0,
          name: "Пирожок",
          price: 54,
          currency: "UAH",
          description: "вкусный",
          available_count: 20,
          image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
        },
        {
          id: 1,
          name: "Пирожок1",
          price: 4,
          currency: "UAH",
          description: "вкусный",
          available_count: 20,
          image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
        },
        {
          id: 2,
          name: "Пирожок2",
          price: 51,
          currency: "UAH",
          description: "вкусный",
          available_count: 20,
          image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
        },
        {
          id: 3,
          name: "Пирожок3",
          price: 34,
          currency: "UAH",
          description: "вкусный",
          available_count: 20,
          image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
        },
        {
          id: 4,
          name: "Пирожок4",
          price: 14,
          currency: "UAH",
          description: "вкусный",
          available_count: 20,
          image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
        },
        {
          id: 5,
          name: "Пирожок5",
          price: 74,
          currency: "UAH",
          description: "вкусный",
          available_count: 20,
          image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
        },
        {
          id: 6,
          name: "Пирожок6",
          price: 55,
          currency: "UAH",
          description: "вкусный",
          available_count: 20,
          image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
        },
        {
          id: 7,
          name: "Пирожок7",
          price: 53,
          currency: "UAH",
          description: "вкусный",
          available_count: 20,
          image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
        },
        {
          id: 8,
          name: "Пирожок8",
          price: 23,
          currency: "UAH",
          description: "вкусный",
          available_count: 20,
          image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
        }
      ]
    }
  },
  computed: {
    getSearchItems() {
      // sending GET to DB
      let regex = new RegExp(localStorage['search_query'], 'i');
      let items = this.products.filter(i => regex.test(i.name))
      return items === undefined?[]:{products: items};
    },
    getSearchText() {
        return localStorage['search_query'];
    }
  }
}
</script>

<style scoped>

</style>