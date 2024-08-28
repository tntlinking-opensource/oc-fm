import Vue from 'vue'
import App from './App'
import store from './store'
import router from './router'
import './public-path'

import ElementUI from 'element-ui'

// 设置 rem 基准值
import 'amfe-flexible';
// 引入 vant 组件库
import '@/utils/vant'
import '@/assets/main.scss'
import '@/utils/permission'
/* 注入全局脚本 */
import './utils/global'
import * as filters from './utils/filters'
import '@/components/common/index'

Vue.use(ElementUI)

Vue.config.productionTip = false

/* 注入全局filter */
Object.keys(filters).forEach(key => {
  Vue.filter(key, filters[key])
})

// 微前端改造
let vm = null
function render(props = {}) {
  const { name, container } = props;

  // 微应用模式下，使用微应用的名称作为路由base
  router.history.base = name ? `/${name}${router.history.base}` : `${router.history.base}`

  vm = new Vue({
    el: container ? container.querySelector('#app') : '#app',
    store,
    router,
    components: { App },
    template: '<App/>'
  })
}

export default vm

// 独立运行时
if (!window.__POWERED_BY_QIANKUN__) {
  render();
}

// 微应用必须暴露三个接口：bootstrap、mount、unmount
export async function bootstrap(props) {
  console.log('[vue] vue app bootstraped');
  console.log("我应该只调用一次才对");
}
export async function mount(props) {
  // 每次进入都会调用
  console.log("我是微应用 a");
  console.log("我应该每次都调用才对");
  console.log('[vue] props from main framework', props);
  render(props);
  
  console.log('client ---------')
  console.log(vm);
  
}
export async function unmount(props) {
  vm.$destroy();
  vm.$el.innerHTML = '';
  vm = null;
  delete window.Vue
}
