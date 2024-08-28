import request from '@/utils/request'

export const getTglVoucherMainsEntryById = (id) =>
  request({
    url: '/cardasset/credit/tglVoucherMainsEntry/' + id,
    method: 'get'
  })

export const listTglVoucherMainsEntryPage = (search) =>
  request({
    url: '/cardasset/credit/tglVoucherMainsEntry/list',
    method: 'post',
    data: search
  })

export const listTglVoucherMainsEntryAll = (search) =>
  request({
    url: '/cardasset/credit/tglVoucherMainsEntry/listAll',
    method: 'post',
    data: search
  })

export const saveTglVoucherMainsEntry = (tglVoucherMainsEntry) =>
  request({
    url: '/cardasset/credit/tglVoucherMainsEntry/save',
    method: 'post',
    data: tglVoucherMainsEntry
  })

export const deleteTglVoucherMainsEntry = (tglVoucherMainsEntry) =>
  request({
    url: '/cardasset/credit/tglVoucherMainsEntry/delete',
    method: 'post',
    data: tglVoucherMainsEntry
  })

export const bulkInsertTglVoucherMainsEntry = (tglVoucherMainsEntrys) =>
  request({
    url: '/cardasset/credit/tglVoucherMainsEntry/bulkInsert',
    method: 'post',
    data: tglVoucherMainsEntrys
  })

export const bulkUpdateTglVoucherMainsEntry = (tglVoucherMainsEntrys) =>
  request({
    url: '/cardasset/credit/tglVoucherMainsEntry/bulkUpdate',
    method: 'post',
    data: tglVoucherMainsEntrys
  })

export const bulkDeleteTglVoucherMainsEntry = (tglVoucherMainsEntrys) =>
  request({
    url: '/cardasset/credit/tglVoucherMainsEntry/bulkDelete',
    method: 'post',
    data: tglVoucherMainsEntrys
  })
