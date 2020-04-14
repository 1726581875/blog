<template>
  <!-- 个人资料主体 -->
  <div class="main clearfix">
    <div class="menu">
      <ul>
        <li>
          <router-link to="/master/message">个人资料</router-link>
        </li>
        <li>
          <router-link to="/master/edit">修改资料</router-link>
        </li>
        <li>
          <router-link to="/master/editBlog">我的博客</router-link>
        </li>
        <li>
          <router-link to="/master/collection">我的收藏</router-link>
        </li>
        <li>
          <router-link to="/master/draft">我的草稿</router-link>
        </li>
        <li>
          <router-link to="/master/editPassword">修改密码</router-link>
        </li>
        <li>
          <router-link to="/master/phoneEditPassword">手机改密</router-link>
        </li>
        <li>
          <router-link to="/master/recycle">回收站</router-link>
        </li>
        <li>
          <a href="#" @click.prevent="logout">退出</a>
        </li>
      </ul>
    </div>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  data () {
    return {

    }
  },
  components: {

  },
  methods: {
    // 退出登陆
    logout () {
      this.axios.get('/loginout')
        .then(res => {
          // 退出成功
          if (res.data.success) {
            // 清空session
            window.sessionStorage.setItem('login', false)
            window.sessionStorage.setItem('userId', null)
            window.sessionStorage.setItem('user', null)
            this.$message({
              message: '退出成功',
              type: 'success'
            })
            // 跳转回登陆页
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

<style scoped lang="less">
a {
  text-decoration: none;
}
li {
  list-style: none;
}
.main {
  width: 1200px;
  margin: 0 auto;
  margin-top: 62px;
  .menu {
    float: left;
    width: 230px;
    background-color: #fff;
    box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.05);
    ul {
      li {
        a {
          display: inline-block;
          width: 100%;
          padding: 15px 0 15px 0;
          text-indent: 1em;
          color: black;
        }
        .router-link-active {
          color: white;
          background-color: #409eff;
        }
      }
    }
  }
}
</style>
