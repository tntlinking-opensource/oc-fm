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
            <el-form-item
              prop="varcode"
              label="类别编号"
              :rules="[{ required: true, message: '类别编号不能为空', trigger: 'blur' }]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="editFormData.varcode"
                :maxLength="50"
                placeholder="请输入类别编号"
                clearable
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
            <el-form-item
              prop="varname"
              label="类别名称"
              :rules="[{ required: true, message: '类别名称不能为空', trigger: 'blur' }]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="editFormData.varname"
                :maxLength="50"
                placeholder="请输入类别名称"
                clearable
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
            <el-form-item
              prop="uqparentid.uqflowtypeid"
              label="上级类别"
              class="dc-el-form-item_CascaderInput"
            >
              <el-input
                disabled
                v-model="editFormData.uqparentid.varcode ? '[' + editFormData.uqparentid.varcode + ']' + editFormData.uqparentid.varname : editFormData.uqparentid.varname"
                :maxLength="50"
                placeholder="请输入类别名称"
                clearable
                class="dc-el-input_SingleInput"
              ></el-input>
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
import { getFlowtypeById, saveFlowtype } from '@/api/tgl/flow/flowtype'
import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'
export default {
  extends: BaseUI,
  name: 'flowtype-form',
  components: {
    /** 根据用户界面配置组件 开始 */

    /** 根据用户界面配置组件 结束 */

    OperationIcon
  },
  data() {
    return {
      /** 根据用户界面配置生成data数据 开始 */
      editFormData: this.initEditData(),
      // 对话框属性变量
      dialogProps: {
        visible: false,
        title: '现金流量类别'
      },
      dialogTitle: '现金流量类别',
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
      if (!validatenull(this.editFormData.parent) && !validatenull(this.editFormData.parent)) {
        let parent = this.editFormData.parent
        this.editFormData.varfullcode = validatenull(parent.varcode) ? this.editFormData.varcode : parent.varcode +  '-' + this.editFormData.varcode
        this.editFormData.varfullname = validatenull(parent.varname) ? this.editFormData.varname : parent.varcode +  '-' + this.editFormData.varname
      }
      saveFlowtype(this.editFormData)
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

    onDialogClose() {
      this.dialogProps.visible = false
    },
    onDialogOpen() {
      this.$nextTick(() => {
        this.$refs['editForm'].clearValidate()
      })
    },
    getFormById(id) {
      this.setLoad()
      return new Promise((resolve) => {
        getFlowtypeById(id)
          .then((responseData) => {
            let form = {}
            if (responseData.code == 100) {
              form = responseData.data
              if (validatenull(form.parent)) {
                form.parent = {
                  id: null
                }
              }
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
    initOptions(This) {
    },
    onParentChange() {
      let nodes = this.$refs['parentCascader'].getCheckedNodes()
      if (nodes.length > 0) {
        if (nodes[0] && nodes[0].data && nodes[0].data.children) {
          this.editFormData.sort = (nodes[0].data.children.length + 1) * 10000
        } else if (nodes[0]) {
          this.editFormData.sort = 10000
        } else {
          this.editFormData.sort = (this.parentOptions.length + 1) * 10000
        }
      } else {
        this.editFormData.sort = (this.parentOptions.length + 1) * 10000
      }
    },
    initEditData(This) {
      return {
        varcode: '', // 类别编号
        varname: '', // 类别名称
        // 上级类别
        uqparentid: {
          varname: validatenull(This) || validatenull(This.varname) ? null : This.varname,
          uqflowtypeid: validatenull(This) || validatenull(This.uqflowtypeid) ? null : This.uqflowtypeid,
          varcode: validatenull(This) || validatenull(This.varcode) ? null : This.varcode
        },
        varfullcode: '', // 全编码
        varfullname: '', // 全名称
        tenantId: currentUser.company.id, // 公司ID
        intlevel: 1, // 级次
        intislastlevel: 1, // 是否末级（0：否，1：是）
        // 父级
        parent: {
          varcode: validatenull(This) || validatenull(This.varcode) ? null : This.varcode,
          varname: validatenull(This) || validatenull(This.varname) ? null : This.varname,
          id: validatenull(This) || validatenull(This.id) ? null : This.id
        },
        sort: This && This.children ? (This.children.length + 1) * 10000 : this.parentOptions
              ? (this.parentOptions.length + 1) * 10000 : 10000,
      }
    }
  },
  watch: {
    parentOptions(newVal, oldVal) {
      if (newVal != oldVal && this.action == 'add') {
        this.$nextTick(() => {
          this.onParentChange()
        })
      }
    }
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
      this.$on('openAddDialog', function (parent) {
        this.action = 'add'
        this.dialogProps.title = `添加${this.dialogTitle}`
        this.editFormData = this.initEditData(parent)
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
