<template>
  <div>
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
            <el-form-item  label="会计期从：" class="dc-el-form-item_NumberInput"
                          >
              <el-select
                value-key="id"
                v-model="form.period"
                :style="{ width: '100%' }"
                placeholder="请选择会计期"
                filterable
                class="dc-el-select_SelectInput"
              >
                <el-option v-for="(item, index) in globalPeriodOptions" :key="index" :label="item.varname" :value="item.intyearmonth"></el-option>
              </el-select>
            </el-form-item>

            <el-form-item  label="凭证状态：" class="dc-el-form-item_NumberInput"
            >
              <el-select
                value-key="id"
                v-model="form.bookStatus"
                :style="{ width: '100%' }"
                filterable
                class="dc-el-select_SelectInput"
              >
                <el-option v-for="item in bookStatusList" :key="item.categorycode" :label="item.categoryname" :value="item.categorycode"></el-option>
              </el-select>
            </el-form-item>

          </el-row>
        </el-form>
      </el-row>
      <span slot="footer" class="dialog-footer">
        <el-button v-on:click="onSubmit" type="primary">确定</el-button>
        <el-button v-on:click="onDialogClose">取消</el-button>
      </span>
    </el-dialog>
  </el-row>
  </div>
</template>
<script>

import { validatenull } from '@/utils/validate'
import addHkontDialog1 from "@/views/components/AccountingAccountQuery/index";
import addHkontDialog2 from "@/views/components/AccountingAccountQuery/index";
import { selectPeriod } from '@/api/tgl/global/globalPeriods'
import { getBookStatus,getHkontLevel,queryHkont,getCompanyList } from '@/api/ureport/accountingBook/accountingBook'
import { listDictItemAll } from '@/api/sys/dictItem.js'
import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'
export default {
  extends: BaseUI,
  name: 'reportDesign-query',
  components: {
    OperationIcon,
    addHkontDialog1,
    addHkontDialog2,
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
      companyList:[],
      globalPeriodOptions: [],
      statusOptions:  [],
      bookStatusList:  [],
      hkontLevelList:  [],
      hkontList:  [],
      form:{
        bookStatus:'',
        period:'',
      }
    }
  },
  props: {
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
      if (this.form.period==''){
        this.$message.error('账期不能为空!');
        return false;
      }
      this.dialogProps.visible = false
      this.$emit('choose-finished', this.form)
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
      var searchParams = {
        params:[],
      }
      // 初始化自定义类型选择框选项变量
      let periodsSearch = {
        params: []
      }
      periodsSearch.params.push.apply(periodsSearch.params, [])
      // 数据权限: 会计期tgl_global_periods
      this.pushDataPermissions(periodsSearch.params, this.$route.meta.routerId, '1457254743943430269')
      selectPeriod(periodsSearch).then((responseData) => {
        debugger;
        if (responseData.code == 100) {
          this.globalPeriodOptions = responseData.data
          if (!validatenull(this.globalPeriodOptions)) {
            this.form.period = this.globalPeriodOptions[0].intyearmonth
          }
        } else {
          this.showMessage(responseData)
        }
      })
      getBookStatus().then(res =>{
        this.form.bookStatus=res.data[0].categorycode;
        this.bookStatusList=res.data;
      })


      let statusSearch = {
        params: [{columnName: 'dict_type_id', queryType: '=', value: '1494073832128356364'}]
      }
      // filter条件
      statusSearch.params.push.apply(statusSearch.params, [])
      // 数据权限: 字典项sys_dict_item
      this.pushDataPermissions(statusSearch.params, this.$route.meta.routerId, '1457254743943431066')
      listDictItemAll(statusSearch).then((responseData) => {
        if (responseData.code == 100) {
          this.statusOptions = responseData.data
          if (!validatenull(this.statusOptions)) {
            let status = this.statusOptions.find((item)=>{
              return item.name == '全部凭证'
            })
            this.editFormData.status = status
          }
        } else {
          this.showMessage(responseData)
        }
      })
    },
    initEditData(id) {
      return {
        id: id,
        globalPeriods: {
          varname: '', // 会计期名称
          intyear: null, // 会计期年份
          intmonth: null, // 会计期月份
          intyearmonth: null, // 年月
          dtbegin: null, // 有效开始时间
          dtend: null, // 有效结束时间
          intstatus: null //状态
        },
        status: {
          id: '',
          name: '',
          value: ''
        }
      }
    }
  },
  watch: {},
  mounted: function () {
    this.$nextTick(() => {
      this.$on('openChooseDialog', async function (id) {
        this.editFormData = this.initEditData(id)
        this.initOptions(this.editFormData)
        this.dialogProps.visible = true
      })
    })
  }
}
</script>
