<template>
  <el-row class="dc-container">
    <el-dialog
      v-on:close="onDialogClose"
      v-loading="loading"
      fullscreen
      title="报表设计器"
      :visible.sync="dialogVisible"
      :close-on-click-modal="false"
      class="dc-el-dialog_ElDialog"
    >
      <iframe id="ureport" :src="src" frameborder="no" style="width: 100%;height: 100%" scrolling="auto" />
    </el-dialog>
  </el-row>

</template>
<script>
import { validatenull } from '@/utils/validate'
import { getLocalToken } from '@/utils/auth'
import Cookies from 'js-cookie'
import request from "../../../utils/request";
import DcMain from '@/views/components/dcMain'
export default {
  extends: DcMain,
  name: "ureportDesOrView",
  data() {
    return {
      src: '',
      baseURL: process.env.BASE_API,
      loading: true,
      uReportEntity: {},
      dialogVisible: false
    };
  },
  methods: {
    onDialogClose() {
      this.src = ''
      this.dialogVisible = false
      this.$emit('ureport-finished')
    },
  },
  mounted:function (){
    const that = this;
    window.onresize = function temp() {
      that.height = document.documentElement.clientHeight - 94.5 + "px;";
    }
    this.$nextTick(() => {
      this.$on('openEditUreportDialog', function (row) {
        const name = 'mysql-provider-' + row.name
        this.src = this.baseURL + '/ureport/designer?_u=' + name
        this.dialogVisible = true
      })
      this.$on('openViewUreportDialog', function (data, row) {
        const status = data.status.value
        const monthYear = data.globalPeriods.intyearmonth
        const name = 'mysql-provider-' + row.name
        const companyName = row.company ? row.name : ''
        this.src = this.baseURL + '/ureport/preview?_u=' + name + '&status=' + status + '&monthYear=' + monthYear + '&companyName=' + companyName
        this.dialogVisible = true
      })
    })

  }
}
</script>

<style scoped>

</style>
