import request from '@/utils/request'

export const getUreportFileById = (id) =>
  request({
    url: '/report/reportFile/' + id,
    method: 'get'
  })

export const listUreportFilePage = (search) =>
  request({
    url: '/report/reportFile/list',
    method: 'post',
    data: search
  })

export const listUreportFileAll = (search) =>
  request({
    url: '/report/reportFile/listAll',
    method: 'post',
    data: search
  })

export const saveUreportFileTbl = (ureportFileTbl) =>
  request({
    url: '/report/reportFile/save',
    method: 'post',
    data: ureportFileTbl
  })

export const updateUreportFile = (ureportFile) =>
  request({
    url: '/report/reportFile/update',
    method: 'post',
    data: ureportFile
  })

export const startUreportFile = (ureportFile) =>
  request({
    url: '/report/reportFile/start',
    method: 'post',
    data: ureportFile
  })

export const deleteUreportFile = (ureportFile) =>
  request({
    url: '/report/reportFile/delete',
    method: 'post',
    data: ureportFile
  })

