<template>
  <div class="draft">
    <h3 class="edit-title">我的草稿</h3>
    <div class="blog-list clearfix">
      <ul>
        <li v-for="(item,index) in drafts" :key="item.articleId">
          <h3>
            <router-link :to="'/article/'+item.articleId" v-text="item.articleTitle"></router-link>
          </h3>
          <div class="blog-msg">
            <span class="time">{{item.createTime | format}}</span>
            <span class="parise">
              <a href="#" v-cloak>
                <i class="iconfont icon-dianzan"></i>
                &nbsp;{{item.articleStar}}
              </a>
            </span>
            <span class="comment">
              <a href="#" v-cloak>
                <i class="iconfont icon-pinglun"></i>
                &nbsp;{{item.commentCount}}
              </a>
            </span>
          </div>
          <div class="blog-handle">
            <span class="check">
              <router-link :to="'/article/'+item.articleId">查看</router-link>
            </span>
            <span class="publish check">
              <a href="#" @click.prevent="publish(item.articleId, index)">发布</a>
            </span>
            <span class="delete">
              <a href="#" @click.prevent="del(item.articleId,index)">移除</a>
            </span>
          </div>
        </li>
      </ul>
    </div>
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
export default {
  data () {
    return {
      drafts: [],
      // 总页
      total: 1,
      // 标识在第几页
      pageIndex: 1,
      pageUrl: '/article/condition/select?userId=' + window.sessionStorage.getItem('userId') + '&articleStatus=0'
    }
  },
  components: {

  },
  methods: {
    // 封装获取文章代码
    getArticle (url) {
      this.axios.get(url)
        .then(res => {
          if (res.data.success) {
            // 页数
            this.total = this.$store.state.pageSize * res.data.data.totalPages
            this.drafts = res.data.data.content
          } else {
            this.$message.error('加载失败')
          }
        })
        .catch(err => {
          console.log(err)
          this.$message.error('加载失败')
        })
    },
    // 下一页
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
    // 封装发布和移除代码
    remove (id, index, url, msg) {
      this.$confirm('是否' + msg + '该草稿？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          var formData = new FormData()
          formData.append('articleIds', [id])
          this.axios.post(url, formData)
            .then(res => {
              console.log(res)
              if (res.data.success) {
                this.drafts.splice(index, 1)
                this.$message({
                  type: 'success',
                  message: msg + '成功！'
                })
              } else {
                this.$message.error(msg + '失败！')
              }
            })
            .catch(err => {
              console.log(err)
              this.$message.error(msg + '失败！')
            })
        })
    },
    // 发布草稿
    publish (id, index) {
      this.remove(id, index, '/article/insert/fromDraft', '发布')
    },
    // 移除草稿
    del (id, index) {
      this.remove(id, index, 'article/delete/forever', '删除')
    }
  },
  created () {
    this.axios.get(this.pageUrl)
      .then(res => {
        console.log(res)
        if (res.data.success) {
          this.total = this.$store.state.pageSize * res.data.data.totalPages
          this.drafts = res.data.data.content
        } else {
          this.$message.error('加载失败')
        }
      })
      .catch(err => {
        console.log(err)
        this.$message.error('获取失败')
      })
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
.draft {
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
  .blog-list {
    margin-top: 10px;
    margin-bottom: 60px;
    li {
      height: 60px;
      border-bottom: 1px solid #dddddd;
      margin-top: 20px;
      h3 {
        font-weight: 400;
        font-size: 20px;
        margin-bottom: 8px;
        a {
          color: #4d4d4d;
        }
        a:hover {
          color: #409eff;
        }
      }
      .blog-msg {
        float: left;
        font-size: 12px;
        color: #999999;
        a {
          padding-left: 10px;
          color: #999999;
        }
      }
      .blog-handle {
        float: right;
        font-size: 12px;
        .delete {
          a {
            color: #ca0c16;
          }
        }
        a {
          padding-left: 10px;
          color: #409eff;
        }
      }
    }
  }
  .getmore {
    text-align: center;
  }
}
</style>
