<template>
  <el-row class="dc-container">
    <el-dialog
      v-on:open="onDialogOpen"
      v-on:close="onDialogClose"
      v-loading="loading"
      width="50%"
      :fullscreen="false"
      :title="dialogProps.title"
      :visible.sync="dialogProps.visible"
      :close-on-click-modal="false"
      class="dc-el-dialog_ElDialog"
    >
      <el-row>
        <el-form
          ref="editForm"
          :model="editFormData"
          label-width="100px"
          :disabled="action == 'view'"
          class="dc-el-form_ElEditForm"
        >
          <el-row>
            <el-col :span="12">
              <el-form-item
                prop="uqaccountid"
                label="会计科目"
                :rules="[
                  {
                    required: true,
                    message: '会计科目不能为空',
                    trigger: 'blur'
                  }
                ]"
                class="dc-el-form-item_SingleInput"
              >
                <el-input
                  v-model="editFormData.uqaccountidtext"
                  :maxLength="40"
                  placeholder="请选择会计科目"
                  clearable
                  class="dc-el-input_SingleInput"
                >
                  <el-button
                    slot="append"
                    type="primary"
                    icon="el-icon-search"
                    @click="onSearch"
                    >查询</el-button
                  >
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="mnydebitperiodall"
                label="借方金额"
                class="dc-el-form-item_NumberInput"
              >
                <number-input
                  v-model="editFormData.mnydebitperiodall"
                  currency="CNY"
                  :disabled="editFormData.mnycreditperiodall != 0 && editFormData.mnycreditperiodall != null"
                  :min="0"
                  :max="999999999999.99"
                  :precision="2"
                  placeholder="请输入借方金额"
                  clearable
                  class="dc-number-input_NumberInput"
                ></number-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="mnycreditperiodall"
                label="贷方金额"
                class="dc-el-form-item_NumberInput"
              >
                <number-input
                  v-model="editFormData.mnycreditperiodall"
                  currency="CNY"
                  :disabled="editFormData.mnydebitperiodall != 0 && editFormData.mnydebitperiodall != null"
                  :min="0"
                  :max="999999999999.99"
                  :precision="2"
                  placeholder="请输入贷方金额"
                  clearable
                  class="dc-number-input_NumberInput"
                ></number-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </el-row>
      <span slot="footer" class="dialog-footer">
        <el-button
          v-on:click="onSubmit"
          type="primary"
          v-show="action != 'view'"
          >保存</el-button
        >
        <el-button v-on:click="onDialogClose" v-if="action != 'view'"
          >取消</el-button
        >
        <el-button v-on:click="onDialogClose" v-if="action == 'view'"
          >关闭</el-button
        >
      </span>
    </el-dialog>
    <AccountQuery
      ref="accountQuery"
      :isLastLevel="true"
      @onGetData="getCheckData"
    ></AccountQuery>
  </el-row>
</template>
<script>
import { validatenull } from "@/utils/validate";
/** 根据用户界面配置import组件 结束 */

/** 根据用户界面配置import组件 结束 */
import {
  getTglPeriodAccountsById,
  saveTglPeriodAccounts
} from "@/api/vouchermanager/tglPeriodAccounts";
import BaseUI from "@/views/components/baseUI";
import OperationIcon from "@/components/OperationIcon";
import AccountQuery from "@/views/components/AccountingAccountQuery";

export default {
  extends: BaseUI,
  name: "tglPeriodAccounts-form",
  components: {
    /** 根据用户界面配置组件 开始 */

    /** 根据用户界面配置组件 结束 */
    AccountQuery,
    OperationIcon
  },
  data() {
    return {
      /** 根据用户界面配置生成data数据 开始 */
      editFormData: this.initEditData(),
      // 对话框属性变量
      dialogProps: {
        visible: false,
        title: "科目期初余额"
      },
      dialogTitle: "科目期初余额",
      // 选项变量

      /** 根据用户界面配置生成data数据 结束 */

      // 窗口操作类型 view/edit/add
      action: ""
    };
  },
  props: {
    // 权限
    permission: {
      type: Object
    }
  },
  computed: {},
  methods: {
    onSearch() {
      this.$refs.accountQuery.open();
    },
    // 获取科目的数据
    getCheckData(data) {
      console.log(data);
      this.editFormData.uqaccountid = data.tableData;
      this.editFormData.uqaccountidtext = `[${data.tableData.varaccountcode}]${data.tableData.varaccountname}`;
    },
    onSubmit() {
      this.$refs["editForm"].validate(valid => {
        if (valid) {
          this.doSave();
        } else {
          return false;
        }
      });
    },
    doSave() {
      this.setLoad();

      saveTglPeriodAccounts(this.editFormData)
        .then(responseData => {
          if (responseData.code == 100) {
            this.dialogProps.visible = false;
            this.showMessage({
              type: "success",
              msg: "保存成功"
            });
            this.$emit("save-finished");
          } else {
            this.showMessage(responseData);
          }
          this.resetLoad();
        })
        .catch(error => {
          this.outputError(error);
        });
    },
    switchEdit() {
      this.action = "edit";
      this.dialogProps.title = `修改${this.dialogTitle}`;
      this.initOptions(this.editFormData);
    },

    getFormById(id) {
      this.setLoad();
      return new Promise(resolve => {
        getTglPeriodAccountsById(id)
          .then(responseData => {
            let form = {};
            if (responseData.code == 100) {
              form = responseData.data;
              form[
                "uqaccountidtext"
              ] = `[${form.uqaccountid.varaccountcode}]${form.uqaccountid.varaccountname}`;
              form["mnydebitperiodall"] = !form["mnydebitperiodall"]
                ? 0
                : form["mnydebitperiodall"];
              form["mnycreditperiodall"] = !form["mnycreditperiodall"]
                ? 0
                : form["mnycreditperiodall"];
            } else {
              this.showMessage(responseData);
            }
            this.resetLoad();
            resolve(form);
          })
          .catch(error => {
            this.outputError(error);
          });
      });
    },
    onDialogClose() {
      this.dialogProps.visible = false;
    },
    onDialogOpen() {
      this.$nextTick(() => {
        this.$refs["editForm"].clearValidate();
      });
    },

    initOptions(This) {
      // 初始化自定义类型选择框选项变量
    },
    initEditData(This) {
      return {
        uqaccountid: "", // 会计科目
        uqaccountidtext: "", // 会计科目文本
        mnydebitperiodall: null, // 借方金额
        mnycreditperiodall: null // 贷方金额
      };
    }
  },
  watch: {},
  mounted: function() {
    this.$nextTick(() => {
      this.$on("openViewDialog", async function(id) {
        this.action = "view";
        this.dialogProps.title = `查看${this.dialogTitle}`;
        this.editFormData = {
          ...this.initEditData(),
          ...(await this.getFormById(id))
        };
        this.initOptions(this.editFormData);
        this.dialogProps.visible = true;
      });

      this.$on("openEditDialog", async function(id) {
        this.action = "edit";
        this.dialogProps.title = `修改${this.dialogTitle}`;
        this.editFormData = {
          ...this.initEditData(),
          ...(await this.getFormById(id))
        };
        this.initOptions(this.editFormData);
        this.dialogProps.visible = true;
      });
      this.$on("openAddDialog", function() {
        this.action = "add";
        this.dialogProps.title = `添加${this.dialogTitle}`;
        this.editFormData = this.initEditData();
        this.initOptions(this.editFormData);
        this.dialogProps.visible = true;
      });
      this.$on("openCopyDialog", async function(id) {
        this.action = "add";
        this.dialogProps.title = `添加${this.dialogTitle}`;
        this.editFormData = {
          ...this.initEditData(),
          ...(await this.getFormById(id))
        };
        this.initOptions(this.editFormData);
        this.editFormData.id = null; //把id设置为空，添加一个新的

        this.dialogProps.visible = true;
      });
    });
  }
};
</script>
