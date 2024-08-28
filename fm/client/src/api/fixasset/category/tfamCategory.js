import request from '@/utils/request'

export const getTfamCategoryById = (id) =>
  request({
    url: '/fixasset/category/tfamCategory/' + id,
    method: 'get'
  })
export const getDataByID = (id) =>
  request({
    url: '/fixasset/category/tfamCategory/getDataByID/' + id,
    method: 'get'
  })

export const listTfamCategoryPage = (search) =>
  request({
    url: '/fixasset/category/tfamCategory/list',
    method: 'post',
    data: search
  })
export const getData = () =>
  request({
    url: '/fixasset/category/tfamCategory/getData',
    method: 'post',
  })

export const listTfamCategoryAll = (search) =>
  request({
    url: '/fixasset/category/tfamCategory/listAll',
    method: 'post',
    data: search
  })

export const saveTfamCategory = (tfamCategory) =>
  request({
    url: '/fixasset/category/tfamCategory/save',
    method: 'post',
    data: tfamCategory
  })

export const deleteTfamCategory = (tfamCategory) =>
  request({
    url: '/fixasset/category/tfamCategory/delete',
    method: 'post',
    data: tfamCategory
  })

export const bulkInsertTfamCategory = (tfamCategorys) =>
  request({
    url: '/fixasset/category/tfamCategory/bulkInsert',
    method: 'post',
    data: tfamCategorys
  })

export const bulkUpdateTfamCategory = (tfamCategorys) =>
  request({
    url: '/fixasset/category/tfamCategory/bulkUpdate',
    method: 'post',
    data: tfamCategorys
  })

export const bulkDeleteTfamCategory = (tfamCategorys) =>
  request({
    url: '/fixasset/category/tfamCategory/bulkDelete',
    method: 'post',
    data: tfamCategorys
  })
export const exportTfamCategory = (formData) =>
  request({
    url: '/fixasset/category/tfamCategory/exportTfamCategory',
    method: 'post',
    data: formData,
    responseType: 'blob'
  })
export const importTfamCategory = (formData) =>
  request({
    url: '/fixasset/category/tfamCategory/importTfamCategory',
    method: 'post',
    data: formData
  })
