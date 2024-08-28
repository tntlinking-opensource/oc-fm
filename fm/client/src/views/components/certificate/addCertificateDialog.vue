<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogVisible"
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    append-to-body
    fullscreen
    @closed="closedDialog"
  >
    <el-row :gutter="15">
      <el-form
        ref="elForm"
        :model="formData"
        :rules="rules"
        size="medium"
        label-width="100px"
      >
        <el-col :span="6">
          <el-form-item label="会计期" prop="uqglobalperiodidtext">
            <el-select
              v-model="formData.uqglobalperiodidtext"
              placeholder="请选择会计期"
              :disabled="formState == 'view'"
              :style="{ width: '100%' }"
              @change="periodChange"
            >
              <el-option
                v-for="(item, index) in uqglobalperiodidOptions"
                :key="index"
                :label="item.varname"
                :value="item.varname"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="交易日期" prop="chargeagainstsubmitodd">
            <el-date-picker
              v-model="formData.chargeagainstsubmitodd"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
              :disabled="formState == 'view'"
              :pickerOptions="pickerOptionsChargeagainstsubmitodd"
              :style="{ width: '100%' }"
              placeholder="请选择交易日期"
            ></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="附件数" prop="intaffix">
            <el-input-number
              v-model="formData.intaffix"
              controls-position="right"
              :disabled="formState == 'view'"
              @change="handleChange"
              :style="{ width: '100%' }"
              :min="1"
              :max="999"
            ></el-input-number>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="制证日期" prop="dtfiller">
            <el-date-picker
              v-model="formData.dtfiller"
              :disabled="formState == 'view'"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
              :pickerOptions="pickerOptionsDtfiller"
              :style="{ width: '100%' }"
              placeholder="请选择制证日期"
            ></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="凭证编号" prop="intvouchernum">
            <el-input
              v-model="formData.intvouchernum"
              placeholder="自动生成"
              :disabled="true"
              :style="{ width: '100%' }"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-form>
    </el-row>
    <vxe-grid
      ref="xGrid"
      v-bind="gridOptions"
      footerRowClassName="footer-class"
      @cell-click="cellClick"
    >
      <template #toolbar_buttons>
        <vxe-button status="primary" icon="vxe-icon-save" @click="printEvent"
          >打印</vxe-button
        >
        <vxe-button
          v-if="formState == 'edit'"
          status="primary"
          icon="vxe-icon-save"
          @click="saveEvent"
          >保存</vxe-button
        >
        <vxe-button
          v-if="formState == 'edit'"
          status="warning"
          icon="vxe-icon-lock"
          @click="tempEvent"
          >暂存</vxe-button
        >
        <vxe-button
          v-show="isShowVarledge"
          icon="vxe-icon-flow-branch"
          :loading="loadCode == 'tglLedgeLoad'"
          @click="tglLedge"
          >分户</vxe-button
        >
        <vxe-button
          v-show="isShowUqtype && formState == 'edit'&&!isShowVarledge"
          icon="vxe-icon-cloudy"
          @click="currentWriteOff"
          >往来核销</vxe-button
        >
        <vxe-button
          v-show="isShowFlowType && formState == 'edit'"
          icon="vxe-icon-file-txt"
          @click="flowType"
          >现金流量编制</vxe-button
        >
      </template>
      <template #varabstract_edit="{ row }">
        <vxe-input
          v-model="row.varabstract"
          readonly
          type="search"
          v-on:focus="addRemark"
          v-on:search-click="addRemark"
        ></vxe-input>
      </template>
      <template #hkont_edit="{ row }">
        <!-- <vxe-pulldown ref="xDown" v-model="accountVisible">
              <template #default>
                <vxe-input
                  v-model="row.hkont"
                  placeholder="可搜索的大数据下拉框"
                  @focus="focusHkont"
                  @keyup="keyupHkont(row.hkont)"
                ></vxe-input>
              </template>
              <template #dropdown>
                <vxe-list
                  height="200"
                  class="my-dropdown2"
                  :data="accountOptions"
                  auto-resize
                >
                  <template #default="{ items }">
                    <div
                      class="list-item2"
                      v-for="item in items"
                      :key="item.uqaccountid"
                      @click="changeAccountQuery(item)"
                    >
                      <i class="vxe-icon-star-fill"></i>
                      <span
                        >[{{ item.varaccountcode }}]{{
                          item.varaccountname
                        }}</span
                      >
                    </div>
                  </template>
                </vxe-list>
              </template>
            </vxe-pulldown> -->
        <vxe-input
          v-model="row.hkont"
          readonly
          type="search"
          v-on:focus="accountQuery"
          v-on:search-click="accountQuery"
        ></vxe-input>
      </template>
      <template #operate="{ row }">
        <vxe-button
          icon="vxe-icon-add"
          title="新增"
          status="success"
          type="text"
          @click="insertEvent(row)"
        ></vxe-button>
        <vxe-button
          icon="vxe-icon-delete"
          title="删除"
          type="text"
          status="danger"
          @click="removeRowEvent"
        ></vxe-button>
      </template>
    </vxe-grid>
    <span slot="footer" class="dialog-footer">
      <div class="foot-info">
        <div>大写金额：{{ !footerObj ? "/" : numToCny(footerObj.bor) }}</div>
        <div>
          合计：{{ !footerObj ? "/" : formatAmount(footerObj.bor) }}
          |
          {{ !footerObj ? "/" : formatAmount(footerObj.loan) }}
        </div>
      </div>
    </span>
    <!-- 科目 -->
    <AccountingAccountQuery
      ref="accountingAccountQuery"
      :appendToBody="true"
      :isLastLevel="true"
      @onGetData="onGetDataAccountQuery"
    />
    <!-- 摘要 -->
    <AddAbstractDialog ref="addAbstractDialog" @onGetData="onGetDataAbstract" />
    <!-- 分户 -->
    <TglLedgetypeDialog
      ref="tglLedgetypeDialog"
      @onGetData="onGetDataTglLedgetype"
    />
    <!-- 现金流量 -->
    <FlowTypeDialog ref="flowTypeDialog" @onGetData="onGetDataFlowTypeDialog" />
  </el-dialog>
</template>

<script>
import { numToCny } from "@/utils/common.js";

import {
  tglVoucherMainsSave,
  tglGlobalPeriods,
  getByUqaccountId
} from "@/api/components/certificate/certificate";
import { listTglAccountsAll } from "@/api/vouchermanager/InitLedgePeriod/tglAccounts.js";

import AccountingAccountQuery from "@/views/components/AccountingAccountQuery";
import AddAbstractDialog from "@/views/components/certificate/addAbstractDialog";
import TglLedgetypeDialog from "@/views/components/certificate/tglLedgetypeDialog";
import FlowTypeDialog from "@/views/components/certificate/flowTypeDialog";
import DcMain from "@/views/components/dcMain";
// 打印样式
const printStyle = `
        *{
          font-size:16px
        }
        .title {
          text-align: center;
          font-size:48px;
        }
        .my-top{
          margin-bottom: 10px;
        }
        .my-list-row {
          display: flex;
          justify-content: space-between;
          align-items: center;
        }
        .my-list-col {
          font-size:16px
        }
        .foot-info {
          font-size:16px;
          margin-top: 10px;
          display: flex;
          font-weight: 600;
          text-align: center;
          padding: 5px 0 0px 0;
          border-top: 1px dashed #c6c6c6;
          align-items: center;
          justify-content: space-between;
        }
        `;
export default {
  extends: DcMain,
  components: {
    AccountingAccountQuery,
    AddAbstractDialog,
    TglLedgetypeDialog,
    FlowTypeDialog
  },
  data() {
    return {
      formState: "edit",
      dialogVisible: false,
      currentData: {},
      title: "",
      // 制证日期限制
      pickerOptionsDtfiller: {
        disabledDate(time) {
          return time.getTime() > new Date();
        }
      },
      // 制证日期限制
      pickerOptionsChargeagainstsubmitodd: {},
      formData: {
        uqvoucherid:undefined,
        uqglobalperiodid: undefined,
        uqglobalperiodidtext: undefined,
        intaffix: 1,
        chargeagainstsubmitodd: this.$dayjs().format("YYYY-MM-DD hh:mm:ss"),
        dtfiller: this.$dayjs().format("YYYY-MM-DD hh:mm:ss"),
        intvouchernum: undefined
      },
      rules: {
        uqglobalperiodidtext: [
          {
            required: true,
            message: "请选择会计期",
            trigger: "change"
          }
        ],
        intaffix: [
          {
            required: true,
            message: "请输入附件数",
            trigger: "blur"
          }
        ],
        chargeagainstsubmitodd: [],
        dtfiller: [],
        intvouchernum: []
      },
      uqglobalperiodidOptions: [],
      // vxe-table配置
      gridOptions: {
        cellClassName: this.cellClassName,
        border: true,
        resizable: true,
        keepSource: true,
        showOverflow: true,
        showFooter: true,
        footerMethod: this.footerMethod,
        loading: false,
        maxHeight: 500,
        id: "toolbar_demo_2",
        printConfig: {
          type: "html",
          style: printStyle,
          isFooter:false,
          columns: [
            { field: "varabstract" },
            { field: "hkont" },
            { field: "bory" },
            { field: "boryq" },
            { field: "boryb" },
            { field: "borys" },
            { field: "borw" },
            { field: "borwq" },
            { field: "borwb" },
            { field: "borws" },
            { field: "borwy" },
            { field: "borj" },
            { field: "borf" },
            { field: "loany" },
            { field: "loanyq" },
            { field: "loanyb" },
            { field: "loanys" },
            { field: "loanw" },
            { field: "loanwq" },
            { field: "loanwb" },
            { field: "loanws" },
            { field: "loanwy" },
            { field: "loanj" },
            { field: "loanf" }
          ],
          beforePrintMethod: this.beforePrintMethod
        },
        keyboardConfig: {
          isEsc: true,
          isEnter: true
        },
        rowConfig: {
          isCurrent: true
        },
        editConfig: {
          trigger: "click",
          mode: "row",
          showStatus: true,
          autoClear: true,
          showIcon: false,
          enabled: this.formState == "edit"
        },
        customConfig: {
          storage: true
        },
        toolbarConfig: {
          custom: true,
          slots: {
            buttons: "toolbar_buttons"
          }
        },
        columns: [
          {
            field: "varabstract",
            title: "摘要",
            editRender: {},
            slots: { edit: "varabstract_edit" },
            headerAlign: "center",
            footerAlign: "center",
            minWidth: "230px"
          },
          {
            field: "hkont",
            title: "科目",
            showOverflow: true,
            editRender: {},
            slots: { edit: "hkont_edit" },
            headerAlign: "center"
          },
          {
            title: "借方金额",
            align: "center",
            children: [
              {
                field: "bory",
                title: "亿",
                showOverflow: true,
                align: "center",
                width: "50px"
              },
              {
                field: "boryq",
                title: "千",
                showOverflow: true,
                align: "center",
                width: "50px"
              },
              {
                field: "boryb",
                title: "百",
                showOverflow: true,
                align: "center",
                width: "50px"
              },
              {
                field: "borys",
                title: "十",
                showOverflow: true,
                align: "center",
                width: "50px"
              },
              {
                field: "borw",
                title: "万",
                showOverflow: true,
                align: "center",
                width: "50px"
              },
              {
                field: "borwq",
                title: "千",
                showOverflow: true,
                align: "center",
                width: "50px"
              },
              {
                field: "borwb",
                title: "百",
                showOverflow: true,
                align: "center",
                width: "50px"
              },
              {
                field: "borws",
                title: "十",
                showOverflow: true,
                align: "center",
                width: "50px"
              },
              {
                field: "borwy",
                title: "元",
                showOverflow: true,
                align: "center",
                width: "50px"
              },
              {
                field: "borj",
                title: "角",
                showOverflow: true,
                align: "center",
                width: "50px"
              },
              {
                field: "borf",
                title: "分",
                showOverflow: true,
                align: "center",
                width: "50px"
              }
            ]
          },
          {
            title: "贷方金额",
            align: "center",
            children: [
              {
                field: "loany",
                title: "亿",
                showOverflow: true,
                align: "center",
                width: "50px"
              },
              {
                field: "loanyq",
                title: "千",
                showOverflow: true,
                align: "center",
                width: "50px"
              },
              {
                field: "loanyb",
                title: "百",
                showOverflow: true,
                align: "center",
                width: "50px"
              },
              {
                field: "loanys",
                title: "十",
                showOverflow: true,
                align: "center",
                width: "50px"
              },
              {
                field: "loanw",
                title: "万",
                showOverflow: true,
                align: "center",
                width: "50px"
              },
              {
                field: "loanwq",
                title: "千",
                showOverflow: true,
                align: "center",
                width: "50px"
              },
              {
                field: "loanwb",
                title: "百",
                showOverflow: true,
                align: "center",
                width: "50px"
              },
              {
                field: "loanws",
                title: "十",
                showOverflow: true,
                align: "center",
                width: "50px"
              },
              {
                field: "loanwy",
                title: "元",
                showOverflow: true,
                align: "center",
                width: "50px"
              },
              {
                field: "loanj",
                title: "角",
                showOverflow: true,
                align: "center",
                width: "50px"
              },
              {
                field: "loanf",
                title: "分",
                showOverflow: true,
                align: "center",
                width: "50px"
              }
            ]
          },
          {
            title: "操作",
            width: "125px",
            align: "center",
            slots: { default: "operate" },
            fixed: "right"
          }
        ],
        data: []
      },
      footerObj: undefined,
      footerData: [
        [
          "分录合计",
          "",
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null
        ]
      ],
      accountVisible: false,
      accountOptions: [],
      hkont: {},
      // 控制分户按钮
      isShowVarledge: false,
      // 控制现金流量按钮
      isShowFlowType: false,
      // 控制往来核销按钮
      isShowUqtype: false,
      // loading 标识
      loadCode: undefined,
      // 是否复制
      isCopy: false
    };
  },
  methods: {
    numToCny,
    // 打开弹窗/新增/修改/查看
    open(title, id = "") {
      this.dialogVisible = false;
      this.gridOptions.editConfig.enabled = true;
      this.dialogVisible = true;
      this.title = title;
      this.$nextTick(_ => {
        this.$refs.xGrid.setMergeFooterItems([
          { row: 0, col: 0, rowspan: 0, colspan: 2 },
          { row: 1, col: 0, rowspan: 0, colspan: 2 }
        ]);
        this.getPeriodList();
        this.$refs.xGrid.insert({});
        // this.btnCheck();
      });
    },
    mainformatDate(Schargeagainstsubmitodd){
      const now = new Date(Schargeagainstsubmitodd);
      // 获取年份和月份
      const year = now.getFullYear();
      const month = String(now.getMonth() + 1).padStart(2, '0'); // 月份从0开始，所以需要加1，并用padStart方法添加前导零
      const day = String(now.getDate()).padStart(2, '0'); // 获取日期，并确保是两位数
      return year+'-'+month+'-'+day;
    },
    // 查看 / 编辑
    onView(data, state = "view", isCopy = false) {
      this.isCopy = isCopy;
      // 禁用编辑状态
      this.gridOptions.editConfig.enabled = state == "edit";
      this.gridOptions.columns.splice(this.gridOptions.columns.length - 1, 1);
      this.getPeriodList();

      let {
        // 会计期
        uqglobalperiodid,
        // 交易日期
        chargeagainstsubmitodd,
        // 附件数
        intaffix,
        // 制证日期
        dtfiller,
        // 凭证编号
        intvouchernum,
        // 分录数据
        tglVoucherDetailsList
      } = data;
      this.formState = state;
      this.dialogVisible = true;
      this.title = "查看";
      this.formData = data;
      this.changeChargeagainstsubmitodd(uqglobalperiodid);
      this.formData.uqglobalperiodid = uqglobalperiodid;
      this.formData.uqglobalperiodidtext = uqglobalperiodid.varname;
      this.formData.chargeagainstsubmitodd = chargeagainstsubmitodd;
      this.formData.intaffix = intaffix;
      this.formData.dtfiller = dtfiller;
      this.formData.intvouchernum = intvouchernum;
      tglVoucherDetailsList.forEach(item => {
        item[
          "hkont"
        ] = `[${item.tglAccounts.varaccountcode}]${item.tglAccounts.varaccountname}`;
        item["value"] = item.mnycredit == 0 ? item.mnydebit : item.mnycredit;
        item["code"] = item.mnycredit == 0 ? "bor" : "loan";
        this.gridOptions.data.push(item);
        item["foot"] = this.setTglMap(
          item.tglledgetype,
          item.mnycredit == 0 ? item.mnydebit : item.mnycredit
        );
      });
      this.gridOptions.data.forEach(item => {
        this.splitCellNum(
          { row: item, data: this.gridOptions.data },
          parseFloat(item.value).toFixed(2),
          this.getOverNum(item.code),
          item.code
        );
      });
    },
    setTglMap(tgl, num) {
      let tempMap = new Map();
      tgl.forEach(item => {
        tempMap.set(item.uqparentid, [
          [
            "合计",
            "",
            "",
            `￥${this.$XEUtils.commafy(this.$XEUtils.toNumber(num), {
              digits: 2
            })}`
          ]
        ]);
      });
      return tempMap;
    },
    // 负数样式
    cellClassName(cell) {
      let { $rowIndex, $columnIndex, row } = cell;
      if ($columnIndex != 0 && $columnIndex != 1) {
        let bol = $columnIndex >= 2 && $columnIndex <= 12 ? "bor" : "loan";

        if (bol.eq("bor")) {
          return !row.borFullCode
            ? ""
            : String(row.borFullCode).indexOf("-") > -1
            ? "txt-red"
            : "";
        } else {
          return !row.loanFullCode
            ? ""
            : String(row.loanFullCode).indexOf("-") > -1
            ? "txt-red"
            : "";
        }
      }
    },
    // 借、贷方单元格填充
    cellClick(cellClickData) {
      let { column, row } = cellClickData;
      this.btnCheck(row["tglAccounts"]);
      if (!!column.field && this.formState == "edit") {
        let code =
          column.field.indexOf("bor") > -1
            ? "bor"
            : column.field.indexOf("loan") > -1
            ? "loan"
            : "other";
        if (code != "other") {
          let overNum = "";
          let overAry = this.getOverNum(code); //code == "bor" ? bor : loan;
          for (let i = 0; i < overAry.length; i++) {
            overNum +=
              row[overAry[i]] == null || row[overAry[i]] == ""
                ? "0"
                : row[overAry[i]];
          }

          let t = row[code == "bor" ? "borFullCode" : "loanFullCode"];
          overNum = !t ? Number(overNum.insertStr(9, ".")).toFixed(2) : t;

          // 输入借方金额
          this.$prompt(code == "bor" ? "借方金额" : "贷方金额", {
            inputValue: overNum,
            closeOnClickModal: false,
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            inputPattern: /^(?:0|-?[1-9]\d{0,8})(?:\.\d{0,2})?$/,
            inputErrorMessage:
              "仅能输入数字，且整数位最多九位，小数位最多保留两位"
          })
            .then(({ value }) => {
              value = parseFloat(value).toFixed(2);
              this.splitCellNum(cellClickData, value, overAry, code);
            })
            .catch(_ => {});
        }
      }
      this.currentData = row;
    },

    // 获取借、贷标识数组
    getOverNum(code) {
      return code == "bor"
        ? [
            "bory",
            "boryq",
            "boryb",
            "borys",
            "borw",
            "borwq",
            "borwb",
            "borws",
            "borwy",
            "borj",
            "borf"
          ]
        : [
            "loany",
            "loanyq",
            "loanyb",
            "loanys",
            "loanw",
            "loanwq",
            "loanwb",
            "loanws",
            "loanwy",
            "loanj",
            "loanf"
          ];
    },
    // 拆分单元格
    splitCellNum(cellClickData, value, overAry, code) {
      let { row, data } = cellClickData;
      // 借方标识数组
      let bor = [
        "bory",
        "boryq",
        "boryb",
        "borys",
        "borw",
        "borwq",
        "borwb",
        "borws",
        "borwy",
        "borj",
        "borf"
      ];
      // 贷方标识数组
      let loan = [
        "loany",
        "loanyq",
        "loanyb",
        "loanys",
        "loanw",
        "loanwq",
        "loanwb",
        "loanws",
        "loanwy",
        "loanj",
        "loanf"
      ];
      // row中不能同时出现borFullCode与loanFullCode
      delete row.borFullCode;
      delete row.loanFullCode;
      row[code == "bor" ? "borFullCode" : "loanFullCode"] = value;
      row[code == "bor" ? "mnydebit" : "mnycredit"] = value;
      // 金额长度
      let len = value.length;
      // 处理基数
      let foo = 0;

      // 合并标识数组
      let conCatAry = bor.concat(loan);

      // 处理前初始化row
      for (let i = 0; i < conCatAry.length; i++) {
        row[conCatAry[i]] = null;
      }

      // 处理小数
      if (/\d+(\.\d{1,})/g.test(value)) {
        value = value.replace(".", "");
        len = value.length;
        foo = 10 - len;
      }
      // 处理整数
      else {
        foo = 8 - len;
      }

      // 单元格处理
      for (let i = len; i > 0; i--) {
        if (value[i - 1] != "-") {
          row[overAry[i + foo]] = value[i - 1];
        }
      }

      /**
       *
       * 处理合计
       *
       **/
      // 初始化合计行
      for (let i = 2; i < this.footerData[0].length; i++) {
        this.footerData[0][i] = null;
      }
      this.gridOptions.footerMethod = () => {
        return this.footerData;
      };

      let borFull = 0;
      let loanFull = 0;

      for (let i = 0; i < data.length; i++) {
        let rowData = data[i];
        if (!rowData.loanFullCode && !rowData.borFullCode) {
          continue;
        }
        if (!rowData.loanFullCode) {
          borFull += parseFloat(rowData.borFullCode);
        } else {
          loanFull += parseFloat(rowData.loanFullCode);
        }
      }

      let tempObj = {
        bor: borFull.toFixed(2),
        loan: loanFull.toFixed(2)
      };
      this.footerObj = tempObj;
      for (let key in tempObj) {
        let valueFoot = tempObj[key];
        // 金额长度
        let lenFoot = valueFoot.length;
        // 处理基数
        let fooFoot = 0;
        // // 处理小数
        if (/\d+(\.\d{1,})/g.test(valueFoot)) {
          valueFoot = valueFoot.replace(".", "");
          lenFoot = valueFoot.length;
          fooFoot = key == "bor" ? 12 - lenFoot : 23 - lenFoot;
        }
        // 处理整数
        else {
          fooFoot = key == "bor" ? 10 - lenFoot : 21 - lenFoot;
        }

        for (let i = lenFoot; i > 0; i--) {
          if (valueFoot[i - 1] != "-") {
            this.footerData[0][i + fooFoot] = valueFoot[i - 1];
          }
        }
      }

      this.gridOptions.footerMethod = () => {
        return this.footerData;
      };
    },
    closedDialog() {
      this.formState = "edit";
      this.footerObj = undefined;
      this.footerData = [
        [
          "分录合计",
          "",
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null
        ]
      ];
      this.gridOptions.data = [];
      this.$emit("closed");
    },
    //获取账期
    async getPeriodList() {
      await tglGlobalPeriods().then(res => {
        this.uqglobalperiodidOptions = res.data;
        if (this.formData.uqglobalperiodid==undefined){
          this.formData.uqglobalperiodid = this.uqglobalperiodidOptions[0];
          this.formData.uqglobalperiodidtext = this.uqglobalperiodidOptions[0].varname;

          // 限制 交易时间的最小时间与最大时间
          this.changeChargeagainstsubmitodd(this.uqglobalperiodidOptions[0]);
        }
      });
    },
    // 会计期联动
    periodChange(option) {
      this.formData.uqglobalperiodid = this.$loadsh.find(
        this.uqglobalperiodidOptions,
        { varname: option }
      );
      this.changeChargeagainstsubmitodd(this.formData.uqglobalperiodid);
    },
    // 会计期联动交易时间
    changeChargeagainstsubmitodd(dateTemp) {
      this.formData.chargeagainstsubmitodd = this.$dayjs(dateTemp.dtend).format(
        "YYYY-MM-DD hh:mm:ss"
      );
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
    //摘要弹窗
    addRemark() {
      this.$refs.addAbstractDialog.open();
    },
    onGetDataAbstract(data) {
      this.currentData.varabstract = data.varabastract;
    },
    // 科目
    focusHkont() {
      this.$refs.xDown.showPanel();
      this.accountVisible = true;
    },
    keyupHkont(value) {
      this.accountOptions = value
        ? this.accountOptions.filter(item => item.label.indexOf(value) > -1)
        : this.accountOptions;
    },
    changeAccountQuery(data) {
      // console.log(data);
    },
    accountQuery() {
      this.$refs.accountingAccountQuery.open();
    },
    onGetDataAccountQuery(data) {
      // 选择科目是 分户需要重置
      delete this.currentData.tglledgetype;
      delete this.currentData.foot;
      this.currentData[
        "hkont"
      ] = `[${data.tableData.varaccountcode}]${data.tableData.varaccountname}`;
      this.currentData["tglAccounts"] = data.tableData;
      this.btnCheck(this.currentData["tglAccounts"]);
    },
    // 表尾
    footerMethod() {
      // 接收二维数组
      return this.footerData;
    },

    // 数据加载
    loadData() {},
    beforePrintMethod({ content }) {
      return (
        `
        <h1 class="title">会计凭证</h1>
        <div class="my-top">
          <div class="my-list-row">
            <div class="my-list-col">上海新致软件股份有限公司</div>
            <div class="my-list-col">制证日期：${this.formData.dtfiller}</div>
            <div class="my-list-col">凭证编号：${this.formData.intvouchernum}</div>
            <div class="my-list-col">附件张数：${this.formData.intaffix}</div>
          </div>
        </div>
        ` +
        content +
        `
        <div class="foot-info" style="border:0px;margin-top: 5px;padding-top:5px">
          <div>
            大写金额：${!this.footerObj ? "/" : this.numToCny(this.footerObj.bor)}
          </div>
          <div>
            合计：${!this.footerObj ? "/" : this.formatAmount(this.footerObj.bor)}
            |
            ${!this.footerObj ? "/" : this.formatAmount(this.footerObj.loan)}
          </div>
        </div>
        <div class="foot-info">
          <div>
            记账：测试A
          </div>
          <div>
           出纳：测试A
          </div>
          <div>
           审核：测试A
          </div>
          <div>
           制单：财务人员
          </div>
        </div>
        `
      );
    },
    printEvent() {
      this.$refs.xGrid.print();
    },
    // 保存、修改、复制
    saveEvent() {
      const allObj = this.$refs.xGrid.getTableData();
      const fullData = allObj.fullData;
      // 基础校验
      if (fullData.length <= 0) {
        this.$message({
          message: "请填写数据",
          type: "warning"
        });
        return;
      }
      if (fullData.length < 2) {
        this.$message({
          message: "至少需要填写两条数据",
          type: "warning"
        });
        return;
      }
      if (!this.footerObj) {
        this.$message({
          message: "请填写借方金额或贷方金额！",
          type: "warning"
        });
        return;
      }
      if (this.footerObj.bor != this.footerObj.loan) {
        this.$message({
          message: "借、贷方合计金额需要保持一致",
          type: "warning"
        });
        return;
      }
      // 行数据校验
      for (let i = 0; i < fullData.length; i++) {
        if (!fullData[i].tglAccounts) {
          this.$message({
            message: `第${i + 1}行数据，请选择科目`,
            type: "warning"
          });
          return;
        }
        if (fullData[i].varabstract == null) {
          this.$message({
            message: `第${i + 1}行数据，请选择摘要`,
            type: "warning"
          });
          return;
        }
        if (
          fullData[i].tglAccounts.intisflow == 2 &&
          !fullData[i].tglAccountsXjll
        ) {
          this.$message({
            message: `第${i + 1}行数据，必须进行现金流量编制`,
            type: "warning"
          });
          return;
        }

        if (!fullData[i].foot && fullData[i].tglAccounts.intisledge != 0) {
          this.$confirm(`第${i + 1}行数据需要进行分户核算，请处理`, "确认", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          })
            .then(() => {
              this.currentData = fullData[i];
              this.tglLedge();
            })
            .catch(() => {});
          return;
        }
      }

      let params = {
        // 保存0  暂存1
        submitodd: 0,
        // 借方金额合计
        mnydebitsum: this.footerObj.bor,
        // 贷方金额合计
        mnycreditsum: this.footerObj.loan,
        // 会计期
        uqglobalperiodid: this.formData.uqglobalperiodid,
        // 凭证分录
        tglVoucherDetailsList: fullData,
        ...this.formData
      };
      // 复制时置空
      if (this.isCopy) {
        params["uqvoucherid"] = "";
      }
      tglVoucherMainsSave(params).then(res => {
        if (res.code == 100) {
          this.dialogVisible = false
          this.showMessage({
            type: 'success',
            msg: '保存成功'
          })
          this.$emit('save-finished')
        } else {
          this.showMessage(res)
        }
        this.resetLoad()
      });
    },
    // 分户
    async tglLedge() {
      // 改成在外调用
      if (!this.currentData.borFullCode && !this.currentData.loanFullCode) {
        this.$message({
          message: "请先填写借或贷方金额",
          type: "warning"
        });
        return;
      }

      if (!this.currentData.tglledgetype) {
        let { tglAccounts } = this.currentData;
        this.loadCode = "tglLedgeLoad";
        // 如果存在特定对象，就不再调用接口
        let req = await getByUqaccountId(tglAccounts.uqaccountid);
        this.currentData["tglledgetype"] = req.data;
      }
      this.loadCode = undefined;
      this.currentData["formState"] = this.formState;
      this.$refs.tglLedgetypeDialog.open(this.currentData);
    },
    onGetDataTglLedgetype(data) {
      this.currentData["foot"] = data["foot"];
      this.currentData["tglledgetype"] = data["tglledgetype"];
    },
    // 现金流量编制
    flowType() {
      this.$refs.flowTypeDialog.open();
    },
    onGetDataFlowTypeDialog(data) {
      // let currentData = this.$refs.xGrid.getCurrentRecord();
      this.currentData["tglAccountsXjll"] = data;
    },
    // 往来核销
    currentWriteOff(){

    },
    /// TODO: 暂存
    tempEvent() {},
    // 新增
    insertEvent(row) {
      let { fullData } = this.$refs.xGrid.getTableData();
      let bol = true;
      for (let i = 0; i < fullData.length; i++) {
        if (!fullData[i].tglAccounts) {
          this.$message({
            message: `第${i + 1}行数据，请选择科目`,
            type: "warning"
          });
          bol = false;
          return;
        }
        if (row.varabstract == null) {
          this.$message({
            message: `第${i + 1}行数据，请选择摘要`,
            type: "warning"
          });
          bol = false;
          return;
        }
      }
      if (bol) {
        this.$refs.xGrid.insert({});
        this.btnCheck();
      }
    },
    // 删除
    removeRowEvent() {
      let { fullData } = this.$refs.xGrid.getTableData();
      if (fullData.length <= 1) {
        this.$message({
          message: "最少保留一条数据，不可删除",
          type: "warning"
        });
        return;
      }
      this.$refs.xGrid.removeCurrentRow();
    },
    // 按钮校验
    btnCheck(tglAccounts = undefined) {
      if (!!tglAccounts) {
        this.isShowVarledge = tglAccounts.intisledge != 0;
        this.isShowFlowType = tglAccounts.intisflow != 0;
        this.isShowUqtype = ["1", "2", "7", "8"].includes(tglAccounts.uqtypeid);
      } else {
        this.isShowVarledge = false;
        this.isShowFlowType = false;
        this.isShowUqtype = false;
      }
    },
    formatAmount(cellValue) {
      return cellValue
        ? `￥${this.$XEUtils.commafy(this.$XEUtils.toNumber(cellValue), {
            digits: 2
          })}`
        : "";
    }
  }
};
</script>
<style lang="scss" scoped>
.foot-info {
  display: flex;
  font-weight: 600;
  text-align: center;
  padding: 20px 0 20px 0;
  border-top: 1px dashed #c6c6c6;
  align-items: center;
  justify-content: space-between;
}
</style>
