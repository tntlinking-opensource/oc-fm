import request from '@/utils/request'

export const getTfamAssetCheckplanMainAttachById = (id) =>
  request({
    url: '/checkplan/attach/tfamAssetCheckplanMainAttach/' + id,
    method: 'get'
  })

export const listTfamAssetCheckplanMainAttachPage = (search) =>
  request({
    url: '/checkplan/attach/tfamAssetCheckplanMainAttach/list',
    method: 'post',
    data: search
  })

export const listTfamAssetCheckplanMainAttachAll = (search) =>
  request({
    url: '/checkplan/attach/tfamAssetCheckplanMainAttach/listAll',
    method: 'post',
    data: search
  })

export const saveTfamAssetCheckplanMainAttach = (tfamAssetCheckplanMainAttach) =>
  request({
    url: '/checkplan/attach/tfamAssetCheckplanMainAttach/save',
    method: 'post',
    data: tfamAssetCheckplanMainAttach
  })

export const deleteTfamAssetCheckplanMainAttach = (tfamAssetCheckplanMainAttach) =>
  request({
    url: '/checkplan/attach/tfamAssetCheckplanMainAttach/delete',
    method: 'post',
    data: tfamAssetCheckplanMainAttach
  })

export const bulkInsertTfamAssetCheckplanMainAttach = (tfamAssetCheckplanMainAttachs) =>
  request({
    url: '/checkplan/attach/tfamAssetCheckplanMainAttach/bulkInsert',
    method: 'post',
    data: tfamAssetCheckplanMainAttachs
  })

export const bulkUpdateTfamAssetCheckplanMainAttach = (tfamAssetCheckplanMainAttachs) =>
  request({
    url: '/checkplan/attach/tfamAssetCheckplanMainAttach/bulkUpdate',
    method: 'post',
    data: tfamAssetCheckplanMainAttachs
  })

export const bulkDeleteTfamAssetCheckplanMainAttach = (tfamAssetCheckplanMainAttachs) =>
  request({
    url: '/checkplan/attach/tfamAssetCheckplanMainAttach/bulkDelete',
    method: 'post',
    data: tfamAssetCheckplanMainAttachs
  })
