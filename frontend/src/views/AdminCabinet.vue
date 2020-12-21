<template>
  <v-app style="z-index: 5">
    <v-card flat>
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
                  <v-list-item-content>
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
                :update-products="updateAllProduct"
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
            <v-btn
                style="text-align: center; display: block"
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
            <v-card-title style="justify-content: center">
              {{curLocale.tabs.tab1.context.notFound}}
              <v-btn
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
            </v-card-title>
          </v-card>
          <v-dialog v-model="addProductDialog" width="650">
            <v-card>
              <v-card-title style="justify-content: center; display: flex">
                {{curLocale.tabs.tab1.context.addProduct.title}}
              </v-card-title>
              <v-divider></v-divider>
              <v-container>
                <v-row>
                  <v-col cols="6">
                    <v-text-field
                        :label="curLocale.tabs.tab1.context.addProduct.labels[0]"
                        v-model="productForm.name"
                        outlined
                        :rules="textRules"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="6">
                    <v-select
                        :label="curLocale.tabs.tab1.context.addProduct.labels[1]"
                        :items="categories"
                        outlined
                        v-model="productForm.category"
                    ></v-select>
                  </v-col>
                  <v-col cols="12">
                    <v-textarea
                        :label="curLocale.tabs.tab1.context.addProduct.labels[2]"
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
                        :label="curLocale.tabs.tab1.context.addProduct.labels[3]"
                        :rules="numRules"
                        v-model="productForm.price"
                        outlined
                    ></v-text-field>
                  </v-col>
                  <v-col cols="4">
                    <v-text-field
                        type="number"
                        :rules="numRules"
                        :label="curLocale.tabs.tab1.context.addProduct.labels[4]"
                        v-model="productForm.weight"
                        outlined
                    ></v-text-field>
                  </v-col>
                  <v-col cols="4">
                    <v-text-field
                        type="number"
                        :rules="numRules"
                        :label="curLocale.tabs.tab1.context.addProduct.labels[5]"
                        v-model="productForm.calories"
                        outlined
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12">
                    <v-text-field
                        type="number"
                        :rules="numRules"
                        :label="curLocale.tabs.tab1.context.addProduct.labels[6]"
                        v-model="productForm.available_count"
                        outlined
                    ></v-text-field>
                  </v-col>
                </v-row>
                <v-btn outlined color="success" block @click="doAddProduct">
                  {{curLocale.tabs.tab1.context.addProduct.btnTitle}}
                </v-btn>
              </v-container>
              <v-snackbar outlined top :color="alertProductFormInfo[0]" timeout="1800" v-model="alertProductForm">
                <template v-slot:action="{attrs}">
                  <v-btn icon v-bind="attrs">
                    <v-icon>
                      close
                    </v-icon>
                  </v-btn>
                </template>
                {{alertProductFormInfo[1]}}
              </v-snackbar>
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
                    <b>{{curLocale.tabs.tab2.context.userInfo[1]}}</b> <u>{{user.personGroup}}</u> <br/>
                    <b>{{curLocale.tabs.tab2.context.userInfo[2]}}</b> <u>{{user.phone}}</u> <br/>
                    <b>{{curLocale.tabs.tab2.context.userInfo[3]}}</b> <u>{{isAdmin[i]}}</u> <br/>
                  </v-card-text>
                </v-card>
              </v-col>
            </v-row>
            <v-btn
                bottom
                right
                fab
                color="success"
                x-large
                absolute
                elevation="10"
                @click="addAdminDialog = true"
            >
              <v-icon large>
                add_circle
              </v-icon>
            </v-btn>
          </v-container>
          <v-dialog v-model="addAdminDialog" max-width="650">
            <v-card>
              <v-card-title style="justify-content: center; display: flex">
                {{curLocale.tabs.tab2.context.addUser.title}}
              </v-card-title>
              <v-stepper v-model="stepperUserForm">
                <v-stepper-header>
                  <v-stepper-step :complete="stepperUserForm > 1" step="1">
                    {{curLocale.tabs.tab2.context.addUser.stepers[0]}}
                  </v-stepper-step>
                  <v-divider></v-divider>
                  <v-stepper-step :complete="stepperUserForm > 2" step="2">
                    {{curLocale.tabs.tab2.context.addUser.stepers[1]}}
                  </v-stepper-step>
                </v-stepper-header>
                <v-stepper-items>
                  <v-stepper-content step="1">
                    <v-card flat>
                      <v-radio-group v-model="chooseBtnUser">
                        <v-container>
                          <v-row>
                            <v-col>
                              <v-radio :label="curLocale.tabs.tab2.context.addUser.radioTips[0]" value="user"></v-radio>
                            </v-col>
                            <v-col>
                              <v-radio :label="curLocale.tabs.tab2.context.addUser.radioTips[1]" value="admin"></v-radio>
                            </v-col>
                          </v-row>
                        </v-container>
                      </v-radio-group>
                    </v-card>
                    <v-btn
                        color="indigo" dark
                        @click="chooseBtnUser !== ''?stepperUserForm = 2:null"
                    >
                      {{curLocale.tabs.tab2.context.addUser.stepperBtns[0]}}
                    </v-btn>
                  </v-stepper-content>
                  <v-stepper-content step="2">
                    <v-card flat>
                      <div v-if="chooseBtnUser === 'user'">
                        <v-container>
                          <v-row>
                            <v-col cols="6">
                              <v-text-field
                                  outlined
                                  :label="curLocale.tabs.tab2.context.addUser.labels[0]"
                                  v-model="userForm.fName"
                                  :rules="textRules"
                              ></v-text-field>
                            </v-col>
                            <v-col cols="6">
                              <v-text-field
                                  outlined
                                  :label="curLocale.tabs.tab2.context.addUser.labels[1]"
                                  v-model="userForm.lName"
                                  :rules="textRules"
                              ></v-text-field>
                            </v-col>
                            <v-col cols="12">
                              <v-text-field
                                  outlined
                                  label="E-mail"
                                  v-model="userForm.email"
                                  :rules="emailRules"
                              ></v-text-field>
                            </v-col>
                            <v-col cols="6">
                              <v-text-field
                                  outlined
                                  :label="curLocale.tabs.tab2.context.addUser.labels[3]"
                                  v-model="userForm.personGroup"
                                  :rules="textRules"
                              ></v-text-field>
                            </v-col>
                            <v-col cols="6">
                              <v-text-field
                                  outlined
                                  type="number"
                                  :label="curLocale.tabs.tab2.context.addUser.labels[4]"
                                  v-model="userForm.phone"
                                  :rules="numRules"
                              ></v-text-field>
                            </v-col>
                            <v-col cols="6">
                              <v-text-field
                                  outlined
                                  :label="curLocale.tabs.tab2.context.addUser.labels[5]"
                                  v-model="userForm.username"
                                  :rules="textRules"
                              ></v-text-field>
                            </v-col>
                            <v-col cols="6">
                              <v-text-field
                                  outlined
                                  type="password"
                                  :label="curLocale.tabs.tab2.context.addUser.labels[6]"
                                  v-model="userForm.password"
                                  :rules="textRules"
                              ></v-text-field>
                            </v-col>
                          </v-row>
                        </v-container>
                      </div>
                      <div v-if="chooseBtnUser === 'admin'">
                        <v-container>
                          <v-row>
                            <v-col cols="6">
                              <v-text-field
                                  outlined
                                  :label="curLocale.tabs.tab2.context.addUser.labels[0]"
                                  v-model="userForm.fName"
                                  :rules="textRules"
                              ></v-text-field>
                            </v-col>
                            <v-col cols="6">
                              <v-text-field
                                  outlined
                                  :label="curLocale.tabs.tab2.context.addUser.labels[1]"
                                  v-model="userForm.lName"
                                  :rules="textRules"
                              ></v-text-field>
                            </v-col>
                            <v-col cols="6">
                              <v-text-field
                                  outlined
                                  :label="curLocale.tabs.tab2.context.addUser.labels[5]"
                                  v-model="userForm.username"
                                  :rules="textRules"
                              ></v-text-field>
                            </v-col>
                            <v-col cols="6">
                              <v-text-field
                                  outlined
                                  type="password"
                                  :label="curLocale.tabs.tab2.context.addUser.labels[6]"
                                  v-model="userForm.password"
                                  :rules="textRules"
                              ></v-text-field>
                            </v-col>
                            <v-col cols="12">
                              <v-text-field
                                  outlined
                                  label="E-mail"
                                  v-model="userForm.email"
                                  :rules="emailRules"
                              ></v-text-field>
                            </v-col>
                          </v-row>
                        </v-container>
                      </div>
                    </v-card>
                    <v-btn
                        color="indigo" dark
                        @click="createUser"
                    >
                      {{curLocale.tabs.tab2.context.addUser.stepperBtns[1]}}
                    </v-btn>
                    <v-btn text @click="stepperUserForm = 1">
                      {{curLocale.tabs.tab2.context.addUser.stepperBtns[2]}}
                    </v-btn>
                  </v-stepper-content>
                </v-stepper-items>
              </v-stepper>
            </v-card>
          </v-dialog>
          <v-snackbar outlined top timeout="1800" v-model="addUserAlertInfo[0]" :color="addUserAlertInfo[2]">
            <template v-slot:action="{attrs}">
              <v-btn text @click="addUserAlertInfo[0] = false" v-bind="attrs">
                {{curLocale.tabs.tab2.context.addUser.stepperBtns[3]}}
              </v-btn>
            </template>
            {{ addUserAlertInfo[1] }}
          </v-snackbar>
        </v-tab-item>
        <v-tab-item>
          <v-card flat v-if="products.length > 0">
            <v-card v-for="(item, i) in products" :key="i" :color="statisticClr[i]" style="margin: 3%">
              <v-card-title class="white--text">
                {{item.name}}
                <v-menu offset-x transition="scale-transition">
                  <template v-slot:activator="{on, attrs}">
                    <v-btn icon v-bind="attrs" v-on="on" dark>
                      <v-icon>
                        keyboard_arrow_right
                      </v-icon>
                    </v-btn>
                  </template>
                  <v-card>
                    <v-card-text>
                      <b>{{curLocale.tabs.tab3.context.info[0]}}</b> {{item.price}} UAH.<br>
                      <b>{{curLocale.tabs.tab3.context.info[1]}}</b> {{item.description}} <br>
                      <b>{{curLocale.tabs.tab3.context.info[2]}}</b> {{item.total_count}} <br>
                      <b>{{curLocale.tabs.tab3.context.info[3]}}</b> {{item.available_count}} <br>
                      <b>{{curLocale.tabs.tab3.context.info[4]}}</b> {{item.calories}}
                      <b>{{curLocale.tabs.tab3.context.info[5]}}</b> {{item.weight}}
                    </v-card-text>
                  </v-card>
                </v-menu>
              </v-card-title>
            </v-card>
          </v-card>
          <v-card flat v-else style="margin: 4%">
            <v-icon style="text-align: center; display: block">
              warning
            </v-icon>
            <v-card-title style="justify-content: center">
              {{curLocale.tabs.tab3.context.notFound}}
            </v-card-title>
          </v-card>
        </v-tab-item>
        <v-tab-item>
          <v-card v-if="orders.length > 0" style="padding: 0 15% 0 15%">
            <v-container>
              <v-row>
                <v-col cols="4">
                  <v-select
                      :label="curLocale.tabs.tab4.context.titleFilter"
                      outlined
                      v-model="chosenFilterOrders"
                      @input="doFilterOrders"
                      :items="curLocale.tabs.tab4.context.filters"
                      append-icon="filter_list"
                  ></v-select>
                </v-col>
              </v-row>
            </v-container>
            <v-divider></v-divider>
            <v-container>
              <v-row>
                <v-col v-for="(order, i) in orders" :key="i">
                  <v-card flat>
                    <v-menu offset-x open-on-hover nudge-width="150">
                      <template v-slot:activator="{on, attrs}">
                        <v-img :src="order.product_id.image" width="120" height="120"></v-img>
                        <v-card-subtitle>
                          {{curLocale.tabs.tab4.context.receipt.title}} №{{order.check_id.check_id}}
                          <v-btn icon large v-on="on" v-bind="attrs">
                            <v-icon>
                              keyboard_arrow_right
                            </v-icon>
                          </v-btn>
                        </v-card-subtitle>
                      </template>
                      <v-card>
                        <v-card-subtitle>
<!--                          {{curLocale.tabs.tab4.context.receipt.labels[0]}} {{order.check_id.canteen}} <br/>-->
                          {{curLocale.tabs.tab4.context.receipt.labels[1]}} {{order.product_id.name}} <br/>
                          {{curLocale.tabs.tab4.context.receipt.labels[2]}} {{order.check_id.purchaseDate.split('T')[0]+' '+order.check_id.time}} <br/>
                          {{curLocale.tabs.tab4.context.receipt.labels[3]}} {{order.product_id.description}} <br/>
                          {{curLocale.tabs.tab4.context.receipt.labels[4]}} {{order.product_id.category}} <br/>
                          {{curLocale.tabs.tab4.context.receipt.labels[5]}} {{order.product_id.price}} {{curLocale.tabs.tab4.context.receipt.currency}} <br/>
                        </v-card-subtitle>
                      </v-card>
                    </v-menu>
                  </v-card>
                </v-col>
              </v-row>
            </v-container>
          </v-card>
        </v-tab-item>
      </v-tabs>
    </v-card>
  </v-app>
</template>

<script>
import ProductInSaleList from "@/components/ProductInSaleList";
const ip = 'nurecanteen'
const port = 'pp.ua';
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
      alertProductForm: false,
      alertProductFormInfo: ['success', ''],
      addProductDialog: false,
      addAdminDialog: false,
      orders: [],
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
                ],
                addProduct: {
                  title: 'Add product',
                  labels: [
                    'Name*',
                    'Category*',
                    'Description*',
                    'Price',
                    'Weight',
                    'Calories',
                    'Available*',
                    'Image'
                  ],
                  btnTitle: 'Add'
                }
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
                ],
                numRules: 'Number cannot be less than 0',
                textRules: [
                  'This field is required',
                  'Field cannot be empty'
                ],
                phoneRules: 'Input correct phone number',
                emailRules: 'Input correct e-mail',
                addUser: {
                  stepers: [
                      'Choose role user',
                      'Fill data'
                  ],
                  radioTips: [
                      'Default user',
                      'Admin'
                  ],
                  title: 'Add user',
                  labels: [
                    'First name*',
                    'Second name*',
                    'E-mail*',
                    'Group',
                    'Phone*',
                    'Username*',
                    'Password*'
                  ],
                  alerts: [
                      'User is successfully created!',
                      'Error creating.'
                  ],
                  stepperBtns: [
                      'Next',
                      'Continue',
                      'Back',
                      'Close'
                  ],
                  btnTitle: 'Add'
                }
              }
            },
            tab3: {
              name: 'Statistics',
              context: {
                sellsTitle: 'sells',
                info: [
                    'Price:',
                    'Description:',
                    'Total count:',
                    'Available count now:',
                    'Calories:',
                    'Weight:'
                ],
                notFound: 'Items not found'
              }
            },
            tab4: {
              name: 'Orders',
              context: {
                titleFilter: 'Sort',
                filters: [
                    'All',
                    'By latest date'
                ],
                receipt: {
                  title: 'Receipt',
                  labels: [
                    'Canteen:',
                    'Item:',
                    'Date of purchase:',
                    'Description:',
                    'Category:',
                    'Price:'
                  ],
                  currency: 'UAH'
                },
                notFound: 'Orders not found'
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
                ],
                addProduct: {
                  title: 'Добавить продукт',
                  labels: [
                    'Название продукта*',
                    'Категория*',
                    'Описание*',
                    'Цена',
                    'Вес',
                    'Ккал',
                    'Доступное кол-во',
                    'Картинка продукта'
                  ],
                  btnTitle: 'Добавить'
                }
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
                ],
                numRules: 'Число не может быть меньше 0',
                textRules: [
                  'Это поле важное',
                  'Поле не может быть пустым'
                ],
                phoneRules: 'Введите коректный телефон',
                emailRules: 'Введите коректный e-mail',
                addUser: {
                  stepers: [
                    'Выберите роль пользователя',
                    'Заполните данные'
                  ],
                  radioTips: [
                    'Обычный пользователь',
                    'Админ'
                  ],
                  title: 'Добавить пользователя',
                  labels: [
                    'Имя*',
                    'Фамилия*',
                    'E-mail*',
                    'Группа',
                    'Телефон*',
                    'Логин*',
                    'Пароль*'
                  ],
                  alerts: [
                    'Пользователь успешно создан',
                    'Ошибка создания'
                  ],
                  stepperBtns: [
                    'Дальше',
                    'Продолжить',
                    'Назад',
                    'Закрыть'
                  ],
                  btnTitle: 'Добавить'
                }
              }
            },
            tab3: {
              name: 'Статистика',
              context: {
                sellsTitle: 'продажи',
                info: [
                  'Цена:',
                  'Описание:',
                  'Общее кол-во:',
                  'Доступно сейчас:',
                  'Ккал:',
                  'Вес:'
                ],
                notFound: 'Товаров не найдено'
              }
            },
            tab4: {
              name: 'Заказы',
              context: {
                titleFilter: 'Сортировка',
                filters: [
                  'Все',
                  'По дате покупки'
                ],
                receipt: {
                  title: 'Чек',
                  labels: [
                    'Столовая:',
                    'Товар:',
                    'Дата покупки:',
                    'Описание:',
                    'Категория:',
                    'Цена:'
                  ],
                  currency: 'ГРН'
                },
                notFound: 'Заказов нет'
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
                addProduct: {
                  title: 'Додати продукт',
                  labels: [
                    'Названня продукта*',
                    'Категорія*',
                    'Опис*',
                    'Ціна',
                    'Вага',
                    'Ккал',
                    'Доступна кількість',
                    'Зображення'
                  ],
                  btnTitle: 'Додати'
                },
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
                numRules: 'Число не може бути менш ніж 0',
                textRules: [
                  'Це поле важливе',
                  'Поле не може бути пустим'
                ],
                phoneRules: 'Напишіть коректний телефон',
                emailRules: 'Напишіть коректний e-mail',
                userInfo: [
                  'E-mail:',
                  'Групи:',
                  'Телефон:',
                  'Админ:'
                ],
                addUser: {
                  stepers: [
                    'Виберіть роль користувача',
                    'Заповніть дані'
                  ],
                  radioTips: [
                    'Звичайний користувач',
                    'Адмін'
                  ],
                  title: 'Додати користувача',
                  labels: [
                    'Ім`я*',
                    'Прізвище*',
                    'E-mail*',
                    'Група*',
                    'Телефон*',
                    'Логін*',
                    'Пароль*'
                  ],
                  alerts: [
                    'Користувач зуспішно створен',
                    'Помилка створення'
                  ],
                  stepperBtns: [
                    'Далі',
                    'Продовжити',
                    'Назад',
                    'Закрити'
                  ],
                  btnTitle: 'Додати'
                }
              }
            },
            tab3: {
              name: 'Статистика',
              context: {
                sellsTitle: 'продажи',
                info: [
                  'Ціна:',
                  'Опис:',
                  'Уся кількість:',
                  'Доступне зараз:',
                  'Ккал:',
                  'Вага:'
                ],
                notFound: 'Товари не знайдено'
              }
            },
            tab4: {
              name: 'Замовлення',
              context: {
                titleFilter: 'Сортировка',
                filters: [
                  'Усі',
                  'За датою купівлі'
                ],
                receipt: {
                  title: 'Чек',
                  labels: [
                    'Їдальня:',
                    'Товар:',
                    'Дата купівлі:',
                    'Опис:',
                    'Категорія:',
                    'Ціна:'
                  ],
                  currency: 'ГРН'
                },
                notFound: 'Замовлення немає'
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
      tabs: false,
      formOff: false,
      clr: "",
      testData: null,
      showFilters: false,
      chosenFilterOrders: '',
      productForm: {
        name: '',
        category: '',
        price: 1,
        available_count: 1,
        total_count: 1,
        description: '',
        weight: 1,
        calories: 1,
        image: ''
      },
      stepperUserForm: 1,
      chooseBtnUser: '',
      addUserAlert: false,
      addUserAlertInfo: [false, '', 'success'],
      addProductAlert: [false, '', 'success'],
      userForm: {
        fName: '',
        lName: '',
        email: '',
        personGroup: '',
        age: 0,
        avatar: 'empty',
        phone: '',
        username: '',
        password: ''
      },
      finishAddProduct: false,
      finishAddAdmin: false,
      searchUsers: "",
      usersInfo: [],
      products: [],
      numRules: [
        v => v > 0 || this.curLocale.tabs.tab2.context.numRules
      ],
      textRules: [
        v => !!v || this.curLocale.tabs.tab2.context.textRules[0],
        v => v.length !== 0 || this.curLocale.tabs.tab2.context.textRules[1]
      ],
      phoneRules: [
          v => v.match("^[0-9]+$") !== null || this.curLocale.tabs.tab2.context.phoneRules
      ],
      emailRules: [
          v => v.match("[a-zA-Z]+@[a-zA-Z]+[.][a-zA-Z]+")  || this.curLocale.tabs.tab2.context.emailRules
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
      url: `https://api.${ip}.${port}/api/product/all`,
      headers: {
        Authorization: 'Bearer ' + localStorage['sid']
      }
    }).then(resp => {
      this.products = resp.data
    })

    axios({
      method: 'GET',
      url: `https://api.${ip}.${port}/api/user/all`,
      headers: {
        Authorization: 'Bearer ' + localStorage['sid']
      }
    }).then(resp => {
      this.usersInfo = resp.data
    })

    axios({
      method: 'GET',
      url: `https://api.${ip}.${port}/api/basket/all`,
      headers: {
        Authorization: 'Bearer ' + localStorage['sid']
      }
    }).then(resp => {
      this.orders = resp.data
      for (let item of this.orders) {
        if (!isNaN(Number(item.product_id)) && item.active === false) {
          axios({
            method: 'GET',
            url: `https://api.${ip}.${port}/api/product/`+item.product_id,
            headers: {
              Authorization: 'Bearer ' + localStorage['sid']
            }
          }).then(resp1 => {
            item.product_id = resp1.data
          })
        }
      }
      console.log(this.orders)
    })
  },
  methods: {
    doAddProduct() {
      this.productForm.total_count = this.productForm.available_count
      axios({
        method: 'POST',
        url: `https://api.${ip}.${port}/api/product/`,
        headers: {
          Authorization: 'Bearer ' + localStorage['sid']
        },
        data: this.productForm
      }).then(() => {
        this.alertProductForm = true
        this.alertProductFormInfo[1] = 'Продукт успешно добавлен'
        axios({
          method: 'GET',
          url: `https://api.${ip}.${port}/api/product/all`,
          headers: {
            Authorization: 'Bearer ' + localStorage['sid']
          }
        }).then(resp => {
          this.products = resp.data
          this.addProductDialog = false
        })
      }).catch(() => {
        this.alertProductForm = true
        this.alertProductFormInfo[0] = 'red'
        this.alertProductFormInfo[1] = 'Ошибка при добавление'
      })
    },
    doFilterOrders() {
      if (this.chosenFilterOrders === this.curLocale.tabs.tab4.context.filters[0]) {
        axios({
          method: 'GET',
          url: `https://api.${ip}.${port}/api/basket/all`,
          headers: {
            Authorization: 'Bearer ' + localStorage['sid']
          }
        }).then(resp => {
          this.orders = resp.data
          for (let item of this.orders) {
            if (!isNaN(Number(item.product_id))) {
              axios({
                method: 'GET',
                url: `https://api.${ip}.${port}/api/product/`+item.product_id,
                headers: {
                  Authorization: 'Bearer ' + localStorage['sid']
                }
              }).then(resp1 => {
                item.product_id = resp1.data
              })
            }
          }
        })
      } else if (this.chosenFilterOrders === this.curLocale.tabs.tab4.context.filters[1]) {
        this.orders = this.orders.sort((a, b) => {
          const dateA = a.check_id.purchaseDate.split('T')[0] + 'T' + a.check_id.time
          const dateB = b.check_id.purchaseDate.split('T')[0] + 'T' + b.check_id.time
          if (dateA < dateB) {
            return -1;
          } else if (dateA > dateB) {
            return 1;
          }
          return 0;
        })
      }
    },
    createUser() {
      axios({
        method: 'POST',
        url: `https://api.${ip}.${port}/api/register`,
        data: this.userForm,
        headers: {
          Authorization: 'Bearer ' + localStorage['sid']
        }
      }).then(resp => {
        axios({
          method: 'GET',
          url: `https://api.${ip}.${port}/api/user/`+resp.data.id,
          headers: {
            Authorization: 'Bearer ' + localStorage['sid']
          }
        }).then(user => {
          this.usersInfo.push(user.data)

          for (let item of Object.keys(this.userForm)) {
            this.userForm[item] = ''
          }

          this.addUserAlert = true
          this.addUserAlertInfo[1] = this.curLocale.tabs.tab2.context.addUser.alerts[0]

          this.addAdminDialog = false
          this.stepperUserForm = 1
          this.chooseBtnUser = ''
        })
      }).catch(() => {
        this.addUserAlert = true
        this.addUserAlertInfo[2] = 'red'
        this.addUserAlertInfo[1] = this.curLocale.tabs.tab2.context.addUser.alerts[1]
      })
    },
    updateAllProduct(info) {
      this.products = info['items']
    },
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
        if (this.usersInfo[i].authorities.map(i => i.name).indexOf('ROLE_ADMIN') !== -1) res.push('Да')
        else res.push('Нет')
      }
      return res;
    },
    statisticClr() {
      let res = []
      for (let i=0;i<this.products.length;i++) {
        let item = this.products[i]
        let totalSells = item.total_count-item.available_count
        res.push(
            item.total_count===totalSells || item.total_count-5===totalSells?'green':item.total_count-10===totalSells?'purple':'red'
        )
      }
      return res;
    },

    getSells() {
      // sending GET
      let res = [];
      for (let i=0;i<this.products.length;i++) {
        let item = this.products[i]
        res.push(Math.ceil(((item.count-item.available_count)/item.count)*100)+'%')
      }
      return res;
    }
  },
}
</script>

<style scoped>
</style>