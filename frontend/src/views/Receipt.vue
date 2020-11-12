<template>
    <v-app>
        <v-card style="margin: 8% 30% 0 30%; padding: 1%" v-if="validateReceipt.status">
            <v-card-title>
                Чек №{{validateReceipt.id}}
                <v-spacer></v-spacer>
                <span style="font-size: 10pt;">{{validateReceipt.purchaseDate}}</span>
            </v-card-title>
            <v-divider></v-divider>
            <div style="margin: 4% 0 4% 0">
              <v-img width="230" height="230" style="margin: 0 auto;display: block" :src="getQrCode"></v-img>
            </div>
            <v-divider></v-divider>
            <v-card-text style="text-align: center">Чтоб забрать еду покажи код на кассе</v-card-text>
            <v-btn

                    color="success"
                    style="justify-content: center; display: flex"
                    to="/products"
                    @click="delReceipt"
            >
                Вернуться к продуктам
            </v-btn>

        </v-card>
        <div style="margin: 20%" v-else>
            <v-icon x-large style="text-align: center; display: flex">
                warning
            </v-icon>
            <v-card-title style="justify-content: center; display: flex">
                <v-icon>navigate_next</v-icon>Чек недоступен<v-icon>navigate_before</v-icon>
            </v-card-title>
        </div>
    </v-app>
</template>

<script>
  export default {
    name: "Receipt",
    data() {
      return {
        curLocale: {},
        locales: {
          'en-EN': {
            titleReceipt: 'Receipt ',
            subText: 'To pick up food, show the qr code at the checkout',
            btnTitle: 'Back to catalog',
            notFound: 'Receipt is not available'
          },
          'ru-RU': {
            titleReceipt: 'Чек ',
            subText: 'Чтоб забрать еду покажи код на кассе',
            btnTitle: 'Вернуться к продуктам',
            notFound: 'Чек недоступен'
          },
          'ua-UA': {
            titleReceipt: 'Чек ',
            subText: 'Щоб забрати страву покажи цей код на касі',
            btnTitle: 'Повернутися до продуктів',
            notFound: 'Чек недоступен'
          }
        },
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
      delReceipt() {
        localStorage.removeItem('receipt')
      }
    },
    computed: {
      validateReceipt() {
          // let idReceipt = this.$router.currentRoute.params.id.toString();
        let receiptInfo = localStorage['receipt'] === undefined?{}:JSON.parse(localStorage['receipt'])
        if (Object.keys(receiptInfo).length > 0) {
          return {
            status: true,
            id: receiptInfo.code,
            fname: receiptInfo.fname,
            lName: receiptInfo.lName,
            email: receiptInfo.email,
            group: receiptInfo.group,
            canteen: receiptInfo.activeCanteen,
            purchaseDate: receiptInfo.purchaseDate
          };
        } else {
          return {
            status: false,
          };
        }
      },
      getQrCode() {
          // let receiptInfo = JSON.parse(document.cookie.split(' ')[1].split('=')[1]);
          return 'https://api.qrserver.com/v1/create-qr-code/?size=150x150&data='+'https://localhost:8080/admin/receipt/'+this.validateReceipt.id;
      }
      }
  }
</script>

<style scoped>

</style>