<template>
  <v-app>
    <v-app-bar app>
      <v-app-bar-nav-icon @click="drawer = !drawer"></v-app-bar-nav-icon>
      <v-toolbar-title @click="$router.push('/')" style="cursor: pointer">
        NUFOOD
      </v-toolbar-title>
      <v-btn icon :title="curLocale.workingTime">
        <v-icon>watch_later</v-icon>
      </v-btn>
      <router-link to="/cart"><v-icon>shopping_cart</v-icon></router-link>
      <v-menu offset-y>
        <template v-slot:activator="{on, attrs}">
          <v-btn icon v-on="on" v-bind="attrs">
            <v-icon>
              notifications
            </v-icon>
          </v-btn>
        </template>
        <v-card max-width="350" max-height="350">
          <v-card-title style="font-size: 11pt">{{curLocale.notification.title}}</v-card-title>
          <v-divider></v-divider>
          <v-list v-for="n in 5" :key="n">
            <v-list-item>
              <v-list-item-content>
                <v-list-item-title>{{ curLocale.notification.stocksTitle }}</v-list-item-title>
                <v-card-text>{{curLocale.notification.contentStocks}}</v-card-text>
              </v-list-item-content>
            </v-list-item>
            <v-divider></v-divider>
          </v-list>
        </v-card>
      </v-menu>
      <v-menu v-model="translate" offset-y>
        <template v-slot:activator="{on, attrs}">
          <v-btn v-on="on" v-bind="attrs" icon :title="curLocale.translation.tip">
            <v-icon large>translate</v-icon>
          </v-btn>
        </template>
        <v-card>
          <v-card-subtitle>{{curLocale.translation.title}}</v-card-subtitle>
          <v-divider></v-divider>
          <v-list shaped>
            <v-list-item-group color="indigo">
              <v-list-item @click="changeLangEN">
                <v-list-item-content>
                  <v-list-item-title>
                    {{curLocale.translation.langs[0]}}
                  </v-list-item-title>
                </v-list-item-content>
                <v-list-item-icon>
                  <v-icon>chevron_right</v-icon>
                </v-list-item-icon>
              </v-list-item>
              <v-list-item @click.prevent="changeLangRU">
                <v-list-item-content>
                  <v-list-item-title>
                    {{curLocale.translation.langs[1]}}
                  </v-list-item-title>
                </v-list-item-content>
                <v-list-item-icon>
                  <v-icon>chevron_right</v-icon>
                </v-list-item-icon>
              </v-list-item>
              <v-list-item @click="changeLangUA">
                <v-list-item-content v-model="langThird">
                  <v-list-item-title>
                    {{curLocale.translation.langs[2]}}
                  </v-list-item-title>
                </v-list-item-content>
                <v-list-item-icon>
                  <v-icon>chevron_right</v-icon>
                </v-list-item-icon>
              </v-list-item>
            </v-list-item-group>
          </v-list>
        </v-card>
      </v-menu>
      <v-spacer></v-spacer>
      <v-form>
        <v-container>
          <v-row>
            <v-col>
              <v-text-field
                  style="width: 100%"
                  v-model="searchText"
                  @input="getSearchItems"
                  color="primary"
                  :placeholder="curLocale.search.title"
                  single-line
                  hide-details
                  append-icon="search"
              ></v-text-field>
            </v-col>
          </v-row>
        </v-container>
      </v-form>
      <v-card
          v-model="searchText"
          v-if="searchText !== ''"
          style="position: absolute; top: 100%; left: 70.5%; width: 50%; overflow-x: hidden; overflow-y: auto"
          max-height="450"
          max-width="450"
          @mouseleave="leaveSearchBar"
      >
        <div style="text-align: center; display: block; margin: 5%">
          <v-hover v-slot:default="{hover}">
            <v-btn outlined color="primary" @click="searchView = 'default'">
              <span v-if="hover">
                {{curLocale.search.searchView[0]}}
              </span>
              <v-icon>
                view_agenda
              </v-icon>
            </v-btn>
          </v-hover>
          <v-hover v-slot:default="{hover}">
            <v-btn outlined color="primary" @click="searchView = 'column'">
              <span v-if="hover">
                {{curLocale.search.searchView[1]}}
              </span>
              <v-icon>
                view_column
              </v-icon>
            </v-btn>
          </v-hover>
        </div>
        <v-divider></v-divider>
        <div v-if="searchView === 'default'">
          <v-card
              flat
              v-for="(item, i) in searchItems.products"
              :key="i"
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
        </div>
        <div v-if="searchView === 'column'">
          <v-container>
            <v-row>
              <v-col cols="4" v-for="(item, j) in searchItems.products" :key="j">
                <v-card>
                  <v-img :src="item.image"></v-img>
                  <v-card-text>
                    <router-link :to="'/product/'+item.id" class="text-decoration-none">
                      {{item.name}} <br/>
                    </router-link>
                    {{item.price}} {{item.currency}}
                  </v-card-text>
                </v-card>
              </v-col>
            </v-row>
          </v-container>
        </div>
        <div v-if="searchItems.products.length <= 0">
             <v-card style="padding: 5% 0 5% 0">
                <v-icon style="text-align: center; display: block">warning</v-icon>
                <v-card-title style="justify-content: center">{{curLocale.search.searchNotFound}}</v-card-title>
             </v-card>
          </div>
      </v-card>
    </v-app-bar>
    <v-navigation-drawer app v-model="drawer">
      <v-list-item>
        <v-list-item-content class="title" style="margin-top: 3.2%">
          {{curLocale.drawer.title}}
        </v-list-item-content>
      </v-list-item>
      <v-divider></v-divider>
      <v-list dense nav flat>
        <v-list-item-group v-if="validAuth">
          <v-list-item>
            <v-list-item-icon>
              <v-icon>
                perm_identity
              </v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title @click="$router.push('/cabinet')">{{curLocale.drawer.listItem[0]}}</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
          <v-list-item @click="logout">
            <v-list-item-icon>
              <v-icon>
                exit_to_app
              </v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>{{curLocale.drawer.listItem[1]}}</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list-item-group>
        <v-list-item-group v-else>
          <v-list-item>
            <v-list-item-icon>
              <v-icon>
                login
              </v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title @click="$router.push('/auth')">
                {{curLocale.drawer.listItem[2]}}
              </v-list-item-title>
            </v-list-item-content>
          </v-list-item>
          <v-list-item>
            <v-list-item-icon>
              <v-icon>
                group_add
              </v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title @click="$router.push('/register')">
                {{curLocale.drawer.listItem[3]}}
              </v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list-item-group>
        <v-divider></v-divider>
        <v-list-item-group>
          <v-list-item>
            <v-list-item-icon>
              <v-icon>
                home
              </v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title @click="$router.push('/')">{{curLocale.drawer.listItem[4]}}</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list-item-group>
        <v-list-item-group>
          <v-list-item>
            <v-list-item-icon>
              <v-icon>
                fastfood
              </v-icon>
            </v-list-item-icon>
            <v-list-item-content>
<!--              <router-link to="/products" class="text-decoration-none">Каталог</router-link>-->
                <v-list-item-title @click="$router.push('/products')">{{curLocale.drawer.listItem[5]}}</v-list-item-title>
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
          price: 4,
          currency: "UAH",
          description: "вкусный",
          available_count: 20,
          image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
        },
        {
          id: 2,
          name: "Пирожок2",
          price: 51,
          currency: "UAH",
          description: "вкусный",
          available_count: 20,
          image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
        },
        {
          id: 3,
          name: "Пирожок3",
          price: 34,
          currency: "UAH",
          description: "вкусный",
          available_count: 20,
          image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
        },
        {
          id: 4,
          name: "Пирожок4",
          price: 14,
          currency: "UAH",
          description: "вкусный",
          available_count: 20,
          image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
        },
        {
          id: 5,
          name: "Пирожок5",
          price: 74,
          currency: "UAH",
          description: "вкусный",
          available_count: 20,
          image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
        },
        {
          id: 6,
          name: "Пирожок6",
          price: 55,
          currency: "UAH",
          description: "вкусный",
          available_count: 20,
          image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
        },
        {
          id: 7,
          name: "Пирожок7",
          price: 53,
          currency: "UAH",
          description: "вкусный",
          available_count: 20,
          image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
        },
        {
          id: 8,
          name: "Пирожок8",
          price: 23,
          currency: "UAH",
          description: "вкусный",
          available_count: 20,
          image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
        }
      ]
    },
    drawer: false,
    notify: false,
    searchText: "",
    searchItems: null,
    searchView: 'default',
    translate: false,
    curLocale: {},
    locales: {
      'en-EN': {
        workingTime: 'Working time ' + '\n' + '09:00 - 16:00',
        notification: {
          title: 'Notification',
          stocksTitle: 'Stocks',
          contentStocks: 'New goods!!'
        },
        translation: {
          tip: 'Choose language for interface',
          title: 'Language of interface',
          langs: [
            'English', 'Russian', 'Ukrainian'
          ]
        },
        search: {
          title: 'Search...',
          searchView: [
            'View solid', 'View columns'
          ],
          searchNotFound: 'Not Found'
        },
        drawer: {
          title: 'INFORMATION',
          listItem: [
            'Personal Area', 'Logout', 'Login', 'Sign-Up', 'Main Page', 'Catalog'
          ]
        }
      },
      'ru-RU': {
        workingTime: 'График работы ' + '\n' + '09:00 - 16:00',
        notification: {
          title: 'Уведомления',
          stocksTitle: 'Акции',
          contentStocks: 'Новинка заходи'
        },
        translation: {
          tip: 'Выбор языка страницы',
          title: 'Язык интерфейса',
          langs: [
              'English', 'Русский', 'Украинский'
          ]
        },
        search: {
          title: 'Поиск...',
          searchView: [
              'Вид сплошные', 'Вид колонок'
          ],
          searchNotFound: 'Результата нет'
        },
        drawer: {
          title: 'ИНФОРМАЦИЯ',
          listItem: [
              'Личный кабинет', 'Выйти', 'Войти', 'Регистрация', 'Главная', 'Каталог'
          ]
        }
      },
      'ua-UA': {
        workingTime: 'Часи праці ' + '\n' + '09:00 - 16:00',
        notification: {
          title: 'Повідомлення',
          stocksTitle: 'Акції',
          contentStocks: 'Нові товари'
        },
        translation: {
          tip: 'Вибір мови інтерфейсу',
          title: 'Мова інтерфейсу',
          langs: [
            'English', 'Російська', 'Українська'
          ]
        },
        search: {
          title: 'Пошук...',
          searchView: [
            'Вид суцільні', 'Вид колонок'
          ],
          searchNotFound: 'Результата немає'
        },
        drawer: {
          title: 'ІНФОРМАЦІЯ',
          listItem: [
            'Особистий кабінет', 'Вийти', 'Увійти', 'Реєстрація', 'Головна', 'Каталог'
          ]
        }
      }
    },
    langOne: false,
    langSecond: false,
    langThird: false,
    ind: 0,
    infoCols: null
  }),
  computed: {
    validAuth() {
      return !(localStorage['sid'] === undefined || localStorage['sid'] === null);
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
    changeLangEN() {
      localStorage.setItem('lang', 'en-EN')
      this.curLocale = this.locales["en-EN"];
      this.$router.go(this.$router.currentRoute.path);
    },
    changeLangRU() {
      localStorage.setItem('lang', 'ru-RU')
      this.curLocale = this.locales["ru-RU"];
      this.$router.go(this.$router.currentRoute.path);
    },
    changeLangUA() {
      localStorage.setItem('lang', 'ua-UA')
      this.curLocale = this.locales["ua-UA"];
      this.$router.go(this.$router.currentRoute.path);
    },
    getSearchItems() {
      // sending GET to DB

      let regex = new RegExp(this.searchText, 'i');
      let items = this.info.products.filter(i => regex.test(i.name))
      this.searchItems = items === undefined?[]:{products: items};
    },
    leaveSearchBar() {
      this.searchItems = []
    },
    logout() {
      localStorage.removeItem('sid')
      window.location.href = '/'
    }
  }
};
</script>

<style>
</style>