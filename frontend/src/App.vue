<template>
  <v-app>
    <v-app-bar app>
      <v-app-bar-nav-icon @click="drawer = !drawer"></v-app-bar-nav-icon>
      <v-toolbar-title>
        <router-link to="/" class="text-decoration-none">NUFOOD</router-link>
      </v-toolbar-title>
      <v-btn icon :title="'График работы' + '\n' + '09:00 - 16:00'">
        <v-icon>watch_later</v-icon>
      </v-btn>
      <router-link to="/cart"><v-icon>shopping_cart</v-icon></router-link>
      <v-btn icon @click="showNotify">
        <v-icon>
          notifications
        </v-icon>
      </v-btn>
      <v-card v-model="notify" v-if="notify" style="overflow: auto; position: absolute; top: 55%; left: 14%;" height="350" width="350" max-width="350" max-height="350">
        <v-card-title style="font-size: 11pt">Уведомления</v-card-title>
        <v-divider></v-divider>
        <v-list v-for="n in 3" :key="n">
          <v-list-item>
            <v-list-item-content>
              <v-list-item-title>Акции</v-list-item-title>
              <v-card-text>Новинка заходи</v-card-text>
            </v-list-item-content>
          </v-list-item>
          <v-divider></v-divider>
        </v-list>
      </v-card>
      <v-form @submit.prevent="searching" style="margin-left: 70%">
            <v-row>
              <v-text-field
                  style="width: 100%"
                  v-model="searchText"
                  @input="getSearchItems"
                  color="primary"
                  placeholder="Поиск..."
                  single-line
                  hide-details
                  append-icon="search"
              ></v-text-field>
            </v-row>
      </v-form>
      <v-card
          v-model="searchText"
          v-if="searchText !== ''"
          style="position: absolute; top: 100%; left: 70.5%; width: 50%; overflow-x: hidden; overflow-y: auto"
          max-height="450"
          max-width="450"
          @mouseleave="leaveSearchBar"
      >
        <v-card
            flat
            v-for="item in getSearchItems.products"
            :key="item"
        >
          <v-img class="float-right" style="position: absolute; top: 10%; left: 85%" width="50" height="50" :src="item.image" :alt="item.name"></v-img>
          <router-link :to="'/product/'+item.id" class="text-decoration-none">
            <v-card-title>
              {{item.name}}
            </v-card-title>
          </router-link>
          <v-card-subtitle>{{item.price}} UAH</v-card-subtitle>
          <v-divider></v-divider>
        </v-card>
          <div v-if="getSearchItems.products.length <= 0">
             <v-card style="padding: 5% 0 5% 0">
                <v-icon style="text-align: center; display: block">warning</v-icon>
                <v-card-title style="justify-content: center">Результата нет</v-card-title>
             </v-card>
          </div>
      </v-card>
    </v-app-bar>
    <v-navigation-drawer app v-model="drawer">
      <v-list-item>
        <v-list-item-content class="title" style="margin-top: 3.2%">
          ИНФОРМАЦИЯ
        </v-list-item-content>
      </v-list-item>
      <v-divider></v-divider>
      <v-list dense nav>
        <v-list-item-group v-if="info.user_info.login" color="primary">
          <v-list-item>
            <v-list-item-icon>
              <v-icon>
                perm_identity
              </v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <router-link to="/cabinet" class="text-decoration-none">
                <v-list-item-title>Личный кабинет</v-list-item-title>
              </router-link>
            </v-list-item-content>
          </v-list-item>
          <v-list-item @click="logout">
            <v-list-item-icon>
              <v-icon>
                exit_to_app
              </v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>Выйти</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list-item-group>
        <v-list-item-group v-else color="primary">
          <v-list-item>
            <v-list-item-icon>
              <v-icon>
                login
              </v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <router-link to="/login" class="text-decoration-none">
                <v-list-item-title>
                  Войти
                </v-list-item-title>
              </router-link>
            </v-list-item-content>
          </v-list-item>
          <v-list-item>
            <v-list-item-icon>
              <v-icon>
                group_add
              </v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <router-link to="/register" class="text-decoration-none">
                <v-list-item-title>
                  Регистрация
                </v-list-item-title>
              </router-link>
            </v-list-item-content>
          </v-list-item>
        </v-list-item-group>
        <v-divider></v-divider>
        <v-list-item-group color="primary">
          <v-list-item>
            <v-list-item-icon>
              <v-icon>
                home
              </v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <router-link to="/" class="text-decoration-none">Главная</router-link>
            </v-list-item-content>
          </v-list-item>
        </v-list-item-group>
        <v-list-item-group color="primary">
          <v-list-item>
            <v-list-item-icon>
              <v-icon>
                fastfood
              </v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <router-link to="/products" class="text-decoration-none">Каталог</router-link>
            </v-list-item-content>
          </v-list-item>
        </v-list-item-group>
      </v-list>
    </v-navigation-drawer>
    <router-view style="background-color: #bfe9ff"/>
  </v-app>
</template>

<script>

export default {
    name: 'App',
    components: {
    },
    data: () => ({
        info: {
          user_info: {
            login: true,
            name: "tester",
            avatar: "https://media1.tenor.com/images/5af3ea60a7715372c434c5ff1877485c/tenor.gif?itemid=15299640",
            university: ""
          },
          filters: [
            {
              id: 0,
              name: "Test1"
            },
            {
              id: 1,
              name: "Test2"
            },
            {
              id: 2,
              name: "Test3"
            },
            {
              id: 3,
              name: "Test4"
            }
          ],
          products: [
            {
              id: 0,
              name: "Пирожок",
              price: 54,
              currency: "UAH",
              description: "вкусный",
              available_count: 20,
              image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
            },
            {
              id: 1,
              name: "Пирожок1",
              price: 54,
              currency: "UAH",
              description: "вкусный",
              available_count: 20,
              image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
            },
            {
              id: 2,
              name: "Пирожок2",
              price: 54,
              currency: "UAH",
              description: "вкусный",
              available_count: 20,
              image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
            },
            {
              id: 3,
              name: "Пирожок3",
              price: 54,
              currency: "UAH",
              description: "вкусный",
              available_count: 20,
              image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
            },
            {
              id: 4,
              name: "Пирожок4",
              price: 54,
              currency: "UAH",
              description: "вкусный",
              available_count: 20,
              image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
            },
            {
              id: 5,
              name: "Пирожок5",
              price: 54,
              currency: "UAH",
              description: "вкусный",
              available_count: 20,
              image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
            },
            {
              id: 6,
              name: "Пирожок6",
              price: 54,
              currency: "UAH",
              description: "вкусный",
              available_count: 20,
              image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
            },
            {
              id: 7,
              name: "Пирожок7",
              price: 54,
              currency: "UAH",
              description: "вкусный",
              available_count: 20,
              image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
            },
            {
              id: 8,
              name: "Пирожок8",
              price: 54,
              currency: "UAH",
              description: "вкусный",
              available_count: 20,
              image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
            }
          ]
        },
        drawer: false,
        notify: false,
        searchText: ""
    }),
    computed: {
      getSearchItems() {
        // sending GET to DB
        return {
          products: []
        }
        // return {
        //   products: [
        //     {
        //       id: 0,
        //       name: "Пирожок",
        //       price: 54,
        //       currency: "UAH",
        //       description: "вкусный",
        //       available_count: 20,
        //       image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
        //     },
        //     {
        //       id: 0,
        //       name: "Пирожок",
        //       price: 54,
        //       currency: "UAH",
        //       description: "вкусный",
        //       available_count: 20,
        //       image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
        //     },
        //     {
        //       id: 0,
        //       name: "Пирожок",
        //       price: 54,
        //       currency: "UAH",
        //       description: "вкусный",
        //       available_count: 20,
        //       image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
        //     },
        //     {
        //       id: 0,
        //       name: "Пирожок",
        //       price: 54,
        //       currency: "UAH",
        //       description: "вкусный",
        //       available_count: 20,
        //       image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
        //     }
        //   ]
        // }
      }
    },
    methods: {
        showNotify() {
            this.$nextTick(() => {
                this.notify = !this.notify;
            });
        },
        searching() {
            alert(0)
            localStorage.search_query = this.searchText
            window.location.href = '/search'
        },
        leaveSearchBar() {
            this.searchText = ""
            this.searchItems = []
        },
        logout() {
            // breaking session
        }
    }
};
</script>

<style>
</style>