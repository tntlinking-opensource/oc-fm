<template>
  <div class="content_box">
    <div class="all_options">
      <!-- 全部功能标题 -->
      <div class="options_title">
        <div class="left_icon2"></div>
        <span>全部功能</span>
      </div>
      <!-- 全部功能按钮 -->
      <div class="all_options_btn">
        <van-row
          class="option_row"
          type="flex"
          v-for="index in rows"
          :key="index"
        >
          <van-col span="6" v-for="route in menuList.slice((index - 1) * 4, (index - 1) * 4 + 4)" :key="route.id" >
            <div @click="routeTo(route.url)" class="item">
              <div :style="{ 'background-color': route.color }">
                <van-icon
                  class-prefix="options-icon"
                  :name="route.icon"
                  size=".6rem"
                  color="#fff"
                />
              </div>
              <span>{{ route.name }}</span>
            </div>
          </van-col>
        </van-row>
      </div>
    </div>
  </div>
</template>
<script>
import "@/assets/iconfont/iconfont.css";
import { getLocalRouters } from "@/utils/auth";

export default {
  data() {
    return {
      menuList: [],
      rows: 0,
      res: {},
    };
  },

  methods: {
    getChildRouters(routers, routeId) {
      let childRouters = []
      if (!routeId) return childRouters
      for (let i = 0; i < routers.length; i++) {
        let router = routers[i]
        if (router.parent.id === routeId) {
          childRouters.push(router)
        }
      }
      return childRouters
    },
    initMenu() {
      let routeId = this.$route.query.parentId;
      const routers = getLocalRouters()
      if (routers && routers.length > 0) {
        const currentRouters = this.getChildRouters(routers, routeId)
        let count = 0;
        for (var i = 0; i < currentRouters.length; i++) {
          let router = currentRouters[i];
          if (router.code === 'more') {
            continue;
          }
          let properties = JSON.parse(router.properties);
          count++;
          this.menuList.push({
            name: router.name,
            code: router.code,
            icon: properties.icon,
            color: properties.color,
            id: router.id,
            url: router.url,
            iconId: router.iconId,
          })
        }
        this.rows = Math.ceil(count / 4);
      }
    },
    routeTo(url) {
      this.$router.push({ path: url }).catch();
    },
  },
  created() {
    this.initMenu();
  },
  mounted() {},
};
</script>
<style lang="scss" scoped>
.content_box {
  height: 100vh;
  .options_title {
    display: flex;
    align-items: center;
    position: relative;
    overflow: hidden;
    padding: 10px 0 0 0;
    .left_icon1 {
      width: 30px;
      height: 30px;
      position: absolute;
      background-color: #0079ee;
      left: -18px;
      top: -10px;
      transform: rotate(30deg);
    }
    .left_icon2 {
      width: 4px;
      height: 14px;
      position: absolute;
      background-color: #0079ee;
      left: 0;
    }
    span {
      margin-left: 10px;
      font-size: 16px;
      color: #333;
      font-weight: bold;
    }
  }
  .all_options {
    height: 100%;
    overflow-y: scroll;
    padding: 0 20px;
    .item {
      height: 100%;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      div {
        width: 45px;
        height: 45px;
        border-radius: 100%;
        display: flex;
        align-items: center;
        justify-content: center;
        box-shadow: 0 1px 10px 2px rgba(0,0,0, .12);
      }
      span {
        margin-top: 10px;
        color: #333;
        font-size: 12px;
      }
    }
    .option_row {
      margin: 20px 0;
    }
    .announcement {
      height: 90px;
      border-radius: 8px;
      margin: 10px 0;
      img {
        width: 100%;
        height: 90px;
      }
    }
  }
  ::-webkit-scrollbar {
    width: 0 !important;
  }
  ::-webkit-scrollbar {
    width: 0 !important;
    height: 0;
  }
}
</style>
