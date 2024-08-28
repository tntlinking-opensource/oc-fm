<template>
  <div>
    <el-row :gutter="18">
      <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px">
        <el-col :span="24">
          <el-form-item label-width="120px" label="当前业务期：" prop="currDate">
            <el-date-picker type="month" v-model="currDate" format="yyyy-MM" value-format="yyyy-MM"
                            :style="{width: '50%'}" disabled placeholder="" clearable></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="15">
          <el-form-item label="折旧期：" prop="depdate">
            <el-date-picker type="month" v-model="formData.depdate" format="yyyy-MM" value-format="yyyy-MM"
                            :style="{width: '100%'}" placeholder="请选择折旧期：" clearable></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="9">
          <el-form-item label="状态：" >
            <el-input v-model="currflag" disabled clearable :style="{width: '100%'}">
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="凭证摘要：" prop="v_abstract">
            <el-input v-model="formData.v_abstract" placeholder="请选择凭证摘要：" clearable :style="{width: '100%'}">
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="凭证字：" prop="v_numbering">
            <el-select v-model="formData.v_numbering" placeholder="请选择凭证字：" clearable
                       :style="{width: '100%'}">
              <el-option v-for="(item, index) in v_numberingOptions" :key="index" :label="item.label"
                         :value="item.value" :disabled="item.disabled"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="制证人：" prop="vorchaerDate">
            <el-select v-model="formData.vorchaerDate" placeholder="请选择制证人：" clearable
                       :style="{width: '100%'}">
              <el-option v-for="(item, index) in vorchaerDateOptions" :key="index" :label="item.name"
                         :value="item.id" :disabled="item.disabled"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="" prop="field108">
            <el-button type="primary" icon="el-icon-caret-right" size="medium" @click="startDepreciation"> 开始折旧 </el-button>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="" prop="field109">
            <el-button type="primary" icon="el-icon-caret-right" @click="submitForm" size="medium"> 生成凭证 </el-button>
          </el-form-item>
        </el-col>
      </el-form>
    </el-row>
  </div>
</template>
<script>
import DcMain from "@/views/components/dcMain";
import { dodep,checkasset,workloadlist,busdate,loadinfo,produceVoucher } from '@/api/cardasset/monthly/monthly'
import {listUserAll} from '@/api/admin/user'
export default {
  extends: DcMain,
  components: {},
  props: [],
  data() {
    return {
      currDate:"",
      currflag:"",
      formData: {
        depdate: null,
        v_abstract: null,
        v_numbering: "CF10100D-ED35-4B1D-AAC7-235F68DFB117",
        vorchaerDate: "",
        field108: undefined,
        field109: undefined,
      },
      rules: {
        depdate: [{
          required: true,
          message: '请选择折旧期：',
          trigger: 'change'
        }],
        v_abstract: [{
          required: true,
          message: '请选择凭证摘要：',
          trigger: 'blur'
        }],
        v_numbering: [{
          required: true,
          message: '请选择凭证字：',
          trigger: 'change'
        }],
        vorchaerDate: [{
          required: true,
          message: '请选择制证人：',
          trigger: 'change'
        }],
      },
      v_numberingOptions: [{
        "label": "转账",
        "value": "CF10100D-ED35-4B1D-AAC7-235F68DFB117"
      }],
      vorchaerDateOptions: [],
      busdate : {},
      search: {
        // 查询条件   业务表设置的筛选条件
        params: [],

        offset: 0,
        limit: 10,
        columnName: '', // 排序字段名
        order: '' // 排序
      },
    }
  },
  computed: {},
  watch: {

  },
  created() {
    this.$watch('formData.depdate', (newVal, oldVal) => {
      console.log("------------")
      this.loadInfo();
      // 进行相应的操作
    });
  },
  mounted() {
    this.initBusDate();
  },
  methods: {
    submitForm() {
      this.$refs['elForm'].validate(valid => {
        if (!valid) return
        // TODO 提交表单
        produceVoucher({
          abstracts : this.formData.v_abstract,//凭证摘要
          numbering :this.formData.v_numbering,//凭证字
          depdate : this.formData.depdate,//年
          loginName :  this.formData.vorchaerDate//制证人
        })
          .then((responseData) => {
            if (responseData.code === 100) {
              this.showMessage({
                type: "success",
                msg: `生成成功`,
              });
            } else {
              this.showMessage(responseData)
            }
            this.resetLoad()
          })
          .catch((error) => {
            this.outputError(error)
          })
      })
    },
    async startDepreciation() {
      debugger
      var isok = await this.checkAsset();
      if (isok) {
        var hasworkload =await this.checkWorkload();
        if (hasworkload) {
          //打开页面
          // $("#workloaddialog").WorkloadDilaog("initData");
          // $("#workloaddialog").WorkloadDilaog("open");
        } else {
          this.startDep(null);
        }
      } else {
        this.showMessage({
          type: "warning",
          msg: `存在未审核资产 请先审核`,
        });
      }
    },
    /**
     * 开始折旧
     * @param workoadData 工作量数据
     */
    startDep (workloadData)
    {
    debugger
      var year = null;
      var month = null;
      if(this.busdate.depreciation_time == 0)
      {
        year = this.busdate.nextyear;
        month = this.busdate.nextmonth;
      }
      else if(this.busdate.depreciation_time == 1)
      {
        year = this.busdate.year;
        month = this.busdate.month;
      }
      this.$confirm("确认开始"+year+"年"+month+"月"+"资产/卡片折旧", '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {

          var param =
            {
              formData : this.getFormData1()
            };
          if (workloadData != null && workloadData !== "")
          {
            param.workloadData =workloadData
          }
          dodep(param)
            .then((responseData) => {
              if (responseData.code === 100) {
                this.initBusDate()
                this.showMessage({
                  type: "success",
                  msg: `本期折旧完成`,
                });
              } else {
                this.showMessage(responseData)
              }
              this.resetLoad()
            })
            .catch((error) => {
              this.outputError(error)
            })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '折旧失败'
        });
      });
    },
    async checkAsset() {

      var result = false;
      await checkasset()
        .then((responseData) => {
          if (responseData.type == "success") {
            result = true;
          } else {
          result=false;
          }
        })
        .catch((error) => {
          this.outputError(error)
        })
      return result;
    },
    /**
     * 检查是否有工作量法的卡片
     */
    async checkWorkload() {
      var result = false;
      await workloadlist({
        type: 'check'
      })
        .then((responseData) => {
          if (responseData.code == 100) {
            result = responseData.success;
          } else {
            this.showMessage(responseData)
          }
        })
        .catch((error) => {
          this.outputError(error)
        })
      return result;
    },
    /**
     * 初始化获取当前业务年月
     */
    initBusDate ()
    {

      busdate()
        .then((responseData) => {
          if (responseData.code == 100) {
            this.busdate = responseData.data;
            this.setBusDate();
          } else {
            this.showMessage(responseData)
          }
          this.resetLoad()
        })
        .catch((error) => {
          this.outputError(error)
        })
      this.search.params = []
      this.search.params.push({
        columnName: 'company_id',
        queryType: '=',
        value:currentUser.company.id
      })
      listUserAll(this.search)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.vorchaerDateOptions = responseData.data;

          } else {
            this.showMessage(responseData)
          }
          this.resetLoad()
        })
        .catch((error) => {
          this.outputError(error)
        })
    },
    /**
     * 当前业务期间赋值
     */
    setBusDate ()
    {
      this.currDate=this.busdate.year + "-" + this.busdate.month
    },
    getFormData1 ()
    {
      debugger
      var year = null;
      var month = null;
      if(this.busdate.depreciation_time == 0)
      {
        year = this.busdate.nextyear;
        month = this.busdate.nextmonth;
      }
      else if(this.busdate.depreciation_time == 1)
      {
        year = this.busdate.year;
        month = this.busdate.month;
      }
      var formData =
        {
          year : year,
          month : month,
          abstract : this.formData.v_abstract,
          vouformat :this.formData.v_numbering
        };
      return formData;
    },
    loadInfo ()
    {
      loadinfo({depdate:this.formData.depdate})
        .then((responseData) => {
          if (responseData.code == 100) {
            this.setFormData(responseData.data);
          } else {
            this.showMessage(responseData)
          }
          this.resetLoad()
        })
        .catch((error) => {
          this.outputError(error)
        })
    },
    /**
     * 表单赋值
     * @param data
     */
    setFormData (data)
    {
      console.log(data)
      if(data != null)
      {
        if (data.isdep == 1)
        {
          this.currflag="已折旧"

          if (data.isvoucher == 1)
          {
            //$("#v_number").text(data.numberingname+'-'+data.vouchernum);
          }
          else
          {
            // $("#v_number").css('color','red');
            // $("#v_number").text('未生成');
          }
        }
        else
        {
          this.currflag="未折旧"
          // $("#d_flag").css('color','red');
          // $("#d_flag").text("未折旧");
          // $("#d_flag").val("未折旧");
        }
      }
      else
      {
        this.currflag="未折旧"
        // $("#d_flag").css('color','red');
        // $("#d_flag").text("未折旧");
        // $("#d_flag").val("未折旧");
      }
    },
  }
}

</script>
<style>
</style>
