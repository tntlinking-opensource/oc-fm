import request from '@/utils/request'


export const proj = (data) =>
  request({
    url: '/cardasset/supplementreduce/tfamAssetOld/proj',
    method: 'post',
    data: data
  })
export const projlist = (search) =>
  request({
    url: '/cardasset/supplementreduce/tfamAssetOld/projlist',
    method: 'post',
    data: search
  })
