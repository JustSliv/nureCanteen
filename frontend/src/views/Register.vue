<template>
  <v-app>
    <v-card v-if="reachAuth" style="margin: 10% 35% 0 35%">
      <v-card-title style="text-align: center; display: block">
        {{ curLocale.formTitle }}
      </v-card-title>
      <v-divider></v-divider>
      <v-snackbar
          v-model="alertSuccess"
          color="success"
          top outlined
      >
        Успешная регистрация
        <template v-slot:action="{attrs}">
          <v-btn icon v-bind="attrs" @click="alertSuccess = false">
            <v-icon>
              close
            </v-icon>
          </v-btn>
        </template>
      </v-snackbar>
      <v-snackbar
          v-model="alertErr"
          color="red"
          top outlined
      >
        Ошибка регистрации
        <template v-slot:action="{attrs}">
          <v-btn icon v-bind="attrs" @click="alertErr = false">
            <v-icon>
              close
            </v-icon>
          </v-btn>
        </template>
      </v-snackbar>
      <v-form>
        <v-container>
          <v-row>
            <v-col cols="6">
              <v-text-field
                  :label="curLocale.form.login"
                  :rules="loginRules"
                  v-model="login"
                  prepend-inner-icon="person"
                  solo
                  required
              ></v-text-field>
            </v-col>
            <v-col cols="6">
              <v-text-field
                  type="password"
                  :label="curLocale.form.password"
                  :rules="pwdRules"
                  v-model="password"
                  prepend-inner-icon="security"
                  solo
                  required
              ></v-text-field>
            </v-col>
            <v-col cols="6">
              <v-text-field
                  :label="curLocale.form.email"
                  :rules="emailRules"
                  v-model="email"
                  prepend-inner-icon="email"
                  solo
                  required
              ></v-text-field>
            </v-col>
            <v-col cols="6">
              <v-text-field
                  :label="curLocale.form.group"
                  :rules="groupRules"
                  v-model="group"
                  prepend-inner-icon="group"
                  solo
              ></v-text-field>
            </v-col>
            <v-col cols="4">
              <v-text-field
                  :label="curLocale.form.fname"
                  :rules="textRules"
                  v-model="fname"
                  solo
                  required
              ></v-text-field>
            </v-col>
            <v-col cols="4">
              <v-text-field
                  type="number"
                  :label="curLocale.form.age"
                  :rules="numRules"
                  v-model="age"
                  prepend-inner-icon="access_time"
                  solo
                  required
              ></v-text-field>
            </v-col>
            <v-col cols="4">
              <v-text-field
                  :label="curLocale.form.lName"
                  :rules="textRules"
                  v-model="lName"
                  solo
                  required
              ></v-text-field>
            </v-col>
          </v-row>
          <v-btn block color="success" @click="doRegist">
            {{curLocale.btnTitle}}
          </v-btn>
        </v-container>
      </v-form>
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
    name: "Register",
    data() {
      return {
        curLocale: {},
        alertSuccess: false,
        alertErr: false,
        locales: {
          'en-EN': {
            formTitle: 'Registration',
            subtext: '*required field',
            form: {
              login: "Login*",
              password: "Password*",
              fname: "First Name*",
              lName: "Second Name*",
              email: "E-mail",
              group: "Group*",
              age: 'Age*'
            },
            btnTitle: 'CREATE ACCOUNT',
            loginRules: [
              'Login is required',
              'Login field cannot be empty'
            ],
            pwdRules: [
              'Password is required',
              'Password field cannot be empty'
            ],
            emailRules: 'Input correct email',
            groupRules: 'Input correct group',
            textRules: [
              'This field is required',
              'Field cannot be empty'
            ],
            numRules: 'Age cannot be less than 0',
            notAvailable: 'You are already logged in!'
          },
          'ru-RU': {
            formTitle: 'Регистрация',
            subtext: '*обязательные поля',
            form: {
              login: "Логин*",
              password: "Пароль*",
              fname: "Имя*",
              lName: "Фамилия*",
              email: "E-mail",
              group: "Группа*",
              age: 'Возраст*'
            },
            btnTitle: 'Создать аккаунт',
            loginRules: [
                'Логин обязателен',
                'Логин не может быть пуст'
            ],
            pwdRules: [
              'Пароль обязателен',
              'Пароль не может быть пуст'
            ],
            emailRules: 'Введите верный e-mail',
            groupRules: 'Введите верную группу',
            textRules: [
                'Это поле обязательно',
                'Поле не может быть пусто'
            ],
            numRules: 'Возраст не может быть меньше 0',
            notAvailable: 'Вы уже авторизованы!'
          },
          'ua-UA': {
            formTitle: 'Реєстрація',
            subtext: "*обов'язкові поля",
            form: {
              login: "Логін*",
              password: "Пароль*",
              fname: "Ім'я*",
              lName: "Прізвище*",
              email: "E-mail",
              group: "Група*",
            },
            btnTitle: 'Створити аккаунт',
            loginRules: [
              'Логін важливий',
              'Поле логіна не може бути пустим'
            ],
            pwdRules: [
              'Пароль важливий',
              'Пароль логіна не може бути пустим'
            ],
            emailRules: 'Напишіть коректний e-mail',
            groupRules: 'Напишіть коректну групу',
            textRules: [
              'Це поле важливе',
              'Поле не може бути пустим'
            ],
            numRules: 'Вік повинен бути більш 0',
            notAvailable: 'Ви вже авторизовані!'
          }
        },
        login: "",
        password: "",
        fname: "",
        lName: "",
        email: "",
        group: "",
        age: "",
        numRules: [
            v => v > 0 || this.curLocale.numRules
        ],
        loginRules: [
          v => !!v || this.curLocale.loginRules[0],
          v => v.length !== 0 || this.curLocale.loginRules[1]
        ],
        pwdRules: [
          v => !!v || this.curLocale.pwdRules[0],
          v => v.length !== 0 || this.curLocale.pwdRules[1]
        ],
        textRules: [
          v => !!v || this.curLocale.textRules[0],
          v => v.length !== 0 || this.curLocale.textRules[1]
        ],
        emailRules: [
          v => v.match("[a-zA-Z]+@[a-zA-Z]+[.][a-zA-Z]+") !== null || this.curLocale.emailRules
        ],
        groupRules: [
          v => v.match('^[a-zA-Zа-яА-Я]+-[0-9]{2}-[0-9]{1,2}$') !== null || this.curLocale.groupRules
        ]
      }
    },
    computed: {
      reachAuth() {
        return localStorage['sid'] === undefined || localStorage['sid'] === null;
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
      doRegist() {
        if ((this.login && this.password) !== "") {
          axios.post(`http://${ip}:${port}/api/register`, {
            username: this.login,
            password: this.password,
            fName: this.fname,
            lName: this.lName,
            personGroup: this.group,
            email: this.email,
            phone: "+(380) xx-xxx-xx-xx",
            avatar: 'data:image/png;base64,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',
            age: '20'
          }).then(resp => {
            console.log(resp)
            this.alertSuccess = true
            axios({
              method: "POST",
              url: `http://${ip}:${port}/api/authenticate`,
              data: {
                username: this.login,
                password: this.password
              }
            }, {
              headers: {
                "Content-Type": "application/x-www-form-urlencoded"
              }
            }).then(resp => {
              console.log(resp)
              localStorage.setItem('sid', resp.data['id_token'])
              window.location.href = '/cabinet';
            }).catch(err => {
              console.error(err)
              this.alertErr = true
            })
          }).catch(err => {
            console.error(err)
            this.alertErr = true
          })
        }
      }
    }
  }
</script>

<style scoped>

</style>