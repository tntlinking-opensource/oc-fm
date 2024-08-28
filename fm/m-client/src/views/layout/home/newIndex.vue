<template>
  <div class="container">
    <div class="content_box">
      <!-- 轮播图 -->
      <div class="banner_box">
        <van-swipe
          class="my-swipe"
          :autoplay="3000"
          :show-indicators="false"
          indicator-color="white"
        >
          <van-swipe-item v-for="(banner, index) in bannerList" :key="index">
            <van-image
              fit="cover"
              :src="require('@/assets/images/' + banner)"
            />
          </van-swipe-item>
        </van-swipe>
        <span v-if="userInfo" class="hello_text"
          >{{ getCurTime }}，{{ userInfo.name | filterName }}！</span
        >
        <span v-if="userInfo" class="company_text">{{
          userInfo.company.name
        }}</span>
        <van-image
          class="top-bg"
          fit="cover"
          :src="require('@/assets/images/top_bg.png')"
        />
        <div class="swipe-bg"></div>
      </div>

      <!-- 天气温度提示语 -->
      <div class="tips_text">
        <div class="tip">
          <van-icon name="volume-o" size="16" />
        </div>
        <van-swipe
          :touchable="false"
          class="my-swipe1"
          :show-indicators="false"
          indicator-color="white"
          :autoplay="3000"
          vertical
        >
          <van-swipe-item>空气质量：优</van-swipe-item>
          <van-swipe-item>天气晴朗有时有阵雨</van-swipe-item>
          <van-swipe-item>今日温度：30℃~34℃</van-swipe-item>
        </van-swipe>
      </div>

      <!-- 我的功能 -->
      <div :class="item.class" v-for="(item, index) in menuList" :key="index">
        <!-- 功能标题 -->
        <div class="options_title">
          <div class="left_icon2"></div>
          <span>{{ item.name }}</span>
        </div>

        <!-- 功能按钮 -->
        <div v-if="item.type === 'van'">
          <van-row
            class="option_row"
            type="flex"
            v-for="index in item.rows"
            :key="index"
          >
            <van-col
              span="6"
              v-for="route in item.menus.slice(
                (index - 1) * 4,
                (index - 1) * 4 + 4
              )"
              :key="route.id"
            >
              <div @click="routeTo(route)" class="item">
                <div :style="{ 'background-color': route.color }">
                  <van-icon
                    v-if="route.code === 'more'"
                    :name="route.icon"
                    size=".8rem"
                    color="#a8a8aa"
                  />

                  <van-badge
                    :content="0"
                    :name="route.code"
                    v-else-if="dotList.includes(route.code)"
                  >
                    <van-icon
                      class-prefix="options-icon"
                      :name="route.icon"
                      size=".6rem"
                      color="#fff"
                    />
                  </van-badge>
                  <van-icon
                    v-else
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

        <ul v-if="item.type === 'ul'" class="move_office_list">
          <li
            class="move_office_item"
            v-for="(route, index) in item.menus"
            :key="index"
            :style="{ background: route.color }"
          >
            <div class="move_office_title">
              {{ route.name }}
            </div>
            <div class="move_office_todo"></div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>
<script>
import "@/assets/iconfont/iconfont.css";
import { getLocalCurrentUser, getLocalRouters } from "@/utils/auth";
import BaseUI from "@/views/components/baseUI";

export default {
  extends: BaseUI,
  data() {
    return {
      bannerList: ["banner2.jpg", "banner1.jpg"],
      images: ["banner1.jpg", "banner2.jpg"],
      menuList: [],
      userInfo: getLocalCurrentUser(),
      hours: new Date().getHours(),
      dotList: ["baoshibaoxiu"],
    };
  },
  created() {},
  mounted() {
    this.initMenu();
  },
  watch: {},
  methods: {
    getTreeList(routers) {
      // 组装树结构
      const treeList = [];
      const map = {};
      routers.forEach((item) => {
        if (!item.children) {
          item.children = [];
        }
        map[item.id] = item;
      });

      routers.forEach((item) => {
        const parent = map[item.parent.id];
        if (parent) {
          // 如果存在 说明 item 不是最顶层
          parent.children.push(item);
        } else {
          // 如果不存在 说明 item 是最顶层
          treeList.push(item);
        }
      });

      return treeList;
    },
    getCataRouters(routers) {
      let catalogues = [];
      let routeId = this.$route.meta.routerId;
      for (let i = 0; i < routers.length; i++) {
        let router = routers[i];
        if (router.parent.id === routeId) {
          catalogues.push(router);
        }
      }
      return catalogues;
    },
    getChildRouters(routers, cata) {
      let childRouters = [];
      if (!cata) return childRouters;
      for (let i = 0; i < routers.length; i++) {
        let router = routers[i];
        if (router.parent.id === cata.id) {
          childRouters.push(router);
        }
      }
      return childRouters;
    },
    initMenu() {
      const routers = getLocalRouters();
      console.log("路由", routers);

      if (routers && routers.length > 0) {
        // 获取目录
        let catalogues = this.getCataRouters(routers);

        for (let i = 0; i < catalogues.length; i++) {
          let router = catalogues[i];
          let properties = JSON.parse(router.properties);
          let thirdRouters = [];
          let count = 0;

          // 获取子路由
          let childRouters = this.getChildRouters(routers, router);

          let childLength = childRouters.length;
          if (childRouters.length > 7 && properties.type === "van") {
            childLength = 8;
          }

          for (let i = 0; i < childLength; i++) {
            let childRouter = childRouters[i];
            let childProperties = JSON.parse(childRouter.properties);
            count++;
            if (properties.type === "van" && i < 7) {
              thirdRouters.push({
                name: childRouter.name,
                code: childRouter.code,
                icon: childProperties.icon,
                color: childProperties.color,
                id: childRouter.id,
                url: childRouter.url,
                iconId: childRouter.iconId,
              });
            } else if (properties.type === "van" && i === 7) {
              thirdRouters.push({
                name: "更多",
                code: "more",
                icon: "apps-o",
                color: "",
                url: "/more",
                id: router.id,
              });
            } else if (properties.type === "ul") {
              thirdRouters.push({
                name: childRouter.name,
                code: childRouter.code,
                icon: childProperties.icon,
                color: childProperties.color,
                id: childRouter.id,
                url: childRouter.url,
                iconId: childRouter.iconId,
              });
            }
          }
          // 全部目录及菜单
          this.menuList.push({
            name: router.name,
            id: router.id,
            url: router.url,
            sort: router.sort,
            type: properties.type,
            code: router.code,
            class: properties.cssClass,
            menus: thirdRouters,
            rows: Math.ceil(count / 4), // 行数
          });
        }
      }
    },
    routeTo(route) {
      if (route.code === "baoshibaoxiu") {
        if (false) {
          this.$router.push({ path: "/bsbx/record" });
        } else {
          this.$router.push({ path: route.url });
        }
      } else if (route.code === "more") {
        this.$router.push({ path: route.url, query: { parentId: route.id } });
      } else {
        this.$router.push({ path: route.url }).catch();
      }
    },
  },
  computed: {
    getCurTime() {
      if (0 <= this.hours && this.hours < 11) {
        return "早上好";
      } else if (11 <= this.hours && this.hours < 14) {
        return "中午好";
      } else if (14 <= this.hours && this.hours < 18) {
        return "下午好";
      } else if (18 <= this.hours && this.hours <= 23) {
        return "晚上好";
      }
    },
  },
  filters: {
    filterName(val) {
      if (val.length < 3) return val;
      return val.slice(1, val.length);
    },
  },
};
</script>
<style lang="scss" scoped>
.container {
  height: 100%;
  padding-bottom: 50px;
  .banner_box {
    position: relative;
  }
  .my-swipe {
    height: 200px;
    .van-swipe-item {
      color: #fff;
      font-size: 20px;
      line-height: 200px;
      text-align: center;
      background-color: #d8bb7d;
    }
  }
  .top-bg {
    position: absolute !important;
    bottom: -5px;
    z-index: 2;
  }
  .swipe-bg {
    position: absolute;
    width: 100%;
    height: 100px;
    bottom: -4px;
    background: linear-gradient(rgba(0, 0, 0, 0) 0%, rgba(0, 0, 0, 0.3) 99%);
  }
  .my-swipe1 {
    width: 100%;
    height: 30px;
    box-sizing: border-box;
    border-radius: 50px;
    background-color: #d9e8f6;
    padding: 0 0 0 40px;
    /deep/ .van-swipe__track {
      border: none;
    }
    /deep/ .van-swipe-item {
      color: #0079ee;
      font-size: 12px;
      line-height: 30px;
      text-align: left;
      border: none;
    }
  }
  .hello_text {
    position: absolute;
    bottom: 20%;
    left: 20px;
    font-size: 28px;
    font-weight: bold;
    color: #fff;
    z-index: 3;
  }
  .company_text {
    position: absolute;
    bottom: 10%;
    left: 20px;
    color: #fff;
    z-index: 3;
  }
  .tips_text {
    position: relative;
    padding: 0 20px;
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    margin: 20px 0 10px 0;
    .tip {
      position: absolute;
      left: 30px;
      color: #0079ee;
      z-index: 2;
      .van-image {
        height: 50px;
      }
    }
  }
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
  .my_options {
    position: relative;
    /*&:before {
      position: absolute;
      content: '';
      height: 30px;
      left: 0;
      right: 0;
      top: -15px;
      border-radius: 15px 15px 0 0;
      background: #f6f8fb;
    }*/
    .item {
      height: 100%;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      div {
        width: 45px;
        height: 45px;
        border-radius: 40%;
        display: flex;
        align-items: center;
        justify-content: center;
        box-shadow: 0 1px 10px 2px rgba(0, 0, 0, 0.12);
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
      .van-image {
        width: 100%;
        height: 90px;
      }
    }
    > div:nth-of-type(2) {
      > .option_row:nth-child(1) {
        > .van-col:nth-child(3) {
          .options-icon {
            font-size: 1rem !important;
          }
        }
      }
    }
  }
  .move_office {
    .move_office_list {
      display: flex;
      overflow-x: scroll;
      margin: 20px 0;
      .move_office_item {
        font-size: 14px;
        padding: 10px 10px 0;
        width: 90px;
        height: 114px;
        border-radius: 10px;
        flex: none;
        display: flex;
        flex-direction: column;
        align-items: center;
        .move_office_title {
          font-weight: bold;
        }
        .move_office_todo {
          font-size: 12px;
          text-overflow: ellipsis;
          overflow: hidden;
          white-space: nowrap;
        }
      }
      .move_office_item:nth-child(n + 2) {
        margin-left: 10px;
      }
    }
  }
  .my_options,
  .move_office {
    padding: 0 20px;
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
