<template>
  <v-app>
    <v-card style="margin: 8% 25% 0 25%">
      <v-tabs v-if="info.user_info !== null"
              v-model="tabs"
              grow
      >
        <v-tab>
          {{curLocale.tabs.tab1.name}}
        </v-tab>
<!--        <v-tab>-->
<!--          {{ curLocale.tabs.tab4.name }}-->
<!--        </v-tab>-->
        <v-tab>
          {{curLocale.tabs.tab2.name}}
        </v-tab>
        <v-tab>
          {{curLocale.tabs.tab3.name}}
        </v-tab>
        <v-tab-item transition="fade-transition">
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
              <v-row no-gutters>
                <v-col>
                  <v-row>
                    <v-col>
                      <v-text-field
                          :label="curLocale.tabs.tab1.context.userInfo.username"
                          v-model="info.user_info.username"
                          readonly
                          rounded
                      ></v-text-field>
                    </v-col>
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
                          :label="curLocale.tabs.tab1.context.userInfo.lName"
                          v-model="info.user_info.lName"
                          readonly
                          rounded
                      ></v-text-field>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col cols="6">
                      <v-text-field
                          :label="curLocale.tabs.tab1.context.userInfo.email"
                          v-model="info.user_info.email"
                          readonly
                          rounded
                      ></v-text-field>
                    </v-col>
                    <v-col cols="6">
                      <v-text-field
                          :label="curLocale.tabs.tab1.context.userInfo.phone"
                          v-model="info.user_info.phone"
                          readonly
                          rounded
                      ></v-text-field>
                    </v-col>
                    <v-col>
                      <v-text-field
                          :label="curLocale.tabs.tab1.context.userInfo.group"
                          v-model="info.user_info.personGroup"
                          readonly
                          rounded
                      ></v-text-field>
                    </v-col>
                    <v-col>
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
                      <v-list-group no-action sub-group v-for="(item, i) in info.last_buy.items" :key="i">
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
              <v-row>
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
                          ФИО: {{info.user_info.fName}} {{info.user_info.lName}}
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
                          Группа: {{info.user_info.personGroup}}
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
<!--        <v-tab-item transition="fade-transition">-->
<!--          <v-card max-height="450">-->
<!--            <div style="justify-content: center;display: flex;padding: 5%">-->
<!--              <v-hover v-slot:default="{hover}">-->
<!--                <v-btn color="primary" outlined @click="addForm = true">-->
<!--                  <span v-if="hover">-->
<!--                    {{ curLocale.tabs.tab4.context.btns[0] }}-->
<!--                  </span>-->
<!--                  <v-icon>-->
<!--                    add-->
<!--                  </v-icon>-->
<!--                </v-btn>-->
<!--              </v-hover>-->
<!--              <v-hover v-slot:default="{hover}">-->
<!--                <v-btn color="primary" outlined @click="editForm = true">-->
<!--                  <span v-if="hover">-->
<!--                    {{ curLocale.tabs.tab4.context.btns[1] }}-->
<!--                  </span>-->
<!--                  <v-icon>-->
<!--                    edit-->
<!--                  </v-icon>-->
<!--                </v-btn>-->
<!--              </v-hover>-->
<!--              <v-hover v-slot:default="{hover}">-->
<!--                <v-btn color="primary" outlined @click="deleteForm = true">-->
<!--                  <span v-if="hover">-->
<!--                    {{ curLocale.tabs.tab4.context.btns[2] }}-->
<!--                  </span>-->
<!--                  <v-icon>-->
<!--                    delete-->
<!--                  </v-icon>-->
<!--                </v-btn>-->
<!--              </v-hover>-->
<!--            </div>-->
<!--            <v-divider></v-divider>-->
<!--            <v-list v-if="info.user_info.templates.length > 0">-->
<!--              <v-list-group v-for="(item, i) in info.user_info.templates" :key="i">-->
<!--                <template v-slot:activator>-->
<!--                  <v-list-item-title>-->
<!--                    {{item.name}}-->
<!--                  </v-list-item-title>-->
<!--                </template>-->
<!--                <v-list-group sub-group no-action v-for="(item1, j) in item.products" :key="j">-->
<!--                  <template v-slot:activator>-->
<!--                    <v-list-item-title>-->
<!--                      {{item1.name}} | {{item1.price}}-->
<!--                    </v-list-item-title>-->
<!--                  </template>-->
<!--                  <v-list-item>-->
<!--                    <v-list-item-title>-->
<!--                      Описание: {{item1.description}} <br>-->
<!--                      Категория: {{item1.category}}-->
<!--                    </v-list-item-title>-->
<!--                  </v-list-item>-->
<!--                </v-list-group>-->
<!--              </v-list-group>-->
<!--            </v-list>-->
<!--            <div v-else style="margin: 10%">-->
<!--              <v-icon style="text-align: center;display: block">warning</v-icon>-->
<!--              <v-card-title style="justify-content: center">-->
<!--                Шаблоны отсутствуют-->
<!--              </v-card-title>-->
<!--            </div>-->
<!--            <v-dialog width="450" persistent v-model="addForm">-->
<!--              <v-card>-->
<!--                <v-card-title>-->
<!--                  {{ curLocale.tabs.tab4.context.forms.add.title }}-->
<!--                  <v-spacer></v-spacer>-->
<!--                  <v-btn icon @click="addForm = false">-->
<!--                    <v-icon>-->
<!--                      close-->
<!--                    </v-icon>-->
<!--                  </v-btn>-->
<!--                </v-card-title>-->
<!--                <v-divider></v-divider>-->
<!--                <v-container>-->
<!--                  <v-row>-->
<!--                    <v-col cols="12">-->
<!--                      <v-text-field-->
<!--                          :label="curLocale.tabs.tab4.context.forms.add.labels[0]"-->
<!--                          v-model="templateForm.add.name"-->
<!--                          solo-->
<!--                      ></v-text-field>-->
<!--                    </v-col>-->
<!--                    <v-col cols="12">-->
<!--                      <v-radio-group>-->
<!--                        <v-radio-->
<!--                            :label="curLocale.tabs.tab4.context.forms.add.labels[1]"-->
<!--                            @input="getBoughtProducts"-->
<!--                        ></v-radio>-->
<!--                        <v-radio-->
<!--                            :label="curLocale.tabs.tab4.context.forms.add.labels[2]"-->
<!--                            @input="getDefaultsProducts"-->
<!--                        ></v-radio>-->
<!--                      </v-radio-group>-->
<!--                    </v-col>-->
<!--                    <v-col cols="12">-->
<!--                      <v-select-->
<!--                          :label="curLocale.tabs.tab4.context.forms.add.labels[3]"-->
<!--                          multiple-->
<!--                          :items="getProducts"-->
<!--                          v-model="templateForm.add.products"-->
<!--                      ></v-select>-->
<!--                    </v-col>-->
<!--                  </v-row>-->
<!--                </v-container>-->
<!--                <v-btn color="primary" block outlined @click="doAddTemplate">-->
<!--                  {{curLocale.tabs.tab4.context.forms.add.btnTitle}}-->
<!--                </v-btn>-->
<!--              </v-card>-->
<!--            </v-dialog>-->
<!--            <v-dialog width="450" persistent v-model="editForm">-->
<!--              <v-card>-->
<!--                <v-card-title>-->
<!--                  {{curLocale.tabs.tab4.context.forms.edit.title}}-->
<!--                  <v-spacer></v-spacer>-->
<!--                  <v-btn icon @click="editForm = false">-->
<!--                    <v-icon>-->
<!--                      close-->
<!--                    </v-icon>-->
<!--                  </v-btn>-->
<!--                </v-card-title>-->
<!--                <v-divider></v-divider>-->
<!--                <v-container>-->
<!--                  <v-row>-->
<!--                    <v-col cols="12">-->
<!--                      <v-select-->
<!--                          :label="curLocale.tabs.tab4.context.forms.edit.labels[0]"-->
<!--                          v-model="templateForm.chosenTemplate"-->
<!--                          :items="getMappedTemplates"-->
<!--                          @input="chooseTemplate"-->
<!--                      ></v-select>-->
<!--                      <v-text-field-->
<!--                          :label="curLocale.tabs.tab4.context.forms.edit.labels[1]"-->
<!--                          v-model="templateForm.edit.name"-->
<!--                          :placeholder="templateForm.edit.name"-->
<!--                          solo-->
<!--                      ></v-text-field>-->
<!--                    </v-col>-->
<!--                    <v-col cols="12">-->
<!--                      <v-radio-group>-->
<!--                        <v-radio-->
<!--                            :label="curLocale.tabs.tab4.context.forms.edit.labels[2]"-->
<!--                            @input="getBoughtProducts"-->
<!--                        ></v-radio>-->
<!--                        <v-radio-->
<!--                            :label="curLocale.tabs.tab4.context.forms.edit.labels[3]"-->
<!--                            @input="getDefaultsProducts"-->
<!--                        ></v-radio>-->
<!--                      </v-radio-group>-->
<!--                    </v-col>-->
<!--                    <v-col cols="12">-->
<!--                      <v-select-->
<!--                          :label="curLocale.tabs.tab4.context.forms.edit.labels[4]"-->
<!--                          multiple-->
<!--                          :items="templateForm.edit.products"-->
<!--                          v-model="templateForm.edit.products"-->
<!--                      ></v-select>-->
<!--                    </v-col>-->
<!--                  </v-row>-->
<!--                </v-container>-->
<!--                <v-btn color="primary" block outlined @click="doEditTemplate">-->
<!--                  {{curLocale.tabs.tab4.context.forms.edit.btnTitle}}-->
<!--                </v-btn>-->
<!--              </v-card>-->
<!--            </v-dialog>-->
<!--            <v-dialog persistent v-model="deleteForm" width="450">-->
<!--              <v-card>-->
<!--                <v-card-title>-->
<!--                  {{curLocale.tabs.tab4.context.forms.delete.title}}-->
<!--                  <v-spacer></v-spacer>-->
<!--                  <v-btn icon @click="deleteForm = false">-->
<!--                    <v-icon>-->
<!--                      close-->
<!--                    </v-icon>-->
<!--                  </v-btn>-->
<!--                </v-card-title>-->
<!--                <v-container>-->
<!--                  <v-row>-->
<!--                    <v-col>-->
<!--                      <v-select-->
<!--                          :label="curLocale.tabs.tab4.context.forms.delete.labels[0]"-->
<!--                          v-model="templateForm.chosenTemplate"-->
<!--                          :items="getMappedTemplates"-->
<!--                          @input="chooseTemplate"-->
<!--                      ></v-select>-->
<!--                    </v-col>-->
<!--                  </v-row>-->
<!--                </v-container>-->
<!--                <v-btn color="primary" block outlined @click="doDeleteTemplate">-->
<!--                  {{curLocale.tabs.tab4.context.forms.delete.btnTitle}}-->
<!--                </v-btn>-->
<!--              </v-card>-->
<!--            </v-dialog>-->
<!--          </v-card>-->
<!--        </v-tab-item>-->
        <v-tab-item transition="fade-transition">
          <v-card flat max-height="400" min-height="500" style="overflow-x: hidden; overflow-y: auto">
            <div v-if="info.orders.length > 0" >
              <v-container>
                <v-row>
                  <v-col v-for="(order, i) in info.orders" :key="i" cols="4">
                    <v-card flat>
                      <v-menu offset-x>
                        <template v-slot:activator="{on, attrs}">
                          <v-img :src="order.product_id.image" width="120" height="120"></v-img>
                          <v-card-subtitle>
                            {{curLocale.tabs.tab2.context.receipt.title}} №{{order.check_id.check_id}}
                            <v-btn icon large v-on="on" v-bind="attrs">
                              <v-icon>
                                keyboard_arrow_right
                              </v-icon>
                            </v-btn>
                          </v-card-subtitle>
                        </template>
                        <v-card>
                          <v-card-subtitle>
                            {{curLocale.tabs.tab2.context.receipt.labels[0]}} {{order.check_id.canteen}} <br/>
                            {{curLocale.tabs.tab2.context.receipt.labels[1]}} {{order.product_id.name}} <br/>
                            {{curLocale.tabs.tab2.context.receipt.labels[2]}} {{order.check_id.purchaseDate.split('T')[0]}} <br/>
                            {{curLocale.tabs.tab2.context.receipt.labels[3]}} {{order.product_id.description}} <br/>
                            {{curLocale.tabs.tab2.context.receipt.labels[4]}} {{order.product_id.category}} <br/>
                            {{curLocale.tabs.tab2.context.receipt.labels[5]}} {{order.product_id.price}} {{curLocale.tabs.tab2.context.receipt.currency}} <br/>
                          </v-card-subtitle>
                        </v-card>
                      </v-menu>
                    </v-card>
                  </v-col>
                </v-row>
              </v-container>
            </div>
            <div v-else style="margin: 10%">
              <v-icon style="text-align: center;display: block">warning</v-icon>
              <v-card-title style="justify-content: center">
                {{curLocale.tabs.tab2.context.notFound}}
              </v-card-title>
            </div>
          </v-card>
        </v-tab-item>
        <v-tab-item transition="fade-transition">
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
                        v-model="info.user_info.lName"
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
                        v-model="info.user_info.personGroup"
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
              v-model="editAlertSuccess"
              color="success"
              multi-line
              top
              timeout="2500"
          >
              {{curLocale.tabs.tab3.context.alert[0]}}
              <template v-slot:action="{attrs}">
                  <v-btn
                      v-bind="attrs"
                      text
                      @click="editAlertSuccess = false"
                  >
                    {{curLocale.tabs.tab3.context.alert[1]}}
                  </v-btn>
              </template>
          </v-snackbar>
          <v-snackbar
              v-model="editAlertErr"
              color="red"
              multi-line
              top
              timeout="2500"
          >
            {{curLocale.tabs.tab3.context.alert[2]}}
            <template v-slot:action="{attrs}">
              <v-btn
                  v-bind="attrs"
                  text
                  @click="editAlertErr = false"
              >
                {{curLocale.tabs.tab3.context.alert[1]}}
              </v-btn>
            </template>
          </v-snackbar>
        </v-tab-item>
      </v-tabs>
      <div v-else style="margin: 10%">
        <v-icon style="text-align: center;display: block">warning</v-icon>
        <v-card-title style="justify-content: center">
          {{curLocale.notFound}}
        </v-card-title>
      </div>
    </v-card>
  </v-app>
</template>

<script>

  const axios = require('axios');
  const ip = 'nurecanteen'
  const port = 'pp.ua';
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
                    username: 'Username:',
                    fname: 'First name:',
                    lName: 'Second name:',
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
                    'Close',
                    'Error on the server. Try later...'
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
            },
            notFound: 'Cabinet is not available'
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
                  title: 'Личные данные',
                  userInfo: {
                    username: 'Логин:',
                    fname: 'Имя',
                    lName: 'Фамилия',
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
                    labels: [
                      'Столовая:',
                      'Продукт:',
                      'Дата покупки:',
                      'Описание:',
                      'Категория:',
                      'Цена:'
                    ],
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
                    'Закрыть',
                    'Ошибка на сервере. Попробуйте позже...'
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
            },
            notFound: 'Кабинет не доступен'
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
                    username: 'Логін:',
                    fname: 'Ім`я',
                    lName: 'Прізвище',
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
                name: 'Мої замовлення',
                context: {
                  receipt: {
                    title: 'Чек',
                    labels: [
                      'Їдальня:',
                      'Продукт:',
                      'Дата купівлі:',
                      'Опис:',
                      'Категория:',
                      'Ціна:'
                    ],
                    currency: 'ГРН'
                  },
                  notFound: 'Замовлень не знайденно'
                }
              },
              tab3: {
                name: 'Налаштування',
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
                  btnTitle: 'Змінити',
                  alert: [
                    'Данні були змінені',
                    'Закрити',
                    'Помилка на сервері. Спробуйте пізніше...'
                  ]
                }
              },
              tab4: {
                name: 'Мої шаблони',
                context: {
                  forms: {
                    add: {
                      title: 'Додати шаблон',
                      labels: [
                        'Названня шаблона',
                        'На основі купівель',
                        'На основі усіх продуктів',
                        'Вибор продуків'
                      ],
                      btnTitle: 'Створити'
                    },
                    edit: {
                      title: 'Редагування шаблона',
                      labels: [
                        'Виберіть шаблон',
                        'Названня шаблона',
                        'На основі купівель',
                        'На основі усіх продуктів',
                        'Вибор продуків'
                      ],
                      btnTitle: 'Оновити'
                    },
                    delete: {
                      title: 'Видалення шаблона',
                      labels: [
                        'Виберіть шаблон'
                      ],
                      btnTitle: 'Видалити'
                    }
                  }
                }
              }
            },
            notFound: 'Кабінет не доступен'
          }
        },
        tabs: false,
        viewsData: 'default',
        editAlertSuccess: false,
        editAlertErr: false,
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
            fName: '',
            lName: '',
            email: '',
            phone: '',
            personGroup: '',
            age: ''
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
              check_id: {
                canteen: 'First ',
                check_id: 0,
                purchaseDate: '2020-12-06T22:00:00.000+0000',
                time: '15:51:20',
                sum: 23
              },
              count: 1,
              product_id: {
                id: 0,
                name: "Пирожок",
                price: 54,
                category: "Пряности",
                description: "вкусный",
                total_count: 20,
                available_count: 20,
                image: require("@/assets/imgs/572f9a16875ed15491f1e81a.png")
              }
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
        if (this.checkChange() && !this.editAlertSuccess) {
          // sending POST
          axios.put(`https://${ip}.${port}/api/user`,{
            fName: this.info.user_info.fName,
            lName: this.info.user_info.lName,
            email: this.info.user_info.email,
            phone: this.info.user_info.phone,
            personGroup: this.info.user_info.personGroup,
            age: this.info.user_info.age
          }, {
            headers: {
              Authorization: 'Bearer ' + localStorage['sid']
            }
          }).then(() => {
            axios.get(`https://${ip}.${port}/api/user`, {
              headers: {
                Authorization: 'Bearer ' + localStorage['sid']
              }
            }).then(resp => {
              console.log(resp.data)
              this.editAlertSuccess = true;
              this.info.user_info = resp.data
              if (resp.data['templates'] === undefined) {
                this.info.user_info['templates'] = [
                  {
                    id: 0,
                    name: 'Еда на завтрак',
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
              }
            }).catch(err => {
              console.error(err)
              this.editAlertErr = true
            })
          }).catch(err => {
            console.error(err)
            this.editAlertErr = true
          })
        }
      },
      checkChange() {
        return this.fname === this.info.user_info.fName ||
            this.lName === this.info.user_info.lName ||
            this.age === this.info.user_info.age ||
            this.group === this.info.user_info.personGroup ||
            this.email === this.info.user_info.email ||
            ((this.fname || this.lName || this.age || this.group || this.email || this.phone) !== '');
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
      axios.get(`https://${ip}.${port}/api/user`, {
        headers: {
          Authorization: 'Bearer ' + localStorage['sid']
        }
      }).then(resp => {
        this.info.user_info=resp.data
        if (this.info.user_info.fName === '') this.info.user_info.fName = 'Не указан'
        if (this.info.user_info.lName === '') this.info.user_info.lName = 'Не указан'
        if (this.info.user_info.phone === '') this.info.user_info.phone = 'Не указан'
        if (this.info.user_info.personGroup === '') this.info.user_info.personGroup = 'Не указан'
        if (this.info.user_info.email === '') this.info.user_info.email = 'Не указан'
        if (resp.data['templates'] === undefined) {
          this.info.user_info['templates'] =[
            {
              id: 0,
              name: 'Еда на завтрак',
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
        }
      }).catch(err => console.error(err))

      axios.get(`https://${ip}.${port}/api/user`, {
        headers: {
          Authorization: 'Bearer ' + localStorage['sid']
        }
      }).then(user_id => {
        axios({
          method: 'GET',
          url: `https://${ip}.${port}/api/basket/all`,
          headers: {
            Authorization: 'Bearer ' + localStorage['sid']
          }
        }).then(resp => {
          this.info.orders = resp.data.filter(i => i.user === user_id.data.id)

          for (let item of resp.data) {
            let date = item.check_id.purchaseDate.split('T')[0]+ '  ' + item.check_id.time
            if (new Date().toISOString() >= new Date(date).toISOString()) {
              this.info.last_buy = item
            }
          }

          let nanObject = false
          this.info.last_buy['total_price'] = this.info.last_buy.check_id.sum
          this.info.last_buy['date'] = this.info.last_buy.check_id.purchaseDate.split('T')[0]+ 'T' + this.info.last_buy.check_id.time
          this.info.last_buy['items'] = [
            isNaN(Number(this.info.last_buy.product_id)) ?
                this.info.last_buy.product_id:
                nanObject = true
          ]
          if (nanObject) {
            axios({
              method: 'GET',
              url: `https://${ip}.${port}/api/product/`+this.info.last_buy.product_id
            }).then(resp => {
              this.info.last_buy.items[0] = resp.data
            })
          }
          console.log(this.info.last_buy)

          this.info.orders.sort((a, b) => {
            if (a.check_id.check_id < b.check_id.check_id) {
              return -1;
            }
            if (a.check_id.check_id > b.check_id.check_id) {
              return 1;
            }
            return 0;
          })

          for (let i=0;i<this.info.orders.length;i++) {
            if (isNaN(Number(this.info.orders[i].product_id))) {
              this.info.orders[i].product_id['nema'] = ''
            } else {
              axios({
                method: 'GET',
                url: `https://${ip}.${port}/api/product/`+this.info.orders[i].product_id,
                headers: {
                  Authorization: 'Bearer ' + localStorage['sid']
                }
              }).then(resp => {
                this.info.orders[i].product_id = resp.data
              })
            }
          }
        })
      })
    }
  }
</script>

<style scoped>

</style>