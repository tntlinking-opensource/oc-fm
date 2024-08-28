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
    <el-row
      gutter="0"
      type="flex"
      justify="end"
      align="top"
      class="dc-el-row_ElRow"
    >
      <el-tooltip disabled class="dc-el-tooltip_Button mr-10">
        <el-input
          placeholder="科目编码/名称"
          clearable
          v-model="queryFormData.varaccountcode"
          @keyup.enter.native="onSearch"
        >
          <el-button
            slot="append"
            type="success"
            icon="el-icon-search"
            @click="onSearch"
            >查询</el-button
          >
        </el-input>
      </el-tooltip>
      <el-tooltip v-show="permission.add" disabled class="dc-el-tooltip_Button">
        <el-button type="success" icon="el-icon-plus" v-on:click="onCreate"
          >添加</el-button
        >
      </el-tooltip>
      <el-tooltip disabled class="dc-el-tooltip_Button">
<!--        <el-button type="primary" icon="el-icon-upload2" v-on:click=""-->
<!--          >导入</el-button-->
<!--        >-->
      </el-tooltip>
      <el-tooltip disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-download" v-on:click="exportTglPeriodAccounts"
          >导出</el-button
        >
      </el-tooltip>
      <el-tooltip disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-download" v-on:click="exportTemplate('initperiodImportModel')"
          >导入模板下载</el-button
        >
      </el-tooltip>
    </el-row>
    <ux-grid
      column-key
      ref="table"
      :data="tableData"
      border
      v-on:sort-change="onSortChange"
      v-on:header-dragend="onWidthChange($refs.table)"
      :tree-config="{
        children: '',
        indent: 20,
        accordion: false,
        line: false,
        showIcon: true,
        iconOpen: '',
        iconClose: ''
      }"
      class="dc-ux-grid_QueryTable"
    >
      <ux-table-column
        :field="tableColumns['129'].field"
        :title="tableColumns['129'].title"
        :width="tableColumns['129'].width"
        :visible="tableColumns['129'].visible"
        :params="{ sortId: '129', summary: false }"
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
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['145'].field"
        :title="tableColumns['145'].title"
        :width="tableColumns['145'].width"
        :visible="tableColumns['145'].visible"
        :params="{ sortId: '145', summary: false }"
        tree-node
        resizable
        min-width="120px"
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
                return row.uqaccountid.varaccountcode;
              })(row.uqaccountid.VARACCOUNTCODE, row, rowIndex)
            "
          ></div>
        </template>
      </ux-table-column>
      <ux-table-column
        :field="tableColumns['146'].field"
        :title="tableColumns['146'].title"
        :width="tableColumns['146'].width"
        :visible="tableColumns['146'].visible"
        :params="{ sortId: '146', summary: false }"
        tree-node
        resizable
        min-width="120px"
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
                return row.uqaccountid.varaccountname;
              })(row.uqaccountid.VARACCOUNTNAME, row, rowIndex)
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
        min-width="100px"
        align="right"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      >
        <template slot-scope="{ row, rowIndex }">
          <div
            v-html="
              ((value, row, rowIndex) => {
                let valueF = !row.mnydebitperiodall ? 0 : row.mnydebitperiodall;
                return Number(valueF)
                  .toFixed(2)
                  .replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
              })(row.mnydebitperiodall, row, rowIndex)
            "
          ></div>
        </template>
      </ux-table-column>
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
      >
        <template slot-scope="{ row, rowIndex }">
          <div
            v-html="
              ((value, row, rowIndex) => {
                let valueF = !row.mnycreditperiodall
                  ? 0
                  : row.mnycreditperiodall;
                return Number(valueF)
                  .toFixed(2)
                  .replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
              })(row.mnycreditperiodall, row, rowIndex)
            "
          ></div>
        </template>
      </ux-table-column>
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
          <el-popover
            ref="popover"
            width="10"
            trigger="click"
            placement="bottom"
            offset="10"
            class="dc-el-popover_ElPopover"
          >
            <view-columns-select
              v-model="tableColumns"
              ref="viewColumnsSelect"
              v-on:save-column-view="saveColumn($event, 'table')"
              v-on:show-all-column="showAllColumn($event, 'table')"
              v-on:show-default-column="showDefaultColumn('table')"
              class="dc-view-columns-select_ViewColumnsSelect"
            ></view-columns-select>
            <template slot="reference">
              <OperationIcon
                type="primary"
                content="自定义列"
                placement="top"
                icon-name="el-icon-tickets"
              ></OperationIcon>
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
            v-on:click="onView(scope)"
            type="info"
            v-show="permission.view"
            content="查看"
            placement="top"
            icon-name="el-icon-view"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <!-- 非末级不能编辑、删除、复制 -->
          <OperationIcon
            v-on:click="onEdit(scope)"
            type="primary"
            v-show="
              permission.edit && scope.row.uqaccountid.intislastlevel == 1
            "
            content="编辑"
            placement="top"
            icon-name="el-icon-edit"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            v-on:click="onCopy(scope)"
            type="primary"
            v-show="permission.add && scope.row.uqaccountid.intislastlevel == 1"
            content="复制"
            placement="top"
            icon-name="el-icon-document"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            v-on:click="onDelete(scope)"
            type="danger"
            v-show="
              permission.remove && scope.row.uqaccountid.intislastlevel == 1
            "
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
import { validatenull } from "@/utils/validate";

import {
  listTglPeriodAccountsPage,
  deleteTglPeriodAccounts,
  exportTglPeriodAccounts
} from "@/api/vouchermanager/tglPeriodAccounts";

import { listResourcePermission } from "@/api/admin/common/permission";

/** 根据用户界面配置import组件 开始 */
import History from "@/views/components/history";
import EditForm from "./form";
import QueryTag from "@/views/components/queryTag";
import QueryConditionPanel from "@/views/components/queryConditionPanel";
import ViewColumnsSelect from "@/views/components/DcViewColumnsSelect";
/** 根据用户界面配置import组件 结束 */
import metadata from "./metadata";
import ExportExcelButton from "@/components/ExportExcelButton";
import DcMain from "@/views/components/dcMain";
import OperationIcon from "@/components/OperationIcon";
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
      metadata,

      /** 根据用户界面配置生成data数据 开始 */
      queryFormData: { varaccountcode: "" },
      tableData: [],
      // 查询表格列头
      tableColumns: {
        129: {
          title: "单位ID",
          field: "uqcompanyid",
          visible: false,
          order: 0
        },
        130: {
          title: "全局会计期ID",
          field: "uqglobalperiodid",
          visible: false,
          order: 1
        },
        131: {
          title: "会计科目ID",
          field: "uqaccountid",
          visible: false,
          order: 2
        },
        145: {
          title: "科目编码",
          field: "uqaccountid.VARACCOUNTCODE",
          visible: true,
          order: 3
        },
        146: {
          title: "科目名称",
          field: "uqaccountid.VARACCOUNTNAME",
          visible: true,
          order: 4
        },
        132: {
          title: "全部本期借方金额",
          field: "mnydebitperiodall",
          visible: true,
          order: 5
        },
        133: {
          title: "全部本期贷方金额",
          field: "mnycreditperiodall",
          visible: true,
          order: 6
        },
        114: {
          title: "操作",
          width: "140px",
          isShow: true,
          visible: true,
          order: 7
        }
      },
      // 分页属性
      tableDataPage: 1,
      tableDataTotal: 0,
      // 选项变量

      /** 根据用户界面配置生成data数据 结束 */
      version: 15,
      search: {
        // 查询条件   业务表设置的筛选条件
        params: [],

        offset: 0,
        limit: 10,
        columnName: "", // 排序字段名
        order: "" // 排序
      },
      searchList: [],
      tableId: "1457254743943431257",
      schemeId: "1457254743943431255"
    };
  },
  computed: {},
  methods: {
    getList() {
      this.setLoad();
      /* 查询参数 和数据权限 */
      this.search.params = [];
      if (!validatenull(this.queryFormData.varaccountcode)) {
        this.search.params = [
          {
            columnName: "accauntid",
            queryType: "=",
            value: this.queryFormData.varaccountcode
          }
        ];
      }
      // 数据权限: 科目发生数汇总表tgl_period_accounts
      this.pushDataPermissions(
        this.search.params,
        this.$route.meta.routerId,
        this.tableId
      );
      listTglPeriodAccountsPage(this.search)
        .then(responseData => {
          if (responseData.code == 100) {
            this.tableDataTotal = responseData.data.total;
            this.tableData = responseData.data.rows
              ? responseData.data.rows
              : [];
          } else {
            this.showMessage(responseData);
          }
          this.resetLoad();
        })
        .catch(error => {
          this.outputError(error);
        });
    },
    onSearch() {
      this.search.offset = 0;
      this.tableDataPage = 1;
      this.getList();
    },
    onSizeChange(val) {
      this.tableDataPage = 1;
      this.search.limit = val;
      this.search.offset = (this.tableDataPage - 1) * val;
      this.getList();
    },
    onCurrentChange(val) {
      this.search.offset = (val - 1) * this.search.limit;
      this.tableDataPage = val;
      this.getList();
    },
    async pageInit() {
      this.setLoad();
      try {
        this.initOptions(this.queryModel);
        this.search.params = [];
        // 数据权限: 科目发生数汇总表tgl_period_accounts
        this.pushDataPermissions(
          this.search.params,
          this.$route.meta.routerId,
          this.tableId
        );
        let [
          listTglPeriodAccountsRespData,
          listPermissionRespData
        ] = await Promise.all([
          listTglPeriodAccountsPage(this.search),
          listResourcePermission(this.$route.meta.routerId)
        ]);
        if (
          listTglPeriodAccountsRespData.code == 100 &&
          listPermissionRespData.code == 100
        ) {
          this.tableDataTotal = listTglPeriodAccountsRespData.data.total;
          this.tableData = listTglPeriodAccountsRespData.data.rows
            ? listTglPeriodAccountsRespData.data.rows
            : [];
          this.permission.view = listPermissionRespData.data.find(item => {
            return item.permission === "tglPeriodAccounts:read";
          });
          this.permission.export = listPermissionRespData.data.find(item => {
            return item.permission === "tglPeriodAccounts:export";
          });

          this.permission.add = listPermissionRespData.data.find(item => {
            return item.permission === "tglPeriodAccounts:create";
          });
          this.permission.edit = listPermissionRespData.data.find(item => {
            return item.permission === "tglPeriodAccounts:update";
          });
          this.permission.remove = listPermissionRespData.data.find(item => {
            return item.permission === "tglPeriodAccounts:delete";
          });
        } else {
          this.showMessage(
            listPermissionRespData.code != 100
              ? listPermissionRespData
              : listTglPeriodAccountsRespData
          );
        }
        this.resetLoad();
      } catch (error) {
        this.outputError(error);
      }
    },
    onView(scope) {
      this.$refs.editForm.$emit(
        "openViewDialog",
        scope.row.uqaccountid.uqaccountid
      );
    },

    onCreate() {
      this.$refs.editForm.$emit("openAddDialog");
    },
    onEdit(scope) {
      console.log(scope.row.uqaccountid.intislastlevel);
      this.$refs.editForm.$emit(
        "openEditDialog",
        scope.row.uqaccountid.uqaccountid
      );
    },
    onCopy(scope) {
      this.$refs.editForm.$emit(
        "openCopyDialog",
        scope.row.uqaccountid.uqaccountid
      );
    },
    onDelete(scope) {
      this.$confirm("确定删除吗？", "确认", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.setLoad();
          deleteTglPeriodAccounts(scope.row)
            .then(responseData => {
              if (responseData.code == 100) {
                this.getList();
                this.showMessage({
                  type: "success",
                  msg: "删除成功"
                });
              } else {
                this.showMessage(responseData);
              }
              this.resetLoad();
            })
            .catch(error => {
              this.outputError(error);
            });
        })
        .catch(() => {});
    },

    onSortChange(orderby) {
      if (validatenull(orderby.prop)) {
        this.search.columnName = "";
        this.search.order = "";
      } else {
        this.search.columnName = orderby.prop;
        this.search.order = orderby.order;
      }
      this.getList();
    },
    exportTglPeriodAccounts() {
      this.setLoad()
      /* 查询参数 和数据权限 */
      let searcharams =  {
        params: []
      }
      searcharams.params.push({
        columnName: '',
        queryType: '',
        value: ''
      })
      exportTglPeriodAccounts(searcharams).then((responseData) => {
        let date = moment().format("YYYY-MM-DD");
        const blob = new Blob([responseData]);
        const url = window.URL.createObjectURL(blob);
        let dom = document.createElement("a");
        dom.style.display = "none";
        dom.href = url;
        dom.setAttribute("download", '科目期初余额_' + date +  '.xls');
        document.body.appendChild(dom);
        dom.click();
        this.resetLoad()
      }).catch((error) => {
        this.outputError(error)
      })
    },
    // 初始化自定义类型选择框选项变量
    initOptions(This) {}
  },
  watch: {},
  mounted() {
    this.pageInit();

    this.columnDrop(this.$refs.table);
  }
};
</script>
