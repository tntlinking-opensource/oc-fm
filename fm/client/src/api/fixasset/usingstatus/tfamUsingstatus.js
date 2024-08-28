import request from '@/utils/request'

export const getTfamUsingstatusById = (id) =>
  request({
    url: '/fixasset/usingstatus/tfamUsingstatus/' + id,
    method: 'get'
  })

export const listTfamUsingstatusPage = (search) =>
  request({
    url: '/fixasset/usingstatus/tfamUsingstatus/list',
    method: 'post',
    data: search
  })

export const listTfamUsingstatusAll = (search) =>
  request({
    url: '/fixasset/usingstatus/tfamUsingstatus/listAll',
    method: 'post',
    data: search
  })

export const saveTfamUsingstatus = (tfamUsingstatus) =>
  request({
    url: '/fixasset/usingstatus/tfamUsingstatus/save',
    method: 'post',
    data: tfamUsingstatus
  })

export const deleteTfamUsingstatus = (tfamUsingstatus) =>
  request({
    url: '/fixasset/usingstatus/tfamUsingstatus/delete',
    method: 'post',
    data: tfamUsingstatus
  })

export const bulkInsertTfamUsingstatus = (tfamUsingstatuss) =>
  request({
    url: '/fixasset/usingstatus/tfamUsingstatus/bulkInsert',
    method: 'post',
    data: tfamUsingstatuss
  })

export const bulkUpdateTfamUsingstatus = (tfamUsingstatuss) =>
  request({
    url: '/fixasset/usingstatus/tfamUsingstatus/bulkUpdate',
    method: 'post',
    data: tfamUsingstatuss
  })

export const bulkDeleteTfamUsingstatus = (tfamUsingstatuss) =>
  request({
    url: '/fixasset/usingstatus/tfamUsingstatus/bulkDelete',
    method: 'post',
    data: tfamUsingstatuss
  })
