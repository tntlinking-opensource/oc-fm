<template>
  <van-row class="dm-container">
    <el-dialog
      width="90%"
      :fullscreen="false"
      :title="dialogProps.title"
      :visible.sync="dialogProps.visible"
      :close-on-click-modal="false"
      class="dc-el-dialog_ElDialog"
    >

      <ux-grid
        column-key
        ref="adjusttable"
        :data="adjustArr"
        border
        @current-change="handleCurrentChange1"
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
          align="left"
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
          align="left"
          header-align="center"
          sortable
          show-overflow
          class="dc-ux-table-column_ElTableColumn"
        >

        </ux-table-column>
        <ux-table-column
          :field="adjusttableColumns['132'].field"
          :title="adjusttableColumns['132'].title"
          :width="adjusttableColumns['132'].width"
          :visible="adjusttableColumns['132'].visible"
          :params="{ sortId: '132', summary: false }"
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
          :field="adjusttableColumns['133'].field"
          :title="adjusttableColumns['133'].title"
          :width="adjusttableColumns['133'].width"
          :visible="adjusttableColumns['133'].visible"
          :params="{ sortId: '133', summary: false }"
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
          :field="adjusttableColumns['134'].field"
          :title="adjusttableColumns['134'].title"
          :width="adjusttableColumns['134'].width"
          :visible="adjusttableColumns['134'].visible"
          :params="{ sortId: '134', summary: false }"
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
          :field="adjusttableColumns['137'].field"
          :title="adjusttableColumns['137'].title"
          :width="adjusttableColumns['137'].width"
          :visible="adjusttableColumns['137'].visible"
          :params="{ sortId: '137', summary: false }"
          tree-node
          resizable
          min-width="180px"
          align="left"
          header-align="center"
          sortable
          show-overflow
          class="dc-ux-table-column_ElTableColumn"
        >
<!--          <template slot-scope="{ row, rowIndex }">-->
<!--            <div-->
<!--              v-html="-->
<!--              ((cellValue, row, index) => {-->
<!--                switch (row.reducereason) {-->
<!--                  case '' :-->
<!--                   return rowObject.addreasonname;-->
<!--                  default:-->
<!--                      return rowObject.reducereasonname;-->
<!--                }-->
<!--              })(row.reducereason, row, rowIndex)-->
<!--            "-->
<!--            ></div>-->
<!--          </template>-->
        </ux-table-column>
        <ux-table-column
          :field="adjusttableColumns['138'].field"
          :title="adjusttableColumns['138'].title"
          :width="adjusttableColumns['138'].width"
          :visible="adjusttableColumns['138'].visible"
          :params="{ sortId: '138', summary: false }"
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
          :field="adjusttableColumns['139'].field"
          :title="adjusttableColumns['139'].title"
          :width="adjusttableColumns['139'].width"
          :visible="adjusttableColumns['139'].visible"
          :params="{ sortId: '139', summary: false }"
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
          :field="adjusttableColumns['140'].field"
          :title="adjusttableColumns['140'].title"
          :width="adjusttableColumns['140'].width"
          :visible="adjusttableColumns['140'].visible"
          :params="{ sortId: '140', summary: false }"
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
          :field="adjusttableColumns['141'].field"
          :title="adjusttableColumns['141'].title"
          :width="adjusttableColumns['141'].width"
          :visible="adjusttableColumns['141'].visible"
          :params="{ sortId: '141', summary: false }"
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
          :field="adjusttableColumns['142'].field"
          :title="adjusttableColumns['142'].title"
          :width="adjusttableColumns['142'].width"
          :visible="adjusttableColumns['142'].visible"
          :params="{ sortId: '142', summary: false }"
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
          :field="adjusttableColumns['143'].field"
          :title="adjusttableColumns['143'].title"
          :width="adjusttableColumns['143'].width"
          :visible="adjusttableColumns['143'].visible"
          :params="{ sortId: '143', summary: false }"
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
          :field="adjusttableColumns['144'].field"
          :title="adjusttableColumns['144'].title"
          :width="adjusttableColumns['144'].width"
          :visible="adjusttableColumns['144'].visible"
          :params="{ sortId: '144', summary: false }"
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
          :field="adjusttableColumns['145'].field"
          :title="adjusttableColumns['145'].title"
          :width="adjusttableColumns['145'].width"
          :visible="adjusttableColumns['145'].visible"
          :params="{ sortId: '145', summary: false }"
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
          :field="adjusttableColumns['146'].field"
          :title="adjusttableColumns['146'].title"
          :width="adjusttableColumns['146'].width"
          :visible="adjusttableColumns['146'].visible"
          :params="{ sortId: '146', summary: false }"
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
          :field="adjusttableColumns['147'].field"
          :title="adjusttableColumns['147'].title"
          :width="adjusttableColumns['147'].width"
          :visible="adjusttableColumns['147'].visible"
          :params="{ sortId: '147', summary: false }"
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
          :field="adjusttableColumns['150'].field"
          :title="adjusttableColumns['150'].title"
          :width="adjusttableColumns['150'].width"
          :visible="adjusttableColumns['150'].visible"
          :params="{ sortId: '150', summary: false }"
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
          :field="adjusttableColumns['151'].field"
          :title="adjusttableColumns['151'].title"
          :width="adjusttableColumns['151'].width"
          :visible="adjusttableColumns['151'].visible"
          :params="{ sortId: '151', summary: false }"
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
  </van-row>
</template>
<script>
import { validatenull } from '@/utils/validate'
import { listTfamAlterPage } from "@/api/fixasset/alter/tfamAlter.js";
import { projinfo } from "@/api/fixasset/asset/tfamDepsub";
/** 根据用户界面配置import组件 结束 */
import { listTfamAssetEntryPage, deleteTfamAssetEntry,linkvoucher1,cancelvoucher1 } from '@/api/cardasset/credit/tfamAssetEntry'
import { listTfamCategoryOldAll } from '@/api/cardasset/supplementreduce/tfamCategoryOld.js'
import { listSysUserOldAll } from '@/api/cardasset/supplementreduce/sysUserOld.js'
import { listTfamUsingstatusOldAll } from '@/api/cardasset/supplementreduce/tfamUsingstatusOld.js'
import { listTfamDepmethodOldAll } from '@/api/cardasset/supplementreduce/tfamDepmethodOld.js'
/** 根据用户界面配置import组件 结束 */
import { getTfamAssetOldById, saveTfamAssetOld } from '@/api/cardasset/supplementreduce/tfamAssetOld'
import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'
import EditForm from "../credit/form.vue";
import addCertificateDialog from "@/views/components/certificate/addCertificateDialog";
import {
  getTglVoucherMainsById
} from "@/api/vouchermanager/tglVoucherMains";
export default {
  extends: BaseUI,
  name: 'tfamAssetOld-form',
  components: {
    EditForm,
    /** 根据用户界面配置组件 开始 */

    /** 根据用户界面配置组件 结束 */

    OperationIcon,
    addCertificateDialog
  },
  data() {
    return {
      dialogProps: {
        visible: false,
        title: "-卡片",
      },
      certShow: false,
      dialogAdjustVisible: false,
      checkdata: {},
      adjustArr: [],
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
          title: "建卡日期",
          field: "businessdate",
          visible: true,
          order: 2,
        },
        130: {
          title: "卡片编号",
          field: "cardcode",
          visible: true,
          order: 3,
        },
        131: {
          title: "责任人",
          field: "userid",
          visible: true,
          order: 4,
        },
        132: {
          title: "资产编号",
          field: "assetcode",
          visible: true,
          order: 5,
        },
        133: {
          title: "资产名称",
          field: "assetname",
          visible: true,
          order: 6,
        },
        134: {
          title: "资产类别",
          field: "categoryname",
          visible: false,
          order: 7,
        },
        137: {
          title: "变动方式",
          field: "changeway",
          visible: true,
          order: 8,
        },
        138: {
          title: "使用状态",
          field: "usingstatusname",
          visible: true,
          order: 9,
        },
        139: {
          title: "建卡人",
          field: "creatorname",
          visible: true,
          order: 10,
        },
        140: {
          title: "原值",
          field: "value",
          visible: true,
          order: 11,
        },
        141: {
          title: "税金",
          field: "tax",
          visible: true,
          order: 12,
        },
        142: {
          title: "累计折旧",
          field: "accudep",
          visible: true,
          order: 13,
        },
        143: {
          title: "减值准备",
          field: "predevaluate",
          visible: true,
          order: 14,
        },
        144: {
          title: "数量",
          field: "num",
          visible: true,
          order: 15,
        },
        145: {
          title: "折旧方法",
          field: "depmethodname",
          visible: true,
          order: 16,
        },
        146: {
          title: "预计使用月数",
          field: "naturemonth",
          visible: true,
          order:17,
        },
        147: {
          title: "剩余使用月数",
          field: "servicemonth",
          visible: true,
          order: 18,
        },
        150: {
          title: "卡片性质",
          field: "naturename",
          visible: true,
          order: 19,
        },
        151: {
          title: "关联凭证号",
          field: "intvouchernum",
          visible: true,
          order: 20,
        },
      },
      currentRow: '',
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
    }
  },
  props: {

  },
  computed: {},
  methods: {
    onView() {
      this.certShow = true;
      setTimeout(_ => {
        getTglVoucherMainsById(this.currentRow.voucherid).then(res => {
          this.$refs.addCertificateDialog.onView(res.data);
        });
      }, 100);
    },
    onAdjustSizeChange(val) {
      this.adjustDataPage = 1;
      this.adjustsearch.limit = val;
      this.adjustsearch.offset = (this.adjustDataPage - 1) * val;
      this.getLists()
    },
    onAdjustCurrentChange(val) {
      this.adjustsearch.offset = (val - 1) * this.adjustsearch.limit;
      this.adjustDataPage = val;
      this.getLists()
    },
    handleCurrentChange1(val) {
      this.currentRow = val;
      debugger
    },
    onWidthChange(val){
      console.log(val)
      debugger
    },







    getLists(){
      projinfo(this.adjustsearch).then((responseData) => {
        if (responseData.code == 100) {
          this.adjustDataTotal = responseData.data.total;
          this.adjustArr = responseData.data.rows ? responseData.data.rows : [];
          console.log("调整记录", responseData);
          this.resetLoad();
        } else {
          this.showMessage(responseData);
        }
      });
    },
    initOptions(This) {

    },
    onDeleteChild(tableData, index) {
      // 根据下标删除子表数据
      tableData.splice(index, 1)
    },
  },
  watch: {

  },
  mounted: function () {
    this.$nextTick(() => {
      this.$on('openViewDialog', async function (currentRow) {
        this.action = 'view'
        this.dialogProps.title = `查看卡片`

        this.initOptions(this.editFormData)
        this.dialogProps.visible = true
        this.adjustsearch.params = []
        this.adjustsearch.params.push({
          columnName: 'type',

          queryType: 'like',

          value: currentRow.pro_type,
        })
        this.adjustsearch.params.push({
          columnName: 'year',

          queryType: 'like',

          value: currentRow.year,
        })
        this.adjustsearch.params.push({
          columnName: 'month',

          queryType: 'like',

          value: currentRow.month,
        })
        this.getLists()
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
