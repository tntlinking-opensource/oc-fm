import request from '@/utils/request'

export const getFlowtypeById = (id) =>
  request({
    url: '/tgl/flow/flowtype/' + id,
    method: 'get'
  })

export const listFlowtypePage = (search) =>
  request({
    url: '/tgl/flow/flowtype/list',
    method: 'post',
    data: search
  })

export const listFlowtypeAll = (search) =>
  request({
    url: '/tgl/flow/flowtype/listAll',
    method: 'post',
    data: search
  })

export const treeFlowtype = (search) =>
  request({
    url: '/tgl/flow/flowtype/tree',
    method: 'post',
    data: search
  })

export const saveFlowtype = (flowtype) =>
  request({
    url: '/tgl/flow/flowtype/save',
    method: 'post',
    data: flowtype
  })

export const deleteFlowtype = (flowtype) =>
  request({
    url: '/tgl/flow/flowtype/delete',
    method: 'post',
    data: flowtype
  })

export const bulkInsertFlowtype = (flowtypes) =>
  request({
    url: '/tgl/flow/flowtype/bulkInsert',
    method: 'post',
    data: flowtypes
  })

export const bulkUpdateFlowtype = (flowtypes) =>
  request({
    url: '/tgl/flow/flowtype/bulkUpdate',
    method: 'post',
    data: flowtypes
  })

export const bulkDeleteFlowtype = (flowtypes) =>
  request({
    url: '/tgl/flow/flowtype/bulkDelete',
    method: 'post',
    data: flowtypes
  })
