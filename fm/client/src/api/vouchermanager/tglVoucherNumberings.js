import request from '@/utils/request'

export const getTglVoucherNumberingsById = (id) =>
  request({
    url: '/vouchermanager/tglVoucherNumberings/' + id,
    method: 'get'
  })

export const listTglVoucherNumberingsPage = (search) =>
  request({
    url: '/vouchermanager/tglVoucherNumberings/list',
    method: 'post',
    data: search
  })

export const listTglVoucherNumberingsAll = (search) =>
  request({
    url: '/vouchermanager/tglVoucherNumberings/listAll',
    method: 'post',
    data: search
  })

export const saveTglVoucherNumberings = (tglVoucherNumberings) =>
  request({
    url: '/vouchermanager/tglVoucherNumberings/save',
    method: 'post',
    data: tglVoucherNumberings
  })

export const deleteTglVoucherNumberings = (tglVoucherNumberings) =>
  request({
    url: '/vouchermanager/tglVoucherNumberings/delete',
    method: 'post',
    data: tglVoucherNumberings
  })

export const bulkInsertTglVoucherNumberings = (tglVoucherNumberingss) =>
  request({
    url: '/vouchermanager/tglVoucherNumberings/bulkInsert',
    method: 'post',
    data: tglVoucherNumberingss
  })

export const bulkUpdateTglVoucherNumberings = (tglVoucherNumberingss) =>
  request({
    url: '/vouchermanager/tglVoucherNumberings/bulkUpdate',
    method: 'post',
    data: tglVoucherNumberingss
  })

export const bulkDeleteTglVoucherNumberings = (tglVoucherNumberingss) =>
  request({
    url: '/vouchermanager/tglVoucherNumberings/bulkDelete',
    method: 'post',
    data: tglVoucherNumberingss
  })
