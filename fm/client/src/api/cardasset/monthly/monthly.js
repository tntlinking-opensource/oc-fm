import request from '@/utils/request'


export const dodep = (data) =>
  request({
    url: '/cardasset/supplementreduce/tfamAssetOld/dodep',
    method: 'post',
    data: data
  })
export const checkasset = (data) =>
  request({
    url: '/cardasset/supplementreduce/tfamAssetOld/checkasset',
    method: 'post',
    data: data
  })
export const workloadlist = (data) =>
  request({
    url: '/cardasset/supplementreduce/tfamAssetOld/workloadlist',
    method: 'post',
    data: data
  })
export const busdate = (data) =>
  request({
    url: '/cardasset/supplementreduce/tfamAssetOld/busdate',
    method: 'post',
    data: data
  })
export const loadinfo = (data) =>
  request({
    url: '/cardasset/supplementreduce/tfamAssetOld/loadinfo',
    method: 'post',
    data: data
  })
export const produceVoucher = (data) =>
  request({
    url: '/cardasset/supplementreduce/tfamAssetOld/produceVoucher',
    method: 'post',
    data: data
  })
