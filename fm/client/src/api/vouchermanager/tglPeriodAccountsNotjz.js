import request from '@/utils/request'

export const getTglPeriodAccountsNotjzById = (id) =>
  request({
    url: '/vouchermanager/tglPeriodAccountsNotjz/' + id,
    method: 'get'
  })

export const listTglPeriodAccountsNotjzPage = (search) =>
  request({
    url: '/vouchermanager/tglPeriodAccountsNotjz/list',
    method: 'post',
    data: search
  })

export const listTglPeriodAccountsNotjzAll = (search) =>
  request({
    url: '/vouchermanager/tglPeriodAccountsNotjz/listAll',
    method: 'post',
    data: search
  })

export const saveTglPeriodAccountsNotjz = (tglPeriodAccountsNotjz) =>
  request({
    url: '/vouchermanager/tglPeriodAccountsNotjz/save',
    method: 'post',
    data: tglPeriodAccountsNotjz
  })

export const deleteTglPeriodAccountsNotjz = (tglPeriodAccountsNotjz) =>
  request({
    url: '/vouchermanager/tglPeriodAccountsNotjz/delete',
    method: 'post',
    data: tglPeriodAccountsNotjz
  })

export const bulkInsertTglPeriodAccountsNotjz = (tglPeriodAccountsNotjzs) =>
  request({
    url: '/vouchermanager/tglPeriodAccountsNotjz/bulkInsert',
    method: 'post',
    data: tglPeriodAccountsNotjzs
  })

export const bulkUpdateTglPeriodAccountsNotjz = (tglPeriodAccountsNotjzs) =>
  request({
    url: '/vouchermanager/tglPeriodAccountsNotjz/bulkUpdate',
    method: 'post',
    data: tglPeriodAccountsNotjzs
  })

export const bulkDeleteTglPeriodAccountsNotjz = (tglPeriodAccountsNotjzs) =>
  request({
    url: '/vouchermanager/tglPeriodAccountsNotjz/bulkDelete',
    method: 'post',
    data: tglPeriodAccountsNotjzs
  })
