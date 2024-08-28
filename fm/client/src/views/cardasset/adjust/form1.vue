<template>
  <van-row class="dm-container">
    <edit-form
      ref="editForm"
      @onGetData="getCheckData4"
      :permission="permission"
    ></edit-form>

    <addCertificateDialog
      v-if="certShow"
      ref="addCertificateDialog"
      @closed="certShow = false"
    ></addCertificateDialog>
    <el-dialog title="调整记录" :visible.sync="dialogAdjustVisible">
      <div style="margin-bottom: 20px">
        <el-button
          type="primary"
          icon="el-icon-guide"
          class="dc-el-button_Button"
          v-on:click="onCreate"
        >关联凭证</el-button
        >
        <el-button
          type="danger"
          icon="el-icon-connection"
          class="dc-el-button_Button"
          v-on:click="onDelect"
        >取消关联</el-button
        >
        <el-button
          type="primary"
          icon="el-icon-search"
          class="dc-el-button_Button"
          v-on:click="onView"
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
          title="序号"
          :params="{ sortId: '148', summary: false }"
          type="index"
          tree-node
          resizable
          min-width="80px"
          fixed="left"
          align="left"
          header-align="center"
          class="dc-ux-table-column_ElTableNumColumn"
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
  /** 根据用户界面配置import组件 结束 */
  import { listTfamAssetEntryPage, deleteTfamAssetEntry,linkvoucher,cancelvoucher } from '@/api/cardasset/credit/tfamAssetEntry'
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
        if (this.currentRow.voucherid==''){
          this.showMessage({
            type: 'success',
            msg: '未关联凭证'
          })
          return
        }
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
      onSubmit() {
        let validFlag = true

        this.$refs['tfamCategoryOldForm'].validate((valid) => {
          if (valid) {
          } else {
            this.tabName = 'tfamCategoryOldTable'
            validFlag = false
            return false
          }
        })

        this.$refs['tfamUsingstatusOldForm'].validate((valid) => {
          if (valid) {
          } else {
            this.tabName = 'tfamUsingstatusOldTable'
            validFlag = false
            return false
          }
        })

        this.$refs['sysUserOldForm'].validate((valid) => {
          if (valid) {
          } else {
            this.tabName = 'sysUserOldTable'
            validFlag = false
            return false
          }
        })

        this.$refs['tfamDepmethodOldForm'].validate((valid) => {
          if (valid) {
          } else {
            this.tabName = 'tfamDepmethodOldTable'
            validFlag = false
            return false
          }
        })

        this.$refs['editForm'].validate((valid) => {
          if (valid) {
          } else {
            this.tabName = 'editForm'
            validFlag = false
            return false
          }
        })
        if (validFlag) {
          this.doSave()
        }
      },
      getCheckData4(data) {
        debugger
        console.log(data)
        console.log(this.currentRow)
        // var ids = $("#gridFormA").jqGrid('getGridParam', 'selarrrow');
        var alterids = [];
        // for (let i = 0; i < this.currentRow.length; i++) {
        //   alterids.push(this.currentRow[i].id)
        // }
        debugger
        alterids.push(this.currentRow.id)
        let json ={
          alterid : alterids,
          voucherid : data
        }
        linkvoucher(json)
          .then((responseData) => {
            if (responseData.code == 100) {
              this. getLists();
              this.showMessage({
                type: 'success',
                msg: '关联成功'
              })
            } else {
              this.showMessage(responseData)
            }
            this.resetLoad()
          })
          .catch((error) => {
            this.outputError(error)
          })
      },
      doSave() {

      },
      switchEdit() {

      },
      onCreate() {
        this.$refs.editForm.$emit('openAddDialog')
      },
      onDelect() {
        if (this.currentRow==''){
          this.showMessage({type:'warning' , msg: '请先选择需要取消关联凭证的折旧明细。'})
          return
        }
        this.$confirm('是否继续取消所选卡片的关联凭证信息？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          cancelvoucher({id:this.currentRow.id})
            .then((responseData) => {
              if (responseData.code == 100) {
                this. getLists();
                this.showMessage({
                  type: 'success',
                  msg: '取消成功'
                })
              } else {
                this.showMessage(responseData)
              }
              this.resetLoad()
            })
            .catch((error) => {
              this.outputError(error)
            })
        }).catch(() => {
        });
      },
      getFormById(id) {

      },
      onDialogClose() {
        this.dialogProps.visible = false
      },
      onDialogOpen() {

      },
      getLists(){
        listTfamAlterPage(this.adjustsearch).then((responseData) => {
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
      tabName(val, oldVal) {
        this.$nextTick(() => {
          if (this.$refs[val] && this.$refs[val].doLayout) {
            this.$refs[val].doLayout()
          }
        })
      }
    },
    mounted: function () {
      this.$nextTick(() => {
        this.$on('openViewDialog', async function (id) {
          this.action = 'view'
          this.dialogProps.title = `查看${this.dialogTitle}`
          this.editFormData = {
            ...this.initEditData(),
            ...(await this.getFormById(id))
          }
          this.initOptions(this.editFormData)
          this.tabName = 'editForm'
          this.dialogProps.visible = true
        })

        this.$on('openEditDialog', async function (id) {
          this.action = 'edit'
          this.dialogProps.title = `修改${this.dialogTitle}`
          this.editFormData = {
            ...this.initEditData(),
            ...(await this.getFormById(id))
          }
          this.initOptions(this.editFormData)
          this.tabName = 'editForm'
          this.dialogProps.visible = true
        })
        this.$on('openAddDialog', function (checkBoxData) {
          // 第一次查询
          this.adjustsearch.offset = 0;
          this.adjustDataPage = 1;
          this.checkdata=checkBoxData
          this.dialogAdjustVisible = true;
          this.setLoad();
          this.adjustsearch.params=[];
          this.adjustsearch.params.push({
            columnName: "asset_id",
            queryType: "like",

            value: checkBoxData.id ? checkBoxData.id : null,
          });
          console.log("查看调整记录====查询条件", this.adjustsearch);
          this. getLists();
        })
        this.$on('openCopyDialog', async function (id) {
          this.action = 'add'
          this.dialogProps.title = `添加${this.dialogTitle}`
          this.editFormData = {
            ...this.initEditData(),
            ...(await this.getFormById(id))
          }
          this.initOptions(this.editFormData)
          this.tabName = 'editForm'
          this.editFormData.id = null //把id设置为空，添加一个新的

          for (var i = 0; i <= this.editFormData.tfamCategoryOldList.length - 1; i++) {
            this.editFormData.tfamCategoryOldList[i].id = null
          }

          for (var i = 0; i <= this.editFormData.tfamUsingstatusOldList.length - 1; i++) {
            this.editFormData.tfamUsingstatusOldList[i].id = null
          }

          for (var i = 0; i <= this.editFormData.sysUserOldList.length - 1; i++) {
            this.editFormData.sysUserOldList[i].id = null
          }

          for (var i = 0; i <= this.editFormData.tfamDepmethodOldList.length - 1; i++) {
            this.editFormData.tfamDepmethodOldList[i].id = null
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
