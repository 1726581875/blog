<template>
  <div class="phoneEditPassword">
    <h3 class="editTitle">手机改密</h3>
    <form>
      <p>
        <label for="phone">手机号:</label>
        <el-input placeholder="请输入手机号" id="phone" v-model="phone" class="phone input"></el-input>
        <el-button type="primary" class="btn" @click="getCode">获取验证码</el-button>
      </p>
      <p>
        <label for="code">验证码:</label>
        <el-input placeholder="请输入手机号" id="code" v-model="code" class="input"></el-input>
      </p>
      <p>
        <label for="newPassword">新密码:</label>
        <el-input
          show-password
          placeholder="请输入新密码"
          id="newPassword"
          v-model="newPassword"
          class="input"
          @blur="checkPassword"
        ></el-input>
        <span v-text="passwordMsg" style="color: red"></span>
      </p>
      <p class="btns">
        <el-button type="primary" class="btn" @click="save">保存</el-button>
        <el-button type="info" @click="reset" class="btn">重置</el-button>
      </p>
    </form>
  </div>
</template>

<script>
export default {
  data () {
    return {
      phone: '',
      code: '',
      newPassword: '',
      passwordMsg: ''
    }
  },
  components: {

  },
  methods: {
    reset () {
      this.phone = ''
      this.code = ''
      this.newPassword = ''
    },
    checkPassword () {
      if (this.newPassword.trim().length < 6) {
        this.passwordMsg = '密码不能小于6个字符'
      } else if (this.newPassword.trim().length > 16) {
        this.passwordMsg = '密码不能大于16个字符'
      } else {
        this.passwordMsg = ''
      }
    },
    // 获取验证码
    getCode () {
      this.axios.post('/phone/getotp?userPhone=' + this.phone)
        .then(res => {
          if (res.data.success) {
            this.$alert(res.data.data.otpCode, '手机验证码', {
              confirmButtonText: '确定'
            })
          } else {
            this.$message.error('获取验证码失败，请重新尝试！')
          }
        })
        .catch(error => {
          console.log(error)
          this.$message.error('手机号输入格式错误！！！')
        })
    },
    // 保存修改
    save () {
      if (this.phone.trim() === '' || this.code.trim() === '' || this.newPassword.trim() === '' || this.passwordMsg.trim() !== '') {
        this.$message.error('请完善表单！')
      } else {
        this.$confirm('确认修改密码？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            var formData = new FormData()
            formData.append('userPhone', this.phone.trim())
            formData.append('otp', this.code.trim())
            formData.append('password', this.newPassword.trim())
            this.axios.post('/user/update/password', formData)
              .then(res => {
                console.log(res)
                if (res.data.success) {
                  this.$message({
                    message: '修改成功',
                    type: 'success'
                  })
                  this.reset()
                } else {
                  this.$message.error('修改失败')
                }
              })
              .catch(err => {
                console.log(err)
                this.$message.error('保存失败，未知原因！！')
              })
          })
          .catch(err => {
            console.log(err)
            this.$message({
              type: 'info',
              message: '已取消保存'
            })
          })
      }
    }
  }
}
</script>

<style scoped lang="less">
.phoneEditPassword {
  float: right;
  width: 860px;
  padding: 10px 30px 10px 30px;
  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.05);
  background-color: #fff;
  .editTitle {
    padding-top: 20px;
    padding-bottom: 30px;
    border-bottom: 1px solid #e0e0e0;
    color: #409eff;
  }
  form {
    p {
      margin-top: 20px;
      label {
        padding: 0 20px 0 0;
      }
      .input {
        width: 300px;
        margin-right: 10px;
      }
      .phone {
        width: 180px;
        margin-right: 10px;
      }
    }
    .btns {
      margin-left: 70px;
      margin-bottom: 60px;
      .btn {
        margin-right: 20px;
      }
    }
  }
}
</style>
