import request from '@/utils/request'

export const getTfamUsingstatusOldById = (id) =>
  request({
    url: '/cardasset/supplementreduce/tfamUsingstatusOld/' + id,
    method: 'get'
  })

export const listTfamUsingstatusOldPage = (search) =>
  request({
    url: '/cardasset/supplementreduce/tfamUsingstatusOld/list',
    method: 'post',
    data: search
  })

export const listTfamUsingstatusOldAll = (search) =>
  request({
    url: '/cardasset/supplementreduce/tfamUsingstatusOld/listAll',
    method: 'post',
    data: search
  })

export const saveTfamUsingstatusOld = (tfamUsingstatusOld) =>
  request({
    url: '/cardasset/supplementreduce/tfamUsingstatusOld/save',
    method: 'post',
    data: tfamUsingstatusOld
  })

export const deleteTfamUsingstatusOld = (tfamUsingstatusOld) =>
  request({
    url: '/cardasset/supplementreduce/tfamUsingstatusOld/delete',
    method: 'post',
    data: tfamUsingstatusOld
  })

export const bulkInsertTfamUsingstatusOld = (tfamUsingstatusOlds) =>
  request({
    url: '/cardasset/supplementreduce/tfamUsingstatusOld/bulkInsert',
    method: 'post',
    data: tfamUsingstatusOlds
  })

export const bulkUpdateTfamUsingstatusOld = (tfamUsingstatusOlds) =>
  request({
    url: '/cardasset/supplementreduce/tfamUsingstatusOld/bulkUpdate',
    method: 'post',
    data: tfamUsingstatusOlds
  })

export const bulkDeleteTfamUsingstatusOld = (tfamUsingstatusOlds) =>
  request({
    url: '/cardasset/supplementreduce/tfamUsingstatusOld/bulkDelete',
    method: 'post',
    data: tfamUsingstatusOlds
  })
