<template>
  <div class="collection">
    <h3 class="edit-title">我的收藏</h3>
    <div class="blog-list clearfix">
      <ul>
        <li v-for="(item,index) in collections" :key="item.articleId">
          <h3>
            <router-link :to="'/article/'+item.articleId" v-text="item.articleTitle"></router-link>
          </h3>
          <div class="blog-msg">
            <span class="time" v-if="item.article">{{item.article.createTime | format}}</span>
            <span class="parise">
              <a href="#" v-cloak v-if="item.article">
                <i class="iconfont icon-dianzan"></i>
                &nbsp;{{item.article.articleStar}}
              </a>
            </span>
            <span class="comment">
              <a href="#" v-cloak v-if="item.article">
                <i class="iconfont icon-pinglun"></i>
                &nbsp;{{item.article.commentCount}}
              </a>
            </span>
          </div>
          <div class="blog-handle">
            <span class="check">
              <router-link :to="'/article/'+item.articleId">查看</router-link>
            </span>
            <span class="delete">
              <a href="#" @click.prevent="del(item.collectionId,index)">移除</a>
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
      collections: [],
      // 总页
      total: 1,
      // 标识在第几页
      pageIndex: 1,
      pageUrl: '/collection/select?userId=' + window.sessionStorage.getItem('userId')
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
            this.collections = res.data.data.content
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
    // 删除收藏文章
    del (id, i) {
      this.$confirm('是否移除该收藏？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          var formData = new FormData()
          formData.append('collectionIds', id)
          this.axios.post('/collection/remove', formData)
            .then(res => {
              console.log(res)
              if (res.data.success) {
                this.collections.splice(i, 1)
                this.$message({
                  type: 'success',
                  message: '移除成功！'
                })
                // 重新获取页面数据
                this.getArticle(this.pageUrl)
              } else {
                this.$message.error('移除失败,请重新尝试!')
              }
            })
            .catch(err => {
              console.log(err)
              this.$message.error('移除异常,请重新尝试!')
            })
        })
    }
  },
  created () {
    this.getArticle(this.pageUrl)
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
.collection {
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
