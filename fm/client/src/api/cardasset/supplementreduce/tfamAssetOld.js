import request from '@/utils/request'

export const getTfamAssetOldById = (id) =>
  request({
    url: '/cardasset/supplementreduce/tfamAssetOld/' + id,
    method: 'get'
  })

export const listTfamAssetOldPage = (search) =>
  request({
    url: '/cardasset/supplementreduce/tfamAssetOld/list',
    method: 'post',
    data: search
  })

export const listTfamAssetOldAll = (search) =>
  request({
    url: '/cardasset/supplementreduce/tfamAssetOld/listAll',
    method: 'post',
    data: search
  })

export const saveTfamAssetOld = (tfamAssetOld) =>
  request({
    url: '/cardasset/supplementreduce/tfamAssetOld/save',
    method: 'post',
    data: tfamAssetOld
  })

export const deleteTfamAssetOld = (tfamAssetOld) =>
  request({
    url: '/cardasset/supplementreduce/tfamAssetOld/delete',
    method: 'post',
    data: tfamAssetOld
  })

export const bulkInsertTfamAssetOld = (tfamAssetOlds) =>
  request({
    url: '/cardasset/supplementreduce/tfamAssetOld/bulkInsert',
    method: 'post',
    data: tfamAssetOlds
  })

export const bulkUpdateTfamAssetOld = (tfamAssetOlds) =>
  request({
    url: '/cardasset/supplementreduce/tfamAssetOld/bulkUpdate',
    method: 'post',
    data: tfamAssetOlds
  })

export const bulkDeleteTfamAssetOld = (tfamAssetOlds) =>
  request({
    url: '/cardasset/supplementreduce/tfamAssetOld/bulkDelete',
    method: 'post',
    data: tfamAssetOlds
  })
export const supplementdep = (tfamAssetOlds) =>
  request({
    url: '/cardasset/supplementreduce/tfamAssetOld/supplementdep',
    method: 'post',
    data: tfamAssetOlds
  })
export const reducedep = (tfamAssetOlds) =>
  request({
    url: '/cardasset/supplementreduce/tfamAssetOld/reducedep',
    method: 'post',
    data: tfamAssetOlds
  })
