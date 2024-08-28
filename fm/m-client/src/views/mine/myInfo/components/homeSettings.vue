<template>
  <div class="homeSettings">
    <van-icon name="scan" size="18" />
    <van-icon name="setting-o" size="18" @click="$router.push({name: 'settings'})" />
  </div>
</template>


<script>
export default {
  name: 'homeSettings',
  components: {
  },
  props: {
    user: {
      type: Object
    }
  },
  data () {
    return {
    }
  }, 
  mounted() {
  },
  methods: {
  }
}
</script>

<style lang="scss" scoped>
/deep/ .van-icon {
  margin: 0 6px;
}
</style>
