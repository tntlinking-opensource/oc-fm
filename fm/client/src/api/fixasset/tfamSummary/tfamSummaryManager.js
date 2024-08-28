import request from '@/utils/request'

export const listAll = (params) =>
  request({
    url: '/fixasset/asset/tfamAccessory/listAll',
    method: 'post',
    data: params
  })
