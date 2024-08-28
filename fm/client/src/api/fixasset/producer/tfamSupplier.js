import request from '@/utils/request'

export const getTfamSupplierById = (id) =>
  request({
    url: '/fixasset/producer/tfamSupplier/' + id,
    method: 'get'
  })

export const listTfamSupplierPage = (search) =>
  request({
    url: '/fixasset/producer/tfamSupplier/list',
    method: 'post',
    data: search
  })

export const listTfamSupplierAll = (search) =>
  request({
    url: '/fixasset/producer/tfamSupplier/listAll',
    method: 'post',
    data: search
  })

export const saveTfamSupplier = (tfamSupplier) =>
  request({
    url: '/fixasset/producer/tfamSupplier/save',
    method: 'post',
    data: tfamSupplier
  })

export const deleteTfamSupplier = (tfamSupplier) =>
  request({
    url: '/fixasset/producer/tfamSupplier/delete',
    method: 'post',
    data: tfamSupplier
  })

export const bulkInsertTfamSupplier = (tfamSuppliers) =>
  request({
    url: '/fixasset/producer/tfamSupplier/bulkInsert',
    method: 'post',
    data: tfamSuppliers
  })

export const bulkUpdateTfamSupplier = (tfamSuppliers) =>
  request({
    url: '/fixasset/producer/tfamSupplier/bulkUpdate',
    method: 'post',
    data: tfamSuppliers
  })

export const bulkDeleteTfamSupplier = (tfamSuppliers) =>
  request({
    url: '/fixasset/producer/tfamSupplier/bulkDelete',
    method: 'post',
    data: tfamSuppliers
  })

export const exportTfamSupplier = (formData) =>
  request({
    url: '/fixasset/producer/tfamSupplier/exportTfamSupplier',
    method: 'post',
    data: formData,
    responseType: 'blob'
  })
export const importTfamSupplier = (formData) =>
  request({
    url: '/fixasset/producer/tfamSupplier/importTfamSupplier',
    method: 'post',
    data: formData
  })
