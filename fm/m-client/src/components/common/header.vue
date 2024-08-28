<template>
  <div class="nav_bar">
    <van-nav-bar :safe-area-inset-top="true" :placeholder="true" :fixed="true">
      <template #left>
        <van-icon v-show="isShowLeftIcon" name="arrow-left" @click="leftFun" />
      </template>
      <template #title>
        <span>{{ title }}</span>
      </template>
      <template #right>
        <van-icon v-show="isShowRightIcon" :name="iconName" @click="rightFun" />
      </template>
    </van-nav-bar>
  </div>
</template>
<script>
export default {
  name: "Header",
  props: {
    isShowLeftIcon: {
      type: Boolean,
      default: false,
    },
    title: {
      type: String,
    },
    iconName: {
      type: String,
      default: "ellipsis",
    },
    isShowRightIcon: {
      type: Boolean,
      default: false,
    },
    isCustomLeft: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {};
  },
  methods: {
    leftFun() {
      console.log(this.isCustomLeft, "===");
      if (this.isCustomLeft) {
        // 自定义左边按钮功能
        this.$emit("leftfun");
      } else {
        // 返回按钮
        this.$router.back();
      }
    },

    rightFun() {
      this.$emit("rightfun");
    },
  },
};
</script>
<style lang="scss" scoped>
/deep/ .van-nav-bar {
  background-color: #0079ee !important;
}
/deep/ .van-nav-bar__left > .van-icon,
/deep/ .van-nav-bar__right > .van-icon {
  font-size: 20px;
  color: #fff;
}
/deep/ .van-nav-bar__title {
  color: white !important;
  font-size: 16px !important;
}
// /deep/ .van-nav-bar__right {
//   font-size: 20px;
//   color: #fff;
// }
</style>
