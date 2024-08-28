<template>
  <div>
    <el-button
      v-for="(item, index) in btnAry"
      :type="item.type"
      :icon="item.icon"
      :key="index"
      @click="onClick(item.code)"
      >{{ item.label }}</el-button
    >
  </div>
</template>

<script>
/**
 * @description 通用按钮组
 *
 * 属性
 * @prop {Array} btnAry - 按钮组
 *
 * 事件
 * @event {Function} onGetData - 获取数据 { tree:Obejct,tableData: Obejct / Array }
 *
 * 方法
 * @method {Function} open - 打开
 */
export default {
  props: {
    btnAry: {
      type: Array,
      default: {
        return: [
          {
            code: "test",
            type: "primary",
            icon: "el-icon-arrow-left",
            label: "按钮"
          }
        ]
      }
    }
  },
  data() {
    return {};
  },

  mounted() {},

  methods: {
    onClick(code){
        this.$emit('onBtnGruopClick',code)
    },
  }
};
</script>

<style lang="scss" scoped></style>
