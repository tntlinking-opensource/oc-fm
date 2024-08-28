import request from '@/utils/request'

export const getTglGroupAccountsById = (id) =>
  request({
    url: '/vouchermanager/tglGroupAccounts/' + id,
    method: 'get'
  })

export const listTglGroupAccountsPage = (search) =>
  request({
    url: '/vouchermanager/tglGroupAccounts/list',
    method: 'post',
    data: search
  })

export const listTglGroupAccountsAll = (search) =>
  request({
    url: '/vouchermanager/tglGroupAccounts/listAll',
    method: 'post',
    data: search
  })

export const saveTglGroupAccounts = (tglGroupAccounts) =>
  request({
    url: '/vouchermanager/tglGroupAccounts/save',
    method: 'post',
    data: tglGroupAccounts
  })

export const deleteTglGroupAccounts = (tglGroupAccounts) =>
  request({
    url: '/vouchermanager/tglGroupAccounts/delete',
    method: 'post',
    data: tglGroupAccounts
  })

export const bulkInsertTglGroupAccounts = (tglGroupAccountss) =>
  request({
    url: '/vouchermanager/tglGroupAccounts/bulkInsert',
    method: 'post',
    data: tglGroupAccountss
  })

export const bulkUpdateTglGroupAccounts = (tglGroupAccountss) =>
  request({
    url: '/vouchermanager/tglGroupAccounts/bulkUpdate',
    method: 'post',
    data: tglGroupAccountss
  })

export const bulkDeleteTglGroupAccounts = (tglGroupAccountss) =>
  request({
    url: '/vouchermanager/tglGroupAccounts/bulkDelete',
    method: 'post',
    data: tglGroupAccountss
  })
