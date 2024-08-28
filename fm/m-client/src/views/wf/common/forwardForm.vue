<template>
  <div class="forwardForm">
    <van-action-sheet
      :loading="loading"
      v-model="dialogProps.visible"
      :actions="users"
      cancel-text="取消"
      :description="dialogProps.title"
      close-on-click-action
      @cancel="onCancel"
      @select="onSelect"
    />
  </div>
</template>

<script>
import BaseUI from '@/views/components/baseUI'
// import { listUserAll } from '@/api/admin/user'
export default {
  extends: BaseUI,
  name: 'forwardForm',
  data () {
    return {
      users: [],
      dialogProps: {
        visible: false,
        action: '',
        title: '选择办理人'
      },
    }
  },
  created () {
      
  },
  methods: {
    onCancel() {
      this.dialogProps.visible = false
    },
    onSelect(val) {
      this.$emit('forward-user-finished', val)
      this.dialogProps.visible = false
    },
    // init() {
    //   if (this.users.length > 0) return
    //   this.setLoad()
    //   listUserAll({}).then(responseData => {
    //     if(responseData.code == 100) {
    //       this.users = responseData.data
    //     } else {
    //       this.showMessage(responseData)
    //     }
    //     this.resetLoad()
    //   }).catch(error => {
    //     this.outputError(error)
    //   })
    // },
    openForwardDialog(user) {
      this.users = user
      // this.init()
      this.dialogProps.visible = true
    }
  }
}
</script>

<style lang="scss" scoped>
.userForm{
  height: 100vh;
}
</style>