<template>
  <el-row class="dc-container" v-loading="loading">
    <query-form
      ref="queryForm"
      v-on:save-finished="getSaveQueryData"
      v-on:close-finished="getCloseQueryData"
      v-on:before-load="setLoad"
      v-on:after-load="resetLoad"
    ></query-form>
    <el-row gutter="0" align="top" class="dc-el-row_ElRow">
      <el-tooltip disabled class="dc-el-tooltip_Button">
        <el-button v-on:click="onSearch" type="primary" icon="el-icon-search">搜索</el-button>
      </el-tooltip>
      <el-tooltip v-show="permission.export && tableData.length > 0" disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-download" v-on:click="uploadTable">导出Excel</el-button>
      </el-tooltip>
      <el-tooltip disabled class="dc-el-tooltip_Button" v-show="showTable">
        <el-button type="primary" icon="el-icon-refresh" @click="getList">刷新</el-button>
      </el-tooltip>
      <el-popover style="float: right" v-show="showTable" ref="popover" width="10" trigger="hover" placement="bottom" offset="10" class="dc-el-popover_ElPopover">
        <div v-for="column in tableColumns" :key='column.field'>
          <el-checkbox v-model='column.visible' :label='column.title' @change='onColumnViewChange' style="text-align:left"></el-checkbox>
        </div>
        <template slot="reference">
          <el-button type="success">自定义列</el-button>
        </template>
      </el-popover>
    </el-row>
    <el-row v-show="showTable" style="margin-top: 10px">
      <ux-grid
        column-key
        ref="table"
        :data="tableData"
        border
        :tree-config="{ children: '', indent: 20, accordion: false, line: false, showIcon: true, iconOpen: '', iconClose: '' }"
        class="dc-ux-grid_QueryTable"
      >
        <ux-table-column
          title=""
          type="index"
          tree-node
          resizable
          width="50px"
          fixed="left"
          align="center"
          header-align="center"
          class="dc-ux-table-column_ElTableNumColumn"
        ></ux-table-column>
        <ux-table-column
          :field="tableColumns['129'].field"
          :title="tableColumns['129'].title"
          :width="tableColumns['129'].width"
          :visible="tableColumns['129'].visible"
          :params="{ sortId: '129', summary: false }"
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
          :field="tableColumns['130'].field"
          :title="tableColumns['130'].title"
          :width="tableColumns['130'].width"
          :visible="tableColumns['130'].visible"
          :params="{ sortId: '130', summary: false }"
          tree-node
          resizable
          min-width="100px"
          align="center"
          header-align="center"
          sortable
          show-overflow
          class="dc-ux-table-column_ElTableColumn"
        ></ux-table-column>
        <ux-table-column
          :field="tableColumns['131'].field"
          :title="tableColumns['131'].title"
          :width="tableColumns['131'].width"
          :visible="tableColumns['131'].visible"
          :params="{ sortId: '131', summary: false }"
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
          :field="tableColumns['132'].field"
          :title="tableColumns['132'].title"
          :width="tableColumns['132'].width"
          :visible="tableColumns['132'].visible"
          :params="{ sortId: '132', summary: false }"
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
          :field="tableColumns['133'].field"
          :title="tableColumns['133'].title"
          :width="tableColumns['133'].width"
          :visible="tableColumns['133'].visible"
          :params="{ sortId: '133', summary: false }"
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
          :field="tableColumns['134'].field"
          :title="tableColumns['134'].title"
          :width="tableColumns['134'].width"
          :visible="tableColumns['134'].visible"
          :params="{ sortId: '134', summary: false }"
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
          v-for="aging in agingList"
          :key="aging.id"
          :field="tableColumns[aging.id].field"
          :title="tableColumns[aging.id].title"
          :visible="tableColumns[aging.id].visible"
          :params="{ sortId: aging.id, summary: false }"
          tree-node
          resizable
          width="100px"
          align="right"
          header-align="center"
          sortable
          show-overflow
          class="dc-ux-table-column_ElTableColumn"
        ></ux-table-column>
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
    </el-row>
  </el-row>
</template>
<script>
import { validatenull } from '@/utils/validate'

import { listContactsBookPage, uploadContactsBook } from '@/api/tgl/aging/contactsBook'

import { listResourcePermission } from '@/api/admin/common/permission'

/** 根据用户界面配置import组件 开始 */
import QueryForm from './form'
import ViewColumnsSelect from '@/views/components/DcViewColumnsSelect'
/** 根据用户界面配置import组件 结束 */
import ExportExcelButton from '@/components/ExportExcelButton'
import DcMain from '@/views/components/dcMain'
import OperationIcon from '@/components/OperationIcon'
import moment from "moment";
export default {
  extends: DcMain,
  components: {
    /** 根据用户界面配置组件 开始 */
    QueryForm,
    ViewColumnsSelect,
    /** 根据用户界面配置组件 结束 */
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
      /** 根据用户界面配置生成data数据 开始 */
      queryFormData: this.initQueryData(),
      tableData: [],
      // 查询表格列头
      tableColumns: this.initQueryTable(),
      agingList: [], // 账龄区间表集合
      // 分页属性
      tableDataPage: 1,
      tableDataTotal: 0,
      showTable: false, // 表格默认不显示

      /** 根据用户界面配置生成data数据 结束 */
      version: 2,
      search: {
        offset: 0,
        limit: 10,
        columnName: '', // 排序字段名
        order: '' // 排序
      },
      tableId: '1457254743943433574',
      schemeId: '1457254743943433572'
    }
  },
  computed: {},
  methods: {
    uploadTable() { // 导出表格数据
      this.setLoad()
      /* 查询参数 和数据权限 */
      uploadContactsBook(this.queryFormData).then((responseData) => {
        let date = moment().format("YYYY-MM-DD");
        const blob = new Blob([responseData]);
        const url = window.URL.createObjectURL(blob);
        let dom = document.createElement("a");
        dom.style.display = "none";
        dom.href = url;
        dom.setAttribute("download", '往来账龄表_' + date +  '.xls');
        document.body.appendChild(dom);
        dom.click();
        this.resetLoad()
        this.resetLoad()
      }).catch((error) => {
        this.outputError(error)
      })
    },
    getList() {
      this.setLoad()
      /* 查询参数 和数据权限 */
      listContactsBookPage(this.queryFormData).then((responseData) => {
        if (responseData.code == 100) {
          this.tableDataTotal = responseData.data.total
          this.tableData = responseData.data.rows ? responseData.data.rows : []
          this.refreshColumn(this.$refs.table)  // 重新刷新列
          this.showTable = true
        } else {
          this.showMessage(responseData)
        }
        this.resetLoad()
      }).catch((error) => {
        this.outputError(error)
      })
    },
    getSaveQueryData(editForm, agingList) {
      this.queryFormData = {...this.initQueryData(), ...editForm}
      this.agingList = JSON.parse(JSON.stringify(agingList))
      this.tableColumns = this.initQueryTable()
      agingList.forEach((aging, index) => {
        const column = {
          title: aging.begday,
          field: 'count' + index,
          visible: true,
          order: 7 + index
        }
        this.$set(this.tableColumns, aging.id, column) // 动态增加列
      })
      this.getList()
    },
    getCloseQueryData(editForm) { // 查询form回调
      this.showTable = false // 不显示查询表格
      this.queryFormData = {...this.initQueryData(), ...editForm}
    },
    onSearch() {
      this.$refs.queryForm.$emit('openQueryDialog', this.queryFormData)
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
      listResourcePermission(this.$route.meta.routerId).then((listPermissionRespData) => {
        if (listPermissionRespData.code == 100) {
          this.permission.view = listPermissionRespData.data.find((item) => {
            return item.permission === 'aging:read'
          })
          this.permission.export = listPermissionRespData.data.find((item) => {
            return item.permission === 'aging:export'
          })
          this.permission.add = listPermissionRespData.data.find((item) => {
            return item.permission === 'aging:create'
          })
          this.permission.edit = listPermissionRespData.data.find((item) => {
            return item.permission === 'aging:update'
          })
          this.permission.remove = listPermissionRespData.data.find((item) => {
            return item.permission === 'aging:delete'
          })
          this.$refs.queryForm.$emit('openQueryDialog', this.queryFormData)
        } else {
          this.showMessage(listPermissionRespData)
        }
        this.resetLoad()
      }).catch((error) => {
        this.outputError(error)
      })
    },
    onColumnViewChange() {
      this.refreshColumn(this.$refs.table)  // 重新刷新列
    },
    initQueryData(This) {
      return {
        uqaccountid: null,
        ledgetype: null,
        ledgetypeName: null,
        byDay: '0',
        maxDay: '',
        isVoucher: '0',
        byWay: '0',
        nozore: '0',
        byDetail: '0',
        period: null,
        offset: 0,
        limit: 10,
        companyId: currentUser.company.id
      }
    },
    initQueryTable() {
      return {
        129: {
          title: '客户档案名称',
          field: 'ledgername',
          visible: true,
          order: 1
        },
        130: {
          title: '方向',
          field: 'direct',
          visible: true,
          order: 2
        },
        131: {
          title: '期初余额',
          field: 'begphase',
          visible: true,
          order: 3
        },
        132: {
          title: '本期借方',
          field: 'perioddebit',
          visible: true,
          order: 4
        },
        133: {
          title: '本期贷方',
          field: 'periodcredit',
          visible: true,
          order: 5
        },
        134: {
          title: '期末余额',
          field: 'endphase',
          visible: true,
          order: 6
        }
      }
    },
    // 初始化自定义类型选择框选项变量
    initOptions(This) {}
  },
  watch: {},
  mounted() {
    this.pageInit()
   // this.columnDrop(this.$refs.table)
  }
}
</script>
