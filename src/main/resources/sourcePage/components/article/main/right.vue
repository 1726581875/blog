<template>
  <div class="right">
    <div class="list">
      <h3 class="list-title">热门推荐</h3>
      <ul class="list-items">
        <li v-for="item in hotList" :key="item.articleId">
          <router-link :to="'/article/'+item.articleId">
            <h3 class="item-titile" v-text="item.articleTitle"></h3>
            <p>
              <span class="item-time">时间:{{item.createTime | formatData()}}</span>
              <span class="item-author">作者:{{item.userName}}</span>
            </p>
          </router-link>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      hotList: [] // 热门推荐
    }
  },
  components: {

  },
  methods: {

  },
  created () {
    this.axios.get('/article/rank')
      .then(res => {
        if (res.data.success) {
          this.hotList = res.data.data.content
        }
      })
      .catch(err => {
        console.log(err)
      })
  },
  filters: {
    formatData (date) {
      date = new Date(Date.parse(date))
      const month = date.getMonth() - 1
      const day = date.getDate()
      return `${month}-${day}`
    }
  }
}
</script>

<style scoped lang="less">
a {
  text-decoration: none;
}
li {
  list-style: none;
}
.list {
  float: right;
  width: 300px;
  padding: 20px;
  background-color: #fff;
  h3 {
    line-height: 26px;
    font-weight: 400px;
    font-size: 17px;
  }
  .list-title {
    padding-left: 8px;
    border-left: 4px solid #419eff;
    color: #419eff;
  }
  .list-items {
    margin-top: 20px;
    li a {
      display: inline-block;
      width: 100%;
      border-bottom: 1px solid #f5f5f5;
      padding-top: 10px;
      padding-bottom: 8px;
      .item-titile {
        color: #5c5c5c;
      }
      .item-titile:hover {
        color: #419eff;
      }
      p {
        span {
          text-align: left;
          padding-left: 30px;
          padding-right: 10px;
          color: #a69999;
          font-size: 13px;
        }
        .item-time {
          margin-right: 10px;
        }
      }
    }
  }
}
</style>
