<template>
  <div class="content" v-loading.fullscreen="loading">
    <p>
      <label for="id">用户名:&nbsp;&nbsp;&nbsp;&nbsp;</label>
      <el-input placeholder="请输入用户名" v-model="id" class="input" id="id"></el-input>
    </p>
    <p>
      <label for="password">密码:&nbsp;&nbsp;&nbsp;&nbsp;</label>
      <el-input
        placeholder="请输入密码"
        show-password
        v-model="password"
        class="input"
        id="password"
        @keydown.enter.native="login"
      ></el-input>
    </p>
    <p class="btns">
      <el-button type="primary" class="btn" @click="login">登陆</el-button>
      <el-button type="info" @click="reset" class="btn">重置</el-button>
    </p>
    <p class="link">
      没有账号?
      <router-link to="/register" class="create">点击注册</router-link>
    </p>
  </div>
</template>

<script>
export default {
  data () {
    return {
      id: '', // 用户名
      password: '', // 密码
      loading: false
    }
  },
  methods: {
    // 重置表单
    reset () {
      this.id = ''
      this.password = ''
    },
    // 登陆
    login () {
      // 校验表单内容
      if (this.id === '' || this.password === '') {
        this.$message.error('请完善表单内容')
        return
      }
      this.loading = true
      // 封装要传的参数
      var formData = new FormData()
      formData.append('username', this.id.trim())
      formData.append('password', this.password.trim())
      // 发起登陆请求
      this.axios.post('/login', formData)
        .then(res => {
          if (res.data.success) {
            console.log(res)
            this.$message({
              message: '登陆成功',
              type: 'success'
            })
            // 存储用户登陆状态
            window.sessionStorage.setItem('login', true)
            window.sessionStorage.setItem('userId', res.data.data)
            // 跳转到首页
            this.$router.push('/index')
            this.loading = false
          } else {
            this.$message.error(res.data.msg)
            this.loading = false
          }
        })
        .catch(err => {
          console.log(err)
          this.$message.error('未知错误')
          this.loading = false
        })
    }
  }
}
</script>

<style scoped lang="less">
.content {
  width: 500px;
  height: 450px;
  padding: 10px 60px 10px 100px;
  margin: 0 auto;
  margin-top: 30px;
  p {
    margin-top: 40px;
    label {
      display: inline-block;
      width: 65px;
      text-align: right;
      font-size: 19px;
      font-weight: 700;
      color: #666;
    }
  }
}
.input {
  width: 300px;
  margin-left: 15px;
}
.btns {
  text-align: center;
  margin-right: 36px;
  .btn {
    margin-right: 30px;
  }
}
.link {
  margin-right: 20px;
  text-align: right;
}
.create {
  margin-top: 30px;
  color: #409eff;
  text-decoration: none;
}
</style>
