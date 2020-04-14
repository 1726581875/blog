<template>
  <div class="main clearfix">
    <div class="content">
      <h3 class="class-title">
        搜索关键字:
        <span>{{$route.params.key}}</span>
      </h3>
      <ul class="content-list">
        <li v-for="(item, index) in contentList" :key="item.articleId">
          <h3 class="content-titile">
            <router-link :to="'/article/'+item.articleId" v-text="item.articleTitle"></router-link>
          </h3>
          <div class="content-author">
            <a href="#">
              <img :src="item.userImage" alt />
              <span>&nbsp;&nbsp;{{item.userName}}</span>
            </a>
          </div>
          <div class="handle">
            <span>
              <a href="#" @click.prevent="parise(item.articleId, index)">
                <i class="iconfont icon-dianzan" :class="{pariseActive: item.star}"></i>
                &nbsp;{{item.articleStar}}
              </a>
            </span>
            <span>
              <a href="#">
                <i class="iconfont icon-pinglun"></i>
                &nbsp;{{item.commentCount}}
              </a>
            </span>
          </div>
        </li>
        <li v-if="contentList.length === 0" style="color:#409eff">无相关记录！</li>
      </ul>
    </div>
    <Right></Right>
    <div class="getmore">
      <el-pagination
        background
        layout="prev, pager, next"
        :total="total"
        :page-size="$store.state.pageSize"
        :current-page="pageIndex"
        @next-click="getNext"
        @prev-click="getPre"
        @current-change="pageSkip"
      ></el-pagination>
    </div>
  </div>
</template>

<script>
import Right from '../article/main/right'
export default {
  data () {
    return {
      contentList: [],
      total: 1,
      pageIndex: 1,
      pageUrl: ''
    }
  },
  components: {
    Right
  },
  methods: {
    // 前往下一页
    getNext () {
      this.pageIndex++
      this.getArticle(this.pageUrl + '&page=' + this.pageIndex)
    },
    // 上一页
    getPre () {
      this.pageIndex--
      this.getArticle(this.pageUrl + '&page=' + this.pageIndex)
    },
    // 跳页
    pageSkip (index) {
      this.pageIndex = index
      this.getArticle(this.pageUrl + '&page=' + this.pageIndex)
    },
    // 获取文章列表
    getArticle (url) {
      this.axios.get(url)
        .then(res => {
          if (res.data.success) {
            // 页数
            this.total = this.$store.state.pageSize * res.data.data.totalPages
            this.contentList = res.data.data.content
            this.contentList = []
            res.data.data.content.forEach(item => {
              this.contentList.push(item)
            })
            // 处理图片路径
            this.contentList.forEach(item => {
              item.userImage = this.$store.state.url + item.userImage
            })
          } else {
            this.$message.error('获取失败！！')
          }
        })
        .catch(err => {
          console.log(err)
          this.$message.error('未知错误')
        })
    },
    // 获取url
    getUrl (val) {
      return 'article/condition/select?articleTitle=' + val
    },
    // 点赞
    parise (id, i) {
      this.axios.get('/islogin')
        .then(res => {
          if (res.data.success) {
            var formData = new FormData()
            formData.append('userId', parseInt(window.sessionStorage.getItem('userId')))
            formData.append('articleId', parseInt(id))
            this.axios.post('/star', formData)
              .then(res => {
                if (res.data.success) {
                  if (this.contentList[i].star === false) {
                    this.contentList[i].articleStar++
                    this.contentList[i].star = true
                  } else {
                    this.contentList[i].articleStar--
                    this.contentList[i].star = false
                  }
                }
              })
              .catch(err => {
                this.$message.error('点赞失败！')
                console.log(err)
              })
          } else {
            this.$message.error('点赞失败，请先登陆！')
          }
        })
        .catch(err => {
          this.$message.error('点赞失败！')
          console.log(err)
        })
    }
  },
  created () {
    this.pageUrl = this.getUrl(this.$route.params.key)
    this.getArticle(this.pageUrl)
  },
  watch: {
    $route: {
      handler (val) {
        this.pageUrl = this.getUrl(val.params.key)
        this.getArticle(this.pageUrl)
        this.pageIndex = 1
      }
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
.main {
  width: 1200px;
  margin: 0 auto;
  margin-top: 68px;
  .getmore {
    float: left;
    width: 830px;
    height: 52px;
    padding-top: 15px;
    border-radius: 10px;
    background-color: #fff;
    line-height: 52px;
    font-size: 18px;
    color: #666;
    text-align: center;
    cursor: pointer;
  }
  .content {
    float: left;
    width: 830px;
    box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.05);
    background-color: #fff;
    .class-title {
      padding: 10px 0 0 15px;
      height: 30px;
      border-left: 4px solid #419eff;
      margin-bottom: 18px;
      color: #666;
      span {
        color: #419eff;
      }
    }
    .content-list {
      li {
        display: inline-block;
        width: 800px;
        padding: 10px 20px 14px 20px;
        border-bottom: 1px solid #f3f3f3;
        color: #333;
        h3 {
          a {
            color: #5c5c5c;
          }
          a:hover {
            color: #47acff;
          }
        }
        .content-author {
          float: left;
          margin-top: 10px;
          font-size: 13px;
          a {
            color: #999;
            img {
              border-radius: 50%;
              width: 20px;
              vertical-align: bottom;
            }
            span {
              text-indent: 2em;
            }
          }
          a:hover {
            color: #47acff;
          }
        }
        .handle {
          float: right;
          span {
            a {
              display: inline-block;
              width: 60px;
              margin-top: 10px;
              padding: 0;
              border: 0;
              color: #41aaff;
              font-size: 13px;
              i {
                color: #999;
                font-size: 14;
              }
              .pariseActive {
                color: #41aaff;
              }
            }
          }
        }
      }
    }
  }
}
</style>
