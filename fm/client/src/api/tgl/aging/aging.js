import request from '@/utils/request'

export const getAgingById = (id) =>
  request({
    url: '/tgl/aging/aging/' + id,
    method: 'get'
  })

export const listAgingPage = (search) =>
  request({
    url: '/tgl/aging/aging/list',
    method: 'post',
    data: search
  })

export const listAgingAll = (search) =>
  request({
    url: '/tgl/aging/aging/listAll',
    method: 'post',
    data: search
  })

export const saveAging = (aging) =>
  request({
    url: '/tgl/aging/aging/save',
    method: 'post',
    data: aging
  })

export const deleteAging = (aging) =>
  request({
    url: '/tgl/aging/aging/delete',
    method: 'post',
    data: aging
  })

export const bulkInsertAging = (agings) =>
  request({
    url: '/tgl/aging/aging/bulkInsert',
    method: 'post',
    data: agings
  })

export const bulkUpdateAging = (agings) =>
  request({
    url: '/tgl/aging/aging/bulkUpdate',
    method: 'post',
    data: agings
  })

export const bulkDeleteAging = (agings) =>
  request({
    url: '/tgl/aging/aging/bulkDelete',
    method: 'post',
    data: agings
  })

export const bulkInsertAndUpdateAging = (agings) =>
  request({
    url: '/tgl/aging/aging/bulkInsertAndUpdate',
    method: 'post',
    data: agings
  })
