<template>
    <v-app>
        <v-card style="margin: 15% 35% 0 35%">
            <v-card-title style="margin-left: 5%;justify-content: center">Авторизация</v-card-title>
            <v-divider></v-divider>
            <v-form style="margin-left: 5%; width: 410px" v-model="valid">
                <v-row>
                    <v-col>
                        <v-text-field
                                label="Логин"
                                v-model="login"
                                :rules="loginRules"
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
                                required
                        ></v-text-field>
                        <router-link class="text-decoration-none" to="restorePwd" style="font-size: 10pt"><span>Забыли пароль?</span></router-link>
                    </v-col>
                </v-row>
                <v-btn style="margin-left: -5%" color="success" absolute width="100%" @click="doAuth">
                    ВОЙТИ
                </v-btn>
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
            }
        }
    }
</script>

<style scoped>

</style>