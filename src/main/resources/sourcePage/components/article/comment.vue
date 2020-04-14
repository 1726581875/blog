<template>
  <div class="comment">
    <h3>评论</h3>
    <p>
      <textarea
        name
        id
        cols="30"
        rows="10"
        placeholder="想对作者说点什么"
        v-model="reply.content"
        maxlength="1000"
        ref="content"
      ></textarea>
    </p>
    <p class="comment-handle">
      <span>你还能输入{{num}}字</span>
      <el-button type="info" class="cancel" ref="cancel" @click="defaultReply">取消回复</el-button>
      <el-button type="primary" @click="comment">发表评论</el-button>
    </p>
    <div class="comment-list">
      <ul>
        <li class="clearfix" v-for="(item,index) in commentList" :key="item.commentId">
          <div class="msg">
            <img :src="$store.state.url+item.userImage" alt />
            <span class="name">{{item.userName}}</span>
            <span class="time">{{item.createTime | format}}</span>
          </div>
          <div class="handle">
            <span>
              <a href="#" @click.prevent="show(index)">查看回复( {{item.replyList.length}})</a>
              <a href="#" @click.prevent="replyed(item.commentId, item.userId, item.userName)">回复</a>
              <a
                href="#"
                :class="{'active': item.star}"
                @click.prevent="bigParise(item.commentId,index)"
              >
                <i class="iconfont icon-dianzan"></i>
                &nbsp; {{item.commentStar}}
              </a>
            </span>
          </div>
          <div class="content">
            <p>{{item.commentContent}}</p>
          </div>
          <div class="watch" ref="minComment">
            <ul>
              <li class="clearfix" v-for="(minItem,i) in item.replyList" :key="minItem.replyId">
                <div class="msg">
                  <img :src="minItem.replyerImage" alt />
                  <span class="name">
                    {{minItem.replyerName}}&nbsp;&nbsp;&nbsp;
                    <span>回复</span>
                    &nbsp;&nbsp;&nbsp;{{minItem.toUserName}}:
                  </span>
                  <span class="time">{{minItem.createTime | format}}</span>
                </div>
                <div class="handle">
                  <span>
                    <a
                      href="#"
                      @click.prevent="replyed(item.commentId, item.userId, item.userName)"
                    >回复</a>
                    <a
                      href="#"
                      @click.prevent="minParise(minItem.replyId,index,i)"
                      :class="{'active': minItem.star}"
                    >
                      <i class="iconfont icon-dianzan"></i>
                      &nbsp;{{minItem.replyStar}}
                    </a>
                  </span>
                </div>
                <div class="content">
                  <p>{{minItem.replyContent}}</p>
                </div>
              </li>
              <li v-if="item.replyList.length === 0" style="color:#409eff">无相关回复！</li>
            </ul>
          </div>
        </li>
        <li v-if="commentList.length === 0" style="color:#409eff">无相关评论！</li>
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
      // 评论消息
      commentList: [
        // {
        //   id: 1,
        //   img: require('../../assets/image/avatar-default.png'),
        //   master: '章鱼哥',
        //   time: '3个月前',
        //   answerNum: 11,
        //   parise: 11,
        //   content: '这个人很懒，什么也没留下来',
        //   minList: [
        //     {
        //       id: 1,
        //       img: require('../../assets/image/avatar-default.png'),
        //       master: '章鱼哥',
        //       visitor: '海绵宝宝',
        //       time: '3个月前',
        //       parise: 12,
        //       content: '这个人很懒，什么也没留下来'
        //     }
        //   ]
        // }
      ],
      reply: {
        articleId: '',
        commentId: '',
        userId: '',
        toUserId: '',
        content: ''
      },
      // 页号
      pageIndex: 1,
      // 页数
      total: 1,
      // 获取评论url
      url: '/comment/comments?articleId=' + this.$route.params.id
    }
  },
  components: {

  },
  methods: {
    // 控制展示评论中的评论
    show (i) {
      this.commentList[i].showFlag = !this.commentList[i].showFlag
      if (this.commentList[i].showFlag) {
        this.$refs.minComment[i].style.display = 'block'
      } else {
        this.$refs.minComment[i].style.display = 'none'
      }
    },
    // 封装点赞代码
    parise (target, data) {
      this.axios.post('/star', data)
        .then(res => {
          if (res.data.success) {
            if (target.star === false) {
              target.commentStar++
              target.replyStar++
              target.star = true
            } else {
              target.commentStar--
              target.replyStar--
              target.star = false
            }
          } else {
            this.$message.error('点赞失败,请重新点赞')
          }
        })
        .catch(err => {
          console.log(err)
          this.$message.error('点赞异常,请重新点赞')
        })
    },
    // 评论的点赞
    bigParise (id, i) {
      var formData = new FormData()
      formData.append('userId', window.sessionStorage.getItem('userId'))
      formData.append('commentId', id)
      // 调用parise方法
      this.parise(this.commentList[i], formData)
    },
    // 评论中的评论的点赞
    minParise (id, index, i) {
      var formData = new FormData()
      formData.append('userId', window.sessionStorage.getItem('userId'))
      formData.append('replyId', id)
      // 调用parise方法
      this.parise(this.commentList[index].replyList[i], formData)
    },
    // 回复作者
    defaultReply () {
      this.reply.commentId = ''
      this.reply.toUserId = ''
      this.$refs.content.placeholder = '想对作者说点什么'
      document.querySelector('.cancel').style.display = 'none'
    },
    // 回复评论
    replyed (id, userId, name) {
      this.reply.commentId = id
      this.reply.toUserId = userId
      this.$refs.content.placeholder = '回复：' + name
      // this.$refs.cancel.style.display = 'block'
      document.querySelector('.cancel').style.display = 'inline'
    },
    // 评论功能
    comment () {
      if (this.reply.content.trim().length === 0) {
        this.$message.error('请输入评论内容')
        return
      }
      var formData = new FormData()
      formData.append('articleId', this.reply.articleId)
      formData.append('commentId', this.reply.commentId)
      formData.append('userId', this.reply.userId)
      formData.append('content', this.reply.content)
      formData.append('toUserId', this.reply.toUserId)
      this.axios.post('/comment/insert', formData)
        .then(res => {
          if (res.data.success) {
            this.$message({
              type: 'success',
              message: '评论成功！'
            })
            this.getComment()
            this.reply.content = ''
            this.defaultReply()
          } else {
            this.$message.error('评论失败，请重新评论！')
          }
        })
        .catch(err => {
          console.log(err)
          this.$message.error('评论异常，请重新评论！')
        })
    },
    // 封装点击上、下、跳页部分代码
    getArticle (url) {
      this.axios.get(url, { withCredentials: true })
        .then(res => {
          if (res.data.success) {
            if (res.data.data.commentList.length !== 0) {
              this.commentList = []
              res.data.data.commentList.forEach(item => {
                this.commentList.push(item)
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
      this.getArticle(this.url + '&page=' + this.pageIndex)
    },
    // 上一页
    getPre () {
      this.pageIndex--
      this.getArticle(this.url + '&page=' + this.pageIndex)
    },
    // 跳页
    pageSkip (index) {
      this.pageIndex = index
      this.getArticle(this.url + '&page=' + this.pageIndex)
    },
    // 获取评论的数据
    getComment () {
      this.axios.get(this.url)
        .then(res => {
          if (res.data.success) {
            this.total = res.data.data.commentList.length * res.data.data.totalPages
            this.commentList = res.data.data.commentList
            this.commentList.forEach(item => {
              item.showFlag = false
            })
          } else {
            this.$message.error('加载评论失败！')
          }
        })
        .catch(err => {
          console.log(err)
          this.$message.error('加载评论失败！')
        })
    }
  },
  computed: {
    // 计算回复框还能输入多少个字
    num () {
      return 1000 - this.reply.content.length
    }
  },
  created () {
    // 获取评论数据
    this.getComment()
    // 初始化评论基本信息
    this.reply.articleId = this.$route.params.id
    this.reply.userId = window.sessionStorage.getItem('userId')
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
.active {
  color: #409eff !important;
}
.comment {
  margin-top: 10px;
  h3 {
    color: #409eff;
  }
  p {
    margin-top: 10px;
    textarea {
      width: 750px;
      height: 60px;
      padding: 10px 10px 10px 10px;
      border-radius: 8px;
      border: 1px solid #c1c1c1;
      font-size: 15px;
      resize: none;
      outline: none;
    }
    ::placeholder {
      font-size: 13px;
    }
  }
  .comment-handle {
    text-align: right;
    .cancel {
      display: none;
    }
    span {
      padding-right: 10px;
      font-size: 13px;
      color: #999;
    }
  }
  .comment-list {
    margin-top: 20px;
    li {
      width: 100%;
      padding-bottom: 15px;
      border-bottom: 1px dashed #e0e0e0;
      .msg {
        float: left;
        img {
          width: 24px;
          margin-right: 8px;
          border-radius: 50%;
          vertical-align: bottom;
        }
        .time {
          margin-right: 15px;
          color: #999999;
          font-size: 13px;
        }
        .name {
          margin-right: 15px;
          color: #2e2e2e;
          font-size: 15px;
          font-weight: 700;
        }
        .content {
          font-size: 15px;
        }
      }
      .handle {
        float: right;
        a {
          margin-right: 10px;
          color: #a6a6a6;
          font-size: 15px;
        }
        a:hover {
          color: #409eff;
        }
      }
      .content {
        margin-top: 30px;
        p {
          text-indent: 2em;
        }
      }
      .watch {
        display: none;
        margin-top: 5px;
        margin-left: 40px;
        padding-left: 5px;
        border-left: 4px solid #c5c5c5;
        li {
          border: 0;
          .msg {
            .time {
              color: #999999;
            }
            span {
              color: #2e2e2e;
              span {
                color: #999999;
              }
            }
          }
          .content {
            margin-top: 25px;
          }
        }
      }
    }
  }
  .getmore {
    float: left;
    width: 830px;
    height: 52px;
    margin-left: -30px;
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
