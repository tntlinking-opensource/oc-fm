<template>

  <el-tabs
    v-loading.fullscreen.lock="fullscreenLoading"
    :tab-position="tabPosition" v-model="activeName" style="height: 100%;margin-top: 20px"  @tab-click="handleClick" v-loading="loading">
    <el-tab-pane label="类别管理" name="assetclass">
      <el-row gutter="0" type="flex" justify="end" align="top" class="dc-el-row_ElRow" style="margin: 20px">
          <el-button type="primary" icon="el-icon-plus" v-on:click="onCreate">添加</el-button>
          <el-button type="primary" icon="el-icon-edit" v-on:click="onEdit">修改</el-button>
          <el-button  type="danger" icon="el-icon-delete" v-on:click="onDelete">删除</el-button>
          <el-button type="primary" icon="el-icon-download" v-on:click="export1">导出</el-button>
           <el-button type="primary" icon="el-icon-download" v-on:click="exportTemplates('assetCategoryModle')">下载导入模板</el-button>
          <el-button type="primary" icon="el-icon-upload2" v-on:click="checkFile">导入</el-button>
      </el-row>
      <el-table
        :data="tableData"
        ref="singleTable"
        row-key="id"
        border
        default-expand-all
        highlight-current-row
        @current-change="handleCurrentChange"
        :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
        <el-table-column
          prop="fullcode"
          label="编码"
          sortable
          width="180">
        </el-table-column>
        <el-table-column
          prop="fullname"
          label="类别名称"
          sortable
          width="180">
        </el-table-column>
        <el-table-column
          prop="methodname"
          label="折旧方式"
          sortable
        >
        </el-table-column>
        <el-table-column
          prop="servicemonth"
          label="使用月数"
          sortable
        >
        </el-table-column>
        <el-table-column
          prop="salvagerate"
          label="产值率(%)"
          sortable
        >
        </el-table-column>
        <el-table-column
          prop="unit"
          label="计量单位"
          sortable
        >
        </el-table-column>
        <el-table-column
          prop="imagename"
          label="图列名称"
          sortable
        >
        </el-table-column>
      </el-table>
<!--      <el-pagination-->
<!--        :total="tableDataTotal"-->
<!--        :page-size="search.limit"-->
<!--        background-->
<!--        :current-page="tableDataPage"-->
<!--        :page-sizes="[10, 20, 30, 40, 50, 100]"-->
<!--        layout="total, sizes, prev, pager, next, jumper"-->
<!--        v-on:size-change="onSizeChange"-->
<!--        v-on:current-change="onCurrentChange"-->
<!--        class="dc-el-pagination_ElPagination"-->
<!--      ></el-pagination>-->
      <edit-form ref="EditForm" @onGetData="getCheckData"></edit-form>
      <input ref="fileinput" id="fileinput"  type="file" style="display: none" @change="uploadExcel"
             accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel "/>
    </el-tab-pane>
    <el-tab-pane label="增减方式管理" name="assetchangeway">

      <el-row gutter="0" type="flex" justify="end" align="top" class="dc-el-row_ElRow" style="margin: 20px">
        <el-button type="primary" icon="el-icon-plus" v-on:click="onCreate2">添加</el-button>
        <el-button type="primary" icon="el-icon-edit" v-on:click="onEdit2">修改</el-button>
        <el-button type="danger" icon="el-icon-delete" v-on:click="onDelete2">删除</el-button>
        <el-button type="primary" icon="el-icon-download" v-on:click="export2">导出</el-button>
        <el-button type="primary" icon="el-icon-download" v-on:click="exportTemplates('assetchangewayModel')">下载导入模板</el-button>
        <el-button type="primary" icon="el-icon-upload2" v-on:click="checkFile2">导入</el-button>
      </el-row>
      <el-table
        :data="tableData2"
        ref="singleTable2"
        row-key="id"
        border
        default-expand-all
        highlight-current-row
        v-on:sort-change="onSortChange2"
        @current-change="handleCurrentChange2"
        style="width: 100%;margin-bottom: 20px;"
      >
        <el-table-column
          prop="code"
          label="编码"
          sortable="custom"
        >
        </el-table-column>
        <el-table-column
          prop="name"
          label="方式名称"
          sortable="custom"
        >
        </el-table-column>
        <el-table-column
          prop="type"
          label="类型"
          sortable="custom"
        >
          <template slot-scope='{row, $index}'>
            {{row.type=="1"?"增加":"减少"}}
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        :total="tableDataTotal2"
        :page-size="search2.limit"
        background
        :current-page="tableDataPage2"
        :page-sizes="[10, 20, 30, 40, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        v-on:size-change="onSizeChange2"
        v-on:current-change="onCurrentChange2"
        class="dc-el-pagination_ElPagination"
      ></el-pagination>
      <edit-form2 ref="EditForm2" @onGetData="getCheckData2" ></edit-form2>
      <input type="file" ref="fileinput2" id="fileinput2" style="display: none" @change="uploadExcel2"
             accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel "/>

    </el-tab-pane>
    <el-tab-pane label="使用状况管理" name="usagestatus">
      <el-row gutter="0" type="flex" justify="end" align="top" class="dc-el-row_ElRow" style="margin: 20px">
        <el-button type="primary" icon="el-icon-plus" v-on:click="onCreate3">添加</el-button>
        <el-button type="primary" icon="el-icon-edit" v-on:click="onEdit3">修改</el-button>
        <el-button type="danger" icon="el-icon-delete" v-on:click="onDelete3">删除</el-button>
      </el-row>
      <el-table
        :data="tableData3"
        ref="singleTable3"
        row-key="id"
        border
        default-expand-all
        highlight-current-row
        @current-change="handleCurrentChange3"
        v-on:sort-change="onSortChange3"
        style="width: 100%;margin-bottom: 20px;"
      >
        <el-table-column
          prop="code"
          label="编码"
          sortable="custom"
          >
        </el-table-column>
        <el-table-column
          prop="name"
          label="状况名称"
          sortable="custom"
          >
        </el-table-column>
      </el-table>
      <el-pagination
        :total="tableDataTotal3"
        :page-size="search3.limit"
        background
        :current-page="tableDataPage3"
        :page-sizes="[10, 20, 30, 40, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        v-on:size-change="onSizeChange3"
        v-on:current-change="onCurrentChange3"
        class="dc-el-pagination_ElPagination"
      ></el-pagination>
      <edit-form3 ref="EditForm3" @onGetData="getCheckData3" ></edit-form3>
    </el-tab-pane>
    <el-tab-pane label="供应商管理" name="supplier">

      <el-row gutter="0" type="flex" justify="end" align="top" class="dc-el-row_ElRow" style="margin: 20px">
        <el-button type="primary" icon="el-icon-plus" v-on:click="onCreate4">添加</el-button>
        <el-button type="primary" icon="el-icon-edit" v-on:click="onEdit4">修改</el-button>
        <el-button type="danger" icon="el-icon-delete" v-on:click="onDelete4">删除</el-button>
        <el-button type="primary" icon="el-icon-download" v-on:click="export4">导出</el-button>
        <el-button type="primary" icon="el-icon-download" v-on:click="exportTemplates('supplierImportModel')">下载导入模板</el-button>
        <el-button type="primary" icon="el-icon-upload2" v-on:click="checkFile4">导入</el-button>
      </el-row>
      <el-table
        :data="tableData4"
        ref="singleTable4"
        row-key="id"
        border
        default-expand-all
        highlight-current-row
        @current-change="handleCurrentChange4"
        v-on:sort-change="onSortChange4"
        style="width: 100%;margin-bottom: 20px;"
      >
        <el-table-column
          prop="code"
          label="编码"
          sortable="custom"
        >
        </el-table-column>
        <el-table-column
          prop="name"
          label="供应商名称"
          sortable="custom"
        >
        </el-table-column>
      </el-table>
      <el-pagination
        :total="tableDataTotal4"
        :page-size="search4.limit"
        background
        :current-page="tableDataPage4"
        :page-sizes="[10, 20, 30, 40, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        v-on:size-change="onSizeChange4"
        v-on:current-change="onCurrentChange4"
        class="dc-el-pagination_ElPagination"
      ></el-pagination>
      <edit-form4 ref="EditForm4" @onGetData="getCheckData4" ></edit-form4>
      <input type="file" ref="fileinput4" id="fileinput4" style="display: none" @change="uploadExcel4"
             accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel "/>
    </el-tab-pane>
  </el-tabs>
</template>
<script>
import { validatenull } from '@/utils/validate'
import { getData,deleteTfamCategory,exportTfamCategory,importTfamCategory } from '@/api/fixasset/category/tfamCategory'
import { listTfamUsingstatusPage,deleteTfamUsingstatus } from '@/api/fixasset/usingstatus/tfamUsingstatus'
import { listTfamSupplierPage,deleteTfamSupplier,exportTfamSupplier,importTfamSupplier } from '@/api/fixasset/producer/tfamSupplier'
import { listTfamChangeWayPage,deleteTfamChangeWay,exportTfamChangeWay,importTfamChangeWay } from '@/api/fixasset/changeway/tfamChangeWay'
import EditForm from './form.vue'
import EditForm2 from './form2.vue'
import EditForm3 from './form3.vue'
import EditForm4 from './form4.vue'
import BaseUI from '@/views/components/baseUI'
import DcMain from "@/views/components/dcMain";
import moment from "moment";
export default {
  extends: DcMain,
  components: {
    EditForm,
    EditForm2,
    EditForm3,
    EditForm4
  },
  data() {
    return {
      fullscreenLoading: false,
      activeName:'assetclass',
      tabPosition: 'left',
      currentRow:null,
      fileinput:null,
      //第一个页面
      tableData:[],
      // 分页属性
      tableDataPage: 1,
      tableDataTotal: 0,
      search: {
        // 查询条件   业务表设置的筛选条件
        params: [],

        offset: 0,
        limit: 10,
        columnName: '', // 排序字段名
        order: '' // 排序
      },
      //第二个
      //第四个
      tableData2:[],
      tableDataPage2: 1,
      tableDataTotal2: 0,
      search2: {
        // 查询条件   业务表设置的筛选条件
        params: [],

        offset: 0,
        limit: 10,
        columnName: '', // 排序字段名
        order: '' // 排序
      },
      currentRow2:null,
      //第三个
      tableData3:[],
      tableDataPage3: 1,
      tableDataTotal3: 0,
      search3: {
        // 查询条件   业务表设置的筛选条件
        params: [],

        offset: 0,
        limit: 10,
        columnName: '', // 排序字段名
        order: '' // 排序
      },
      currentRow3:null,
      //第四个
      tableData4:[],
      tableDataPage4: 1,
      tableDataTotal4: 0,
      search4: {
        // 查询条件   业务表设置的筛选条件
        params: [],

        offset: 0,
        limit: 10,
        columnName: '', // 排序字段名
        order: '' // 排序
      },
      currentRow4:null
    };
  },
  methods: {
    exportTemplates(fileName) {
      this.fullscreenLoading = true;
      let a = document.createElement("a");
      a.href = 'static/template/' + fileName + '.xls'
      a.download = fileName + ".xls"
      a.style.display = "none"
      document.body.appendChild(a)
      a.click()
      a.remove()
      setTimeout(() => {
        this.fullscreenLoading = false;
      }, 1000);
    },
    onSortChange2(orderby){
      debugger
      if (validatenull(orderby.prop)) {
        this.search2.columnName = ''
        this.search2.order = ''
      } else {
        this.search2.columnName = orderby.prop
        this.search2.order = orderby.order=="ascending"?"asc":"desc"
      }
      this.getList2()
    },
    handleCurrentChange(val){
      this.currentRow = val;
    },
    handleClick(tab, event) {
      console.log(tab, event);
      console.log(this.activeName);
      if(this.activeName=="assetclass"){
        this.getList()
      }else if(this.activeName=="assetchangeway"){
        this.getList2()
      }else if(this.activeName=="usagestatus"){
        this.getList3()
      }else if(this.activeName=="supplier"){
        this.getList4()
      }

    },
    //第一个
    onCurrentChange(val) {
      this.search.offset = (val - 1) * this.search.limit
      this.tableDataPage = val
      this.getList()
    },
    onSizeChange(val) {
      this.tableDataPage = 1
      this.search.limit = val
      this.search.offset = (this.tableDataPage - 1) * val
      this.getList()
    },
    getList(){
      this.setLoad()
      getData().then((responseData) => {
        if (responseData.code == 100) {
         this.tableData=responseData.data;
        } else {
          this.showMessage(responseData)
        }
        this.currentRow=null
        this.resetLoad()
      })
        .catch((error) => {
          this.outputError(error)
        })
    },
    getCheckData(data) {
      this.getList();
    },
    onCreate(){
      this.$refs.EditForm.$emit('openAddDialog',this.currentRow)
    },
    export1() {
      this.setLoad()
      /* 查询参数 和数据权限 */
      this.search.params.push({
        columnName: 'company_id',
        queryType: '=',
        value: currentUser.company.id
      })
      exportTfamCategory(this.search).then((responseData) => {
        let date = moment().format("YYYY-MM-DD");
        const blob = new Blob([responseData]);
        const url = window.URL.createObjectURL(blob);
        let dom = document.createElement("a");
        dom.style.display = "none";
        dom.href = url;
        dom.setAttribute("download", '类别管理_' + date +  '.xls');
        document.body.appendChild(dom);
        dom.click();
        this.resetLoad()
      }).catch((error) => {
        this.outputError(error)
      })
    },
    checkFile() {
      document.querySelector('#fileinput').click()
    },
    onEdit(){
      if (this.currentRow==null){
        this.showMessage({
          type: 'success',
          msg: '请选中一行'
        })
        return
      }
      this.$refs.EditForm.$emit('openEditDialog',this.currentRow.id)
    },
    onDelete(){
      if (this.currentRow==null){
        this.showMessage({
          type: 'success',
          msg: '请选中一行'
        })
        return
      }
      if (this.currentRow.isleaf==0){
        this.showMessage({
          type: 'success',
          msg: '不能删除非末级数据'
        })
        return
      }
      deleteTfamCategory(this.currentRow).then(responseData => {
        if (responseData.code == 100) {
          this.showMessage({
            type: "success",
            msg: "删除成功"
          });
        } else {
          this.showMessage(responseData);
        }
        this.getList()
      })
        .catch(error => {
          this.outputError(error);
        });
    },
    uploadExcel(evt) {
      const files = evt.target.files
      if (files == null || files.length == 0) {
        return
      }
      let formData = new FormData()
      formData.append('file', files[0])
      this.setLoad()
      importTfamCategory(formData).then((responseData) => {
        if (responseData.code == 100) {
          this.getList()
          this.showMessage({
            type: 'success',
            msg: '导入成功'
          })

        } else {
          this.showMessage(responseData)
        }
        this.$refs.fileinput.value = ''
        this.resetLoad()
      }).catch((error) => {
        this.$refs.fileinput.value = ''
        this.outputError(error)
      })
    },
    //第二个
    getList2(){
      this.search2.params.push({
        columnName: 'company_id',
        queryType: '=',
        value: currentUser.company.id
      })
      this.setLoad()
      listTfamChangeWayPage(this.search2).then((responseData) => {

        if (responseData.code == 100) {
          this.tableData2= responseData.data.rows ? responseData.data.rows : []
          this.tableDataTotal2 = responseData.data.total
          this.$refs.singleTable2.setCurrentRow();
          this.currentRow2=null
        } else {
          this.showMessage(responseData)
        }
        this.resetLoad()
      })
        .catch((error) => {
          this.outputError(error)
        })
    },
    getCheckData2(data) {
      debugger
      this.getList2();
    },
    onCreate2(){
      this.$refs.EditForm2.$emit('openAddDialog',)
    },
    onEdit2(){
      if (this.currentRow2==null){
        this.showMessage({
          type: 'success',
          msg: '请选中一行'
        })
        return
      }
      this.$refs.EditForm2.$emit('openEditDialog',this.currentRow2.id)
    },
    onDelete2(){
      if (this.currentRow2==null){
        this.showMessage({
          type: 'success',
          msg: '请选中一行'
        })
        return
      }
      deleteTfamChangeWay(this.currentRow2).then(responseData => {
        if (responseData.code == 100) {
          this.showMessage({
            type: "success",
            msg: "删除成功"
          });
        } else {
          this.showMessage(responseData);
        }
        this.getList2()
      })
        .catch(error => {
          this.outputError(error);
        });
    },
    handleCurrentChange2(val){
      this.currentRow2 = val;
    },
    onCurrentChange2(val) {
      this.search2.offset = (val - 1) * this.search2.limit
      this.tableDataPage2 = val
      this.getList2()
    },
    onSizeChange2(val) {
      this.tableDataPage2 = 1
      this.search2.limit = val
      this.search2.offset = (this.tableDataPage2 - 1) * val
      this.getList2()
    },
    export2() {
      this.setLoad()
      /* 查询参数 和数据权限 */
      this.search2.params.push({
        columnName: 'company_id',
        queryType: '=',
        value: currentUser.company.id
      })
      exportTfamChangeWay(this.search2).then((responseData) => {
        let date = moment().format("YYYY-MM-DD");
        const blob = new Blob([responseData]);
        const url = window.URL.createObjectURL(blob);
        let dom = document.createElement("a");
        dom.style.display = "none";
        dom.href = url;
        dom.setAttribute("download", '增减方式管理_' + date +  '.xls');
        document.body.appendChild(dom);
        dom.click();
        this.resetLoad()
      }).catch((error) => {
        this.outputError(error)
      })
    },
    checkFile2() {
      document.querySelector('#fileinput2').click()
    },
    uploadExcel2(evt) {
      const files = evt.target.files
      if (files == null || files.length == 0) {
        return
      }
      let formData = new FormData()
      formData.append('file', files[0])
      this.setLoad()
      importTfamChangeWay(formData).then((responseData) => {
        if (responseData.code == 100) {
          this.getList2()
          this.showMessage({
            type: 'success',
            msg: '导入成功'
          })

        } else {
          this.showMessage(responseData)
        }
        this.$refs.fileinput2.value = ''
        this.resetLoad()
      }).catch((error) => {
        this.$refs.fileinput2.value = ''
        this.outputError(error)
      })
    },
    //第三个
    getList3(){
      this.search3.params.push({
        columnName: 'company_id',
        queryType: '=',
        value: currentUser.company.id
      })
      this.setLoad()
      listTfamUsingstatusPage(this.search3).then((responseData) => {

        if (responseData.code == 100) {
          this.tableData3= responseData.data.rows ? responseData.data.rows : []
          this.tableDataTotal3 = responseData.data.total
          this.$refs.singleTable3.setCurrentRow();
          this.currentRow3=null
        } else {
          this.showMessage(responseData)
        }
        this.resetLoad()
      })
        .catch((error) => {
          this.outputError(error)
        })
    },
    getCheckData3(data) {
      debugger
      this.getList3();
    },
    onCreate3(){
      this.$refs.EditForm3.$emit('openAddDialog',)
    },
    onEdit3(){
      if (this.currentRow3==null){
        this.showMessage({
          type: 'success',
          msg: '请选中一行'
        })
        return
      }
      this.$refs.EditForm3.$emit('openEditDialog',this.currentRow3.id)
    },
    onDelete3(){

      if (this.currentRow3==null){
        this.showMessage({
          type: 'success',
          msg: '请选中一行'
        })
        return
      }
      deleteTfamUsingstatus(this.currentRow3).then(responseData => {
        if (responseData.code == 100) {
          this.$refs.singleTable4.setCurrentRow();
          this.currentRow4=null
          this.showMessage({
            type: "success",
            msg: "删除成功"
          });
        } else {
          this.showMessage(responseData);
        }
        this.getList3()
      })
        .catch(error => {
          this.outputError(error);
        });
    },
    handleCurrentChange3(val){
      this.currentRow3 = val;
    },
    onSortChange3(orderby){
      debugger
      if (validatenull(orderby.prop)) {
        this.search3.columnName = ''
        this.search3.order = ''
      } else {
        this.search3.columnName = orderby.prop
        this.search3.order = orderby.order=="ascending"?"asc":"desc"
      }
      this.getList3()
    },
    onCurrentChange3(val) {
      this.search.offset3 = (val - 1) * this.search3.limit
      this.tableDataPage3 = val
      this.getList3()
    },
    onSizeChange3(val) {
      this.tableDataPage4 = 1
      this.search3.limit = val
      this.search3.offset = (this.tableDataPage3 - 1) * val
      this.getList3()
    },
    //第四个
    getList4(){
      this.search4.params.push({
        columnName: 'company_id',
        queryType: '=',
        value: currentUser.company.id
      })
      this.setLoad()
      listTfamSupplierPage(this.search4).then((responseData) => {

        if (responseData.code == 100) {
          this.tableData4= responseData.data.rows ? responseData.data.rows : []
          this.tableDataTotal4 = responseData.data.total
          this.$refs.singleTable4.setCurrentRow();
          this.currentRow4=null
        } else {
          this.showMessage(responseData)
        }
        this.resetLoad()
      })
        .catch((error) => {
          this.outputError(error)
        })
    },
    getCheckData4(data) {
      debugger
      this.getList4();
    },
    onCreate4(){
      this.$refs.EditForm4.$emit('openAddDialog',)
    },
    onEdit4(){
      if (this.currentRow4==null){
        this.showMessage({
          type: 'success',
          msg: '请选中一行'
        })
        return
      }
      this.$refs.EditForm4.$emit('openEditDialog',this.currentRow4.id)
    },
    onDelete4(){
      if (this.currentRow4==null){
        this.showMessage({
          type: 'success',
          msg: '请选中一行'
        })
        return
      }
      deleteTfamSupplier(this.currentRow4).then(responseData => {
        if (responseData.code == 100) {
          this.showMessage({
            type: "success",
            msg: "删除成功"
          });
        } else {
          this.showMessage(responseData);
        }
        this.getList4()
      })
        .catch(error => {
          this.outputError(error);
        });
    },
    handleCurrentChange4(val){
      this.currentRow4 = val;
    },
    onSortChange4(orderby){
      debugger
      if (validatenull(orderby.prop)) {
        this.search4.columnName = ''
        this.search4.order = ''
      } else {
        this.search4.columnName = orderby.prop
        this.search4.order = orderby.order=="ascending"?"asc":"desc"
      }
      this.getList4()
    },
    onCurrentChange4(val) {
      this.search4.offset = (val - 1) * this.search4.limit
      this.tableDataPage4 = val
      this.getList4()
    },
    onSizeChange4(val) {
      this.tableDataPage4 = 1
      this.search4.limit = val
      this.search4.offset = (this.tableDataPage4 - 1) * val
      this.getList4()
    },
    export4() {
      this.setLoad()
      /* 查询参数 和数据权限 */
      this.search4.params.push({
        columnName: 'company_id',
        queryType: '=',
        value: currentUser.company.id
      })
      exportTfamSupplier(this.search4).then((responseData) => {
        let date = moment().format("YYYY-MM-DD");
        const blob = new Blob([responseData]);
        const url = window.URL.createObjectURL(blob);
        let dom = document.createElement("a");
        dom.style.display = "none";
        dom.href = url;
        dom.setAttribute("download", '供应商管理_' + date +  '.xls');
        document.body.appendChild(dom);
        dom.click();
        this.resetLoad()
      }).catch((error) => {
        this.outputError(error)
      })
    },
    checkFile4() {
      document.querySelector('#fileinput4').click()
    },
    uploadExcel4(evt) {
      const files = evt.target.files
      if (files == null || files.length == 0) {
        return
      }
      let formData = new FormData()
      formData.append('file', files[0])
      this.setLoad()
      importTfamSupplier(formData).then((responseData) => {
        if (responseData.code == 100) {
          this.getList4()
          this.showMessage({
            type: 'success',
            msg: '导入成功'
          })

        } else {
          this.showMessage(responseData)
        }
        this.$refs.fileinput4.value = ''
        this.resetLoad()
      }).catch((error) => {
        this.$refs.fileinput4.value = ''
        this.outputError(error)
      })
    },
  },
  mounted() {
    this.getList();
  }
}
</script>
