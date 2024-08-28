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
    <edit-view
      ref="editView"
      :permission="permission"
    ></edit-view>
    <el-row gutter="0" type="flex" justify="start" align="top" class="dc-el-row_ElRow">
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
              <el-button v-on:click="onSearch" type="primary" icon="el-icon-search"></el-button>
            </el-tooltip>
            <el-tooltip effect="dark" content="重置" placement="top">
              <el-button
                v-on:click="isQueryConditionPanel ? $refs.conditionPanel.reset() : $refs.queryForm.resetFields()"
                type="primary"
                icon="el-icon-refresh-right"
              ></el-button>
            </el-tooltip>
            <el-tooltip effect="dark" content="更多" placement="top">
              <el-button v-on:click="onQueryConditionPanel('query')" type="primary" icon="el-icon-d-arrow-right"></el-button>
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
          return item.type == 'main'
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
          <el-form-item prop="cardCode" label="卡片编号" class="dc-el-form-item_SingleInput">
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
          <el-form-item prop="assetCode" label="资产编号" class="dc-el-form-item_SingleInput">
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
          <el-form-item prop="assetName" label="资产名称" class="dc-el-form-item_SingleInput">
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
          <el-form-item prop="usingstatus" label="使用状况" class="dc-el-form-item_SelectInput">
            <el-select
              v-model="queryFormData.usingstatus"
              :style="{ width: '100%' }"
              placeholder="请选择tfam_usingstatus 使用状况（1使用中，2未使用，3不需用，4经营性租出，5愈龄，7损坏需维修，8设备已外。借，9设备待报废，10损坏丢失）"
              clearable
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option v-for="(item, index) in usingstatusOptions" :key="index" :label="item.name" :value="item"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="userid" label="责任人" class="dc-el-form-item_SingleInput">
            <el-input
              v-model="queryFormData.userid"
              :maxLength="100"
              placeholder="请输责任人"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="employeeno" label="负责人工号" class="dc-el-form-item_SingleInput">
            <el-input
              v-model="queryFormData.employeeno"
              :maxLength="10"
              placeholder="请输入负责人工号"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="businessDate" label="建卡日期" class="dc-el-form-item_PickerTimeDate">
            <el-date-picker
              v-model="queryFormData.businessDate"
              :style="{ width: '100%' }"
              placeholder="请选择建卡日期"
              type="date"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
              class="dc-el-date-picker_PickerTimeDate"
            ></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="businessDate" label="到" class="dc-el-form-item_PickerTimeDate">
            <el-date-picker
              v-model="queryFormData.businessDate1"
              :style="{ width: '100%' }"
              placeholder="请选择建卡日期"
              type="date"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
              class="dc-el-date-picker_PickerTimeDate"
            ></el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <el-row gutter="0" type="flex" justify="end" align="top" class="dc-el-row_ElRow">
      <el-tooltip v-show="1 == 2" disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-plus" v-on:click="onCreate">添加</el-button>
      </el-tooltip>
      <el-tooltip disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-sort-up" v-on:click="onSupplement"  class="dc-el-button_Button">补提折旧</el-button>
      </el-tooltip>
      <el-tooltip disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-sort-down" v-on:click="onSupplement1" class="dc-el-button_Button">冲减折旧</el-button>
      </el-tooltip>
      <el-tooltip disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-s-order"  v-on:click="onCreate" class="dc-el-button_Button" >查看折旧明细</el-button>
      </el-tooltip>
      <el-tooltip disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-search" class="dc-el-button_Button"  v-on:click="onView()">查看卡片</el-button>
      </el-tooltip>
    </el-row>
    <ux-grid
      column-key
      ref="table"
      :data="tableData"
      border
      v-on:sort-change="onSortChange"
      @current-change="handleCurrentChange"
      v-on:header-dragend="onWidthChange($refs.table)"
      :tree-config="{ children: '', indent: 20, accordion: false, line: false, showIcon: true, iconOpen: '', iconClose: '' }"
      class="dc-ux-grid_QueryTable"
    >
      <ux-table-column
        :field="tableColumns['121'].field"
        :title="tableColumns['121'].title"
        :width="tableColumns['121'].width"
        :visible="tableColumns['121'].visible"
        :params="{ sortId: '121', summary: false }"
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
        :field="tableColumns['185'].field"
        :title="tableColumns['185'].title"
        :width="tableColumns['185'].width"
        :visible="tableColumns['185'].visible"
        :params="{ sortId: '185', summary: false }"
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
        :field="tableColumns['186'].field"
        :title="tableColumns['186'].title"
        :width="tableColumns['186'].width"
        :visible="tableColumns['186'].visible"
        :params="{ sortId: '186', summary: false }"
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
        :field="tableColumns['187'].field"
        :title="tableColumns['187'].title"
        :width="tableColumns['187'].width"
        :visible="tableColumns['187'].visible"
        :params="{ sortId: '187', summary: false }"
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
        :field="tableColumns['189'].field"
        :title="tableColumns['189'].title"
        :width="tableColumns['189'].width"
        :visible="tableColumns['189'].visible"
        :params="{ sortId: '189', summary: false }"
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
        :field="tableColumns['191'].field"
        :title="tableColumns['191'].title"
        :width="tableColumns['191'].width"
        :visible="tableColumns['191'].visible"
        :params="{ sortId: '191', summary: false }"
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
        :field="tableColumns['192'].field"
        :title="tableColumns['192'].title"
        :width="tableColumns['192'].width"
        :visible="tableColumns['192'].visible"
        :params="{ sortId: '192', summary: false }"
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
        :field="tableColumns['193'].field"
        :title="tableColumns['193'].title"
        :width="tableColumns['193'].width"
        :visible="tableColumns['193'].visible"
        :params="{ sortId: '193', summary: false }"
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
        :field="tableColumns['194'].field"
        :title="tableColumns['194'].title"
        :width="tableColumns['194'].width"
        :visible="tableColumns['194'].visible"
        :params="{ sortId: '194', summary: false }"
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
        :field="tableColumns['195'].field"
        :title="tableColumns['195'].title"
        :width="tableColumns['195'].width"
        :visible="tableColumns['195'].visible"
        :params="{ sortId: '195', summary: false }"
        tree-node
        resizable
        min-width="180px"
        align="left"
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
    <el-dialog
      title="补提折旧"
      :visible.sync="dialogFormVisible"
      label-width="100px"
    >
      <el-form
        :model="formDate"
        label-width="100px"
        class="dc-el-form_ElQueryForm"
      >
        <el-row>
          <el-col :span="12">
            <el-form-item prop="userid" label="按月补提" class="dc-el-form-item_SingleInput">
          <el-input v-model="formDate.supplementNumber"    class="dc-el-input_SingleInput" autocomplete="off"></el-input>
        </el-form-item>
          </el-col>
          <el-col :span="12">
        <el-form-item label="按金额补提：" >
          <el-input v-model="formDate.supplementMoney" autocomplete="off"></el-input>
        </el-form-item>
          </el-col>
          <el-col :span="12">
        <el-form-item label="补提原因：" >
          <el-input v-model="formDate.supplementReason" autocomplete="off"></el-input>
        </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogFormVisibles">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog
      title="冲减折旧"
      :visible.sync="dialogFormVisible1"
      label-width="100px"
    >
      <el-form
        :model="formDate1"
        label-width="100px"
        class="dc-el-form_ElQueryForm"
      >
        <el-row>
          <el-col :span="12">
            <el-form-item prop="userid" label="冲减金额" class="dc-el-form-item_SingleInput">
          <el-input v-model="formDate1.reduceMoney"    class="dc-el-input_SingleInput" autocomplete="off"></el-input>
        </el-form-item>
          </el-col>
          <el-col :span="12">
        <el-form-item label="冲减原因：" >
          <el-input v-model="formDate1.reduceReason" autocomplete="off"></el-input>
        </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible1 = false">取 消</el-button>
        <el-button type="primary" @click="dialogFormVisibles1">确 定</el-button>
      </div>
    </el-dialog>
  </el-row>

</template>
<script>
import { validatenull } from '@/utils/validate'

import { listTfamAssetOldPage, deleteTfamAssetOld,supplementdep,reducedep } from '@/api/cardasset/supplementreduce/tfamAssetOld'

import { listResourcePermission } from '@/api/admin/common/permission'

/** 根据用户界面配置import组件 开始 */
import History from '@/views/components/history'
import EditForm from './form'
import EditView from  '@/views/fixasset/asset/tfamAsset/form'
import QueryTag from '@/views/components/queryTag'
import QueryConditionPanel from '@/views/components/queryConditionPanel'
import { listTfamUsingstatusOldAll } from '@/api/cardasset/supplementreduce/tfamUsingstatusOld.js'
import ViewColumnsSelect from '@/views/components/DcViewColumnsSelect'
/** 根据用户界面配置import组件 结束 */
import metadata from './metadata'
import ExportExcelButton from '@/components/ExportExcelButton'
import DcMain from '@/views/components/dcMain'
import OperationIcon from '@/components/OperationIcon'
export default {
  extends: DcMain,
  components: {
    /** 根据用户界面配置组件 开始 */
    History,
    EditForm,
    EditView,
    QueryTag,
    QueryConditionPanel,
    ViewColumnsSelect,
    /** 根据用户界面配置组件 结束 */
    ExportExcelButton,
    OperationIcon
  },
  data() {
    return {
      formDate:{
        supplementNumber:"",
        supplementMoney:"",
        supplementReason:"",
      },
      formDate1:{
        reduceMoney:"",
        reduceReason:"",
      },
      dialogFormVisible: false,
      dialogFormVisible1: false,
      permission: {
        view: false,
        add: false,
        edit: false,
        remove: false,
        export: false
      },
      metadata,

      /** 根据用户界面配置生成data数据 开始 */
      queryFormData: {
        cardCode: '', // 卡片编号
        assetCode: '', // 资产编号
        assetName: '', // 资产名称
        usingstatus: {
          id: null,
          name: null
        },
        userid: '', // 责任人
        employeeno: '', // 负责人工号
        businessDate: '', // 建卡日期
        businessDate1: '' // 到
      },
      tableData: [],
      // 查询表格列头
      tableColumns: {
        121: {
          title: '建卡日期',
          field: 'businessDate',
          visible: true,
          order: 0
        },
        185: {
          title: '卡片编号',
          field: 'cardCode',
          visible: true,
          order: 1
        },
        186: {
          title: '资产编号',
          field: 'assetCode',
          visible: true,
          order: 2
        },
        187: {
          title: '资产名称',
          field: 'assetName',
          visible: true,
          order: 3
        },
        188: {
          title: '资产类别',
          field: 'category.name',
          visible: true,
          order: 4
        },
        189: {
          title: '使用状况',
          field: 'usingstatus.name',
          visible: true,
          order: 5
        },
        190: {
          title: '原值',
          field: 'value',
          visible: true,
          order: 6
        },
        191: {
          title: '累计折旧',
          field: 'accudep',
          visible: true,
          order: 7
        },
        192: {
          title: '净值',
          field: 'netvalue',
          visible: true,
          order: 8
        },
        193: {
          title: '残值',
          field: 'salvage',
          visible: true,
          order: 9
        },
        194: {
          title: '折旧方法',
          field: 'depmethod.name',
          visible: true,
          order: 10
        },
        195: {
          title: '替补关联凭证号',
          field: 'id',
          visible: true,
          order: 11
        },
      },
      // 分页属性
      tableDataPage: 1,
      tableDataTotal: 0,
      // 选项变量
      // 使用状况选项
      usingstatusOptions: [],
      /** 根据用户界面配置生成data数据 结束 */
      version: 5,
      search: {
        // 查询条件   业务表设置的筛选条件
        params: [],

        offset: 0,
        limit: 10,
        columnName: '', // 排序字段名
        order: '' // 排序
      },
      searchList: [],
      tableId: '1641034040897358518',
      schemeId: '1641034040897358516',
      currentRow:''
    }
  },
  computed: {},
  methods: {
    dialogFormVisibles1(){
      var reduceMoney = this.formDate1.reduceMoney;
      var reduceReason = this.formDate1.reduceReason;

      if(reduceMoney <= 0)
      {
        this.showMessage({
          type: 'warning',
          msg: '冲减金额须填写大于0的正数'
        })

        return false;
      }
      if(reduceReason == "")
      {
        this.showMessage({
          type: 'warning',
          msg: '请输入【冲减原因】'
        })

        return;
      }
      let arraySelecteID =this.currentRow;
      //冲减金额不能大于卡片的累计折旧金额 accudep


        var asset_code = arraySelecteID.asset_code;  //资产编号
        var accudep = arraySelecteID.accudep;  //累计折旧金额
        if(reduceMoney > accudep)
        {
          this.showMessage({
            type: 'warning',
            msg: '卡片资产编号：'+asset_code+"冲减金额不能大于卡片的累计折旧金额。"
          })
          return;
        }
      this.doReduceDep(arraySelecteID,reduceMoney,reduceReason);

    },
    dialogFormVisibles(){
      var supplementNumber = this.formDate.supplementNumber;
      var supplementMoney = this.formDate.supplementMoney;
      var supplementReason =  this.formDate.supplementReason;

      if(supplementNumber < 0)
      {
        this.showMessage({
          type: 'warning',
          msg: '补提月数仅可为正整数'
        })
        return false;
      }
      if(supplementMoney < 0)
      {
        this.showMessage({
          type: 'warning',
          msg: '补提金额仅可为正数'
        })
        return false;
      }
      if(supplementNumber === 0 && supplementMoney === 0)
      {
        this.showMessage({
          type: 'warning',
          msg: '请选择 按月补提 及 按金额补提 其中一项'
        })
        return false;
      }
      if(supplementNumber > 0 && supplementMoney > 0)
      {
        this.showMessage({
          type: 'warning',
          msg: '按月补提 及 按金额补提 仅可选择一项'
        })
        return false;
      }
      if(supplementReason === "")
      {
        this.showMessage({
          type: 'warning',
          msg: '请输入【补提原因】'
        })
        return;
      }
      this.doSupplementDep(this.currentRow.id,supplementNumber,supplementMoney,supplementReason);
    },
    handleCurrentChange(val) {
      this.currentRow = val;
    },
    getList() {
      this.setLoad()
      /* 查询参数 和数据权限 */
      this.search.params = []
      if (this.isQueryConditionPanel) {
        this.search.params = this.search.params.concat(this.compositeCondition())
      } else {
        this.search.params.push({
          columnName: 'card_code',

          queryType: 'like',

          value: !validatenull(this.queryFormData.cardCode) ? this.queryFormData.cardCode : null
        })

        this.search.params.push({
          columnName: 'asset_code',

          queryType: 'like',

          value: !validatenull(this.queryFormData.assetCode) ? this.queryFormData.assetCode : null
        })

        this.search.params.push({
          columnName: 'asset_name',

          queryType: 'like',

          value: !validatenull(this.queryFormData.assetName) ? this.queryFormData.assetName : null
        })

        this.search.params.push({
          columnName: 'usingstatus',

          queryType: '=',

          value: !validatenull(this.queryFormData.usingstatus.id) ? this.queryFormData.usingstatus.id : null
        })

        this.search.params.push({
          columnName: 'userid.name',

          queryType: 'like',

          value: !validatenull(this.queryFormData.userid) ? this.queryFormData.userid : null
        })

        this.search.params.push({
          columnName: 'employeeno',

          queryType: 'like',

          value: !validatenull(this.queryFormData.employeeno.empolyno) ? this.queryFormData.employeeno.empolyno : null
        })

        this.search.params.push({
          columnName: 'business_date',

          queryType: '<=',

          value: !validatenull(this.queryFormData.businessDate) ? this.queryFormData.businessDate : null
        })

        this.search.params.push({
          columnName: 'business_date',

          queryType: '>=',

          value: !validatenull(this.queryFormData.businessDate1) ? this.queryFormData.businessDate1 : null
        })
      }
      // 数据权限: 卡片表入账使用tfam_asset
      this.pushDataPermissions(this.search.params, this.$route.meta.routerId, this.tableId)
      listTfamAssetOldPage(this.search)
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
      if (this.isQueryConditionPanel) {
        this.search.offset = 0
        this.tableDataPage = 1
        this.getList()
      } else {
        this.$refs['queryForm'].validate((valid) => {
          if (valid) {
            this.search.offset = 0
            this.tableDataPage = 1
            this.getList()
          } else {
            return false
          }
        })
      }
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
      this.initOptions(this.queryModel)
      this.onSearch()
      listResourcePermission(this.$route.meta.routerId)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.permission.view = responseData.data.find((item) => {
              return item.permission === 'tfamAssetOld:read'
            })
            this.permission.export = responseData.data.find((item) => {
              return item.permission === 'tfamAssetOld:export'
            })

            this.permission.add = responseData.data.find((item) => {
              return item.permission === 'tfamAssetOld:create'
            })
            this.permission.edit = responseData.data.find((item) => {
              return item.permission === 'tfamAssetOld:update'
            })
            this.permission.remove = responseData.data.find((item) => {
              return item.permission === 'tfamAssetOld:delete'
            })
          } else {
            this.showMessage(responseData)
          }
          this.resetLoad()
        })
        .catch((error) => {
          this.outputError(error)
        })
    },
    onView(scope) {
      this.$refs.editView.$emit("openViewDialog", this.currentRow.id);
    },

    onCreate() {
      this.$refs.editForm.$emit('openAddDialog',this.currentRow)
    },
    onSupplement() {
     this.dialogFormVisible = true
    },
    onSupplement1(){
      this.dialogFormVisible1 = true
    },
    onEdit(scope) {
      this.$refs.editForm.$emit('openEditDialog', scope.row.id)
    },
    onCopy(scope) {
      this.$refs.editForm.$emit('openCopyDialog', scope.row.id)
    },
    onDelete(scope) {
      this.$confirm('确定删除吗？', '确认', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.setLoad()
          deleteTfamAssetOld(scope.row)
            .then((responseData) => {
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
            })
            .catch((error) => {
              this.outputError(error)
            })
        })
        .catch(() => {})
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

    listUsingstatusOptions() {
      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [])

      // 数据权限: 使用状况表。对应卡片中usingstatustfam_usingstatus
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1641034040897358520')

      listTfamUsingstatusOldAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.usingstatusOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },
    // 初始化自定义类型选择框选项变量
    initOptions(This) {
      this.listUsingstatusOptions()
    } ,
   doSupplementDep (arraySelecteID,supplementNumber,supplementMoney,supplementReason)
    {
      var param =
        {
          supplementNumber : supplementNumber,
          supplementMoney : supplementMoney,
          supplementReason : supplementReason
        };
      let p_param =
        {
          jsonCondition : param,
          arraySelecteID : arraySelecteID
        };
      supplementdep(p_param).then((responseData) => {
        if (responseData.code == 100) {
          this.showMessage({
            type: 'success',
            msg: '补提折旧成功'
          })
          this.dialogFormVisible1 = false
          this.getList();
        } else {
          this.showMessage(responseData)
        }
      })
    },
    doReduceDep (arraySelecteID,reduceMoney,reduceReason)
    {
      var param =
        {
          reduceMoney : reduceMoney,
          reduceReason : reduceReason
        };
      let p_param =
        {
          jsonCondition : param,
          arraySelecteID :arraySelecteID
        };
      reducedep(p_param).then((responseData) => {
        if (responseData.code == 100) {
          this.showMessage({
            type: 'success',
            msg: '冲减折旧成功'
          })
          this.dialogFormVisible = false
          this.getList();
        } else {
          this.showMessage(responseData)
        }
      })

    },
  },
  watch: {},
  mounted() {
    this.pageInit()

    this.columnDrop(this.$refs.table)
  }
}
</script>
