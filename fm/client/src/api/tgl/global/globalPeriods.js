import request from '@/utils/request'

export const getGlobalPeriodsById = (id) =>
  request({
    url: '/tgl/global/globalPeriods/' + id,
    method: 'get'
  })

export const listGlobalPeriodsPage = (search) =>
  request({
    url: '/tgl/global/globalPeriods/list',
    method: 'post',
    data: search
  })

export const listGlobalPeriodsAll = (search) =>
  request({
    url: '/tgl/global/globalPeriods/listAll',
    method: 'post',
    data: search
  })

export const saveGlobalPeriods = (globalPeriods) =>
  request({
    url: '/tgl/global/globalPeriods/save',
    method: 'post',
    data: globalPeriods
  })

export const deleteGlobalPeriods = (globalPeriods) =>
  request({
    url: '/tgl/global/globalPeriods/delete',
    method: 'post',
    data: globalPeriods
  })

export const bulkInsertGlobalPeriods = (globalPeriodss) =>
  request({
    url: '/tgl/global/globalPeriods/bulkInsert',
    method: 'post',
    data: globalPeriodss
  })

export const bulkUpdateGlobalPeriods = (globalPeriodss) =>
  request({
    url: '/tgl/global/globalPeriods/bulkUpdate',
    method: 'post',
    data: globalPeriodss
  })

export const bulkDeleteGlobalPeriods = (globalPeriodss) =>
  request({
    url: '/tgl/global/globalPeriods/bulkDelete',
    method: 'post',
    data: globalPeriodss
  })

export const selectPeriod = (search) =>
  request({
    url: '/tgl/global/globalPeriods/selectPeriod',
    method: 'post',
    data: search
  })
