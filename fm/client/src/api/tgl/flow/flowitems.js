import request from '@/utils/request'

export const getFlowitemsById = (id) =>
  request({
    url: '/tgl/flow/flowitems/' + id,
    method: 'get'
  })

export const listFlowitemsPage = (search) =>
  request({
    url: '/tgl/flow/flowitems/list',
    method: 'post',
    data: search
  })

export const listFlowitemsAll = (search) =>
  request({
    url: '/tgl/flow/flowitems/listAll',
    method: 'post',
    data: search
  })

export const saveFlowitems = (flowitems) =>
  request({
    url: '/tgl/flow/flowitems/save',
    method: 'post',
    data: flowitems
  })

export const exportFlowItems = (formData) =>
  request({
    url: '/tgl/flow/flowitems/export',
    method: 'post',
    data: formData,
    responseType: 'blob'
  })

export const importFlowItems = (formData) =>
  request({
    url: '/tgl/flow/flowitems/import',
    method: 'post',
    data: formData
  })

export const deleteFlowitems = (flowitems) =>
  request({
    url: '/tgl/flow/flowitems/delete',
    method: 'post',
    data: flowitems
  })

export const bulkInsertFlowitems = (flowitemss) =>
  request({
    url: '/tgl/flow/flowitems/bulkInsert',
    method: 'post',
    data: flowitemss
  })

export const bulkUpdateFlowitems = (flowitemss) =>
  request({
    url: '/tgl/flow/flowitems/bulkUpdate',
    method: 'post',
    data: flowitemss
  })

export const bulkDeleteFlowitems = (flowitemss) =>
  request({
    url: '/tgl/flow/flowitems/bulkDelete',
    method: 'post',
    data: flowitemss
  })
