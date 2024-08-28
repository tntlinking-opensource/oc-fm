import request from '@/utils/request'

export const getTfamDepmethodEntryById = (id) =>
  request({
    url: '/cardasset/credit/tfamDepmethodEntry/' + id,
    method: 'get'
  })

export const listTfamDepmethodEntryPage = (search) =>
  request({
    url: '/cardasset/credit/tfamDepmethodEntry/list',
    method: 'post',
    data: search
  })

export const listTfamDepmethodEntryAll = (search) =>
  request({
    url: '/cardasset/credit/tfamDepmethodEntry/listAll',
    method: 'post',
    data: search
  })

export const saveTfamDepmethodEntry = (tfamDepmethodEntry) =>
  request({
    url: '/cardasset/credit/tfamDepmethodEntry/save',
    method: 'post',
    data: tfamDepmethodEntry
  })

export const deleteTfamDepmethodEntry = (tfamDepmethodEntry) =>
  request({
    url: '/cardasset/credit/tfamDepmethodEntry/delete',
    method: 'post',
    data: tfamDepmethodEntry
  })

export const bulkInsertTfamDepmethodEntry = (tfamDepmethodEntrys) =>
  request({
    url: '/cardasset/credit/tfamDepmethodEntry/bulkInsert',
    method: 'post',
    data: tfamDepmethodEntrys
  })

export const bulkUpdateTfamDepmethodEntry = (tfamDepmethodEntrys) =>
  request({
    url: '/cardasset/credit/tfamDepmethodEntry/bulkUpdate',
    method: 'post',
    data: tfamDepmethodEntrys
  })

export const bulkDeleteTfamDepmethodEntry = (tfamDepmethodEntrys) =>
  request({
    url: '/cardasset/credit/tfamDepmethodEntry/bulkDelete',
    method: 'post',
    data: tfamDepmethodEntrys
  })
