import request from '@/utils/request'

export const getTfamChangeWayById = (id) =>
  request({
    url: '/fixasset/changeway/tfamChangeWay/' + id,
    method: 'get'
  })

export const listTfamChangeWayPage = (search) =>
  request({
    url: '/fixasset/changeway/tfamChangeWay/list',
    method: 'post',
    data: search
  })

export const listTfamChangeWayAll = (search) =>
  request({
    url: '/fixasset/changeway/tfamChangeWay/listAll',
    method: 'post',
    data: search
  })

export const saveTfamChangeWay = (tfamChangeWay) =>
  request({
    url: '/fixasset/changeway/tfamChangeWay/save',
    method: 'post',
    data: tfamChangeWay
  })

export const deleteTfamChangeWay = (tfamChangeWay) =>
  request({
    url: '/fixasset/changeway/tfamChangeWay/delete',
    method: 'post',
    data: tfamChangeWay
  })

export const bulkInsertTfamChangeWay = (tfamChangeWays) =>
  request({
    url: '/fixasset/changeway/tfamChangeWay/bulkInsert',
    method: 'post',
    data: tfamChangeWays
  })

export const bulkUpdateTfamChangeWay = (tfamChangeWays) =>
  request({
    url: '/fixasset/changeway/tfamChangeWay/bulkUpdate',
    method: 'post',
    data: tfamChangeWays
  })

export const bulkDeleteTfamChangeWay = (tfamChangeWays) =>
  request({
    url: '/fixasset/changeway/tfamChangeWay/bulkDelete',
    method: 'post',
    data: tfamChangeWays
  })
export const exportTfamChangeWay = (formData) =>
  request({
    url: '/fixasset/changeway/tfamChangeWay/exportTfamChangeWay',
    method: 'post',
    data: formData,
    responseType: 'blob'
  })
export const importTfamChangeWay = (formData) =>
  request({
    url: '/fixasset/changeway/tfamChangeWay/importTfamChangeWay',
    method: 'post',
    data: formData
  })
