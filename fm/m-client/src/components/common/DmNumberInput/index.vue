<template>
  <van-field
    ref="numberInput"
    v-model="formatValue"
    v-currency="options"
    :label="label"
    v-bind="$attrs"
    @input="updateValue"
    @blur="handleBlur"
    @focus="handleFocus"
    @clear="handleClear">
    <template v-if="currency" slot="right-icon">{{currency | handleCType}}</template>
    <template v-if="append" slot="right-icon">{{append}}</template>
  </van-field>
</template>

<script>
  import { CurrencyDirective, parse, setValue, getValue } from 'vue-currency-input'

  export default {
    name: 'DmNumberInput',
    model: {
      prop: 'value',
      event: 'input'
    },
    props: {
      value: {
        type: Number | String
      },
      // 语言环境
      locale: {
        type: String
      },
      // 货币类型
      currency: {
        type: String,
        default: null
      },
      // 小数位数
      precision: {
        type: Number,
        default: 2
      },
      // 是否隐藏可忽略的十进制数字、货币符号和焦点分组符号
      distractionFree: {
        type: Boolean,
        default: false
      },
      prefixIcon: {
        type: String
      },
      suffixIcon: {
        type: String
      },
      label: {
        type: String
      },
      min: {
        type: Number,
        default: -Infinity
      },
      max: {
        type: Number,
        default: Infinity
      },
      prepend: {
        type: String,
        default: undefined
      },
      append: {
        type: String,
        default: undefined
      },
      allowNegative: {
        type: Boolean | String,
        default: true
      },
    },
    filters: {
      handleCType(val) {
        let type
        const cTypes = [
          { key: 'CNY', value: '¥' },
          { key: 'EUR', value: '€' },
          { key: 'USD', value: '$' },
          { key: 'GBP', value: '£' }
        ]
        cTypes.forEach(item => {
          if (item.key === val) {
            type = item.value
          }
        })

        return type
      }
    },
    directives: {
      currency: CurrencyDirective
    },
    data() {
      return {
        formatValue: this.value,
        formatOldValue: null
      }
    },
    mounted() {
      this.$nextTick(() => {
        setValue(this.$refs.numberInput, this.value)
      })
    },
    watch:  {
      value(newVal, oldVal) {
        if(newVal != oldVal) {
          this.$nextTick(() => {
            setValue(this.$refs.numberInput, this.value)
          })
        }
      }
    },
    computed: {
      options() {
        return {
          locale: this.locale,
          // currency: this.currency,
          currency: null,
          valueRange: { min: this.min, max: this.max },
          precision: this.precision,
          distractionFree: this.distractionFree,
          allowNegative: this.allowNegative
        }
      }
    },
    methods: {
      updateValue(value) {
        this.$emit('input', parse(this.formatValue, this.options))
      },
      handleBlur(e) {
        if (!e.target.value) {
          setValue(this.$refs.numberInput, 0)
        }
        this.$emit('blur', parse(this.formatValue, this.options), this.formatOldValue)
      },
      handleFocus(e) {
        if (!e.target.value) {
          setValue(this.$refs.numberInput, 0)
        }
        this.$emit('focus', e)
        this.formatOldValue = getValue(this.$refs.numberInput)
      },
      handleClear() {
        setValue(this.$refs.numberInput, null)
        this.$emit('clear')
      }
    }
  }
</script>

<style scoped>

</style>
