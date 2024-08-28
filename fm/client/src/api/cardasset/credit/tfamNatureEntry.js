import request from '@/utils/request'

export const getTfamNatureEntryById = (id) =>
  request({
    url: '/cardasset/credit/tfamNatureEntry/' + id,
    method: 'get'
  })

export const listTfamNatureEntryPage = (search) =>
  request({
    url: '/cardasset/credit/tfamNatureEntry/list',
    method: 'post',
    data: search
  })

export const listTfamNatureEntryAll = (search) =>
  request({
    url: '/cardasset/credit/tfamNatureEntry/listAll',
    method: 'post',
    data: search
  })

export const saveTfamNatureEntry = (tfamNatureEntry) =>
  request({
    url: '/cardasset/credit/tfamNatureEntry/save',
    method: 'post',
    data: tfamNatureEntry
  })

export const deleteTfamNatureEntry = (tfamNatureEntry) =>
  request({
    url: '/cardasset/credit/tfamNatureEntry/delete',
    method: 'post',
    data: tfamNatureEntry
  })

export const bulkInsertTfamNatureEntry = (tfamNatureEntrys) =>
  request({
    url: '/cardasset/credit/tfamNatureEntry/bulkInsert',
    method: 'post',
    data: tfamNatureEntrys
  })

export const bulkUpdateTfamNatureEntry = (tfamNatureEntrys) =>
  request({
    url: '/cardasset/credit/tfamNatureEntry/bulkUpdate',
    method: 'post',
    data: tfamNatureEntrys
  })

export const bulkDeleteTfamNatureEntry = (tfamNatureEntrys) =>
  request({
    url: '/cardasset/credit/tfamNatureEntry/bulkDelete',
    method: 'post',
    data: tfamNatureEntrys
  })
