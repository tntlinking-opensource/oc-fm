import request from '@/utils/request'

export const getTglAccountsXjllById = (id) =>
  request({
    url: '/vouchermanager/tglAccountsXjll/' + id,
    method: 'get'
  })

export const listTglAccountsXjllPage = (search) =>
  request({
    url: '/vouchermanager/tglAccountsXjll/list',
    method: 'post',
    data: search
  })

export const listTglAccountsXjllAll = (search) =>
  request({
    url: '/vouchermanager/tglAccountsXjll/listAll',
    method: 'post',
    data: search
  })

export const saveTglAccountsXjll = (tglAccountsXjll) =>
  request({
    url: '/vouchermanager/tglAccountsXjll/save',
    method: 'post',
    data: tglAccountsXjll
  })

export const deleteTglAccountsXjll = (tglAccountsXjll) =>
  request({
    url: '/vouchermanager/tglAccountsXjll/delete',
    method: 'post',
    data: tglAccountsXjll
  })

export const bulkInsertTglAccountsXjll = (tglAccountsXjlls) =>
  request({
    url: '/vouchermanager/tglAccountsXjll/bulkInsert',
    method: 'post',
    data: tglAccountsXjlls
  })

export const bulkUpdateTglAccountsXjll = (tglAccountsXjlls) =>
  request({
    url: '/vouchermanager/tglAccountsXjll/bulkUpdate',
    method: 'post',
    data: tglAccountsXjlls
  })

export const bulkDeleteTglAccountsXjll = (tglAccountsXjlls) =>
  request({
    url: '/vouchermanager/tglAccountsXjll/bulkDelete',
    method: 'post',
    data: tglAccountsXjlls
  })
