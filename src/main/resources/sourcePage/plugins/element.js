import Vue from 'vue'
import { Button, input, Message, Radio, Select, Option, MessageBox, pagination, dialog, Loading } from 'element-ui'

Vue.use(Button)
Vue.use(dialog)
Vue.use(input)
Vue.use(Radio)
Vue.use(Select)
Vue.use(pagination)
Vue.use(Option)
Vue.use(Loading)
Vue.prototype.$msgbox = MessageBox
Vue.prototype.$alert = MessageBox.alert
Vue.prototype.$confirm = MessageBox.confirm
Message.install = function (Vue, options) { Vue.prototype.$message = Message }
Vue.use(Message)
