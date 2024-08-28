import request from '@/utils/request'

export const getTfamAlterEntryById = (id) =>
  request({
    url: '/cardasset/credit/tfamAlterEntry/' + id,
    method: 'get'
  })

export const listTfamAlterEntryPage = (search) =>
  request({
    url: '/cardasset/credit/tfamAlterEntry/list',
    method: 'post',
    data: search
  })

export const listTfamAlterEntryAll = (search) =>
  request({
    url: '/cardasset/credit/tfamAlterEntry/listAll',
    method: 'post',
    data: search
  })

export const saveTfamAlterEntry = (tfamAlterEntry) =>
  request({
    url: '/cardasset/credit/tfamAlterEntry/save',
    method: 'post',
    data: tfamAlterEntry
  })

export const deleteTfamAlterEntry = (tfamAlterEntry) =>
  request({
    url: '/cardasset/credit/tfamAlterEntry/delete',
    method: 'post',
    data: tfamAlterEntry
  })

export const bulkInsertTfamAlterEntry = (tfamAlterEntrys) =>
  request({
    url: '/cardasset/credit/tfamAlterEntry/bulkInsert',
    method: 'post',
    data: tfamAlterEntrys
  })

export const bulkUpdateTfamAlterEntry = (tfamAlterEntrys) =>
  request({
    url: '/cardasset/credit/tfamAlterEntry/bulkUpdate',
    method: 'post',
    data: tfamAlterEntrys
  })

export const bulkDeleteTfamAlterEntry = (tfamAlterEntrys) =>
  request({
    url: '/cardasset/credit/tfamAlterEntry/bulkDelete',
    method: 'post',
    data: tfamAlterEntrys
  })
