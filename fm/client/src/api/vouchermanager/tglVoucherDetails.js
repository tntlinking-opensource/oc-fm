import request from '@/utils/request'

export const getTglVoucherDetailsById = (id) =>
  request({
    url: '/vouchermanager/tglVoucherDetails/' + id,
    method: 'get'
  })

export const listTglVoucherDetailsPage = (search) =>
  request({
    url: '/vouchermanager/tglVoucherDetails/list',
    method: 'post',
    data: search
  })

export const listTglVoucherDetailsAll = (search) =>
  request({
    url: '/vouchermanager/tglVoucherDetails/listAll',
    method: 'post',
    data: search
  })

export const saveTglVoucherDetails = (tglVoucherDetails) =>
  request({
    url: '/vouchermanager/tglVoucherDetails/save',
    method: 'post',
    data: tglVoucherDetails
  })

export const deleteTglVoucherDetails = (tglVoucherDetails) =>
  request({
    url: '/vouchermanager/tglVoucherDetails/delete',
    method: 'post',
    data: tglVoucherDetails
  })

export const bulkInsertTglVoucherDetails = (tglVoucherDetailss) =>
  request({
    url: '/vouchermanager/tglVoucherDetails/bulkInsert',
    method: 'post',
    data: tglVoucherDetailss
  })

export const bulkUpdateTglVoucherDetails = (tglVoucherDetailss) =>
  request({
    url: '/vouchermanager/tglVoucherDetails/bulkUpdate',
    method: 'post',
    data: tglVoucherDetailss
  })

export const bulkDeleteTglVoucherDetails = (tglVoucherDetailss) =>
  request({
    url: '/vouchermanager/tglVoucherDetails/bulkDelete',
    method: 'post',
    data: tglVoucherDetailss
  })
