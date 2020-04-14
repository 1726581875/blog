<template>
  <div class="master clearfix">
    <Header></Header>
    <Main></Main>
  </div>
</template>

<script>
import Header from './index/header'
import Main from './master/main'
export default {
  data () {
    return {

    }
  },
  components: {
    Header,
    Main
  },
  methods: {

  },
  created () {
    // 处理用户登陆失效在用户界面跳回登陆页
    this.axios.get('/islogin')
      .then(res => {
        if (!res.data.success) {
          this.$router.push('/minlogin')
        }
      })
  }
}
</script>

<style scoped lang="less">
.master {
  background-color: #f5f6f7;
}
</style>
