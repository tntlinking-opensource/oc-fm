<template>
  <div class="homeGrid">
    <div class="cells">
      <van-cell :to="{name: 'settings'}" is-link>
        <template #title>
          <van-icon name="setting-o" />
          <span>设置</span>
        </template>
      </van-cell>
    </div>
  </div>
</template>

<script>
import BaseUI from '@/views/components/baseUI'
export default {
  name: 'homeGrid',
  extends: BaseUI,
  props: {
    user: {
      type: Object
    },
  },
  data () {
    return {
      modelList: [
        { name: '秩序安全巡检', propInspectType: '8637' },
        { name: '消防安全巡检', propInspectType: '8649' },
        { name: '环境卫生巡检', propInspectType: '8650' },
        { name: '四害消杀巡检', propInspectType: '8651' },
        { name: '设备巡检', propInspectType: '8654' }
      ]
    }
  },
  created() {
  },
  methods: {
    getPropInspectTask(model) {
      this.$store.dispatch('property/setPropInspectType',model.propInspectType)
      this.$nextTick(() => {
        this.$router.push({name: 'propInspectTask'})
      })
    },
  }
}
</script>

<style lang="scss" scoped>
/deep/ .van-grid-item__content--center {
  border-radius: 8px;
  box-shadow: #dcdfe6 0px 1px 5px 1px;
}
.cells {
  padding: 10px;
}
/deep/ .van-cell__title {
  .van-icon {
    font-size: 16px;
    color: #2A67FE;
  }
}
/deep/ .van-grid-item__text {
  color: #000;
  font-size: 14px;
}
</style>
