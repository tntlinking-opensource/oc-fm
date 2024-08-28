<template>
  <div>
    <el-dialog
      title="选择摘要"
      :visible.sync="dialogVisible"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :destroy-on-close="true"
      append-to-body
      width="60%"
    >
      <vxe-grid
        ref="xGrid"
        v-bind="gridOptions"
        footerRowClassName="footer-class"
      >
        <template #toolbar_buttons>
          <vxe-input
            v-model="abstract"
            placeholder="搜索"
            type="search"
            v-on:search-click="searchClick"
          ></vxe-input>
          <vxe-button status="success" icon="vxe-icon-add" @click="insertEvent"
            >添加摘要</vxe-button
          >
          <vxe-button
            status="danger"
            icon="vxe-icon-delete"
            @click="removeRowEvent"
            >删除</vxe-button
          >
          <vxe-button status="primary" icon="vxe-icon-save" @click="saveEvent"
            >保存</vxe-button
          >
        </template>
        <template #toolbar_input> </template>
        <template #varabstract_edit="{ row }">
          <vxe-input v-model="row.varabastract"></vxe-input>
        </template>
        <template #pager>
          <vxe-pager
            :layouts="[
              'Sizes',
              'PrevJump',
              'PrevPage',
              'Number',
              'NextPage',
              'NextJump',
              'FullJump',
              'Total'
            ]"
            :current-page.sync="search.currentPage"
            :page-size.sync="search.limit"
            :total="search.total"
            v-on:page-change="handlePageChange"
          >
          </vxe-pager>
        </template>
      </vxe-grid>
      <span slot="footer" class="dialog-footer">
        <el-button @click="close">关 闭</el-button>
        <el-button type="primary" @click="onGetData">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  getAbstract,
  addAbstract,
  delAbstract
} from "@/api/components/certificate/certificate";

export default {
  data() {
    return {
      dialogVisible: false,
      abstract: "",
      search: {
        currentPage: 1,
        offset: 0,
        limit: 20,
        total: 0
      },
      // vxe-table配置
      gridOptions: {
        border: true,
        resizable: true,
        keepSource: true,
        showOverflow: true,
        loading: false,
        maxHeight: 500,
        id: "toolbar_demo_2",
        keyboardConfig: {
          isEsc: true,
          isEnter: true
        },
        rowConfig: {
          isCurrent: true
        },
        editConfig: {
          trigger: "click",
          mode: "row",
          showStatus: true,
          autoClear: true,
          activeMethod: this.activeRowMethod
        },
        customConfig: {
          storage: true
        },
        toolbarConfig: {
          custom: true,
          slots: {
            tools: "toolbar_input",
            buttons: "toolbar_buttons"
          }
        },
        columns: [
          {
            field: "varabastract",
            title: "摘要",
            editRender: { autofocus: ".vxe-input--inner" },
            slots: { edit: "varabstract_edit" },
            headerAlign: "center",
            footerAlign: "center"
          }
        ],
        data: []
      }
    };
  },
  methods: {
    // 打开弹窗
    open(title, id = "") {
      this.dialogVisible = true;
      this.loadData();
    },
    close() {
      this.dialogVisible = false;
    },
    // 数据加载
    loadData() {
      this.gridOptions.loading = true;
      getAbstract(this.abstract, this.search.offset, this.search.limit).then(
        res => {
          this.gridOptions.data = res.data.rows;
          this.search.total = res.data.total;
          this.gridOptions.loading = false;
        }
      );
    },
    // 插入数据/静态
    insertEvent() {
      const $grid = this.$refs.xGrid;
      $grid.insert({});
    },
    // 禁用非新增行
    activeRowMethod({ row }) {
      return !row.id;
    },
    // 保存
    async saveEvent() {
      const insertData = this.$refs.xGrid.getRecordset().insertRecords;
      if (insertData.length <= 0) {
        this.$message({
          message: "请先新增摘要",
          type: "warning"
        });
        return;
      }
      let bol;
      for (let i = 0; i < insertData.length; i++) {
        bol = await addAbstract(insertData[i].varabastract);
      }
      if (bol.type) {
        this.loadData();
      }
    },
    // 删除
    removeRowEvent() {
      let temp = this.$refs.xGrid.getCurrentRecord();
      if (temp == null) {
        this.$message({
          message: "请选中你需要删除的数据",
          type: "warning"
        });
        return;
      }
      this.$confirm("确定删除吗？", "确认", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(_ => {
        this.$refs.xGrid.removeCurrentRow();
        if (!!temp.id) {
          delAbstract(temp.id).then(res => {
            if (res.type) {
              this.$message.success(res.msg);
            }
          });
        }
      });
    },
    // 搜索
    searchClick() {
      this.search = {
        currentPage: 1,
        offset: 0,
        limit: 20,
        total: 0
      };
      this.loadData();
    },
    // 分页
    handlePageChange({ currentPage, pageSize }) {
      this.search.currentPage = currentPage;
      this.search.offset = (currentPage - 1) * pageSize;
      this.search.limit = pageSize;
      this.loadData();
    },
    onGetData() {
      let temp = this.$refs.xGrid.getCurrentRecord();
      if (temp == null) {
        this.$message({
          message: "请选择摘要",
          type: "warning"
        });
        return;
      }
      this.close();
      this.$emit("onGetData", temp);
    }
  }
};
</script>
