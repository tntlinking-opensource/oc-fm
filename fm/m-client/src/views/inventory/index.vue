<template>
  <div v-if="pageType == 'detail'" class="auditForm">
    <headerItem
      :isShowLeftIcon="true"
      :title="'个人电脑盘点'"
      :iconName="'scan'"
      :isShowRightIcon="true"
      @rightfun="scanFun"
    />
    <input type="file" @change="uploadFile" ref="scanFun" multiple="false" style="display: none;" />
    <div class="contain">
      <div class="center">
        <div class="dm-van-popup_VanPopup_body">
          <p class="text-line">资产信息</p>
          <van-form ref="zichanForm" class="dm-van-form_VanEditForm">
            <van-field
              v-model="editFormData.assetId"
              label="资产编号"
              :maxLength="255"
              placeholder="请扫描或输入卡片编号"
              clearable
              class="dm-van-field_VanField"
              @blur="getFormById(editFormData.assetId)"
            ></van-field>
            <van-field
              v-model="editFormData.assetName"
              label="资产名称"
              :required="false"
              :maxLength="255"
              placeholder="请输入资产名称"
              clearable
              readonly
              autocomplete="new-password"
              class="dm-van-field_VanField"
            ></van-field>
            <van-field
              v-model="editFormData.category.name"
              label="类别"
              :required="false"
              :maxLength="50"
              placeholder="请输入类别"
              clearable
              readonly
              class="dm-van-field_VanField"
            ></van-field>
            <dm-number-input
              v-model="editFormData.usedmonth"
              label="使用月数"
              :min="0"
              :max="99999999999"
              :precision="0"
              placeholder="请输入使用月数"
              clearable
              readonly
              :required="false"
              class="dm-dm-number-input_DmNumberInput"
            ></dm-number-input>
            <dm-number-input
              v-model="editFormData.servicemonth"
              label="剩余使用月数"
              :min="0"
              :max="99999999999"
              :precision="0"
              placeholder="请输入剩余使用月数"
              clearable
              readonly
              :required="false"
              class="dm-dm-number-input_DmNumberInput"
            ></dm-number-input>
            <van-field
              v-model="editFormData.cardModel"
              label="型号"
              :required="false"
              :maxLength="255"
              placeholder="请输入型号"
              clearable
              readonly
              class="dm-van-field_VanField"
            ></van-field>
          </van-form>
          <!-- 盘点信息部分 -->
          <p class="text-line">盘点信息</p>
          <van-form ref="editForm" class="dm-van-form_VanEditForm">
            <dm-picker
              v-model="editFormData.username"
              label="负责人"
              value-key="name"
              :value="editFormData.username"
              :rules="[{ required: true, message: '请选择负责人' }]"
              show-toolbar
              :columns="useridOptions"
              :required="true"
              placeholder="请选择负责人"
              @oninput="useridOptionsChange()"
              class="dm-dm-picker_DmPicker"
            ></dm-picker>
            <van-field
              v-model="editFormData.employeeno"
              label="工号"
              :required="true"
              :maxLength="255"
              :rules="[{ required: true, message: '请输入工号' }]"
              placeholder="请输入工号"
              clearable
              class="dm-van-field_VanField"
            ></van-field>
            <dm-picker
              v-model="editFormData.deptName"
              label="部门"
              value-key="name"
              :value="editFormData.deptName"
              show-toolbar
              :columns="usedeptidOptions"
              :rules="[{ required: true, message: '请选择部门' }]"
              :required="true"
              placeholder="请选择部门"
              @oninput="usedeptidOptionsChange()"
              class="dm-dm-picker_DmPicker"
            ></dm-picker>
            <!-- <van-cell
              title="所在地点"
              :value="editFormData.address"
              placeholder="请选择所在地点"
              is-link
              :required="true"
              @click="areaFun"
            >
            </van-cell> -->
            <van-field
              v-model="editFormData.address"
              label="所在地点"
              :required="true"
              :maxLength="10"
              placeholder="请选择所在地点"
              :rules="[{ required: true, message: '请选择所在地点' }]"
              clearable
              @click="areaFun"
              class="dm-van-field_VanField"
            ></van-field>
            <!-- <dm-picker
              v-model="editFormData.file"
              label="附件上传"
              :required="true"
              placeholder="请选择附件上传"
              @click="uploadfileFun"
              class="dm-dm-picker_DmPicker"
            ></dm-picker> -->
            <van-cell
              title="附件上传"
              :value="editFormData.file"
              is-link
              @click="uploadfileFun"
            >
            </van-cell>
          </van-form>
        </div>
      </div>
      <div class="footer">
        <div class="dm-van-popup_VanPopup_footer">
          <van-row
            gutter="0"
            type="flex"
            justify="center"
            align="middle"
            class="dm-van-row_VanRow"
          >
            <van-button type="primary" block v-on:click="onSubmit"
              >提交</van-button
            >
          </van-row>
        </div>
      </div>
    </div>
  </div>
  <div v-else-if="pageType == 'scan'">
    <headerItem
      :isShowLeftIcon="true"
      :title="'扫描二维码'"
      :isCustomLeft="true"
      @leftfun="backDetailFun()"
    />

    <div id="app_scan">
      <div>
        <span  id = "my_result" ></span>
      </div>
      <div>
        <button @click="qinit()">开始扫描</button>
        <button @click="qstop()">停止扫描</button>
      </div>
      <div>识别的条形码为：{{ codeNumber }}</div>
    </div>


  </div>
  <div v-else class="uploadBox">
    <headerItem
      :isShowLeftIcon="true"
      :title="'附件上传'"
      :isCustomLeft="true"
      @leftfun="backDetailFun()"
    />
    <div class="upload-box-text">
      <span
        >点击图片放大预览，双击或双指缩放可对图片进行放大，缩小操作。左右滑动可查看同组其他图片，请勿上传超过10M图片！</span
      >
    </div>
    <div class="upload-file-box">
      <div class="fliebox">
        <p class="file-title">身份证</p>
        <uploadImage
          ref="idFlie"
          :maxCount="2"
          :maxSize="10485760"
          :uploadText="'点击上传'"
          @beforeReadFun="beforeReadFun"
        />
      </div>
    </div>
    <div class="upload-file-box">
      <div class="fliebox">
        <p class="file-title">资产图片</p>
        <uploadImage
          ref="zichanFlie"
          :maxCount="7"
          :maxSize="10485760"
          :uploadText="'点击上传'"
          @beforeReadFun="beforeReadFun"
        />
      </div>
    </div>
    <div class="footer">
      <div class="dm-van-popup_VanPopup_footer">
        <van-row
          gutter="0"
          type="flex"
          justify="center"
          align="middle"
          class="dm-van-row_VanRow"
        >
          <van-button type="primary" block v-on:click="onSubmitfile"
            >保存</van-button
          >
        </van-row>
      </div>
    </div>
  </div>
</template>

<script>

import Quagga from 'quagga';

import headerItem from "@/components/common/header.vue";
import uploadImage from "@/views/components/uploadImage.vue";
import { Toast } from "vant";
/** 根据用户界面配置import组件 结束 */
import {
  listUserAll,
  treeDepartment,
  getTfamAssetById,
  saveCheckAttach,
  personCheck,
} from "@/api/checkpaln/detail/tfamAssetCheckplanDetail";
import BaseUI from "@/views/components/baseUI";
let Base64 = require("js-base64").Base64;
export default {
  extends: BaseUI,
  name: "auditForm",
  components: {
    headerItem,
    uploadImage,
  },
  data() {
    return {

      pageType: "sacn", // 详情detail，扫描sacn， 附件file 上传模块
      /** 根据用户界面配置生成data数据 开始 */
      // zichanData: {
      //   assetId: "", // 资产编号
      //   assetName: "", // 资产名称
      //   category: "", // 类别
      //   usedmonth: "", // 使用月数
      //   naturemonth: "", // 预计使用月数
      //   servicemonth: "", // 剩余使用月数
      //   cardModel: "", // 型号
      // },
      InventoryInfo: {
        address: "",
      },
      editFormData: this.initEditData(),
      fileList: [
        {
          name: "food.jpeg",
          url: "https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100",
        },
        {
          name: "food2.jpeg",
          url: "https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100",
        },
      ],

      olduseridValue: {},
      // 选项变量
      useridOptions: [],
      usedeptidOptions: [],
      /** 根据用户界面配置生成data数据 结束 */
    };
  },
  mounted: function () {
    this.initEditData();
    this.$nextTick((_) => {
      // console.log(this.$route.query, "======");
      this.editFormData.planId = this.$route.query.planId;
      this.listUseridOptions();
      this.listUsedeptidOptions();
    });
  },
  methods: {
    //开始扫描
    qinit(){

      Quagga.init({
        inputStream : {
          name : 'Live',
          type : 'LiveStream',
          target: document.querySelector('#my_result')
        },
        decoder : {
          readers : ["ean_reader", "qr_reader"],
          debug: {
            drawBoundingBox: true,
            showFrequency: true,
            drawScanline: true,
            showPattern: true
          },
          multiple: true
        }
      }, function(err) {
        console.log("扫码;",err);
        if (err) {
          console.log(err);
          return
        }
        Quagga.start();
        Quagga.onDetected(function(data){

          console.log("条形码：",data)

        })

      });



      // let _self = this
      // _self.isShow = true
      // Quagga.init({
      //   decoder : {
      //     readers : ["ean_reader",'code_39_reader'],
      //     multiple: false
      //   }
      // }, function(err) {
      //   if (err) {
      //     console.dir(err);
      //     alert('error of init camera')
      //     return
      //   }
      //   Quagga.start();
      //   Quagga.onDetected(function(data){
      //     _self.codeNumber = data.codeResult.code
      //     console.log("条形码：",_self.codeNumber)
      //     _self.isShow = false
      //   })
      //
      // })
    },
    //停止扫描
    qstop(){
      Quagga.stop();
    },



    // 负责人
    listUseridOptions() {
      let search_List = {
        params: [],
      };
      // filter条件
      search_List.params.push.apply(search_List.params, []);
      // 数据权限: 用户sys_user
      this.pushDataPermissions(
        search_List.params,
        this.$route.meta.routerId,
        "1457254743943431179"
      );
      listUserAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.useridOptions = responseData.data;
          // responseData.data.map((item) => {
          //   this.useridOptions.push({
          //     text: item.name,
          //     value: item.empolyno,
          //     id: item.id,
          //   });
          // });
          console.log(this.useridOptions, "列表====");
        } else {
          Toast(responseData.msg);
        }
      });
    },

    // 负责人改变
    useridOptionsChange(value) {
      // console.log(value, "====", this.editFormData.username);

      this.editFormData.userId = this.editFormData.username
        ? this.editFormData.username.id
        : "";
      this.editFormData.employeeno = this.editFormData.username
        ? this.editFormData.username.empolyno
        : "";
    },

    // 部门
    listUsedeptidOptions() {
      let search_List = {
        params: [],
      };
      // filter条件
      search_List.params.push.apply(search_List.params, []);
      // 数据权限: 部门org_department
      this.pushDataPermissions(
        search_List.params,
        this.$route.meta.routerId,
        "1633870636743041550"
      );
      treeDepartment(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.usedeptidOptions = responseData.data;
          // responseData.data.map((item) => {
          //   this.usedeptidOptions.push({
          //     text: item.name,
          //     value: item.id,
          //   });
          // });
        } else {
          Toast(responseData.msg);
        }
      });
    },

    // 部门改变
    usedeptidOptionsChange() {
      this.editFormData.deptId = this.editFormData.deptName
        ? this.editFormData.deptName.id
        : "";
    },

    // 卡片id查询数据
    getFormById(id) {
      this.setLoad();
      getTfamAssetById(id)
        .then((responseData) => {
          console.log("查询列表数据", responseData);
          if (responseData.code == 100) {
            this.editFormData = Object.assign(
              this.editFormData,
              responseData.data
            );
            this.editFormData.usedmonth =
              this.editFormData.naturemonth * 1 -
              this.editFormData.servicemonth * 1;

            this.editFormData.username = this.editFormData.userid.name;
            this.editFormData.userId = this.editFormData.userid.id;
            this.editFormData.employeeno = this.editFormData.userid.empolyno;
            this.editFormData.deptName = this.editFormData.usedeptid.name;
            this.editFormData.deptId = this.editFormData.usedeptid.id;
          } else {
            Toast(responseData.msg);
          }
          this.resetLoad();
        })
        .catch((error) => {
          this.outputError(error);
        });
    },

    // 图片上传前
    beforeReadFun(file) {
      console.log("图片信息", file);
      if (file.size > 10485760) {
        Toast("图片大小超过10M");
      }
    },

    // 保存文件上传
    onSubmitfile() {
      debugger
      let idfiles = this.$refs.idFlie.getFileList();
      let zichanfiles = this.$refs.zichanFlie.getFileList();
      console.log("文件保存", idfiles, zichanfiles);
      let attachList = idfiles.concat(zichanfiles);
      let params = new FormData()
      for (let idx in attachList) {
        params.append('file', attachList[idx].file)
      }
      // params.append('file', attachList)
      params.append('conditionDto', JSON.stringify(this.editFormData))
      // let params = {
      //   file:attachList,
      //   conditionDto:this.editFormData
      // }
      saveCheckAttach(params).then((responseData) => {
        if (responseData.code == 100) {
          Toast("保存成功");
          console.log(this.useridOptions, "图片上传====");
        } else {
          Toast(responseData.msg);
        }
      });
    },

    // 自定义上传文件返回
    backDetailFun() {
      console.log("自定义返回");
      this.pageType = "detail";

    },
    // 扫描二维码
    scanFun() {
      // this.pageType = "scan";
      // console.log("唤起二维码");
      this.$refs.scanFun.click();


    },
    convertToBase64(file) {
      let _self = this
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => {
        let base64Data = reader.result;
        Quagga.decodeSingle({
          src: base64Data,
          decoder : {
            readers : ["code_128_reader"]
          },
          locate: true
        }, function(result) {
          if(result&&result.codeResult) {
            _self.getFormById(result.codeResult.code) ;
            _self.editFormData.assetId=result.codeResult.code
          } else {
            _self.$toast.success("未能识别出任何编码");
          }
        });
      };
    },
  uploadFile(event) {
      if (event.target.files.length!=1){
        Toast("请选择一个文件上传");
        return
      }

    this.convertToBase64(event.target.files[0])

    },
    onSubmit() {
      console.log(this.editFormData, "提交数据");
      this.$refs.editForm
        .validate()
        .then(() => {
          this.doSave();
        })
        .catch(() => {});
    },
    doSave() {
      this.setLoad();

      personCheck(this.editFormData)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.$toast.success("提交成功");
          } else {
            // Toast(responseData.msg);
            Toast({
              message: responseData.msg,
              duration: 2000,
              onClose: () => {
                this.resetLoad();
              },
            });
          }
        })
        .catch((error) => {
          this.outputError(error);
        });
    },

    // 获取地址
    areaFun() {
      console.log("获取地址");
      if (this.editFormData.address.length > 0) return;
      var that = this;
      //下面试地图定位
      var geolocation = new BMap.Geolocation();
      // 开启SDK辅助定位
      geolocation.enableSDKLocation();
      var myGeo = new BMap.Geocoder(); //根据经纬度获取地址信息
      geolocation.getCurrentPosition(function (r) {
        if (this.getStatus() == BMAP_STATUS_SUCCESS) {
          myGeo.getLocation(r.point, function (result) {
            if (result) {
              that.editFormData.address = result.address;
            }
          });
        } else {
          Toast("定位失败");
          console.log("定位失败");
          //this.outputError("定位失败");
        }
      });
    },

    // 上传附件
    uploadfileFun() {
      console.log("上传附件方法");
      this.pageType = "file";
    },

    initEditData(This) {
      return {
        assetId: "", // 资产编号
        assetName: "", // 资产名称
        category: "", // 类别
        usedmonth: "", // 使用月数
        naturemonth: "", // 预计使用月数
        servicemonth: "", // 剩余使用月数
        cardModel: "", // 型号
        // 分割线
        username: "",
        userId: "", // 负责人
        employeeno: "", // 工号
        deptName: "", //
        deptId: "", // 部门
        address: "", // 获取地址
        file: null, // 盘点前资产状态
        // 界面没有的字段
        planId: "", // 盘点列表id
        checkresult: "", // 盘点结果
        assetStatue: "",
        remark: "",
      };
    },
  },
};
</script>

<style lang="scss" scoped>
.auditForm {
  height: 100%;
  .contain {
    height: 100%;
    .center {
      padding-bottom: 50px;
      .text-line {
        margin-left: 10px;
      }
    }
    .footer {
      width: 100%;
      bottom: 0;
      position: fixed;
    }

    .van-cell {
      color: #646566;
    }
  }
}
.uploadBox {
  .upload-box-text {
    background: #fff;
    margin: 20px 10px;
    padding: 25px 20px;
    color: #646566;
  }
  .upload-file-box {
    background: #fff;
    margin: 20px 10px;
    padding: 15px 20px;
    color: #646566;
    .file-title {
      margin-bottom: 5px;
    }
  }
  .footer {
    width: 100%;
    bottom: 0;
    position: fixed;
  }
}



</style>
