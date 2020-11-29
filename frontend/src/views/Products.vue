<template>
  <v-app>
    <v-card style="margin: 10%; background-color: #FFF9C4" flat>
      <v-menu v-model="showFilters" max-width="180">
        <template v-slot:activator="{on}">
          <v-card-title>
            {{curLocale.productInfo.title}}
            <v-btn v-on="on" icon @click="showFilters = !showFilters">
              <v-icon>
                filter_list
              </v-icon>
            </v-btn>
          </v-card-title>
        </template>
        <v-card>
          <v-card-title>
            {{curLocale.productInfo.filterTitle}}
            <v-radio-group>
              <v-radio
                  v-for="item in info.filters"
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
      <ProductList
          :info="info"
          @update-products="updateProductsList"
          v-model="info.products"
          :alert-unauthorized="alertUnauthorized"
          :updater="updater"
      />
      <v-dialog v-model="alertUnauthorized" width="480">
        <v-card>
          <v-card-title style="justify-content: center; display: flex">
            Войдите в систему чтобы оформить заказ!
          </v-card-title>
          <v-divider></v-divider>
          <v-card-actions style="justify-content: center; display: flex">
            <v-btn color="deep-purple" text outlined to="/register" @click="doRegisterForProducts">
              Регистрация
            </v-btn>
            <v-btn color="success" text outlined to="/auth" @click="doLoginForProducts">
              Войти
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-card>
  </v-app>
</template>

<script>
  import ProductList from "../components/ProductList";

  const ip = 'localhost'
  const port = 25016;
  const axios = require('axios')

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
                category: "Напиток",
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
        ],
        filters: [
          {
            id: 0,
            category: 'Первое блюдо'
          },
          {
            id: 1,
            category: 'Второе блюдо'
          },
          {
            id: 2,
            category: 'Пряности'
          },
          {
            id: 3,
            category: 'Напитки'
          }
        ]
        },
      drawer: false,
      showFilters: false,
      translate: false,
      curLocale: {},
      locales: {
        'en-EN': {
          productInfo: {
            title: 'All goods',
            filterTitle: 'Filters:',
            currency: 'UAH.',
            toCart: {
              toCartTitle: 'To cart',
              tip: 'Item will be put to cart',
              btns: [
                'Cancel', 'Continue'
              ],
              alerts: [
                'This item already in cart', 'Item added to cart'
              ],
              alertBtn: 'Cart'
            },
            info: [
              'Category:', 'Available:', '', 'Description:'
            ],
          }
        },
        'ru-RU': {
          productInfo: {
            title: 'Все продукты',
            filterTitle: 'Фильтры:',
            currency: 'ГРН.',
            toCart: {
              toCartTitle: 'В корзину',
              tip: 'Товар будет занесен к вам в корзину',
              btns: [
                  'Отмена', 'Продолжить'
              ],
              alerts: [
                  'Данный товар уже в корзине', 'Товар добавлен в корзине'
              ],
              alertBtn: 'Корзина'
            },
            info: [
                'Категория:', 'Доступно:', 'шт.', 'Описание:'
            ],
          }
        },
        'ua-UA': {
          productInfo: {
            title: 'Усі продукті',
            filterTitle: 'Фільтри:',
            currency: 'ГРН.',
            toCart: {
              toCartTitle: 'До корзини',
              tip: 'Товар буде додан до вашої корзини',
              btns: [
                'Відміна', 'Продовжити'
              ],
              alerts: [
                'Данний товар вже у корзині', 'Товар додан до корзини'
              ],
              alertBtn: 'Корзина'
            },
            info: [
              'Категорія:', 'Доступно:', 'шт.', 'Опис:'
            ],
          }
        }
      },
      langOne: false,
      langSecond: false,
      langThird: false,
      alertUnauthorized: false
    }),
    methods: {
      doRegisterForProducts() {
        localStorage['redirect'] = this.$route.fullPath
        this.alertUnauthorized = false;
        this.$router.push('/register')
      },
      doLoginForProducts() {
        localStorage['redirect'] = this.$route.fullPath
        this.alertUnauthorized = false;
        this.$router.push('/auth')
      },
      updater(info) {
        this.alertUnauthorized = info.value
      },
      execFilter(ev) {
        let filter_id = 0;
        try {
          filter_id = parseInt(ev.path[0].attributes[0].ownerElement.previousSibling.attributes['data-id'].value)
        } catch (err) {
          filter_id = parseInt(ev.path[1].firstChild.lastChild.offsetParent.firstChild.nextSibling.attributes['data-id'].value)
        }
        // sending GET to product
        for (let i=0;i<this.info.filters.length;i++) {
          let item = this.info.filters[i]
          if (item.id === filter_id) {
            axios.get(`http://${ip}:${port}/api/product/all`, {
              headers: {
                Authorization: 'Bearer ' + localStorage['sid']
              }
            }).
            then(resp => {
              this.info.products = resp.data.filter(i => i.category === item.category)
            })
          }
        }
      },
      updateProductsList(newValue) {
        this.info.products = newValue
      }
    },
    beforeMount() {
      if (localStorage['lang'] === 'ru-RU') {
        this.curLocale = this.locales["ru-RU"];
      } else if (localStorage['lang'] === 'en-EN') {
        this.curLocale = this.locales["en-EN"];
      } else if (localStorage['lang'] === 'ua-UA') {
        this.curLocale = this.locales["ua-UA"];
      } else {
        localStorage.setItem('lang', 'ua-UA')
        this.curLocale = this.locales["ua-UA"];
      }
    },
    mounted() {
      axios.get(`http://${ip}:${port}/api/product/all`, {
        headers: {
          Authorization: 'Bearer ' + localStorage['sid']
        }
      }).
        then(resp => {
          this.info.products = resp.data
      })
    }
  }
</script>

<style scoped>

</style>