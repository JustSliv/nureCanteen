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
            <router-link class="text-decoration-none" :to="'/product/'+product.id">
              {{product.name}}
            </router-link> <br/> {{product.price}} UAH
          </v-card-subtitle>
          <v-btn @click="buyProduct" color="success" width="100%">В КОРЗИНУ</v-btn>
        </div>
      </template>
      <v-card>
        <v-card-text>
          <b>Категория:</b> {{product.category}} <br/>
          <b>Доступно:</b> {{ product.available_count }} шт. <br/>
          <b>Описание:</b> {{ product.description }} <br/>
        </v-card-text>
      </v-card>
    </v-menu>
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
            Продолжить
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
      Данный товар уже в корзине
    </v-snackbar>
    <v-snackbar
        top
        v-model="inCart"
        v-if="inCart"
        multi-line
        timeout="2000"
        color="success"
    >
      Товар добавлен в корзине
      <v-btn text to="/cart">В корзину</v-btn>
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
      infoProduct: false,
      activeInfoProduct: false
    }
  },
  methods: {
    reRenderInfoBtn() {
      this.activeInfoProduct = false;
      this.infoProduct = false;
    },
    buyProduct() {
      let choose = false;
      let id = this.product.id;
      let info = localStorage['cart'] === undefined?[]:JSON.parse(localStorage['cart']);
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

</style>