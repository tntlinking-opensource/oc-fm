<template>
  <div>
    <van-uploader
      v-model="uploader"
      multiple
      :max-count="maxCount"
      :max-size="maxSize"
      :before-read="beforeRead"
      :after-read="afterRead"
      :upload-text="uploadText"
      class="van-cell-uploader"
    />
  </div>
</template>
<script>
import { Toast } from "vant";
import {
  fileUploadById,
  deleteSysFile,
  listSysFileAll,
} from "@/api/sys/sysFile";

export default {
  data() {
    return {
      uploader: [],
      fileList: [],
    };
  },
  props: {
    maxCount: {
      typeof: Number,
      default: "",
    },
    maxSize: {
      typeof: Number,
      default: "",
    },
    uploadText: {
      typeof: String,
      default: "",
    },
  },
  methods: {
    // 上传前预处理
    beforeRead(file) {
      this.$emit("beforeReadFun", file);
      if (file.type.indexOf("image") < 0) {
        Toast("请上传图片");
        return false;
      }
      return true;
    },
    // 上传后处理
    afterRead(file) {
      this.fileList.push(file);
      this.$emit("afterReadFun", file);
      /**
       * 需要上传文件的列表在
       * fileList:[{content:'',file:file,message:'',status:''}]
       * file才是数据文件流，在doSave时，取出来并拼接到formdata中进行保存
       */
    },
    getFileList() {
      return this.fileList;
    },
  },
};
</script>
<style lang="scss">
.van-cell-uploader {
  .van-uploader__wrapper {
    justify-content: end;
  }
}
</style>
