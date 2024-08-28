<template>
<div class="page-container report">
  <div class="page-header">
    <van-nav-bar title="我的报表" left-arrow @click-left="onNavLeft" />
    <van-tabs v-model="reportType" @change="onReportTypeChange">
      <van-tab title="我的报表" name="FROM_SHARE"></van-tab>
      <van-tab title="我的收藏" name="MY_STORE"></van-tab>
    </van-tabs>
  </div>
  <div class="page-content">
    <Loading :show="loading" color="#1989fa"/>
    <van-pull-refresh v-model="refreshing" @refresh="onRefresh">
      <van-list
        v-model="listLoading"
        :finished="listFinished"
        finished-text="没有更多了"
        @load="onLoad"
      >
        <van-cell
          v-for="(item, index) in reportList"
          :key="index"
          :title="item.name"
          is-link
          icon="notes-o"
          @click="onListClick(item)" />
      </van-list>
    </van-pull-refresh>
  </div>
  <reportView ref="reportView"></reportView>
</div>
</template>

<script>
import BaseUI from '@/views/components/baseUI'
import { loadTree } from '@/api/report/panel/share'
import { deleteEnshrine, enshrineList } from '@/api/report/panel/enshrine'
import reportView from './reportView'
import bus from '@/views/layout/report/utils/bus'
export default {
  extends: BaseUI,
  name: 'report',
  components: { reportView },
  data() {
    return {
      reportType: 'FROM_SHARE',
      refreshing: false,
      listLoading: false,
      listFinished: true,

      reportList: [],
      reportShow: false
    }
  },
  computed: {
    panelInfo() {
      return this.panelInfo
    }
  },
  watch: {
    reportType(newVal, oldVal) {
      this.clear()
    }
  },
  methods: {
    pageInit() {
      this.getReportList()
    },
    onLoad() {

    },
    onRefresh() {

    },
    onListClick(row) {
      this.reportShow = true
      let reportData = {
        row,
        type: this.reportType
      }
      this.$refs.reportView.$emit('openReportView', reportData)
    },
    onReportTypeChange(val) {
      val === 'FROM_SHARE' ? this.getReportList() : this.getCollectList()
    },
    getCollectList() {
      this.setLoad()
      enshrineList({}).then(responseData => {
        if(responseData.success) {
          this.reportList = responseData.data
        } else {
          this.showMessage(responseData)
        }
        this.resetLoad()
      }).catch(error => {
        this.outputError(error)
      })
    },
    getReportList() {
      this.setLoad()
      this.reportList = []
      loadTree({}).then(responseData => {
        if(responseData.success) {
          let list = responseData.data
          list.forEach((item, index) => {
            this.reportList = [...this.reportList, ...item.children]
          })
        } else {
          this.showMessage(responseData)
        }
        this.resetLoad()
      }).catch(error => {
        this.outputError(error)
      })
    },
    onNavLeft() {
      this.$router.push({path: '/'})
    },
    clear() {
      // 清空
      bus.$emit('setPanelInfo', {
        id: null,
        name: '',
        preStyle: null
      })
    }
  },
  created() {

  },
  mounted() {
    this.pageInit()
  }
}
</script>

<style scoped lang="scss">
.report {
  padding-top: 92px;
}
</style>
