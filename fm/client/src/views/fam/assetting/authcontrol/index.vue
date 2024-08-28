<template>
  <el-row :gutter="15">
    <el-col :span="8">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>管理员</span>
        </div>
        <el-row class="dc-container" v-loading="">
          <edit-form
            ref="editForm"
            @onGetData="getCheckData"
            :permission="permission"
          ></edit-form>
          <el-row :gutter="15" type="flex" align="top" class="dc-el-row_ElRow">
            <el-col :span="15">
              <el-button
                type="primary"
                icon="el-icon-plus"
                v-on:click="onCreate"
                >添加</el-button
              >
              <el-button
                style="margin-left: 20px"
                type="danger"
                icon="el-icon-delete-solid"
                v-on:click="onDelete"
                >删除</el-button
              >
            </el-col>
          </el-row>
          <ux-grid
            column-key
            ref="table"
            :data="tableData"
            border
            v-on:sort-change="onSortChange"
            row-key="id"
            default-expand-all
            highlight-current-row
            @current-change="handleCurrentChange"
            :tree-config="{
              children: '',
              indent: 20,
              accordion: false,
              line: false,
              showIcon: true,
              iconOpen: '',
              iconClose: '',
            }"
            class="dc-ux-grid_QueryTable"
          >
            <ux-table-column
              :field="tableColumns['118'].field"
              :title="tableColumns['118'].title"
              :width="tableColumns['118'].width"
              :visible="tableColumns['118'].visible"
              :params="{ sortId: '118', summary: false }"
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
              :field="tableColumns['119'].field"
              :title="tableColumns['119'].title"
              :width="tableColumns['119'].width"
              :visible="tableColumns['119'].visible"
              :params="{ sortId: '119', summary: false }"
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
              :field="tableColumns['120'].field"
              :title="tableColumns['120'].title"
              :width="tableColumns['120'].width"
              :visible="tableColumns['120'].visible"
              :params="{ sortId: '120', summary: false }"
              tree-node
              resizable
              min-width="180px"
              align="center"
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
            :page-sizes="[10, 20, 30, 40, 50, 100]"
            layout="total, sizes, prev, pager, next, jumper"
            v-on:size-change="onSizeChange"
            v-on:current-change="onCurrentChange"
            class="dc-el-pagination_ElPagination"
          ></el-pagination>
        </el-row>
      </el-card>
    </el-col>
    <el-col :span="8">
      <el-row>
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>操作权限配置</span>
            <el-button
              style="float: right"
              type="primary"
              icon="el-icon-plus"
              v-on:click="onCreate1"
              >保存</el-button
            >
          </div>
          <el-form
            ref="elForm"
            :model="formData"
            :rules="rules"
            size="medium"
            label-width="100px"
          >
            <el-col :span="24">
              <el-form-item
                label-width="200px"
                label="卡片新增（含数据导入）"
                prop="switch_1"
                required
              >
                <el-switch
                  v-model="formData.switch_1"
                  :active-value="0"
                  :inactive-value="1"
                ></el-switch>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item
                label-width="200px"
                label="卡片减少 - 报废"
                prop="switch_2"
                required
              >
                <el-switch
                  v-model="formData.switch_2"
                  :active-value="0"
                  :inactive-value="1"
                ></el-switch>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item
                label-width="200px"
                label="卡片审核 - 审核 、反审核"
                prop="switch_3"
                required
              >
                <el-switch
                  v-model="formData.switch_3"
                  :active-value="0"
                  :inactive-value="1"
                ></el-switch>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item
                label-width="200px"
                label="卡片调整 - 调整"
                prop="switch_4"
                required
              >
                <el-switch
                  v-model="formData.switch_4"
                  :active-value="0"
                  :inactive-value="1"
                ></el-switch>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item
                label-width="200px"
                label="卡片调拨 - 调拨"
                prop="switch_5"
                required
              >
                <el-switch
                  v-model="formData.switch_5"
                  :active-value="0"
                  :inactive-value="1"
                ></el-switch>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item
                label-width="200px"
                label="资产盘点 - 创建任务"
                prop="switch_6"
                required
              >
                <el-switch
                  v-model="formData.switch_6"
                  :active-value="0"
                  :inactive-value="1"
                ></el-switch>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item
                label-width="200px"
                label="每月折旧"
                prop="switch_7"
                required
              >
                <el-switch
                  v-model="formData.switch_7"
                  :active-value="0"
                  :inactive-value="1"
                ></el-switch>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item
                label-width="200px"
                label="补提冲减"
                prop="switch_8"
                required
              >
                <el-switch
                  v-model="formData.switch_8"
                  :active-value="0"
                  :inactive-value="1"
                ></el-switch>
              </el-form-item>
            </el-col>
          </el-form>
        </el-card>
      </el-row>
    </el-col>
    <el-col :span="8">
      <el-row>
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>职权范围配置</span>
          </div>
          <el-row :gutter="15" type="flex" align="top" class="dc-el-row_ElRow">
            <el-col :span="4">
              <el-button
                type="primary"
                icon="el-icon-plus"
                v-on:click="onCreate3"
                >保存</el-button
              >
            </el-col>
          </el-row>
          <el-table
            ref="multipleTable"
            :data="tableData3"
            tooltip-effect="dark"
            style="width: 100%"
            @selection-change="handleSelectionChange"
          >
            <el-table-column type="selection" width="55"> </el-table-column>
            <el-table-column prop="name" label="名称" width="120">
            </el-table-column>
            <el-table-column prop="code" label="编号" show-overflow-tooltip>
            </el-table-column>
          </el-table>
        </el-card>
      </el-row>
    </el-col>
  </el-row>
</template>
<script>
import { validatenull } from "@/utils/validate";

import {
  listTfamAssetManagerPage,
  deleteTfamAssetManager,
  getlistmenu,
  addmenu,
} from "@/api/assetting/tfamAssetManager";
import {
  listTfamAssetManagerDeptAll,
  adddept,
} from "@/api/assetting/tfamAssetManagerDept";
import { listDepartmentAll } from "@/api/org/department";

import { listResourcePermission } from "@/api/admin/common/permission";

/** 根据用户界面配置import组件 开始 */
import History from "@/views/components/history";
import EditForm from "./form.vue";
import QueryTag from "@/views/components/queryTag";
import QueryConditionPanel from "@/views/components/queryConditionPanel";
import ViewColumnsSelect from "@/views/components/DcViewColumnsSelect";
/** 根据用户界面配置import组件 结束 */
import metadata from "./metadata";
import ExportExcelButton from "@/components/ExportExcelButton";
import DcMain from "@/views/components/dcMain";
import OperationIcon from "@/components/OperationIcon";

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
  },
  data() {
    return {
      permission: {
        view: false,
        add: false,
        edit: false,
        remove: false,
        export: false,
      },
      metadata,

      /** 根据用户界面配置生成data数据 开始 */
      queryFormData: {},
      tableData: [],
      tableData3: [],
      // 查询表格列头
      tableColumns: {
        118: {
          title: "管理员",
          field: "userid.name",
          visible: true,
          order: 0,
        },
        119: {
          title: "工号",
          field: "userid.id",
          visible: false,
          order: 1,
        },
        120: {
          title: "部门",
          field: "userid.department.name",
          visible: true,
          order: 2,
        },
      },
      // 分页属性
      tableDataPage: 1,
      tableDataTotal: 0,
      // 选项变量
      currentRow: null,
      /** 根据用户界面配置生成data数据 结束 */
      version: 2,
      search: {
        // 查询条件   业务表设置的筛选条件
        params: [],

        offset: 0,
        limit: 10,
        columnName: "", // 排序字段名
        order: "", // 排序
      },
      search3: {
        // 查询条件   业务表设置的筛选条件
        params: [],

        offset: 0,
        limit: 10,
        columnName: "", // 排序字段名
        order: "", // 排序
      },
      searchList: [],
      tableId: "1641034040897356567",
      schemeId: "1641034040897356565",
      formData: {
        field114: 1,
        switch_1: 1,
        switch_2: 1,
        switch_3: 1,
        switch_4: 1,
        switch_5: 1,
        switch_6: 1,
        switch_7: 1,
        switch_8: 1,
        userid: null,
      },
      multipleSelection: [],
    };
  },
  computed: {},
  methods: {
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    getCheckData() {
      this.getList();
    },
    getList() {
      this.setLoad();
      /* 查询参数 和数据权限 */
      this.search.params = [];
      if (this.isQueryConditionPanel) {
        this.search.params = this.search.params.concat(
          this.compositeCondition()
        );
      } else {
      }
      // 数据权限: 卡片管理tfam_asset_manager
      this.pushDataPermissions(
        this.search.params,
        this.$route.meta.routerId,
        this.tableId
      );
      listTfamAssetManagerPage(this.search)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.tableDataTotal = responseData.data.total;
            this.tableData = responseData.data.rows
              ? responseData.data.rows
              : [];
            this.currentRow = null;
          } else {
            this.showMessage(responseData);
          }
          this.resetLoad();
        })
        .catch((error) => {
          this.outputError(error);
        });
    },
    getList3() {
      this.setLoad();
      /* 查询参数 和数据权限 */
      this.search3.params = [];
      this.search3.params.push({
        columnName: "company_id",
        queryType: "=",
        value: currentUser.company.id == "0" ? "" : currentUser.company.id,
      });
      // 数据权限: 卡片管理tfam_asset_manager
      listDepartmentAll(this.search3)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.tableData3 = responseData.data;
          } else {
            this.showMessage(responseData);
          }
          this.resetLoad();
        })
        .catch((error) => {
          this.outputError(error);
        });
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
      this.initOptions(this.queryModel);
      this.search.offset = 0;
      this.tableDataPage = 1;
      this.getList();
    },
    onView(scope) {
      this.$refs.editForm.$emit("openViewDialog", scope.row.id);
    },

    onCreate() {
      this.$refs.editForm.$emit("openAddDialog");
    },
    onCreate1() {
      if (this.currentRow == null) {
        this.showMessage({
          type: "success",
          msg: "请选择管理员",
        });
        return;
      } else {
        this.formData.userid = this.currentRow.userid.id;
        addmenu(this.formData)
          .then((responseData) => {
            if (responseData.code == 100) {
              this.showMessage({
                type: "success",
                msg: "保存成功",
              });
            } else {
              this.showMessage(responseData);
            }
            this.resetLoad();
          })
          .catch((error) => {
            this.outputError(error);
          });
      }
    },
    onCreate3() {
      if (this.currentRow == null) {
        this.showMessage({
          type: "success",
          msg: "请选择管理员",
        });
        return;
      } else {
        let formadata = {
          userid: this.currentRow.userid.id,
          multipleSelection: this.multipleSelection,
        };
        adddept(formadata)
          .then((responseData) => {
            if (responseData.code == 100) {
              this.showMessage({
                type: "success",
                msg: "保存成功",
              });
            } else {
              this.showMessage(responseData);
            }
            this.resetLoad();
          })
          .catch((error) => {
            this.outputError(error);
          });
      }
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
        type: "warning",
      })
        .then(() => {
          this.setLoad();
          deleteTfamAssetManager(this.currentRow)
            .then((responseData) => {
              if (responseData.code == 100) {
                this.getList();
                this.showMessage({
                  type: "success",
                  msg: "删除成功",
                });
              } else {
                this.showMessage(responseData);
              }
              this.resetLoad();
            })
            .catch((error) => {
              this.outputError(error);
            });
        })
        .catch(() => {});
    },
    handleCurrentChange(val) {
      this.currentRow = val;
      getlistmenu(val.userid.id)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.formData = {
              field114: 1,
              switch_1: 1,
              switch_2: 1,
              switch_3: 1,
              switch_4: 1,
              switch_5: 1,
              switch_6: 1,
              switch_7: 1,
              switch_8: 1,
              userid: null,
            };
            var data = responseData.data;
            for (let key in data) {
              this.formData[key] = data[key];
              // console.log(key, data[key]);
            }
          } else {
            this.showMessage(responseData);
          }
          this.resetLoad();
        })
        .catch((error) => {
          this.outputError(error);
        });
      var search6 = {
        // 查询条件   业务表设置的筛选条件
        params: [],

        offset: 0,
        limit: 10,
        columnName: "", // 排序字段名
        order: "", // 排序
      };
      search6.params.push({
        columnName: "company_id",
        queryType: "=",
        value: currentUser.company.id,
      });
      search6.params.push({
        columnName: "userid",
        queryType: "=",
        value: val.userid.id,
      });
      listTfamAssetManagerDeptAll(search6)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.$refs.multipleTable.clearSelection();
            var data = responseData.data;
            for (let i = 0; i < data.length; i++) {
              for (let j = 0; j < this.tableData3.length; j++) {
                if (data[i].deptid === this.tableData3[j].id) {
                  this.$refs.multipleTable.toggleRowSelection(
                    this.tableData3[j]
                  );
                }
              }
            }
          } else {
            this.showMessage(responseData);
          }
          this.resetLoad();
        })
        .catch((error) => {
          this.outputError(error);
        });
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
    initOptions(This) {},
  },
  watch: {},
  mounted() {
    this.pageInit();
    this.getList3();
    this.columnDrop(this.$refs.table);
  },
};
</script>
