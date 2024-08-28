import request from '@/utils/request'

export const getTfamStatusById = (id) =>
  request({
    url: '/fixasset/status/tfamStatus/' + id,
    method: 'get'
  })

export const listTfamStatusPage = (search) =>
  request({
    url: '/fixasset/status/tfamStatus/list',
    method: 'post',
    data: search
  })

export const listTfamStatusAll = (search) =>
  request({
    url: '/fixasset/status/tfamStatus/listAll',
    method: 'post',
    data: search
  })

export const saveTfamStatus = (tfamStatus) =>
  request({
    url: '/fixasset/status/tfamStatus/save',
    method: 'post',
    data: tfamStatus
  })

export const deleteTfamStatus = (tfamStatus) =>
  request({
    url: '/fixasset/status/tfamStatus/delete',
    method: 'post',
    data: tfamStatus
  })

export const bulkInsertTfamStatus = (tfamStatuss) =>
  request({
    url: '/fixasset/status/tfamStatus/bulkInsert',
    method: 'post',
    data: tfamStatuss
  })

export const bulkUpdateTfamStatus = (tfamStatuss) =>
  request({
    url: '/fixasset/status/tfamStatus/bulkUpdate',
    method: 'post',
    data: tfamStatuss
  })

export const bulkDeleteTfamStatus = (tfamStatuss) =>
  request({
    url: '/fixasset/status/tfamStatus/bulkDelete',
    method: 'post',
    data: tfamStatuss
  })
