<template>
  <v-app>
    <v-card style="margin: 15% 10% 0 10%; background-color: #FFF9C4" flat>
      <v-menu v-model="showFilters" max-width="180" offset-y v-if="selCategory === undefined">
        <template v-slot:activator="{on, attrs}">
          <v-card-title>
            {{curLocale.productInfo.title}}
            <v-btn v-on="on" v-bind="attrs" icon @click="showFilters = !showFilters">
              <v-icon>
                filter_list
              </v-icon>
            </v-btn>
          </v-card-title>
        </template>
        <v-card>
      <v-card-subtitle>
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
      </v-card-subtitle>
        </v-card>
      </v-menu>
      <v-card-title v-else>
        {{selCategory}}
      </v-card-title>
      <ProductList
          :info="info"
          @update-products="updateProductsList"
          v-model="info.products"
          :alert-unauthorized="alertUnauthorized"
          :updater="updater"
          :locale="curLocale"
      />
      <v-dialog v-model="alertUnauthorized" width="480">
        <v-card>
          <v-card-title style="justify-content: center; display: flex">
            {{curLocale.unauthorized.labels[0]}}
          </v-card-title>
          <v-divider></v-divider>
          <v-card-actions style="justify-content: center; display: flex">
            <v-btn color="deep-purple" text outlined to="/register" @click="doRegisterForProducts">
              {{curLocale.unauthorized.labels[1]}}
            </v-btn>
            <v-btn color="success" text outlined to="/auth" @click="doLoginForProducts">
              {{curLocale.unauthorized.labels[2]}}
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-card>
  </v-app>
</template>

<script>
  import ProductList from "../components/ProductList";

  const ip = 'nurecanteen'
  const port = 'pp.ua';
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
        products: [],
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
      redirTo: '',
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
              'Category:', 'Available:', '', 'Description:', 'Weight:', 'Calories:'
            ],
            filtersItems: [
                'First dish',
                'Second Dish',
                'Spices',
                'Drinks'
            ]
          },
          unauthorized: {
            labels: [
                'Log-in to the system for buy goods!',
                'Sign-Up',
                'Log-In'
            ]
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
                'Категория:', 'Доступно:', 'шт.', 'Описание:', 'Вес:', 'Ккал:'
            ],
            filtersItems: [
              'Первое блюдо',
              'Второе блюдо',
              'Пряности',
              'Напитки'
            ]
          },
          unauthorized: {
            labels: [
              'Войдите в систему чтобы купить продукты!',
              'Регистрация',
              'Войти'
            ]
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
              'Категорія:', 'Доступно:', 'шт.', 'Опис:', 'Вага:', 'Ккал:'
            ],
            filtersItems: [
              'Перша страва',
              'Друга страва',
              'Прянощі',
              'Напої'
            ]
          },
          unauthorized: {
            labels: [
              'Війдіть до системи, щоб купувати продукти!',
              'Реєстрація',
              'Війти'
            ]
          }
        }
      },
      langOne: false,
      langSecond: false,
      langThird: false,
      alertUnauthorized: false,
      selCategory: localStorage['category']
    }),
    methods: {
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
            axios.get(`https://api.${ip}.${port}/api/product/all`, {
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
    async mounted() {
      axios.get(`https://api.${ip}.${port}/api/product/all`)
          .then(resp => {
            if (localStorage['category'] !== undefined) {
              this.info.products = resp.data.filter(i => i.category === localStorage['category'])
            } else this.info.products = resp.data
          })
      for (let i = 0; i < this.info.filters.length; i++) {
        this.info.filters[i].category = this.curLocale.productInfo.filtersItems[i]
      }
    }
  }
</script>

<style scoped>

</style>