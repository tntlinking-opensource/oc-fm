<template>
  <el-row class="dc-container" v-loading="loading">
    <history ref="history" :bussObject="curentRow"></history>
    <edit-form
      ref="editForm"
      v-on:save-finished="getList"
      v-on:before-load="setLoad"
      v-on:after-load="resetLoad"
      :permission="permission"
    ></edit-form>
    <el-form
      ref="queryForm"
      :model="queryFormData"
      label-width="100px"
      v-if="!isQueryConditionPanel"
      class="dc-el-form_ElQueryForm"
    >
      <el-row>
        <el-col :span="6">
          <el-form-item prop="uqcompanyid.id" label="公司" class="dc-el-form-item_CascaderInput">
            <el-cascader
              ref="cascader152"
              disabled
              :options="uqcompanyidOptions"
              v-model="queryFormData.uqcompanyid.id"
              :style="{ width: '100%' }"
              placeholder="请选择公司"
              :props="{ label: 'name', value: 'id', children: 'children', checkStrictly: true, emitPath: false }"
              clearable
              filterable
              separator="/"
              class="dc-el-cascader_CascaderInput"
            ></el-cascader>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item prop="uqaccountid.uqaccountid" label="会计科目" class="dc-el-form-item_SelectInput">
            <el-select
              v-model="queryFormData.uqaccountid"
              :style="{ width: '70%' }"
              placeholder="请选择会计科目"
              clearable
              value-key="uqaccountid"
              filterable
              v-on:change="onChangeAccount">
              <el-option v-for="(item, index) in uqaccountidOptions"
                         :key="index"
                         :label="item.varaccountcode
                                  ? '[' + item.varaccountcode + ']' + item.varaccountname: item.varaccountname"
                         :value="item"></el-option>
            </el-select>
            <el-button type="info" plain v-on:click="$refs.accountQuery.open()" icon="el-icon-search"></el-button>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="uqledgetype.uqledgetypeid" label="分户类别" class="dc-el-form-item_SelectInput">
            <el-select
              v-model="queryFormData.uqledgetype"
              :style="{ width: '100%' }"
              placeholder="请选择分户类别"
              value-key="uqledgetypeid"
              filterable
              v-on:change="onSearch"
              class="dc-el-select_SelectInput"
            >
              <el-option v-for="(item, index) in uqledgetypeOptions"
                         :key="index"
                         :label="item.uqledgetypeid.varledgetypename"
                         :value="item.uqledgetypeid"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <AccountQuery ref="accountQuery" @onGetData="getCheckData"></AccountQuery>
    </el-form>
    <el-row gutter="0" type="flex" justify="end" align="top" class="dc-el-row_ElRow">
      <el-tooltip v-show="permission.add" disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-upload2" v-on:click="checkFile" class="dc-el-button_Button">导入</el-button>
      </el-tooltip>
      <el-tooltip v-show="permission.export" disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-download" v-on:click="exportCompanyLedgerPeriodsList" class="dc-el-button_Button">导出</el-button>
      </el-tooltip>
      <el-tooltip v-show="permission.export" disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-download" v-on:click="exportTemplate('initledgeperiodImportModel')" class="dc-el-button_Button">模板下载</el-button>
      </el-tooltip>
<!--      <el-tooltip v-show="permission.add" disabled class="dc-el-tooltip_Button">-->
<!--        <el-button type="primary" icon="el-icon-plus" v-on:click="onCreate">添加</el-button>-->
<!--      </el-tooltip>-->
    </el-row>
    <ux-grid
      column-key
      ref="table"
      :data="tableData"
      border
      v-on:sort-change="onSortChange"
      v-on:header-dragend="onWidthChange($refs.table)"
      :tree-config="{ children: '', indent: 20, accordion: false, line: false, showIcon: true, iconOpen: '', iconClose: '' }"
      class="dc-ux-grid_QueryTable"
    >
      <ux-table-column
        :field="tableColumns['138'].field"
        :title="tableColumns['138'].title"
        :width="tableColumns['138'].width"
        :visible="tableColumns['138'].visible"
        :params="{ sortId: '138', summary: false }"
        tree-node
        resizable
        min-width="120px"
        align="left"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['137'].field"
        :title="tableColumns['137'].title"
        :width="tableColumns['137'].width"
        :visible="tableColumns['137'].visible"
        :params="{ sortId: '137', summary: false }"
        tree-node
        resizable
        min-width="120px"
        align="left"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['139'].field"
        :title="tableColumns['139'].title"
        :width="tableColumns['139'].width"
        :visible="tableColumns['139'].visible"
        :params="{ sortId: '139', summary: false }"
        tree-node
        resizable
        min-width="100px"
        align="right"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['140'].field"
        :title="tableColumns['140'].title"
        :width="tableColumns['140'].width"
        :visible="tableColumns['140'].visible"
        :params="{ sortId: '140', summary: false }"
        tree-node
        resizable
        min-width="100px"
        align="right"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        title="操作"
        :params="{ sortId: '114' }"
        tree-node
        resizable
        width="140px"
        min-width="140px"
        fixed="right"
        align="left"
        header-align="center"
        class="dc-ux-table-column_ElTableOptColumn"
      >
        <template v-slot:header="scope">
          <span>操作</span>
          <el-popover ref="popover" width="10" trigger="click" placement="bottom" offset="10" class="dc-el-popover_ElPopover">
            <view-columns-select
              v-model="tableColumns"
              ref="viewColumnsSelect"
              v-on:save-column-view="saveColumn($event, 'table')"
              v-on:show-all-column="showAllColumn($event, 'table')"
              v-on:show-default-column="showDefaultColumn('table')"
              class="dc-view-columns-select_ViewColumnsSelect"
            ></view-columns-select>
            <template slot="reference">
              <OperationIcon type="primary" content="自定义列" placement="top" icon-name="el-icon-tickets"></OperationIcon>
            </template>
          </el-popover>
          <OperationIcon
            v-on:click="onExport(tableData)"
            type="primary"
            v-show="permission.export"
            content="导出excel文件"
            placement="top"
            icon-name="el-icon-download"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
        </template>
        <template slot-scope="scope">
          <OperationIcon
            v-on:click="onEdit(scope)"
            type="primary"
            v-show="permission.edit"
            content="编辑"
            placement="top"
            icon-name="el-icon-edit"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            v-on:click="onShowHistory(scope)"
            type="info"
            v-show="permission.view"
            content="历史记录"
            placement="top"
            icon-name="el-icon-info"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
        </template>
      </ux-table-column>
    </ux-grid>
    <el-pagination
      :total="tableDataTotal"
      :page-size="search.limit"
      background
      :current-page="tableDataPage"
      :page-sizes="[5, 10, 20, 30, 40, 50, 100, tableDataTotal]"
      layout="total, sizes, prev, pager, next, jumper"
      v-on:size-change="onSizeChange"
      v-on:current-change="onCurrentChange"
      class="dc-el-pagination_ElPagination"
    ></el-pagination>
    <div>
      <input type="file" id="fileinput" style="display: none" @change="uploadExcel"
             accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel "/>
    </div>
  </el-row>
</template>
<script>
import { validatenull } from '@/utils/validate'

import { exportCompanyLedgerPeriods, importCompanyLedgerPeriods } from '@/api/vouchermanager/companyLedgerPeriods'

import { listResourcePermission } from '@/api/admin/common/permission'

/** 根据用户界面配置import组件 开始 */
import History from '@/views/components/history'
import EditForm from './form'
import QueryTag from '@/views/components/queryTag'
import QueryConditionPanel from '@/views/components/queryConditionPanel'
import AccountQuery from "@/views/components/AccountingAccountQuery";
import { treeCompany } from '@/api/org/company.js'
import { listTglAccountsAll } from '@/api/vouchermanager/InitLedgePeriod/tglAccounts.js'
import { listAccountLedgetypeAll } from '@/api/vouchermanager/accountLedgetype'
import { listCompanyLedgerByLedgety } from '@/api/vouchermanager/tglLedger.js'
import ViewColumnsSelect from '@/views/components/DcViewColumnsSelect'
/** 根据用户界面配置import组件 结束 */
import metadata from './metadata'
import ExportExcelButton from '@/components/ExportExcelButton'
import DcMain from '@/views/components/dcMain'
import OperationIcon from '@/components/OperationIcon'
import moment from "moment";
export default {
  extends: DcMain,
  components: {
    /** 根据用户界面配置组件 开始 */
    History,
    EditForm,
    QueryTag,
    QueryConditionPanel,
    ViewColumnsSelect,
    /** 根据用户界面配置组件 结束 */
    ExportExcelButton,
    OperationIcon,
    AccountQuery
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
      metadata,
      /** 根据用户界面配置生成data数据 开始 */
      queryFormData: {
        // 公司
        uqcompanyid: currentUser.company,
        uqaccountid: {
          uqaccountid: null,
          varaccountcode: null,
          varaccountname: null
        },
        uqledgetype: {
          uqledgetypeid: null,
          varledgetypecode: null,
          varledgetypename: null
        }
      },
      tableData: [],
      // 查询表格列头
      tableColumns: {
        138: {
          title: '分户项目编码',
          field: 'varledgecode',
          visible: true,
          order: 6
        },
        137: {
          title: '分户项目名称',
          field: 'varledgename',
          visible: true,
          order: 7
        },
        139: {
          title: '年初借方金额',
          field: 'companyLedgerPeriods.mnydebitperiodall',
          visible: true,
          order: 8
        },
        140: {
          title: '年初贷方金额',
          field: 'companyLedgerPeriods.mnycreditperiodall',
          visible: true,
          order: 9
        },
        114: {
          title: '操作',
          width: '140px',
          isShow: true,
          visible: true,
          order: 10
        }
      },
      // 分页属性
      tableDataPage: 1,
      tableDataTotal: 0,
      // 选项变量
      // 公司选项
      uqcompanyidOptions: [],
      // 会计科目选项
      uqaccountidOptions: [],
      // 分户类别选项
      uqledgetypeOptions: [],
      /** 根据用户界面配置生成data数据 结束 */
      version: 2,
      search: {
        // 查询条件   业务表设置的筛选条件
        params: [],

        offset: 0,
        limit: 10,
        columnName: '', // 排序字段名
        order: '' // 排序
      },
      searchList: [],
      tableId: '1457254743943431111',
      schemeId: '1457254743943431109'
    }
  },
  computed: {},
  methods: {
    getList() {
      if (validatenull(this.queryFormData.uqaccountid.uqaccountid) || validatenull(this.queryFormData.uqledgetype.uqledgetypeid)) {
        this.showMessage({type: "waning", msg: "请选择科目和分户类别"})
        return false
      }
      /* 查询参数 和数据权限 */
      this.setLoad()
      this.search.params = []
      this.search.params.push({
        columnName: 'UQCOMPANYID',
        queryType: '=',
        value: !validatenull(this.queryFormData.uqcompanyid.id) ? this.queryFormData.uqcompanyid.id : null
      })
      this.search.params.push({
        columnName: 'UQACCOUNTID',
        queryType: '=',
        value: !validatenull(this.queryFormData.uqaccountid.uqaccountid) ? this.queryFormData.uqaccountid.uqaccountid : null
      })
      this.search.params.push({
        columnName: 'UQLEDGETYPEID',
        queryType: '=',
        value: !validatenull(this.queryFormData.uqledgetype.uqledgetypeid) ? this.queryFormData.uqledgetype.uqledgetypeid : null
      })
      // 数据权限: 分户期初余额设置tgl_company_ledger_periods
      this.pushDataPermissions(this.search.params, this.$route.meta.routerId, this.tableId)
      listCompanyLedgerByLedgety(this.search)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.tableDataTotal = responseData.data.total
            this.tableData = responseData.data.rows ? responseData.data.rows : []
          } else {
            this.showMessage(responseData)
          }
          this.resetLoad()
        })
        .catch((error) => {
          this.outputError(error)
        })
    },
    onSearch() {
      if (this.isQueryConditionPanel) {
        this.search.offset = 0
        this.tableDataPage = 1
        this.getList()
      } else {
        this.$refs['queryForm'].validate((valid) => {
          if (valid) {
            this.search.offset = 0
            this.tableDataPage = 1
            this.getList()
          } else {
            return false
          }
        })
      }
    },
    onSizeChange(val) {
      this.tableDataPage = 1
      this.search.limit = val
      this.search.offset = (this.tableDataPage - 1) * val
      this.getList()
    },
    onCurrentChange(val) {
      this.search.offset = (val - 1) * this.search.limit
      this.tableDataPage = val
      this.getList()
    },
    async pageInit() {
      this.setLoad()
      try {
        this.initOptions(this.queryModel)
        this.search.params = []
        // 数据权限: 分户期初余额设置tgl_company_ledger_periods
        this.pushDataPermissions(this.search.params, this.$route.meta.routerId, this.tableId)
        let [listPermissionRespData] = await Promise.all([
          listResourcePermission(this.$route.meta.routerId)
        ])
        if (listPermissionRespData.code == 100) {
          this.permission.view = listPermissionRespData.data.find((item) => {
            return item.permission === 'companyLedgerPeriods:read'
          })
          this.permission.export = listPermissionRespData.data.find((item) => {
            return item.permission === 'companyLedgerPeriods:export'
          })

          this.permission.add = listPermissionRespData.data.find((item) => {
            return item.permission === 'companyLedgerPeriods:create'
          })
          this.permission.edit = listPermissionRespData.data.find((item) => {
            return item.permission === 'companyLedgerPeriods:update'
          })
          this.permission.remove = listPermissionRespData.data.find((item) => {
            return item.permission === 'companyLedgerPeriods:delete'
          })
        } else {
          this.showMessage(listPermissionRespData)
        }
        this.resetLoad()
      } catch (error) {
        this.outputError(error)
      }
    },
    onEdit(scope) {
      this.$set(scope.row, 'uqaccountid', this.queryFormData.uqaccountid)
      this.$refs.editForm.$emit('openEditDialog', scope.row)
    },
    onSortChange(orderby) {
      if (validatenull(orderby.prop)) {
        this.search.columnName = ''
        this.search.order = ''
      } else {
        this.search.columnName = orderby.prop
        this.search.order = orderby.order
      }
      this.getList()
    },
    listUqcompanyidOptions() {
      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [])
      // 数据权限: 公司org_company
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1457254743943431008')
      treeCompany(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.uqcompanyidOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },
    getCheckData(data) {
      if (!validatenull(data.tableData)) {
        this.queryFormData.uqaccountid = data.tableData;
      }
    },
    listUqaccountidOptions() {
      // 获取科目管理列表
      let search_List = {
        // params: [{ columnName: "INTFLAG", queryType: "=", value: "2" }]
        params: [],
        columnName: 'varaccountfullcode',
        orderby: 'asc'
      };
      // filter条件
      search_List.params.push.apply(search_List.params, []);
      // 数据权限: 会计科目表tgl_accounts
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, "1457254743943430348");
      listTglAccountsAll(search_List).then(responseData => {
        if (responseData.code == 100) {
          this.uqaccountidOptions = responseData.data;
        } else {
          this.showMessage(responseData);
        }
      });
    },

    listUqledgeTypeOptions() {
      if (validatenull(this.queryFormData.uqaccountid) || validatenull(this.queryFormData.uqaccountid.varaccountcode)) {
        return
      }
      let search_List = {
        params: [{
          columnName: 'UQACCOUNTID',
          queryType: '=',
          value: this.queryFormData.uqaccountid.uqaccountid
        }]
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [])
      debugger
      console.log(111111)
      // 数据权限: 分户明细表tgl_ledger
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1457254743943430950')
      listAccountLedgetypeAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.uqledgetypeOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },
    exportCompanyLedgerPeriodsList() {
      this.setLoad()
      /* 查询参数 和数据权限 */
      let searcharams =  {
        params: []
      }
      searcharams.params.push({
        columnName: 'UQCOMPANYID',
        queryType: '=',
        value: !validatenull(this.queryFormData.uqcompanyid.id) ? this.queryFormData.uqcompanyid.id : null
      })
      searcharams.params.push({
        columnName: 'UQACCOUNTID',
        queryType: '=',
        value: !validatenull(this.queryFormData.uqaccountid.uqaccountid) ? this.queryFormData.uqaccountid.uqaccountid : null
      })
      searcharams.params.push({
        columnName: 'UQLEDGETYPEID',
        queryType: '=',
        value: !validatenull(this.queryFormData.uqledgetype.uqledgetypeid) ? this.queryFormData.uqledgetype.uqledgetypeid : null
      })
      exportCompanyLedgerPeriods(searcharams).then((responseData) => {
        let date = moment().format("YYYY-MM-DD");
        const blob = new Blob([responseData]);
        const url = window.URL.createObjectURL(blob);
        let dom = document.createElement("a");
        dom.style.display = "none";
        dom.href = url;
        dom.setAttribute("download", '分户期初余额设置_' + date +  '.xls');
        document.body.appendChild(dom);
        dom.click();
        this.resetLoad()
      }).catch((error) => {
        this.outputError(error)
      })
    },
    checkFile() {
      document.querySelector('#fileinput').click()
    },
    uploadExcel(evt) {
      const files = evt.target.files
      if (files == null || files.length == 0) {
        alert('No files wait for import')
        return
      }
      let formData = new FormData()
      formData.append('file', files[0])
      this.setLoad()
      importCompanyLedgerPeriods(formData).then((responseData) => {
        if (responseData.code == 100) {
          this.pageInit()
          this.showMessage({
            type: 'success',
            msg: '导入成功'
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
      this.listUqcompanyidOptions()

      this.listUqaccountidOptions()
    }
  },
  watch: {
    'queryFormData.uqaccountid' :{
      handler(newVal, oldVal){
        if(newVal != oldVal){
          this.queryFormData.uqledgetype = {
            uqledgetypeid: null,
            varledgetypecode: null,
            varledgetypename: null
          }
          this.tableDataTotal = 0
          this.tableData = []
          this.uqledgetypeOptions = []
          this.listUqledgeTypeOptions()
        }
      },
      deep: true
    }
  },
  mounted() {
    this.pageInit()

    this.columnDrop(this.$refs.table)
  }
}
</script>
<style lang="scss" scoped>

</style>
