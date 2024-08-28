import request from '@/utils/request'

export const getTglVoucherDetailLedgertypeById = (id) =>
  request({
    url: '/vouchermanager/tglVoucherDetailLedgertype/' + id,
    method: 'get'
  })

export const listTglVoucherDetailLedgertypePage = (search) =>
  request({
    url: '/vouchermanager/tglVoucherDetailLedgertype/list',
    method: 'post',
    data: search
  })

export const listTglVoucherDetailLedgertypeAll = (search) =>
  request({
    url: '/vouchermanager/tglVoucherDetailLedgertype/listAll',
    method: 'post',
    data: search
  })

export const saveTglVoucherDetailLedgertype = (tglVoucherDetailLedgertype) =>
  request({
    url: '/vouchermanager/tglVoucherDetailLedgertype/save',
    method: 'post',
    data: tglVoucherDetailLedgertype
  })

export const deleteTglVoucherDetailLedgertype = (tglVoucherDetailLedgertype) =>
  request({
    url: '/vouchermanager/tglVoucherDetailLedgertype/delete',
    method: 'post',
    data: tglVoucherDetailLedgertype
  })

export const bulkInsertTglVoucherDetailLedgertype = (tglVoucherDetailLedgertypes) =>
  request({
    url: '/vouchermanager/tglVoucherDetailLedgertype/bulkInsert',
    method: 'post',
    data: tglVoucherDetailLedgertypes
  })

export const bulkUpdateTglVoucherDetailLedgertype = (tglVoucherDetailLedgertypes) =>
  request({
    url: '/vouchermanager/tglVoucherDetailLedgertype/bulkUpdate',
    method: 'post',
    data: tglVoucherDetailLedgertypes
  })

export const bulkDeleteTglVoucherDetailLedgertype = (tglVoucherDetailLedgertypes) =>
  request({
    url: '/vouchermanager/tglVoucherDetailLedgertype/bulkDelete',
    method: 'post',
    data: tglVoucherDetailLedgertypes
  })
