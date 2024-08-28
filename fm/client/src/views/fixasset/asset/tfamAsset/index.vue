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
            prop="category"
            label="资产类别"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="queryFormData.category"
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
                :value="item"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="assetCode"
            label="资产编号"
            class="dc-el-form-item_SingleInput"
          >
            <el-input
              v-model="queryFormData.assetCode"
              :maxLength="40"
              placeholder="请输入资产编号"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="assetName"
            label="资产名称"
            class="dc-el-form-item_SingleInput"
          >
            <el-input
              v-model="queryFormData.assetName"
              :maxLength="200"
              placeholder="请输入资产名称"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="usingstatus"
            label="使用状况"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="queryFormData.usingstatus"
              :style="{ width: '100%' }"
              placeholder="请选择使用状况"
              clearable
              value-key="id"
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in usingstatusOptions"
                :key="index"
                :label="item.name"
                :value="item"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="usedeptid"
            label="使用部门"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="queryFormData.usedeptid"
              :style="{ width: '100%' }"
              placeholder="请选择使用部门"
              clearable
              value-key="id"
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in usedeptidOptions"
                :key="index"
                :label="item.name"
                :value="item"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="userid"
            label="责任人"
            class="dc-el-form-item_SingleInput"
          >
            <el-input
              v-model="queryFormData.userid"
              :maxLength="100"
              placeholder="请输入责任人"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="servicemonth"
            label="剩余使用月数"
            class="dc-el-form-item_SingleInput"
          >
            <el-input
              v-model="queryFormData.servicemonth"
              :maxLength="11"
              placeholder="请输入剩余使用月数"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="status"
            label="卡片状态"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="queryFormData.status"
              :style="{ width: '100%' }"
              placeholder="请选择卡片状态"
              value-key="id"
              filterable
              clearable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in statusOptions"
                :key="index"
                :label="item.name"
                :value="item"
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
      <!-- <el-tooltip disabled class="dc-el-tooltip_Button">
        <el-button
          type="primary"
          icon="el-icon-search"
          v-on:click="openCardFun"
          class="dc-el-button_Button"
        >
          查看卡片
        </el-button>
      </el-tooltip> -->
      <el-tooltip v-show="permission.add" disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-plus" v-on:click="onCreate"
        >添加</el-button
        >
      </el-tooltip>
      <el-tooltip disabled class="dc-el-tooltip_Button">
        <el-button
          type="primary"
          icon="el-icon-document-checked"
          v-on:click="searchQrCodeFun"
          class="dc-el-button_Button"
        >
          查看二维码
        </el-button>
      </el-tooltip>
      <el-tooltip disabled class="dc-el-tooltip_Button">
        <el-button
          type="primary"
          icon="el-icon-download"
          v-on:click="onExportFun"
          class="dc-el-button_Button"
        >导出</el-button
        >
      </el-tooltip>
      <el-tooltip disabled class="dc-el-tooltip_Button">
        <el-button
          type="primary"
          icon="el-icon-download"
          v-on:click="exportTemplate"
          class="dc-el-button_Button"
        >
          下载导入模板
        </el-button>
      </el-tooltip>
      <el-tooltip disabled class="dc-el-tooltip_Button">
        <el-button
          type="primary"
          icon="el-icon-upload2"
          v-on:click="checkFileFun"
          class="dc-el-button_Button"
        >导入</el-button
        >
      </el-tooltip>
      <el-tooltip disabled class="dc-el-tooltip_Button">
        <el-button
          type="primary"
          icon="el-icon-document-checked"
          v-on:click="onOpreFun()"
          class="dc-el-button_Button"
        >
          审核
        </el-button>
      </el-tooltip>
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
        :params="{ sortId: '249', summary: false }"
        type="index"
        tree-node
        resizable
        min-width="100px"
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
        min-width="100px"
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
        min-width="100px"
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
        min-width="100px"
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
        min-width="120px"
        align="center"
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
        min-width="100px"
        align="center"
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
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['209'].field"
        :title="tableColumns['209'].title"
        :width="tableColumns['209'].width"
        :visible="tableColumns['209'].visible"
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
        :field="tableColumns['188'].field"
        :title="tableColumns['188'].title"
        :width="tableColumns['188'].width"
        :visible="tableColumns['188'].visible"
        :params="{ sortId: '188', summary: false }"
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
        :field="tableColumns['189'].field"
        :title="tableColumns['189'].title"
        :width="tableColumns['189'].width"
        :visible="tableColumns['189'].visible"
        :params="{ sortId: '189', summary: false }"
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
        :field="tableColumns['146'].field"
        :title="tableColumns['146'].title"
        :width="tableColumns['146'].width"
        :visible="tableColumns['146'].visible"
        :params="{ sortId: '146', summary: false }"
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
        :field="tableColumns['201'].field"
        :title="tableColumns['201'].title"
        :width="tableColumns['201'].width"
        :visible="tableColumns['201'].visible"
        :params="{ sortId: '201', summary: false }"
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
        :field="tableColumns['197'].field"
        :title="tableColumns['197'].title"
        :width="tableColumns['197'].width"
        :visible="tableColumns['197'].visible"
        :params="{ sortId: '197', summary: false }"
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
        :field="tableColumns['177'].field"
        :title="tableColumns['177'].title"
        :width="tableColumns['177'].width"
        :visible="tableColumns['177'].visible"
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
        :field="tableColumns['182'].field"
        :title="tableColumns['182'].title"
        :width="tableColumns['182'].width"
        :visible="tableColumns['182'].visible"
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
        :field="tableColumns['184'].field"
        :title="tableColumns['184'].title"
        :width="tableColumns['184'].width"
        :visible="tableColumns['184'].visible"
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
        :field="tableColumns['213'].field"
        :title="tableColumns['213'].title"
        :width="tableColumns['213'].width"
        :visible="tableColumns['213'].visible"
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
        :field="tableColumns['141'].field"
        :title="tableColumns['141'].title"
        :width="tableColumns['141'].width"
        :visible="tableColumns['141'].visible"
        :params="{ sortId: '141', summary: false }"
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
        :field="tableColumns['251'].field"
        :title="tableColumns['251'].title"
        :width="tableColumns['251'].width"
        :visible="tableColumns['251'].visible"
        :params="{ sortId: '251', summary: false }"
        tree-node
        resizable
        min-width="100px"
        align="center"
        header-align="center"
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['179'].field"
        :title="tableColumns['179'].title"
        :width="tableColumns['179'].width"
        :visible="tableColumns['179'].visible"
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
        :field="tableColumns['180'].field"
        :title="tableColumns['180'].title"
        :width="tableColumns['180'].width"
        :visible="tableColumns['180'].visible"
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
        :field="tableColumns['122'].field"
        :title="tableColumns['122'].title"
        :width="tableColumns['122'].width"
        :visible="tableColumns['122'].visible"
        :params="{ sortId: '122', summary: false }"
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
        :field="tableColumns['183'].field"
        :title="tableColumns['183'].title"
        :width="tableColumns['183'].width"
        :visible="tableColumns['183'].visible"
        :params="{ sortId: '183', summary: false }"
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
        :field="tableColumns['190'].field"
        :title="tableColumns['190'].title"
        :width="tableColumns['190'].width"
        :visible="tableColumns['190'].visible"
        :params="{ sortId: '190', summary: false }"
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
        :field="tableColumns['120'].field"
        :title="tableColumns['120'].title"
        :width="tableColumns['120'].width"
        :visible="tableColumns['120'].visible"
        :params="{ sortId: '120', summary: false }"
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
        title="操作"
        :params="{ sortId: '113' }"
        tree-node
        resizable
        width="160px"
        min-width="160px"
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
      :page-sizes="[10, 20, 30, 40, 50, 100]"
      layout="total, sizes, prev, pager, next, jumper"
      v-on:size-change="onSizeChange"
      v-on:current-change="onCurrentChange"
      class="dc-el-pagination_ElPagination"
    ></el-pagination>
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
</template>
<script>
  import { validatenull } from "@/utils/validate";

  import {
    listTfamAssetPage,
    deleteTfamAsset,
  } from "@/api/fixasset/asset/tfamAsset";

  import { listResourcePermission } from "@/api/admin/common/permission";

  /** 根据用户界面配置import组件 开始 */
  import History from "@/views/components/history";
  import EditForm from "./form";
  import QueryTag from "@/views/components/queryTag";
  import QueryConditionPanel from "@/views/components/queryConditionPanel";
  import { listTfamCategoryAll } from "@/api/fixasset/category/tfamCategory.js";
  import { listTfamUsingstatusAll } from "@/api/fixasset/usingstatus/tfamUsingstatus.js";
  import { listDepartmentAll } from "@/api/org/department.js";
  import { listTfamStatusAll } from "@/api/fixasset/status/tfamStatus.js";
  import {
    listTfamAssetExport,
    listTfamAssetImport,
    listTfamAssetAudit,
  } from "@/api/fixasset/asset/tfamAssetTransfer.js";
  import { printQRCode } from "@/api/fixasset/asset/tfamAssetTransfer.js";
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
      OperationIcon,
      loading: false,
    },
    data() {
      return {
        prCodeBaseApi: "http://61.172.179.26:8099/fmUpload/asset/qrcode/pdf/",
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
          category: {
            id: null,
            name: null,
            fullname: null,
            fullcode: null,
          },
          assetCode: "", // 资产编号
          assetName: "", // 资产名称
          usingstatus: {
            id: null,
            name: null,
            code: null,
            remarks: null,
            companyId: null,
          },
          usedeptid: {
            id: null,
            name: null,
            code: null,
            companyId: null,
          },
          userid: "", // 责任人
          servicemonth: "", // 剩余使用月数
          status: {
            name: null,
            remarks: null,
            code: null,
          },
        },
        tableData: [],
        // 查询表格列头
        tableColumns: {
          249: {
            title: "序号",
            isShow: true,
            visible: true,
            order: 0,
          },
          250: {
            isShow: true,
            visible: true,
            order: 1,
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
          120: {
            title: "卡片编号",
            field: "cardCode",
            visible: true,
            order: 24,
          },
          113: {
            title: "操作",
            width: "160px",
            visible: true,
            order: 25,
          },
        },
        // 分页属性
        tableDataPage: 1,
        tableDataTotal: 0,
        // 选项变量
        // 资产类别选项
        categoryOptions: [],
        // 使用状况选项
        usingstatusOptions: [],
        // 使用部门选项
        usedeptidOptions: [],
        // 卡片状态选项
        statusOptions: [],
        /** 根据用户界面配置生成data数据 结束 */
        version: 24,
        search: {
          // 查询条件   业务表设置的筛选条件
          params: [],

          offset: 0,
          limit: 10,
          columnName: "", // 排序字段名
          order: "", // 排序
        },
        searchList: [],
        /*tableId: "1633870636743041902",
        schemeId: "1633870636743041900",*/
        currentRow: [],
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
            columnName: "category_id",

            queryType: "=",

            value: !validatenull(this.queryFormData.category.id)
              ? this.queryFormData.category.id
              : null,
          });

          this.search.params.push({
            columnName: "asset_code",

            queryType: "like",

            value: !validatenull(this.queryFormData.assetCode)
              ? this.queryFormData.assetCode
              : null,
          });

          this.search.params.push({
            columnName: "asset_name",

            queryType: "like",

            value: !validatenull(this.queryFormData.assetName)
              ? this.queryFormData.assetName
              : null,
          });

          this.search.params.push({
            columnName: "usingstatus",

            queryType: "=",

            value: !validatenull(this.queryFormData.usingstatus.id)
              ? this.queryFormData.usingstatus.id
              : null,
          });

          this.search.params.push({
            columnName: "usedeptid",

            queryType: "=",

            value: !validatenull(this.queryFormData.usedeptid.id)
              ? this.queryFormData.usedeptid.id
              : null,
          });

          this.search.params.push({
            columnName: "userid",

            queryType: "like",

            value: !validatenull(this.queryFormData.userid.id)
              ? this.queryFormData.userid.id
              : null,
          });

          this.search.params.push({
            columnName: "servicemonth",

            queryType: "like",

            value: !validatenull(this.queryFormData.servicemonth)
              ? this.queryFormData.servicemonth
              : null,
          });

          /* this.search.params.push({
             columnName: "status",

             queryType: "=",

             value: !validatenull(this.queryFormData.status.id)
               ? this.queryFormData.status.id
               : null,
           });*/
          this.search.params.push({
            columnName: "status",
            queryType: "=",
            value: "",
          });
        }
        // 数据权限: 卡片表tfam_asset
        this.pushDataPermissions(
          this.search.params,
          this.$route.meta.routerId,
          this.tableId
        );
        listTfamAssetPage(this.search)
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
        this.currentRow = [];
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
      handleCurrentChange(val) {
        this.currentRow = [val];
        console.log(val, "点击的");
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
                return item.permission === "tfamAsset:read";
              });
              this.permission.export = responseData.data.find((item) => {
                return item.permission === "tfamAsset:export";
              });

              this.permission.add = responseData.data.find((item) => {
                return item.permission === "tfamAsset:create";
              });
              this.permission.edit = responseData.data.find((item) => {
                return item.permission === "tfamAsset:update";
              });
              this.permission.remove = responseData.data.find((item) => {
                return item.permission === "tfamAsset:delete";
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
      // 查看二维码
      searchQrCodeFun() {
        // console.log("查看二维码", this.prCodeBaseApi);
        let checkBoxData =
          this.$refs.table.getCheckboxRecords().length > 0
            ? this.$refs.table.getCheckboxRecords()
            : this.currentRow;
        let card_ids = [];
        if (checkBoxData.length <= 0) {
          this.showMessage({
            type: "warning",
            msg: `请先选择卡片`,
          });
          return;
        }

        checkBoxData.map((item) => {
          card_ids.push(item.id);
        });

        let params = {
          ids: card_ids,
        };
        this.loading = true;
        printQRCode(params).then((responseData) => {
          if (responseData.code == 100) {
            this.loading = false;
            // console.log(responseData, "二维码接口信息", this.prCodeBaseApi);
            let url = this.prCodeBaseApi + responseData.data;
            window.open(url);
          } else {
            this.loading = false;
            this.showMessage(responseData);
          }
        });
      },

      onView(scope) {
        this.$refs.editForm.$emit("openViewDialog", scope.row.id);
      },

      onCreate() {
        this.$refs.editForm.$emit("openAddDialog");
      },
      onEdit(scope) {
        if (scope.row.status.name !== "新增待审核") {
          this.showMessage({
            type: "warning",
            msg: `该卡片状态为${scope.row.status.name}，不可修改`,
          });
        } else {
          this.$refs.editForm.$emit("openEditDialog", scope.row.id);
        }
      },
      onCopy(scope) {
        if (scope.row.status.name !== "新增待审核") {
          this.showMessage({
            type: "warning",
            msg: `该卡片状态为${scope.row.status.name}，不可复制`,
          });
        } else {
          this.$refs.editForm.$emit("openCopyDialog", scope.row.id);
        }
      },
      onDelete(scope) {
        if (scope.row.status.name !== "新增待审核") {
          this.showMessage({
            type: "warning",
            msg: `该卡片状态为${scope.row.status.name}，不可删除`,
          });
          return;
        }
        this.$confirm("确定删除吗？", "确认", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            this.setLoad();
            deleteTfamAsset(scope.row)
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

      listCategoryOptions() {
        let search_List = {
          params: [],
        };
        // filter条件
        search_List.params.push.apply(search_List.params, [
          {
            columnName: "company_id",
            queryType: "=",
            value: currentUser.company.id == "0" ? "" : currentUser.company.id,
          },
        ]);

        // 数据权限: 资产类别表tfam_category
        this.pushDataPermissions(
          search_List.params,
          this.$route.meta.routerId,
          "1633870636743042273"
        );

        listTfamCategoryAll(search_List).then((responseData) => {
          if (responseData.code == 100) {
            this.categoryOptions = responseData.data;
          } else {
            this.showMessage(responseData);
          }
        });
      },

      listUsingstatusOptions() {
        let search_List = {
          params: [],
        };
        // filter条件
        search_List.params.push.apply(search_List.params, [
          {
            columnName: "company_id",
            queryType: "=",
            value: currentUser.company.id == "0" ? "" : currentUser.company.id,
          },
        ]);

        // 数据权限: 使用状况表tfam_usingstatus
        this.pushDataPermissions(
          search_List.params,
          this.$route.meta.routerId,
          "1633870636743042259"
        );

        listTfamUsingstatusAll(search_List).then((responseData) => {
          if (responseData.code == 100) {
            this.usingstatusOptions = responseData.data;
          } else {
            this.showMessage(responseData);
          }
        });
      },

      listUsedeptidOptions() {
        let search_List = {
          params: [
            {
              columnName: "company_id",
              queryType: "=",
              value: currentUser.company.id == "0" ? "" : currentUser.company.id,
            },
          ],
        };
        // filter条件
        search_List.params.push.apply(search_List.params, []);

        // 数据权限: 部门org_department
        this.pushDataPermissions(
          search_List.params,
          this.$route.meta.routerId,
          "1633870636743041550"
        );

        listDepartmentAll(search_List).then((responseData) => {
          if (responseData.code == 100) {
            this.usedeptidOptions = responseData.data;
          } else {
            this.showMessage(responseData);
          }
        });
      },

      listStatusOptions() {
        let search_List = {
          params: [],
        };
        // filter条件
        search_List.params.push.apply(search_List.params, [
          {
            columnName: "company_id",
            queryType: "=",
            value: currentUser.company.id == "0" ? "" : currentUser.company.id,
          },
        ]);

        // 数据权限: 卡片状态表 对应卡片中 statustfam_status
        this.pushDataPermissions(
          search_List.params,
          this.$route.meta.routerId,
          "1641034040897356409"
        );

        listTfamStatusAll(search_List).then((responseData) => {
          if (responseData.code == 100) {
            this.statusOptions = responseData.data;
            // this.queryFormData.status = this.statusOptions[0];
          } else {
            this.showMessage(responseData);
          }
        });
      },

      // 初始化自定义类型选择框选项变量
      initOptions(This) {
        this.listCategoryOptions();

        this.listUsingstatusOptions();

        this.listUsedeptidOptions();

        this.listStatusOptions();
      },

      // 导出
      onExportFun() {
        this.setLoad();
        /* 查询参数 和数据权限 */
        listTfamAssetExport(this.search)
          .then((responseData) => {
            let date = moment().format("YYYY-MM-DD");
            const blob = new Blob([responseData]);
            const url = window.URL.createObjectURL(blob);
            let dom = document.createElement("a");
            dom.style.display = "none";
            dom.href = url;
            dom.setAttribute("download", "资产系统资产相关表_" + date + ".xls");
            document.body.appendChild(dom);
            dom.click();
            // 释放内存
            window.URL.revokeObjectURL(url);
            setTimeout(() => {
              this.resetLoad();
            }, 1000);
          })
          .catch((error) => {
            this.outputError(error);
          });
        setTimeout(() => {
          this.resetLoad();
        }, 1000);
      },

      //下载导入模板
      exportTemplate() {
        this.setLoad();
        let a = document.createElement("a");
        a.href = "static/template/" + "资产导入模板.xls";
        a.download = "资产导入模板.xls";
        a.style.display = "none";
        document.body.appendChild(a);
        a.click();
        a.remove();
        setTimeout(() => {
          this.resetLoad();
        }, 1000);
      },

      // 导入
      checkFileFun() {
        document.querySelector("#fileinput").click();
      },

      // 上传导入文件
      uploadExcel(evt) {
        const files = evt.target.files;
        if (files == null || files.length == 0) {
          alert("No files wait for import");
          return;
        }
        let formData = new FormData();
        formData.append("file", files[0]);
        this.setLoad();
        listTfamAssetImport(formData)
          .then((responseData) => {
            if (responseData.code == 100) {
              this.pageInit();
              if (responseData.data.length > 0) {
                this.showMessage({
                  type: "error",
                  msg: responseData.data[0],
                });
              } else {
                this.showMessage({
                  type: "success",
                  msg: "导入成功",
                });
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

      // 审核
      onOpreFun() {
        // let checkBoxData = this.$refs.table.getCheckboxRecords();
        let checkBoxData =
          this.$refs.table.getCheckboxRecords().length > 0
            ? this.$refs.table.getCheckboxRecords()
            : this.currentRow;
        let idsarr = [];
        let moveType = 1;
        if (checkBoxData.length <= 0) {
          this.showMessage({
            type: "warning",
            msg: `请选择需要审核的卡片`,
          });
          return;
        }
        checkBoxData.map((item) => {
          idsarr.push(item.id);
        });

        let params = {
          ids: idsarr,
          moveType: moveType,
        };

        listTfamAssetAudit(params).then((responseData) => {
          if (responseData.code == 100) {
            if (
              responseData.data.noNeedAuditAssets &&
              responseData.data.noNeedAuditAssets.length > 0
            ) {
              this.showMessage({
                type: "warning",
                msg: responseData.data.cardSets || "审核未通过",
              });
            } else {
              this.showMessage({
                type: "success",
                msg: `审核提交通过`,
              });
            }

            this.onSearch();
          } else {
            this.showMessage(responseData);
          }
        });
      },
    },
    watch: {},
    mounted() {
      this.pageInit();

      this.columnDrop(this.$refs.table);
    },
  };
</script>
