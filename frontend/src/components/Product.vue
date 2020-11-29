<template>
  <v-card max-width="220px" :data-id="product.id">
    <v-menu transition="fab-transition" offset-y v-model="infoProduct">
      <template v-slot:activator="{on, attrs}">
        <div style="justify-content: center; display: flex">
          <v-btn
              icon
              x-large
              title="Описание товара"
              v-bind="attrs"
              v-on="on"
              v-if="!activeInfoProduct"
              @click="activeInfoProduct = true"
          >
            <v-icon x-large>
              keyboard_arrow_down
            </v-icon>
          </v-btn>
          <v-btn
              icon
              x-large
              v-bind="attrs"
              v-on="on"
              v-if="activeInfoProduct"
              @click="activeInfoProduct = false"
              @mouseleave="reRenderInfoBtn"
          >
            <v-icon x-large>
              keyboard_arrow_up
            </v-icon>
          </v-btn>
        </div>
        <div>
          <v-img :src="product.image" :alt="product.name"></v-img>
          <v-card-subtitle>
            <router-link class="text-decoration-none" :to="'/product/'+product.product_id">
              {{product.name}}
            </router-link> <br/>
            {{product.price}} {{curLocale.productInfo.currency}}
          </v-card-subtitle>
          <v-btn @click="showBuyDialog" color="success" width="100%">{{curLocale.productInfo.toCart.toCartTitle}}</v-btn>
        </div>
      </template>
      <v-card>
        <v-card-text>
          <b>{{curLocale.productInfo.info[0]}}</b> {{product.category}} <br/>
          <b>{{curLocale.productInfo.info[1]}}</b> {{ product.available_count }} {{curLocale.productInfo.info[2]}} <br/>
          <b>{{curLocale.productInfo.info[3]}}</b> {{ product.description }} <br/>
        </v-card-text>
      </v-card>
    </v-menu>
    <v-dialog v-model="dialog" max-width="320" v-if="dialog">
      <v-card>
        <v-card-title class="headline">
          {{product.name}}
        </v-card-title>
        <v-card-text>
          {{curLocale.productInfo.toCart.tip}}
        </v-card-text>
        <v-container>
          <v-row no-gutters>
            <v-col>
              <v-text-field
                  label="Кол-во продукта:"
                  outlined
                  type="number"
                  v-model="countProducts"
              ></v-text-field>
            </v-col>
          </v-row>
        </v-container>
        <v-divider></v-divider>
        <v-card-actions>
          <v-btn
              color="red"
              text
              @click="dialog = false"
          >
            {{curLocale.productInfo.toCart.btns[0]}}
          </v-btn>
          <v-btn
              color="green"
              text
              @click="toCart"
          >
            {{curLocale.productInfo.toCart.btns[1]}}
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-snackbar
        top
        v-model="duplicate"
        v-if="duplicate"
        multi-line
        timeout="2000"
        color="success"
    >
      {{curLocale.productInfo.toCart.alerts[0]}}
    </v-snackbar>
    <v-snackbar
        top
        v-model="inCart"
        v-if="inCart"
        multi-line
        timeout="2000"
        color="success"
    >
      {{curLocale.productInfo.toCart.alerts[1]}}
      <v-btn text to="/cart">{{curLocale.productInfo.toCart.alertBtn}}</v-btn>
    </v-snackbar>
  </v-card>
</template>

<script>
const ip = 'localhost'
const port = 25016;
const axios = require('axios')

export default {
  name: "Product",
  props: ['product', 'alertUnauthorized', 'updater'],
  data() {
    return {
      curLocale: null,
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
      dialog: false,
      duplicate: false,
      inCart: false,
      infoProduct: false,
      activeInfoProduct: false,
      countProducts: 1
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
  methods: {
    reRenderInfoBtn() {
      this.activeInfoProduct = false;
      this.infoProduct = false;
    },
    showBuyDialog(){
      if (localStorage['sid'] !== undefined && this.countProducts > 0) {
        this.dialog = true;
      }
      else if (localStorage['sid'] === undefined) {
        this.updater({
          value: true
        })
      }
    },
    // подтверждение нажатия на кнопку в корзину
    toCart() {
      this.dialog = false;
      axios.get(`http://${ip}:${port}/api/user`, {
        headers: {
          Authorization: 'Bearer ' + localStorage['sid']
        }
      }).then(resp => {
        axios({
          method: 'POST',
          url: `http://${ip}:${port}/api/basket/`,
          data: {
            product_id: this.product.product_id,
            user: resp.data.id,
            count: this.countProducts
          }
        }).then(() => {
          this.inCart = true;
          // if (localStorage['cart'] !== undefined) {
          //   if (JSON.parse(localStorage['cart']).length > 0) {
          //     localStorage['cart'] = JSON.parse(localStorage['cart']).push(cart.data)
          //   }
          // } else {
          //   let info = []
          //   info.push(cart.data)
          //   localStorage['cart'] = JSON.stringify(info)
          // }
        })
      })
    }
  }
}
</script>

<style scoped>

</style>