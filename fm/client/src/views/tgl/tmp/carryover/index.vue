<template>
  <el-row class="dc-container" v-loading="loading">
    <div class="search">
      <div class="mr-10">
        <el-select
          style="width: 100%"
          value-key="uqaccountid"
          v-model="editFormData.uqglobalperiodid"
          placeholder="请选择会计期"
        >
          <el-option
            v-for="(item, index) in uqglobalperiodidOptions"
            :key="index"
            :label="item.varname"
            :value="item.uqglobalperiodid"
          ></el-option>
        </el-select>
      </div>
      <div>
        <el-select
          style="width: 100%"
          value-key="uqaccountid"
          v-model="editFormData.bulkid"
          placeholder="请选择本年利润科目"
          clearable
          filterable
        >
          <el-option
            v-for="item in accountOptions"
            :key="item.uqaccountid"
            :label="
              item.varaccountcode
                ? '[' + item.varaccountcode + ']' + item.varaccountname
                : item.varaccountname
            "
            :value="item.uqaccountid"
          >
          </el-option>
        </el-select>
      </div>

      <div class="search-btn">
        <el-button
          v-on:click="onSubmit"
          icon="el-icon-document-checked"
          type="success"
          >生成结转凭证</el-button
        >
      </div>
    </div>
    <AccountQuery ref="accountQuery" @onGetData="getCheckData"></AccountQuery>
  </el-row>
</template>
<script>
import { validatenull } from "@/utils/validate";
import DcMain from "@/views/components/dcMain";
import AccountQuery from "@/views/components/AccountingAccountQuery";
import { listResourcePermission } from "@/api/admin/common/permission";
import { listTglAccountsAll } from "@/api/vouchermanager/InitLedgePeriod/tglAccounts.js";
import { saveTmpIdlist } from "@/api/tgl/tmp/tmpIdlist_account";
import { selectPeriod } from "@/api/tgl/global/globalPeriods";
import { listTmpIdlistAll } from "@/api/tgl/tmp/tmpIdlist_account";
import { autoconvert } from "@/api/vouchermanager/tglVoucherMains";
export default {
  extends: DcMain,
  components: {
    AccountQuery
  },
  data() {
    return {
      search: {
        // 查询条件   业务表设置的筛选条件
        params: [],

        offset: 0,
        limit: 10,
        columnName: "", // 排序字段名
        order: "" // 排序
      },
      permission: {
        view: false,
        add: false,
        edit: false,
        remove: false,
        export: false
      },
      editFormData: this.initEditData(),
      accountOptions: [], // 科目列表
      uqglobalperiodidOptions: []
    };
  },
  computed: {},
  methods: {
    onSubmit() {
      if (
        !validatenull(this.editFormData.bulkid) &&
        !validatenull(this.editFormData.uqglobalperiodid)
      ) {
        this.doSave();
      }
    },
    doSave() {
      this.$confirm("将进行自动结转凭证，是否继续?", "确认", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          let uqglobalperiodid = this.editFormData.uqglobalperiodid;
          let profitaccount = this.editFormData.bulkid;
          var obj = {
            profitaccount: profitaccount,
            uqglobalperiodid: uqglobalperiodid
          };
          this.setLoad();
          autoconvert(obj)
            .then(responseData => {
              if (responseData.code == 100) {
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
        .catch(() => {});
    },
    getCheckData(data) {
      if (!validatenull(data.tableData)) {
        this.editFormData.bulkid = data.tableData.uqaccountid;
      }
    },
    onSearchAccount() {
      this.$refs.accountQuery.open();
    },
    initEditData() {
      return {
        id: "PROFITACCOUNT", // 标识
        typename: "记录科目ID", // 类型
        tenantId: currentUser.company.id, // 公司
        bulkid: null, // 科目
        uqglobalperiodid: ""
      };
    },
    async pageInit() {
      this.setLoad();
      try {
        this.initOptions(this.queryModel);
        this.search.params = [];
        // 数据权限: 利润结转科目配置tgl_tmp_idlist
        let [listPermissionRespData] = Promise.all([
          listResourcePermission(this.$route.meta.routerId)
        ]);
        if (listPermissionRespData.code == 100) {
          this.permission.view = listPermissionRespData.data.find(item => {
            return item.permission === "globalPeriods:read";
          });
          this.permission.export = listPermissionRespData.data.find(item => {
            return item.permission === "globalPeriods:export";
          });

          this.permission.add = listPermissionRespData.data.find(item => {
            return item.permission === "globalPeriods:create";
          });
          this.permission.edit = listPermissionRespData.data.find(item => {
            return item.permission === "globalPeriods:update";
          });
          this.permission.remove = listPermissionRespData.data.find(item => {
            return item.permission === "globalPeriods:delete";
          });
        } else {
          this.showMessage(listPermissionRespData);
        }
        this.resetLoad();
      } catch (error) {
        this.outputError(error);
      }
    },
    // 初始化自定义类型选择框选项变量
    initOptions(This) {
      selectPeriod({
        params: []
      }).then(responseData => {
        this.uqglobalperiodidOptions = responseData.data;
        this.editFormData.uqglobalperiodid = this.uqglobalperiodidOptions[0].uqglobalperiodid;
        this.search.params.push({
          columnName: "id",
          queryType: "=",
          value: "PROFITACCOUNT",
          logic: "AND"
        });
        listTmpIdlistAll(this.search)
          .then(responseData => {
            if (responseData.code == 100) {
              console.log(responseData.data);
              if (responseData.data != null) {
                this.editFormData.bulkid = responseData.data[0].bulkid;
              }
            } else {
              this.showMessage(responseData);
            }
            this.resetLoad();
          })
          .catch(error => {
            this.outputError(error);
          });
      });

      // 获取科目管理列表
      let search_List = {
        // params: [{ columnName: "INTFLAG", queryType: "=", value: "2" }]
        params: [],
        columnName: "varaccountfullcode",
        orderby: "asc"
      };
      // filter条件
      search_List.params.push.apply(search_List.params, []);
      // 数据权限: 会计科目表tgl_accounts
      this.pushDataPermissions(
        search_List.params,
        this.$route.meta.routerId,
        "1457254743943430348"
      );

      listTglAccountsAll(search_List).then(responseData => {
        if (responseData.code == 100) {
          this.accountOptions = responseData.data;
        } else {
          this.showMessage(responseData);
        }
      });
    }
  },
  watch: {},
  mounted() {
    this.initOptions();
  }
};
</script>
<style lang="scss" scoped>
.search {
  width: 600px;
  display: flex;
  &-btn {
    display: flex;
    margin-left: 10px;
  }
}
</style>
