<template>
  <van-popup v-model="reportShow" class="page-popup">
    <van-nav-bar class="report-header" :title="navTitle" left-arrow @click-left="onNavLeft" />
    <div class="page-popup-content">
      <Loading :show="loading" color="#1989fa"/>
      <Preview></Preview>
    </div>
  </van-popup>
</template>

<script>
import BaseUI from '@/views/components/baseUI'
import bus from '@/views/layout/report/utils/bus'
import { get } from '@/api/report/panel/panel'
import { getPanelAllLinkageInfo } from '@/api/report/panel/linkage'
import { queryPanelJumpInfo } from '@/api/report/panel/linkJump'
import { uuid } from 'vue-uuid'
import Preview from '@/views/layout/report/components/Preview'
export default {
  extends: BaseUI,
  name: 'reportView',
  components: {
    Preview
  },
  data() {
    return {
      reportShow: false,
      navTitle: ''
    }
  },
  methods: {
    onStoreClick(row) {
      this.setLoad()
      this.setDataNull()
      this.getData(row.panelGroupId).then(response => {
        this.resetLoad()
        this.openStore(row, response)
      }).catch(error => {
        this.outputError(error)
      })
    },
    onShareClick(row) {
      this.setLoad()
      this.setDataNull()
      this.getData(row.id).then(response => {
        this.resetLoad()
        this.openShare(row, response)
      }).catch(error => {
        this.outputError(error)
      })
    },
    openStore(row, response) {
      if (window["vue-dataviewer"]) {
        bus.$emit('setComponentData', this.resetID(JSON.parse(response.data.panelData)))
        bus.$emit('setCanvasStyle', JSON.parse(response.data.panelStyle))
        const data = {
          id: row.panelGroupId,
          name: row.name
        }
        bus.$emit('setPanelInfo', data)

        // 刷新联动信息
        getPanelAllLinkageInfo(row.panelGroupId).then(rsp => {
          bus.$emit('setNowPanelTrackInfo', rsp.data)
        })

        // 刷新跳转信息
        queryPanelJumpInfo(row.panelGroupId).then(rsp => {
          bus.$emit('setNowPanelJumpInfo', rsp.data)
        })
      } else {
        let _this = this
        setTimeout(function() { _this.openStore(row, response) }, 50)
      }
    },
    openShare(row, response) {
      if (window["vue-dataviewer"]) {
        bus.$emit('setComponentData', this.resetID(JSON.parse(response.data.panelData)))
        bus.$emit('setCanvasStyle', JSON.parse(response.data.panelStyle))
        // this.$store.dispatch('panel/setPanelInfo', row)
        // this.$emit('setPanelInfo', row)
        bus.$emit('setPanelInfo', row)

        // 刷新联动信息
        getPanelAllLinkageInfo(row.id).then(rsp => {
          bus.$emit('setNowPanelTrackInfo', rsp.data)
        })

        // 刷新跳转信息
        queryPanelJumpInfo(row.id).then(rsp => {
          bus.$emit('setNowPanelJumpInfo', rsp.data)
        })
      } else {
        let _this = this
        setTimeout(function() { _this.openShare(row, response) }, 50)
      }
    },
    resetID(data) {
      if (data) {
        data.forEach(item => {
          item.type !== 'custom' && (item.id = uuid.v1())
        })
      }
      return data
    },
    onNavLeft() {
      this.reportShow = false
    },
    getData(id) {
      return get('panel/group/findOne/' + id)
    },
    setDataNull() {
      bus.$emit('setPanelInfo', [])
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.$on('openReportView', (data) => {
        this.reportShow = true
        this.navTitle = data.row.name
        data.type === 'FROM_SHARE' ? this.onShareClick(data.row) : this.onStoreClick(data.row)
      })
    })
  }
}
</script>

<style scoped lang="scss">
.page-popup {
  z-index: 9999!important;
}
.page-popup-content {
  position: relative;
  height: calc(100% - 46px);
  /*height: calc(100% - 92px);*/
}
/deep/ .panel-design {
  min-width: 100%;
  overflow: inherit;
}
/deep/ .panel-design-head {
  padding: 0!important;
  .el-col {
    &:first-of-type {
      display: none;
    }
    width: 100%;
  }
}
/deep/ .panel-design-preview {
  z-index: 10000!important;
  overflow: initial!important;
}
  /deep/ .mobile-dialog-css {
    z-index: 10001!important;
  }
</style>
