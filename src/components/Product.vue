<template>
    <v-card max-width="220px" class="product">
        <div :data-id="product.id">
            <img :src="product.image" :alt="product.name"> <br/>
            <span>{{product.category}}</span>
            <span><a :to="'/product/'+product.id">{{ product.name }}</a></span> <br/>
            <span>Доступно: {{ product.available_count }}</span> <br/>
            <p>
                Описание:
                {{ product.description }}
            </p>
            <span>Цена: {{ product.price }} UAH</span>
            <br><br>
            <v-btn @click="buyProduct">В КОРЗИНУ</v-btn>
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
                inCart: false
            }
        },
        methods: {
            buyProduct() {
                // parseInt(ev.path[0].attributes[3].value);
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
                let cur_count = isNaN(localStorage.count_cart)?0:localStorage.count_cart;
                cur_count++;
                localStorage.count_cart = cur_count;
                let info = document.cookie === undefined || document.cookie === ""?[]:document.cookie;
                if (info.length >= 10) info = JSON.parse(info.split("=")[1]);
                info.push(
                    {
                        id: this.product.id,
                        name: this.product.name,
                        category: this.category,
                        description: this.product.description,
                        price: this.product.price
                    }
                );
                document.cookie = "cart_items=" + JSON.stringify(info)+";";
                this.$nextTick(() => {
                    this.inCart = true;
                });
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