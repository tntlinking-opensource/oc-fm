<template>
  <el-row v-loading="loading" :style="'height:'+ height" class="dc-container">
    <periodQuery ref="periodQuery"
                     v-on:choose-finished="getQueryPeriods"
                     v-on:before-load="setLoad"
                     v-on:after-load="resetLoad"
    ></periodQuery>
    <el-row gutter="0" type="flex" justify="end" align="top" class="dc-el-row_ElRow">
      <el-tooltip disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-search" v-on:click="onSearch">查询</el-button>
      </el-tooltip>
    </el-row>
    <iframe v-if="reportVisible" :src="src" frameborder="no" style="width: 100%;height: 100%" scrolling="auto" />
  </el-row>
</template>
<script>
import { validatenull } from '@/utils/validate'
import { listUreportFileAll } from '@/api/ureport/ureportFile.js'
import periodQuery from '@/views/ureport/ureportFile/periodQuery'
import DcMain from '@/views/components/dcMain'
export default {
  extends: DcMain,
  name: "lrReport",
  components: {
    periodQuery
  },
  data() {
    return {
      // window.open(this.baseURL + '/ureport/preview?_u=' + name + '&status=' + status + '&monthYear=' + monthYear)
      src: '',
      baseURL: process.env.BASE_API,
      height: document.documentElement.clientHeight - 94.5 + "px;",
      loading: true,
      uReportEntity: {},
      reportVisible: false
    };
  },
  methods: {
    onSearch() {
      this.$refs.periodQuery.$emit('openChooseDialog', this.uReportEntity.id)
    },
    // 初始化自定义类型选择框选项变量
    initOptions(This) {
      const that = this;
      window.onresize = function temp() {
        that.height = document.documentElement.clientHeight - 94.5 + "px;";
      }
      this.reportVisible = false
      // 根据type:类型、status：状态，查询报表信息
      this.setLoad()
      /* 查询参数 和数据权限 */
      let ureportSearch = {
        params: [
          {columnName: 'type', queryType: '=', value: this.$route.params.typeParam}, // 1：利润表2：资产负债表3：现金流量表
          {columnName: 'status', queryType: '=', value: 1} // 状态，启用
        ],
        order: ''
      }
      this.uReportEntity = {}
        listUreportFileAll(ureportSearch).then((responseData) => {
        if (responseData.code == 100) {
          let uReportList = responseData.data
          if (validatenull(uReportList)) { // 为空提示，不显示报表
            this.$alert('没有匹配的报表，请前往【报表设计器】菜单，进行配置', '提示', {
              confirmButtonText: '确定',
              type: 'info'
            })
          } else {
            this.uReportEntity = uReportList[0]
            this.$refs.periodQuery.$emit('openChooseDialog', this.uReportEntity.id)
          }
        } else {
          this.showMessage(responseData)
        }
        this.resetLoad()
      }).catch((error) => {
        this.outputError(error)
      })
    },
    getQueryPeriods(data) {
      this.src = ''
      if (validatenull(this.uReportEntity)) { // 为空提示，不显示报表
        this.$alert('没有匹配的报表，请前往【报表设计器】菜单，进行配置', '提示', {
          confirmButtonText: '确定',
          type: 'info'
        })
        return false
      }
      const status = data.status.value
      const monthYear = data.globalPeriods.intyearmonth
      const name = 'mysql-provider-' + this.uReportEntity.name
      const companyName = this.uReportEntity.company ? this.uReportEntity.company.name : ''
      this.src = this.baseURL + '/ureport/preview?_u=' + name + '&status=' + status + '&monthYear=' + monthYear + '&companyName=' + companyName + '&companyId=' + currentUser.company.id
      this.reportVisible = true
    }
  },
  watch: {
    '$route.params.typeParam'(newVal, oldVal) {
      if (newVal && newVal != oldVal) {
        this.initOptions()
      }
    }
  },
  mounted: function() {

    this.initOptions()
  }
};
</script>

<style scoped>

</style>
