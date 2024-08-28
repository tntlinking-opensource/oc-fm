<template>
  <el-dialog
    title="现金流量"
    :visible.sync="dialogProps.visible"
    :close-on-click-modal="false"
    :destroy-on-close="true"
    v-loading="pageLoading"
    custom-class="dialog-mask dialog-body"
    append-to-body
    width="90%"
  >
    <el-row class="dc-container" v-loading="loading">
      <el-container direction="horizontal" class="dc-el-container_ElContainer">
        <el-aside width="250px" height="60px" class="custom-tree-container">
          <div class="block">
            <el-tag class="custom-tree-header">现金流量类别</el-tag>
            <el-tree
              :data="flowTypeData"
              node-key="id"
              default-expand-all
              :expand-on-click-node="false"
              @current-change="onChangeCurrentRow"
            >
              <span class="custom-tree-node" slot-scope="{ node, data }">
                <span class="text-span">{{
                  data.varcode && data.intlevel != 0
                    ? "[" + data.varcode + "]" + data.varname
                    : data.varname
                }}</span>
              </span>
            </el-tree>
          </div>
        </el-aside>
        <el-main>
          <el-row>
            <ux-grid
              column-key
              ref="table"
              :data="tableData"
              border
              v-on:sort-change="onSortChange"
              v-on:header-dragend="onWidthChange($refs.table)"
              max-height="500"
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
              @row-click="onRowClick"
            >
              <ux-table-column
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
                :field="tableColumns['131'].field"
                :title="tableColumns['131'].title"
                :width="tableColumns['131'].width"
                :visible="tableColumns['131'].visible"
                :params="{ sortId: '131', summary: false }"
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
                min-width="120px"
                align="left"
                header-align="center"
                sortable
                show-overflow
                class="dc-ux-table-column_ElTableColumn"
              ></ux-table-column>
              <ux-table-column
                :field="tableColumns['135'].field"
                :title="tableColumns['135'].title"
                :width="tableColumns['135'].width"
                :visible="tableColumns['135'].visible"
                :params="{ sortId: '135', summary: false }"
                tree-node
                resizable
                min-width="80px"
                align="left"
                header-align="center"
                sortable
                show-overflow
                class="dc-ux-table-column_ElTableColumn"
              >
              </ux-table-column>
              <ux-table-column
                :field="tableColumns['133'].field"
                :title="tableColumns['133'].title"
                :width="tableColumns['133'].width"
                :visible="tableColumns['133'].visible"
                :params="{ sortId: '133', summary: false }"
                tree-node
                resizable
                min-width="80px"
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
                        switch (value) {
                          case 0:
                            return '新增';
                          case 1:
                            return '启用';
                          case 2:
                            return '停用';
                          default:
                            return '';
                        }
                      })(row.intstatus, row, rowIndex)
                    "
                  ></div>
                </template>
              </ux-table-column>
              <ux-table-column
                :field="tableColumns['137'].field"
                :title="tableColumns['137'].title"
                :width="tableColumns['137'].width"
                :visible="tableColumns['137'].visible"
                :params="{ sortId: '137', summary: false }"
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
                :field="tableColumns['138'].field"
                :title="tableColumns['138'].title"
                :width="tableColumns['138'].width"
                :visible="tableColumns['138'].visible"
                :params="{ sortId: '138', summary: false }"
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
                :field="tableColumns['139'].field"
                :title="tableColumns['139'].title"
                :width="tableColumns['139'].width"
                :visible="tableColumns['139'].visible"
                :params="{ sortId: '139', summary: false }"
                tree-node
                resizable
                min-width="160px"
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
                min-width="180px"
                align="left"
                header-align="center"
                sortable
                show-overflow
                class="dc-ux-table-column_ElTableColumn"
              ></ux-table-column>
              <ux-table-column
                :field="tableColumns['141'].field"
                :title="tableColumns['141'].title"
                :width="tableColumns['141'].width"
                :visible="tableColumns['141'].visible"
                :params="{ sortId: '141', summary: false }"
                tree-node
                resizable
                min-width="160px"
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
              :page-sizes="[10, 20, 30, 40, 50, 100, tableDataTotal]"
              layout="total, sizes, prev, pager, next, jumper"
              v-on:size-change="onSizeChange"
              v-on:current-change="onCurrentChange"
              class="dc-el-pagination_ElPagination"
            ></el-pagination>
          </el-row>
        </el-main>
      </el-container>
      <div>
        <input
          type="file"
          id="fileinput"
          style="display: none"
          @change="uploadExcel"
          accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel "
        />
      </div>
    </el-row>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialogProps.visible = false">关 闭</el-button>
      <el-button type="primary" @click="onGetData">确 定</el-button>
    </span>
  </el-dialog>
</template>
<script>
import { validatenull } from "@/utils/validate";
import {
  listFlowitemsPage,
  deleteFlowitems,
  listFlowitemsAll,
  saveFlowitems,
  importFlowItems,
  exportFlowItems
} from "@/api/tgl/flow/flowitems";
import { treeFlowtype, deleteFlowtype } from "@/api/tgl/flow/flowtype";
import { listResourcePermission } from "@/api/admin/common/permission";
/** 根据用户界面配置import组件 开始 */
import History from "@/views/components/history";
import QueryTag from "@/views/components/queryTag";
import QueryConditionPanel from "@/views/components/queryConditionPanel";
import ViewColumnsSelect from "@/views/components/DcViewColumnsSelect";
import moment from "moment";
/** 根据用户界面配置import组件 结束 */
import ExportExcelButton from "@/components/ExportExcelButton";
import DcMain from "@/views/components/dcMain";
import OperationIcon from "@/components/OperationIcon";
export default {
  extends: DcMain,
  components: {
    /** 根据用户界面配置组件 开始 */
    History,
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
        export: false,
        typeCreate: false,
        typeUpdate: false,
        typeDelete: false
      },
      /** 根据用户界面配置生成data数据 开始 */
      parentTable: {},
      queryFormData: {
        varname: "" // 项目名称
      },
      flowTypeData: [],
      tableData: [],
      // 查询表格列头
      tableColumns: {
        128: {
          title: "项目编号",
          field: "varcode",
          visible: true,
          order: 0
        },
        129: {
          title: "项目名称",
          field: "varname",
          visible: true,
          order: 1
        },
        131: {
          title: "所属分类编号",
          field: "uqflowtypeid.varcode",
          visible: true,
          order: 2
        },
        130: {
          title: "所属分类名称",
          field: "uqflowtypeid.varname",
          visible: true,
          order: 3
        },
        135: {
          title: "流向",
          field: "direct.name",
          visible: true,
          order: 4
        },
        133: {
          title: "状态",
          field: "intstatus",
          visible: true,
          order: 5
        },
        137: {
          title: "备注信息",
          field: "remarks",
          visible: false,
          order: 6
        },
        138: {
          title: "创建者",
          field: "createBy",
          visible: false,
          order: 7
        },
        139: {
          title: "创建时间",
          field: "createDate",
          visible: false,
          order: 8
        },
        140: {
          title: "更新者",
          field: "updateBy",
          visible: false,
          order: 9
        },
        141: {
          title: "更新时间",
          field: "updateDate",
          visible: false,
          order: 10
        },
        119: {
          title: "操作",
          width: "160px",
          isShow: true,
          visible: true,
          order: 11
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
      parentSearch: {
        // 查询条件   业务表设置的筛选条件
        params: [],
        columnName: "", // 排序字段名
        order: "" // 排序
      },
      searchList: [],
      tableId: "1457254743943430686",
      schemeId: "1457254743943430684",
      dialogProps: {
        visible: false
      },
      radioData: {}
    };
  },
  computed: {},
  methods: {
    open() {
      this.dialogProps.visible = true;
    },
    // 单选
    onRowClick(data) {
      this.radioData = data;
    },
    onGetData() {
      if (this.radioData.intstatus == 1) {
        this.dialogProps.visible = false;
        this.$emit("onGetData", this.radioData);
      } else {
        this.showMessage({
          type: "warning",
          msg: "只能选择状态为启用的数据"
        });
      }
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
        columnName: "uqflowtypeid",
        queryType: "=",
        // 最外层父级查询所有
        value:
          this.parentTable.uqflowtypeid ==
          "00000000-0000-0000-0000-000000000000"
            ? null
            : this.parentTable.uqflowtypeid
      });
      if (this.isQueryConditionPanel) {
        this.search.params = this.search.params.concat(
          this.compositeCondition()
        );
      } else {
        this.search.params.push({
          columnName: "varname",
          queryType: "like",
          value: !validatenull(this.queryFormData.varname)
            ? this.queryFormData.varname
            : null
        });
      }
      // 数据权限: 现金流量项目tgl_flowitems
      this.pushDataPermissions(
        this.search.params,
        this.$route.meta.routerId,
        this.tableId
      );
      listFlowitemsPage(this.search)
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
    treeFlowTypeList() {
      this.setLoad();
      /* 查询参数 和数据权限 */
      this.search.params = [];
      // 数据权限: 现金流量项目tgl_flowitems
      this.pushDataPermissions(
        this.search.params,
        this.$route.meta.routerId,
        this.tableId
      );
      treeFlowtype(this.search)
        .then(responseData => {
          if (responseData.code == 100) {
            this.flowTypeData = responseData.data ? responseData.data : [];
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
    exportFlowItemsList() {
      this.setLoad();
      exportFlowItems()
        .then(responseData => {
          let date = moment().format("YYYY-MM-DD");
          const blob = new Blob([responseData]);
          const url = window.URL.createObjectURL(blob);
          let dom = document.createElement("a");
          dom.style.display = "none";
          dom.href = url;
          dom.setAttribute("download", "现金流量_" + date + ".xls");
          document.body.appendChild(dom);
          dom.click();
          this.resetLoad();
        })
        .catch(error => {
          this.outputError(error);
        });
    },
    checkFile() {
      document.querySelector("#fileinput").click();
    },
    uploadExcel(evt) {
      const files = evt.target.files;
      if (files == null || files.length == 0) {
        alert("No files wait for import");
        return;
      }
      let formData = new FormData();
      formData.append("file", files[0]);
      this.setLoad();
      importFlowItems(formData)
        .then(responseData => {
          if (responseData.code == 100) {
            this.pageInit();
            this.showMessage({
              type: "success",
              msg: "导入成功"
            });
          } else {
            this.showMessage(responseData);
          }
          this.resetLoad();
        })
        .catch(error => {
          this.outputError(error);
        });
    },
    async pageInit() {
      this.setLoad();
      try {
        this.initOptions(this.queryModel);
        let [treeFlowTypeRespData, listPermissionRespData] = await Promise.all([
          treeFlowtype({ params: [], orderby: "" }),
          listResourcePermission(this.$route.meta.routerId)
        ]);
        if (
          treeFlowTypeRespData.code == 100 &&
          listPermissionRespData.code == 100
        ) {
          this.flowTypeData = treeFlowTypeRespData.data
            ? treeFlowTypeRespData.data
            : [];
          this.$nextTick(() => {
            if (this.flowTypeData && this.flowTypeData.length > 0) {
              this.onChangeCurrentRow(this.flowTypeData[0]);
            }
          });
          this.permission.view = listPermissionRespData.data.find(item => {
            return item.permission === "flowitems:read";
          });
          this.permission.export = listPermissionRespData.data.find(item => {
            return item.permission === "flowitems:export";
          });
          this.permission.add = listPermissionRespData.data.find(item => {
            return item.permission === "flowitems:create";
          });
          this.permission.edit = listPermissionRespData.data.find(item => {
            return item.permission === "flowitems:update";
          });
          this.permission.remove = listPermissionRespData.data.find(item => {
            return item.permission === "flowitems:delete";
          });
          this.permission.typeCreate = listPermissionRespData.data.find(
            item => {
              return item.permission === "flowitems:typeCreate";
            }
          );
          this.permission.typeUpdate = listPermissionRespData.data.find(
            item => {
              return item.permission === "flowitems:typeUpdate";
            }
          );
          this.permission.typeDelete = listPermissionRespData.data.find(
            item => {
              return item.permission === "flowitems:typeDelete";
            }
          );
        } else {
          this.showMessage(
            listPermissionRespData.code != 100
              ? listPermissionRespData
              : treeFlowTypeRespData
          );
        }
        this.resetLoad();
      } catch (error) {
        this.outputError(error);
      }
    },
    onView(scope) {
      this.$refs.editForm.$emit("openViewDialog", scope.row.id);
    },

    onCreate() {
      if (validatenull(this.parentTable)) {
        this.$alert("请选择左侧列表", "提示", {
          confirmButtonText: "确定",
          type: "info"
        });
        return;
      }
      if (
        this.parentTable.uqflowtypeid == "00000000-0000-0000-0000-000000000000"
      ) {
        this.$alert("只能在末级分类下新增项目，请重新选择分类！", "提示", {
          confirmButtonText: "确定",
          type: "info"
        });
        return;
      }
      let row = {
        uqflowtypeid: {
          uqflowtypeid: this.parentTable.uqflowtypeid,
          varcode: this.parentTable.varcode,
          varname: this.parentTable.varname
        }
      };
      this.$refs.editForm.$emit("openAddDialog", row);
    },
    onEdit(scope) {
      this.$refs.editForm.$emit("openEditDialog", scope.row.id);
    },
    onCopy(scope) {
      this.$refs.editForm.$emit("openCopyDialog", scope.row.id);
    },
    onDelete(scope) {
      this.$confirm("确定删除吗？", "确认", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.setLoad();
          deleteFlowitems(scope.row)
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
    openOrClose(row, status) {
      row.intstatus = status;
      this.setLoad();
      saveFlowitems(row)
        .then(responseData => {
          if (responseData.code == 100) {
            this.showMessage({
              type: "success",
              msg: "操作成功"
            });
            this.getList();
          } else {
            this.showMessage(responseData);
          }
          this.resetLoad();
        })
        .catch(error => {
          this.outputError(error);
        });
    },
    onCreateFlowType(node, row) {
      this.$refs.editFlowTypeForm.$emit("openAddDialog", row);
    },
    onUpdateFlowType(node, row) {
      this.$refs.editFlowTypeForm.$emit("openEditDialog", row.id);
    },
    onDeleteFlowType(node, row) {
      let search = {
        params: [
          {
            columnName: "uqflowtypeid",
            queryType: "=",
            value: row.uqflowtypeid
          }
        ]
      };
      this.setLoad();
      listFlowitemsAll(search)
        .then(responseData => {
          if (responseData.code == 100) {
            let tableData = responseData.data ? responseData.data : []; // 判断当前类别下是否存在项目
            this.$confirm("确定删除吗？", "确认", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning"
            }).then(() => {
              if (validatenull(tableData)) {
                // 可删除
                this.doDeleteFlowType(row);
              } else {
                this.$alert(
                  "删除失败: 该现金流量类别存在现金流量项目",
                  "提示",
                  {
                    confirmButtonText: "确定",
                    type: "info"
                  }
                );
              }
            });
          } else {
            this.showMessage(responseData);
          }
          this.resetLoad();
        })
        .catch(error => {
          this.outputError(error);
        });
    },
    doDeleteFlowType(row) {
      deleteFlowtype(row)
        .then(responseData => {
          if (responseData.code == 100) {
            this.treeFlowTypeList();
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
    },
    onChangeCurrentRow(currentRow, oldCurrentRow) {
      if (currentRow != this.parentTable) {
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
