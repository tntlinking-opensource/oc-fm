import request from '@/utils/request'

export const getTfamChangeWayEntryById = (id) =>
  request({
    url: '/cardasset/credit/tfamChangeWayEntry/' + id,
    method: 'get'
  })

export const listTfamChangeWayEntryPage = (search) =>
  request({
    url: '/cardasset/credit/tfamChangeWayEntry/list',
    method: 'post',
    data: search
  })

export const listTfamChangeWayEntryAll = (search) =>
  request({
    url: '/cardasset/credit/tfamChangeWayEntry/listAll',
    method: 'post',
    data: search
  })

export const saveTfamChangeWayEntry = (tfamChangeWayEntry) =>
  request({
    url: '/cardasset/credit/tfamChangeWayEntry/save',
    method: 'post',
    data: tfamChangeWayEntry
  })

export const deleteTfamChangeWayEntry = (tfamChangeWayEntry) =>
  request({
    url: '/cardasset/credit/tfamChangeWayEntry/delete',
    method: 'post',
    data: tfamChangeWayEntry
  })

export const bulkInsertTfamChangeWayEntry = (tfamChangeWayEntrys) =>
  request({
    url: '/cardasset/credit/tfamChangeWayEntry/bulkInsert',
    method: 'post',
    data: tfamChangeWayEntrys
  })

export const bulkUpdateTfamChangeWayEntry = (tfamChangeWayEntrys) =>
  request({
    url: '/cardasset/credit/tfamChangeWayEntry/bulkUpdate',
    method: 'post',
    data: tfamChangeWayEntrys
  })

export const bulkDeleteTfamChangeWayEntry = (tfamChangeWayEntrys) =>
  request({
    url: '/cardasset/credit/tfamChangeWayEntry/bulkDelete',
    method: 'post',
    data: tfamChangeWayEntrys
  })
