import Vue from 'vue'
import App from './App.vue'
import router from './router/router'
import './plugins/element.js'
import './assets/css/global.css'
import './assets/css/iconfont.css'
import './assets/css/github-markdown.min.css'
import axios from 'axios'
import Vuex from 'vuex'
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import showdown from 'showdown'

// 配置showdown
Vue.prototype.converter = new showdown.Converter()

// 配置编辑器mavonEditor
Vue.use(mavonEditor)

Vue.config.productionTip = false

// 配置axios
Vue.prototype.axios = axios
axios.defaults.withCredentials = true
axios.defaults.baseURL = 'http://47.105.35.184:8080/blog'
// axios.defaults.baseURL = 'http://localhost:8080/blog'
Vue.use(Vuex)

var store = new Vuex.Store({
  state: {
    avatar: JSON.parse(window.sessionStorage.getItem('user')) === null ? '' : JSON.parse(window.sessionStorage.getItem('user')).userImage,
    pageSize: 10,
    url: 'http://47.105.35.184:8080/blog'
    // url: 'http://localhost:8080/blog'
  }
})

// 时间过滤器
Vue.filter('format', (val) => {
  var date = new Date(Date.parse(val))
  var year = date.getFullYear()
  var month = date.getMonth() + 1
  var day = date.getDate()
  var hour = date.getHours()
  var minutes = date.getMinutes()
  var seconde = date.getSeconds()
  return `${year}年${month}月${day}日 ${hour}:${minutes}:${seconde}`
})

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
