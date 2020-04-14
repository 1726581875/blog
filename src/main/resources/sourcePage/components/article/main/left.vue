<template>
  <div class="left clearfix">
    <h2 class="title" v-text="blog.title"></h2>
    <p class="msg">
      <span class="author">
        作者:
        <a href="#" v-text="blog.author"></a>
      </span>
      <span class="time" v-cloak>最后发布于{{blog.time | format}}</span>
      <a href="#" class="parise">
        点赞数:
        &nbsp;{{blog.parise}}
      </a>
      <a href="#" class="collection" @click.prevent="addCollection()">
        <i class="iconfont icon-shoucang" :class="{'active':blog.collection}">收藏</i>
      </a>
    </p>
    <div class="markdown-body content" v-html="blog.content"></div>
    <Comment></Comment>
  </div>
</template>

<script>
import Comment from '../comment'
export default {
  data () {
    return {
      blog: {
        title: '',
        author: '',
        time: '',
        parise: '',
        content: '',
        collection: null
      }
    }
  },
  components: {
    Comment
  },
  methods: {
    // 添加收藏
    addCollection () {
      this.axios.get('/islogin')
        .then(res => {
          if (res.data.success) {
            var formData = new FormData()
            formData.append('userId', window.sessionStorage.getItem('userId'))
            formData.append('articleId', this.$route.params.id)
            this.axios.post('/collection/add', formData)
              .then(res => {
                if (res.data.success) {
                  this.blog.collection = true
                  this.$message({
                    type: 'success',
                    message: '收藏成功！'
                  })
                } else {
                  this.$message.error('收藏失败,请重新尝试')
                }
              })
              .catch(err => {
                console.log(err)
                this.$message.error('收藏异常,请重新尝试')
              })
          } else {
            this.$message.error('收藏失败,请重新尝试')
          }
        })
        .catch(err => {
          this.$message.error('收藏失败！')
          console.log(err)
        })
    }
  },
  // 获取文章详情
  created () {
    this.axios.get('/article/detail?articleId=' + this.$route.params.id)
      .then(res => {
        if (res.data.success) {
          var _data = res.data.data
          this.blog.content = this.converter.makeHtml(_data.articleContent)
          console.log(this.blog.content)
          this.blog.title = _data.articleTitle
          this.blog.author = _data.userDetail.userName
          this.blog.time = _data.createTime
          this.blog.parise = _data.articleStar
          this.blog.collection = _data.collection
        }
      })
  }
}
</script>

<style scoped lang="less">
a {
  text-decoration: none;
}
.left {
  float: left;
  width: 770px;
  padding: 30px 30px 10px 30px;
  background-color: #fff;
  .title {
    color: #5c5c5c;
  }
  .msg {
    margin-top: 10px;
    font-size: 13px;
    color: #999;
    span {
      padding-right: 10px;
      a {
        color: #409eff;
      }
    }
    .parise,
    .collection {
      color: #999;
      i {
        font-size: 13px;
      }
    }
    .active {
      color: #45a0ff;
    }
  }
  .content {
    margin-top: 15px;
    padding-bottom: 30px;
    border-bottom: 1px solid #e0e0e0;
    color: #4d4d4d;
    font-size: 15px;
  }
}
</style>
