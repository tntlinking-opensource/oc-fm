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
          label-width="120px"
          :disabled="action == 'view'"
          class="dc-el-form_ElEditForm"
        >
          <el-row>
            <el-col :span="24">
              <el-form-item
                prop="varname"
                label="会计期名称"
                :rules="[{ required: true, message: '会计期名称不能为空', trigger: 'blur' }]"
                class="dc-el-form-item_SingleInput"
              >
                <el-input
                  v-model="editFormData.varname"
                  :maxLength="40"
                  placeholder="请输入会计期名称"
                  clearable
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="intyear"
                label="会计期年份"
                :rules="[{ required: true, message: '会计期年份不能为空', trigger: 'change' }]"
                class="dc-el-form-item_NumberInput"
              >
                <el-date-picker
                  :disabled="editFormData.intstatus != 1"
                  v-model="editFormData.intyear"
                  type="year"
                  value-format="yyyy"
                  :style="{ width: '100%' }"
                  placeholder="选择年"
                  @change="onChangeYear">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="intmonth"
                label="会计期月份"
                :rules="[{ required: true, message: '会计期月份不能为空', trigger: 'change' }]"
                class="dc-el-form-item_NumberInput"
              >
                <el-select
                  :disabled="editFormData.intstatus != 1"
                  v-model="editFormData.intmonth"
                  clearable
                  placeholder="请输入月份"
                  @change="onChangeYear"
                  :style="{ width: '100%' }">
                  <el-option
                    v-for="item in monthOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="dtbegin"
                label="有效开始时间"
                :rules="[{ required: true, message: '有效开始时间不能为空', trigger: 'change' }]"
                class="dc-el-form-item_PickerTimeDate"
              >
                <el-date-picker
                  :disabled="editFormData.intstatus != 1"
                  v-model="editFormData.dtbegin"
                  :style="{ width: '100%' }"
                  v-on:change="onChangeBeginDate"
                  placeholder="请选择有效开始时间"
                  type="date"
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd"
                  class="dc-el-date-picker_PickerTimeDate"
                ></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="dtend"
                label="有效结束时间"
                :rules="[{ required: true, message: '有效结束时间不能为空', trigger: 'change' }]"
                class="dc-el-form-item_PickerTimeDate"
              >
                <el-date-picker
                  :disabled="editFormData.intstatus != 1"
                  v-model="editFormData.dtend"
                  :style="{ width: '100%' }"
                  placeholder="请选择有效结束时间"
                  type="date"
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd"
                  :picker-options="pickerOptionsEndDateTime"
                  class="dc-el-date-picker_PickerTimeDate"
                ></el-date-picker>
              </el-form-item>
            </el-col>
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
/** 根据用户界面配置import组件 结束 */

/** 根据用户界面配置import组件 结束 */
import { getGlobalPeriodsById, saveGlobalPeriods } from '@/api/tgl/global/globalPeriods'
import BaseUI from '@/views/components/baseUI'
import moment from "moment";
import OperationIcon from '@/components/OperationIcon'
export default {
  extends: BaseUI,
  name: 'globalPeriods-form',
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
        title: '会计期管理'
      },
      dialogTitle: '会计期管理',
      // 选项变量
      monthOptions: [
        {label: '1月', value: 1},
        {label: '2月', value: 2},
        {label: '3月', value: 3},
        {label: '4月', value: 4},
        {label: '5月', value: 5},
        {label: '6月', value: 6},
        {label: '7月', value: 7},
        {label: '8月', value: 8},
        {label: '9月', value: 9},
        {label: '10月', value: 10},
        {label: '11月', value: 11},
        {label: '12月', value: 12},
      ],
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
  computed: {
    pickerOptionsEndDateTime() {
      const _this = this
      return {
        disabledDate (time) {
          if (_this.editFormData.dtbegin) { // 如果开始时间不为空，结束时间大于开始时间
            return new Date(_this.editFormData.dtbegin).getTime() > time.getTime()
          } else {  // 开始时间不选时，结束时间小于等于当天
            return time.getTime() > Date.now()
          }
        }
      }
    }
  },
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
      // 设置年月字段，由年份 + 月份组成。格式：yyyyMM
      let valMonth = this.editFormData.intmonth + ''
      if (this.editFormData.intmonth < 10) {
        valMonth = '0' + this.editFormData.intmonth
      }
      this.editFormData.intyearmonth = parseInt(this.editFormData.intyear + valMonth)
      saveGlobalPeriods(this.editFormData).then((responseData) => {
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
        }).catch((error) => {
          this.outputError(error)
        })
    },
    onChangeYear() { // 初始化开始日期和结束日期
      if (!validatenull(this.editFormData.intyear) && !validatenull(this.editFormData.intmonth)) {
        let yearMonth = this.editFormData.intyear + '' + this.editFormData.intmonth
        if (this.editFormData.intmonth < 10) {
          yearMonth = this.editFormData.intyear + '0' + this.editFormData.intmonth
        }
        this.editFormData.dtbegin = moment(yearMonth).startOf('month').format("YYYY-MM-DD")
        this.editFormData.dtend = moment(yearMonth).endOf('month').format("YYYY-MM-DD")
      }
    },
    onChangeBeginDate() {
      this.editFormData.dtend = '' // 清空结束时间
    },
    switchEdit() {
      this.action = 'edit'
      this.dialogProps.title = `修改${this.dialogTitle}`
      this.initOptions(this.editFormData)
    },

    getFormById(id) {
      this.setLoad()
      return new Promise((resolve) => {
        getGlobalPeriodsById(id)
          .then((responseData) => {
            let form = {}
            if (responseData.code == 100) {
              form = responseData.data
              form.intyear = form.intyear + ''
            } else {
              this.showMessage(responseData)
            }
            this.resetLoad()
            resolve(form)
          })
          .catch((error) => {
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
    initEditData(This) {
      return {
        varname: '', // 会计期名称
        intyear: new Date().getFullYear() + '', // 会计期年份
        intmonth: new Date().getMonth() + 1, // 会计期月份
        intyearmonth: null, // 年月
        tenantId: currentUser.company.id, // 公司ID
        dtbegin: moment().startOf('months').format('YYYY-MM-DD'), // 有效开始时间
        dtend: moment().endOf('months').format('YYYY-MM-DD'), // 有效结束时间
        intstatus: 1 //状态
      }
    }
  },
  watch: {
  },
  mounted: function () {
    this.$nextTick(() => {
      this.$on('openViewDialog', async function (id) {
        this.action = 'view'
        this.dialogProps.title = `查看${this.dialogTitle}`
        this.editFormData = {
          ...this.initEditData(),
          ...(await this.getFormById(id))
        }
        this.initOptions(this.editFormData)
        this.dialogProps.visible = true
      })

      this.$on('openEditDialog', async function (id) {
        this.action = 'edit'
        this.dialogProps.title = `修改${this.dialogTitle}`
        this.editFormData = {
          ...this.initEditData(),
          ...(await this.getFormById(id))
        }
        this.initOptions(this.editFormData)
        this.dialogProps.visible = true
      })
      this.$on('openAddDialog', function () {
        this.action = 'add'
        this.dialogProps.title = `添加${this.dialogTitle}`
        this.editFormData = this.initEditData()
        this.initOptions(this.editFormData)
        this.dialogProps.visible = true
      })
      this.$on('openCopyDialog', async function (id) {
        this.action = 'add'
        this.dialogProps.title = `添加${this.dialogTitle}`
        this.editFormData = {
          ...this.initEditData(),
          ...(await this.getFormById(id))
        }
        this.initOptions(this.editFormData)
        this.editFormData.id = null //把id设置为空，添加一个新的

        this.dialogProps.visible = true
      })
    })
  }
}
</script>
