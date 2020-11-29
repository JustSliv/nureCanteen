<template>
  <v-app>
    <v-card style="margin: 15% 35% 0 35%" v-if="reachAuth">
      <v-card-title style="text-align: center; display: block">{{ curLocale.authTitle }}</v-card-title>
      <v-divider></v-divider>
      <v-form v-model="valid">
        <v-container>
          <v-row>
            <v-col>
              <v-text-field
                  :label="curLocale.formLabels[0]"
                  v-model="login"
                  prepend-inner-icon="person"
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
                  prepend-inner-icon="security"
                  :rules="pwdRules"
                  type="password"
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
      <v-snackbar
          outlined top
          timeout="2500"
          color="success"
          v-model="alertSuccess"
      >
        Успешная авторизация!
        <template v-slot:action="{attrs}">
          <v-btn icon outlined v-bind="attrs" @click="alertSuccess = false">
            <v-icon>
              close
            </v-icon>
          </v-btn>
        </template>
      </v-snackbar>
      <v-snackbar
          outlined top
          timeout="2500"
          color="red"
          v-model="alertErr"
      >
        Ошибка авторизации!
        <template v-slot:action="{attrs}">
          <v-btn icon outlined v-bind="attrs" @click="alertErr = false">
            <v-icon>
              close
            </v-icon>
          </v-btn>
        </template>
      </v-snackbar>
    </v-card>
    <v-card v-else style="margin: 15% 35% 0 35%">
      <div style="margin: 10%">
        <v-icon style="text-align: center;display: block">
          warning
        </v-icon>
        <v-card-title style="justify-content: center">
          {{curLocale.notAvailable}}
        </v-card-title>
      </div>
    </v-card>
  </v-app>
</template>

<script>
  const axios = require('axios')
  const ip = 'localhost'
  const port = 25016;
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
            notAvailable: 'You are already logged in!'
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
            notAvailable: ' Вы уже авторизованы!'
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
            notAvailable: ' Вы вже авторизовані!'
          }
        },
        valid: false,
        login: "",
        pwd: "",
        loginRules: [
          v => !!v  || this.curLocale.loginRules[0],
          v => v.length !== 0 || this.curLocale.loginRules[1]
        ],
        pwdRules: [
          v => !!v || this.curLocale.pwdRules[0],
          v => v.length !== 0 || this.curLocale.pwdRules[1]
        ],
        load: false,
        alertErr: false,
        alertSuccess: false
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
    computed: {
      reachAuth() {
        return localStorage['sid'] === undefined || localStorage['sid'] === null;
      }
    },
    methods: {
      doAuth() {
        this.load = true;
        try {
          if (this.login && this.pwd !== '') {
            axios({
              method: "POST",
              url: `http://${ip}:${port}/api/authenticate`,
              data: {
                username: this.login,
                password: this.pwd
              }
            }, {
              headers: {
                "Content-Type": "application/x-www-form-urlencoded"
              }
            }).then(resp => {
              console.log(resp)
              this.alertSuccess = true
              setTimeout(() => {
                localStorage.setItem('sid', resp.data['id_token'])
                this.load = false;
                if (localStorage['redirect'] !== undefined) {
                  window.location.href = localStorage['redirect']
                } else {
                  window.location.href = '/cabinet';
                }
              }, 1500)
            }).catch(e => {
              console.error(e)
              this.alertErr = true
              this.load = false;
            })
          }
        } catch (e) {
          console.error(e)
          this.alertErr = true
          this.load = false;
        }
      }
    }
  }
</script>

<style scoped>

</style>