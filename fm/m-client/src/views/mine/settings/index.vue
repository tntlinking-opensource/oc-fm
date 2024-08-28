<template>
  <div class="settings">
    <div class="page-container">
      <div class="scroll">
        <van-cell is-link title="语言" />
        <van-cell is-link title="账号与安全" />
        <van-cell is-link title="应用设置" @click="fetchSso"/>
      </div>
      <div class="submitbotton">
        <van-button style="margin-top: 30px" round block type="info" @click="doLogOut">退出登录</van-button>
      </div>
    </div>
  </div>
</template>


<script>
import { logOut } from '@/api/auth'
import { clearLocalData } from '@/utils/auth'
import { Notify } from 'vant'
export default {
  name: 'settings',
  components: {
  },
  props: {
    user: {
      type: Object
    }
  },
  data () {
    return {

    }
  },
  mounted() {

  },
  methods: {
    doLogOut() {
      this.$dialog.confirm({
        title: '提示',
        message: '确认退出吗？',
      })
      .then(() => {
        logOut().then(responseData => {
          if (responseData.code == 100) {
            clearLocalData()
            this.$router.push('/login')
          } else {
            Notify({ type: 'warning', message: responseData.msg })
          }
        })
        .catch(error => {
          this.outputError(error)
        })
      })
      .catch(() => {
        // on cancel
      })
    }
  }
}
</script>

<style lang="scss" scoped>
</style>
