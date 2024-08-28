<template>
  <van-row class="dc-container">
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


 <ux-grid
      column-key
      ref="table"
      :data="search_data"
      border
      v-onsort-change="onSortChange"
      v-on:header-dragend="onWidthChange($refs.table)"
      :tree-config="{ children: '', indent: 20, accordion: false, line: false, showIcon: true, iconOpen: '', iconClose: '' }"
      class="dc-ux-grid_QueryTable"
      >


      <ux-table-column
        :field="tableColumns['130'].field"
        :title="tableColumns['130'].title"
        :width="tableColumns['130'].width"
        :visible="tableColumns['130'].visible"
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
<!--      <ux-table-column-->
<!--        :field="tableColumns['132'].field"-->
<!--        :title="tableColumns['132'].title"-->
<!--        :width="tableColumns['132'].width"-->
<!--        :visible="tableColumns['132'].visible"-->
<!--        :params="{ sortId: '132', summary: false }"-->
<!--        tree-node-->
<!--        resizable-->
<!--        min-width="180px"-->
<!--        align="left"-->
<!--        header-align="center"-->
<!--        sortable-->
<!--        show-overflow-->
<!--        class="dc-ux-table-column_ElTableColumn"-->
<!--      ></ux-table-column>-->
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
      ></ux-table-column>
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
      ></ux-table-column>

      <ux-table-column
        :field="tableColumns['135'].field"
        :title="tableColumns['135'].title"
        :width="tableColumns['135'].width"
        :visible="tableColumns['135'].visible"
        :params="{ sortId: '135', summary: false }"
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
        :field="tableColumns['136'].field"
        :title="tableColumns['136'].title"
        :width="tableColumns['136'].width"
        :visible="tableColumns['136'].visible"
        :params="{ sortId: '136', summary: false }"
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
     :field="tableColumns['137'].field"
     :title="tableColumns['137'].title"
     :width="tableColumns['137'].width"
     :visible="tableColumns['137'].visible"
     :params="{ sortId: '137', summary: false }"
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
      :total="tableDataTotal"
      :page-size="search.limit"
      background
      :current-page="tableDataPage"
      :page-sizes="[5, 10, 20, 30, 40, 50, 100]"
      layout="total, sizes, prev, pager, next, jumper"
      v-on:size-change="onSizeChange"
      v-on:current-change="onCurrentChange"
      class="dc-el-pagination_ElPagination"
    ></el-pagination>





      <span slot="footer" class="dialog-footer">
        <el-button v-on:click="onSubmit" type="primary" v-show="action != 'view'">保存</el-button>
        <el-button v-on:click="onDialogClose" v-if="action != 'view'">取消</el-button>
        <el-button v-on:click="onDialogClose" v-if="action == 'view'">关闭</el-button>
      </span>
    </el-dialog>
  </van-row>
</template>
<script>
import { validatenull } from '@/utils/validate'
/** 根据用户界面配置import组件 结束 */

/** 根据用户界面配置import组件 结束 */
import {  listAll } from '@/api/fixasset/tfamSummary/tfamSummaryManager'
import {  listTfamAssetTransferPage } from "@/api/fixasset/asset/tfamAssetTransfer.js";
import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'
export default {
  extends: BaseUI,
  name: 'tfamAsset2Manager-form',
  components: {
    /** 根据用户界面配置组件 开始 */

    /** 根据用户界面配置组件 结束 */

    OperationIcon
  },
  data() {
    return {
      // 查询表格列头
      tableColumns: {

        130: {
          title: '资产编号',
          field: 'card.assetCode',
          visible: true,
          order: 1
        },
        131: {
          title: '资产名称',
          field: 'card.assetName',
          visible: true,
          order: 2
        },
        // 132: {
        //   title: '调拨项目',
        //   field: 'accudep',
        //   visible: true,
        //   order: 3
        // },
        133: {
          title: '调拨原因',
          field: 'transferReason',
          visible: true,
          order: 4
        },
        134: {
          title: '调拨前使用部门',
          field: 'beforeTransferDepartment',
          visible: true,
          order: 5
        },
        135: {
          title: '调拨后使用部门',
          field: 'afterTransferDepartment',
          visible: true,
          order: 6
        },
        136: {
          title: '调拨前责任人',
          field: 'beforeTransferOwner',
          visible: true,
          order: 7
        },
        137: {
          title: '调拨后责任人',
          field: 'afterTransferOwner',
          visible: true,
          order: 8
        }
      },
      // 分页属性
      tableDataPage: 1,
      tableDataTotal: 0,
      search_data:[],
      transfer_date:"",
      search: {
        // 查询条件   业务表设置的筛选条件
        params: [],

        offset: 0,
        limit: 10,
        columnName: '', // 排序字段名
        order: '' // 排序
      },
      queryFormData: {
        source_type: "",
        create_date: "",
        end_date: "",
      },


      /** 根据用户界面配置生成data数据 开始 */
      editFormData: this.initEditData(),
      // 对话框属性变量
      dialogProps: {
        visible: false,
        title: '调拨明细'
      },
      dialogTitle: '调拨明细',
      // 选项变量

      /** 根据用户界面配置生成data数据 结束 */

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

    clickItem(data){
        //this.chooseItemData = data;

    },

    onSizeChange(val) {
      this.tableDataPage = 1
      this.search.limit = val
      this.search.offset = (this.tableDataPage - 1) * val
      this.getList()
    },
    onCurrentChange(val) {
      this.search.offset = (val - 1) * this.search.limit
      this.tableDataPage = val
      this.getList()
    },

    getFormById(id) {
      this.setLoad()
      return new Promise((resolve) => {
        getTfamAsset2ManagerById(id)
          .then((responseData) => {
            let form = {}
            if (responseData.code == 100) {
               form = responseData.data
            } else {
               this.showMessage(responseData)
            }
            this.resetLoad()
            resolve(form)
          })
          .catch((error) => {
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

    getList(){//202208
      let year = this.transfer_date.substring(0,4)
      let month = this.transfer_date.substring(4)
      let start = year+"-"+month+"-01 00:00:00";
      let end  = year+"-"+month+"-"+(new Date(year,month,0)).getDate()+" 00:00:00";

      this.search.params = [
        {
          columnName: "create_date",
          queryType: ">=",
          value: !validatenull(start) ? start : null,
        },
        {
          columnName: "create_date",
          queryType: "<=",
          value: !validatenull(end) ? end : null,
        }
      ];

      console.log("调拨展示的数据111", this.search);
      listTfamAssetTransferPage(this.search)
        .then((responseData) => {
          console.log("调拨展示的数据222", responseData);
          if (responseData.code == 100) {
            this.search_data = responseData.data.rows;
            this.tableDataTotal =  responseData.data.total;
          }
        })
        .catch((error) => {
          this.outputError(error);
        });
    },
    initOptions(This) {
      this.getList();
    },
    initEditData(This) {
      return {
        userid: '', // 管理员用户ID
        createTime: '', // 创建时间
        companyId: '', // 租户ID
        remarks: '' // 备注信息
      }
    }
  },
  watch: {},
  mounted: function () {
    this.$nextTick(() => {
      this.$on('openViewDialog', async function (id) {
        this.transfer_date = id
        this.action = 'view'
        this.dialogProps.title = id+this.dialogTitle
        this.editFormData = {
          ...this.initEditData(),
          // ...(await this.getFormById(id))
        }
        this.initOptions(this.editFormData)
        this.dialogProps.visible = true
      })
    })
  }
}
</script>
