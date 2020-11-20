<template>
  <v-app style="z-index: 5">
      <v-card>
          <v-toolbar style="justify-content: center; display: flex">
              <router-link to="/admin/cabinet">
                <v-btn icon>
                  <v-icon>
                    perm_identity
                  </v-icon>
                </v-btn>
              </router-link>
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
              <v-tab>Продукты в продаже</v-tab>
              <v-tab>Пользователи</v-tab>
              <v-tab>Супер-панель</v-tab>
              <v-tab>Статистика</v-tab>
              <v-tab-item>
                  <v-card v-if="products.length > 0">
                      <v-list>
                          <v-list-group v-for="(item, i) in products" :key="i">
                            <template v-slot:activator>
                                <v-img :src="item.image" width="80" height="80" style="margin-right: 1%"></v-img>
                                <v-list-item-title :data-id="item.id">
                                  {{item.name}}
                                  <v-btn icon @click="delProduct">
                                    <v-icon>
                                      close
                                    </v-icon>
                                  </v-btn>
                                  <v-btn icon @click="showEditForm">
                                    <v-icon>
                                      edit
                                    </v-icon>
                                  </v-btn>
                                </v-list-item-title>
                                <v-item-group>
                                  <v-list-item-title>{{item.price}} UAH</v-list-item-title>
                                </v-item-group>
                            </template>
                            <v-item-group style="margin-left: 2%">
                                <v-list-item-content>
                                    <v-card-text>
                                        <v-list-item-subtitle>Категория: {{item.category}}</v-list-item-subtitle>
                                        Описание: {{item.description}} <br/>
                                        Доступно: {{item.available_count}}
                                    </v-card-text>
                                </v-list-item-content>
                            </v-item-group>
                          </v-list-group>
                      </v-list>
                      <v-dialog v-model="editDialog" width="650">
                        <v-card>
                          <v-card-title :key="renderName">
                            Редактировать продукт "{{preloadInfo.name}}"
                            <v-spacer></v-spacer>
                            <v-img width="50" height="140" :src="preloadInfo.image" v-model="preloadInfo.image" :key="renderImg"></v-img>
                          </v-card-title>
                          <v-card-subtitle>Введите:</v-card-subtitle>
                          <v-divider></v-divider>
                          <v-form>
                            <v-container>
                              <v-row>
                                <v-col>
                                  <v-text-field
                                      v-model="preloadInfo.name"
                                      label="Название товара"
                                      :rules="textRules"
                                      @input="reRenderName"
                                      required
                                  ></v-text-field>
                                </v-col>
                                <v-col>
                                  <v-select
                                      :items="getCategories"
                                      v-model="preloadInfo.category"
                                      label="Категорию товара"
                                      required
                                  ></v-select>
                                </v-col>
                              </v-row>
                              <v-row>
                                <v-col>
                                  <v-text-field
                                      v-model.number="preloadInfo.price"
                                      type="number"
                                      label="Цена*"
                                      :rules="textRules"
                                      required
                                  ></v-text-field>
                                </v-col>
                                <v-col>
                                  <v-text-field
                                      v-model.number="preloadInfo.available"
                                      type="number"
                                      label="Кол-во доступное"
                                      :rules="textRules"
                                      required
                                  ></v-text-field>
                                </v-col>
                              </v-row>
                              <v-row>
                                <v-text-field
                                    label="Описание товара"
                                    v-model="preloadInfo.description"
                                    :rules="textRules"
                                    style="margin: 0 2% 0 2%"
                                    required
                                ></v-text-field>
                              </v-row>
                              <v-row>
                                <v-file-input
                                    accept="image/png, image/jpeg, image/jpg"
                                    v-model="preloadInfo.image"
                                    label="Картинка товара"
                                    style="margin: 0 2% 0 2%"
                                    @change="uploadImg"
                                ></v-file-input>
                              </v-row>
                            </v-container>
                            <v-btn @click="submitEditForm" color="success" width="100%">ИЗМЕНИТЬ</v-btn>
                          </v-form>
                        </v-card>
                      </v-dialog>
                  </v-card>
                  <v-card flat v-else style="margin: 4%">
                    <v-icon style="text-align: center; display: block">
                      warning
                    </v-icon>
                    <v-card-title style="justify-content: center">Товаров не найдено</v-card-title>
                  </v-card>
                  <v-snackbar v-if="finishEdit" v-model="finishEdit" color="success" top timeout="4000">
                    Продукт {{products[preloadInfoId].name}} был изменен
                    <template>
                      <v-btn text dark @click="finishEdit=false">Закрыть</v-btn>
                    </template>
                  </v-snackbar>
                  <v-snackbar v-else-if="finishDel" v-model="finishDel" color="success" top timeout="4000">
                    Продукт был удален
                    <template>
                      <v-btn text dark @click="finishDel=false">Закрыть</v-btn>
                    </template>
                  </v-snackbar>
              </v-tab-item>
              <v-tab-item>
                <v-card flat>
                  <v-text-field v-model="searchUsers" @input="searchUser" label="Поиск" style="margin: 1% 15%">
                    <v-icon slot="append">search</v-icon>
                  </v-text-field>
                  <v-menu v-model="showFilters" max-width="180">
                    <template v-slot:activator="{on}">
                      <v-card-title style="margin-left: 2%">
                        Пользователи
                        <v-btn v-on="on" icon @click="showFilters = !showFilters">
                          <v-icon>filter_list</v-icon>
                        </v-btn>
                      </v-card-title>
                    </template>
                    <v-card>
                      <v-card-title>
                        Фильтры:
                        <v-radio-group style="margin-left: 5%">
                          <v-radio
                              label="Студенты"
                              @change="filterStudents"
                          ></v-radio>
                          <v-radio
                              label="Админы"
                              @change="filterAdmins"
                          ></v-radio>
                        </v-radio-group>
                      </v-card-title>
                    </v-card>
                  </v-menu>
                </v-card>
                <v-card flat id="grid" :style="'display: grid; grid-template-columns: repeat('+isMoreItems+', auto);grid-gap:' +isMoreItems+ 'px;margin: 0 0 2% 5%;'">
                  <v-card v-for="(user, i) in usersInfo" :key="i" width="220" v-model="usersInfo">
                    <v-img :src="user.avatar" width="220" height="220"></v-img>
                    <v-card-subtitle>{{user.fName}} {{user.sName}}, {{user.age}}</v-card-subtitle>
                    <v-card-text>
                      <b>E-mail:</b> <u>{{user.email}}</u> <br/>
                      <b>Группа:</b> <u>{{user.group}}</u> <br/>
                      <b>Телефон:</b> <u>{{user.phone}}</u> <br/>
                      <b>Админ:</b> <u>{{isAdmin[i]}}</u> <br/>
                    </v-card-text>
                  </v-card>
                </v-card>
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
                                              Добавить продукта
                                            </v-list-item-title>
<!--                                            <v-item-group>-->
<!--                                                <v-list-item-title>Введите:</v-list-item-title>-->
<!--                                            </v-item-group>-->
                                          </template>
                                          <v-list-item-group>
                                            <v-form style="margin: 0 3% 0 3%">
                                              <v-row>
                                                <v-col>
                                                    <v-text-field
                                                      label="Название товара*"
                                                      v-model="productName"
                                                      :rules="textRules"
                                                      required
                                                  ></v-text-field>
                                                </v-col>
                                                <v-col>
                                                    <v-text-field
                                                        label="Название категории*"
                                                        v-model="category"
                                                        :rules="textRules"
                                                        required
                                                    ></v-text-field>
                                                </v-col>
                                              </v-row>
                                              <v-row>
                                                <v-col>
                                                  <v-text-field
                                                      type="number"
                                                      label="Цена товара*"
                                                      v-model.number="price"
                                                      :rules="textRules"
                                                      required
                                                  ></v-text-field>
                                                </v-col>
                                                <v-col>
                                                  <v-text-field
                                                      type="number"
                                                      label="Доступное кол-во*"
                                                      v-model.number="total_count"
                                                      :rules="textRules"
                                                      required
                                                  ></v-text-field>
                                                </v-col>
                                              </v-row>
                                              <v-text-field
                                                  label="Описание товара*"
                                                  v-model="descrip"
                                                  :rules="textRules"
                                                  required
                                              ></v-text-field>
                                              <v-file-input
                                                  placeholder="Картинка товара"
                                                  accept="image/png, image/jpg, image/jpeg"
                                                  prepend-icon="mdi-camera"
                                              ></v-file-input>
                                              <v-btn
                                                  style="margin-bottom: 2%"
                                                  width="100%"
                                                  color="success"
                                                  @click="addProduct"
                                              >
                                                ВЫПОЛНИТЬ
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
                                                  <v-list-item-title style="justify-content: center">Добавить админа</v-list-item-title>
<!--                                                  <v-item-group>-->
<!--                                                      <v-list-item-title>Введите:</v-list-item-title>-->
<!--                                                  </v-item-group>-->
                                              </template>
                                              <v-list-item-group>
                                                  <v-form style="margin: 0 3% 0 3%">
                                                    <v-text-field
                                                        label="ФИО*"
                                                        v-model="fullname"
                                                        :rules="textRules"
                                                        required
                                                    ></v-text-field>
                                                    <v-row>
                                                      <v-col>
                                                        <v-text-field
                                                            label="Номер телефона*"
                                                            v-model="phone"
                                                            :rules="phoneRules"
                                                            required
                                                        ></v-text-field>
                                                      </v-col>
                                                      <v-col>
                                                        <v-text-field
                                                            label="E-mail*"
                                                            v-model="email"
                                                            :rules="emailRules"
                                                            required
                                                        ></v-text-field>
                                                      </v-col>
                                                    </v-row>
                                                    <v-text-field
                                                        label="Логин*"
                                                        v-model="login"
                                                        :rules="textRules"
                                                        required
                                                    ></v-text-field>
                                                    <v-text-field
                                                        type="password"
                                                        label="Пароль*"
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
                                                      ВЫПОЛНИТЬ
                                                    </v-btn>
                                                  </v-form>
                                              </v-list-item-group>
                                          </v-list-group>
                                      </v-list>
                                  </v-card>
                              </v-col>
                          </v-row>
                          <v-row style="justify-content: center; display: flex">
                              <v-col
                                  cols="6"
                              >
                                <v-card>
                                    <v-list>
                                        <v-list-group>
                                            <template v-slot:activator>
                                                <v-list-item-title>Добавить фильтр</v-list-item-title>
                                            </template>
                                            <v-list-item-group>
                                              <form style="margin: 0 3% 0 3%">
                                                <v-text-field
                                                    label="Название фильтра*"
                                                    :rules="textRules"
                                                    v-model="filterName"
                                                    required
                                                ></v-text-field>
                                                <v-select label="Выберите категорию" v-model="categoryName" solo :items="getCategories"></v-select>
                                                <v-btn
                                                    style="margin-bottom: 2%"
                                                    width="100%"
                                                    color="success"
                                                    @click="addFilter"
                                                >
                                                  ВЫПОЛНИТЬ
                                                </v-btn>
                                              </form>
                                            </v-list-item-group>
                                        </v-list-group>
                                    </v-list>
                                </v-card>
                              </v-col>
                          </v-row>
                      </v-container>
                      <v-snackbar color="success" v-model="finishAddProduct" timeout="4000" bottom>
                        Продукт был добавлен
                        <template>
                          <v-btn text @click="finishAddProduct = false">Закрыть</v-btn>
                        </template>
                      </v-snackbar>
                      <v-snackbar color="success" v-model="finishAddAdmin" timeout="4000" bottom>
                        Админ был добавлен
                        <template>
                          <v-btn text @click="finishAddAdmin = false">Закрыть</v-btn>
                        </template>
                      </v-snackbar>
                      <v-snackbar color="success" v-model="finishAddFilter" timeout="4000" bottom>
                        Фильтр был добавлен
                        <template>
                          <v-btn text @click="finishAddFilter = false">Закрыть</v-btn>
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
                              {{getSells[i]}} продаж
                            </v-item-group>
                          </template>
                          <v-card>
                            <container>
                              <v-row>
                                <v-col>
                                  <v-card-subtitle>
                                    <b>Описание: </b> {{item.description}}
                                  </v-card-subtitle>
                                </v-col>
                                <v-col>
                                  <v-card-subtitle>
                                    <b>Последняя покупка:</b> {{getLastBuy}}
                                  </v-card-subtitle>
                                </v-col>
                                <v-col>
                                  <v-card-subtitle>
                                    <b>Осталось в наличии:</b> {{item.available_count}}
                                  </v-card-subtitle>
                                </v-col>
                                <v-col>
                                  <v-card-subtitle>
                                    <b>Было изначально:</b> {{item.count}}
                                  </v-card-subtitle>
                                </v-col>
                              </v-row>
                            </container>
                          </v-card>
                        </v-list-group>
                      </v-list>
                    </div>
                </v-card>
                <v-card flat v-else style="margin: 4%">
                  <v-icon style="text-align: center; display: block">
                    warning
                  </v-icon>
                  <v-card-title style="justify-content: center">Товаров не найдено</v-card-title>
                </v-card>
              </v-tab-item>
          </v-tabs>
      </v-card>
  </v-app>
</template>

<script>
export default {
  name: "AdminCabinet",
  data() {
    return {
      tabs: false,
      formOff: false,
      editDialog: false,
      finishEdit: false,
      finishDel: false,
      clr: "",
      testData: null,
      showFilters: false,
      preloadInfoId: 0,
      preloadInfo: {},
      renderName: 0,
      renderImg: 0,
      reloader: true,
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
      finishAddFilter: false,
      searchUsers: "",
      usersInfo: [
        {
          id: 0,
          fName: 'Tim',
          sName: 'Livr',
          age: 20,
          group: '',
          email: 'tes@mai.xom',
          phone: '0214512314',
          avatar: require('@/assets/imgs/572f9a16875ed15491f1e81a.png'),
          isAdmin: true
        },
        {
          id: 1,
          fName: 'Lim',
          sName: 'Tivr',
          age: 22,
          group: 'PZPI-18-4',
          email: 'tes@mai.xom',
          phone: '0214512314',
          avatar: require('@/assets/imgs/572f9a16875ed15491f1e81a.png'),
          isAdmin: false
        }
      ],
      products: [
        {
          id: 0,
          name: 'Пирожок',
          category: 'Духовный',
          description: 'вкусный',
          price: '24',
          count: 30,
          available_count: 20,
          image: require('@/assets/imgs/572f9a16875ed15491f1e81a.png')
        },
        {
          id: 1,
          name: 'Пирожок',
          category: 'Духовный',
          description: 'вкусный',
          price: '24',
          count: 40,
          available_count: 25,
          image: require('@/assets/imgs/572f9a16875ed15491f1e81a.png')
        }
      ],
      textRules: [
        v => !!v || 'Это поле обязательно',
        v => v.length !== 0 || "Поле не может быть пусто"
      ],
      phoneRules: [
          v => v.length !== 0 || "Поле не может быть пусто",
          v => v.match("^[0-9]+$") !== null || "Введите верный телефон"
      ],
      emailRules: [
          v => v.length !== 0 || "Поле не может быть пусто",
          v => (v.match("[a-zA-Z]+@[a-zA-Z]+[.][a-zA-Z]+")!==null?v.match("[a-zA-Z]+@[a-zA-Z]+[.][a-zA-Z]+")[0]:null) !== null || "Введите верный e-mail"
      ]
    }
  },
  methods: {
    filterStudents() {
      /** {
          id: 0,
          fName: 'Tim',
          sName: 'Livr',
          age: 20,
          group: '',
          email: 'tes@mai.xom',
          phone: '0214512314',
          avatar: require('@/assets/imgs/572f9a16875ed15491f1e81a.png'),
          isAdmin: true
        }
      **/
      this.usersInfo = this.usersInfo.filter(i => !i.isAdmin)
    },
    showEditForm(ev) {
      this.preloadInfoId = this.getProductId(ev)
      for (let i=0;i<this.products.length;i++) {
        let item = this.products[i];
        if (item.id === this.preloadInfoId) {
          this.preloadInfo['id'] = item.id
          this.preloadInfo['name'] = item.name
          this.preloadInfo['price'] = item.price
          this.preloadInfo['available'] = item.available_count
          this.preloadInfo['category'] = item.category
          this.preloadInfo['description'] = item.description
          this.preloadInfo['image'] = item.image
        }
      }
      this.editDialog = true;
    },
    submitEditForm() {
      // sending POST with update
      this.products[this.preloadInfoId].id = this.preloadInfo.id
      this.products[this.preloadInfoId].name = this.preloadInfo.name
      this.products[this.preloadInfoId].price = this.preloadInfo.price
      this.products[this.preloadInfoId].available_count = this.preloadInfo.available
      this.products[this.preloadInfoId].category = this.preloadInfo.category
      this.products[this.preloadInfoId].description = this.preloadInfo.description
      this.products[this.preloadInfoId].image = this.preloadInfo.image
      this.reloader = false;
      this.editDialog = false;
      this.$nextTick(() => {
        this.reloader = true;
      })
      this.finishEdit = true;
    },
    getProductId(ev){
      let prod_id = ev.path[3].attributes['data-id']
      return parseInt(prod_id.value)
    },
    filterAdmins() {
      this.usersInfo = this.usersInfo.filter(i => i.isAdmin === true)
    },
    reRenderName() {
      this.renderName++;
    },
    delProduct(ev) {
      // sending DELETE
      this.preloadInfoId = this.getProductId(ev)
      this.products = this.products.filter(i => i.id !== this.preloadInfoId)
      this.finishDel = true;
    },
    uploadImg(ev) {
      if (ev.name !== undefined) {
        let reader = new FileReader();
        console.log(this.preloadInfo.image)
        reader.onload = (e) => {
          console.log(e)
          this.preloadInfo.image = e.target.result;
        }
        console.log(this.preloadInfo.image)
        reader.readAsDataURL(ev)
        this.renderImg++;
      }
    },
    addProduct() {
      //sending POST
      this.finishAddProduct = true;
    },
    searchUser() {
      this.usersInfo = []
      //sending GET
    },
    addFilter() {
      //sending POST
      this.finishAddFilter = true;
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
      if (localStorage['color'] !== undefined) {
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
        return 0;
      }
    },
    getCategories() {
        let res = [];
        for (let i=0;i<this.products.length;i++) {
            let item = this.products[i];
            res.push(item.category);
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