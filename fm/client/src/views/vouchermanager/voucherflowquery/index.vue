<template>
  <el-row class="dc-container" v-loading="loading">
    <el-form
      ref="elForm"
      :model="formData"
      label-width="100px"
      class="dc-el-form_ElQueryForm"
    >
      <el-row>
        <el-col :span="6">
          <el-form-item
            label="会计期"
            prop="uqglobalperiodid"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="formData.uqglobalperiodidstart"
              placeholder="请选择会计期"
              :style="{ width: '100%' }"
              class="dc-el-form-item_SelectInput"
            >
              <el-option
                v-for="(item, index) in uqglobalperiodidOptions"
                :key="index"
                :label="item.varname"
                :value="item.intyearmonth"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            label="至"
            prop="uqglobalperiodid"
            class="dc-el-form-item_SelectInput"
          >
          <el-select
            v-model="formData.uqglobalperiodidend"
            placeholder="请选择会计期"
            :style="{ width: '100%' }"
            class="dc-el-form-item_SelectInput"
          >
            <el-option
              v-for="(item, index) in uqglobalperiodidOptions"
              :key="index"
              :label="item.varname"
              :value="item.intyearmonth"
            ></el-option>
          </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="交易日期" prop="chargeagainstsubmitodd">
            <el-date-picker
              type="daterange"
              v-model="formData.chargeagainstsubmitodd"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
              :style="{ width: '100%' }"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              range-separator="至"
            ></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="摘要" prop="varabstract">
            <el-input
              v-model="formData.varabstract"
              placeholder=""
              :style="{ width: '100%' }"
            >
            </el-input>
          </el-form-item>
        </el-col>
          <el-col :span="6">
          <el-form-item label="项目名称/编码：" prop="project">
            <el-input
              v-model="formData.project"
              placeholder=""
              :style="{ width: '100%' }"
            >
            </el-input>
          </el-form-item>
        </el-col>
          <el-col :span="6">
          <el-form-item label="科目编码/名称：" prop="project">
            <el-select
              style="width: 100%"
              value-key="uqaccountid"
              v-model="formData.uqaccountid"
              placeholder="请选择本年利润科目"
              clearable
              filterable
            >
              <el-option
                v-for="item in accountOptions"
                :key="item.uqaccountid"
                :label="
              item.varaccountcode
                ? '[' + item.varaccountcode + ']' + item.varaccountname
                : item.varaccountname
            "
                :value="item.uqaccountid"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-button v-on:click="onSearch" type="primary" icon="el-icon-search"
            >查询</el-button
          >
        </el-col>
      </el-row>
    </el-form>
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
        min-width="180px"
        align="left"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      >
      </ux-table-column>
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
      >
      </ux-table-column>
      <ux-table-column
        :field="tableColumns['120'].field"
        :title="tableColumns['120'].title"
        :width="tableColumns['120'].width"
      :visible="tableColumns['120'].visible"
          :params="{ sortId: '120', summary: false }"
        tree-node
        resizable
        min-width="180px"
        align="left"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      >
        <template slot-scope="{ row, rowIndex }">
          <div
            v-html="
              ((value, row, rowIndex) => {
                let valueF = row.dtaccountant
                let yes = new Date(valueF);

                return yes.getFullYear()+'-'+yes.getMonth()+'-'+yes.getDay();
              })(row.dtaccountant, row, rowIndex)
            "
          ></div>
        </template>
      </ux-table-column>
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
      >
      </ux-table-column>
      <ux-table-column
        :field="tableColumns['122'].field"
        :title="tableColumns['122'].title"
        :width="tableColumns['122'].width"
      :visible="tableColumns['122'].visible"
          :params="{ sortId: '122', summary: false }"
        tree-node
        resizable
        min-width="180px"
        align="left"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      >
      </ux-table-column>
      <ux-table-column
        :field="tableColumns['123'].field"
        :title="tableColumns['123'].title"
        :width="tableColumns['123'].width"
      :visible="tableColumns['123'].visible"
          :params="{ sortId: '123', summary: false }"
        tree-node
        resizable
        min-width="180px"
        align="left"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      >
      </ux-table-column>
      <ux-table-column
        :field="tableColumns['124'].field"
        :title="tableColumns['124'].title"
        :width="tableColumns['124'].width"
      :visible="tableColumns['124'].visible"
          :params="{ sortId: '124', summary: false }"
        tree-node
        resizable
        min-width="180px"
        align="left"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      >
      </ux-table-column>
      <ux-table-column
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
      >
      </ux-table-column>
      <ux-table-column
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
      >
        <template slot-scope="{ row, rowIndex }">
          <div
            v-html="
              ((value, row, rowIndex) => {
                let valueF = !row.mnydebit ? 0 : row.mnydebit;
                return Number(valueF)
                  .toFixed(2)
                  .replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
              })(row.mnydebit, row, rowIndex)
            "
          ></div>
        </template>
      </ux-table-column>
      <ux-table-column
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
      >
        <template slot-scope="{ row, rowIndex }">
          <div
            v-html="
              ((value, row, rowIndex) => {
                let valueF = !row.mnycredit ? 0 : row.mnycredit;
                return Number(valueF)
                  .toFixed(2)
                  .replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
              })(row.mnycredit, row, rowIndex)
            "
          ></div>
        </template>
      </ux-table-column>

      <ux-table-column
        title="操作"
        :visible="tableColumns['113'].visible"
        v-if="tableColumns['113'].isShow"
        :params="{ sortId: '113' }"
        tree-node
        resizable
        width="120px"
        fixed="right"
        align="center"
        header-align="center"
        class="dc-ux-table-column_ElTableOptColumn"
      >
        <template slot-scope="scope">
          <OperationIcon
            v-on:click="onView(scope)"
            type="info"
            v-show="permission.view"
            content="查看凭证"
            placement="top"
            icon-name="el-icon-view"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            v-on:click="onView(scope)"
            type="info"
            v-show="permission.view"
            content="打印凭证"
            placement="top"
            icon-name="el-icon-download"
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

    <addCertificateDialog
      v-if="certShow"
      ref="addCertificateDialog"
      @closed="certShow = false"
    ></addCertificateDialog>
    <el-dialog
      :visible.sync="dialogVisible"
      :show-close="false"
      :title="title"
      :center="true"
      width="20%"
    >
      <el-date-picker
        v-model="dtchecker"
        :style="{ width: '100%' }"
        type="date"
        placeholder="选择日期"
      >
      </el-date-picker>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitCheck">确 定</el-button>
      </span>
    </el-dialog>
  </el-row>
</template>

<script>
import { validatenull } from "@/utils/validate";

import {
  listTglVoucherMainsPage,
  xjlllistPage,
  deleteTglVoucherMains,
  getTglVoucherMainsById,
  voucherCheck,
  unVoucherCheck,
  voucherCash,
  unVoucherCash,
  unVouCherEnd,
  voucherEnd,
  tglVoucherNumberings
} from "@/api/vouchermanager/tglVoucherMains";
import { selectPeriod } from "@/api/tgl/global/globalPeriods";

import { listResourcePermission } from "@/api/admin/common/permission";

/** 根据用户界面配置import组件 开始 */
import addCertificateDialog from "@/views/components/certificate/addCertificateDialog";
import { listTglAccountsAll } from "@/api/vouchermanager/InitLedgePeriod/tglAccounts.js";
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
import {Base64} from "js-base64";
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
    addCertificateDialog
  },
  data() {
    return {
      certShow: false,
      dialogVisible: false,
      code: "",
      params: [],
      title: "",
      permission: {
        view: false,
        add: false,
        edit: false,
        remove: false,
        export: false
      },
      metadata,
      dtchecker: new Date(),
      /** 根据用户界面配置生成data数据 开始 */
      queryFormData: {},
      tableData: [],
      // 查询表格列头
      tableColumns: {
        118: {
          title: "项目编号",
          field: "flowitemcode",
          visible: true,
          order: 0
        },
        119: {
          title: "项目名称",
          field: "flowitemname",
          isShow: false,
          visible: true,
          order: 0
        },
        120: {
          title: "已编日期",
          field: "dtaccountant",
          visible: true,
          order: 1
        },
        121: {
          title: "制证日期",
          field: "dtfiller",
          visible: true,
          order: 2
        },
        122: {
          title: "凭证字",
          field: "numberingname",
          visible: true,
          order: 3
        },
        123: {
          title: "凭证编号",
          field: "intvouchernum",
          visible: true,
          order: 5
        },
        124: {
          title: "科目",
          field: "uqaccountid",
          visible: true,
          order: 6
        },
        125: {
          title: "摘要",
          field: "varabstract",
          visible: true,
          order: 7
        },
        126: {
          title: "借方金额",
          field: "mnydebit",
          visible: true,
          order: 7
        },
        127: {
          title: "贷方金额",
          field: "mnycredit",
          visible: true,
          order: 7
        },
        113: {
          title: "操作",
          width: "160px",
          isShow: true,
          visible: true,
          order: 16
        }
      },
      // 分页属性
      tableDataPage: 1,
      tableDataTotal: 0,
      // 选项变量

      /** 根据用户界面配置生成data数据 结束 */
      version: 3,
      search: {
        // 查询条件   业务表设置的筛选条件
        params: [],

        offset: 0,
        limit: 10,
        columnName: "", // 排序字段名
        order: "" // 排序
      },
      searchList: [],
      accountOptions:[],
      tableId: "1457254743943432957",
      schemeId: "1457254743943432955",
      formData: {
        uqglobalperiodidstart: "",
        uqglobalperiodidend: "",
        uqnumbering: "",
        varabstract: "",
        project: "",
        uqaccountid: "",
        intflag: "0",
        chargeagainstsubmitodd: []
      },
      uqglobalperiodidOptions: [],
      // uqnumberingOptions: [],
      // intflagOptions: [
      //   {
      //     label: "全部",
      //     value: 0
      //   },
      //   {
      //     label: "待审核",
      //     value: 1
      //   },
      //   {
      //     label: "待出纳",
      //     value: 2
      //   },{
      //     label: "待记账",
      //     value: 3
      //   },{
      //     label: "已记账",
      //     value: 4
      //   }
      // ]
    };
  },
  computed: {},
  methods: {
    changeChargeagainstsubmitodd(dateTemp) {
      this.formData.chargeagainstsubmitodd = [
        this.$dayjs(dateTemp.dtbegin).format("YYYY-MM-DD hh:mm:ss"),
        this.$dayjs(dateTemp.dtend).format("YYYY-MM-DD hh:mm:ss")
      ];
    },
    getList() {
      this.setLoad();
      /* 查询参数 和数据权限 */
      console.log(this.formData.uqaccountid)
      this.search.params = [
        {
          columnName: "m.CHARGEAGAINSTSUBMITODD",
          logic: "AND",
          queryType: ">=",
          value: this.formData.chargeagainstsubmitodd[0]
        },
        {
          columnName: "m.CHARGEAGAINSTSUBMITODD",
          logic: "AND",
          queryType: "<=",
          value: this.formData.chargeagainstsubmitodd[1]
        },
      ];
      this.search.params.push({
        columnName: "g.INTYEARMONTH",
        logic: "AND",
        queryType: ">=",
        value:this.formData.uqglobalperiodidstart
      })
      this.search.params.push({
        columnName: "g.INTYEARMONTH",
        logic: "AND",
        queryType: "<=",
        value:this.formData.uqglobalperiodidend
      })
      if ( this.formData.varabstract!=""){
        this.search.params.push({
          columnName: "d.varabstract",
          logic: "AND",
          queryType: "like",
          value:this.formData.varabstract
        })
      }
      if ( this.formData.uqaccountid!=""){
        this.search.params.push({
          columnName: "a.uqaccountid",
          logic: "AND",
          queryType: "=",
          value:this.formData.uqaccountid
        })
      }
      if ( this.formData.project!=""){
        this.search.params.push({
          columnName: "",
          logic: "AND",
          queryType: "(",
          value:""
        })
        this.search.params.push({
          columnName: "s.varcode",
          logic: "AND",
          queryType: "like",
          value:this.formData.project
        })
        this.search.params.push({
          columnName: "s.varname",
          logic: "or",
          queryType: "like",
          value:this.formData.project
        })
        this.search.params.push({
          columnName: "",
          queryType: ")",
          value: "",
          logic: ""
        })
      }
      if (this.isQueryConditionPanel) {
        this.search.params = this.search.params.concat(
          this.compositeCondition()
        );
      } else {
      }

      // 数据权限: 凭证主表tgl_voucher_mains
      this.pushDataPermissions(
        this.search.params,
        this.$route.meta.routerId,
        this.tableId
      );
      xjlllistPage(this.search)
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
      await selectPeriod({
        params: []
      }).then(responseData => {
        this.uqglobalperiodidOptions = responseData.data;
        this.formData.uqglobalperiodidstart = this.uqglobalperiodidOptions[0].intyearmonth;
        this.formData.uqglobalperiodidend = this.uqglobalperiodidOptions[0].intyearmonth;
        this.changeChargeagainstsubmitodd(this.uqglobalperiodidOptions[0]);
      });
      // 获取科目管理列表
      let search_List = {
        // params: [{ columnName: "INTFLAG", queryType: "=", value: "2" }]
        params: [],
        columnName: "varaccountfullcode",
        orderby: "asc"
      };
      // filter条件
      search_List.params.push.apply(search_List.params, []);
      // 数据权限: 会计科目表tgl_accounts
      this.pushDataPermissions(
        search_List.params,
        this.$route.meta.routerId,
        "1457254743943430348"
      );

      listTglAccountsAll(search_List).then(responseData => {
        if (responseData.code == 100) {
          this.accountOptions = responseData.data;
        } else {
          this.showMessage(responseData);
        }
      });
      this.setLoad();
      try {
        this.initOptions(this.queryModel);
        this.search.params = [
          {
            columnName: "m.CHARGEAGAINSTSUBMITODD",
            logic: "AND",
            queryType: ">",
            value: this.formData.chargeagainstsubmitodd[0]
          },
          {
            columnName: "m.CHARGEAGAINSTSUBMITODD",
            logic: "AND",
            queryType: "<",
            value: this.formData.chargeagainstsubmitodd[1]
          }
        ];
        if ( this.formData.varabstract!=""){
          this.search.params.push({
            columnName: "d.varabstract",
            logic: "AND",
            queryType: "like",
            value:this.formData.varabstract
          })
        }
        if ( this.formData.project!=""){
          this.search.params.push({
            columnName: "",
            logic: "AND",
            queryType: "(",
            value:""
          })
          this.search.params.push({
            columnName: "s.varcode",
            logic: "AND",
            queryType: "like",
            value:this.formData.project
          })
          this.search.params.push({
            columnName: "s.varname",
            logic: "or",
            queryType: "like",
            value:this.formData.project
          })
          this.search.params.push({
            columnName: "",
            queryType: ")",
            value: "",
            logic: ""
          })
        }
        this.search.params.push({
          columnName: "g.INTYEARMONTH",
          logic: "AND",
          queryType: ">=",
          value:this.formData.uqglobalperiodidstart
        })
        this.search.params.push({
          columnName: "g.INTYEARMONTH",
          logic: "AND",
          queryType: "<=",
          value:this.formData.uqglobalperiodidend
        })
        if ( this.formData.uqaccountid!=""){
          this.search.params.push({
            columnName: "a.uqaccountid",
            logic: "AND",
            queryType: "=",
            value:this.formData.uqaccountid
          })
        }
        // 数据权限: 凭证主表tgl_voucher_mains
        this.pushDataPermissions(
          this.search.params,
          this.$route.meta.routerId,
          this.tableId
        );
        let [
          listTglVoucherMainsRespData,
          listPermissionRespData
        ] = await Promise.all([
          xjlllistPage(this.search),
          listResourcePermission(this.$route.meta.routerId)
        ]);
        if (
          listTglVoucherMainsRespData.code == 100 &&
          listPermissionRespData.code == 100
        ) {
          this.tableDataTotal = listTglVoucherMainsRespData.data.total;
          this.tableData = listTglVoucherMainsRespData.data.rows
            ? listTglVoucherMainsRespData.data.rows
            : [];
          this.permission.view = listPermissionRespData.data.find(item => {
            return item.permission === "voucherflowquery:read";
          });
          this.permission.export = listPermissionRespData.data.find(item => {
            return item.permission === "voucherflowquery:export";
          });

          this.permission.add = listPermissionRespData.data.find(item => {
            return item.permission === "voucherflowquery:create";
          });
          this.permission.edit = listPermissionRespData.data.find(item => {
            return item.permission === "voucherflowquery:update";
          });
          this.permission.remove = listPermissionRespData.data.find(item => {
            return item.permission === "voucherflowquery:delete";
          });
        } else {
          this.showMessage(
            listPermissionRespData.code != 100
              ? listPermissionRespData
              : listTglVoucherMainsRespData
          );
        }
        this.resetLoad();
      } catch (error) {
        this.outputError(error);
      }
    },
    onView(scope) {
      this.certShow = true;
      setTimeout(_ => {
        getTglVoucherMainsById(scope.row.voucherid).then(res => {
          this.$refs.addCertificateDialog.onView(res.data);
        });
      }, 100);
    },

    onCreate(scope) {
      console.log(scope.row);
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
          deleteTglVoucherMains(scope.row)
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
    onOpre(code, scope = null) {
      let checkBoxData = [];
      let params = [];
      checkBoxData = validatenull(scope)
        ? this.$refs.table.getCheckboxRecords()
        : [scope.row];
      if (checkBoxData.length <= 0) {
        this.showMessage({
          type: "warning",
          msg: "请选择要操作的数据"
        });
        return;
      }
      try {
        checkBoxData.forEach(item => {
          if (item.intflag != 0 && code == "start") {
            throw new Error(`${item.intvouchernum}不是未记账，不能记账!`);
          } else if (item.intflag != 1 && code == "stop") {
            throw new Error(`${item.intvouchernum}不是已记账状态!`);
          } else {
            params.push(item.uqvoucherid);
          }
        });
        this.code = code;
        this.params = params;
        this.dialogVisible = true;
        this.title = ` 选择${code == "start" ? "记账" : "反记账"}日期`;
      } catch (error) {
        this.showMessage({
          type: "warning",
          msg: error
        });
      }
    },
    submitCheck() {
      this.setLoad();
      this.dialogVisible = false;
      if (this.code == "start") {
        voucherEnd(
          this.params,
          this.$dayjs(this.dtchecker).format("YYYY-MM-DD")
        )
          .then(responseData => {
            if (responseData.code == 100) {
              this.getList();
              this.showMessage({
                type: "success",
                msg: "记账成功"
              });
            } else {
              this.showMessage(responseData);
            }
            this.resetLoad();
          })
          .catch(error => {
            this.outputError(error);
          });
      } else {
        unVouCherEnd(this.params)
          .then(responseData => {
            if (responseData.code == 100) {
              this.getList();
              this.showMessage({
                type: "success",
                msg: "反记账成功"
              });
            } else {
              this.showMessage(responseData);
            }
            this.resetLoad();
          })
          .catch(error => {
            this.outputError(error);
          });
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
