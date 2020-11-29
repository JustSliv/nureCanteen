<template>
  <v-app>
    <v-card style="margin: 8% 30% 0 30%">
      <v-card-title>{{info.product.name}}</v-card-title>
      <v-card-subtitle>{{info.product.category}}</v-card-subtitle>
      <v-divider></v-divider>
        <v-container>
          <v-row>
            <v-col>
              <v-img :src="info.product.image" width="280" height="280"></v-img>
            </v-col>
            <v-col>
              <v-row>
                <v-col>
                  <b>{{curLocale.info[0]}}</b>
                  {{info.product.price}} {{curLocale.info[1]}}
                </v-col>
              </v-row>
              <v-row>
                <v-col>
                  <b>{{curLocale.info[2]}}</b>
                  {{info.product.description}}
                </v-col>
              </v-row>
              <v-row>
                <v-col>
                  <b>{{curLocale.info[3]}}</b>
                  <v-text-field
                      type="number"
                      :rules="numRules"
                      outlined
                  ></v-text-field>
                </v-col>
              </v-row>
            </v-col>
            <v-col></v-col>
          </v-row>
        </v-container>
        <v-divider></v-divider>
        <v-card-title>
          {{curLocale.comment.title}}
          <v-btn :title="curLocale.comment.commentTip" @click="addReview" icon>
            <v-icon>
              add_circle_outline
            </v-icon>
          </v-btn>
        </v-card-title>
        <v-dialog v-model="newReview" max-width="550">
          <v-card>
            <v-card-title>{{curLocale.comment.addCmnTitle}} {{info.product.name}}</v-card-title>
            <v-divider></v-divider>
            <form>
              <v-container>
                <v-row>
                  <v-col cols="12">
                    <v-text-field
                        :label="curLocale.comment.labelText[0]"
                        required
                        outlined
                        v-model="fullname"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12">
                    <v-text-field
                        :label="curLocale.comment.labelText[1]"
                        v-model="contentReview"
                        :rules="textRules"
                        required
                        outlined
                        counter="250"
                    ></v-text-field>
                  </v-col>
                </v-row>
              </v-container>
              <v-btn width="100%" color="success" text>{{curLocale.comment.btnTitle}}</v-btn>
            </form>
          </v-card>
        </v-dialog>
        <v-divider></v-divider>
        <v-card v-for="(item, i) in info.comments" :key="i" outlined rounded>
          <v-card-title>
            {{item.user}}
            <v-spacer></v-spacer>
            <v-card-subtitle>
              {{item.date}}
            </v-card-subtitle>
          </v-card-title>
          <v-card-text style="margin-left: 5%">
            {{item.msg}}
          </v-card-text>
        </v-card>
        <div v-if="info.comments.length <= 0">
          <v-card-title>{{curLocale.comment.notFound}}</v-card-title>
        </div>
        <v-btn
            color="success"
            elevation="6"
            rounded
            icon
            outlined
            x-large
            absolute
            top
            right
            :title="curLocale.toCart"
            @click="doOrder"
        >
          <v-icon>
            add_shopping_cart
          </v-icon>
        </v-btn>
      <v-dialog v-model="dialog" max-width="320" v-if="dialog">
        <v-card>
          <v-card-title class="headline">
            {{product.name}}
          </v-card-title>
          <v-card-text>
            {{curLocale.productInfo.toCart.tip}}
          </v-card-text>
          <v-container>
            <v-row>
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
        {{curLocale.productInfo}}
        {{curLocale.productInfo.toCart.alerts[1]}}
        <v-btn text to="/cart">{{curLocale.productInfo.toCart.alertBtn}}</v-btn>
      </v-snackbar>
    </v-card>
  </v-app>
</template>

<script>
const ip = 'localhost'
const port = 25016;
const axios = require('axios')

export default {
  name: "Product",
  data() {
    return {
      dialog: false,
      duplicate: false,
      inCart: false,
      countProducts: 0,
      newReview: false,
      contentReview: "",
      fullname: '',
      curLocale: {},
      locales: {
        'en-EN': {
          info: [
            'Price:', 'UAH.', 'Description:', 'Amount:'
          ],
          comment: {
            title: 'Reviews',
            commentTip: 'Add comment',
            addCmnTitle: 'Add reviews to',
            labelText: [
              'Fullname:',
              'Input a review'
            ],
            textRules: 'Field cannot be empty!',
            numRules: 'Amount cannot be less than 0',
            btnTitle: 'Ready',
            notFound: 'Reviews not found'
          },
          toCart: 'To cart'
        },
        'ru-RU': {
          info: [
            'Цена:', 'ГРН.', 'Описание:', 'Кол-во, шт:'
          ],
          comment: {
            title: 'Отзывы',
            commentTip: 'Добавить отзыв',
            addCmnTitle: 'Добавить отзыв для',
            labelText: [
              'Ваше ФИО:',
              'Введите отзыв'
            ],
            textRules: 'Поле не может быть пустым',
            numRules: 'Кол-во должно быть больше 0',
            btnTitle: 'Завершить',
            notFound: 'Отзывы отсутствуют'
          },
          toCart: 'В корзину'
        },
        'ua-UA': {
          info: [
            'Ціна:', 'ГРН.', 'Опис:', 'Кількість, шт:'
          ],
          comment: {
            title: 'Відгуки',
            commentTip: 'Додати відгук',
            addCmnTitle: 'Додати відгук до',
            labelText: [
              'Ваше ПІБ',
              'Напишіть відгук'
            ],
            textRules: 'Поле не може бути пустим!',
            numRules: 'Кількість не може бути менш 0',
            btnTitle: 'Завершити',
            notFound: 'Відгуки відстутні'
          },
          toCart: 'До кошуку'
        }
      },
      info: {
        product: {},
        comments: []
      },
      textRules: [
          v => v.length > 0 || this.curLocale.comment.textRules
      ],
      numRules: [
          v => v > 0 || this.curLocale.comment.numRules
      ]
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
    let curProduct = this.$route.path.split('/')
    console.log(curProduct)
    axios.get(`http://${ip}:${port}/api/product/`+curProduct[curProduct.length-1]).
      then(resp => {
        this.info.product = resp.data
        axios.get(`http://${ip}:${port}/api/product/comments/`+curProduct[curProduct.length-1]).
        then(cmts => {
          this.info.comments = cmts.data
        })
      })
  },
  methods: {
    // обработка кнопки в корзину
    buyProduct() {
      this.$nextTick(() => {
        this.dialog = true;
      });
    },
    // подтверждение нажатия на кнопку в корзину
    toCart() {
      this.dialog = false;
      if (localStorage['sid'] !== undefined || localStorage['sid'] !== null && this.countProducts > 0) {
        let id = parseInt(this.$route.path.split('/')[2])
        axios.get(`http://${ip}:${port}/api/user`, {
          headers: {
            Authorization: 'Bearer ' + localStorage['sid']
          }
        }).then(resp => {
          axios({
            method: 'POST',
            url: `http://${ip}:${port}/api/basket`,
            data: {
              product_id: id,
              user: resp.data.id,
              count: this.countProducts
            }
          }).then(cart => {
            localStorage['cart'] = JSON.stringify(cart.data)
          })
        })
      } else if (localStorage['sid'] === undefined || localStorage['sid'] === null && this.countProducts > 0) {
        let info = localStorage['cart'] === undefined?[]:localStorage['cart']
        if (info.length >= 10) info = JSON.parse(info)
        info.push(
            {
              id: this.product.id,
              name: this.product.name,
              category: this.product.category,
              description: this.product.description,
              price: this.product.price
            }
        )
        localStorage['cart'] = JSON.stringify(info)
      }
      this.$nextTick(() => {
        this.inCart = true;
      });
    },
    doOrder() {

    },
    addReview() {
      this.newReview = true;
    }
  }
}
</script>

<style scoped>

</style>