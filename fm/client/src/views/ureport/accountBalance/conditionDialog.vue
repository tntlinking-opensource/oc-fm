<template>
  <div>
  <el-row class="dc-container">
    <el-dialog
      v-on:open="onDialogOpen"
      v-on:close="onDialogClose"
      v-loading="loading"
      width="30%"
      :fullscreen="false"
      :title="dialogProps.title"
      :visible.sync="dialogProps.visible"
      :close-on-click-modal="false"
      class="dc-el-dialog_ElDialog"
    >
      <el-row>
        <el-form
          ref="editForm"
          :model="editFormData"
          label-width="100px"
          class="dc-el-form_ElEditForm"
        >
          <el-row>
            <el-form-item label="公司名称：" >
              <el-select v-model="form.companyValue" placeholder="请选择"
                         multiple="true"
                         size="medium"
                         clearable="true"
                         collapse-tags
                         style="width: 80%"
              >
                <el-option
                  v-for="item in companyList"
                  :key="item.id"
                  :label="item.fullName"
                  :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item  label="科目从：" class="dc-el-form-item_NumberInput"
                          :rules="[{ required: true, message: '请选择科目', trigger: 'change' }]">
              <temlapte slot-scope="scope">
                <el-select v-model="form.startHkont" slot="suffix"  placeholder="请选择开始科目" :fetch-suggestions="queryHkont" style="width: 80%">
                  <el-option v-for="(item, index) in uqaccountidOptions"
                             :key="index"
                             :label="item.varaccountcode
                                  ? '[' + item.varaccountcode + ']' + item.varaccountname : item.varaccountname"
                             :value="item.varaccountcode
                                  ? '[' + item.varaccountcode + ']' + item.varaccountname : item.varaccountname"></el-option>
                </el-select>
                <el-button
                  icon="el-icon-search"
                  slot="append"
                  @click="addHkont1"
                ></el-button>
<!--                <el-button-->
<!--                  type="danger"-->
<!--                  icon="el-icon-delete"-->
<!--                  slot="append"-->
<!--                  @click="removeHkont1"-->
<!--                ></el-button>-->
              </temlapte>
            </el-form-item>
            <el-form-item  label="到：" class="dc-el-form-item_NumberInput"
                           :rules="[{ required: true, message: '请选择科目', trigger: 'change' }]">
              <temlapte slot-scope="scope">
                <el-select v-model="form.endHkont" placeholder="请选择开始科目" slot="suffix" :fetch-suggestions="queryHkont" style="width: 80%">
                  <el-option v-for="(item, index) in uqaccountidOptions"
                             :key="index"
                             :label="item.varaccountcode
                                  ? '[' + item.varaccountcode + ']' + item.varaccountname : item.varaccountname"
                             :value="item.varaccountcode
                                  ? '[' + item.varaccountcode + ']' + item.varaccountname : item.varaccountname"></el-option>
                </el-select>
                <el-button
                  icon="el-icon-search"
                  slot="append"
                  @click="addHkont2"
                ></el-button>
<!--                <el-button-->
<!--                  type="danger"-->
<!--                  icon="el-icon-delete"-->
<!--                  slot="append"-->
<!--                  @click="removeHkont2"-->
<!--                ></el-button>-->
              </temlapte>
            </el-form-item>
            <el-form-item  label="会计期从：" class="dc-el-form-item_NumberInput"
                          >
              <el-select
                value-key="id"
                v-model="form.startPeriod"
                :style="{ width: '100%' }"
                placeholder="请选择会计期"
                filterable
                class="dc-el-select_SelectInput"
              >
                <el-option v-for="(item, index) in globalPeriodOptions" :key="index" :label="item.varname" :value="item.intyearmonth"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item  label="到：" class="dc-el-form-item_NumberInput"
            >
              <el-select
                value-key="id"
                v-model="form.endPeriod"
                :style="{ width: '100%' }"
                placeholder="请选择会计期"
                filterable
                class="dc-el-select_SelectInput"
              >
                <el-option v-for="(item, index) in globalPeriodOptions" :key="index" :label="item.varname" :value="item.intyearmonth"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item  label="凭证状态：" class="dc-el-form-item_NumberInput"
            >
              <el-select
                value-key="id"
                v-model="form.bookStatus"
                :style="{ width: '100%' }"
                filterable
                class="dc-el-select_SelectInput"
              >
                <el-option v-for="item in bookStatusList" :key="item.categorycode" :label="item.categoryname" :value="item.categorycode"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item  label="显示科目级：" class="dc-el-form-item_NumberInput"
            >
              <el-select
                value-key="id"
                v-model="form.hkontLevel"
                :style="{ width: '100%' }"
                filterable
                class="dc-el-select_SelectInput"
              >
                <el-option v-for="item in hkontLevelList" :key="item.categorycode" :label="item.categoryname" :value="item.categorycode"></el-option>
              </el-select>
            </el-form-item>
            <template>
                <el-checkbox label="不显示期初数、发生数、余额都为零的科目"  v-model="form.isseveral"></el-checkbox>
            </template>
          </el-row>
        </el-form>
      </el-row>
      <span slot="footer" class="dialog-footer">
        <el-button v-on:click="onSubmit" type="primary">确定</el-button>
        <el-button v-on:click="onDialogClose">取消</el-button>
      </span>
    </el-dialog>
  </el-row>
  <addHkontDialog1
    ref="addHkontDialog1"
    @onGetData="onGetHkontData1"
  ></addHkontDialog1>
    <addHkontDialog2
    ref="addHkontDialog2"
    @onGetData="onGetHkontData2"
  ></addHkontDialog2>
  </div>
</template>
<script>
  import { listTglAccountsAll, getSubjectlevelList } from '@/api/vouchermanager/InitLedgePeriod/tglAccounts.js'
import { validatenull } from '@/utils/validate'
import addHkontDialog1 from "@/views/components/AccountingAccountQuery/index";
import addHkontDialog2 from "@/views/components/AccountingAccountQuery/index";
import { selectPeriod } from '@/api/tgl/global/globalPeriods'
import { getBookStatus,getHkontLevel,queryHkont,getCompanyList } from '@/api/ureport/accountingBook/accountingBook'
import { listDictItemAll } from '@/api/sys/dictItem.js'
import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'
export default {
  extends: BaseUI,
  name: 'reportDesign-query',
  components: {
    OperationIcon,
    addHkontDialog1,
    addHkontDialog2,
  },
  data() {
    return {
      /** 根据用户界面配置生成data数据 开始 */
      editFormData: this.initEditData(),
      // 对话框属性变量
      dialogProps: {
        visible: false,
        title: '过滤条件'
      },
      uqaccountidOptions: [],
      companyList:[],
      globalPeriodOptions: [],
      statusOptions:  [],
      bookStatusList:  [],
      hkontLevelList:  [],
      hkontList:  [],
      form:{
        companyValue:[],
        startAmount:'',
        endAmount:'',
        startHkont:'',
        endHkont:'',
        bookStatus:'',
        hkontLevel:'',
        startPeriod:'',
        endPeriod:'',
        isseveral:true,
      }
    }
  },
  props: {
  },
  computed: {},
  methods: {

    //科目弹窗
    addHkont1(){
      this.$refs.addHkontDialog1.open()
    },
    addHkont2(){
      this.$refs.addHkontDialog2.open()
    },
    //删除科目
    removeHkont1(){
      this.form.startHkont='';
    },
    //删除科目
    removeHkont2(){
      this.form.endHkont='';
    },
    onGetHkontData1(data) {
      this.form.startHkont =
        "[" +
        data.tableData.varaccountcode +
        "]" +
        data.tableData.varaccountname;
      this.form.endHkont =
        "[" +
        data.tableData.varaccountcode +
        "]" +
        data.tableData.varaccountname;
    },
    onGetHkontData2(data) {
      this.form.endHkont =
        "[" +
        data.tableData.varaccountcode +
        "]" +
        data.tableData.varaccountname;
    },

    onSubmit() {
      this.$refs['editForm'].validate((valid) => {
        if (valid) {
          this.doSave()
        } else {
          return false
        }
      })
    },
    doSave() {
      if (this.form.startPeriod==''){
        this.$message.error('开始账期不能为空!');
        return false;
      }
      if (this.form.endPeriod==''){
        this.$message.error('结束账期不能为空!');
        return false;
      }
      if (this.form.startPeriod-this.form.endPeriod>0){
        this.$message.error('会计期顺序有误!');
        return false;
      }
      if (this.form.startHkont=='' || this.form.endHkont==''){
        this.$message.error('科目不能为空!');
        return false;
      }
      if (this.form.companyValue==''){
        this.$message.error('公司不能为空!');
        return false;
      }
      this.dialogProps.visible = false
      this.$emit('choose-finished', this.form)
    },
    onDialogClose() {
      this.dialogProps.visible = false
    },
    onDialogOpen() {
      this.$nextTick(() => {
        this.$refs['editForm'].clearValidate()
      })
    },
    initOptions(This) {
      var searchParams = {
        params:[],
      }
      getCompanyList(searchParams).then(res =>{
         debugger;
         this.companyList=res.data.rows;
      })
      // 初始化自定义类型选择框选项变量
      let periodsSearch = {
        params: []
      }
      periodsSearch.params.push.apply(periodsSearch.params, [])
      // 数据权限: 会计期tgl_global_periods
      this.pushDataPermissions(periodsSearch.params, this.$route.meta.routerId, '1457254743943430269')
      selectPeriod(periodsSearch).then((responseData) => {
        debugger;
        if (responseData.code == 100) {
          this.globalPeriodOptions = responseData.data
          if (!validatenull(this.globalPeriodOptions)) {
            this.form.startPeriod = this.globalPeriodOptions[0].intyearmonth
            this.form.endPeriod = this.globalPeriodOptions[0].intyearmonth
          }
        } else {
          this.showMessage(responseData)
        }
      })
      getBookStatus().then(res =>{
        this.form.bookStatus=res.data[0].categorycode;
        this.bookStatusList=res.data;
      })
      getHkontLevel().then(res =>{
        this.form.hkontLevel=res.data[res.data.length-1].categorycode;
        this.hkontLevelList=res.data;
      })
      queryHkont().then(res =>{
        this.hkontList=res.data;
      })

      let statusSearch = {
        params: [{columnName: 'dict_type_id', queryType: '=', value: '1494073832128356364'}]
      }
      // filter条件
      statusSearch.params.push.apply(statusSearch.params, [])
      // 数据权限: 字典项sys_dict_item
      this.pushDataPermissions(statusSearch.params, this.$route.meta.routerId, '1457254743943431066')
      listDictItemAll(statusSearch).then((responseData) => {
        if (responseData.code == 100) {
          this.statusOptions = responseData.data
          if (!validatenull(this.statusOptions)) {
            let status = this.statusOptions.find((item)=>{
              return item.name == '全部凭证'
            })
            this.editFormData.status = status
          }
        } else {
          this.showMessage(responseData)
        }
      })
      // 获取科目管理列表
      let account_search = {
        params: [],
        columnName: 'varaccountfullcode',
        orderby: 'asc'
      };
      // filter条件
      account_search.params.push.apply(account_search.params, []);
      // 数据权限: 会计科目表tgl_accounts
      this.pushDataPermissions(account_search.params, this.$route.meta.routerId, "1457254743943430348");
      listTglAccountsAll(account_search).then(responseData => {
        if (responseData.code == 100) {
          this.uqaccountidOptions = responseData.data;
        } else {
          this.showMessage(responseData);
        }
      });
    },
    onChangeAccount(type) { // 选择科目
      this.accountType = type
      this.$refs.accountQuery.open()
    },
    queryHkont(queryString, cb) {
      var restaurants = this.hkontList;
      var results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    createFilter(queryString) {
      queryString="["+queryString
      return (restaurant) => {
        return (restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
      };
    },
    initEditData(id) {
      return {
        id: id,
        globalPeriods: {
          varname: '', // 会计期名称
          intyear: null, // 会计期年份
          intmonth: null, // 会计期月份
          intyearmonth: null, // 年月
          dtbegin: null, // 有效开始时间
          dtend: null, // 有效结束时间
          intstatus: null //状态
        },
        status: {
          id: '',
          name: '',
          value: ''
        }
      }
    }
  },
  watch: {},
  mounted: function () {
    this.$nextTick(() => {
      this.$on('openChooseDialog', async function (id) {
        this.editFormData = this.initEditData(id)
        this.initOptions(this.editFormData)
        this.dialogProps.visible = true
      })
    })
  }
}
</script>
