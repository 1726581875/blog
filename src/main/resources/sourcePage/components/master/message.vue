<template>
  <div class="message">
    <h3 class="info-title">个人资料</h3>
    <div class="info">
      <p class="info-img">
        <img :src="$store.state.url+user.userImage" alt />
      </p>
      <p class="info-item">
        昵称：
        <span v-text="user.userName"></span>
      </p>
      <p class="info-item">
        性别：
        <span v-text="user.userSex"></span>
      </p>
      <p class="info-item">
        年龄：
        <span v-text="user.userAge"></span>
      </p>
      <p class="info-item">
        格言：
        <span v-text="user.userMotto"></span>
        <span v-if="user.userMotto.length === 0">无</span>
      </p>
      <p class="info-handle">
        <router-link to="/master/edit">修改资料</router-link>
      </p>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      user: {
        userAge: '',
        userId: '',
        userImage: '',
        userMotto: '',
        userName: '',
        userSex: ''
      }
    }
  },
  components: {

  },
  methods: {

  },
  created () {
    // 获取用户信息
    this.axios.get('/user/detail?userId=' + window.sessionStorage.getItem('userId'))
      .then(res => {
        if (res.data.success) {
          window.sessionStorage.setItem('user', JSON.stringify(res.data.data))
          this.user = JSON.parse(window.sessionStorage.getItem('user'))
          // 将用户头像放到vuex中
          this.$store.state.avatar = this.user.userImage
        }
      })
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
.message {
  float: right;
  width: 860px;
  padding: 10px 30px 10px 30px;
  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.05);
  background-color: #fff;
  .info-title {
    padding-top: 20px;
    padding-bottom: 30px;
    border-bottom: 1px solid #e0e0e0;
    color: #409eff;
  }
  .info {
    .info-img {
      text-align: center;
      img {
        width: 85px;
        border-radius: 50%;
        padding: 10px 0 10px 0;
      }
    }
    .info-item {
      padding: 10px 60px 10px 60px;
      color: #4d4d4d;
      font-size: 15px;
    }
    .info-handle {
      padding: 0 30px 10px 0;
      text-align: right;
      a {
        font-size: 14px;
        color: #409eff;
      }
    }
  }
}
</style>
