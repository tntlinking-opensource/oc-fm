<template>
  <el-row class="dc-container">
    <el-dialog
      v-on:open="onDialogOpen"
      v-on:close="onDialogClose"
      v-loading="loading"
      width="90%"
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
          <!-- 卡片表 -->
          <el-tab-pane
            label="卡片表"
            name="editForm"
            class="dc-el-tab-pane_ElTabPane"
            ><el-form
              ref="editForm"
              :model="editFormData"
              label-width="100px"
              :disabled="action == 'view'"
              class="dc-el-form_ElEditForm"
            >
              <el-row>
                <el-divider content-position="left">基本字段</el-divider>
                <el-col :span="6">
                  <el-form-item
                    prop="cardCode"
                    label="卡片编号"
                    class="dc-el-form-item_AutoSerial"
                  >
                    <el-input
                      v-model="editFormData.cardCode"
                      :maxLength="20"
                      placeholder="保存后自动生成"
                      disabled
                      class="dc-el-input_AutoSerial"
                    >
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="businessDate"
                    label="建卡日期"
                    class="dc-el-form-item_SingleInput"
                  >
                    <el-input
                      v-model="editFormData.businessDate"
                      :maxLength="-1"
                      placeholder="请输入建卡日期"
                      suffix-icon="el-icon-date"
                      disabled
                      clearable
                      class="dc-el-input_SingleInput"
                    >
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="nature.id"
                    label="卡片性质"
                    class="dc-el-form-item_SelectInput"
                  >
                    <el-select
                      v-model="editFormData.nature"
                      :style="{ width: '100%' }"
                      placeholder="请选择卡片性质"
                      disabled
                      clearable
                      value-key="id"
                      filterable
                      class="dc-el-select_SelectInput"
                    >
                      <el-option
                        v-for="(item, index) in natureOptions"
                        :key="index"
                        :label="item.name"
                        :value="item"
                      ></el-option
                    ></el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="createrid"
                    label="建卡人"
                    class="dc-el-form-item_SingleInput"
                  >
                    <el-input
                      v-model="editFormData.createrid"
                      :maxLength="100"
                      placeholder="请输入建卡人"
                      disabled
                      clearable
                      class="dc-el-input_SingleInput"
                    >
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="assetCode"
                    label="资产编号"
                    :rules="[
                      {
                        required: true,
                        message: '资产编号不能为空',
                        trigger: 'blur',
                      },
                    ]"
                    class="dc-el-form-item_SingleInput"
                  >
                    <el-input
                      v-model="editFormData.assetCode"
                      :maxLength="40"
                      placeholder="请输入资产编号"
                      clearable
                      class="dc-el-input_SingleInput"
                    >
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="category.id"
                    label="资产类别"
                    :rules="[
                      {
                        required: true,
                        message: '资产类别不能为空',
                        trigger: 'change',
                      },
                    ]"
                    class="dc-el-form-item_SelectInput"
                  >
                    <el-select
                      v-model="editFormData.category"
                      :style="{ width: '100%' }"
                      placeholder="请选择资产类别"
                      clearable
                      value-key="id"
                      filterable
                      class="dc-el-select_SelectInput"
                    >
                      <el-option
                        v-for="(item, index) in categoryOptions"
                        :key="index"
                        :label="item.name"
                        :value="item"
                      ></el-option
                    ></el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="assetName"
                    label="资产名称"
                    :rules="[
                      {
                        required: true,
                        message: '资产名称不能为空',
                        trigger: 'blur',
                      },
                    ]"
                    class="dc-el-form-item_SingleInput"
                  >
                    <el-input
                      v-model="editFormData.assetName"
                      :maxLength="200"
                      placeholder="请输入资产名称"
                      clearable
                      autocomplete="new-password"
                      class="dc-el-input_SingleInput"
                    >
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="usingstatus.id"
                    label="使用状况"
                    :rules="[
                      {
                        required: true,
                        message: '使用状况不能为空',
                        trigger: 'change',
                      },
                    ]"
                    class="dc-el-form-item_SelectInput"
                  >
                    <el-select
                      v-model="editFormData.usingstatus"
                      :style="{ width: '100%' }"
                      placeholder="请选择使用状况"
                      clearable
                      value-key="id"
                      filterable
                      class="dc-el-select_SelectInput"
                    >
                      <el-option
                        v-for="(item, index) in usingstatusOptions"
                        :key="index"
                        :label="item.name"
                        :value="item"
                      ></el-option
                    ></el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="usedeptid.id"
                    label="使用部门"
                    class="dc-el-form-item_CascaderInput"
                  >
                    <el-cascader
                      ref="cascader135"
                      :options="usedeptidOptions"
                      v-model="editFormData.usedeptid.id"
                      :style="{ width: '100%' }"
                      placeholder="请选择使用部门"
                      :props="{
                        label: 'name',
                        value: 'id',
                        children: 'children',
                        checkStrictly: true,
                        emitPath: false,
                      }"
                      clearable
                      filterable
                      separator="/"
                      class="dc-el-cascader_CascaderInput"
                    ></el-cascader>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="businessdept"
                    label="所属事业部"
                    class="dc-el-form-item_SingleInput"
                  >
                    <el-input
                      v-model="editFormData.businessdept"
                      :maxLength="255"
                      placeholder="请输入所属事业部"
                      clearable
                      autocomplete="new-password"
                      class="dc-el-input_SingleInput"
                    >
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="userid.id"
                    label="责任人"
                    class="dc-el-form-item_SelectInput"
                  >
                    <el-select
                      v-model="editFormData.userid"
                      :style="{ width: '100%' }"
                      placeholder="请选择责任人"
                      clearable
                      value-key="id"
                      filterable
                      class="dc-el-select_SelectInput"
                      @change="useridOptionsChange()"
                    >
                      <el-option
                        v-for="(item, index) in useridOptions"
                        :key="index"
                        :label="item.name"
                        :value="item"
                      ></el-option
                    ></el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="employeeno"
                    label="工号"
                    class="dc-el-form-item_SingleInput"
                  >
                    <el-input
                      v-model="editFormData.employeeno"
                      :maxLength="10"
                      placeholder="请输入工号"
                      disabled
                      clearable
                      class="dc-el-input_SingleInput"
                    >
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="userAscription"
                    label="员工隶属"
                    class="dc-el-form-item_SingleInput"
                  >
                    <el-input
                      v-model="editFormData.userAscription"
                      :maxLength="255"
                      placeholder="请输入员工隶属"
                      clearable
                      autocomplete="new-password"
                      class="dc-el-input_SingleInput"
                    >
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="assetAscription"
                    label="设备隶属"
                    class="dc-el-form-item_SingleInput"
                  >
                    <el-input
                      v-model="editFormData.assetAscription"
                      :maxLength="255"
                      placeholder="请输入设备隶属"
                      clearable
                      autocomplete="new-password"
                      class="dc-el-input_SingleInput"
                    >
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="place"
                    label="所在地点"
                    class="dc-el-form-item_SingleInput"
                  >
                    <el-input
                      v-model="editFormData.place"
                      :maxLength="40"
                      placeholder="请输入所在地点"
                      clearable
                      autocomplete="new-password"
                      class="dc-el-input_SingleInput"
                    >
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="producer.id"
                    label="供应商"
                    class="dc-el-form-item_SelectInput"
                  >
                    <el-select
                      v-model="editFormData.producer"
                      :style="{ width: '100%' }"
                      placeholder="请选择供应商"
                      clearable
                      value-key="id"
                      filterable
                      class="dc-el-select_SelectInput"
                    >
                      <el-option
                        v-for="(item, index) in producerOptions"
                        :key="index"
                        :label="item.name"
                        :value="item"
                      ></el-option
                    ></el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="macAddress"
                    label="MAC地址"
                    class="dc-el-form-item_SingleInput"
                  >
                    <el-input
                      v-model="editFormData.macAddress"
                      :maxLength="255"
                      placeholder="请输入MAC地址"
                      clearable
                      autocomplete="new-password"
                      class="dc-el-input_SingleInput"
                    >
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="cardModel"
                    label="型号"
                    class="dc-el-form-item_SingleInput"
                  >
                    <el-input
                      v-model="editFormData.cardModel"
                      :maxLength="80"
                      placeholder="请输入型号"
                      clearable
                      autocomplete="new-password"
                      class="dc-el-input_SingleInput"
                    >
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="assetbrand"
                    label="品牌"
                    class="dc-el-form-item_SingleInput"
                  >
                    <el-input
                      v-model="editFormData.assetbrand"
                      :maxLength="255"
                      placeholder="请输入品牌"
                      clearable
                      autocomplete="new-password"
                      class="dc-el-input_SingleInput"
                    >
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="spec"
                    label="规格"
                    class="dc-el-form-item_SingleInput"
                  >
                    <el-input
                      v-model="editFormData.spec"
                      :maxLength="800"
                      placeholder="请输入规格"
                      clearable
                      autocomplete="new-password"
                      class="dc-el-input_SingleInput"
                    >
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="num"
                    label="数量"
                    :rules="[
                      {
                        required: true,
                        message: '数量不能为空',
                        trigger: 'blur',
                      },
                    ]"
                    class="dc-el-form-item_NumberInput"
                  >
                    <number-input
                      v-model="editFormData.num"
                      :min="0"
                      :max="99999999999"
                      :precision="0"
                      placeholder="请输入数量"
                      clearable
                      class="dc-number-input_NumberInput"
                    ></number-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="unit"
                    label="计量单位"
                    :rules="[
                      {
                        required: true,
                        message: '计量单位不能为空',
                        trigger: 'blur',
                      },
                    ]"
                    class="dc-el-form-item_SingleInput"
                  >
                    <el-input
                      v-model="editFormData.unit"
                      :maxLength="20"
                      placeholder="请输入计量单位"
                      clearable
                      class="dc-el-input_SingleInput"
                    >
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="memo"
                    label="备注"
                    class="dc-el-form-item_SingleInput"
                  >
                    <el-input
                      v-model="editFormData.memo"
                      :maxLength="200"
                      placeholder="请输入备注"
                      clearable
                      autocomplete="new-password"
                      class="dc-el-input_SingleInput"
                    >
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="enableDate"
                    label="启用日期"
                    :rules="[
                      {
                        required: true,
                        message: '启用日期不能为空',
                        trigger: 'blur',
                      },
                    ]"
                    class="dc-el-form-item_SingleInput"
                  >
                    <el-date-picker
                      v-model="editFormData.enableDate"
                      type="date"
                      class="dc-el-input_SingleInput"
                      style="width: 100%"
                      placeholder="请输入启用日期"
                      value-format="yyyy-MM-dd"
                    >
                    </el-date-picker>
                  </el-form-item>
                </el-col>
                <el-divider content-position="left">资产折旧</el-divider>
                <el-col :span="6">
                  <el-form-item
                    prop="value"
                    label="原值"
                    :rules="[
                      {
                        required: true,
                        message: '原值不能为空',
                        trigger: 'blur',
                      },
                    ]"
                    class="dc-el-form-item_NumberInput"
                  >
                    <number-input
                      v-model="editFormData.value"
                      currency="CNY"
                      :min="0"
                      :max="999999999999.99"
                      :precision="2"
                      placeholder="请输入原值"
                      clearable
                      class="dc-number-input_NumberInput"
                    ></number-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="netvalue"
                    label="净值"
                    :rules="[
                      {
                        required: true,
                        message: '净值不能为空',
                        trigger: 'blur',
                      },
                    ]"
                    class="dc-el-form-item_NumberInput"
                  >
                    <number-input
                      v-model="editFormData.netvalue"
                      currency="CNY"
                      :min="0"
                      :max="999999999999.99"
                      :precision="2"
                      placeholder="请输入净值"
                      disabled
                      clearable
                      class="dc-number-input_NumberInput"
                    ></number-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="salvage"
                    label="残值"
                    :rules="[
                      {
                        required: true,
                        message: '残值不能为空',
                        trigger: 'blur',
                      },
                    ]"
                    class="dc-el-form-item_NumberInput"
                  >
                    <number-input
                      v-model="editFormData.salvage"
                      currency="CNY"
                      :min="0"
                      :max="999999999999.99"
                      :precision="2"
                      placeholder="请输入残值"
                      disabled
                      clearable
                      class="dc-number-input_NumberInput"
                    ></number-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="salvagerate"
                    label="残值率(%)"
                    class="dc-el-form-item_NumberInput"
                  >
                    <number-input
                      v-model="editFormData.salvagerate"
                      :min="0"
                      :max="999999999999.99"
                      append="%"
                      :precision="2"
                      placeholder="请输入残值率(%)"
                      clearable
                      class="dc-number-input_NumberInput"
                    ></number-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="rate"
                    label="税率(%)"
                    class="dc-el-form-item_NumberInput"
                  >
                    <number-input
                      v-model="editFormData.rate"
                      :min="0"
                      :max="999999999999.99"
                      append="%"
                      :precision="2"
                      placeholder="请输入税率(%)"
                      clearable
                      class="dc-number-input_NumberInput"
                    ></number-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="tax"
                    label="税金"
                    class="dc-el-form-item_NumberInput"
                  >
                    <number-input
                      v-model="editFormData.tax"
                      currency="CNY"
                      :min="0"
                      :max="999999999999.99"
                      :precision="2"
                      placeholder="请输入税金"
                      disabled
                      clearable
                      class="dc-number-input_NumberInput"
                    ></number-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="accudep"
                    label="累计折旧"
                    class="dc-el-form-item_NumberInput"
                  >
                    <number-input
                      v-model="editFormData.accudep"
                      currency="CNY"
                      :min="0"
                      :max="999999999999.99"
                      :precision="2"
                      placeholder="请输入累计折旧"
                      disabled
                      clearable
                      class="dc-number-input_NumberInput"
                    ></number-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="predevaluate"
                    label="减值准备"
                    class="dc-el-form-item_NumberInput"
                  >
                    <number-input
                      v-model="editFormData.predevaluate"
                      currency="CNY"
                      :min="0"
                      :max="999999999999.99"
                      :precision="2"
                      placeholder="请输入减值准备"
                      clearable
                      class="dc-number-input_NumberInput"
                    ></number-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="depmethod.id"
                    label="折旧方法"
                    class="dc-el-form-item_SelectInput"
                  >
                    <el-select
                      v-model="editFormData.depmethod"
                      :style="{ width: '100%' }"
                      placeholder="请选择折旧方法"
                      value-key="id"
                      filterable
                      class="dc-el-select_SelectInput"
                    >
                      <el-option
                        v-for="(item, index) in depmethodOptions"
                        :key="index"
                        :label="item.name"
                        :value="item"
                      ></el-option
                    ></el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="startupDate"
                    label="购置日期"
                    :rules="[
                      {
                        required: true,
                        message: '购置日期不能为空',
                        trigger: 'blur',
                      },
                    ]"
                    class="dc-el-form-item_SingleInput"
                  >
                    <!-- <el-input
                      v-model="editFormData.startupDate"
                      :maxLength="-1"
                      placeholder="请输入购置日期"
                      suffix-icon="el-icon-date"
                      clearable
                      class="dc-el-input_SingleInput"
                    >
                    </el-input> -->
                    <el-date-picker
                      v-model="editFormData.startupDate"
                      type="date"
                      class="dc-el-input_SingleInput"
                      style="width: 100%"
                      placeholder="请输入购置日期"
                      value-format="yyyy-MM-dd"
                    >
                    </el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="naturemonth"
                    label="预计使用月数"
                    :rules="[
                      {
                        required: true,
                        message: '预计使用月数不能为空',
                        trigger: 'blur',
                      },
                    ]"
                    class="dc-el-form-item_NumberInput"
                  >
                    <number-input
                      v-model="editFormData.naturemonth"
                      :min="0"
                      :max="99999999999"
                      :precision="0"
                      placeholder="请输入预计使用月数"
                      clearable
                      class="dc-number-input_NumberInput"
                    ></number-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="servicemonth"
                    label="剩余使用月数"
                    class="dc-el-form-item_NumberInput"
                  >
                    <number-input
                      v-model="editFormData.servicemonth"
                      :min="0"
                      :max="99999999999"
                      :precision="0"
                      placeholder="请输入剩余使用月数"
                      disabled
                      clearable
                      class="dc-number-input_NumberInput"
                    ></number-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="deprate"
                    label="月折旧率(%)"
                    class="dc-el-form-item_NumberInput"
                  >
                    <number-input
                      v-model="editFormData.deprate"
                      :min="0"
                      :max="99.99"
                      append="%"
                      :precision="2"
                      placeholder="请输入月折旧率(%)"
                      disabled
                      clearable
                      class="dc-number-input_NumberInput"
                    ></number-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="depamount"
                    label="月折旧额"
                    class="dc-el-form-item_NumberInput"
                  >
                    <number-input
                      v-model="editFormData.depamount"
                      currency="CNY"
                      :min="0"
                      :max="999999999999.99"
                      :precision="2"
                      placeholder="请输入月折旧额"
                      disabled
                      clearable
                      class="dc-number-input_NumberInput"
                    ></number-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="addReason.id"
                    label="增加方式"
                    class="dc-el-form-item_SelectInput"
                  >
                    <el-select
                      v-model="editFormData.addReason"
                      :style="{ width: '100%' }"
                      placeholder="请选择增加方式"
                      clearable
                      value-key="id"
                      filterable
                      class="dc-el-select_SelectInput"
                    >
                      <el-option
                        v-for="(item, index) in addReasonOptions"
                        :key="index"
                        :label="item.name"
                        :value="item"
                      ></el-option
                    ></el-select>
                  </el-form-item>
                </el-col>
                <el-divider content-position="left">科目配置</el-divider>
                <el-col :span="6">
                  <el-form-item
                    label="固定资产科目"
                    prop="editFormData.assetAccountid.id"
                    class="dc-el-form-item_SingleInput"
                  >
                    <el-input
                      v-model="editFormData.assetAccountid.varaccountname"
                      placeholder="请选择固定资产科目"
                      show-word-limit
                      readonly
                      clearable
                      :style="{ width: '100%' }"
                    >
                      <el-button
                        icon="el-icon-search"
                        slot="append"
                        v-on:click="
                          onSearchAccount('asset_account', 'assetAccountid')
                        "
                      ></el-button>
                      <el-button
                        type="danger"
                        icon="el-icon-delete"
                        slot="append"
                        v-on:click="
                          clearAccount('asset_account', 'assetAccountid')
                        "
                      ></el-button>
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    label="累计折旧科目"
                    prop="editFormData.accudepAccountid.id"
                    class="dc-el-form-item_SingleInput"
                  >
                    <el-input
                      v-model="editFormData.accudepAccountid.varaccountname"
                      placeholder="请选择累计折旧科目"
                      show-word-limit
                      readonly
                      clearable
                      :style="{ width: '100%' }"
                    >
                      <el-button
                        icon="el-icon-search"
                        slot="append"
                        v-on:click="
                          onSearchAccount('accudep_account', 'accudepAccountid')
                        "
                      ></el-button>
                      <el-button
                        type="danger"
                        icon="el-icon-delete"
                        slot="append"
                        v-on:click="
                          clearAccount('accudep_account', 'accudepAccountid')
                        "
                      ></el-button>
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    label="固定资产减值准备科目"
                    prop="editFormData.predevaluateAccountid.id"
                    class="dc-el-form-item_SingleInput"
                  >
                    <el-input
                      v-model="
                        editFormData.predevaluateAccountid.varaccountname
                      "
                      placeholder="请选择固定资产减值准备科目"
                      show-word-limit
                      readonly
                      clearable
                      :style="{ width: '100%' }"
                    >
                      <el-button
                        icon="el-icon-search"
                        slot="append"
                        v-on:click="
                          onSearchAccount(
                            'predevaluate_account',
                            'predevaluateAccountid'
                          )
                        "
                      ></el-button>
                      <el-button
                        type="danger"
                        icon="el-icon-delete"
                        slot="append"
                        v-on:click="
                          clearAccount(
                            'predevaluate_account',
                            'predevaluateAccountid'
                          )
                        "
                      ></el-button>
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    label="折旧费用科目"
                    prop="editFormData.depcostAccountid.id"
                    class="dc-el-form-item_SingleInput"
                  >
                    <el-input
                      v-model="editFormData.depcostAccountid.varaccountname"
                      placeholder="请选择折旧费用科目"
                      show-word-limit
                      readonly
                      clearable
                      :style="{ width: '100%' }"
                    >
                      <el-button
                        icon="el-icon-search"
                        slot="append"
                        v-on:click="
                          onSearchAccount('depcost_account', 'depcostAccountid')
                        "
                      ></el-button>
                      <el-button
                        type="danger"
                        icon="el-icon-delete"
                        slot="append"
                        v-on:click="
                          clearAccount('depcost_account', 'depcostAccountid')
                        "
                      ></el-button>
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    label="减值准备对方科目"
                    prop="editFormData.predevaluateCoraccountid.id"
                    class="dc-el-form-item_SingleInput"
                  >
                    <el-input
                      v-model="
                        editFormData.predevaluateCoraccountid.varaccountname
                      "
                      placeholder="请选择减值准备对方科目"
                      show-word-limit
                      readonly
                      clearable
                      :style="{ width: '100%' }"
                    >
                      <el-button
                        icon="el-icon-search"
                        slot="append"
                        v-on:click="
                          onSearchAccount(
                            'predevaluate_coraccount',
                            'predevaluateCoraccountid'
                          )
                        "
                      ></el-button>
                      <el-button
                        type="danger"
                        icon="el-icon-delete"
                        slot="append"
                        v-on:click="
                          clearAccount(
                            'predevaluate_coraccount',
                            'predevaluateCoraccountid'
                          )
                        "
                      ></el-button>
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    prop="isproject"
                    label="是否在建工程中转"
                    class="dc-el-form-item_Switch"
                  >
                    <el-switch
                      v-model="editFormData.isproject"
                      placeholder="请选择是否在建工程中转"
                      active-value="1"
                      inactive-value="0"
                      class="dc-el-switch_Switch"
                      @change="isProjectFun"
                    ></el-switch>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    label="在建工程科目"
                    prop="editFormData.projectAccountid.id"
                    class="dc-el-form-item_SingleInput"
                  >
                    <el-input
                      v-model="editFormData.projectAccountid.varaccountname"
                      placeholder="请选择在建工程科目"
                      show-word-limit
                      readonly
                      clearable
                      :style="{ width: '100%' }"
                    >
                      <el-button
                        v-show="editFormData.isproject * 1 == 1"
                        icon="el-icon-search"
                        slot="append"
                        v-on:click="
                          onSearchAccount('project_account', 'projectAccountid')
                        "
                      ></el-button>
                      <el-button
                        v-show="editFormData.isproject * 1 == 1"
                        type="danger"
                        icon="el-icon-delete"
                        slot="append"
                        v-on:click="
                          clearAccount('project_account', 'projectAccountid')
                        "
                      ></el-button>
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item
                    label="税金科目"
                    prop="editFormData.taxAccountid.id"
                    class="dc-el-form-item_SingleInput"
                  >
                    <el-input
                      v-model="editFormData.taxAccountid.varaccountname"
                      placeholder="请选择税金科目"
                      show-word-limit
                      readonly
                      clearable
                      :style="{ width: '100%' }"
                    >
                      <el-button
                        icon="el-icon-search"
                        slot="append"
                        v-on:click="
                          onSearchAccount('tax_account', 'taxAccountid')
                        "
                      ></el-button>
                      <el-button
                        type="danger"
                        icon="el-icon-delete"
                        slot="append"
                        v-on:click="clearAccount('tax_account', 'taxAccountid')"
                      ></el-button>
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="24">
                  <el-form-item
                    prop=""
                    label="变动原因"
                    class="dc-el-form-item_SingleInput"
                  >
                    <el-input
                      v-model="editFormData.reason"
                      :maxLength="-1"
                      placeholder="请输入变动原因"
                      clearable
                      class="dc-el-input_SingleInput"
                    >
                    </el-input>
                  </el-form-item>
                </el-col>
              </el-row> </el-form
          ></el-tab-pane>
          <!-- 附属设备表 -->
          <el-tab-pane
            label="附属设备表"
            name="tfamAccessoryTable"
            class="dc-el-tab-pane_ElTabPane"
            ><el-row
              gutter="0"
              type="flex"
              justify="end"
              align="top"
              class="dc-el-row_ElRow"
              ><el-tooltip disabled class="dc-el-tooltip_Button"
                ><el-button
                  type="primary"
                  icon="el-icon-plus"
                  v-on:click="onAddTfamAccessoryList"
                  class="dc-el-button_Button"
                  >增加</el-button
                ></el-tooltip
              ></el-row
            >
            <el-form
              ref="tfamAccessoryForm"
              :model="editFormData"
              label-width="100px"
              :disabled="action == 'view'"
              class="dc-el-form_ElEditForm"
            >
              <el-row>
                <ux-grid
                  :data="editFormData.tfamAccessoryList"
                  ref="tfamAccessoryTable"
                  :currentRow="table383CurrentRow"
                  height="200px"
                  border
                  :edit-config="{ trigger: 'click', mode: 'cell' }"
                  class="dc-ux-grid_EditTable"
                >
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
                        :prop="'tfamAccessoryList.' + rowIndex + '.name'"
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.name"
                          :maxLength="100"
                          placeholder="请输入名称"
                          clearable
                          class="dc-el-input_SingleInput"
                        >
                        </el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tfamAccessoryList.' + rowIndex + '.name'"
                          label-width="0px"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.name }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="spec"
                    title="规格"
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
                        :prop="'tfamAccessoryList.' + rowIndex + '.spec'"
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.spec"
                          :maxLength="80"
                          placeholder="请输入规格"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        >
                        </el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tfamAccessoryList.' + rowIndex + '.spec'"
                          label-width="0px"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.spec }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="unit"
                    title="单位"
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
                        :prop="'tfamAccessoryList.' + rowIndex + '.unit'"
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.unit"
                          :maxLength="80"
                          placeholder="请输入单位"
                          clearable
                          class="dc-el-input_SingleInput"
                        >
                        </el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tfamAccessoryList.' + rowIndex + '.unit'"
                          label-width="0px"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.unit }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="num"
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
                        :prop="'tfamAccessoryList.' + rowIndex + '.num'"
                        label-width="0px"
                        class="dc-el-form-item_NumberInput"
                      >
                        <number-input
                          v-model="row.num"
                          :min="0"
                          :max="99999999999"
                          :precision="0"
                          placeholder="请输入数量"
                          clearable
                          class="dc-number-input_NumberInput"
                        ></number-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tfamAccessoryList.' + rowIndex + '.num'"
                          label-width="0px"
                          class="dc-el-form-item_NumberInput"
                        >
                          {{
                            row.num
                              ? "" +
                                "" +
                                Number(row.num)
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
                    field="money"
                    title="金额"
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
                        :prop="'tfamAccessoryList.' + rowIndex + '.money'"
                        label-width="0px"
                        class="dc-el-form-item_NumberInput"
                      >
                        <number-input
                          v-model="row.money"
                          :min="0"
                          :max="999999999999.99"
                          :precision="2"
                          placeholder="请输入金额"
                          clearable
                          class="dc-number-input_NumberInput"
                        ></number-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tfamAccessoryList.' + rowIndex + '.money'"
                          label-width="0px"
                          class="dc-el-form-item_NumberInput"
                        >
                          {{
                            row.money
                              ? "" +
                                "" +
                                Number(row.money)
                                  .toFixed(2)
                                  .replace(/(\d)(?=(\d{3})+\.)/g, "$1,") +
                                ""
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
                        v-on:click="
                          onDeleteChild(
                            editFormData.tfamAccessoryList,
                            scope.rowIndex
                          )
                        "
                        type="danger"
                        content="删除"
                        placement="top"
                        icon-name="el-icon-delete"
                        class="dc-OperationIcon_IconButton"
                      ></OperationIcon>
                      <OperationIcon
                        type="primary"
                        content="提示信息"
                        placement="bottom"
                        icon-name="el-icon-edit-outline"
                        class="dc-OperationIcon_IconButton"
                      ></OperationIcon>
                    </template>
                  </ux-table-column>
                </ux-grid>
              </el-row> </el-form
          ></el-tab-pane>
          <!-- 折旧明细表 -->
          <el-tab-pane
            v-if="action === 'view'"
            label="折旧明细表"
            name="tfamDepsubTable"
            class="dc-el-tab-pane_ElTabPane"
            ><el-row
              v-if="action === 'view'"
              gutter="0"
              type="flex"
              justify="end"
              align="top"
              class="dc-el-row_ElRow"
              ><el-tooltip disabled class="dc-el-tooltip_Button"
                ><el-button
                  type="primary"
                  icon="el-icon-plus"
                  v-on:click="onAddTfamDepsubList"
                  class="dc-el-button_Button"
                ></el-button></el-tooltip
            ></el-row>
            <el-form
              ref="tfamDepsubForm"
              :model="editFormData"
              label-width="100px"
              :disabled="action == 'view'"
              class="dc-el-form_ElEditForm"
            >
              <el-row>
                <ux-grid
                  :data="editFormData.tfamDepsubList"
                  ref="tfamDepsubTable"
                  :currentRow="table230CurrentRow"
                  height="200px"
                  border
                  :edit-config="{ trigger: 'click', mode: 'cell' }"
                  class="dc-ux-grid_EditTable"
                >
                  <ux-table-column
                    field="year"
                    title="折旧年份"
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
                      <span style="color: #f56c6c">*</span>{{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        :prop="'tfamDepsubList.' + rowIndex + '.year'"
                        label-width="0px"
                        :rules="[
                          {
                            required: true,
                            message: '折旧年份不能为空',
                            trigger: 'blur',
                          },
                        ]"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.year"
                          :maxLength="4"
                          placeholder="请输入折旧年份"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        >
                        </el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tfamDepsubList.' + rowIndex + '.year'"
                          label-width="0px"
                          :rules="[
                            {
                              required: true,
                              message: '折旧年份不能为空',
                              trigger: 'blur',
                            },
                          ]"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.year }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="month"
                    title="折旧月份"
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
                      <span style="color: #f56c6c">*</span>{{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        :prop="'tfamDepsubList.' + rowIndex + '.month'"
                        label-width="0px"
                        :rules="[
                          {
                            required: true,
                            message: '折旧月份不能为空',
                            trigger: 'blur',
                          },
                        ]"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.month"
                          :maxLength="2"
                          placeholder="请输入折旧月份"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        >
                        </el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tfamDepsubList.' + rowIndex + '.month'"
                          label-width="0px"
                          :rules="[
                            {
                              required: true,
                              message: '折旧月份不能为空',
                              trigger: 'blur',
                            },
                          ]"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.month }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="date"
                    title="折旧日期"
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
                      <span style="color: #f56c6c">*</span>{{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        :prop="'tfamDepsubList.' + rowIndex + '.date'"
                        label-width="0px"
                        :rules="[
                          {
                            required: true,
                            message: '折旧日期不能为空',
                            trigger: 'blur',
                          },
                        ]"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.date"
                          :maxLength="-1"
                          placeholder="请输入折旧日期"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        >
                        </el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tfamDepsubList.' + rowIndex + '.date'"
                          label-width="0px"
                          :rules="[
                            {
                              required: true,
                              message: '折旧日期不能为空',
                              trigger: 'blur',
                            },
                          ]"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.date }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="depamount"
                    title="折旧金额"
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
                      <span style="color: #f56c6c">*</span>{{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        :prop="'tfamDepsubList.' + rowIndex + '.depamount'"
                        label-width="0px"
                        :rules="[
                          {
                            required: true,
                            message: '折旧金额不能为空',
                            trigger: 'blur',
                          },
                        ]"
                        class="dc-el-form-item_NumberInput"
                      >
                        <number-input
                          v-model="row.depamount"
                          :min="0"
                          :max="999999999999.99"
                          :precision="2"
                          placeholder="请输入折旧金额"
                          clearable
                          class="dc-number-input_NumberInput"
                        ></number-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tfamDepsubList.' + rowIndex + '.depamount'"
                          label-width="0px"
                          :rules="[
                            {
                              required: true,
                              message: '折旧金额不能为空',
                              trigger: 'blur',
                            },
                          ]"
                          class="dc-el-form-item_NumberInput"
                        >
                          {{
                            row.depamount
                              ? "" +
                                "" +
                                Number(row.depamount)
                                  .toFixed(2)
                                  .replace(/(\d)(?=(\d{3})+\.)/g, "$1,") +
                                ""
                              : null
                          }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="depType"
                    title="折旧类型（1月折旧，2补提，3冲减）"
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
                      <span style="color: #f56c6c">*</span>{{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        :prop="'tfamDepsubList.' + rowIndex + '.depType'"
                        label-width="0px"
                        :rules="[
                          {
                            required: true,
                            message:
                              '折旧类型（1月折旧，2补提，3冲减）不能为空',
                            trigger: 'blur',
                          },
                        ]"
                        class="dc-el-form-item_NumberInput"
                      >
                        <number-input
                          v-model="row.depType"
                          :min="0"
                          :max="99999999999"
                          :precision="0"
                          placeholder="请输入折旧类型（1月折旧，2补提，3冲减）"
                          clearable
                          class="dc-number-input_NumberInput"
                        ></number-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tfamDepsubList.' + rowIndex + '.depType'"
                          label-width="0px"
                          :rules="[
                            {
                              required: true,
                              message:
                                '折旧类型（1月折旧，2补提，3冲减）不能为空',
                              trigger: 'blur',
                            },
                          ]"
                          class="dc-el-form-item_NumberInput"
                        >
                          {{
                            row.depType
                              ? "" +
                                "" +
                                Number(row.depType)
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
                    field="resaon"
                    title="补提冲减原因"
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
                        :prop="'tfamDepsubList.' + rowIndex + '.resaon'"
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.resaon"
                          :maxLength="80"
                          placeholder="请输入补提冲减原因"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        >
                        </el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tfamDepsubList.' + rowIndex + '.resaon'"
                          label-width="0px"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.resaon }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="value"
                    title="原值"
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
                      <span style="color: #f56c6c">*</span>{{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        :prop="'tfamDepsubList.' + rowIndex + '.value'"
                        label-width="0px"
                        :rules="[
                          {
                            required: true,
                            message: '原值不能为空',
                            trigger: 'blur',
                          },
                        ]"
                        class="dc-el-form-item_NumberInput"
                      >
                        <number-input
                          v-model="row.value"
                          :min="0"
                          :max="999999999999.99"
                          :precision="2"
                          placeholder="请输入原值"
                          clearable
                          class="dc-number-input_NumberInput"
                        ></number-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tfamDepsubList.' + rowIndex + '.value'"
                          label-width="0px"
                          :rules="[
                            {
                              required: true,
                              message: '原值不能为空',
                              trigger: 'blur',
                            },
                          ]"
                          class="dc-el-form-item_NumberInput"
                        >
                          {{
                            row.value
                              ? "" +
                                "" +
                                Number(row.value)
                                  .toFixed(2)
                                  .replace(/(\d)(?=(\d{3})+\.)/g, "$1,") +
                                ""
                              : null
                          }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="accudep"
                    title="累计折旧"
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
                      <span style="color: #f56c6c">*</span>{{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        :prop="'tfamDepsubList.' + rowIndex + '.accudep'"
                        label-width="0px"
                        :rules="[
                          {
                            required: true,
                            message: '累计折旧不能为空',
                            trigger: 'blur',
                          },
                        ]"
                        class="dc-el-form-item_NumberInput"
                      >
                        <number-input
                          v-model="row.accudep"
                          :min="0"
                          :max="999999999999.99"
                          :precision="2"
                          placeholder="请输入累计折旧"
                          clearable
                          class="dc-number-input_NumberInput"
                        ></number-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tfamDepsubList.' + rowIndex + '.accudep'"
                          label-width="0px"
                          :rules="[
                            {
                              required: true,
                              message: '累计折旧不能为空',
                              trigger: 'blur',
                            },
                          ]"
                          class="dc-el-form-item_NumberInput"
                        >
                          {{
                            row.accudep
                              ? "" +
                                "" +
                                Number(row.accudep)
                                  .toFixed(2)
                                  .replace(/(\d)(?=(\d{3})+\.)/g, "$1,") +
                                ""
                              : null
                          }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="netvalue"
                    title="净值"
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
                      <span style="color: #f56c6c">*</span>{{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        :prop="'tfamDepsubList.' + rowIndex + '.netvalue'"
                        label-width="0px"
                        :rules="[
                          {
                            required: true,
                            message: '净值不能为空',
                            trigger: 'blur',
                          },
                        ]"
                        class="dc-el-form-item_NumberInput"
                      >
                        <number-input
                          v-model="row.netvalue"
                          :min="0"
                          :max="999999999999.99"
                          :precision="2"
                          placeholder="请输入净值"
                          clearable
                          class="dc-number-input_NumberInput"
                        ></number-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tfamDepsubList.' + rowIndex + '.netvalue'"
                          label-width="0px"
                          :rules="[
                            {
                              required: true,
                              message: '净值不能为空',
                              trigger: 'blur',
                            },
                          ]"
                          class="dc-el-form-item_NumberInput"
                        >
                          {{
                            row.netvalue
                              ? "" +
                                "" +
                                Number(row.netvalue)
                                  .toFixed(2)
                                  .replace(/(\d)(?=(\d{3})+\.)/g, "$1,") +
                                ""
                              : null
                          }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="vouformat"
                    title="凭证字"
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
                        :prop="'tfamDepsubList.' + rowIndex + '.vouformat'"
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.vouformat"
                          :maxLength="50"
                          placeholder="请输入凭证字"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        >
                        </el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tfamDepsubList.' + rowIndex + '.vouformat'"
                          label-width="0px"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.vouformat }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="voucherid"
                    title="凭证id"
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
                        :prop="'tfamDepsubList.' + rowIndex + '.voucherid'"
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.voucherid"
                          :maxLength="100"
                          placeholder="请输入凭证id"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        >
                        </el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tfamDepsubList.' + rowIndex + '.voucherid'"
                          label-width="0px"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.voucherid }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="abstracts"
                    title="凭证摘要"
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
                        :prop="'tfamDepsubList.' + rowIndex + '.abstracts'"
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.abstracts"
                          :maxLength="100"
                          placeholder="请输入凭证摘要"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        >
                        </el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tfamDepsubList.' + rowIndex + '.abstracts'"
                          label-width="0px"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.abstracts }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="creatorid"
                    title="操作人（用户表id）"
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
                      <span style="color: #f56c6c">*</span>{{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        :prop="'tfamDepsubList.' + rowIndex + '.creatorid'"
                        label-width="0px"
                        :rules="[
                          {
                            required: true,
                            message: '操作人（用户表id）不能为空',
                            trigger: 'blur',
                          },
                        ]"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.creatorid"
                          :maxLength="100"
                          placeholder="请输入操作人（用户表id）"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        >
                        </el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tfamDepsubList.' + rowIndex + '.creatorid'"
                          label-width="0px"
                          :rules="[
                            {
                              required: true,
                              message: '操作人（用户表id）不能为空',
                              trigger: 'blur',
                            },
                          ]"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.creatorid }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="createTime"
                    title="操作日期"
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
                      <span style="color: #f56c6c">*</span>{{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        :prop="'tfamDepsubList.' + rowIndex + '.createTime'"
                        label-width="0px"
                        :rules="[
                          {
                            required: true,
                            message: '操作日期不能为空',
                            trigger: 'blur',
                          },
                        ]"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.createTime"
                          :maxLength="-1"
                          placeholder="请输入操作日期"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        >
                        </el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tfamDepsubList.' + rowIndex + '.createTime'"
                          label-width="0px"
                          :rules="[
                            {
                              required: true,
                              message: '操作日期不能为空',
                              trigger: 'blur',
                            },
                          ]"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.createTime }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="companyid"
                    title="公司id（后期多公司备用）"
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
                        :prop="'tfamDepsubList.' + rowIndex + '.companyid'"
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.companyid"
                          :maxLength="100"
                          placeholder="请输入公司id（后期多公司备用）"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        >
                        </el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tfamDepsubList.' + rowIndex + '.companyid'"
                          label-width="0px"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.companyid }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="workloan"
                    title="工作量（工作量法每月折旧的工作量）"
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
                        :prop="'tfamDepsubList.' + rowIndex + '.workloan'"
                        label-width="0px"
                        class="dc-el-form-item_NumberInput"
                      >
                        <number-input
                          v-model="row.workloan"
                          :min="0"
                          :max="999999999999.99"
                          :precision="2"
                          placeholder="请输入工作量（工作量法每月折旧的工作量）"
                          clearable
                          class="dc-number-input_NumberInput"
                        ></number-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tfamDepsubList.' + rowIndex + '.workloan'"
                          label-width="0px"
                          class="dc-el-form-item_NumberInput"
                        >
                          {{
                            row.workloan
                              ? "" +
                                "" +
                                Number(row.workloan)
                                  .toFixed(2)
                                  .replace(/(\d)(?=(\d{3})+\.)/g, "$1,") +
                                ""
                              : null
                          }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="depmethod"
                    title="折旧方法"
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
                        :prop="'tfamDepsubList.' + rowIndex + '.depmethod'"
                        label-width="0px"
                        class="dc-el-form-item_NumberInput"
                      >
                        <number-input
                          v-model="row.depmethod"
                          :min="0"
                          :max="99999999999"
                          :precision="0"
                          placeholder="请输入折旧方法"
                          clearable
                          class="dc-number-input_NumberInput"
                        ></number-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tfamDepsubList.' + rowIndex + '.depmethod'"
                          label-width="0px"
                          class="dc-el-form-item_NumberInput"
                        >
                          {{
                            row.depmethod
                              ? "" +
                                "" +
                                Number(row.depmethod)
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
                    field="dbflag"
                    title="双倍余额法是否变动（1-已变动，0-没有变动）双倍余额法有效"
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
                        :prop="'tfamDepsubList.' + rowIndex + '.dbflag'"
                        label-width="0px"
                        class="dc-el-form-item_NumberInput"
                      >
                        <number-input
                          v-model="row.dbflag"
                          :min="0"
                          :max="99999999999"
                          :precision="0"
                          placeholder="请输入双倍余额法是否变动（1-已变动，0-没有变动）双倍余额法有效"
                          clearable
                          class="dc-number-input_NumberInput"
                        ></number-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tfamDepsubList.' + rowIndex + '.dbflag'"
                          label-width="0px"
                          class="dc-el-form-item_NumberInput"
                        >
                          {{
                            row.dbflag
                              ? "" +
                                "" +
                                Number(row.dbflag)
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
                    field="usingstatus"
                    title="usingstatus"
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
                        :prop="'tfamDepsubList.' + rowIndex + '.usingstatus'"
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.usingstatus"
                          :maxLength="20"
                          placeholder="请输入usingstatus"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        >
                        </el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tfamDepsubList.' + rowIndex + '.usingstatus'"
                          label-width="0px"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.usingstatus }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="companyId"
                    title="company_id"
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
                      <span style="color: #f56c6c">*</span>{{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        :prop="'tfamDepsubList.' + rowIndex + '.companyId'"
                        label-width="0px"
                        :rules="[
                          {
                            required: true,
                            message: 'company_id不能为空',
                            trigger: 'blur',
                          },
                        ]"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.companyId"
                          :maxLength="100"
                          placeholder="请输入company_id"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        >
                        </el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tfamDepsubList.' + rowIndex + '.companyId'"
                          label-width="0px"
                          :rules="[
                            {
                              required: true,
                              message: 'company_id不能为空',
                              trigger: 'blur',
                            },
                          ]"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.companyId }}
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
                        :prop="'tfamDepsubList.' + rowIndex + '.remarks'"
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
                        >
                        </el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'tfamDepsubList.' + rowIndex + '.remarks'"
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
                        v-on:click="
                          onDeleteChild(
                            editFormData.tfamDepsubList,
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
              </el-row> </el-form
          ></el-tab-pane>
          <!-- 变动记录 -->
          <el-tab-pane
            v-if="action === 'view'"
            label="变动记录"
            name="tfamAssetTransferTable"
            class="dc-el-tab-pane_ElTabPane"
          >
            <!-- <el-row
              v-if="action === 'view'"
              gutter="0"
              type="flex"
              justify="end"
              align="top"
              class="dc-el-row_ElRow"
              ><el-tooltip disabled class="dc-el-tooltip_Button"
                ><el-button
                  type="primary"
                  icon="el-icon-plus"
                  v-on:click="onAddTfamAssetTransferList"
                  class="dc-el-button_Button"
                ></el-button></el-tooltip
            ></el-row> -->
            <el-form
              ref="tfamAssetTransferForm"
              :model="editFormData"
              label-width="100px"
              :disabled="action == 'view'"
              class="dc-el-form_ElEditForm"
            >
              <el-row>
                <ux-grid
                  :data="editFormData.tfamAssetTransferList"
                  ref="tfamAssetTransferTable"
                  :currentRow="table343CurrentRow"
                  height="200px"
                  border
                  :edit-config="{ trigger: 'click', mode: 'cell' }"
                  class="dc-ux-grid_EditTable"
                >
                  <ux-table-column
                    field="beforeTransferOwner"
                    title="调拨前责任人"
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
                          'tfamAssetTransferList.' +
                          rowIndex +
                          '.beforeTransferOwner'
                        "
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.beforeTransferOwner"
                          :maxLength="255"
                          placeholder="请输入调拨前责任人"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        >
                        </el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="
                            'tfamAssetTransferList.' +
                            rowIndex +
                            '.beforeTransferOwner'
                          "
                          label-width="0px"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.beforeTransferOwner }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="afterTransferOwner"
                    title="调拨后责任人"
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
                          'tfamAssetTransferList.' +
                          rowIndex +
                          '.afterTransferOwner'
                        "
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.afterTransferOwner"
                          :maxLength="255"
                          placeholder="请输入调拨后责任人"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        >
                        </el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="
                            'tfamAssetTransferList.' +
                            rowIndex +
                            '.afterTransferOwner'
                          "
                          label-width="0px"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.afterTransferOwner }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="beforeTransferDepartment"
                    title="调拨前使用部门"
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
                          'tfamAssetTransferList.' +
                          rowIndex +
                          '.beforeTransferDepartment'
                        "
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.beforeTransferDepartment"
                          :maxLength="255"
                          placeholder="请输入调拨前使用部门"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        >
                        </el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="
                            'tfamAssetTransferList.' +
                            rowIndex +
                            '.beforeTransferDepartment'
                          "
                          label-width="0px"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.beforeTransferDepartment }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="afterTransferDepartment"
                    title="调拨后使用部门"
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
                          'tfamAssetTransferList.' +
                          rowIndex +
                          '.afterTransferDepartment'
                        "
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.afterTransferDepartment"
                          :maxLength="255"
                          placeholder="请输入调拨后使用部门"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        >
                        </el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="
                            'tfamAssetTransferList.' +
                            rowIndex +
                            '.afterTransferDepartment'
                          "
                          label-width="0px"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.afterTransferDepartment }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="transferReason"
                    title="调拨原因"
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
                          'tfamAssetTransferList.' +
                          rowIndex +
                          '.transferReason'
                        "
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.transferReason"
                          :maxLength="400"
                          placeholder="请输入调拨原因"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        >
                        </el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="
                            'tfamAssetTransferList.' +
                            rowIndex +
                            '.transferReason'
                          "
                          label-width="0px"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.transferReason }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="companyId"
                    title="租户id"
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
                          'tfamAssetTransferList.' + rowIndex + '.companyId'
                        "
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.companyId"
                          :maxLength="100"
                          placeholder="请输入租户id"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        >
                        </el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="
                            'tfamAssetTransferList.' + rowIndex + '.companyId'
                          "
                          label-width="0px"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.companyId }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="transferDate"
                    title="调拨日期"
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
                      <span style="color: #f56c6c">*</span>{{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        :prop="
                          'tfamAssetTransferList.' + rowIndex + '.transferDate'
                        "
                        label-width="0px"
                        :rules="[
                          {
                            required: true,
                            message: '调拨日期不能为空',
                            trigger: 'blur',
                          },
                        ]"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.transferDate"
                          :maxLength="-1"
                          placeholder="请输入调拨日期"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        >
                        </el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="
                            'tfamAssetTransferList.' +
                            rowIndex +
                            '.transferDate'
                          "
                          label-width="0px"
                          :rules="[
                            {
                              required: true,
                              message: '调拨日期不能为空',
                              trigger: 'blur',
                            },
                          ]"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.transferDate }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="transferCreatorId"
                    title="操作人id"
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
                          'tfamAssetTransferList.' +
                          rowIndex +
                          '.transferCreatorId'
                        "
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.transferCreatorId"
                          :maxLength="255"
                          placeholder="请输入操作人id"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        >
                        </el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="
                            'tfamAssetTransferList.' +
                            rowIndex +
                            '.transferCreatorId'
                          "
                          label-width="0px"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.transferCreatorId }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="transferCreatorName"
                    title="操作人姓名"
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
                          'tfamAssetTransferList.' +
                          rowIndex +
                          '.transferCreatorName'
                        "
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.transferCreatorName"
                          :maxLength="255"
                          placeholder="请输入操作人姓名"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        >
                        </el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="
                            'tfamAssetTransferList.' +
                            rowIndex +
                            '.transferCreatorName'
                          "
                          label-width="0px"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.transferCreatorName }}
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
                        :prop="'tfamAssetTransferList.' + rowIndex + '.remarks'"
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
                        >
                        </el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="
                            'tfamAssetTransferList.' + rowIndex + '.remarks'
                          "
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
                        v-on:click="
                          onDeleteChild(
                            editFormData.tfamAssetTransferList,
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
              </el-row> </el-form
          ></el-tab-pane>
          <!-- 实物图片 -->
          <el-tab-pane
            label="实物图片"
            name="tfamAssetCheckimageTable"
            class="dc-el-tab-pane_ElTabPane"
            ><el-row
              gutter="0"
              type="flex"
              justify="end"
              align="top"
              class="dc-el-row_ElRow"
              ><el-tooltip disabled class="dc-el-tooltip_Button"
                ><el-button
                  type="primary"
                  icon="el-icon-plus"
                  v-on:click="onAddTfamAssetCheckimageList"
                  class="dc-el-button_Button"
                ></el-button></el-tooltip
            ></el-row>
            <el-form
              ref="tfamAssetCheckimageForm"
              :model="editFormData"
              label-width="100px"
              :disabled="action == 'view'"
              class="dc-el-form_ElEditForm"
            >
              <el-row>
                <ux-grid
                  :data="editFormData.tfamAssetCheckimageList"
                  ref="tfamAssetCheckimageTable"
                  :currentRow="table303CurrentRow"
                  height="200px"
                  border
                  :edit-config="{ trigger: 'click', mode: 'cell' }"
                  class="dc-ux-grid_EditTable"
                >
                  <ux-table-column
                    field="cardNo"
                    title="卡片编号"
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
                          'tfamAssetCheckimageList.' + rowIndex + '.cardNo'
                        "
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.cardNo"
                          :maxLength="30"
                          placeholder="请输入卡片编号"
                          clearable
                          class="dc-el-input_SingleInput"
                        >
                        </el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="
                            'tfamAssetCheckimageList.' + rowIndex + '.cardNo'
                          "
                          label-width="0px"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.cardNo }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="creator"
                    title="上传人"
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
                          'tfamAssetCheckimageList.' + rowIndex + '.creator'
                        "
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.creator"
                          :maxLength="255"
                          placeholder="请输入上传人"
                          clearable
                          class="dc-el-input_SingleInput"
                        >
                        </el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="
                            'tfamAssetCheckimageList.' + rowIndex + '.creator'
                          "
                          label-width="0px"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.creator }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="createReason"
                    title="上传原因"
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
                          'tfamAssetCheckimageList.' +
                          rowIndex +
                          '.createReason'
                        "
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.createReason"
                          :maxLength="255"
                          placeholder="请输入上传原因"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        >
                        </el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="
                            'tfamAssetCheckimageList.' +
                            rowIndex +
                            '.createReason'
                          "
                          label-width="0px"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.createReason }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="createTime"
                    title="上传时间"
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
                          'tfamAssetCheckimageList.' + rowIndex + '.createTime'
                        "
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.createTime"
                          :maxLength="-1"
                          placeholder="请输入上传时间"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        >
                        </el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="
                            'tfamAssetCheckimageList.' +
                            rowIndex +
                            '.createTime'
                          "
                          label-width="0px"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.createTime }}
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
                            editFormData.tfamAssetCheckimageList,
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
              </el-row> </el-form
          ></el-tab-pane>
          <!-- 存放资产二维码 -->
          <!-- <el-tab-pane
            v-if="action !== 'add'"
            label="存放资产二维码"
            name="tfamAsset2qrcodeTable"
            class="dc-el-tab-pane_ElTabPane"
            ><el-row
              v-if="action != 'view'"
              gutter="0"
              type="flex"
              justify="end"
              align="top"
              class="dc-el-row_ElRow"
              ><el-tooltip disabled class="dc-el-tooltip_Button"
                ><el-button
                  type="primary"
                  icon="el-icon-plus"
                  v-on:click="onAddTfamAsset2qrcodeList"
                  class="dc-el-button_Button"
                ></el-button></el-tooltip
            ></el-row>
            <el-form
              ref="tfamAsset2qrcodeForm"
              :model="editFormData"
              label-width="100px"
              :disabled="action == 'view'"
              class="dc-el-form_ElEditForm"
            >
              <el-row>
                <ux-grid
                  :data="editFormData.tfamAsset2qrcodeList"
                  ref="tfamAsset2qrcodeTable"
                  :currentRow="table411CurrentRow"
                  height="200px"
                  border
                  :edit-config="{ trigger: 'click', mode: 'cell' }"
                  class="dc-ux-grid_EditTable"
                >
                  <ux-table-column
                    field="qrcodePath"
                    title="二维码路径"
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
                      <span style="color: #f56c6c">*</span>{{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        :prop="
                          'tfamAsset2qrcodeList.' + rowIndex + '.qrcodePath'
                        "
                        label-width="0px"
                        :rules="[
                          {
                            required: true,
                            message: '二维码路径不能为空',
                            trigger: 'blur',
                          },
                        ]"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.qrcodePath"
                          :maxLength="255"
                          placeholder="请输入二维码路径"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        >
                        </el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="
                            'tfamAsset2qrcodeList.' + rowIndex + '.qrcodePath'
                          "
                          label-width="0px"
                          :rules="[
                            {
                              required: true,
                              message: '二维码路径不能为空',
                              trigger: 'blur',
                            },
                          ]"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.qrcodePath }}
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
                        :prop="'tfamAsset2qrcodeList.' + rowIndex + '.remarks'"
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
                        >
                        </el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="
                            'tfamAsset2qrcodeList.' + rowIndex + '.remarks'
                          "
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
                    <template>
                      <span>操作</span>
                    </template>
                    <template slot-scope="scope">
                      <OperationIcon
                        v-on:click="
                          onDeleteChild(
                            editFormData.tfamAsset2qrcodeList,
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
              </el-row> </el-form
          ></el-tab-pane> -->
          <!-- 生命周期 -->
          <el-tab-pane
            label="生命周期"
            name="tfamAssetHistroyTable"
            class="dc-el-tab-pane_ElTabPane"
          >
            <div class="lifecycle" v-if="lifeObj">
              <el-timeline>
                <el-timeline-item placement="top">
                  <el-card>
                    <h3>
                      {{
                        lifeObj.endTime ? lifeObj.endTime.slice(0, 10) : ""
                      }}
                      该资产到达可使用期限
                    </h3>
                  </el-card>
                </el-timeline-item>
                <el-timeline-item
                  v-for="(item, index) in lifeObj.alters"
                  :key="index"
                  :timestamp="item.date.slice(0, 10)"
                  placement="top"
                >
                  <el-card>
                    <h4>{{ "资产" + item.reason }}</h4>
                    <el-table :data="[item]">
                      <el-table-column property="reason" label="变动内容">
                      </el-table-column>
                      <el-table-column
                        property="oldValue"
                        label="变动前值"
                      ></el-table-column>
                      <el-table-column
                        property="newValue"
                        label="变动后值"
                      ></el-table-column>
                    </el-table>
                  </el-card>
                </el-timeline-item>
              </el-timeline>
            </div> </el-tab-pane
        ></el-tabs>
      </el-row>
      <span slot="footer" class="dialog-footer">
        <el-button
          v-on:click="onSubmit"
          type="primary"
          v-show="action != 'view'"
        >
          保存
        </el-button>
        <el-button v-on:click="onDialogClose" v-if="action != 'view'">
          取消
        </el-button>
        <el-button v-on:click="onDialogClose" v-if="action == 'view'">
          关闭
        </el-button>
      </span>
    </el-dialog>
    <AccountQuery ref="accountQuery" @onGetData="getCheckData"></AccountQuery>
  </el-row>
</template>
<script>
import { validatenull } from "@/utils/validate";
import { dateMonths, nowDate } from "@/utils/format.js";
/** 根据用户界面配置import组件 结束 */
import { listTfamNatureAll } from "@/api/fixasset/nature/tfamNature.js";
import { listTfamCategoryAll } from "@/api/fixasset/category/tfamCategory.js";
import { listTfamUsingstatusAll } from "@/api/fixasset/usingstatus/tfamUsingstatus.js";
import { treeDepartment } from "@/api/org/department.js";
import { listUserAll } from "@/api/admin/user.js";
import { listTfamSupplierAll } from "@/api/fixasset/producer/tfamSupplier.js";
import { listTfamDepmethodAll } from "@/api/fixasset/depmethod/tfamDepmethod.js";
import { listTfamChangeWayAll } from "@/api/fixasset/changeway/tfamChangeWay.js";
// import { listTglAccountsAll } from "@/api/vouchermanager/InitLedgePeriod/tglAccounts.js";
import {
  listTfamDefaultSettingsDefault,
  lifeCircle,
} from "@/api/fixasset/asset/tfamAssetTransfer.js";
/** 根据用户界面配置import组件 结束 */
import {
  getTfamAssetById,
  saveTfamAsset,
} from "@/api/fixasset/asset/tfamAsset";
import BaseUI from "@/views/components/baseUI";
import OperationIcon from "@/components/OperationIcon";
import AccountQuery from "@/views/components/AccountingAccountQuery";
export default {
  extends: BaseUI,
  name: "tfamAsset-form",
  components: {
    /** 根据用户界面配置组件 开始 */
    /** 根据用户界面配置组件 结束 */
    OperationIcon,
    AccountQuery,
  },
  data() {
    return {
      openAccountQuery: "",
      openAccountKey: "",
      defaultObj: {
        asset_account: undefined, // 固定资产折旧
        accudep_account: undefined, // 累计折旧科目
        predevaluate_account: undefined, // 固定资产减值准备科目
        depcost_account: undefined, // 折旧费用科目
        predevaluate_coraccount: undefined, // 减值准备对方科目
        project_account: undefined, // 在建工程科目
        tax_account: undefined, // 税金科目
      },
      lifeObj: null,
      /** 根据用户界面配置生成data数据 开始 */
      editFormData: this.initEditData(),
      // 对话框属性变量
      dialogProps: {
        visible: false,
        title: "-资产卡片",
      },
      dialogTitle: "-资产卡片",
      // 选项变量
      // 卡片性质选项
      natureOptions: [],
      // 资产类别选项
      categoryOptions: [],
      // 使用状况选项
      usingstatusOptions: [],
      // 使用部门选项
      usedeptidOptions: [],
      // 责任人选项
      useridOptions: [],
      // 供应商选项
      producerOptions: [],
      // 折旧方法选项
      depmethodOptions: [],
      // 增加方式选项
      addReasonOptions: [],
      table230CurrentRow: {}, // 当前行
      table303CurrentRow: {}, // 当前行
      table343CurrentRow: {}, // 当前行
      table383CurrentRow: {}, // 当前行
      table411CurrentRow: {}, // 当前行
      tabName: "editForm", // tab标签页
      // 窗口操作类型 view/edit/add
      action: "",
    };
  },
  props: {
    // 权限
    permission: {
      type: Object,
    },
  },
  computed: {},
  methods: {
    onSubmit() {
      console.log(this.editFormData, "提交数据1");
      let validFlag = true;
      // // 折旧明细表
      // this.$refs["tfamDepsubForm"].validate((valid) => {
      //   if (valid) {
      //   } else {
      //     this.tabName = "tfamDepsubTable";
      //     validFlag = false;
      //     return false;
      //   }
      // });
      // 实物图片
      this.$refs["tfamAssetCheckimageForm"].validate((valid) => {
        if (valid) {
        } else {
          this.tabName = "tfamAssetCheckimageTable";
          validFlag = false;
          return false;
        }
      });
      // // 变动记录
      // this.$refs["tfamAssetTransferForm"].validate((valid) => {
      //   if (valid) {
      //   } else {
      //     this.tabName = "tfamAssetTransferTable";
      //     validFlag = false;
      //     return false;
      //   }
      // });
      // 附属设备表
      this.$refs["tfamAccessoryForm"].validate((valid) => {
        if (valid) {
        } else {
          this.tabName = "tfamAccessoryTable";
          validFlag = false;
          return false;
        }
      });
      // // 存放资产二维码
      // this.$refs["tfamAsset2qrcodeForm"].validate((valid) => {
      //   if (valid) {
      //   } else {
      //     this.tabName = "tfamAsset2qrcodeTable";
      //     validFlag = false;
      //     return false;
      //   }
      // });
      // 卡片表
      this.$refs["editForm"].validate((valid) => {
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
      console.log(this.editFormData, "提交数据");
      saveTfamAsset(this.editFormData)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.dialogProps.visible = false;
            this.showMessage({ type: "success", msg: "保存成功" });
            this.$emit("save-finished");
          } else {
            this.showMessage(responseData);
          }
          this.resetLoad();
        })
        .catch((error) => {
          this.outputError(error);
        });
    },
    switchEdit() {
      this.action = "edit";
      this.dialogProps.title = `修改${this.dialogTitle}`;
      this.initOptions(this.editFormData);
    },
    getFormById(id) {
      this.setLoad();
      return new Promise((resolve) => {
        getTfamAssetById(id)
          .then((responseData) => {
            let form = {};
            if (responseData.code == 100) {
              form = responseData.data;
            } else {
              this.showMessage(responseData);
            }
            this.resetLoad();
            resolve(form);
          })
          .catch((error) => {
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
    listNatureOptions() {
      let search_List = {
        params: [],
      };
      // filter条件
      search_List.params.push.apply(search_List.params, [
        {
          columnName: "company_id",
          queryType: "=",
          value: currentUser.company.id == "0" ? "" : currentUser.company.id,
        },
      ]);
      // 数据权限: 卡片性质表tfam_nature
      this.pushDataPermissions(
        search_List.params,
        this.$route.meta.routerId,
        "1641034040897356358"
      );
      listTfamNatureAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.natureOptions = responseData.data;
          this.editFormData.nature =
            this.natureOptions.length > 0
              ? this.natureOptions[0]
              : {
                  name: null,
                  remarks: null,
                };
        } else {
          this.showMessage(responseData);
        }
      });
    },
    listCategoryOptions() {
      let search_List = {
        params: [],
      };
      // filter条件
      search_List.params.push.apply(search_List.params, [
        {
          columnName: "company_id",
          queryType: "=",
          value: currentUser.company.id == "0" ? "" : currentUser.company.id,
        },
      ]);
      // 数据权限: 资产类别表tfam_category
      this.pushDataPermissions(
        search_List.params,
        this.$route.meta.routerId,
        "1633870636743042273"
      );
      listTfamCategoryAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.categoryOptions = responseData.data;
        } else {
          this.showMessage(responseData);
        }
      });
    },
    listUsingstatusOptions() {
      let search_List = {
        params: [],
      };
      // filter条件
      search_List.params.push.apply(search_List.params, [
        {
          columnName: "company_id",
          queryType: "=",
          value: currentUser.company.id == "0" ? "" : currentUser.company.id,
        },
      ]);
      // 数据权限: 使用状况表tfam_usingstatus
      this.pushDataPermissions(
        search_List.params,
        this.$route.meta.routerId,
        "1633870636743042259"
      );
      listTfamUsingstatusAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.usingstatusOptions = responseData.data;
        } else {
          this.showMessage(responseData);
        }
      });
    },
    listUsedeptidOptions() {
      let search_List = {
        params: [],
      };
      // filter条件
      search_List.params.push.apply(search_List.params, []);
      // 数据权限: 部门org_department
      this.pushDataPermissions(
        search_List.params,
        this.$route.meta.routerId,
        "1633870636743041550"
      );
      treeDepartment(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.usedeptidOptions = responseData.data;
        } else {
          this.showMessage(responseData);
        }
      });
    },
    listUseridOptions() {
      let search_List = {
        params: [],
      };
      // filter条件
      search_List.params.push.apply(search_List.params, []);
      // 数据权限: 用户sys_user
      this.pushDataPermissions(
        search_List.params,
        this.$route.meta.routerId,
        "1457254743943431179"
      );
      listUserAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.useridOptions = responseData.data;
        } else {
          this.showMessage(responseData);
        }
      });
    },
    listProducerOptions() {
      let search_List = {
        params: [],
      };
      // filter条件
      search_List.params.push.apply(search_List.params, [
        {
          columnName: "company_id",
          queryType: "=",
          value: currentUser.company.id == "0" ? "" : currentUser.company.id,
        },
      ]);
      // 数据权限: 供应商表tfam_supplier
      this.pushDataPermissions(
        search_List.params,
        this.$route.meta.routerId,
        "1633870636743042245"
      );
      listTfamSupplierAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.producerOptions = responseData.data;
        } else {
          this.showMessage(responseData);
        }
      });
    },
    listDepmethodOptions() {
      let search_List = {
        params: [],
      };
      // filter条件
      search_List.params.push.apply(search_List.params, []);
      // 数据权限: 折旧方法表tfam_depmethod
      this.pushDataPermissions(
        search_List.params,
        this.$route.meta.routerId,
        "1641034040897356422"
      );
      listTfamDepmethodAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.depmethodOptions = responseData.data;
          this.editFormData.depmethod = this.depmethodOptions[0] || {
            name: null,
            remarks: null,
            code: null,
          };
        } else {
          this.showMessage(responseData);
        }
      });
    },
    listAddReasonOptions() {
      let search_List = {
        params: [],
      };
      // filter条件
      search_List.params.push.apply(search_List.params, [
        {
          columnName: "company_id",
          queryType: "=",
          value: currentUser.company.id == "0" ? "" : currentUser.company.id,
        },
      ]);
      // 数据权限: 变动方式表tfam_change_way
      this.pushDataPermissions(
        search_List.params,
        this.$route.meta.routerId,
        "1633870636743042398"
      );
      listTfamChangeWayAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.addReasonOptions = responseData.data;
        } else {
          this.showMessage(responseData);
        }
      });
    },

    // 科目配置默认值初始化
    listTfamDefaultOptions() {
      let search_List = {
        params: [],
      };
      // filter条件
      search_List.params.push.apply(search_List.params, []);

      listTfamDefaultSettingsDefault(search_List).then((responseData) => {
        if (responseData.code == 100) {
          let tfamDefaultArrys = responseData.data;
          tfamDefaultArrys.map((val) => {
            if (this.defaultObj.hasOwnProperty(val["item"])) {
              this.defaultObj[val["item"]] = val["tglAccounts"];
              let nameArr = val["item"].split("_");
              let newName =
                nameArr[0] +
                nameArr[1][0].toUpperCase() +
                nameArr[1].substr(1) +
                "id";
              this.editFormData[newName] = val["tglAccounts"];
            }
          });
          this.isProjectFun();
        } else {
          this.showMessage(responseData);
        }
      });
    },
    initOptions(This) {
      // 初始化自定义类型选择框选项变量
      this.listNatureOptions();
      this.listCategoryOptions();
      this.listUsingstatusOptions();
      this.listUsedeptidOptions();
      this.listUseridOptions();
      this.listProducerOptions();
      this.listDepmethodOptions();
      this.listAddReasonOptions();

      if (this.action === "add") {
        this.listTfamDefaultOptions(); // 科目配置默认值初始化
      }
      this.tfamAccesSoryFun(This.id || ""); // 查询生命周期
    },
    onDeleteChild(tableData, index) {
      // 根据下标删除子表数据
      tableData.splice(index, 1);
    },
    initEditData(This) {
      return {
        operateType: null, // 2调整
        reason: "", // 变动原因
        cardCode: "", // 卡片编号
        businessDate: "", // 建卡日期
        nature: {
          name: null,
          remarks: null,
        },
        createrid: "", // 建卡人
        assetCode: "", // 资产编号
        category: {
          id: null,
          name: null,
          fullname: null,
          fullcode: null,
        },
        assetName: "", // 资产名称
        usingstatus: {
          id: null,
          name: null,
          code: null,
          remarks: null,
          companyId: null,
        },
        // 使用部门
        usedeptid: {
          id: null,
          name: null,
          code: null,
          companyId: null,
        },
        businessdept: "", // 所属事业部
        userid: {
          id: null,
          name: null,
          companyId: null,
          departmentId: null,
        },
        employeeno: "", // 工号
        userAscription: "", // 员工隶属
        assetAscription: "", // 设备隶属
        place: "", // 所在地点
        producer: {
          id: null,
          name: null,
          code: null,
        },
        macAddress: "", // MAC地址
        cardModel: "", // 型号
        assetbrand: "", // 品牌
        spec: "", // 规格
        num: null, // 数量
        unit: "", // 计量单位
        memo: "", // 备注
        enableDate: "", // 启用日期
        value: null, // 原值
        netvalue: null, // 净值
        salvage: null, // 残值
        salvagerate: null, // 残值率(%)
        rate: null, // 税率(%)
        tax: null, // 税金
        accudep: null, // 累计折旧
        predevaluate: null, // 减值准备
        depmethod: {
          name: null,
          remarks: null,
          code: null,
        },
        startupDate: "", // 购置日期
        naturemonth: null, // 预计使用月数
        servicemonth: null, // 剩余使用月数
        deprate: null, // 月折旧率(%)
        depamount: null, // 月折旧额
        addReason: {
          id: null,
          name: null,
          code: null,
          type: null,
          remarks: null,
        },
        assetAccountid: {
          // 固定资产折旧
          id: null,
          uqaccountid: null,
          varaccountcode: null,
          varaccountfullcode: null,
          varaccountname: null,
        },
        accudepAccountid: {
          // 累计折旧科目
          id: null,
          uqaccountid: null,
          varaccountcode: null,
          varaccountfullcode: null,
          varaccountname: null,
        },
        predevaluateAccountid: {
          // 固定资产减值准备科目
          id: null,
          uqaccountid: null,
          varaccountcode: null,
          varaccountfullcode: null,
          varaccountname: null,
        },
        depcostAccountid: {
          // 折旧费用科目
          id: null,
          uqaccountid: null,
          varaccountcode: null,
          varaccountfullcode: null,
          varaccountname: null,
        },
        predevaluateCoraccountid: {
          // 减值准备对方科目
          id: null,
          uqaccountid: null,
          varaccountcode: null,
          varaccountfullcode: null,
          varaccountname: null,
        },
        isproject: "0", // 是否在建工程中转
        projectAccountid: {
          // 在建工程科目
          id: null,
          uqaccountid: null,
          varaccountcode: null,
          varaccountfullcode: null,
          varaccountname: null,
        },
        taxAccountid: {
          // 税金科目
          id: null,
          uqaccountid: null,
          varaccountcode: null,
          varaccountfullcode: null,
          varaccountname: null,
        },
        tfamDepsubList: [],
        tfamAssetCheckimageList: [],
        tfamAssetTransferList: [],
        tfamAccessoryList: [],
        tfamAsset2qrcodeList: [],
      };
    },
    onAddTfamDepsubList() {
      return;
      this.editFormData.tfamDepsubList.push({
        year: "", // 折旧年份
        month: "", // 折旧月份
        date: "", // 折旧日期
        depamount: null, // 折旧金额
        depType: null, // 折旧类型（1月折旧，2补提，3冲减）
        resaon: "", // 补提冲减原因
        value: null, // 原值
        accudep: null, // 累计折旧
        netvalue: null, // 净值
        vouformat: "", // 凭证字
        voucherid: "", // 凭证id
        abstracts: "", // 凭证摘要
        creatorid: "", // 操作人（用户表id）
        createTime: "", // 操作日期
        companyid: "", // 公司id（后期多公司备用）
        workloan: null, // 工作量（工作量法每月折旧的工作量）
        depmethod: null, // 折旧方法
        dbflag: null, // 双倍余额法是否变动（1-已变动，0-没有变动）双倍余额法有效
        usingstatus: "", // usingstatus
        companyId: "", // company_id
        remarks: "", // 备注信息
      });
    },
    onAddTfamAssetCheckimageList() {
      this.editFormData.tfamAssetCheckimageList.push({
        cardNo: {
          id: null,
          cardCode: null,
          assetName: null,
        },
        creator: "", // 上传人
        createReason: "", // 上传原因
        createTime: "", // 上传时间
      });
    },
    onAddTfamAssetTransferList() {
      this.editFormData.tfamAssetTransferList.push({
        beforeTransferOwner: "", // 调拨前责任人
        afterTransferOwner: "", // 调拨后责任人
        beforeTransferDepartment: "", // 调拨前使用部门
        afterTransferDepartment: "", // 调拨后使用部门
        transferReason: "", // 调拨原因
        companyId: "", // 租户id
        transferDate: "CURRENT_TIMESTAMP", // 调拨日期
        transferCreatorId: "", // 操作人id
        transferCreatorName: "", // 操作人姓名
        remarks: "", // 备注信息
      });
    },
    onAddTfamAccessoryList() {
      this.editFormData.tfamAccessoryList.push({
        name: "", // 名称
        spec: "", // 规格
        unit: "", // 单位
        num: null, // 数量
        money: null, // 金额
      });
    },
    onAddTfamAsset2qrcodeList() {
      this.editFormData.tfamAsset2qrcodeList.push({
        qrcodePath: "", // 二维码路径
        remarks: "", // 备注信息
      });
    },

    // 查询固定资产
    onSearchAccount(data, key) {
      this.openAccountQuery = data;
      this.openAccountKey = key;

      this.$refs.accountQuery.open();
    },
    clearAccount(data, key) {
      this.editFormData[data] = "";
      this.editFormData[key] = {
        id: null,
        uqaccountid: null,
        varaccountcode: null,
        varaccountfullcode: null,
        varaccountname: null,
      };
    },
    getCheckData(data) {
      if (!validatenull(data.tableData)) {
        this.editFormData[this.openAccountQuery] =
          data.tableData.varaccountname;
        this.editFormData[this.openAccountKey] = {
          id: data.tableData.id,
          uqaccountid: data.tableData.uqaccountid,
          varaccountcode: data.tableData.varaccountcode,
          varaccountfullcode: data.tableData.varaccountfullcode,
          varaccountname: data.tableData.varaccountname,
        };
      }
    },
    // 负责人改变
    useridOptionsChange() {
      this.editFormData.employeeno = this.editFormData.userid.empolyno || "";
    },

    // 是否在建工程中转
    isProjectFun() {
      if (this.action === "add") {
        if (this.editFormData.isproject * 1 == 0) {
          this.editFormData.projectAccountid = {
            id: null,
            uqaccountid: null,
            varaccountcode: null,
            varaccountfullcode: null,
            varaccountname: null,
          };
        } else {
          // [TODO默认值]
          this.editFormData["projectAccountid"] =
            this.defaultObj["project_account"];
        }
      }
    },

    // 卡片生命周期查询
    tfamAccesSoryFun(id) {
      this.lifeObj = null
      let params = {
        assetId: id,
      };

      lifeCircle(params).then((responseData) => {
        if (responseData.code == 100) {
          this.lifeObj = responseData.data;
          console.log(responseData, "生命周期======");
        } else {
          this.lifeObj = null
          this.showMessage(responseData);
        }
      });
    },
  },
  watch: {
    "editFormData.value": function (val, oldVal) {
      // 原值
      this.editFormData.netvalue = this.editFormData.value; // 净值等于原值
      if (this.editFormData.salvagerate && this.editFormData.value) {
        this.editFormData.salvage =
          this.editFormData.salvagerate * this.editFormData.value * 0.01; // 残值 = 残值率*原值
      } else {
        this.editFormData.salvage = "";
      }
      if (this.editFormData.value && this.editFormData.rate) {
        this.editFormData.tax =
          this.editFormData.value * this.editFormData.rate * 0.01; // 税金 = 原值*税率
      } else {
        this.editFormData.tax = "";
      }

      if (this.editFormData.value && this.editFormData.salvage) {
        this.editFormData.deprate =
          ((this.editFormData.value - this.editFormData.salvage) /
            this.editFormData.naturemonth) *
          100; // 月折旧率 = （原值-残值）/预计使用月数
      } else {
        this.editFormData.deprate = "";
      }
      if (this.editFormData.value && this.editFormData.deprate) {
        this.editFormData.depamount =
          this.editFormData.value * this.editFormData.deprate * 0.01; //  月折旧额 = 原值*月折旧率
      } else {
        this.editFormData.depamount = "";
      }
    },
    "editFormData.salvagerate": function (val, oldVal) {
      // 残值率
      if (this.editFormData.salvagerate > 100) {
        this.showMessage({
          type: "warning",
          msg: "残值率不得大于100",
        });
        this.editFormData.salvagerate = "";
        return;
      }
      if (this.editFormData.salvagerate && this.editFormData.value) {
        this.editFormData.salvage =
          this.editFormData.salvagerate * this.editFormData.value * 0.01; // 残值 = 残值率*原值
      } else {
        this.editFormData.salvage = "";
      }
      // this.editFormData.value =
      //   (this.editFormData.salvage / this.editFormData.salvagerate) * 100;
    },
    "editFormData.salvage": function (val, oldVal) {
      // 残值
      // this.editFormData.salvagerate =
      //   (this.editFormData.salvage / this.editFormData.value) * 100; // 残值率=残值/原值
      if (
        this.editFormData.value &&
        this.editFormData.salvage &&
        this.editFormData.naturemonth
      ) {
        this.editFormData.deprate =
          ((this.editFormData.value - this.editFormData.salvage) /
            this.editFormData.naturemonth) *
          100; // 月折旧率 = （原值-残值）/预计使用月数
      } else {
        this.editFormData.deprate = "";
      }
    },
    "editFormData.rate": function (val, oldVal) {
      // 税率
      if (this.editFormData.rate > 100) {
        this.showMessage({
          type: "warning",
          msg: "税率不得大于100",
        });
        this.editFormData.rate = "";
        return;
      }
      if (this.editFormData.value && this.editFormData.rate) {
        this.editFormData.tax =
          this.editFormData.value * this.editFormData.rate * 0.01; // 税金 = 原值*税率
      } else {
        this.editFormData.tax = "";
      }
    },
    "editFormData.deprate": function (val, oldVal) {
      // 月折旧率
      this.editFormData.accudep =
        this.editFormData.deprate *
        (this.editFormData.naturemonth - this.editFormData.servicemonth) *
        100; // 累计折旧 = 月折旧率*（预计使用月数-剩余使用月数）
      if (this.editFormData.value && this.editFormData.deprate) {
        this.editFormData.depamount =
          this.editFormData.value * this.editFormData.deprate * 0.01; //  月折旧额 = 原值*月折旧率
      } else {
        this.editFormData.depamount = "";
      }
    },
    "editFormData.naturemonth": function (val, oldVal) {
      // 预计使用月数
      if (
        this.editFormData.deprate &&
        this.editFormData.naturemonth &&
        this.editFormData.servicemonth
      ) {
        this.editFormData.accudep =
          this.editFormData.deprate *
          (this.editFormData.naturemonth - this.editFormData.servicemonth) *
          0.01; // 累计折旧 = 月折旧率*（预计使用月数-剩余使用月数）
      } else {
        this.editFormData.accudep = "";
      }
      if (
        this.editFormData.value &&
        this.editFormData.salvage &&
        this.editFormData.naturemonth
      ) {
        this.editFormData.deprate =
          ((this.editFormData.value - this.editFormData.salvage) /
            this.editFormData.naturemonth) *
          100; // 月折旧率 = （原值-残值）/预计使用月数
      } else {
        this.editFormData.deprate = "";
      }
      if (this.editFormData.naturemonth && this.editFormData.enableDate) {
        this.editFormData.servicemonth =
          this.editFormData.naturemonth -
          dateMonths(nowDate(), this.editFormData.enableDate); // 剩余使用月数 = 预计使用月数 - （当前日期 - 启用日期）
      } else {
        this.editFormData.servicemonth = "";
      }
    },
    "editFormData.servicemonth": function (val, oldVal) {
      // 剩余使用月数
      if (
        this.editFormData.deprate &&
        this.editFormData.naturemonth &&
        this.editFormData.servicemonth
      ) {
        this.editFormData.accudep =
          this.editFormData.deprate *
          (this.editFormData.naturemonth - this.editFormData.servicemonth) *
          0.01; // 累计折旧 = 月折旧率*（预计使用月数-剩余使用月数）
      } else {
        this.editFormData.accudep = "";
      }
    },
    "editFormData.enableDate": function (val, oldVal) {
      // 启用日期
      if (this.editFormData.naturemonth) {
        this.editFormData.servicemonth =
          this.editFormData.naturemonth -
          dateMonths(nowDate(), this.editFormData.enableDate); // 剩余使用月数 = 预计使用月数 - （当前日期 - 启用日期）
      } else {
        this.editFormData.servicemonth = "";
      }
    },
    tabName(val, oldVal) {
      this.$nextTick(() => {
        if (this.$refs[val] && this.$refs[val].doLayout) {
          this.$refs[val].doLayout();
        }
      });
    },
  },
  mounted: function () {
    this.$nextTick(() => {
      this.$on("openViewDialog", async function (id) {
        this.action = "view";
        this.dialogProps.title = `查看${this.dialogTitle}`;
        this.editFormData = {
          ...this.initEditData(),
          ...(await this.getFormById(id)),
        };
        this.initOptions(this.editFormData);
        this.tabName = "editForm";
        this.dialogProps.visible = true;
      });
      this.$on("openEditDialog", async function (id) {
        this.action = "edit";
        this.dialogProps.title = `修改${this.dialogTitle}`;
        this.editFormData = {
          ...this.initEditData(),
          ...(await this.getFormById(id)),
        };
        this.initOptions(this.editFormData);
        this.tabName = "editForm";
        this.dialogProps.visible = true;
      });
      this.$on("openAdjustDialog", async function (id) {
        this.action = "adjust";
        this.dialogProps.title = `调整${this.dialogTitle}`;
        this.editFormData = {
          ...this.initEditData(),
          ...(await this.getFormById(id)),
        };
        this.initOptions(this.editFormData);
        this.tabName = "editForm";
        this.editFormData.operateType = 2;
        this.dialogProps.visible = true;
      });
      this.$on("openAddDialog", function () {
        this.action = "add";
        this.dialogProps.title = `添加${this.dialogTitle}`;
        this.editFormData = this.initEditData();
        this.initOptions(this.editFormData);
        this.tabName = "editForm";
        this.dialogProps.visible = true;
      });
      this.$on("openCopyDialog", async function (id) {
        this.action = "add";
        this.dialogProps.title = `添加${this.dialogTitle}`;
        this.editFormData = {
          ...this.initEditData(),
          ...(await this.getFormById(id)),
        };
        this.initOptions(this.editFormData);
        this.tabName = "editForm";
        this.editFormData.id = null; //把id设置为空，添加一个新的
        for (var i = 0; i <= this.editFormData.tfamDepsubList.length - 1; i++) {
          this.editFormData.tfamDepsubList[i].id = null;
        }
        for (
          var i = 0;
          i <= this.editFormData.tfamAssetCheckimageList.length - 1;
          i++
        ) {
          this.editFormData.tfamAssetCheckimageList[i].id = null;
        }
        for (
          var i = 0;
          i <= this.editFormData.tfamAssetTransferList.length - 1;
          i++
        ) {
          this.editFormData.tfamAssetTransferList[i].id = null;
        }
        for (
          var i = 0;
          i <= this.editFormData.tfamAccessoryList.length - 1;
          i++
        ) {
          this.editFormData.tfamAccessoryList[i].id = null;
        }
        for (
          var i = 0;
          i <= this.editFormData.tfamAsset2qrcodeList.length - 1;
          i++
        ) {
          this.editFormData.tfamAsset2qrcodeList[i].id = null;
        }
        this.dialogProps.visible = true;
      });
    });
  },
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