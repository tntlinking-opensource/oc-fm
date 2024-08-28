<template>
  <div>
    <van-field
      v-model="currentValueStartText"
      v-bind="$attrs"
      :label="startLabel"
      :placeholder="startPlaceholder"
      readonly
      is-link
      v-on="$listeners"
      @click="disabled ? '' : (showStartPicker = true)"
    />
    <van-popup
      v-model="showStartPicker"
      position="bottom"
      v-bind="$attrs"
      v-on="$listeners"
    >
      <van-datetime-picker
        :type="type"
        show-toolbar
        v-model="currentValueStartDateTime"
        v-bind="$attrs"
        v-on="$listeners"
        @cancel="onCancel"
        @confirm="onConfirm"
      />
    </van-popup>
    <van-field
      v-model="currentValueEndText"
      v-bind="$attrs"
      :label="endLabel"
      :placeholder="endPlaceholder"
      readonly
      is-link
      v-on="$listeners"
      @click="disabled ? '' : (showEndPicker = true)"
    />
    <van-popup
      v-model="showEndPicker"
      position="bottom"
      v-bind="$attrs"
      v-on="$listeners"
    >
      <van-datetime-picker
        :type="type"
        show-toolbar
        v-model="currentValueEndDateTime"
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
  Date.prototype.Format = function (format) {
    let o = {
      "M+": this.getMonth() + 1, //month
      "d+": this.getDate(), //day
      "h+": this.getHours(), //hour
      "m+": this.getMinutes(), //minute
      "s+": this.getSeconds(), //second
      "q+": Math.floor((this.getMonth() + 3) / 3), //quarter
      S: this.getMilliseconds(), //millisecond
    };

    if (/(y+)/.test(format)) {
      format = format.replace(
        RegExp.$1,
        (this.getFullYear() + "").substr(4 - RegExp.$1.length)
      );
    }

    for (var k in o) {
      if (new RegExp("(" + k + ")").test(format)) {
        format = format.replace(
          RegExp.$1,
          RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length)
        );
      }
    }
    return format;
  };

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
    name: 'DmPickerTimeRange',
    model: {
      prop: 'value',
      event: 'oninput',
    },
    props: {
      disabled: Boolean,
      value: {
        default() {
          return []
        }
      },
      format: String, // 显示格式
      valueFormat: String,
      startPlaceholder: {
        type: String,
        default() {
          return '请选择开始时间'
        }
      },
      endPlaceholder: {
        type: String,
        default() {
          return '请选择结束时间'
        }
      },
      type: String,
      startLabel: {
        type: String,
        default() {
          return '开始时间'
        }
      },
      endLabel: {
        type: String,
        default() {
          return '结束时间'
        }
      }
    },
    data() {
      return {
        // 开始
        showStartPicker: false,
        currentValueStartText: undefined,
        currentValueStartDateTime: undefined,
        // 结束
        showEndPicker: false,
        currentValueEndText: undefined,
        currentValueEndDateTime: undefined
      };
    },
    watch: {
      value(val, old) {
        if (this.isArrayObject(val)) {
          this.currentValueStartText = this.toDateString(val[0])
          this.currentValueStartDateTime = this.formatToDate(this.type, val[0])

          this.currentValueEndText = this.toDateString(val[1])
          this.currentValueEndDateTime = this.formatToDate(this.type, val[1])
        }
      }
    },
    methods: {
      isArrayObject(val) {
        return (val && Array.isArray(val) && val.length > 0)
      },
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
      emitInput(startValue, endValue) {
        const startFormatted = this.formatDateValue(startValue);
        const endFormatted = this.formatDateValue(endValue);
        if (!valueEquals(startValue, startFormatted) && !valueEquals(endValue, endFormatted)) {
          this.$emit('oninput', [
            this.formatDateValue(startFormatted),
            this.formatDateValue(endFormatted)
          ]);
        }
      },
      onConfirm(value) {
        if (this.showStartPicker) {
          this.currentValueStartText = this.dateTimeFormat(value, this.format)
          this.currentValueStartDateTime = value;
          this.showStartPicker = false;
        }
        if (this.showEndPicker) {
          this.currentValueEndText = this.dateTimeFormat(value, this.format)
          this.currentValueEndDateTime = value;
          this.showEndPicker = false;
        }
        this.emitInput(this.currentValueStartDateTime, this.currentValueEndDateTime)
      },
      onCancel(val) {
        if (this.showStartPicker) {
          this.showStartPicker = false;
        }
        if (this.showEndPicker) {
          this.showEndPicker = false;
        }
      },
    },
  };
</script>

<style scoped>
</style>

