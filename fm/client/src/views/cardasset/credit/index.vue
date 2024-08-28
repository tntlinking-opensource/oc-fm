<template>
  <el-row class="dc-container" v-loading="loading">
    <history ref="history" :bussObject="curentRow"></history>
    <edit-form
      ref="editForm"
      v-on:save-finished="getList"
      v-on:before-load="setLoad"
      v-on:after-load="resetLoad"
      @onGetData="getCheckData4"
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
            prop="cardCode"
            label="卡片编号"
            class="dc-el-form-item_SingleInput"
          >
            <el-input
              v-model="queryFormData.cardCode"
              :maxLength="20"
              placeholder="请输入卡片编号"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
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
            prop="nature"
            label="状态"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="queryFormData.nature"
              :style="{ width: '100%' }"
              placeholder="请选择tfam_nature 卡片性质（1待新增制证，2已新增制证，3待减少制证，4已减少制证）"
              clearable
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in natureOptions"
                :key="index"
                :label="item.name"
                :value="item"
              ></el-option>
            </el-select>
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
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in usingstatusOptions"
                :key="index"
                :label="item.name"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop=""
            label="建卡开始日期"
            class="dc-el-form-item_PickerMonth"
          >
            <el-date-picker
              v-model="queryFormData.businessDate"
              :style="{ width: '100%' }"
              placeholder="请选择"
              type="month"
              format="yyyy-MM"
              value-format="yyyyMM"
              class="dc-el-date-picker_PickerMonth"
            ></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop=""
            label="建卡结束日期"
            class="dc-el-form-item_PickerMonth"
          >
            <el-date-picker
              v-model="queryFormData.businessDate"
              :style="{ width: '100%' }"
              placeholder="请选择"
              type="month"
              format="yyyy-MM"
              value-format="yyyyMM"
              class="dc-el-date-picker_PickerMonth"
            ></el-date-picker>
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
            prop=""
            label="责任人工号"
            class="dc-el-form-item_SingleInput"
          >
            <el-input
              v-model="queryFormData.empolyno"
              :maxLength="-1"
              placeholder="请输入"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
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
      <el-button type="primary" icon="el-icon-plus" v-on:click="onCreate"
        >关联凭证</el-button
      >
    </el-row>
    <ux-grid
      column-key
      ref="table"
      :data="tableData"
      border
      v-on:sort-change="onSortChange"
      @selection-change="handleCurrentChange"
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
        type="checkbox"
        fixed="left"
        width="50"
        align="center"
        class="dc-ux-table-column_ElTableColumn"
      />
      <ux-table-column
        :field="tableColumns['118'].field"
        :title="tableColumns['118'].title"
        :width="tableColumns['118'].width"
        :visible="tableColumns['118'].visible"
        :params="{ sortId: '118', summary: false }"
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
        :field="tableColumns['123'].field"
        :title="tableColumns['123'].title"
        :width="tableColumns['123'].width"
        :visible="tableColumns['123'].visible"
        :params="{ sortId: '123', summary: false }"
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
        :field="tableColumns['186'].field"
        :title="tableColumns['186'].title"
        :width="tableColumns['186'].width"
        :visible="tableColumns['186'].visible"
        :params="{ sortId: '186', summary: false }"
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
        :field="tableColumns['187'].field"
        :title="tableColumns['187'].title"
        :width="tableColumns['187'].width"
        :visible="tableColumns['187'].visible"
        :params="{ sortId: '187', summary: false }"
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
        :field="tableColumns['122'].field"
        :title="tableColumns['122'].title"
        :width="tableColumns['122'].width"
        :visible="tableColumns['122'].visible"
        :params="{ sortId: '122', summary: false }"
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
        min-width="180px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['126'].field"
        :title="tableColumns['126'].title"
        :width="tableColumns['126'].width"
        :visible="tableColumns['126'].visible"
        :params="{ sortId: '126', summary: false }"
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
        min-width="120px"
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
        min-width="120px"
        align="center"
        header-align="center"
        sortable
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
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['147'].field"
        :title="tableColumns['147'].title"
        :width="tableColumns['147'].width"
        :visible="tableColumns['147'].visible"
        :params="{ sortId: '147', summary: false }"
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
        :field="tableColumns['149'].field"
        :title="tableColumns['149'].title"
        :width="tableColumns['149'].width"
        :visible="tableColumns['149'].visible"
        :params="{ sortId: '149', summary: false }"
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
        :field="tableColumns['150'].field"
        :title="tableColumns['150'].title"
        :width="tableColumns['150'].width"
        :visible="tableColumns['150'].visible"
        :params="{ sortId: '150', summary: false }"
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
        :field="tableColumns['151'].field"
        :title="tableColumns['151'].title"
        :width="tableColumns['151'].width"
        :visible="tableColumns['151'].visible"
        :params="{ sortId: '151', summary: false }"
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
        :field="tableColumns['128'].field"
        :title="tableColumns['128'].title"
        :width="tableColumns['128'].width"
        :visible="tableColumns['128'].visible"
        :params="{ sortId: '128', summary: false }"
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
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['156'].field"
        :title="tableColumns['156'].title"
        :width="tableColumns['156'].width"
        :visible="tableColumns['156'].visible"
        :params="{ sortId: '156', summary: false }"
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
        :field="tableColumns['157'].field"
        :title="tableColumns['157'].title"
        :width="tableColumns['157'].width"
        :visible="tableColumns['157'].visible"
        :params="{ sortId: '157', summary: false }"
        tree-node
        resizable
        min-width="80px"
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
      :page-sizes="[10, 20, 30, 40, 50, 100]"
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
  listTfamAssetEntryPage,
  deleteTfamAssetEntry,
  linkvoucher,
} from "@/api/cardasset/credit/tfamAssetEntry";

import { listResourcePermission } from "@/api/admin/common/permission";

/** 根据用户界面配置import组件 开始 */
import History from "@/views/components/history";
import EditForm from "./form";
import QueryTag from "@/views/components/queryTag";
import QueryConditionPanel from "@/views/components/queryConditionPanel";
import { listTfamNatureEntryAll } from "@/api/cardasset/credit/tfamNatureEntry.js";
import { listTfamUsingstatusEntryAll } from "@/api/cardasset/credit/tfamUsingstatusEntry.js";
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
      queryFormData: {
        cardCode: "", // 卡片编号（自增长）
        assetCode: "", // 资产编号
        assetName: "", // 资产名称
        nature: {
          name: null,
          id: null,
        },
        usingstatus: "",
        userid: "", // 责任人
      },
      tableData: [],
      // 查询表格列头
      tableColumns: {
        118: {
          title: "id主键.凭证主表id",
          field: "id.voucherid",
          visible: false,
          order: 0,
        },
        123: {
          title: "建卡日期",
          field: "businessDate",
          visible: true,
          order: 1,
        },
        186: {
          title: "卡片编号",
          field: "cardCode",
          visible: true,
          order: 2,
        },
        187: {
          title: "负责人",
          field: "userid.name",
          visible: true,
          order: 3,
        },
        122: {
          title: "资产编号",
          field: "assetCode",
          visible: true,
          order: 4,
        },
        127: {
          title: "资产名称",
          field: "assetName",
          visible: true,
          order: 5,
        },
        126: {
          title: "资产类别",
          field: "category.name",
          visible: true,
          order: 6,
        },
        137: {
          title: "变动方式",
          field: "addReason.name",
          visible: true,
          order: 7,
        },
        188: {
          title: "使用状况",
          field: "usingstatus.name",
          visible: true,
          order: 8,
        },
        164: {
          title: "建卡人",
          field: "createrid.name",
          visible: true,
          order: 9,
        },
        147: {
          title: "原值",
          field: "value",
          visible: true,
          order: 10,
        },
        149: {
          title: "税金",
          field: "tax",
          visible: true,
          order: 11,
        },
        150: {
          title: "累计折旧",
          field: "accudep",
          visible: true,
          order: 12,
        },
        151: {
          title: "减值准备",
          field: "predevaluate",
          visible: true,
          order: 13,
        },
        128: {
          title: "数量",
          field: "num",
          visible: true,
          order: 14,
        },
        140: {
          title: "折旧方法",
          field: "depmethod.name",
          visible: true,
          order: 15,
        },
        156: {
          title: "预计使用月数",
          field: "naturemonth",
          visible: true,
          order: 16,
        },
        157: {
          title: "剩余使用月数",
          field: "servicemonth",
          visible: true,
          order: 17,
        },
        113: {
          title: "操作",
          width: "160px",
          visible: true,
          order: 18,
        },
      },
      // 分页属性
      tableDataPage: 1,
      tableDataTotal: 0,
      // 选项变量
      // 状态选项
      natureOptions: [],
      // 使用状况选项
      usingstatusOptions: [],
      /** 根据用户界面配置生成data数据 结束 */
      version: 4,
      search: {
        // 查询条件   业务表设置的筛选条件
        params: [],

        offset: 0,
        limit: 10,
        columnName: "", // 排序字段名
        order: "", // 排序
      },
      searchList: [],
      tableId: "1641034040897358087",
      schemeId: "1641034040897358085",
      currentRow: "",
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
          columnName: "card_code",

          queryType: "like",

          value: !validatenull(this.queryFormData.cardCode)
            ? this.queryFormData.cardCode
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
          columnName: "nature",

          queryType: "=",

          value: !validatenull(this.queryFormData.nature.id)
            ? this.queryFormData.nature.id
            : null,
        });

        this.search.params.push({
          columnName: "usingstatus",

          queryType: "=",

          value: !validatenull(this.queryFormData.usingstatus)
            ? this.queryFormData.usingstatus
            : null,
        });

        this.search.params.push({
          columnName: "userid.name",

          queryType: "like",

          value: !validatenull(this.queryFormData.userid)
            ? this.queryFormData.userid
            : null,
        });
      }
      this.search.params.push({
        columnName: "company_id",

        queryType: "like",

        value: currentUser.company.id,
      });
      // this.search.params.push({
      //   columnName: "id.alter_type",
      //
      //   queryType: "=",
      //
      //   value: "0001",
      // });
      this.search.params.push({
        columnName: "id.voucherid",

        queryType: "is null",

        value: "",
      });
      // 数据权限: 卡片表入账使用tfam_asset
      this.pushDataPermissions(
        this.search.params,
        this.$route.meta.routerId,
        this.tableId
      );
      listTfamAssetEntryPage(this.search)
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
    getCheckData4(data) {
      debugger;
      console.log(data);
      console.log(this.currentRow);
      // var ids = $("#gridFormA").jqGrid('getGridParam', 'selarrrow');
      var alterids = [];
      for (let i = 0; i < this.currentRow.length; i++) {
        alterids.push(this.currentRow[i].id);
      }
      let json = {
        alterid: alterids,
        voucherid: data,
      };
      linkvoucher(json)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.getList();
            this.showMessage({
              type: "success",
              msg: "关联成功",
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
    onSearch() {
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
              return item.permission === "tfamAssetEntry:read";
            });
            this.permission.export = responseData.data.find((item) => {
              return item.permission === "tfamAssetEntry:export";
            });

            this.permission.add = responseData.data.find((item) => {
              return item.permission === "tfamAssetEntry:create";
            });
            this.permission.edit = responseData.data.find((item) => {
              return item.permission === "tfamAssetEntry:update";
            });
            this.permission.remove = responseData.data.find((item) => {
              return item.permission === "tfamAssetEntry:delete";
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
      this.$refs.editForm.$emit("openViewDialog", scope.row.id);
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
          deleteTfamAssetEntry(scope.row)
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

    listNatureOptions() {
      let search_List = {
        params: [],
      };
      // filter条件
      search_List.params.push.apply(search_List.params, []);

      // 数据权限: 卡片性质表入账使用tfam_nature
      this.pushDataPermissions(
        search_List.params,
        this.$route.meta.routerId,
        "1641034040897358089"
      );

      listTfamNatureEntryAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.natureOptions = responseData.data;
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
      search_List.params.push.apply(search_List.params, []);

      // 数据权限: 使用状况表。对应卡片中usingstatus入账使用tfam_usingstatus
      this.pushDataPermissions(
        search_List.params,
        this.$route.meta.routerId,
        "1641034040897358092"
      );

      listTfamUsingstatusEntryAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.usingstatusOptions = responseData.data;
        } else {
          this.showMessage(responseData);
        }
      });
    },

    // 初始化自定义类型选择框选项变量
    initOptions(This) {
      this.listNatureOptions();

      this.listUsingstatusOptions();
    },
  },
  watch: {},
  mounted() {
    this.pageInit();

    this.columnDrop(this.$refs.table);
  },
};
</script>
