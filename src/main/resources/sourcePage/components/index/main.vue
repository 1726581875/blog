<template>
  <div class="main clearfix">
    <div class="class clearfix">
      <ul>
        <li v-for="(item,index) in titleList" :key="index">
          <a
            href="#"
            v-text="item"
            :class="{active:index === checkIndex}"
            @click.prevent="select(index)"
          ></a>
        </li>
      </ul>
    </div>
    <Right></Right>
    <div class="content">
      <h3 class="class-title">
        当前类别:
        <span v-text="titleList[checkIndex]"></span>
      </h3>
      <ul class="content-list">
        <li v-for="(item,index) in contentList" :key="item.articleId">
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
              <a href="#" @click.prevent="parise(item.articleId,index)">
                <i
                  class="iconfont icon-dianzan"
                  :class="{'active': item.star}"
                >&nbsp;{{item.articleStar}}</i>
              </a>
            </span>
            <span>
              <a href="#">
                <i class="iconfont icon-pinglun"></i>
                &nbsp;{{item.commentCount}}
              </a>
            </span>
            <span>
              <a href="#">
                <i class="iconfont icon-liulan">&nbsp;{{item.articleView}}</i>
              </a>
            </span>
          </div>
        </li>
        <li v-if="contentList.length === 0" style="color:#409eff">无相关记录！</li>
      </ul>
    </div>
    <div class="getmore">
      <el-pagination
        background
        layout="prev, pager, next"
        :total="total"
        :page-size="this.$store.state.pageSize"
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
      // 类别
      titleList: ['全部', '前端', '后端'],
      // 当前选中的类别的下标
      checkIndex: 0,
      // 文章列表
      contentList: [],
      // 页号
      pageIndex: 1,
      // 页数
      total: 1
      // allContentList: [
      //   {
      //     id: 1,
      //     title: 'Java实现 LeetCode 341 扁平化嵌套列表迭代器',
      //     img: require('../../assets/image/avatar-default.png'),
      //     authorName: '章鱼哥',
      //     praise: 18,
      //     comment: 12
      //   }
    }
  },
  components: {
    Right
  },
  methods: {
    // 改变类别
    select (index) {
      var i = index
      // 把title的下标更换成点击的下标
      this.checkIndex = index
      // 处理其他分类
      if (index > 2) {
        i -= 2
        this.axios.get('/article/condition/select?articleType=' + i)
          .then(res => {
            if (res.data.status === 0) {
              // 页数
              this.total = this.$store.state.pageSize * res.data.data.totalPages
              this.pageIndex = 1 // 让默认的页号为第一页
              this.contentList = res.data.data.content
              // 处理图片路径
              this.contentList.forEach(item => {
                item.userImage = this.$store.state.url + item.userImage
              })
            } else {
              // 获取失败
              this.$message.error('获取分类失败！！')
            }
          })
          // 后台错误
          .catch(err => {
            console.log(err)
            this.$message.error('未知错误')
          })
      } else if (index === 0) { // 处理全部分类
        // 获取文章列表
        this.axios.get('/article/condition/select')
          .then(res => {
            if (res.data.status === 0) {
              // 页数
              this.total = res.data.data.content.length * res.data.data.totalPages
              this.contentList = res.data.data.content
              // 处理图片路径
              this.contentList.forEach(item => {
                item.userImage = this.$store.state.url + item.userImage
              })
            } else {
              this.$message.error('加载文章列表失败，请刷新页面重新试试')
            }
          })
          .catch(err => {
            console.log(err)
            this.$message.error('加载文章列表失败，请刷新页面重新试试')
          })
      } else {
        i -= 1
        this.axios.get('/article/condition/select?bigType=' + i)
          .then(res => {
            if (res.data.status === 0) {
              // 页数
              this.total = this.$store.state.pageSize * res.data.data.totalPages
              this.pageIndex = 1 // 让默认的页号为第一页
              this.contentList = res.data.data.content
              // 处理图片路径
              this.contentList.forEach(item => {
                item.userImage = this.$store.state.url + item.userImage
              })
            } else {
              // 获取失败
              this.$message.error('获取分类失败！！')
            }
          })
          // 后台错误
          .catch(err => {
            console.log(err)
            this.$message.error('未知错误')
          })
      }
    },
    // 封装点击上、下、跳页部分代码
    getArticle (url) {
      this.axios.get(url)
        .then(res => {
          if (res.data.success) {
            if (res.data.data.content.length !== 0) {
              this.contentList = []
              res.data.data.content.forEach(item => {
                this.contentList.push(item)
              })
              // 处理图片路径
              this.contentList.forEach(item => {
                item.userImage = this.$store.state.url + item.userImage
              })
            } else {
              this.$message.error('已经加载到尽头了！')
            }
          } else {
            this.$message.error('获取失败！！')
          }
        })
        .catch(err => {
          console.log(err)
          this.$message.error('未知错误，请重试尝试！')
        })
    },
    // 前往下一页
    getNext () {
      this.pageIndex++
      var page = this.checkIndex
      if (this.checkIndex > 2) {
        page -= 2
        this.getArticle('/article/condition/select?articleType=' + page + '&page=' + this.pageIndex)
      } else if (this.checkIndex === 0) {
        this.getArticle('/article/condition/select?page=' + this.pageIndex)
      } else {
        page -= 1
        this.getArticle('/article/condition/select?bigType=' + page + '&page=' + this.pageIndex)
      }
    },
    // 上一页
    getPre () {
      this.pageIndex--
      var page = this.checkIndex
      if (this.checkIndex > 2) {
        page -= 2
        this.getArticle('/article/condition/select?articleType=' + page + '&page=' + this.pageIndex)
      } else if (this.checkIndex === 0) {
        this.getArticle('/article/condition/select?page=' + this.pageIndex)
      } else {
        page -= 1
        this.getArticle('/article/condition/select?bigType=' + page + '&page=' + this.pageIndex)
      }
    },
    // 跳页
    pageSkip (index) {
      this.pageIndex = index
      var page = this.checkIndex
      if (this.checkIndex > 2) {
        page -= 2
        this.getArticle('/article/condition/select?articleType=' + page + '&page=' + this.pageIndex)
      } else if (this.checkIndex === 0) {
        this.getArticle('/article/condition/select?page=' + this.pageIndex)
      } else {
        page -= 1
        this.getArticle('/article/condition/select?bigType=' + page + '&page=' + this.pageIndex)
      }
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
  // 初始化数据
  created () {
    // 获取文章列表
    this.axios.get('/article/condition/select')
      .then(res => {
        console.log(res)
        if (res.data.status === 0) {
          // 获取页数
          this.total = res.data.data.content.length * res.data.data.totalPages
          this.contentList = res.data.data.content
          this.contentList.forEach(item => {
            item.userImage = this.$store.state.url + item.userImage
          })
        } else {
          this.$message.error('加载文章列表失败，请刷新页面重新试试')
        }
      })
      .catch(err => {
        console.log(err)
        this.$message.error('加载文章列表失败，请刷新页面重新试试')
      })
    // 获取类别
    this.axios.get('/categories', { withCredentials: false })
      .then(res => {
        if (res.data.status === 0) {
          res.data.data.forEach(item => {
            this.titleList.push(item.categoryName)
          })
        } else {
          this.$message.error('加载类别失败，请刷新页面重新试试')
        }
      })
      .catch(err => {
        console.log(err)
        this.$message.error('加载类别失败，请刷新页面重新试试')
      })
    // 获取用户信息
    this.axios.get('/user/detail?userId=' + window.sessionStorage.getItem('userId'))
      .then(res => {
        if (res.data.success) {
          window.sessionStorage.setItem('user', JSON.stringify(res.data.data))
          this.$store.state.avatar = res.data.data.userImage
        }
      })
  }
}
</script>

<style scoped lang="less">
li {
  list-style: none;
}
a {
  text-decoration: none;
}
.clearfix:after,
.clearfix:before {
  content: '';
  display: table;
}
.clearfix:after {
  clear: both;
}
.clearfix {
  *zoom: 1;
}
.main {
  width: 1200px;
  margin: 0 auto;
  margin-top: 68px;
  .class {
    float: left;
    width: 790px;
    padding: 20px;
    margin: 0 auto;
    border-radius: 10px;
    background-color: #fff;
    li {
      float: left;
      .active {
        border-color: #46a1ff;
        color: #46a1ff;
      }
      a {
        display: inline-block;
        border: 1px solid #999;
        border-radius: 8px;
        padding: 10px 20px 10px 20px;
        margin-left: 20px;
        margin-top: 15px;
        color: #999;
      }
    }
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
        height: 67px;
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
          text-align: right;
          span {
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
  .content {
    float: left;
    width: 830px;
    margin-top: 25px;
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
              color: #999999;
              font-size: 13px;
              i {
                color: #999;
                font-size: 14px;
              }
              .active {
                color: #41aaff;
              }
            }
          }
        }
      }
    }
  }
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
}
</style>
