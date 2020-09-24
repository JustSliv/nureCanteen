<template>
    <v-app>
<!--        <v-card style="margin-top: 8%" max-width="240px" max-height="100%">-->
<!--            <FilterList :info="info"/>-->
<!--        </v-card>-->
      <v-card style="margin: 10%; background-color: #bfe9ff" flat>
        <v-menu v-model="showFilters" max-width="180">
          <template v-slot:activator="{on}">
            <v-card-title>
              Все продукты
              <v-btn v-on="on" icon @click="showFilters = !showFilters">
                <v-icon>
                  filter_list
                </v-icon>
              </v-btn>
            </v-card-title>
          </template>
          <v-card>
            <v-card-title>
              Фильтры:
              <v-radio-group>
                <v-radio
                    v-for="item in getFilters"
                    :key="item.id"
                    :label="item.category"
                    :value="item.category"
                    @click="execFilter"
                    :data-id="item.id"
                ></v-radio>
              </v-radio-group>
            </v-card-title>
          </v-card>
        </v-menu>
        <ProductList :info="info" @update-products="updateProductsList" v-model="info.products"/>
      </v-card>
    </v-app>
</template>

<script>
    import ProductList from "../components/ProductList";

  export default {
    name: "Products",
    components: {
      ProductList
    },
    data: () => ({
      info: {
        user_info: {
            login: false,
            name: "tester",
            university: ""
        },
        products: [
            {
                id: 0,
                name: "Пирожок",
                price: 54,
                category: "Пряности",
                description: "вкусный",
                total_count: 20,
                available_count: 20,
                image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
            },
            {
                id: 1,
                name: "Пирожок1",
                price: 54,
                category: "Пряности",
                description: "вкусный",
                total_count: 20,
              available_count: 20,
                image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
            },
            {
                id: 2,
                name: "Пирожок2",
                price: 54,
                category: "Пряности",
                description: "вкусный",
              total_count: 20,
              available_count: 20,
                image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
            },
            {
                id: 3,
                name: "Пирожок3",
                price: 54,
                category: "Пряности",
                description: "вкусный",
              total_count: 20,
              available_count: 20,
                image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
            },
            {
                id: 4,
                name: "Пирожок4",
                price: 54,
                category: "Пряности",
                description: "вкусный",
              total_count: 20,
              available_count: 20,
                image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
            },
            {
                id: 5,
                name: "Пирожок5",
                price: 54,
                category: "Пряности",
                description: "вкусный",
              total_count: 20,
              available_count: 20,
                image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
            },
            {
                id: 6,
                name: "Пирожок6",
                price: 54,
                category: "Пряности",
                description: "вкусный",
              total_count: 20,
              available_count: 20,
                image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
            },
            {
                id: 7,
                name: "Пирожок7",
                price: 54,
                category: "Пряности",
                description: "вкусный",
              total_count: 20,
              available_count: 20,
                image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
            },
            {
                id: 8,
                name: "Пирожок8",
                price: 54,
                category: "Пряности",
                description: "вкусный",
              total_count: 20,
              available_count: 20,
                image: require("../assets/imgs/572f9a16875ed15491f1e81a.png")
            }
        ]
        },
      drawer: false,
      showFilters: false
    }),
    methods: {
      execFilter(ev) {
        let filter_id = 0;
        try {
          filter_id = parseInt(ev.path[0].attributes[0].ownerElement.previousSibling.attributes['data-id'].value)
        } catch (err) {
          filter_id = parseInt(ev.path[1].firstChild.lastChild.offsetParent.firstChild.nextSibling.attributes['data-id'].value)
        }
        for (let i=0;i<this.info.filters.length;i++) {
          let item = this.info.filters[i]
          if (item.id === filter_id) {
            this.info.products = this.info.products.filter(i => i.category === item.category)
          }
        }
      },
      updateProductsList(newValue) {
        console.log('emited')
        console.log(newValue)
        this.info.products = newValue
      }
    },
    computed: {
      getFilters() {
        let res = [];
        localStorage.removeItem('receipt')
        for (let i=0;i<this.info.products.length;i++) {
          let item = this.info.products[i]
          if (res.length > 0) {
            try {
              if (item.category !== res[i-1].category) {
                if (res[0].category === null) res.splice(0, 1)
                res.push({
                  id: i,
                  category: item.category
                })
              }
            } catch (err) {
              continue;
            }
          } else if (res.length <= 0) {
            res.push(
              {
                id: null,
                category: null
              }
            )
          }
        }
        return res;
      }
    }
  }
</script>

<style scoped>
    /*@media screen and (min-width: 1200px) and (max-width: 1700px){*/
    /*    .products {*/
    /*        margin-top: 1.5%;*/
    /*        margin-left: 1.5%;*/
    /*    }*/
    /*}*/
</style>