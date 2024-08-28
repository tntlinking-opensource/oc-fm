import request from '@/utils/request'

export const getTfamAlterById = (id) =>
  request({
    url: '/fixasset/alter/tfamAlter/' + id,
    method: 'get'
  })

export const listTfamAlterPage = (search) =>
  request({
    url: '/fixasset/alter/tfamAlter/list',
    method: 'post',
    data: search
  })

export const listTfamAlterAll = (search) =>
  request({
    url: '/fixasset/alter/tfamAlter/listAll',
    method: 'post',
    data: search
  })

export const saveTfamAlter = (tfamAlter) =>
  request({
    url: '/fixasset/alter/tfamAlter/save',
    method: 'post',
    data: tfamAlter
  })

export const deleteTfamAlter = (tfamAlter) =>
  request({
    url: '/fixasset/alter/tfamAlter/delete',
    method: 'post',
    data: tfamAlter
  })

export const bulkInsertTfamAlter = (tfamAlters) =>
  request({
    url: '/fixasset/alter/tfamAlter/bulkInsert',
    method: 'post',
    data: tfamAlters
  })

export const bulkUpdateTfamAlter = (tfamAlters) =>
  request({
    url: '/fixasset/alter/tfamAlter/bulkUpdate',
    method: 'post',
    data: tfamAlters
  })

export const bulkDeleteTfamAlter = (tfamAlters) =>
  request({
    url: '/fixasset/alter/tfamAlter/bulkDelete',
    method: 'post',
    data: tfamAlters
  })
