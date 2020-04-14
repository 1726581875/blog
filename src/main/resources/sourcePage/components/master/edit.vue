<template>
  <!-- 用户修改资料表单 -->
  <div class="edit" v-loading="loading">
    <h3 class="edit-title">修改资料</h3>
    <form>
      <div class="edit-img">
        <label>头像:</label>
        <input
          type="file"
          @change="preview($event)"
          ref="file"
          accept="image/gif, image/jpeg, image/png"
          style="display:none"
        />
        <el-button type="primary" class="upload" @click="upload">上传头像</el-button>
        <span>头像预览:</span>
        <img alt ref="avatar" :src="$store.state.url+user.userImage" />
      </div>
      <p>
        <label for="uname">昵称:</label>
        <el-input placeholder="请输入昵称" v-model="user.userName" id="uname" class="input"></el-input>
      </p>
      <p>
        <label for="gender">性别:</label>
        <el-radio v-model="user.userSex" label="男">男</el-radio>
        <el-radio v-model="user.userSex" label="女">女</el-radio>
      </p>
      <p>
        <label for="age">年龄:</label>
        <el-select v-model="user.userAge" placeholder="请选择">
          <el-option v-for="item in 100" :key="item" :label="item" :value="item"></el-option>
        </el-select>
        <!-- <el-input placeholder="18" v-model="user.userAge" id="age" class="input age-input"></el-input> -->
      </p>
      <p>
        <label for="introduction">个人简介:</label>
        <el-input
          type="textarea"
          placeholder="请输入个人简介"
          v-model="user.userMotto"
          id="introduction"
          class="input introduction"
        ></el-input>
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
      user: {
        userAge: '',
        userId: '',
        userImage: '',
        userMotto: '',
        userName: '',
        userSex: ''
      },
      avatar: null,
      loading: false
    }
  },
  components: {

  },
  methods: {
    reset () {
      // 将输入框的改成没修改前的
      this.getUserMsg()
    },
    // 预览头像
    preview (event) {
      var imgFile = event.target.files[0]
      var fr = new FileReader()
      var that = this
      fr.onload = function () {
        that.$refs.avatar.src = fr.result
      }
      fr.readAsDataURL(imgFile)
      this.avatar = imgFile
    },
    upload () {
      this.$refs.file.click()
    },
    // 获取sessionStorage中用户的信息
    getUserMsg () {
      this.user = JSON.parse(window.sessionStorage.getItem('user'))
    },
    // 保存信息
    save () {
      if (this.user.userName.trim() === '') {
        this.$message.error('用户名不能为空！')
      } else {
        this.$confirm('是否保存信息？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            this.loading = true
            // 封装发送数据
            var formData = new FormData()
            formData.append('fileName', this.avatar)
            formData.append('userId', parseInt(window.sessionStorage.getItem('userId')))
            formData.append('userName', this.user.userName.trim())
            formData.append('userSex', this.user.userSex)
            formData.append('userAge', parseInt(this.user.userAge))
            formData.append('userMotto', this.user.userMotto.trim() === '' ? '' : this.user.userMotto)
            this.axios.post('/user/update/detail', formData)
              .then(res => {
                console.log(res)
                if (res.data.success) {
                  this.$message({
                    message: '保存成功',
                    type: 'success'
                  })
                  // 跳转回我的资料
                  this.$router.push('/master/message')
                  this.loading = false
                } else {
                  this.$message.error('保存失败，请重新尝试')
                  this.loading = false
                }
              })
              .catch(err => {
                console.log(err)
                this.$message.error('未知错误，请重新尝试')
                this.loading = false
              })
          })
      }
    }
  },
  created () {
    // 获取用户信息
    this.getUserMsg()
  }
}
</script>

<style scoped lang="less">
.edit {
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
    padding: 10px 60px 80px 60px;
    margin-top: 20px;
    .edit-img {
      .upload {
        margin-right: 80px;
      }
      span {
        color: #999;
      }
      label {
        display: inline-block;
        width: 85px;
        color: black;
        font-size: 15px;
      }
      img {
        position: relative;
        top: 30px;
        left: 20px;
        width: 85px;
        height: 85px;
        border-radius: 50%;
      }
    }
    p {
      margin-top: 20px;
      color: #999;
      font-size: 15px;
      font-weight: 400;
      label {
        display: inline-block;
        width: 85px;
        text-align: left;
        color: black;
        font-size: 15px;
      }
      .input {
        width: 250px;
      }
      .age-input {
        width: 60px;
      }
      .introduction {
        vertical-align: middle;
      }
    }
    .btns {
      margin-left: 90px;
      .btn {
        margin-right: 20px;
      }
    }
  }
}
</style>
