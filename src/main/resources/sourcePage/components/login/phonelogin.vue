<template>
  <div class="content">
    <p class="checkbox">
      <label for="phone">手机号:</label>
      <el-input placeholder="请输入手机号" v-model="id" class="input"></el-input>
      <el-button type="primary" class="code" @click="getCode">获取验证码</el-button>
    </p>
    <p>
      <label for="code">验证码:</label>
      <el-input
        placeholder="请输入验证码"
        v-model="code"
        class="input"
        id="code"
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
    </p>C
  </div>
</template>

<script>
export default {
  data () {
    return {
      id: '', // 手机号
      code: '' // 验证码
    }
  },
  components: {

  },
  methods: {
    // 重置表单
    reset () {
      this.id = ''
      this.code = ''
    },
    // 获取验证码
    getCode () {
      this.axios.post('/phone/getotp?userPhone=' + this.id)
        .then(response => {
          var code = response.data.data.otpCode
          this.$alert(code, '手机验证码', {
            confirmButtonText: '确定'
          })
        })
        .catch(error => {
          console.log(error)
          this.$message.error('手机号输入格式错误！！！')
        })
    },
    // 登陆事件
    login () {
      if (this.id === '' || this.code === '') {
        this.$message.error('请完善表单内容')
        return
      }
      // 封装要传的数据
      var formData = new FormData()
      formData.append('userPhone', this.id.trim())
      formData.append('otp', this.code.trim())
      // 发送登陆请求
      this.axios.post('/login/phone', formData)
        .then(res => {
          if (res.data.status === 0) {
            this.$message({
              message: '登陆成功',
              type: 'success'
            })
            // 存储登陆状态
            window.sessionStorage.setItem('userId', res.data.data)
            window.sessionStorage.setItem('login', true)
            // 跳转页面
            this.$router.push('/index')
          } else {
            this.$message.error(res.data.msg)
          }
        })
        .catch(error => {
          console.log(error)
          this.$message.error('未知错误')
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
.checkbox .input {
  width: 167px;
  margin-right: 20px;
}
.create {
  margin-top: 30px;
  color: #409eff;
  text-decoration: none;
}
.link {
  text-align: right;
  margin-right: 20px;
}
</style>
