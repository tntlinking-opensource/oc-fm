<template>
  <el-row class="dc-container" v-loading="loading">
    <el-row :gutter="15">
      <el-form ref="elForm" :model="formData" label-width="100px">
        <el-row class="flex-wrap">
          <el-col :span="6">
            <el-form-item label="科目" prop="account">
              <el-select
                style="width: 80%"
                value-key="uqaccountid"
                placeholder="选择科目"
                v-model="formData.account"
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
              <el-button
                type="info"
                plain
                v-on:click="$refs.accountQuery.open()"
                icon="el-icon-search"
              ></el-button>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="分户" prop="ledgerTxt">
              <el-input
                v-model="formData.ledgerTxt"
                clearable
                placeholder="选择分户"
                :style="{ width: '100%' }"
                readonly
                @focus="$refs.ledgetypeQuery.open()"
              >
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="记账日期" prop="chargeagainstsubmitodd">
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
            <el-form-item label="制证人" prop="account_user">
              <el-input
                v-model="formData.account_user"
                placeholder="输入制证人"
                clearable
                :style="{ width: '100%' }"
              >
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="状态" prop="intstatus">
              <el-select style="width: 100%" v-model="formData.intstatus">
                <el-option
                  v-for="item in intstatusOptions"
                  :key="item.value"
                  :label="item.name"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="金额从" prop="money1">
              <el-input
                v-model="formData.money_form"
                clearable
                :style="{ width: '100%' }"
              >
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="3">
            <el-form-item label="至" prop="money2" label-width="35px">
              <el-input
                v-model="formData.money_to"
                clearable
                :style="{ width: '100%' }"
              >
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="2">
            <div class="ml-20">
              <el-button
                type="primary"
                plain
                v-on:click="searchData"
                icon="el-icon-search"
                >查询</el-button
              >
            </div>
          </el-col>
        </el-row>
      </el-form>
    </el-row>
    <Comp
      ref="writeOffComp"
      :params="search"
      type="writeOff"
      @onBtnClick="onWriteOffClick"
    />
    <Comp
      ref="payAccountComp"
      :params="search"
      type="payAccount"
      @onBtnClick="onPayAccountClick"
    />

    <el-dialog
      v-if="show"
      :visible.sync="dialogVisible"
      width="90%"
      @closed="show = false"
    >
      <el-row :gutter="15">
        <el-form ref="elFormDialog" :model="formDataDialog" label-width="100px">
          <el-row class="flex-wrap">
            <el-col :span="6">
              <el-form-item label="科目" prop="account">
                <el-select
                  style="width: 100%"
                  value-key="uqaccountid"
                  placeholder="选择科目"
                  v-model="formDataDialog.account"
                  disabled
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
            <el-col :span="6">
              <el-form-item label="分户" prop="ledgerTxt">
                <el-input
                  v-model="formDataDialog.ledgerTxt"
                  clearable
                  placeholder="选择分户"
                  :style="{ width: '100%' }"
                  disabled
                >
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="未冲金额" prop="nopay">
                <el-input
                  v-model="formDataDialog.nopay"
                  placeholder="输入制证人"
                  clearable
                  disabled
                  :style="{ width: '100%' }"
                >
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="记账日期" prop="chargeagainstsubmitodd">
                <el-date-picker
                  type="daterange"
                  v-model="formDataDialog.chargeagainstsubmitodd"
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd"
                  :style="{ width: '100%' }"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                  range-separator="至"
                ></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="3">
              <el-form-item label="金额从" prop="money1">
                <el-input
                  v-model="formDataDialog.money1"
                  clearable
                  :style="{ width: '100%' }"
                >
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="3">
              <el-form-item label="至" prop="money2" label-width="35px">
                <el-input
                  v-model="formDataDialog.money2"
                  clearable
                  :style="{ width: '100%' }"
                >
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="摘要" prop="varabstract">
                <el-input
                  v-model="formDataDialog.varabstract"
                  placeholder="输入摘要"
                  clearable
                  disabled
                  :style="{ width: '100%' }"
                >
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </el-row>
      <Comp
        ref="diaComp"
        :type="dialogType"
        :params="searchDia"
        :isInner="true"
      />
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="matchConf">确 定</el-button>
      </span>
    </el-dialog>
    <AccountQuery ref="accountQuery" @onGetData="getCheckData" />
    <LedgetypeQuery ref="ledgetypeQuery" @onGetTreeData="onGetTreeData" />
  </el-row>
</template>

<script>
import { listTglAccountsAll } from "@/api/vouchermanager/InitLedgePeriod/tglAccounts.js";
import { listDictItemAll } from "@/api/sys/dictItem.js";

import Comp from "./components/index.vue";
import AccountQuery from "@/views/components/AccountingAccountQuery";
import LedgetypeQuery from "@/views/components/LedgetypeQuery";

import { validatenull } from "@/utils/validate";
import { findMost } from "@/utils/common";
import DcMain from "@/views/components/dcMain";
import {
  doOffset,
  revertRush,
  getOffsetData,
  getOnAccountData,
  doManualRush
} from "@/api/vouchermanager/tglAcOffsetMain";
export default {
  extends: DcMain,
  components: {
    Comp,
    AccountQuery,
    LedgetypeQuery
  },
  data() {
    return {
      loading: false,
      show: false,
      dialogVisible: false,
      dialogType: "",
      search: {
        // 查询条件   业务表设置的筛选条件
        params: [],
        columnName: "", // 排序字段名
        order: "" // 排序
      },
      searchDia: {
        // 查询条件   业务表设置的筛选条件
        params: [],
        columnName: "", // 排序字段名
        order: "" // 排序
      },
      formData: {
        // 获取的科目id
        account: undefined,
        ledgerTxt: undefined,
        // 分户类别id
        ledgertypeid: undefined,
        // 分户项目id
        ledger: undefined,
        // 记账时间
        chargeagainstsubmitodd: undefined,
        account_user: undefined,
        intstatus: "0",
        money_form: undefined,
        money_to: undefined,
        iswindow: 0
      },
      formDataDialog: {
        // 获取的科目id
        account: undefined,
        ledgerTxt: undefined,
        // 分户类别id
        uqledgeid: undefined,
        // 分户项目id
        ledger: undefined,
        // 记账时间
        chargeagainstsubmitodd: undefined,
        // 未冲金额
        nopay: undefined,
        // 摘要
        varabstract: undefined,
        account_user: undefined,
        intstatus: "0",
        money1: undefined,
        money2: undefined,
        iswindow: 0
      },
      accountOptions: [], // 科目列表
      intstatusOptions: [] // 状态
    };
  },
  created() {},
  mounted() {
    listTglAccountsAll({
      params: [],
      columnName: "varaccountfullcode",
      orderby: "asc"
    }).then(responseData => {
      if (responseData.code == 100) {
        this.accountOptions = responseData.data;
      } else {
        this.showMessage(responseData);
      }
    });
    // 计量单位
    listDictItemAll({
      params: [
        {
          columnName: "dict_type_id",
          queryType: "=",
          value: "1554753336243265549"
        }
      ]
    }).then(res => {
      this.intstatusOptions = res.data;
    });
    this.searchData();
  },
  methods: {
    // 查询按钮
    searchData() {
      this.$refs.writeOffComp.restPage();
      this.$refs.payAccountComp.restPage();
      this.search.params = [];
      for (let key in this.formData) {
        if (!validatenull(this.formData[key])) {
          if (key == "chargeagainstsubmitodd") {
            this.search.params.push({
              columnName: "dtfilldatefrom",
              logic: "AND",
              queryType: "=",
              value: this.formData.chargeagainstsubmitodd[0]
            });
            this.search.params.push({
              columnName: "dtfilldateto",
              logic: "AND",
              queryType: "=",
              value: this.formData.chargeagainstsubmitodd[1]
            });
          } else {
            this.search.params.push({
              columnName: key,
              logic: "AND",
              queryType: "=",
              value: this.formData[key]
            });
          }
        }
      }
      // 查询冲销记录
      this.$refs.writeOffComp.getData();
      // 查询挂账记录
      this.$refs.payAccountComp.getData();
    },
    // 选择科目
    getCheckData(data) {
      if (!validatenull(data.tableData)) {
        this.formData.account = data.tree.uqaccountid;
      }
    },
    // 选择分户
    onGetTreeData(data) {
      this.formData.ledgertypeid = data.parentIds.toString();
      this.formData.ledger = data.arrData.toString();
      this.formData.ledgerTxt = data.arrName.toString();
    },
    // 人工匹配
    matchConf() {
      let { res, data } = this.$refs.diaComp.calculation();
      debugger
      let neNumber = Number(this.formDataDialog.nopay) - res;
      if (neNumber < 0) {
        this.showMessage({
          type: "warning",
          msg: `本次冲销金额合计不得大于未冲金额，您已超出￥${neNumber}`
        });
        return;
      }
      // 人工匹配接口调用,data为填过的数据
      let mainData;
      if (this.dialogType != 'writeOff'){
        mainData=this.$refs.writeOffComp.getTableData()[0];
      }else {
        mainData=this.$refs.payAccountComp.getTableData()[0];
      }
      mainData.money = res;
      doManualRush({
        mainData: mainData,
        detaildatas: data
      }).then(responseData => {
        if (responseData.code == 100) {
          this.loading = false;
          this.show = false;
          this.$message({
            message: "冲销成功",
            type: "success"
          });
        } else {
          this.showMessage(responseData);
        }
      });
    },
    // 冲销记录 弹窗
    onWriteOffClick(code) {
      // 冲销
      if (code == "CX") {
        // 获取冲销记录选中的数据
        let allOffsetRowData = this.$refs.writeOffComp.getTableData();
        // 获取挂账选中的数据
        let allOnAccountRowData = this.$refs.payAccountComp.getTableData();

        if (allOffsetRowData.length <= 0) {
          this.showMessage({
            type: "warning",
            msg: "请勾选冲销记录！"
          });
          return;
        }
        if (allOnAccountRowData.length <= 0) {
          this.showMessage({
            type: "warning",
            msg: "请勾选挂账记录！"
          });
          return;
        }
        console.log(allOffsetRowData);
        console.log(allOnAccountRowData);
        let checkUqaccountid = [];
        let checkUqledgeid = [];
        // 上下数据判断以冲销记录勾选数据为准
        allOffsetRowData.forEach(item => {
          checkUqaccountid.push(item.uqaccountid);
          checkUqledgeid.push(item.uqledgeid);
        });
        allOnAccountRowData.forEach(item => {
          checkUqaccountid.push(item.uqaccountid);
          checkUqledgeid.push(item.uqledgeid);
        });
        // 多数科目
        let maxUqa = findMost(checkUqaccountid);
        // 多数分户
        let maxUql = findMost(checkUqledgeid);
        // 校验冲销记录
        for (let i = 0; i < allOffsetRowData.length; i++) {
          if (allOffsetRowData[i].remainmoney == 0) {
            this.$alert(
              `请检查选中数据的第${i + 1}行，未冲金额不能为0`,
              `冲销记录警告`,
              {
                confirmButtonText: "了解",
                type: "warning"
              }
            );
            return;
          }
          // 采取少数服从多数的勾选判断
          if (allOffsetRowData[i].uqaccountid != maxUqa) {
            this.$alert(
              `请检查选中数据的第${i + 1}行，科目是否与其他的不一致`,
              `冲销记录警告`,
              {
                confirmButtonText: "了解",
                type: "warning"
              }
            );
            return;
          }
          if (allOffsetRowData[i].uqledgeid != maxUql) {
            this.$alert(
              `请检查选中数据的第${i + 1}行，分户是否与其他的不一致`,
              `冲销记录警告`,
              {
                confirmButtonText: "了解",
                type: "warning"
              }
            );
            return;
          }
        }
        // 校验挂账记录
        for (let i = 0; i < allOnAccountRowData.length; i++) {
          if (allOnAccountRowData[i].remainmoney == 0) {
            this.$alert(
              `请检查选中数据的第${i + 1}行，挂账余额不能为0`,
              `挂账记录警告`,
              {
                confirmButtonText: "了解",
                type: "warning"
              }
            );
            return;
          }
          if (allOnAccountRowData[i].uqaccountid != maxUqa) {
            this.$alert(
              `请检查选中数据的第${i + 1}行，科目与冲销记录科目选择不一致`,
              `挂账记录警告`,
              {
                confirmButtonText: "了解",
                type: "warning"
              }
            );
            return;
          }
          if (allOnAccountRowData[i].uqledgeid != maxUql) {
            this.$alert(
              `请检查选中数据的第${i + 1}行，分户与冲销记录分户选择不一致`,
              `挂账记录警告`,
              {
                confirmButtonText: "了解",
                type: "warning"
              }
            );
            return;
          }
        }
        this.loading = true;
        doOffset({
          offsetDatas: allOffsetRowData,
          onaccountDatas: allOnAccountRowData
        }).then(responseData => {
          if (responseData.code == 100) {
            this.loading = false;
            this.$message({
              message: "冲销成功",
              type: "success"
            });
            // 查询冲销记录
            this.$refs.writeOffComp.getData();
            // 查询挂账记录
            this.$refs.payAccountComp.getData();
          } else {
            this.showMessage(responseData);
          }
        });
      }
      // 人工匹配
      else if (code == "RGPP") {
        // 冲销记录的人工匹配，展示挂账记录的表
        this.show = true;
        this.$nextTick(_ => {
          this.dialogVisible = true;
          this.dialogType = "payAccount";
          let allOffsetRowData = this.$refs.writeOffComp.getTableData()[0];
          this.formDataDialog.account = allOffsetRowData.uqaccountid;
          this.formDataDialog.ledgerTxt = allOffsetRowData.accountledger;
          this.formDataDialog.ledger = allOffsetRowData.uqledgeid;
          this.formDataDialog.nopay = allOffsetRowData.remainmoney;
          this.getDiaForm();
        });
      }
      // 还原冲销
      else if (code == "HYCX") {
        let allOffsetRowData = this.$refs.writeOffComp.getTableData();
        for (let i = 0; i < allOffsetRowData.length; i++) {
          if (allOffsetRowData[0].yetmoney <= 0) {
            this.$alert(
              `请检查选中数据的第${i + 1}行，已冲金额不能为0`,
              `冲销记录警告`,
              {
                confirmButtonText: "了解",
                type: "warning"
              }
            );
            return;
          }
        }

        revertRush({
          revertRecords: allOffsetRowData
        }).then(responseData => {
          if (responseData.code == 100) {
            this.loading = false;
            this.$message({
              message: "还原冲销完成！",
              type: "success"
            });
            // 查询冲销记录
            this.$refs.writeOffComp.getData();
            // 查询挂账记录
            this.$refs.payAccountComp.getData();
          } else {
            this.showMessage(responseData);
          }
        });
      }
    },
    // 挂账记录 弹窗
    onPayAccountClick(code) {
      // 人工匹配
      if (code == "RGPP") {
        this.show = true;
        this.$nextTick(_ => {
          this.dialogVisible = true;
          this.dialogType = "writeOff";
          let allOnAccountRowData = this.$refs.payAccountComp.getTableData()[0];
          this.formDataDialog.account = allOnAccountRowData.uqaccountid;
          this.formDataDialog.ledgerTxt = allOnAccountRowData.accountledger;
          this.formDataDialog.ledger = allOnAccountRowData.uqledgeid;
          this.formDataDialog.nopay = allOnAccountRowData.remainmoney;
          this.getDiaForm();
        });
      }
      // 还原冲销
      else if (code == "HYCX") {
        let allOnAccountRowData = this.$refs.payAccountComp.getTableData();
        for (let i = 0; i < allOffsetRowData.length; i++) {
          if (allOffsetRowData[0].yetmoney <= 0) {
            this.$alert(
              `请检查选中数据的第${i + 1}行，已冲金额不能为0`,
              `挂账记录警告`,
              {
                confirmButtonText: "了解",
                type: "warning"
              }
            );
            return;
          }
        }
        revertRush({
          revertRecords: allOnAccountRowData
        }).then(responseData => {
          if (responseData.code == 100) {
            this.showMessage("还原冲销完成！");
          } else {
            this.showMessage(responseData);
          }
        });
      }
    },
    // 获取的人工匹配表单
    getDiaForm() {
      this.searchDia.params = [];
      for (let key in this.formDataDialog) {
        if (!validatenull(this.formDataDialog[key])) {
          if (key == "chargeagainstsubmitodd") {
            this.searchDia.params.push({
              columnName: "dtfilldatefrom",
              logic: "AND",
              queryType: "=",
              value: this.formDataDialog.chargeagainstsubmitodd[0]
            });
            this.searchDia.params.push({
              columnName: "dtfilldateto",
              logic: "AND",
              queryType: "=",
              value: this.formDataDialog.chargeagainstsubmitodd[1]
            });
          } else {
            this.searchDia.params.push({
              columnName: key,
              logic: "AND",
              queryType: "=",
              value: this.formDataDialog[key]
            });
          }
        }
      }

      setTimeout(_ => {
        this.$refs.diaComp.restPage();
        this.$refs.diaComp.getData();
      }, 200);
    }
  }
};
</script>
