<template>
  <el-row class="dc-container">
    <el-dialog
      v-on:open="onDialogOpen"
      v-on:close="onDialogClose"
      v-loading="loading"
      width="70%"
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
                prop="varaccountcode"
                label="科目编码"
                :rules="[
                  {
                    required: true,
                    message: '科目编码不能为空',
                    trigger: 'blur'
                  }
                ]"
                class="dc-el-form-item_SingleInput"
              >
                <el-input
                  v-model="editFormData.varaccountcode"
                  :maxLength="100"
                  placeholder="请输入科目编码"
                  disabled
                  clearable
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>

            <el-col :span="12">
              <el-form-item
                prop="varaccountname"
                label="科目名称"
                :rules="[
                  {
                    required: true,
                    message: '科目名称不能为空',
                    trigger: 'blur'
                  }
                ]"
                class="dc-el-form-item_SingleInput"
              >
                <el-input
                  v-model="editFormData.varaccountname"
                  :maxLength="100"
                  placeholder="请输入科目名称"
                  clearable
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="intproperty"
                label="科目属性"
                :rules="[
                  {
                    required: true,
                    message: '科目属性不能为空',
                    trigger: 'change'
                  }
                ]"
                class="dc-el-form-item_SelectInput"
              >
                <el-select
                  v-model="editFormData.intproperty"
                  :style="{ width: '100%' }"
                  placeholder="请选择科目属性"
                  clearable
                  filterable
                  class="dc-el-select_SelectInput"
                >
                  <el-option
                    v-for="(item, index) in intpropertyOptions"
                    :key="index"
                    :label="item.name"
                    :value="item.value"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="uqtypeid"
                label="科目类别"
                class="dc-el-form-item_SelectInput"
              >
                <el-select
                  v-model="editFormData.uqtypeid"
                  :style="{ width: '100%' }"
                  placeholder="请选择科目类别"
                  clearable
                  filterable
                  class="dc-el-select_SelectInput"
                >
                  <el-option
                    v-for="(item, index) in uqtypeidOptions"
                    :key="index"
                    :label="item.name"
                    :value="item.value"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="intisflow"
                label="现金流量"
                :rules="[
                  {
                    required: true,
                    message: '现金流量不能为空',
                    trigger: 'change'
                  }
                ]"
                class="dc-el-form-item_SelectInput"
              >
                <el-select
                  v-model="editFormData.intisflow"
                  :style="{ width: '100%' }"
                  placeholder="请选择现金流量"
                  clearable
                  filterable
                  class="dc-el-select_SelectInput"
                >
                  <el-option
                    v-for="(item, index) in intisflowOptions"
                    :key="index"
                    :label="item.name"
                    :value="item.value"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="uqforeigncurrid"
                label="外币"
                class="dc-el-form-item_SelectInput"
              >
                <el-select
                  v-model="editFormData.uqforeigncurrid"
                  :style="{ width: '100%' }"
                  placeholder="请选择外币"
                  clearable
                  filterable
                  class="dc-el-select_SelectInput"
                >
                  <el-option
                    v-for="(item, index) in uqforeigncurridOptions"
                    :key="index"
                    :label="item.name"
                    :value="item.value"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="varmeasure"
                label="计量单位"
                class="dc-el-form-item_SelectInput"
              >
                <el-select
                  v-model="editFormData.varmeasure"
                  :style="{ width: '100%' }"
                  placeholder="请选择计量单位"
                  clearable
                  filterable
                  class="dc-el-select_SelectInput"
                >
                  <el-option
                    v-for="(item, index) in varmeasureOptions"
                    :key="index"
                    :label="item.name"
                    :value="item.value"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="uqledgetypenames"
                label="分户"
                class="dc-el-form-item_SingleInput"
              >
                <el-input
                  v-model="editFormData.uqledgetypenames"
                  :maxLength="40"
                  placeholder="请选择分户"
                  disabled
                  class="dc-el-input_SingleInput"
                >
                  <el-button
                    slot="append"
                    type="primary"
                    icon="el-icon-search"
                    @click="onSearchUqledgetyp"
                    >查询</el-button
                  >
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="uqparenttext"
                label="上级科目"
                :rules="[
                  {
                    required: true,
                    message: '上级科目不能为空',
                    trigger: 'blur'
                  }
                ]"
                class="dc-el-form-item_SingleInput"
              >
                <el-input
                  v-model="editFormData.uqparenttext"
                  :maxLength="40"
                  placeholder="请选择上级科目"
                  disabled
                  class="dc-el-input_SingleInput"
                >
<!--                  <el-button-->
<!--                    slot="append"-->
<!--                    type="primary"-->
<!--                    icon="el-icon-search"-->
<!--                    @click="onSearch"-->
<!--                    >查询</el-button-->
<!--                  >-->
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="isbalance"
                label="余额方向"
                class="dc-el-form-item_SelectInput"
              >
                <el-select
                  v-model="editFormData.isbalance"
                  :style="{ width: '100%' }"
                  placeholder="请选择余额方向"
                  clearable
                  filterable
                  class="dc-el-select_SelectInput"
                >
                  <el-option
                    v-for="(item, index) in isbalanceOptions"
                    :key="index"
                    :label="item.name"
                    :value="item.value"
                  ></el-option>
                </el-select>
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
      showTree="parent"
      @onGetData="getCheckData"
    ></AccountQuery>
    <uqledgetyp ref="uqledgetyp" @onGetData="getUqledgetypData" />
  </el-row>
</template>
<script>
import { validatenull } from "@/utils/validate";
// import { editTmpIdlist, saveTmpIdlist } from "@/api/tgl/tmp/tmpIdlist";
import { listDictItemAll } from "@/api/sys/dictItem.js";
import AccountQuery from "@/views/components/AccountingAccountQuery";
import uqledgetyp from "./uqledgetyp";
// src\api\vouchermanager\InitLedgePeriod\tglAccounts.js
import {
  getTglAccountsById,
  saveTglAccounts
} from "@/api/vouchermanager/InitLedgePeriod/tglAccounts";
import BaseUI from "@/views/components/baseUI";
import OperationIcon from "@/components/OperationIcon";
export default {
  extends: BaseUI,
  name: "tglAccounts-form",
  components: {
    OperationIcon,
    AccountQuery,
    uqledgetyp
  },
  data() {
    return {
      /** 根据用户界面配置生成data数据 开始 */
      editFormData: this.initEditData(),
      // 对话框属性变量
      dialogProps: {
        visible: false,
        title: "会计科目管理"
      },
      dialogTitle: "会计科目管理",
      // 选项变量
      // 科目属性选项
      intpropertyOptions: [],
      // 科目类别选项
      uqtypeidOptions: [],
      // 现金流量选项
      intisflowOptions: [],
      // 外币选项
      uqforeigncurridOptions: [],
      // 余额方向选项
      isbalanceOptions: [
        {
          name: "借",
          value: "0"
        },
        {
          name: "贷",
          value: "1"
        }
      ],
      // 计量单位选项
      varmeasureOptions: [],
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
    onSearchUqledgetyp() {
      this.$refs.uqledgetyp.open();
    },
    // 获取科目的数据
    getCheckData(data) {
      debugger
      console.log(data);
      this.editFormData.uqparentid = data.tree;
      this.editFormData.uqparenttext = data.tree.name;
    },
    // 获取分户的数据
    getUqledgetypData(data) {
      let ids = [];
      let names = [];
      if (data.length > 0) {
        data.forEach(item => {
          ids.push(item.uqledgetypeid);
          names.push(item.varledgetypename);
        });
        this.editFormData.uqledgetypeids = ids.toString();
        this.editFormData.uqledgetypenames = names.toString();
        this.$forceUpdate()
      }else {
        this.editFormData.uqledgetypeids = "";
        this.editFormData.uqledgetypenames = "";
      }
      this.$refs.uqledgetyp.close();
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
      saveTglAccounts(this.editFormData)
        .then(responseData => {
          if (responseData.code == 100) {
            this.dialogProps.visible = false;
            this.showMessage({
              type: "success",
              msg: "修改成功"
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
        varaccountcode: "", // 科目编码
        varaccountname: "", // 科目名称
        intproperty: null, // 科目属性
        uqtypeid: null, // 科目类别
        intisflow: null, // 现金流量
        uqforeigncurrid: null, // 外币
        isbalance: null, // 余额方向
        varmeasure: null, // 计量单位
        uqparentid: "" // 上级科目
      };
    }
  },
  watch: {},
  mounted: function() {
    this.$nextTick(() => {
      // 计量单位
      listDictItemAll({
        params: [
          {
            columnName: "dict_type_id",
            queryType: "=",
            value: "1474912007333019936"
          }
        ]
      }).then(res => {
        this.varmeasureOptions = res.data;
      });
      // 外币
      listDictItemAll({
        params: [
          {
            columnName: "dict_type_id",
            queryType: "=",
            value: "1474912007333019929"
          }
        ]
      }).then(res => {
        this.uqforeigncurridOptions = res.data;
      });
      // 现金流量
      listDictItemAll({
        params: [
          {
            columnName: "dict_type_id",
            queryType: "=",
            value: "1474912007333019924"
          }
        ]
      }).then(res => {
        this.intisflowOptions = res.data;
      });
      // 科目类别
      listDictItemAll({
        params: [
          {
            columnName: "dict_type_id",
            queryType: "=",
            value: "1474912007333019912"
          }
        ]
      }).then(res => {
        this.uqtypeidOptions = res.data;
      });
      // 科目性质
      listDictItemAll({
        params: [
          {
            columnName: "dict_type_id",
            queryType: "=",
            value: "1474912007333019905"
          }
        ]
      }).then(res => {
        this.intpropertyOptions = res.data;
      });

      this.$on("openEditDialog", async function(parent) {
        console.log(parent);
        parent["intproperty"] =
          parent["intproperty"] == 0 ? "" : parent["intproperty"];
        parent["uqtypeid"] = parent["uqtypeid"] == 0 ? "" : parent["uqtypeid"];
        parent["intisflow"] =
          parent["intisflow"] == 0 ? "" : parent["intisflow"];
        parent["uqforeigncurrid"] =
          parent["uqforeigncurrid"] == 0 ? "" : parent["uqforeigncurrid"];
        parent["varmeasure"] =
          parent["varmeasure"] == 0 ? "" : parent["varmeasure"];
        parent[
          "uqparenttext"
        ] = `[${parent.uqparentid.varaccountcode}]${parent.uqparentid.varaccountname}`;
        this.action = "edit";
        this.dialogProps.title = `修改${this.dialogTitle}`;
        this.editFormData = {
          ...this.initEditData(),
          ...parent
        };
        this.initOptions(this.editFormData);
        this.dialogProps.visible = true;
      });
    });
  }
};
</script>
