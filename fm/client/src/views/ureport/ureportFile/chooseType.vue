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
            <el-form-item prop="type" label="报表类型" class="dc-el-form-item_NumberInput"
                          :rules="[{ required: true, message: '请选择报表类型', trigger: 'change' }]">
              <el-select
                v-model="editFormData.type"
                :style="{ width: '100%' }"
                placeholder="请选择凭证状态"
                filterable
                class="dc-el-select_SelectInput"
              >
                <el-option v-for="(item, index) in typeOptions" :key="index" :label="item.name" :value="item.value"></el-option>
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
import { updateUreportFile } from '@/api/ureport/ureportFile.js'
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
      typeOptions:  []
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
      this.setLoad()
      updateUreportFile(this.editFormData).then((responseData) => {
        if (responseData.code == 100) {
          this.dialogProps.visible = false
          this.showMessage({
            type: 'success',
            msg: '操作成功'
          })
          this.$emit('chooseType-finished')
        } else {
          this.showMessage(responseData)
        }
        this.resetLoad()
      }).catch((error) => {
        this.outputError(error)
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
      let typeSearch = {
        params: [{columnName: 'dict_type_id', queryType: '=', value: '1495577912495775744'}]
      }
      // filter条件
      typeSearch.params.push.apply(typeSearch.params, [])
      // 数据权限: 字典项sys_dict_item
      this.pushDataPermissions(typeSearch.params, this.$route.meta.routerId, '1457254743943431066')
      listDictItemAll(typeSearch).then((responseData) => {
        if (responseData.code == 100) {
          this.typeOptions = responseData.data
          if (!validatenull(this.typeOptions)) {
            let type = this.typeOptions.find((item)=>{
              return item.name == '利润表'
            })
            this.editFormData.type = type.value
          }
        } else {
          this.showMessage(responseData)
        }
      })
    },
    initEditData() {
      return {
        id: null, // id_
        name: '', // name_
        content: '', // content_
        type: null, // 报表类型（1：利润表，2：资产负债，3：现金流量）
        status: null, // 报表状态（0：停用，1：启用）
        createTime: null, // create_time_
        updateTime: null // update_time_
      }
    }
  },
  watch: {},
  mounted: function () {
    this.$nextTick(() => {
      this.$on('openChooseTypeDialog', async function (row) {
        this.editFormData = row
        this.initOptions(this.editFormData)
        this.dialogProps.visible = true
      })
    })
  }
}
</script>
