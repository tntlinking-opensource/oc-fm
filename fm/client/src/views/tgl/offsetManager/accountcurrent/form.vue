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
        <el-tabs v-model="tabName" type="border-card" tab-position="top" class="dc-el-tabs_ElTabs">
          <el-tab-pane label="往来初始化表" name="editForm" class="dc-el-tab-pane_ElTabPane">
            <el-row v-if="action != 'view'" gutter="0" type="flex" justify="end" align="top" class="dc-el-row_ElRow">
              <el-tooltip disabled class="dc-el-tooltip_Button">
                <el-button
                  type="primary"
                  icon="el-icon-plus"
                  v-on:click="onAddTglAcIni1List"
                  class="dc-el-button_Button"
                ></el-button>
              </el-tooltip>
            </el-row>
            <el-form
              ref="tglAcIni1Form"
              :model="editFormData"
              label-width="100px"
              :disabled="action == 'view'"
              class="dc-el-form_ElEditForm"
            >
              <el-row>
                <ux-grid
                  :data="editFormData"
                  ref="tglAcIni1Table"
                  :currentRow="table134CurrentRow"
                  height="200px"
                  border
                  :edit-config="{ trigger: 'click', mode: 'cell' }"
                  class="dc-ux-grid_EditTable"
                >
                  <ux-table-column
                    field="iniid"
                    title="编号"
                    tree-node
                    resizable
                    min-width="120px"
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
                        prop="iniid"
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.iniid"
                          :maxLength="40"
                          placeholder="请输入往来初始化明细ID"
                          clearable
                          disabled
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          prop="iniid"
                          label-width="0px"
                          disabled
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.iniid }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field=""
                    title="业务日期"
                    tree-node
                    resizable
                    align="left"
                    header-align="center"
                    show-overflow
                    :edit-render="{ autofocus: '.el-input__inner' }"
                    class="dc-ux-table-column_PickerTimeDate"
                  >
                    <template v-slot:header="{ column }">
                      {{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        prop="busdate"
                        label-width="0px"
                        class="dc-el-form-item_PickerTimeDate"
                      >
                        <el-date-picker
                          v-model="row.busdate"
                          :style="{ width: '100%' }"
                          placeholder="请选择"
                          type="datetime"
                          format="yyyy-MM-dd HH:mm:ss"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          class="dc-el-date-picker_PickerTimeDate"
                        ></el-date-picker>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          prop="busdate"
                          label-width="0px"
                          class="dc-el-form-item_PickerTimeDate"
                        >
                          {{row.busdate }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="varabstract"
                    title="摘要"
                    tree-node
                    resizable
                    min-width="120px"
                    align="left"
                    header-align="center"
                    show-overflow
                    :edit-render="{ autofocus: '.el-input__inner' }"
                    class="dc-ux-table-column_SingleInput"
                  >
                    <template v-slot:header="{ column }">
                      {{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        prop="varabstract"
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.varabstract"
                          :maxLength="500"
                          placeholder="请输入摘要"
                          clearable
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          prop="varabstract"
                          label-width="0px"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.varabstract }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="ledger"
                    title="分户ID"
                    tree-node
                    resizable
                    min-width="120px"
                    align="left"
                    header-align="center"
                    show-overflow
                    :edit-render="{ autofocus: '.el-input__inner' }"
                    class="dc-ux-table-column_SingleInput"
                  >
                    <template v-slot:header="{ column }">
                      {{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        prop="ledger"
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.uqledgeid"
                          :maxLength="40"
                          placeholder="请输入分户ID"
                          clearable
                          class="dc-el-input_SingleInput"
                          @focus="$refs.ledgetypeQuery.open()"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          prop="ledger"
                          label-width="0px"
                          class="dc-el-form-item_SingleInput"
                          @focus="$refs.ledgetypeQuery.open()"
                        >
                          {{ row.uqledgeid }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="mnydebit"
                    title="借方金额"
                    tree-node
                    resizable
                    min-width="100px"
                    align="left"
                    header-align="center"
                    show-overflow
                    :edit-render="{ autofocus: '.el-input__inner' }"
                    class="dc-ux-table-column_NumberInput"
                  >
                    <template v-slot:header="{ column }">
                      {{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        prop="mnydebit"
                        label-width="0px"
                        class="dc-el-form-item_NumberInput"
                      >
                        <number-input
                          v-model="row.mnydebit"
                          :min="0"
                          :max="999999999999.99"
                          :precision="2"
                          placeholder="请输入借方金额"
                          clearable
                          class="dc-number-input_NumberInput"
                        ></number-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          prop="mnydebit"
                          label-width="0px"
                          class="dc-el-form-item_NumberInput"
                        >
                          {{
                            row.mnydebit
                              ? '' +
                                '' +
                                Number(row.mnydebit)
                                  .toFixed(2)
                                  .replace(/(\d)(?=(\d{3})+\.)/g, '$1,') +
                                ''
                              : null
                          }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="mnycredit"
                    title="贷方金额"
                    tree-node
                    resizable
                    min-width="100px"
                    align="left"
                    header-align="center"
                    show-overflow
                    :edit-render="{ autofocus: '.el-input__inner' }"
                    class="dc-ux-table-column_NumberInput"
                  >
                    <template v-slot:header="{ column }">
                      {{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        prop="mnycredit"
                        label-width="0px"
                        class="dc-el-form-item_NumberInput"
                      >
                        <number-input
                          v-model="row.mnycredit"
                          :min="0"
                          :max="999999999999.99"
                          :precision="2"
                          placeholder="请输入贷方金额"
                          clearable
                          class="dc-number-input_NumberInput"
                        ></number-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          prop="mnycredit"
                          label-width="0px"
                          class="dc-el-form-item_NumberInput"
                        >
                          {{
                            row.mnycredit
                              ? '' +
                                '' +
                                Number(row.mnycredit)
                                  .toFixed(2)
                                  .replace(/(\d)(?=(\d{3})+\.)/g, '$1,') +
                                ''
                              : null
                          }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="inttype"
                    title="类型"
                    tree-node
                    resizable
                    min-width="100px"
                    align="left"
                    header-align="center"
                    show-overflow
                    :edit-render="{ autofocus: '.el-input__inner' }"
                    class="dc-ux-table-column_NumberInput"
                  >
                    <template v-slot:header="{ column }">
                      <span style="color: #f56c6c"></span>
                      {{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        prop="inttype"
                        label-width="0px"
                        class="dc-el-form-item_NumberInput"
                      >
                        <el-input
                          v-model="row.inttype"
                          clearable
                          disabled
                          class="dc-number-input_NumberInput"
                        >{{
                          row.inttype==""? "":(row.inttype==1? "挂账":"冲销") }}</el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          prop="inttype"
                          label-width="0px"
                          disabled
                          class="dc-el-form-item_NumberInput"
                        >
                          {{
                            row.inttype==null? "":(row.inttype==1? "挂账":"冲销")

                          }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    title="操作"
                    tree-node
                    resizable
                    width="160px"
                    min-width="80px"
                    fixed="right"
                    align="left"
                    header-align="center"
                    v-if="action != 'view'"
                    class="dc-ux-table-column_ElTableOptColumn"
                  >
                    <template v-slot:header="scope">
                      <span>操作</span>
                    </template>
                    <template slot-scope="scope">
                      <OperationIcon
                        v-on:click="onDeleteChild(editFormData, scope.rowIndex)"
                        type="danger"
                        content="删除"
                        placement="top"
                        icon-name="el-icon-delete"
                        class="dc-OperationIcon_IconButton"
                      ></OperationIcon>
                    </template>
                  </ux-table-column>
                </ux-grid>
              </el-row>
            </el-form>
          </el-tab-pane>
        </el-tabs>
      </el-row>
      <span slot="footer" class="dialog-footer">
        <el-button v-on:click="onSubmit" type="primary" v-show="action != 'view'">保存</el-button>
        <el-button v-on:click="onDialogClose" v-if="action != 'view'">取消</el-button>
        <el-button v-on:click="onDialogClose" v-if="action == 'view'">关闭</el-button>
      </span>
    </el-dialog>
    <LedgetypeQuery ref="ledgetypeQuery" @onGetTreeData="onGetTreeData" />
  </el-row>
</template>
<script>
  import LedgetypeQuery from "@/views/components/LedgetypeQuery";
  import { validatenull } from '@/utils/validate'
  /** 根据用户界面配置import组件 结束 */

  /** 根据用户界面配置import组件 结束 */
  import { getTglAcIniById, saveTglAcIni } from '@/api/acini/tglAcIni'
  import BaseUI from '@/views/components/baseUI'
  import OperationIcon from '@/components/OperationIcon'
  export default {
    extends: BaseUI,
    name: 'tglAcIni-form',
    components: {
      /** 根据用户界面配置组件 开始 */

      /** 根据用户界面配置组件 结束 */
      OperationIcon,
      LedgetypeQuery
    },
    data() {
      return {
        /** 根据用户界面配置生成data数据 开始 */

        editFormData: [],
        // 对话框属性变量
        dialogProps: {
          visible: false,
          title: '往来初始化表'
        },
        uqaccountid:"",
        dialogTitle: '往来初始化表',
        // 选项变量
        table134CurrentRow: {}, // 当前行
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
      onGetTreeData(data) {
        this.table134CurrentRow.UQLEDGETYPEID = data.parentIds.toString();
        this.table134CurrentRow.ledger = data.arrData.toString();
        this.table134CurrentRow.UQLEDGEID = data.arrName.toString();
      },
      onSubmit() {
          this.doSave()
      },
      doSave() {
        this.setLoad()
        saveTglAcIni(this.editFormData)
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
      getFormById(id) {
        this.setLoad()
          getTglAcIniById(id)
            .then((responseData) => {

              if (responseData.code == 100) {
                this.editFormData = responseData.data.rows
                for (let i = 0; i < this.editFormData.length; i++) {
                  this.editFormData[i].uqaccountid = this.uqaccountid
                }
              } else {
                this.showMessage(responseData)
              }
              this.resetLoad()
            })
            .catch((error) => {
              this.outputError(error)
            })

      },
      onDialogClose() {
        this.dialogProps.visible = false
      },
      onDialogOpen() {
        this.editFormData = [];
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
          iniid: '', // 往来初始化明细ID
          uqaccountid: this.uqaccountid, // 科目ID
          uqcompanyid: '', // 公司ID
          uqledgetypeid: '', // 分户类别ID
          uqledgeid: '', // 分户ID
          varabstract: '', // 摘要
          mnydebit: null, // 借方金额
          mnycredit: null, // 贷方金额
          uquserid: '', // 用户ID
          busdate: '', // 业务日期
          inttype: null, // 类型：1：挂账。2：冲销
          tenantId: '', // tenant_id
        }
      },

      onAddTglAcIni1List() {
        this.editFormData.push({
          iniid: '', // 往来初始化明细ID
          uqaccountid: this.uqaccountid, // 科目ID
          uqcompanyid: '', // 公司ID
          uqledgetypeid: '', // 分户类别ID
          uqledgeid: '', // 分户ID
          varabstract: '', // 摘要
          mnydebit: null, // 借方金额
          mnycredit: null, // 贷方金额
          uquserid: '', // 用户ID
          busdate: '', // 业务日期
          inttype: null, // 类型：1：挂账。2：冲销
          tenantId: '', // tenant_id
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
    'table134CurrentRow.row.mnydebit': {
      handler: function (val, oldVal) {
        this.$nextTick(() => {
          if (val > 0) {
            this.row.inttype = 2
          } else if (val < 0) {
            this.row.inttype = 1
          }

        })
      },
      deep: true
    },
    'table134CurrentRow.row.mnycredit': {
      handler: function (val, oldVal) {
        this.$nextTick(() => {
          if (val > 0) {
            this.row.inttype = 1
          } else if (val < 0) {
            this.row.inttype = 2
          }
        })
      },
      deep: true
    },
    mounted: function () {
      this.$nextTick(() => {
        this.$on('openEditDialog', async function (id) {
          this.action = 'edit'
          this.dialogProps.title = `修改${this.dialogTitle}`
          this.uqaccountid=id;
          this.getFormById(id);
          this.tabName = 'editForm'
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
