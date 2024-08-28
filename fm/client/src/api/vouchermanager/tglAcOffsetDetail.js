import request from '@/utils/request'

export const getTglAcOffsetDetailById = (id) =>
  request({
    url: '/vouchermanager/tglAcOffsetDetail/' + id,
    method: 'get'
  })

export const listTglAcOffsetDetailPage = (search) =>
  request({
    url: '/vouchermanager/tglAcOffsetDetail/list',
    method: 'post',
    data: search
  })

export const listTglAcOffsetDetailAll = (search) =>
  request({
    url: '/vouchermanager/tglAcOffsetDetail/listAll',
    method: 'post',
    data: search
  })

export const saveTglAcOffsetDetail = (tglAcOffsetDetail) =>
  request({
    url: '/vouchermanager/tglAcOffsetDetail/save',
    method: 'post',
    data: tglAcOffsetDetail
  })

export const deleteTglAcOffsetDetail = (tglAcOffsetDetail) =>
  request({
    url: '/vouchermanager/tglAcOffsetDetail/delete',
    method: 'post',
    data: tglAcOffsetDetail
  })

export const bulkInsertTglAcOffsetDetail = (tglAcOffsetDetails) =>
  request({
    url: '/vouchermanager/tglAcOffsetDetail/bulkInsert',
    method: 'post',
    data: tglAcOffsetDetails
  })

export const bulkUpdateTglAcOffsetDetail = (tglAcOffsetDetails) =>
  request({
    url: '/vouchermanager/tglAcOffsetDetail/bulkUpdate',
    method: 'post',
    data: tglAcOffsetDetails
  })

export const bulkDeleteTglAcOffsetDetail = (tglAcOffsetDetails) =>
  request({
    url: '/vouchermanager/tglAcOffsetDetail/bulkDelete',
    method: 'post',
    data: tglAcOffsetDetails
  })
