import request from '@/utils/request'

export const getTglLedgerModelById = (id) =>
  request({
    url: '/vouchermanager/tglLedgerModel/' + id,
    method: 'get'
  })

export const listTglLedgerModelPage = (search) =>
  request({
    url: '/vouchermanager/tglLedgerModel/list',
    method: 'post',
    data: search
  })

export const listTglLedgerModelAll = (search) =>
  request({
    url: '/vouchermanager/tglLedgerModel/listAll',
    method: 'post',
    data: search
  })

export const saveTglLedgerModel = (tglLedgerModel) =>
  request({
    url: '/vouchermanager/tglLedgerModel/save',
    method: 'post',
    data: tglLedgerModel
  })

export const deleteTglLedgerModel = (tglLedgerModel) =>
  request({
    url: '/vouchermanager/tglLedgerModel/delete',
    method: 'post',
    data: tglLedgerModel
  })

export const bulkInsertTglLedgerModel = (tglLedgerModels) =>
  request({
    url: '/vouchermanager/tglLedgerModel/bulkInsert',
    method: 'post',
    data: tglLedgerModels
  })

export const bulkUpdateTglLedgerModel = (tglLedgerModels) =>
  request({
    url: '/vouchermanager/tglLedgerModel/bulkUpdate',
    method: 'post',
    data: tglLedgerModels
  })

export const bulkDeleteTglLedgerModel = (tglLedgerModels) =>
  request({
    url: '/vouchermanager/tglLedgerModel/bulkDelete',
    method: 'post',
    data: tglLedgerModels
  })
