<template>
  <v-app v-if="existsItems" style="padding-left: 2%">
    <v-card-title style="margin-top: 8%; padding-left: 25%">
    <v-badge
            :content="cartItems.length"
    >
      {{curLocale.titlePage}}
    </v-badge>
      <v-btn style="margin-left: 42%" @click="acceptOrder">
        {{curLocale.btnSuccess}}
        <v-icon style="margin-left: 2%">
          check_circle
        </v-icon>
      </v-btn>
    </v-card-title>
    <v-list style="margin: 0 25% 0 25%" v-for="(item, i) in cartItems" :key="i">
      <v-card>
        <v-list-group>
          <template v-slot:activator>
            <v-list-item-title>{{item.name}}</v-list-item-title>
            <v-item-group>
                <v-list-item-title>{{curLocale.infoCart[0]}} {{item.price}} {{curLocale.infoCart[1]}}</v-list-item-title>
            </v-item-group>
          </template>
          <v-list-item-group style="margin-left: 5%">
            <v-list-item-content>
              <v-list-item-title>{{curLocale.infoCart[2]}} {{item.description}}</v-list-item-title>
              <v-list-item-subtitle>{{curLocale.infoCart[3]}} {{item.category}}</v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item-group>
      </v-list-group>
      <v-btn icon @click="delCartItem" absolute style="margin-top: -5.2%; margin-left: 100%">
        <v-icon :data-id="item.id">
            cancel
        </v-icon>
        </v-btn>
      </v-card>
    </v-list>
      <v-card-title style="margin-top: 0; padding-left: 25%">
          {{curLocale.totalPay[0]}} <span style="margin-left: 46.5%">{{totalPrice}} {{curLocale.totalPay[1]}}</span>
    </v-card-title>
      <v-dialog v-model="buyingItems" width="650" persistent>
        <v-card>
          <v-card-title>
            <span class="headline">
              {{curLocale.userData.title}}
            </span>
          </v-card-title>
          <v-divider></v-divider>
          <v-alert type="error" v-if="errBuy">
            {{curLocale.userData.alertErr}}
          </v-alert>
          <v-container>
            <v-row>
              <v-col>
                <v-text-field
                    v-model="info.userInfo.fName"
                    :rules="textRules"
                    :label="curLocale.userData.form.labels[0]"
                    required
                ></v-text-field>
              </v-col>
              <v-col>
                <v-text-field
                    v-model="info.userInfo.lName"
                    :rules="textRules"
                    :label="curLocale.userData.form.labels[1]"
                    required
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <v-text-field
                    v-model="info.userInfo.group"
                    :rules="textRules"
                    :label="curLocale.userData.form.labels[2]"
                    required
                ></v-text-field>
              </v-col>
              <v-col>
                <v-text-field
                    v-model="info.userInfo.email"
                    :rules="emailRules"
                    :label="curLocale.userData.form.labels[3]"
                    :hint="curLocale.userData.form.hintEmail"
                    persistent-hint
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row style="text-align: center; display: block">
              <v-btn x-large icon @click="chooseCanteen = !chooseCanteen">
                <v-icon x-large>
                  keyboard_arrow_down
                </v-icon>
              </v-btn>
              <v-col>
                <v-select
                    v-model="info.userInfo.activeCanteen"
                    :label="curLocale.userData.form.chooseCanteen"
                    style="padding: 4%"
                    v-if="chooseCanteen"
                    :items="['dsa']"
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
            <v-btn color="red" text @click="buyingItems = false">
              {{curLocale.userData.form.btns[0]}}
            </v-btn>
            <v-btn color="blue" text @click="getReceipt">
              {{curLocale.userData.form.btns[1]}}
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
  </v-app>
  <v-app v-else>
    <v-card style="margin: 20%;background-color: #bfe9ff" flat>
      <v-icon style="text-align: center; display: block" x-large>not_interested</v-icon>
      <v-card-title style="justify-content: center">
        {{curLocale.notFound}}
      </v-card-title>
    </v-card>
  </v-app>
</template>

<script>
  export default {
    name: "Cart",
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
        cartItems: localStorage['cart'] === undefined?[]:JSON.parse(localStorage['cart']),
        buyingItems: false,
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
    methods: {
      getNowDate() {
        let now = new Date();
        let day = now.getDate();
        if (day < 10) day = "0"+now.getDate().toString();
        let month = now.getMonth();
        if (month < 10) month = "0"+now.getMonth().toString();
        let year = now.getFullYear();
        return [day, month, year];
      },
      delCartItem(ev) {
        // this.cart_counter--;
        // localStorage.count_cart = this.cart_counter;
        let id = parseInt(ev.path[0].attributes[2].value);
        this.cartItems = this.cartItems.filter(function (item) {
            return item.id !== id
        });
        localStorage['cart'] = JSON.stringify(this.cartItems);
      },
      acceptOrder() {
        let isAuth = false
        if (isAuth) {
          // sending GET to users
          // put data in userInfo

          this.getReceipt()
        } else {
          this.buyingItems = true;
        }
      },
      doValidateForm() {
        return (this.info.userInfo.fname &&
            this.info.userInfo.lName &&
            this.info.userInfo.group &&
            this.info.userInfo.activeCanteen) !== ''
      },
      getReceipt() {
        if (this.doValidateForm()) {
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
          this.buyingItems = false;
          localStorage.removeItem('cart')
          localStorage['receipt'] = JSON.stringify(data)
          this.$router.push('/receipt');
        }
        else {
          this.chooseCanteen = true
          this.errBuy = true;
        }
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