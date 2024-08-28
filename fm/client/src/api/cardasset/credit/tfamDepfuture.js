import request from '@/utils/request'

export const getTfamDepfutureById = (id) =>
  request({
    url: '/cardasset/credit/tfamDepfuture/' + id,
    method: 'get'
  })

export const listTfamDepfuturePage = (search) =>
  request({
    url: '/cardasset/credit/tfamDepfuture/list',
    method: 'post',
    data: search
  })

export const listTfamDepfutureAll = (search) =>
  request({
    url: '/cardasset/credit/tfamDepfuture/listAll',
    method: 'post',
    data: search
  })

export const saveTfamDepfuture = (tfamDepfuture) =>
  request({
    url: '/cardasset/credit/tfamDepfuture/save',
    method: 'post',
    data: tfamDepfuture
  })

export const deleteTfamDepfuture = (tfamDepfuture) =>
  request({
    url: '/cardasset/credit/tfamDepfuture/delete',
    method: 'post',
    data: tfamDepfuture
  })

export const bulkInsertTfamDepfuture = (tfamDepfutures) =>
  request({
    url: '/cardasset/credit/tfamDepfuture/bulkInsert',
    method: 'post',
    data: tfamDepfutures
  })

export const bulkUpdateTfamDepfuture = (tfamDepfutures) =>
  request({
    url: '/cardasset/credit/tfamDepfuture/bulkUpdate',
    method: 'post',
    data: tfamDepfutures
  })

export const bulkDeleteTfamDepfuture = (tfamDepfutures) =>
  request({
    url: '/cardasset/credit/tfamDepfuture/bulkDelete',
    method: 'post',
    data: tfamDepfutures
  })
