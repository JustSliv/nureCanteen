<template>
  <v-app style="z-index: 5;">
    <v-card style="margin: 10% 35% 0 35%">
      <v-card-title style="justify-content: center" color="primary">
        {{curLocale.titlePage}}
      </v-card-title>
      <v-divider></v-divider>
      <v-form>
        <v-container style="width: 70%" v-model="valid">
          <v-row>
            <v-text-field
                :label="curLocale.labels[0]"
                :rules="loginRules"
                required
            ></v-text-field>
          </v-row>
          <v-row>
            <v-text-field
                :rules="pwdRules"
                :label="curLocale.labels[1]"
                type="password"
                required
            ></v-text-field>
          </v-row>
        </v-container>
        <v-btn width="100%" color="success" absolute @click="auth">
          {{curLocale.btnTitle}}
        </v-btn>
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
      valid: false,
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
    auth() {
      // sending POST
      window.location.href = '/admin/cabinet'
    }
  }
}
</script>

<style scoped>

</style>