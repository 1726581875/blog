<template>
  <div class="main" v-loading="loading">
    <h3 class="title">发表博客</h3>
    <form action>
      <p>
        <label for="blog-title">请输入文章标题:</label>
        <el-input placeholder="请输入文章标题" id="blog-title" v-model="blog.title" class="input"></el-input>
      </p>
      <p>
        <label for="blog-content">请输入文章内容:</label>
        <!-- <textarea
          name="blog-content"
          id="blog-content"
          v-model="blog.content"
          cols="200"
          rows="10"
          placeholder="请输入文章内容"
        ></textarea>-->
        <mavon-editor
          v-model="blog.content"
          style="color:black;z-index:1;height:700px;box-shadow:0;"
          :ishljs="true"
          ref="md"
          @imgAdd="$imgAdd"
        />
      </p>
      <p class="check">
        <span>请选择文章分类:</span>
        <el-select v-model="blog.belong" placeholder="请选择类别" class="select">
          <el-option
            v-for="item in belongs"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
        <span>请选择文章专栏:</span>
        <el-select v-model="blog.class" placeholder="请选择类别" class="select">
          <el-option
            v-for="item in classs"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
      </p>
      <p class="btns">
        <el-button type="success" class="btn" @click="saveAsDraft">保存</el-button>
        <el-button type="primary" class="btn" @click="saveAsBlog">发表</el-button>
      </p>
    </form>
  </div>
</template>

<script>
export default {
  data () {
    return {
      blog: {
        title: '', // 标题
        content: '', // 内容
        belong: '', // 大分类
        class: ''// 小分类
      },
      belongs: [{
        value: '前端',
        label: '前端'
      }, {
        value: '后端',
        label: '后端'
      }],
      // 小分类
      classs: [],
      frontEnd: [],
      backEnd: [],
      loading: false,
      status: 1, // 1是发表 0是保存
      url: '/article/insert' // 默认是新增文章
    }
  },
  components: {

  },
  watch: {
    'blog.belong' (val, oldval) {
      this.blog.class = ''
      if (val === '前端') {
        this.classs = this.frontEnd
      }
      if (val === '后端') {
        this.classs = this.backEnd
      }
    }
  },
  methods: {
    reset () {
      this.blog.title = ''
      this.blog.content = ''
      this.blog.belong = ''// 大分类
      this.blog.class = ''// 小分类
    },
    // 保存为草稿
    saveAsDraft () {
      this.status = 0
      this.publish()
    },
    // 发表博客
    saveAsBlog () {
      this.status = 1
      this.publish()
    },
    // 发表文章
    publish () {
      if (this.blog.title.trim() !== '' && this.blog.content.trim() !== '' && this.blog.belong.trim() !== '' && this.blog.class !== '') {
        this.loading = true // 开启加载
        var formData = new FormData()
        formData.append('bigType', this.blog.belong === '前端' ? 0 : 1)
        formData.append('articleType', this.blog.class)
        formData.append('userId', window.sessionStorage.getItem('userId'))
        formData.append('articleTitle', this.blog.title)
        formData.append('articleContent', this.blog.content)
        formData.append('articleStatus', this.status)
        if (this.$route.params.id !== '-1') { // 处理修改文章
          formData.append('articleId', this.$route.params.id)
          this.url = '/article/update'
        }
        this.axios.post(this.url, formData)
          .then(res => {
            console.log(res)
            if (res.data.success) {
              this.$message({
                message: '操作成功',
                type: 'success'
              })
              this.$router.push('/index')
              this.loading = false // 结束加载
            } else {
              this.$message.error('操作失败！！请重新尝试!')
              this.loading = false
            }
          })
          .catch(err => {
            console.log(err)
            this.$message.error('服务器异常')
            this.loading = false
          })
      } else {
        this.$message.error('请完善表单内容！！')
      }
    },
    // 上传文章图片，pos是图片的位置，就是第几张的意思
    $imgAdd (pos, $file) {
      var formData = new FormData()
      formData.append('fileName', $file)
      this.axios.post('/article/image', formData)
        .then(res => {
          if (res.data.success) {
            this.$refs.md.$img2Url(pos, this.$store.state.url + res.data.data.pic)
          }
        })
    }
  },
  created () {
    // 获取小类别
    this.axios.get('/categories', { withCredentials: false })
      .then(res => {
        if (res.data.status === 0) {
          res.data.data.forEach(item => {
            if (item.bigType === 0) {
              this.frontEnd.push({ value: item.categoryId, label: item.categoryName })
            } else {
              this.backEnd.push({ value: item.categoryId, label: item.categoryName })
            }
          })
        } else {
          this.$message.error('加载类别失败，请刷新页面重新试试')
        }
      })
      .catch(err => {
        console.log(err)
        this.$message.error('加载类别失败，请刷新页面重新试试')
      })
    // 处理修改文章的初始化
    if (this.$route.params.id !== '-1') {
      this.axios.get('/article/detail?articleId=' + this.$route.params.id)
        .then(res => {
          if (res.data.success) {
            this.blog.title = res.data.data.articleTitle
            this.blog.content = res.data.data.articleContent
          } else {
            this.$message.error('加载失败')
          }
        })
        .catch(err => {
          console.log(err)
          this.$message.error('加载失败')
        })
    }
  }
}
</script>

<style scoped lang="less">
.main {
  width: 1140px;
  margin: 0 auto;
  margin-top: 68px;
  padding: 15px 30px 10px 30px;
  background-color: #fff;
  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.05);
  .title {
    padding-top: 20px;
    padding-bottom: 30px;
    border-bottom: 1px solid #e0e0e0;
    color: #409eff;
  }
  form {
    margin-top: 20px;
    p {
      margin-bottom: 10px;
      label {
        display: inline-block;
        margin-bottom: 10px;
        font-size: 16px;
        color: #3d3d3d;
      }
      textarea {
        width: 1120px;
        height: 400px;
        padding: 10px 10px 0 10px;
        border-radius: 8px;
        resize: none;
        outline: none;
        font-size: 16px;
      }
      textarea:focus {
        border: 1px solid #409eff;
      }
      ::placeholder {
        color: #999;
        font-size: 14px;
        font-weight: 700;
      }
    }
    .check {
      margin-left: -100px;
      text-align: center;
      span {
        color: #999;
        margin: 0 20px;
      }
    }
    .btns {
      margin-top: 30px;
      margin-left: 40px;
      text-align: center;
      .btn {
        margin-right: 40px;
      }
    }
  }
}
</style>
