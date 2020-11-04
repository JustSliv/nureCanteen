<template>
    <v-app style="background-color: #bfe9ff">
        <div style="margin-top: 20%">
            <v-card-title style="justify-content: center">NUFOOD</v-card-title>
            <v-card-text style="text-align: center; display: block">{{ curLocale.subTextPage }}</v-card-text>
            <div style="text-align: center; display: block" class="btns" v-if="info.user_info.login">
                <v-btn to="/products" color="primary" class="v--btn">{{curLocale.btnsPage[0]}}</v-btn>
                <v-btn to="/cabinet" color="secondary" class="v--btn">{{curLocale.btnsPage[1]}}</v-btn>
                <v-btn to="/cabinet/orders" class="v--btn">{{curLocale.btnsPage[2]}}</v-btn>
            </div>
            <div style="text-align: center; display: block" class="btns" v-else>
                <v-btn to="/products" color="primary" class="v--btn">{{curLocale.btnsPage[3]}}</v-btn>
                <v-btn to="/auth" color="secondary" class="v--btn">{{curLocale.btnsPage[4]}}</v-btn>
                <v-btn to="/register" class="v--btn">{{curLocale.btnsPage[5]}}</v-btn>
            </div>
        </div>
    </v-app>
</template>

<script>
  export default {
    name: "Home",
    data() {
      return {
        info: {
          user_info: {
            login: false,
            name: "tester",
            university: ""
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
    }
  }
</script>

<style scoped>
    .btns .v--btn {
        margin: 5px;
    }
</style>