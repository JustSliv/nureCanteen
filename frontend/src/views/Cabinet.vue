<template>
    <v-app>
        <v-card style="margin: 8% 25% 0 25%">
            <v-tabs
                    v-model="tabs"
                    grow
            >
                <v-tab>Дашборд</v-tab>
                <v-tab>Мои заказы</v-tab>
                <v-tab>Настройки</v-tab>
                <v-tab-item>
                    <v-card max-height="450" style="overflow-x: hidden; overflow-y: auto">
                        <v-card-title>{{info.user_info.fName}} {{info.user_info.sName}}</v-card-title>
                        <v-divider></v-divider>
                        <img style="margin: 2%" width="200" height="200" alt="Пусто" :src="info.user_info.avatar"/>
                        <div style="position: absolute; top: 14%; left: 65%">
                            <v-card-text>Возраст: {{info.user_info.age}}</v-card-text>
                            <v-card-text>Ваша группа: {{ info.user_info.personGroup }}</v-card-text>
                            <v-card-text>Ваш e-mail: {{info.user_info.email}}</v-card-text>
                            <v-card-text>Ваш номер: {{info.user_info.phone}}</v-card-text>
                        </div>
                        <v-divider></v-divider>
                        <v-card-title>Последня покупка</v-card-title>
                        <v-card-text style="position: absolute; top: 75%; left: 85%" v-if="info.last_buy.items.length > 0">
                          {{info.last_buy.date}}
                        </v-card-text>
                        <v-divider></v-divider>
                        <v-list v-if="info.last_buy.items.length > 0">
                            <v-list-group>
                                <template v-slot:activator>
                                    <v-list-item-title>Оплачено: </v-list-item-title>
                                    <v-item-group>
                                        <v-list-item-title>{{info.last_buy.total_price}} UAH</v-list-item-title>
                                    </v-item-group>
                                </template>
                                <v-list-group no-action sub-group v-for="item in info.last_buy.items" :key="item">
                                    <template v-slot:activator>
                                        <v-list-item-title>{{item.name}}</v-list-item-title>
                                        <v-item-group>
                                            <v-list-item-title>{{item.price}} UAH</v-list-item-title>
                                        </v-item-group>
                                    </template>
                                    <v-list-item>
                                        <v-list-item-title>{{item.description}}</v-list-item-title>
                                    </v-list-item>
                                </v-list-group>
                            </v-list-group>
                        </v-list>
                        <v-card flat v-else style="margin: 5%">
                          <v-icon style="text-align: center;display: block">
                            warning
                          </v-icon>
                          <v-card-title style="justify-content: center">Покупки отсутствуют</v-card-title>
                        </v-card>
                    </v-card>
                </v-tab-item>
                <v-tab-item>
                    <v-card flat max-height="450" style="overflow-x: hidden; overflow-y: auto">
                        <v-list v-if="info.orders.length > 0">
                            <v-list-group v-for="order in info.orders" :key="order">
                                <template v-slot:activator>
                                    <v-list-item-title>Чек №{{order.receipt_id}}</v-list-item-title>
                                    <v-item-group>
                                        <v-list-item-title>{{order.total_price}} UAH</v-list-item-title>
                                    </v-item-group>
                                </template>
                                <v-list-group sub-group no-action v-for="item in order.items" :key="item">
                                    <template v-slot:activator>
                                        <v-list-item-title>{{item.name}}</v-list-item-title>
                                        <v-item-group>
                                            <v-list-item-title>{{item.price}} UAH</v-list-item-title>
                                        </v-item-group>
                                    </template>
                                    <v-list-item>
                                        <v-list-item-subtitle>{{item.category}}</v-list-item-subtitle>
                                        <v-list-item-title>{{item.description}}</v-list-item-title>
                                    </v-list-item>
                                </v-list-group>
                            </v-list-group>
                        </v-list>
                        <div v-else style="margin: 10%">
                            <v-icon style="text-align: center;display: block">warning</v-icon>
                            <v-card-title style="justify-content: center">Заказов не найдено</v-card-title>
                        </div>
                    </v-card>
                </v-tab-item>
                <v-tab-item>
                    <v-card>
                        <v-form>
                            <v-container style="margin-left: 18%">
                                <v-row>
                                    <v-col
                                        cols="10"
                                        md="4"
                                        sm="2"
                                        style="padding: 3%"
                                    >
                                        <v-row>
                                            Имя:
                                        </v-row>
                                        <v-row>
                                            <v-text-field
                                                v-model="info.user_info.fName"
                                            ></v-text-field>
                                        </v-row>
                                    </v-col>
                                    <v-col
                                        cols="10"
                                        md="4"
                                        sm="2"
                                        style="padding: 3%"
                                    >
                                        <v-row>
                                            Фамилия:
                                        </v-row>
                                        <v-row>
                                            <v-text-field
                                                v-model="info.user_info.sName"
                                            ></v-text-field>
                                        </v-row>
                                    </v-col>
                                </v-row>
                                <v-row>
                                    <v-col
                                        cols="10"
                                        md="4"
                                        sm="2"
                                        style="padding: 3%"
                                    >
                                        <v-row>
                                            Возраст:
                                        </v-row>
                                        <v-row>
                                            <v-text-field type="number"
                                                v-model="info.user_info.age"
                                            ></v-text-field>
                                        </v-row>
                                    </v-col>
                                    <v-col
                                        cols="10"
                                        md="4"
                                        sm="2"
                                        style="padding: 3%"
                                    >
                                        <v-row>
                                            Группа:
                                        </v-row>
                                        <v-row>
                                            <v-text-field
                                                v-model="info.user_info.personGroup"
                                                :rules="groupRules"
                                            ></v-text-field>
                                        </v-row>
                                    </v-col>
                                </v-row>
                                <v-row>
                                    <v-col
                                        cols="10"
                                        md="4"
                                        sm="2"
                                        style="padding: 3%"
                                    >
                                        <v-row>
                                            E-mail:
                                        </v-row>
                                        <v-row>
                                            <v-text-field type="email"
                                                v-model="info.user_info.email"
                                                :rules="emailRules"
                                            ></v-text-field>
                                        </v-row>
                                    </v-col>
                                    <v-col
                                        cols="10"
                                        md="4"
                                        sm="2"
                                        style="padding: 3%"
                                    >
                                        <v-row>
                                            Телефон:
                                        </v-row>
                                        <v-row>
                                            <v-text-field
                                                v-model="info.user_info.phone"
                                            ></v-text-field>
                                        </v-row>
                                    </v-col>
                                </v-row>
                                <v-row>
                                    <v-col
                                        cols="10"
                                        md="4"
                                        sm="2"
                                    >
                                        <v-img width="50" height="50" :src="info.user_info.avatar" v-model="info.user_info.avatar"></v-img>
                                    </v-col>
                                    <v-col
                                        cols="10"
                                        md="4"
                                        sm="2"
                                    >
                                        <v-file-input
                                            accept="image/png, image/jpeg, image/jpg"
                                            placeholder="Иконка профиля"
                                            @change="reRenderImg"
                                        >

                                        </v-file-input>
                                    </v-col>
                                </v-row>
                            </v-container>
                            <v-btn width="100%" color="success" @click="editMyData">ИЗМЕНИТЬ ДАННЫЕ</v-btn>
                        </v-form>
                    </v-card>
                    <v-snackbar
                        v-model="editDialog"
                        color="success"
                        multi-line
                        top
                        timeout="4000"
                    >
                        Данные изменены
                        <template v-slot:action>
                            <v-btn
                                text
                                @click="editDialog = false"
                            >
                                Закрыть
                            </v-btn>
                        </template>
                    </v-snackbar>
                </v-tab-item>
            </v-tabs>
        </v-card>
    </v-app>
</template>

<script>
    const axios = require('axios');
    export default {
        name: "Cabinet",
        data() {
            return {
                tabs: false,
                editDialog: false,
                info: {
                    user_info: {
                      fName: 'tim',
                      sName: 'livr',
                      email: 'tsda@mail.com',
                      group: 'PZPI',
                      age: 20,
                      avatar: 'data:image/png;base64,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'
                    },
                    last_buy: {
                        total_price: 23,
                        date: "12.09.2020",
                        items: [
                          {
                            id: 0,
                            name: "Пирожок",
                            category: "Духовный",
                            description: "вкусный",
                            price: 23
                          },
                          {
                            id: 0,
                            name: "Пирожок",
                            category: "Духовный",
                            description: "вкусный",
                            price: 23
                          },
                          {
                            id: 0,
                            name: "Пирожок",
                            category: "Духовный",
                            description: "вкусный",
                            price: 23
                          },
                          {
                            id: 0,
                            name: "Пирожок",
                            category: "Духовный",
                            description: "вкусный",
                            price: 23
                          }
                        ]
                    },
                    orders: [
                      {
                        receipt_id: "675843",
                        total_price: 7,
                        date: "10.09.2020",
                        items: [
                          {
                            id: 1,
                            name: "Пирожок",
                            description: "вкусный",
                            price: 7
                          }
                        ]
                      },
                      {
                        receipt_id: "123456",
                        date: "12.09.2020",
                        total_price: 25,
                        items: [
                          {
                            id: 0,
                            name: "Пирожок",
                            description: "вкусный",
                            price: 15
                          },
                          {
                            id: 2,
                            name: "Пирожок",
                            description: "вкусный",
                            price: 10
                          }
                        ]
                      }
                    ]
                },
                emailRules: [
                  v => v.match("[a-zA-Z]+@[a-zA-Z]+[.][a-zA-Z]+") || "Введите верный e-mail"
                ],
                groupRules: [
                  v => v.match('^[a-zA-Zа-яА-Я]+-[0-9]{2}-[0-9]{1,2}$') || 'Введите верную группу'
                ]
            }
        },
        methods: {
            reRenderImg(ev) {
                let reader = new FileReader();
                reader.onload = (e) => {
                  this.info.user_info.avatar = e.target.result
                }
                reader.readAsDataURL(ev);
            },
            editMyData() {
                if (this.checkChange() && !this.editDialog) {
                    // sending POST
                    //-------------
                    // after success sending POST
                    this.$nextTick(() => {
                        this.editDialog = true;
                    })
                    setTimeout(function (){
                        window.location.reload();
                    }, 1500)
                    return 0;
                }
            },
            checkChange() {
                return this.fname === this.info.user_info.fName ||
                    this.sName === this.info.user_info.sName ||
                    this.age === this.info.user_info.age ||
                    this.group === this.info.user_info.personGroup ||
                    this.email === this.info.user_info.email ||
                    ((this.fname || this.sName || this.age || this.group || this.email || this.phone) !== '');
            }
        },
        mounted() {
          axios.get('http://192.168.0.109:25016/api/person/5/', {
            headers: {
              'Access-Control-Allow-Origin': '*'
            }
          }).then(resp => (this.info.user_info=resp.data))
        }
    }
</script>

<style scoped>

</style>