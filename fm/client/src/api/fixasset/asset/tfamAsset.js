import request from '@/utils/request'

export const getTfamAssetById = (id) =>
  request({
    url: '/fixasset/asset/tfamAsset/' + id,
    method: 'get'
  })

export const listTfamAssetPage = (search) =>
  request({
    url: '/fixasset/asset/tfamAsset/list',
    method: 'post',
    data: search
  })

export const listTfamAssetAll = (search) =>
  request({
    url: '/fixasset/asset/tfamAsset/listAll',
    method: 'post',
    data: search
  })

export const saveTfamAsset = (tfamAsset) =>
  request({
    url: '/fixasset/asset/tfamAsset/saveAndUpdate',
    method: 'post',
    data: tfamAsset
  })

export const deleteTfamAsset = (tfamAsset) =>
  request({
    url: '/fixasset/asset/tfamAsset/delete',
    method: 'post',
    data: tfamAsset
  })

export const bulkInsertTfamAsset = (tfamAssets) =>
  request({
    url: '/fixasset/asset/tfamAsset/bulkInsert',
    method: 'post',
    data: tfamAssets
  })

export const bulkUpdateTfamAsset = (tfamAssets) =>
  request({
    url: '/fixasset/asset/tfamAsset/bulkUpdate',
    method: 'post',
    data: tfamAssets
  })

export const bulkDeleteTfamAsset = (tfamAssets) =>
  request({
    url: '/fixasset/asset/tfamAsset/bulkDelete',
    method: 'post',
    data: tfamAssets
  })

  // 汇总数据导出
export const countAssetExport = (tfamAssets) =>
  request({
    url: '/fixasset/asset/tfamAsset/countAssetExport',
    method: 'post',
    data: tfamAssets,
    responseType: 'blob'
  })

    // 明细汇总信息
export const countFun = (tfamAssets) =>
  request({
    url: '/fixasset/asset/tfamAsset/count',
    method: 'post',
    data: tfamAssets
  })
