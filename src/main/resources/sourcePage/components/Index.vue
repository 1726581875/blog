<template>
  <div class="container clearfix">
    <Header></Header>
    <Main></Main>
    <Footer></Footer>
  </div>
</template>

<script>
import Main from './index/main.vue'
import Header from './index/header.vue'
import Footer from './index/footer'
export default {
  data () {
    return {

    }
  },
  components: {
    Main,
    Header,
    Footer
  },
  methods: {

  }
}
</script>

<style scoped>
.container {
  height: 100%;
  background-color: #f5f6f7;
}
</style>
