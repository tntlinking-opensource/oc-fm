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
      class="dc-el-dialog_ElDialog">
      <el-row gutter="0" type="flex" justify="end" align="top" class="dc-el-row_ElRow">
        <el-tooltip disabled class="dc-el-tooltip_Button">
          <el-button type="primary" icon="el-icon-plus" v-on:click="onAddAgingList"
            class="dc-el-button_Button">增加</el-button>
        </el-tooltip>
      </el-row>
      <el-form
        ref="agingForm"
        :model="editFormData"
        label-width="100px"
        style="margin-top: 10px"
        class="dc-el-form_ElEditForm">
        <ux-grid
          :data="editFormData.agingList"
          ref="dictItemTable"
          border
          :edit-config="{ trigger: 'click', mode: 'cell' }"
          class="dc-ux-grid_EditTable">
          <ux-table-column type="index" header-align="center" align="center" width="50"/>
          <ux-table-column
            field="begday"
            title="起始天数"
            tree-node
            resizable
            min-width="100px"
            align="left"
            header-align="center"
            show-overflow
            :edit-render="{ autofocus: '.el-input__inner' }"
            class="dc-ux-table-column_SingleInput"
          >
            <template v-slot:header="{ column }">
              <span style="color: #f56c6c">*</span>
              {{ column.title }}
            </template>
            <template v-slot:edit="{ row, rowIndex }">
              <el-form-item
                :prop="'agingList.' + rowIndex + '.begday'"
                label-width="0px"
                :rules="[{ required: true, message: '请输入起始天数', trigger: 'blur' }]"
                class="dc-el-form-item_SingleInput"
              >
                <el-input v-model="row.begday" :maxLength="255" clearable class="dc-el-input_SingleInput"></el-input>
              </el-form-item>
            </template>
            <template v-slot="{ row, rowIndex }">
              <span class="my-input-sc">
                <el-form-item
                  :prop="'agingList.' + rowIndex + '.begday'"
                  label-width="0px"
                  :rules="[{ required: true, message: '请输入起始天数', trigger: 'blur' }]"
                  class="dc-el-form-item_SingleInput"
                >
                  {{ row.begday }}
                </el-form-item>
              </span>
            </template>
          </ux-table-column>
          <ux-table-column
            field="aging"
            title="总天数"
            tree-node
            resizable
            align="left"
            min-width="100px"
            header-align="center"
            show-overflow
            :edit-render="{ autofocus: '.el-input__inner' }"
            class="dc-ux-table-column_NumberInput"
          >
            <template v-slot:header="{ column }">
              <span style="color: #f56c6c">*</span>
              {{ column.title }}
            </template>
            <template v-slot:edit="{ row, rowIndex }">
              <el-form-item
                :prop="'agingList.' + rowIndex + '.aging'"
                :rules="[{ required: true, message: '请输入总天数', trigger: 'blur' }]"
                label-width="0px"
                class="dc-el-form-item_NumberInput"
              >
                <number-input
                  v-model="row.aging"
                  :min="0"
                  :max="99999"
                  :precision="0"
                  placeholder="请输入"
                  clearable
                  class="dc-number-input_NumberInput"
                ></number-input>
              </el-form-item>
            </template>
            <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'agingList.' + rowIndex + '.aging'"
                    :rules="[{ required: true, message: '请输入总天数', trigger: 'blur' }]"
                    label-width="0px"
                    class="dc-el-form-item_NumberInput"
                  >
                    {{
                      row.aging
                        ? '' +
                        '' +
                        Number(row.aging)
                          .toFixed(0)
                          .replace(/(\d)(?=(\d{3})+\.)/g, '$1,') +
                        ''
                        : null
                    }}
                  </el-form-item>
                </span>
            </template>
          </ux-table-column>
          <ux-table-column
            title="操作"
            tree-node
            resizable
            width="60px"
            fixed="right"
            align="left"
            header-align="center"
            class="dc-ux-table-column_ElTableOptColumn">
            <template v-slot:header="scope">
              <span>操作</span>
            </template>
            <template slot-scope="scope">
              <OperationIcon
                v-on:click="onDeleteChild(editFormData.agingList, scope.rowIndex)"
                type="danger"
                content="删除"
                placement="top"
                icon-name="el-icon-delete"
                class="dc-OperationIcon_IconButton"
              ></OperationIcon>
            </template>
          </ux-table-column>
        </ux-grid>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button v-on:click="onSubmit" type="primary">保存</el-button>
        <el-button v-on:click="onDialogClose">取消</el-button>
      </span>
    </el-dialog>
  </el-row>
</template>
<script>
import { validatenull } from '@/utils/validate'
import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'
import { bulkInsertAndUpdateAging } from "@/api/tgl/aging/aging";
export default {
  extends: BaseUI,
  name: 'aging-form',
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
        title: '账龄表区间设置'
      }
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
      this.$refs['agingForm'].validate((valid) => {
        if (valid) {
          this.doSave()
        } else {
          return false
        }
      })
    },
    doSave() {
      this.setLoad()
      if (this.editFormData.agingList.length > 7) {
        this.$alert('请勿添加超过7个！', '提示', {
          confirmButtonText: '确定',
          type: 'info'
        })
        return false
      }
      bulkInsertAndUpdateAging(this.editFormData.agingList).then((responseData) => {
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
    onDialogClose() {
      this.dialogProps.visible = false
    },
    onDialogOpen() {
      this.$nextTick(() => {
        this.$refs['agingForm'].clearValidate()
      })
    },
    initOptions(This) {
      // 初始化自定义类型选择框选项变量
    },
    onDeleteChild(tableData, index) {
      // 根据下标删除子表数据
      tableData.splice(index, 1)
    },
    initEditData() {
      return {
        agingList: []
      }
    },
    onAddAgingList() {
      if (this.editFormData.agingList.length >= 7) {
        this.$alert('请勿添加超过7个！', '提示', {
          confirmButtonText: '确定',
          type: 'info'
        })
        return false
      }
      this.editFormData.agingList.push({
        begday: '', // 起始天数
        aging: null, // 账龄
        tenantId: currentUser.company.id, // 公司
      })
    }
  },
  watch: {
  },
  mounted: function () {
    this.$nextTick(() => {
      this.$on('openEditAgingDialog', async function (agingTableData) {
        this.editFormData.agingList = JSON.parse(JSON.stringify(agingTableData))
        this.initOptions(this.editFormData)
        this.dialogProps.visible = true
      })
    })
  }
}
</script>
<style>
.my-input-sc {
  display: inline-block;
  height: 30px;
  line-height: 30px;
  -webkit-appearance: none;
  background-color: #ffffff;
  background-image: none;
  border-radius: 4px;
  border: 1px solid #dcdfe6;
  box-sizing: border-box;
  color: #606266;
  font-size: inherit;
  outline: none;
  padding: 0 15px;
  transition: border-color 0.2s cubic-bezier(0.645, 0.045, 0.355, 1);
  width: 100%;
}
</style>
