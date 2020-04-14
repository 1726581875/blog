<template>
  <div>
    <div class="header clearfix">
      <div class="content">
        <div class="logo">
          <router-link to="/index"></router-link>
        </div>
        <div class="list">
          <ul>
            <li>
              <router-link to="/index">首页</router-link>
            </li>
            <!-- <li>
              <router-link :to="{name: 'search', params: {key: '前端'}}">前端</router-link>
            </li>
            <li>
              <router-link :to="{name: 'search', params: {key: '后端'}}">后端</router-link>
            </li>-->
            <li>
              <router-link to="/about">关于</router-link>
            </li>
          </ul>
        </div>
        <div class="search">
          <input type="text" placeholder="请输入搜索的内容" v-model="content" @keydown.enter="search" />
          <a href="#" class="search-logo" @click.prevent="search">
            <i class="iconfont icon-search"></i>
          </a>
        </div>
        <div class="avatar" v-if="login">
          <router-link to="/master/message">
            <img :src="$store.state.url+$store.state.avatar" alt />
          </router-link>
          <ul class="myItems">
            <li>
              <router-link to="/master/editBlog">
                <i class="iconfont icon-wode"></i>我的博客
              </router-link>
            </li>
            <li>
              <router-link to="/master/edit">
                <i class="iconfont icon-setup_icon"></i>账号设置
              </router-link>
            </li>
            <li>
              <a href="#" @click.prevent="logout">
                <i class="iconfont icon-tuichu"></i>退出
              </a>
            </li>
          </ul>
        </div>
        <div class="write" v-if="login">
          <span>
            <router-link to="/publish/-1">
              <i class="iconfont">&#xe7b9;</i>写博客
            </router-link>
          </span>
        </div>
        <div class="login" v-else>
          <router-link to="/minlogin">登陆</router-link>&nbsp;/
          <router-link to="/register">注册</router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      content: '',
      login: null
    }
  },
  components: {

  },
  created () {
    // 检测用户是否登陆
    this.axios.get('/islogin')
      .then(res => {
        if (res.data.success) {
          this.login = true
          // 获取用户信息
          this.axios.get('/user/detail?userId=' + res.data.data.userId)
            .then(res => {
              if (res.data.success) {
                window.sessionStorage.setItem('user', JSON.stringify(res.data.data))
                window.sessionStorage.setItem('login', true)
                window.sessionStorage.setItem('userId', res.data.data.userId)
                this.$store.state.avatar = res.data.data.userImage
              }
            })
        } else {
          this.$router.push('/minlogin')
        }
      })
      .catch(err => {
        this.$router.push('/minlogin')
        console.log(err)
      })
    if (window.sessionStorage.getItem('login') === 'true') {
      this.login = true
      return
    }
    this.login = false
  },
  methods: {
    // 搜索功能
    search () {
      if (this.content.trim() !== '') {
        this.$router.push({ name: 'search', params: { key: this.content.trim() } })
        this.content = ''
      } else {
        this.$alert('<h3 style="color: #f56c6c">请输入有效内容</h3>', '错误提示', {
          dangerouslyUseHTMLString: true,
          type: 'error',
          center: true,
          lockScroll: false
        })
      }
    },
    // 退出登陆
    logout () {
      this.axios.get('/loginout')
        .then(res => {
          if (res.data.success) {
            window.sessionStorage.setItem('login', false)
            window.sessionStorage.setItem('userId', null)
            window.sessionStorage.setItem('user', null)
            this.login = false
            this.$message({
              message: '退出成功',
              type: 'success'
            })
            this.$router.push('/minlogin')
          } else {
            this.$message.error('退出失败，请重新尝试')
          }
        })
        .catch(err => {
          console.log(err)
          this.$message.error('未知错误')
        })
    }
  }
}
</script>

<style scoped>
.clearfix:after,
.clearfix:before {
  content: '';
  display: table;
}
.clearfix:after {
  clear: both;
}
.clearfix {
  *zoom: 1;
}
input {
  outline: none;
}
li {
  list-style: none;
}
a {
  text-decoration: none;
}
.header {
  position: fixed;
  top: 0;
  left: 0;
  height: 52px;
  width: 100%;
  background-color: #fff;
  z-index: 99;
  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.05);
}
.content {
  width: 1200px;
  height: 52px;
  margin: 0 auto;
  background-color: #fff;
}
.logo a {
  display: block;
  float: left;
  width: 120px;
  height: 52px;
  border: 0;
  background: url('../../assets/image/logo.png') no-repeat;
  background-size: 120px 52px;
  background-position: center center;
}
.list {
  float: left;
  margin-left: 60px;
}
.list > ul > li {
  float: left;
  margin-left: 30px;
}
.list ul li a {
  display: block;
  height: 52px;
  line-height: 52px;
  padding: 0 30px;
  color: #409eff;
  font-size: 18px;
  font-weight: 600;
  transform: all 0.5s;
}
.list ul li:hover {
  background-color: #f0f1f2;
}
.list ul li:hover .more {
  display: block;
}
.list ul li .router-link-active {
  height: 49px;
  border-bottom: 3px solid #409eff;
}
.search {
  float: left;
  margin-left: 30px;
  height: 52px;
  line-height: 52px;
}
.search input {
  width: 210px;
  height: 30px;
  border: 0;
  border-radius: 6px;
  border-top-right-radius: 0;
  border-bottom-right-radius: 0;
  margin-left: 30px;
  padding-left: 8px;
  background-color: #f5f6f7;
  color: #c4c4c4;
}
.search-logo {
  display: inline-block;
  height: 30px;
  line-height: 30px;
  vertical-align: middle;
  border-top-right-radius: 6px;
  border-bottom-right-radius: 6px;
  background-color: #f5f6f7;
}
.search-logo i {
  color: #a5abb8;
  font-weight: 700;
  font-size: 20px;
  background-color: #f5f6f7;
}
.write {
  float: right;
  height: 52px;
  line-height: 52px;
}
.write i {
  font-size: 20px;
  color: #409eff;
  font-weight: 700;
}
.write a {
  font-size: 15px;
  color: #409eff;
}
.avatar {
  float: right;
  width: 28px;
  height: 52px;
  margin-left: 20px;
  line-height: 52px;
}
.avatar a img {
  display: inline-block;
  vertical-align: middle;
  line-height: 52px;
  width: 28px;
  height: 28px;
  border-radius: 50%;
}
.avatar:hover .myItems {
  display: block;
}
.myItems {
  display: none;
  position: relative;
  width: 126px;
  left: -30px;
  background-color: #fff;
}
.myItems li a {
  display: inline-block;
  padding: 10px 18px 10px 18px;
  width: 90px;
  color: #409eff;
}
.myItems li a:hover {
  background-color: #f0f1f2;
}
.myItems li a i {
  padding-right: 6px;
}
.login {
  float: right;
  height: 52px;
  line-height: 52px;
}
.login a {
  color: #409eff;
  font-size: 18px;
}
</style>
