<template>
  <v-app style="z-index: 5">
    <v-card>
      <v-toolbar style="justify-content: center; display: flex">
        <v-menu v-model="translate" offset-y>
          <template v-slot:activator="{on, attrs}">
            <v-btn v-on="on" v-bind="attrs" icon :title="curLocale.translation.tip">
              <v-icon>translate</v-icon>
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
        <v-toolbar-title>ADMIN PANEL</v-toolbar-title>
        <v-btn icon @click="logout">
              <v-icon>
                  exit_to_app
              </v-icon>
          </v-btn>
      </v-toolbar>
      <v-tabs
          v-model="tabs"
          grow
      >
        <v-tab>
          {{curLocale.tabs.tab1.name}}
        </v-tab>
        <v-tab>
          {{curLocale.tabs.tab2.name}}
        </v-tab>
        <v-tab>
          {{curLocale.tabs.tab3.name}}
        </v-tab>
        <v-tab>
          {{curLocale.tabs.tab4.name}}
        </v-tab>
        <v-tab-item>
          <v-card v-if="products.length > 0">
            <ProductInSaleList
                :products="products"
                :locale="curLocale"
                :updater="updateInSaleComp"
            />
            <v-btn
                bottom
                right
                fab
                color="success"
                x-large
                absolute
                elevation="10"
                @click="addProductDialog = true"
            >
              <v-icon large>
                add_circle
              </v-icon>
            </v-btn>
          </v-card>
          <v-card flat v-else style="margin: 4%">
            <v-icon style="text-align: center; display: block">
              warning
            </v-icon>
            <v-card-title style="justify-content: center">
              {{curLocale.tabs.tab1.context.notFound}}
            </v-card-title>
          </v-card>
          <v-dialog v-model="addProductDialog" width="650">
            <v-card>
              <v-card-title style="justify-content: center; display: flex">
                Добавить продукт
              </v-card-title>
              <v-divider></v-divider>
              <v-container>
                <v-row>
                  <v-col cols="6">
                    <v-text-field
                        label="Название:"
                        v-model="productForm.name"
                        outlined
                        :rules="textRules"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="6">
                    <v-select
                        label="Категория:"
                        :items="categories"
                        outlined
                        v-model="productForm.category"
                    ></v-select>
                  </v-col>
                  <v-col cols="12">
                    <v-textarea
                        label="Описание:"
                        v-model="productForm.description"
                        outlined
                        :rules="textRules"
                    ></v-textarea>
                  </v-col>
                </v-row>
                <v-row>
                  <v-col cols="4">
                    <v-text-field
                        type="number"
                        label="Цена:"
                        :rules="numRules"
                        v-model="productForm.price"
                        outlined
                    ></v-text-field>
                  </v-col>
                  <v-col cols="4">
                    <v-text-field
                        type="number"
                        :rules="numRules"
                        label="Вес:"
                        v-model="productForm.weight"
                        outlined
                    ></v-text-field>
                  </v-col>
                  <v-col cols="4">
                    <v-text-field
                        type="number"
                        :rules="numRules"
                        label="Ккал:"
                        v-model="productForm.calories"
                        outlined
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12">
                    <v-text-field
                        type="number"
                        :rules="numRules"
                        label="Доступно, шт:"
                        v-model="productForm.available_count"
                        outlined
                    ></v-text-field>
                  </v-col>
                </v-row>
                <v-btn outlined color="success" block>
                  СОЗДАТЬ
                </v-btn>
              </v-container>
            </v-card>
          </v-dialog>
        </v-tab-item>
        <v-tab-item>
          <v-card flat>
            <v-text-field v-model="searchUsers" @input="searchUser" :label="curLocale.tabs.tab2.context.searchLabel" style="margin: 1% 15%">
              <v-icon slot="append">search</v-icon>
            </v-text-field>
          </v-card>
          <v-container>
            <v-row>
              <v-col>
                <v-menu v-model="showFilters" max-width="180" offset-y>
                  <template v-slot:activator="{on, attrs}">
                    <v-card-title>
                      {{curLocale.tabs.tab2.context.title}}
                      <v-btn v-on="on" v-bind="attrs" icon @click="showFilters = !showFilters">
                        <v-icon>filter_list</v-icon>
                      </v-btn>
                    </v-card-title>
                  </template>
                  <v-card>
                    <v-card-title>
                      {{curLocale.tabs.tab2.context.filter}}:
                      <v-radio-group style="margin-left: 5%">
                        <v-radio
                            :label="curLocale.tabs.tab2.context.filterLabels[0]"
                            @change="filterStudents"
                        ></v-radio>
                        <v-radio
                            :label="curLocale.tabs.tab2.context.filterLabels[1]"
                            @change="filterAdmins"
                        ></v-radio>
                      </v-radio-group>
                    </v-card-title>
                  </v-card>
                </v-menu>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="2" v-for="(user, i) in usersInfo" :key="i">
                <v-card max-width="220">
                  <v-img :src="user.avatar" width="220" height="220"></v-img>
                  <v-card-subtitle>{{user.fName}} {{user.lName}}, {{user.age}}</v-card-subtitle>
                  <v-card-text>
                    <b>{{curLocale.tabs.tab2.context.userInfo[0]}}</b> <u>{{user.email}}</u> <br/>
                    <b>{{curLocale.tabs.tab2.context.userInfo[1]}}</b> <u>{{user.group}}</u> <br/>
                    <b>{{curLocale.tabs.tab2.context.userInfo[2]}}</b> <u>{{user.phone}}</u> <br/>
                    <b>{{curLocale.tabs.tab2.context.userInfo[3]}}</b> <u>{{isAdmin[i]}}</u> <br/>
                  </v-card-text>
                </v-card>
              </v-col>
            </v-row>
          </v-container>
<!--          <v-card flat id="grid" :style="'display: grid; grid-template-columns: repeat('+isMoreItems+', auto);grid-gap:' +isMoreItems+ 'px;margin: 0 0 2% 5%;'">-->
<!--            <v-card v-for="(user, i) in usersInfo" :key="i" width="220" v-model="usersInfo">-->
<!--              <v-img :src="user.avatar" width="220" height="220"></v-img>-->
<!--              <v-card-subtitle>{{user.fName}} {{user.lName}}, {{user.age}}</v-card-subtitle>-->
<!--              <v-card-text>-->
<!--                <b>{{curLocale.tabs.tab2.context.userInfo[0]}}</b> <u>{{user.email}}</u> <br/>-->
<!--                <b>{{curLocale.tabs.tab2.context.userInfo[1]}}</b> <u>{{user.group}}</u> <br/>-->
<!--                <b>{{curLocale.tabs.tab2.context.userInfo[2]}}</b> <u>{{user.phone}}</u> <br/>-->
<!--                <b>{{curLocale.tabs.tab2.context.userInfo[3]}}</b> <u>{{isAdmin[i]}}</u> <br/>-->
<!--              </v-card-text>-->
<!--            </v-card>-->
<!--          </v-card>-->
        </v-tab-item>
        <v-tab-item>
          <v-card>
            <v-card-title></v-card-title>
            <v-divider></v-divider>
            <v-container>
              <v-row no-gutters>
                <v-col>
                  <v-card>
                    <v-list>
                      <v-list-group>
                        <template v-slot:activator>
                          <v-list-item-title style="justify-content: center">
                            {{curLocale.tabs.tab3.context.addProduct.title}}
                          </v-list-item-title>
                        </template>
                        <v-list-item-group>
                          <v-form style="margin: 0 3% 0 3%">
                            <v-row>
                              <v-col>
                                  <v-text-field
                                    :label="curLocale.tabs.tab3.context.addProduct.labels[0]"
                                    v-model="productName"
                                    :rules="textRules"
                                    required
                                ></v-text-field>
                              </v-col>
                              <v-col>
                                  <v-select :label="curLocale.tabs.tab3.context.addProduct.labels[1]" v-model="category" solo :items="categories" required :rules="textRules"></v-select>
                              </v-col>
                            </v-row>
                            <v-row>
                              <v-col>
                                <v-text-field
                                    type="number"
                                    :label="curLocale.tabs.tab3.context.addProduct.labels[2]"
                                    v-model.number="price"
                                    :rules="numRules"
                                    required
                                ></v-text-field>
                              </v-col>
                              <v-col>
                                <v-text-field
                                    type="number"
                                    :label="curLocale.tabs.tab3.context.addProduct.labels[3]"
                                    v-model.number="total_count"
                                    :rules="numRules"
                                    required
                                ></v-text-field>
                              </v-col>
                            </v-row>
                            <v-text-field
                                :label="curLocale.tabs.tab3.context.addProduct.labels[4]"
                                v-model="descrip"
                                :rules="textRules"
                                required
                            ></v-text-field>
                            <v-file-input
                                :placeholder="curLocale.tabs.tab3.context.addProduct.labels[5]"
                                accept="image/png, image/jpg, image/jpeg"
                                prepend-icon="mdi-camera"
                            ></v-file-input>
                            <v-btn
                                style="margin-bottom: 2%"
                                width="100%"
                                color="success"
                                @click="addProduct"
                            >
                              {{curLocale.tabs.tab3.context.addProduct.btnTitle}}
                            </v-btn>
                          </v-form>
                        </v-list-item-group>
                      </v-list-group>
                    </v-list>
                  </v-card>
                </v-col>
                <v-col>
                    <v-card>
                      <v-list>
                        <v-list-group>
                          <template v-slot:activator>
                              <v-list-item-title style="justify-content: center">
                                {{curLocale.tabs.tab3.context.addAdmin.title}}
                              </v-list-item-title>
                          </template>
                          <v-list-item-group>
                            <v-form style="margin: 0 3% 0 3%">
                              <v-text-field
                                  :label="curLocale.tabs.tab3.context.addAdmin.labels[0]"
                                  v-model="fullname"
                                  :rules="textRules"
                                  required
                              ></v-text-field>
                              <v-row>
                                <v-col>
                                  <v-text-field
                                      :label="curLocale.tabs.tab3.context.addAdmin.labels[1]"
                                      v-model="phone"
                                      :rules="phoneRules"
                                      required
                                  ></v-text-field>
                                </v-col>
                                <v-col>
                                  <v-text-field
                                      :label="curLocale.tabs.tab3.context.addAdmin.labels[2]"
                                      v-model="email"
                                      :rules="emailRules"
                                      required
                                  ></v-text-field>
                                </v-col>
                              </v-row>
                              <v-text-field
                                  :label="curLocale.tabs.tab3.context.addAdmin.labels[3]"
                                  v-model="login"
                                  :rules="textRules"
                                  required
                              ></v-text-field>
                              <v-text-field
                                  type="password"
                                  :label="curLocale.tabs.tab3.context.addAdmin.labels[4]"
                                  v-model="pwd"
                                  :rules="textRules"
                                  required
                              ></v-text-field>
                              <v-btn
                                  style="margin-bottom: 2%"
                                  width="100%"
                                  color="success"
                                  @click="addAdmin"
                              >
                                {{curLocale.tabs.tab3.context.addAdmin.btnTitle}}
                              </v-btn>
                            </v-form>
                          </v-list-item-group>
                        </v-list-group>
                      </v-list>
                    </v-card>
                  </v-col>
              </v-row>
            </v-container>
            <v-snackbar color="success" v-model="finishAddProduct" timeout="4000" bottom>
              {{curLocale.tabs.tab3.context.alerts[0]}}
              <template>
                <v-btn text @click="finishAddProduct = false">
                  {{curLocale.tabs.tab3.context.alerts[2]}}
                </v-btn>
              </template>
            </v-snackbar>
            <v-snackbar color="success" v-model="finishAddAdmin" timeout="4000" bottom>
              {{curLocale.tabs.tab3.context.alerts[1]}}
              <template>
                <v-btn text @click="finishAddAdmin = false">
                  {{curLocale.tabs.tab3.context.alerts[2]}}
                </v-btn>
              </template>
            </v-snackbar>
          </v-card>
        </v-tab-item>
        <v-tab-item>
          <v-card flat v-if="products.length > 0">
              <div v-for="(item, i) in products" :key="i" style="margin: 2%">
                <v-list>
                  <div :style="'position: absolute;background-color: #' + statisticClr[i] + '' + ';opacity: 0.6;height: 5%;width: '+getSells[i]"></div>
                  <v-list-group>
                    <template v-slot:activator>
                      <v-card-title>{{ item.name }}</v-card-title>
                      <v-spacer></v-spacer>
                      <v-item-group>
                        {{getSells[i]}} {{curLocale.tabs.tab4.context.sellsTitle}}
                      </v-item-group>
                    </template>
                    <v-card>
                      <v-container>
                        <v-row>
                          <v-col>
                            <v-card-subtitle>
                              <b>{{curLocale.tabs.tab4.context.info[0]}} </b> {{item.description}}
                            </v-card-subtitle>
                          </v-col>
                          <v-col>
                            <v-card-subtitle>
                              <b>{{curLocale.tabs.tab4.context.info[1]}}</b> {{getLastBuy}}
                            </v-card-subtitle>
                          </v-col>
                          <v-col>
                            <v-card-subtitle>
                              <b>{{curLocale.tabs.tab4.context.info[2]}}</b> {{item.available_count}}
                            </v-card-subtitle>
                          </v-col>
                          <v-col>
                            <v-card-subtitle>
                              <b>{{curLocale.tabs.tab4.context.info[3]}}</b> {{item.count}}
                            </v-card-subtitle>
                          </v-col>
                        </v-row>
                      </v-container>
                    </v-card>
                  </v-list-group>
                </v-list>
              </div>
          </v-card>
          <v-card flat v-else style="margin: 4%">
            <v-icon style="text-align: center; display: block">
              warning
            </v-icon>
            <v-card-title style="justify-content: center">
              {{curLocale.tabs.tab4.context.notFound}}
            </v-card-title>
          </v-card>
        </v-tab-item>
      </v-tabs>
    </v-card>
  </v-app>
</template>

<script>
import ProductInSaleList from "@/components/ProductInSaleList";
const ip = 'localhost'
const port = 25016;
const axios = require('axios')

export default {
  name: "AdminCabinet",
  components: {ProductInSaleList},
  data() {
    return {
      categories: [
        'Первое блюдо',
        'Второе блюдо',
        'Пряности',
        'Напитки',
      ],
      addProductDialog: false,
      curLocale: {},
      locales: {
        'en-EN': {
          tabs: {
            tab1: {
              name: 'Products on sale',
              context: {
                infoProduct: [
                    'Category:',
                    'Description:',
                    'Available:',
                    'Calories:',
                    'Weight, g:',
                    'Total count:'
                ],
                editMainBtnTitle: 'Edit product',
                labels: [
                    'Name of item',
                    'Category of item',
                    'Price',
                    'Amount available',
                    'Description',
                    'Image of item'
                ],
                editBtn: 'Change',
                notFound: 'Item not found',
                alerts: [
                    'Product', 'was changed',
                    'Product was deleted',
                    'Close'
                ]
              }
            },
            tab2: {
              name: 'Users',
              context: {
                searchLabel: 'Search',
                title: 'Users',
                filter: 'Filters',
                filterLabels: [
                    'Students',
                    'Admins'
                ],
                userInfo: [
                    'E-mail:',
                    'Group:',
                    'Phone:',
                    'Admin:'
                ]
              }
            },
            tab3: {
              name: 'Super-Panel',
              context: {
                addProduct: {
                  title: 'Add product',
                  labels: [
                      'Name of item*',
                      'Choose a category*',
                      'Price*',
                      'Available amount*',
                      'Description*',
                      'Image of item'
                  ],
                  btnTitle: 'ADD'
                },
                addAdmin: {
                  title: 'Add admin',
                  labels: [
                      'FullName*',
                      'Phone*',
                      'E-mail*',
                      'Login*',
                      'Password*'
                  ],
                  btnTitle: 'ADD'
                },
                alerts: [
                    'Product was added',
                    'Admin was added',
                    'Close'
                ],
                numRules: 'Number cannot be less than 0',
                textRules: [
                    'This field is required',
                    'Field cannot be empty'
                ],
                phoneRules: 'Input correct phone number',
                emailRules: 'Input correct e-mail'
              }
            },
            tab4: {
              name: 'Statistics',
              context: {
                sellsTitle: 'sells',
                info: [
                    'Description:',
                    'Last buy:',
                    'Remained in stock:',
                    'It was at the beginning:'
                ],
                notFound: 'Items not found'
              }
            }
          },
          translation: {
            tip: 'Choose language for interface',
            title: 'Language of interface',
            langs: [
              'English', 'Russian', 'Ukrainian'
            ]
          }
        },
        'ru-RU': {
          tabs: {
            tab1: {
              name: 'Продукты в продаже',
              context: {
                infoProduct: [
                  'Категория:',
                  'Описание:',
                  'Доступно:',
                  'Ккал:',
                  'Вес, г:',
                  'Кол-во изначально:'
                ],
                editMainBtnTitle: 'Редактировать продукт',
                labels: [
                  'Название товара',
                  'Категория',
                  'Цена',
                  'Доступное кол-во',
                  'Описание',
                  'Картинка продукта'
                ],
                editBtn: 'Изменить',
                notFound: 'Товаров не найдено',
                alerts: [
                  'Продукт', 'был изменен',
                  'Продукт был удален',
                  'Закрыть'
                ]
              }
            },
            tab2: {
              name: 'Пользователи',
              context: {
                searchLabel: 'Поиск',
                title: 'Пользователи',
                filter: 'Фильтры',
                filterLabels: [
                  'Студенты',
                  'Админы'
                ],
                userInfo: [
                  'E-mail:',
                  'Группа:',
                  'Телефон:',
                  'Админ:'
                ]
              }
            },
            tab3: {
              name: 'Супер-Панель',
              context: {
                addProduct: {
                  title: 'Добавить продукт',
                  labels: [
                    'Название продукта*',
                    'Категория*',
                    'Цена*',
                    'Доступное кол-во*',
                    'Описание*',
                    'Картинка продукта'
                  ],
                  btnTitle: 'Добавить'
                },
                addAdmin: {
                  title: 'Добавить админа',
                  labels: [
                    'ФИО*',
                    'Телефон*',
                    'E-mail*',
                    'Логин*',
                    'Пароль*'
                  ],
                  btnTitle: 'Добавить'
                },
                alerts: [
                  'Product was added',
                  'Admin was added',
                  'Close'
                ],
                numRules: 'Число не может быть меньше 0',
                textRules: [
                  'Это поле важное',
                  'Поле не может быть пустым'
                ],
                phoneRules: 'Введите коректный телефон',
                emailRules: 'Введите коректный e-mail'
              }
            },
            tab4: {
              name: 'Статистика',
              context: {
                sellsTitle: 'продажи',
                info: [
                  'Описание:',
                  'Последняя покупка:',
                  'Осталось в продаже:',
                  'Было изначально'
                ],
                notFound: 'Товаров не найдено'
              }
            }
          },
          translation: {
            tip: 'Выберите язык интерфейса',
            title: 'Язык интерфейса',
            langs: [
              'English', 'Русский', 'Украинский'
            ]
          }
        },
        'ua-UA': {
          tabs: {
            tab1: {
              name: 'Продукти у продажу',
              context: {
                infoProduct: [
                  'Категорія:',
                  'Опис:',
                  'Доступно:',
                  'Ккал:',
                  'Вага, г.:',
                  'Кількість спочатку:'
                ],
                editMainBtnTitle: 'Редагувати товар',
                labels: [
                  'Названня товару',
                  'Категорія',
                  'Ціна',
                  'Доступна кількість',
                  'Опис',
                  'Зображення до товару'
                ],
                editBtn: 'Змінити',
                notFound: 'Товар не знайдено',
                alerts: [
                  'Товар', 'було зміненно',
                  'Товар було видалено',
                  'Закрити'
                ]
              }
            },
            tab2: {
              name: 'Користувачі',
              context: {
                searchLabel: 'Пошук',
                title: 'Користувачі',
                filter: 'Фільтри:',
                filterLabels: [
                  'Студенти',
                  'Адміни'
                ],
                userInfo: [
                  'E-mail:',
                  'Групи:',
                  'Телефон:',
                  'Админ:'
                ]
              }
            },
            tab3: {
              name: 'Супер-Панель',
              context: {
                addProduct: {
                  title: 'Додати товар',
                  labels: [
                    'Названня товару*',
                    'Вибір категорії*',
                    'Ціна*',
                    'Доступна кількість*',
                    'Опис*',
                    'Зобрження товару'
                  ],
                  btnTitle: 'Додати'
                },
                addAdmin: {
                  title: 'Додати адміна',
                  labels: [
                    'ПІБ*',
                    'Телефон*',
                    'E-mail*',
                    'Логін*',
                    'Пароль*'
                  ],
                  btnTitle: 'Додати'
                },
                alerts: [
                  'Товар було додано',
                  'Адміна було додано',
                  'Закрити'
                ],
                numRules: 'Число не може бути менш ніж 0',
                textRules: [
                  'Це поле важливе',
                  'Поле не може бути пустим'
                ],
                phoneRules: 'Напишіть коректний телефон',
                emailRules: 'Напишіть коректний e-mail'
              }
            },
            tab4: {
              name: 'Статистика',
              context: {
                sellsTitle: 'продажи',
                info: [
                  'Опис:',
                  'Остан`я купівля:',
                  'Лишилося товару:',
                  'Було з початку:'
                ],
                notFound: 'Товари не знайдено'
              }
            }
          },
          translation: {
            tip: 'Виберіть мову інтерфейсу',
            title: 'Мова інтерфейсу',
            langs: [
              'English', 'Російська', 'Українська'
            ]
          }
        }
      },
      translate: false,
      langOne: false,
      langSecond: false,
      langThird: false,
      tabs: false,
      formOff: false,
      clr: "",
      testData: null,
      showFilters: false,
      reloader: true,
      productForm: {
        name: '',
        category: '',
        price: 1,
        available_count: 1,
        total_count: 1,
        description: '',
        weight: 1,
        calories: 1
      },
      productName: "",
      category: "",
      price: 0,
      total_count: 0,
      available_count: 0,
      descrip: "",
      filterName: "",
      fullname: "",
      phone: "",
      email: "",
      categoryName: "",
      login: "",
      pwd: "",
      finishAddProduct: false,
      finishAddAdmin: false,
      searchUsers: "",
      usersInfo: [],
      products: [],
      numRules: [
        v => v > 0 || this.curLocale.tabs.tab3.context.numRules
      ],
      textRules: [
        v => !!v || this.curLocale.tabs.tab3.context.textRules[0],
        v => v.length !== 0 || this.curLocale.tabs.tab3.context.textRules[1]
      ],
      phoneRules: [
          v => v.match("^[0-9]+$") !== null || this.curLocale.tabs.tab3.context.phoneRules
      ],
      emailRules: [
          v => v.match("[a-zA-Z]+@[a-zA-Z]+[.][a-zA-Z]+")  || this.curLocale.tabs.tab3.context.emailRules
      ]
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
  mounted() {
    axios({
      method: 'GET',
      url: `http://${ip}:${port}/api/product/all`,
      headers: {
        Authorization: 'Bearer ' + localStorage['sid']
      }
    }).then(resp => {
      console.log(resp.data)
      this.products = resp.data
    })
  },
  methods: {
    updateInSaleComp(info) {
      this.editDialog = info['edit']
    },
    changeLangEN() {
      localStorage.setItem('lang', 'en-EN')
      this.curLocale = this.locales["en-EN"];
      // this.$router.go(this.$router.currentRoute.path);
    },
    changeLangRU() {
      localStorage.setItem('lang', 'ru-RU')
      this.curLocale = this.locales["ru-RU"];
      // this.$router.go(this.$router.currentRoute.path);
    },
    changeLangUA() {
      localStorage.setItem('lang', 'ua-UA')
      this.curLocale = this.locales["ua-UA"];
      // this.$router.go(this.$router.currentRoute.path);
    },
    filterStudents() {
      this.usersInfo = this.usersInfo.filter(i => !i.isAdmin)
    },
    filterAdmins() {
      this.usersInfo = this.usersInfo.filter(i => i.isAdmin === true)
    },
    addProduct() {
      //sending POST
      this.finishAddProduct = true;
    },
    searchUser() {
      this.usersInfo = []
      //sending GET
    },
    addAdmin() {
      //sending POST
      this.finishAddAdmin = true;
    },
    logout() {},
  },
  computed: {
    isMoreItems() {
      let res = 0
      if (this.usersInfo.length > 6) res = 6;
      else res = 24;
      return res;
    },
    isAdmin() {
      let res = [];
      for (let i=0; i<this.usersInfo.length; i++) {
        if (this.usersInfo[i].isAdmin) res.push('Да')
        else res.push('Нет')
      }
      return res;
    },
    statisticClr() {
      if (localStorage['color'] === undefined) {
        let res = [];
        let nums = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
        for (let i=0;i<this.products.length;i++) {
          res.push("")
          for (let j=0;j<6;j++) {
            res[i] += nums[Math.floor(Math.random()*nums.length)].toString(16)
          }
        }
        localStorage.setItem('color', res)
        return res;
      } else {
        return localStorage.getItem('color').split(',');
      }
    },

    getSells() {
      // sending GET
      let res = [];
      for (let i=0;i<this.products.length;i++) {
        let item = this.products[i]
        res.push(Math.ceil(((item.count-item.available_count)/item.count)*100)+'%')
      }
      return res;
    },
    getLastBuy() {
      // sending GET
      return "18.09.2020 16:20"
    }
  },
}
</script>

<style scoped>
</style>