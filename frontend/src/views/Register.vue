<template>
  <v-app>
    <v-card style="margin: 15% 20% 0 20%">
      <v-card-title style="margin-left: 3%">
        {{ curLocale.formTitle }}
        <v-spacer></v-spacer>
        <v-card-subtitle>
          {{curLocale.subtext}}
        </v-card-subtitle>
      </v-card-title>
      <v-divider></v-divider>
      <v-form>
        <v-container>
          <v-row>
            <v-col>
              <v-text-field
                  :label="curLocale.form.login"
                  :rules="loginRules"
                  v-model="login"
                  solo
                  required
              ></v-text-field>
            </v-col>
            <v-col>
              <v-text-field
                  :label="curLocale.form.password"
                  :rules="pwdRules"
                  v-model="password"
                  solo
                  required
              ></v-text-field>
            </v-col>
          </v-row>
          <v-row>
            <v-col>
              <v-text-field
                  :label="curLocale.form.email"
                  :rules="emailRules"
                  v-model="email"
                  solo
                  required
              ></v-text-field>
            </v-col>
            <v-col>
              <v-text-field
                  :label="curLocale.form.group"
                  :rules="groupRules"
                  v-model="group"
                  solo
              ></v-text-field>
            </v-col>
          </v-row>
          <v-row>
            <v-col>
              <v-text-field
                  :label="curLocale.form.fname"
                  :rules="textRules"
                  v-model="fname"
                  solo
                  required
              ></v-text-field>
            </v-col>
            <v-col>
              <v-text-field
                  :label="curLocale.form.sname"
                  :rules="textRules"
                  v-model="sname"
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
  </v-app>
</template>

<script>
  const axios = require('axios')
  export default {
    name: "Register",
    data() {
      return {
        curLocale: {},
        locales: {
          'en-EN': {
            formTitle: 'Registration',
            subtext: '*required field',
            form: {
              login: "Login*",
              password: "Password*",
              fname: "First Name*",
              sname: "Second Name*",
              email: "E-mail",
              group: "Group*",
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
            ]
          },
          'ru-RU': {
            formTitle: 'Регистрация',
            subtext: '*обязательные поля',
            form: {
              login: "Логин*",
              password: "Пароль*",
              fname: "Имя*",
              sname: "Фамилия*",
              email: "E-mail",
              group: "Группа*",
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
            ]
          },
          'ua-UA': {
            formTitle: 'Реєстрація',
            subtext: "*обов'язкові поля",
            form: {
              login: "Логін*",
              password: "Пароль*",
              fname: "Ім'я*",
              sname: "Прізвище*",
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
            ]
          }
        },
        login: "",
        password: "",
        fname: "",
        sname: "",
        email: "",
        group: "",
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
          v => v.match("[a-zA-Z]+@[a-zA-Z]+[.][a-zA-Z]+") || this.curLocale.emailRules
        ],
        groupRules: [
          v => v.match('^[a-zA-Zа-яА-Я]+-[0-9]{2}-[0-9]{1,2}$') || this.curLocale.groupRules
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
      doRegist() {
        if ((this.login && this.password && this.fname && this.sname && this.group) !== "") {
          axios.post('http://192.168.0.109:25016/api/person/', {
            login: this.login,
            password: this.password,
            fName: this.fname,
            sName: this.sname,
            personGroup: this.group,
            email: this.email
          }).then(resp => console.log(resp))
          this.$router.push('/cabinet')
        }
      }
    }
  }
</script>

<style scoped>

</style>