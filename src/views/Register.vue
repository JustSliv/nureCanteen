<template>
    <v-app>
        <v-card style="margin: 15% 20% 0 20%">
            <v-card-title style="margin-left: 3%">
                Регистрация
                <v-card-text style="position: absolute; left: 80%">*обязательные поля</v-card-text>
            </v-card-title>
            <v-divider></v-divider>
            <v-form style="margin-left: 5%">
                <v-row>
                    <v-col>
                        <v-text-field
                                label="Логин*"
                                :rules="loginRules"
                                v-model="login"
                                style="width: 250px"
                                required
                        ></v-text-field>
                    </v-col>
                    <v-col>
                        <v-text-field
                                label="Пароль*"
                                :rules="pwdRules"
                                v-model="password"
                                style="width: 250px"
                                required
                        ></v-text-field>
                    </v-col>
                    <v-col>
                        <v-text-field
                                label="E-mail"
                                :rules="emailRules"
                                v-model="email"
                                style="width: 250px"
                                required
                        ></v-text-field>
                    </v-col>
                </v-row>
                <v-row>
                    <v-col>
                        <v-text-field
                                label="Имя*"
                                :rules="textRules"
                                v-model="fname"
                                style="width: 250px"
                                required
                        ></v-text-field>
                    </v-col>
                    <v-col>
                        <v-text-field
                                label="Фамилия*"
                                :rules="textRules"
                                v-model="sname"
                                style="width: 250px"
                                required
                        ></v-text-field>
                    </v-col>
                    <v-col>
                        <v-text-field
                                label="Группа*"
                                :rules="textRules"
                                v-model="group"
                                style="width: 250px"
                                required
                        ></v-text-field>
                    </v-col>
                </v-row>
                <v-btn width="100%" absolute color="success" style="margin-left: -5%" @click="doRegist">
                    СОЗДАТЬ АККАУНТ
                </v-btn>
            </v-form>
        </v-card>
    </v-app>
</template>

<script>
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
                    v => (v.match("[a-zA-Z]+@[a-zA-Z]+[.][a-zA-Z]+")!==null?v.match("[a-zA-Z]+@[a-zA-Z]+[.][a-zA-Z]+")[0]:null) !== null || "Введите верный e-mail"
                ]
            }
        },
        methods: {
            doRegist() {
                if ((this.login && this.password && this.fname && this.sname && this.group) !== "") {
                    return false;
                }
            }
        }
    }
</script>

<style scoped>

</style>