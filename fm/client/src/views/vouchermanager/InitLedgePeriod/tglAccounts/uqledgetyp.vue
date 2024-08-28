<template>
  <el-dialog
    title="选择分户"
    :visible.sync="dialogVisible"
    v-on:open="onDialogOpen"
    :close-on-click-modal="false"
  >
    <el-container
      direction="horizontal"
      class="dc-el-container_ElContainer"
      v-loading="loading"
    >
      <el-main>
        <div class="content">
          <el-row>
            <ux-grid
              column-key
              ref="table"
              :data="tableData"
              max-height="500"
              border
              :checkbox-config="{
                trigger: 'row'
              }"
              class="dc-ux-grid_QueryTable"
            >
              <ux-table-column type="checkbox" width="50" align="center" />
              <ux-table-column
                v-for="(item, index) in tableColumns"
                :key="index"
                :field="item.field"
                :title="item.title"
                :width="item.width"
                :visible="item.visible"
                :align="item.align"
                tree-node
                resizable
                min-width="180px"
                header-align="center"
                show-overflow
                class="dc-ux-table-column_ElTableColumn"
              ></ux-table-column>
            </ux-grid>
          </el-row>
        </div>
      </el-main>
    </el-container>
    <span slot="footer" class="dialog-footer">
      <el-button @click="close">关 闭</el-button>
      <el-button type="primary" @click="onGetData">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import DcMain from "@/views/components/dcMain";

import { listTglLedgetypeAll } from "@/api/vouchermanager/tglLedgetype";

export default {
  extends: DcMain,
  data() {
    return {
      dialogVisible: false,
      tableData: [],
      tableColumns: [
        {
          title: "分户",
          field: "varledgetypename",
          visible: true,
          order: 0
        }
      ],
      search: {}
    };
  },

  methods: {
    onDialogOpen() {
      this.getList();
    },
    open() {
      this.dialogVisible = true;
    },
    close() {
      this.dialogVisible = false;
    },
    getList() {
      this.setLoad();
      this.search.params = [];
      listTglLedgetypeAll(this.search)
        .then(responseData => {
          if (responseData.code == 100) {
            let ary = responseData.data;
            this.tableData = ary ? ary : [];
          } else {
            this.showMessage(responseData);
          }
          this.resetLoad();
        })
        .catch(error => {
          this.outputError(error);
        });
    },
    onGetData() {
      this.$emit("onGetData",  this.$refs.table.getCheckboxRecords());
    }
  }
};
</script>

<style lang="scss" scoped>
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
