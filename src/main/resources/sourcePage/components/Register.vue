<template>
  <div class="container">
    <form action>
      <h1 class="title">用户注册</h1>
      <p>
        <label for="uname">用户名:</label>
        <el-input
          placeholder="请输入用户名"
          id="uname"
          v-model="uname"
          class="input"
          required
          @blur="checkname"
        ></el-input>
        <span v-text="nameMsg" style="color: red"></span>
      </p>
      <p>
        <label for="password">密码:</label>
        <el-input
          placeholder="请输入密码"
          show-password
          v-model="password"
          class="input"
          id="password"
          @blur="checkPass"
        ></el-input>
        <span v-text="passwordMsg" style="color: red"></span>
      </p>
      <p>
        <label for="confirm">确认密码:</label>
        <el-input
          placeholder="请输入8-16位密码"
          show-password
          v-model="confirm"
          class="input"
          id="confirm"
          @blur="checkPassword"
        ></el-input>
        <span v-if="errorText === '两次输入密码不一致'" class="error">
          <i class="iconfont icon-shibai"></i>
          {{errorText}}
        </span>
        <span v-if="successText === 'ok'" class="success">
          <i class="iconfont icon-chenggong"></i>
          {{successText}}
        </span>
      </p>
      <p class="checking">
        <label for="phone">手机号:</label>
        <el-input placeholder="请输入手机号" v-model="phone" id="phone" class="input"></el-input>
        <el-button type="primary" class="getcode" @click="getCode">获取验证码</el-button>
      </p>
      <p>
        <label for="code">验证码:</label>
        <el-input placeholder="请输入验证码" v-model="code" class="input" id="code"></el-input>
      </p>
      <p class="btns">
        <el-button type="primary" class="btn" @click="register">注册</el-button>
        <el-button type="info" @click="reset" class="btn">重置</el-button>
      </p>
      <p class="link">
        已有账号?
        <router-link to="/minlogin" class="create">点击登陆</router-link>
      </p>
    </form>
  </div>
</template>

<script>
// import $ from 'jquery'
export default {
  data () {
    return {
      uname: '', // 用户名
      password: '', // 密码
      confirm: '', // 确认的密码
      phone: '', // 手机号
      code: '', // 验证码
      realCode: '', // 存储获取到的验证码
      errorText: '', // 验证错误提示
      successText: '', // 验证正确提示
      nameMsg: '',
      passwordMsg: ''
    }
  },
  components: {

  },
  methods: {
    // 检测用户名是否合法
    checkname () {
      if (this.uname.trim().length < 2) {
        this.nameMsg = '用户名过短'
      } else if (this.uname.trim.length > 16) {
        this.nameMsg = '用户名过长'
      } else {
        this.nameMsg = ''
      }
    },
    // 检测密码是否合法
    checkPass () {
      if (this.password.trim().length < 6) {
        this.passwordMsg = '密码不能小于6个字符'
      } else if (this.password.trim().length > 16) {
        this.passwordMsg = '密码不能大于16个字符'
      } else {
        this.passwordMsg = ''
      }
    },
    // 重置表单
    reset () {
      this.uname = ''
      this.password = ''
      this.confirm = ''
      this.phone = ''
      this.code = ''
    },
    // 确认密码
    checkPassword () {
      if (this.confirm !== this.password) {
        this.errorText = '两次输入密码不一致'
        this.successText = ''
      } else {
        this.successText = 'ok'
        this.errorText = ''
      }
      if (this.confirm === '') {
        this.errorText = ''
        this.successText = ''
      }
    },
    // 获取验证码
    getCode () {
      this.axios.post('/phone/getotp?userPhone=' + this.phone)
        .then(response => {
          var code = response.data.data.otpCode
          this.realCode = code
          this.$alert(code, '手机验证码', {
            confirmButtonText: '确定'
          })
        })
        .catch(error => {
          console.log(error)
          this.$message.error('手机号输入格式错误！！！')
        })
    },
    // 注册事件
    register () {
      // 校验表单内容是否合法
      if (this.uname.trim().length === 0) {
        this.nameMsg = '请填写用户名'
        return
      }
      if (this.password.trim().length === 0) {
        this.passwordMsg = '请填写密码'
        return
      }
      if (this.confirm.trim() !== this.password.trim() || this.phone.trim().length === 0 || this.code.trim().length === 0) {
        this.$message.error('请完成注册表单内容！！！')
        return
      }
      // 发送注册请求
      var formData = new URLSearchParams()
      formData.append('userPhone', this.phone.trim())
      formData.append('password', this.password.trim())
      formData.append('username', this.uname.trim())
      formData.append('otp', this.code.trim())
      this.axios.post('/register', formData, { headers: { 'Content-Type': 'application/x-www-form-urlencoded' } }, { withCredentials: true })
        .then(res => {
          if (res.data.status === 0) {
            this.$message({
              message: '注册成功！！！',
              type: 'success'
            })
            this.$router.push('/minlogin')
          } else {
            this.$message.error(res.data.msg)
          }
        })
        .catch(err => {
          this.$message.error(err)
        })
    }
  }
}
</script>

<style scoped lang="less">
.container {
  position: relative;
  height: 100%;
  background: url('../assets/image/background.jpg') no-repeat;
  background-position: center center;
}
form {
  position: absolute;
  top: 50%;
  left: 50%;
  width: 468px;
  height: 544px;
  padding: 0 30px;
  margin-top: -272px;
  margin-left: -234px;
  border-radius: 10px;
  background-color: #fff;
  p {
    margin-top: 30px;
    span {
      font-size: 13px;
      padding-left: 8px;
    }
    .success {
      color: #88d260;
    }
    .error {
      color: #e84335;
    }
    label {
      display: inline-block;
      width: 85px;
      text-align: right;
      margin-right: 15px;
      font-size: 19px;
      font-weight: 700;
    }
  }
}
.title {
  height: 60px;
  padding: 0 20px;
  line-height: 70px;
  color: #409eff;
  text-align: center;
}
.checking .input {
  width: 200px;
  margin-right: 20px;
}
.input {
  width: 200px;
}
.btns {
  text-align: center;
  margin-left: 15px;
  .btn {
    margin-right: 30px;
  }
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
