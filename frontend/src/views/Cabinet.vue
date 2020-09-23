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
                    user_info: null,
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
        computed: {
            loadImg() {
              return 0;
              // let b64encode = btoa([].reduce.call(new Uint8Array(arrayBuffer),function(p,c){return p+String.fromCharCode(c)},''));
              // let mimetype = "image/jpeg";
              // return "data:"+mimetype+";base64,"+b64encode;
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