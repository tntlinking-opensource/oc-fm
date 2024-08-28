import request from '@/utils/request'

export const getTfamAccessoryById = (id) =>
  request({
    url: '/fixasset/asset/tfamAccessory/' + id,
    method: 'get'
  })

export const listTfamAccessoryPage = (search) =>
  request({
    url: '/fixasset/asset/tfamAccessory/list',
    method: 'post',
    data: search
  })

export const listTfamAccessoryAll = (search) =>
  request({
    url: '/fixasset/asset/tfamAccessory/listAll',
    method: 'post',
    data: search
  })

export const saveTfamAccessory = (tfamAccessory) =>
  request({
    url: '/fixasset/asset/tfamAccessory/save',
    method: 'post',
    data: tfamAccessory
  })

export const deleteTfamAccessory = (tfamAccessory) =>
  request({
    url: '/fixasset/asset/tfamAccessory/delete',
    method: 'post',
    data: tfamAccessory
  })

export const bulkInsertTfamAccessory = (tfamAccessorys) =>
  request({
    url: '/fixasset/asset/tfamAccessory/bulkInsert',
    method: 'post',
    data: tfamAccessorys
  })

export const bulkUpdateTfamAccessory = (tfamAccessorys) =>
  request({
    url: '/fixasset/asset/tfamAccessory/bulkUpdate',
    method: 'post',
    data: tfamAccessorys
  })

export const bulkDeleteTfamAccessory = (tfamAccessorys) =>
  request({
    url: '/fixasset/asset/tfamAccessory/bulkDelete',
    method: 'post',
    data: tfamAccessorys
  })
