import request from '@/utils/request'

export const getTfamAssetCheckresultById = (id) =>
  request({
    url: '/checkplan/result/tfamAssetCheckresult/' + id,
    method: 'get'
  })

export const listTfamAssetCheckresultPage = (search) =>
  request({
    url: '/checkplan/result/tfamAssetCheckresult/list',
    method: 'post',
    data: search
  })

export const listTfamAssetCheckresultAll = (search) =>
  request({
    url: '/checkplan/result/tfamAssetCheckresult/listAll',
    method: 'post',
    data: search
  })

export const saveTfamAssetCheckresult = (tfamAssetCheckresult) =>
  request({
    url: '/checkplan/result/tfamAssetCheckresult/save',
    method: 'post',
    data: tfamAssetCheckresult
  })

export const deleteTfamAssetCheckresult = (tfamAssetCheckresult) =>
  request({
    url: '/checkplan/result/tfamAssetCheckresult/delete',
    method: 'post',
    data: tfamAssetCheckresult
  })

export const bulkInsertTfamAssetCheckresult = (tfamAssetCheckresults) =>
  request({
    url: '/checkplan/result/tfamAssetCheckresult/bulkInsert',
    method: 'post',
    data: tfamAssetCheckresults
  })

export const bulkUpdateTfamAssetCheckresult = (tfamAssetCheckresults) =>
  request({
    url: '/checkplan/result/tfamAssetCheckresult/bulkUpdate',
    method: 'post',
    data: tfamAssetCheckresults
  })

export const bulkDeleteTfamAssetCheckresult = (tfamAssetCheckresults) =>
  request({
    url: '/checkplan/result/tfamAssetCheckresult/bulkDelete',
    method: 'post',
    data: tfamAssetCheckresults
  })
