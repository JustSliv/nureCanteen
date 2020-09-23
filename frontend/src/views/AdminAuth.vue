<template>
  <v-app style="z-index: 5;">
    <v-card style="margin: 10% 35% 0 35%">
      <v-card-title style="justify-content: center" color="primary">Админ панель</v-card-title>
      <v-divider></v-divider>
      <v-form>
        <v-container style="width: 70%" v-model="valid">
          <v-row>
            <v-text-field
                label="Логин:"
                :rules="loginRules"
                required
            ></v-text-field>
          </v-row>
          <v-row>
            <v-text-field
                :rules="pwdRules"
                label="Пароль:"
                type="password"
                required
            ></v-text-field>
          </v-row>
        </v-container>
        <v-btn width="100%" color="success" absolute @click="auth">ВОЙТИ</v-btn>
      </v-form>
    </v-card>
  </v-app>
</template>

<script>
export default {
  name: "Admin",
  data() {
      return {
          valid: false,
          loginRules: [
              v => !!v || 'Логин обязателен',
              v => v.length <= 0 || "Логин не может быть пуст"
          ],
          pwdRules: [
            v => !!v || 'Пароль обязателен',
            v => v.length <= 0 || "Пароль не может быть пуст"
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