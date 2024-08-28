import request from '@/utils/request'
export const queryAuthData = () =>
  request({
    url: '/fam/assetting/productswitch/queryAuth',
    method: 'get'
  })
export const updateParam = (jsonCondition) =>
  request({
    url: '/fam/assetting/productswitch/updateParam',
    method: 'post',
    data: jsonCondition
  })
export const updateSwitch = (jsonCondition) =>
  request({
    url: '/fam/assetting/productswitch/updateSwitch',
    method: 'post',
    data: jsonCondition
  })
