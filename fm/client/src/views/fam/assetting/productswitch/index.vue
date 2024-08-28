<template>

    <el-col :span="24">
  <el-card class="box-card">
<div slot="header" class="clearfix">
  <span>报废提醒</span>
  <el-switch
    v-model="permission.remindSwitch"
    active-color="#13ce66"
    inactive-color="#ff4949"
    active-value= "0"
    inactive-value= "1"
    v-on:change="Switch"
  >
  </el-switch>
</div>
<div class="text item">
  用于待报废资产提醒功能；
  当资产剩余
  <el-input-number v-model="permission.lastMonth"  :min="1" label="描述文字"></el-input-number>
  月后进行报废提醒；
  <div>
    <el-checkbox v-model="permission.indexWarn"  true-label= "0" false-label= "1" label="首页提醒" border></el-checkbox>
    <el-checkbox v-model="permission.emailWarn"  true-label= "0" false-label= "1" label="邮件提醒" border></el-checkbox>
  </div>
</div>
      <el-button  style="float: right;" type="primary" @click="confirm">保 存 </el-button>
    <br >
</el-card>
    </el-col>
</template>
<style>
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}

.box-card {
  width: 480px;
}
</style>

<script>
import { queryAuthData,updateParam,updateSwitch } from '@/api/fam/assetting/productswitch/productswitch'
import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'
export default {
  extends: BaseUI,
  name: 'user-form',
  components: {
    /** 根据用户界面配置组件 开始 */

    /** 根据用户界面配置组件 结束 */
    OperationIcon
  },
  data() {
    return {
      permission: {
        id:"",
        remindSwitch: "1",
        lastMonth:"1" ,
        indexWarn: "1",
        emailWarn: "1",
      },
      value: true
    }
  },
  mounted() {
    this.queryAuthData()
  },
  methods: {
    Switch() {
      updateSwitch(this.permission).then((responseData) => {
        if (responseData.code == 100) {
          this.showMessage({type: 'success', msg: '保存成功'})
        } else {
          this.showMessage(responseData)
        }
      })
        .catch((error) => {
          this.outputError(error)
        })
    },
    confirm() {
      updateParam(this.permission).then((responseData) => {
        if (responseData.code == 100) {
          this.showMessage({type: 'success', msg: '保存成功'})
        } else {
          this.showMessage(responseData)
        }
      })
        .catch((error) => {
          this.outputError(error)
        })
    },
    queryAuthData(){
      queryAuthData()
        .then((responseData) => {
          if (responseData.code == 100) {
            debugger
            this.permission = responseData.data
            if (this.permission.remindSwitch=="1"){
              this.permission.remindSwitch="1"
            }else {
              this.permission.remindSwitch="0"
            }
            if (this.permission.indexWarn=="1"){
              this.permission.indexWarn="1"
            }else {
              this.permission.indexWarn="0"
            }
            if (this.permission.emailWarn=="1"){
              this.permission.emailWarn="1"
            }else {
              this.permission.emailWarn="0"
            }
            console.log(this.permission)
          } else {
            this.showMessage(responseData)
          }
        })
        .catch((error) => {
          this.outputError(error)
        })
    }

  },
};
</script>
