<template>
  <el-row class="dc-container" v-loading="loading">
    <history ref="history" :bussObject="curentRow"></history>
    <ureportDesOrView ref="ureportDesOrView"
                      v-on:ureport-finished="getList"
                      v-on:before-load="setLoad"
                      v-on:after-load="resetLoad"
    ></ureportDesOrView>
    <periodQueryForm ref="periodQueryForm"
      v-on:choose-finished="getQueryPeriods"
      v-on:before-load="setLoad"
      v-on:after-load="resetLoad"
    ></periodQueryForm>
    <edit-form ref="editForm"
                     v-on:save-finished="getList"
                     v-on:before-load="setLoad"
                     v-on:after-load="resetLoad"
    ></edit-form>
    <el-row gutter="0" type="flex" justify="end" align="top" class="dc-el-row_ElRow">
      <el-tooltip v-show="permission.add" disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-plus" v-on:click="onCreate">添加</el-button>
      </el-tooltip>
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
        :field="tableColumns['130'].field"
        :title="tableColumns['130'].title"
        :width="tableColumns['130'].width"
        :visible="tableColumns['130'].visible"
        :params="{ sortId: '130', summary: false }"
        tree-node
        resizable
        min-width="180px"
        align="left"
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
        min-width="180px"
        align="left"
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
        min-width="120px"
        align="left"
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
        min-width="80px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      >
        <template slot-scope="{ row, rowIndex }">
          <div
            v-html="
              ((value, row, rowIndex) => {
                switch (value) {
                  case 1:
                    return '利润表'
                  case 2:
                    return '资产负债表'
                  case 3:
                    return '现金流量表'
                }
              })(row.type, row, rowIndex)
            "
          ></div>
        </template>
      </ux-table-column>
      <ux-table-column
        :field="tableColumns['134'].field"
        :title="tableColumns['134'].title"
        :width="tableColumns['134'].width"
        :visible="tableColumns['134'].visible"
        :params="{ sortId: '134', summary: false }"
        tree-node
        resizable
        min-width="80px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      >
        <template slot-scope="{ row, rowIndex }">
          <div
            v-html="
              ((value, row, rowIndex) => {
                switch (value) {
                  case 1:
                    return '启用'
                  default:
                    return '停用'
                }
              })(row.status, row, rowIndex)
            "
          ></div>
        </template>
      </ux-table-column>
      <ux-table-column
        :field="tableColumns['132'].field"
        :title="tableColumns['132'].title"
        :width="tableColumns['132'].width"
        :visible="tableColumns['132'].visible"
        :params="{ sortId: '132', summary: false }"
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
        :field="tableColumns['131'].field"
        :title="tableColumns['131'].title"
        :width="tableColumns['131'].width"
        :visible="tableColumns['131'].visible"
        :params="{ sortId: '131', summary: false }"
        tree-node
        resizable
        min-width="180px"
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
        class="dc-ux-table-column_ElTableOptColumn">
        <template slot-scope="scope">
          <OperationIcon
            v-on:click="onView(scope)"
            type="info"
            v-show="permission.view"
            content="预览报表"
            placement="top"
            icon-name="el-icon-view"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            v-on:click="onEdit(scope.row)"
            type="primary"
            v-show="permission.edit"
            content="编辑"
            placement="top"
            icon-name="el-icon-edit"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            v-if="scope.row.status != 1 && scope.row.type && scope.row.type != 0"
            v-on:click="onStart(scope)"
            type="primary"
            v-show="permission.start"
            content="启用"
            placement="top"
            icon-name="el-icon-video-play"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            v-if="scope.row.status != 1"
            v-on:click="onDelete(scope)"
            type="danger"
            v-show="permission.remove"
            content="删除"
            placement="top"
            icon-name="el-icon-delete"
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
      :page-sizes="[5, 10, 20, 30, 40, 50, 100]"
      layout="total, sizes, prev, pager, next, jumper"
      v-on:size-change="onSizeChange"
      v-on:current-change="onCurrentChange"
      class="dc-el-pagination_ElPagination"
    ></el-pagination>
  </el-row>
</template>
<script>
import { validatenull } from '@/utils/validate'
import { listUreportFilePage, startUreportFile, deleteUreportFile } from '@/api/ureport/ureportFile.js'
import { listResourcePermission } from '@/api/admin/common/permission'
/** 根据用户界面配置import组件 开始 */
import History from '@/views/components/history'
import periodQueryForm from './periodQuery'
import ureportDesOrView from "./ureportDesOrView";
import editForm from "./form";
/** 根据用户界面配置import组件 结束 */
import ExportExcelButton from '@/components/ExportExcelButton'
import DcMain from '@/views/components/dcMain'
import OperationIcon from '@/components/OperationIcon'
export default {
  extends: DcMain,
  components: {
    /** 根据用户界面配置组件 开始 */
    History,
    periodQueryForm,
    editForm,
    /** 根据用户界面配置组件 结束 */
    ExportExcelButton,
    OperationIcon,
    ureportDesOrView
  },
  data() {
    return {
      permission: {
        view: false,
        add: false,
        edit: false,
        remove: false,
        export: false,
        start: false,
      },
      /** 根据用户界面配置生成data数据 开始 */
      tableData: [],
      // 查询表格列头
      tableColumns: {
        130: {
          title: '报表名称',
          field: 'name',
          visible: true,
          order: 0
        },
        133: {
          title: '类型',
          field: 'type',
          visible: true,
          order: 1
        },
        134: {
          title: '状态',
          field: 'status',
          visible: true,
          order: 2
        },
        132: {
          title: '操作人',
          field: 'updateBy',
          visible: true,
          order: 3
        },
        131: {
          title: '操作时间',
          field: 'updateTime',
          visible: true,
          order: 4
        },
        114: {
          title: '操作',
          width: '140px',
          isShow: true,
          visible: true,
          order: 5
        }
      },
      // 分页属性
      tableDataPage: 1,
      tableDataTotal: 0,
      baseURL: process.env.BASE_API,
      // 选项变量

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
      viewCurrentRow: {},
      searchList: [],
      tableId: '1457254743943431209',
      schemeId: '1457254743943431207'
    }
  },
  computed: {},
  methods: {
    getList() {
      this.setLoad()
      /* 查询参数 和数据权限 */
      this.search.params = [
        { columnName: 'company_id', queryType: '=', value: currentUser.company.id == '0' ? null : currentUser.company.id },
        { logic: 'AND', queryType: '(' },
        { logic: 'AND', columnName: 'type', queryType: '=', value: 1 },
        { logic: 'or', columnName: 'type', queryType: '=', value: 2 },
        { logic: 'or', columnName: 'type', queryType: '=', value: 3 },
        {logic: '', queryType: ')'}
      ]
      // 数据权限: 报表设计器tgl_report_design
      this.pushDataPermissions(this.search.params, this.$route.meta.routerId, this.tableId)
      listUreportFilePage(this.search)
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
      this.search.offset = 0
      this.tableDataPage = 1
      this.getList()
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
        this.search.params = [
          { columnName: 'company_id', queryType: '=', value: currentUser.company.id == '0' ? null : currentUser.company.id },
          { logic: 'AND', queryType: '(' },
          { logic: 'AND', columnName: 'type', queryType: '=', value: 1 },
          { logic: 'or', columnName: 'type', queryType: '=', value: 2 },
          { logic: 'or', columnName: 'type', queryType: '=', value: 3 },
          {logic: '', queryType: ')'}
        ]
        let [listReportDesignRespData, listPermissionRespData] = await Promise.all([
          listUreportFilePage(this.search),
          listResourcePermission(this.$route.meta.routerId)
        ])
        if (listReportDesignRespData.code == 100 && listPermissionRespData.code == 100) {
          this.tableDataTotal = listReportDesignRespData.data.total
          this.tableData = listReportDesignRespData.data.rows ? listReportDesignRespData.data.rows : []
          this.permission.view = listPermissionRespData.data.find((item) => {
            return item.permission === 'ureportFile:read'
          })
          this.permission.export = listPermissionRespData.data.find((item) => {
            return item.permission === 'ureportFile:export'
          })

          this.permission.add = listPermissionRespData.data.find((item) => {
            return item.permission === 'ureportFile:create'
          })
          this.permission.edit = listPermissionRespData.data.find((item) => {
            return item.permission === 'ureportFile:update'
          })
          this.permission.remove = listPermissionRespData.data.find((item) => {
            return item.permission === 'ureportFile:delete'
          })
          this.permission.start = listPermissionRespData.data.find((item) => {
            return item.permission === 'ureportFile:start'
          })
        } else {
          this.showMessage(listPermissionRespData.code != 100 ? listPermissionRespData : listReportDesignRespData)
        }
        this.resetLoad()
      } catch (error) {
        this.outputError(error)
      }
    },
    onEdit(row) {
      this.$refs.ureportDesOrView.$emit('openEditUreportDialog', row)
      // const name = 'mysql-provider-' + row.name
      // window.open(this.baseURL + '/ureport/designer?_u=' + name)
    },
    onCreate() {
      this.$refs.editForm.$emit('openAddDialog')
    },
    onView(scope) {
      this.viewCurrentRow = scope.row
      this.$refs.periodQueryForm.$emit('openChooseDialog', scope.row.id)
    },
    getQueryPeriods(data) {
      this.$refs.ureportDesOrView.$emit('openViewUreportDialog', data, this.viewCurrentRow)
    },
    onStart(scope) { // 启用
      this.setLoad()
      startUreportFile(scope.row).then((responseData) => {
        if (responseData.code == 100) {
          this.showMessage({
            type: 'success',
            msg: '操作成功'
          })
          this.getList()
        } else {
          this.showMessage(responseData)
        }
        this.resetLoad()
      }).catch((error) => {
        this.outputError(error)
      })
    },
    onDelete(scope) {
      this.$confirm('确定删除吗？', '确认', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.setLoad()
        deleteUreportFile(scope.row).then((responseData) => {
          if (responseData.code == 100) {
            this.getList()
            this.showMessage({
              type: 'success',
              msg: '删除成功'
            })
          } else {
            this.showMessage(responseData)
          }
          this.resetLoad()
        }).catch((error) => {
          this.outputError(error)
        })
      }).catch(() => {})
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

    // 初始化自定义类型选择框选项变量
    initOptions(This) {}
  },
  watch: {},
  mounted() {
    this.pageInit()
  }
}
</script>
