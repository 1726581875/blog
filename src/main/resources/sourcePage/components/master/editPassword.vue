<template>
  <div class="editPassword">
    <h3 class="edit-title">修改密码</h3>
    <form action>
      <p>
        <label for="oldPassword">旧密码:</label>
        <el-input
          show-password
          placeholder="请输入旧密码"
          id="oldPassword"
          v-model="oldPassword"
          class="input"
        ></el-input>
      </p>
      <p>
        <label for="newPassword">新密码:</label>
        <el-input
          show-password
          placeholder="请输入8-16位新密码"
          id="newPassword"
          v-model="newPassword"
          class="input"
          @blur="checkPassword"
        ></el-input>
      </p>
      <p>
        <label for="confirm">确认密码:</label>
        <el-input
          show-password
          placeholder="请确认新密码"
          id="confirm"
          v-model="confirm"
          class="input"
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
      oldPassword: '',
      newPassword: '',
      confirm: '',
      errorText: '',
      successText: ''
    }
  },
  components: {

  },
  methods: {
    reset () {
      this.oldPassword = ''
      this.newPassword = ''
      this.confirm = ''
      this.errorText = ''
      this.successText = ''
    },
    checkPassword () {
      if (this.confirm !== this.newPassword) {
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
    // 提交修改
    save () {
      if (this.newPassword.trim().length < 8 || this.newPassword.trim().length > 16) {
        this.$message.error('请输入8-16位的新密码')
      } else if (this.oldPassword.trim() === '' || this.newPassword.trim() === '' || this.confirm.trim() === '') {
        this.$message.error('请完善表单内容')
      } else {
        this.$confirm('确认修改密码？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            var formData = new FormData()
            formData.append('userId', window.sessionStorage.getItem('userId'))
            formData.append('oldPassword', this.oldPassword.trim())
            formData.append('newPassword', this.confirm.trim())
            this.axios.post('/user/update/oldPassword', formData)
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
.editPassword {
  float: right;
  width: 860px;
  padding: 10px 30px 10px 30px;
  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.05);
  background-color: #fff;
  .edit-title {
    padding-top: 20px;
    padding-bottom: 30px;
    border-bottom: 1px solid #e0e0e0;
    color: #409eff;
  }
  form {
    p {
      margin-top: 15px;
      label {
        width: 85px;
        height: 40px;
        line-height: 40px;
        float: left;
      }
      .input {
        width: 200px;
      }
    }
    .btns {
      margin-left: 70px;
      margin-bottom: 60px;
      .btn {
        margin-right: 20px;
      }
    }
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
  }
}
</style>
