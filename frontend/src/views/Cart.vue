<template>
    <v-app v-if="existsItems" style="padding-left: 2%">
        <v-card-title style="margin-top: 8%; padding-left: 25%">
        <v-badge
                :content="cartItems.length"
        >
            Корзина
        </v-badge>
            <v-btn style="margin-left: 42%" @click="acceptOrder">
                Оформить заказ
                <v-icon style="margin-left: 2%">
                    check_circle
                </v-icon>
            </v-btn>
        </v-card-title>
        <v-list style="margin: 0 25% 0 25%" v-for="(item, i) in cartItems" :key="i">
            <v-card>
                <v-list-group>
                    <template v-slot:activator>
                        <v-list-item-title>{{item.name}}</v-list-item-title>
                        <v-item-group>
                            <v-list-item-title>Цена: {{item.price}} UAH</v-list-item-title>
                        </v-item-group>
                    </template>
                    <v-list-item-group style="margin-left: 5%">
                        <v-list-item-content>
                          <v-list-item-title>Описание: {{item.description}}</v-list-item-title>
                          <v-list-item-subtitle>Категория: {{item.category}}</v-list-item-subtitle>
                        </v-list-item-content>
                    </v-list-item-group>
                </v-list-group>
                <v-btn icon @click="delCartItem" absolute style="margin-top: -5.2%; margin-left: 100%">
                    <v-icon :data-id="item.id">
                        cancel
                    </v-icon>
                </v-btn>
            </v-card>
        </v-list>
        <v-card-title style="margin-top: 0; padding-left: 25%">
            Всего к оплате: <span style="margin-left: 46.5%">{{totalPrice}} UAH</span>
        </v-card-title>
          <v-dialog v-model="buyingItems" width="650">
              <v-card>
                <v-card-title>
                  <span class="headline">Ваши Данные</span>
                </v-card-title>
                <v-divider></v-divider>
                <v-container>
                  <v-row>
                    <v-col>
                      <v-text-field v-model="info.userInfo.fName" :rules="textRules" label="Имя*" required></v-text-field>
                    </v-col>
                    <v-col>
                      <v-text-field v-model="info.userInfo.sName" :rules="textRules" label="Фамилия*" required></v-text-field>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col>
                      <v-text-field v-model="info.userInfo.group" :rules="textRules" label="Группа*" required></v-text-field>
                    </v-col>
                    <v-col>
                      <v-text-field v-model="info.userInfo.email" :rules="emailRules" label="Email" hint="Для рассылки акции и т.п." persistent-hint></v-text-field>
                    </v-col>
                  </v-row>
                </v-container>
                <v-alert type="error" v-if="errBuy">
                  Проверьте все поля
                </v-alert>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="red" text @click="buyingItems = false">Отмена</v-btn>
                  <v-btn color="blue" text @click="getReceipt">Подтвердить</v-btn>
                </v-card-actions>
              </v-card>
          </v-dialog>
    </v-app>
    <v-app v-else>
        <v-card style="margin: 20%;background-color: #bfe9ff" flat>
            <v-icon style="text-align: center; display: block" x-large>not_interested</v-icon>
            <v-card-title style="justify-content: center">Корзина пуста</v-card-title>
        </v-card>
    </v-app>
</template>

<script>
    export default {
        name: "Cart",
        data() {
            return {
                info: {
                  userInfo: {
                    id: 0,
                    fName: 'Tim',
                    sName: 'Livr',
                    group: 'PZPI',
                    email: 'tes@mal.com'
                  }
                },
                // cart_counter: localStorage.count_cart,
                // document.cookie.split('=')[0]==="cart_items"?JSON.parse(document.cookie.split('=')[1]):[]
                cartItems: localStorage['cart'] === undefined?[]:JSON.parse(localStorage['cart']),
                buyingItems: false,
                errBuy: false,
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
            delCartItem(ev) {
                // this.cart_counter--;
                // localStorage.count_cart = this.cart_counter;
                let id = parseInt(ev.path[0].attributes[2].value);
                this.cartItems = this.cartItems.filter(function (item) {
                    return item.id !== id
                });
                localStorage['cart'] = JSON.stringify(this.cartItems);
            },
            acceptOrder() {
              let isAuth = false
              if (isAuth) {
                this.getReceipt()
              } else {
                this.buyingItems = true;
              }
            },
            getReceipt() {
              if ((this.fname && this.sname && this.group) !== null) {
                let codeReceipt = "";
                let data;
                for(let i=0;i<6;i++){
                    codeReceipt += Math.floor(Math.random()*10).toString();
                }
                let now = new Date();
                let day = now.getDate();
                if (day < 10) day = "0"+now.getDate().toString();
                let month = now.getMonth();
                if (month < 10) month = "0"+now.getMonth().toString();
                let year = now.getFullYear();
                data = {
                    code: codeReceipt,
                    fname: this.fname,
                    sname: this.sname,
                    email: this.email,
                    group: this.group,
                    purchaseDate: day+"."+month+"."+year
                };
                let time = now.getTime();
                let expTime = time + 1000*36000;
                now.setTime(expTime);
                this.buyingItems = false;
                localStorage.removeItem('cart')
                localStorage['receipt'] = JSON.stringify(data)
                this.$router.push('/receipt');
              }
              else {
                  return this.errBuy = true;
              }
            }
        },
        computed: {
          totalPrice() {
            let total = 0;
            const items = this.cartItems;
            for (let i=0; i<items.length; i++){
                total += items[i].price;
            }
            return total;
          },
          existsItems() {
            try {
              return this.cartItems.length > 0;
            } catch (err) {
              return false
            }
          }
        }
    }
</script>

<style scoped>

</style>