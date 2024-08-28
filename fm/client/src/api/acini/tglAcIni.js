import request from '@/utils/request'


export const listTglAcIniPage = (search) =>
  request({
    url: '/acini/tglAcIni/list',
    method: 'post',
    data: search
  })

export const listTglAcIniAll = (search) =>
  request({
    url: '/acini/tglAcIni/listAll',
    method: 'post',
    data: search
  })

export const saveTglAcIni = (tglAcIni) =>
  request({
    url: '/vouchermanager/tglAcOffsetMain/savedata',
    method: 'post',
    data: tglAcIni
  })

export const deleteTglAcIni = (tglAcIni) =>
  request({
    url: '/acini/tglAcIni/delete',
    method: 'post',
    data: tglAcIni
  })

export const bulkInsertTglAcIni = (tglAcInis) =>
  request({
    url: '/acini/tglAcIni/bulkInsert',
    method: 'post',
    data: tglAcInis
  })

export const bulkUpdateTglAcIni = (tglAcInis) =>
  request({
    url: '/acini/tglAcIni/bulkUpdate',
    method: 'post',
    data: tglAcInis
  })

export const bulkDeleteTglAcIni = (tglAcInis) =>
  request({
    url: '/acini/tglAcIni/bulkDelete',
    method: 'post',
    data: tglAcInis
  })
export const getTglAcIniById = (formData) =>
  request({
    url: '/vouchermanager/tglAcOffsetMain/editlist/'+formData,
    method: 'get'
  })
