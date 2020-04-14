<template>
  <div class="main clearfix">
    <Left></Left>
    <Right></Right>
  </div>
</template>

<script>
import Right from '../article/main/right'
import Left from '../article/main/left'
export default {
  data () {
    return {
    }
  },
  components: {
    Right,
    Left
  },
  methods: {

  }
}
</script>

<style scoped lang="less">
.main {
  width: 1200px;
  margin: 0 auto;
  margin-top: 68px;
}
</style>
