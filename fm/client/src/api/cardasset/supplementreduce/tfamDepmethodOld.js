import request from '@/utils/request'

export const getTfamDepmethodOldById = (id) =>
  request({
    url: '/cardasset/supplementreduce/tfamDepmethodOld/' + id,
    method: 'get'
  })

export const listTfamDepmethodOldPage = (search) =>
  request({
    url: '/cardasset/supplementreduce/tfamDepmethodOld/list',
    method: 'post',
    data: search
  })

export const listTfamDepmethodOldAll = (search) =>
  request({
    url: '/cardasset/supplementreduce/tfamDepmethodOld/listAll',
    method: 'post',
    data: search
  })

export const saveTfamDepmethodOld = (tfamDepmethodOld) =>
  request({
    url: '/cardasset/supplementreduce/tfamDepmethodOld/save',
    method: 'post',
    data: tfamDepmethodOld
  })

export const deleteTfamDepmethodOld = (tfamDepmethodOld) =>
  request({
    url: '/cardasset/supplementreduce/tfamDepmethodOld/delete',
    method: 'post',
    data: tfamDepmethodOld
  })

export const bulkInsertTfamDepmethodOld = (tfamDepmethodOlds) =>
  request({
    url: '/cardasset/supplementreduce/tfamDepmethodOld/bulkInsert',
    method: 'post',
    data: tfamDepmethodOlds
  })

export const bulkUpdateTfamDepmethodOld = (tfamDepmethodOlds) =>
  request({
    url: '/cardasset/supplementreduce/tfamDepmethodOld/bulkUpdate',
    method: 'post',
    data: tfamDepmethodOlds
  })

export const bulkDeleteTfamDepmethodOld = (tfamDepmethodOlds) =>
  request({
    url: '/cardasset/supplementreduce/tfamDepmethodOld/bulkDelete',
    method: 'post',
    data: tfamDepmethodOlds
  })
