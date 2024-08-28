<template>
  <div class="home">
    <Loading :show="loading" color="#1989fa"/>
    <div class="page-container">
      <div class="headerFiex">
        <!-- 头部卡片 -->
        <homeCard :user="this.currentUser" />
      </div>

      <div class="scroll">
        <div class="myCard-not">
          <homeGrid :user="this.currentUser" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import homeCard from './components/homeCard' // 头部卡片
import homeGrid from './components/homeGrid'
import BaseUI from '@/views/components/baseUI'
export default {
  name: 'homepage',
  extends: BaseUI,
  components: {
    homeCard,
    homeGrid,
  },
  data () {
    return {
    }
  },
  created() {
  },
  mounted(){

  },
  methods: {
  }
}
</script>

<style lang="scss" scoped>
.page-container{
  background-color: rgb(246, 248, 251);
}
.myCard-not {
  margin: 0.14rem 0.2rem 0 0.2rem;
  color: #fff;
}
.scoll {
  padding-bottom: 20px;
}
</style>
