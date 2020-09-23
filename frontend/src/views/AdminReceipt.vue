<template>
  <v-app style="z-index: 5">
    <v-card style="margin: 10% 25% 0 25%">
      <v-card-title>
        Чек №{{getReceiptInfo.receiptInfo.id}}
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
                  Имя: {{getReceiptInfo.receiptInfo.user.fname}} <br/>
                  Фамилия: {{getReceiptInfo.receiptInfo.user.sname}} <br/>
                  Группа: {{getReceiptInfo.receiptInfo.user.group}} <br/>
                  Телефон: {{getReceiptInfo.receiptInfo.user.phone}} <br/>
                  E-mail: {{getReceiptInfo.receiptInfo.user.email}}
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
                    <v-list-item-subtitle>{{item.price}} UAH</v-list-item-subtitle>
                  </v-item-group>
                </template>
                <v-list-item-group style="text-align: center; display: flex">
                  <v-list-item-content>
                    <v-list-item-title>Описание: {{item.description}}</v-list-item-title>
                    <v-list-item-subtitle>Категория: {{item.category}}</v-list-item-subtitle>
                  </v-list-item-content>
                </v-list-item-group>
              </v-list-group>
            </v-list>
            <v-card-title v-if="!getReceiptInfo.receiptInfo.paid">Всего к оплате: {{getTotalPrice}} UAH</v-card-title>
          </v-col>
        </v-row>
      </v-container>
      <v-divider></v-divider>
      <v-container v-if="!getReceiptInfo.receiptInfo.paid">
        <v-row>
          <v-col>
            <v-dialog v-model="dialogCash" width="450">
              <template v-slot:activator>
                <v-btn width="100%" @click="buyOfCash">Оплата наличными</v-btn>
              </template>
              <v-card>
                <v-card-title>Всего к оплате: {{getTotalPrice}} UAH</v-card-title>
                <v-container>
                  <v-row>
                    <v-col>
                      <v-text-field
                          type="number"
                          label="Полная сумма"
                          v-model.number="fullAmount"
                          @input="pollChange"
                          :rules="numRules"
                          required
                      ></v-text-field>
                    </v-col>
                    <v-col>
                      <v-text-field
                          placeholder="Сдача:"
                          v-model="changeAmount"
                          :label="pollChange"
                          hint="Сдача"
                          persistent-hint
                          disabled
                      ></v-text-field>
                    </v-col>
                  </v-row>
                  <v-btn width="100%" @click="endBuyOfCash">ЗАВЕРШИТЬ ОПЛАТУ</v-btn>
                </v-container>
              </v-card>
            </v-dialog>
          </v-col>
          <v-col>
            <v-btn width="100%" @click="buyOfCreditCard">Оплата картой</v-btn>
          </v-col>
        </v-row>
      </v-container>
      <v-btn v-else width="100%" color="success" @click="processReceipt">НАЖМИТЕ ДЛЯ ОБРАБОТКИ ЧЕКА</v-btn>
    </v-card>
  </v-app>
</template>

<script>
export default {
  name: "AdminReceipt",
  data() {
    return {
      dialogCash: false,
      fullAmount: 0,
      changeAmount: 0
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
            sname: "Livr",
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