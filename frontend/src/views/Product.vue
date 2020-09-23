<template>
  <v-app>
      <v-card style="margin: 12% 20% 0 20%; padding: 2% 0 0 0;">
          <v-card-title>{{getProduct.name}}</v-card-title>
          <v-card-subtitle>{{getProduct.category}}</v-card-subtitle>
          <v-divider></v-divider>
            <v-row>
              <v-col cols="12" md="6" sm="4">
                <v-img :src="getProduct.image" style="margin: 2%" width="280" height="280"></v-img>
              </v-col>
              <v-col cols="6" md="6">
                <v-row style="margin-left: 55%">
                  <v-card-text><b>Цена:</b> {{getProduct.price}} UAH</v-card-text>
                </v-row>
                <v-row style="margin-left: 55%">
                  <v-card-text><b>Описание:</b> <br/>{{getProduct.description}}</v-card-text>
                </v-row>
                <v-row style="margin-left: 55%">
                  <v-card-text>
                    <b>Кол-во, шт:</b>
                    <v-text-field
                        type="number"
                        style="max-width: 20%; position: absolute"
                    ></v-text-field>
                  </v-card-text>
                </v-row>
              </v-col>
            </v-row>
            <v-divider></v-divider>
            <v-card-title>
              Отзывы
              <v-btn title="Добавить отзыв" @click="addReview" icon>
                <v-icon>
                  add_circle_outline
                </v-icon>
              </v-btn>
            </v-card-title>
            <v-dialog v-model="newReview" max-width="550">
                <v-card>
                  <v-card-title>Добавить отзыв для {{getProduct.name}}</v-card-title>
                  <v-divider></v-divider>
                  <form>
                    <v-container style="padding: 0 4% 0 4%">
                      <v-row>
                        <v-text-field
                            disabled
                            v-model="fullname"
                        ></v-text-field>
                      </v-row>
                      <v-row>
                        <v-text-field
                            label="Введите отзыв"
                            v-model="contentReview"
                            :rules="textRules"
                            required
                            counter="250"
                        ></v-text-field>
                      </v-row>
                    </v-container>
                    <v-btn width="100%" color="success" text>Завершить</v-btn>
                  </form>
                </v-card>
            </v-dialog>
            <v-divider></v-divider>
            <v-card v-model="getProduct.comments" flat>
                <div v-for="item in getProduct.comments" :key="item">
                    <v-card-text>
                        <v-card-title>
                          {{item.fname}} {{item.sname}}
                          <span style="font-size: 10pt; margin-left: 80%">{{item.date}}</span>
                        </v-card-title>
                        <v-card-text style="margin-left: 5%">
                            {{item.msg}}
                        </v-card-text>
                    </v-card-text>
                </div>
                <v-divider></v-divider>
                <div v-if="getProduct.comments.length <= 0">
                    <v-card-title>Отзывы отсутствуют</v-card-title>
                </div>
            </v-card>
            <v-btn color="success" width="100%" top absolute style="margin-top: -1.8%">
              В Корзину
            </v-btn>
      </v-card>
  </v-app>
</template>

<script>
export default {
  name: "Product",
  data() {
    return {
      newReview: false,
      contentReview: "",
      fullname: "Tim Livr"
    }
  },
  methods: {
    addReview() {
      this.newReview = true;
    }
  },
  computed: {
      getProduct() {
          return {
              id: 0,
              name: "Пирожок",
              price: 54,
              currency: "UAH",
              category: "Духовный",
              description: "вкусный",
              available_count: 20,
              comments: [
                  {
                      comment_id: 0,
                      user_id: 0,
                      date: "09.09.2020",
                      fname: "Tim",
                      sname: "Livr",
                      msg: "Вкусна <3"
                  },
                {
                  comment_id: 0,
                  user_id: 0,
                  date: "09.09.2020",
                  fname: "Tim",
                  sname: "Livr",
                  msg: "Вкусна <3"
                },
                {
                  comment_id: 0,
                  user_id: 0,
                  date: "09.09.2020",
                  fname: "Tim",
                  sname: "Livr",
                  msg: "Вкусна <3"
                },
                {
                  comment_id: 0,
                  user_id: 0,
                  date: "09.09.2020",
                  fname: "Tim",
                  sname: "Livr",
                  msg: "Вкусна <3"
                },
              ],
              image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
          }
          // sending GET
          // return {};
      }
  }
}
</script>

<style scoped>

</style>