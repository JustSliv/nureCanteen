<template>
  <v-app>
    <v-card style="margin: 8% 25% 0 25%">
      <v-tabs
              v-model="tabs"
              grow
      >
        <v-tab>
          {{curLocale.tabs.tab1.name}}
        </v-tab>
        <v-tab>
          {{ curLocale.tabs.tab4.name }}
        </v-tab>
        <v-tab>
          {{curLocale.tabs.tab2.name}}
        </v-tab>
        <v-tab>
          {{curLocale.tabs.tab3.name}}
        </v-tab>
        <v-tab-item>
          <v-card max-height="450">
            <v-card-title style="justify-content: center; display: flex">
              <v-hover v-slot:default="{hover}">
                <v-btn outlined color="primary" @click="viewsData = 'icons'">
                  <span v-if="hover">
                    {{curLocale.tabs.tab1.context.viewsBtn[0]}}
                  </span>
                  <v-icon large>
                    view_sidebar
                  </v-icon>
                </v-btn>
              </v-hover>
              <v-spacer></v-spacer>
              <div class="p-5" v-if="viewsData === 'default'" :title="curLocale.tabs.tab1.context.avatarTip">
                <v-avatar>
                  <img :src="info.user_info.avatar"  alt="empty"/>
                </v-avatar>
              </div>
              <pre> {{curLocale.tabs.tab1.context.title}} </pre>
              <v-spacer></v-spacer>
              <v-hover v-slot:default="{hover}">
                <v-btn outlined color="primary" @click="viewsData = 'default'">
                  <span v-if="hover">
                    {{curLocale.tabs.tab1.context.viewsBtn[1]}}
                  </span>
                  <v-icon large>
                    view_headline
                  </v-icon>
                </v-btn>
              </v-hover>
            </v-card-title>
            <v-divider></v-divider>
            <v-container v-if="viewsData === 'default'">
              <v-row>
                <v-col>
                  <v-row>
                    <v-col>
                      <v-text-field
                          :label="curLocale.tabs.tab1.context.userInfo.fname"
                          v-model="info.user_info.fName"
                          readonly
                          rounded
                      ></v-text-field>
                    </v-col>
                    <v-col>
                      <v-text-field
                          :label="curLocale.tabs.tab1.context.userInfo.sname"
                          v-model="info.user_info.sName"
                          readonly
                          rounded
                      ></v-text-field>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col cols="8">
                      <v-text-field
                          :label="curLocale.tabs.tab1.context.userInfo.email"
                          v-model="info.user_info.email"
                          readonly
                          rounded
                      ></v-text-field>
                    </v-col>
                    <v-col>
                      <v-text-field
                          :label="curLocale.tabs.tab1.context.userInfo.group"
                          v-model="info.user_info.group"
                          readonly
                          rounded
                      ></v-text-field>
                    </v-col>
                    <v-col cols="6">
                      <v-text-field
                          :label="curLocale.tabs.tab1.context.userInfo.age"
                          v-model="info.user_info.age"
                          readonly
                          rounded
                      ></v-text-field>
                    </v-col>
                  </v-row>
                </v-col>
                <v-col>
                  <v-card-title v-if="info.last_buy.items.length > 0">
                    {{curLocale.tabs.tab1.context.lastBuy}}
                    <v-spacer></v-spacer>
                    <v-card-subtitle>
                      {{info.last_buy.date}}
                    </v-card-subtitle>
                  </v-card-title>
                  <v-divider></v-divider>
                  <v-list v-if="info.last_buy.items.length > 0" style="overflow-y: scroll; max-height: 200px">
                    <v-list-group>
                      <template v-slot:activator>
                        <v-list-item-title>
                          {{curLocale.tabs.tab1.context.lastBuyItem.sum}}
                        </v-list-item-title>
                        <v-item-group>
                          <v-list-item-title>{{info.last_buy.total_price}} {{curLocale.tabs.tab1.context.lastBuyItem.currency}}</v-list-item-title>
                        </v-item-group>
                      </template>
                      <v-list-group  no-action sub-group v-for="(item, i) in info.last_buy.items" :key="i">
                        <template v-slot:activator>
                          <v-list-item-title>{{item.name}}</v-list-item-title>
                          <v-item-group>
                            <v-list-item-title>{{item.price}} {{curLocale.tabs.tab1.context.lastBuyItem.currency}}</v-list-item-title>
                          </v-item-group>
                        </template>
                        <v-list-item>
                          <v-list-item-title>{{item.description}}</v-list-item-title>
                        </v-list-item>
                      </v-list-group>
                    </v-list-group>
                  </v-list>
                  <v-card flat v-else style="margin: 5%">
                    <v-icon style="text-align: center;display: block">
                      warning
                    </v-icon>
                    <v-card-title style="justify-content: center">
                      {{curLocale.tabs.tab1.context.lastBuyNotFound}}
                    </v-card-title>
                  </v-card>
                </v-col>
              </v-row>
            </v-container>
            <v-container v-if="viewsData === 'icons'">
              <v-row align="middle">
                <v-col >
                  <v-img width="280" height="300" :src="info.user_info.avatar"></v-img>
                </v-col>
                <v-col>
                  <v-row>
                    <v-col>
                      <v-tooltip transition="fab-transition" right color="primary">
                        <template v-slot:activator="{on, attrs}">
                          <v-icon
                              large
                              v-bind="attrs"
                              v-on="on"
                          >
                            person
                          </v-icon>
                        </template>
                        <v-card-title>
                          ФИО: {{info.user_info.fName}} {{info.user_info.sName}}
                        </v-card-title>
                      </v-tooltip>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col>
                      <v-tooltip transition="fab-transition" right color="primary">
                        <template v-slot:activator="{on, attrs}">
                          <v-icon
                              large
                              v-bind="attrs"
                              v-on="on"
                          >
                            access_time
                          </v-icon>
                        </template>
                        <v-card-title>
                          Возраст: {{info.user_info.age}}
                        </v-card-title>
                      </v-tooltip>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col>
                      <v-tooltip transition="fab-transition" right color="primary">
                        <template v-slot:activator="{on, attrs}">
                          <v-icon
                              large
                              v-bind="attrs"
                              v-on="on"
                          >
                            email
                          </v-icon>
                        </template>
                        <v-card-title>
                          E-mail: {{info.user_info.email}}
                        </v-card-title>
                      </v-tooltip>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col>
                      <v-tooltip transition="fab-transition" right color="primary">
                        <template v-slot:activator="{on, attrs}">
                          <v-icon
                              large
                              v-bind="attrs"
                              v-on="on"
                          >
                            group
                          </v-icon>
                        </template>
                        <v-card-title>
                          Группа: {{info.user_info.group}}
                        </v-card-title>
                      </v-tooltip>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col>
                      <v-tooltip transition="fab-transition" right color="primary">
                        <template v-slot:activator="{on, attrs}">
                          <v-icon
                              large
                              v-bind="attrs"
                              v-on="on"
                          >
                            phone
                          </v-icon>
                        </template>
                        <v-card-title>
                          Телефон: {{info.user_info.phone}}
                        </v-card-title>
                      </v-tooltip>
                    </v-col>
                  </v-row>
                </v-col>
                <v-col>
                  <v-card-title v-if="info.last_buy.items.length > 0">
                    {{curLocale.tabs.tab1.context.lastBuy}}
                    <v-card-subtitle>
                      {{info.last_buy.date}}
                    </v-card-subtitle>
                  </v-card-title>
                  <v-divider></v-divider>
                  <v-list v-if="info.last_buy.items.length > 0" style="overflow-y: scroll; max-height: 200px">
                    <v-list-group>
                      <template v-slot:activator>
                        <v-list-item-title>
                          {{curLocale.tabs.tab1.context.lastBuyItem.sum}}
                        </v-list-item-title>
                        <v-item-group>
                          <v-list-item-title>{{info.last_buy.total_price}} {{curLocale.tabs.tab1.context.lastBuyItem.currency}}</v-list-item-title>
                        </v-item-group>
                      </template>
                      <v-list-group  no-action sub-group v-for="(item, i) in info.last_buy.items" :key="i">
                        <template v-slot:activator>
                          <v-list-item-title>{{item.name}}</v-list-item-title>
                          <v-item-group>
                            <v-list-item-title>{{item.price}} {{curLocale.tabs.tab1.context.lastBuyItem.currency}}</v-list-item-title>
                          </v-item-group>
                        </template>
                        <v-list-item>
                          <v-list-item-title>{{item.description}}</v-list-item-title>
                        </v-list-item>
                      </v-list-group>
                    </v-list-group>
                  </v-list>
                  <v-card flat v-else style="margin: 5%">
                    <v-icon style="text-align: center;display: block">
                      warning
                    </v-icon>
                    <v-card-title style="justify-content: center">
                      {{curLocale.tabs.tab1.context.lastBuyNotFound}}
                    </v-card-title>
                  </v-card>
                </v-col>
              </v-row>
            </v-container>
          </v-card>
        </v-tab-item>
        <v-tab-item>
          <v-card max-height="450">
            <div style="justify-content: center;display: flex;padding: 5%">
              <v-hover v-slot:default="{hover}">
                <v-btn color="primary" outlined @click="addForm = true">
                  <span v-if="hover">
                    {{ curLocale.tabs.tab4.context.btns[0] }}
                  </span>
                  <v-icon>
                    add
                  </v-icon>
                </v-btn>
              </v-hover>
              <v-hover v-slot:default="{hover}">
                <v-btn color="primary" outlined @click="editForm = true">
                  <span v-if="hover">
                    {{ curLocale.tabs.tab4.context.btns[1] }}
                  </span>
                  <v-icon>
                    edit
                  </v-icon>
                </v-btn>
              </v-hover>
              <v-hover v-slot:default="{hover}">
                <v-btn color="primary" outlined @click="deleteForm = true">
                  <span v-if="hover">
                    {{ curLocale.tabs.tab4.context.btns[2] }}
                  </span>
                  <v-icon>
                    delete
                  </v-icon>
                </v-btn>
              </v-hover>
            </div>
            <v-divider></v-divider>
            <v-list v-if="info.user_info.templates.length > 0">
              <v-list-group v-for="(item, i) in info.user_info.templates" :key="i">
                <template v-slot:activator>
                  <v-list-item-title>
                    {{item.name}}
                  </v-list-item-title>
                </template>
                <v-list-group sub-group no-action v-for="(item1, j) in item.products" :key="j">
                  <template v-slot:activator>
                    <v-list-item-title>
                      {{item1.name}} | {{item1.price}}
                    </v-list-item-title>
                  </template>
                  <v-list-item>
                    <v-list-item-title>
                      Описание: {{item1.description}} <br>
                      Категория: {{item1.category}}
                    </v-list-item-title>
                  </v-list-item>
                </v-list-group>
              </v-list-group>
            </v-list>
            <div v-else style="margin: 10%">
              <v-icon style="text-align: center;display: block">warning</v-icon>
              <v-card-title style="justify-content: center">
                Шаблоны отсутствуют
              </v-card-title>
            </div>
            <v-dialog width="450" persistent v-model="addForm">
              <v-card>
                <v-card-title>
                  {{ curLocale.tabs.tab4.context.forms.add.title }}
                  <v-spacer></v-spacer>
                  <v-btn icon @click="addForm = false">
                    <v-icon>
                      close
                    </v-icon>
                  </v-btn>
                </v-card-title>
                <v-divider></v-divider>
                <v-container>
                  <v-row>
                    <v-col cols="12">
                      <v-text-field
                          :label="curLocale.tabs.tab4.context.forms.add.labels[0]"
                          v-model="templateForm.add.name"
                          solo
                      ></v-text-field>
                    </v-col>
                    <v-col cols="12">
                      <v-radio-group>
                        <v-radio
                            :label="curLocale.tabs.tab4.context.forms.add.labels[1]"
                            @input="getBoughtProducts"
                        ></v-radio>
                        <v-radio
                            :label="curLocale.tabs.tab4.context.forms.add.labels[2]"
                            @input="getDefaultsProducts"
                        ></v-radio>
                      </v-radio-group>
                    </v-col>
                    <v-col cols="12">
                      <v-select
                          :label="curLocale.tabs.tab4.context.forms.add.labels[3]"
                          multiple
                          :items="getProducts"
                          v-model="templateForm.add.products"
                      ></v-select>
                    </v-col>
                  </v-row>
                </v-container>
                <v-btn color="primary" block outlined @click="doAddTemplate">
                  {{curLocale.tabs.tab4.context.forms.add.btnTitle}}
                </v-btn>
              </v-card>
            </v-dialog>
            <v-dialog width="450" persistent v-model="editForm">
              <v-card>
                <v-card-title>
                  {{curLocale.tabs.tab4.context.forms.edit.title}}
                  <v-spacer></v-spacer>
                  <v-btn icon @click="editForm = false">
                    <v-icon>
                      close
                    </v-icon>
                  </v-btn>
                </v-card-title>
                <v-divider></v-divider>
                <v-container>
                  <v-row>
                    <v-col cols="12">
                      <v-select
                          :label="curLocale.tabs.tab4.context.forms.edit.labels[0]"
                          v-model="templateForm.chosenTemplate"
                          :items="getMappedTemplates"
                          @input="chooseTemplate"
                      ></v-select>
                      <v-text-field
                          :label="curLocale.tabs.tab4.context.forms.edit.labels[1]"
                          v-model="templateForm.edit.name"
                          :placeholder="templateForm.edit.name"
                          solo
                      ></v-text-field>
                    </v-col>
                    <v-col cols="12">
                      <v-radio-group>
                        <v-radio
                            :label="curLocale.tabs.tab4.context.forms.edit.labels[2]"
                            @input="getBoughtProducts"
                        ></v-radio>
                        <v-radio
                            :label="curLocale.tabs.tab4.context.forms.edit.labels[3]"
                            @input="getDefaultsProducts"
                        ></v-radio>
                      </v-radio-group>
                    </v-col>
                    <v-col cols="12">
                      <v-select
                          :label="curLocale.tabs.tab4.context.forms.edit.labels[4]"
                          multiple
                          :items="templateForm.edit.products"
                          v-model="templateForm.edit.products"
                      ></v-select>
                    </v-col>
                  </v-row>
                </v-container>
                <v-btn color="primary" block outlined @click="doEditTemplate">
                  {{curLocale.tabs.tab4.context.forms.edit.btnTitle}}
                </v-btn>
              </v-card>
            </v-dialog>
            <v-dialog persistent v-model="deleteForm" width="450">
              <v-card>
                <v-card-title>
                  {{curLocale.tabs.tab4.context.forms.delete.title}}
                  <v-spacer></v-spacer>
                  <v-btn icon @click="deleteForm = false">
                    <v-icon>
                      close
                    </v-icon>
                  </v-btn>
                </v-card-title>
                <v-container>
                  <v-row>
                    <v-col>
                      <v-select
                          :label="curLocale.tabs.tab4.context.forms.delete.labels[0]"
                          v-model="templateForm.chosenTemplate"
                          :items="getMappedTemplates"
                          @input="chooseTemplate"
                      ></v-select>
                    </v-col>
                  </v-row>
                </v-container>
                <v-btn color="primary" block outlined @click="doDeleteTemplate">
                  {{curLocale.tabs.tab4.context.forms.delete.btnTitle}}
                </v-btn>
              </v-card>
            </v-dialog>
          </v-card>
        </v-tab-item>
        <v-tab-item>
            <v-card flat max-height="450" style="overflow-x: hidden; overflow-y: auto">
                <v-list v-if="info.orders.length > 0">
                    <v-list-group v-for="(order, i) in info.orders" :key="i">
                        <template v-slot:activator>
                            <v-list-item-title>{{curLocale.tabs.tab2.context.receipt.title}} №{{order.receipt_id}}</v-list-item-title>
                            <v-item-group>
                                <v-list-item-title>{{order.total_price}} {{curLocale.tabs.tab2.context.receipt.currency}}</v-list-item-title>
                            </v-item-group>
                        </template>
                        <v-list-group sub-group no-action v-for="(item, j) in order.items" :key="j">
                            <template v-slot:activator>
                                <v-list-item-title>{{item.name}}</v-list-item-title>
                                <v-item-group>
                                    <v-list-item-title>{{item.price}} {{curLocale.tabs.tab2.context.receipt.currency}}</v-list-item-title>
                                </v-item-group>
                            </template>
                            <v-list-item>
                                <v-list-item-subtitle>{{item.category}}</v-list-item-subtitle>
                                <v-list-item-title>{{item.description}}</v-list-item-title>
                            </v-list-item>
                        </v-list-group>
                    </v-list-group>
                </v-list>
                <div v-else style="margin: 10%">
                    <v-icon style="text-align: center;display: block">warning</v-icon>
                    <v-card-title style="justify-content: center">
                      {{curLocale.tabs.tab2.context.notFound}}
                    </v-card-title>
                </div>
            </v-card>
        </v-tab-item>
        <v-tab-item>
          <v-card>
            <v-form>
              <v-container>
                <v-row>
                  <v-col>
                    <v-text-field
                        :label="curLocale.tabs.tab3.context.labels[0]"
                        solo
                        v-model="info.user_info.fName"
                    ></v-text-field>
                  </v-col>
                  <v-col>
                    <v-text-field
                        :label="curLocale.tabs.tab3.context.labels[1]"
                        solo
                        v-model="info.user_info.sName"
                    ></v-text-field>
                  </v-col>
                </v-row>
                <v-row>
                  <v-col>
                    <v-text-field
                        type="number"
                        :label="curLocale.tabs.tab3.context.labels[2]"
                        solo
                        v-model="info.user_info.age"
                    ></v-text-field>
                  </v-col>
                  <v-col>
                    <v-text-field
                        :label="curLocale.tabs.tab3.context.labels[3]"
                        solo
                        v-model="info.user_info.group"
                    ></v-text-field>
                  </v-col>
                  </v-row>
                  <v-row>
                    <v-col>
                      <v-text-field
                          :label="curLocale.tabs.tab3.context.labels[4]"
                          solo
                          v-model="info.user_info.email"
                      ></v-text-field>
                    </v-col>
                    <v-col>
                      <v-text-field
                          :label="curLocale.tabs.tab3.context.labels[5]"
                          solo
                          v-model="info.user_info.phone"
                      ></v-text-field>
                    </v-col>
                    <v-col :title="curLocale.tabs.tab3.context.tipImage" cols="12">
                      <v-row no-gutters align="end">
                        <v-col>
                          <v-img width="150" height="150" :src="info.user_info.avatar" v-model="info.user_info.avatar"></v-img>
                        </v-col>
                        <v-col>
                          <v-file-input
                              @change="reRenderImg"
                          ></v-file-input>
                        </v-col>
                      </v-row>
                    </v-col>
                  </v-row>
                <v-btn block color="success" @click="editMyData">
                  {{curLocale.tabs.tab3.context.btnTitle}}
                </v-btn>
              </v-container>
            </v-form>
          </v-card>
          <v-snackbar
              v-model="editAlert"
              color="success"
              multi-line
              top
              timeout="4000"
          >
              {{curLocale.tabs.tab3.context.alert[0]}}
              <template v-slot:action>
                  <v-btn
                      text
                      @click="editAlert = false"
                  >
                    {{curLocale.tabs.tab3.context.alert[1]}}
                  </v-btn>
              </template>
          </v-snackbar>
        </v-tab-item>
      </v-tabs>
    </v-card>
  </v-app>
</template>

<script>
  // const axios = require('axios');
  export default {
    name: "Cabinet",
    data() {
      return {
        curLocale: {},
        locales: {
          'en-EN': {
            tabs: {
              tab1: {
                name: 'DASHBOARD',
                context: {
                  viewsBtn: [
                    'View icons',
                    'View default'
                  ],
                  avatarTip: 'Your avatar',
                  title: 'Personal info',
                  userInfo: {
                    fname: 'First name',
                    sname: 'Second name',
                    age: 'Age:',
                    group: 'Group:',
                    email: 'E-mail:',
                    phone: 'Phone:'
                  },
                  lastBuy: 'Date purchase',
                  lastBuyItem: {
                    sum: 'Paid ',
                    currency: 'UAH ',
                  },
                  lastBuyNotFound: 'You don`t buy anything yet'
                }
              },
              tab2: {
                name: 'MY ORDERS',
                context: {
                  receipt: {
                    title: 'Receipt',
                    currency: 'UAH'
                  },
                  notFound: 'Orders not found'
                }
              },
              tab3: {
                name: 'SETTINGS',
                context: {
                  labels: [
                    'First Name',
                    'Second Name',
                    'Age',
                    'Group',
                    'Email',
                    'Phone'
                  ],
                  tipImage: 'Your avatar',
                  btnTitle: 'Change',
                  alert: [
                    'Data were changing',
                    'Close'
                  ]
                }
              },
              tab4: {
                name: 'MY TEMPLATES',
                context: {
                  btns: [
                      'ADD',
                      'EDIT',
                      'DELETE'
                  ],
                  info: [
                      'Описание:',
                      'Категория:'
                  ],
                  forms: {
                    add: {
                      title: 'Add template',
                      labels: [
                        'Name of template',
                        'Based on purchases',
                        'Based on all products',
                        'Choose a product'
                      ],
                      btnTitle: 'Create'
                    },
                    edit: {
                      title: 'Edit template',
                      labels: [
                        'Choose template',
                        'Name of template',
                        'Based on purchases',
                        'Based on all products',
                        'Choose a product'
                      ],
                      btnTitle: 'Update'
                    },
                    delete: {
                      title: 'Delete template',
                      labels: [
                        'Choose template'
                      ],
                      btnTitle: 'Delete'
                    }
                  }
                }
              }
            }
          },
          'ru-RU': {
            tabs: {
              tab1: {
                name: 'Главная',
                context: {
                  viewsBtn: [
                      'Вид иконок',
                      'Вид обычный'
                  ],
                  avatarTip: 'Ваш аватар',
                  title: 'Личный данные',
                  userInfo: {
                    fname: 'Имя',
                    sname: 'Фамилия',
                    age: 'Возраст:',
                    group: 'Группа:',
                    email: 'Е-mail:',
                    phone: 'Телефон:'
                  },
                  lastBuy: 'Дата покупки',
                  lastBuyItem: {
                    sum: 'Оплачено ',
                    currency: ' ГРН',
                  },
                  lastBuyNotFound: 'Покупки отсутствуют'
                }
              },
              tab2: {
                name: 'Мои заказы',
                context: {
                  receipt: {
                    title: 'Чек',
                    currency: 'ГРН'
                  },
                  notFound: 'Заказы не найдены'
                }
              },
              tab3: {
                name: 'Настройки',
                context: {
                  labels: [
                    'Имя',
                    'Фамилия',
                    'Возраст',
                    'Группа',
                    'Email',
                    'Телефон'
                  ],
                  tipImage: 'Ваш аватар',
                  btnTitle: 'Изменить',
                  alert: [
                    'Данные изменены',
                    'Закрыть'
                  ]
                }
              },
              tab4: {
                name: 'Мои шаблоны',
                context: {
                  forms: {
                    add: {
                      title: 'Добавление шаблона',
                      labels: [
                          'Название шаблона',
                          'На основе покупок',
                          'На основе всех продуктов',
                          'Выбор продуктов'
                      ],
                      btnTitle: 'Создать'
                    },
                    edit: {
                      title: 'Редактирование шаблона',
                      labels: [
                        'Выберите шаблон',
                        'Название шаблона',
                        'На основе покупок',
                        'На основе всех продуктов',
                        'Выбор продуктов'
                      ],
                      btnTitle: 'Обновить'
                    },
                    delete: {
                      title: 'Удаление шаблона',
                      labels: [
                        'Выберите шаблон'
                      ],
                      btnTitle: 'Удалить'
                    }
                  }
                }
              }
            }
          },
          'ua-UA': {
            tabs: {
              tab1: {
                name: 'Головна',
                context: {
                  viewsBtn: [
                    'Вигляд іконок',
                    'Вигляд звичайний'
                  ],
                  avatarTip: 'Ваш аватар',
                  title: 'Особисті данні',
                  userInfo: {
                    fname: 'Ім`я',
                    sname: 'Прізвище',
                    age: 'Вік:',
                    group: 'Група:',
                    email: 'Е-mail:',
                    phone: 'Телефон:'
                  },
                  lastBuy: 'Дата купівлі',
                  lastBuyItem: {
                    sum: 'Оплачено ',
                    currency: 'ГРН ',
                  },
                  lastBuyNotFound: 'Немає покупок'
                }
              },
              tab2: {
                name: 'Мои заказі',
                context: {
                  receipt: {
                    title: 'Чек',
                    currency: 'ГРН'
                  },
                  notFound: 'Заказов не найдено'
                }
              },
              tab3: {
                name: 'Настройки',
                context: {
                  labels: [
                    'Ім`я',
                    'Прізвище',
                    'Вік',
                    'Група',
                    'Email',
                    'Телефон'
                  ],
                  tipImage: 'Ваш аватар',
                  btnTitle: 'Изменить',
                  alert: [
                    'Данні були змінені',
                    'Закрити'
                  ]
                }
              },
              tab4: {
                name: 'Мои шаблоны',
                context: {
                  forms: {
                    add: {
                      title: 'Добавление шаблона',
                      labels: [
                        'Название шаблона',
                        'На основе покупок',
                        'На основе всех продуктов',
                        'Выбор продуктов'
                      ],
                      btnTitle: 'Создать'
                    },
                    edit: {
                      title: 'Редактирование шаблона',
                      labels: [
                        'Выберите шаблон',
                        'Название шаблона',
                        'На основе покупок',
                        'На основе всех продуктов',
                        'Выбор продуктов'
                      ],
                      btnTitle: 'Обновить'
                    },
                    delete: {
                      title: 'Удаление шаблона',
                      labels: [
                        'Выберите шаблон'
                      ],
                      btnTitle: 'Удалить'
                    }
                  }
                }
              }
            }
          }
        },
        tabs: false,
        viewsData: 'default',
        editAlert: false,
        addForm: false,
        templateForm: {
          add: {
            name: '',
            products: []
          },
          edit: '',
          delItem: '',
          chosenTemplate: ''
        },
        editForm: false,
        deleteForm: false,
        info: {
          user_info: {
            fName: 'tim',
            sName: 'livr',
            email: 'tsda@mail.com',
            group: 'PZPI',
            phone: '02131343141',
            age: 20,
            avatar: 'data:image/png;base64,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',
            templates: [
              {
                id: 0,
                name: 'Херата на завтрак',
                products: [
                  {
                    id: 0,
                    name: "Пирожок",
                    price: 54,
                    category: "Пряности",
                    description: "вкусный",
                    total_count: 20,
                    available_count: 20,
                    image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
                  },
                  {
                    id: 1,
                    name: "Пирожок1",
                    price: 54,
                    category: "Пряности",
                    description: "вкусный",
                    total_count: 20,
                    available_count: 20,
                    image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
                  },
                  {
                    id: 2,
                    name: "Пирожок2",
                    price: 54,
                    category: "Напиток",
                    description: "вкусный",
                    total_count: 20,
                    available_count: 20,
                    image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
                  }
                ]
              },
              {
                id: 1,
                name: 'Ланч на вечер',
                products: [
                  {
                    id: 1,
                    name: "Пирожок1",
                    price: 54,
                    category: "Пряности",
                    description: "вкусный",
                    total_count: 20,
                    available_count: 20,
                    image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
                  },
                  {
                    id: 2,
                    name: "Пирожок2",
                    price: 54,
                    category: "Напиток",
                    description: "вкусный",
                    total_count: 20,
                    available_count: 20,
                    image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
                  }
                ]
              }
            ]
          },
          last_buy: {
            total_price: 23,
            date: "12.09.2020",
            items: [
              {
                id: 0,
                name: "Пирожок",
                category: "Духовный",
                description: "вкусный",
                price: 23
              },
              {
                id: 0,
                name: "Пирожок",
                category: "Духовный",
                description: "вкусный",
                price: 23
              },
              {
                id: 0,
                name: "Пирожок",
                category: "Духовный",
                description: "вкусный",
                price: 23
              },
              {
                id: 0,
                name: "Пирожок",
                category: "Духовный",
                description: "вкусный",
                price: 23
              }
            ]
          },
          orders: [
            {
              receipt_id: "675843",
              total_price: 7,
              date: "10.09.2020",
              items: [
                {
                  id: 1,
                  name: "Пирожок",
                  description: "вкусный",
                  price: 7
                }
              ]
            },
            {
              receipt_id: "123456",
              date: "12.09.2020",
              total_price: 25,
              items: [
                {
                  id: 0,
                  name: "Пирожок",
                  description: "вкусный",
                  price: 15
                },
                {
                  id: 2,
                  name: "Пирожок",
                  description: "вкусный",
                  price: 10
                }
              ]
            }
          ],
          products: [
            {
              id: 0,
              name: "Пирожок",
              price: 54,
              category: "Пряности",
              description: "вкусный",
              total_count: 20,
              available_count: 20,
              image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
            },
            {
              id: 1,
              name: "Пирожок1",
              price: 54,
              category: "Пряности",
              description: "вкусный",
              total_count: 20,
              available_count: 20,
              image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
            },
            {
              id: 2,
              name: "Пирожок2",
              price: 54,
              category: "Напиток",
              description: "вкусный",
              total_count: 20,
              available_count: 20,
              image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
            }
          ]
        }
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
      doAddTemplate() {
        // sending PUT
      },
      doEditTemplate() {
        // sending PUT with id
      },
      doDeleteTemplate() {
        // sending DELETE with id
      },
      chooseTemplate() {
        this.info.user_info.templates.filter(i => {
          if (i.name === this.templateForm.chosenTemplate) {
            this.templateForm.delItem = i.id
            this.templateForm.edit = {
              id: i.id,
              name: i.name,
              products: i.products.map(i => i.name + ' ' + i.price + ' UAH')
            }
          }
        })
      },
      reRenderImg(ev) {
        let reader = new FileReader();
        reader.onload = (e) => {
          this.info.user_info.avatar = e.target.result
        }
        reader.readAsDataURL(ev);
      },
      editMyData() {
        if (this.checkChange() && !this.editAlert) {
          // sending POST
          //-------------
          // after success sending POST
          this.$nextTick(() => {
              this.editAlert = true;
          })
          setTimeout(function (){
              window.location.reload();
          }, 1500)
          return 0;
        }
      },
      checkChange() {
        return this.fname === this.info.user_info.fName ||
            this.sName === this.info.user_info.sName ||
            this.age === this.info.user_info.age ||
            this.group === this.info.user_info.personGroup ||
            this.email === this.info.user_info.email ||
            ((this.fname || this.sName || this.age || this.group || this.email || this.phone) !== '');
      }
    },
    computed: {
      getProducts() {
        return this.info.products.map(i => i.name + ' ' + i.price + ' UAH')
      },
      getMappedTemplates() {
        return this.info.user_info.templates.map(i => i.name)
      },
      getBoughtProducts() {
        // sending GET to Drafts for current user
        return 0;
      },
      getDefaultsProducts() {
        // sending GET to Products
        return 0;
      }
    },
    mounted() {
      // axios.get('http://192.168.0.109:25016/api/person/5/', {
      //   headers: {
      //     'Access-Control-Allow-Origin': '*'
      //   }
      // }).then(resp => (this.info.user_info=resp.data))
    }
  }
</script>

<style scoped>

</style>