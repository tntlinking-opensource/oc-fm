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
              label="项目编号"
              :rules="[{ required: true, message: '项目编号不能为空', trigger: 'blur' }]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="editFormData.varcode"
                :maxLength="50"
                placeholder="请输入项目编号"
                clearable
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
            <el-form-item
              prop="varname"
              label="项目名称"
              :rules="[{ required: true, message: '项目名称不能为空', trigger: 'blur' }]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="editFormData.varname"
                :maxLength="100"
                placeholder="请输入项目名称"
                clearable
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
            <el-form-item
              prop="uqflowtypeid.uqflowtypeid"
              label="所属分类"
              :rules="[{ required: true, message: '所属分类不能为空', trigger: 'change' }]"
              class="dc-el-form-item_SelectInput"
            >
              <el-input
                disabled
                v-model="editFormData.uqflowtypeid.varcode ? '[' + editFormData.uqflowtypeid.varcode + ']' + editFormData.uqflowtypeid.varname : editFormData.uqflowtypeid.varname"
                :maxLength="50"
                placeholder="现金流量类别"
                clearable
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
            <el-form-item
              prop="direct.value"
              label="流向"
              :rules="[{ required: true, message: '流向不能为空', trigger: 'change' }]"
              class="dc-el-form-item_SelectInput"
            >
              <el-select
                v-model="editFormData.direct"
                :style="{ width: '100%' }"
                placeholder="请选择流向"
                clearable
                filterable
                class="dc-el-select_SelectInput"
              >
                <el-option v-for="(item, index) in directOptions" :key="index" :label="item.name" :value="item"></el-option>
              </el-select>
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
import { getFlowitemsById, saveFlowitems } from '@/api/tgl/flow/flowitems'
import { listDictItemAll } from '@/api/sys/dictItem.js'
import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'
export default {
  extends: BaseUI,
  name: 'flowitems-form',
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
        title: '现金流量项目'
      },
      dialogTitle: '现金流量项目',
      // 选项变量
      // 所属分类选项
      // 流向选项
      directOptions: [],
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

      saveFlowitems(this.editFormData)
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

    getFormById(id) {
      this.setLoad()
      return new Promise((resolve) => {
        getFlowitemsById(id)
          .then((responseData) => {
            let form = {}
            if (responseData.code == 100) {
              form = responseData.data
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
    listDirectOptions() {
      let search_List = {
        params: [{columnName: 'dict_type_id', queryType: '=', value: '1485382141169614860'}]
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [])
      // 数据权限: 字典项sys_dict_item
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1457254743943431066')
      listDictItemAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.directOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },
    initOptions(This) {
      // 初始化自定义类型选择框选项变量
      this.listDirectOptions()
    },
    initEditData(This) {
      return {
        varcode: '', // 项目编号
        varname: '', // 项目名称
        uqflowtypeid: {
          uqflowtypeid: validatenull(This) || validatenull(This.uqflowtypeid) ? null : This.uqflowtypeid,
          varcode: validatenull(This) || validatenull(This.varcode) ? null : This.varcode,
          varname: validatenull(This) || validatenull(This.varname) ? null : This.varname,
        },
        tenantId: currentUser.company.id, // 公司ID
        intstatus: 0, // 状态(0新建,1启用,2停用)
        direct: { // 流向
          id: null,
          name: null,
          value: null
        }
      }
    }
  },
  watch: {},
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
        this.editFormData = {
          ...this.initEditData(),
          ...parent
        }
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
