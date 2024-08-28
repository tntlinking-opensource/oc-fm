<template>
  <component ref='wfForm' :is='wfForm' v-on:after-wfForm-load='afterWfFormload()' :bizParams='bizParams'></component>
</template>

<script>
import WfBaseUI from './wfBaseUI'
import { getProcessByKey, getStartForm } from '@/api/wf/processdefinition'
import { listResourcePermission } from '@/api/admin/common/permission'
export default {
  extends: WfBaseUI,
  name: 'ProcessPanel',
  components: { },
  data() {
    return {
      wfForm:  null,  // 工作流表单
      currentProc: null, // 当前流程
      currentFormKey: null, // 当前的表单URL

      permission: {
        view: false,
        add: false,
        edit: false,
        suspend: false,
        export: false
      }
    }
  },
  props: {
    // 权限
    bizAdd: {
      default: () => {return null }
    },
    // 租户id（公司id)
    tenantId: {
      type: String,
      default: () => {return null}
    },
    // 流程key的前缀
    keyPrefix: {
      type: String,
      default: () => {return null}
    },
    // 流程初始时间
    procName: {
      type: String,
      default: ()=> {return ''}
    },
    // 审批表单url
    formKey: {
      type: String,
      default: ()=> {return ''}
    },
    // 流程实例标题
    procTitile: {
      type: String,
      default: ()=> {return ''}
    },
    // 表单参数,用于设置工作流表单默认值
    bizParams: {
      type: Object,
      default: () => { return null }
    }
  },
  watch:{

  },
  methods: {
    // 新建流程审批
    onCreate() {
      let procKey = this.keyPrefix + this.tenantId              // 合同审批流程key
      this.$emit('v-loading', true)
      getProcessByKey(procKey, this.tenantId).then(responseData => {
        if(responseData.id) {
          this.startProcess(responseData)
        }else{
          // 没有找到流程
          if(responseData.code == 404) {
            this.showMessage({
              type:'warning',
              msg: '请联系公司管理员配置审批流程。'
            })
          } else {
            this.showMessage(responseData)
          }
        }
        this.$emit('v-loading', false)
      }).catch(error => {
        this.$emit('v-loading', false)
        this.outputError(error)
      })
    },
    startProcess(proc) {
      getStartForm(proc.id).then(responseData => {
        if(responseData.hasOwnProperty('key')) {
          if(responseData.key) {
            this.currentProc = proc
            this.currentFormKey = responseData.key
            this.loadWfForm(responseData.key)
          } else {
            this.showMessage({type:'warning' , msg: '流程未配置form，请联系管理员。'})
          }
        }else{
          this.showMessage(responseData)
        }
        this.$emit('v-loading', false)
      }).catch(error => {
        this.$emit('v-loading', false)
        this.outputError(error)
      })
    },
    afterWfFormload() {
      this.$refs.wfForm.$emit('openCreateDialog', this.currentProc, this.currentFormKey)
    },
    initCompent() {
      this.$emit('v-loading', true)
      // 流程设计权限 3005是流程设计路由的id
      listResourcePermission(3005).then(responseData => {
        if(responseData.code == 100) {
          this.permission.view = responseData.data.find(item => {
            return item.permission === 'designer:read'
          })
          this.permission.add = responseData.data.find(item => {
            return item.permission === 'designer:create'
          })
          this.permission.edit = responseData.data.find(item => {
            return item.permission === 'designer:update'
          })
          this.permission.suspend = responseData.data.find(item => {
            return item.permission === 'designer:suspend'
          })
          this.permission.export = responseData.data.find(item => {
            return item.permission === 'designer:export'
          })

        }else{
          this.showMessage(responseData)
        }
        this.$emit('v-loading', false)
      }).catch(error => {
        this.$emit('v-loading', false)
        this.outputError(error)
      })
    }
  },
  mounted(){
    this.initCompent()
    this.$nextTick(() => {
      this.$on('onCreate', () => {
        this.onCreate()
      })
    })
  }
}

</script>

