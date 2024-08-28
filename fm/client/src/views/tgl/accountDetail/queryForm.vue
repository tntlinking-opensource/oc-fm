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
          class="dc-el-form_ElEditForm"
        >
          <el-row>
            <el-form-item
              prop="companyName"
              :rules="[{ required: true, message: '请选择分户', trigger: 'change' }]"
              label="所属公司"
              class="dc-el-form-item_SelectInput">
              <el-input
                v-model="editFormData.companyName"
                placeholder="请选择所属公司"
                disabled
                class="dc-el-input_SingleInput">
              </el-input>
            </el-form-item>
            <el-form-item
              prop="accountcode"
              :rules="[{ required: true, message: '请选择开始科目', trigger: 'change' }]"
              label="科目"
              class="dc-el-form-item_SelectInput">
              <el-select
                v-model="editFormData.accountcode"
                :style="{ width: '70%' }"
                placeholder="请选择科目"
                clearable
                filterable
                @change="onChangeAccount">
                <el-option v-for="(item, index) in uqaccountidOptions"
                           :key="index"
                           :label="item.varaccountcode
                                  ? '[' + item.varaccountcode + ']' + item.varaccountname : item.varaccountname"
                           :value="item.varaccountcode"></el-option>
              </el-select>
              <el-button type="info" plain v-on:click="$refs.accountQuery.open()" icon="el-icon-search"></el-button>
            </el-form-item>
            <el-form-item
              prop="ledgerdetailName"
              :rules="[{ required: true, message: '请选择分户', trigger: 'change' }]"
              label="分户"
              class="dc-el-form-item_SelectInput">
              <el-input
                v-model="editFormData.ledgerdetailName"
                placeholder="请选择分户"
                :style="{ width: '70%' }"
                disabled
                class="dc-el-input_SingleInput">
                slot="suffix"重点  btnClearable事件，value有值就显示没有值就隐藏
                <span slot="suffix" v-show="editFormData.ledgerdetailName" class="iconClose">
                   <i class="el-icon-close" style="margin-left: 5px;cursor: pointer;" @click="btnClearable"></i>
                </span>
              </el-input>
              <el-button type="info" plain v-on:click="onSearchLedgetype" icon="el-icon-search"></el-button>
            </el-form-item>
            <el-form-item prop="beginyearmonth" label="会计期从" class="dc-el-form-item_NumberInput"
                          :rules="[{ required: true, message: '请选择会计期', trigger: 'change' }]">
              <el-select
                value-key="id"
                v-model="editFormData.beginyearmonth"
                :style="{ width: '100%' }"
                placeholder="请选择会计期"
                filterable
                class="dc-el-select_SelectInput"
              >
                <el-option v-for="(item, index) in globalPeriodOptions" :key="index" :label="item.varname" :value="item.intyearmonth"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item prop="endyearmonth" label="到" class="dc-el-form-item_NumberInput"
                          :rules="[{ required: true, message: '请选择会计期', trigger: 'change' }]">
              <el-select
                value-key="id"
                v-model="editFormData.endyearmonth"
                :style="{ width: '100%' }"
                placeholder="请选择会计期"
                filterable
                class="dc-el-select_SelectInput"
              >
                <el-option v-for="(item, index) in globalPeriodOptions" :key="index" :label="item.varname" :value="item.intyearmonth"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item prop="intstatus" label="凭证状态" class="dc-el-form-item_NumberInput"
                          :rules="[{ required: true, message: '请选择凭证状态', trigger: 'change' }]">
              <el-select
                v-model="editFormData.intstatus"
                :style="{ width: '100%' }"
                placeholder="请选择凭证状态"
                value-key="id"
                filterable
                class="dc-el-select_SelectInput"
              >
                <el-option v-for="(item, index) in statusOptions" :key="index" :label="item.name" :value="item.value"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item prop="ledgerlevel" label="显示分户级" class="dc-el-form-item_NumberInput"
                          :rules="[{ required: true, message: '请选择分户级', trigger: 'change' }]">
              <el-select
                v-model="editFormData.ledgerlevel"
                :style="{ width: '100%' }"
                placeholder="请选择分户级"
                filterable
                class="dc-el-select_SelectInput"
              >
                <el-option v-for="(item, index) in subjectlevelList" :key="index" :label="item.name" :value="item.code"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item prop="isallzerodata" class="dc-el-form-item_SelectInput">
              <el-checkbox v-model="editFormData.isallzerodata" :true-label="1" :false-label="0">无年初数，发生数的科目不显示</el-checkbox>
            </el-form-item>
            <el-form-item prop="isallbalance" class="dc-el-form-item_SelectInput">
              <el-checkbox v-model="editFormData.isallbalance" :true-label="1" :false-label="0">逐笔显示余额</el-checkbox>
            </el-form-item>
          </el-row>
          <AccountQuery ref="accountQuery" :appendToBody="true" :isIntisledge="true" @onGetData="getCheckData"></AccountQuery>
          <LedgetypeQuery ref="typeQuery" :appendToBody="true"
                          :uqaccountid="this.editFormData.uqaccountid"
                          @onGetTreeData="getTreeCheckData"></LedgetypeQuery>
        </el-form>
      </el-row>
      <span slot="footer" class="dialog-footer">
        <el-button v-on:click="onSubmit" type="primary">确定</el-button>
        <el-button v-on:click="onDialogClose">取消</el-button>
      </span>
    </el-dialog>
  </el-row>
</template>
<script>
import { validatenull } from '@/utils/validate'
import { selectPeriod } from '@/api/tgl/global/globalPeriods'
import { listDictItemAll } from '@/api/sys/dictItem.js'
import { listTglAccountsAll, getSubjectlevelList } from '@/api/vouchermanager/InitLedgePeriod/tglAccounts.js'
import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'
import AccountQuery from "@/views/components/AccountingAccountQuery"
import LedgetypeQuery from '@/views/components/LedgetypeQuery'

export default {
  extends: BaseUI,
  name: 'accountDetail-query',
  components: {
    OperationIcon,
    AccountQuery,
    LedgetypeQuery,
  },
  data() {
    return {
      /** 根据用户界面配置生成data数据 开始 */
      editFormData: this.initEditData(),
      // 对话框属性变量
      dialogProps: {
        visible: false,
        title: '过滤条件'
      },
      // 会计科目选项
      uqaccountidOptions: [],
      globalPeriodOptions: [],
      statusOptions: [],
      subjectlevelList: []
    }
  },
  props: {
  },
  computed: {},
  methods: {
    onSubmit() {
      this.$refs['editForm'].validate((valid) => {
        if (valid) {
          if (this.editFormData.beginyearmonth > this.editFormData.endyearmonth) {
            this.$alert('结束会计期不应小于开始会计期', '提示', {
              confirmButtonText: '确定',
              type: 'info'
            })
            return false
          }
          this.doSave()
        } else {
          return false
        }
      })
    },
    doSave() {
      this.dialogProps.visible = false
      this.$emit('choose-finished', this.editFormData)
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
    onChangeAccount(data) {
      if (!validatenull(data)) {
        const account = this.uqaccountidOptions.find(item => {
          return item.varaccountcode == data
        })
        this.editFormData.uqaccountid = account.uqaccountid
      }
    },
    initOptions(This) {
      this.setLoad()
      // 初始化自定义类型选择框选项变量
      let periodsSearch = {
        params: []
      }
      periodsSearch.params.push.apply(periodsSearch.params, [])
      // 数据权限: 会计期tgl_global_periods
      this.pushDataPermissions(periodsSearch.params, this.$route.meta.routerId, '1457254743943430269')
      selectPeriod(periodsSearch).then((responseData) => {
        if (responseData.code == 100) {
          this.globalPeriodOptions = responseData.data
          if (!validatenull(this.globalPeriodOptions)) {
            if (validatenull(this.editFormData.beginyearmonth)) {
              this.editFormData.beginyearmonth = this.globalPeriodOptions[0].intyearmonth
            }
            if (validatenull(this.editFormData.endyearmonth)) {
              this.editFormData.endyearmonth = this.globalPeriodOptions[0].intyearmonth
            }
          }
        } else {
          this.showMessage(responseData)
        }
      })
      // 获取科目管理列表
      let account_search = {
        params: [],
        columnName: 'varaccountfullcode',
        orderby: 'asc'
      };
      // filter条件
      account_search.params.push.apply(account_search.params, []);
      // 数据权限: 会计科目表tgl_accounts
      this.pushDataPermissions(account_search.params, this.$route.meta.routerId, "1457254743943430348");
      listTglAccountsAll(account_search).then(responseData => {
        if (responseData.code == 100) {
          this.uqaccountidOptions = responseData.data;
        } else {
          this.showMessage(responseData);
        }
      });
      // 获取科目级别拉框
      let level_search = {
        params: []
      };
      // filter条件
      level_search.params.push.apply(level_search.params, []);
      getSubjectlevelList(level_search).then(responseData => {
        if (responseData.code == 100) {
          this.subjectlevelList = responseData.data;
        } else {
          this.showMessage(responseData);
        }
      });
      let statusSearch = {
        params: [{columnName: 'dict_type_id', queryType: '=', value: '1523758172179726706'}]
      }
      // filter条件
      statusSearch.params.push.apply(statusSearch.params, [])
      // 数据权限: 字典项sys_dict_item
      this.pushDataPermissions(statusSearch.params, this.$route.meta.routerId, '1457254743943431066')
      listDictItemAll(statusSearch).then((responseData) => {
        if (responseData.code == 100) {
          this.statusOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
      this.resetLoad()
    },
    initEditData() {
      return {
        uqcompanyid: currentUser.company.id, // 公司ID
        companyName: currentUser.company.name, // 公司名称
        uqaccountid: '', // 科目id
        accountcode: '', // 科目编码
        ledgerdetailid: null, // 分户
        ledgerdetailName: null, // 分户名称
        beginyearmonth: null, // 起始会计期
        endyearmonth: null, // 结束会计期
        ledgerlevel: '99', // 分户等级
        ledgerlevelName: '末级科目', // 级次名称
        intstatus: '0', // 凭证状态（0：全部，1：未记账，2：已记账）
        isallzerodata: 1, // 无年初数、发生数科目不显示（1：是，0：否）
        isallbalance: 1, // 逐笔显示余额（1：是，0：否）
      }
    },
    btnClearable() {
      this.editFormData.ledgerdetailid = null
      this.editFormData.ledgerdetailName = ''
    },
    onSearchLedgetype() {
      if (validatenull(this.editFormData.accountcode)) {
        this.$alert('请选中科目', '提示', {
          confirmButtonText: '确定',
          type: 'info'
        })
        return false
      }
      this.$refs.typeQuery.open(this.editFormData.ledgerdetailid)
    },
    getCheckData(data) {
      if (!validatenull(data.tableData)) {
        this.editFormData.uqaccountid = data.tableData.uqaccountid
        this.editFormData.accountcode = data.tableData.varaccountcode
      }
    },
    getTreeCheckData(data) {
      if (!validatenull(data)) {
        this.editFormData.ledgerdetailid = data.arrData;
        this.editFormData.ledgerdetailName = data.arrName.join(",")
      }
    },
  },
  watch: {
    'editFormData.accountcode': {
      handler(newVal, oldVal) {
        if(newVal != oldVal) {
          this.editFormData.ledgerdetailid = null
          this.editFormData.ledgerdetailName = ''
        }
      },
      immediate:false, // 初始化监听
    },
    'editFormData.ledgerlevel': {
      handler(newVal, oldVal) {
        if(newVal != oldVal && !validatenull(newVal)) {
          const accountLevel = this.subjectlevelList.find(item => {
            return item.code == newVal
          })
          if (!validatenull(accountLevel)) {
            this.editFormData.ledgerlevelName = accountLevel.name
          }
        }
      }
    }
  },
  mounted: function () {
    this.$nextTick(() => {
      this.$on('openChooseDialog', async function (queryForm) {
        this.editFormData = { ...this.initEditData(), ...queryForm }
        this.initOptions(this.editFormData)
        this.dialogProps.visible = true
      })
    })
  }
}
</script>
