import request from '@/utils/request'

export const getTfamAssetCheckimageById = (id) =>
  request({
    url: '/checkplan/image/tfamAssetCheckimage/' + id,
    method: 'get'
  })

export const listTfamAssetCheckimagePage = (search) =>
  request({
    url: '/checkplan/image/tfamAssetCheckimage/list',
    method: 'post',
    data: search
  })

export const listTfamAssetCheckimageAll = (search) =>
  request({
    url: '/checkplan/image/tfamAssetCheckimage/listAll',
    method: 'post',
    data: search
  })

export const saveTfamAssetCheckimage = (tfamAssetCheckimage) =>
  request({
    url: '/checkplan/image/tfamAssetCheckimage/save',
    method: 'post',
    data: tfamAssetCheckimage
  })

export const deleteTfamAssetCheckimage = (tfamAssetCheckimage) =>
  request({
    url: '/checkplan/image/tfamAssetCheckimage/delete',
    method: 'post',
    data: tfamAssetCheckimage
  })

export const bulkInsertTfamAssetCheckimage = (tfamAssetCheckimages) =>
  request({
    url: '/checkplan/image/tfamAssetCheckimage/bulkInsert',
    method: 'post',
    data: tfamAssetCheckimages
  })

export const bulkUpdateTfamAssetCheckimage = (tfamAssetCheckimages) =>
  request({
    url: '/checkplan/image/tfamAssetCheckimage/bulkUpdate',
    method: 'post',
    data: tfamAssetCheckimages
  })

export const bulkDeleteTfamAssetCheckimage = (tfamAssetCheckimages) =>
  request({
    url: '/checkplan/image/tfamAssetCheckimage/bulkDelete',
    method: 'post',
    data: tfamAssetCheckimages
  })
