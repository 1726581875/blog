<template>
  <div class="main">
    <div class="container">
      <router-link to="/index" class="logo"></router-link>
      <form action class="form">
        <router-link to="/minlogin" class="login">密码登陆</router-link>
        <router-link to="/phonelogin" class="phonelogin">手机登陆</router-link>
        <router-view></router-view>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
    }
  }
}
</script>

<style scoped lang="less">
.main {
  position: relative;
  height: 100%;
  background: url('../assets/image/background.jpg') no-repeat;
  background-position: center center;
}
.logo {
  position: relative;
  top: 15px;
  display: block;
  width: 200px;
  height: 200px;
  margin: 0 auto;
  background: url('..//assets/image/javue.png') no-repeat;
  background-size: 200px 200px;
}
.form {
  position: absolute;
  top: 50%;
  left: 50%;
  width: 600px;
  height: 410px;
  border-radius: 10px;
  margin-top: -225px;
  margin-left: -300px;
  background: rgba(255, 255, 255, 1);
}
.login,
.phonelogin {
  display: inline-block;
  height: 60px;
  width: 300px;
  text-align: center;
  line-height: 60px;
  font-size: 25px;
  text-decoration: none;
  color: white;
}
.login {
  border-top-left-radius: 10px;
  background-color: #409eff;
}
.router-link-active {
  color: #409eff !important;
  background-color: white !important;
}
.phonelogin {
  border-top-right-radius: 10px;
  background-color: #409eff;
}
</style>
