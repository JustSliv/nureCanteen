<template>
  <v-app>
    <v-card style="margin: 15% 35% 0 35%">
      <v-card-title style="text-align: center; display: block">Авторизация</v-card-title>
      <v-divider></v-divider>
      <v-form v-model="valid">
        <v-container>
          <v-row>
            <v-col>
              <v-text-field
                  label="Логин"
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
                  label="Пароль"
                  v-model="pwd"
                  :rules="pwdRules"
                  outlined
                  required
              ></v-text-field>
            </v-col>
          </v-row>
          <router-link class="text-decoration-none" to="restoreAccount" style="font-size: 10pt">
            Забыли пароль?
          </router-link>
          <v-btn color="success" block @click="doAuth">
            ВОЙТИ
          </v-btn>
        </v-container>
      </v-form>
      <v-dialog v-model="load" hide-overlay width="220">
        <v-card color="primary">
          <v-card-text style="color: white;">
            Загрузка...
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
        valid: false,
        login: "",
        pwd: "",
        loginRules: [
          v => !!v || 'Логин обязателен',
          v => v.length <= 0 || "Логин не может быть пуст"
        ],
        pwdRules: [
          v => !!v || 'Пароль обязателен',
          v => v.length <= 0 || "Пароль не может быть пуст"
        ],
        load: false
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