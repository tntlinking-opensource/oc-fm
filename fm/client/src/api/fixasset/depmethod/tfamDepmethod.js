import request from '@/utils/request'

export const getTfamDepmethodById = (id) =>
  request({
    url: '/fixasset/depmethod/tfamDepmethod/' + id,
    method: 'get'
  })

export const listTfamDepmethodPage = (search) =>
  request({
    url: '/fixasset/depmethod/tfamDepmethod/list',
    method: 'post',
    data: search
  })

export const listTfamDepmethodAll = (search) =>
  request({
    url: '/fixasset/depmethod/tfamDepmethod/listAll',
    method: 'post',
    data: search
  })

export const saveTfamDepmethod = (tfamDepmethod) =>
  request({
    url: '/fixasset/depmethod/tfamDepmethod/save',
    method: 'post',
    data: tfamDepmethod
  })

export const deleteTfamDepmethod = (tfamDepmethod) =>
  request({
    url: '/fixasset/depmethod/tfamDepmethod/delete',
    method: 'post',
    data: tfamDepmethod
  })

export const bulkInsertTfamDepmethod = (tfamDepmethods) =>
  request({
    url: '/fixasset/depmethod/tfamDepmethod/bulkInsert',
    method: 'post',
    data: tfamDepmethods
  })

export const bulkUpdateTfamDepmethod = (tfamDepmethods) =>
  request({
    url: '/fixasset/depmethod/tfamDepmethod/bulkUpdate',
    method: 'post',
    data: tfamDepmethods
  })

export const bulkDeleteTfamDepmethod = (tfamDepmethods) =>
  request({
    url: '/fixasset/depmethod/tfamDepmethod/bulkDelete',
    method: 'post',
    data: tfamDepmethods
  })
