<template>
  <v-card style="margin: 0 25% 0 25%" flat rounded outlined>
    <v-card-actions title="Кол-во этого блюда" style="justify-content: center; display: flex">
      <v-btn icon @click="delCartItem">
        <v-icon>
          remove
        </v-icon>
      </v-btn>
      {{cartItem.count}} шт.
      <v-btn icon @click="addCartItem">
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
const ip = 'nurecanteen'
const port = 'pp.ua';
const axios = require('axios')

export default {
  name: "CartItem",
  props: ['cartItem', 'allCart', 'locales', 'updater'],
  methods: {
    addCartItem() {
      this.cartItem.count++;
      axios({
        method: 'PUT',
        url: `https://${ip}.${port}/api/basket/`+this.cartItem.basket_id,
        data: {
          count: this.cartItem.count
        },
        headers: {
          Authorization: 'Bearer ' + localStorage['sid']
        }
      })
      axios({
        method: 'GET',
        url: `https://${ip}.${port}/api/basket/` + this.cartItem.basket_id,
        headers: {
          Authorization: 'Bearer ' + localStorage['sid']
        }
      })
    },
    delCartItem() {
      if (this.cartItem.count <= 1) {
        axios({
          method: 'DELETE',
          url: `https://${ip}.${port}/api/basket/`+this.cartItem.basket_id,
          headers: {
            Authorization: 'Bearer ' + localStorage['sid']
          }
        }).then(() => {
          axios({
            method: 'GET',
            url: `https://${ip}.${port}/api/user`,
            headers: {
              Authorization: 'Bearer ' + localStorage['sid']
            }
          }).then(user => {
            axios({
              method: 'GET',
              url: `https://${ip}.${port}/api/basket/user/` + user.data.id,
              headers: {
                Authorization: 'Bearer ' + localStorage['sid']
              }
            }).then(cart => {
              this.updater({
                items: cart.data
              })
            })
          })
        })
      }
      else if (this.cartItem.count > 1) {
        this.cartItem.count--
        axios({
          method: 'PUT',
          url: `https://${ip}.${port}/api/basket/`+this.cartItem.basket_id,
          data: {
            count: this.cartItem.count
          },
          headers: {
            Authorization: 'Bearer ' + localStorage['sid']
          }
        })
      }
    }
  }
}
</script>

<style scoped>

</style>