import request from '@/utils/request'

export const getTfamDepsubById = (id) =>
  request({
    url: '/fixasset/asset/tfamDepsub/' + id,
    method: 'get'
  })

export const listTfamDepsubPage = (search) =>
  request({
    url: '/fixasset/asset/tfamDepsub/list',
    method: 'post',
    data: search
  })

export const listTfamDepsubAll = (search) =>
  request({
    url: '/fixasset/asset/tfamDepsub/listAll',
    method: 'post',
    data: search
  })

export const saveTfamDepsub = (tfamDepsub) =>
  request({
    url: '/fixasset/asset/tfamDepsub/save',
    method: 'post',
    data: tfamDepsub
  })

export const deleteTfamDepsub = (tfamDepsub) =>
  request({
    url: '/fixasset/asset/tfamDepsub/delete',
    method: 'post',
    data: tfamDepsub
  })

export const bulkInsertTfamDepsub = (tfamDepsubs) =>
  request({
    url: '/fixasset/asset/tfamDepsub/bulkInsert',
    method: 'post',
    data: tfamDepsubs
  })

export const bulkUpdateTfamDepsub = (tfamDepsubs) =>
  request({
    url: '/fixasset/asset/tfamDepsub/bulkUpdate',
    method: 'post',
    data: tfamDepsubs
  })

export const bulkDeleteTfamDepsub = (tfamDepsubs) =>
  request({
    url: '/fixasset/asset/tfamDepsub/bulkDelete',
    method: 'post',
    data: tfamDepsubs
  })
export const projinfo = (tfamDepsubs) =>
  request({
    url: '/fixasset/asset/tfamDepsub/projinfo',
    method: 'post',
    data: tfamDepsubs
  })
