import request from '@/utils/request'

export const getTglAcOffsetMainById = (id) =>
  request({
    url: '/vouchermanager/tglAcOffsetMain/' + id,
    method: 'get'
  })

export const listTglAcOffsetMainPage = (search) =>
  request({
    url: '/vouchermanager/tglAcOffsetMain/list',
    method: 'post',
    data: search
  })

export const listTglAcOffsetMainAll = (search) =>
  request({
    url: '/vouchermanager/tglAcOffsetMain/listAll',
    method: 'post',
    data: search
  })

export const saveTglAcOffsetMain = (tglAcOffsetMain) =>
  request({
    url: '/vouchermanager/tglAcOffsetMain/save',
    method: 'post',
    data: tglAcOffsetMain
  })

export const deleteTglAcOffsetMain = (tglAcOffsetMain) =>
  request({
    url: '/vouchermanager/tglAcOffsetMain/delete',
    method: 'post',
    data: tglAcOffsetMain
  })

export const bulkInsertTglAcOffsetMain = (tglAcOffsetMains) =>
  request({
    url: '/vouchermanager/tglAcOffsetMain/bulkInsert',
    method: 'post',
    data: tglAcOffsetMains
  })

export const bulkUpdateTglAcOffsetMain = (tglAcOffsetMains) =>
  request({
    url: '/vouchermanager/tglAcOffsetMain/bulkUpdate',
    method: 'post',
    data: tglAcOffsetMains
  })

export const bulkDeleteTglAcOffsetMain = (tglAcOffsetMains) =>
  request({
    url: '/vouchermanager/tglAcOffsetMain/bulkDelete',
    method: 'post',
    data: tglAcOffsetMains
  })
export const doOffset = (revertRecords) =>
  request({
    url: '/vouchermanager/tglAcOffsetMain/doOffset',
    method: 'post',
    data: revertRecords
  })
export const revertRush = (revertRecords) =>
  request({
    url: '/vouchermanager/tglAcOffsetMain/revertRush',
    method: 'post',
    data: revertRecords
  })
export const getOffsetData = (search) =>
  request({
    url: '/vouchermanager/tglAcOffsetMain/getOffsetData',
    method: 'post',
    data: search
  })
export const getOnAccountData = (search) =>
  request({
    url: '/vouchermanager/tglAcOffsetMain/getOnAccountData',
    method: 'post',
    data: search
  })
export const getdata = (search) =>
  request({
    url: '/vouchermanager/tglAcOffsetMain/getdata',
    method: 'post',
    data: search
  })
export const accountlist = (search) =>
  request({
    url: '/vouchermanager/tglAcOffsetMain/accountlist',
    method: 'post',
    data: search
  })
export const deletedata = (search) =>
  request({
    url: '/vouchermanager/tglAcOffsetMain/deletedata',
    method: 'post',
    data: search
  })
export const exportExcels = (formData) =>
  request({
    url: '/vouchermanager/tglAcOffsetMain/exportExcels',
    method: 'post',
    data: formData,
    responseType: 'blob'
  })
export const doManualRush = (revertRecords) =>
  request({
    url: '/vouchermanager/tglAcOffsetMain/doManualRush',
    method: 'post',
    data: revertRecords
  })
export const importExcel = (formData) =>
  request({
    url: '/vouchermanager/tglAcOffsetMain/importExcel',
    method: 'post',
    data: formData
  })
