<template>
  <v-app style="z-index: 5;">
    <v-card style="margin: 10% 35% 0 35%">
      <v-card-title style="justify-content: center" color="primary">
        {{curLocale.titlePage}}
      </v-card-title>
      <v-divider></v-divider>
      <v-form>
        <v-container>
          <v-row>
            <v-col cols="12">
              <v-text-field
                  :label="curLocale.labels[0]"
                  :rules="loginRules"
                  append-icon="person"
                  required
              ></v-text-field>
            </v-col>
            <v-col cols="12">
              <v-text-field
                  :rules="pwdRules"
                  :label="curLocale.labels[1]"
                  :type="showPwd === true ? 'password' : 'text'"
                  :append-icon="showPwd !== true ? 'visibility' : 'visibility_off'"
                  @click:append="() => (showPwd = !showPwd)"
                  required
              ></v-text-field>
            </v-col>
          </v-row>
          <v-btn block color="success" @click="doAuth">
            {{curLocale.btnTitle}}
          </v-btn>
        </v-container>
      </v-form>
    </v-card>
  </v-app>
</template>

<script>
export default {
  name: "Admin",
  data() {
    return {
      curLocale: {},
      locales: {
        'en-EN': {
          titlePage: 'Admin panel',
          labels: [
            'Login:',
            'Password:'
          ],
          btnTitle: 'Log-in',
          loginRules: [
            'Login is required',
            'Login field cannot be empty'
          ],
          pwdRules: [
            'Password is required',
            'Password field cannot be empty'
          ]
        },
        'ru-RU': {
          titlePage: 'Админ панель',
          labels: [
            'Логин:',
            'Пароль:'
          ],
          btnTitle: 'Войти',
          loginRules: [
            'Логин обязателен',
            'Логин не может быть пуст'
          ],
          pwdRules: [
            'Пароль обязателен',
            'Пароль не может быть пуст'
          ]
        },
        'ua-UA': {
          titlePage: 'Адмін панель',
          labels: [
            'Логін:',
            'Пароль:'
          ],
          btnTitle: 'Війти',
          loginRules: [
            'Логін важливий',
            'Поле логіна не може бути пустим'
          ],
          pwdRules: [
            'Пароль важливий',
            'Пароль логіна не може бути пустим'
          ]
        }
      },
      showPwd: true,
      loginRules: [
        v => !!v || this.curLocale.loginRules[0],
        v => v.length <= 0 || this.curLocale.loginRules[1]
      ],
      pwdRules: [
        v => !!v || this.curLocale.pwdRules[0],
        v => v.length <= 0 || this.curLocale.pwdRules[1]
      ]
    }
  },
  methods: {
    doAuth() {
      // sending POST
      window.location.href = '/admin/cabinet'
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

</style>