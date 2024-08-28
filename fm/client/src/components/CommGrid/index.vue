<template>
  <div>
    <vxe-grid
      v-on="$listeners"
      ref="vxeTable"
      v-bind="gridOptions"
      footerRowClassName="footer-class"
      :footerCellClassName="footerCellClassName"
      @cell-click="ellClick"
      @footer-cell-click="footerCellClick"
    >
      <template #top v-if="!!descData">
        <el-alert
          v-for="(item, index) in descData"
          type="warning"
          show-icon
          :key="index"
          :title="item.title"
          :description="item.desc"
          :closable="false"
        >
        </el-alert>
      </template>
      <template v-if="isShowBtn" #toolbar_buttons>
        <div class="mr5" v-show="isShowRadio">
          <el-radio-group v-model="radioData" @change="radioChange">
            <el-radio-button
              v-for="(item, index) in radioDataAry"
              :label="item"
              :key="index"
            ></el-radio-button>
          </el-radio-group>
        </div>

        <div v-if="customBtn.length <= 0">
          <vxe-button
            v-if="loadsh.indexOf(btnAry, COMM_CONST.VIEW) > -1"
            @click="btnOperate(COMM_CONST.VIEW)"
            :status="COMM_CONST.PRIMARY"
            :icon="COMM_CONST.SEARCH_ICON"
            size="mini"
            >查询</vxe-button
          >
          <vxe-button
            v-if="loadsh.indexOf(btnAry, COMM_CONST.ADD) > -1"
            @click="btnOperate(COMM_CONST.ADD)"
            :status="COMM_CONST.SUCCESS"
            :icon="COMM_CONST.ADD_ICON"
            size="mini"
            >新增</vxe-button
          >
          <vxe-button
            v-if="loadsh.indexOf(btnAry, COMM_CONST.EDIT) > -1"
            @click="btnOperate(COMM_CONST.EDIT)"
            :status="COMM_CONST.WARNING"
            :icon="COMM_CONST.EDIT_ICON"
            size="mini"
            >修改</vxe-button
          >
          <vxe-button
            v-if="loadsh.indexOf(btnAry, COMM_CONST.DELETE) > -1"
            @click="btnOperate(COMM_CONST.DELETE)"
            :status="COMM_CONST.DANGER"
            :icon="COMM_CONST.DELETE_ICON"
            size="mini"
            >删除</vxe-button
          >
          <vxe-button
            v-if="loadsh.indexOf(btnAry, COMM_CONST.REST) > -1"
            @click="btnOperate(COMM_CONST.REST)"
            :icon="COMM_CONST.REPEAT_ICON"
            size="mini"
            >重置</vxe-button
          >
          <vxe-button
            v-if="loadsh.indexOf(btnAry, COMM_CONST.EXPORT) > -1"
            @click="btnOperate(COMM_CONST.EXPORT)"
            :icon="COMM_CONST.DOWNLOAD_ICON"
            size="mini"
            >导出</vxe-button
          >
        </div>
        <div v-else>
          <vxe-button
            v-for="(item, index) in customBtn"
            v-show="item.isShow"
            :key="index"
            :status="item.status"
            :icon="item.icon"
            size="mini"
            :loading="item.loading"
            @click="btnOperate(item.code)"
            >{{ item.name }}</vxe-button
          >
        </div>
        <div class="table-title" v-if="tableTile != ''">
          <div>{{ tableTile }}</div>
        </div>
        <div v-if="isShowTableLink">
          <div v-if="typeof tableLink == 'string'">
            <el-link type="primary" @click="tableLinkEvent">{{
              tableLink
            }}</el-link>
          </div>
          <div v-else>
            <div
              class="dis-in-block"
              :class="index == 0 ? '' : 'ml5'"
              v-for="(item, index) in tableLink"
              :key="index"
            >
              <el-link type="primary" @click="tableLinkEvent(index)">{{
                item
              }}</el-link>
            </div>
          </div>
        </div>
      </template>
      <template #pager v-if="isShowPage">
        <vxe-pager
          :layouts="[
            'Sizes',
            'PrevJump',
            'PrevPage',
            'Number',
            'NextPage',
            'NextJump',
            'FullJump',
            'Total'
          ]"
          :current-page.sync="tablePage.currentPage"
          :page-size.sync="tablePage.pageSize"
          :total="tablePage.total"
          @page-change="handlePageChange"
        >
        </vxe-pager>
      </template>
      <template #link="{ row, rowIndex, $rowIndex, column }">
        <el-link type="primary" @click="linkClick(row, rowIndex, $rowIndex)">{{
          row[column.property]
        }}</el-link>
      </template>
      <template #operate="{ row }">
        <div
          class="dis-in-block"
          v-for="(item, index) in inlineBtn"
          :key="index"
        >
          <vxe-button
            type="text"
            v-if="!item.rule ? true : inlineChange(row, item.rule)"
            :title="item.name"
            :content="item.name"
            :status="item.status"
            @click="inLineOperate(row, item.code)"
          ></vxe-button>
        </div>
      </template>
      <template v-if="isShowBar" #toolbar_bar>
        <vxe-button
          v-for="(item, index) in customBar"
          v-hasPermi="item.hasPermi"
          type="text"
          :key="index"
          :status="item.status"
          :icon="item.icon"
          @click="barOperate(item.code)"
          >{{ item.name }}</vxe-button
        >
      </template>
      <template v-for="(itemSlot, index) in slotEdit" #[itemSlot]="{ row }">
        <vxe-input
          autofocus
          :key="index"
          v-model="row[itemSlot]"
          @change="slotChange(row, itemSlot, index)"
        ></vxe-input>
      </template>
      <template
        v-for="(itemLink, index) in linkAry"
        #[itemLink]="{ row, rowIndex, $rowIndex, column }"
      >
        <div :key="index">
          <el-link
            type="primary"
            @click="linkClick(row, rowIndex, $rowIndex, column)"
            >{{ row[column.property] }}</el-link
          >
        </div>
      </template>
    </vxe-grid>
  </div>
</template>

<script>
import { COMM_CONST, GET_OPERATOR, GET_LOGICAL } from "@/utils/const";

/**
 * @description 通用数据表格
 *
 * 属性
 * @prop {String} slotType - 插槽类型
 * @prop {String} checkboxTrigger - 多选的触发方式 row/cell 默认row
 * @prop {Boolean} isShowBtn - 是否展示按钮
 * @prop {Boolean} isShowBar - 是否启用右侧工具栏
 * @prop {Boolean} isShowPage - 是否展示分页
 * @prop {Boolean} isShowTableLink - 是否展示链接
 * @prop {Boolean} isExport - 是否前端导出
 * @prop {Boolean} isShowRadio - 是否展示工具栏单选按钮
 * @prop {Boolean} isShowInDate - 是否展示工具栏时间条件
 * @prop {Array} btnAry - 按钮数组
 * @prop {Array} customBtn - 自定义按钮组
 * @prop {Array} customBar - 自定义工具按钮组与 customBtn 保持一致
 * @prop {Array} inlineBtn - 行内操作按钮组
 * @prop {Array} slotEdit - 动态创建编辑输入框
 * @prop {Array} formula - 配合编辑输入框的公式计算,仅支持简单的四则运算，计算使用：:formula="['surplusAmount=surplusAmount-pickpairsAmount']" 比较字段使用：:formula="['@pickpairs_amount>invoiced_amount']"
 * @prop {Array} descData - 提示行
 * @prop {Array} footerData - 表尾数据 二维数组 [[data]]
 * @prop {Array} footLink - 表尾可以点击 二维数组表尾数据的下标，一般情况下只有一行，外数组中的item对应每行表尾数据，例：一行表尾数据需要点击：[[0,1]]，两个或多行数据需要点击:[[0,1],[1...1],[...]] 参考：src\views\kpiindex\2022AssIndi\1FullCaliberRate\index.vue
 * @prop {Array} mergeCellsAry - 表格合并 [{row:0,col:0,rowspan:1,colspan:1}]
 * @prop {Array} linkAry - 多链接点击下载
 * @prop {Array} radioDataAry - 单选数据数组
 * @prop {Number} maxHeight - 表格最大高度 默认600
 * @prop {Array,String} tableLink - 表头链接内容
 * @prop {Number} heightRlex - 表高偏差值
 * @prop {Boolean} autoHeight - 自动高度 默认不做适应
 * @prop {Boolean} rowHeight - 每一行行高，只对showOverflow:true 有效
 * @prop {Date} defaultTime - 默认时间 使用参考
 * @prop {Number} pageNumSize - 页码设置 10、15、20、50、100 默认20
 *
 * 事件
 * @event {Function} btnOperate - 工具栏左侧按钮组 return code
 * @event {Function} barOperate - 工具栏右侧按钮组 return code
 * @event {Function} pageChange - 分页监听 return {total: 0, currentPage: 1,pageSize: 20}
 * @event {Function} inLineOperate - 行内按钮组 return  { row: row, code: code }
 * @event {Function} linkClick - 行内链接点击事件 return {row, rowIndex, $rowIndex}
 * @event {Function} tableLink - 表头链接点击事件 return index
 * @event {Function} radioChange - 单选变化事件 return label
 *
 * 方法
 * @method {Function} getData - 获取数据
 * @method {Function} setPage - 设置分页数据，多用于重置分页
 * @method {Function} setColumns - 设置表头
 * @method {Function} setTotal - 设置数据总数，用于自动计算页数
 * @method {Function} setData - 设置数据
 * @method {Function} clearData - 清空数据
 * @method {Function} loading - 设置加载状态
 * @method {Function} setAllCheckboxRow - 设置多选全选
 * @method {Function} removeCheckboxRow - 删除复选框中的值
 * @method {Function} removeCurrentRow - 删除当前行
 * @method {Function} remove - 删除指定行数据 (rows/Array)
 * @method {Function} getRemoveRecords - 获取一删除的数据
 * @method {Function} hideBtn - 隐藏工具栏
 * @method {Function} setMergeCells - 设置临时合并，数据表中的合并 入参参考：prop：mergeCellsAry
 * @method {Function} setMergeFooterItems - 设置表尾临时合并 入参参考：prop：mergeCellsAry
 * @method {Function} vxeMethods - 使用vxe-table原生方法，调用：this.$refs.commGrid.vxeMethods('getRadioRecord')， 使用参考：src\views\channelEfficiency\effImprovement\mlEEP\index.vue
 *
 *
 */

export default {
  props: {
    heightRlex: {
      type: Number,
      default() {
        return 0;
      }
    },
    autoHeight: {
      type: Boolean,
      default() {
        return true;
      }
    },
    // 默认操作按钮
    btnAry: {
      type: Array,
      default() {
        return [
          COMM_CONST.ADD,
          COMM_CONST.EDIT,
          COMM_CONST.DELETE,
          COMM_CONST.VIEW,
          COMM_CONST.EXPORT
        ];
      }
    },
    /**
     * 自定义按钮组
     * code:'view',
     * name:'查询按钮',
     * status:'primary/success/warning/danger',
     * icon:'vxe-icon--search' 更多在：https://vxetable.cn/v3/#/table/module/icon
     * hasPermi:['none'] 按钮权限此属性必填，若无指定权限则使用['none'] 示例参考：@/components/WorkFlowUpload/index.vue -> customBtn
     */
    customBtn: {
      type: Array,
      default() {
        return [];
      }
    },
    /**
     * 与自定义按钮组保持一致
     */
    customBar: {
      type: Array,
      default() {
        return [];
      }
    },
    /**
     * 行内操作按钮组
     * code:'code' 按钮自定义键值
     * name:'' 按钮名称
     * rule:'' 操作按钮显示规则 keyword =、>、<、! keyvalu
     * status:'primary/success/warning/danger',
     */
    inlineBtn: {
      type: Array,
      default() {
        return undefined;
      }
    },
    // 是否使用操作按钮
    isShowBtn: {
      type: Boolean,
      default: true
    },
    // 是否启用右侧工具栏
    isShowBar: {
      type: Boolean,
      default: true
    },
    // 是否使用分页
    isShowPage: {
      type: Boolean,
      default: true
    },
    // 动态创建编辑输入框
    slotEditTest: {
      type: Array,
      default() {
        return [];
      }
    },
    // 动态创建编辑输入框
    slotEdit: {
      type: Array,
      default() {
        return [];
      }
    },
    // 多链接插槽
    linkAry: {
      type: Array,
      default() {
        return [];
      }
    },
    formula: {
      type: Array,
      default() {
        return [];
      }
    },
    /**
     * 提示行
     * title:'标题/内容'
     * desc:'更多描述'
     */
    descData: {
      type: Array,
      default() {
        return undefined;
      }
    },
    // 表尾数据 二维数组 [[data]]
    footerData: {
      type: Array,
      default() {
        return [];
      }
    },
    // 表尾可以点击 二维数组表尾数据的下标，一般情况下只有一行，外数组中的item对应每行表尾数据，例：一行表尾数据需要点击：[[0,1]]，两个或多行数据需要点击:[[0,1],[1...1],[...]]
    footLink: {
      type: Array,
      default() {
        return [];
      }
    },
    // 表格最高高度
    height: {
      type: [Number, String],
      default() {
        return 400;
      }
    },
    // 每一行行高，只对showOverflow:true 有效
    rowHeight: {
      type: Number,
      default() {
        return 20;
      }
    },
    // 表格最高高度
    maxHeight: {
      type: [Number, String],
      default() {
        return 600;
      }
    },
    // 表格合并
    mergeCellsAry: {
      type: Array,
      default() {
        return [];
      }
    },
    mergeFooterItems: {
      type: Array,
      default() {
        return [];
      }
    },

    // 是否展示表头
    showHeader: {
      type: Boolean,
      default() {
        return true;
      }
    },
    isShowFooter: {
      type: Boolean,
      default() {
        return true;
      }
    },
    // 是否前端导出
    isExport: {
      type: Boolean,
      default() {
        return false;
      }
    },
    tableTile: {
      type: String,
      default() {
        return "";
      }
    },
    isShowTableLink: {
      type: Boolean,
      default() {
        return false;
      }
    },
    isShowInDate: {
      type: Boolean,
      default() {
        return false;
      }
    },
    isShowRadio: {
      type: Boolean,
      default() {
        return false;
      }
    },
    defaultRadio: {
      type: String,
      default() {
        return "";
      }
    },
    radioDataAry: {
      type: Array,
      default() {
        return [];
      }
    },
    tableLink: {
      type: [Array, String],
      default() {
        return "";
      }
    },
    defaultTime: {
      type: Date,
      default() {
        return COMM_CONST.YESTERDATE;
      }
    },
    pageNumSize: {
      type: Number,
      default() {
        return 20;
      }
    },
    checkboxTrigger: {
      type: String,
      default: "row"
    }
  },
  data() {
    return {
      defaultValue: this.defaultTime,
      radioData: this.defaultRadio,
      COMM_CONST,
      testAry: [],
      formData: {},
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now() || time.getFullYear() < 2022;
        }
      },
      // vxe-table 属性配置
      gridOptions: {
        loading: false,
        autoResize: true,
        border: true,
        resizable: true,
        showOverflow: true,
        showFooter: this.isShowFooter,
        showHeader: this.showHeader,
        footerMethod: this.footerMethod,
        mergeCells: this.mergeCellsAry,
        mergeFooterItems: this.mergeFooterItems,
        loading: false,
        align: "center",
        height: this.autoHeight
          ? this.$store.getters.sysInnerHeight - this.heightRlex
          : this.height,
        maxHeight: this.autoHeight
          ? this.$store.getters.sysInnerHeight - this.heightRlex
          : this.maxHeight,
        scrollY: {
          enabled: false,
          rHeight: 30
        },
        toolbarConfig: {
          slots: {
            buttons: "toolbar_buttons",
            tools: "toolbar_bar"
          },
          export: this.isExport
        },

        columns: [],
        data: [],
        exportConfig: { remote: false, mode: "all", type: "xlsx" },
        radioConfig: {
          trigger: "row",
          highlight: true,
          strict: false
        },
        checkboxConfig: {
          trigger: this.checkboxTrigger,
          highlight: false,
          strict: false
        },
        editConfig: {
          // 设置触发编辑为手动模式
          trigger: "click",
          // 设置为整行编辑模式
          mode: "row",
          // 显示修改状态和新增状态
          showStatus: true
        },
        rowConfig: {
          height: this.rowHeight,
          isCurrent: false
        }
      },
      tablePage: {
        pageSize: this.pageNumSize,
        total: 0,
        currentPage: 1
      },
      constValue: new Map()
    };
  },
  mounted() {
    this.defaultValue = this.defaultTime;
    if (!this.isShowBtn) {
      this.hideBtn();
    }
  },
  methods: {
    // 表尾点击样式
    footerCellClassName(data) {
      if (this.footLink.length > 0) {
        for (let row = 0; row < this.footLink.length; row++) {
          if (data.$rowIndex == row) {
            for (let col = 0; col < this.footLink[row].length; col++) {
              if (data.$columnIndex == this.footLink[row][col]) {
                return "foot-link";
              }
            }
          }
        }
      }
    },
    // 单元格单击
    ellClick(
      row,
      rowIndex,
      $rowIndex,
      column,
      columnIndex,
      $columnIndex,
      triggerRadio,
      triggerCheckbox,
      triggerTreeNode,
      triggerExpandNode,
      $event
    ) {
      this.$emit("ellClick", {
        currentRow: row,
        rowIndex,
        $rowIndex,
        column,
        columnIndex,
        $columnIndex,
        triggerRadio,
        triggerCheckbox,
        triggerTreeNode,
        triggerExpandNode,
        $event
      });
    },
    /**
     * 表尾点击，只有正在footLink不为空的情况下可以被点击
     * { items, $rowIndex, column, columnIndex, $columnIndex, $event }
     */
    footerCellClick(data) {
      if (this.footLink.length > 0) {
        for (let row = 0; row < this.footLink.length; row++) {
          if (data.$rowIndex == row) {
            for (let col = 0; col < this.footLink[row].length; col++) {
              if (data.$columnIndex == this.footLink[row][col]) {
                this.$emit("footerCellClick", data);
              }
            }
          }
        }
      }
    },
    /**
     * 使用vxe-table原生方法
     * @param {*} method 方法名称
     * @param {*} params 入参，有需要的话
     * 使用示例:src\views\channelEfficiency\effImprovement\mlEEP\index.vue,行：645
     */
    vxeMethods(method, params) {
      return this.$refs.vxeTable[method](params);
    },
    /**
     * @description 设置表尾
     */
    footerMethod() {
      return this.footerData;
    },
    mergeCells(ary) {
      return [
        { row: 1, col: 1, rowspan: 3, colspan: 3 },
        { row: 5, col: 0, rowspan: 2, colspan: 2 }
      ];
    },
    // 计算插槽变化
    slotChange(row, item, index) {
      if (this.formula.length > 0) {
        for (let i = 0; i < this.formula.length; i++) {
          let data = this.formula[i];
          let msg = "";
          let bol = true;
          if (data.indexOf("@") > -1) {
            let rule = data.split("@")[1];
            let logical = GET_LOGICAL(rule);
            let logicalVal = rule.split(logical);
            if (logical == ">") {
              if (Number(row[logicalVal[0]]) > Number(row[logicalVal[1]])) {
                msg = "不得大于";
                bol = false;
              }
            } else if (logical == "<") {
              if (Number(row[logicalVal[0]]) < Number(row[logicalVal[1]])) {
                msg = "不得小于";
                bol = false;
              }
            }
            if (!bol) {
              this.$message({
                message:
                  this.loadsh.find(this.gridOptions.columns, {
                    field: logicalVal[0]
                  }).title +
                  msg +
                  this.loadsh.find(this.gridOptions.columns, {
                    field: logicalVal[1]
                  }).title,
                type: "warning"
              });
              row[item] = 0;
              return;
            }
          }
          if (data.indexOf("=") > -1) {
            let ary = String(data).split("=");
            let caName = ary[0];
            let caValue = ary[1];
            // 获取运算符
            let operator = GET_OPERATOR(caValue);

            // 通过运算符分割 需要计算的两数
            let operatorValue = caValue.split(operator);
            let has =
              operatorValue[1].indexOf("@") > -1
                ? operatorValue[1].split("@")[0]
                : operatorValue[1];

            if (!this.constValue.get(row["_X_ROW_KEY"])) {
              this.constValue.set(
                row["_X_ROW_KEY"],
                this.loadsh.cloneDeep(row[operatorValue[0]])
              );
            }
            if (operator == "+") {
              row[caName] = (
                Number(this.constValue.get(row["_X_ROW_KEY"])) +
                Number(row[has])
              ).toFixed(2);
            } else if (operator == "-") {
              row[caName] = (
                Number(this.constValue.get(row["_X_ROW_KEY"])) -
                Number(row[has])
              ).toFixed(2);
            } else if (operator == "*") {
              row[caName] = (
                Number(this.constValue.get(row["_X_ROW_KEY"])) *
                Number(row[has])
              ).toFixed(2);
            } else {
              row[caName] = (
                Number(this.constValue.get(row["_X_ROW_KEY"])) /
                Number(row[has])
              ).toFixed(2);
            }
          }
        }
      }
    },
    isShowHeader(bol = true) {
      this.gridOptions.showHeader = bol;
    },
    // 设置表头
    setColumns(columns) {
      this.gridOptions.columns = columns;
      setTimeout(_ => {
        this.$refs.vxeTable.recalculate();
      }, 200);
    },
    // 设置表格数据
    setData(data) {
      this.gridOptions.data = data;
    },
    /**
     * 设置表尾数据，适用于后端返回合计行
     * @param {Array} footerKeys 需要按顺序展示的key
     * @param {Object} footerData 后端传入的表尾数据
     *
     * 示例代码如下：行 377
     * src\views\channelEfficiency\effImprovement\mlEEP\index.vue
     */
    setFooterData(footerKeys, footerData) {
      let ft = [[]];
      for (let i = 0; i < footerKeys.length; i++) {
        ft[0].push(footerData[footerKeys[i]]);
      }
      this.gridOptions.footerMethod = () => {
        return ft;
      };
    },
    // 清空数据
    clearData() {
      this.gridOptions.data = [];
    },
    // 设置总数据数
    setTotal(total) {
      this.tablePage.total = total;
    },
    // 设置分页
    setPage(page) {
      this.tablePage = page;
    },
    // 这是多选全选
    setAllCheckboxRow(checked) {
      // console.log(checked);
      setTimeout(_ => {
        this.$refs.vxeTable.setAllCheckboxRow(checked);
      }, 300);
    },
    // 删除复选框选中的值
    removeCheckboxRow() {
      this.$refs.vxeTable.removeCheckboxRow();
    },
    // 删除当前行
    removeCurrentRow() {
      this.$refs.vxeTable.removeCurrentRow();
    },
    // 合并指定行
    setMergeCells(merges) {
      setTimeout(_ => {
        this.$refs.vxeTable.setMergeCells(merges);
      }, 50);
    },
    // 临时合并表尾
    setMergeFooterItems(merges) {
      this.$nextTick(_ => {
        this.$refs.vxeTable.setMergeFooterItems(merges);
      });
    },
    // 删除当前行
    remove(rows) {
      this.$refs.vxeTable.remove(rows);
    },
    // 获取已删除的数据
    getRemoveRecords() {
      return this.$refs.vxeTable.getRemoveRecords();
    },
    // 加载状态z
    loading(bol) {
      this.gridOptions.loading = bol;
    },
    // 分页
    handlePageChange({ currentPage, pageSize }) {
      this.$emit("pageChange", {
        offset: (currentPage - 1) * pageSize,
        limit: pageSize
      });
    },
    // 获取选中数据
    getData() {
      let code = this.gridOptions.columns[0].type || "";
      if (code == "checkbox") {
        return this.$refs.vxeTable.getCheckboxRecords(true);
      } else if (code == "radio") {
        return this.$refs.vxeTable.getRadioRecord(false);
      }
    },
    btnOperate(btn) {
      this.$emit("btnOperate", btn);
    },
    barOperate(bar) {
      this.$emit("barOperate", bar);
    },
    // 行内按钮操作
    inLineOperate(row, code) {
      this.$emit("inLineOperate", { row: row, code: code });
    },
    // 行内操作按钮显示规则
    inlineChange(row, rule) {
      let logStr = GET_LOGICAL(rule);
      let ary = rule.split(logStr);
      switch (logStr) {
        case "=":
          return row[ary[0]] == ary[1];
      }
    },
    linkClick(row, rowIndex, $rowIndex, column) {
      this.$emit("linkClick", row, rowIndex, $rowIndex, column);
    },
    tableLinkEvent(index) {
      this.$emit("tableLinkEvent", index || 0);
    },
    hideBtn() {
      delete this.gridOptions.toolbarConfig;
    },
    dateChange(date) {
      this.$emit("dateChange", date);
    },
    radioChange(label) {
      this.$emit("radioChange", label);
    },
    hasData() {
      let bol = this.gridOptions.data.length > 0;
      if (!bol) {
        this.$message({
          message: "数据为空",
          type: "warning"
        });
      }
      return bol;
    }
  }
};

export function getFooterKeys(tabelCol) {
  console.log(test);
}
</script>
<style scoped>
.table-title {
  text-align: center;
  flex: 1;
  font-size: 18px;
  font-weight: 600;
  color: #4b4b4b;
}

.footer-class {
  background-color: #f8f8f9;
  color: #77797c;
  font-weight: 700;
}
</style>
