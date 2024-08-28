<template>
  <div class="mytask">
    <div class="page-container">
      <div class="headerFiex">
        <van-search
          v-model.trim="queryModel.description"
          shape="round"
          show-action
          placeholder="请输入搜索关键词"
          @clear="onSearch(true)"
          @search="onSearch(true)"
        >
          <template #action>
            <div @click="onSearch(true)">查询</div>
          </template>
        </van-search>
      </div>
    </div>
  </div>
</template>

<script>
import WfBaseUI from '@/views/wf/common/wfBaseUI'

export default {
  name: 'mytask',
  extends: WfBaseUI,
  data () {
    return {
      queryModel: {
        description: ''
      }
    }
  },
  created () {

  },
  mounted () {
  },
  methods: {
    onSearch() {

    }
  }
}
</script>

<style lang="scss" scoped>
</style>
