import request from '@/utils/request'

export const getTfamAssetCheckimageById = (id) =>
  request({
    url: '/fixasset/asset/tfamAssetCheckimage/' + id,
    method: 'get'
  })

export const listTfamAssetCheckimagePage = (search) =>
  request({
    url: '/fixasset/asset/tfamAssetCheckimage/list',
    method: 'post',
    data: search
  })

export const listTfamAssetCheckimageAll = (search) =>
  request({
    url: '/fixasset/asset/tfamAssetCheckimage/listAll',
    method: 'post',
    data: search
  })

export const saveTfamAssetCheckimage = (tfamAssetCheckimage) =>
  request({
    url: '/fixasset/asset/tfamAssetCheckimage/save',
    method: 'post',
    data: tfamAssetCheckimage
  })

export const deleteTfamAssetCheckimage = (tfamAssetCheckimage) =>
  request({
    url: '/fixasset/asset/tfamAssetCheckimage/delete',
    method: 'post',
    data: tfamAssetCheckimage
  })

export const bulkInsertTfamAssetCheckimage = (tfamAssetCheckimages) =>
  request({
    url: '/fixasset/asset/tfamAssetCheckimage/bulkInsert',
    method: 'post',
    data: tfamAssetCheckimages
  })

export const bulkUpdateTfamAssetCheckimage = (tfamAssetCheckimages) =>
  request({
    url: '/fixasset/asset/tfamAssetCheckimage/bulkUpdate',
    method: 'post',
    data: tfamAssetCheckimages
  })

export const bulkDeleteTfamAssetCheckimage = (tfamAssetCheckimages) =>
  request({
    url: '/fixasset/asset/tfamAssetCheckimage/bulkDelete',
    method: 'post',
    data: tfamAssetCheckimages
  })
