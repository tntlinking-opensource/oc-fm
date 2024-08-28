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
            prop="huizongrange"
            label="资产类别"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="queryFormData.huizongrange"
              :style="{ width: '100%' }"
              placeholder="请选择汇总范围"
              clearable
              value-key="id"
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in huizongOptions"
                :key="index"
                :label="item.name"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="groupCondition"
            label="汇总项目"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="queryFormData.groupCondition"
              :style="{ width: '100%' }"
              placeholder="请选择汇总项目"
              value-key="id"
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in projectOptions"
                :key="index"
                :label="item.name"
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
      justify="start"
      align="top"
      class="dc-el-row_ElRow"
    >
      <el-col :span="10">
        <el-tooltip disabled class="dc-el-tooltip_Button">
          <el-button
            type="primary"
            icon="el-icon-tickets"
            v-on:click="detailListFun('1')"
            class="dc-el-button_Button"
          >
            查看明细
          </el-button>
        </el-tooltip>
        <el-tooltip disabled class="dc-el-tooltip_Button">
          <el-button
            type="primary"
            icon="el-icon-download"
            v-on:click="onExportFun"
            class="dc-el-button_Button"
            >数据导出</el-button
          >
        </el-tooltip>
      </el-col>
      <el-col :span="14">
        <div class="text_box" v-if="countObj">
          <div class="box_item">
            <span class="span1">数量：</span>
            <span class="span2">{{ countObj.num || "" }}</span>
          </div>
          <div class="box_item">
            <span class="span1">原值：</span>
            <span class="span2">￥{{ countObj.value || "" }}</span>
          </div>
          <div class="box_item">
            <span class="span1">累计折旧：</span>
            <span class="span2">￥{{ countObj.accudep || "" }}</span>
          </div>
          <div class="box_item">
            <span class="span1">净值</span>
            <span class="span2">￥{{ countObj.netvalue || "" }}</span>
          </div>
        </div>
      </el-col>
    </el-row>
    <ux-grid
      column-key
      ref="summarytable"
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
        iconClose: '',
      }"
      class="dc-ux-grid_QueryTable"
    >
      <ux-table-column
        title="序号"
        :params="{ sortId: '249', summary: false }"
        type="index"
        tree-node
        resizable
        min-width="50px"
        fixed="left"
        align="center"
        header-align="center"
        class="dc-ux-table-column_ElTableNumColumn"
      ></ux-table-column>
      <ux-table-column
        :params="{ sortId: '250', summary: false }"
        type="checkbox"
        tree-node
        resizable
        min-width="40px"
        fixed="left"
        align="center"
        header-align="center"
        class="dc-ux-table-column_ElTableCheckBoxColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['121'].field"
        :title="tableColumns['121'].title"
        :width="tableColumns['121'].width"
        :visible="tableColumns['121'].visible"
        :params="{ sortId: '121', summary: false }"
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
        :field="tableColumns['127'].field"
        :title="tableColumns['127'].title"
        :width="tableColumns['127'].width"
        :visible="tableColumns['127'].visible"
        :params="{ sortId: '127', summary: false }"
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
        :field="tableColumns['128'].field"
        :title="tableColumns['128'].title"
        :width="tableColumns['128'].width"
        :visible="tableColumns['128'].visible"
        :params="{ sortId: '128', summary: false }"
        tree-node
        resizable
        min-width="180px"
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
                return '¥' + Number(row.value).toFixed(2);
              })(row.isbalance, row, rowIndex)
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
                return '¥' + Number(row.accudep).toFixed(2);
              })(row.isbalance, row, rowIndex)
            "
          ></div>
        </template>
      </ux-table-column>
      <ux-table-column
        :field="tableColumns['140'].field"
        :title="tableColumns['140'].title"
        :width="tableColumns['140'].width"
        :visible="tableColumns['140'].visible"
        :params="{ sortId: '140', summary: false }"
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
                return '¥' + Number(row.netvalue).toFixed(2);
              })(row.isbalance, row, rowIndex)
            "
          ></div>
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
    <!-- 查看明细 -->
    <el-dialog title="查看明细" :visible.sync="dialogDetailVisible" width="90%">
      <ux-grid
        column-key
        ref="detailtable"
        :data="detailArr"
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
          :params="{ sortId: '249', summary: false }"
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
          :field="detailtableColumns['120'].field"
          :title="detailtableColumns['120'].title"
          :width="detailtableColumns['120'].width"
          :visible="detailtableColumns['120'].visible"
          :params="{ sortId: '120', summary: false }"
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
          :field="detailtableColumns['121'].field"
          :title="detailtableColumns['121'].title"
          :width="detailtableColumns['121'].width"
          :visible="detailtableColumns['121'].visible"
          :params="{ sortId: '121', summary: false }"
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
          :field="detailtableColumns['127'].field"
          :title="detailtableColumns['127'].title"
          :width="detailtableColumns['127'].width"
          :visible="detailtableColumns['127'].visible"
          :params="{ sortId: '127', summary: false }"
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
          :field="detailtableColumns['128'].field"
          :title="detailtableColumns['128'].title"
          :width="detailtableColumns['128'].width"
          :visible="detailtableColumns['128'].visible"
          :params="{ sortId: '128', summary: false }"
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
          :field="detailtableColumns['137'].field"
          :title="detailtableColumns['137'].title"
          :width="detailtableColumns['137'].width"
          :visible="detailtableColumns['137'].visible"
          :params="{ sortId: '137', summary: false }"
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
          :field="detailtableColumns['140'].field"
          :title="detailtableColumns['140'].title"
          :width="detailtableColumns['140'].width"
          :visible="detailtableColumns['140'].visible"
          :params="{ sortId: '140', summary: false }"
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
          :field="detailtableColumns['209'].field"
          :title="detailtableColumns['209'].title"
          :width="detailtableColumns['209'].width"
          :visible="detailtableColumns['209'].visible"
          :params="{ sortId: '209', summary: false }"
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
          :field="detailtableColumns['188'].field"
          :title="detailtableColumns['188'].title"
          :width="detailtableColumns['188'].width"
          :visible="detailtableColumns['188'].visible"
          :params="{ sortId: '188', summary: false }"
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
          :field="detailtableColumns['189'].field"
          :title="detailtableColumns['189'].title"
          :width="detailtableColumns['189'].width"
          :visible="detailtableColumns['189'].visible"
          :params="{ sortId: '189', summary: false }"
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
          :field="detailtableColumns['146'].field"
          :title="detailtableColumns['146'].title"
          :width="detailtableColumns['146'].width"
          :visible="detailtableColumns['146'].visible"
          :params="{ sortId: '146', summary: false }"
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
          :field="detailtableColumns['201'].field"
          :title="detailtableColumns['201'].title"
          :width="detailtableColumns['201'].width"
          :visible="detailtableColumns['201'].visible"
          :params="{ sortId: '201', summary: false }"
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
          :field="detailtableColumns['197'].field"
          :title="detailtableColumns['197'].title"
          :width="detailtableColumns['197'].width"
          :visible="detailtableColumns['197'].visible"
          :params="{ sortId: '197', summary: false }"
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
          :field="detailtableColumns['177'].field"
          :title="detailtableColumns['177'].title"
          :width="detailtableColumns['177'].width"
          :visible="detailtableColumns['177'].visible"
          :params="{ sortId: '177', summary: false }"
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
          :field="detailtableColumns['182'].field"
          :title="detailtableColumns['182'].title"
          :width="detailtableColumns['182'].width"
          :visible="detailtableColumns['182'].visible"
          :params="{ sortId: '182', summary: false }"
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
          :field="detailtableColumns['184'].field"
          :title="detailtableColumns['184'].title"
          :width="detailtableColumns['184'].width"
          :visible="detailtableColumns['184'].visible"
          :params="{ sortId: '184', summary: false }"
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
          :field="detailtableColumns['213'].field"
          :title="detailtableColumns['213'].title"
          :width="detailtableColumns['213'].width"
          :visible="detailtableColumns['213'].visible"
          :params="{ sortId: '213', summary: false }"
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
          :field="detailtableColumns['141'].field"
          :title="detailtableColumns['141'].title"
          :width="detailtableColumns['141'].width"
          :visible="detailtableColumns['141'].visible"
          :params="{ sortId: '141', summary: false }"
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
          :field="detailtableColumns['251'].field"
          :title="detailtableColumns['251'].title"
          :width="detailtableColumns['251'].width"
          :visible="detailtableColumns['251'].visible"
          :params="{ sortId: '251', summary: false }"
          tree-node
          resizable
          min-width="180px"
          align="center"
          header-align="center"
          show-overflow
          class="dc-ux-table-column_ElTableColumn"
        ></ux-table-column>
        <ux-table-column
          :field="detailtableColumns['179'].field"
          :title="detailtableColumns['179'].title"
          :width="detailtableColumns['179'].width"
          :visible="detailtableColumns['179'].visible"
          :params="{ sortId: '179', summary: false }"
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
          :field="detailtableColumns['180'].field"
          :title="detailtableColumns['180'].title"
          :width="detailtableColumns['180'].width"
          :visible="detailtableColumns['180'].visible"
          :params="{ sortId: '180', summary: false }"
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
          :field="detailtableColumns['122'].field"
          :title="detailtableColumns['122'].title"
          :width="detailtableColumns['122'].width"
          :visible="detailtableColumns['122'].visible"
          :params="{ sortId: '122', summary: false }"
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
          :field="detailtableColumns['189'].field"
          :title="detailtableColumns['189'].title"
          :width="detailtableColumns['189'].width"
          :visible="detailtableColumns['189'].visible"
          :params="{ sortId: '189', summary: false }"
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
          :field="detailtableColumns['183'].field"
          :title="detailtableColumns['183'].title"
          :width="detailtableColumns['183'].width"
          :visible="detailtableColumns['183'].visible"
          :params="{ sortId: '183', summary: false }"
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
          :field="detailtableColumns['190'].field"
          :title="detailtableColumns['190'].title"
          :width="detailtableColumns['190'].width"
          :visible="detailtableColumns['190'].visible"
          :params="{ sortId: '190', summary: false }"
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
        :total="detailDataTotal"
        :page-size="detailsearch.limit"
        background
        :current-page="detailDataPage"
        :page-sizes="[10, 20, 30, 40, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        v-on:size-change="onDetailSizeChange"
        v-on:current-change="onDetailCurrentChange"
        class="dc-el-pagination_ElPagination"
      ></el-pagination>
      <div slot="top" class="dialog-footer">
        <el-button type="primary" @click="dialogDetailVisible = false"
          >关 闭</el-button
        >
      </div>
    </el-dialog>
  </el-row>
</template>
<script>
import { validatenull } from "@/utils/validate";
import {
  listTfamAssetPage,
  countAssetExport,
} from "@/api/fixasset/asset/tfamAsset";
import { countAssetTransfer } from "@/api/fixasset/asset/tfamAssetTransfer";
import { listResourcePermission } from "@/api/admin/common/permission";
/** 根据用户界面配置import组件 开始 */
import History from "@/views/components/history";
import EditForm from "./form";
import QueryTag from "@/views/components/queryTag";
import QueryConditionPanel from "@/views/components/queryConditionPanel";
import ViewColumnsSelect from "@/views/components/DcViewColumnsSelect";
/** 根据用户界面配置import组件 结束 */
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
    OperationIcon,
  },
  data() {
    return {
      dialogDetailVisible: false,
      checkdata: {},
      detailArr: [],
      // 查询表格列头
      detailtableColumns: {
        249: {
          title: "序号",
          isShow: true,
          visible: true,
          order: 0,
        },
        120: {
          title: "卡片编号",
          field: "cardCode",
          visible: true,
          order: 24,
        },
        121: {
          title: "资产编号",
          field: "assetCode",
          visible: true,
          order: 2,
        },
        127: {
          title: "资产类别",
          field: "category.name",
          visible: true,
          order: 3,
        },
        128: {
          title: "资产名称",
          field: "assetName",
          visible: true,
          order: 4,
        },
        137: {
          title: "使用部门",
          field: "usedeptid.name",
          visible: true,
          order: 5,
        },
        140: {
          title: "责任人",
          field: "userid.name",
          visible: true,
          order: 6,
        },
        209: {
          title: "工号",
          field: "employeeno",
          visible: true,
          order: 7,
        },
        188: {
          title: "预计使用月数",
          field: "naturemonth",
          visible: true,
          order: 8,
        },
        189: {
          title: "剩余使用月数",
          field: "servicemonth",
          visible: true,
          order: 9,
        },
        146: {
          title: "使用状况",
          field: "usingstatus.name",
          visible: true,
          order: 10,
        },
        201: {
          title: "卡片状态",
          field: "status.name",
          visible: true,
          order: 11,
        },
        197: {
          title: "变动方式",
          field: "addReason.name",
          visible: true,
          order: 12,
        },
        177: {
          title: "原值",
          field: "value",
          visible: true,
          order: 13,
        },
        182: {
          title: "净值",
          field: "netvalue",
          visible: true,
          order: 14,
        },
        184: {
          title: "残值",
          field: "salvage",
          visible: true,
          order: 15,
        },
        213: {
          title: "购置日期",
          field: "startupDate",
          visible: true,
          order: 16,
        },
        141: {
          title: "数量",
          field: "num",
          visible: true,
          order: 17,
        },
        251: {
          title: "单位",
          field: "unit",
          visible: true,
          order: 18,
        },
        179: {
          title: "税金",
          field: "tax",
          visible: true,
          order: 19,
        },
        180: {
          title: "累计折旧",
          field: "accudep",
          visible: true,
          order: 20,
        },
        122: {
          title: "折旧方法",
          field: "depmethod.name",
          visible: true,
          order: 21,
        },
        189: {
          title: "卡片性质",
          field: "nature.name",
          visible: true,
          order: 13,
        },
        183: {
          title: "建卡人",
          field: "createBy",
          visible: true,
          order: 22,
        },
        190: {
          title: "建卡日期",
          field: "createDate",
          visible: true,
          order: 23,
        },
      },
      // 调整记录分页属性
      detailDataPage: 1,
      detailDataTotal: 0,
      detailsearch: {
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
      /** 根据用户界面配置生成data数据 开始 */
      countObj: null,
      queryFormData: {
        huizongrange: "",
        groupCondition: "usedeptid",
      },
      huizongOptions: [
        {
          name: "未入账卡片",
          value: "1",
        },
        {
          name: "已入账卡片",
          value: "2",
        },
        {
          name: "本年新增",
          value: "3",
        },
        {
          name: "本年减少",
          value: "4",
        },
      ],
      projectOptions: [
        {
          name: "使用部门",
          value: "usedeptid",
        },
        {
          name: "会计科目",
          value: "asset_accountid",
        },
        {
          name: "资产类别",
          value: "category_id",
        },
        {
          name: "折旧方法",
          value: "depmethod",
        },
        {
          name: "使用状况",
          value: "usingstatus",
        },
      ],
      tableData: [],
      // 查询表格列头
      tableColumns: {
        249: {
          title: "序号",
          isShow: true,
          visible: true,
          order: 0,
        },
        121: {
          title: "汇总项目",
          field: "countItemName",
          visible: true,
          order: 2,
        },
        127: {
          title: "数量",
          field: "num",
          visible: true,
          order: 3,
        },
        128: {
          title: "原值",
          field: "value",
          visible: true,
          order: 4,
        },
        137: {
          title: "累计折旧",
          field: "accudep",
          visible: true,
          order: 5,
        },
        140: {
          title: "净值",
          field: "netvalue",
          visible: true,
          order: 6,
        },
      },
      // 分页属性
      tableDataPage: 1,
      tableDataTotal: 0,
      // 选项变量
      // 资产类别选项
      categoryOptions: [],
      // 在职状态选项
      statusOptions: [
        {
          name: "在职",
          value: "0",
        },
        {
          name: "离职",
          value: "1",
        },
      ],
      // 使用状况选项
      usingstatusOptions: [],
      /** 根据用户界面配置生成data数据 结束 */
      version: 6,
      search: {
        // 查询条件   业务表设置的筛选条件
        // params: {},
        groupCondition: "",
        offset: 0,
        limit: 10,
        columnName: "", // 排序字段名
        order: "", // 排序
      },
      searchList: [],
      tableId: "1633870636743041902",
      schemeId: "1633870636743041900",
    };
  },
  computed: {},
  methods: {
    // 【 待优化分页效果，暂时没有，为了查询在职状态】
    getList() {
      this.setLoad();
      /* 查询参数 和数据权限 */
      // this.search.params = {
      //   groupCondition: this.queryFormData.groupCondition,
      // };
      this.search.groupCondition = this.queryFormData.groupCondition;

      countAssetTransfer(this.search)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.countObj = responseData.data;
            this.tableDataTotal = responseData.data.total;
            this.tableData = responseData.data.countAssetDtos
              ? responseData.data.countAssetDtos
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
      this.$refs["queryForm"].validate((valid) => {
        if (valid) {
          this.search = {
            groupCondition: "",
            offset: 0,
            limit: 10,
            columnName: "", // 排序字段名
            order: "", // 排序
          };
          // this.search.offset = 0;
          this.tableDataPage = 1;
          this.getList();
        } else {
          return false;
        }
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
      // this.initOptions(this.queryModel);
      this.onSearch();
      listResourcePermission(this.$route.meta.routerId)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.permission.view = responseData.data.find((item) => {
              return item.permission === "tfamSummary:read";
            });
            this.permission.export = responseData.data.find((item) => {
              return item.permission === "tfamSummary:export";
            });
            this.permission.add = responseData.data.find((item) => {
              return item.permission === "tfamSummary:create";
            });
            this.permission.edit = responseData.data.find((item) => {
              return item.permission === "tfamSummary:update";
            });
            this.permission.remove = responseData.data.find((item) => {
              return item.permission === "tfamSummary:delete";
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

    onDetailSizeChange(val) {
      this.detailDataPage = 1;
      this.detailsearch.limit = val;
      this.detailsearch.offset = (this.detailDataPage - 1) * val;
      this.detailListFun();
    },
    onDetailCurrentChange(val) {
      this.detailsearch.offset = (val - 1) * this.detailsearch.limit;
      this.detailDataPage = val;
      this.detailListFun();
    },

    // 查看明细
    detailListFun(code) {
      if (code == "1") {
        // 第一次查询
        this.detailsearch.offset = 0;
        this.detailDataPage = 1;
      }
      let checkBoxData = this.$refs.summarytable.getCheckboxRecords();
      if (checkBoxData.length <= 0) {
        this.showMessage({
          type: "warning",
          msg: `请选择需要查看明细的卡片`,
        });
        return;
      } else if (checkBoxData.length > 1) {
        this.showMessage({
          type: "warning",
          msg: "请只选择一张卡片",
        });
        return;
      }
      this.checkdata = checkBoxData[0];
      this.dialogDetailVisible = true;
      this.setLoad();

      this.detailsearch.params = [];
      this.detailsearch.params.push({
        columnName: "usedeptid",
        queryType: "=",

        value: checkBoxData[0].countItemId ? checkBoxData[0].countItemId : null,
      });
      // console.log("查看调整记录====查询条件", this.detailsearch);
      listTfamAssetPage(this.detailsearch).then((responseData) => {
        if (responseData.code == 100) {
          this.detailDataTotal = responseData.data.total;
          this.detailArr = responseData.data.rows ? responseData.data.rows : [];
          // console.log("调整记录", responseData);
          this.resetLoad();
        } else {
          this.resetLoad();
          this.showMessage(responseData);
        }
      });
    },

    // 数据导出
    onExportFun() {
      this.setLoad();
      countAssetExport({ groupCondition: this.queryFormData.groupCondition })
        .then((responseData) => {
          let date = moment().format("YYYY-MM-DD");
          const blob = new Blob([responseData]);
          const url = window.URL.createObjectURL(blob);
          let dom = document.createElement("a");
          dom.style.display = "none";
          dom.href = url;
          dom.setAttribute("download", "卡片汇总表_" + date + ".xls");
          document.body.appendChild(dom);
          dom.click();
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
    this.columnDrop(this.$refs.summarytable);
    // this.columnDrop(this.$refs.detailtable);
  },
};
</script>
<style scoped lang="scss">
.text_box {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: flex-end;
  .box_item {
    margin: 0 10px;
    font-size: 14px;
    color: #101011;
    .span2 {
      color: #018cb7;
      font-weight: bold;
    }
  }
}
.el-dialog__wrapper /deep/ {
  .el-dialog {
    height: 85%;
  }
}
</style>
