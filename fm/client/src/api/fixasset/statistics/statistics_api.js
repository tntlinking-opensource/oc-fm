import request from '@/utils/request'

export const getListALL = () =>
  request({
    url: '/fixasset/asset/tfamAsset/countAssetForGraph/',
    method: 'post'
  })
