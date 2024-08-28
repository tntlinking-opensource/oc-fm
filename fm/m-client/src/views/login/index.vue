<template>
    <div class="container">
        <div class="content-wrapper">
          <div class="login-bg">
            <van-image fit="cover" :src="require('@/assets/images/login-bg.jpg')"/>
          </div>
          <van-form class="login-form" ref="form">
            <van-cell-group>
              <van-field
                required
                v-model="loginForm.username"
                name="用户名"
                label="用户名"
                :rules="[{ required: true, message: '请填写用户名' }]"
                placeholder="请输入用户名"/>
              <van-field
                required
                v-model="loginForm.password"
                type="password"
                name="密码"
                label="密码"
                :rules="[{ required: true, message: '请填写密码' }]"
                placeholder="密码"/>
            </van-cell-group>
            <van-button :disabled="isLogining" style="margin-top: 30px" round block type="info" @click="authLogin">登录</van-button>
          </van-form>
        </div>
        <Loading :show="loading" color="#1989fa"/>
    </div>
</template>
<script>
import BaseUI from '@/views/components/baseUI'
const _import = require('@/router/_import_' + process.env.NODE_ENV)
import { getToken } from '@/api/auth'
import { setLocalToken, setLocalCurrentUser, setLocalRouters, setLocalDataPermisions, setLocalCurrentCompany, getAppRoot } from '@/utils/auth'
import { Notify } from 'vant'

// 微应用时，appKey作为路由前缀
const root = getAppRoot()

export default {
  name: '',
  extends: BaseUI,
  data() {
    return {
      loginForm: {
        username: '',
        password: ''
      },
      isLogining: false
    }
  },
  mounted() {
  },
  computed: {},
  methods: {
    authLogin() {
      this.$refs.form.validate().then(() => {
        const toast = this.$toast.loading({
          message: '登录中...',
          duration: 0,
          forbidClick: true
        })
        this.isLogining = true
        getToken(this.loginForm.username, this.loginForm.password).then(responseData => {
          if(responseData.code == 100) {
            this.handleLoginInfo(responseData.data)
          } else {
            this.loginForm.username = ''
            this.loginForm.password = ''
            Notify({ type: 'warning', message: responseData.msg })
            this.isLogining = false
          }
          toast.clear()
        })
        .catch(error => {
          this.outputError(error)
          toast.clear()
          this.isLogining = false
        })
      }).catch(() => {})
    },
    handleLoginInfo(userData) {
      setLocalCurrentUser({
        id: userData.userId,
        name: userData.username,
        loginname: userData.loginname,
        company: userData.company,
        department: userData.department
      })
      setLocalCurrentCompany({
        id: userData.company.id,
        code: userData.company.code,
        name: userData.company.name
      })
      setLocalToken(userData.token)

      this.$router.push(`${root}/afterlogin`);
    }
  }
}
</script>

<style scoped lang="scss">
  @import "../../assets/mixin.scss";
  .container {
      height: 100%;
      background-color: #ffffff;
    .login-bg {
      height: 150px;
      width: 100%;
      .van-image {
        width: 100%;
        height: 100%;
      }
    }
    .login-form {
      padding: 30px;
    }
  }
</style>
