import request from '@/utils/request'

export const getPropValueById = (id) =>
  request({
    url: '/tgl/prop/propValue/' + id,
    method: 'get'
  })

export const getPropValueByCompany = (companyId) =>
  request({
    url: '/tgl/prop/propValue/getByCompany/' + companyId,
    method: 'get'
  })

export const listPropValuePage = (search) =>
  request({
    url: '/tgl/prop/propValue/list',
    method: 'post',
    data: search
  })

export const listPropValueAll = (search) =>
  request({
    url: '/tgl/prop/propValue/listAll',
    method: 'post',
    data: search
  })

export const savePropValue = (propValue) =>
  request({
    url: '/tgl/prop/propValue/save',
    method: 'post',
    data: propValue
  })

export const deletePropValue = (propValue) =>
  request({
    url: '/tgl/prop/propValue/delete',
    method: 'post',
    data: propValue
  })

export const bulkInsertPropValue = (propValues) =>
  request({
    url: '/tgl/prop/propValue/bulkInsert',
    method: 'post',
    data: propValues
  })

export const bulkUpdatePropValue = (propValues) =>
  request({
    url: '/tgl/prop/propValue/bulkUpdate',
    method: 'post',
    data: propValues
  })

export const bulkDeletePropValue = (propValues) =>
  request({
    url: '/tgl/prop/propValue/bulkDelete',
    method: 'post',
    data: propValues
  })
