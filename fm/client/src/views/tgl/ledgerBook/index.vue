<template>
  <el-row v-loading="loading" :style="'height:'+ height" class="dc-container">
    <query-form ref="queryForm"
                     v-on:choose-finished="getQueryData"
                    v-on:close-finished="getCloseQueryData"
                     v-on:before-load="setLoad"
                     v-on:after-load="resetLoad"
    ></query-form>
    <el-row gutter="0" type="flex" justify="end" align="top" class="dc-el-row_ElRow">
      <el-tooltip disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-search" v-on:click="onSearch">查询</el-button>
      </el-tooltip>
      <el-tooltip disabled class="dc-el-tooltip_Button" v-show="reportVisible">
        <el-button type="primary" icon="el-icon-refresh" v-on:click="getQueryData(queryFormData)">刷新</el-button>
      </el-tooltip>
    </el-row>
    <iframe v-if="reportVisible" :src="src" frameborder="no" style="width: 100%;height: 100%" scrolling="auto" />
  </el-row>
</template>
<script>
import { validatenull } from '@/utils/validate'
import queryForm from './queryForm'
import DcMain from '@/views/components/dcMain'
import { getTableName } from '@/api/tgl/ledger/ledgerBook.js'
import moment from "moment";
export default {
  extends: DcMain,
  name: "ledgerBook",
  components: {
    queryForm
  },
  data() {
    return {
      src: '',
      baseURL: process.env.BASE_API,
      height: document.documentElement.clientHeight - 94.5 + "px;",
      loading: true,
      reportVisible: false,
      queryFormData: this.initQueryData(),
    };
  },
  methods: {
    onSearch() {
      this.$refs.queryForm.$emit('openChooseDialog', this.queryFormData)
    },
    // 初始化自定义类型选择框选项变量
    initOptions(This) {
      this.$nextTick(() => {
        const that = this;
        this.resetLoad()
        this.$refs.queryForm.$emit('openChooseDialog', this.queryFormData)
        window.onresize = function temp() {
          that.height = document.documentElement.clientHeight - 94.5 + "px;";
        }
        this.reportVisible = false
      })
    },
    initQueryData() {
      return {
        ledgerde: '', // 分户ID
        ledgerTypeID: '', // 分户类型ID
        ledgetypeName: '', // 分户名称，回显
        beginaccount: '', // 起始科目
        endaccount: '', // 结束科目
        accountlevel: '99', // 级次
        accountLevelName: '末级科目', // 级次名称
        beginyearmonth: null, // 起始会计期
        endyearmonth: null, // 结束会计期
        intstatus: '0', // 凭证状态（0：全部，1：未记账，2：已记账）
        isallzerodata: '1', // 无年初数、发生数科目不显示（1：是，0：否）
        uqcompanyid: currentUser.company.id
      }
    },
    getQueryData(editForm) {
      this.queryFormData = {...this.initQueryData(), ...editForm}
      this.src = ''
      this.setLoad()
      getTableName(this.queryFormData).then((responseData) => {
        if (responseData.code == 100) {
          const tableName = responseData.data
          const beginyearmonth = this.queryFormData.beginyearmonth
          const endyearmonth = this.queryFormData.endyearmonth
          const accountLevelName = this.queryFormData.accountLevelName
          const ledgetypeName = this.queryFormData.ledgetypeName
          const date = moment().startOf('day').format('YYYY-MM-DD')
          const name = 'mysql-provider-ledgerBook.ureport.xml' // 报表名，固定
          const isseveral = this.queryFormData.isallzerodata
          this.src = this.baseURL + '/ureport/preview?_u=' + name + '&_i=1&_r=1&tableName=' + tableName + '&beginyearmonth=' + beginyearmonth + '&endyearmonth=' + endyearmonth + '&accountLevelName=' + accountLevelName + '&date=' + date + '&ledgetypeName=' + ledgetypeName.replaceAll("[","(").replaceAll("]",")") + '&isseveral=' + isseveral+ '&companyId=' + currentUser.company.id
          this.reportVisible = true
        } else {
          this.showMessage(responseData)
        }
        this.resetLoad()
      }).catch((error) => {
        this.outputError(error)
      })
    },
    getCloseQueryData(editForm) { // 查询form回调
      this.queryFormData = {...this.initQueryData(), ...editForm}
    },
  },
  watch: {

  },
  mounted: function() {
    this.initOptions()
  }
};
</script>

<style scoped>

</style>
