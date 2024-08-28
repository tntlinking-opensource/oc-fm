<template >
  <router-link
    class="link-a"
    :class="!isPanelSetIcon ? 'router':''"
    :to="handleRouterLink(router)"
    @click="handleRouterDefault"
  >
    <el-card class="index-module-card" :style="myStyle" >
      <!-- <i
        style="margin-left: 10px;"
        v-show="!isPanelSetIcon"
        class="card-close el-icon-close"
        @click="deletePanelItem"
      ></i> -->
      <span class="router-name">{{ appName }}</span>
    </el-card>
  </router-link>
</template>

<script>
// TODO: 修复编辑状态下不能被拖拽
import { validatenull } from "@/utils/validate";
import appIcon from "../appIcon";
import { mapActions } from "vuex";
import { getRouterById } from "@/api/admin/router";
export default {
  components: { appIcon },
  props: {
    id: {
      String,
      default: ""
    },
    panelSetIcon: {
      String,
      default: true
    },
    routerId: {
      String,
      default: ""
    },
    appName: {
      String,
      default: ""
    },
    borderColor: {
      String,
      default: "#67c23a"
    },
    // 蒙版颜色
    maskColor: {
      String,
      default: "#ffffff00"
    },
    // 背景图
    bgIcon: {
      String,
      default: "viewx.png"
    },
    bgSize: {
      String,
      default: "110px"
    }
  },
  watch: {
    panelSetIcon: {
      deep: true,
      handler() {
        this.isPanelSetIcon = this.panelSetIcon;
        this.$forceUpdate();
      }
    }
  },
  data() {
    return {
      router: {},
      panelId: this.id,
      isPanelSetIcon: this.panelSetIcon
    };
  },
  computed: {
    myStyle() {
      return {
        "--borderColor": this.borderColor,
        "--maskColor": this.maskColor,
        "--bgIcon": `url(${require("@/views/homepage/components/AppCard/bgIcon/" +
          this.bgIcon)})`,
        "--bgSize": this.bgSize,
        "--disPoint": !this.isPanelSetIcon ? "none" : "auto"
      };
    }
  },

  methods: {
    // 删除面板项发送事件
    deletePanelItem() {
      this.$emit("deletePanelItemEvent", this.panelId);
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
.test{
  pointer-events:none
}
.router{
  pointer-events:none

}
.link-a {
  text-decoration: none;
  display: block;
  height: 100%;
}
.index-module-card {
  box-sizing: border-box;
  height: 100%;
  cursor: pointer;
  background: var(--bgIcon) no-repeat;
  background-color: #fff;
  // background-position: -45px;
  background-position: 20px center;
  background-size: var(--bgSize);
  border-top: 4px solid var(--borderColor);
  transition: 0.6s;

  /*&:hover {*/
  /*  background-position: 0px;*/
  /*  transition: 0.6s;*/
  /*}*/
  /deep/ .el-card__body {
   // background-color: var(--maskColor);
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: end;
    align-items: center;
    box-sizing: border-box!important;
  }

  /deep/ .router-name {
    position: relative;
    left: 0px;
    font-size: 18px;
    font-weight: bold;
    // color: var(--borderColor);
    color: #555555;
    transition: 0.5s;
  }
  &:hover {
    /deep/ .router-name {
      transition: 0.6s;
      font-size: 20px;
    }
  }
}
</style>
