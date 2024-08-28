import request from '@/utils/request'

export const getTfamAssetManagerDeptById = (id) =>
  request({
    url: '/assetting/tfamAssetManagerDept/' + id,
    method: 'get'
  })

export const listTfamAssetManagerDeptPage = (search) =>
  request({
    url: '/assetting/tfamAssetManagerDept/list',
    method: 'post',
    data: search
  })

export const listTfamAssetManagerDeptAll = (search) =>
  request({
    url: '/assetting/tfamAssetManagerDept/listAll',
    method: 'post',
    data: search
  })

export const saveTfamAssetManagerDept = (tfamAssetManagerDept) =>
  request({
    url: '/assetting/tfamAssetManagerDept/save',
    method: 'post',
    data: tfamAssetManagerDept
  })
export const adddept = (tfamAssetManagerDept) =>
  request({
    url: '/assetting/tfamAssetManagerDept/adddept',
    method: 'post',
    data: tfamAssetManagerDept
  })

export const deleteTfamAssetManagerDept = (tfamAssetManagerDept) =>
  request({
    url: '/assetting/tfamAssetManagerDept/delete',
    method: 'post',
    data: tfamAssetManagerDept
  })

export const bulkInsertTfamAssetManagerDept = (tfamAssetManagerDepts) =>
  request({
    url: '/assetting/tfamAssetManagerDept/bulkInsert',
    method: 'post',
    data: tfamAssetManagerDepts
  })

export const bulkUpdateTfamAssetManagerDept = (tfamAssetManagerDepts) =>
  request({
    url: '/assetting/tfamAssetManagerDept/bulkUpdate',
    method: 'post',
    data: tfamAssetManagerDepts
  })

export const bulkDeleteTfamAssetManagerDept = (tfamAssetManagerDepts) =>
  request({
    url: '/assetting/tfamAssetManagerDept/bulkDelete',
    method: 'post',
    data: tfamAssetManagerDepts
  })
