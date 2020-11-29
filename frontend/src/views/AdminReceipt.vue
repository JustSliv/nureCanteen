<template>
  <v-app style="z-index: 5">
    <v-card style="margin: 10% 25% 0 25%">
      <v-card-title>
        {{ curLocale.title}} №{{getReceiptInfo.receiptInfo.id}}
        <v-spacer></v-spacer>
        <v-card-subtitle>{{getReceiptInfo.receiptInfo.date}}</v-card-subtitle>
      </v-card-title>
      <v-divider></v-divider>
      <v-container>
        <v-row>
          <v-col>
            <v-row>
              <v-col>
                <v-img :src="getReceiptInfo.receiptInfo.user.avatar" width="100" height="100"></v-img>
              </v-col>
              <v-col>
                <v-card-text>
                  {{curLocale.form[0]}} {{getReceiptInfo.receiptInfo.user.fname}} <br/>
                  {{curLocale.form[1]}} {{getReceiptInfo.receiptInfo.user.lName}} <br/>
                  {{curLocale.form[2]}} {{getReceiptInfo.receiptInfo.user.group}} <br/>
                  {{curLocale.form[3]}} {{getReceiptInfo.receiptInfo.user.phone}} <br/>
                  {{curLocale.form[4]}} {{getReceiptInfo.receiptInfo.user.email}}

                </v-card-text>
              </v-col>
            </v-row>
          </v-col>
          <v-col style="max-height: 350px;overflow-x: hidden; overflow-y: auto">
            <v-list>
              <v-list-group v-for="item in getReceiptInfo.receiptInfo.userCart" :key="item">
                <template v-slot:activator>
                  <v-list-item-title>{{item.name}}</v-list-item-title>
                  <v-item-group>
                    <v-list-item-subtitle>{{item.price}} {{curLocale.totalPay[1]}}</v-list-item-subtitle>
                  </v-item-group>
                </template>
                <v-list-item-group style="text-align: center; display: flex">
                  <v-list-item-content>
                    <v-list-item-title>{{curLocale.info[0]}} {{item.description}}</v-list-item-title>
                    <v-list-item-subtitle>{{curLocale.info[1]}} {{item.category}}</v-list-item-subtitle>
                  </v-list-item-content>
                </v-list-item-group>
              </v-list-group>
            </v-list>
            <v-card-title v-if="!getReceiptInfo.receiptInfo.paid">{{curLocale.totalPay[0]}} {{getTotalPrice}} {{curLocale.totalPay[1]}}</v-card-title>
          </v-col>
        </v-row>
      </v-container>
      <v-divider></v-divider>
      <v-container v-if="!getReceiptInfo.receiptInfo.paid">
        <v-row>
          <v-col>
            <v-dialog v-model="dialogCash" width="450">
              <template v-slot:activator>
                <v-btn width="100%" @click="buyOfCash">
                  {{curLocale.typePay[0]}}
                </v-btn>
              </template>
              <v-card>
                <v-card-title>{{curLocale.totalPay[0]}} {{getTotalPrice}} {{curLocale.totalPay[1]}}</v-card-title>
                <v-container>
                  <v-row>
                    <v-col>
                      <v-text-field
                          type="number"
                          :label="curLocale.labels[0]"
                          v-model.number="fullAmount"
                          @input="pollChange"
                      ></v-text-field>
                    </v-col>
                    <v-col>
                      <v-text-field
                          :placeholder="curLocale.labels[1]"
                          v-model="changeAmount"
                          :label="pollChange"
                          :hint="curLocale.labels[1]"
                          persistent-hint
                          disabled
                      ></v-text-field>
                    </v-col>
                  </v-row>
                  <v-btn width="100%" @click="endBuyOfCash">
                    {{curLocale.btnTitle}}
                  </v-btn>
                </v-container>
              </v-card>
            </v-dialog>
          </v-col>
          <v-col>
            <v-btn width="100%" @click="buyOfCreditCard">
              {{curLocale.typePay[1]}}
            </v-btn>
          </v-col>
        </v-row>
      </v-container>
      <v-btn v-else width="100%" color="success" @click="processReceipt">
        {{curLocale.btnSuccess}}
      </v-btn>
    </v-card>
  </v-app>
</template>

<script>
export default {
  name: "AdminReceipt",
  data() {
    return {
      curLocale: {},
      locales: {
        'en-EN': {
          title: 'Receipt',
          form: [
              'First name:',
              'Second name:',
              'Group:',
              'Phone:',
              'E-mail:'
          ],
          info: [
              'Description:',
              'Category:'
          ],
          totalPay: [
              'Total to pay',
              'UAH'
          ],
          typePay: [
              'Payment cash',
              'Payment credit card'
          ],
          labels: [
              'Full sum:',
              'changes:'
          ],
          btnTitle: 'Complete Payment',
          btnSuccess: 'TAP TO PROCESS RECEIPT'
        },
        'ru-RU': {
          title: 'Чек',
          form: [
            'Имя:',
            'Фамилия:',
            'Група:',
            'Телефон:',
            'E-mail:'
          ],
          info: [
            'Описание:',
            'Категория:'
          ],
          totalPay: [
            'Всего к оплате',
            'ГРН'
          ],
          typePay: [
            'Оплата наличными',
            'Оплата картой'
          ],
          labels: [
            'Полная сума:',
            'сдача:'
          ],
          btnPayTitle: 'Завершить оплату',
          btnSuccess: 'НАЖМИТЕ ДЛЯ ОБРАБОТКИ ЧЕКА'
        },
        'ua-UA': {
          title: 'Чек',
          form: [
            'Ім`я:',
            'Прізвище:',
            'Група:',
            'Телефон:',
            'E-mail:'
          ],
          info: [
            'Опис:',
            'Категорія:'
          ],
          totalPay: [
            'Усього до сплати',
            'ГРН'
          ],
          typePay: [
            'Сплата готівкою',
            'Сплата картою'
          ],
          labels: [
            'Повна сума:',
            'решта:'
          ],
          btnTitle: 'Завершення сплати',
          btnSuccess: 'НАЖМІТЬ ДЛЯ ОБРОБКИ ЧЕКА'
        }
      },
      dialogCash: false,
      fullAmount: 0,
      changeAmount: 0
    }
  },
  beforeMount() {
    if (localStorage['lang'] === 'ru-RU') {
      this.curLocale = this.locales["ru-RU"];
    } else if (localStorage['lang'] === 'en-EN') {
      this.curLocale = this.locales["en-EN"];
    } else if (localStorage['lang'] === 'ua-UA') {
      this.curLocale = this.locales["ua-UA"];
    } else {
      localStorage.setItem('lang', 'ua-UA')
      this.curLocale = this.locales["ua-UA"];
    }
  },
  methods: {
    processReceipt() {
      // sending POST
    },
    buyOfCash() {
      this.dialogCash = true;
    },
    buyOfCreditCard() {
      // redirect LiqPay
    },
    pollChange() {
      this.changeAmount = this.fullAmount - this.getTotalPrice
    },
    endBuyOfCash() {
      // sending POST
      this.dialogCash = false
    }
  },
  computed: {
    getTotalPrice() {
      let res = 0;
      for (let i=0;i<this.getReceiptInfo.receiptInfo.userCart.length;i++){
        let item = this.getReceiptInfo.receiptInfo.userCart[i];
        res += item.price;
      }
      return res;
    },
    getReceiptInfo() {
      // return {
      //   receiptInfo: JSON.parse(localStorage['receipt'])
      // }
      return {
        receiptInfo: {
          id: 123456,
          date: "09.09.2020",
          paid: false,
          userCart: [
            {
              id: 0,
              name: "Пирожок0",
              price: 21,
              category: "Духовный",
              description: "вкусный",
              available_count: 20,
              image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
            },
            {
              id: 0,
              name: "Пирожок0",
              price: 21,
              category: "Духовный",
              description: "вкусный",
              available_count: 20,
              image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
            },
            {
              id: 0,
              name: "Пирожок0",
              price: 21,
              category: "Духовный",
              description: "вкусный",
              available_count: 20,
              image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
            },{
              id: 0,
              name: "Пирожок0",
              price: 21,
              category: "Духовный",
              description: "вкусный",
              available_count: 20,
              image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
            },
            {
              id: 0,
              name: "Пирожок0",
              price: 21,
              category: "Духовный",
              description: "вкусный",
              available_count: 20,
              image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
            },{
              id: 0,
              name: "Пирожок0",
              price: 21,
              category: "Духовный",
              description: "вкусный",
              available_count: 20,
              image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
            },
            {
              id: 0,
              name: "Пирожок0",
              price: 21,
              category: "Духовный",
              description: "вкусный",
              available_count: 20,
              image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
            },
            {
              id: 0,
              name: "Пирожок0",
              price: 21,
              category: "Духовный",
              description: "вкусный",
              available_count: 20,
              image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
            },
            {
              id: 0,
              name: "Пирожок0",
              price: 21,
              category: "Духовный",
              description: "вкусный",
              available_count: 20,
              image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
            }
          ],
          user: {
            fname: "Tim",
            lName: "Livr",
            group: "PZPI-18-7",
            email: "test@test.com",
            phone: "12345678910",
            avatar: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
          }
        }
      }
    }
  }
}
</script>

<style scoped>

</style>