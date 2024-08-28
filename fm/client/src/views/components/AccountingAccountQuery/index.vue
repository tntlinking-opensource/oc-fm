<template>
  <div>
    <div>
      <el-dialog
        title="选择科目"
        v-on:open="onDialogOpen"
        :append-to-body="appendToBody"
        :visible.sync="dialogVisible"
        :width="showTree == 'parent' ? '' : '90%'"
        :close-on-click-modal="false"
        :destroy-on-close="isDestroy"
      >
        <el-container
          direction="horizontal"
          class="dc-el-container_ElContainer"
          v-loading="loading"
        >
          <el-aside
            :width="showTree == 'parent' ? '100%' : '200px'"
            height="60px"
            v-if="showTree == 'parent' || showTree == 'all'"
          >
            <el-row class="left">
              <list-tree
                ref="listTree"
                border
                v-on:current-change="onChangeCurrentRow"
                title="科目"
                field="name"
                align="left"
                header-align="center"
                show-overflow
                :tree-config="{
                  children: 'children',
                  indent: 20,
                  accordion: false,
                  line: false,
                  showIcon: true,
                  iconOpen: '',
                  iconClose: ''
                }"
                :api="{
                  baseURL: 'BASE_API',
                  method: 'post',
                  url: 'vouchermanager/InitLedgePeriod/tglAccounts/tree',
                  params: [],
                  data: 'return {\n        params: [],\n      }',
                  reqBefore:
                    '\n\nconfig.headers[&quot;X-Token&quot;] = sessionStorage.getItem(&quot;token&quot;)\n    return config',
                  reqResponse:
                    'if (response.data.code == 100) {\n          \n\treturn response.data.data\n}',
                  reqError: 'console.error(error)'
                }"
                class="dc-list-tree_ListTree"
              ></list-tree>
            </el-row>
          </el-aside>
          <el-main v-if="showTree == 'all'">
            <div class="content">
              <div class="content-search">
                <el-row
                  gutter="0"
                  type="flex"
                  align="top"
                  class="dc-el-row_ElRow"
                >
                  <!-- <el-input :clearable='true' placeholder='请输入条件值' ></el-input> -->
                  <el-input
                    placeholder="科目编码/名称"
                    v-model="varaccountcode"
                  >
                    <el-button
                      slot="append"
                      type="primary"
                      icon="el-icon-search"
                      @click="onSearch"
                      >查询</el-button
                    >
                  </el-input>
                </el-row>
              </div>
              <el-row>
                <ux-grid
                  column-key
                  ref="table"
                  :data="tableData"
                  max-height="500"
                  border
                  class="dc-ux-grid_QueryTable"
                  @row-click="onRowClick"
                >
                  <ux-table-column
                    v-if="checkType === 'checkbox'"
                    type="checkbox"
                    width="50"
                    align="center"
                  />
                  <ux-table-column
                    v-for="(item, index) in tableColumns"
                    :key="index"
                    :field="item.field"
                    :title="item.title"
                    :width="item.width"
                    :visible="item.visible"
                    :align="item.align"
                    tree-node
                    resizable
                    min-width="180px"
                    header-align="center"
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
            </div>
          </el-main>
        </el-container>
        <span slot="footer" class="dialog-footer">
          <el-button @click="close">关 闭</el-button>
          <el-button type="primary" @click="onGetData">确 定</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import DcMain from "@/views/components/dcMain";

import { validatenull } from "@/utils/validate";

import {
  listTglAccountsPage,
  deleteTglAccounts
} from "@/api/vouchermanager/InitLedgePeriod/tglAccounts";

/**
 * @description 科目选择通用组件
 *
 * 属性
 * @prop {String} checkType - 选中类型  radio/checkbox  默认:radio
 * @prop {String} showTree - 展示类型，仅展示父级或都展示  parent/all   默认:all
 * @prop {Boolean} isLastLevel - 是否限制末级，true/false   默认:false
 * @prop {Boolean} isIntisledge - 是否限制分户核算，true/false   默认:false
 * @prop {Boolean} appendToBody - 是否插入Body，用于多层dialog中
 * @prop {Boolean} isDestroy - 是否在关闭组件时销毁
 *
 * 事件
 * @event {Function} onGetData - 获取数据，compType为dialog时通过事件获取 { tree:Obejct,tableData: Obejct / Array }
 *
 * 方法
 * @method {Function} open - 打开
 * @method {Function} onGetData - 获取数据 { tree:Obejct,tableData: Obejct / Array }
 */
export default {
  extends: DcMain,
  props: {
    checkType: {
      type: String,
      default: "radio"
    },
    showTree: {
      type: String,
      default: "all"
    },
    isLastLevel: {
      type: Boolean,
      default: false
    },
    isIntisledge: {
      type: Boolean,
      default: false
    },
    appendToBody: {
      type: Boolean,
      default: false
    },
    isDestroy: {
      Boolean,
      default: false
    }
  },
  data() {
    return {
      dialogVisible: false,
      varaccountcode: "",
      tableData: [],
      parentTable: {},
      radioData: {},
      checkBoxData: [],
      tableColumns: [
        {
          title: "科目编码",
          field: "varaccountcode",
          visible: true,
          order: 0,
          width: "110px"
        },
        {
          title: "科目名称",
          field: "varaccountname",
          visible: true,
          order: 1,
          width: "180px"
        },
        {
          title: "父级科目",
          field: "parentvaraccountname",
          visible: true,
          order: 3,
          width: "208px"
        },
        {
          title: "科目性质",
          field: "intpropertyno",
          visible: true,
          order: 4,
          width: "100px"
        },
        {
          title: "科目类别",
          field: "uqtypeidno",
          visible: true,
          order: 5,
          width: "150px"
        },
        {
          title: "现金流量",
          field: "intisflowno",
          visible: true,
          order: 6,
          width: "120px"
        },
        {
          title: "外币",
          field: "uqforeigncurridno",
          visible: true,
          order: 7,
          align: "center",
          width: "100px"
        },
        {
          title: "分户",
          field: "intisledgeText",
          visible: true,
          order: 8,
          align: "center",
          width: "85px"
        },
        {
          title: "余额方向",
          field: "isbalanceText",
          visible: true,
          order: 9,
          align: "center",
          width: "90px"
        },
        {
          title: "计量单位",
          field: "varmeasureno",
          visible: true,
          order: 10,
          align: "center",
          width: "90px"
        },
        {
          title: "启停",
          field: "intflagText",
          visible: true,
          order: 11,
          align: "center",
          width: "85px"
        },
        {
          title: "是否末级",
          field: "intislastlevelText",
          visible: true,
          order: 11,
          align: "center",
          width: "85px"
        }
      ],

      // 分页属性
      tableDataPage: 1,
      tableDataTotal: 0,
      search: {
        // 查询条件   业务表设置的筛选条件
        params: [
          {
            columnName: "",
            queryType: "(",
            value: "",
            logic: "and"
          },
          {
            columnName: "varaccountcode",
            queryType: "like",
            value: "",
            logic: "and"
          },
          {
            columnName: "varaccountname",
            queryType: "like",
            value: "",
            logic: "or"
          },
          {
            columnName: "",
            queryType: ")",
            value: "",
            logic: ""
          }
        ],
        offset: 0,
        limit: 10,
        columnName: "", // 排序字段名
        order: "" // 排序
      }
    };
  },
  methods: {
    onDialogOpen() {},
    open() {
      this.dialogVisible = true;
    },
    close() {
      this.dialogVisible = false;
    },
    getList() {
      this.setLoad();

      this.search.params = [];
      if (validatenull(this.parentTable)) {
        this.$alert("请选择左侧列表", "提示", {
          confirmButtonText: "确定",
          type: "info"
        });
        return;
      }
      this.search.params.push({
        columnName: "varaccountcode",
        queryType: "like",
        value: this.parentTable.varaccountcode
      });
      if (this.varaccountcode != "") {
        this.search.params = [
          {
            columnName: "",
            queryType: "(",
            value: "",
            logic: "and"
          },
          {
            columnName: "varaccountcode",
            queryType: "like",
            value: this.varaccountcode,
            logic: "and"
          },
          {
            columnName: "varaccountname",
            queryType: "like",
            value: this.varaccountcode,
            logic: "or"
          },
          {
            columnName: "",
            queryType: ")",
            value: "",
            logic: ""
          }
        ];
      }

      listTglAccountsPage(this.search)
        .then(responseData => {
          if (responseData.code == 100) {
            let ary = responseData.data.rows;
            this.tableDataTotal = responseData.data.total;
            ary.forEach(item => {
              item[
                "parentvaraccountname"
              ] = `[${item.uqparentid.varaccountcode}]${item.uqparentid.varaccountname}`;
              item["isbalanceText"] = item["isbalance"] == 0 ? "借" : "贷";
              item["intisledgeText"] = item["intisledge"] == 0 ? "否" : "是";
              item["intflagText"] =
                item["intflag"] == 0
                  ? "停用"
                  : item["intflag"] == 1
                  ? "新建"
                  : "启用";
              item["intislastlevelText"] =
                item["intislastlevel"] == 0 ? "否" : "是";
            });
            this.tableData = ary ? ary : [];
          } else {
            this.showMessage(responseData);
          }
          this.resetLoad();
        })
        .catch(error => {
          this.outputError(error);
        });
    },
    onChangeCurrentRow(currentRow, oldCurrentRow) {
      if (currentRow != oldCurrentRow && this.showTree == "all") {
        this.parentTable = currentRow;
        this.varaccountcode = "";
        this.radioData = {};
        this.checkBoxData = [];
        this.onSearch();
      } else {
        this.parentTable = currentRow;
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
    onSearch() {
      this.search.offset = 0;
      this.tableDataPage = 1;
      this.getList();
    },
    // 单选
    onRowClick(data) {
      this.radioData = data;
    },
    checkData() {
      // 仅为父级时，跳过校验
      if (this.showTree == "parent") {
        return true;
      }
      switch (this.checkType) {
        case "radio":
          if (JSON.stringify(this.radioData) === "{}") {
            this.showMessage({
              type: "warning",
              msg: "请选择科目"
            });
            return false;
          } else {
            return true;
          }
        case "checkbox":
          // 此处只会控制至少选择一条，而不会控制在何等情况下最多选几条，至多选择的实现应根据业务自行通过返回数据控制
          this.checkBoxData = this.$refs.table.getCheckboxRecords();
          if (this.checkBoxData.length <= 0) {
            this.showMessage({
              type: "warning",
              msg: "请选择科目"
            });
            return false;
          } else {
            return true;
          }
      }
    },
    onGetData() {
      if (this.checkData()) {
        let back = {
          tree: this.parentTable,
          tableData:
            this.checkType === "radio" ? this.radioData : this.checkBoxData
        };
        if (this.isLastLevel && back.tableData.intislastlevel == 0) {
          this.showMessage({
            type: "warning",
            msg: "请选择末级的科目"
          });
          return false;
        }
        if (this.isIntisledge && back.tableData.intisledge == 0) {
          this.showMessage({
            type: "warning",
            msg: "该科目暂未设置分户，不允许选中！"
          });
          return false;
        }
        this.$emit("onGetData", back);
        this.dialogVisible = false;
        return back;
      }
    }
  }
};
</script>

<style lang="scss" scoped>
/deep/ .plTableBox {
  .col--tree-node:not(:first-child) {
    .elx-cell--tree-node {
      padding-left: 0 !important;
      .elx-tree--btn-wrapper {
        display: none;
      }
      .elx-tree-cell {
        padding-left: 0;
      }
    }
  }
}
.left {
  height: 535px;
  overflow: auto;
}

.content {
  padding: 0 0 0 10px;
  &-search {
    width: 270px;
    padding: 0 0 10px 0;
  }
}
</style>
