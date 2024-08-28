<template>
  <div>
    <van-field
      :value="valueText"
      :clickable="!disabled"
      readonly
      is-link
      v-bind="$attrs"
      v-on="$listeners"
      @click="disabled ? '' : (showPicker = true)"
    />
    <van-popup
      v-model="showPicker"
      position="bottom"
      v-bind="$attrs"
      v-on="$listeners"
      @open="onOpen"
    >
      <van-picker
        ref="picker"
        show-toolbar
        :value-key="valueKey"
        @cancel="onCancel"
        @confirm="onConfirm"
        @change="onChange"
        :columns="columns"
        :default-index="defaultIndex"
        v-bind="$attrs"
        v-on="$listeners"
      />
    </van-popup>
  </div>
</template>

<script>
export default {
  name: "DmPicker",
  model: {
    prop: "value",
    event: "oninput",
  },
  props: {
    value: {
      type: [String, Number, Object],
    },
    valueKey: {
      type: String,
      default: () => {
        return "label";
      },
    },
    columns: Array,
    disabled: Boolean,
    defaultIndex: {
      type: [String, Number],
    },
  },
  data() {
    return {
      valueText: "",
      currentValue: this.value,
      showPicker: false,
      oldValue: null,
    };
  },
  watch: {
    value(val, old) {
      this.handleValue(val);
    },
  },
  mounted() {
    // this.handleValue(this.value)
  },
  methods: {
    handleValue(value) {
      if (value) {
        if (
          Object.prototype.toString.call(value) === "[object Object]" &&
          this.valueKey
        ) {
          this.valueText = value[this.valueKey];
        } else {
          this.valueText = value;
        }
        this.currentValue = value;
      }
    },
    getSelectedIndex(key, columns) {
      if (!columns) return 0;
      let index = null;
      if (this.valueKey) {
        for (let i = 0; i < columns.length; i++) {
          if (key === columns[i][this.valueKey]) {
            index = i;
            break;
          }
        }
      } else {
        index = columns.findIndex((item) => item === key);
      }
      return index;
    },
    onConfirm(value, index) {
      this.showPicker = false;
      this.handleValue(value);
      this.$emit("oninput", this.currentValue);
    },
    onChange(picker, value, index) {
      this.handleValue(value);
    },
    onCancel(val) {
      this.showPicker = false;
      console.log(this.oldValue, "关闭了");
      this.handleValue(this.oldValue);
      this.$emit("closeinput", val);
    },
    onOpen() {
      if (this.value) {
        this.oldValue = this.value;
        this.defaultIndex = this.getSelectedIndex(this.valueText, this.columns);
      }
    },
  },
};
</script>

<style scoped>
</style>
