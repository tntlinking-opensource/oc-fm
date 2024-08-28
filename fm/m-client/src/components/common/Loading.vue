<template>
  <van-overlay :show="show" class-name="page-loading">
    <van-loading type="spinner" :color="color">
      <template #default>
        <slot name="default"></slot>
      </template>
    </van-loading>
  </van-overlay>
</template>

<script>
export default {
	name: 'Loading',
  props: {
    show: { // 是否固定在顶部
      type: Boolean,
      default: false
    },
    color: { // 是否固定在顶部
      type: String
    }
  },
  data() {
    return {}
  }
}
</script>
<style lang="scss" scoped>
.page-loading {
  position: absolute;
  z-index: 100;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  width: 100% !important;
  height: 100% !important;
  background-color: rgba(255, 255, 255, 0.7);
}

</style>
