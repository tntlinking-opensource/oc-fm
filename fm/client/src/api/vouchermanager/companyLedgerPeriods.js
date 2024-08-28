import request from '@/utils/request'

export const getCompanyLedgerPeriodsById = (id) =>
  request({
    url: '/vouchermanager/companyLedgerPeriods/' + id,
    method: 'get'
  })

export const getCompanyLedgerPeriodsByAccount = (search) =>
  request({
    url: '/vouchermanager/companyLedgerPeriods/getByAccount',
    method: 'post',
    data: search
  })

export const listCompanyLedgerPeriodsPage = (search) =>
  request({
    url: '/vouchermanager/companyLedgerPeriods/list',
    method: 'post',
    data: search
  })

export const listCompanyLedgerPeriodsAll = (search) =>
  request({
    url: '/vouchermanager/companyLedgerPeriods/listAll',
    method: 'post',
    data: search
  })

export const exportCompanyLedgerPeriods = (formData) =>
  request({
    url: '/vouchermanager/companyLedgerPeriods/export',
    method: 'post',
    data: formData,
    responseType: 'blob'
  })

export const importCompanyLedgerPeriods = (formData) =>
  request({
    url: '/vouchermanager/companyLedgerPeriods/import',
    method: 'post',
    data: formData
  })

export const saveCompanyLedgerPeriods = (companyLedgerPeriods) =>
  request({
    url: '/vouchermanager/companyLedgerPeriods/save',
    method: 'post',
    data: companyLedgerPeriods
  })

export const deleteCompanyLedgerPeriods = (companyLedgerPeriods) =>
  request({
    url: '/vouchermanager/companyLedgerPeriods/delete',
    method: 'post',
    data: companyLedgerPeriods
  })

export const bulkInsertCompanyLedgerPeriods = (companyLedgerPeriodss) =>
  request({
    url: '/vouchermanager/companyLedgerPeriods/bulkInsert',
    method: 'post',
    data: companyLedgerPeriodss
  })

export const bulkUpdateCompanyLedgerPeriods = (companyLedgerPeriodss) =>
  request({
    url: '/vouchermanager/companyLedgerPeriods/bulkUpdate',
    method: 'post',
    data: companyLedgerPeriodss
  })

export const bulkDeleteCompanyLedgerPeriods = (companyLedgerPeriodss) =>
  request({
    url: '/vouchermanager/companyLedgerPeriods/bulkDelete',
    method: 'post',
    data: companyLedgerPeriodss
  })
