<template>
  <el-card class="index-module-card" :class="{ panelBg: isPanelStyle }">
    <div class="personal-message-box">
      <el-avatar :size="70" src="https://picsum.photos/200/200"></el-avatar>
      <span class="user-title" @click="openPersonalInfoDialog">{{ userName }}</span>
    </div>
<!--    <personal-info ref="personalInfo" v-on:username-changed="doGetCurrentUsername"></personal-info>-->
  </el-card>
</template>

<script>
// import PersonalInfo from '@/views/admin/common/personalInfo'
export default {
  props: ['id', 'editPersonStyle'],
  data () {
    return {
      panelId: this.id,
      userName: currentUser.name,
      isPanelStyle: this.editPersonStyle
    }
  },
  components: {
    // PersonalInfo
  },
  watch:{
    editPersonStyle:{
      deep: true,
      handler() {
        this.isPanelStyle = this.editPersonStyle;
      }
    },
  },
  methods: {
    doGetCurrentUsername() {
      this.userName = currentUser.name
    },
    openPersonalInfoDialog() {
      this.$refs.personalInfo.$emit('openPersonalInfoDialog')
    }
  },
  mounted() {
  },
}
</script>

<style lang="scss" scoped>
  .index-module-card.el-card /deep/ .el-card__header {
    padding: 10px 16px!important;
    .card-close, .card-setting {
      float: right;
      padding: 3px 0
    }
  }
.el-card {
  height: 100%;
}
.personal-message-box {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 20px 0;
  height: 100%;
}
.personal-message-box .user-title {
  margin: 20px 0;
  font-size: 1.5em;
  font-weight: bold;
}
.btn-box {
  display: flex;
  justify-content: center;
  width: 100%;
}
.btn-box .person-btn,
.edit-btn {
  flex: 0 0 50%;
  border: 1px solid #d7ddeb;
  border-radius: 4px;
  padding: 10px 0;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 0.9em;
}
.btn-box .person-btn {
  margin-right: 5px;
}
.btn-box .edit-btn {
  margin-left: 5px;
}
.panelBg {
  background-color: #d3d6dc !important;
  border: none !important;
}
.exitBtn {
  border: 1px solid #d7ddeb;
  border-radius: 4px;
  text-align: center;
  padding: 10px 50px;
  cursor: pointer;
  background-color: #fff;
}
</style>
