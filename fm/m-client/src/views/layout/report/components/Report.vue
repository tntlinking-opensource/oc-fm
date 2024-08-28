<template>
  <div style="height: 100%; background-color: rgb(247, 248, 250)">
    <Preview
      v-if="reportType == 1"
      :is-click-component="isClickComponent"
      :component-data="componentData"
      :canvas-style-data="canvasStyleData"
      :now-panel-track-info="nowPanelTrackInfo"
      :now-panel-jump-info="nowPanelJumpInfo"
      @EmbendChartClick='onEmbendChartClick'></Preview>

    <PanelViewShow
      v-if="reportType == 2"
      :is-click-component="isClickComponent"
      :component-data="componentData"
      :canvas-style-data="canvasStyleData"
      :panelInfos="panelInfo"
      :now-panel-track-info="nowPanelTrackInfo"
      :now-panel-jump-info="nowPanelJumpInfo"
      :user="user"></PanelViewShow>

    <PreviewEject
      v-if="reportType == 3"
      :is-click-component="isClickComponent"
      :component-data="componentData"
      :canvas-style-data="canvasStyleData"
      :now-panel-track-info="nowPanelTrackInfo"
      :now-panel-jump-info="nowPanelJumpInfo"></PreviewEject>
  </div>
</template>

<script>
import BaseUI from '@/views/components/baseUI'
import Vue from 'vue'
import { get } from '@/api/report/panel/panel'
import { getPanelAllLinkageInfo } from '@/api/report/panel/linkage'
import { queryPanelJumpInfo } from '@/api/report/panel/linkJump'
import { uuid } from 'vue-uuid'

import bus from '@/views/layout/report/utils/bus'
// 注入报表组件
import store from '@/store'
import { UxGrid } from 'umy-ui'
Vue.use(window['vue-dataviewer']).use(UxGrid)

const reportMixin = window['vue-dataviewer'].reportMixin

export default {
  name: "Report",
  extends: BaseUI,
  mixins: [reportMixin],
  props: {
    // 报表显示模式 1 嵌入式模式； 2 浏览报表模式； 3 浏览器弹出页面模式
    reportType: {
      type: String | Number,
      required: true
    },
    // 仅在嵌入式报表有效
    dashboardId: {
      type: String,
      default: () => {
        return null
      }
    }
  },
  mounted() {
    this.$nextTick(() => {
      // 获取看板 仅在嵌入式报表有效
      if(this.dashboardId) {
        this.getDashboard(this.dashboardId)
      }

      // 将混入组件内的对象的 addViewFilter 方法拿出来并执行
      this.$on('addEmbeddedViewFilter', function(param) {
        this.addViewFilter(param)
      })
      // 将混入组件内的对象的 removeViewFilter 方法拿出来并执行
      this.$on('removeEmbeddedViewFilter', function(componentId) {
        this.removeViewFilter(componentId)
      })

      // 在 PanelViewShow 使用
      bus.$on('setComponentData', (data) => {
        this.setComponentData(data)
      })
      bus.$on('setCanvasStyle', (data) => {
        this.setCanvasStyle(data)
      })
      bus.$on('setPanelInfo', (data) => {
        this.setPanelInfo(data)
      })
      bus.$on('setNowPanelTrackInfo', (data) => {
        this.setNowPanelTrackInfo(data)
      })
      bus.$on('setNowPanelJumpInfo', (data) => {
        this.setNowPanelJumpInfo(data)
      })
    })
  },
  methods: {
    onEmbendChartClick(param) {
      this.$emit('EmbendChartClick', param)
    },
    getDashboard(dashboardId) {
      get('panel/group/findOne/' + dashboardId).then(response => {
        if (response.data) {
          this.setComponentData(this.resetID(JSON.parse(response.data.panelData)))
          this.setCanvasStyle(JSON.parse(response.data.panelStyle))
        } else {
          this.setComponentData([])
          this.setCanvasStyle([])
        }
        const data = {
          id: dashboardId,
        }
        // this.$store.dispatch('panel/setPanelInfo', data)
        this.setPanelInfo(data)
        // 刷新联动信息
        getPanelAllLinkageInfo(dashboardId).then(rsp => {
          // this.$store.commit('report/setNowPanelTrackInfo', rsp.data)
          this.setNowPanelTrackInfo(rsp.data)
        })

        // 刷新跳转信息
        queryPanelJumpInfo(dashboardId).then(rsp => {
          // this.$store.commit('report/setNowPanelJumpInfo', rsp.data)
          this.setNowPanelJumpInfo(rsp.data)
        })
      }).catch(error => {
        this.outputError(error)
      })
    },
    resetID(data) {
      if (data) {
        data.forEach(item => {
          item.type !== 'custom' && (item.id = uuid.v1())
        })
      }
      return data
    }
  }
}
</script>
