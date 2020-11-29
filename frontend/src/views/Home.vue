<template>
  <v-app>
    <v-card flat style="margin-top: 15%; background-color: #FFF9C4">
      <v-card-title style="justify-content: center" class="display-4 deep-purple--text">NUFOOD</v-card-title>
      <v-card-text style="text-align: center; display: block;" class="display-3 purple--text">
        {{ curLocale.subTextPage }}
      </v-card-text>
      <v-container style="text-align: center; display: block" v-if="info.user_info.login">
        <v-row>
          <v-col cols="12">
            <v-btn elevation="9" rounded dark x-large to="/products" color="purple">{{curLocale.btnsPage[0]}}</v-btn>
          </v-col>
          <v-col cols="12">
            <v-btn elevation="9" rounded dark x-large to="/cabinet" color="deep-purple">{{curLocale.btnsPage[1]}}</v-btn>
          </v-col>
        </v-row>
      </v-container>
      <v-container style="text-align: center; display: block" v-else>
        <v-row>
          <v-col cols="12">
            <v-btn elevation="10" rounded dark x-large to="/auth" color="deep-purple">
              {{curLocale.btnsPage[4]}}
            </v-btn>
          </v-col>
          <v-col cols="12">
            <v-btn elevation="10" rounded dark x-large to="/products" color="purple" >
              {{curLocale.btnsPage[3]}}
            </v-btn>
          </v-col>
          <v-col cols="12">
            <v-btn elevation="10" rounded dark x-large to="/register" color="indigo">
              {{curLocale.btnsPage[5]}}
            </v-btn>
          </v-col>
        </v-row>
      </v-container>
    </v-card>
  </v-app>
</template>

<script>
const ip = 'localhost'
const port = 25016;
const axios = require('axios')

  export default {
    name: "Home",
    data() {
      return {
        info: {
          user_info: {
            login: false
          }
        },
        translate: false,
        curLocale: {},
        locales: {
          'en-EN': {
            subTextPage: 'Canteen right on the lesson!',
            btnsPage: [
                'Products', 'Personal Area', 'My orders',
                'Products', 'Login', 'Sign-up'
            ]
          },
          'ru-RU': {
            subTextPage: 'Столовая прямо на паре!',
            btnsPage: [
              'Продукты', 'Личный кабинет', 'Мои заказы',
              'Продукты', 'Войти', 'Регистрация'
            ]
          },
          'ua-UA': {
            subTextPage: 'Їдальня одразу на парі!',
            btnsPage: [
              'Продукти', 'Особистий кабінет', 'Мої замовлення',
              'Продукти', 'Увійти', 'Реєстрація'
            ]
          }
        },
        langOne: false,
        langSecond: false,
        langThird: false
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
     try {
       axios.get(`http://${ip}:${port}/api/user`, {
         headers: {
           Authorization: 'Bearer ' + localStorage['sid']
         }
       }).then(() => {
         this.info.user_info.login = true
       }).catch(() => {
         this.info.user_info.login = false
       })
     } catch {
       this.info.user_info.login = false
       console.log()
     }
    }
  }
</script>

<style scoped>

</style>