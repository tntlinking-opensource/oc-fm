import request from '@/utils/request'

export const getTfamAssetCheckplanMainDeptById = (id) =>
  request({
    url: '/checkplan/dept/tfamAssetCheckplanMainDept/' + id,
    method: 'get'
  })

export const listTfamAssetCheckplanMainDeptPage = (search) =>
  request({
    url: '/checkplan/dept/tfamAssetCheckplanMainDept/list',
    method: 'post',
    data: search
  })

export const listTfamAssetCheckplanMainDeptAll = (search) =>
  request({
    url: '/checkplan/dept/tfamAssetCheckplanMainDept/listAll',
    method: 'post',
    data: search
  })

export const saveTfamAssetCheckplanMainDept = (tfamAssetCheckplanMainDept) =>
  request({
    url: '/checkplan/dept/tfamAssetCheckplanMainDept/save',
    method: 'post',
    data: tfamAssetCheckplanMainDept
  })

export const deleteTfamAssetCheckplanMainDept = (tfamAssetCheckplanMainDept) =>
  request({
    url: '/checkplan/dept/tfamAssetCheckplanMainDept/delete',
    method: 'post',
    data: tfamAssetCheckplanMainDept
  })

export const bulkInsertTfamAssetCheckplanMainDept = (tfamAssetCheckplanMainDepts) =>
  request({
    url: '/checkplan/dept/tfamAssetCheckplanMainDept/bulkInsert',
    method: 'post',
    data: tfamAssetCheckplanMainDepts
  })

export const bulkUpdateTfamAssetCheckplanMainDept = (tfamAssetCheckplanMainDepts) =>
  request({
    url: '/checkplan/dept/tfamAssetCheckplanMainDept/bulkUpdate',
    method: 'post',
    data: tfamAssetCheckplanMainDepts
  })

export const bulkDeleteTfamAssetCheckplanMainDept = (tfamAssetCheckplanMainDepts) =>
  request({
    url: '/checkplan/dept/tfamAssetCheckplanMainDept/bulkDelete',
    method: 'post',
    data: tfamAssetCheckplanMainDepts
  })
