<template>
  <div>
    <el-dialog
      v-bind="$attrs"
      v-on="$listeners"
      @open="onOpen"
      @close="onClose"
      :title="dialogProps.title"
      :visible.sync="dialogProps.visible">
      <el-row :gutter="15">
        <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px">
          <el-col :span="12">
            <el-form-item label="编号" prop="fullcode">
              <el-input v-model="formData.fullcode" placeholder="请输入编号" clearable :style="{width: '100%'}">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="类别名称" prop="fullname">
              <el-input v-model="formData.fullname" placeholder="请输入类别名称" clearable :style="{width: '100%'}">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="折旧方法" prop="depmethod">
              <el-select v-model="formData.depmethod" placeholder="请选择折旧方法" clearable
                         :style="{width: '100%'}">
                <el-option v-for="(item, index) in depmethodOptions" :key="index" :label="item.label"
                           :value="item.value" :disabled="item.disabled"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="残值率" prop="salvagerate">
              <el-input v-model="formData.salvagerate" placeholder="请输入残值率" clearable
                        :style="{width: '100%'}"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label-width="120px" label="预计使用月数" prop="servicemonth">
              <el-input v-model="formData.servicemonth" placeholder="请输入预计使用月数" clearable
                        :style="{width: '100%'}"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="计量单位" prop="unit">
              <el-input v-model="formData.unit" placeholder="请输入计量单位" clearable :style="{width: '100%'}">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="上级类别" prop="parentcode">
              <el-input v-model="formData.parentcode" placeholder="请输入上级类别" clearable :disabled="true"
                        :style="{width: '100%'}"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="资产图例" prop="imagePath">
              <el-upload ref="imagePath" list-type="picture" :file-list="imagePathfileList" :action="imagePathAction"
                         :auto-upload="false"
                         :on-change="imagePathBeforeUpload">
                <el-button size="small" type="primary" icon="el-icon-upload">点击上传</el-button>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-form>
      </el-row>
      <div slot="footer">
        <el-button @click="close">取消</el-button>
        <el-button type="primary" @click="handelConfirm">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import BaseUI from '@/views/components/baseUI'
import {getDataByID, saveTfamCategory} from '@/api/fixasset/category/tfamCategory'

export default {
  extends: BaseUI,
  inheritAttrs: false,
  components: {},
  props: [],
  data() {
    return {
      dialogProps: {
        visible: false,
        title: '类别管理'
      },
      dialogTitle: '类别管理',
      formData: {
        id: undefined,
        fullcode: undefined,
        fullname: undefined,
        name: undefined,
        isleaf: 1,
        depmethod: undefined,
        salvagerate: undefined,
        servicemonth: undefined,
        unit: undefined,
        parentcode: undefined,
        parentid: undefined,
        imagePath: null,
        imagename: null,
        imagebase64: null,
      },
      rules: {
        fullcode: [{
          required: true,
          message: '请输入编号',
          trigger: 'blur'
        }],
        fullname: [{
          required: true,
          message: '请输入类别名称',
          trigger: 'blur'
        }],
        depmethod: [{
          required: true,
          message: '请选择折旧方法',
          trigger: 'change'
        }],
        salvagerate: [{
          required: true,
          message: '请输入残值率',
          trigger: 'blur'
        }],
        servicemonth: [{
          required: true,
          message: '请输入预计使用月数',
          trigger: 'blur'
        }],
        unit: [{
          required: true,
          message: '请输入计量单位',
          trigger: 'blur'
        }],
        parentcode: [{
          required: true,
          message: '请输入上级类别',
          trigger: 'blur'
        }],
      },
      imagePathAction: '',
      imagePathfileList: [],
      depmethodOptions: [{
        "label": "平均年限",
        "value": 1
      }],
    }
  },
  computed: {},
  watch: {
    'formData.salvagerate':{
      handler: 'validateInput',
      immediate: true,
      deep: true
    }

  },
  created() {
  },
  mounted() {
    this.$nextTick(() => {
      this.$on('openEditDialog', async function (id) {
        this.imagePathfileList=[]
        this.action = 'edit'
        this.dialogProps.title = `修改${this.dialogTitle}`
        this.getDataById(id);
        this.tabName = 'editForm'
        this.dialogProps.visible = true
      })
      this.$on('openAddDialog', function (parent) {
        this.imagePathfileList=[]
        this.action = 'add'
        this.dialogProps.title = `添加${this.dialogTitle}`
        this.initOptions()
        if (parent == null) {
          this.formData.parentcode = "0-0";
        } else {
          this.formData.parentcode = parent.fullcode
          this.formData.parentid = parent.id
        }
        this.tabName = 'editForm'
        this.dialogProps.visible = true
      })
    })
  },
  methods: {
    validateInput() {
      debugger
      const salvagerate = this.formData.salvagerate;
      if (this.formData.salvagerate==undefined){
        return '';
      }
      console.log(typeof salvagerate);

      if ( typeof salvagerate=='number'){
        return salvagerate;
      }
      this.formData.salvagerate =salvagerate.replace(/[^\d.]/g, "").replace(/\.{2,}/g, ".").replace(".","$#$").replace(/\./g, "").replace("$#$",".").replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');
      if (this.formData.salvagerate > 100) {
        this.formData.salvagerate = '';
      }
      },
    initOptions() {
      this.formData = {
        id: undefined,
        fullcode: undefined,
        fullname: undefined,
        name: undefined,
        isleaf: 1,
        depmethod: undefined,
        salvagerate: undefined,
        servicemonth: undefined,
        unit: undefined,
        parentcode: undefined,
        parentid: undefined,
        imagePath: null,
        imagename: null,
        imagebase64: null,
      }
    },
    getDataById(data) {
      getDataByID(data).then(responseData => {
        if (responseData.code == 100) {
          this.formData = responseData.data;
          var imgeBase64 = responseData.data.imagebase64;
          // var base64ToFile1 = this.base64ImgtoFile(imgeBase64,this.formData.imagename);
          if (imgeBase64){
            this.imagePathfileList = [];
            this.imagePathfileList = [{
              name: this.formData.imagename,
              url:imgeBase64
            }];
          }
         //  console.log("imgFile",base64ToFile1);
         // this.imagePathBeforeUpload(base64ToFile1)
        } else {
          this.showMessage(responseData);
        }
        this.resetLoad();
      })
        .catch(error => {
          this.outputError(error);
        });
    },
    onOpen() {
    },
    onClose() {
      this.$refs['elForm'].resetFields()
    },
    close() {
      this.dialogProps.visible = false
    },
    handelConfirm() {
      this.$refs['elForm'].validate(async valid => {
        if (!valid) return
        this.formData.companyId = currentUser.company.id
        this.formData.name = this.formData.fullname
        if (this.imagePathfileList.length != 0) {
          this.formData.imagename = this.imagePathfileList[0].name
          if(this.imagePathfileList[0].raw==undefined){
            this.formData.imagebase64 = this.imagePathfileList[0].url
          }else{
            this.formData.imagebase64 = await this.fileToBase64Async(this.imagePathfileList[0].raw);
          }
          
        }

        saveTfamCategory(this.formData).then(responseData => {
          if (responseData.code == 100) {
            this.$emit("onGetData", "");
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
    fileToBase64Async(file) {
      return new Promise((resolve, reject) => {
        let reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = (e) => {
          resolve(e.target.result);
        };
      });
    },
    imagePathBeforeUpload(file) {
      this.imagePathfileList = [];
      debugger
      let isRightSize = file.size / 1024 / 1024 < 2
      if (!isRightSize) {
        this.$message.error('文件大小超过 2MB')
      }
      let fileExtension = file.name.split(',')[0].split('.').pop().toLowerCase();
      let isJPG = ['jpg', 'png', 'gif', 'svg','jpeg'].includes(fileExtension);
      if (!isJPG) {
        this.$message.error('上传文件只能是jpg、png格式');
      }
      if (isRightSize && isJPG) {

        this.imagePathfileList[0] = file
      }
      return isRightSize && isJPG;
    },
    base64ImgtoFile (dataurl, filename ){
      let arr = dataurl.split(',')
      let mime = arr[0].match(/:(.*?);/)[1]
      let suffix = mime.split('/')[1]
      let bstr = atob(arr[1])
      let n = bstr.length
      let u8arr = new Uint8Array(n)
      while (n--) {
        u8arr[n] = bstr.charCodeAt(n)
      }
      return new File([u8arr], `${filename}.${suffix}`, {
        type: mime
      })
    },

  }
}

</script>
<style>
.el-upload__tip {
  line-height: 1.2;
}

</style>
