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
    <el-container direction="horizontal" class="dc-el-container_ElContainer">
      <el-aside width="250px" height="60px" class="custom-tree-container">
        <div class="block">
          <el-tag class="custom-tree-header">分户名称</el-tag>
          <span class="custom-tree-node-default mtb-10">
            <el-tooltip
              v-show="permission.add"
              disabled
              class="dc-el-tooltip_Button"
            >
              <el-button
                type="primary"
                icon="el-icon-plus"
                v-on:click="onCreate"
                >新增分户</el-button
              >
            </el-tooltip>
          </span>
          <el-tree
            :data="tglLedgetypeData"
            node-key="id"
            default-expand-all
            :expand-on-click-node="false"
            @current-change="onChangeCurrentRow"
          >
            <span class="custom-tree-node" slot-scope="{ node, data }">
              <span class="text-span">{{ data.varledgetypename }}</span>
              <span>
                <OperationIcon
                  type="primary"
                  v-on:click="onEdit(data)"
                  content="修改"
                  placement="top"
                  icon-name="el-icon-edit"
                  class="dc-OperationIcon_IconButton"
                ></OperationIcon>
                <OperationIcon
                  type="danger"
                  v-on:click="onDelete(data)"
                  content="删除"
                  placement="top"
                  icon-name="el-icon-delete"
                  class="dc-OperationIcon_IconButton"
                ></OperationIcon>
              </span>
            </span>
          </el-tree>
        </div>
      </el-aside>
      <el-main>
        <el-row>
          <el-row
            gutter="0"
            type="flex"
            justify="end"
            align="top"
            class="dc-el-row_ElRow"
          >
            <el-tooltip disabled class="dc-el-tooltip_Button">
              <el-button type="primary" icon="el-icon-upload2" v-on:click="checkFile"
                >导入</el-button
              >
            </el-tooltip>
            <el-tooltip disabled class="dc-el-tooltip_Button">
              <el-button type="primary" icon="el-icon-download" v-on:click="exportTglLedge"
                >导出</el-button
              >
            </el-tooltip>
            <el-tooltip disabled class="dc-el-tooltip_Button">
              <el-button type="primary" icon="el-icon-download" v-on:click="exportTemplate('ledgerImportModel')"
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
              v-if="tableColumns['125'].show"
              :field="tableColumns['125'].field"
              :title="tableColumns['125'].title"
              :width="tableColumns['125'].width"
              :visible="tableColumns['125'].visible"
              :params="{ sortId: '125', summary: false }"
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
              v-if="tableColumns['126'].show"
              :field="tableColumns['126'].field"
              :title="tableColumns['126'].title"
              :width="tableColumns['126'].width"
              :visible="tableColumns['126'].visible"
              :params="{ sortId: '126', summary: false }"
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
              v-if="tableColumns['127'].show"
              :field="tableColumns['127'].field"
              :title="tableColumns['127'].title"
              :width="tableColumns['127'].width"
              :visible="tableColumns['127'].visible"
              :params="{ sortId: '127', summary: false }"
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
              v-if="tableColumns['128'].show"
              :field="tableColumns['128'].field"
              :title="tableColumns['128'].title"
              :width="tableColumns['128'].width"
              :visible="tableColumns['128'].visible"
              :params="{ sortId: '128', summary: false }"
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
              v-if="tableColumns['129'].show"
              :field="tableColumns['129'].field"
              :title="tableColumns['129'].title"
              :width="tableColumns['129'].width"
              :visible="tableColumns['129'].visible"
              :params="{ sortId: '129', summary: false }"
              tree-node
              resizable
              min-width="80px"
              align="right"
              header-align="center"
              sortable
              show-overflow
              class="dc-ux-table-column_ElTableColumn"
            ></ux-table-column>
            <ux-table-column
              v-if="tableColumns['130'].show"
              :field="tableColumns['130'].field"
              :title="tableColumns['130'].title"
              :width="tableColumns['130'].width"
              :visible="tableColumns['130'].visible"
              :params="{ sortId: '130', summary: false }"
              tree-node
              resizable
              min-width="80px"
              align="right"
              header-align="center"
              sortable
              show-overflow
              class="dc-ux-table-column_ElTableColumn"
            ></ux-table-column>
            <ux-table-column
              v-if="tableColumns['131'].show"
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
              title="操作"
              :params="{ sortId: '117' }"
              tree-node
              resizable
              width="160px"
              min-width="160px"
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
                  offset="0"
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
                <!-- <OperationIcon
                  v-on:click="onView(scope)"
                  type="info"
                  v-show="permission.view"
                  content="查看"
                  placement="top"
                  icon-name="el-icon-view"
                  class="dc-OperationIcon_IconButton"
                ></OperationIcon> -->
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
                  v-on:click="onCopy(scope)"
                  type="primary"
                  v-show="permission.add"
                  content="复制"
                  placement="top"
                  icon-name="el-icon-document"
                  class="dc-OperationIcon_IconButton"
                ></OperationIcon>
                <OperationIcon
                  v-on:click="onTglLedgerDelete(scope)"
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
            :page-sizes="[10, 20, 30, 40, 50, 100]"
            layout="total, sizes, prev, pager, next, jumper"
            v-on:size-change="onSizeChange"
            v-on:current-change="onCurrentChange"
            class="dc-el-pagination_ElPagination"
          ></el-pagination>
        </el-row>
      </el-main>
    </el-container>
    <input type="file" id="fileinput" style="display: none" @change="uploadExcel"
           accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel "/>
  </el-row>
</template>
<script>
import { validatenull } from "@/utils/validate";

import {
  listTglLedgetypePage,
  importtTglLedge,
  deleteTglLedgetype,
  exportTglLedge
} from "@/api/vouchermanager/tglLedgetype";
import {
  listTglLedgerPage,
  listTglLedgerTree,
  deleteTglLedger
} from "@/api/vouchermanager/tglLedger";

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
import moment from "moment/moment";
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
      parentTable: {},
      queryFormData: {},
      tableData: [],
      // 查询表格列头
      tableColumns: {
        125: {
          title: "分户类别ID",
          field: "uqledgetypeid",
          visible: true,
          show: false,
          order: 0
        },
        126: {
          title: "分户类别编码",
          field: "varledgecode",
          visible: true,
          show: true,
          order: 1
        },
        127: {
          title: "分户类别名称",
          field: "varledgename",
          visible: true,
          show: true,
          order: 2
        },
        128: {
          title: "上级ID",
          field: "uqparentid",
          show: false,
          visible: true,
          order: 3
        },
        129: {
          title: "是否末级",
          field: "intislastlevel",
          visible: true,
          show: false,
          order: 4
        },
        130: {
          title: "状态",
          field: "intstatus",
          visible: true,
          show: false,
          order: 5
        },
        131: {
          title: "tenant_id",
          field: "tenantId",
          visible: true,
          show: false,
          order: 6
        },
        132: {
          title: "uqledgetypeid",
          field: "uqledgetypeid",
          visible: true,
          show: false,
          order: 6
        },
        117: {
          title: "操作",
          width: "160px",
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
      version: 2,
      search: {
        // 查询条件   业务表设置的筛选条件
        params: [],

        offset: 0,
        limit: 10,
        columnName: "", // 排序字段名
        order: "" // 排序
      },
      searchList: [],
      tableId: "1457254743943430949",
      schemeId: "1457254743943430947",
      tglLedgetypeData: []
    };
  },
  computed: {},
  methods: {
    uploadExcel(evt) {
      const files = evt.target.files
      if (files == null || files.length == 0) {
        alert('No files wait for import')
        return
      }
      let formData = new FormData()
      formData.append('file', files[0])
      this.setLoad()
      importtTglLedge(formData).then((responseData) => {
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
    getList() {
      this.setLoad();
      /* 查询参数 和数据权限 */
      this.search.params = [];

      if (validatenull(this.parentTable)) {
        this.$alert("请选择左侧列表", "提示", {
          confirmButtonText: "确定",
          type: "info"
        });
        return;
      }
      this.search.params.push({
        columnName: "uqledgetypeid",
        queryType: "=",
        value: this.parentTable.uqledgetypeid
      });

      if (this.isQueryConditionPanel) {
        this.search.params = this.search.params.concat(
          this.compositeCondition()
        );
      } else {
      }
      // 数据权限: 分户类别表tgl_ledgetype
      this.pushDataPermissions(
        this.search.params,
        this.$route.meta.routerId,
        this.tableId
      );
      this.treeList();
      listTglLedgerPage(this.search)
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
    treeList() {
      listTglLedgerTree().then(res => {
        this.tglLedgetypeData = res.data;
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
      this.initOptions(this.queryModel);
      this.setLoad();
      listResourcePermission(this.$route.meta.routerId)
        .then(responseData => {
          if (responseData.code == 100) {
            this.permission.view = responseData.data.find(item => {
              return item.permission === "tglLedgetype:read";
            });
            this.permission.export = responseData.data.find(item => {
              return item.permission === "tglLedgetype:export";
            });

            this.permission.add = responseData.data.find(item => {
              return item.permission === "tglLedgetype:create";
            });
            this.permission.edit = responseData.data.find(item => {
              return item.permission === "tglLedgetype:update";
            });
            this.permission.remove = responseData.data.find(item => {
              return item.permission === "tglLedgetype:delete";
            });
          } else {
            this.showMessage(responseData);
          }
          this.resetLoad();
        })
        .catch(error => {
          this.outputError(error);
        });
      this.treeList();
    },
    onView(scope) {
      this.$refs.editForm.$emit(
        "openViewDialog",
        scope.row.uqledgetypeid.uqledgetypeid
      );
    },

    onCreate() {
      let row = {};
      this.$refs.editForm.$emit("openAddDialog", row);
    },
    onEdit(scope) {
      this.$refs.editForm.$emit(
        "openEditDialog",
        !scope.row ? scope.uqledgetypeid : scope.row.uqledgetypeid.uqledgetypeid
      );
    },
    onCopy(scope) {
      this.$refs.editForm.$emit(
        "openCopyDialog",
        scope.row.uqledgetypeid.uqledgetypeid
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
          deleteTglLedgetype(!scope.row ? scope : scope.row)
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
    onTglLedgerDelete(scope) {
      this.$confirm("确定删除吗？", "确认", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.setLoad();
          deleteTglLedger(!scope.row ? scope : scope.row)
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

    onChangeCurrentRow(currentRow, oldCurrentRow) {
      if (currentRow != oldCurrentRow) {
        this.parentTable = currentRow;
        this.initOptions(this.queryModel);
        this.onSearch();
      }
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
    checkFile() {
      document.querySelector('#fileinput').click()
    },
    exportTglLedge() {
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
      exportTglLedge(searcharams).then((responseData) => {
        let date = moment().format("YYYY-MM-DD");
        const blob = new Blob([responseData]);
        const url = window.URL.createObjectURL(blob);
        let dom = document.createElement("a");
        dom.style.display = "none";
        dom.href = url;
        dom.setAttribute("download", '科目模板管理_' + date +  '.xls');
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
<style lang="scss" scoped>
.custom-tree-container {
  .custom-tree-node-default {
    display: flex;
    align-items: center;
    justify-content: end;
  }
  .custom-tree-node {
    display: flex;
    align-items: center;
    justify-content: space-between;
    position: relative;
    white-space: nowrap;
    text-overflow: ellipsis;
    overflow: hidden;
    flex: 1;
    .text-span {
      white-space: nowrap;
      text-overflow: ellipsis;
      overflow: hidden;
    }
  }
  .custom-tree-node {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 14px;
    padding-right: 8px;
  }
  .custom-tree-header {
    height: 48px;
    line-height: 48px;
    text-align: center;
    background-color: #f2f6fc;
    font-weight: 700;
    font-size: 14px;
    color: #606266;
    border-radius: 0;
    border: 0;
    border: none;
    width: 100%;
  }
}
</style>
