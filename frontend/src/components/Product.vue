<template>
  <v-card max-width="220px" :data-id="product.id">
    <v-menu transition="fab-transition" offset-y v-model="infoProduct">
      <template v-slot:activator="{on, attrs}">
        <div style="justify-content: center; display: flex">
          <v-btn
              icon
              x-large
              title="Описание товара"
              v-bind="attrs"
              v-on="on"
              v-if="!activeInfoProduct"
              @click="activeInfoProduct = true"
          >
            <v-icon x-large>
              keyboard_arrow_down
            </v-icon>
          </v-btn>
          <v-btn
              icon
              x-large
              v-bind="attrs"
              v-on="on"
              v-if="activeInfoProduct"
              @click="activeInfoProduct = false"
              @mouseleave="reRenderInfoBtn"
          >
            <v-icon x-large>
              keyboard_arrow_up
            </v-icon>
          </v-btn>
        </div>
        <div>
          <v-img :src="product.image" :alt="product.name"></v-img>
          <v-card-subtitle>
            <router-link class="text-decoration-none" :to="'/product/'+product.product_id">
              {{product.name}}
            </router-link> <br/>
            {{product.price}} {{locale.productInfo.currency}}
          </v-card-subtitle>
          <v-btn @click="showBuyDialog" color="success" width="100%">{{locale.productInfo.toCart.toCartTitle}}</v-btn>
        </div>
      </template>
      <v-card>
        <v-card-text>
          <b>{{locale.productInfo.info[0]}}</b> {{product.category}} <br/>
          <b>{{locale.productInfo.info[4]}}</b> {{product.weight}} <br/>
          <b>{{locale.productInfo.info[5]}}</b> {{product.calories}} <br/>
          <b>{{locale.productInfo.info[1]}}</b> {{ product.available_count }} {{locale.productInfo.info[2]}} <br/>
          <b>{{locale.productInfo.info[3]}}</b> {{ product.description }} <br/>
        </v-card-text>
      </v-card>
    </v-menu>
    <v-dialog v-model="dialog" max-width="320" v-if="dialog">
      <v-card>
        <v-card-title class="headline">
          {{product.name}}
        </v-card-title>
        <v-card-text>
          {{locale.productInfo.toCart.tip}}
        </v-card-text>
        <v-container>
          <v-row no-gutters>
            <v-col>
              <v-text-field
                  label="Кол-во продукта:"
                  outlined
                  type="number"
                  v-model="countProducts"
              ></v-text-field>
            </v-col>
          </v-row>
        </v-container>
        <v-divider></v-divider>
        <v-card-actions>
          <v-btn
              color="red"
              text
              @click="dialog = false"
          >
            {{locale.productInfo.toCart.btns[0]}}
          </v-btn>
          <v-btn
              color="green"
              text
              @click="toCart"
          >
            {{locale.productInfo.toCart.btns[1]}}
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-snackbar
        top
        v-model="duplicate"
        v-if="duplicate"
        multi-line
        timeout="2000"
        color="success"
    >
      {{locale.productInfo.toCart.alerts[0]}}
    </v-snackbar>
    <v-snackbar
        top
        v-model="inCart"
        v-if="inCart"
        multi-line
        timeout="2000"
        color="success"
    >
      {{locale.productInfo.toCart.alerts[1]}}
      <v-btn text to="/cart">{{locale.productInfo.toCart.alertBtn}}</v-btn>
    </v-snackbar>
  </v-card>
</template>

<script>
const ip = 'nurecanteen'
const port = 'pp.ua';
const axios = require('axios')

export default {
  name: "Product",
  props: ['product', 'alertUnauthorized', 'updater', 'locale'],
  data() {
    return {
      dialog: false,
      duplicate: false,
      inCart: false,
      infoProduct: false,
      activeInfoProduct: false,
      countProducts: 1
    }
  },
  methods: {
    reRenderInfoBtn() {
      this.activeInfoProduct = false;
      this.infoProduct = false;
    },
    showBuyDialog(){
      if (localStorage['sid'] !== undefined && this.countProducts > 0) {
        this.dialog = true;
      }
      else if (localStorage['sid'] === undefined) {
        this.updater({
          value: true
        })
      }
    },
    // подтверждение нажатия на кнопку в корзину
    toCart() {
      this.dialog = false;
      axios.get(`https://api.${ip}.${port}/api/user`, {
        headers: {
          Authorization: 'Bearer ' + localStorage['sid']
        }
      }).then(resp => {
        axios({
          method: 'GET',
          url: `https://api.${ip}.${port}/api/basket/user/`+resp.data.id,
          headers: {
            Authorization: 'Bearer ' + localStorage['sid']
          }
        }).then(cart => {
          let ids = cart.data.map(i => i.product_id)
          if (ids.indexOf(this.product.product_id) !== -1) {
            let duplItem = cart.data[ids.indexOf(this.product.product_id)]
            console.log(duplItem)
            duplItem.count += parseInt(this.countProducts)
            axios({
              method: 'PUT',
              url: `https://api.${ip}.${port}/api/basket/`+duplItem.basket_id,
              data: {
                count: duplItem.count
              },
              headers: {
                Authorization: 'Bearer ' + localStorage['sid']
              }
            }).then(() => {
              this.inCart = true;
            })
          } else {
            axios({
              method: 'POST',
              url: `https://api.${ip}.${port}/api/basket/`,
              headers: {
                Authorization: 'Bearer ' + localStorage['sid']
              },
              data: {
                product_id: this.product.product_id,
                user: resp.data.id,
                count: this.countProducts
              }
            }).then(() => {
              this.inCart = true;
            })
          }
        })
      })
    }
  }
}
</script>

<style scoped>

</style>