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
      justify="start"
      align="top"
      class="dc-el-row_ElRow"
    >
      <el-col :span="24">
        <query-tag
          ref="queryTag"
          v-model="queryEditObject"
          :moreParm="moreParm"
          :routerId="$route && $route.meta.routerId"
          :isQueryConditionPanel="isQueryConditionPanel"
          v-on:queryTagPanel="onQueryTagPanel"
          v-on:queryTagClick="onQueryTagClick"
          v-on:search="onSearch"
          class="dc-query-tag_ElConditionTag"
        ></query-tag>
      </el-col>
      <el-col :span="6">
        <div style="text-align: right">
          <el-button-group class="dc-el-button-group_ButtonGroup">
            <el-tooltip effect="dark" content="搜索" placement="top">
              <el-button
                v-on:click="onSearch"
                type="primary"
                icon="el-icon-search"
              ></el-button>
            </el-tooltip>
            <el-tooltip effect="dark" content="重置" placement="top">
              <el-button
                v-on:click="
                  isQueryConditionPanel
                    ? $refs.conditionPanel.reset()
                    : $refs.queryForm.resetFields()
                "
                type="primary"
                icon="el-icon-refresh-right"
              ></el-button>
            </el-tooltip>
            <el-tooltip effect="dark" content="更多" placement="top">
              <el-button
                v-on:click="onQueryConditionPanel('query')"
                type="primary"
                icon="el-icon-d-arrow-right"
              ></el-button>
            </el-tooltip>
          </el-button-group>
        </div>
      </el-col>
    </el-row>
    <query-condition-panel
      v-if="isQueryConditionPanel"
      ref="conditionPanel"
      :conditionColumns="
        metadata.find((item) => {
          return item.type == 'main';
        }).conditionPanel
      "
      v-model="moreParm"
      :routerId="$route.meta.routerId"
      class="dc-query-condition-panel_ElConditionPanel"
    ></query-condition-panel>
    <el-form
      ref="queryForm"
      :model="queryFormData"
      label-width="100px"
      v-if="!isQueryConditionPanel"
      class="dc-el-form_ElQueryForm"
    >
      <el-row>
        <el-col :span="6">
          <el-form-item
            prop="taskName"
            label="任务名称"
            class="dc-el-form-item_SingleInput"
          >
            <el-input
              v-model="queryFormData.taskName"
              :maxLength="255"
              placeholder="请输入计划名称"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="already_num"
            label="执行状态"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="queryFormData.already_num"
              :style="{ width: '100%' }"
              placeholder="请选择执行状态"
              clearable
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in alreadyOptions"
                :key="index"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="status"
            label="任务状态"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="queryFormData.status"
              :style="{ width: '100%' }"
              placeholder="请选择任务状态"
              clearable
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in statusOptions"
                :key="index"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <el-row
      gutter="0"
      type="flex"
      justify="end"
      align="top"
      class="dc-el-row_ElRow"
    >
      <el-tooltip v-show="permission.add" disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-plus" v-on:click="onCreate"
          >新建任务</el-button
        >
      </el-tooltip>
      <!-- <el-tooltip v-show="permission.add" disabled class="dc-el-tooltip_Button">
        <el-button
          type="primary"
          icon="el-icon-edit-outline"
          v-on:click="onCreate"
          >修改任务</el-button
        >
      </el-tooltip> -->
      <el-tooltip v-show="permission.add" disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-document" v-on:click="onDetail"
          >盘点详情</el-button
        >
      </el-tooltip>
      <el-tooltip v-show="permission.add" disabled class="dc-el-tooltip_Button">
        <el-button
          type="primary"
          icon="el-icon-edit-outline"
          v-on:click="openTaskstatus"
          >修改状态</el-button
        >
      </el-tooltip>
      <!-- <el-tooltip disabled class="dc-el-tooltip_Button">
        <el-button
          type="primary"
          icon="el-icon-delete"
          class="dc-el-button_Button"
          >删除</el-button
        >
      </el-tooltip> -->
    </el-row>
    <ux-grid
      column-key
      ref="table"
      :data="tableData"
      border
      v-on:sort-change="onSortChange"
      v-on:header-dragend="onWidthChange($refs.table)"
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
        title="序号"
        :params="{ sortId: '146', summary: false }"
        type="index"
        tree-node
        resizable
        min-width="80px"
        fixed="left"
        align="center"
        header-align="center"
        class="dc-ux-table-column_ElTableNumColumn"
      ></ux-table-column>
      <ux-table-column
        :params="{ sortId: '147', summary: false }"
        type="checkbox"
        tree-node
        resizable
        min-width="80px"
        fixed="left"
        align="center"
        header-align="center"
        class="dc-ux-table-column_ElTableCheckBoxColumn"
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
        align="center"
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
        min-width="180px"
        align="center"
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
        min-width="180px"
        align="center"
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
        min-width="180px"
        align="center"
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
        min-width="120px"
        align="center"
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
        align="right"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['162'].field"
        :title="tableColumns['162'].title"
        :width="tableColumns['162'].width"
        :visible="tableColumns['162'].visible"
        :params="{ sortId: '162', summary: false }"
        tree-node
        resizable
        min-width="120px"
        align="center"
        header-align="center"
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['164'].field"
        :title="tableColumns['164'].title"
        :width="tableColumns['164'].width"
        :visible="tableColumns['164'].visible"
        :params="{ sortId: '164', summary: false }"
        tree-node
        resizable
        min-width="120px"
        align="center"
        header-align="center"
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['163'].field"
        :title="tableColumns['163'].title"
        :width="tableColumns['163'].width"
        :visible="tableColumns['163'].visible"
        :params="{ sortId: '163', summary: false }"
        tree-node
        resizable
        min-width="120px"
        align="center"
        header-align="center"
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['165'].field"
        :title="tableColumns['165'].title"
        :width="tableColumns['165'].width"
        :visible="tableColumns['165'].visible"
        :params="{ sortId: '165', summary: false }"
        tree-node
        resizable
        min-width="120px"
        align="center"
        header-align="center"
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
                switch (row.status * 1) {
                  case 0:
                    return '开启';
                  case 1:
                    return '关闭';
                  default:
                    return '';
                }
              })(row.isbalance, row, rowIndex)
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
        align="center"
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
          <!-- <OperationIcon
            v-on:click="onExport(tableData)"
            type="primary"
            v-show="permission.export"
            content="导出excel文件"
            placement="top"
            icon-name="el-icon-download"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon> -->
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
          <!-- <OperationIcon
            v-on:click="onCopy(scope)"
            type="primary"
            v-show="permission.add"
            content="复制"
            placement="top"
            icon-name="el-icon-document"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon> -->
          <OperationIcon
            v-on:click="onDelete(scope)"
            type="danger"
            v-show="permission.remove"
            content="删除"
            placement="top"
            icon-name="el-icon-delete"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <!-- <OperationIcon
            v-on:click="onShowHistory(scope)"
            type="info"
            v-show="permission.view"
            content="历史记录"
            placement="top"
            icon-name="el-icon-info"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon> -->
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

    <!-- 附件弹框 -->
    <el-dialog title="状态" width="30%" :visible.sync="dialogtaskVisible">
      <el-col :span="24">
        <el-radio v-model="taskradio" label="0">开启</el-radio>
        <el-radio v-model="taskradio" label="1">关闭</el-radio>
      </el-col>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogtaskVisible = false">取 消</el-button>
        <el-button type="primary" @click="onChangeTaskstatus">确 定</el-button>
      </div>
    </el-dialog>
  </el-row>
</template>
<script>
import { validatenull } from "@/utils/validate";

import {
  listTfamAssetCheckplanMainPage,
  deleteTfamAssetCheckplanMain,
  saveTfamAssetCheckplanMain,
  getTfamAssetCheckplandetail,
} from "@/api/checkplan/checkplanmain/tfamAssetCheckplanMain";

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
      params: null,
      taskradio: "", // 任务状态
      dialogtaskVisible: false,
      permission: {
        view: false,
        add: false,
        edit: false,
        remove: false,
        export: false,
      },
      metadata,

      /** 根据用户界面配置生成data数据 开始 */
      queryFormData: {
        taskName: "", // 任务名称
        already_num: null, // 执行状态
        status: null, // 任务状态
      },
      tableData: [],
      // 查询表格列头
      tableColumns: {
        146: {
          title: "序号",
          isShow: true,
          visible: true,
          order: 0,
        },
        147: {
          isShow: true,
          visible: true,
          order: 1,
        },
        129: {
          title: "任务名称",
          field: "taskName",
          visible: true,
          order: 2,
        },
        130: {
          title: "表单号",
          field: "applyNo",
          visible: true,
          order: 3,
        },
        131: {
          title: "登记日期",
          field: "registryDate",
          visible: true,
          order: 4,
        },
        132: {
          title: "开始时间",
          field: "startDate",
          visible: true,
          order: 5,
        },
        133: {
          title: "线束日期",
          field: "endDate",
          visible: true,
          order: 6,
        },
        134: {
          title: "登记人",
          field: "registryPerson.name",
          visible: true,
          order: 7,
        },
        135: {
          title: "资产总数量",
          field: "totalAsset",
          visible: true,
          order: 8,
        },
        162: {
          title: "已盘点数量",
          field: "alreadyNum",
          visible: true,
          order: 9,
        },
        164: {
          title: "完成进度",
          field: "percentage",
          visible: true,
          order: 10,
        },
        163: {
          title: "附件数量",
          field: "attachNum",
          visible: true,
          order: 11,
        },
        165: {
          title: "执行状态",
          field: "excuteStatus",
          visible: true,
          order: 12,
        },
        140: {
          title: "状态",
          field: "status",
          visible: true,
          order: 13,
        },
        114: {
          title: "操作",
          width: "140px",
          visible: true,
          order: 14,
        },
      },
      // 分页属性
      tableDataPage: 1,
      tableDataTotal: 0,
      // 选项变量
      // 执行状态选项
      alreadyOptions: [
        {
          label: "未执行",
          value: "0",
        },
        {
          label: "执行中",
          value: "1",
        },
        {
          label: "已结束",
          value: "2",
        },
      ],
      // 任务状态选项
      statusOptions: [
        {
          label: "开启",
          value: "0",
        },
        {
          label: "关闭",
          value: "1",
        },
      ],
      /** 根据用户界面配置生成data数据 结束 */
      version: 11,
      search: {
        // 查询条件   业务表设置的筛选条件
        params: [],

        offset: 0,
        limit: 10,
        columnName: "", // 排序字段名
        order: "", // 排序
      },
      searchList: [],
      tableId: "1641034040897357834",
      schemeId: "1641034040897357832",
      currentRow: null,
    };
  },
  computed: {},
  methods: {
    getList() {
      this.setLoad();
      /* 查询参数 和数据权限 */
      this.search.params = [];
      if (this.isQueryConditionPanel) {
        this.search.params = this.search.params.concat(
          this.compositeCondition()
        );
      } else {
        this.search.params.push({
          columnName: "task_name",
          queryType: "like",

          value: !validatenull(this.queryFormData.taskName)
            ? this.queryFormData.taskName
            : null,
        });

        this.search.params.push({
          columnName: "already_num",
          queryType: "=",

          value: !validatenull(this.queryFormData.already_num)
            ? this.queryFormData.already_num
            : null,
        });

        this.search.params.push({
          columnName: "status",
          queryType: "=",

          value: !validatenull(this.queryFormData.status)
            ? this.queryFormData.status
            : null,
        });
      }
      // 数据权限: 资产盘点计划主表tfam_asset_checkplan_main
      this.pushDataPermissions(
        this.search.params,
        this.$route.meta.routerId,
        this.tableId
      );
      listTfamAssetCheckplanMainPage(this.search)
        .then((responseData) => {
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
        .catch((error) => {
          this.outputError(error);
        });
    },
    onSearch() {
      this.currentRow = null;
      if (this.isQueryConditionPanel) {
        this.search.offset = 0;
        this.tableDataPage = 1;
        this.getList();
      } else {
        this.$refs["queryForm"].validate((valid) => {
          if (valid) {
            this.search.offset = 0;
            this.tableDataPage = 1;
            this.getList();
          } else {
            return false;
          }
        });
      }
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
      this.onSearch();
      listResourcePermission(this.$route.meta.routerId)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.permission.view = responseData.data.find((item) => {
              return item.permission === "checkplanmain:read";
            });
            this.permission.export = responseData.data.find((item) => {
              return item.permission === "checkplanmain:export";
            });

            this.permission.add = responseData.data.find((item) => {
              return item.permission === "checkplanmain:create";
            });
            this.permission.edit = responseData.data.find((item) => {
              return item.permission === "checkplanmain:update";
            });
            this.permission.remove = responseData.data.find((item) => {
              return item.permission === "checkplanmain:delete";
            });
          } else {
            this.showMessage(responseData);
          }
          this.resetLoad();
        })
        .catch((error) => {
          this.outputError(error);
        });
    },
    handleCurrentChange(val) {
      this.currentRow = [val];
      console.log(val, "点击的");
    },
    onView(scope) {
      this.$refs.editForm.$emit("openViewDialog", scope.row.id);
    },

    onDetail() {
      // let checkBoxData = this.$refs.table.getCheckboxRecords();
      let checkBoxData =
        this.$refs.table.getCheckboxRecords().length > 0
          ? this.$refs.table.getCheckboxRecords()
          : this.currentRow;
      if (checkBoxData.length <= 0) {
        this.showMessage({
          type: "warning",
          msg: `请选择一行`,
        });
        return;
      } else if (checkBoxData.length > 1) {
        this.showMessage({
          type: "warning",
          msg: `请单选一行`,
        });
        return;
      }
      this.$refs.editForm.$emit("openDetailDialog", checkBoxData[0].id);
    },

    onCreate() {
      this.$refs.editForm.$emit("openAddDialog");
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
          deleteTfamAssetCheckplanMain(scope.row)
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

    // 修改查询接口信息
    getDetailById(id) {
      this.setLoad();
      return new Promise((resolve) => {
        getTfamAssetCheckplandetail({ id: id })
          .then((responseData) => {
            let form = {};
            if (responseData.code == 100) {
              form = responseData.data;
            } else {
              this.showMessage(responseData);
            }
            this.resetLoad();
            resolve(form);
          })
          .catch((error) => {
            this.outputError(error);
          });
      });
    },

    // 打开任务状态弹框
    async openTaskstatus() {
      this.params = null;
      // let checkBoxData = this.$refs.table.getCheckboxRecords();
      let checkBoxData =
        this.$refs.table.getCheckboxRecords().length > 0
          ? this.$refs.table.getCheckboxRecords()
          : this.currentRow;
      if (checkBoxData.length <= 0) {
        this.showMessage({
          type: "warning",
          msg: `请选择一行`,
        });
        return;
      }
      this.taskradio = checkBoxData[0].status + "";
      this.dialogtaskVisible = true;
      this.params = await this.getDetailById(checkBoxData[0].id);
    },

    // 任务状态弹框提交
    async onChangeTaskstatus() {
      // console.log(this.taskradio, "====", this.params);
      this.setLoad();

      this.params.status = this.taskradio * 1;

      saveTfamAssetCheckplanMain(this.params)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.showMessage({
              type: "success",
              msg: "修改成功",
            });
            this.dialogtaskVisible = false;
          } else {
            this.showMessage(responseData);
          }
          this.resetLoad();
        })
        .catch((error) => {
          this.outputError(error);
        });
      this.resetLoad();
    },
  },
  watch: {},
  mounted() {
    this.pageInit();

    this.columnDrop(this.$refs.table);
  },
};
</script>
