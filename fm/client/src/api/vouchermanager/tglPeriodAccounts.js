import request from '@/utils/request'

export const getTglPeriodAccountsById = (id) =>
  request({
    url: '/vouchermanager/tglPeriodAccounts/' + id,
    method: 'get'
  })

export const listTglPeriodAccountsPage = (search) =>
  request({
    url: '/vouchermanager/tglPeriodAccounts/list',
    method: 'post',
    data: search
  })

export const listTglPeriodAccountsAll = (search) =>
  request({
    url: '/vouchermanager/tglPeriodAccounts/listAll',
    method: 'post',
    data: search
  })

export const saveTglPeriodAccounts = (tglPeriodAccounts) =>
  request({
    url: '/vouchermanager/tglPeriodAccounts/save',
    method: 'post',
    data: tglPeriodAccounts
  })

export const deleteTglPeriodAccounts = (tglPeriodAccounts) =>
  request({
    url: '/vouchermanager/tglPeriodAccounts/delete',
    method: 'post',
    data: tglPeriodAccounts
  })

export const bulkInsertTglPeriodAccounts = (tglPeriodAccountss) =>
  request({
    url: '/vouchermanager/tglPeriodAccounts/bulkInsert',
    method: 'post',
    data: tglPeriodAccountss
  })

export const bulkUpdateTglPeriodAccounts = (tglPeriodAccountss) =>
  request({
    url: '/vouchermanager/tglPeriodAccounts/bulkUpdate',
    method: 'post',
    data: tglPeriodAccountss
  })

export const bulkDeleteTglPeriodAccounts = (tglPeriodAccountss) =>
  request({
    url: '/vouchermanager/tglPeriodAccounts/bulkDelete',
    method: 'post',
    data: tglPeriodAccountss
  })
export const exportTglPeriodAccounts = (formData) =>
  request({
    url: '/vouchermanager/tglPeriodAccounts/exportTglPeriodAccounts',
    method: 'post',
    data: formData,
    responseType: 'blob'
  })

