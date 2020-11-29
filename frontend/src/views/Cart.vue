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
          <v-btn color="deep-purple" dark @click="dialogChooseCanteen = true">
            {{curLocale.btnSuccess}}
            <v-icon>
              check_circle
            </v-icon>
          </v-btn>
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
    <v-dialog persistent v-model="dialogChooseCanteen" width="450">
      <v-card>
        <v-card-title>
          ss
        </v-card-title>
        <v-container>
          <v-row>
            <v-col>
              <v-select
                  v-model="info.userInfo.activeCanteen"
                  :label="curLocale.userData.form.chooseCanteen"
                  style="padding: 4%"
                  v-if="chooseCanteen"
                  :items="curLocale.userData.form.canteens"
              ></v-select>
              <v-select
                  v-model="info.userInfo.typePay"
                  :label="curLocale.userData.form.payTitle"
                  :items="curLocale.userData.form.typePay"
                  v-if="chooseCanteen"
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
<!--    <v-dialog v-model="dialogChooseCanteen" width="650" persistent>-->
<!--        <v-card>-->
<!--          <v-card-title>-->
<!--            <span class="headline">-->
<!--              {{curLocale.userData.title}}-->
<!--            </span>-->
<!--          </v-card-title>-->
<!--          <v-divider></v-divider>-->
<!--          <v-alert type="error" v-if="errBuy">-->
<!--            {{curLocale.userData.alertErr}}-->
<!--          </v-alert>-->
<!--          <v-container>-->
<!--            <v-row>-->
<!--              <v-col>-->
<!--                <v-text-field-->
<!--                    v-model="info.userInfo.fName"-->
<!--                    :rules="textRules"-->
<!--                    :label="curLocale.userData.form.labels[0]"-->
<!--                    required-->
<!--                ></v-text-field>-->
<!--              </v-col>-->
<!--              <v-col>-->
<!--                <v-text-field-->
<!--                    v-model="info.userInfo.lName"-->
<!--                    :rules="textRules"-->
<!--                    :label="curLocale.userData.form.labels[1]"-->
<!--                    required-->
<!--                ></v-text-field>-->
<!--              </v-col>-->
<!--            </v-row>-->
<!--            <v-row>-->
<!--              <v-col>-->
<!--                <v-text-field-->
<!--                    v-model="info.userInfo.group"-->
<!--                    :rules="textRules"-->
<!--                    :label="curLocale.userData.form.labels[2]"-->
<!--                    required-->
<!--                ></v-text-field>-->
<!--              </v-col>-->
<!--              <v-col>-->
<!--                <v-text-field-->
<!--                    v-model="info.userInfo.email"-->
<!--                    :rules="emailRules"-->
<!--                    :label="curLocale.userData.form.labels[3]"-->
<!--                    :hint="curLocale.userData.form.hintEmail"-->
<!--                    persistent-hint-->
<!--                ></v-text-field>-->
<!--              </v-col>-->
<!--            </v-row>-->
<!--            <v-row style="text-align: center; display: block">-->
<!--              <v-btn x-large icon @click="chooseCanteen = !chooseCanteen">-->
<!--                <v-icon x-large>-->
<!--                  keyboard_arrow_down-->
<!--                </v-icon>-->
<!--              </v-btn>-->

<!--            </v-row>-->
<!--          </v-container>-->
<!--          <v-card-actions>-->
<!--            <v-spacer></v-spacer>-->
<!--            <v-btn color="red" text @click="dialogChooseCanteen = false">-->
<!--              {{curLocale.userData.form.btns[0]}}-->
<!--            </v-btn>-->
<!--            <v-btn color="blue" text @click="getReceipt">-->
<!--              {{curLocale.userData.form.btns[1]}}-->
<!--            </v-btn>-->
<!--          </v-card-actions>-->
<!--        </v-card>-->
<!--      </v-dialog>-->
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
  const ip = 'localhost'
  const port = 25016;
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
        axios.get(`http://${ip}:${port}/api/user`,{
          headers: {
            Authorization: 'Bearer ' + localStorage['sid']
          }
        }).then(resp => {
          axios.get(`http://${ip}:${port}/api/basket/user/` + resp.data.id)
              .then(cart => {
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
          summa += item.price
        }
        axios({
          method: 'GET',
          url: `http://${ip}:${port}/api/user`,
          headers: {
            Authorization: 'Bearer ' + localStorage['sid']
          }
        }).then(user => {
          axios({
            method: 'POST',
            url: `http://${ip}:${port}/api/check/`,
            data: {
              user_id: user.data.id,
              canteen: this.info.userInfo.activeCanteen,
              purchaseDate: now.toISOString().split('T')[0],
              sum: summa
            }
          }).then(resp => {
            localStorage['receipt'] = JSON.stringify(resp.data)
            this.$router.push('/receipt');
          }).catch(() => {

          })
        })
      },
      getReceipt() {
          let date = this.getNowDate()
          let codeReceipt = "";
          let data;
          for(let i=0;i<6;i++){
              codeReceipt += Math.floor(Math.random()*10).toString();
          }
          data = {
            code: codeReceipt,
            fname: this.info.userInfo.fname,
            lName: this.info.userInfo.lName,
            email: this.info.userInfo.email,
            group: this.info.userInfo.group,
            cart: this.cartItems,
            purchaseDate: date[0]+"."+date[1]+"."+date[2]
          };
          this.dialogChooseCanteen = false;
          localStorage.removeItem('cart')
          axios.post(`http://${ip}:${port}/api/checks`, {

          }).then(resp => {
            console.log(resp)
          })
          localStorage['receipt'] = JSON.stringify(data)
          this.$router.push('/receipt');
        // else {
        //   this.chooseCanteen = true
        //   this.errBuy = true;
        // }
      }
    },
    computed: {
      totalPrice() {
        let total = 0;
        const items = this.cartItems;
        for (let i=0; i<items.length; i++){
            total += items[i].price;
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