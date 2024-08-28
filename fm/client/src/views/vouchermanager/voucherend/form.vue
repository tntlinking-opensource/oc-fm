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
          <el-tab-pane label="凭证主表" name="editForm" class="dc-el-tab-pane_ElTabPane">
            <el-form
              ref="editForm"
              :model="editFormData"
              label-width="100px"
              :disabled="action == 'view'"
              class="dc-el-form_ElEditForm"
            >
              <el-row>
                <el-col :span="6">
                  <el-form-item
                    prop="uqvoucherid"
                    label="凭证ID"
                    :rules="[{ required: true, message: '凭证ID不能为空', trigger: 'blur' }]"
                    class="dc-el-form-item_SingleInput"
                  >
                    <el-input
                      v-model="editFormData.uqvoucherid"
                      :maxLength="40"
                      placeholder="请输入凭证ID"
                      clearable
                      autocomplete="new-password"
                      class="dc-el-input_SingleInput"
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item prop="intvouchernum" label="凭证编码" class="dc-el-form-item_SingleInput">
                    <el-input
                      v-model="editFormData.intvouchernum"
                      :maxLength="40"
                      placeholder="请输入凭证编码"
                      clearable
                      autocomplete="new-password"
                      class="dc-el-input_SingleInput"
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="uqnumbering"
                    label="凭证类型"
                    :rules="[{ required: true, message: '凭证类型不能为空', trigger: 'blur' }]"
                    class="dc-el-form-item_SingleInput"
                  >
                    <el-input
                      v-model="editFormData.uqnumbering"
                      :maxLength="40"
                      placeholder="请输入凭证类型"
                      clearable
                      autocomplete="new-password"
                      class="dc-el-input_SingleInput"
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="uqcompanyid"
                    label="凭证核算单位"
                    :rules="[{ required: true, message: '凭证核算单位不能为空', trigger: 'blur' }]"
                    class="dc-el-form-item_SingleInput"
                  >
                    <el-input
                      v-model="editFormData.uqcompanyid"
                      :maxLength="40"
                      placeholder="请输入凭证核算单位"
                      clearable
                      autocomplete="new-password"
                      class="dc-el-input_SingleInput"
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item prop="intcompanyseq" label="流水号" class="dc-el-form-item_NumberInput">
                    <number-input
                      v-model="editFormData.intcompanyseq"
                      :min="0"
                      :max="99999999999"
                      :precision="0"
                      placeholder="请输入流水号"
                      clearable
                      class="dc-number-input_NumberInput"
                    ></number-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="intaffix"
                    label="单据数"
                    :rules="[{ required: true, message: '单据数不能为空', trigger: 'blur' }]"
                    class="dc-el-form-item_NumberInput"
                  >
                    <number-input
                      v-model="editFormData.intaffix"
                      :min="0"
                      :max="99999999999"
                      :precision="0"
                      placeholder="请输入单据数"
                      clearable
                      class="dc-number-input_NumberInput"
                    ></number-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="uqglobalperiodid.UQGLOBALPERIODID"
                    label="会计期"
                    :rules="[{ required: true, message: '会计期不能为空', trigger: 'change' }]"
                    class="dc-el-form-item_SelectInput"
                  >
                    <el-select
                      v-model="editFormData.uqglobalperiodid"
                      :style="{ width: '100%' }"
                      placeholder="请选择会计期"
                      clearable
                      value-key="UQGLOBALPERIODID"
                      filterable
                      class="dc-el-select_SelectInput"
                    >
                      <el-option
                        v-for="(item, index) in uqglobalperiodidOptions"
                        :key="index"
                        :label="item.label"
                        :value="item"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="mnydebitsum"
                    label="借方金额合计"
                    :rules="[{ required: true, message: '借方金额合计不能为空', trigger: 'blur' }]"
                    class="dc-el-form-item_NumberInput"
                  >
                    <number-input
                      v-model="editFormData.mnydebitsum"
                      :min="0"
                      :max="999999999999.99"
                      :precision="2"
                      placeholder="请输入借方金额合计"
                      clearable
                      class="dc-number-input_NumberInput"
                    ></number-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="mnycreditsum"
                    label="贷方金额合计"
                    :rules="[{ required: true, message: '贷方金额合计不能为空', trigger: 'blur' }]"
                    class="dc-el-form-item_NumberInput"
                  >
                    <number-input
                      v-model="editFormData.mnycreditsum"
                      :min="0"
                      :max="999999999999.99"
                      :precision="2"
                      placeholder="请输入贷方金额合计"
                      clearable
                      class="dc-number-input_NumberInput"
                    ></number-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item prop="uqfinacialmanagerid.id" label="会计主管" class="dc-el-form-item_SelectInput">
                    <el-select
                      v-model="editFormData.uqfinacialmanagerid"
                      :style="{ width: '100%' }"
                      placeholder="请选择会计主管"
                      clearable
                      value-key="id"
                      filterable
                      class="dc-el-select_SelectInput"
                    >
                      <el-option
                        v-for="(item, index) in uqfinacialmanageridOptions"
                        :key="index"
                        :label="item.name"
                        :value="item"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item prop="uqaccountantid.id" label="记账人" class="dc-el-form-item_SelectInput">
                    <el-select
                      v-model="editFormData.uqaccountantid"
                      :style="{ width: '100%' }"
                      placeholder="请选择记账人"
                      clearable
                      value-key="id"
                      filterable
                      class="dc-el-select_SelectInput"
                    >
                      <el-option
                        v-for="(item, index) in uqaccountantidOptions"
                        :key="index"
                        :label="item.name"
                        :value="item"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item prop="uqcasherid.id" label="出纳人" class="dc-el-form-item_SelectInput">
                    <el-select
                      v-model="editFormData.uqcasherid"
                      :style="{ width: '100%' }"
                      placeholder="请选择出纳人"
                      clearable
                      value-key="id"
                      filterable
                      class="dc-el-select_SelectInput"
                    >
                      <el-option
                        v-for="(item, index) in uqcasheridOptions"
                        :key="index"
                        :label="item.name"
                        :value="item"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item prop="uqcheckerid.id" label="审核人" class="dc-el-form-item_SelectInput">
                    <el-select
                      v-model="editFormData.uqcheckerid"
                      :style="{ width: '100%' }"
                      placeholder="请选择审核人"
                      clearable
                      value-key="id"
                      filterable
                      class="dc-el-select_SelectInput"
                    >
                      <el-option
                        v-for="(item, index) in uqcheckeridOptions"
                        :key="index"
                        :label="item.name"
                        :value="item"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item prop="uqfillerid.id" label="制单人" class="dc-el-form-item_SelectInput">
                    <el-select
                      v-model="editFormData.uqfillerid"
                      :style="{ width: '100%' }"
                      placeholder="请选择制单人"
                      clearable
                      value-key="id"
                      filterable
                      class="dc-el-select_SelectInput"
                    >
                      <el-option
                        v-for="(item, index) in uqfilleridOptions"
                        :key="index"
                        :label="item.name"
                        :value="item"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item prop="dtaccountant" label="记账日期" class="dc-el-form-item_SingleInput">
                    <el-input
                      v-model="editFormData.dtaccountant"
                      :maxLength="-1"
                      placeholder="请输入记账日期"
                      clearable
                      autocomplete="new-password"
                      class="dc-el-input_SingleInput"
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item prop="dtcasher" label="出纳日期" class="dc-el-form-item_SingleInput">
                    <el-input
                      v-model="editFormData.dtcasher"
                      :maxLength="-1"
                      placeholder="请输入出纳日期"
                      clearable
                      autocomplete="new-password"
                      class="dc-el-input_SingleInput"
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item prop="dtchecker" label="审核日期" class="dc-el-form-item_SingleInput">
                    <el-input
                      v-model="editFormData.dtchecker"
                      :maxLength="-1"
                      placeholder="请输入审核日期"
                      clearable
                      autocomplete="new-password"
                      class="dc-el-input_SingleInput"
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item prop="dtfiller" label="制单日期" class="dc-el-form-item_SingleInput">
                    <el-input
                      v-model="editFormData.dtfiller"
                      :maxLength="-1"
                      placeholder="请输入制单日期"
                      clearable
                      autocomplete="new-password"
                      class="dc-el-input_SingleInput"
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item prop="dtaccountantsrv" label="记账服务器时间" class="dc-el-form-item_SingleInput">
                    <el-input
                      v-model="editFormData.dtaccountantsrv"
                      :maxLength="-1"
                      placeholder="请输入记账服务器时间"
                      clearable
                      autocomplete="new-password"
                      class="dc-el-input_SingleInput"
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item prop="dtcashersrv" label="出纳服务器时间" class="dc-el-form-item_SingleInput">
                    <el-input
                      v-model="editFormData.dtcashersrv"
                      :maxLength="-1"
                      placeholder="请输入出纳服务器时间"
                      clearable
                      autocomplete="new-password"
                      class="dc-el-input_SingleInput"
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item prop="dtcheckersrv" label="审核服务器时间" class="dc-el-form-item_SingleInput">
                    <el-input
                      v-model="editFormData.dtcheckersrv"
                      :maxLength="-1"
                      placeholder="请输入审核服务器时间"
                      clearable
                      autocomplete="new-password"
                      class="dc-el-input_SingleInput"
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item prop="dtfillersrv" label="制单服务器时间" class="dc-el-form-item_SingleInput">
                    <el-input
                      v-model="editFormData.dtfillersrv"
                      :maxLength="-1"
                      placeholder="请输入制单服务器时间"
                      clearable
                      autocomplete="new-password"
                      class="dc-el-input_SingleInput"
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item prop="intflag" label="凭证标志" class="dc-el-form-item_NumberInput">
                    <number-input
                      v-model="editFormData.intflag"
                      :min="0"
                      :max="99999999999"
                      :precision="0"
                      placeholder="请输入凭证标志"
                      clearable
                      class="dc-number-input_NumberInput"
                    ></number-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item prop="intcashflag" label="出纳标志" class="dc-el-form-item_NumberInput">
                    <number-input
                      v-model="editFormData.intcashflag"
                      :min="0"
                      :max="99999999999"
                      :precision="0"
                      placeholder="请输入出纳标志"
                      clearable
                      class="dc-number-input_NumberInput"
                    ></number-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item prop="intdeleteflag" label="删除标志(0是正常 1是删除)" class="dc-el-form-item_NumberInput">
                    <number-input
                      v-model="editFormData.intdeleteflag"
                      :min="0"
                      :max="99999999999"
                      :precision="0"
                      placeholder="请输入删除标志(0是正常 1是删除)"
                      clearable
                      class="dc-number-input_NumberInput"
                    ></number-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item prop="uqcancelid" label="被冲销凭证ID" class="dc-el-form-item_SingleInput">
                    <el-input
                      v-model="editFormData.uqcancelid"
                      :maxLength="40"
                      placeholder="请输入被冲销凭证ID"
                      clearable
                      autocomplete="new-password"
                      class="dc-el-input_SingleInput"
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item prop="fromid" label="凭证接口所需字段" class="dc-el-form-item_SingleInput">
                    <el-input
                      v-model="editFormData.fromid"
                      :maxLength="40"
                      placeholder="请输入凭证接口所需字段"
                      clearable
                      autocomplete="new-password"
                      class="dc-el-input_SingleInput"
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="submitodd"
                    label="报账单号（接口调用）限用于区分暂存凭证 （0-保存，1-暂存）"
                    class="dc-el-form-item_SingleInput"
                  >
                    <el-input
                      v-model="editFormData.submitodd"
                      :maxLength="40"
                      placeholder="请输入报账单号（接口调用）限用于区分暂存凭证 （0-保存，1-暂存）"
                      clearable
                      autocomplete="new-password"
                      class="dc-el-input_SingleInput"
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="chargeagainstsubmitodd"
                    label="冲销报账单号（接口调用）//暂时用作存储交易时间"
                    class="dc-el-form-item_SingleInput"
                  >
                    <el-input
                      v-model="editFormData.chargeagainstsubmitodd"
                      :maxLength="-1"
                      placeholder="请输入冲销报账单号（接口调用）//暂时用作存储交易时间"
                      clearable
                      autocomplete="new-password"
                      class="dc-el-input_SingleInput"
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="tenantId"
                    label="tenant_id"
                    :rules="[{ required: true, message: 'tenant_id不能为空', trigger: 'blur' }]"
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
                </el-col>
              </el-row>
            </el-form>
          </el-tab-pane>
          <el-tab-pane label="会计期" name="tglGlobalPeriodsTable" class="dc-el-tab-pane_ElTabPane">
            <el-row v-if="action != 'view'" gutter="0" type="flex" justify="end" align="top" class="dc-el-row_ElRow">
              <el-tooltip disabled class="dc-el-tooltip_Button">
                <el-button
                  type="primary"
                  icon="el-icon-plus"
                  v-on:click="onAddTglGlobalPeriodsList"
                  class="dc-el-button_Button"
                ></el-button>
              </el-tooltip>
            </el-row>
            <el-form
              ref="tglGlobalPeriodsForm"
              :model="editFormData"
              label-width="100px"
              :disabled="action == 'view'"
              class="dc-el-form_ElEditForm"
            >
              <el-row>
                <ux-grid
                  :data="editFormData.tglGlobalPeriodsList"
                  ref="tglGlobalPeriodsTable"
                  :currentRow="table170CurrentRow"
                  height="200px"
                  border
                  :edit-config="{ trigger: 'click', mode: 'cell' }"
                  class="dc-ux-grid_EditTable"
                >
                  <ux-table-column
                    field="tenantId"
                    title="租户"
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
                        :prop="'tglGlobalPeriodsList.' + rowIndex + '.tenantId'"
                        label-width="0px"
                        :rules="[{ required: true, message: '租户不能为空', trigger: 'blur' }]"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.tenantId"
                          :maxLength="21"
                          placeholder="请输入租户"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglGlobalPeriodsList.' + rowIndex + '.tenantId'"
                          label-width="0px"
                          :rules="[{ required: true, message: '租户不能为空', trigger: 'blur' }]"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.tenantId }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="intyear"
                    title="年份"
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
                      <span style="color: #f56c6c">*</span>
                      {{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        :prop="'tglGlobalPeriodsList.' + rowIndex + '.intyear'"
                        label-width="0px"
                        :rules="[{ required: true, message: '年份不能为空', trigger: 'blur' }]"
                        class="dc-el-form-item_NumberInput"
                      >
                        <number-input
                          v-model="row.intyear"
                          :min="0"
                          :max="9999"
                          :precision="0"
                          placeholder="请输入年份"
                          clearable
                          class="dc-number-input_NumberInput"
                        ></number-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglGlobalPeriodsList.' + rowIndex + '.intyear'"
                          label-width="0px"
                          :rules="[{ required: true, message: '年份不能为空', trigger: 'blur' }]"
                          class="dc-el-form-item_NumberInput"
                        >
                          {{
                            row.intyear
                              ? '' +
                                '' +
                                Number(row.intyear)
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
                    field="intmonth"
                    title="月份"
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
                      <span style="color: #f56c6c">*</span>
                      {{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        :prop="'tglGlobalPeriodsList.' + rowIndex + '.intmonth'"
                        label-width="0px"
                        :rules="[{ required: true, message: '月份不能为空', trigger: 'blur' }]"
                        class="dc-el-form-item_NumberInput"
                      >
                        <number-input
                          v-model="row.intmonth"
                          :min="0"
                          :max="99999999999"
                          :precision="0"
                          placeholder="请输入月份"
                          clearable
                          class="dc-number-input_NumberInput"
                        ></number-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglGlobalPeriodsList.' + rowIndex + '.intmonth'"
                          label-width="0px"
                          :rules="[{ required: true, message: '月份不能为空', trigger: 'blur' }]"
                          class="dc-el-form-item_NumberInput"
                        >
                          {{
                            row.intmonth
                              ? '' +
                                '' +
                                Number(row.intmonth)
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
                    field="varname"
                    title="会计期名称"
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
                        :prop="'tglGlobalPeriodsList.' + rowIndex + '.varname'"
                        label-width="0px"
                        :rules="[{ required: true, message: '会计期名称不能为空', trigger: 'blur' }]"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.varname"
                          :maxLength="40"
                          placeholder="请输入会计期名称"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglGlobalPeriodsList.' + rowIndex + '.varname'"
                          label-width="0px"
                          :rules="[{ required: true, message: '会计期名称不能为空', trigger: 'blur' }]"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.varname }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="intstatus"
                    title="状态(0 停用，1 新增，2 启用)"
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
                        :prop="'tglGlobalPeriodsList.' + rowIndex + '.intstatus'"
                        label-width="0px"
                        class="dc-el-form-item_NumberInput"
                      >
                        <number-input
                          v-model="row.intstatus"
                          :min="0"
                          :max="99999999999"
                          :precision="0"
                          placeholder="请输入状态(0 停用，1 新增，2 启用)"
                          clearable
                          class="dc-number-input_NumberInput"
                        ></number-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglGlobalPeriodsList.' + rowIndex + '.intstatus'"
                          label-width="0px"
                          class="dc-el-form-item_NumberInput"
                        >
                          {{
                            row.intstatus
                              ? '' +
                                '' +
                                Number(row.intstatus)
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
                    field="intproperty"
                    title="属性"
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
                        :prop="'tglGlobalPeriodsList.' + rowIndex + '.intproperty'"
                        label-width="0px"
                        class="dc-el-form-item_NumberInput"
                      >
                        <number-input
                          v-model="row.intproperty"
                          :min="0"
                          :max="99999999999"
                          :precision="0"
                          placeholder="请输入属性"
                          clearable
                          class="dc-number-input_NumberInput"
                        ></number-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglGlobalPeriodsList.' + rowIndex + '.intproperty'"
                          label-width="0px"
                          class="dc-el-form-item_NumberInput"
                        >
                          {{
                            row.intproperty
                              ? '' +
                                '' +
                                Number(row.intproperty)
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
                    field="dtbegin"
                    title="有效开始时间"
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
                        :prop="'tglGlobalPeriodsList.' + rowIndex + '.dtbegin'"
                        label-width="0px"
                        :rules="[{ required: true, message: '有效开始时间不能为空', trigger: 'blur' }]"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.dtbegin"
                          :maxLength="24"
                          placeholder="请输入有效开始时间"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglGlobalPeriodsList.' + rowIndex + '.dtbegin'"
                          label-width="0px"
                          :rules="[{ required: true, message: '有效开始时间不能为空', trigger: 'blur' }]"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.dtbegin }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="dtend"
                    title="有效结束时间"
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
                        :prop="'tglGlobalPeriodsList.' + rowIndex + '.dtend'"
                        label-width="0px"
                        :rules="[{ required: true, message: '有效结束时间不能为空', trigger: 'blur' }]"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.dtend"
                          :maxLength="24"
                          placeholder="请输入有效结束时间"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglGlobalPeriodsList.' + rowIndex + '.dtend'"
                          label-width="0px"
                          :rules="[{ required: true, message: '有效结束时间不能为空', trigger: 'blur' }]"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.dtend }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="intyearmonth"
                    title="年月"
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
                      <span style="color: #f56c6c">*</span>
                      {{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        :prop="'tglGlobalPeriodsList.' + rowIndex + '.intyearmonth'"
                        label-width="0px"
                        :rules="[{ required: true, message: '年月不能为空', trigger: 'blur' }]"
                        class="dc-el-form-item_NumberInput"
                      >
                        <number-input
                          v-model="row.intyearmonth"
                          :min="0"
                          :max="99999999999"
                          :precision="0"
                          placeholder="请输入年月"
                          clearable
                          class="dc-number-input_NumberInput"
                        ></number-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglGlobalPeriodsList.' + rowIndex + '.intyearmonth'"
                          label-width="0px"
                          :rules="[{ required: true, message: '年月不能为空', trigger: 'blur' }]"
                          class="dc-el-form-item_NumberInput"
                        >
                          {{
                            row.intyearmonth
                              ? '' +
                                '' +
                                Number(row.intyearmonth)
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
                    field="remarks"
                    title="备注信息"
                    tree-node
                    resizable
                    min-width="120px"
                    align="left"
                    header-align="center"
                    show-overflow
                    :edit-render="{ autofocus: '.el-input__inner' }"
                    class="dc-ux-table-column_MutilpleInput"
                  >
                    <template v-slot:header="{ column }">
                      {{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        :prop="'tglGlobalPeriodsList.' + rowIndex + '.remarks'"
                        label-width="0px"
                        class="dc-el-form-item_MutilpleInput"
                      >
                        <el-input
                          v-model="row.remarks"
                          type="textarea"
                          placeholder="请输入备注信息"
                          rows="2"
                          :maxLength="255"
                          class="dc-el-input_MutilpleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglGlobalPeriodsList.' + rowIndex + '.remarks'"
                          label-width="0px"
                          class="dc-el-form-item_MutilpleInput"
                        >
                          {{ row.remarks }}
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
                        v-on:click="onDeleteChild(editFormData.tglGlobalPeriodsList, scope.rowIndex)"
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
          <el-tab-pane label="用户" name="sysUserTable" class="dc-el-tab-pane_ElTabPane">
            <el-row v-if="action != 'view'" gutter="0" type="flex" justify="end" align="top" class="dc-el-row_ElRow">
              <el-tooltip disabled class="dc-el-tooltip_Button">
                <el-button
                  type="primary"
                  icon="el-icon-plus"
                  v-on:click="onAddSysUserList"
                  class="dc-el-button_Button"
                ></el-button>
              </el-tooltip>
            </el-row>
            <el-form
              ref="sysUserForm"
              :model="editFormData"
              label-width="100px"
              :disabled="action == 'view'"
              class="dc-el-form_ElEditForm"
            >
              <el-row>
                <ux-grid
                  :data="editFormData.sysUserList"
                  ref="sysUserTable"
                  :currentRow="table210CurrentRow"
                  height="200px"
                  border
                  :edit-config="{ trigger: 'click', mode: 'cell' }"
                  class="dc-ux-grid_EditTable"
                >
                  <ux-table-column
                    field="companyId"
                    title="公司"
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
                        :prop="'sysUserList.' + rowIndex + '.companyId'"
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.companyId"
                          :maxLength="21"
                          placeholder="请输入公司"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'sysUserList.' + rowIndex + '.companyId'"
                          label-width="0px"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.companyId }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="departmentId"
                    title="部门"
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
                        :prop="'sysUserList.' + rowIndex + '.departmentId'"
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.departmentId"
                          :maxLength="21"
                          placeholder="请输入部门"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'sysUserList.' + rowIndex + '.departmentId'"
                          label-width="0px"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.departmentId }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="name"
                    title="名称"
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
                        :prop="'sysUserList.' + rowIndex + '.name'"
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.name"
                          :maxLength="128"
                          placeholder="请输入名称"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'sysUserList.' + rowIndex + '.name'"
                          label-width="0px"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.name }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="loginName"
                    title="登录账号"
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
                        :prop="'sysUserList.' + rowIndex + '.loginName'"
                        label-width="0px"
                        :rules="[{ required: true, message: '登录账号不能为空', trigger: 'blur' }]"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.loginName"
                          :maxLength="10"
                          placeholder="请输入登录账号"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'sysUserList.' + rowIndex + '.loginName'"
                          label-width="0px"
                          :rules="[{ required: true, message: '登录账号不能为空', trigger: 'blur' }]"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.loginName }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="loginPassword"
                    title="密码"
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
                        :prop="'sysUserList.' + rowIndex + '.loginPassword'"
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.loginPassword"
                          :maxLength="64"
                          placeholder="请输入密码"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'sysUserList.' + rowIndex + '.loginPassword'"
                          label-width="0px"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.loginPassword }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="isLocked"
                    title="禁用"
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
                        :prop="'sysUserList.' + rowIndex + '.isLocked'"
                        label-width="0px"
                        :rules="[{ required: true, message: '禁用不能为空', trigger: 'blur' }]"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.isLocked"
                          :maxLength="1"
                          placeholder="请输入禁用"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'sysUserList.' + rowIndex + '.isLocked'"
                          label-width="0px"
                          :rules="[{ required: true, message: '禁用不能为空', trigger: 'blur' }]"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.isLocked }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="phone"
                    title="手机号"
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
                        :prop="'sysUserList.' + rowIndex + '.phone'"
                        label-width="0px"
                        :rules="[{ required: true, message: '手机号不能为空', trigger: 'blur' }]"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.phone"
                          :maxLength="20"
                          placeholder="请输入手机号"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'sysUserList.' + rowIndex + '.phone'"
                          label-width="0px"
                          :rules="[{ required: true, message: '手机号不能为空', trigger: 'blur' }]"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.phone }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="email"
                    title="邮箱地址"
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
                        :prop="'sysUserList.' + rowIndex + '.email'"
                        label-width="0px"
                        :rules="[{ required: true, message: '邮箱地址不能为空', trigger: 'blur' }]"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.email"
                          :maxLength="36"
                          placeholder="请输入邮箱地址"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'sysUserList.' + rowIndex + '.email'"
                          label-width="0px"
                          :rules="[{ required: true, message: '邮箱地址不能为空', trigger: 'blur' }]"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.email }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="remarks"
                    title="备注信息"
                    tree-node
                    resizable
                    min-width="120px"
                    align="left"
                    header-align="center"
                    show-overflow
                    :edit-render="{ autofocus: '.el-input__inner' }"
                    class="dc-ux-table-column_MutilpleInput"
                  >
                    <template v-slot:header="{ column }">
                      {{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        :prop="'sysUserList.' + rowIndex + '.remarks'"
                        label-width="0px"
                        class="dc-el-form-item_MutilpleInput"
                      >
                        <el-input
                          v-model="row.remarks"
                          type="textarea"
                          placeholder="请输入备注信息"
                          rows="2"
                          :maxLength="255"
                          class="dc-el-input_MutilpleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'sysUserList.' + rowIndex + '.remarks'"
                          label-width="0px"
                          class="dc-el-form-item_MutilpleInput"
                        >
                          {{ row.remarks }}
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
                        v-on:click="onDeleteChild(editFormData.sysUserList, scope.rowIndex)"
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
          <el-tab-pane label="会计科目表" name="tglGroupAccountsTable" class="dc-el-tab-pane_ElTabPane">
            <el-row v-if="action != 'view'" gutter="0" type="flex" justify="end" align="top" class="dc-el-row_ElRow">
              <el-tooltip disabled class="dc-el-tooltip_Button">
                <el-button
                  type="primary"
                  icon="el-icon-plus"
                  v-on:click="onAddTglGroupAccountsList"
                  class="dc-el-button_Button"
                ></el-button>
              </el-tooltip>
            </el-row>
            <el-form
              ref="tglGroupAccountsForm"
              :model="editFormData"
              label-width="100px"
              :disabled="action == 'view'"
              class="dc-el-form_ElEditForm"
            >
              <el-row>
                <ux-grid
                  :data="editFormData.tglGroupAccountsList"
                  ref="tglGroupAccountsTable"
                  :currentRow="table247CurrentRow"
                  height="200px"
                  border
                  :edit-config="{ trigger: 'click', mode: 'cell' }"
                  class="dc-ux-grid_EditTable"
                >
                  <ux-table-column
                    field="uqaccountsetid"
                    title="科目套id"
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
                        :prop="'tglGroupAccountsList.' + rowIndex + '.uqaccountsetid'"
                        label-width="0px"
                        :rules="[{ required: true, message: '科目套id不能为空', trigger: 'blur' }]"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.uqaccountsetid"
                          :maxLength="40"
                          placeholder="请输入科目套id"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglGroupAccountsList.' + rowIndex + '.uqaccountsetid'"
                          label-width="0px"
                          :rules="[{ required: true, message: '科目套id不能为空', trigger: 'blur' }]"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.uqaccountsetid }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="varaccountcode"
                    title="科目编码"
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
                        :prop="'tglGroupAccountsList.' + rowIndex + '.varaccountcode'"
                        label-width="0px"
                        :rules="[{ required: true, message: '科目编码不能为空', trigger: 'blur' }]"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.varaccountcode"
                          :maxLength="100"
                          placeholder="请输入科目编码"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglGroupAccountsList.' + rowIndex + '.varaccountcode'"
                          label-width="0px"
                          :rules="[{ required: true, message: '科目编码不能为空', trigger: 'blur' }]"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.varaccountcode }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="varaccountfullcode"
                    title="科目全编码"
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
                        :prop="'tglGroupAccountsList.' + rowIndex + '.varaccountfullcode'"
                        label-width="0px"
                        :rules="[{ required: true, message: '科目全编码不能为空', trigger: 'blur' }]"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.varaccountfullcode"
                          :maxLength="250"
                          placeholder="请输入科目全编码"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglGroupAccountsList.' + rowIndex + '.varaccountfullcode'"
                          label-width="0px"
                          :rules="[{ required: true, message: '科目全编码不能为空', trigger: 'blur' }]"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.varaccountfullcode }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="varaccountname"
                    title="科目名称"
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
                        :prop="'tglGroupAccountsList.' + rowIndex + '.varaccountname'"
                        label-width="0px"
                        :rules="[{ required: true, message: '科目名称不能为空', trigger: 'blur' }]"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.varaccountname"
                          :maxLength="100"
                          placeholder="请输入科目名称"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglGroupAccountsList.' + rowIndex + '.varaccountname'"
                          label-width="0px"
                          :rules="[{ required: true, message: '科目名称不能为空', trigger: 'blur' }]"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.varaccountname }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="intproperty"
                    title="科目属性"
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
                      <span style="color: #f56c6c">*</span>
                      {{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        :prop="'tglGroupAccountsList.' + rowIndex + '.intproperty'"
                        label-width="0px"
                        :rules="[{ required: true, message: '科目属性不能为空', trigger: 'blur' }]"
                        class="dc-el-form-item_NumberInput"
                      >
                        <number-input
                          v-model="row.intproperty"
                          :min="0"
                          :max="99999999999"
                          :precision="0"
                          placeholder="请输入科目属性"
                          clearable
                          class="dc-number-input_NumberInput"
                        ></number-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglGroupAccountsList.' + rowIndex + '.intproperty'"
                          label-width="0px"
                          :rules="[{ required: true, message: '科目属性不能为空', trigger: 'blur' }]"
                          class="dc-el-form-item_NumberInput"
                        >
                          {{
                            row.intproperty
                              ? '' +
                                '' +
                                Number(row.intproperty)
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
                    field="uqtypeid"
                    title="科目类别"
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
                        :prop="'tglGroupAccountsList.' + rowIndex + '.uqtypeid'"
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.uqtypeid"
                          :maxLength="40"
                          placeholder="请输入科目类别"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglGroupAccountsList.' + rowIndex + '.uqtypeid'"
                          label-width="0px"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.uqtypeid }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="uqforeigncurrid"
                    title="外币id"
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
                        :prop="'tglGroupAccountsList.' + rowIndex + '.uqforeigncurrid'"
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.uqforeigncurrid"
                          :maxLength="40"
                          placeholder="请输入外币id"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglGroupAccountsList.' + rowIndex + '.uqforeigncurrid'"
                          label-width="0px"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.uqforeigncurrid }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="varmeasure"
                    title="计量单位"
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
                        :prop="'tglGroupAccountsList.' + rowIndex + '.varmeasure'"
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.varmeasure"
                          :maxLength="100"
                          placeholder="请输入计量单位"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglGroupAccountsList.' + rowIndex + '.varmeasure'"
                          label-width="0px"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.varmeasure }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="intisledge"
                    title="是否分户核算"
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
                        :prop="'tglGroupAccountsList.' + rowIndex + '.intisledge'"
                        label-width="0px"
                        class="dc-el-form-item_NumberInput"
                      >
                        <number-input
                          v-model="row.intisledge"
                          :min="0"
                          :max="99999999999"
                          :precision="0"
                          placeholder="请输入是否分户核算"
                          clearable
                          class="dc-number-input_NumberInput"
                        ></number-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglGroupAccountsList.' + rowIndex + '.intisledge'"
                          label-width="0px"
                          class="dc-el-form-item_NumberInput"
                        >
                          {{
                            row.intisledge
                              ? '' +
                                '' +
                                Number(row.intisledge)
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
                    field="intiscrossledge"
                    title="是否分户交叉查询"
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
                        :prop="'tglGroupAccountsList.' + rowIndex + '.intiscrossledge'"
                        label-width="0px"
                        class="dc-el-form-item_NumberInput"
                      >
                        <number-input
                          v-model="row.intiscrossledge"
                          :min="0"
                          :max="99999999999"
                          :precision="0"
                          placeholder="请输入是否分户交叉查询"
                          clearable
                          class="dc-number-input_NumberInput"
                        ></number-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglGroupAccountsList.' + rowIndex + '.intiscrossledge'"
                          label-width="0px"
                          class="dc-el-form-item_NumberInput"
                        >
                          {{
                            row.intiscrossledge
                              ? '' +
                                '' +
                                Number(row.intiscrossledge)
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
                    field="uqpreaccountid"
                    title="追朔科目ID"
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
                        :prop="'tglGroupAccountsList.' + rowIndex + '.uqpreaccountid'"
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.uqpreaccountid"
                          :maxLength="40"
                          placeholder="请输入追朔科目ID"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglGroupAccountsList.' + rowIndex + '.uqpreaccountid'"
                          label-width="0px"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.uqpreaccountid }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="uqparentid"
                    title="父级科目ID"
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
                        :prop="'tglGroupAccountsList.' + rowIndex + '.uqparentid'"
                        label-width="0px"
                        :rules="[{ required: true, message: '父级科目ID不能为空', trigger: 'blur' }]"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.uqparentid"
                          :maxLength="40"
                          placeholder="请输入父级科目ID"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglGroupAccountsList.' + rowIndex + '.uqparentid'"
                          label-width="0px"
                          :rules="[{ required: true, message: '父级科目ID不能为空', trigger: 'blur' }]"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.uqparentid }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="intislastlevel"
                    title="是否为末级（0不是，1是）"
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
                      <span style="color: #f56c6c">*</span>
                      {{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        :prop="'tglGroupAccountsList.' + rowIndex + '.intislastlevel'"
                        label-width="0px"
                        :rules="[{ required: true, message: '是否为末级（0不是，1是）不能为空', trigger: 'blur' }]"
                        class="dc-el-form-item_NumberInput"
                      >
                        <number-input
                          v-model="row.intislastlevel"
                          :min="0"
                          :max="99999999999"
                          :precision="0"
                          placeholder="请输入是否为末级（0不是，1是）"
                          clearable
                          class="dc-number-input_NumberInput"
                        ></number-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglGroupAccountsList.' + rowIndex + '.intislastlevel'"
                          label-width="0px"
                          :rules="[{ required: true, message: '是否为末级（0不是，1是）不能为空', trigger: 'blur' }]"
                          class="dc-el-form-item_NumberInput"
                        >
                          {{
                            row.intislastlevel
                              ? '' +
                                '' +
                                Number(row.intislastlevel)
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
                    field="intlevel"
                    title="科目等级"
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
                      <span style="color: #f56c6c">*</span>
                      {{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        :prop="'tglGroupAccountsList.' + rowIndex + '.intlevel'"
                        label-width="0px"
                        :rules="[{ required: true, message: '科目等级不能为空', trigger: 'blur' }]"
                        class="dc-el-form-item_NumberInput"
                      >
                        <number-input
                          v-model="row.intlevel"
                          :min="0"
                          :max="99999999999"
                          :precision="0"
                          placeholder="请输入科目等级"
                          clearable
                          class="dc-number-input_NumberInput"
                        ></number-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglGroupAccountsList.' + rowIndex + '.intlevel'"
                          label-width="0px"
                          :rules="[{ required: true, message: '科目等级不能为空', trigger: 'blur' }]"
                          class="dc-el-form-item_NumberInput"
                        >
                          {{
                            row.intlevel
                              ? '' +
                                '' +
                                Number(row.intlevel)
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
                    field="intflag"
                    title="启停(0停用,1新建,2启用)"
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
                      <span style="color: #f56c6c">*</span>
                      {{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        :prop="'tglGroupAccountsList.' + rowIndex + '.intflag'"
                        label-width="0px"
                        :rules="[{ required: true, message: '启停(0停用,1新建,2启用)不能为空', trigger: 'blur' }]"
                        class="dc-el-form-item_NumberInput"
                      >
                        <number-input
                          v-model="row.intflag"
                          :min="0"
                          :max="99999999999"
                          :precision="0"
                          placeholder="请输入启停(0停用,1新建,2启用)"
                          clearable
                          class="dc-number-input_NumberInput"
                        ></number-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglGroupAccountsList.' + rowIndex + '.intflag'"
                          label-width="0px"
                          :rules="[{ required: true, message: '启停(0停用,1新建,2启用)不能为空', trigger: 'blur' }]"
                          class="dc-el-form-item_NumberInput"
                        >
                          {{
                            row.intflag
                              ? '' +
                                '' +
                                Number(row.intflag)
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
                    field="varaccountfullname"
                    title="科目全名称"
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
                        :prop="'tglGroupAccountsList.' + rowIndex + '.varaccountfullname'"
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.varaccountfullname"
                          :maxLength="1000"
                          placeholder="请输入科目全名称"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglGroupAccountsList.' + rowIndex + '.varaccountfullname'"
                          label-width="0px"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.varaccountfullname }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="intisflow"
                    title="是否需要现金流量（0不需要，1可要可不要，2必须要）"
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
                        :prop="'tglGroupAccountsList.' + rowIndex + '.intisflow'"
                        label-width="0px"
                        class="dc-el-form-item_NumberInput"
                      >
                        <number-input
                          v-model="row.intisflow"
                          :min="0"
                          :max="99999999999"
                          :precision="0"
                          placeholder="请输入是否需要现金流量（0不需要，1可要可不要，2必须要）"
                          clearable
                          class="dc-number-input_NumberInput"
                        ></number-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglGroupAccountsList.' + rowIndex + '.intisflow'"
                          label-width="0px"
                          class="dc-el-form-item_NumberInput"
                        >
                          {{
                            row.intisflow
                              ? '' +
                                '' +
                                Number(row.intisflow)
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
                    field="groupId"
                    title="group_id"
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
                        :prop="'tglGroupAccountsList.' + rowIndex + '.groupId'"
                        label-width="0px"
                        :rules="[{ required: true, message: 'group_id不能为空', trigger: 'blur' }]"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.groupId"
                          :maxLength="100"
                          placeholder="请输入group_id"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglGroupAccountsList.' + rowIndex + '.groupId'"
                          label-width="0px"
                          :rules="[{ required: true, message: 'group_id不能为空', trigger: 'blur' }]"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.groupId }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="isbalance"
                    title="余额方向（0-借，1-贷）"
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
                        :prop="'tglGroupAccountsList.' + rowIndex + '.isbalance'"
                        label-width="0px"
                        class="dc-el-form-item_NumberInput"
                      >
                        <number-input
                          v-model="row.isbalance"
                          :min="0"
                          :max="99999999999"
                          :precision="0"
                          placeholder="请输入余额方向（0-借，1-贷）"
                          clearable
                          class="dc-number-input_NumberInput"
                        ></number-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglGroupAccountsList.' + rowIndex + '.isbalance'"
                          label-width="0px"
                          class="dc-el-form-item_NumberInput"
                        >
                          {{
                            row.isbalance
                              ? '' +
                                '' +
                                Number(row.isbalance)
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
                        v-on:click="onDeleteChild(editFormData.tglGroupAccountsList, scope.rowIndex)"
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
          <el-tab-pane label="凭证明细表" name="tglVoucherDetailsTable" class="dc-el-tab-pane_ElTabPane">
            <el-row v-if="action != 'view'" gutter="0" type="flex" justify="end" align="top" class="dc-el-row_ElRow">
              <el-tooltip disabled class="dc-el-tooltip_Button">
                <el-button
                  type="primary"
                  icon="el-icon-plus"
                  v-on:click="onAddTglVoucherDetailsList"
                  class="dc-el-button_Button"
                ></el-button>
              </el-tooltip>
            </el-row>
            <el-form
              ref="tglVoucherDetailsForm"
              :model="editFormData"
              label-width="100px"
              :disabled="action == 'view'"
              class="dc-el-form_ElEditForm"
            >
              <el-row>
                <ux-grid
                  :data="editFormData.tglVoucherDetailsList"
                  ref="tglVoucherDetailsTable"
                  :currentRow="table314CurrentRow"
                  height="200px"
                  border
                  :edit-config="{ trigger: 'click', mode: 'cell' }"
                  class="dc-ux-grid_EditTable"
                >
                  <ux-table-column
                    field="uqvoucherdetailid"
                    title="凭证明细ID"
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
                        :prop="'tglVoucherDetailsList.' + rowIndex + '.uqvoucherdetailid'"
                        label-width="0px"
                        :rules="[{ required: true, message: '凭证明细ID不能为空', trigger: 'blur' }]"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.uqvoucherdetailid"
                          :maxLength="40"
                          placeholder="请输入凭证明细ID"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglVoucherDetailsList.' + rowIndex + '.uqvoucherdetailid'"
                          label-width="0px"
                          :rules="[{ required: true, message: '凭证明细ID不能为空', trigger: 'blur' }]"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.uqvoucherdetailid }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="intseq"
                    title="序号"
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
                      <span style="color: #f56c6c">*</span>
                      {{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        :prop="'tglVoucherDetailsList.' + rowIndex + '.intseq'"
                        label-width="0px"
                        :rules="[{ required: true, message: '序号不能为空', trigger: 'blur' }]"
                        class="dc-el-form-item_NumberInput"
                      >
                        <number-input
                          v-model="row.intseq"
                          :min="0"
                          :max="99999999999"
                          :precision="0"
                          placeholder="请输入序号"
                          clearable
                          class="dc-number-input_NumberInput"
                        ></number-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglVoucherDetailsList.' + rowIndex + '.intseq'"
                          label-width="0px"
                          :rules="[{ required: true, message: '序号不能为空', trigger: 'blur' }]"
                          class="dc-el-form-item_NumberInput"
                        >
                          {{
                            row.intseq
                              ? '' +
                                '' +
                                Number(row.intseq)
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
                      <span style="color: #f56c6c">*</span>
                      {{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        :prop="'tglVoucherDetailsList.' + rowIndex + '.varabstract'"
                        label-width="0px"
                        :rules="[{ required: true, message: '摘要不能为空', trigger: 'blur' }]"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.varabstract"
                          :maxLength="500"
                          placeholder="请输入摘要"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglVoucherDetailsList.' + rowIndex + '.varabstract'"
                          label-width="0px"
                          :rules="[{ required: true, message: '摘要不能为空', trigger: 'blur' }]"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.varabstract }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="uqcgsid"
                    title="往来ID"
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
                        :prop="'tglVoucherDetailsList.' + rowIndex + '.uqcgsid'"
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.uqcgsid"
                          :maxLength="40"
                          placeholder="请输入往来ID"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglVoucherDetailsList.' + rowIndex + '.uqcgsid'"
                          label-width="0px"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.uqcgsid }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="uqaccountid"
                    title="会计科目ID"
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
                        :prop="'tglVoucherDetailsList.' + rowIndex + '.uqaccountid'"
                        label-width="0px"
                        :rules="[{ required: true, message: '会计科目ID不能为空', trigger: 'blur' }]"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.uqaccountid"
                          :maxLength="40"
                          placeholder="请输入会计科目ID"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglVoucherDetailsList.' + rowIndex + '.uqaccountid'"
                          label-width="0px"
                          :rules="[{ required: true, message: '会计科目ID不能为空', trigger: 'blur' }]"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.uqaccountid }}
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
                      <span style="color: #f56c6c">*</span>
                      {{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        :prop="'tglVoucherDetailsList.' + rowIndex + '.mnydebit'"
                        label-width="0px"
                        :rules="[{ required: true, message: '借方金额不能为空', trigger: 'blur' }]"
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
                          :prop="'tglVoucherDetailsList.' + rowIndex + '.mnydebit'"
                          label-width="0px"
                          :rules="[{ required: true, message: '借方金额不能为空', trigger: 'blur' }]"
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
                      <span style="color: #f56c6c">*</span>
                      {{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        :prop="'tglVoucherDetailsList.' + rowIndex + '.mnycredit'"
                        label-width="0px"
                        :rules="[{ required: true, message: '贷方金额不能为空', trigger: 'blur' }]"
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
                          :prop="'tglVoucherDetailsList.' + rowIndex + '.mnycredit'"
                          label-width="0px"
                          :rules="[{ required: true, message: '贷方金额不能为空', trigger: 'blur' }]"
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
                    field="mnyfdebit"
                    title="MNYFDEBIT"
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
                        :prop="'tglVoucherDetailsList.' + rowIndex + '.mnyfdebit'"
                        label-width="0px"
                        class="dc-el-form-item_NumberInput"
                      >
                        <number-input
                          v-model="row.mnyfdebit"
                          :min="0"
                          :max="999999999999.99"
                          :precision="2"
                          placeholder="请输入MNYFDEBIT"
                          clearable
                          class="dc-number-input_NumberInput"
                        ></number-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglVoucherDetailsList.' + rowIndex + '.mnyfdebit'"
                          label-width="0px"
                          class="dc-el-form-item_NumberInput"
                        >
                          {{
                            row.mnyfdebit
                              ? '' +
                                '' +
                                Number(row.mnyfdebit)
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
                    field="mnyfcredit"
                    title="MNYFCREDIT"
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
                        :prop="'tglVoucherDetailsList.' + rowIndex + '.mnyfcredit'"
                        label-width="0px"
                        class="dc-el-form-item_NumberInput"
                      >
                        <number-input
                          v-model="row.mnyfcredit"
                          :min="0"
                          :max="999999999999.99"
                          :precision="2"
                          placeholder="请输入MNYFCREDIT"
                          clearable
                          class="dc-number-input_NumberInput"
                        ></number-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglVoucherDetailsList.' + rowIndex + '.mnyfcredit'"
                          label-width="0px"
                          class="dc-el-form-item_NumberInput"
                        >
                          {{
                            row.mnyfcredit
                              ? '' +
                                '' +
                                Number(row.mnyfcredit)
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
                    field="uqpaytypeid"
                    title="结算方式"
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
                        :prop="'tglVoucherDetailsList.' + rowIndex + '.uqpaytypeid'"
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.uqpaytypeid"
                          :maxLength="40"
                          placeholder="请输入结算方式"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglVoucherDetailsList.' + rowIndex + '.uqpaytypeid'"
                          label-width="0px"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.uqpaytypeid }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="vartypeno"
                    title="结算号"
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
                        :prop="'tglVoucherDetailsList.' + rowIndex + '.vartypeno'"
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.vartypeno"
                          :maxLength="40"
                          placeholder="请输入结算号"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglVoucherDetailsList.' + rowIndex + '.vartypeno'"
                          label-width="0px"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.vartypeno }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="decnumber"
                    title="数量"
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
                        :prop="'tglVoucherDetailsList.' + rowIndex + '.decnumber'"
                        label-width="0px"
                        class="dc-el-form-item_NumberInput"
                      >
                        <number-input
                          v-model="row.decnumber"
                          :min="0"
                          :max="999999999999.99"
                          :precision="2"
                          placeholder="请输入数量"
                          clearable
                          class="dc-number-input_NumberInput"
                        ></number-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglVoucherDetailsList.' + rowIndex + '.decnumber'"
                          label-width="0px"
                          class="dc-el-form-item_NumberInput"
                        >
                          {{
                            row.decnumber
                              ? '' +
                                '' +
                                Number(row.decnumber)
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
                    field="mnyprice"
                    title="单价"
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
                        :prop="'tglVoucherDetailsList.' + rowIndex + '.mnyprice'"
                        label-width="0px"
                        class="dc-el-form-item_NumberInput"
                      >
                        <number-input
                          v-model="row.mnyprice"
                          :min="0"
                          :max="999999999999.99"
                          :precision="2"
                          placeholder="请输入单价"
                          clearable
                          class="dc-number-input_NumberInput"
                        ></number-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglVoucherDetailsList.' + rowIndex + '.mnyprice'"
                          label-width="0px"
                          class="dc-el-form-item_NumberInput"
                        >
                          {{
                            row.mnyprice
                              ? '' +
                                '' +
                                Number(row.mnyprice)
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
                    field="mnyforeigncurr"
                    title="外币金额"
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
                        :prop="'tglVoucherDetailsList.' + rowIndex + '.mnyforeigncurr'"
                        label-width="0px"
                        class="dc-el-form-item_NumberInput"
                      >
                        <number-input
                          v-model="row.mnyforeigncurr"
                          :min="0"
                          :max="999999999999.99"
                          :precision="2"
                          placeholder="请输入外币金额"
                          clearable
                          class="dc-number-input_NumberInput"
                        ></number-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglVoucherDetailsList.' + rowIndex + '.mnyforeigncurr'"
                          label-width="0px"
                          class="dc-el-form-item_NumberInput"
                        >
                          {{
                            row.mnyforeigncurr
                              ? '' +
                                '' +
                                Number(row.mnyforeigncurr)
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
                    field="decexchagerate"
                    title="外币汇率"
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
                        :prop="'tglVoucherDetailsList.' + rowIndex + '.decexchagerate'"
                        label-width="0px"
                        class="dc-el-form-item_NumberInput"
                      >
                        <number-input
                          v-model="row.decexchagerate"
                          :min="0"
                          :max="999999999999.99"
                          :precision="2"
                          placeholder="请输入外币汇率"
                          clearable
                          class="dc-number-input_NumberInput"
                        ></number-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglVoucherDetailsList.' + rowIndex + '.decexchagerate'"
                          label-width="0px"
                          class="dc-el-form-item_NumberInput"
                        >
                          {{
                            row.decexchagerate
                              ? '' +
                                '' +
                                Number(row.decexchagerate)
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
                        v-on:click="onDeleteChild(editFormData.tglVoucherDetailsList, scope.rowIndex)"
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
          <el-tab-pane label="凭证分录分户分摊表" name="tglVoucherDetailLedgerTable" class="dc-el-tab-pane_ElTabPane">
            <el-row v-if="action != 'view'" gutter="0" type="flex" justify="end" align="top" class="dc-el-row_ElRow">
              <el-tooltip disabled class="dc-el-tooltip_Button">
                <el-button
                  type="primary"
                  icon="el-icon-plus"
                  v-on:click="onAddTglVoucherDetailLedgerList"
                  class="dc-el-button_Button"
                ></el-button>
              </el-tooltip>
            </el-row>
            <el-form
              ref="tglVoucherDetailLedgerForm"
              :model="editFormData"
              label-width="100px"
              :disabled="action == 'view'"
              class="dc-el-form_ElEditForm"
            >
              <el-row>
                <ux-grid
                  :data="editFormData.tglVoucherDetailLedgerList"
                  ref="tglVoucherDetailLedgerTable"
                  :currentRow="table369CurrentRow"
                  height="200px"
                  border
                  :edit-config="{ trigger: 'click', mode: 'cell' }"
                  class="dc-ux-grid_EditTable"
                >
                  <ux-table-column
                    field="uqvouledgerid"
                    title="凭证分录分户主键ID"
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
                        :prop="'tglVoucherDetailLedgerList.' + rowIndex + '.uqvouledgerid'"
                        label-width="0px"
                        :rules="[{ required: true, message: '凭证分录分户主键ID不能为空', trigger: 'blur' }]"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.uqvouledgerid"
                          :maxLength="40"
                          placeholder="请输入凭证分录分户主键ID"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglVoucherDetailLedgerList.' + rowIndex + '.uqvouledgerid'"
                          label-width="0px"
                          :rules="[{ required: true, message: '凭证分录分户主键ID不能为空', trigger: 'blur' }]"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.uqvouledgerid }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="uqledgerid"
                    title="凭证分录分户分摊ID"
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
                        :prop="'tglVoucherDetailLedgerList.' + rowIndex + '.uqledgerid'"
                        label-width="0px"
                        :rules="[{ required: true, message: '凭证分录分户分摊ID不能为空', trigger: 'blur' }]"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.uqledgerid"
                          :maxLength="40"
                          placeholder="请输入凭证分录分户分摊ID"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglVoucherDetailLedgerList.' + rowIndex + '.uqledgerid'"
                          label-width="0px"
                          :rules="[{ required: true, message: '凭证分录分户分摊ID不能为空', trigger: 'blur' }]"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.uqledgerid }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="uqledgertypeid"
                    title="凭证分录分户类别ID"
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
                        :prop="'tglVoucherDetailLedgerList.' + rowIndex + '.uqledgertypeid'"
                        label-width="0px"
                        :rules="[{ required: true, message: '凭证分录分户类别ID不能为空', trigger: 'blur' }]"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.uqledgertypeid"
                          :maxLength="40"
                          placeholder="请输入凭证分录分户类别ID"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglVoucherDetailLedgerList.' + rowIndex + '.uqledgertypeid'"
                          label-width="0px"
                          :rules="[{ required: true, message: '凭证分录分户类别ID不能为空', trigger: 'blur' }]"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.uqledgertypeid }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="mnyamount"
                    title="摊销金额"
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
                        :prop="'tglVoucherDetailLedgerList.' + rowIndex + '.mnyamount'"
                        label-width="0px"
                        class="dc-el-form-item_NumberInput"
                      >
                        <number-input
                          v-model="row.mnyamount"
                          :min="0"
                          :max="999999999999.99"
                          :precision="2"
                          placeholder="请输入摊销金额"
                          clearable
                          class="dc-number-input_NumberInput"
                        ></number-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglVoucherDetailLedgerList.' + rowIndex + '.mnyamount'"
                          label-width="0px"
                          class="dc-el-form-item_NumberInput"
                        >
                          {{
                            row.mnyamount
                              ? '' +
                                '' +
                                Number(row.mnyamount)
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
                    field="mnydebit"
                    title="摊销借方金额"
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
                        :prop="'tglVoucherDetailLedgerList.' + rowIndex + '.mnydebit'"
                        label-width="0px"
                        class="dc-el-form-item_NumberInput"
                      >
                        <number-input
                          v-model="row.mnydebit"
                          :min="0"
                          :max="999999999999.99"
                          :precision="2"
                          placeholder="请输入摊销借方金额"
                          clearable
                          class="dc-number-input_NumberInput"
                        ></number-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglVoucherDetailLedgerList.' + rowIndex + '.mnydebit'"
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
                    title="摊销贷方金额"
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
                        :prop="'tglVoucherDetailLedgerList.' + rowIndex + '.mnycredit'"
                        label-width="0px"
                        class="dc-el-form-item_NumberInput"
                      >
                        <number-input
                          v-model="row.mnycredit"
                          :min="0"
                          :max="999999999999.99"
                          :precision="2"
                          placeholder="请输入摊销贷方金额"
                          clearable
                          class="dc-number-input_NumberInput"
                        ></number-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglVoucherDetailLedgerList.' + rowIndex + '.mnycredit'"
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
                    field="intseq"
                    title="序号"
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
                        :prop="'tglVoucherDetailLedgerList.' + rowIndex + '.intseq'"
                        label-width="0px"
                        class="dc-el-form-item_NumberInput"
                      >
                        <number-input
                          v-model="row.intseq"
                          :min="0"
                          :max="99999999999"
                          :precision="0"
                          placeholder="请输入序号"
                          clearable
                          class="dc-number-input_NumberInput"
                        ></number-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tglVoucherDetailLedgerList.' + rowIndex + '.intseq'"
                          label-width="0px"
                          class="dc-el-form-item_NumberInput"
                        >
                          {{
                            row.intseq
                              ? '' +
                                '' +
                                Number(row.intseq)
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
                        v-on:click="onDeleteChild(editFormData.tglVoucherDetailLedgerList, scope.rowIndex)"
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
  </el-row>
</template>
<script>
import {
  validatenull
} from '@/utils/validate'
/** 根据用户界面配置import组件 结束 */
import {
  listTglGlobalPeriodsAll
} from '@/api/vouchermanager/tglGlobalPeriods.js'
import {
  listSysUserAll
} from '@/api/vouchermanager/sysUser.js'
/** 根据用户界面配置import组件 结束 */
import {
  getTglVoucherMainsById,
  saveTglVoucherMains
} from '@/api/vouchermanager/tglVoucherMains'
import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'
export default {
  extends: BaseUI,
  name: 'tglVoucherMains-form',
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
        title: '制证'
      },
      dialogTitle: '制证',
      // 选项变量
      // 会计期选项
      uqglobalperiodidOptions: [],
      // 会计主管选项
      uqfinacialmanageridOptions: [],
      // 记账人选项
      uqaccountantidOptions: [],
      // 出纳人选项
      uqcasheridOptions: [],
      // 审核人选项
      uqcheckeridOptions: [],
      // 制单人选项
      uqfilleridOptions: [],
      table170CurrentRow: {}, // 当前行
      table210CurrentRow: {}, // 当前行
      table247CurrentRow: {}, // 当前行
      table314CurrentRow: {}, // 当前行
      table369CurrentRow: {}, // 当前行
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

    onSubmit() {
      let validFlag = true



























































































































































































































































































































































































































































































      this.$refs['tglGlobalPeriodsForm'].validate(valid => {
        if (valid) {} else {
          this.tabName = 'tglGlobalPeriodsTable'
          validFlag = false
          return false
        }
      })






































































































































































































































































      this.$refs['sysUserForm'].validate(valid => {
        if (valid) {} else {
          this.tabName = 'sysUserTable'
          validFlag = false
          return false
        }
      })



















































































































































































































































      this.$refs['tglGroupAccountsForm'].validate(valid => {
        if (valid) {} else {
          this.tabName = 'tglGroupAccountsTable'
          validFlag = false
          return false
        }
      })

















































































































































































































































































































































































































































      this.$refs['tglVoucherDetailsForm'].validate(valid => {
        if (valid) {} else {
          this.tabName = 'tglVoucherDetailsTable'
          validFlag = false
          return false
        }
      })





































































































































































































































































































































































      this.$refs['tglVoucherDetailLedgerForm'].validate(valid => {
        if (valid) {} else {
          this.tabName = 'tglVoucherDetailLedgerTable'
          validFlag = false
          return false
        }
      })



























































































































































































      this.$refs['editForm'].validate(valid => {
        if (valid) {} else {
          this.tabName = 'editForm'
          validFlag = false
          return false
        }
      })
      if (validFlag) {
        this.doSave()
      }
    },
    doSave() {
      this.setLoad()

      saveTglVoucherMains(this.editFormData).then(responseData => {

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
      }).catch(error => {
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
        getTglVoucherMainsById(id).then((responseData) => {
          let form = {}
          if (responseData.code == 100) {
            form = responseData.data
          } else {
            this.showMessage(responseData)
          }
          this.resetLoad()
          resolve(form)
        }).catch((error) => {
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












































































































    listUqglobalperiodidOptions() {

      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [])


      // 数据权限: 会计期tgl_global_periods
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, "1457254743943432960")


      listTglGlobalPeriodsAll(search_List).then(responseData => {
        if (responseData.code == 100) {

          this.uqglobalperiodidOptions = responseData.data

        } else {
          this.showMessage(responseData)
        }
      })
    },














































    listUqfinacialmanageridOptions() {

      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [])


      // 数据权限: 用户sys_user
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, "1457254743943432961")


      listSysUserAll(search_List).then(responseData => {
        if (responseData.code == 100) {

          this.uqfinacialmanageridOptions = responseData.data

        } else {
          this.showMessage(responseData)
        }
      })
    },


















    listUqaccountantidOptions() {

      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [])


      // 数据权限: 用户sys_user
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, "1457254743943432961")


      listSysUserAll(search_List).then(responseData => {
        if (responseData.code == 100) {

          this.uqaccountantidOptions = responseData.data

        } else {
          this.showMessage(responseData)
        }
      })
    },


















    listUqcasheridOptions() {

      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [])


      // 数据权限: 用户sys_user
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, "1457254743943432961")


      listSysUserAll(search_List).then(responseData => {
        if (responseData.code == 100) {

          this.uqcasheridOptions = responseData.data

        } else {
          this.showMessage(responseData)
        }
      })
    },


















    listUqcheckeridOptions() {

      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [])


      // 数据权限: 用户sys_user
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, "1457254743943432961")


      listSysUserAll(search_List).then(responseData => {
        if (responseData.code == 100) {

          this.uqcheckeridOptions = responseData.data

        } else {
          this.showMessage(responseData)
        }
      })
    },


















    listUqfilleridOptions() {

      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [])


      // 数据权限: 用户sys_user
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, "1457254743943432961")


      listSysUserAll(search_List).then(responseData => {
        if (responseData.code == 100) {

          this.uqfilleridOptions = responseData.data

        } else {
          this.showMessage(responseData)
        }
      })
    },


















































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































    initOptions(This) {
      // 初始化自定义类型选择框选项变量











































































































      this.listUqglobalperiodidOptions()













































      this.listUqfinacialmanageridOptions()

















      this.listUqaccountantidOptions()

















      this.listUqcasheridOptions()

















      this.listUqcheckeridOptions()

















      this.listUqfilleridOptions()


















































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































    },
    onDeleteChild(tableData, index) { // 根据下标删除子表数据
      tableData.splice(index, 1)
    },
    initEditData(This) {
      return {
        uqvoucherid: '', // 凭证ID
        intvouchernum: '', // 凭证编码
        uqnumbering: '', // 凭证类型
        uqcompanyid: '', // 凭证核算单位
        intcompanyseq: null, // 流水号
        intaffix: null, // 单据数
        uqglobalperiodid: {
          varname: null,
          UQGLOBALPERIODID: null,
        },
        mnydebitsum: null, // 借方金额合计
        mnycreditsum: null, // 贷方金额合计
        uqfinacialmanagerid: {
          id: null,
          name: null,
        },
        uqaccountantid: {
          name: null,
          id: null,
        },
        uqcasherid: {
          id: null,
          name: null,
        },
        uqcheckerid: {
          id: null,
          name: null,
        },
        uqfillerid: {
          id: null,
          name: null,
        },
        dtaccountant: '', // 记账日期
        dtcasher: '', // 出纳日期
        dtchecker: '', // 审核日期
        dtfiller: '', // 制单日期
        dtaccountantsrv: '', // 记账服务器时间
        dtcashersrv: '', // 出纳服务器时间
        dtcheckersrv: '', // 审核服务器时间
        dtfillersrv: '', // 制单服务器时间
        intflag: null, // 凭证标志
        intcashflag: null, // 出纳标志
        intdeleteflag: null, // 删除标志(0是正常 1是删除)
        uqcancelid: '', // 被冲销凭证ID
        fromid: '', // 凭证接口所需字段
        submitodd: '', // 报账单号（接口调用）限用于区分暂存凭证（ 0 - 保存， 1 - 暂存）
        chargeagainstsubmitodd: '', // 冲销报账单号（接口调用）//暂时用作存储交易时间
        tenantId: '', // tenant_id
        tglGlobalPeriodsList: [],
        sysUserList: [],
        tglGroupAccountsList: [],
        tglVoucherDetailsList: [],
        tglVoucherDetailLedgerList: [],
      }
    },































































































































































































































































































































































































































































































    onAddTglGlobalPeriodsList() {
      this.editFormData.tglGlobalPeriodsList.push({






        tenantId: "", // 租户










        intyear: null, // 年份










        intmonth: null, // 月份










        varname: "", // 会计期名称










        intstatus: null, // 状态(0 停用，1 新增，2 启用)










        intproperty: null, // 属性










        dtbegin: "", // 有效开始时间










        dtend: "", // 有效结束时间










        intyearmonth: null, // 年月










        remarks: "", // 备注信息













      })
    },









































    onAddSysUserList() {
      this.editFormData.sysUserList.push({






        companyId: "", // 公司










        departmentId: "", // 部门










        name: "", // 名称










        loginName: "", // 登录账号










        loginPassword: "", // 密码










        isLocked: "0", // 禁用










        phone: "", // 手机号










        email: "", // 邮箱地址










        remarks: "", // 备注信息













      })
    },









































    onAddTglGroupAccountsList() {
      this.editFormData.tglGroupAccountsList.push({






        uqaccountsetid: "", // 科目套id










        varaccountcode: "", // 科目编码










        varaccountfullcode: "", // 科目全编码










        varaccountname: "", // 科目名称










        intproperty: null, // 科目属性










        uqtypeid: "", // 科目类别










        uqforeigncurrid: "", // 外币id










        varmeasure: "", // 计量单位










        intisledge: null, // 是否分户核算










        intiscrossledge: null, // 是否分户交叉查询










        uqpreaccountid: "", // 追朔科目ID










        uqparentid: "", // 父级科目ID










        intislastlevel: null, // 是否为末级（0不是，1是）










        intlevel: null, // 科目等级










        intflag: null, // 启停(0停用,1新建,2启用)










        varaccountfullname: "", // 科目全名称










        intisflow: 0, // 是否需要现金流量（0不需要，1可要可不要，2必须要）










        groupId: "", // group_id










        isbalance: null, // 余额方向（0-借，1-贷）













      })
    },









































    onAddTglVoucherDetailsList() {
      this.editFormData.tglVoucherDetailsList.push({






        uqvoucherdetailid: "", // 凭证明细ID










        intseq: null, // 序号










        varabstract: "", // 摘要










        uqcgsid: "", // 往来ID










        uqaccountid: "", // 会计科目ID










        mnydebit: null, // 借方金额










        mnycredit: null, // 贷方金额










        mnyfdebit: null, // MNYFDEBIT










        mnyfcredit: null, // MNYFCREDIT










        uqpaytypeid: "", // 结算方式










        vartypeno: "", // 结算号










        decnumber: null, // 数量










        mnyprice: null, // 单价










        mnyforeigncurr: null, // 外币金额










        decexchagerate: null, // 外币汇率













      })
    },









































    onAddTglVoucherDetailLedgerList() {
      this.editFormData.tglVoucherDetailLedgerList.push({






        uqvouledgerid: "", // 凭证分录分户主键ID










        uqledgerid: "", // 凭证分录分户分摊ID










        uqledgertypeid: "", // 凭证分录分户类别ID










        mnyamount: null, // 摊销金额










        mnydebit: null, // 摊销借方金额










        mnycredit: null, // 摊销贷方金额










        intseq: null, // 序号













      })
    },




















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
  mounted: function() {
    this.$nextTick(() => {
      this.$on('openViewDialog', async function(id) {
        this.action = 'view'
        this.dialogProps.title = `查看${this.dialogTitle}`
        this.editFormData = {
          ...this.initEditData(),
          ...await this.getFormById(id)
        }
        this.initOptions(this.editFormData)
        this.tabName = 'editForm'
        this.dialogProps.visible = true
      })

      this.$on('openEditDialog', async function(id) {
        this.action = 'edit'
        this.dialogProps.title = `修改${this.dialogTitle}`
        this.editFormData = {
          ...this.initEditData(),
          ...await this.getFormById(id)
        }
        this.initOptions(this.editFormData)
        this.tabName = 'editForm'
        this.dialogProps.visible = true
      })
      this.$on('openAddDialog', function() {
        this.action = 'add'
        this.dialogProps.title = `添加${this.dialogTitle}`
        this.editFormData = this.initEditData()
        this.initOptions(this.editFormData)
        this.tabName = 'editForm'
        this.dialogProps.visible = true
      })
      this.$on('openCopyDialog', async function(id) {
        this.action = 'add'
        this.dialogProps.title = `添加${this.dialogTitle}`
        this.editFormData = {
          ...this.initEditData(),
          ...await this.getFormById(id)
        }
        this.initOptions(this.editFormData)
        this.tabName = 'editForm'
        this.editFormData.id = null //把id设置为空，添加一个新的































































































































































































































































































































































































































































































        for (var i = 0; i <= this.editFormData.tglGlobalPeriodsList.length - 1; i++) {
          this.editFormData.tglGlobalPeriodsList[i].id = null
        }









































        for (var i = 0; i <= this.editFormData.sysUserList.length - 1; i++) {
          this.editFormData.sysUserList[i].id = null
        }









































        for (var i = 0; i <= this.editFormData.tglGroupAccountsList.length - 1; i++) {
          this.editFormData.tglGroupAccountsList[i].id = null
        }









































        for (var i = 0; i <= this.editFormData.tglVoucherDetailsList.length - 1; i++) {
          this.editFormData.tglVoucherDetailsList[i].id = null
        }









































        for (var i = 0; i <= this.editFormData.tglVoucherDetailLedgerList.length - 1; i++) {
          this.editFormData.tglVoucherDetailLedgerList[i].id = null
        }





















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
