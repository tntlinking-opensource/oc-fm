import request from '@/utils/request'

export const getTglLedgetypeById = (id) =>
  request({
    url: '/vouchermanager/tglLedgetype/' + id,
    method: 'get'
  })

export const listTglLedgetypePage = (search) =>
  request({
    url: '/vouchermanager/tglLedgetype/list',
    method: 'post',
    data: search
  })

export const listTglLedgetypeAll = (search) =>
  request({
    url: '/vouchermanager/tglLedgetype/listAll',
    method: 'post',
    data: search
  })

export const saveTglLedgetype = (tglLedgetype) =>
  request({
    url: '/vouchermanager/tglLedgetype/save',
    method: 'post',
    data: tglLedgetype
  })

export const deleteTglLedgetype = (tglLedgetype) =>
  request({
    url: '/vouchermanager/tglLedgetype/delete',
    method: 'post',
    data: tglLedgetype
  })

export const bulkInsertTglLedgetype = (tglLedgetypes) =>
  request({
    url: '/vouchermanager/tglLedgetype/bulkInsert',
    method: 'post',
    data: tglLedgetypes
  })

export const bulkUpdateTglLedgetype = (tglLedgetypes) =>
  request({
    url: '/vouchermanager/tglLedgetype/bulkUpdate',
    method: 'post',
    data: tglLedgetypes
  })

export const bulkDeleteTglLedgetype = (tglLedgetypes) =>
  request({
    url: '/vouchermanager/tglLedgetype/bulkDelete',
    method: 'post',
    data: tglLedgetypes
  })

export const listLedgetypeAndChild = (search) =>
  request({
    url: '/vouchermanager/tglLedgetype/listAllAndChild',
    method: 'post',
    data: search
  })
export const exportTglLedge = (formData) =>
  request({
    url: '/vouchermanager/tglLedgetype/exportTglLedge',
    method: 'post',
    data: formData,
    responseType: 'blob'
  })
export const importtTglLedge = (formData) =>
  request({
    url: '/vouchermanager/tglLedgetype/importtTglLedge',
    method: 'post',
    data: formData
  })
