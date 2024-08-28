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
            <el-form-item prop="globalPeriods.varname" label="会计期" class="dc-el-form-item_NumberInput"
                          :rules="[{ required: true, message: '请选择会计期', trigger: 'change' }]">
              <el-select
                value-key="id"
                v-model="editFormData.globalPeriods"
                :style="{ width: '100%' }"
                placeholder="请选择会计期"
                filterable
                class="dc-el-select_SelectInput"
              >
                <el-option v-for="(item, index) in globalPeriodOptions" :key="index" :label="item.varname" :value="item"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item prop="status.name" label="凭证状态" class="dc-el-form-item_NumberInput"
                          :rules="[{ required: true, message: '请选择凭证状态', trigger: 'change' }]">
              <el-select
                v-model="editFormData.status"
                :style="{ width: '100%' }"
                placeholder="请选择凭证状态"
                value-key="id"
                filterable
                class="dc-el-select_SelectInput"
              >
                <el-option v-for="(item, index) in statusOptions" :key="index" :label="item.name" :value="item"></el-option>
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
</template>
<script>
import { validatenull } from '@/utils/validate'
import { selectPeriod } from '@/api/tgl/global/globalPeriods'
import { listDictItemAll } from '@/api/sys/dictItem.js'
import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'
export default {
  extends: BaseUI,
  name: 'reportDesign-query',
  components: {
    OperationIcon
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
      globalPeriodOptions: [],
      statusOptions:  []
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
      this.dialogProps.visible = false
      this.$emit('choose-finished', this.editFormData)
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
            this.editFormData.globalPeriods = this.globalPeriodOptions[0]
          }
        } else {
          this.showMessage(responseData)
        }
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
