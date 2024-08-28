<template>
  <el-row class="dc-container">
    <el-dialog
      v-on:open="onDialogOpen"
      v-on:close="onDialogClose"
      v-loading="loading"
      width="70%"
      :fullscreen="false"
      :title="dialogProps.title"
      :visible.sync="dialogProps.visible"
      :close-on-click-modal="false"
      class="dc-el-dialog_ElDialog"
    >
      <el-row>
                  <el-form
                    ref="queryForm"
                    :model="queryFormData"
                    label-width="100px"
                    class="dc-el-form_ElQueryForm"
                  >
                    <el-row>
                      <el-col :span="6">
                        <el-form-item prop="department.id" label="部门" class="dc-el-form-item_CascaderInput">
                          <el-cascader
                            ref="cascader145"
                            :options="departmentOptions"
                            v-model="queryFormData.department.id"
                            :style="{ width: '100%' }"
                            placeholder="请选择部门"
                            :props="{ label: 'name', value: 'id', children: 'children', checkStrictly: true, emitPath: false }"
                            clearable
                            filterable
                            separator="/"
                            class="dc-el-cascader_CascaderInput"
                          ></el-cascader>
                        </el-form-item>
                      </el-col>
                      <el-col :span="6">
                        <el-form-item prop="name" label="用户名" class="dc-el-form-item_SingleInput">
                          <el-input
                            v-model="queryFormData.name"
                            :maxLength="128"
                            placeholder="请输入用户名"
                            clearable
                            class="dc-el-input_SingleInput"
                          ></el-input>
                        </el-form-item>
                      </el-col>
                      <el-col :span="6">
                        <el-tooltip effect="dark" content="搜索" placement="top">
                          <el-button v-on:click="onSearch" type="primary" icon="el-icon-search"></el-button>
                        </el-tooltip>
                      </el-col>
                    </el-row>

                  </el-form>
                  <ux-grid
                    ref="table"
                    :data="tableData"
                    border
                    row-key="id"
                    default-expand-all
                    highlight-current-row
                    @current-change="handleCurrentChange"
                    :tree-config="{
              children: '',
              indent: 20,
              accordion: false,
              line: false,
              showIcon: true,
              iconOpen: '',
              iconClose: ''
            }"
                    class="dc-ux-grid_QueryTable"
                  >
                    <ux-table-column
                      :field="tableColumns['126'].field"
                      :title="tableColumns['126'].title"
                      :width="tableColumns['126'].width"
                      :visible="tableColumns['126'].visible"
                      :params="{ sortId: '126' }"
                      tree-node
                      resizable
                      min-width="120px"
                      align="left"
                      header-align="center"
                      sortable
                      show-overflow
                      class="dc-ux-table-column_ElTableColumn"
                    ></ux-table-column>
                    <ux-table-column
                      :field="tableColumns['127'].field"
                      :title="tableColumns['127'].title"
                      :width="tableColumns['127'].width"
                      :visible="tableColumns['127'].visible"
                      :params="{ sortId: '127' }"
                      tree-node
                      resizable
                      min-width="120px"
                      align="left"
                      header-align="center"
                      sortable
                      show-overflow
                      class="dc-ux-table-column_ElTableColumn"
                    ></ux-table-column>
                    <ux-table-column
                      :field="tableColumns['128'].field"
                      :title="tableColumns['128'].title"
                      :width="tableColumns['128'].width"
                      :visible="tableColumns['128'].visible"
                      :params="{ sortId: '128' }"
                      tree-node
                      resizable
                      min-width="120px"
                      align="left"
                      header-align="center"
                      sortable
                      show-overflow
                      class="dc-ux-table-column_ElTableColumn"
                    ></ux-table-column>
                    <ux-table-column
                      :field="tableColumns['129'].field"
                      :title="tableColumns['129'].title"
                      :width="tableColumns['129'].width"
                      :visible="tableColumns['129'].visible"
                      :params="{ sortId: '129' }"
                      tree-node
                      resizable
                      min-width="120px"
                      align="left"
                      header-align="center"
                      sortable
                      show-overflow
                      class="dc-ux-table-column_ElTableColumn"
                    ></ux-table-column>
                    <ux-table-column
                      :field="tableColumns['130'].field"
                      :title="tableColumns['130'].title"
                      :width="tableColumns['130'].width"
                      :visible="tableColumns['130'].visible"
                      :params="{ sortId: '130' }"
                      tree-node
                      resizable
                      min-width="120px"
                      align="left"
                      header-align="center"
                      sortable
                      show-overflow
                      class="dc-ux-table-column_ElTableColumn"
                    ></ux-table-column>
                    <ux-table-column
                      :field="tableColumns['131'].field"
                      :title="tableColumns['131'].title"
                      :width="tableColumns['131'].width"
                      :visible="tableColumns['131'].visible"
                      :params="{ sortId: '131' }"
                      tree-node
                      resizable
                      min-width="180px"
                      align="left"
                      header-align="center"
                      sortable
                      show-overflow
                      class="dc-ux-table-column_ElTableColumn"
                    ></ux-table-column>
                    <ux-table-column
                      :field="tableColumns['132'].field"
                      :title="tableColumns['132'].title"
                      :width="tableColumns['132'].width"
                      :visible="tableColumns['132'].visible"
                      :params="{ sortId: '132' }"
                      tree-node
                      resizable
                      min-width="180px"
                      align="left"
                      header-align="center"
                      sortable
                      show-overflow
                      class="dc-ux-table-column_ElTableColumn"
                    ></ux-table-column>
                    <ux-table-column
                      :field="tableColumns['135'].field"
                      :title="tableColumns['135'].title"
                      :width="tableColumns['135'].width"
                      :visible="tableColumns['135'].visible"
                      :params="{ sortId: '135' }"
                      tree-node
                      resizable
                      min-width="180px"
                      align="left"
                      header-align="center"
                      sortable
                      show-overflow
                      class="dc-ux-table-column_ElTableColumn"
                    ></ux-table-column>
                    <ux-table-column
                      :field="tableColumns['139'].field"
                      :title="tableColumns['139'].title"
                      :width="tableColumns['139'].width"
                      :visible="tableColumns['139'].visible"
                      :params="{ sortId: '139' }"
                      tree-node
                      resizable
                      min-width="180px"
                      align="left"
                      header-align="center"
                      sortable
                      show-overflow
                      class="dc-ux-table-column_ElTableColumn"
                    ></ux-table-column>
                  </ux-grid>
                  <el-pagination
                    :total="tableDataTotal"
                    :page-size="search.limit"
                    background
                    :current-page="tableDataPage"
                    :page-sizes="[10, 20, 30, 40, 50, 100]"
                    layout="total, sizes, prev, pager, next, jumper"
                    v-on:size-change="onSizeChange"
                    v-on:current-change="onCurrentChange"
                    class="dc-el-pagination_ElPagination"
                  ></el-pagination>
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

import {validatenull} from '@/utils/validate'
import {listUserPage} from '@/api/admin/user'
/** 根据用户界面配置import组件 结束 */

/** 根据用户界面配置import组件 结束 */
import {getTfamAssetManagerById, saveTfamAssetManager} from '@/api/assetting/tfamAssetManager'
import { treeDepartment } from '@/api/org/department.js'
import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'

export default {
  extends: BaseUI,
  name: 'tfamAssetManager-form',
  components: {
    /** 根据用户界面配置组件 开始 */

    /** 根据用户界面配置组件 结束 */

    OperationIcon
  },
  data() {
    return {
      formData: {
        id:undefined,
        userid: undefined,
        companyId:undefined,
      },
      /** 根据用户界面配置生成data数据 开始 */
      editFormData: this.initEditData(),
      // 对话框属性变量
      dialogProps: {
        visible: false,
        title: '卡片管理'
      },
      search: {
        // 查询条件   业务表设置的筛选条件
        params: [],

        offset: 0,
        limit: 10,
        columnName: '', // 排序字段名
        order: '' // 排序
      },
      // 分页属性
      tableDataPage: 1,
      tableDataTotal: 0,
      tableData: [],
      currentRow:null,
      dialogTitle: '卡片管理',
      // 选项变量
      // 管理员用户ID选项
      useridOptions: [],
      table118CurrentRow: {}, // 当前行
      // null选项
      departmentOptions: [],
      table155CurrentRow: {}, // 当前行
      // null选项
      cascaderOptions: [],
      tableColumns: {
        126: {
          title: '公司.全称',
          field: 'company.fullName',
          visible: false,
          order: 0
        },
        127: {
          title: '公司.编号',
          field: 'company.code',
          visible: false,
          order: 1
        },
        128: {
          title: '公司.名称',
          field: 'company.name',
          visible: false,
          order: 2
        },
        129: {
          title: '部门.编号',
          field: 'department.code',
          visible: false,
          order: 3
        },
        130: {
          title: '部门.名称',
          field: 'department.name',
          visible: false,
          order: 4
        },
        131: {
          title: '用户名',
          field: 'name',
          visible: true,
          order: 5
        },
        132: {
          title: '登录账号',
          field: 'loginName',
          visible: false,
          order: 6
        },
        133: {
          title: '密码',
          field: 'loginPassword',
          visible: false,
          order: 7
        },
        134: {
          title: '禁用',
          field: 'isLocked',
          visible: true,
          order: 8
        },
        135: {
          title: '手机号',
          field: 'phone',
          visible: false,
          order: 9
        },
        139: {
          title: '备注信息',
          field: 'remarks',
          visible: true,
          order: 11
        },
        136: {
          title: '邮箱地址',
          field: 'email',
          visible: false,
          order: 12
        }
      },
      queryFormData: {
        // 部门
        department: {
          id: null,
          name: null,
          code: null,
          ids: null
        },
        name: '' // 用户名
      },
      tabName: 'editForm', // tab标签页
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
    handleCurrentChange(val){
      this.currentRow = val;
    },
    onSearch() {
        this.$refs['queryForm'].validate((valid) => {
          if (valid) {
            this.search.offset = 0
            this.tableDataPage = 1
            this.getList()
          } else {
            return false
          }
        })
    },
    onCurrentChange(val) {
      this.search.offset = (val - 1) * this.search.limit
      this.tableDataPage = val
      this.getList()
    },
    onSizeChange(val) {
      this.tableDataPage = 1
      this.search.limit = val
      this.search.offset = (this.tableDataPage - 1) * val
      this.getList()
    },
    listDepartmentOptions() {
      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [
        {
          columnName: 'company_id',
          queryType: '=',
          value:currentUser.company.id
        }
      ])


      treeDepartment(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.departmentOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },
    getList() {
      this.setLoad()
      /* 查询参数 和数据权限 */
      this.search.params = []


      this.search.params.push({
        columnName: 'company_id',
        queryType: '=',
        value: currentUser.company.id
      })
      this.search.params.push({
        columnName: 'department_id',
        queryType: '=',
        value: !validatenull(this.queryFormData.department.id) ? this.queryFormData.department.id : null
      })

      this.search.params.push({
        columnName: 'name',
        queryType: 'like',
        value: !validatenull(this.queryFormData.name) ? this.queryFormData.name : null
      })

      listUserPage(this.search)
        .then((responseData) => {
          if (responseData.code == 100) {
            debugger
            this.tableDataTotal = responseData.data.total
            this.tableData = responseData.data.rows ? responseData.data.rows : []
          } else {
            this.showMessage(responseData)
          }
          this.resetLoad()
        })
        .catch((error) => {
          this.outputError(error)
        })
    },
    onSubmit() {
      debugger
      this.formData.userid=this.currentRow
      this.formData.companyId=currentUser.company.id
      saveTfamAssetManager(this.formData)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.dialogProps.visible = false
            this.showMessage({
              type: 'success',
              msg: '保存成功'
            })
            this.$emit("onGetData", );
            this.dialogVisible = false;
          } else {
            this.showMessage(responseData)
          }
          this.resetLoad()
        })
        .catch((error) => {
          this.outputError(error)
        })
    },
    doSave() {
      this.setLoad()

      saveTfamAssetManager(this.editFormData)
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
        getTfamAssetManagerById(id)
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
      this.getList()
    },


    initOptions(This) {
      // 初始化自定义类型选择框选项变量


    },
    onDeleteChild(tableData, index) {
      // 根据下标删除子表数据
      tableData.splice(index, 1)
    },
    initEditData(This) {
      return {
        userid: {
          id: null,
          name: null
        },
        createTime: '', // 创建时间
        companyId: '', // 租户ID
        remarks: '', // 备注信息
        assettingSysUserList: [],
        assettingOrgDepartmentList: []
      }
    },

    onAddAssettingSysUserList() {
      this.editFormData.assettingSysUserList.push({
        companyId: '', // 公司

        department: {
          id: null,

          name: null
        },

        name: '', // 名称

        loginName: '', // 登录账号

        loginPassword: '', // 密码

        isLocked: '0', // 禁用

        phone: '', // 手机号

        email: '', // 邮箱地址

        remarks: '' // 备注信息
      })
    },

    onAddAssettingOrgDepartmentList() {
      this.editFormData.assettingOrgDepartmentList.push({
        companyId: '', // 公司

        code: '', // 编号

        parent: '', // 父级

        name: '', // 名称

        director: '', // 总监

        manager: '', // 经理

        remarks: '' // 备注信息
      })
    }
  },
  watch: {
    tabName(val, oldVal) {
      this.$nextTick(() => {
        if (this.$refs[val] && this.$refs[val].doLayout) {
          this.$refs[val].doLayout()
        }
      })
    }
  },
  mounted: function () {
    this.$nextTick(() => {
      this.$on('openAddDialog', function (parent) {
        this.action = 'add'
        this.dialogProps.title = `添加${this.dialogTitle}`
        this.listDepartmentOptions()
        this.getList()
        this.tabName = 'add'
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
