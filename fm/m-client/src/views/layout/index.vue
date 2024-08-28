<template>
  <div>
    <router-view></router-view>
    <van-tabbar route safe-area-inset-bottom>
      <van-tabbar-item
        v-for="item in menus"
        :key="item.id"
        :icon="item.icon"
        :to="item.url">
        {{item.name}}
      </van-tabbar-item>
    </van-tabbar>
  </div>
</template>

<script>
import { getLocalRouters, getLocalCurrentUser } from '@/utils/auth'
export default {
  name: 'layout',
  data() {
    return {
      firstRoutes: [],
      menus: []
    }
  },
  created() {
    this.initMenuRoutes()
  },
  watch: {

  },
  methods: {
    initMenuRoutes() {
     const routers = getLocalRouters()
      if (routers && routers.length > 0) {
        routers.forEach((router) => {
          if (router.parent.id == 0) {
            this.firstRoutes.push(router)
          }
        })
        this.firstRoutes.forEach((router) => {
          let properties = JSON.parse(router.properties)
          this.menus.push({
            name: router.name,
            id: router.id,
            url: router.url,
            icon: properties.icon
          })
        })
      }
    }
  },
  beforeCreate() {
    // 当前登录用户放进全局参数中
    window.currentUser = getLocalCurrentUser()
  },
}
</script>

<style>

</style>
