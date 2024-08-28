<template>
  <el-dialog
    title="选择分户"
    v-on:open="onDialogOpen"
    :append-to-body="appendToBody"
    :visible.sync="dialogVisible"
    width="30%"
    :close-on-click-modal="false"
  >
    <el-row v-loading="loading">
      <el-tree
        ref="treeData"
        :data="tableData"
        node-key="uqledgeid"
        :expand-on-click-node="false"
        :props="{ label: initLabel, children: 'tglLedgerList' }"
        :show-checkbox="checkType == 'checkbox'"
        @node-click="onRowClick">
      </el-tree>
    </el-row>
    <span slot="footer" class="dialog-footer">
          <el-button @click="close">关 闭</el-button>
          <el-button type="primary" @click="onGetData">确 定</el-button>
        </span>
  </el-dialog>
</template>

<script>
import DcMain from "@/views/components/dcMain";

import { validatenull } from "@/utils/validate";

import { listLedgetypeAndChild } from '@/api/vouchermanager/tglLedgetype'

/**
 * @description 分户选择通用组件（只选中子级）
 *
 * 属性
 * @prop {String} checkType - 选中类型  radio/checkbox  默认:checkbox
 * @prop {Boolean} appendToBody - 是否插入Body，用于多层dialog中
 * @prop {String} uqaccountid - 科目参数
 *
 * 事件
 * @event {Function} onGetData - 获取数据，compType为dialog时通过事件获取 { tree:Obejct,tableData: Obejct / Array }
 *
 * 方法
 * @method {Function} open - 打开
 * @method {Function} onGetData - 获取数据 { tree:Obejct,tableData: Obejct / Array }
 */
export default {
  extends: DcMain,
  props: {
    checkType: {
      type: String,
      default: "checkbox"
    },
    appendToBody:{
      type:Boolean,
      default:false,
    },
    uqaccountid: {
      type: String,
      default: null
    }
  },
  data() {
    return {
      dialogVisible: false,
      radioData: {},
      checkBoxData: [],
      tableData: []
    };
  },
  methods: {
    onDialogOpen() {},
    open(data) {
      this.$nextTick(() => {
        if (!validatenull(this.checkType) && this.checkType == 'checkbox') {
          this.$refs.treeData.setCheckedKeys([]);
          if (!validatenull(data)) {
            // 将后端接口数据回显到树上的方法
            this.$refs.treeData.setCheckedKeys(data)
          }
        }
      })
      this.dialogVisible = true
      this.getList()
    },
    close() {
      this.dialogVisible = false;
    },
    getList() {
      this.setLoad();
      let search_List = {
        params: [
          { columnName: "account.UQACCOUNTID", queryType: "=", value: !validatenull(this.uqaccountid) ? this.uqaccountid : null },
          { columnName: "tenant_id", queryType: "=", value: currentUser.company.id != '0' ? currentUser.company.id : null }
        ]
      }
      listLedgetypeAndChild(search_List).then(responseData => {
        if (responseData.code == 100) {
          responseData.data.forEach(item => {
            if (validatenull(item.tglLedgerList)) {
              this.$set(item, 'disabled', true)
            }
          })
          this.tableData = responseData.data
        } else {
          this.showMessage(responseData);
        }
        this.resetLoad();
      }).catch(error => {
        this.outputError(error);
      });
    },
    initLabel(a, b) {
      return a.code ? '[' + a.code  + ']' + a.name : a.name
    },
    // 单选
    onRowClick(data, node) {
      console.log(data,node);
      this.radioData = data;
    },
    onGetData() {
      let back = {}
      // radio 单选
      if (!validatenull(this.checkType) && this.checkType == 'radio') {
        if (validatenull(this.radioData)) {
          this.showMessage({
            type: "warning",
            msg: "请选择分户"
          });
          return false;
        }
        if (this.radioData.hasOwnProperty('tglLedgerList')) { // 判断选择的是类型，还是分户
          back = {
            ledgetypeid: this.radioData.id,
            ledgetypename: this.radioData.name,
          }
        } else {
          let ledgerList = this.radioData.tglLedgerList
          back = {
            ledgerid: this.radioData.uqledgeid,
            ledgetypename: this.radioData.code ? '[' + this.radioData.code + ']' + this.radioData.name : this.radioData.name
          }
        }
      } else { // 多选
        let listData = this.$refs['treeData'].getCheckedNodes() // 选中的值对象
        if (validatenull(listData)) {
          this.showMessage({
            type: "warning",
            msg: "请选择分户"
          });
          return false;
        }
        let parentIds = listData.map(item =>{
          if (item.hasOwnProperty('intislastlevel')) {
            return item.uqledgetypeid
          }
        }).filter(item => { return !validatenull(item) })
        
        let arrName = listData.map(item => {
          if (item.hasOwnProperty('uqledgeid')) {
            return item.code ? '[' + item.code + ']' + item.name : item.name
          }
        }).filter(item => { return !validatenull(item) })
        
        let arrData = listData.map(item => {
          if (item.hasOwnProperty('uqledgeid')) {
            return item.uqledgeid
          }
        }).filter(item => { return !validatenull(item) })
        back = {
          parentIds:parentIds,
          arrName: arrName,
          arrData: arrData
        }
      }
      this.$emit("onGetTreeData", back);
      this.dialogVisible = false;
    }
  }
};
</script>

<style lang="scss" scoped>
/deep/ .plTableBox {
  .col--tree-node:not(:first-child) {
    .elx-cell--tree-node {
      padding-left: 0 !important;
      .elx-tree--btn-wrapper {
        display: none;
      }
      .elx-tree-cell {
        padding-left: 0;
      }
    }
  }
}
.left {
  height: 535px;
  overflow: auto;
}

.content {
  padding: 0 0 0 10px;
  &-search {
    width: 270px;
    padding: 0 0 10px 0;
  }
}
</style>
