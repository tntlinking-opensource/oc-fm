<template>
  <el-card
    class="index-module-card"
    :body-style="{
      display: 'flex',
      'justify-content': 'center',
      'align-items': 'center',
      height: '80%'
    }"
  >
    <div slot="header">
      <span class="el-card-title">新增凭证</span>
      <i
        style="margin-left: 10px;"
        v-show="!isPanelSetIcon"
        class="card-close el-icon-close"
        @click="deletePanelItem"
      ></i>
      <i
        style="margin-left: 10px;"
        v-show="isPanelSetIcon"
        class="card-setting iconfont icon-setting"
      ></i>
    </div>
    <div class="voucher-message-box" @click="onClick">
      <div class="app-icon">
        <i class="icon el-icon-plus" type="success"></i>
      </div>
      <span class="app-name">添加</span>
      <addCertificateDialog ref="addCertificateDialog"></addCertificateDialog>
    </div>
  </el-card>
</template>

<script>
import { validatenull } from "@/utils/validate";
import { mapActions } from "vuex";
import addCertificateDialog from "@/views/components/certificate/addCertificateDialog";
import { getRouterById } from "@/api/admin/router";
export default {
  props: ["id", "panelSetIcon"],
  name: "voucherMainsView",
  data() {
    return {
      router: {},
      isPanelSetIcon: this.panelSetIcon,
      routerId: "1494253464840634373" // 制证路由ID
    };
  },
  components: {
    addCertificateDialog
  },
  watch: {
    panelSetIcon: {
      deep: true,
      handler() {
        this.isPanelSetIcon = this.panelSetIcon;
      }
    }
  },
  methods: {
    onClick() {
      this.$nextTick(() => {
        this.$refs.addCertificateDialog.open("新增");
      });
    },
    ...mapActions({
      setDefaultOpeneds: "menus/setDefaultOpeneds"
    }),
    handleRouterLink(router) {
      if (!validatenull(router)) {
        const url = router.url;
        const properties = JSON.parse(router.properties);
        if (this.hasContainSpecial(url)) {
          return `/${url.split(":")[0]}${properties.params}`;
        } else {
          return `/${url}`;
        }
      }
      return "";
    },
    hasContainSpecial(url) {
      const specialStr = new RegExp(/:/g);
      return specialStr.test(url);
    },
    handleRouterDefault() {
      this.setDefaultOpeneds(this.$route.meta.family);
    },
    // 删除面板项发送事件
    deletePanelItem() {
      this.$emit("deletePanelItemEvent", this.panelId);
    }
  },
  mounted() {
    getRouterById(this.routerId)
      .then(responseData => {
        if (responseData.code == 100) {
          this.router = responseData.data;
        } else {
          this.showMessage(responseData);
        }
      })
      .catch(error => {
        this.outputError(error);
      });
  }
};
</script>

<style lang="scss" scoped>
.index-module-card.el-card /deep/ .el-card__header {
  padding: 10px 16px !important;
  .card-close,
  .card-setting {
    float: right;
    padding: 3px 0;
  }
}
.el-card {
  height: 100%;
}
.el-card-title {
  font-weight: bold;
}
.voucher-message-box {
  cursor: pointer;
  text-align: center;
  .app-icon {
    margin: 0 auto;
    position: relative;
    width: 50px;
    height: 50px;
    border: 3px solid #999;
    border-radius: 5px;
    display: flex;
    justify-content: center;
    align-items: center;
    .icon {
      font-size: 35px;
      color: #999;
    }
  }
  .app-name {
    padding: 10px 0;
    font-size: 14px;
    display: inline-block;
  }
  /deep/ .app-icon {
    transition: 0.5s;
  }
  .app-name {
    transition: 0.5s;
  }
  &:hover {
    /deep/ .app-icon {
      width: 60px;
      height: 60px;
      transition: 0.5s;
      .icon {
        font-size: 40px;
      }
    }
    .app-name {
      transition: 0.5s;
      color: #888;
    }
  }
}
</style>
