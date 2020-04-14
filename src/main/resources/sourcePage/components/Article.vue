<template>
  <div class="article clearfix">
    <Header></Header>
    <Main></Main>
    <Footer></Footer>
  </div>
</template>

<script>
import Header from '../components/index/header'
import Main from '../components/article/main'
import Footer from '../components/index/footer'
export default {
  data () {
    return {

    }
  },
  components: {
    Header,
    Main,
    Footer
  },
  methods: {

  }
}
</script>

<style scoped>
</style>
