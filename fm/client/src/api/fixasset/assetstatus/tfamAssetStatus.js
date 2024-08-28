import request from '@/utils/request'

export const getTfamAssetStatusById = (id) =>
  request({
    url: '/fixasset/assetstatus/tfamAssetStatus/' + id,
    method: 'get'
  })

export const listTfamAssetStatusPage = (search) =>
  request({
    url: '/fixasset/assetstatus/tfamAssetStatus/list',
    method: 'post',
    data: search
  })

export const listTfamAssetStatusAll = (search) =>
  request({
    url: '/fixasset/assetstatus/tfamAssetStatus/listAll',
    method: 'post',
    data: search
  })

export const saveTfamAssetStatus = (tfamAssetStatus) =>
  request({
    url: '/fixasset/assetstatus/tfamAssetStatus/save',
    method: 'post',
    data: tfamAssetStatus
  })

export const deleteTfamAssetStatus = (tfamAssetStatus) =>
  request({
    url: '/fixasset/assetstatus/tfamAssetStatus/delete',
    method: 'post',
    data: tfamAssetStatus
  })

export const bulkInsertTfamAssetStatus = (tfamAssetStatuss) =>
  request({
    url: '/fixasset/assetstatus/tfamAssetStatus/bulkInsert',
    method: 'post',
    data: tfamAssetStatuss
  })

export const bulkUpdateTfamAssetStatus = (tfamAssetStatuss) =>
  request({
    url: '/fixasset/assetstatus/tfamAssetStatus/bulkUpdate',
    method: 'post',
    data: tfamAssetStatuss
  })

export const bulkDeleteTfamAssetStatus = (tfamAssetStatuss) =>
  request({
    url: '/fixasset/assetstatus/tfamAssetStatus/bulkDelete',
    method: 'post',
    data: tfamAssetStatuss
  })
