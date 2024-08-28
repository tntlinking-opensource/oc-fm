<template>
  <div>
    <van-field
      :value="label"
      v-bind="$attrs"
      v-on="$listeners"
      :clickable="!disabled"
      readonly
      is-link
      @click="disabled ? '' : (show = true)"
    />
    <van-popup
      v-model="show"
      round
      position="bottom"
      v-bind="$attrs"
      v-on="$listeners"
    >
      <van-cascader
        :title="title"
        v-bind="$attrs"
        v-on="$listeners"
        :value="currentValue"
        :active-color="activeColor"
        :placeholder="placeholder"
        :closeable="closeable"
        :options="options"
        :show-header="showHeader"
        :field-names="fieldNames"
        @close="show = false"
        @finish="onFinish"
      />
    </van-popup>
  </div>
</template>

<script>
  export default {
    name: "DmCascader",
    model: {
      prop: "value",
      event: "onInput",
    },
    props: {
      disabled: Boolean,
      value: {
        type: [String, Number],
      },
      options: {
        type: Array,
        default() {
          return [
            {
              label: "浙江省",
              value: "330000",
              children: [{ label: "杭州市", value: "330100" }],
            },
            {
              label: "江苏省",
              value: "320000",
              children: [{ label: "南京市", value: "320100" }],
            },
          ];
        },
      },
      activeColor: String,
      placeholder: {
        type: String,
        default() {
          return "请选择";
        },
      },
      closeable: {
        type: Boolean,
        default() {
          return true;
        },
      },
      showHeader: {
        type: Boolean,
        default() {
          return true;
        },
      },
      title: {
        type: String,
        default() {
          return "请选择所在地区";
        },
      },
      fieldNames: {
        type: Object,
        default() {
          return { text: "text", value: "value", children: "children" };
        },
      },
    },
    data() {
      return {
        label: "",
        // 组件内 当前值
        currentValue: this.value,
        show: false,
        fieldValue: "",
        cascaderValue: "",
      };
    },
    watch: {
      value(v) {
        this.currentValue = v;
        if (this.options.length > 0) {
          this.findChildren(this.options);
        }
      },
      options(val, old) {
        this.findChildren(val);
      },
    },
    methods: {
      // 寻找末级，回显组件内Filed
      findChildren(ary) {
        for (let i = 0; i < ary.length; i++) {
          if (!!ary[i].children) {
            this.findChildren(ary[i].children);
          } else {
            if (this.currentValue == ary[i][this.fieldNames.value]) {
              this.label = ary[i][this.fieldNames.text];
            }
          }
        }
      },
      // 全部选项选择完毕后，会触发 finish 事件
      onFinish({ selectedOptions }) {
        this.show = false;
        let lastData = selectedOptions[selectedOptions.length - 1];
        this.label = lastData[this.fieldNames.text];
        this.$emit("onInput", lastData[this.fieldNames.value]);
      },
    },
  };
</script>
