<template>
  <v-list-group>
    <template v-slot:activator>
      <v-img :src="product.image" width="80" height="80" style="margin-right: 1%"></v-img>
      <v-list-item-title>
        {{product.name}}
        <v-btn icon @click="delDialog = true">
          <v-icon>
            close
          </v-icon>
        </v-btn>
        <v-btn icon @click="editDialog = true">
          <v-icon>
            edit
          </v-icon>
        </v-btn>
      </v-list-item-title>
      <v-dialog v-model="editDialog" width="650">
        <v-card>
          <v-card-title>
            {{locale.tabs.tab1.context.editMainBtnTitle}} "{{product.name}}"
            <v-spacer></v-spacer>
            <v-img width="50" height="140" :src="product.image" v-model="product.image"></v-img>
          </v-card-title>
          <v-divider></v-divider>
          <v-form>
            <v-container>
              <v-row>
                <v-col>
                  <v-text-field
                      v-model="product.name"
                      :label="locale.tabs.tab1.context.labels[0]"
                      :rules="textRules"
                  ></v-text-field>
                </v-col>
                <v-col>
                  <v-select
                      :items="categories"
                      v-model="product.category"
                      :label="locale.tabs.tab1.context.labels[1]"
                  ></v-select>
                </v-col>
              </v-row>
              <v-row>
                <v-col>
                  <v-text-field
                      v-model="product.price"
                      type="number"
                      :label="locale.tabs.tab1.context.labels[2]"
                      :rules="numRules"
                  ></v-text-field>
                </v-col>
                <v-col>
                  <v-text-field
                      v-model="product.available_count"
                      type="number"
                      :label="locale.tabs.tab1.context.labels[3]"
                      :rules="numRules"
                  ></v-text-field>
                </v-col>
              </v-row>
              <v-row>
                <v-col>
                  <v-text-field
                      :label="locale.tabs.tab1.context.labels[4]"
                      v-model="product.description"
                      :rules="textRules"
                  ></v-text-field>
                </v-col>
              </v-row>
              <v-row>
                <v-col>
                  <v-file-input
                      accept="image/png, image/jpeg, image/jpg"
                      v-model="product.image"
                      :label="locale.tabs.tab1.context.labels[5]"
                      style="margin: 0 2% 0 2%"
                      @change="uploadImg"
                  ></v-file-input>
                </v-col>
              </v-row>
            </v-container>
            <v-btn @click="submitEditForm" color="success" block>
              {{locale.tabs.tab1.context.editBtn}}
            </v-btn>
          </v-form>
        </v-card>
      </v-dialog>
      <v-dialog v-model="delDialog" width="280">
        <v-card>
          <v-card-title>
            Удалить этот продукт ?
          </v-card-title>
          <v-card-actions style="justify-content: center; display: flex">
            <v-btn
                color="red"
                text
                @click="delDialog = false"
            >
              Нет
            </v-btn>
            <v-btn
                color="success"
                text
                @click="delProduct"
            >
              Да
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
      <v-snackbar v-model="finishEdit" color="success" top timeout="2000">
        {{locale.tabs.tab1.context.alerts[0]}} {{product.name}} {{locale.tabs.tab1.context.alerts[1]}}
        <template>
          <v-btn text @click="finishEdit = false">
            {{locale.tabs.tab1.context.alerts[3]}}
          </v-btn>
        </template>
      </v-snackbar>
      <v-snackbar v-model="finishDel" color="success" top timeout="2000">
        {{locale.tabs.tab1.context.alerts[2]}}
        <template>
          <v-btn text @click="finishDel = false">
            {{locale.tabs.tab1.context.alerts[3]}}
          </v-btn>
        </template>
      </v-snackbar>
      <v-item-group>
        <v-list-item-title>{{product.price}} UAH</v-list-item-title>
      </v-item-group>
    </template>
    <v-container>
      <v-row>
        <v-col cols="6">
          <v-text-field
              :label="locale.tabs.tab1.context.infoProduct[0]"
              v-model="product.category"
              rounded
              filled
              readonly
          />
        </v-col>
        <v-col cols="6">
          <v-text-field
              :label="locale.tabs.tab1.context.infoProduct[1]"
              v-model="product.description"
              rounded
              filled
              readonly
          />
        </v-col>
        <v-col>
          <v-text-field
              :label="locale.tabs.tab1.context.infoProduct[2]"
              v-model="product.available_count"
              rounded
              filled
              readonly
          />
        </v-col>
        <v-col>
          <v-text-field
              :label="locale.tabs.tab1.context.infoProduct[3]"
              v-model="product.calories"
              rounded
              filled
              readonly
          />
        </v-col>
        <v-col>
          <v-text-field
              :label="locale.tabs.tab1.context.infoProduct[4]"
              v-model="product.weight"
              rounded
              filled
              readonly
          />
        </v-col>
        <v-col>
          <v-text-field
              :label="locale.tabs.tab1.context.infoProduct[5]"
              v-model="product.total_count"
              rounded
              filled
              readonly
          />
        </v-col>
      </v-row>
    </v-container>
  </v-list-group>
</template>

<script>
const ip = 'localhost'
const port = 25016;
const axios = require('axios')

export default {
  name: "ProductInSaleItem",
  props: ['product', 'updater', 'locale'],
  data() {
    return {
      editDialog: false,
      delDialog: false,
      categories: [
        'Первое блюдо',
        'Второе блюдо',
        'Пряности',
        'Напитки',
      ],
      finishEdit: false,
      finishDel: false,
      textRules: [
        v => !!v || this.locale.tabs.tab3.context.textRules[0],
        v => v.length !== 0 || this.locale.tabs.tab3.context.textRules[1]
      ],
      numRules: [
          v => v > 0 || this.locale.tabs.tab3.context.numRules
      ]
    }
  },
  methods: {
    uploadImg(ev) {
      if (ev.name !== undefined) {
        let reader = new FileReader();
        reader.onload = (e) => {
          console.log(e)
          this.product.image = e.target.result;
        }
        reader.readAsDataURL(ev)
      }
    },
    delProduct() {
      axios({
        method: 'DELETE',
        url: `http://${ip}:${port}/api/product/`+this.product.product_id,
        headers: {
          Authorization: 'Bearer ' + localStorage['sid']
        }
      }).then(() => {
        this.finishDel = true;
      })
    },
    submitEditForm() {
      axios({
        method: 'PUT',
        url: `http://${ip}:${port}/api/product/`+this.product.product_id,
        headers: {
          Authorization: 'Bearer ' + localStorage['sid']
        }
      }).then(() => {
        axios({
          method: 'GET',
          url: `http://${ip}:${port}/api/product/`+this.product.product_id,
          headers: {
            Authorization: 'Bearer ' + localStorage['sid']
          }
        }).then(resp => {
          this.product = resp.data
          this.finishEdit = true;
        })
      })
    }
  }
}
</script>

<style scoped>

</style>