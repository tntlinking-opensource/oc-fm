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
      <el-col :span="24">
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
                v-on:click="$refs.queryForm.resetFields()"
                type="primary"
                icon="el-icon-refresh-right"
              ></el-button>
            </el-tooltip>
          </el-button-group>
        </div>
      </el-col>
    </el-row>
    <el-form
      ref="queryForm"
      :model="queryFormData"
      label-width="100px"
      class="dc-el-form_ElQueryForm"
    >
      <el-row>
        <el-col :span="6">
          <el-form-item
            prop="source_type"
            label="操作类型"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="queryFormData.source_type"
              :style="{ width: '100%' }"
              placeholder="请选择资产类别"
              clearable
              value-key="id"
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in categoryOptions"
                :key="index"
                :label="item.name"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            prop="timeRange"
            label="时间范围"
            class="dc-el-form-item_PickerTimeDateRange"
          >
            <el-date-picker
              v-model="timeRange"
              :style="{ width: '100%' }"
              type="datetimerange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              format="yyyy-MM-dd hh:mm:ss"
              value-format="yyyy-MM-dd hh:mm:ss"
              class="dc-el-date-picker_PickerTimeDateRange"
              @change="ChangeTimeRange"
            ></el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <ux-grid
      column-key
      ref="adjusttable"
      :data="adjustArr"
      border
      v-on:header-dragend="onWidthChange($refs.table)"
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
        :params="{ sortId: '148', summary: false }"
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
        :field="adjusttableColumns['129'].field"
        :title="adjusttableColumns['129'].title"
        :width="adjusttableColumns['129'].width"
        :visible="adjusttableColumns['129'].visible"
        :params="{ sortId: '129', summary: false }"
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
        :field="adjusttableColumns['130'].field"
        :title="adjusttableColumns['130'].title"
        :width="adjusttableColumns['130'].width"
        :visible="adjusttableColumns['130'].visible"
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
        :field="adjusttableColumns['131'].field"
        :title="adjusttableColumns['131'].title"
        :width="adjusttableColumns['131'].width"
        :visible="adjusttableColumns['131'].visible"
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
        :field="adjusttableColumns['132'].field"
        :title="adjusttableColumns['132'].title"
        :width="adjusttableColumns['132'].width"
        :visible="adjusttableColumns['132'].visible"
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
        :field="adjusttableColumns['133'].field"
        :title="adjusttableColumns['133'].title"
        :width="adjusttableColumns['133'].width"
        :visible="adjusttableColumns['133'].visible"
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
        :field="adjusttableColumns['134'].field"
        :title="adjusttableColumns['134'].title"
        :width="adjusttableColumns['134'].width"
        :visible="adjusttableColumns['134'].visible"
        :params="{ sortId: '134', summary: false }"
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
        :field="adjusttableColumns['137'].field"
        :title="adjusttableColumns['137'].title"
        :width="adjusttableColumns['137'].width"
        :visible="adjusttableColumns['137'].visible"
        :params="{ sortId: '137', summary: false }"
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
        title="操作"
        :params="{ sortId: '113' }"
        tree-node
        resizable
        width="60px"
        min-width="40px"
        fixed="right"
        align="center"
        header-align="center"
        class="dc-ux-table-column_ElTableOptColumn"
      >
        <template>
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
          </el-popover>
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
        </template>
      </ux-table-column>
    </ux-grid>
    <el-pagination
      :total="adjustDataTotal"
      :page-size="adjustsearch.limit"
      background
      :current-page="adjustDataPage"
      :page-sizes="[5, 10, 20, 30, 40, 50, 100]"
      layout="total, sizes, prev, pager, next, jumper"
      v-on:size-change="onAdjustSizeChange"
      v-on:current-change="onAdjustCurrentChange"
      class="dc-el-pagination_ElPagination"
    ></el-pagination>
  </el-row>
</template>
<script>
import { validatenull } from "@/utils/validate";
import { listResourcePermission } from "@/api/admin/common/permission";
/** 根据用户界面配置import组件 开始 */
import History from "@/views/components/history";
import EditForm from "./form";
import QueryTag from "@/views/components/queryTag";
import QueryConditionPanel from "@/views/components/queryConditionPanel";
import { listTfamAlterPage } from "@/api/fixasset/alter/tfamAlter.js";
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
      adjustArr: [],
      // 查询表格列头
      adjusttableColumns: {
        148: {
          title: "序号",
          isShow: true,
          visible: true,
          order: 0,
        },
        129: {
          title: "变动日期",
          field: "updateDate",
          visible: true,
          order: 2,
        },
        130: {
          title: "变动名称",
          field: "alterType",
          visible: true,
          order: 3,
        },
        131: {
          title: "变动原因",
          field: "reason",
          visible: true,
          order: 4,
        },
        132: {
          title: "变动前值",
          field: "oldValue",
          visible: true,
          order: 5,
        },
        133: {
          title: "变动后值",
          field: "newValue",
          visible: true,
          order: 6,
        },
        134: {
          title: "变动者",
          field: "updateBy",
          visible: true,
          order: 7,
        },
        137: {
          title: "变动关联凭证号",
          field: "voucherid",
          visible: true,
          order: 8,
        },
        113: {
          title: "操作",
          width: "160px",
          visible: true,
          order: 14,
        },
      },
      // 调整记录分页属性
      adjustDataPage: 1,
      adjustDataTotal: 0,
      adjustsearch: {
        // 查询条件   业务表设置的筛选条件
        params: [],
        offset: 0,
        limit: 10,
        columnName: "", // 排序字段名
        order: "", // 排序
      },

      permission: {
        view: false,
        add: false,
        edit: false,
        remove: false,
        export: false,
      },
      metadata,
      /** 根据用户界面配置生成data数据 开始 */
      categoryOptions: [
        {
          name: "卡片新增",
          value: "1",
        },
        {
          name: "卡片减少",
          value: "2",
        },
        {
          name: "卡片调整",
          value: "3",
        },
        {
          name: "卡片调拨",
          value: "4",
        },
      ],
      timeRange: "",
      queryFormData: {
        source_type: null,
        create_date: null,
        end_date: null,
      },
    };
  },
  computed: {},
  methods: {
    async pageInit() {
      this.setLoad();
      this.onSearch("1");
      listResourcePermission(this.$route.meta.routerId)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.permission.view = responseData.data.find((item) => {
              return item.permission === "Operation:read";
            });
            this.permission.export = responseData.data.find((item) => {
              return item.permission === "Operation:export";
            });
            this.permission.add = responseData.data.find((item) => {
              return item.permission === "Operation:create";
            });
            this.permission.edit = responseData.data.find((item) => {
              return item.permission === "Operation:update";
            });
            this.permission.remove = responseData.data.find((item) => {
              return item.permission === "Operation:delete";
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
    onView(scope) {
      console.log(scope, "查询", scope.row.assetId);
      this.$refs.editForm.$emit("openViewDialog", scope.row.assetId);
    },

    onAdjustSizeChange(val) {
      this.adjustDataPage = 1;
      this.adjustsearch.limit = val;
      this.adjustsearch.offset = (this.adjustDataPage - 1) * val;
      this.onSearch();
    },
    onAdjustCurrentChange(val) {
      this.adjustsearch.offset = (val - 1) * this.adjustsearch.limit;
      this.adjustDataPage = val;
      this.onSearch();
    },

    ChangeTimeRange() {
      console.log(this.timeRange, "======");
      if (this.timeRange && this.timeRange.length > 0) {
        this.queryFormData.create_date = this.timeRange[0];
        this.queryFormData.end_date = this.timeRange[1];
      } else {
        this.queryFormData.create_date = null;
        this.queryFormData.end_date = null;
      }
    },

    // 查看调整记录
    onSearch(code) {
      if (code == "1") {
        // 第一次查询
        this.adjustsearch.offset = 0;
        this.adjustDataPage = 1;
      }
      this.setLoad();

      this.adjustsearch.params = [
        {
          columnName: "source_type",
          queryType: "=",
          value: !validatenull(this.queryFormData.source_type)
            ? this.queryFormData.source_type
            : null,
        },
        {
          columnName: "create_date",
          queryType: ">=",
          value: !validatenull(this.queryFormData.create_date)
            ? this.queryFormData.create_date
            : null,
        },
        {
          columnName: "create_date",
          queryType: "<=",
          value: !validatenull(this.queryFormData.end_date)
            ? this.queryFormData.end_date
            : null,
        },
      ];
      // this.adjustsearch.params.push(...this.queryFormData);
      // console.log("查看调整记录====查询条件", this.adjustsearch);
      listTfamAlterPage(this.adjustsearch).then((responseData) => {
        if (responseData.code == 100) {
          this.adjustDataTotal = responseData.data.total;
          this.adjustArr = responseData.data.rows ? responseData.data.rows : [];
          console.log("调整记录", responseData);
          this.resetLoad();
        } else {
          this.showMessage(responseData);
          this.resetLoad();
        }
      });
    },
  },
  watch: {},
  mounted() {
    this.pageInit();
  },
};
</script>
