<template>
  <div>
    <el-dialog
      title="分户分摊"
      :visible.sync="dialogVisible"
      :close-on-click-modal="false"
      :show-close="false"
      top="0vh"
      append-to-body
      width="70%"
      :destroy-on-close="true"
      v-on:open="onDialogOpen"
    >
      <span slot="title">
        <div class="top-main">
          <div class="top-main-l">
            <div>
              <span class="font-w-s font-size-18">摘要：</span>
              <span class="font-size-16 txt-5a5">{{
                formData.varabstract == "" || formData.varabstract == null
                  ? "暂未选择"
                  : formData.varabstract
              }}</span>
            </div>
            <div class="flex">
              <div class="mr-20">
                <span class="font-w-s font-size-18">科目：</span>
                <span class="font-size-16 txt-5a5">{{ formData.hkont }}</span>
              </div>
              <div class="txt-018cb7">
                <span class="font-w-s font-size-18">{{ btnTxt }}</span>
              </div>
            </div>
          </div>
          <div class="top-main-r">
            <vxe-button
              status="info"
              icon="vxe-icon-close"
              type="text"
              @click="dialogVisible = false"
            ></vxe-button>
          </div>
        </div>
      </span>

      <vxe-grid
        ref="xGrid"
        v-bind="gridOptions"
        footerRowClassName="footer-class"
      >
        <template #top>
          <Tabs
            v-model="activeName"
            :tabs="tabPanes"
            :keyValue="{ key: 'uqparentid', value: 'varledgetypename' }"
            @onTabClick="handleClick"
          >
          </Tabs>
          <div class="mb-10">
            <vxe-input
              v-model="searchValue"
              placeholder="分户编码/名称"
              type="search"
              v-on:search-click="searchStatic"
            ></vxe-input>
            <vxe-button icon="vxe-icon-cloudy" @click="currentWriteOff"
              >往来核销</vxe-button
            >
          </div>
        </template>
        <template #mnyamount_edit="{ row }">
          <vxe-input v-model="row.mnyamount" @blur="blurAm(row)"></vxe-input>
        </template>
      </vxe-grid>
      <span slot="footer" class="dialog-footer">
        <el-button @click="close">关 闭</el-button>
        <el-button type="primary" @click="onGetData">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import DcMain from "@/views/components/dcMain";
import Tabs from "@/components/TabberCard";
import { validatenull } from "@/utils/validate";
import { staticSearch } from "@/utils/common";

export default {
  extends: DcMain,
  components: {
    Tabs
  },
  props: {
    currentRow: {
      type: Object,
      default: {}
    }
  },
  data() {
    return {
      activeName: "",
      activeObj: {},
      tabPanes: [],
      btnTxt: "借贷方向：借",
      dialogVisible: false,
      formData: {
        varabstract: "",
        hkont: "",
        isbalance: 0
      },
      searchValue: "",
      searchList: [],
      gridOptions: {
        loading: false,
        border: true,
        resizable: true,
        showOverflow: true,
        showFooter: true,
        height: 500,
        align: "left",
        editConfig: {
          trigger: "click",
          mode: "row",
          showStatus: true,
          autoClear: true,
          showIcon: false,
          // TODO: 控制查看不可编
          enabled: true
        },
        columns: [
          { type: "seq", width: 50, title: "序号", align: "center" },
          { field: "varledgecode", title: "分户编号", align: "center" },
          {
            field: "varledgefullname",
            title: "分户名称",
            showHeaderOverflow: true,
            align: "center"
          },
          {
            field: "mnyamount",
            title: "金额",
            showOverflow: true,
            formatter: this.formatAmount,
            align: "center",
            editRender: { autofocus: ".vxe-input--inner" },
            slots: { edit: "mnyamount_edit" }
          }
        ],
        data: [],
        footerMethod: this.footerMethod
      },
      footerData: [],
      // 从外传入的 数据对象
      dataObject: {}
    };
  },
  methods: {
    onDialogOpen() {},
    open(data) {
      this.dataObject = this.$loadsh.cloneDeep(data);
      this.gridOptions.editConfig.enabled = this.dataObject.formState == "edit";
      if (!this.dataObject.foot) {
        this.dataObject["foot"] = new Map();
      }
      let {
        // 科目
        tglAccounts,
        // 分户
        tglledgetype,
        // 摘要
        varabstract
      } = this.dataObject;

      this.dialogVisible = true;
      this.formData.hkont = `[${tglAccounts.varaccountcode}]${tglAccounts.varaccountname}`;
      this.formData.varabstract = varabstract;
      this.btnTxt = !this.dataObject.loanFullCode
        ? "借贷方向：借"
        : "借贷方向：贷";

      this.tabPanes = tglledgetype;
      this.activeName = this.tabPanes[0].uqparentid;
      this.activeObj = this.tabPanes[0];
      this.handleClick(this.tabPanes[0]);
    },
    handleClick(data) {
      this.activeObj = data;
      this.searchList = data.tglLedgerList;
      this.searchList.forEach(item => {
        item["mnyamount"] = item.tglVoucherDetailLedger.mnyamount;
      });
      this.gridOptions.data = this.searchList;

      this.footerData = !this.dataObject["foot"]
        ? []
        : !this.dataObject["foot"].get(this.activeName) //[this.activeName]
        ? []
        : this.dataObject["foot"].get(this.activeName); //[this.activeName];
    },
    // 静态搜索
    searchStatic() {
      let params = {};
      if (/[\u4e00-\u9fa5]+/g.test(this.searchValue)) {
        params = { varledgefullname: this.searchValue };
      } else {
        params = { varledgecode: this.searchValue };
      }
      this.gridOptions.data = staticSearch(this.searchList, params);
    },
    // 往来核销
    currentWriteOff() {},
    // 格式化金额
    formatAmount({ cellValue }) {
      return cellValue
        ? `￥${this.$XEUtils.commafy(this.$XEUtils.toNumber(cellValue), {
            digits: 2
          })}`
        : `￥0`;
    },
    blurAm(row) {
      // 借方金额mnydebit 贷方金额mnycredit , 需要跟着借贷方向变化而变化
      row["tglVoucherDetailLedger"] = !this.dataObject.loanFullCode
        ? { mnyamount: row.mnyamount, mnydebit: row.mnyamount }
        : { mnyamount: row.mnyamount, mnycredit: row.mnyamount };

      const { fullData } = this.$refs.xGrid.getTableData();
      let count = 0;
      fullData.forEach(item => {
        count += Number(
          item["mnyamount"] == "" || item["mnyamount"] == null
            ? 0
            : item["mnyamount"]
        );
      });

      // 根据不同页签切换不同表尾数据
      this.footerData = [
        [
          "合计",
          "",
          "",
          `￥${this.$XEUtils.commafy(this.$XEUtils.toNumber(count), {
            digits: 2
          })}`
        ]
      ];
      // foot 改成map存储,
      this.dataObject["foot"].set(this.activeName, this.footerData);
    },
    footerMethod() {
      this.$refs.xGrid.setMergeFooterItems([
        { row: 0, col: 0, rowspan: 0, colspan: 3 }
      ]);
      return this.footerData;
    },
    close() {
      this.dialogVisible = false;
    },
    onGetData() {
      let checkNum =
        this.dataObject.loanFullCode || this.dataObject.borFullCode;
      let { foot } = this.dataObject;
      if (foot.size < this.tabPanes.length) {
        this.$message({
          message: "请填写各个分户金额,并于外页保持一致",
          type: "warning"
        });
        return;
      }
      for (let value of foot) {
        let ftNum = value[1][0][3];
        ftNum = ftNum.indexOf(",") > -1 ? ftNum.replacAllS(",", "") : ftNum;
        if (`￥${checkNum}` != ftNum) {
          this.$message({
            message: `分户：${
              this.$loadsh.find(this.tabPanes, { uqparentid: value[0] })
                .varledgetypename
            }合计金额应该为￥${checkNum}，请仔细核对`,
            type: "warning"
          });
          return;
        }
      }

      this.dialogVisible = false;
      this.$emit("onGetData", this.dataObject);
    }
  }
};
</script>
<style lang="scss" scoped>
.top-main {
  display: flex;
  justify-content: space-between;
  box-shadow: 0 0 14px 4px #8c8c8c24;
  padding: 12px 16px;
  line-height: 30px;
  border-radius: 6px;
  &-l {
    width: 100%;
  }
}
</style>
