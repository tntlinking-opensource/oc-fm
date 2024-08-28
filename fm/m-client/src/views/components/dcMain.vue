<template>
  <div>
    list窗口的基础组件类
  </div>
</template>
<script>
import BaseUI from './baseUI'
import { validatenull, validateURL } from '../../utils/validate'
export default {
  extends: BaseUI,
  name: 'DcMain',
  data() {
    return {
      moreParm: {   // 更多条件值
        groupOne: [
          {logic: 'AND', column: null, queryType: '', value: null, operations: []},
          {logic: 'AND', column: null, queryType: '', value: null, operations: []},
          {logic: 'AND', column: null, queryType: '', value: null, operations: []}
        ],
        groupTwo: [
          {logic: 'AND', column: null, queryType: '', value: null, operations: []},
          {logic: 'AND', column: null, queryType: '', value: null, operations: []},
          {logic: 'AND', column: null, queryType: '', value: null, operations: []}
        ],
        groupLogic: 'OR'
      }
    }
  },
  methods: {

  },
  mounted() {

  }
}
</script>
