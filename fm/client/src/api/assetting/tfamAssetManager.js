import request from '@/utils/request'

export const getTfamAssetManagerById = (id) =>
  request({
    url: '/assetting/tfamAssetManager/' + id,
    method: 'get'
  })
export const getlistmenu = (id) =>
  request({
    url: '/assetting/tfamAssetManager/listmenu/' + id,
    method: 'post'
  })

export const listTfamAssetManagerPage = (search) =>
  request({
    url: '/assetting/tfamAssetManager/list',
    method: 'post',
    data: search
  })

export const listTfamAssetManagerAll = (search) =>
  request({
    url: '/assetting/tfamAssetManager/listAll',
    method: 'post',
    data: search
  })

export const saveTfamAssetManager = (tfamAssetManager) =>
  request({
    url: '/assetting/tfamAssetManager/save',
    method: 'post',
    data: tfamAssetManager
  })
export const addmenu = (jsonCondition) =>
  request({
    url: '/assetting/tfamAssetManager/addmenu',
    method: 'post',
    data: jsonCondition
  })

export const deleteTfamAssetManager = (tfamAssetManager) =>
  request({
    url: '/assetting/tfamAssetManager/delete',
    method: 'post',
    data: tfamAssetManager
  })

export const bulkInsertTfamAssetManager = (tfamAssetManagers) =>
  request({
    url: '/assetting/tfamAssetManager/bulkInsert',
    method: 'post',
    data: tfamAssetManagers
  })

export const bulkUpdateTfamAssetManager = (tfamAssetManagers) =>
  request({
    url: '/assetting/tfamAssetManager/bulkUpdate',
    method: 'post',
    data: tfamAssetManagers
  })

export const bulkDeleteTfamAssetManager = (tfamAssetManagers) =>
  request({
    url: '/assetting/tfamAssetManager/bulkDelete',
    method: 'post',
    data: tfamAssetManagers
  })
