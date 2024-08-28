<template>
  <div>
    <div class="tabs-main">
      <div
        v-for="(item, index) in tabs"
        :key="index"
        :class="active == item[keyValue['key']] ? 'tabs-main-item-active' : ''"
        @click="onTabClick(item)"
        class="tabs-main-item dis-in-block"
      >
        {{ item[keyValue["value"]] }}
      </div>
    </div>
  </div>
</template>

<script>
export default {
  model: {
    prop: "activeName",
    event: "change"
  },
  props: {
    activeName: {
      type: String,
      default: ""
    },
    tabs: {
      type: Array,
      default() {
        return [
          {
            code: "name",
            label: "张三"
          }
        ];
      }
    },
    keyValue: {
      type: Object,
      default() {
        return {
          key: "code",
          value: "label"
        };
      }
    }
  },
  watch: {
    activeName(v) {
      this.active = v;
    }
  },
  mounted(){
  },
  data() {
    return {
      active: this.activeName
    };
  },
  methods: {
    onTabClick(item) {
      this.active = item[this.keyValue["key"]];
      this.$emit("change", this.active);
      this.$emit("onTabClick", item);
    }
  }
};
</script>
<style lang="scss" scoped>
.tabs-main {
  &-item {
    padding: 6px 18px;
    border-radius: 6px;
    margin-bottom: 10px;
    margin-right: 10px;
    transition: 0.3s;
    cursor: pointer;
  }
  &-item-active {
    background-color: #018cb7;
    color: #eee;
    box-shadow: 0px 2px 6px 0px #045973a6;
    transition: 0.3s;
  }
  &-item:hover {
    transition: 0.3s;
    color: #34a3c5;
  }
}
</style>
