import request from '@/utils/request'

export const getTglCompanyLedgerPeriodsById = (id) =>
  request({
    url: '/vouchermanager/tglCompanyLedgerPeriods/' + id,
    method: 'get'
  })

export const listTglCompanyLedgerPeriodsPage = (search) =>
  request({
    url: '/vouchermanager/tglCompanyLedgerPeriods/list',
    method: 'post',
    data: search
  })

export const listTglCompanyLedgerPeriodsAll = (search) =>
  request({
    url: '/vouchermanager/tglCompanyLedgerPeriods/listAll',
    method: 'post',
    data: search
  })

export const saveTglCompanyLedgerPeriods = (tglCompanyLedgerPeriods) =>
  request({
    url: '/vouchermanager/tglCompanyLedgerPeriods/save',
    method: 'post',
    data: tglCompanyLedgerPeriods
  })

export const deleteTglCompanyLedgerPeriods = (tglCompanyLedgerPeriods) =>
  request({
    url: '/vouchermanager/tglCompanyLedgerPeriods/delete',
    method: 'post',
    data: tglCompanyLedgerPeriods
  })

export const bulkInsertTglCompanyLedgerPeriods = (tglCompanyLedgerPeriodss) =>
  request({
    url: '/vouchermanager/tglCompanyLedgerPeriods/bulkInsert',
    method: 'post',
    data: tglCompanyLedgerPeriodss
  })

export const bulkUpdateTglCompanyLedgerPeriods = (tglCompanyLedgerPeriodss) =>
  request({
    url: '/vouchermanager/tglCompanyLedgerPeriods/bulkUpdate',
    method: 'post',
    data: tglCompanyLedgerPeriodss
  })

export const bulkDeleteTglCompanyLedgerPeriods = (tglCompanyLedgerPeriodss) =>
  request({
    url: '/vouchermanager/tglCompanyLedgerPeriods/bulkDelete',
    method: 'post',
    data: tglCompanyLedgerPeriodss
  })
