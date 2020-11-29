<template>
  <v-card style="margin: 0 25% 0 25%" flat rounded outlined>
    <v-card-actions title="Кол-во этого блюда" style="justify-content: center; display: flex">
      <v-btn icon @click="delCartItem">
        <v-icon>
          remove
        </v-icon>
      </v-btn>
      {{cartItem.count}} шт.
      <v-btn icon>
        <v-icon>
          add
        </v-icon>
      </v-btn>
    </v-card-actions>
    <v-list-group>
      <template v-slot:activator>
        <v-list-item-title>{{cartItem.name}}</v-list-item-title>
        <v-item-group>
          <v-list-item-title>{{locales.infoCart[0]}} {{cartItem.price}} {{locales.infoCart[1]}}</v-list-item-title>
        </v-item-group>
      </template>
      <v-list-item-group>
        <v-container>
          <v-row>
            <v-col>
              <v-text-field
                  :label="locales.infoCart[2]"
                  readonly
                  v-model="cartItem.description"
                  filled
                  rounded
              ></v-text-field>
            </v-col>
            <v-col>
              <v-text-field
                  :label="locales.infoCart[3]"
                  readonly
                  v-model="cartItem.category"
                  filled
                  rounded
              ></v-text-field>
            </v-col>
          </v-row>
        </v-container>
      </v-list-item-group>
    </v-list-group>
  </v-card>
</template>

<script>
const ip = 'localhost'
const port = 25016;
const axios = require('axios')

export default {
  name: "CartItem",
  props: ['cartItem', 'allCart', 'locales', 'updater'],
  methods: {
    delCartItem() {
      if (this.cartItem.count <= 1) {
        axios({
          method: 'DELETE',
          url: `http://${ip}:${port}/api/basket/`+this.cartItem.basket_id,
        }).then(() => {
          axios({
            method: 'GET',
            url: `http://${ip}:${port}/api/user`,
            headers: {
              Authorization: 'Bearer ' + localStorage['sid']
            }
          }).then(user => {
            axios({
              method: 'GET',
              url: `http://${ip}:${port}/api/basket/user/` + user.data.id,
            }).then(cart => {
              console.log(cart.data)
              this.updater({
                items: cart.data
              })
              localStorage['cart'] = JSON.stringify(cart.data)
            })
          })
        })
      }
      else if (this.cartItem.count > 1) {
        this.cartItem.count--
        axios({
          method: 'PUT',
          url: `http://${ip}:${port}/api/basket/`+this.cartItem.basket_id,
          data: {
            count: this.cartItem.count
          }
        }).then(resp => {
          this.updater({
            items: resp.data
          })
          localStorage['cart'] = JSON.stringify(
              JSON.parse(localStorage['cart']).filter(i => {
                if (i.basket_id === this.cartItem.basket_id) {
                  i.count--;
                  return i;
                }
              })
          )
        })
      }
    }
  }
}
</script>

<style scoped>

</style>