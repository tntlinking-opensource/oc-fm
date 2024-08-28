import request from '@/utils/request'

export const getTfamDepmonthById = (id) =>
  request({
    url: '/assetting/tfamDepmonth/' + id,
    method: 'get'
  })

export const listTfamDepmonthPage = (search) =>
  request({
    url: '/assetting/tfamDepmonth/list',
    method: 'post',
    data: search
  })

export const listTfamDepmonthAll = (search) =>
  request({
    url: '/assetting/tfamDepmonth/listAll',
    method: 'post',
    data: search
  })

export const saveTfamDepmonth = (tfamDepmonth) =>
  request({
    url: '/assetting/tfamDepmonth/save',
    method: 'post',
    data: tfamDepmonth
  })

export const deleteTfamDepmonth = (tfamDepmonth) =>
  request({
    url: '/assetting/tfamDepmonth/delete',
    method: 'post',
    data: tfamDepmonth
  })

export const bulkInsertTfamDepmonth = (tfamDepmonths) =>
  request({
    url: '/assetting/tfamDepmonth/bulkInsert',
    method: 'post',
    data: tfamDepmonths
  })

export const bulkUpdateTfamDepmonth = (tfamDepmonths) =>
  request({
    url: '/assetting/tfamDepmonth/bulkUpdate',
    method: 'post',
    data: tfamDepmonths
  })

export const bulkDeleteTfamDepmonth = (tfamDepmonths) =>
  request({
    url: '/assetting/tfamDepmonth/bulkDelete',
    method: 'post',
    data: tfamDepmonths
  })
