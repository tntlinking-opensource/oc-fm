import Login from '@/views/login/index'
import afterLoginView from '@/views/login/afterLogin'
import { getAppRoot } from '@/utils/auth'

const _import = require('./_import_' + process.env.NODE_ENV)
const Home = _import('layout/index')

Vue.use(VueRouter)

// 微应用时，appKey作为路由前缀
const root = getAppRoot()

const router = new VueRouter({
  routes: [
    {
      path: `${root}/login`,
      name: 'login',
      component: Login,
      meta: {  }
    },
    {
      path: `${root}/more`,
      name: 'more',
      component: _import('layout/more/more'),
      meta: {
        name: '更多',
      }
    },
    {
      path: `${root}/afterlogin`,
      name: 'afterlogin',
      component: afterLoginView,
      meta: { }
    }
  ]
})

export default router
