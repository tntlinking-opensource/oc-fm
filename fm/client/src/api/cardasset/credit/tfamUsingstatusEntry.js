import request from '@/utils/request'

export const getTfamUsingstatusEntryById = (id) =>
  request({
    url: '/cardasset/credit/tfamUsingstatusEntry/' + id,
    method: 'get'
  })

export const listTfamUsingstatusEntryPage = (search) =>
  request({
    url: '/cardasset/credit/tfamUsingstatusEntry/list',
    method: 'post',
    data: search
  })

export const listTfamUsingstatusEntryAll = (search) =>
  request({
    url: '/cardasset/credit/tfamUsingstatusEntry/listAll',
    method: 'post',
    data: search
  })

export const saveTfamUsingstatusEntry = (tfamUsingstatusEntry) =>
  request({
    url: '/cardasset/credit/tfamUsingstatusEntry/save',
    method: 'post',
    data: tfamUsingstatusEntry
  })

export const deleteTfamUsingstatusEntry = (tfamUsingstatusEntry) =>
  request({
    url: '/cardasset/credit/tfamUsingstatusEntry/delete',
    method: 'post',
    data: tfamUsingstatusEntry
  })

export const bulkInsertTfamUsingstatusEntry = (tfamUsingstatusEntrys) =>
  request({
    url: '/cardasset/credit/tfamUsingstatusEntry/bulkInsert',
    method: 'post',
    data: tfamUsingstatusEntrys
  })

export const bulkUpdateTfamUsingstatusEntry = (tfamUsingstatusEntrys) =>
  request({
    url: '/cardasset/credit/tfamUsingstatusEntry/bulkUpdate',
    method: 'post',
    data: tfamUsingstatusEntrys
  })

export const bulkDeleteTfamUsingstatusEntry = (tfamUsingstatusEntrys) =>
  request({
    url: '/cardasset/credit/tfamUsingstatusEntry/bulkDelete',
    method: 'post',
    data: tfamUsingstatusEntrys
  })
