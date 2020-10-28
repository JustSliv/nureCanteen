<template>
  <v-app>
    <v-card style="margin: 8% 25% 0 25%">
      <v-card-title>
        Восстановление аккаунт
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
        Успех
      </v-alert>
      <v-container>
        <v-row>
          <v-col>
            <v-text-field
                label="Введите своё имя фамилию"
                :rules="rulesText"
                solo
                v-model="initials"
            ></v-text-field>
          </v-col>
        </v-row>
        <v-row>
          <v-col>
            <v-btn block large outlined color="primary" @click="doRecovery">Дальше</v-btn>
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
      initials: '',
      rulesText: [
          v => v.length > 0 || 'Поле не может быть пустым'
      ],
      successForm: false,
      errForm: false,
      errText: false
    }
  },
  methods: {
    doRecovery() {

    }
  }
}
</script>

<style scoped>

</style>