<template>
  <div>
    <el-row :gutter="15">
      <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px" style="margin-top: 20px">
        <el-col :span="8">
          <el-form-item label="固定资产科目：" prop="asset_account">
            <el-input v-model="formData.asset_account" placeholder="固定资产科目：" show-word-limit readonly
                      clearable  :style="{width: '100%'}">
              <el-button
                icon="el-icon-search"
                slot="append"
                v-on:click="onSearchAccount('asset_account')"
              ></el-button>
              <el-button
                type="danger"
                icon="el-icon-delete"
                slot="append"
                v-on:click="clearAccount('asset_account')"
              ></el-button>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="累计折旧科目：" prop="accudep_account">
            <el-input v-model="formData.accudep_account" placeholder="累计折旧科目：" show-word-limit readonly
                      clearable  :style="{width: '100%'}">
              <el-button
                icon="el-icon-search"
                slot="append"
                v-on:click="onSearchAccount('accudep_account')"
              ></el-button>
              <el-button
                type="danger"
                icon="el-icon-delete"
                slot="append"
                v-on:click="clearAccount('accudep_account')"
              ></el-button>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="固定资产减值准备科目：" prop="predevaluate_account">
            <el-input v-model="formData.predevaluate_account" placeholder="固定资产减值准备科目：" show-word-limit
                      readonly clearable  :style="{width: '100%'}">
              <el-button
                icon="el-icon-search"
                slot="append"
                v-on:click="onSearchAccount('predevaluate_account')"
              ></el-button>
              <el-button
                type="danger"
                icon="el-icon-delete"
                slot="append"
                v-on:click="clearAccount('predevaluate_account')"
              ></el-button>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="在建工程科目：" prop="project_account">
            <el-input v-model="formData.project_account" placeholder="在建工程科目：" show-word-limit readonly
                      clearable  :style="{width: '100%'}">
              <el-button
                icon="el-icon-search"
                slot="append"
                v-on:click="onSearchAccount('project_account')"
              ></el-button>
              <el-button
                type="danger"
                icon="el-icon-delete"
                slot="append"
                v-on:click="clearAccount('project_account')"
              ></el-button>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="减值准备对方科目：" prop="predevaluate_coraccount">
            <el-input v-model="formData.predevaluate_coraccount" placeholder="减值准备对方科目：" show-word-limit
                      readonly clearable  :style="{width: '100%'}">
              <el-button
                icon="el-icon-search"
                slot="append"
                v-on:click="onSearchAccount('predevaluate_coraccount')"
              ></el-button>
              <el-button
                type="danger"
                icon="el-icon-delete"
                slot="append"
                v-on:click="clearAccount('predevaluate_coraccount')"
              ></el-button>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="折旧费用科目：" prop="depcost_account">
            <el-input v-model="formData.depcost_account" placeholder="折旧费用科目：" clearable
                      :style="{width: '100%'}">
              <el-button
                icon="el-icon-search"
                slot="append"
                v-on:click="onSearchAccount('depcost_account')"
              ></el-button>
              <el-button
                type="danger"
                icon="el-icon-delete"
                slot="append"
                v-on:click="clearAccount('depcost_account')"
              ></el-button>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="税金科目：" prop="tax_account">
            <el-input v-model="formData.tax_account" placeholder="税金科目：" show-word-limit readonly clearable
                       :style="{width: '100%'}">
              <el-button
                icon="el-icon-search"
                slot="append"
                v-on:click="onSearchAccount('tax_account')"
              ></el-button>
              <el-button
                type="danger"
                icon="el-icon-delete"
                slot="append"
                v-on:click="clearAccount('tax_account')"
              ></el-button>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="税率：" prop="rate">
            <el-input v-model="formData.rate" placeholder="税率：" show-word-limit clearable
                      :style="{width: '100%'}">
              <template slot="append">%</template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="折旧时点: " prop="depreciation_time">
            <el-select v-model="formData.depreciation_time" placeholder="请选择折旧时点: " clearable
                       :style="{width: '100%'}">
              <el-option v-for="(item, index) in depreciation_timeOptions" :key="index" :label="item.label"
                         :value="item.value" :disabled="item.disabled"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="系统启用年月：" prop="sys_startdate">
            <el-date-picker v-model="formData.sys_startdate" format="yyyy-MM" value-format="yyyy-MM"
                            :style="{width: '100%'}" placeholder="请选择系统启用年月：" clearable></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item size="large">
            <el-button type="primary" @click="submitForm">保存</el-button>
          </el-form-item>
        </el-col>
      </el-form>
      <AccountQuery ref="accountQuery" @onGetData="getCheckData"></AccountQuery>
    </el-row>
  </div>
</template>
<script>
import { getData,defaultsettingsave } from '@/api/assetting/tfamDefaultSettings'
import BaseUI from '@/views/components/baseUI'
import AccountQuery from "@/views/components/AccountingAccountQuery";
import { validatenull } from "@/utils/validate";
export default {
  extends: BaseUI,
  components: {
    AccountQuery
  },
  props: [],
  data() {
    return {
      openAccountQuery:"",
      formData: {
        asset_account: undefined,
        asset_accountID: undefined,
        accudep_account: undefined,
        accudep_accountID: undefined,
        predevaluate_account: undefined,
        predevaluate_accountID: undefined,
        project_account: undefined,
        project_accountID: undefined,
        predevaluate_coraccount: undefined,
        predevaluate_coraccountID: undefined,
        depcost_account: undefined,
        depcost_accountID: undefined,
        tax_account: undefined,
        tax_accountID: undefined,
        rate: undefined,
        rateID: undefined,
        depreciation_time: "0",
        depreciation_timeID: "0",
        sys_startdate: "2023-08",
        sys_startdateID: "2023-08",
      },
      rules: {
        asset_account: [{
          required: true,
          message: '固定资产科目：',
          trigger: 'blur'
        }],
        accudep_account: [{
          required: true,
          message: '累计折旧科目：',
          trigger: 'blur'
        }],
        predevaluate_account: [{
          required: true,
          message: '固定资产减值准备科目：',
          trigger: 'blur'
        }],
        project_account: [{
          required: true,
          message: '在建工程科目：',
          trigger: 'blur'
        }],
        predevaluate_coraccount: [{
          required: true,
          message: '减值准备对方科目：',
          trigger: 'blur'
        }],
        depcost_account: [{
          required: true,
          message: '折旧费用科目：',
          trigger: 'blur'
        }],
        tax_account: [{
          required: true,
          message: '税金科目：',
          trigger: 'blur'
        }],
        rate: [{
          required: true,
          message: '税率：',
          trigger: 'blur'
        }],
        depreciation_time: [{
          required: true,
          message: '请选择折旧时点: ',
          trigger: 'change'
        }],
        sys_startdate: [{
          required: true,
          message: '请选择系统启用年月：',
          trigger: 'change'
        }],
      },
      depreciation_timeOptions: [{
        "label": "月初折旧",
        "value": "0"
      }, {
        "label": "月末折旧",
        "value": "1"
      }],
    }
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {
    getData().then((responseData) => {
      if (responseData.code == 100) {
        for (let key in  responseData.data) {
          this.formData[key]=responseData.data[key]
        }
        // responseData.data
        // this.formData = responseData.data

      } else {
        this.showMessage(responseData)
      }
    })
      .catch((error) => {
        this.outputError(error)
      })
  },
  methods: {
    submitForm() {
      this.$refs['elForm'].validate(valid => {
        if (!valid) return
        defaultsettingsave(this.formData).then((responseData) => {
          if (responseData.code == 100) {
            this.showMessage({type: 'success', msg: '保存成功'})
          } else {
            this.showMessage(responseData)
          }
        })
          .catch((error) => {
            this.outputError(error)
          })
        // TODO 提交表单
      })
    },
    onSearchAccount(data) {
      this.openAccountQuery=data;
      console.log(data)

      this.$refs.accountQuery.open();
    },
    clearAccount(data){
      this.formData[data]="";
      this.formData[data+"ID"]="";
    },
    getCheckData(data) {
      if (!validatenull(data.tableData)) {
        var openAccountQuery = this.openAccountQuery;
        console.log(data.tableData)
        this.formData[this.openAccountQuery]=data.tableData.varaccountname;
        this.formData[this.openAccountQuery+"ID"]=data.tableData.uqaccountid;
      }
    },
  }
}

</script>
<style>
</style>
