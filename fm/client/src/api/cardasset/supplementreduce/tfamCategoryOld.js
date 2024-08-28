import request from '@/utils/request'

export const getTfamCategoryOldById = (id) =>
  request({
    url: '/cardasset/supplementreduce/tfamCategoryOld/' + id,
    method: 'get'
  })

export const listTfamCategoryOldPage = (search) =>
  request({
    url: '/cardasset/supplementreduce/tfamCategoryOld/list',
    method: 'post',
    data: search
  })

export const listTfamCategoryOldAll = (search) =>
  request({
    url: '/cardasset/supplementreduce/tfamCategoryOld/listAll',
    method: 'post',
    data: search
  })

export const saveTfamCategoryOld = (tfamCategoryOld) =>
  request({
    url: '/cardasset/supplementreduce/tfamCategoryOld/save',
    method: 'post',
    data: tfamCategoryOld
  })

export const deleteTfamCategoryOld = (tfamCategoryOld) =>
  request({
    url: '/cardasset/supplementreduce/tfamCategoryOld/delete',
    method: 'post',
    data: tfamCategoryOld
  })

export const bulkInsertTfamCategoryOld = (tfamCategoryOlds) =>
  request({
    url: '/cardasset/supplementreduce/tfamCategoryOld/bulkInsert',
    method: 'post',
    data: tfamCategoryOlds
  })

export const bulkUpdateTfamCategoryOld = (tfamCategoryOlds) =>
  request({
    url: '/cardasset/supplementreduce/tfamCategoryOld/bulkUpdate',
    method: 'post',
    data: tfamCategoryOlds
  })

export const bulkDeleteTfamCategoryOld = (tfamCategoryOlds) =>
  request({
    url: '/cardasset/supplementreduce/tfamCategoryOld/bulkDelete',
    method: 'post',
    data: tfamCategoryOlds
  })
