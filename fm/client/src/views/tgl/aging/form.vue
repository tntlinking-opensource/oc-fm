<template>
  <el-row class="dc-container">
    <aging-form
      ref="agingForm"
      v-on:save-finished="getAgingList"
      v-on:before-load="setLoad"
      v-on:after-load="resetLoad"
    ></aging-form>
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
      <el-form
        ref="editForm"
        :model="editFormData"
        label-width="100px"
        label-position="right"
        class="dc-el-form_ElEditForm"
      >
        <el-row gutter="0" type="flex" justify="end" align="top" class="dc-el-row_ElRow" style="margin-bottom: 10px">
          <el-button type="primary" v-on:click="onEditAging">账龄区间设置</el-button>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="12">
            <div class="left-box">
              <div class="top">
                <el-form-item
                  prop="uqaccountid"
                  :rules="[{ required: true, message: '请选择会计科目', trigger: 'change' }]"
                  label="会计科目"
                  class="dc-el-form-item_SelectInput">
                  <el-select
                    v-model="editFormData.uqaccountid"
                    :style="{ width: '70%' }"
                    placeholder="请选择会计科目"
                    clearable
                    filterable>
                    <el-option v-for="(item, index) in uqaccountidOptions"
                               :key="index"
                               :label="item.varaccountcode
                                  ? '[' + item.varaccountcode + ']' + item.varaccountname: item.varaccountname"
                               :value="item.uqaccountid"></el-option>
                  </el-select>
                  <el-button type="info" plain v-on:click="$refs.accountQuery.open()" icon="el-icon-search"></el-button>
                </el-form-item>
                <el-form-item
                  prop="ledgetypeName"
                  :rules="[{ required: true, message: '请选择分户', trigger: 'change' }]"
                  label="分户"
                  class="dc-el-form-item_SelectInput">
                  <el-input
                    v-model="editFormData.ledgetypeName"
                    placeholder="请选择分户"
                    :style="{ width: '70%' }"
                    disabled
                    class="dc-el-input_SingleInput">
                    slot="suffix"重点  btnClearable事件，value有值就显示没有值就隐藏
                    <span slot="suffix" v-show="editFormData.ledgetypeName" class="iconClose">
                   <i class="el-icon-close" style="margin-left: 5px;cursor: pointer;" @click="btnClearable"></i>
                </span>
                  </el-input>
                  <el-button type="info" plain v-on:click="onSearchLedgetype" icon="el-icon-search"></el-button>
                </el-form-item>
              </div>
              <el-card shadow="hover">
                <el-form-item prop="byDay" label-width="0px" class="dc-el-form-item_SelectInput">
                  <el-radio v-model="editFormData.byDay" label="0">制单日期分析</el-radio>
                  <el-radio v-model="editFormData.byDay" label="1">发生日期分析</el-radio>
                </el-form-item>
                <el-form-item
                  prop="maxDay"
                  :rules="[{ required: true, message: '请选择截止日期', trigger: 'change' }]"
                  label-width="80px"
                  label="截止日期"
                  class="dc-el-form-item_SelectInput">
                  <el-date-picker
                    :style="{ width: '80%' }"
                    v-model="editFormData.maxDay"
                    type="date"
                    format="yyyy-MM-dd"
                    value-format="yyyy-MM-dd"
                    placeholder="选择日期">
                  </el-date-picker>
                </el-form-item>
                <el-form-item prop="isVoucher" label-width="0px" class="dc-el-form-item_SelectInput">
                  <el-checkbox v-model="editFormData.isVoucher" true-label="0" false-label="1">包含未记账凭证</el-checkbox>
                </el-form-item>
              </el-card>
            </div>
          </el-col>
          <el-col :span="12">

            <ux-grid
              column-key
              ref="agingTable"
              :data="agingTableData"
              border
              class="dc-ux-grid_QueryTable">
              <ux-table-column type="index" header-align="center" align="center" width="50"/>
              <ux-table-column title="起止天数" min-width="100px" field="begday" header-align="center"></ux-table-column>
              <ux-table-column title="总天数" min-width="100px" field="aging" header-align="center" align="right"></ux-table-column>
            </ux-grid>
          </el-col>
          <el-col :span="12" style="margin-top: 10px;">
            <el-card shadow="hover" style="height: 120px">
              <el-form-item prop="byWay" label-width="0px" class="dc-el-form-item_SelectInput">
                <el-radio v-model="editFormData.byWay" label="0">余额发生法</el-radio>
                <el-radio v-model="editFormData.byWay" label="1">实际发生法</el-radio>
              </el-form-item>
              <el-form-item prop="nozore" label-width="0px" class="dc-el-form-item_SelectInput">
                <el-checkbox v-model="editFormData.nozore" true-label="0" false-label="1">无发生数不显示</el-checkbox>
              </el-form-item>
            </el-card>
          </el-col>
          <el-col :span="12" style="margin-top: 10px;">
            <el-card shadow="hover" style="height: 120px;align-items: center">
              <el-form-item prop="byDetail" label-width="0px" class="dc-el-form-item_SelectInput">
                <el-radio v-model="editFormData.byDetail" label="0">按所有往来明细分析</el-radio>
              </el-form-item>
            </el-card>
          </el-col>
        </el-row>
        <AccountQuery ref="accountQuery" :appendToBody="true" :isIntisledge="true" @onGetData="getCheckData"></AccountQuery>
        <LedgetypeQuery ref="typeQuery" :appendToBody="true"
                        :uqaccountid="this.editFormData.uqaccountid"
                        @onGetTreeData="getTreeCheckData"></LedgetypeQuery>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button v-on:click="onSubmit" type="primary">确定</el-button>
        <el-button v-on:click="onDialogClose">取消</el-button>
      </span>
    </el-dialog>
  </el-row>
</template>
<script>
import { validatenull } from '@/utils/validate'
/** 根据用户界面配置import组件 结束 */
import { listTglAccountsAll } from '@/api/vouchermanager/InitLedgePeriod/tglAccounts.js'
import AccountQuery from "@/views/components/AccountingAccountQuery"
import LedgetypeQuery from '@/views/components/LedgetypeQuery'
import AgingForm from './agingForm'
import moment from "moment";
/** 根据用户界面配置import组件 结束 */
import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'
import { listAgingAll } from "@/api/tgl/aging/aging";
export default {
  extends: BaseUI,
  name: 'contacts-book-form',
  components: {
    AccountQuery,
    LedgetypeQuery,
    OperationIcon,
    AgingForm
  },
  data() {
    return {
      /** 根据用户界面配置生成data数据 开始 */
      editFormData: this.initEditData(),
      // 对话框属性变量
      dialogProps: {
        visible: false,
        title: '往来账龄报表-查询'
      },
      agingTableData: [],
      // 会计科目选项
      uqaccountidOptions: [],
      /** 根据用户界面配置生成data数据 结束 */
    }
  },
  props: {
  },
  computed: {},
  methods: {
    onSubmit() {
      this.$refs['editForm'].validate((valid) => {
        if (valid) {
          if (validatenull(this.agingTableData)) {
            this.$alert('请配置账龄区间！', '提示', {
              confirmButtonText: '确定',
              type: 'info'
            })
            return false
          }
          this.$emit('save-finished', this.editFormData, this.agingTableData)
          this.dialogProps.visible = false
        } else {
          return false
        }
      })
    },
    onDialogClose() {
      this.$emit('close-finished', this.editFormData)
      this.dialogProps.visible = false
    },
    onDialogOpen() {
      this.$nextTick(() => {
        this.$refs['editForm'].clearValidate()
      })
    },
    listUqaccountidOptions() {
      // 获取科目管理列表
      let search_List = {
        params: [],
        columnName: 'varaccountfullcode',
        orderby: 'asc'
      };
      // filter条件
      search_List.params.push.apply(search_List.params, []);
      // 数据权限: 会计科目表tgl_accounts
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, "1457254743943430348");
      listTglAccountsAll(search_List).then(responseData => {
        if (responseData.code == 100) {
          this.uqaccountidOptions = responseData.data;
        } else {
          this.showMessage(responseData);
        }
      });
    },
    getAgingList() {
      let search_List = {
        params: []
      };
      // filter条件
      search_List.params.push.apply(search_List.params, []);
      listAgingAll(search_List).then(responseData => {
        if (responseData.code == 100) {
          this.agingTableData = responseData.data;
        } else {
          this.showMessage(responseData);
        }
      });
    },
    initOptions(This) {
      // 初始化自定义类型选择框选项变量
      this.listUqaccountidOptions()

      this.getAgingList()
    },
    onSearchLedgetype() {
      if (validatenull(this.editFormData.uqaccountid)) {
        this.$alert('请选中科目', '提示', {
          confirmButtonText: '确定',
          type: 'info'
        })
        return false
      }
      this.$refs.typeQuery.open(this.editFormData.ledgetype)
    },
    getCheckData(data) {
      if (!validatenull(data.tableData)) {
        this.editFormData.uqaccountid = data.tableData.uqaccountid;
      }
    },
    getTreeCheckData(data) {
      if (!validatenull(data)) {
        this.editFormData.ledgetype = data.arrData;
        this.editFormData.ledgetypeName = data.arrName.join(",")
      }
    },
    btnClearable() {
      this.editFormData.ledgetype = ''
      this.editFormData.ledgetypeName = ''
    },
    onEditAging() {
      this.$refs.agingForm.$emit('openEditAgingDialog', this.agingTableData)
    },
    initEditData(This) {
      return {
        uqaccountid: null,
        ledgetype: null,
        ledgetypeName: null,
        byDay: '0',
        maxDay: '',
        isVoucher: '0',
        byWay: '0',
        nozore: '0',
        byDetail: '0',
        period: null,
        companyId: currentUser.company.id
      }
    }
  },
  watch: {
    'editFormData.uqaccountid': {
      handler(newVal, oldVal) {
        if(newVal != oldVal) {
          this.editFormData.ledgetype = ''
          this.editFormData.ledgetypeName = ''
        }
      },
      immediate:false, // 初始化监听
    },
    'editFormData.maxDay': {
      handler(newVal, oldVal) {
        if(newVal != oldVal && !validatenull(newVal)) {
          let month = moment(new Date(newVal)).format('yyyyMM')
          this.editFormData.period = month
        }
      }
    }
  },
  mounted: function () {
    this.$nextTick(() => {
      this.$on('openQueryDialog', function (queryFormData) {
        this.editFormData = {...this.initEditData(), ...queryFormData}
        this.initOptions(this.editFormData)
        this.dialogProps.visible = true
      })
    })
  }
}
</script>
<style lang="scss" scoped>
.el-icon-close:before {
  content: "\E78D";
}
.left-box {
  height: 100%;
  display: flex;
  flex-direction: column;
}
.left-box .el-card {
  flex: 1;
  display: flex;
  align-items: center;
}
</style>
