<template>
  <div class="input-number-range">
    <dm-number-input
      v-model="beginValue"
      :currency="currency"
      :label="label"
      :precision="precision"
      :disabled="disabled"
      :readonly="readonly"
      :placeholder="startPlaceholder"
      :prepend="prepend"
      :append="append"
      :allow-negative="allowNegative"
      :clearable="clearable"
      @blur="onBeginBlur">
    </dm-number-input>
    <span>{{rangeSeparator}}</span>
    <dm-number-input
      v-model="endValue"
      :currency="currency"
      :precision="precision"
      :disabled="disabled"
      :readonly="readonly"
      :placeholder="endPlaceholder"
      :prepend="prepend"
      :append="append"
      :allow-negative="allowNegative"
      :clearable="clearable"
      @blur="onEndBlur">
    </dm-number-input>
  </div>
</template>

<script>
  export default {
    name: 'DmNumberRangeInput',
    model: {
      prop: 'value',
      event: 'input'
    },
    data() {
      return {
        arrValue: this.value,
        beginValue: this.value && this.value.length > 0 ? this.value[0] : null,
        endValue: this.value && this.value.length > 0 ? this.value[1] : null
      }
    },
    props: {
      // 接受外部v-model传入的值
      value: {
        type: String | Array
      },
      // 货币类型
      currency: {
        type: String,
        default: null
      },
      prepend: {
        type: String,
        default: undefined
      },
      append: {
        type: String,
        default: undefined
      },
      // 小数位数
      precision: {
        type: Number,
        default: 0
      },
      clearable: {
        type: Boolean
      },
      label: {
        type: String
      },
      disabled: {
        type: Boolean
      },
      readonly: {
        type: Boolean
      },
      startPlaceholder: {
        type: String,
        default: () => {
          return '请输入'
        }
      },
      endPlaceholder: {
        type: String,
        default: () => {
          return '请输入'
        }
      },
      rangeSeparator: {
        type: String,
        default: () => {
          return '至'
        }
      },
      allowNegative: {
        type: Boolean | String,
        default: true
      },
      // 数据类型 （一般数值、金额）
      type: {
        type: String
      }
    },
    watch: {
      value(val, old) {
        if (val != old) {
          [this.beginValue, this.endValue] = val
        }
      },
      arrValue(val, old) {
        this.$emit('input', val)
      },
      beginValue(val, oldVal) {
        // console.log('new: %s, old: %s', val, oldVal)
        if (val != oldVal) {
          this.$emit('input', [this.beginValue, this.endValue])
        }
      },
      endValue(val, oldVal) {
        // console.log('new: %s, old: %s', val, oldVal)
        if (val != oldVal) {
          this.$emit('input', [this.beginValue, this.endValue])
        }
      }
    },
    mounted() {
    },
    methods: {
      // 处理最值
      onBeginBlur(val) {
        if (val > this.endValue) {
          this.endValue = val
        }
      },
      onEndBlur(val) {
        if (this.beginValue && val < this.beginValue) {
          this.endValue = this.beginValue
        }
      }
    }
  }
</script>

<style lang="scss" scoped>
  .input-number-range {
    display: flex;
    box-sizing: border-box;
    justify-content: center;
    align-content: center;
    .el-input {
      flex: 1;
    }
    span {
      padding: 0 6px;
      font-size: 12px;
      display: flex;
      align-items: center;
    }
    > .van-cell {
      padding: 10px 16px 10px 5px;
    }
    .van-cell:first-child {
      padding: 10px 5px 10px 16px;
    }
  }
</style>
