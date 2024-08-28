import request from '@/utils/request'

export const getTfamAssetHistroyById = (id) =>
  request({
    url: '/fixasset/asset/tfamAssetHistroy/' + id,
    method: 'get'
  })

export const listTfamAssetHistroyPage = (search) =>
  request({
    url: '/fixasset/asset/tfamAssetHistroy/list',
    method: 'post',
    data: search
  })

export const listTfamAssetHistroyAll = (search) =>
  request({
    url: '/fixasset/asset/tfamAssetHistroy/listAll',
    method: 'post',
    data: search
  })

export const saveTfamAssetHistroy = (tfamAssetHistroy) =>
  request({
    url: '/fixasset/asset/tfamAssetHistroy/save',
    method: 'post',
    data: tfamAssetHistroy
  })

export const deleteTfamAssetHistroy = (tfamAssetHistroy) =>
  request({
    url: '/fixasset/asset/tfamAssetHistroy/delete',
    method: 'post',
    data: tfamAssetHistroy
  })

export const bulkInsertTfamAssetHistroy = (tfamAssetHistroys) =>
  request({
    url: '/fixasset/asset/tfamAssetHistroy/bulkInsert',
    method: 'post',
    data: tfamAssetHistroys
  })

export const bulkUpdateTfamAssetHistroy = (tfamAssetHistroys) =>
  request({
    url: '/fixasset/asset/tfamAssetHistroy/bulkUpdate',
    method: 'post',
    data: tfamAssetHistroys
  })

export const bulkDeleteTfamAssetHistroy = (tfamAssetHistroys) =>
  request({
    url: '/fixasset/asset/tfamAssetHistroy/bulkDelete',
    method: 'post',
    data: tfamAssetHistroys
  })
