<template>
  <div class="homeCard">
    <div class="homepage-image">
        <homeSettings :user="currentUser" />

        <!-- 卡片背景 暂用代替换-->
        <div class="homepage-user">
          <div class="bolang1"></div>
          <div class="bolang2"></div>
        </div>

        <div class="homepage-user">
          <van-row type="flex" align="center" style="height: 100%; " justify="space-between">
              <van-col :span="8">
                <Avatar
                  :avatarSrc="currentUser.avatar"
                  width="80px"
                  height="80px"
                  bgcolor="#2a67fe30"
                  :showBorder="true"
                  icon="plus"
                  iconColor="#2A67FE"
                  :preview="true"
                />
              </van-col>

              <van-col :span="15" @click="openMyUserPop">
                <p style="font-size: 14px;">{{user.name}}</p>
                <p>{{user.department.name}}</p>
                <p>{{user.company.name}}</p>
              </van-col>
          </van-row>
        </div>
    </div>
  </div>
</template>

<script>
import homeSettings from './homeSettings.vue'
import { getLocalCurrentUser } from '@/utils/auth'
export default {
  name: 'homeCard',
  components: {
    homeSettings
  },
  props: {
    user: {
      type: Object
    }
  },
  data () {
    return {
      currentUser: {}
    }
  },
  mounted() {
    this.currentUser = getLocalCurrentUser()
  },
  methods: {
  }
}
</script>

<style lang="scss" scoped>
  .homepage-image {
    position: relative;
    width: 100%;
    height: 180px;
    background: linear-gradient(to top, #FFFFFF, transparent),url('~@/assets/images/backimg.jpeg') no-repeat;
    background-size: 100% auto;
  }
  .homepage-user {
    position: absolute; // 保证背景图片上的文字清晰
    top: 66%;
    left: 50%;
    width: 90%;
    height: 120px;
    background: #ffffffbd;
    border-radius: 20px;
    transform: translate(-50%, -50%);
    overflow: hidden;
    padding: 0 10px;
  }
.homepage-qr {
  position: absolute;
  right: 0;
  top: 10%;
  display: flex;
  flex-direction: column;
  .van-image {
    width: 30px;
    margin-right: 16px;
  }
  .homepage-tag {
    color: #fff;
    margin-bottom: 10px;
    padding: 0px 10px;
    background: #2A67FE;
    border-radius: 20px 0 0 20px;
    width: 90px;
    text-align: center;
  }
}

.bolang1,.bolang2 {
  height: 100%;
  width: 150%;
  position: absolute;
  border-radius: 55%;
}
.bolang1 {
  left: 80%;
  top: 60%;
  transform: translate(-30%, 0%);
  -webkit-transform: translate(-30%, 0);
  background: #2a67fe
}
.bolang2 {
  left: -110%;
  top: 66%;
  transform: translate(10%, 0);
  -webkit-transform: translate(10%, 0);
  background: rgba(65,101,255, 0.8);
}
.homeSettings {
  position: absolute;
  right: 20px;
  top: 20px;
  color: #fff;
}
</style>
