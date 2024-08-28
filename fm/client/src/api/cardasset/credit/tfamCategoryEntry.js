import request from '@/utils/request'

export const getTfamCategoryEntryById = (id) =>
  request({
    url: '/cardasset/credit/tfamCategoryEntry/' + id,
    method: 'get'
  })

export const listTfamCategoryEntryPage = (search) =>
  request({
    url: '/cardasset/credit/tfamCategoryEntry/list',
    method: 'post',
    data: search
  })

export const listTfamCategoryEntryAll = (search) =>
  request({
    url: '/cardasset/credit/tfamCategoryEntry/listAll',
    method: 'post',
    data: search
  })

export const saveTfamCategoryEntry = (tfamCategoryEntry) =>
  request({
    url: '/cardasset/credit/tfamCategoryEntry/save',
    method: 'post',
    data: tfamCategoryEntry
  })

export const deleteTfamCategoryEntry = (tfamCategoryEntry) =>
  request({
    url: '/cardasset/credit/tfamCategoryEntry/delete',
    method: 'post',
    data: tfamCategoryEntry
  })

export const bulkInsertTfamCategoryEntry = (tfamCategoryEntrys) =>
  request({
    url: '/cardasset/credit/tfamCategoryEntry/bulkInsert',
    method: 'post',
    data: tfamCategoryEntrys
  })

export const bulkUpdateTfamCategoryEntry = (tfamCategoryEntrys) =>
  request({
    url: '/cardasset/credit/tfamCategoryEntry/bulkUpdate',
    method: 'post',
    data: tfamCategoryEntrys
  })

export const bulkDeleteTfamCategoryEntry = (tfamCategoryEntrys) =>
  request({
    url: '/cardasset/credit/tfamCategoryEntry/bulkDelete',
    method: 'post',
    data: tfamCategoryEntrys
  })
