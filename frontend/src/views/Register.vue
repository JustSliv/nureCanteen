<template>
  <v-app>
    <v-card style="margin: 15% 20% 0 20%">
      <v-card-title style="margin-left: 3%">
        Регистрация
        <v-spacer></v-spacer>
        <v-card-subtitle>
          *обязательные поля
        </v-card-subtitle>
      </v-card-title>
      <v-divider></v-divider>
      <v-form>
        <v-container>
          <v-row>
            <v-col>
              <v-text-field
                  label="Логин*"
                  :rules="loginRules"
                  v-model="login"
                  solo
                  required
              ></v-text-field>
            </v-col>
            <v-col>
              <v-text-field
                  label="Пароль*"
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
                  label="E-mail"
                  :rules="emailRules"
                  v-model="email"
                  solo
                  required
              ></v-text-field>
            </v-col>
            <v-col>
              <v-text-field
                  label="Группа*"
                  :rules="groupRules"
                  v-model="group"
                  solo
              ></v-text-field>
            </v-col>
          </v-row>
          <v-row>
            <v-col>
              <v-text-field
                  label="Имя*"
                  :rules="textRules"
                  v-model="fname"
                  solo
                  required
              ></v-text-field>
            </v-col>
            <v-col>
              <v-text-field
                  label="Фамилия*"
                  :rules="textRules"
                  v-model="sname"
                  solo
                  required
              ></v-text-field>
            </v-col>
          </v-row>
          <v-btn block color="success" @click="doRegist">
            СОЗДАТЬ АККАУНТ
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
        login: "",
        password: "",
        fname: "",
        sname: "",
        email: "",
        group: "",
        loginRules: [
          v => !!v || 'Логин обязателен',
          v => v.length !== 0 || "Логин не может быть пуст"
        ],
        pwdRules: [
          v => !!v || 'Пароль обязателен',
          v => v.length !== 0 || "Пароль не может быть пуст"
        ],
        textRules: [
          v => !!v || 'Это поле обязательно',
          v => v.length !== 0 || "Поле не может быть пусто"
        ],
        emailRules: [
          v => v.match("[a-zA-Z]+@[a-zA-Z]+[.][a-zA-Z]+") || "Введите верный e-mail"
        ],
        groupRules: [
          v => v.match('^[a-zA-Zа-яА-Я]+-[0-9]{2}-[0-9]{1,2}$') || 'Введите верную группу'
        ]
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