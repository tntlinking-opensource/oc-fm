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
            <el-col :span="24">
              <el-form-item
                prop="name"
                label="报表名称"
                :rules="[{ required: true, message: '报表名称不能为空', trigger: 'blur' }]"
                class="dc-el-form-item_SingleInput"
              >
                <el-input
                  v-model="editFormData.name"
                  :maxLength="100"
                  placeholder="请输入报表名称"
                  clearable
                  class="dc-el-input_SingleInput"
                >
                  <template slot="append">.ureport.xml</template>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item prop="type" label="报表类型" class="dc-el-form-item_NumberInput"
                            :rules="[{ required: true, message: '请选择报表类型', trigger: 'change' }]">
                <el-select
                  v-model="editFormData.type"
                  :style="{ width: '100%' }"
                  placeholder="请选择报表类型"
                  filterable
                  class="dc-el-select_SelectInput"
                >
                  <el-option v-for="(item, index) in typeOptions" :key="index" :label="item.name" :value="item.value"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </el-row>
      <span slot="footer" class="dialog-footer">
        <el-button v-on:click="onSubmit" type="primary">保存</el-button>
        <el-button v-on:click="onDialogClose">取消</el-button>
      </span>
    </el-dialog>
  </el-row>
</template>
<script>
import { validatenull } from '@/utils/validate'
import { saveUreportFileTbl } from '@/api/ureport/ureportFile.js'
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
        title: '添加报表'
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
      const name = this.editFormData.name
      this.editFormData.name = name + '.ureport.xml'
      saveUreportFileTbl(this.editFormData).then((responseData) => {
        if (responseData.code == 100) {
          this.dialogProps.visible = false
          this.showMessage({
            type: 'success',
            msg: '操作成功'
          })
          this.$emit('save-finished')
        } else {
          this.editFormData.name = name
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
        // 默认空报表
        content: 'PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz48dXJlcG9ydD48Y2VsbCBleHBhbmQ9Ik5vbmUiIG5hbWU9IkExIiByb3c9IjEiIGNvbD0iMSI+PGNlbGwtc3R5bGUgZm9udC1zaXplPSIxMCIgYWxpZ249ImNlbnRlciIgdmFsaWduPSJtaWRkbGUiPjwvY2VsbC1zdHlsZT48c2ltcGxlLXZhbHVlPjwhW0NEQVRBW11dPjwvc2ltcGxlLXZhbHVlPjwvY2VsbD48Y2VsbCBleHBhbmQ9Ik5vbmUiIG5hbWU9IkIxIiByb3c9IjEiIGNvbD0iMiI+PGNlbGwtc3R5bGUgZm9udC1zaXplPSIxMCIgYWxpZ249ImNlbnRlciIgdmFsaWduPSJtaWRkbGUiPjwvY2VsbC1zdHlsZT48c2ltcGxlLXZhbHVlPjwhW0NEQVRBW11dPjwvc2ltcGxlLXZhbHVlPjwvY2VsbD48Y2VsbCBleHBhbmQ9Ik5vbmUiIG5hbWU9IkMxIiByb3c9IjEiIGNvbD0iMyI+PGNlbGwtc3R5bGUgZm9udC1zaXplPSIxMCIgYWxpZ249ImNlbnRlciIgdmFsaWduPSJtaWRkbGUiPjwvY2VsbC1zdHlsZT48c2ltcGxlLXZhbHVlPjwhW0NEQVRBW11dPjwvc2ltcGxlLXZhbHVlPjwvY2VsbD48Y2VsbCBleHBhbmQ9Ik5vbmUiIG5hbWU9IkQxIiByb3c9IjEiIGNvbD0iNCI+PGNlbGwtc3R5bGUgZm9udC1zaXplPSIxMCIgYWxpZ249ImNlbnRlciIgdmFsaWduPSJtaWRkbGUiPjwvY2VsbC1zdHlsZT48c2ltcGxlLXZhbHVlPjwhW0NEQVRBW11dPjwvc2ltcGxlLXZhbHVlPjwvY2VsbD48Y2VsbCBleHBhbmQ9Ik5vbmUiIG5hbWU9IkEyIiByb3c9IjIiIGNvbD0iMSI+PGNlbGwtc3R5bGUgZm9udC1zaXplPSIxMCIgYWxpZ249ImNlbnRlciIgdmFsaWduPSJtaWRkbGUiPjwvY2VsbC1zdHlsZT48c2ltcGxlLXZhbHVlPjwhW0NEQVRBW11dPjwvc2ltcGxlLXZhbHVlPjwvY2VsbD48Y2VsbCBleHBhbmQ9Ik5vbmUiIG5hbWU9IkIyIiByb3c9IjIiIGNvbD0iMiI+PGNlbGwtc3R5bGUgZm9udC1zaXplPSIxMCIgYWxpZ249ImNlbnRlciIgdmFsaWduPSJtaWRkbGUiPjwvY2VsbC1zdHlsZT48c2ltcGxlLXZhbHVlPjwhW0NEQVRBW11dPjwvc2ltcGxlLXZhbHVlPjwvY2VsbD48Y2VsbCBleHBhbmQ9Ik5vbmUiIG5hbWU9IkMyIiByb3c9IjIiIGNvbD0iMyI+PGNlbGwtc3R5bGUgZm9udC1zaXplPSIxMCIgYWxpZ249ImNlbnRlciIgdmFsaWduPSJtaWRkbGUiPjwvY2VsbC1zdHlsZT48c2ltcGxlLXZhbHVlPjwhW0NEQVRBW11dPjwvc2ltcGxlLXZhbHVlPjwvY2VsbD48Y2VsbCBleHBhbmQ9Ik5vbmUiIG5hbWU9IkQyIiByb3c9IjIiIGNvbD0iNCI+PGNlbGwtc3R5bGUgZm9udC1zaXplPSIxMCIgYWxpZ249ImNlbnRlciIgdmFsaWduPSJtaWRkbGUiPjwvY2VsbC1zdHlsZT48c2ltcGxlLXZhbHVlPjwhW0NEQVRBW11dPjwvc2ltcGxlLXZhbHVlPjwvY2VsbD48Y2VsbCBleHBhbmQ9Ik5vbmUiIG5hbWU9IkEzIiByb3c9IjMiIGNvbD0iMSI+PGNlbGwtc3R5bGUgZm9udC1zaXplPSIxMCIgYWxpZ249ImNlbnRlciIgdmFsaWduPSJtaWRkbGUiPjwvY2VsbC1zdHlsZT48c2ltcGxlLXZhbHVlPjwhW0NEQVRBW11dPjwvc2ltcGxlLXZhbHVlPjwvY2VsbD48Y2VsbCBleHBhbmQ9Ik5vbmUiIG5hbWU9IkIzIiByb3c9IjMiIGNvbD0iMiI+PGNlbGwtc3R5bGUgZm9udC1zaXplPSIxMCIgYWxpZ249ImNlbnRlciIgdmFsaWduPSJtaWRkbGUiPjwvY2VsbC1zdHlsZT48c2ltcGxlLXZhbHVlPjwhW0NEQVRBW11dPjwvc2ltcGxlLXZhbHVlPjwvY2VsbD48Y2VsbCBleHBhbmQ9Ik5vbmUiIG5hbWU9IkMzIiByb3c9IjMiIGNvbD0iMyI+PGNlbGwtc3R5bGUgZm9udC1zaXplPSIxMCIgYWxpZ249ImNlbnRlciIgdmFsaWduPSJtaWRkbGUiPjwvY2VsbC1zdHlsZT48c2ltcGxlLXZhbHVlPjwhW0NEQVRBW11dPjwvc2ltcGxlLXZhbHVlPjwvY2VsbD48Y2VsbCBleHBhbmQ9Ik5vbmUiIG5hbWU9IkQzIiByb3c9IjMiIGNvbD0iNCI+PGNlbGwtc3R5bGUgZm9udC1zaXplPSIxMCIgYWxpZ249ImNlbnRlciIgdmFsaWduPSJtaWRkbGUiPjwvY2VsbC1zdHlsZT48c2ltcGxlLXZhbHVlPjwhW0NEQVRBW11dPjwvc2ltcGxlLXZhbHVlPjwvY2VsbD48cm93IHJvdy1udW1iZXI9IjEiIGhlaWdodD0iMTgiLz48cm93IHJvdy1udW1iZXI9IjIiIGhlaWdodD0iMTgiLz48cm93IHJvdy1udW1iZXI9IjMiIGhlaWdodD0iMTgiLz48Y29sdW1uIGNvbC1udW1iZXI9IjEiIHdpZHRoPSI4MCIvPjxjb2x1bW4gY29sLW51bWJlcj0iMiIgd2lkdGg9IjgwIi8+PGNvbHVtbiBjb2wtbnVtYmVyPSIzIiB3aWR0aD0iODAiLz48Y29sdW1uIGNvbC1udW1iZXI9IjQiIHdpZHRoPSI4MCIvPjxwYXBlciB0eXBlPSJBNCIgbGVmdC1tYXJnaW49IjkwIiByaWdodC1tYXJnaW49IjkwIgogICAgdG9wLW1hcmdpbj0iNzIiIGJvdHRvbS1tYXJnaW49IjcyIiBwYWdpbmctbW9kZT0iZml0cGFnZSIgZml4cm93cz0iMCIKICAgIHdpZHRoPSI1OTUiIGhlaWdodD0iODQyIiBvcmllbnRhdGlvbj0icG9ydHJhaXQiIGh0bWwtcmVwb3J0LWFsaWduPSJsZWZ0IiBiZy1pbWFnZT0iIiBodG1sLWludGVydmFsLXJlZnJlc2gtdmFsdWU9IjAiIGNvbHVtbi1lbmFibGVkPSJmYWxzZSI+PC9wYXBlcj48L3VyZXBvcnQ+', // content_
        company: {
          id: currentUser.company.id,
          name: currentUser.company.name
        },
        type: null, // 报表类型（1：利润表，2：资产负债，3：现金流量）
        status: 0, // 报表状态（0：停用，1：启用）
        createTime: null, // create_time_
        updateTime: null // update_time_
      }
    }
  },
  watch: {},
  mounted: function () {
    this.$nextTick(() => {
      this.$on('openAddDialog', async function () {
        this.editFormData = this.initEditData()
        this.initOptions(this.editFormData)
        this.dialogProps.visible = true
      })
    })
  }
}
</script>
