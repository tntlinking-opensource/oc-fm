import request from '@/utils/request'

export const getTfamAssetManagerPowerById = (id) =>
  request({
    url: '/assetting/tfamAssetManagerPower/' + id,
    method: 'get'
  })

export const listTfamAssetManagerPowerPage = (search) =>
  request({
    url: '/assetting/tfamAssetManagerPower/list',
    method: 'post',
    data: search
  })

export const listTfamAssetManagerPowerAll = (search) =>
  request({
    url: '/assetting/tfamAssetManagerPower/listAll',
    method: 'post',
    data: search
  })

export const saveTfamAssetManagerPower = (tfamAssetManagerPower) =>
  request({
    url: '/assetting/tfamAssetManagerPower/save',
    method: 'post',
    data: tfamAssetManagerPower
  })

export const deleteTfamAssetManagerPower = (tfamAssetManagerPower) =>
  request({
    url: '/assetting/tfamAssetManagerPower/delete',
    method: 'post',
    data: tfamAssetManagerPower
  })

export const bulkInsertTfamAssetManagerPower = (tfamAssetManagerPowers) =>
  request({
    url: '/assetting/tfamAssetManagerPower/bulkInsert',
    method: 'post',
    data: tfamAssetManagerPowers
  })

export const bulkUpdateTfamAssetManagerPower = (tfamAssetManagerPowers) =>
  request({
    url: '/assetting/tfamAssetManagerPower/bulkUpdate',
    method: 'post',
    data: tfamAssetManagerPowers
  })

export const bulkDeleteTfamAssetManagerPower = (tfamAssetManagerPowers) =>
  request({
    url: '/assetting/tfamAssetManagerPower/bulkDelete',
    method: 'post',
    data: tfamAssetManagerPowers
  })
