import request from '@/utils/request'

export const getTfamAssetTransferById = (id) =>
  request({
    url: '/fixasset/asset/tfamAssetTransfer/' + id,
    method: 'get'
  })
export const getVoucherId = (id) =>
  request({
    url: '/fixasset/asset/tfamAssetTransfer/getVoucherId/' + id,
    method: 'get'
  })

export const listTfamAssetTransferPage = (search) =>
  request({
    url: '/fixasset/asset/tfamAssetTransfer/list',
    method: 'post',
    data: search
  })

export const listTfamAssetTransferAll = (search) =>
  request({
    url: '/fixasset/asset/tfamAssetTransfer/listAll',
    method: 'post',
    data: search
  })

export const saveTfamAssetTransfer = (tfamAssetTransfer) =>
  request({
    url: '/fixasset/asset/tfamAssetTransfer/save',
    method: 'post',
    data: tfamAssetTransfer
  })

export const deleteTfamAssetTransfer = (tfamAssetTransfer) =>
  request({
    url: '/fixasset/asset/tfamAssetTransfer/delete',
    method: 'post',
    data: tfamAssetTransfer
  })

export const bulkInsertTfamAssetTransfer = (tfamAssetTransfers) =>
  request({
    url: '/fixasset/asset/tfamAssetTransfer/bulkInsert',
    method: 'post',
    data: tfamAssetTransfers
  })

export const bulkUpdateTfamAssetTransfer = (tfamAssetTransfers) =>
  request({
    url: '/fixasset/asset/tfamAssetTransfer/bulkUpdate',
    method: 'post',
    data: tfamAssetTransfers
  })

export const bulkDeleteTfamAssetTransfer = (tfamAssetTransfers) =>
  request({
    url: '/fixasset/asset/tfamAssetTransfer/bulkDelete',
    method: 'post',
    data: tfamAssetTransfers
  })

  // 查询默认配置
  export const listTfamDefaultSettingsDefault = (search) =>
  request({
    url: '/assetting/tfamDefaultSettings/listDefault',
    method: 'post',
    data: search
  })

  // 导出
  export const listTfamAssetExport= (search) =>
  request({
    url: '/fixasset/asset/tfamAsset/export',
    method: 'post',
    data: search,
    responseType: 'blob'
  })

  // 导入
  export const listTfamAssetImport = (search) =>
  request({
    url: '/fixasset/asset/tfamAsset/import',
    method: 'post',
    data: search
  })

  // 审核
  export const listTfamAssetAudit = (search) =>
  request({
    url: '/fixasset/asset/tfamAsset/audit',
    method: 'post',
    data: search
  })

  // 二维码
  export const printQRCode = (search) =>
  request({
    url: '/fixasset/asset/tfamAsset/printQRCode',
    method: 'post',
    data: search
  })

  // 生命周期
  export const lifeCircle = (search) =>
  request({
    url: '/fixasset/asset/tfamAsset/lifeCircle',
    method: 'post',
    data: search
  })

   // 调拨
  export const assetDiaoboTransfer = (search) =>
  request({
    url: '/fixasset/asset/tfamAsset/assetTransfer',
    method: 'post',
    data: search
  })

  // 数据汇总
  export const countAssetTransfer = (search) =>
  request({
    url: '/fixasset/asset/tfamAsset/countAsset',
    method: 'post',
    data: search
  })
