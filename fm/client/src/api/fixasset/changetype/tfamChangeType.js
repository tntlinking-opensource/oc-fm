import request from '@/utils/request'

export const getTfamChangeTypeById = (id) =>
  request({
    url: '/fixasset/changetype/tfamChangeType/' + id,
    method: 'get'
  })

export const listTfamChangeTypePage = (search) =>
  request({
    url: '/fixasset/changetype/tfamChangeType/list',
    method: 'post',
    data: search
  })

export const listTfamChangeTypeAll = (search) =>
  request({
    url: '/fixasset/changetype/tfamChangeType/listAll',
    method: 'post',
    data: search
  })

export const saveTfamChangeType = (tfamChangeType) =>
  request({
    url: '/fixasset/changetype/tfamChangeType/save',
    method: 'post',
    data: tfamChangeType
  })

export const deleteTfamChangeType = (tfamChangeType) =>
  request({
    url: '/fixasset/changetype/tfamChangeType/delete',
    method: 'post',
    data: tfamChangeType
  })

export const bulkInsertTfamChangeType = (tfamChangeTypes) =>
  request({
    url: '/fixasset/changetype/tfamChangeType/bulkInsert',
    method: 'post',
    data: tfamChangeTypes
  })

export const bulkUpdateTfamChangeType = (tfamChangeTypes) =>
  request({
    url: '/fixasset/changetype/tfamChangeType/bulkUpdate',
    method: 'post',
    data: tfamChangeTypes
  })

export const bulkDeleteTfamChangeType = (tfamChangeTypes) =>
  request({
    url: '/fixasset/changetype/tfamChangeType/bulkDelete',
    method: 'post',
    data: tfamChangeTypes
  })
