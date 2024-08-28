<template>
  <div class="viewBox" v-if="isShow">
    <!-- 关闭按钮 -->
    <van-icon class="closeBtn" name="cross" @click="isShow = false" />
    <!-- pdf组件 -->
    <div class="pdf-box" v-if="type === 'pdf'">
      <div class="pdf_btn" v-if="pageTotalNum > 1">
        <van-button plain color="#07AC7F" @click="prePage">上一页</van-button>
        {{ pageNum }}/{{ pageTotalNum }}
        <van-button plain color="#07AC7F" @click="nextPage">下一页</van-button>
      </div>
      <pdf
        :page="pageNum"
        :src="newSrc"
        @progress="loadedRatio = $event"
        @num-pages="pageTotalNum = $event"
      ></pdf>
    </div>

    <!-- docx组件 -->
    <div class="word-box" v-if="type === 'word'" ref="docPreview"></div>

    <!-- 表格组件 -->
    <div class="table-box" v-if="type === 'excel'">
      <van-tabs
        class="table-tab"
        v-if="sheetNames.length"
        title-active-color="#07AC7F"
        color="#07AC7F"
        @click="clickTab"
      >
        <van-tab
          v-for="(item, index) in sheetNames"
          :key="index"
          :name="item"
          :title="item"
        ></van-tab>
      </van-tabs>
      <div class="tableBox" ref="excPreview"></div>
    </div>

    <div class="txt-box" v-if="type === 'txt'" ref="txtPreview"></div>
  </div>
</template>

<script>
import pdf from "vue-pdf"; // pdf
import mammoth from "mammoth"; // word文档
import XLSX from "xlsx"; // excel表格
import { ImagePreview } from "vant";

export default {
  name: "FilePreview",
  components: {
    pdf,
  },
  props: {
    datas: {},
    type: {},
  },
  data() {
    return {
      isShow: false,
      pageNum: 1,
      pageTotalNum: 1, // 总页数
      newSrc: "", // 路径
      sheetNames: [],
      wsObj: {},
    };
  },
  methods: {
    /**
     * 处理展示数据
     */
    showFile(newVal) {
      if (this.type === "img") {
        // 图片预览，这里是后台返回了base64的数据，处理成了blob格式
        const val = this.base64ToBlob(newVal, "image/jpeg");
        this.newSrc = window.URL.createObjectURL(val);
        // vant自带图片预览组件
        const that = this;
        ImagePreview({
          images: [this.newSrc],
          showIndex: false,
          onClose() {
            that.isShow = false;
          },
        });
      }

      if (this.type === "pdf") {
        this.pageNum = 1;
        this.pageTotalNum = 1;
        // pdf预览
        const val = this.base64ToBlob(newVal, "application/pdf");
        const href = window.URL.createObjectURL(val);
        this.newSrc = pdf.createLoadingTask(href);
      }

      if (this.type === "word") {
        // word文档预览，只能预览docx
        const val = this.base64ToBlob(newVal, "application/msword");
        mammoth
          .convertToHtml({ arrayBuffer: val })
          .then(this.displayResult)
          .done();
      }

      if (this.type === "excel") {
        // excel预览
        this.sheetNames = []; // 重置
        const wb = XLSX.read(newVal, { type: "base64" });
        this.sheetNames = [...wb.SheetNames]; // 数组
        this.wsObj = { ...wb.Sheets };
        this.changeExcel(this.sheetNames[0]);
      }

      if (this.type === "txt") {
        // txt
        const val = this.$base64ToBlob(newVal, "text/plain");
        const reader = new FileReader();
        const that = this;
        reader.readAsText(val);
        reader.onload = function () {
          that.$refs.txtPreview.innerHTML = reader.result || "";
        };
      }
    },

    /**
     * 上一页
     */
    prePage() {
      let page = this.pageNum;
      page = page > 1 ? page - 1 : this.pageTotalNum;
      this.pageNum = page;
    },
    /**
     * 下一页
     */
    nextPage() {
      let page = this.pageNum;
      page = page < this.pageTotalNum ? page + 1 : 1;
      this.pageNum = page;
    },

    /**
     * word----docx预览
     */
    displayResult(result) {
      this.$refs.docPreview.innerHTML = result.value || "";
    },

    /**
     * 切换表格
     */
    clickTab(name) {
      this.changeExcel(name);
    },

    /**
     * 处理excel表格
     */
    changeExcel(item) {
      // 获取当前选中表格对象
      const ws = this.wsObj[item];
      const keyArr = Object.keys(ws) || [];
      const HTML =
        keyArr.length > 1
          ? XLSX.utils.sheet_to_html(ws)
          : '<html><head><meta charset="utf-8"/>' +
            '<title>SheetJS Table Export</title></head><body><div class="myTable">暂无数据</div></body>' +
            "</html>";
      this.$nextTick(() => {
        this.$refs.excPreview.innerHTML = HTML;
        // 获取表格dom元素
        const tables = this.$refs.excPreview.children[2];
        // 添加完毕后 通过空格将数组组装为字符串
        tables.className = "myTable";
      });
    },

    base64ToBlob(base64, mimetype, slicesize) {
      let num = 0;
      if (!window.atob || !window.Uint8Array) {
        return null;
      }
      mimetype = mimetype || "";
      slicesize = slicesize || 512;
      const bytechars = atob(base64);
      const bytearrays = [];
      for (let a = 0; a < bytechars.length; a += slicesize) {
        const slice = bytechars.slice(a, a + slicesize);
        const bytenums = new Array(slice.length);
        for (var i = 0; i < slice.length; i++) {
          bytenums[i] = slice.charCodeAt(i);
        }
        const bytearray = new Uint8Array(bytenums);
        bytearrays[bytearrays.length] = bytearray;
      }
      return new Blob(bytearrays, { type: mimetype });
    },
  },
};
</script>

<style scoped lang="scss">
.viewBox {
  width: 100vw;
  height: 100vh;
  overflow: hidden;
  background: #ffffff;
  position: absolute;
  top: 0;
  left: 0;
  z-index: 2000;
  .closeBtn {
    position: absolute;
    top: 10px;
    right: 10px;
    z-index: 10;
    font-size: 24px;
  }
}

.pdf-box,
.word-box,
.table-box,
.txt-box {
  width: 100vw;
  height: 100vh;
}
.pdf-box {
  .pdf_btn {
    padding: 0 10px;
    height: 44px;
    box-sizing: border-box;
    display: flex;
    justify-content: center;
    align-items: center;
    /deep/.van-button {
      margin: 0 2px;
      height: 24px;
      &:nth-child(1) {
        border-radius: 15px 0 0 15px;
      }
      &:nth-child(2) {
        border-radius: 0 15px 15px 0;
      }
    }
  }
  > span {
    width: 100%;
    height: calc(100% - 44px);
  }
}
.word-box {
  padding: 40px 15px 15px;
  box-sizing: border-box;
  overflow: auto;
}
.table-tab {
  width: calc(100% - 44px);
}
// 考核模块table样式
.tableBox {
  width: 100vw;
  height: calc(100vh - 44px);
  overflow: auto;
  // 表格边框
  @table-border: 1px solid #929292;
  // 表格
  /deep/ table.myTable {
    width: auto;
    height: auto;
    color: #333333;
    // 合并边框
    border-collapse: collapse;
    // border: @table-border;
    // th，td
    th,
    td {
      // 禁止换行
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
      background: #ffffff;
      padding: 10px;
      // border: @table-border;
    }
    th {
      // 正常粗细
      font-weight: normal;
      // 表头信息居左显示
      &.th-info {
        text-align: left;
        span {
          // 表头信息样式优化
          margin-right: 30px;
        }
      }
    }
    // 表格内容居中
    td {
      text-align: center;
    }
  }
  /deep/ div.myTable {
    text-align: center;
    margin: 30% auto;
  }
}
.txt-box {
  padding: 40px 15px 15px;
  box-sizing: border-box;
  overflow: auto;
}
</style>
