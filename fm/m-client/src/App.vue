<template>
  <div id="app">
    <keep-alive>
      <router-view v-if="isRouterAlive"></router-view>
    </keep-alive>
  </div>
</template>

<script>
  import { getLocalCurrentUser } from '@/utils/auth'
// 传给报表组件的后台服务地址
window.REPORT_SERVER_URL = process.env.REPORT_SERVER_URL;
export default {
  name: "App",
  provide () {
    return {
      reload: this.reload
    }
  },
  data() {
    return {
      isRouterAlive: true
    }
  },
  methods: {
    reload (){
      this.isRouterAlive = false
      this.$nextTick(function(){
        this.isRouterAlive = true
      })
    }
  },
  beforeCreate() {
    // 当前登录用户放进全局参数中
    window.currentUser = getLocalCurrentUser()
  },
};
</script>
<style lang="scss">

</style>
