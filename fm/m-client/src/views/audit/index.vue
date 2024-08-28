<template>
  <div class="container">
    <headerItem :isShowLeftIcon="true" :title="'资产盘点'" />
    <div v-if="listData.length > 0" class="content_box">
      <div
        v-for="(item, index) in listData"
        :key="index"
        class="item-box"
        @click="openCheckItem(item.id)"
      >
        <div class="left-box">
          <van-image
            width="90"
            height="90"
            :src="item.categoryImages[0].imagepath"
          />
        </div>
        <div class="right-box">
          <div class="text-title">
            {{ item.taskName }}
          </div>
          <div class="text-line">
            盘点资产：{{ item.categoryImages[0].fullname }}
          </div>
          <div class="text-line">
            盘点时间：{{ item.startDate }}至{{ item.endDate }}
          </div>
        </div>
      </div>
    </div>
    <van-empty v-else description="暂无数据" />
  </div>
</template>
<script>
import headerItem from "@/components/common/header.vue";
import { selectPlanByPerson } from "@/api/checkpaln/detail/tfamAssetCheckplanDetail";
import { Toast } from "vant";

export default {
  data() {
    return {
      listData: [],
      showAudit: false,
      checkobj: {}, // 需审核的数据
    };
  },
  components: {
    headerItem,
  },
  created() {},
  mounted() {
    this.init();
  },
  watch: {},
  methods: {
    init() {
      selectPlanByPerson({})
        .then((responseData) => {
          if (responseData.code == 100) {
            console.log("列表数据", responseData);
            this.listData = responseData.data ? responseData.data : [];
          } else {
            this.listData = [];
            Toast(responseData.msg);
          }
        })
        .catch((error) => {
          this.listData = [];
          Toast(error);
        });
    },
    openCheckItem(val) {
      // this.showAudit = true;
      this.$router.push({
        path: "/inventory",
        query: { planId: val },
      });
    },
  },
};
</script>
<style lang="scss" scoped>
.container {
  background-color: #f8f8f8;
  height: 100%;
  padding-bottom: 50px;
  font-size: 15px;

  .content_box {
    padding: 10px;

    .item-box {
      display: flex;
      flex-direction: row;
      //justify-content: space-between;
      align-items: center;
      padding: 20px 10px;
      margin-bottom: 8px;
      background: white;
      border-radius: 6px;
      height: 80px;

      .left-box {
        width: 30%;
        //background: red;
        height: 100%;

        img {
          background: black;
          width: 80%;
          height: 100%;
        }
      }

      .right-box {
        flex: 1;
        //background: blue;
        .text-title {
          font-size: 16px;
          font-weight: bold;
          color: #323233;
        }
        .text-line {
          margin-top: 2px;
          font-size: 13px;
          color: #646566;
        }
      }
    }
  }
}
</style>
