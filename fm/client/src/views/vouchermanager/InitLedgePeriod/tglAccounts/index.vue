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
    <el-container direction="horizontal" class="dc-el-container_ElContainer">
      <el-aside width="200px" height="60px">
        <el-row class="h-m of-auto">
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
      <el-main>
        <el-row>
          <el-row
            gutter="0"
            type="flex"
            justify="end"
            align="top"
            class="dc-el-row_ElRow"
          >
            <el-tooltip disabled class="dc-el-tooltip_Button mr-10">
              <el-input
                placeholder="科目编码/名称"
                clearable
                v-model="queryFormData.varaccountcode"
                @keyup.enter.native="onSearch"
              >
                <el-button
                  slot="append"
                  type="primary"
                  icon="el-icon-search"
                  @click="onSearch"
                  >查询</el-button
                >
              </el-input>
            </el-tooltip>
            <el-tooltip
              v-show="permission.add"
              disabled
              class="dc-el-tooltip_Button"
            >
              <el-button
                type="success"
                icon="el-icon-circle-check"
                v-on:click="onOpre('start')"
                >启用科目</el-button
              >
            </el-tooltip>
            <el-tooltip
              v-show="permission.add"
              disabled
              class="dc-el-tooltip_Button"
            >
              <el-button
                type="danger"
                icon="el-icon-warning"
                v-on:click="onOpre('stop')"
                >停用科目</el-button
              >
            </el-tooltip>
            <el-tooltip
              v-show="permission.add"
              disabled
              class="dc-el-tooltip_Button"
            >
              <el-button
                type="success"
                icon="el-icon-download"
                v-on:click="onPull"
                >拉取科目</el-button
              >
            </el-tooltip>
          </el-row>
          <ux-grid
            column-key
            ref="table"
            max-height="500"
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
              iconClose: ''
            }"
            class="dc-ux-grid_QueryTable"
          >
            <ux-table-column
              type="checkbox"
              fixed="left"
              width="50"
              align="center"
              class="dc-ux-table-column_ElTableColumn"
            />
            <ux-table-column
              :field="tableColumns['129'].field"
              :title="tableColumns['129'].title"
              :width="tableColumns['129'].width"
              :visible="tableColumns['129'].visible"
              :params="{ sortId: '129', summary: false }"
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
              :field="tableColumns['131'].field"
              :title="tableColumns['131'].title"
              :width="tableColumns['131'].width"
              :visible="tableColumns['131'].visible"
              :params="{ sortId: '131', summary: false }"
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
              :field="tableColumns['168'].field"
              :title="tableColumns['168'].title"
              :width="tableColumns['168'].width"
              :visible="tableColumns['168'].visible"
              :params="{ sortId: '168', summary: false }"
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
              :field="tableColumns['142'].field"
              :title="tableColumns['142'].title"
              :width="tableColumns['142'].width"
              :visible="tableColumns['142'].visible"
              :params="{ sortId: '142', summary: false }"
              tree-node
              resizable
              min-width="120px"
              align="left"
              header-align="center"
              sortable
              show-overflow
              class="dc-ux-table-column_ElTableColumn"
            >
              <template slot-scope="{ row, rowIndex }">
                <div
                  v-html="
                    ((value, row, rowIndex) => {
                      return `[${row.uqparentid.varaccountcode}]${row.uqparentid.varaccountname}`;
                    })(row.uqparentid.VARACCOUNTFULLNAME, row, rowIndex)
                  "
                ></div>
              </template>
            </ux-table-column>
            <ux-table-column
              :field="tableColumns['132'].field"
              :title="tableColumns['132'].title"
              :width="tableColumns['132'].width"
              :visible="tableColumns['132'].visible"
              :params="{ sortId: '132', summary: false }"
              tree-node
              resizable
              min-width="80px"
              align="right"
              header-align="center"
              sortable
              show-overflow
              class="dc-ux-table-column_ElTableColumn"
            >
              <template slot-scope="{ row, rowIndex }">
                <div
                  v-html="
                    ((value, row, rowIndex) => {
                      switch (value) {
                        case '1':
                          return '资产';
                        case '2':
                          return '负债';
                        case '3':
                          return '权益';
                        case '4':
                          return '成本';
                        case '5':
                          return '损益';
                        default:
                          return '';
                      }
                    })(row.intproperty, row, rowIndex)
                  "
                ></div>
              </template>
            </ux-table-column>
            <ux-table-column
              :field="tableColumns['133'].field"
              :title="tableColumns['133'].title"
              :width="tableColumns['133'].width"
              :visible="tableColumns['133'].visible"
              :params="{ sortId: '133', summary: false }"
              tree-node
              resizable
              min-width="180px"
              align="left"
              header-align="center"
              sortable
              show-overflow
              class="dc-ux-table-column_ElTableColumn"
            >
              <template slot-scope="{ row, rowIndex }">
                <div
                  v-html="
                    ((value, row, rowIndex) => {
                      switch (value) {
                        case '1':
                          return '预付';
                        case '2':
                          return '预收';
                        case '3':
                          return '库存';
                        case '4':
                          return '资产';
                        case '5':
                          return '现金';
                        case '6':
                          return '银行';
                        case '7':
                          return '应收';
                        case '8':
                          return '应付';
                        case '9':
                          return '费用';
                        case '10':
                          return '收入';
                        default:
                          return '';
                      }
                    })(row.uqtypeid, row, rowIndex)
                  "
                ></div>
              </template>
            </ux-table-column>
            <ux-table-column
              :field="tableColumns['169'].field"
              :title="tableColumns['169'].title"
              :width="tableColumns['169'].width"
              :visible="tableColumns['169'].visible"
              :params="{ sortId: '169', summary: false }"
              tree-node
              resizable
              min-width="80px"
              align="right"
              header-align="center"
              sortable
              show-overflow
              class="dc-ux-table-column_ElTableColumn"
            >
              <template slot-scope="{ row, rowIndex }">
                <div
                  v-html="
                    ((value, row, rowIndex) => {
                      switch (value) {
                        case '0':
                          return '不需要';
                        case '1':
                          return '可输';
                        case '2':
                          return '必须';
                        default:
                          return '';
                      }
                    })(row.intisflow, row, rowIndex)
                  "
                ></div>
              </template>
            </ux-table-column>
            <ux-table-column
              :field="tableColumns['134'].field"
              :title="tableColumns['134'].title"
              :width="tableColumns['134'].width"
              :visible="tableColumns['134'].visible"
              :params="{ sortId: '134', summary: false }"
              tree-node
              resizable
              min-width="180px"
              align="left"
              header-align="center"
              sortable
              show-overflow
              class="dc-ux-table-column_ElTableColumn"
            >
              <template slot-scope="{ row, rowIndex }">
                <div
                  v-html="
                    ((value, row, rowIndex) => {
                      switch (value) {
                        case '1':
                          return '美元';
                        case '2':
                          return '日元';
                        case '3':
                          return '欧元';
                        default:
                          return '';
                      }
                    })(row.uqforeigncurrid, row, rowIndex)
                  "
                ></div>
              </template>
            </ux-table-column>
            <ux-table-column
              :field="tableColumns['136'].field"
              :title="tableColumns['136'].title"
              :width="tableColumns['136'].width"
              :visible="tableColumns['136'].visible"
              :params="{ sortId: '136', summary: false }"
              tree-node
              resizable
              min-width="80px"
              align="right"
              header-align="center"
              sortable
              show-overflow
              class="dc-ux-table-column_ElTableColumn"
            >
              <template slot-scope="{ row, rowIndex }">
                <div
                  v-html="
                    ((value, row, rowIndex) => {
                      switch (value) {
                        case 0:
                          return '否';
                        case 1:
                          return '是';
                        default:
                          return '';
                      }
                    })(row.intisledge, row, rowIndex)
                  "
                ></div>
              </template>
            </ux-table-column>
            <ux-table-column
              :field="tableColumns['171'].field"
              :title="tableColumns['171'].title"
              :width="tableColumns['171'].width"
              :visible="tableColumns['171'].visible"
              :params="{ sortId: '171', summary: false }"
              tree-node
              resizable
              min-width="80px"
              align="right"
              header-align="center"
              sortable
              show-overflow
              class="dc-ux-table-column_ElTableColumn"
            >
              <template slot-scope="{ row, rowIndex }">
                <div
                  v-html="
                    ((value, row, rowIndex) => {
                      switch (value) {
                        case '0':
                          return '借';
                        case '1':
                          return '贷';
                        default:
                          return '';
                      }
                    })(row.isbalance, row, rowIndex)
                  "
                ></div>
              </template>
            </ux-table-column>
            <ux-table-column
              :field="tableColumns['153'].field"
              :title="tableColumns['153'].title"
              :width="tableColumns['153'].width"
              :visible="tableColumns['153'].visible"
              :params="{ sortId: '153', summary: false }"
              tree-node
              resizable
              min-width="120px"
              align="left"
              header-align="center"
              sortable
              show-overflow
              class="dc-ux-table-column_ElTableColumn"
            >
              <template slot-scope="{ row, rowIndex }">
                <div
                  v-html="
                    ((value, row, rowIndex) => {
                      switch (value) {
                        case '1':
                          return '米';
                        case '2':
                          return '吨';
                        default:
                          return '';
                      }
                    })(row.uqparentid.VARMEASURE, row, rowIndex)
                  "
                ></div>
              </template>
            </ux-table-column>
            <ux-table-column
              :field="tableColumns['167'].field"
              :title="tableColumns['167'].title"
              :width="tableColumns['167'].width"
              :visible="tableColumns['167'].visible"
              :params="{ sortId: '167', summary: false }"
              tree-node
              resizable
              min-width="80px"
              align="right"
              header-align="center"
              sortable
              show-overflow
              class="dc-ux-table-column_ElTableColumn"
            >
              <template slot-scope="{ row, rowIndex }">
                <div
                  v-html="
                    ((value, row, rowIndex) => {
                      switch (value) {
                        case '0':
                          return '停用';
                        case '1':
                          return '新建';
                        case '2':
                          return '启用';
                        default:
                          return '';
                      }
                    })(row.intflag, row, rowIndex)
                  "
                ></div>
              </template>
            </ux-table-column>
            <ux-table-column
              title="操作"
              :params="{ sortId: '119' }"
              tree-node
              resizable
              width="160px"
              min-width="160px"
              fixed="right"
              align="left"
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
                  offset="10"
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
                  v-on:click="onEdit(scope)"
                  type="primary"
                  v-show="permission.edit"
                  content="修改"
                  placement="top"
                  icon-name="el-icon-edit"
                  class="dc-OperationIcon_IconButton"
                ></OperationIcon>
                <OperationIcon
                  v-on:click="onOpre('start', scope)"
                  type="success"
                  v-show="permission.view"
                  content="启用"
                  placement="top"
                  icon-name="el-icon-circle-check"
                  class="dc-OperationIcon_IconButton"
                ></OperationIcon>
                <OperationIcon
                  v-on:click="onOpre('stop', scope)"
                  type="danger"
                  v-show="permission.remove"
                  content="停用"
                  placement="top"
                  icon-name="el-icon-warning-outline"
                  class="dc-OperationIcon_IconButton"
                ></OperationIcon>
                <!-- <OperationIcon
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
        </el-row>
      </el-main>
    </el-container>
    <pull ref="pull" @pullEnd="getList"/>
  </el-row>
</template>
<script>
import { validatenull } from "@/utils/validate";
import {
  listTglAccountsPage,
  deleteTglAccounts,
  updateflagclose,
  updateflagstart
} from "@/api/vouchermanager/InitLedgePeriod/tglAccounts";
import pull from "./pull";

import { listResourcePermission } from "@/api/admin/common/permission";
/** 根据用户界面配置import组件 开始 */
import History from "@/views/components/history";
import EditForm from "./form";
import QueryTag from "@/views/components/queryTag";
import QueryConditionPanel from "@/views/components/queryConditionPanel";
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
    QueryTag,
    QueryConditionPanel,
    ViewColumnsSelect,
    /** 根据用户界面配置组件 结束 */
    ExportExcelButton,
    OperationIcon,
    pull
  },
  data() {
    return {
      permission: {
        view: false,
        add: false,
        edit: false,
        remove: false,
        export: false
      },
      metadata,
      /** 根据用户界面配置生成data数据 开始 */
      parentTable: {},
      queryFormData: {
        varaccountcode: "" // 科目编码/名称
      },
      tableData: [],
      // 查询表格列头
      tableColumns: {
        129: {
          title: "科目编码",
          field: "varaccountcode",
          visible: true,
          order: 0
        },
        131: {
          title: "科目名称",
          field: "varaccountname",
          visible: true,
          order: 1
        },
        168: {
          title: "科目全名称",
          field: "varaccountfullname",
          visible: true,
          order: 2
        },
        142: {
          title: "父级科目",
          field: "uqparentid.VARACCOUNTFULLNAME",
          visible: true,
          order: 3
        },
        132: {
          title: "科目性质",
          field: "intproperty",
          visible: true,
          order: 4
        },
        133: {
          title: "科目类别",
          field: "uqtypeid",
          visible: true,
          order: 5
        },
        169: {
          title: "现金流量",
          field: "intisflow",
          visible: true,
          order: 6
        },
        134: {
          title: "外币",
          field: "uqforeigncurrid",
          visible: true,
          order: 7
        },
        136: {
          title: "分户",
          field: "intisledge",
          visible: true,
          order: 8
        },
        171: {
          title: "余额方向",
          field: "isbalance",
          visible: true,
          order: 9
        },
        153: {
          title: "计量单位",
          field: "uqparentid.VARMEASURE",
          visible: true,
          order: 10
        },
        167: {
          title: "状态",
          field: "intflag",
          visible: true,
          order: 11
        },
        119: {
          title: "操作",
          width: "160px",
          isShow: true,
          visible: true,
          order: 12
        }
      },
      // 分页属性
      tableDataPage: 1,
      tableDataTotal: 0,
      // 选项变量

      /** 根据用户界面配置生成data数据 结束 */
      version: 6,
      search: {
        // 查询条件   业务表设置的筛选条件
        params: [],

        offset: 0,
        limit: 10,
        columnName: "", // 排序字段名
        order: "" // 排序
      },
      searchList: [],
      tableId: "1457254743943430348",
      schemeId: "1457254743943430346"
    };
  },
  computed: {},
  methods: {
    getList() {
      this.setLoad();
      /* 查询参数 和数据权限 */
      this.search.params = [];

      if (validatenull(this.parentTable)) {
        this.$alert("请选择左侧列表", "提示", {
          confirmButtonText: "确定",
          type: "info"
        });
        return;
      }
      this.search.params.push({
        columnName: "VARACCOUNTCODE",
        queryType: "like",
        value: this.parentTable.varaccountcode
      });
      if (!validatenull(this.queryFormData.varaccountcode)) {
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
            value: this.queryFormData.varaccountcode,
            logic: "and"
          },
          {
            columnName: "varaccountname",
            queryType: "like",
            value: this.queryFormData.varaccountcode,
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
      // 数据权限: 会计科目表tgl_accounts
      this.pushDataPermissions(
        this.search.params,
        this.$route.meta.routerId,
        this.tableId
      );
      listTglAccountsPage(this.search)
        .then(responseData => {
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
        .catch(error => {
          this.outputError(error);
        });
    },
    onSearch() {
      this.search.offset = 0;
      this.tableDataPage = 1;
      this.getList();
      // if (this.isQueryConditionPanel) {
      //   this.search.offset = 0;
      //   this.tableDataPage = 1;
      //   this.getList();
      // } else {
      //   this.$refs["queryForm"].validate(valid => {
      //     if (valid) {
      //       this.search.offset = 0;
      //       this.tableDataPage = 1;
      //       this.getList();
      //     } else {
      //       return false;
      //     }
      //   });
      // }
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
      this.initOptions(this.queryModel);
      this.setLoad();
      listResourcePermission(this.$route.meta.routerId)
        .then(responseData => {
          if (responseData.code == 100) {
            this.permission.view = responseData.data.find(item => {
              return item.permission === "tglAccounts:read";
            });
            this.permission.export = responseData.data.find(item => {
              return item.permission === "tglAccounts:export";
            });

            this.permission.add = responseData.data.find(item => {
              return item.permission === "tglAccounts:create";
            });
            this.permission.edit = responseData.data.find(item => {
              return item.permission === "tglAccounts:update";
            });
            this.permission.remove = responseData.data.find(item => {
              return item.permission === "tglAccounts:delete";
            });
          } else {
            this.showMessage(responseData);
          }
          this.resetLoad();
        })
        .catch(error => {
          this.outputError(error);
        });
    },
    // 编辑
    onEdit(scope) {
      this.$refs.editForm.$emit("openEditDialog", scope.row);
    },
    onPull(){
      this.$refs.pull.open()
    },
    // 启用/停用
    onOpre(code, scope = null) {
      let checkBoxData = [];
      let params = [];
      checkBoxData = validatenull(scope)
        ? this.$refs.table.getCheckboxRecords()
        : [scope.row];
      if (checkBoxData.length <= 0) {
        this.showMessage({
          type: "warning",
          msg: "请选择要操作的科目"
        });
        return;
      }
      try {
        checkBoxData.forEach(item => {
          if (item.intflag == 2 && code == "start") {
            throw new Error(
              `${item.varaccountfullname}不是新建或关闭状态，不能启用!`
            );
          } else if (item.intflag != 2 && code == "stop") {
            throw new Error(`${item.varaccountfullname}不处于启用状态!`);
          } else {
            params.push(item.uqaccountid);
          }
        });
        let ids = { ids: params.toString() };
        this.$confirm(
          `请确认是否${
            code == "start" ? "启用" : "关闭"
          }所选科目及其所有子级科目？`,
          "确认",
          {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }
        ).then(_ => {
          this.setLoad();
          if (code == "start") {
            updateflagstart(ids)
              .then(responseData => {
                if (responseData.code == 100) {
                  this.getList();
                  this.showMessage({
                    type: "success",
                    msg: "启用成功"
                  });
                } else {
                  this.showMessage(responseData);
                }
                this.resetLoad();
              })
              .catch(error => {
                this.outputError(error);
              });
          } else {
            updateflagclose(ids)
              .then(responseData => {
                if (responseData.code == 100) {
                  this.getList();
                  this.showMessage({
                    type: "success",
                    msg: "停用成功"
                  });
                } else {
                  this.showMessage(responseData);
                }
                this.resetLoad();
              })
              .catch(error => {
                this.outputError(error);
              });
          }
        });
      } catch (error) {
        this.showMessage({
          type: "warning",
          msg: error
        });
      }
    },
    onDelete(scope) {
      this.$confirm("确定删除吗？", "确认", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.setLoad();
          deleteTglAccounts(scope.row)
            .then(responseData => {
              if (responseData.code == 100) {
                this.getList();
                this.showMessage({
                  type: "success",
                  msg: "删除成功"
                });
              } else {
                this.showMessage(responseData);
              }
              this.resetLoad();
            })
            .catch(error => {
              this.outputError(error);
            });
        })
        .catch(() => {});
    },

    onChangeCurrentRow(currentRow, oldCurrentRow) {
      if (currentRow != oldCurrentRow) {
        this.parentTable = currentRow;
        this.initOptions(this.queryModel);
        this.onSearch();
      }
    },
    onSortChange(orderby) {
      if (validatenull(orderby.prop)) {
        this.search.columnName = "";
        this.search.order = "";
      } else {
        this.search.columnName = orderby.prop;
        this.search.order = orderby.order === "descending" ? "desc" : "asc";
      }
      this.getList();
    },

    // 初始化自定义类型选择框选项变量
    initOptions(This) {}
  },
  watch: {},
  mounted() {
    this.pageInit();

    this.columnDrop(this.$refs.table);
  }
};
</script>
