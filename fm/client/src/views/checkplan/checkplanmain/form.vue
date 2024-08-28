<template>
  <el-row class="dc-container">
    <el-dialog
      v-on:open="onDialogOpen"
      v-on:close="onDialogClose"
      v-loading="loading"
      width="80%"
      :fullscreen="false"
      :title="dialogProps.title"
      :visible.sync="dialogProps.visible"
      :close-on-click-modal="false"
      class="dc-el-dialog_ElDialog"
    >
      <el-row>
        <p class="title_line">任务设置</p>
        <el-form
          ref="editForm"
          :model="editFormData"
          label-width="100px"
          :disabled="action == 'view'"
          class="dc-el-form_ElEditForm"
        >
          <el-row>
            <el-col :span="12">
              <el-form-item
                prop="applyNo"
                label="申请单号"
                class="dc-el-form-item_SingleInput"
              >
                <el-input
                  v-model="editFormData.applyNo"
                  :maxLength="255"
                  placeholder="请输入申请单号"
                  clearable
                  disabled
                  autocomplete="new-password"
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="registryDate"
                label="登记日期"
                class="dc-el-form-item_SingleInput"
              >
                <el-date-picker
                  v-model="editFormData.registryDate"
                  type="date"
                  class="dc-el-input_SingleInput"
                  style="width: 100%"
                  disabled
                  placeholder="请输入登记日期"
                  value-format="yyyy-MM-dd"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="registryPerson.id"
                label="登记人"
                class="dc-el-form-item_SelectInput"
              >
                <el-input
                  v-model="editFormData.registryPerson.name"
                  :maxLength="-1"
                  placeholder="请输入登记人"
                  clearable
                  disabled
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="taskName"
                label="任务名称"
                :rules="[
                  {
                    required: true,
                    message: '任务名称不能为空',
                    trigger: 'blur',
                  },
                ]"
                class="dc-el-form-item_SingleInput"
              >
                <el-input
                  v-model="editFormData.taskName"
                  :maxLength="255"
                  placeholder="请输入计划名称"
                  clearable
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="startDate"
                label="开始时间"
                :rules="[
                  {
                    required: true,
                    message: '开始时间不能为空',
                    trigger: 'blur',
                  },
                ]"
                class="dc-el-form-item_SingleInput"
              >
                <el-date-picker
                  v-model="editFormData.startDate"
                  type="date"
                  class="dc-el-input_SingleInput"
                  style="width: 100%"
                  placeholder="请输入开始时间"
                  value-format="yyyy-MM-dd"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="endDate"
                label="线束日期"
                :rules="[
                  {
                    required: true,
                    message: '线束日期不能为空',
                    trigger: 'blur',
                  },
                ]"
                class="dc-el-form-item_SingleInput"
              >
                <el-date-picker
                  v-model="editFormData.endDate"
                  type="date"
                  class="dc-el-input_SingleInput"
                  style="width: 100%"
                  placeholder="请输入线束日期"
                  value-format="yyyy-MM-dd"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item
                prop="remarks"
                label="备注信息"
                :rules="[
                  {
                    required: true,
                    message: '备注信息不能为空',
                    trigger: 'blur',
                  },
                ]"
                class="dc-el-form-item_MutilpleInput"
              >
                <el-input
                  v-model="editFormData.remarks"
                  type="textarea"
                  placeholder="请输入备注信息"
                  rows="2"
                  :maxLength="255"
                  class="dc-el-input_MutilpleInput"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>

        <el-divider class="dc-el-divider_ElDivider"></el-divider>
        <!-- 盘点部门范围 -->
        <div>
          <p class="title_line">
            {{ action !== "detail" ? "盘点部门范围" : "盘点范围" }}
          </p>
          <el-row :gutter="20" v-if="action !== 'detail'">
            <el-col :span="16">
              <div class="left-box">
                <div class="item-box">
                  <p class="text-line">部门范围：</p>
                  <el-select
                    v-model="categoryObj.deptName"
                    multiple
                    placeholder="请选择"
                    style="width: 100%"
                  >
                    <el-option>
                      <el-checkbox
                        v-model="deptNamechecked"
                        @change="deptNameCheckAllFun"
                        >全选</el-checkbox
                      >
                    </el-option>
                    <el-option
                      v-for="item in usedeptidOptions"
                      :key="item['id']"
                      :label="item['name']"
                      :value="item['id']"
                    ></el-option>
                  </el-select>
                </div>
              </div>
            </el-col>
          </el-row>
          <ux-grid
            v-if="action == 'detail'"
            column-key
            ref="tableDept"
            :data="editFormData.deptIds"
            border
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
              :params="{ sortId: '179', summary: false }"
              type="index"
              tree-node
              resizable
              min-width="40px"
              fixed="left"
              align="center"
              header-align="center"
              class="dc-ux-table-column_ElTableNumColumn"
            ></ux-table-column>
            <ux-table-column
              v-if="action !== 'detail'"
              :params="{ sortId: '180', summary: false }"
              type="checkbox"
              tree-node
              resizable
              min-width="40px"
              fixed="left"
              align="center"
              header-align="center"
              class="dc-ux-table-column_ElTableCheckBoxColumn"
            ></ux-table-column>
            <ux-table-column
              :field="tablebumenColumns['141'].field"
              :title="tablebumenColumns['141'].title"
              :width="tablebumenColumns['141'].width"
              :visible="tablebumenColumns['141'].visible"
              :params="{ sortId: '141', summary: false }"
              tree-node
              resizable
              min-width="120px"
              align="center"
              header-align="center"
              show-overflow
              class="dc-ux-table-column_ElTableColumn"
            ></ux-table-column>
            <ux-table-column
              :field="tablebumenColumns['142'].field"
              :title="tablebumenColumns['142'].title"
              :width="tablebumenColumns['142'].width"
              :visible="tablebumenColumns['142'].visible"
              :params="{ sortId: '142', summary: false }"
              tree-node
              resizable
              min-width="120px"
              align="center"
              header-align="center"
              show-overflow
              class="dc-ux-table-column_ElTableColumn"
            ></ux-table-column>
            <ux-table-column
              :field="tablebumenColumns['143'].field"
              :title="tablebumenColumns['143'].title"
              :width="tablebumenColumns['143'].width"
              :visible="tablebumenColumns['143'].visible"
              :params="{ sortId: '143', summary: false }"
              tree-node
              resizable
              min-width="120px"
              align="center"
              header-align="center"
              show-overflow
              class="dc-ux-table-column_ElTableColumn"
            ></ux-table-column>
            <ux-table-column
              :field="tablebumenColumns['144'].field"
              :title="tablebumenColumns['144'].title"
              :width="tablebumenColumns['144'].width"
              :visible="tablebumenColumns['144'].visible"
              :params="{ sortId: '144', summary: false }"
              tree-node
              resizable
              min-width="120px"
              align="center"
              header-align="center"
              show-overflow
              class="dc-ux-table-column_ElTableColumn"
            ></ux-table-column>
            <ux-table-column
              :field="tablebumenColumns['145'].field"
              :title="tablebumenColumns['145'].title"
              :width="tablebumenColumns['145'].width"
              :visible="tablebumenColumns['145'].visible"
              :params="{ sortId: '145', summary: false }"
              tree-node
              resizable
              min-width="120px"
              align="center"
              header-align="center"
              show-overflow
              class="dc-ux-table-column_ElTableColumn"
            ></ux-table-column>
          </ux-grid>
        </div>

        <el-divider class="dc-el-divider_ElDivider"></el-divider>
        <!-- 盘点资产 -->
        <div>
          <p class="title_line">盘点资产</p>
          <el-row :gutter="20" v-if="action !== 'detail'">
            <el-col :span="16">
              <div class="left-box">
                <div class="item-box">
                  <p class="text-line">资产类别：</p>
                  <el-select
                    v-model="categoryObj.categoryName"
                    multiple
                    placeholder="请选择"
                    style="width: 100%"
                  >
                    <el-option>
                      <el-checkbox
                        v-model="categoryNamechecked"
                        @change="categoryNameCheckAllFun"
                        >全选</el-checkbox
                      >
                    </el-option>
                    <el-option
                      v-for="item in categoryOptions"
                      :key="item['id']"
                      :label="item['fullname']"
                      :value="item['id']"
                    ></el-option>
                  </el-select>
                </div>

                <div class="item-box">
                  <p class="text-line">使用状况：</p>
                  <el-select
                    v-model="categoryObj.statusName"
                    multiple
                    placeholder="请选择"
                    style="width: 100%"
                  >
                    <el-option>
                      <el-checkbox
                        v-model="usingstatusNamechecked"
                        @change="usingstatusNameCheckAllFun"
                        >全选</el-checkbox
                      >
                    </el-option>
                    <el-option
                      v-for="item in usingstatusOptions"
                      :key="item['id']"
                      :label="item['name']"
                      :value="item['id']"
                    ></el-option>
                  </el-select>
                </div>
              </div>
            </el-col>
            <el-col :span="8">
              <div class="right-box">
                <el-tooltip disabled class="dc-el-tooltip_Button">
                  <el-button
                    type="primary"
                    icon="el-icon-plus"
                    class="dc-el-button_Button"
                    @click="addCategoryFun"
                    >添加</el-button
                  >
                </el-tooltip>
                <el-tooltip disabled class="dc-el-tooltip_Button">
                  <el-button
                    type="primary"
                    icon="el-icon-minus"
                    class="dc-el-button_Button"
                    @click="deleteCategoryFun"
                    >删除</el-button
                  >
                </el-tooltip>
              </div>
            </el-col>
          </el-row>

          <ux-grid
            column-key
            ref="tableCategory"
            :data="editFormData.statusAndCategoryIds"
            border
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
              :params="{ sortId: '176', summary: false }"
              type="index"
              tree-node
              resizable
              min-width="40px"
              fixed="left"
              align="center"
              header-align="center"
              class="dc-ux-table-column_ElTableNumColumn"
            ></ux-table-column>
            <ux-table-column
              v-if="action !== 'detail'"
              :params="{ sortId: '177', summary: false }"
              type="checkbox"
              tree-node
              resizable
              min-width="40px"
              fixed="left"
              align="center"
              header-align="center"
              class="dc-ux-table-column_ElTableCheckBoxColumn"
            ></ux-table-column>
            <ux-table-column
              :field="tablezichanColumns['150'].field"
              :title="tablezichanColumns['150'].title"
              :width="tablezichanColumns['150'].width"
              :visible="tablezichanColumns['150'].visible"
              :params="{ sortId: '150', summary: false }"
              tree-node
              resizable
              min-width="180px"
              align="center"
              header-align="center"
              show-overflow
              class="dc-ux-table-column_ElTableColumn"
            ></ux-table-column>
            <ux-table-column
              v-if="action !== 'detail'"
              :field="tablezichanColumns['151'].field"
              :title="tablezichanColumns['151'].title"
              :width="tablezichanColumns['151'].width"
              :visible="tablezichanColumns['151'].visible"
              :params="{ sortId: '151', summary: false }"
              tree-node
              resizable
              min-width="120px"
              align="center"
              header-align="center"
              show-overflow
              class="dc-ux-table-column_ElTableColumn"
            ></ux-table-column>
            <ux-table-column
              v-if="action !== 'detail'"
              :field="tablezichanColumns['152'].field"
              :title="tablezichanColumns['152'].title"
              :width="tablezichanColumns['152'].width"
              :visible="tablezichanColumns['152'].visible"
              :params="{ sortId: '152', summary: false }"
              tree-node
              resizable
              min-width="180px"
              align="center"
              header-align="center"
              show-overflow
              class="dc-ux-table-column_ElTableColumn"
            ></ux-table-column>
            <ux-table-column
              v-if="action === 'detail'"
              :field="tablebumenColumns['143'].field"
              :title="tablebumenColumns['143'].title"
              :width="tablebumenColumns['143'].width"
              :visible="tablebumenColumns['143'].visible"
              :params="{ sortId: '143', summary: false }"
              tree-node
              resizable
              min-width="120px"
              align="center"
              header-align="center"
              show-overflow
              class="dc-ux-table-column_ElTableColumn"
            ></ux-table-column>
            <ux-table-column
              :field="tablezichanColumns['153'].field"
              :title="tablezichanColumns['153'].title"
              :width="tablezichanColumns['153'].width"
              :visible="tablezichanColumns['153'].visible"
              :params="{ sortId: '153', summary: false }"
              tree-node
              resizable
              min-width="180px"
              align="center"
              header-align="center"
              show-overflow
              class="dc-ux-table-column_ElTableColumn"
            ></ux-table-column>
            <ux-table-column
              v-if="action === 'detail'"
              :field="tablezichanColumns['154'].field"
              :title="tablezichanColumns['154'].title"
              :width="tablezichanColumns['154'].width"
              :visible="tablezichanColumns['154'].visible"
              :params="{ sortId: '154', summary: false }"
              tree-node
              resizable
              min-width="180px"
              align="center"
              header-align="center"
              show-overflow
              class="dc-ux-table-column_ElTableColumn"
            ></ux-table-column>
            <ux-table-column
              v-if="action === 'detail'"
              :field="tablezichanColumns['155'].field"
              :title="tablezichanColumns['155'].title"
              :width="tablezichanColumns['155'].width"
              :visible="tablezichanColumns['155'].visible"
              :params="{ sortId: '155', summary: false }"
              tree-node
              resizable
              min-width="180px"
              align="center"
              header-align="center"
              show-overflow
              class="dc-ux-table-column_ElTableColumn"
            ></ux-table-column>
          </ux-grid>
        </div>

        <el-divider class="dc-el-divider_ElDivider"></el-divider>
        <!-- 资产盘点附件管理 -->
        <div>
          <p class="title_line">
            {{ action !== "detail" ? "资产盘点附件管理" : "盘点附件" }}
          </p>
          <el-tooltip
            disabled
            class="dc-el-tooltip_Button"
            v-if="action !== 'detail'"
          >
            <el-button
              type="primary"
              icon="el-icon-plus"
              class="dc-el-button_Button"
              @click="addFujian"
              >新增</el-button
            >
          </el-tooltip>
          <el-tooltip
            disabled
            class="dc-el-tooltip_Button"
            v-if="action !== 'detail'"
          >
            <el-button
              type="primary"
              icon="el-icon-edit-outline"
              class="dc-el-button_Button"
              @click="editFujian"
              >修改</el-button
            >
          </el-tooltip>
          <el-tooltip
            disabled
            class="dc-el-tooltip_Button"
            v-if="action !== 'detail'"
          >
            <el-button
              type="primary"
              icon="el-icon-minus"
              class="dc-el-button_Button"
              @click="deleteFujian"
              >删除</el-button
            >
          </el-tooltip>
          <ux-grid
            column-key
            ref="tablefujian"
            :data="editFormData.attachs"
            border
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
              :params="{ sortId: '174', summary: false }"
              type="index"
              tree-node
              resizable
              min-width="40px"
              fixed="left"
              align="center"
              header-align="center"
              class="dc-ux-table-column_ElTableNumColumn"
            ></ux-table-column>
            <ux-table-column
              v-if="action !== 'detail'"
              :params="{ sortId: '175', summary: false }"
              type="checkbox"
              tree-node
              resizable
              min-width="40px"
              fixed="left"
              align="center"
              header-align="center"
              class="dc-ux-table-column_ElTableCheckBoxColumn"
            ></ux-table-column>
            <ux-table-column
              :field="tablefujianColumns['163'].field"
              :title="tablefujianColumns['163'].title"
              :width="tablefujianColumns['163'].width"
              :visible="tablefujianColumns['163'].visible"
              :params="{ sortId: '163', summary: false }"
              tree-node
              resizable
              min-width="120px"
              align="center"
              header-align="center"
              show-overflow
              class="dc-ux-table-column_ElTableColumn"
            ></ux-table-column>
            <ux-table-column
              :field="tablefujianColumns['164'].field"
              :title="tablefujianColumns['164'].title"
              :width="tablefujianColumns['164'].width"
              :visible="tablefujianColumns['164'].visible"
              :params="{ sortId: '164', summary: false }"
              tree-node
              resizable
              min-width="120px"
              align="center"
              header-align="center"
              show-overflow
              class="dc-ux-table-column_ElTableColumn"
            ></ux-table-column>
            <ux-table-column
              v-if="action !== 'detail'"
              :field="tablefujianColumns['165'].field"
              :title="tablefujianColumns['165'].title"
              :width="tablefujianColumns['165'].width"
              :visible="tablefujianColumns['165'].visible"
              :params="{ sortId: '165', summary: false }"
              tree-node
              resizable
              min-width="180px"
              align="center"
              header-align="center"
              show-overflow
              class="dc-ux-table-column_ElTableColumn"
            ></ux-table-column>
            <ux-table-column
              v-if="action == 'detail'"
              :field="tablefujianColumns['166'].field"
              :title="tablefujianColumns['166'].title"
              :width="tablefujianColumns['166'].width"
              :visible="tablefujianColumns['166'].visible"
              :params="{ sortId: '166', summary: false }"
              tree-node
              resizable
              min-width="180px"
              align="center"
              header-align="center"
              show-overflow
              class="dc-ux-table-column_ElTableColumn"
            ></ux-table-column>
            <ux-table-column
              v-if="action == 'detail'"
              title="操作"
              :params="{ sortId: '167' }"
              tree-node
              resizable
              width="160px"
              fixed="right"
              align="center"
              header-align="center"
              class="dc-ux-table-column_ElTableOptColumn"
            >
              <template v-slot:header="scope">
                <span>操作</span>
              </template>
              <template slot-scope="scope">
                <OperationIcon
                  type="primary"
                  content="下载"
                  placement="bottom"
                  icon-name="el-icon-download"
                  class="dc-OperationIcon_IconButton"
                  @click="downloadFiles(scope)"
                ></OperationIcon>
              </template>
            </ux-table-column>
          </ux-grid>
        </div>

        <el-divider class="dc-el-divider_ElDivider"></el-divider>
        <!-- 资产图表 -->
        <div v-if="action == 'detail'">
          <p class="title_line">
            资产总量：<span class="num-box">{{ editFormData.totalAsset }}</span>
          </p>
          <p class="title_line2">
            已盘点：<span class="num-box">{{ editFormData.alreadyNum }}</span>
          </p>
          <p class="title_line2">
            未盘点：<span class="num-box">{{
              editFormData.totalAsset * 1 - editFormData.alreadyNum * 1
            }}</span>
          </p>
          <div
            id="Chartbox"
            style="
              width: 50%;
              height: 300px;
              display: inline-block;
              border-right: 1px solid #eee;
            "
          ></div>
          <div
            id="Chartbox2"
            style="width: 49%; height: 300px; display: inline-block"
          ></div>
        </div>
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
    <!-- 附件弹框 -->
    <el-dialog
      title="附件上传配置"
      width="50%"
      :visible.sync="dialogattachsVisible"
    >
      <el-form :model="attachsform">
        <el-col :span="24">
          <el-form-item
            prop="name"
            label="附件名称"
            class="dc-el-form-item_AutoSerial"
          >
            <el-input
              v-model="attachsform.attachTitle"
              :maxLength="20"
              placeholder="请输入附件名称"
              class="dc-el-input_AutoSerial"
            >
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="是否必传" class="dc-el-form-item_SelectInput">
            <el-select
              v-model="attachsform.attachRequired"
              :style="{ width: '100%' }"
              placeholder="请选择是否必传"
              clearable
              value-key="id"
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option label="是" value="是"></el-option>
              <el-option label="否" value="否"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item
            prop="limit"
            label="数量限制"
            class="dc-el-form-item_AutoSerial"
          >
            <el-input
              v-model="attachsform.quantity"
              placeholder="请输入数量限制"
              type="number"
              min="0"
              class="dc-el-input_AutoSerial"
            >
            </el-input>
          </el-form-item>
        </el-col>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogattachsVisible = false">取 消</el-button>
        <el-button type="primary" @click="onAddattachsList">确 定</el-button>
      </div>
    </el-dialog>
  </el-row>
</template>
<script>
/** 根据用户界面配置import组件 结束 */
import {
  getTfamAssetCheckplanMainById,
  saveTfamAssetCheckplanMain,
  getByStatusAndCategory,
  getTfamAssetCheckplandetail,
  downloadCheckplandetail,
} from "@/api/checkplan/checkplanmain/tfamAssetCheckplanMain";
import { listTfamCategoryAll } from "@/api/fixasset/category/tfamCategory.js";
import { listTfamUsingstatusAll } from "@/api/fixasset/usingstatus/tfamUsingstatus.js";
import { listDepartmentAll } from "@/api/org/department.js";
import BaseUI from "@/views/components/baseUI";
import OperationIcon from "@/components/OperationIcon";
import moment from "moment";
export default {
  extends: BaseUI,
  name: "tfamAssetCheckplanMain-form",
  components: {
    /** 根据用户界面配置组件 开始 */

    /** 根据用户界面配置组件 结束 */

    OperationIcon,
  },
  data() {
    return {
      dialogattachsVisible: false,
      fujianFlag: "",
      editIndex: null,
      /** 根据用户界面配置生成data数据 开始 */
      editFormData: this.initEditData(),
      // 对话框属性变量
      dialogProps: {
        visible: false,
        title: "盘点主表",
      },
      dialogTitle: "盘点主表",
      // 查询表格列头
      tablebumenColumns: {
        179: {
          title: "序号",
          isShow: true,
          visible: true,
          order: 0,
        },
        180: {
          isShow: true,
          visible: true,
          order: 1,
        },
        141: {
          title: "部门名称",
          field: "deptName",
          visible: true,
          order: 1,
        },
        142: {
          title: "部门简称",
          field: "deptName",
          visible: true,
          order: 2,
        },
        143: {
          title: "数量合计",
          field: "total",
          visible: true,
          order: 3,
        },
        144: {
          title: "已盘点合计",
          field: "alreadlyCkeck",
          visible: true,
          order: 4,
        },
        145: {
          title: "盘点进度",
          field: "percent",
          visible: true,
          order: 5,
        },
      },
      categoryObj: {
        deptName: [], //  部门范围
        // 资产多选
        categoryName: [],
        statusName: [],
      },
      deptNamecheckedAllList: [],
      deptNamechecked: false,
      categoryNamecheckedAllList: [],
      categoryNamechecked: false,
      usingstatuscheckedAllList: [],
      usingstatusNamechecked: false,
      // 使用部门选项
      usedeptidOptions: [],
      // 资产类别选项
      categoryOptions: [],
      // 使用状况选项
      usingstatusOptions: [],

      // 查询表格列头
      tablezichanColumns: {
        176: {
          title: "序号",
          isShow: true,
          visible: true,
          order: 0,
        },
        177: {
          isShow: true,
          visible: true,
          order: 1,
        },
        150: {
          title: "资产类型",
          field: "categoryName",
          visible: true,
          order: 1,
        },
        151: {
          title: "使用状态",
          field: "statusName",
          visible: true,
          order: 2,
        },
        152: {
          title: "数量合计",
          field: "tot",
          visible: true,
          order: 3,
        },
        143: {
          title: "数量合计",
          field: "total",
          visible: true,
          order: 3,
        },
        153: {
          title: "计量单位",
          field: "unit",
          visible: true,
          order: 4,
        },
        154: {
          title: "已盘点合计",
          field: "alreadlyCkeck",
          visible: true,
          order: 5,
        },
        155: {
          title: "盘点进度",
          field: "percent",
          visible: true,
          order: 6,
        },
      },
      attachsform: {
        attachTitle: "", // 附件名称
        attachRequired: null, // 是否必传
        quantity: null, // 数量限制
      },
      // 查询表格列头
      tablefujianColumns: {
        174: {
          title: "序号",
          isShow: true,
          visible: true,
          order: 0,
        },
        175: {
          isShow: true,
          visible: true,
          order: 1,
        },
        163: {
          title: "附件名称",
          field: "attachTitle",
          visible: true,
          order: 1,
        },
        164: {
          title: "是否必传",
          field: "attachRequired",
          visible: true,
          order: 2,
        },
        165: {
          title: "数量限制",
          field: "quantity",
          visible: true,
          order: 3,
        },
        166: {
          title: "收到文件",
          field: "num",
          visible: true,
          order: 4,
        },
        167: {
          title: "操作",
          width: "160px",
          isShow: true,
          visible: true,
          order: 4,
        },
      },
      // 选项变量
      // 登记人选项
      // registryPersonOptions: [],
      /** 根据用户界面配置生成data数据 结束 */

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
      console.log(this.editFormData, "保存");
      this.$refs["editForm"].validate((valid) => {
        if (valid) {
          this.doSave();
        } else {
          return false;
        }
      });
    },
    doSave() {
      this.setLoad();

      saveTfamAssetCheckplanMain(this.editFormData)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.dialogProps.visible = false;
            this.showMessage({
              type: "success",
              msg: "保存成功",
            });
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

    // 修改查询接口信息
    getDetailById(id) {
      this.setLoad();
      return new Promise((resolve) => {
        getTfamAssetCheckplandetail({ id: id })
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

    getFormById(id) {
      this.setLoad();
      return new Promise((resolve) => {
        getTfamAssetCheckplanMainById(id)
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

    // 下载
    downloadFiles(scope) {
      this.setLoad();

      let params = {
        mainId: scope.row.main.id || "",
        attachCategory: scope.row.id || "",
      };

      this.setLoad();
      /* 查询参数 和数据权限 */
      downloadCheckplandetail(params)
        .then((responseData) => {
          let date = moment().format("YYYY-MM-DD");
          const blob = new Blob([responseData], {
            type: "application/zip;charset-UTF-8",
          });
          const url = window.URL.createObjectURL(blob);
          let dom = document.createElement("a");
          dom.style.display = "none";
          dom.href = url;
          dom.setAttribute("download", `${scope.row.attachTitle}_${date}.zip`);
          document.body.appendChild(dom);
          dom.click();
          // 释放内存
          window.URL.revokeObjectURL(url);
          this.resetLoad();
        })
        .catch((error) => {
          this.outputError(error);
        });
      this.resetLoad();
    },

    // // 部门范围添加
    // adddeptFun(){

    // },

    // // 删除部门范围
    // deletedeptFun() {

    // },

    // 新增附件
    addFujian() {
      this.fujianFlag = "add";
      this.editIndex = null;
      this.attachsform = {
        attachTitle: "", // 附件名称
        attachRequired: null, // 是否必传
        quantity: null, // 数量限制
      };
      this.dialogattachsVisible = true;
    },

    // 修改附件
    editFujian() {
      this.fujianFlag = "edit";
      let checkBoxData = this.$refs.tablefujian.getCheckboxRecords();
      if (checkBoxData.length <= 0) {
        this.showMessage({
          type: "warning",
          msg: `请选择一行`,
        });
        return;
      } else if (checkBoxData.length > 1) {
        this.showMessage({
          type: "warning",
          msg: "请单选修改",
        });
        return;
      }
      this.dialogattachsVisible = true;
      this.attachsform = checkBoxData[0];
      this.editIndex = this.editFormData.attachs.indexOf(checkBoxData[0]);
    },

    // 删除附件
    deleteFujian() {
      let checkBoxData = this.$refs.tablefujian.getCheckboxRecords();
      if (checkBoxData.length <= 0) {
        this.showMessage({
          type: "warning",
          msg: `请选择需要行`,
        });
        return;
      }
      checkBoxData.map((val) => {
        // console.log(this.editFormData.attachs, val, "删除的项");
        let index = this.editFormData.attachs.indexOf(val);
        this.editFormData.attachs.splice(index, 1);
      });
    },

    // 附件添加
    onAddattachsList() {
      if (this.fujianFlag === "add") {
        this.editFormData.attachs.push(this.attachsform);
      } else if (this.fujianFlag === "edit" && this.editIndex) {
        this.editFormData.attachs[this.editIndex] = this.attachsform;
      }
      this.dialogattachsVisible = false;
    },

    // 盘点资产删除
    deleteCategoryFun() {
      let checkBoxData = this.$refs.tableCategory.getCheckboxRecords();
      if (checkBoxData.length <= 0) {
        this.showMessage({
          type: "warning",
          msg: `请选择需要行`,
        });
        return;
      }
      checkBoxData.map((val) => {
        // console.log(this.editFormData.attachs, val, "删除的项");
        let index = this.editFormData.statusAndCategoryIds.indexOf(val);

        this.editFormData.statusAndCategoryIds.splice(index, 1);

        this.editFormData.statusIds.splice(index, 1),
          this.editFormData.categoryIds.splice(index, 1);
        // console.log(index, val, "删除的项", this.editFormData.statusAndCategoryIds, this.editFormData);
      });
    },

    // 盘点资产添加
    addCategoryFun() {
      if (this.categoryObj.deptName.length == 0) {
        this.showMessage({
          type: "warning",
          msg: `未选择部门范围`,
        });
        return;
      } else if (this.categoryObj.categoryName.length == 0) {
        this.showMessage({
          type: "warning",
          msg: `未选择资产类别`,
        });
        return;
      } else if (this.categoryObj.statusName.length == 0) {
        this.showMessage({
          type: "warning",
          msg: `未选择使用状况`,
        });
        return;
      }
      this.setLoad();
      let params = {
        deptIds: this.categoryObj.deptName,
        categoryIds: this.categoryObj.categoryName,
        statusIds: this.categoryObj.statusName,
      };
      getByStatusAndCategory(params)
        .then((responseData) => {
          if (responseData.code == 100) {
            console.log(responseData, "资产单位数量");
            this.editFormData.statusAndCategoryIds = responseData.data || [];
            this.editFormData.statusAndCategoryIds.map((item) => {
              this.editFormData.statusIds.push(item.statusId);
              this.editFormData.categoryIds.push(item.categoryId);
            });
            this.editFormData.deptIds = this.categoryObj.deptName;
          } else {
            this.showMessage(responseData);
          }
          this.resetLoad();
        })
        .catch((error) => {
          this.outputError(error);
        });
      this.resetLoad();
    },

    // 部门范围下拉
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

      listDepartmentAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.usedeptidOptions = responseData.data;
          this.usedeptidOptions.map((item) => {
            this.deptNamecheckedAllList.push(item.id);
          });
        } else {
          this.showMessage(responseData);
        }
      });
    },

    // 部门范围全选
    deptNameCheckAllFun() {
      if (this.deptNamechecked) {
        this.categoryObj.deptName = this.deptNamecheckedAllList;
      } else {
        this.categoryObj.deptName = [];
      }
    },

    // 资产类别全选
    categoryNameCheckAllFun() {
      if (this.categoryNamechecked) {
        this.categoryObj.categoryName = this.categoryNamecheckedAllList;
      } else {
        this.categoryObj.categoryName = [];
      }
    },

    // 资产类别下拉
    listCategoryOptions() {
      let search_List = {
        params: [],
      };
      // filter条件
      search_List.params.push.apply(search_List.params, [
        {
          columnName: "company_id",
          queryType: "=",
          value: "",
        },
      ]);

      listTfamCategoryAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.categoryOptions = responseData.data;
          this.categoryOptions.map((item) => {
            this.categoryNamecheckedAllList.push(item.id);
          });
        } else {
          this.showMessage(responseData);
        }
      });
    },

    // 使用状况全选
    usingstatusNameCheckAllFun() {
      if (this.usingstatusNamechecked) {
        this.categoryObj.statusName = this.usingstatuscheckedAllList;
      } else {
        this.categoryObj.statusName = [];
      }
    },

    // 使用状况下拉
    listUsingstatusOptions() {
      let search_List = {
        params: [],
      };
      // filter条件
      search_List.params.push.apply(search_List.params, [
        {
          columnName: "company_id",
          queryType: "=",
          value: "",
        },
      ]);

      listTfamUsingstatusAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.usingstatusOptions = responseData.data;
          this.usingstatusOptions.map((item) => {
            this.usingstatuscheckedAllList.push(item.id);
          });
        } else {
          this.showMessage(responseData);
        }
      });
    },

    initOptions(This) {
      // 初始化自定义类型选择框选项变量
      this.listUsedeptidOptions();
      this.listCategoryOptions();
      this.listUsingstatusOptions();
    },
    initEditData(This) {
      return {
        applyNo: "", // 申请单号
        registryDate: "", // 登记日期
        registryPerson: {
          id: null,
          name: null,
        },
        taskName: "", // 任务名称
        startDate: "", // 开始时间
        endDate: "", // 线束日期
        remarks: "", // 备注信息
        deptIds: [], // 盘点部门范围
        statusIds: [],
        categoryIds: [],
        statusAndCategoryIds: [
          {
            statusId: "",
            statusName: "",
            categoryId: "",
            categoryName: "",
            num: "",
            unit: "",
          },
        ], // 盘点资产
        attachs: [], // 资产盘点附件管理
      };
    },

    drawChart() {
      console.log(document.getElementById("Chartbox"), "====");
      let myChart = this.$echarts.init(document.getElementById("Chartbox"));
      // console.log(document.getElementById("Chartbox"), myChart, "====");
      let percent = (
        (this.editFormData.alreadyNum / this.editFormData.totalAsset) *
        100
      ).toFixed(2);
      let option = {
        tooltip: {
          formatter: "{a} <br/>{b} : {c}%",
        },
        toolbox: {
          show: true,
          orient: "vertical",
          left: "right",
          top: "top",
          feature: {
            saveAsImage: { show: true }, // 保存图表
          },
        },
        series: [
          {
            name: "Pressure",
            type: "gauge",
            radius: "100%",
            title: {
              // 仪表盘标题。
              show: true, // 是否显示标题,默认 true。
              offsetCenter: [0, "-30%"], //相对于仪表盘中心的偏移位置，数组第一项是水平方向的偏移，第二项是垂直方向的偏移。可以是绝对的数值，也可以是相对于仪表盘半径的百分比。
              color: "#333", // 文字的颜色,默认 #333。
              fontSize: 20, // 文字的字体大小,默认 15。
            },
            detail: {
              formatter: "{value}",
            },
            data: [
              {
                value: percent,
                name: "完成率",
              },
            ],
          },
        ],
      };
      myChart.setOption(option);
    },

    drawChart2() {
      let myChart = this.$echarts.init(document.getElementById("Chartbox2"));
      let chartdata = [];
      this.editFormData.deptIds.map((item) => {
        chartdata.push({
          name: item.deptName,
          value: item.percent.split("%")[0] * 1,
        });
      });
      let option = {
        title: {
          text: "部门资产盘点数量占比",
          left: "center",
          top: 0,
          textStyle: {
            color: "#333",
          },
        },
        tooltip: {
          trigger: "item",
        },
        // visualMap: {
        //   show: false,
        //   min: 0,
        //   max: 100,
        // },
        series: [
          {
            type: "pie",
            data: chartdata,
            radius: "70%",
          },
        ],
        // series: [
        //   {
        //     name: "盘点进度",
        //     type: "pie",
        //     radius: "70%",
        //     center: ["50%", "50%"],
        //     data: chartdata,
        //     roseType: "radius",
        //     label: {
        //       color: "#333",
        //     },
        //     labelLine: {
        //       lineStyle: {
        //         color: "#333",
        //       },
        //       smooth: 0.2,
        //       length: 10,
        //       length2: 20,
        //     },
        //     itemStyle: {
        //       color: "#d53a35",
        //     },
        //   },
        // ],
      };
      myChart.setOption(option);
    },
  },
  watch: {},
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
        this.dialogProps.visible = true;
      });

      this.$on("openEditDialog", async function (id) {
        this.action = "edit";
        this.dialogProps.title = `修改${this.dialogTitle}`;
        this.editFormData = {
          ...this.initEditData(),
          ...(await this.getDetailById(id)),
        };
        this.editFormData.deptList.map((item) => {
          this.categoryObj.deptName.push(item.deptId);
        });
        this.editFormData.statusAndCategorys.map((item) => {
          this.categoryObj.categoryName.push(item.categoryId);
          this.categoryObj.statusName.push(item.statusId);
        });
        this.editFormData.statusAndCategoryIds =
          this.editFormData.statusAndCategorys;
        this.editFormData.attachs = this.editFormData.attaches;
        this.initOptions(this.editFormData);
        this.dialogProps.visible = true;
      });

      this.$on("openDetailDialog", async function (id) {
        this.action = "detail";
        this.dialogProps.title = `盘点详情`;
        this.editFormData = {
          ...this.initEditData(),
          ...(await this.getFormById(id)),
        };
        this.initOptions(this.editFormData);
        this.editFormData.deptIds = this.editFormData.depts;
        this.editFormData.statusAndCategoryIds = this.editFormData.categorys;
        this.editFormData.attachs = this.editFormData.attachResps;
        console.log(this.editFormData, "详情内容======");
        this.dialogProps.visible = true;
        this.$nextTick((_) => {
          this.drawChart();
          this.drawChart2();
        });
      });

      this.$on("openAddDialog", function () {
        this.action = "add";
        this.dialogProps.title = `添加${this.dialogTitle}`;
        this.editFormData = this.initEditData();
        //this.editFormData.registryDate = new Date().format("yyyy-MM-dd");
        this.editFormData.registryDate = moment().format("YYYY-MM-DD");
        let userObj = window.currentUser;
        this.editFormData.registryPerson.id = userObj.id;
        this.editFormData.registryPerson.name = userObj.name;
        this.editFormData.attachs = [
          {
            attachTitle: "身份证", // 附件名称
            attachRequired: "是", // 是否必传
            quantity: 2, // 数量限制
          },
          {
            attachTitle: "资产图片", // 附件名称
            attachRequired: "是", // 是否必传
            quantity: 1, // 数量限制
          },
        ];
        this.initOptions(this.editFormData);
        this.dialogProps.visible = true;
      });
      this.$on("openCopyDialog", async function (id) {
        this.action = "add";
        this.dialogProps.title = `添加${this.dialogTitle}`;
        this.editFormData = {
          ...this.initEditData(),
          ...(await this.getFormById(id)),
        };
        //this.editFormData.registryDate = new Date().format("yyyy-MM-dd");
        this.editFormData.registryDate = moment().format("YYYY-MM-DD");
        this.initOptions(this.editFormData);
        this.editFormData.id = null; //把id设置为空，添加一个新的

        this.dialogProps.visible = true;
      });
    });
  },
};
</script>
<style scoped lang="scss">
.title_line {
  padding-left: 10px;
  border-left: 2px solid #018cb7;
  font-weight: bold;
}
.title_line2 {
  padding-left: 10px;
  border-left: 2px solid #fff;
  font-weight: bold;
}
.num-box {
  color: #018cb7;
}
.dc-ux-grid_QueryTable {
  margin-top: 10px;
}
.text-line {
  margin: 8px 0;
}

.left-box {
  padding: 10px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: flex-start;
  .item-box {
    flex: 1;
    margin-left: 20px;
  }
}
.right-box {
  padding: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
