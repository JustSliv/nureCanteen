<template>
  <v-app>
    <v-card style="margin: 8% 25% 0 25%">
      <v-card-title>
        {{curLocale.titlePage}}
      </v-card-title>
      <v-divider></v-divider>
      <v-alert
          type="error"
          v-model="errForm"
      >
        {{errText}}
      </v-alert>
      <v-alert
          type="success"
          v-model="successForm"
      >
        {{curLocale.alerts[0]}}
      </v-alert>
      <v-container>
        <v-row>
          <v-col>
            <v-text-field
                :label="curLocale.labelText"
                :rules="rulesText"
                solo
                v-model="personName"
            ></v-text-field>
          </v-col>
        </v-row>
        <v-row>
          <v-col>
            <v-btn block large outlined color="primary" @click="doRecovery">{{curLocale.btnTitle}}</v-btn>
          </v-col>
        </v-row>
      </v-container>
    </v-card>
  </v-app>
</template>

<script>
export default {
  name: "RestoreAcc",
  data() {
    return {
      curLocale: {},
      locales: {
        'en-EN': {
          titlePage: 'Recovery account',
          alerts: [
            'Success', 'Input First and Second name!'
          ],
          labelText: 'Input your First and Second name',
          btnTitle: 'Continue',
          rules: [
              'Field cannot be empty!'
          ]
        },
        'ru-RU': {
          titlePage: 'Восстановление аккаунта',
          alerts: [
              'Успех', 'Введите имя и фамилию!'
          ],
          labelText: 'Введите своё имя и фамилию',
          btnTitle: 'Дальше',
          rules: [
            'Поле не может быть пустым!'
          ]
        },
        'ua-UA': {
          titlePage: 'Відновлення аккаунт',
          alerts: [
            'Успіх', "Напиши ім'я та прізвище!"
          ],
          labelText: "Напиши своє ім'я та прізвище",
          btnTitle: 'Продовжити',
          rules: [
            'Форма не може бути пустою!'
          ]
        }
      },
      personName: '',
      rulesText: [
          v => v.length > 0 || this.curLocale.rules[0]
      ],
      successForm: false,
      errForm: false,
      errText: false
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
    doRecovery() {
      if (this.personName !== '') {
        this.successForm = true
        // sending POST
      } else {
        this.errForm = true
        this.errText = this.curLocale.alerts[1]
      }
    }
  }
}
</script>

<style scoped>

</style>