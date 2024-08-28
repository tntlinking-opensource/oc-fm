<template>
  <el-row class="dc-container" v-loading="loading">
    <span>产品开关配置</span>
    <el-divider></el-divider>
    <el-col :span="8">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>是否出纳</span>
          <el-switch v-show="permission.edit" style="float: right; padding: 3px 0"
                     v-model="propValueEntity.status"
                     active-value="1"
                     inactive-value="0"
                     active-color="#13ce66"
                     v-on:change="onChangeStatus"></el-switch>
        </div>
        <div class="text item">
          {{'当开关开启时，需要出纳的凭证将自动出纳。当开关关闭时，需要出纳的科目需要手动出纳。'}}
        </div>
      </el-card>
    </el-col>
  </el-row>
</template>
<script>
import { validatenull } from '@/utils/validate'

import { savePropValue, getPropValueByCompany } from '@/api/tgl/prop/propValue'

import { listResourcePermission } from '@/api/admin/common/permission'

import ExportExcelButton from '@/components/ExportExcelButton'
import DcMain from '@/views/components/dcMain'
import OperationIcon from '@/components/OperationIcon'
export default {
  extends: DcMain,
  components: {
    ExportExcelButton,
    OperationIcon
  },
  data() {
    return {
      permission: {
        view: false,
        add: false,
        edit: false,
        remove: false,
        export: false
      },
      propValueEntity: {},
      version: 4,
      searchList: [],
      tableId: '1457254743943431027',
      schemeId: '1457254743943431025'
    }
  },
  computed: {},
  methods: {
    initEditData(This) {
      return {
        company: currentUser.company,
        name: '自动出纳开关', // 名称
        status: '0', // 是否自动出纳（0：否，1：是）
        remarks: '' // 备注信息
      }
    },
    onChangeStatus() {
      this.setLoad()
      savePropValue(this.propValueEntity).then((responseData) => {
        if (responseData.code == 100) {
          this.propValueEntity.id = responseData.data
          this.showMessage({
            type: 'success',
            msg: '操作成功'
          })
        } else {
          this.showMessage(responseData)
        }
        this.resetLoad()
      }).catch((error) => {
        this.outputError(error)
      })
    },
    async pageInit() {
      this.setLoad()
      this.initOptions()
      // 数据权限: 自动出纳配置tgl_prop_value
      this.pushDataPermissions([], this.$route.meta.routerId, this.tableId)
      listResourcePermission(this.$route.meta.routerId).then((responseData) => {
        if (responseData.code == 100) {
          this.permission.view = responseData.data.find((item) => {
            return item.permission === 'propValue:read'
          })
          this.permission.edit = responseData.data.find((item) => {
            return item.permission === 'propValue:update'
          })
        } else {
          this.showMessage(responseData)
        }
        this.resetLoad()
      }).catch((error) => {
        this.outputError(error)
      })
    },
    // 初始化自定义类型选择框选项变量
    initOptions(This) {
      // 数据权限: 自动出纳配置tgl_prop_value
      let companyId = currentUser.company.id
      this.pushDataPermissions([], this.$route.meta.routerId, this.tableId)
      getPropValueByCompany(companyId).then((responseData) => {
        if (responseData.code == 100) {
          if (validatenull(responseData.data)) {
            this.propValueEntity = this.initEditData()
          } else {
            this.propValueEntity = responseData.data
          }
        } else {
          this.showMessage(responseData)
        }
        this.resetLoad()
      }).catch((error) => {
        this.outputError(error)
      })
    }
  },
  watch: {},
  mounted() {
    this.pageInit()
  }
}
</script>
<style lang="scss" scoped>
  .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

  .box-card {
    width: 480px;
  }
</style>
