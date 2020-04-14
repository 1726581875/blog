<template>
  <div class="search clearfix">
    <Header></Header>
    <Main></Main>
    <Footer></Footer>
  </div>
</template>

<script>
import Header from '../components/index/header'
import Main from '../components/search/main'
import Footer from '../components/index/footer'
export default {
  data () {
    return {
      keyvalue: ''
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
