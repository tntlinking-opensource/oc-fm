<template>
  <div class="Avatar">
      <div class="avatar" :style="{height: height, width: width,background: bgcolor}" :class="{'showBorder': showBorder }">
        <div class="avatar-icon" v-if="avatarSrc == '' || avatarSrc == null">
          <van-icon :name="icon" :color="iconColor" :size="iconSize" />
        </div>
        <van-image fit="cover" :src="avatarSrc"  @click="sceneImg(avatarSrc)" />
      </div>
  </div>
</template>

<script>
import { ImagePreview } from "vant";
export default {
	name: 'Avatar',
  props: {
    avatarSrc: { // 头像url
      type: String
    },
    preview: { // 点击头像是否预览
      type: Boolean,
      default: false
    },
    width: {
      type: String,
      default: '30px'
    },
    height: {
      type: String,
      default: '30px'
    },
    bgcolor: {
      type: String,
      default: '#b7c5d4'
    },
    showBorder: { // 是否显示border
      type: Boolean,
      default: false
    },
    icon: {
      type: String,
      default: 'manager'
    },
    iconColor: {
      type: String,
      default: '#5e6a78'
    },
    iconSize: {
      type: String,
      default: '26px'
    }
  },
  data() {
    return {

    }
  },
  methods: {
    sceneImg(images) {
      if (!this.preview) return
      let list = [images]
      ImagePreview({
        images: list, //需要预览的图片 URL 数组
        showIndex: true, //是否显示页码
        loop: false, //是否开启循环播放
      })
    },
  }
}
</script>

<style lang="scss" scoped>
.avatar {
  display: inline-block;
  border-radius: 50%;
  margin-right: 6px;
  overflow: hidden;
  position: relative;
  .avatar-icon {
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
  }
  .upload {
    opacity: 0;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    z-index: 9;
  }
  /deep/ .van-icon-photo:before {
    content: '';
  }
}
.showBorder {
  border: 2px dashed #2A67FE;
}
</style>