<template>
  <el-row class="dc-container" v-loading="loading">
    <history ref="history" :bussObject="curentRow"></history>
    <edit-form
      ref="editForm"
      v-on:save-finished="getList"
      v-on:before-load="setLoad"
      v-on:after-load="resetLoad"
      :permission="permission"
    ></edit-form>
    <edit-form1
      ref="editForm1"
      v-on:save-finished="getList"
      v-on:before-load="setLoad"
      v-on:after-load="resetLoad"
      :permission="permission"
    ></edit-form1>
    <el-row
      gutter="0"
      type="flex"
      justify="start"
      align="top"
      class="dc-el-row_ElRow"
    >
      <el-col :span="24">
        <query-tag
          ref="queryTag"
          v-model="queryEditObject"
          :moreParm="moreParm"
          :routerId="$route && $route.meta.routerId"
          :isQueryConditionPanel="isQueryConditionPanel"
          v-on:queryTagPanel="onQueryTagPanel"
          v-on:queryTagClick="onQueryTagClick"
          v-on:search="onSearch"
          class="dc-query-tag_ElConditionTag"
        ></query-tag>
      </el-col>
      <el-col :span="6">
        <div style="text-align: right">
          <el-button-group class="dc-el-button-group_ButtonGroup">
            <el-tooltip effect="dark" content="搜索" placement="top">
              <el-button
                v-on:click="onSearch"
                type="primary"
                icon="el-icon-search"
              ></el-button>
            </el-tooltip>
            <el-tooltip effect="dark" content="重置" placement="top">
              <el-button
                v-on:click="
                  isQueryConditionPanel
                    ? $refs.conditionPanel.reset()
                    : $refs.queryForm.resetFields()
                "
                type="primary"
                icon="el-icon-refresh-right"
              ></el-button>
            </el-tooltip>
            <el-tooltip effect="dark" content="更多" placement="top">
              <el-button
                v-on:click="onQueryConditionPanel('query')"
                type="primary"
                icon="el-icon-d-arrow-right"
              ></el-button>
            </el-tooltip>
          </el-button-group>
        </div>
      </el-col>
    </el-row>
    <query-condition-panel
      v-if="isQueryConditionPanel"
      ref="conditionPanel"
      :conditionColumns="
        metadata.find((item) => {
          return item.type == 'main';
        }).conditionPanel
      "
      v-model="moreParm"
      :routerId="$route.meta.routerId"
      class="dc-query-condition-panel_ElConditionPanel"
    ></query-condition-panel>
    <el-form
      ref="queryForm"
      :model="queryFormData"
      label-width="100px"
      v-if="!isQueryConditionPanel"
      class="dc-el-form_ElQueryForm"
    >
      <el-row>
        <el-col :span="6">
          <el-form-item
            prop="category"
            label="资产类别"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="queryFormData.category"
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
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="assetCode"
            label="资产编号"
            class="dc-el-form-item_SingleInput"
          >
            <el-input
              v-model="queryFormData.assetCode"
              :maxLength="40"
              placeholder="请输入资产编号"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="assetName"
            label="资产名称"
            class="dc-el-form-item_SingleInput"
          >
            <el-input
              v-model="queryFormData.assetName"
              :maxLength="200"
              placeholder="请输入资产名称"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="userstatus"
            label="在职状态"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="queryFormData.userstatus"
              :style="{ width: '100%' }"
              placeholder="请选择在职状态"
              clearable
              value-key="id"
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in statusOptions"
                :key="index"
                :label="item.name"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="usedeptid"
            label="使用部门"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="queryFormData.usedeptid"
              :style="{ width: '100%' }"
              placeholder="请选择使用部门"
              clearable
              value-key="id"
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in usedeptidOptions"
                :key="index"
                :label="item.name"
                :value="item"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="userid"
            label="责任人"
            class="dc-el-form-item_SingleInput"
          >
            <el-input
              v-model="queryFormData.userid"
              :maxLength="100"
              placeholder="请输入责任人"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="usingstatus"
            label="使用状况"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="queryFormData.usingstatus"
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
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="servicemonth"
            label="剩余使用月数"
            class="dc-el-form-item_SingleInput"
          >
            <el-input
              v-model="queryFormData.servicemonth"
              :maxLength="11"
              placeholder="请输入剩余使用月数"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <el-row
      gutter="0"
      type="flex"
      justify="end"
      align="top"
      class="dc-el-row_ElRow"
    >
      <el-tooltip v-show="permission.add" disabled class="dc-el-tooltip_Button">
        <!-- <el-button type="primary" icon="el-icon-plus" v-on:click="onCreate"
          >添加</el-button
        > -->
      </el-tooltip>
      <!-- <el-tooltip disabled class="dc-el-tooltip_Button">
        <el-button
          type="primary"
          icon="el-icon-paperclip"
          v-on:click="adjustFun"
          class="dc-el-button_Button"
          >调整</el-button
        >
      </el-tooltip> -->
      <el-tooltip disabled class="dc-el-tooltip_Button">
        <el-button
          type="primary"
          icon="el-icon-tickets"
          v-on:click="adjustListFun('1')"
          class="dc-el-button_Button"
        >
          查看调整记录
        </el-button>
      </el-tooltip>
      <el-tooltip disabled class="dc-el-tooltip_Button">
        <el-button
          type="primary"
          icon="el-icon-refresh"
          v-on:click="allocateFun"
          class="dc-el-button_Button"
        >调拨</el-button
        >
      </el-tooltip>
      <el-tooltip disabled class="dc-el-tooltip_Button">
        <el-button
          type="danger"
          icon="el-icon-help"
          v-on:click="onOpreFun('stop')"
        >反审核</el-button
        >
      </el-tooltip>
      <el-tooltip disabled class="dc-el-tooltip_Button">
        <el-button
          type="danger"
          icon="el-icon-warning"
          v-on:click="dialogFormVisible = true"
        >报废</el-button
        >
      </el-tooltip>
    </el-row>
    <ux-grid
      column-key
      ref="table"
      :data="tableData"
      border
      v-on:sort-change="onSortChange"
      v-on:header-dragend="onWidthChange($refs.table)"
      :tree-config="{
        children: '',
        indent: 20,
        accordion: false,
        line: false,
        showIcon: true,
        iconOpen: '',
        iconClose: '',
      }"
      class="dc-ux-grid_QueryTable"
    >
      <ux-table-column
        title="序号"
        :params="{ sortId: '249', summary: false }"
        type="index"
        tree-node
        resizable
        min-width="80px"
        fixed="left"
        align="center"
        header-align="center"
        class="dc-ux-table-column_ElTableNumColumn"
      ></ux-table-column>
      <ux-table-column
        :params="{ sortId: '250', summary: false }"
        type="checkbox"
        tree-node
        resizable
        min-width="80px"
        fixed="left"
        align="center"
        header-align="center"
        class="dc-ux-table-column_ElTableCheckBoxColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['121'].field"
        :title="tableColumns['121'].title"
        :width="tableColumns['121'].width"
        :visible="tableColumns['121'].visible"
        :params="{ sortId: '121', summary: false }"
        tree-node
        resizable
        min-width="180px"
        align="center"
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
        :params="{ sortId: '127', summary: false }"
        tree-node
        resizable
        min-width="120px"
        align="center"
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
        :params="{ sortId: '128', summary: false }"
        tree-node
        resizable
        min-width="180px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['137'].field"
        :title="tableColumns['137'].title"
        :width="tableColumns['137'].width"
        :visible="tableColumns['137'].visible"
        :params="{ sortId: '137', summary: false }"
        tree-node
        resizable
        min-width="120px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['140'].field"
        :title="tableColumns['140'].title"
        :width="tableColumns['140'].width"
        :visible="tableColumns['140'].visible"
        :params="{ sortId: '140', summary: false }"
        tree-node
        resizable
        min-width="120px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['209'].field"
        :title="tableColumns['209'].title"
        :width="tableColumns['209'].width"
        :visible="tableColumns['209'].visible"
        :params="{ sortId: '209', summary: false }"
        tree-node
        resizable
        min-width="180px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['259'].field"
        :title="tableColumns['259'].title"
        :width="tableColumns['259'].width"
        :visible="tableColumns['259'].visible"
        :params="{ sortId: '259', summary: false }"
        tree-node
        resizable
        align="center"
        header-align="center"
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      >
        <template slot-scope="{ row, rowIndex }">
          <div
            v-html="
              ((value, row, rowIndex) => {
                switch (row.userstatus) {
                  case '0':
                    return '在职';
                  case '1':
                    return '离职';
                  default:
                    return '未知';
                }
              })(row.isbalance, row, rowIndex)
            "
          ></div>
        </template>
      </ux-table-column>
      <ux-table-column
        :field="tableColumns['183'].field"
        :title="tableColumns['183'].title"
        :width="tableColumns['183'].width"
        :visible="tableColumns['183'].visible"
        :params="{ sortId: '183', summary: false }"
        tree-node
        resizable
        min-width="180px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['190'].field"
        :title="tableColumns['190'].title"
        :width="tableColumns['190'].width"
        :visible="tableColumns['190'].visible"
        :params="{ sortId: '190', summary: false }"
        tree-node
        resizable
        min-width="160px"
        align="right"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['197'].field"
        :title="tableColumns['197'].title"
        :width="tableColumns['197'].width"
        :visible="tableColumns['197'].visible"
        :params="{ sortId: '197', summary: false }"
        tree-node
        resizable
        min-width="120px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['146'].field"
        :title="tableColumns['146'].title"
        :width="tableColumns['146'].width"
        :visible="tableColumns['146'].visible"
        :params="{ sortId: '146', summary: false }"
        tree-node
        resizable
        min-width="120px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['189'].field"
        :title="tableColumns['189'].title"
        :width="tableColumns['189'].width"
        :visible="tableColumns['189'].visible"
        :params="{ sortId: '189', summary: false }"
        tree-node
        resizable
        min-width="80px"
        align="right"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        title="操作"
        :params="{ sortId: '113' }"
        tree-node
        resizable
        width="160px"
        min-width="160px"
        fixed="right"
        align="center"
        header-align="center"
        class="dc-ux-table-column_ElTableOptColumn"
      >
        <template v-slot:header="scope">
          <span>操作</span>
          <el-popover
            ref="popover"
            width="10"
            trigger="click"
            placement="bottom"
            offset="0"
            class="dc-el-popover_ElPopover"
          >
            <view-columns-select
              v-model="tableColumns"
              ref="viewColumnsSelect"
              v-on:save-column-view="saveColumn($event, 'table')"
              v-on:show-all-column="showAllColumn($event, 'table')"
              v-on:show-default-column="showDefaultColumn('table')"
              class="dc-view-columns-select_ViewColumnsSelect"
            ></view-columns-select>
            <template slot="reference">
              <OperationIcon
                type="primary"
                content="自定义列"
                placement="top"
                icon-name="el-icon-tickets"
              ></OperationIcon>
            </template>
          </el-popover>
          <OperationIcon
            v-on:click="onExport(tableData)"
            type="primary"
            v-show="permission.export"
            content="导出excel文件"
            placement="top"
            icon-name="el-icon-download"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
        </template>
        <template slot-scope="scope">
          <OperationIcon
            v-on:click="onView(scope)"
            type="info"
            v-show="permission.view"
            content="查看"
            placement="top"
            icon-name="el-icon-view"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            v-on:click="onAdjust(scope)"
            type="primary"
            v-show="permission.edit"
            content="调整"
            placement="top"
            icon-name="el-icon-paperclip"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <!-- <OperationIcon
            v-on:click="onEdit(scope)"
            type="primary"
            v-show="permission.edit"
            content="编辑"
            placement="top"
            icon-name="el-icon-edit"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon> -->
          <!-- <OperationIcon
            v-on:click="onCopy(scope)"
            type="primary"
            v-show="permission.add"
            content="复制"
            placement="top"
            icon-name="el-icon-document"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            v-on:click="onDelete(scope)"
            type="danger"
            v-show="permission.remove"
            content="删除"
            placement="top"
            icon-name="el-icon-delete"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            v-on:click="onShowHistory(scope)"
            type="info"
            v-show="permission.view"
            content="历史记录"
            placement="top"
            icon-name="el-icon-info"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon> -->
        </template>
      </ux-table-column>
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

    <!-- 卡片调拨 -->
    <el-dialog title="卡片调拨" :visible.sync="dialogTableVisible">
      <el-form ref="diaoboForm" :model="diaoboFormData">
        <el-col :span="10">
          <el-form-item
            prop="usedeptid"
            label="使用部门"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="diaoboFormData.usedeptid"
              :style="{ width: '100%' }"
              placeholder="请选择使用部门"
              clearable
              value-key="id"
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in usedeptidOptions"
                :key="index"
                :label="item.name"
                :value="item"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item
            prop="userid.id"
            label="责任人"
            class="dc-el-form-item_SelectInput"
            style="margin-left: 20px"
          >
            <el-select
              v-model="diaoboFormData.userid"
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
        <el-col :span="24">
          <el-form-item
            prop="reason"
            label="调拨原因"
            :rules="[
              { required: true, message: '调拨原因不能为空', trigger: 'blur' },
            ]"
            class="dc-el-form-item_MutilpleInput"
          >
            <el-input
              v-model="diaoboFormData.reason"
              type="textarea"
              placeholder="请输入调拨原因"
              rows="2"
              :maxLength="-1"
              class="dc-el-input_MutilpleInput"
            >
            </el-input>
          </el-form-item>
        </el-col>
      </el-form>
      <el-table :data="gridData">
        <el-table-column property="businessDate" label="建卡日期">
          <template slot-scope="{ row, rowIndex }">
            <div
              v-html="
                ((value, row, rowIndex) => {
                  return row.businessDate ? row.businessDate.slice(0, 10) : '';
                })(row.businessDate, row, rowIndex)
              "
            ></div>
          </template>
        </el-table-column>
        <el-table-column property="cardCode" label="卡片编号"></el-table-column>
        <el-table-column
          property="assetCode"
          label="资产编号"
        ></el-table-column>
        <el-table-column
          property="assetName"
          label="资产名称"
        ></el-table-column>
        <el-table-column
          property="userid.name"
          label="责任人"
        ></el-table-column>
        <el-table-column
          property="usedeptid.name"
          label="使用部门"
        ></el-table-column>
        <el-table-column
          property="category.name"
          label="资产类别"
        ></el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogTableVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitTableDialog">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 报废方式 -->
    <el-dialog title="请选择减少方式" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="减少方式" label-width="120px">
          <el-select v-model="scrapeReason" placeholder="请选择减少方式">
            <el-option label="报废" value="报废"></el-option>
            <el-option label="丢失" value="丢失"></el-option>
            <el-option label="盘亏" value="盘亏"></el-option>
            <el-option label="出售" value="出售"></el-option>
            <el-option label="其他减少" value="其他减少"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitReasonDialog">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 调整记录 -->
    <el-dialog title="调整记录" :visible.sync="dialogAdjustVisible">
      <div style="margin-bottom: 20px">
        <el-button
          type="primary"
          icon="el-icon-guide"
          class="dc-el-button_Button"
        >关联凭证</el-button
        >
        <el-button
          type="danger"
          icon="el-icon-connection"
          class="dc-el-button_Button"
        >取消关联</el-button
        >
        <el-button
          type="primary"
          icon="el-icon-search"
          class="dc-el-button_Button"
        >查看凭证</el-button
        >
      </div>
      <div style="margin: 20px 0; font-weight: bold">
        <span>卡片编号：{{ checkdata.cardCode }}</span>
        <span style="margin: 0 20px">资产编号：{{ checkdata.assetCode }}</span>
        <span>资产名称：{{ checkdata.assetName }}</span>
      </div>
      <ux-grid
        column-key
        ref="adjusttable"
        :data="adjustArr"
        border
        v-on:header-dragend="onWidthChange($refs.table)"
        :tree-config="{
          children: '',
          indent: 20,
          accordion: false,
          line: false,
          showIcon: true,
          iconOpen: '',
          iconClose: '',
        }"
        class="dc-ux-grid_QueryTable"
      >
        <ux-table-column
          title="序号"
          :params="{ sortId: '148', summary: false }"
          type="index"
          tree-node
          resizable
          min-width="80px"
          fixed="left"
          align="center"
          header-align="center"
          class="dc-ux-table-column_ElTableNumColumn"
        ></ux-table-column>
        <ux-table-column
          :params="{ sortId: '149', summary: false }"
          type="checkbox"
          tree-node
          resizable
          min-width="80px"
          fixed="left"
          align="center"
          header-align="center"
          class="dc-ux-table-column_ElTableCheckBoxColumn"
        ></ux-table-column>
        <ux-table-column
          :field="adjusttableColumns['129'].field"
          :title="adjusttableColumns['129'].title"
          :width="adjusttableColumns['129'].width"
          :visible="adjusttableColumns['129'].visible"
          :params="{ sortId: '129', summary: false }"
          tree-node
          resizable
          min-width="160px"
          align="right"
          header-align="center"
          sortable
          show-overflow
          class="dc-ux-table-column_ElTableColumn"
        ></ux-table-column>
        <ux-table-column
          :field="adjusttableColumns['130'].field"
          :title="adjusttableColumns['130'].title"
          :width="adjusttableColumns['130'].width"
          :visible="adjusttableColumns['130'].visible"
          :params="{ sortId: '130', summary: false }"
          tree-node
          resizable
          min-width="180px"
          align="center"
          header-align="center"
          sortable
          show-overflow
          class="dc-ux-table-column_ElTableColumn"
        ></ux-table-column>
        <ux-table-column
          :field="adjusttableColumns['131'].field"
          :title="adjusttableColumns['131'].title"
          :width="adjusttableColumns['131'].width"
          :visible="adjusttableColumns['131'].visible"
          :params="{ sortId: '131', summary: false }"
          tree-node
          resizable
          min-width="180px"
          align="center"
          header-align="center"
          sortable
          show-overflow
          class="dc-ux-table-column_ElTableColumn"
        ></ux-table-column>
        <ux-table-column
          :field="adjusttableColumns['132'].field"
          :title="adjusttableColumns['132'].title"
          :width="adjusttableColumns['132'].width"
          :visible="adjusttableColumns['132'].visible"
          :params="{ sortId: '132', summary: false }"
          tree-node
          resizable
          min-width="180px"
          align="center"
          header-align="center"
          sortable
          show-overflow
          class="dc-ux-table-column_ElTableColumn"
        ></ux-table-column>
        <ux-table-column
          :field="adjusttableColumns['133'].field"
          :title="adjusttableColumns['133'].title"
          :width="adjusttableColumns['133'].width"
          :visible="adjusttableColumns['133'].visible"
          :params="{ sortId: '133', summary: false }"
          tree-node
          resizable
          min-width="180px"
          align="center"
          header-align="center"
          sortable
          show-overflow
          class="dc-ux-table-column_ElTableColumn"
        ></ux-table-column>
        <ux-table-column
          :field="adjusttableColumns['134'].field"
          :title="adjusttableColumns['134'].title"
          :width="adjusttableColumns['134'].width"
          :visible="adjusttableColumns['134'].visible"
          :params="{ sortId: '134', summary: false }"
          tree-node
          resizable
          min-width="180px"
          align="center"
          header-align="center"
          sortable
          show-overflow
          class="dc-ux-table-column_ElTableColumn"
        ></ux-table-column>
        <ux-table-column
          :field="adjusttableColumns['137'].field"
          :title="adjusttableColumns['137'].title"
          :width="adjusttableColumns['137'].width"
          :visible="adjusttableColumns['137'].visible"
          :params="{ sortId: '137', summary: false }"
          tree-node
          resizable
          min-width="180px"
          align="center"
          header-align="center"
          sortable
          show-overflow
          class="dc-ux-table-column_ElTableColumn"
        ></ux-table-column>
      </ux-grid>
      <el-pagination
        :total="adjustDataTotal"
        :page-size="adjustsearch.limit"
        background
        :current-page="adjustDataPage"
        :page-sizes="[5, 10, 20, 30, 40, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        v-on:size-change="onAdjustSizeChange"
        v-on:current-change="onAdjustCurrentChange"
        class="dc-el-pagination_ElPagination"
      ></el-pagination>
      <div slot="top" class="dialog-footer">
        <el-button @click="dialogAdjustVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogAdjustVisible = false"
        >确 定</el-button
        >
      </div>
    </el-dialog>
  </el-row>
</template>
<script>
  import { validatenull } from "@/utils/validate";
  import {
    listTfamAssetPage,
    deleteTfamAsset,
  } from "@/api/fixasset/asset/tfamAsset";
  import { listResourcePermission } from "@/api/admin/common/permission";
  /** 根据用户界面配置import组件 开始 */
  import History from "@/views/components/history";
  import EditForm from "./form";
  import EditForm1 from "./form1";
  import QueryTag from "@/views/components/queryTag";
  import QueryConditionPanel from "@/views/components/queryConditionPanel";
  import { listTfamCategoryAll } from "@/api/fixasset/category/tfamCategory.js";
  import { listDepartmentAll } from "@/api/org/department.js";
  import { listTfamUsingstatusAll } from "@/api/fixasset/usingstatus/tfamUsingstatus.js";
  import { listTfamAlterPage } from "@/api/fixasset/alter/tfamAlter.js";
  import {
    listTfamAssetAudit,
    assetDiaoboTransfer,
  } from "@/api/fixasset/asset/tfamAssetTransfer.js";
  // import { listTfamAssetAll } from "@/api/fixasset/asset/tfamAsset.js";
  import { listUserAll } from "@/api/admin/user.js";
  import ViewColumnsSelect from "@/views/components/DcViewColumnsSelect";
  /** 根据用户界面配置import组件 结束 */
  import metadata from "./metadata";
  import ExportExcelButton from "@/components/ExportExcelButton";
  import DcMain from "@/views/components/dcMain";
  import OperationIcon from "@/components/OperationIcon";
  export default {
    extends: DcMain,
    components: {
      /** 根据用户界面配置组件 开始 */
      History,
      EditForm,
      EditForm1,
      QueryTag,
      QueryConditionPanel,
      ViewColumnsSelect,
      /** 根据用户界面配置组件 结束 */
      ExportExcelButton,
      OperationIcon,
    },
    data() {
      return {
        dialogTableVisible: false,
        dialogFormVisible: false,
        dialogAdjustVisible: false,
        checkdata: {},
        adjustArr: [],
        // 查询表格列头
        adjusttableColumns: {
          148: {
            title: "序号",
            isShow: true,
            visible: true,
            order: 0,
          },
          149: {
            isShow: true,
            visible: true,
            order: 1,
          },
          129: {
            title: "变动日期",
            field: "updateDate",
            visible: true,
            order: 2,
          },
          130: {
            title: "变动名称",
            field: "alterType",
            visible: true,
            order: 3,
          },
          131: {
            title: "变动原因",
            field: "reason",
            visible: true,
            order: 4,
          },
          132: {
            title: "变动前值",
            field: "oldValue",
            visible: true,
            order: 5,
          },
          133: {
            title: "变动后值",
            field: "newValue",
            visible: true,
            order: 6,
          },
          134: {
            title: "变动者",
            field: "updateBy",
            visible: true,
            order: 7,
          },
          137: {
            title: "变动关联凭证号",
            field: "voucherid",
            visible: true,
            order: 8,
          },
        },
        // 调整记录分页属性
        adjustDataPage: 1,
        adjustDataTotal: 0,
        adjustsearch: {
          // 查询条件   业务表设置的筛选条件
          params: [],
          offset: 0,
          limit: 10,
          columnName: "", // 排序字段名
          order: "", // 排序
        },

        gridData: [],
        diaoboFormData: {
          usedeptid: "",
          userid: null,
          employeeno: "",
          reason: "",
        },
        useridOptions: [], // 责任人选项
        scrapeReason: "",
        permission: {
          view: false,
          add: false,
          edit: false,
          remove: false,
          export: false,
        },
        metadata,
        /** 根据用户界面配置生成data数据 开始 */
        queryFormData: {
          category: {
            id: null,
            name: null,
            fullname: null,
            fullcode: null,
          },
          assetCode: "", // 资产编号
          assetName: "", // 资产名称
          userstatus: null,
          usedeptid: {
            id: null,
            name: null,
            code: null,
            companyId: null,
          },
          userid: "", // 责任人
          usingstatus: {
            id: null,
            name: null,
            code: null,
            remarks: null,
            companyId: null,
          },
          servicemonth: "", // 剩余使用月数
        },
        tableData: [],
        // 查询表格列头
        tableColumns: {
          249: {
            title: "序号",
            isShow: true,
            visible: true,
            order: 0,
          },
          250: {
            isShow: true,
            visible: true,
            order: 1,
          },
          121: {
            title: "资产编号",
            field: "assetCode",
            visible: true,
            order: 2,
          },
          127: {
            title: "资产类别",
            field: "category.name",
            visible: true,
            order: 3,
          },
          128: {
            title: "资产名称",
            field: "assetName",
            visible: true,
            order: 4,
          },
          137: {
            title: "使用部门",
            field: "usedeptid.name",
            visible: true,
            order: 5,
          },
          140: {
            title: "责任人",
            field: "userid.name",
            visible: true,
            order: 6,
          },
          209: {
            title: "责任人工号",
            field: "employeeno",
            visible: true,
            order: 7,
          },
          259: {
            title: "在职状态",
            field: "userstatus",
            visible: true,
            order: 8,
          },
          183: {
            title: "建卡人",
            field: "createBy",
            visible: true,
            order: 9,
          },
          190: {
            title: "建卡日期",
            field: "createDate",
            visible: true,
            order: 10,
          },
          197: {
            title: "增加方式",
            field: "addReason.name",
            visible: true,
            order: 11,
          },
          146: {
            title: "使用状况",
            field: "usingstatus.name",
            visible: true,
            order: 12,
          },
          189: {
            title: "剩余使用月数",
            field: "servicemonth",
            visible: true,
            order: 13,
          },
          113: {
            title: "操作",
            width: "160px",
            visible: true,
            order: 14,
          },
        },
        // 分页属性
        tableDataPage: 1,
        tableDataTotal: 0,
        // 选项变量
        // 资产类别选项
        categoryOptions: [],
        // 在职状态选项
        statusOptions: [
          {
            name: "在职",
            value: "0",
          },
          {
            name: "离职",
            value: "1",
          },
        ],
        // 使用部门选项
        usedeptidOptions: [],
        // 使用状况选项
        usingstatusOptions: [],
        /** 根据用户界面配置生成data数据 结束 */
        version: 6,
        search: {
          // 查询条件   业务表设置的筛选条件
          params: [],
          offset: 0,
          limit: 10,
          columnName: "", // 排序字段名
          order: "", // 排序
        },
        searchList: [],
        tableId: "1633870636743041902",
        schemeId: "1633870636743041900",
      };
    },
    computed: {},
    methods: {
      // 【 待优化分页效果，暂时没有，为了查询在职状态】
      getList() {
        this.setLoad();
        /* 查询参数 和数据权限 */
        this.search.params = [];
        if (this.isQueryConditionPanel) {
          this.search.params = this.search.params.concat(
            this.compositeCondition()
          );
        } else {
          this.search.params.push({
            columnName: "category_id",
            queryType: "=",
            value: !validatenull(this.queryFormData.category.id)
              ? this.queryFormData.category.id
              : null,
          });
          this.search.params.push({
            columnName: "asset_code",
            queryType: "like",
            value: !validatenull(this.queryFormData.assetCode)
              ? this.queryFormData.assetCode
              : null,
          });
          this.search.params.push({
            columnName: "asset_name",
            queryType: "like",
            value: !validatenull(this.queryFormData.assetName)
              ? this.queryFormData.assetName
              : null,
          });
          this.search.params.push({
            columnName: "userstatus",
            queryType: "=",
            value: !validatenull(this.queryFormData.userstatus)
              ? this.queryFormData.userstatus
              : null,
          });
          this.search.params.push({
            columnName: "usedeptid",
            queryType: "=",
            value: !validatenull(this.queryFormData.usedeptid.id)
              ? this.queryFormData.usedeptid.id
              : null,
          });
          this.search.params.push({
            columnName: "userid",
            queryType: "like",
            value: !validatenull(this.queryFormData.userid.id)
              ? this.queryFormData.userid.id
              : null,
          });
          this.search.params.push({
            columnName: "usingstatus",
            queryType: "=",
            value: !validatenull(this.queryFormData.usingstatus.id)
              ? this.queryFormData.usingstatus.id
              : null,
          });
          this.search.params.push({
            columnName: "servicemonth",
            queryType: "like",
            value: !validatenull(this.queryFormData.servicemonth)
              ? this.queryFormData.servicemonth
              : null,
          });
          this.search.params.push({
            columnName: "status",
            queryType: "=",
            value: "2,4",
          });
        }
        // 数据权限: 卡片表tfam_asset
        this.pushDataPermissions(
          this.search.params,
          this.$route.meta.routerId,
          this.tableId
        );
        listTfamAssetPage(this.search)
          .then((responseData) => {
            if (responseData.code == 100) {
              this.tableDataTotal = responseData.data.total;
              this.tableData = responseData.data.rows
                ? responseData.data.rows
                : [];
            } else {
              this.showMessage(responseData);
            }
            this.resetLoad();
          })
          .catch((error) => {
            this.outputError(error);
          });
      },
      onSearch() {
        if (this.isQueryConditionPanel) {
          this.search.offset = 0;
          this.tableDataPage = 1;
          this.getList();
        } else {
          this.$refs["queryForm"].validate((valid) => {
            if (valid) {
              this.search.offset = 0;
              this.tableDataPage = 1;
              this.getList();
            } else {
              return false;
            }
          });
        }
      },
      onSizeChange(val) {
        this.tableDataPage = 1;
        this.search.limit = val;
        this.search.offset = (this.tableDataPage - 1) * val;
        this.getList();
      },
      onCurrentChange(val) {
        this.search.offset = (val - 1) * this.search.limit;
        this.tableDataPage = val;
        this.getList();
      },

      async pageInit() {
        this.setLoad();
        this.initOptions(this.queryModel);
        this.onSearch();
        listResourcePermission(this.$route.meta.routerId)
          .then((responseData) => {
            if (responseData.code == 100) {
              this.permission.view = responseData.data.find((item) => {
                return item.permission === "adjust:read";
              });
              this.permission.export = responseData.data.find((item) => {
                return item.permission === "adjust:export";
              });
              this.permission.add = responseData.data.find((item) => {
                return item.permission === "adjust:create";
              });
              this.permission.edit = responseData.data.find((item) => {
                return item.permission === "adjust:update";
              });
              this.permission.remove = responseData.data.find((item) => {
                return item.permission === "adjust:delete";
              });
            } else {
              this.showMessage(responseData);
            }
            this.resetLoad();
          })
          .catch((error) => {
            this.outputError(error);
          });
      },
      onView(scope) {
        this.$refs.editForm.$emit("openViewDialog", scope.row.id);
      },
      onCreate() {
        this.$refs.editForm.$emit("openAddDialog");
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

      // 负责人改变
      useridOptionsChange() {
        this.diaoboFormData.employeeno =
          this.diaoboFormData.userid.empolyno || "";
      },

      // 调拨
      allocateFun() {
        this.gridData = [];
        this.diaoboFormData = {
          usedeptid: "",
          userid: null,
          employeeno: "",
          reason: "",
        };
        this.listUseridOptions();

        let checkBoxData = this.$refs.table.getCheckboxRecords();
        if (checkBoxData.length <= 0) {
          this.showMessage({
            type: "warning",
            msg: `请选择需要调拨的卡片`,
          });
          return;
        }

        this.gridData = checkBoxData;
        console.log("调拨展示的数据", checkBoxData);

        this.dialogTableVisible = true;
      },

      // 调整
      onAdjust(scope) {
        // if (scope.row.status.name == "新增待审核") {
        //   this.showMessage({
        //     type: "warning",
        //     msg: `该卡片状态为${scope.row.status.name}，不可调整`,
        //   });
        // } else {
        // this.$refs.editForm.$emit("openAdjustDialog", scope.row.id);
        // }
        this.$refs.editForm.$emit("openAdjustDialog", scope.row.id);
      },
      onEdit(scope) {
        this.$refs.editForm.$emit("openEditDialog", scope.row.id);
      },
      onCopy(scope) {
        this.$refs.editForm.$emit("openCopyDialog", scope.row.id);
      },
      onDelete(scope) {
        this.$confirm("确定删除吗？", "确认", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            this.setLoad();
            deleteTfamAsset(scope.row)
              .then((responseData) => {
                if (responseData.code == 100) {
                  this.getList();
                  this.showMessage({
                    type: "success",
                    msg: "删除成功",
                  });
                } else {
                  this.showMessage(responseData);
                }
                this.resetLoad();
              })
              .catch((error) => {
                this.outputError(error);
              });
          })
          .catch(() => {});
      },
      onSortChange(orderby) {
        if (validatenull(orderby.prop)) {
          this.search.columnName = "";
          this.search.order = "";
        } else {
          this.search.columnName = orderby.prop;
          this.search.order = orderby.order;
        }
        this.getList();
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
      // UserStatusOptions() {
      //   let search_List = {
      //     params: [],
      //   };
      //   // filter条件
      //   search_List.params.push.apply(search_List.params, [
      //     {
      //       columnName: "userstatus",
      //       queryType: "=",
      //       value: "1",
      //     },
      //   ]);
      //   // 数据权限: 卡片状态表 对应卡片中 statustfam_status
      //   this.pushDataPermissions(
      //     search_List.params,
      //     this.$route.meta.routerId,
      //     "1641034040897356409"
      //   );
      //   listTfamAssetAll(search_List).then((responseData) => {
      //     if (responseData.code == 100) {
      //       this.statusOptions = responseData.data;
      //     } else {
      //       this.showMessage(responseData);
      //     }
      //   });
      // },
      listUsedeptidOptions() {
        let search_List = {
          params: [
            {
              columnName: "company_id",
              queryType: "=",
              value: currentUser.company.id == "0" ? "" : currentUser.company.id,
            },
          ],
        };
        // filter条件
        search_List.params.push.apply(search_List.params, []);
        // 数据权限: 部门org_department
        this.pushDataPermissions(
          search_List.params,
          this.$route.meta.routerId,
          "1633870636743041550"
        );
        listDepartmentAll(search_List).then((responseData) => {
          if (responseData.code == 100) {
            this.usedeptidOptions = responseData.data;
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
      // 初始化自定义类型选择框选项变量
      initOptions(This) {
        this.listCategoryOptions();
        // this.UserStatusOptions();
        this.listUsedeptidOptions();
        this.listUsingstatusOptions();
      },

      onAdjustSizeChange(val) {
        this.adjustDataPage = 1;
        this.adjustsearch.limit = val;
        this.adjustsearch.offset = (this.adjustDataPage - 1) * val;
        this.adjustListFun();
      },
      onAdjustCurrentChange(val) {
        this.adjustsearch.offset = (val - 1) * this.adjustsearch.limit;
        this.adjustDataPage = val;
        this.adjustListFun();
      },

      // 查看调整记录
      // adjustListFun(code) {
      //   if (code == "1") {
      //     // 第一次查询
      //     this.adjustsearch.offset = 0;
      //     this.adjustDataPage = 1;
      //   }
      //   let checkBoxData = this.$refs.table.getCheckboxRecords();
      //   if (checkBoxData.length <= 0) {
      //     this.showMessage({
      //       type: "warning",
      //       msg: `请选择需要查看调整记录的卡片`,
      //     });
      //     return;
      //   } else if (checkBoxData.length > 1) {
      //     this.showMessage({
      //       type: "warning",
      //       msg: "请只选择一张卡片",
      //     });
      //     return;
      //   }
      //   this.checkdata = checkBoxData[0];
      //   this.dialogAdjustVisible = true;
      //   this.setLoad();
      //
      //   this.adjustsearch.params = [];
      //   this.adjustsearch.params.push({
      //     columnName: "asset_id",
      //     queryType: "like",
      //
      //     value: checkBoxData[0].id ? checkBoxData[0].id : null,
      //   });
      //   console.log("查看调整记录====查询条件", this.adjustsearch);
      //   listTfamAlterPage(this.adjustsearch).then((responseData) => {
      //     if (responseData.code == 100) {
      //       this.adjustDataTotal = responseData.data.total;
      //       this.adjustArr = responseData.data.rows ? responseData.data.rows : [];
      //       console.log("调整记录", responseData);
      //       this.resetLoad();
      //     } else {
      //       this.showMessage(responseData);
      //     }
      //   });
      // },
      adjustListFun() {
        let checkBoxData = this.$refs.table.getCheckboxRecords();
        if (checkBoxData.length <= 0) {
          this.showMessage({
            type: "warning",
            msg: `请选择需要查看调整记录的卡片`,
          });
          return;
        } else if (checkBoxData.length > 1) {
          this.showMessage({
            type: "warning",
            msg: "请只选择一张卡片",
          });
          return;
        }
        this.checkdata = checkBoxData[0];
        this.$refs.editForm1.$emit("openAddDialog", this.checkdata);
      },
      // 调拨弹框提交
      submitTableDialog() {
        this.$refs["diaoboForm"].validate((valid) => {
          if (valid) {
            let idsarr = [];
            this.gridData.map((item) => {
              idsarr.push(item.id);
            });
            let params = {
              ids: idsarr,
              deptId: this.diaoboFormData.usedeptid.id,
              userId: this.diaoboFormData.userid.id,
              reason: this.diaoboFormData.reason,
            };
            console.log("调拨校验通过====", params);
            assetDiaoboTransfer(params).then((responseData) => {
              if (responseData.code == 100) {
                this.showMessage({
                  type: "success",
                  msg: `调拨通过`,
                });
                this.getList();
                this.dialogTableVisible = false;
              } else {
                this.dialogTableVisible = false;
                this.showMessage(responseData);
              }
            });
          } else {
            return false;
          }
        });
      },

      // 报销打开减少类型弹框
      submitReasonDialog() {
        this.onOpreFun("scrap");
      },

      // 卡片操作 反审核，报废
      onOpreFun(code) {
        let checkBoxData = this.$refs.table.getCheckboxRecords();
        if (checkBoxData.length <= 0) {
          this.showMessage({
            type: "warning",
            msg: `请选择至少一张卡片`,
          });
          return;
        }

        let idsarr = [];
        let idtext = "";
        let moveType = null;
        switch (code) {
          case "stop": {
            idtext = "反审核";
            moveType = 2;
            break;
          }
          case "scrap": {
            idtext = "报废";
            moveType = 1;
            break;
          }
          default: {
            idtext = "";
            moveType = null;
            break;
          }
        }

        checkBoxData.map((item) => {
          idsarr.push(item.id);
        });

        let params = {
          ids: idsarr,
          moveType: moveType,
        };
        if (code == "scrap") {
          params.scrapeReason = this.scrapeReason;
        } else {
          params.scrapeReason = "";
          this.scrapeReason = "";
        }

        listTfamAssetAudit(params).then((responseData) => {
          if (responseData.code == 100) {
            if (
              responseData.data.noNeedAuditAssets &&
              responseData.data.noNeedAuditAssets.length > 0
            ) {
              this.showMessage({
                type: "dangerous",
                msg: responseData.data.cardSets || `${idtext}未通过`,
              });
            } else {
              this.showMessage({
                type: "success",
                msg: `${idtext}提交通过`,
              });
            }
            this.dialogFormVisible = false;

            this.onSearch();
          } else {
            this.dialogFormVisible = false;
            this.showMessage(responseData);
          }
        });
      },
    },
    watch: {},
    mounted() {
      this.pageInit();
      this.columnDrop(this.$refs.table);
    },
  };
</script>
