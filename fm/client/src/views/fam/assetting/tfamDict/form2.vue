<template>
  <div>
    <el-dialog
      v-bind="$attrs"
      v-on="$listeners"
      @open="onOpen"
      @close="onClose"
      :title="dialogProps.title"
      :visible.sync="dialogProps.visible"
      >
      <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px">
        <el-form-item label="编号" prop="code">
          <el-input v-model="formData.code" type="number" placeholder="请输入编号" clearable :style="{width: '100%'}"></el-input>
        </el-form-item>
        <el-form-item label="方式名称" prop="name">
          <el-input v-model="formData.name" placeholder="请输入方式名称" clearable :style="{width: '100%'}">
          </el-input>
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-radio-group v-model="formData.type" size="medium">
            <el-radio v-for="(item, index) in field104Options" :key="index" :label="item.value"
                      :disabled="item.disabled">{{item.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="close">取消</el-button>
        <el-button type="primary" @click="handelConfirm">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { saveTfamChangeWay,getTfamChangeWayById } from '@/api/fixasset/changeway/tfamChangeWay'
import BaseUI from '@/views/components/baseUI'
export default {
  extends: BaseUI,
  inheritAttrs: false,
  components: {},
  props: [],
  data() {
    return {
      // 窗口操作类型 view/edit/add
      action: '',
      dialogProps: {
        visible: false,
        title: '增减方式管理'
      },
      dialogTitle: '增减方式管理',
      formData: {
        id: undefined,
        code: undefined,
        name: undefined,
        type: 1,
        companyId: undefined
      },
      rules: {
        code: [{
          required: true,
          message: '请输入编号',
          trigger: 'blur'
        }],
        name: [{
          required: true,
          message: '请输入名称',
          trigger: 'blur'
        }],
        type: [{
          required: true,
          message: '请选择类型',
          trigger: 'change'
        }],
      },
      field104Options: [{
        "label": "增加",
        "value": 1
      }, {
        "label": "减少",
        "value": 2
      }],
    }
  },
  computed: {},
  watch: {},
  created() {
  },
  mounted() {
    this.$nextTick(() => {
      this.$on('openEditDialog', async function (id) {
        this.action = 'edit'
        this.dialogProps.title = `修改${this.dialogTitle}`
        this.getDataById(id);
        this.tabName = 'editForm'
        this.dialogProps.visible = true
      })
      this.$on('openAddDialog', function (parent) {
        this.action = 'add'
        this.dialogProps.title = `添加${this.dialogTitle}`
        this.initOptions()
        this.tabName = 'editForm'
        this.dialogProps.visible = true
      })




    })
  },
  methods: {
    initOptions(){
     this.formData= {
          id: undefined,
          code: undefined,
          name: undefined,
          type: undefined,
          companyId: undefined
      }
    },
    onOpen() {
    },
    onClose() {
      this.$refs['elForm'].resetFields()
    },
    close() {
      this.dialogProps.visible = false
    },
    getDataById(data){
      getTfamChangeWayById(data).then(responseData => {
        if (responseData.code == 100) {
          this.formData=responseData.data;
        } else {
          this.showMessage(responseData);
        }
        this.resetLoad();
      })
        .catch(error => {
          this.outputError(error);
        });
    },
    handelConfirm() {
      this.$refs['elForm'].validate(valid => {
        if (!valid) return
        this.formData.companyId= currentUser.company.id
        saveTfamChangeWay(this.formData).then(responseData => {
          if (responseData.code == 100) {
            this.$emit("onGetData","");
            this.dialogProps.visible = false;
            this.showMessage({
              type: "success",
              msg: "保存成功"
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
    },
  }
}

</script>
<style>
</style>
