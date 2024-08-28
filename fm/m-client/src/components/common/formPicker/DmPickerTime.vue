<template>
  <div>
    <van-field
      :value="valueText"
      v-bind="$attrs"
      v-on="$listeners"
      :clickable="!disabled"
      :placeholder="placeholder"
      readonly
      is-link
      @click="disabled ? '' : (showPicker = true)"
    />
    <van-popup
      v-model="showPicker"
      position="bottom"
      v-bind="$attrs"
      v-on="$listeners"
    >
      <van-datetime-picker
        :type="type"
        show-toolbar
        v-model='currentValue'
        v-bind="$attrs"
        v-on="$listeners"
        @cancel="onCancel"
        @confirm="onConfirm"
      />
    </van-popup>
  </div>
</template>

<script>
  /**
   * yyyy-MM-dd
   * yyyy-MM-dd hh:mm:ss
   * yyyy年MM月dd日
   * yyyy年MM月
   * MM月dd日
   * MM-dd
   * MMdd
   * MM/dd/yyyy
   * yyyyMMdd
   * yyyy-MM
   * yyyyMM
   * hh:mm:ss
   * hh:mm
   */
  Date.prototype.Format = function(format){
    let o = {
      "M+" : this.getMonth()+1, //month
      "d+" : this.getDate(), //day
      "h+" : this.getHours(), //hour
      "m+" : this.getMinutes(), //minute
      "s+" : this.getSeconds(), //second
      "q+" : Math.floor((this.getMonth()+3)/3), //quarter
      "S" : this.getMilliseconds() //millisecond
    }

    if(/(y+)/.test(format)) {
      format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }

    for(var k in o) {
      if(new RegExp("("+ k +")").test(format)) {
        format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
      }
    }
    return format
  }

  const isDateObject = function(val) {
    return val instanceof Date;
  };

  const valueEquals = function(a, b) {
    // considers Date object and string
    const dateEquals = function(a, b) {
      const aIsDate = a instanceof Date;
      const bIsDate = b instanceof Date;
      if (aIsDate && bIsDate) {
        return a.getTime() === b.getTime();
      }
      if (!aIsDate && !bIsDate) {
        return a === b;
      }
      return false;
    };

    const aIsArray = a instanceof Array;
    const bIsArray = b instanceof Array;
    if (aIsArray && bIsArray) {
      if (a.length !== b.length) {
        return false;
      }
      return a.every((item, index) => dateEquals(item, b[index]));
    }
    if (!aIsArray && !bIsArray) {
      return dateEquals(a, b);
    }
    return false;
  };

  export default {
    name: 'DmPickerTime',
    model: {
      prop: 'value',
      event: 'oninput',
    },
    props: {
      value: String | Object,
      type: String,
      format: String, // 显示格式
      valueFormat: String,
      disabled: Boolean,
      placeholder: {
        type: String,
        default() {
          return '请选择时间'
        }
      },
    },
    data() {
      return {
        valueText: undefined,
        currentValue: undefined,
        showPicker: false,
      };
    },
    watch: {
      value(val, old) {
        if (val && typeof val == 'string') {
          console.log(val);
          this.valueText = this.toDateString(this.value)
          this.currentValue = this.formatToDate(this.type, this.value)
        }
      }
    },
    mounted() {

    },
    methods: {
      toDateString(str) {
        if (this.type === 'time') return str
        if (this.type === 'year-month') {
          let pattern = /(\d{4})(\d{2})/
          str = str.replace(pattern, '$1-$2')
          return this.dateTimeFormat(this.formatToDate(this.type, str), this.format)
        } else {
          let pattern = /(\d{4})(\d{2})(\d{2})/
          str = str.replace(pattern, '$1-$2-$3')
          return this.dateTimeFormat(this.formatToDate(this.type, str), this.format)
        }
      },
      formatToDate(type, value) { // 转换为时间对象
        if (type === 'time') {
          return value
        } else if (type === 'year-month') {
          let pattern = /(\d{4})(\d{2})/
          return new Date(value.replace(pattern, '$1-$2'))
        } else {
          return new Date(value)
        }
      },
      dateTimeFormat(value, format) { // 格式化为所需显示格式
        let dateTimeStr = ''
        if (!value) return dateTimeStr
        switch (this.type) {
          case 'date':
            dateTimeStr = format ? value.Format(format) : value.Format('yyyy-MM-dd')
            break
          case 'time':
            dateTimeStr = value
            break
          case 'year-month':
            dateTimeStr = format ? value.Format(format) : value.Format('yyyy-MM')
            break
          case 'month-day':
            dateTimeStr = format ? value.Format(format) : value.Format('MM-dd')
            break
          default:
            dateTimeStr = format ? value.Format(format) : value.Format('yyyy-MM-dd hh:mm:ss')
        }
        return dateTimeStr
      },
      formatDateValue(date) {
        if (this.valueFormat && isDateObject(date)) {
          return this.dateTimeFormat(date, this.valueFormat)
        } else {
          return date
        }
      },
      onConfirm(value) {
        this.valueText = this.dateTimeFormat(value, this.format)
        this.currentValue = value
        this.emitInput(value)
        this.showPicker = false
      },
      onCancel(val) {
        this.showPicker = false
      },
      emitInput(val) {
        const formatted = this.formatDateValue(val);
        if (!valueEquals(this.value, formatted)) {
          this.$emit('oninput', formatted);
        }
      },
    },
  };
</script>

<style scoped>
</style>

