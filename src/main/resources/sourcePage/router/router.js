import Vue from 'vue'
import VueRouter from 'vue-router'
// 登陆组件
import Login from '../components/Login.vue'
import minlogin from '../components/login/minLogin.vue'
import phonelogin from '../components/login/phonelogin.vue'
// 注册组件
import Register from '../components/Register.vue'
// 主页组件
import Index from '../components/Index.vue'
// 用户页面
import Master from '../components/Master.vue'
import Message from '../components/master/message.vue'
import Edit from '../components/master/edit.vue'
import EditPassword from '../components/master/editPassword.vue'
import EditBlog from '../components/master/editBlog.vue'
import Collection from '../components/master/collection.vue'
import PhoneEditPassword from '../components/master/phoneEditPassword.vue'
import Draft from '../components/master/draft.vue'
import Recycle from '../components/master/recycle.vue'
// 发表文章页面
import Publish from '../components/Publish.vue'
// 文章页面
import Article from '../components/Article'
// 搜索页面
import Search from '../components/Search'
// 关于页面
import About from '../components/About'

Vue.use(VueRouter)

const routes = [
  {
    // 重定向去密码登陆
    path: '/',
    redirect: '/minlogin'
  },
  {
    // 关于页面
    path: '/about',
    component: About
  },
  {
    // 登陆页面
    path: '/login',
    component: Login,
    children: [
      // 密码登陆
      { path: '/minlogin', component: minlogin },
      // 手机登陆
      { path: '/phonelogin', component: phonelogin }
    ]
  },
  {
    // 注册页面
    path: '/register',
    component: Register
  }, {
    // 首页
    path: '/index',
    component: Index
  },
  {
    // 用户页
    path: '/master',
    component: Master,
    children: [
      {
        // 用户信息
        path: '/master/message',
        component: Message
      },
      {
        // 修改资料
        path: '/master/edit',
        component: Edit
      },
      {
        // 修改密码
        path: '/master/editPassword',
        component: EditPassword
      },
      {
        // 管理博客
        path: '/master/editBlog',
        component: EditBlog
      },
      {
        // 我的收藏
        path: '/master/collection',
        component: Collection
      },
      {
        // 手机改密
        path: '/master/phoneEditPassword',
        component: PhoneEditPassword
      },
      {
        // 我的草稿
        path: '/master/draft',
        component: Draft
      },
      {
        path: '/master/recycle',
        component: Recycle
      }
    ]
  },
  {
    // 发表博客
    path: '/publish/:id',
    component: Publish
  },
  {
    path: '/article/:id',
    component: Article
  },
  {
    name: 'search',
    path: '/search/:key',
    component: Search
  }
]

const router = new VueRouter({
  routes
})

export default router
