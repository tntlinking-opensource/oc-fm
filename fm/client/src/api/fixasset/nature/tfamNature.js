import request from '@/utils/request'

export const getTfamNatureById = (id) =>
  request({
    url: '/fixasset/nature/tfamNature/' + id,
    method: 'get'
  })

export const listTfamNaturePage = (search) =>
  request({
    url: '/fixasset/nature/tfamNature/list',
    method: 'post',
    data: search
  })

export const listTfamNatureAll = (search) =>
  request({
    url: '/fixasset/nature/tfamNature/listAll',
    method: 'post',
    data: search
  })

export const saveTfamNature = (tfamNature) =>
  request({
    url: '/fixasset/nature/tfamNature/save',
    method: 'post',
    data: tfamNature
  })

export const deleteTfamNature = (tfamNature) =>
  request({
    url: '/fixasset/nature/tfamNature/delete',
    method: 'post',
    data: tfamNature
  })

export const bulkInsertTfamNature = (tfamNatures) =>
  request({
    url: '/fixasset/nature/tfamNature/bulkInsert',
    method: 'post',
    data: tfamNatures
  })

export const bulkUpdateTfamNature = (tfamNatures) =>
  request({
    url: '/fixasset/nature/tfamNature/bulkUpdate',
    method: 'post',
    data: tfamNatures
  })

export const bulkDeleteTfamNature = (tfamNatures) =>
  request({
    url: '/fixasset/nature/tfamNature/bulkDelete',
    method: 'post',
    data: tfamNatures
  })
