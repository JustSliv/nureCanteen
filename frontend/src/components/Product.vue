<template>
  <v-card width="150px" :data-id="product.id">
    <div style="justify-content: center; display: flex">
      <v-btn icon x-large title="Описание товара" @click="infoProduct = !infoProduct">
        <v-icon x-large>
          keyboard_arrow_down
        </v-icon>
      </v-btn>
    </div>
    <div v-if="infoProduct">
      <v-card-text>
        <b>Категория:</b> {{product.category}} <br/>
        <b>Доступно:</b> {{ product.available_count }} шт. <br/>
        <b>Описание:</b> {{ product.description }} <br/>
      </v-card-text>
    </div>
    <div v-else>
      <v-img :src="product.image" :alt="product.name"></v-img>
      <v-card-subtitle
          style="cursor: pointer"
          @click="$router.push('/product/'+product.id)"
      >
        {{product.name}} <br/> {{product.price}} UAH
      </v-card-subtitle>
      <v-btn @click="buyProduct" color="success" width="100%">В КОРЗИНУ</v-btn>
    </div>
    <v-dialog v-model="dialog" max-width="320" v-if="dialog">
      <v-card>
        <v-card-title class="headline">
          {{product.name}}
        </v-card-title>
        <v-card-text>
          Товар будет занесен к вам в корзину
        </v-card-text>
        <v-card-actions>
          <v-btn
              color="red"
              text
              @click="dialog = false"
          >
            Отмена
          </v-btn>
          <v-btn
              color="green"
              text
              @click="toCart"
          >
            Продолжить покупки
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-snackbar
        top
        v-model="duplicate"
        v-if="duplicate"
        multi-line
        timeout="4000"
        color="success"
    >
      Данный товар уже в корзине
    </v-snackbar>
    <v-snackbar
        top
        v-model="inCart"
        v-if="inCart"
        multi-line
        timeout="4000"
        color="success"
    >
      Товар добавлен в корзине
    </v-snackbar>
  </v-card>
</template>

<script>
export default {
  name: "Product",
  props: {
    product: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      dialog: false,
      duplicate: false,
      inCart: false,
      infoProduct: false
    }
  },
  methods: {
    buyProduct() {
      let id = this.product.id;
      let info = document.cookie === undefined || document.cookie === ""?[]:document.cookie;
      if (info.length >= 10) info = info.split('=')[0]==="cart_items"?JSON.parse(info.split('=')[1].replace(';', '').split(' ')[0]):[];
      let choose = false;
      for (let i=0;i<info.length;i++) {
        if (id === info[i].id) {
          choose = true;
        }
      }
      if (choose === true) {
        this.$nextTick(() => {
          this.duplicate = true;
        });
      } else {
        this.$nextTick(() => {
          this.dialog = true;
        });
      }
    },
    toCart() {
      this.dialog = false;
      // let cur_count = isNaN(localStorage.count_cart)?0:localStorage.count_cart;
      // cur_count++;
      // localStorage.count_cart = cur_count;
      let isAuth = false;
      if (isAuth) {
        return 0;
      } else {
        let info = localStorage['cart'] === undefined?[]:localStorage['cart']
        if (info.length >= 10) info = JSON.parse(info)
        info.push(
            {
              id: this.product.id,
              name: this.product.name,
              category: this.product.category,
              description: this.product.description,
              price: this.product.price
            }
        )
        localStorage['cart'] = JSON.stringify(info)
      }
      // let info = document.cookie === undefined || document.cookie === ""?[]:document.cookie;
      // if (info.length >= 10) info = JSON.parse(info.split("=")[1]);
      // info.push(
      //     {
      //         id: this.product.id,
      //         name: this.product.name,
      //         category: this.product.category,
      //         description: this.product.description,
      //         price: this.product.price
      //     }
      // );
      // document.cookie = "cart_items=" + JSON.stringify(info)+";";
      this.$nextTick(() => {
        this.inCart = true;
      });

      // sending POST to update product count
      this.product.available_count--;
    }
  }
}
</script>

<style scoped>
.product {
  padding: 10%;
  margin: 0 0 3% 3%;
}
</style>