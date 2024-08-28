<template>
  <el-row class="dc-container" v-loading="loading">
    <edit-form
      ref="editForm"
      v-on:save-finished="getList"
      v-on:before-load="setLoad"
      v-on:after-load="resetLoad"
      :permission="permission"
    ></edit-form>
    <el-form
      ref="elForm"
      :model="formData"
      label-width="100px"
      class="dc-el-form_ElQueryForm"
    >
      <el-row>
        <el-col :span="6">
          <el-form-item label="科目编号" prop="inputNum">
            <el-input
              v-model="formData.inputNum"
              placeholder="输入科目编号"
              clearable
              :style="{ width: '100%' }"
            >
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="科目名称" prop="imputName">
            <el-input
              v-model="formData.imputName"
              placeholder="输入科目名称"
              clearable
              :style="{ width: '100%' }"
            >
            </el-input>
          </el-form-item>
        </el-col>
        <input ref="fileinput" id="fileinput"  type="file" style="display: none" @change="uploadExcel"
               accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel "/>
        <el-col :span="12">
          <el-button v-on:click="onSearch" type="primary" icon="el-icon-search"
            >查询</el-button
          >
          <el-button v-on:click="checkFile" type="primary" icon="el-icon-upload2"
            >导入</el-button
          >
          <el-button v-on:click="exportExcel" type="primary" icon="el-icon-download"
            >导出</el-button
          >
          <el-button v-on:click="exportTemplate('accountCurrentInitializationImportModel')" type="primary" icon="el-icon-download"
            >模板下载</el-button
          >
        </el-col>
        <el-col :span="24">
          <el-button v-on:click="onEdit" type="primary"
            >初始化</el-button
          >          <el-button v-on:click="onEdit" type="primary"
            >修改初始化</el-button
          >          <el-button v-on:click="deleteInit" type="primary"
            >清除初始化</el-button
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
        min-width="180px"
        align="left"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['125'].field"
        :title="tableColumns['125'].title"
        :width="tableColumns['125'].width"
        :visible="tableColumns['125'].visible"
        :params="{ sortId: '125', summary: false }"
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
        :field="tableColumns['126'].field"
        :title="tableColumns['126'].title"
        :width="tableColumns['126'].width"
        :visible="tableColumns['126'].visible"
        :params="{ sortId: '126', summary: false }"
        tree-node
        resizable
        min-width="100px"
        align="right"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      >
      </ux-table-column>
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
        :field="tableColumns['127'].field"
        :title="tableColumns['127'].title"
        :width="tableColumns['127'].width"
        :visible="tableColumns['127'].visible"
        :params="{ sortId: '127', summary: false }"
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
        min-width="120px"
        align="left"
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
        min-width="180px"
        align="left"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>

      <ux-table-column
        :field="tableColumns['136'].field"
        :title="tableColumns['136'].title"
        :width="tableColumns['136'].width"
        :visible="tableColumns['136'].visible"
        :params="{ sortId: '136', summary: false }"
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
      >
        <template slot-scope="{ row, rowIndex }">
          <div
            v-html="
              ((value, row, rowIndex) => {
                if (row.submitodd == 0) {
                  return `<span style='font-size: 12px'>正常</span>`;
                } else {
                  return `<span style='font-size: 12px'>暂存</span>`;
                }
              })(row.submitodd, row, rowIndex)
            "
          ></div>
        </template>
      </ux-table-column>
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
      >
        <template slot-scope="{ row, rowIndex }">
          <div
            v-html="
              ((value, row, rowIndex) => {
                if (row.intflag == 2) {
                  return `<span style='font-size: 12px'>已记账</span>`;
                } else {
                  return `<span style='font-size: 12px'>未记账</span>`;
                }
              })(row.intflag, row, rowIndex)
            "
          ></div>
        </template>
      </ux-table-column>
      <ux-table-column
        :field="tableColumns['132'].field"
        :title="tableColumns['132'].title"
        :width="tableColumns['132'].width"
        :visible="tableColumns['132'].visible"
        :params="{ sortId: '132', summary: false }"
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
        :field="tableColumns['135'].field"
        :title="tableColumns['135'].title"
        :width="tableColumns['135'].width"
        :visible="tableColumns['135'].visible"
        :params="{ sortId: '135', summary: false }"
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
      >
        <template slot-scope='scope'>
          {{ uqtypeidformat(scope.row)}}
        </template>
      </ux-table-column>
      <ux-table-column
        :field="tableColumns['134'].field"
        :title="tableColumns['134'].title"
        :width="tableColumns['134'].width"
        :visible="tableColumns['134'].visible"
        :params="{ sortId: '134', summary: false }"
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
        min-width="120px"
        align="left"
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
        align="left"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>

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
            v-on:click="onOpre('start', scope)"
            type="success"
            v-show="permission.view"
            content="审核"
            placement="top"
            icon-name="el-icon-circle-check"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            v-on:click="onOpre('stop', scope)"
            type="danger"
            v-show="permission.view"
            content="反审核"
            placement="top"
            icon-name="el-icon-warning"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
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
import {
  accountlist,deletedata,exportExcels,importExcel
} from "@/api/vouchermanager/tglAcOffsetMain";
/** 根据用户界面配置import组件 开始 */
import addCertificateDialog from "@/views/components/certificate/addCertificateDialog";
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
          title: "交易时间",
          field: "chargeagainstsubmitodd",
          visible: false,
          order: 0
        },
        119: {
          title: "科目编号",
          field: "varaccountcode",
          isShow: false,
          visible: true,
          order: 0
        },
        120: {
          title: "凭证类型",
          field: "uqnumbering",
          visible: false,
          order: 1
        },
        139: {
          title: "科目名称",
          field: "varaccountname",
          visible: true,
          order: 1
        },
        125: {
          title: "会计期",
          field: "uqglobalperiodid.varname",
          visible: false,
          order: 2
        },
        126: {
          title: "上级科目",
          field: "uqparentid",
          visible: true,
          order: 3
        },
        149: {
          title: "借方金额合计",
          field: "mnydebitsum",
          visible: false,
          order: 4
        },
        127: {
          title: "贷方金额合计",
          field: "mnycreditsum",
          visible: false,
          order: 5
        },
        128: {
          title: "会计主管.名称",
          field: "uqfinacialmanagerid.name",
          visible: false,
          order: 6
        },
        131: {
          title: "科目性质",
          field: "intproperty",
          visible: true,
          order: 7
        },
        130: {
          title: "科目类别",
          field: "uqtypeid",
          visible: true,
          order: 8
        },
        129: {
          title: "初始化日期",
          field: "inidate",
          visible: true,
          order: 9
        },
        132: {
          title: "制单人",
          field: "uqfillerid.name",
          visible: false,
          order: 10
        },
        151: {
          title: "被冲销凭证ID",
          field: "uqcancelid",
          visible: false,
          order: 11
        },
        133: {
          title: "记账日期",
          field: "dtaccountant",
          visible: false,
          order: 12
        },
        134: {
          title: "出纳日期",
          field: "dtcasher",
          visible: false,
          order: 13
        },
        135: {
          title: "审核日期",
          field: "dtchecker",
          visible: false,
          order: 14
        },
        136: {
          title: "制单日期",
          field: "dtfiller",
          visible: false,
          order: 15
        },
        137: {
          title: "状态",
          field: "submitodd",
          visible: false,
          order: 15
        },
        138: {
          title: "审核状态",
          field: "intflag",
          visible: false,
          order: 15
        },
        113: {
          title: "操作",
          width: "160px",
          isShow: true,
          visible: false,
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
      tableId: "1457254743943432957",
      schemeId: "1457254743943432955",
      formData: {
        uqglobalperiodid: "",
        uqnumbering: "",
        intflag: "0",
        chargeagainstsubmitodd: []
      },
      uqglobalperiodidOptions: [],
      uqnumberingOptions: [],
      intflagOptions: [
        {
          label: "未记账",
          value: "0"
        },
        {
          label: "已记账",
          value: "1"
        }
      ]
    };
  },
  computed: {},
  methods: {
    uploadExcel(evt) {
      const files = evt.target.files
      if (files == null || files.length == 0) {
        return
      }
      let formData = new FormData()
      formData.append('file', files[0])
      this.setLoad()
      importExcel(formData).then((responseData) => {
        if (responseData.code == 100) {
          this.getList()
          this.showMessage({
            type: 'success',
            msg: '导入成功'
          })

        } else {
          this.showMessage(responseData)
        }
        this.$refs.fileinput.value = ''
        this.resetLoad()
      }).catch((error) => {
        this.$refs.fileinput.value = ''
        this.outputError(error)
      })
    },
    checkFile() {
      document.querySelector('#fileinput').click()
    },
    uqtypeidformat(rowObject){
      if (rowObject.uqtypeid == 1 || rowObject.uqtypeid == '1')
        return '预付';
      if (rowObject.uqtypeid == 2 || rowObject.uqtypeid == '2')
        return '预收';
      if (rowObject.uqtypeid == 8 || rowObject.uqtypeid == '8')
        return '应付';
      if (rowObject.uqtypeid == 7 || rowObject.uqtypeid == '7')
        return '应收';
      return '';
    },
    exportExcel(){
      exportExcels(this.search)
        .then(responseData => {
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
        })
        .catch(error => {
          this.outputError(error);
        });
    },
    deleteInit(){
      var checkboxRecords = this.$refs.table.getCheckboxRecords();
      if (checkboxRecords.length==0){
        this.showMessage({
          type: "warning",
          msg: "请选择需要清除的数据"
        });
      }
      this.$confirm(
        `是否要清除所有初始化明细数据？`,
        "确认",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      ).then(_ => {
        this.setLoad();
        deletedata(checkboxRecords)
            .then(responseData => {
              if (responseData.code == 100) {
                this.getList();
                this.showMessage({
                  type: "success",
                  msg: "清除成功"
                });
              } else {
                this.showMessage(responseData);
              }
              this.resetLoad();
            })
            .catch(error => {
              this.outputError(error);
            });

      });

      console.log(checkboxRecords)
    },
    periodChange(option) {
      this.changeChargeagainstsubmitodd(
        this.$loadsh.find(this.uqglobalperiodidOptions, {
          uqglobalperiodid: option
        })
      );
    },
    changeChargeagainstsubmitodd(dateTemp) {
      this.formData.chargeagainstsubmitodd = [
        this.$dayjs(dateTemp.dtbegin).format("YYYY-MM-DD hh:mm:ss"),
        this.$dayjs(dateTemp.dtend).format("YYYY-MM-DD hh:mm:ss")
      ];
      this.pickerOptionsChargeagainstsubmitodd = {
        disabledDate(time) {
          return (
            time.getTime() <
              new Date(
                new Date(dateTemp.dtbegin).getTime() - 24 * 60 * 60 * 1000
              ) || time.getTime() >= new Date(dateTemp.dtend)
          );
        }
      };
    },
    getList() {
      this.setLoad();
      /* 查询参数 和数据权限 */
      // this.search.params = [
      //   {
      //     columnName: "uqglobalperiodid",
      //     logic: "AND",
      //     queryType: "=",
      //     value: this.formData.uqglobalperiodid
      //   },
      //   {
      //     columnName: "uqnumbering",
      //     logic: "AND",
      //     queryType: "=",
      //     value: this.formData.uqnumbering
      //   },
      //   {
      //     columnName: "CHARGEAGAINSTSUBMITODD",
      //     logic: "AND",
      //     queryType: ">=",
      //     value: this.formData.chargeagainstsubmitodd[0]
      //   },
      //   {
      //     columnName: "CHARGEAGAINSTSUBMITODD",
      //     logic: "AND",
      //     queryType: "<=",
      //     value: this.formData.chargeagainstsubmitodd[1]
      //   },
      // ];
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
      accountlist(this.search)
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
        this.formData.uqglobalperiodid = this.uqglobalperiodidOptions[0].uqglobalperiodid;
        this.changeChargeagainstsubmitodd(this.uqglobalperiodidOptions[0]);
      });
      await tglVoucherNumberings().then(responseData => {
        this.uqnumberingOptions = responseData.data;
        this.formData.uqnumbering = "00000000-0000-0000-0000-000000000000";
      });
      this.setLoad();
      try {
        this.initOptions(this.queryModel);
        this.search.params = [
          {
            columnName: "uqglobalperiodid",
            logic: "AND",
            queryType: "=",
            value: this.formData.uqglobalperiodid
          },
          {
            columnName: "uqnumbering",
            logic: "AND",
            queryType: "=",
            value: this.formData.uqnumbering
          },
          {
            columnName: "CHARGEAGAINSTSUBMITODD",
            logic: "AND",
            queryType: ">=",
            value: this.formData.chargeagainstsubmitodd[0]
          },
          {
            columnName: "CHARGEAGAINSTSUBMITODD",
            logic: "AND",
            queryType: "<=",
            value: this.formData.chargeagainstsubmitodd[1]
          }
        ];
        if (this.formData.intflag==0){
          this.search.params.push({
            columnName: "intflag",
            logic: "AND",
            queryType: "=",
            value: 1
          })
          this.search.params.push({
            columnName: "intcashflag",
            logic: "AND",
            queryType: "in",
            value: [0,2]
          })
        }else {
          this.search.params.push({
            columnName: "intflag",
            logic: "AND",
            queryType: "=",
            value: 2
          })
          this.search.params.push({
            columnName: "intcashflag",
            logic: "AND",
            queryType: "in",
            value: [0,2]
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
          listTglVoucherMainsPage(this.search),
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
            return item.permission === "voucherend:read";
          });
          this.permission.export = listPermissionRespData.data.find(item => {
            return item.permission === "voucherend:export";
          });

          this.permission.add = listPermissionRespData.data.find(item => {
            return item.permission === "voucherend:create";
          });
          this.permission.edit = listPermissionRespData.data.find(item => {
            return item.permission === "voucherend:update";
          });
          this.permission.remove = listPermissionRespData.data.find(item => {
            return item.permission === "voucherend:delete";
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
        getTglVoucherMainsById(scope.row.uqvoucherid).then(res => {
          this.$refs.addCertificateDialog.onView(res.data);
        });
      }, 100);
    },

    onCreate(scope) {
      console.log(scope.row);
    },
    onEdit() {
      var checkboxRecords = this.$refs.table.getCheckboxRecords();
      if (checkboxRecords.length!=1){
        this.showMessage({
          type: "warning",
          msg: "请选择一个初始化的数据"
        });
      }
      this.$refs.editForm.$emit("openEditDialog",checkboxRecords[0].uqaccountid);
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
          debugger
          if (item.intflag != 1 && code == "start") {
            throw new Error(`${item.intvouchernum}不是未记账，不能记账!`);
          } else if (item.intflag != 2 && code == "stop") {
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
    this.getList()
    this.columnDrop(this.$refs.table);
  }
};
</script>
