<template>
  <v-app v-if="existsItems">
    <v-card-title style="margin-top: 8%; padding-left: 25%">
    <v-container>
      <v-row>
        <v-col>
          <v-badge
              :content="cartItems.length"
          >
            {{curLocale.titlePage}}
          </v-badge>
        </v-col>
        <v-col>
          <v-dialog persistent v-model="dialogChooseCanteen" width="450">
            <template v-slot:activator="{on, attrs}">
              <v-btn color="deep-purple" dark v-bind="attrs" v-on="on">
                {{curLocale.btnSuccess}}
                <v-icon>
                  check_circle
                </v-icon>
              </v-btn>
            </template>
            <v-card>
              <v-card-title style="justify-content: center; display: flex">
                {{curLocale.userData.form.titleConfirmOrder}}
              </v-card-title>
              <v-divider></v-divider>
              <v-container>
                <v-row>
                  <v-col>
                    <v-select
                        v-model="info.userInfo.activeCanteen"
                        :label="curLocale.userData.form.chooseCanteen"
                        :items="curLocale.userData.form.canteens"
                    ></v-select>
                    <v-select
                        v-model="info.userInfo.typePay"
                        :label="curLocale.userData.form.payTitle"
                        :items="curLocale.userData.form.typePay"
                        v-if="info.userInfo.activeCanteen"
                    ></v-select>
                  </v-col>
                </v-row>
              </v-container>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="red" text @click="dialogChooseCanteen = false">
                  {{curLocale.userData.form.btns[0]}}
                </v-btn>
                <v-btn color="blue" text @click="acceptOrder">
                  {{curLocale.userData.form.btns[1]}}
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-col>
      </v-row>
    </v-container>
    </v-card-title>
    <CartItemList
        style="background-color: #FFF9C4"
        :items="cartItems"
        :locales="curLocale"
        :all-cart="cartItems"
        :updater="updateItems"
    />
    <v-card-title style="margin-top: 0; padding-left: 25%">
      {{curLocale.totalPay[0]}} <span style="margin-left: 46.5%">{{totalPrice}} {{curLocale.totalPay[1]}}</span>
    </v-card-title>
  </v-app>
  <v-app v-else>
    <v-card style="margin: 20%;background-color: #FFF9C4" flat>
      <v-icon style="text-align: center; display: block" x-large>not_interested</v-icon>
      <v-card-title style="justify-content: center">
        {{curLocale.notFound}}
      </v-card-title>
    </v-card>
  </v-app>
</template>

<script>
  import CartItemList from "@/components/CartItemList";
  const ip = 'nurecanteen'
  const port = 'pp.ua';
  const axios = require('axios')

  export default {
    name: "Cart",
    components: {CartItemList},
    data() {
      return {
        curLocale: {},
        locales: {
          'en-EN': {
            titlePage: 'Cart',
            btnSuccess: 'Confirm the order',
            infoCart: [
                'Price:', 'UAH', 'Description:', 'Category:'
            ],
            totalPay: [
              'Total to pay', 'UAH'
            ],
            userData: {
              title: 'Your personal info',
              alertErr: 'Check all fields',
              form: {
                labels: [
                  'First Name*',
                  'Second Name*',
                  'Group*',
                  'Email'
                ],
                titleConfirmOrder: 'Choose canteen with payment',
                canteens: ['First - the best', 'Second, but better'],
                hintEmail: 'For stocks and other/',
                chooseCanteen: 'Choose a canteen:',
                payTitle: 'Payment',
                typePay: [
                    'Credit Card',
                    'On the checkout'
                ],
                textRules: [
                    'Field cannot be empty!',
                    'Fill this field!'
                ],
                emailRule: "Input correct e-mail!",
                btns: [
                    'Cancel',
                    'Confirm'
                ]
              }
            },
            notFound: 'Cart is empty'
          },
          'ru-RU': {
            titlePage: 'Корзина',
            btnSuccess: 'Оформить заказ',
            infoCart: [
              'Цена:', 'ГРН', 'Описание:', 'Категория:'
            ],
            totalPay: [
              'Всего к оплате', 'ГРН'
            ],
            userData: {
              title: 'Ваши данные',
              alertErr: 'Проверьте все поля',
              form: {
                labels: [
                  'Имя*',
                  'Фамилия*',
                  'Группа*',
                  'Email'
                ],
                titleConfirmOrder: 'Выберите столовую и оплату',
                canteens: ['Первая среди лучших', 'Вторая, но хорошая'],
                hintEmail: 'Для рассылки акций',
                chooseCanteen: 'Выберите столовую:',
                payTitle: 'Оплата',
                typePay: [
                  'Кредитной картой',
                  'На кассе'
                ],
                textRules: [
                  'Поле не может быть пустым',
                  'Заполните поле!'
                ],
                emailRule: "Введите правильный e-mail!",
                btns: [
                  'Отменить',
                  'Подтвердить'
                ]
              }
            },
            notFound: 'Корзина пуста'
          },
          'ua-UA': {
            titlePage: 'Кошук',
            btnSuccess: 'Оформити замовлення',
            infoCart: [
              'Ціна:', 'ГРН', 'Опис:', 'Категорія:'
            ],
            totalPay: [
              'Усього до сплати', 'ГРН'
            ],
            userData: {
              title: 'Ваші данні',
              alertErr: 'Перевірте усі поля',
              form: {
                labels: [
                  "Ім'я*",
                  'Прізвище*',
                  'Група*',
                  'Email'
                ],
                titleConfirmOrder: 'Виберіть їдальню та сплату',
                canteens: ['Перша серед кращих', 'Друга, но теж добре'],
                hintEmail: 'Для розсилки акцій',
                chooseCanteen: 'Виберіть їдальню:',
                payTitle: 'Оплата',
                typePay: [
                  'Кредитна карта',
                  'На касі'
                ],
                textRules: [
                  'Поле не може бути пустим!',
                  'Заповніть це поле!'
                ],
                emailRule: "Напишіть правильний e-mail!",
                btns: [
                  'Відміна',
                  'Підтвердити'
                ]
              }
            },
            notFound: 'Кошук порожній'
          }
        },
        info: {
          userInfo: {
            fName: 'Tim',
            lName: 'Livr',
            group: 'PZPI',
            email: 'tes@mal.com',
            typePay: '',
            activeCanteen: ''
          }
        },
        cartItems: [],
        dialogChooseCanteen: false,
        errBuy: false,
        chooseCanteen: false,
        textRules: [
            v => !!v || this.curLocale.userData.form.textRules[0],
            v => v.length !== 0 || this.curLocale.userData.form.textRules[1]
        ],
        emailRules: [
            v => (v.match("[a-zA-Z]+@[a-zA-Z]+[.][a-zA-Z]+")!==null?v.match("[a-zA-Z]+@[a-zA-Z]+[.][a-zA-Z]+")[0]:null) !== null || this.curLocale.userData.form.emailRule
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
      if (localStorage['sid'] !== undefined) {
        axios.get(`https://api.${ip}.${port}/api/user`,{
          headers: {
            Authorization: 'Bearer ' + localStorage['sid']
          }
        }).then(resp => {
          axios.get(`https://api.${ip}.${port}/api/basket/user/` + resp.data.id, {
            headers: {
              Authorization: 'Bearer ' + localStorage['sid']
            }
          }).then(cart => {
            this.cartItems = cart.data
          })
        })
      } else {
        this.cartItems = []
      }
    },
    methods: {
      updateItems(info) {
        this.cartItems = info.items
      },
      getNowDate() {
        let now = new Date();
        let day = now.getDate();
        if (day < 10) day = "0"+now.getDate().toString();
        let month = now.getMonth();
        if (month < 10) month = "0"+now.getMonth().toString();
        let year = now.getFullYear();
        return [day, month, year];
      },
      acceptOrder() {
        let now = new Date();
        let summa = 0
        for (let item of this.cartItems) {
          summa += (item.price*item.count)
        }
        axios({
          method: 'GET',
          url: `https://api.${ip}.${port}/api/user`,
          headers: {
            Authorization: 'Bearer ' + localStorage['sid']
          }
        }).then(user => {
          axios({
            method: 'POST',
            url: `https://api.${ip}.${port}/api/check/`,
            data: {
              user_id: user.data.id,
              canteen: this.info.userInfo.activeCanteen,
              purchaseDate: now.toISOString().split('T')[0],
              sum: summa
            },
            headers: {
              Authorization: 'Bearer ' + localStorage['sid']
            }
          }).then(resp => {
            if (this.info.userInfo.typePay === this.curLocale.userData.form.typePay[0]) {
              axios({
                method: 'POST',
                url: `https://api.${ip}.${port}/api/pay`,
                data: {
                  action: 'pay',
                  amount: summa,
                  currency: 'UAH',
                  description: 'Оплата: ' + this.cartItems.map(i => i.name).join(' - '),
                  order_id: resp.data.check_id
                },
                headers: {
                  Authorization: 'Bearer ' + localStorage['sid']
                }
              }).then(payPage => {
                let rawHtml = payPage.data
                let htmlParser = new DOMParser().parseFromString(rawHtml, "text/html")
                let linkPay = 'https://www.liqpay.ua/api/3/checkout'
                let data = htmlParser.body.getElementsByTagName("form")[0].getElementsByTagName("input")[0].attributes[2].value
                let signature = htmlParser.body.getElementsByTagName("form")[0].getElementsByTagName("input")[1].attributes[2].value
                axios({
                  method: 'POST',
                  url: linkPay,
                  data: {
                    data: data,
                    signature: signature
                  }
                }).then(pay => {
                  console.log('pay', pay.data)
                })
              }).catch(e => {
                console.error('errr pay', e)
              })
            }
            axios({
              method: 'PUT',
              url: `https://api.${ip}.${port}/api/check/`+resp.data.check_id,
              headers: {
                Authorization: 'Bearer ' + localStorage['sid']
              }})
            axios({
              method: 'DELETE',
              url: `https://api.${ip}.${port}/api/basket/user/`+user.data.id+'/'+resp.data.check_id,
              headers: {
                Authorization: 'Bearer ' + localStorage['sid']
              }
            })
            // this.$router.push('/receipt');
          }).catch(() => {

          })
        })
      }
    },
    computed: {
      totalPrice() {
        let total = 0;
        const items = this.cartItems;
        for (let i=0; i<items.length; i++){
            total += (items[i].price*items[i].count);
        }
        return total;
      },
      existsItems() {
        try {
          return this.cartItems.length > 0;
        } catch (err) {
          return false
        }
      }
    }
  }
</script>

<style scoped>

</style>