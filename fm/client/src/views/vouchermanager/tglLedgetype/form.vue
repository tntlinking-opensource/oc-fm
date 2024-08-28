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
        <el-tabs
          v-model="tabName"
          type="border-card"
          tab-position="top"
          class="dc-el-tabs_ElTabs"
        >
          <el-tab-pane
            label="分户类别表"
            name="editForm"
            class="dc-el-tab-pane_ElTabPane"
          >
            <el-form
              ref="editForm"
              :model="editFormData"
              label-width="100px"
              :disabled="action == 'view'"
              class="dc-el-form_ElEditForm"
            >
              <el-row>
                <el-form-item
                  v-if="false"
                  prop="uqledgetypeid"
                  label="分户类别ID"
                  class="dc-el-form-item_SingleInput"
                >
                  <el-input
                    v-model="editFormData.uqledgetypeid"
                    :maxLength="40"
                    placeholder="请输入分户类别ID"
                    clearable
                    autocomplete="new-password"
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
                <el-form-item
                  v-if="false"
                  prop="varledgetypecode"
                  label="分户类别编码"
                  class="dc-el-form-item_SingleInput"
                >
                  <el-input
                    v-model="editFormData.varledgetypecode"
                    :maxLength="100"
                    placeholder="请输入分户类别编码"
                    clearable
                    autocomplete="new-password"
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
                <el-form-item
                  prop="varledgetypename"
                  label="分户类别名称"
                  class="dc-el-form-item_SingleInput"
                >
                  <el-input
                    v-model="editFormData.varledgetypename"
                    :maxLength="100"
                    placeholder="请输入分户类别名称"
                    clearable
                    autocomplete="new-password"
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
                <el-form-item
                  v-if="false"
                  prop="uqparentid"
                  label="上级ID"
                  class="dc-el-form-item_SingleInput"
                >
                  <el-input
                    v-model="editFormData.uqparentid"
                    :maxLength="40"
                    placeholder="请输入上级ID"
                    clearable
                    autocomplete="new-password"
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
                <el-form-item
                  v-if="false"
                  prop="intislastlevel"
                  label="是否末级"
                  class="dc-el-form-item_NumberInput"
                >
                  <number-input
                    v-model="editFormData.intislastlevel"
                    :min="0"
                    :max="99999999999"
                    :precision="0"
                    placeholder="请输入是否末级"
                    clearable
                    class="dc-number-input_NumberInput"
                  ></number-input>
                </el-form-item>
                <el-form-item
                  v-if="false"
                  prop="intstatus"
                  label="状态"
                  class="dc-el-form-item_NumberInput"
                >
                  <number-input
                    v-model="editFormData.intstatus"
                    :min="0"
                    :max="99999999999"
                    :precision="0"
                    placeholder="请输入状态"
                    clearable
                    class="dc-number-input_NumberInput"
                  ></number-input>
                </el-form-item>
                <el-form-item
                  v-if="false"
                  prop="tenantId"
                  label="tenant_id"
                  :rules="[
                    {
                      required: true,
                      message: 'tenant_id不能为空',
                      trigger: 'blur'
                    }
                  ]"
                  class="dc-el-form-item_SingleInput"
                >
                  <el-input
                    v-model="editFormData.tenantId"
                    :maxLength="100"
                    placeholder="请输入tenant_id"
                    clearable
                    autocomplete="new-password"
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </el-row>
            </el-form>
          </el-tab-pane>
          <el-tab-pane
            label="分户明细表"
            name="tglLedgerTable"
            class="dc-el-tab-pane_ElTabPane"
          >
            <el-row
              v-if="action != 'view'"
              gutter="0"
              type="flex"
              justify="end"
              align="top"
              class="dc-el-row_ElRow"
            >
              <el-tooltip disabled class="dc-el-tooltip_Button">
                <el-button
                  type="primary"
                  icon="el-icon-plus"
                  v-on:click="onAddTglLedgerList"
                  class="dc-el-button_Button"
                ></el-button>
              </el-tooltip>
            </el-row>
            <el-form
              ref="tglLedgerForm"
              :model="editFormData"
              label-width="100px"
              :disabled="action == 'view'"
              class="dc-el-form_ElEditForm"
            >
              <el-row>
                <ux-grid
                  :data="editFormData.tglLedgerList"
                  ref="tglLedgerTable"
                  :currentRow="table124CurrentRow"
                  height="200px"
                  border
                  :edit-config="{ trigger: 'click', mode: 'cell' }"
                  class="dc-ux-grid_EditTable"
                >
                  <ux-table-column
                    v-if="false"
                    field="uqledgeid"
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
                      <span style="color: #f56c6c">*</span>
                      {{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        :prop="'tglLedgerList.' + rowIndex + '.uqledgeid'"
                        label-width="0px"
                        :rules="[
                          {
                            required: true,
                            message: '分户ID不能为空',
                            trigger: 'blur'
                          }
                        ]"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.uqledgeid"
                          :maxLength="40"
                          placeholder="请输入分户ID"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglLedgerList.' + rowIndex + '.uqledgeid'"
                          label-width="0px"
                          :rules="[
                            {
                              required: true,
                              message: '分户ID不能为空',
                              trigger: 'blur'
                            }
                          ]"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.uqledgeid }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="varledgecode"
                    title="分户编码"
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
                        :prop="'tglLedgerList.' + rowIndex + '.varledgecode'"
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.varledgecode"
                          :maxLength="100"
                          placeholder="请输入分户编码"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglLedgerList.' + rowIndex + '.varledgecode'"
                          label-width="0px"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.varledgecode }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="varledgename"
                    title="分户名称"
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
                        :prop="'tglLedgerList.' + rowIndex + '.varledgename'"
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.varledgename"
                          :maxLength="100"
                          placeholder="请输入分户名称"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglLedgerList.' + rowIndex + '.varledgename'"
                          label-width="0px"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.varledgename }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    v-if="false"
                    field="varledgefullcode"
                    title="分户全编码"
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
                        :prop="
                          'tglLedgerList.' + rowIndex + '.varledgefullcode'
                        "
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.varledgefullcode"
                          :maxLength="250"
                          placeholder="请输入分户全编码"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="
                            'tglLedgerList.' + rowIndex + '.varledgefullcode'
                          "
                          label-width="0px"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.varledgefullcode }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    v-if="false"
                    field="varledgefullname"
                    title="分户全名称"
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
                        :prop="
                          'tglLedgerList.' + rowIndex + '.varledgefullname'
                        "
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.varledgefullname"
                          :maxLength="1000"
                          placeholder="请输入分户全名称"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="
                            'tglLedgerList.' + rowIndex + '.varledgefullname'
                          "
                          label-width="0px"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.varledgefullname }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    v-if="false"
                    field="uqparentid"
                    title="上级ID"
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
                        :prop="'tglLedgerList.' + rowIndex + '.uqparentid'"
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.uqparentid"
                          :maxLength="40"
                          placeholder="请输入上级ID"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglLedgerList.' + rowIndex + '.uqparentid'"
                          label-width="0px"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.uqparentid }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    v-if="false"
                    field="intlevel"
                    title="分户等级"
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
                        :prop="'tglLedgerList.' + rowIndex + '.intlevel'"
                        label-width="0px"
                        class="dc-el-form-item_NumberInput"
                      >
                        <number-input
                          v-model="row.intlevel"
                          :min="0"
                          :max="99999999999"
                          :precision="0"
                          placeholder="请输入分户等级"
                          clearable
                          class="dc-number-input_NumberInput"
                        ></number-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglLedgerList.' + rowIndex + '.intlevel'"
                          label-width="0px"
                          class="dc-el-form-item_NumberInput"
                        >
                          {{
                            row.intlevel
                              ? "" +
                                "" +
                                Number(row.intlevel)
                                  .toFixed(0)
                                  .replace(/(\d)(?=(\d{3})+\.)/g, "$1,") +
                                ""
                              : null
                          }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    v-if="false"
                    field="intislastlevel"
                    title="是否末级"
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
                        :prop="'tglLedgerList.' + rowIndex + '.intislastlevel'"
                        label-width="0px"
                        class="dc-el-form-item_NumberInput"
                      >
                        <number-input
                          v-model="row.intislastlevel"
                          :min="0"
                          :max="99999999999"
                          :precision="0"
                          placeholder="请输入是否末级"
                          clearable
                          class="dc-number-input_NumberInput"
                        ></number-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="
                            'tglLedgerList.' + rowIndex + '.intislastlevel'
                          "
                          label-width="0px"
                          class="dc-el-form-item_NumberInput"
                        >
                          {{
                            row.intislastlevel
                              ? "" +
                                "" +
                                Number(row.intislastlevel)
                                  .toFixed(0)
                                  .replace(/(\d)(?=(\d{3})+\.)/g, "$1,") +
                                ""
                              : null
                          }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    v-if="false"
                    field="intstatus"
                    title="状态"
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
                        :prop="'tglLedgerList.' + rowIndex + '.intstatus'"
                        label-width="0px"
                        class="dc-el-form-item_NumberInput"
                      >
                        <number-input
                          v-model="row.intstatus"
                          :min="0"
                          :max="99999999999"
                          :precision="0"
                          placeholder="请输入状态"
                          clearable
                          class="dc-number-input_NumberInput"
                        ></number-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglLedgerList.' + rowIndex + '.intstatus'"
                          label-width="0px"
                          class="dc-el-form-item_NumberInput"
                        >
                          {{
                            row.intstatus
                              ? "" +
                                "" +
                                Number(row.intstatus)
                                  .toFixed(0)
                                  .replace(/(\d)(?=(\d{3})+\.)/g, "$1,") +
                                ""
                              : null
                          }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    v-if="false"
                    field="tenantId"
                    title="tenant_id"
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
                        :prop="'tglLedgerList.' + rowIndex + '.tenantId'"
                        label-width="0px"
                        :rules="[
                          {
                            required: true,
                            message: 'tenant_id不能为空',
                            trigger: 'blur'
                          }
                        ]"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.tenantId"
                          :maxLength="100"
                          placeholder="请输入tenant_id"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglLedgerList.' + rowIndex + '.tenantId'"
                          label-width="0px"
                          :rules="[
                            {
                              required: true,
                              message: 'tenant_id不能为空',
                              trigger: 'blur'
                            }
                          ]"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.tenantId }}
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
                        v-on:click="
                          onDeleteChild(
                            editFormData.tglLedgerList,
                            scope.rowIndex
                          )
                        "
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
        <el-button
          v-on:click="onSubmit"
          type="primary"
          v-show="action != 'view'"
          >保存</el-button
        >
        <el-button v-on:click="onDialogClose" v-if="action != 'view'"
          >取消</el-button
        >
        <el-button v-on:click="onDialogClose" v-if="action == 'view'"
          >关闭</el-button
        >
      </span>
    </el-dialog>
  </el-row>
</template>
<script>
import { validatenull } from "@/utils/validate";
/** 根据用户界面配置import组件 结束 */

/** 根据用户界面配置import组件 结束 */
import {
  getTglLedgetypeById,
  saveTglLedgetype
} from "@/api/vouchermanager/tglLedgetype";
import BaseUI from "@/views/components/baseUI";
import OperationIcon from "@/components/OperationIcon";
export default {
  extends: BaseUI,
  name: "tglLedgetype-form",
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
        title: "分户"
      },
      dialogTitle: "分户",
      // 选项变量
      table124CurrentRow: {}, // 当前行
      tabName: "editForm", // tab标签页
      // 窗口操作类型 view/edit/add
      action: ""
    };
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
      let validFlag = true;

      this.$refs["tglLedgerForm"].validate(valid => {
        if (valid) {
        } else {
          this.tabName = "tglLedgerTable";
          validFlag = false;
          return false;
        }
      });

      this.$refs["editForm"].validate(valid => {
        if (valid) {
        } else {
          this.tabName = "editForm";
          validFlag = false;
          return false;
        }
      });
      if (validFlag) {
        this.doSave();
      }
    },
    doSave() {
      this.setLoad();

      saveTglLedgetype(this.editFormData)
        .then(responseData => {
          if (responseData.code == 100) {
            this.dialogProps.visible = false;
            this.showMessage({
              type: "success",
              msg: "保存成功"
            });
            this.$emit("save-finished");
          } else {
            this.showMessage(responseData);
          }
          this.resetLoad();
        })
        .catch(error => {
          this.outputError(error);
        });
    },
    switchEdit() {
      this.action = "edit";
      this.dialogProps.title = `修改${this.dialogTitle}`;
      this.initOptions(this.editFormData);
      
      let defaultIndex = 0;
      let value = "1";
      let genderColumns = [
        { label: "男", value: "1" },
        { label: "女", value: "0" }
      ];
      for (let i = 0; i < genderColumns.length; i++) {
        if (item.value == value) {
          defaultIndex = i;
          break;
        }
      }
    },

    getFormById(id) {
      this.setLoad();
      return new Promise(resolve => {
        getTglLedgetypeById(id)
          .then(responseData => {
            let form = {};
            if (responseData.code == 100) {
              form = responseData.data;
            } else {
              this.showMessage(responseData);
            }
            this.resetLoad();
            resolve(form);
          })
          .catch(error => {
            this.outputError(error);
          });
      });
    },
    onDialogClose() {
      this.dialogProps.visible = false;
    },
    onDialogOpen() {
      this.$nextTick(() => {
        this.$refs["editForm"].clearValidate();
      });
    },

    initOptions(This) {
      // 初始化自定义类型选择框选项变量
    },
    onDeleteChild(tableData, index) {
      // 根据下标删除子表数据
      tableData.splice(index, 1);
    },
    initEditData(This) {
      return {
        uqledgetypeid: "", // 分户类别ID
        varledgetypecode: "", // 分户类别编码
        varledgetypename: "", // 分户类别名称
        uqparentid: "", // 上级ID
        intislastlevel: null, // 是否末级
        intstatus: null, // 状态
        tenantId: "", // tenant_id
        tglLedgerList: []
      };
    },

    onAddTglLedgerList() {
      this.editFormData.tglLedgerList.push({
        uqledgeid: "", // 分户ID

        varledgecode: "", // 分户编码

        varledgename: "", // 分户名称

        varledgefullcode: "", // 分户全编码

        varledgefullname: "", // 分户全名称

        uqparentid: "", // 上级ID

        intlevel: null, // 分户等级

        intislastlevel: null, // 是否末级

        intstatus: null, // 状态

        tenantId: "" // tenant_id
      });
    }
  },
  watch: {
    tabName(val, oldVal) {
      this.$nextTick(() => {
        if (this.$refs[val] && this.$refs[val].doLayout) {
          this.$refs[val].doLayout();
        }
      });
    }
  },
  mounted: function() {
    this.$nextTick(() => {
      this.$on("openViewDialog", async function(id) {
        this.action = "view";
        this.dialogProps.title = `查看${this.dialogTitle}`;
        this.editFormData = {
          ...this.initEditData(),
          ...(await this.getFormById(id))
        };
        this.initOptions(this.editFormData);
        this.tabName = "editForm";
        this.dialogProps.visible = true;
      });

      this.$on("openEditDialog", async function(id) {
        this.action = "edit";
        this.dialogProps.title = `修改${this.dialogTitle}`;
        this.editFormData = {
          ...this.initEditData(),
          ...(await this.getFormById(id))
        };
        this.initOptions(this.editFormData);
        this.tabName = "editForm";
        this.dialogProps.visible = true;
      });
      this.$on("openAddDialog", function(parent) {
        this.action = "add";
        this.dialogProps.title = `添加${this.dialogTitle}`;
        this.editFormData = {
          ...this.initEditData(),
          ...parent
        };
        this.initOptions(this.editFormData);
        this.tabName = "editForm";
        this.dialogProps.visible = true;
      });
      this.$on("openCopyDialog", async function(id) {
        this.action = "add";
        this.dialogProps.title = `添加${this.dialogTitle}`;
        this.editFormData = {
          ...this.initEditData(),
          ...(await this.getFormById(id))
        };
        this.initOptions(this.editFormData);
        this.tabName = "editForm";
        this.editFormData.id = null; //把id设置为空，添加一个新的

        for (var i = 0; i <= this.editFormData.tglLedgerList.length - 1; i++) {
          this.editFormData.tglLedgerList[i].id = null;
        }

        this.dialogProps.visible = true;
      });
    });
  }
};
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
