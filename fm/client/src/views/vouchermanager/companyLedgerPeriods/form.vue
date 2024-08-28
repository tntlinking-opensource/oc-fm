<template>
  <el-row class="dc-container">
    <el-dialog
      v-on:open="onDialogOpen"
      v-on:close="onDialogClose"
      v-loading="loading"
      width="30%"
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
            <el-form-item prop="mnydebitperiodall" label="年初借方金额" class="dc-el-form-item_NumberInput">
              <number-input
                :disabled="editFormData.mnycreditperiodall > 0"
                v-model="editFormData.mnydebitperiodall"
                :min="0"
                :max="999999999999.99"
                :precision="2"
                placeholder="请输入全部本期借方金额"
                clearable
                class="dc-number-input_NumberInput"
              ></number-input>
            </el-form-item>
            <el-form-item prop="mnycreditperiodall" label="年初贷方金额" class="dc-el-form-item_NumberInput">
              <number-input
                :disabled="editFormData.mnydebitperiodall > 0"
                v-model="editFormData.mnycreditperiodall"
                :min="0"
                :max="999999999999.99"
                :precision="2"
                placeholder="请输入全部本期贷方金额"
                clearable
                class="dc-number-input_NumberInput"
              ></number-input>
            </el-form-item>
          </el-row>
        </el-form>
      </el-row>
      <span slot="footer" class="dialog-footer">
        <el-button v-on:click="onSubmit" type="primary" v-show="action != 'view'">保存</el-button>
        <el-button v-on:click="onDialogClose" v-if="action != 'view'">取消</el-button>
        <el-button v-on:click="onDialogClose" v-if="action == 'view'">关闭</el-button>
      </span>
    </el-dialog>
  </el-row>
</template>
<script>
import { validatenull } from '@/utils/validate'
import { getCompanyLedgerPeriodsByAccount, saveCompanyLedgerPeriods } from '@/api/vouchermanager/companyLedgerPeriods'
import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'
export default {
  extends: BaseUI,
  name: 'companyLedgerPeriods-form',
  components: {
    /** 根据用户界面配置组件 开始 */

    /** 根据用户界面配置组件 结束 */

    OperationIcon
  },
  data() {
    return {
      /** 根据用户界面配置生成data数据 开始 */
      editFormData: {},
      // 对话框属性变量
      dialogProps: {
        visible: false,
        title: '分户期初余额设置'
      },
      dialogTitle: '分户期初余额设置',
      ledgerInfo: {},  // 分户信息
      /** 根据用户界面配置生成data数据 结束 */
      // 窗口操作类型 view/edit/add
      action: ''
    }
  },
  props: {
    // 权限
    permission: {
      type: Object
    }
  },
  computed: {},
  methods: {
    onSubmit() {
      this.$refs['editForm'].validate((valid) => {
        if (valid) {
          this.doSave()
        } else {
          return false
        }
      })
    },
    doSave() {
      this.setLoad()
      this.editFormData.mnydebitperiod = this.editFormData.mnydebitperiodall
      this.editFormData.mnycreditperiod = this.editFormData.mnycreditperiodall
      saveCompanyLedgerPeriods(this.editFormData)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.dialogProps.visible = false
            this.showMessage({
              type: 'success',
              msg: '保存成功'
            })
            this.$emit('save-finished')
          } else {
            this.showMessage(responseData)
          }
          this.resetLoad()
        })
        .catch((error) => {
          this.outputError(error)
        })
    },
    switchEdit() {
      this.action = 'edit'
      this.dialogProps.title = `修改${this.dialogTitle}`
      this.initOptions(this.editFormData)
    },

    getFormById(ledger) {
      this.setLoad()
      let searchParam = {
        params: []
      }
      searchParam.params.push({
        columnName: 'UQCOMPANYID',
        queryType: '=',
        value: currentUser.company.id
      })
      searchParam.params.push({
        columnName: 'UQACCOUNTID',
        queryType: '=',
        value: !validatenull(ledger.uqaccountid.uqaccountid) ? ledger.uqaccountid.uqaccountid : null
      })
      searchParam.params.push({
        columnName: 'UQLEDGEID',
        queryType: '=',
        value: !validatenull(ledger.uqledgeid) ? ledger.uqledgeid : null
      })
      return new Promise((resolve) => {
        getCompanyLedgerPeriodsByAccount(searchParam).then((responseData) => {
          let form = {}
          if (responseData.code == 100) {
            form = responseData.data
          } else {
            this.showMessage(responseData)
          }
          this.resetLoad()
          resolve(form)
        }).catch((error) => {
          this.outputError(error)
        })
      })
    },
    onDialogClose() {
      this.dialogProps.visible = false
    },
    onDialogOpen() {
      this.$nextTick(() => {
        this.$refs['editForm'].clearValidate()
      })
    },
    initOptions(This) {
      // 初始化自定义类型选择框选项变量
    },
    initEditData(ledge) {
      return {
        id: null,
        uqcompanyid: currentUser.company,
        uqglobalperiodid: { // 会计期
          uqglobalperiodid: '00000000-0000-0000-0000-000000000000'
        },
        uqaccountid: ledge.uqaccountid,
        uqledgeid: ledge,
        mnydebitperiodall: 0, // 年初借方金额
        mnycreditperiodall: 0, // 年初贷方金额
        mnydebitperiod: 0,  // 年初借方金额
        mnycreditperiod: 0  // 年初贷方金额
      }
    }
  },
  watch: {},
  mounted: function () {
    this.$nextTick(() => {
      this.$on('openEditDialog', async function (ledger) {
        this.ledger = ledger
        this.action = 'edit'
        this.dialogProps.title = `${ledger.varledgecode ? '[' + ledger.varledgecode + ']' + ledger.varledgename: ledger.varledgename}`
        this.editFormData = {
          ...this.initEditData(ledger),
          ...(await this.getFormById(ledger))
        }
        this.initOptions(this.editFormData)
        this.dialogProps.visible = true
      })
    })
  }
}
</script>
