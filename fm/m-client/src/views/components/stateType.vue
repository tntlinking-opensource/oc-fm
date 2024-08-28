<template>
  <span>
    <div class="triangle-topright" :class="getStateType('class')"></div>
    <div class="triangle-rotate">{{getStateType('value')}}</div>
    <div class="backImgs"></div>
  </span>
</template>

<script>
import { validatenull } from '@/utils/validate'
export default {
  props: {
    row: {
      type: Object
    },
    active: {
      type: String
    }
  },
  data () {
   return {
      state_Obj: {
        '001': 'ended',
        '002': 'cancele',
        '003': 'refused',
        '004': 'ended'
      },
      state_ObjName: {
        '001': '今日',
        '002': '明日',
        '003': '逾期',
        '004': '当期'
      },
    }
  },
  methods: {
    getStateType(type) {
      if(this.active == '1') { // 待执行
        let content = this.getBeforeState(type)
        return content
      } else if(this.active == '2') { // 执行中
        let content = this.getAfterState(type)
        return content
      } else if(this.active == '3') { // 已完成
        let content = this.getFinishState(type)
        return content
      }
    },

    getBeforeState(type) {
      //当前时间
      let time = new Date()
      let nowTime = time.Format("yyyy-MM-dd hh:mm:ss")
      let now = nowTime.replace(/-/g,'/')
      let iosNowTime = JSON.stringify(new Date(now))

      //任务开始时间
      let startTime = new Date(this.row.startTime).Format("yyyy-MM-dd hh:mm:ss")
      startTime = startTime.replace(/-/g,'/')
      let iosStartTime = JSON.stringify(new Date(startTime))

//    当前日期
      let date = new Date()
      let nowDate = date.Format("yyyy-MM-dd")
      let iosNowDate = JSON.stringify(new Date(nowDate.replace(/-/g,'/')))

//    任务开始日期
      let startDate = new Date(this.row.startTime).Format("yyyy-MM-dd")
      startDate = startDate.replace(/-/g,'/')
      let iosStartDate = JSON.stringify(new Date(startDate))

      //任务结束时间
      let endTime = new Date(this.row.endTime).Format("yyyy-MM-dd hh:mm:ss")
      endTime = endTime.replace(/-/g,'/')
      let iosEndTime = JSON.stringify(new Date(endTime))

      //今日（绿色）：1、当前时间<=任务开始时间2、当前日期=任务开始日期3、任务未开始
      if(JSON.stringify(iosStartTime) <= JSON.stringify(iosNowTime) && JSON.stringify(iosNowTime) <= JSON.stringify(iosEndTime)){
        if(type == 'class') {
          return this.state_Obj["001"]
        } else {
          return this.state_ObjName["001"]
        }
      }

      //明日（黄色）：1、当前日期<任务开始日期2、任务未开始
      if(JSON.stringify(iosNowDate) < JSON.stringify(iosStartDate)){
        if(type == 'class') {
            return this.state_Obj["002"]
        } else {
            return this.state_ObjName["002"]
        }
      }

      //逾期（红色）：1、当前日期>=任务开始日期2、当前时间>任务结束时间3、任务未开始
      if(JSON.stringify(iosNowDate) >= JSON.stringify(iosStartDate) && JSON.stringify(iosNowTime) > JSON.stringify(iosEndTime)){
        if(type == 'class') {
          return this.state_Obj["003"]
        } else {
          return this.state_ObjName["003"]
        }
      }
    },

    getAfterState(type) {
       //任务开始时间
      let startTime = new Date(this.row.startTime).Format("yyyy-MM-dd hh:mm:ss")
      startTime = startTime.replace(/-/g,'/')
      let iosStartTime = JSON.stringify(new Date(startTime))

      //当前时间
      let time = new Date()
      let nowTime = time.Format("yyyy-MM-dd hh:mm:ss")
      let now = nowTime.replace(/-/g,'/')
      let iosNowTime = JSON.stringify(new Date(now))

      //任务结束时间
      let endTime = new Date(this.row.endTime).Format("yyyy-MM-dd hh:mm:ss")
      endTime = endTime.replace(/-/g,'/')
      let iosEndTime = JSON.stringify(new Date(endTime))

      // 任务开始日期
      let startDate = new Date(this.row.startTime).Format("yyyy-MM-dd")
      startDate = startDate.replace(/-/g,'/')
      let iosStartDate = JSON.stringify(new Date(startDate))

      // 当前日期
      let date = new Date()
      let nowDate = date.Format("yyyy-MM-dd")
      let iosNowDate = JSON.stringify(new Date(nowDate.replace(/-/g,'/')))

      //正常（绿色）：1、任务开始时间<=当前时间<=任务结束时间2、任务执行中3、任务开始日期=当前日期
      if(JSON.stringify(iosStartTime) <= JSON.stringify(iosNowTime) && JSON.stringify(iosNowTime) <= JSON.stringify(iosEndTime) 
        && JSON.stringify(iosStartDate) == JSON.stringify(iosNowDate)){
        if(type == 'class') {
          return this.state_Obj["001"]
        } else {
          return this.state_ObjName["001"]
        }
      }

      //逾期（红色）：1、任务结束时间<当前时间2、任务开始日期<=当前日期3、任务执行中
      if(JSON.stringify(iosEndTime) < JSON.stringify(iosNowTime) && JSON.stringify(iosStartDate) <= JSON.stringify(iosNowDate)){
        if(type == 'class') {
          return this.state_Obj["003"]
        } else {
          return this.state_ObjName["003"]
        }
      }
    },

    getFinishState(type) {
      if(validatenull(this.row.finishDate)) {
        return null
      }

      //任务完成时间
      let finishTime = new Date(this.row.finishDate).Format("yyyy-MM-dd hh:mm:ss")
      finishTime = finishTime.replace(/-/g,'/')
      let iosFinishTime = JSON.stringify(new Date(finishTime))

      //任务开始时间
      let startTime = new Date(this.row.startTime).Format("yyyy-MM-dd hh:mm:ss")
      startTime = startTime.replace(/-/g,'/')
      let iosStartTime = JSON.stringify(new Date(startTime))

      //任务结束时间
      let endTime = new Date(this.row.endTime).Format("yyyy-MM-dd hh:mm:ss")
      endTime = endTime.replace(/-/g,'/')
      let iosEndTime = JSON.stringify(new Date(endTime))

      // 任务开始日期
      let startDate = new Date(this.row.startTime).Format("yyyy-MM-dd")
      startDate = startDate.replace(/-/g,'/')
      let iosStartDate = JSON.stringify(new Date(startDate))

      //任务完成日期
      let finishDate = new Date(this.row.finishDate).Format("yyyy-MM-dd")
      finishDate = finishDate.replace(/-/g,'/')
      let iosFinishDate = JSON.stringify(new Date(finishDate))
      
      //正常（绿色）：1、任务开始时间<=实际完成时间<=任务结束时间
      if(JSON.stringify(iosStartTime) <= JSON.stringify(iosFinishTime) && JSON.stringify(iosFinishTime) <= JSON.stringify(iosEndTime)){
        if(type == 'class') {
          return this.state_Obj["004"]
        } else {
          return this.state_ObjName["004"]
        }
      }

      //逾期（红色）：1、任务结束时间<实际完成时间2、任务开始日期<=实际完成日期
      if(JSON.stringify(iosEndTime) < JSON.stringify(iosFinishTime) && JSON.stringify(iosStartDate) <= JSON.stringify(iosFinishDate)){
        if(type == 'class') {
          return this.state_Obj["003"]
        } else {
          return this.state_ObjName["003"]
        }
      }
    }
  },
  watch: {
    
  }
}
</script>

<style lang="scss" scoped>
.cancele {
  border-top: 1.4rem solid #F9C95E;
}
.ended {
  border-top: 1.4rem solid #67c23a;
}
.refused {
  border-top: 1.4rem solid #FD325C;
}
</style>