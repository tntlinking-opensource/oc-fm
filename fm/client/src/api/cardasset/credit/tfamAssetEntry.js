import request from '@/utils/request'

export const getTfamAssetEntryById = (id) =>
  request({
    url: '/cardasset/credit/tfamAssetEntry/' + id,
    method: 'get'
  })

export const listTfamAssetEntryPage = (search) =>
  request({
    url: '/cardasset/credit/tfamAssetEntry/list',
    method: 'post',
    data: search
  })

export const listTfamAssetEntryAll = (search) =>
  request({
    url: '/cardasset/credit/tfamAssetEntry/listAll',
    method: 'post',
    data: search
  })

export const saveTfamAssetEntry = (tfamAssetEntry) =>
  request({
    url: '/cardasset/credit/tfamAssetEntry/save',
    method: 'post',
    data: tfamAssetEntry
  })

export const deleteTfamAssetEntry = (tfamAssetEntry) =>
  request({
    url: '/cardasset/credit/tfamAssetEntry/delete',
    method: 'post',
    data: tfamAssetEntry
  })

export const bulkInsertTfamAssetEntry = (tfamAssetEntrys) =>
  request({
    url: '/cardasset/credit/tfamAssetEntry/bulkInsert',
    method: 'post',
    data: tfamAssetEntrys
  })

export const bulkUpdateTfamAssetEntry = (tfamAssetEntrys) =>
  request({
    url: '/cardasset/credit/tfamAssetEntry/bulkUpdate',
    method: 'post',
    data: tfamAssetEntrys
  })

export const bulkDeleteTfamAssetEntry = (tfamAssetEntrys) =>
  request({
    url: '/cardasset/credit/tfamAssetEntry/bulkDelete',
    method: 'post',
    data: tfamAssetEntrys
  })
export const linkvoucher = (tfamAssetEntrys) =>
  request({
    url: '/cardasset/credit/tfamAssetEntry/linkvoucher',
    method: 'post',
    data: tfamAssetEntrys
  })
export const cancelvoucher = (tfamAssetEntrys) =>
  request({
    url: '/cardasset/credit/tfamAssetEntry/cancelvoucher',
    method: 'post',
    data: tfamAssetEntrys
  })
export const linkvoucher1 = (tfamAssetEntrys) =>
  request({
    url: '/cardasset/credit/tfamAssetEntry/linkvoucher1',
    method: 'post',
    data: tfamAssetEntrys
  })
export const cancelvoucher1 = (tfamAssetEntrys) =>
  request({
    url: '/cardasset/credit/tfamAssetEntry/cancelvoucher1',
    method: 'post',
    data: tfamAssetEntrys
  })
