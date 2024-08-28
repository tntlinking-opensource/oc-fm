<template>
  <div>
    <CommGrid
      v-bind="$attrs"
      v-on="$listeners"
      ref="commGrid"
      :customBtn="customBtn"
      :autoHeight="false"
      :maxHeight="500"
      :descData="[{ title: tableTile }]"
      :pageNumSize="50"
      :slotEdit="['currentrushmoney']"
      @ellClick="ellClick"
      @btnOperate="btnOperate"
      @pageChange="pageChange"
      @checkbox-all="checkboxAll"
    />
    <addCertificateDialog
      v-if="certShow"
      ref="addCertificateDialog"
      @closed="certShow = false"
    ></addCertificateDialog>
  </div>
</template>

<script>
import CommGrid from "@/components/CommGrid/index.vue";
import { COMM_CONST } from "@/utils/const";
import DcMain from "@/views/components/dcMain";
import addCertificateDialog from "@/views/components/certificate/addCertificateDialog";
import {
  doOffset,
  revertRush,
  getOffsetData,
  getOnAccountData
} from "@/api/vouchermanager/tglAcOffsetMain";
import { validatenull } from "@/utils/validate";

import { getTglVoucherMainsById } from "@/api/vouchermanager/tglVoucherMains";
export default {
  extends: DcMain,
  components: {
    CommGrid,
    addCertificateDialog
  },
  props: {
    // 往来核销类型  writeOff：冲销记录、payAccount：挂账日期
    type: {
      String,
      default: ""
    },
    params: {
      Object,
      default: {}
    },
    isInner: {
      Boolean,
      default: false
    }
  },
  data() {
    return {
      currentRow: {},
      certShow: false,
      COMM_CONST,
      WRITEOFF: "writeOff",
      PAYACCOUNT: "payAccount",
      tableTile: "冲销记录",
      customBtn: [
        {
          code: "VIEW",
          name: "查询",
          status: COMM_CONST.PRIMARY,
          icon: COMM_CONST.SEARCH_ICON,
          isShow: true
        }
      ],
      customBtnWrite: [
        {
          code: "VIEW",
          name: "查询",
          status: COMM_CONST.PRIMARY,
          icon: COMM_CONST.SEARCH_ICON,
          isShow: true
        },
        {
          code: "CX",
          name: "冲销",
          status: COMM_CONST.PRIMARY,
          icon: COMM_CONST.SEARCH_ICON,
          isShow: false
        },
        {
          code: "RGPP",
          name: "人工匹配",
          status: COMM_CONST.PRIMARY,
          icon: COMM_CONST.SEARCH_ICON,
          isShow: false
        },
        {
          code: "HYCX",
          name: "还原冲销",
          status: COMM_CONST.PRIMARY,
          icon: COMM_CONST.SEARCH_ICON,
          isShow: false
        },
        {
          code: "PZMX",
          name: "凭证明细",
          status: COMM_CONST.PRIMARY,
          icon: COMM_CONST.SEARCH_ICON,
          isShow: false,
          loading: false
        }
      ],
      // ,
    // {
    //   code: "WLMX",
    //     name: "往来明细",
    //   status: COMM_CONST.PRIMARY,
    //   icon: COMM_CONST.SEARCH_ICON,
    //   isShow: false,
    //   loading: false
    // }
      customBtnPay: [
        {
          code: "VIEW",
          name: "查询",
          status: COMM_CONST.PRIMARY,
          icon: COMM_CONST.SEARCH_ICON,
          isShow: true
        },
        {
          code: "RGPP",
          name: "人工匹配",
          status: COMM_CONST.PRIMARY,
          icon: COMM_CONST.SEARCH_ICON,
          isShow: false
        },
        {
          code: "HYCX",
          name: "还原冲销",
          status: COMM_CONST.PRIMARY,
          icon: COMM_CONST.SEARCH_ICON,
          isShow: false
        },
        {
          code: "PZMX",
          name: "凭证明细",
          status: COMM_CONST.PRIMARY,
          icon: COMM_CONST.SEARCH_ICON,
          isShow: false,
          loading: false
        }
      ],
      // ,
      // {
      //   code: "WLMX",
      //   name: "往来明细",
      //   status: COMM_CONST.PRIMARY,
      //   icon: COMM_CONST.SEARCH_ICON,
      //   isShow: false,
      //   loading: false
      // }
      page: {
        offset: 0,
        limit: 50
      }
    };
  },

  mounted() {
    this.tableTile = this.type == this.WRITEOFF ? "冲销记录" : "挂账记录";
    this.customBtn =
      this.type == this.WRITEOFF ? this.customBtnWrite : this.customBtnPay;
    this.initTable();
  },

  methods: {
    checkboxAll({ checked }) {
      if (checked) {
        this.customBtn[1].isShow = false;
        this.customBtn[2].isShow = false;
        this.customBtn[3].isShow = false;
        this.customBtn[4].isShow = false;
        this.customBtn[5].isShow = false;
      }
    },
    // 初始化表格
    initTable() {
      // 初始化表头
      this.$refs.commGrid.setColumns(
        !this.isInner
          ? [
              {
                type: "checkbox",
                minWidth: 80
              },
              {
                field: "accountdate",
                title: "记账日期",
                showOverflow: true,
                minWidth: 115
              },
              {
                field: "varabstract",
                title: "摘要",
                showOverflow: true,
                minWidth: 115
              },
              {
                field: "accountledgertype",
                title: "分户类别",
                showOverflow: true,
                minWidth: 115
              },
              {
                field: "accountledger",
                title: "分户",
                showOverflow: true,
                minWidth: 115
              },
              {
                field: "offsetmoney",
                title: "付款金额",
                showOverflow: true,
                minWidth: 115
              },
              {
                field: "yetmoney",
                title: "已冲金额",
                showOverflow: true,
                minWidth: 115
              },
              {
                field: "remainmoney",
                title: this.type == this.WRITEOFF ? "未冲金额" : "挂账余额",
                showOverflow: true,
                minWidth: 115
              },
              {
                field: "isrelate",
                title: "关联凭证",
                showOverflow: true,
                minWidth: 115
              },
              {
                field: "intvouchernum",
                title: "凭证编号",
                showOverflow: true,
                minWidth: 115
              },
              {
                field: "accountcode",
                title: "科目",
                showOverflow: true,
                minWidth: 115
              },
              {
                field: "accountuser",
                title: "制证人",
                showOverflow: true,
                minWidth: 115
              }
            ]
          : [
              {
                type: "radio",
                minWidth: 80
              },
              {
                field: "accountdate",
                title: this.type == this.WRITEOFF ? "付款日期" : "挂账日期",
                showOverflow: true,
                minWidth: 115
              },
              {
                field: "varabstract",
                title: "摘要",
                showOverflow: true,
                minWidth: 115
              },
              {
                field: "accountledgertype",
                title: "分户类别",
                showOverflow: true,
                minWidth: 115
              },
              {
                field: "accountledger",
                title: "分户",
                showOverflow: true,
                minWidth: 115
              },
              {
                field: "offsetmoney",
                title: "付款金额",
                showOverflow: true,
                minWidth: 115
              },
              {
                field: "yetmoney",
                title: "已冲金额",
                showOverflow: true,
                minWidth: 115
              },
              {
                field: "currentrushmoney",
                title: "本次冲销金额",
                editRender: { name: "input" },
                slots: { edit: "currentrushmoney" },
                showOverflow: true,
                minWidth: 115
              },
              {
                field: "remainmoney",
                title: "余额",
                showOverflow: true,
                minWidth: 115
              },
              {
                field: "isrelate",
                title: "关联凭证",
                showOverflow: true,
                minWidth: 115
              },
              {
                field: "intcompanyseq",
                title: "流水号",
                showOverflow: true,
                minWidth: 115
              },
              {
                field: "intvouchernum",
                title: "凭证编号",
                showOverflow: true,
                minWidth: 115
              },
              {
                field: "accountcode",
                title: "科目",
                showOverflow: true,
                minWidth: 115
              },
              {
                field: "accountuser",
                title: "制证人",
                showOverflow: true,
                minWidth: 115
              }
            ]
      );
    },
    // 获取数据
    async getData() {
      let search = {
        ...this.params,
        ...this.page
      };
      this.$refs.commGrid.loading(true);

      let responseData =
        this.type == this.WRITEOFF
          ? await getOffsetData(search)
          : await getOnAccountData(search);

      if (responseData.code == 100) {
        this.$refs.commGrid.setData(responseData.data.rows);
        this.$refs.commGrid.setTotal(responseData.data.total);
        this.$refs.commGrid.loading(false);
      } else {
        this.showMessage(responseData);
      }
    },
    ellClick() {
      let data = this.$refs.commGrid.getData();
      if (!this.isInner) {
        let dataSize = data.length;
        // 通过校验控制按钮组的变化
        let row = this.$refs.commGrid.getData()[0];
        if (this.type == this.WRITEOFF) {
          this.customBtn[1].isShow = dataSize > 0;
          this.customBtn[2].isShow = dataSize == 1 && row.remainmoney > 0;
          this.customBtn[3].isShow = dataSize > 0;
          this.customBtn[4].isShow = dataSize == 1;
          this.customBtn[5].isShow = dataSize == 1;
        } else {
          this.customBtn[1].isShow = dataSize == 1 && row.remainmoney > 0;
          this.customBtn[2].isShow = dataSize > 0;
          this.customBtn[3].isShow = dataSize == 1;
          this.customBtn[4].isShow = dataSize == 1;
        }
      } else {
        this.customBtn[this.type == this.WRITEOFF ? 4 : 3].isShow =
          data != null;
      }
    },
    // 工具栏操作
    btnOperate(code) {
      // 查询
      if (code == "VIEW") {
        this.restPage();
        this.getData();
      }
      // 凭证明细
      else if (code == "PZMX") {
        this.certShow = true;
        let data = !this.isInner
          ? this.$refs.commGrid.getData()[0]
          : this.$refs.commGrid.getData();
        this.customBtn[this.type == this.WRITEOFF ? 3 : 2].loading = true;

        this.$nextTick(_ => {
          getTglVoucherMainsById(data.voucherid).then(res => {
            this.$refs.addCertificateDialog.onView(res.data);
            this.customBtn[this.type == this.WRITEOFF ? 3 : 2].loading = false;
          });
        });
      }
      this.$emit("onBtnClick", code);
    },
    getTableData() {
      return this.$refs.commGrid.getData();
    },
    calculation() {
      let allData = this.$refs.commGrid.vxeMethods("getTableData");
      let { tableData } = allData;
      let data = [];
      let res = 0;
      tableData.forEach(element => {
        if (!validatenull(element.currentrushmoney)) {
          res += Number(element.currentrushmoney);
          data.push(element);
        }
      });
      return { res: res, data: data };
    },
    // 分页
    pageChange(page) {
      this.page.offset = page.offset;
      this.page.limit = page.limit;
      this.getData();
    },
    restPage() {
      this.page = {
        offset: 0,
        limit: 50
      };
    }
  }
};
</script>
