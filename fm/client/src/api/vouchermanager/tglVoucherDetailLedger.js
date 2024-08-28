import request from '@/utils/request'

export const getTglVoucherDetailLedgerById = (id) =>
  request({
    url: '/vouchermanager/tglVoucherDetailLedger/' + id,
    method: 'get'
  })

export const listTglVoucherDetailLedgerPage = (search) =>
  request({
    url: '/vouchermanager/tglVoucherDetailLedger/list',
    method: 'post',
    data: search
  })

export const listTglVoucherDetailLedgerAll = (search) =>
  request({
    url: '/vouchermanager/tglVoucherDetailLedger/listAll',
    method: 'post',
    data: search
  })

export const saveTglVoucherDetailLedger = (tglVoucherDetailLedger) =>
  request({
    url: '/vouchermanager/tglVoucherDetailLedger/save',
    method: 'post',
    data: tglVoucherDetailLedger
  })

export const deleteTglVoucherDetailLedger = (tglVoucherDetailLedger) =>
  request({
    url: '/vouchermanager/tglVoucherDetailLedger/delete',
    method: 'post',
    data: tglVoucherDetailLedger
  })

export const bulkInsertTglVoucherDetailLedger = (tglVoucherDetailLedgers) =>
  request({
    url: '/vouchermanager/tglVoucherDetailLedger/bulkInsert',
    method: 'post',
    data: tglVoucherDetailLedgers
  })

export const bulkUpdateTglVoucherDetailLedger = (tglVoucherDetailLedgers) =>
  request({
    url: '/vouchermanager/tglVoucherDetailLedger/bulkUpdate',
    method: 'post',
    data: tglVoucherDetailLedgers
  })

export const bulkDeleteTglVoucherDetailLedger = (tglVoucherDetailLedgers) =>
  request({
    url: '/vouchermanager/tglVoucherDetailLedger/bulkDelete',
    method: 'post',
    data: tglVoucherDetailLedgers
  })
