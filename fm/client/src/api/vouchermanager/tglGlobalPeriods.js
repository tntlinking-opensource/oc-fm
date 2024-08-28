import request from '@/utils/request'

export const getTglGlobalPeriodsById = (id) =>
  request({
    url: '/vouchermanager/tglGlobalPeriods/' + id,
    method: 'get'
  })

export const listTglGlobalPeriodsPage = (search) =>
  request({
    url: '/vouchermanager/tglGlobalPeriods/list',
    method: 'post',
    data: search
  })

export const listTglGlobalPeriodsAll = (search) =>
  request({
    url: '/vouchermanager/tglGlobalPeriods/listAll',
    method: 'post',
    data: search
  })

export const saveTglGlobalPeriods = (tglGlobalPeriods) =>
  request({
    url: '/vouchermanager/tglGlobalPeriods/save',
    method: 'post',
    data: tglGlobalPeriods
  })

export const deleteTglGlobalPeriods = (tglGlobalPeriods) =>
  request({
    url: '/vouchermanager/tglGlobalPeriods/delete',
    method: 'post',
    data: tglGlobalPeriods
  })

export const bulkInsertTglGlobalPeriods = (tglGlobalPeriodss) =>
  request({
    url: '/vouchermanager/tglGlobalPeriods/bulkInsert',
    method: 'post',
    data: tglGlobalPeriodss
  })

export const bulkUpdateTglGlobalPeriods = (tglGlobalPeriodss) =>
  request({
    url: '/vouchermanager/tglGlobalPeriods/bulkUpdate',
    method: 'post',
    data: tglGlobalPeriodss
  })

export const bulkDeleteTglGlobalPeriods = (tglGlobalPeriodss) =>
  request({
    url: '/vouchermanager/tglGlobalPeriods/bulkDelete',
    method: 'post',
    data: tglGlobalPeriodss
  })
