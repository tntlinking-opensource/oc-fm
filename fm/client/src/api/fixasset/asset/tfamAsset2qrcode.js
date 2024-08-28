import request from '@/utils/request'

export const getTfamAsset2qrcodeById = (id) =>
  request({
    url: '/fixasset/asset/tfamAsset2qrcode/' + id,
    method: 'get'
  })

export const listTfamAsset2qrcodePage = (search) =>
  request({
    url: '/fixasset/asset/tfamAsset2qrcode/list',
    method: 'post',
    data: search
  })

export const listTfamAsset2qrcodeAll = (search) =>
  request({
    url: '/fixasset/asset/tfamAsset2qrcode/listAll',
    method: 'post',
    data: search
  })

export const saveTfamAsset2qrcode = (tfamAsset2qrcode) =>
  request({
    url: '/fixasset/asset/tfamAsset2qrcode/save',
    method: 'post',
    data: tfamAsset2qrcode
  })

export const deleteTfamAsset2qrcode = (tfamAsset2qrcode) =>
  request({
    url: '/fixasset/asset/tfamAsset2qrcode/delete',
    method: 'post',
    data: tfamAsset2qrcode
  })

export const bulkInsertTfamAsset2qrcode = (tfamAsset2qrcodes) =>
  request({
    url: '/fixasset/asset/tfamAsset2qrcode/bulkInsert',
    method: 'post',
    data: tfamAsset2qrcodes
  })

export const bulkUpdateTfamAsset2qrcode = (tfamAsset2qrcodes) =>
  request({
    url: '/fixasset/asset/tfamAsset2qrcode/bulkUpdate',
    method: 'post',
    data: tfamAsset2qrcodes
  })

export const bulkDeleteTfamAsset2qrcode = (tfamAsset2qrcodes) =>
  request({
    url: '/fixasset/asset/tfamAsset2qrcode/bulkDelete',
    method: 'post',
    data: tfamAsset2qrcodes
  })
