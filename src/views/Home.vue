<template>
    <v-app style="background-color: #bfe9ff">
        <div style="margin-top: 20%">
            <v-card-title style="justify-content: center">NUFOOD</v-card-title>
            <v-card-text style="text-align: center; display: block">Столовая прямо на паре!</v-card-text>
            <div style="text-align: center; display: block" class="btns" v-if="info.user_info.login">
                <v-btn to="/products" color="primary" class="v--btn">Продукты</v-btn>
                <v-btn to="/cabinet" color="secondary" class="v--btn">Личный кабинет</v-btn>
                <v-btn to="/cabinet/orders" class="v--btn">Мои заказы</v-btn>
            </div>
            <div style="text-align: center; display: block" class="btns" v-else>
                <v-btn to="/products" color="primary" class="v--btn">Продукты</v-btn>
                <v-btn to="/auth" color="secondary" class="v--btn">Войти</v-btn>
                <v-btn to="/register" class="v--btn">Регистрация</v-btn>
            </div>
        </div>
    </v-app>
</template>

<script>
    export default {
        name: "Home",
        data() {
            return {
                info: {
                    user_info: {
                        login: false,
                        name: "tester",
                        university: ""
                    }
                }
            }
        }
    }
</script>

<style scoped>
    .btns .v--btn {
        margin: 5px;
    }
</style>