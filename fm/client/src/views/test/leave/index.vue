<template>
  <div>
    <el-row :gutter="15">
      <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px">
        <el-col :span="12">
          <el-form-item label="会计期" prop="uqglobalperiodid">
            <el-select v-model="formData.uqglobalperiodid" placeholder="请选择会计期" :style="{width: '100%'}">
              <el-option v-for="(item, index) in uqglobalperiodidOptions" :key="index" :label="item.label"
                :value="item.value" :disabled="item.disabled"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="附件数" prop="intaffix">
            <el-input v-model="formData.intaffix" placeholder="请输入附件数" :style="{width: '100%'}"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="交易日期" prop="chargeagainstsubmitodd">
            <el-date-picker v-model="formData.chargeagainstsubmitodd" format="yyyy-MM-dd"
              value-format="yyyy-MM-dd" :style="{width: '100%'}" placeholder="请选择交易日期"></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="制证日期" prop="dtfiller">
            <el-date-picker v-model="formData.dtfiller" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
              :style="{width: '100%'}" placeholder="请选择制证日期"></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="凭证编号" prop="intvouchernum">
            <el-input v-model="formData.intvouchernum" placeholder="请输入凭证编号" :disabled='true'
              :style="{width: '100%'}"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item size="large">
            <el-button type="primary" @click="submitForm">提交</el-button>
            <el-button @click="resetForm">重置</el-button>
          </el-form-item>
        </el-col>
      </el-form>
    </el-row>
  </div>
</template>
<script>
export default {
  components: {},
  props: [],
  data() {
    return {
      formData: {
        uqglobalperiodid: undefined,
        intaffix: undefined,
        chargeagainstsubmitodd: null,
        dtfiller: null,
        intvouchernum: undefined,
      },
      rules: {
        uqglobalperiodid: [{
          required: true,
          message: '请选择会计期',
          trigger: 'change'
        }],
        intaffix: [{
          required: true,
          message: '请输入附件数',
          trigger: 'blur'
        }],
        chargeagainstsubmitodd: [],
        dtfiller: [],
        intvouchernum: [],
      },
      uqglobalperiodidOptions: [{
        "label": "选项一",
        "value": 1
      }, {
        "label": "选项二",
        "value": 2
      }],
    }
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {
    submitForm() {
      this.$refs['elForm'].validate(valid => {
        if (!valid) return
        // TODO 提交表单
      })
    },
    resetForm() {
      this.$refs['elForm'].resetFields()
    },
  }
}

</script>
<style>
</style>
