import request from '@/utils/request'

export const getTfamAssetCheckplanDetailById = (id) =>
  request({
    url: '/checkpaln/detail/tfamAssetCheckplanDetail/' + id,
    method: 'get'
  })

export const listTfamAssetCheckplanDetailPage = (search) =>
  request({
    url: '/checkpaln/detail/tfamAssetCheckplanDetail/list',
    method: 'post',
    data: search
  })

export const listTfamAssetCheckplanDetailAll = (search) =>
  request({
    url: '/checkpaln/detail/tfamAssetCheckplanDetail/listAll',
    method: 'post',
    data: search
  })

export const saveTfamAssetCheckplanDetail = (tfamAssetCheckplanDetail) =>
  request({
    url: '/checkpaln/detail/tfamAssetCheckplanDetail/save',
    method: 'post',
    data: tfamAssetCheckplanDetail
  })

export const deleteTfamAssetCheckplanDetail = (tfamAssetCheckplanDetail) =>
  request({
    url: '/checkpaln/detail/tfamAssetCheckplanDetail/delete',
    method: 'post',
    data: tfamAssetCheckplanDetail
  })

export const bulkInsertTfamAssetCheckplanDetail = (tfamAssetCheckplanDetails) =>
  request({
    url: '/checkpaln/detail/tfamAssetCheckplanDetail/bulkInsert',
    method: 'post',
    data: tfamAssetCheckplanDetails
  })

export const bulkUpdateTfamAssetCheckplanDetail = (tfamAssetCheckplanDetails) =>
  request({
    url: '/checkpaln/detail/tfamAssetCheckplanDetail/bulkUpdate',
    method: 'post',
    data: tfamAssetCheckplanDetails
  })

export const bulkDeleteTfamAssetCheckplanDetail = (tfamAssetCheckplanDetails) =>
  request({
    url: '/checkpaln/detail/tfamAssetCheckplanDetail/bulkDelete',
    method: 'post',
    data: tfamAssetCheckplanDetails
  })
