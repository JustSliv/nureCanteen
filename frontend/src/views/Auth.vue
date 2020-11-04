<template>
  <v-app>
    <v-card style="margin: 15% 35% 0 35%">
      <v-card-title style="text-align: center; display: block">{{ curLocale.authTitle }}</v-card-title>
      <v-divider></v-divider>
      <v-form v-model="valid">
        <v-container>
          <v-row>
            <v-col>
              <v-text-field
                  :label="curLocale.formLabels[0]"
                  v-model="login"
                  :rules="loginRules"
                  outlined
                  required
              ></v-text-field>
            </v-col>
          </v-row>
          <v-row>
            <v-col>
              <v-text-field
                  :label="curLocale.formLabels[1]"
                  v-model="pwd"
                  :rules="pwdRules"
                  outlined
                  required
              ></v-text-field>
            </v-col>
          </v-row>
          <router-link class="text-decoration-none" to="restoreAccount" style="font-size: 10pt">
            {{ curLocale.remindPwd }}
          </router-link>
          <v-btn color="success" block @click="doAuth">
            {{ curLocale.btnTitle }}
          </v-btn>
        </v-container>
      </v-form>
      <v-dialog v-model="load" hide-overlay width="220">
        <v-card color="primary">
          <v-card-text style="color: white;">
            {{curLocale.alertTitle}}
            <v-progress-linear
                    indeterminate
                    color="white"
            ></v-progress-linear>
          </v-card-text>
        </v-card>
      </v-dialog>
    </v-card>
  </v-app>
</template>

<script>
  export default {
    name: "Auth",
    data() {
      return {
        curLocale: {},
        locales: {
          'en-EN': {
            authTitle: 'Authorization',
            formLabels: [
              'Login', 'Password'
            ],
            remindPwd: 'Recovery account?',
            alertTitle: 'Loading...',
            btnTitle: 'Log-in',
            loginRules: [
              'Login is required',
              'Login field cannot be empty'
            ],
            pwdRules: [
              'Password is required',
              'Password field cannot be empty'
            ],
          },
          'ru-RU': {
            authTitle: 'Авторизация',
            formLabels: [
                'Логин', 'Пароль'
            ],
            remindPwd: 'Забыли пароль?',
            alertTitle: 'Загрузка...',
            btnTitle: 'Войти',
            loginRules: [
              'Логин обязателен',
              'Логин не может быть пуст'
            ],
            pwdRules: [
              'Пароль обязателен',
              'Пароль не может быть пуст'
            ],
          },
          'ua-UA': {
            authTitle: 'Авторизація',
            formLabels: [
              'Логін', 'Пароль'
            ],
            remindPwd: 'Забули пароль?',
            alertTitle: 'Завантаження...',
            btnTitle: 'Війти',
            loginRules: [
              'Логін важливий',
              'Поле логіна не може бути пустим'
            ],
            pwdRules: [
              'Пароль важливий',
              'Пароль логіна не може бути пустим'
            ],
          }
        },
        valid: false,
        login: "",
        pwd: "",
        loginRules: [
          v => !!v || this.curLocale.loginRules[0],
          v => v.length !== 0 || this.curLocale.loginRules[1]
        ],
        pwdRules: [
          v => !!v || this.curLocale.pwdRules[0],
          v => v.length !== 0 || this.curLocale.pwdRules[1]
        ],
        load: false
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
      doAuth() {
        this.load = true;
        // sending POST
      }
    }
  }
</script>

<style scoped>

</style>