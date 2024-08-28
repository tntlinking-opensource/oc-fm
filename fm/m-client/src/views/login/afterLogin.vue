<template>
  <div v-if='isLoading'>
    正在加载页面...
  </div>
</template>

<script>
import { getLoginedToken } from '@/api/auth'
import { getAppRoot } from '@/utils/auth'
import { getLocalRouters, setLocalRouters, setLocalDataPermisions, setLocalPersonalTheme } from '@/utils/auth'
import defaultRouters from './defaultRouters'

import BaseUI from '@/views/components/baseUI'
const _import = require('@/router/_import_' + process.env.NODE_ENV)

// 微应用时，appKey作为路由前缀
const root = getAppRoot()

export default {
  extends: BaseUI,
  data() {
    return {
    }
  },
  methods: {
    getUserData() {
      getLoginedToken().then(responseData => {
        if(responseData.code == 100) {
          this.handleUserData(responseData.data)
        } else {
          this.isError = true
          this.loginError = responseData.msg
        }
      })
      .catch(error => {
        this.outputError(error)
      })
    },
    handleUserData(userData) {
      setLocalDataPermisions(userData.dataPermisions)
      if (userData.routersApp && userData.routersApp.length > 0) {
        setLocalRouters(userData.routersApp)
      } else {
        setLocalRouters(defaultRouters.data)
      }
      this.initRouters()
      this.$router.push(`${root ? root : '/'}`)
    },
    initRouters() {
      let routers = getLocalRouters()
      if (routers && routers.length > 0) {
        let indexRouter = { // 根路由
          path: `${root ? root : '/'}`,
          name: '/',
          redirect: '/home',
          component: _import('layout/index'),
          meta: { requireAuth: true, verify: true },
          children: [
            // 一层菜单路由
            ...this.generateRouters().menuRouters
          ]
        }
        this.$router.addRoutes([indexRouter, ...this.generateRouters().childRouters])
      }
    },
    generateRouters() {

      let routers = getLocalRouters()
      let menuRouters = []
      let childRouters = []
      if (!routers) {
        return { menuRouters, childRouters }
      }
      for (let router of routers) {
        if (router.code) { // 非目录
          let routerProps = JSON.parse(router.properties)
          if (router.parent.id == 0) {
            let menuRouter = {
              path: router.url,
              name: router.code,
              component: _import(routerProps.path),
              meta: {
                name: router.name,
                cssClass: routerProps.cssClass,
                routerId: router.id,
                parentId: router.parent.id,
                requireAuth: process.env.REQUIRE_AUTH,
                verify: process.env.REQUIRE_AUTH,
                keepAlive: routerProps.keepAlive
              }
            }
            menuRouters.push(menuRouter)
          } else {
            let childRouter = {
              path: router.url,
              name: router.code,
              component: _import(routerProps.path),
              meta: {
                name: router.name,
                cssClass: routerProps.cssClass,
                routerId: router.id,
                parentId: router.parent.id,
                requireAuth: process.env.REQUIRE_AUTH,
                verify: process.env.REQUIRE_AUTH,
                keepAlive: routerProps.keepAlive
              }
            }
            childRouters.push(childRouter)
          }
        }
      }

      return { menuRouters, childRouters }
    }
  },
  created: function() {
    this.getUserData()
  }
}
</script>
