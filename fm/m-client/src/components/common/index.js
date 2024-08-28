import Vue from 'vue'
import Loading from './Loading.vue'
import Avatar from './Avatar.vue'


import DmNumberInput from './DmNumberInput/index.vue'
import DmNumberRangeInput from './DmNumberRangeInput/index.vue'
import DmPicker from './formPicker/DmPicker.vue'
import DmCascader from './DmCascader/index.vue'
import DmPickerTime from './formPicker/DmPickerTime.vue'
import DmPickerTimeRange from './formPicker/DmPickerTimeRange.vue'

const components = [
  Loading,
  Avatar,
  
  DmNumberInput,
  DmNumberRangeInput,
  DmPicker,
  DmCascader,
  DmPickerTime,
  DmPickerTimeRange
]

components.map(component => {
  Vue.component(component.name, component)
})
