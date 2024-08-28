import request from '@/utils/request'

export const getSysUserEntryById = (id) =>
  request({
    url: '/cardasset/credit/sysUserEntry/' + id,
    method: 'get'
  })

export const listSysUserEntryPage = (search) =>
  request({
    url: '/cardasset/credit/sysUserEntry/list',
    method: 'post',
    data: search
  })

export const listSysUserEntryAll = (search) =>
  request({
    url: '/cardasset/credit/sysUserEntry/listAll',
    method: 'post',
    data: search
  })

export const saveSysUserEntry = (sysUserEntry) =>
  request({
    url: '/cardasset/credit/sysUserEntry/save',
    method: 'post',
    data: sysUserEntry
  })

export const deleteSysUserEntry = (sysUserEntry) =>
  request({
    url: '/cardasset/credit/sysUserEntry/delete',
    method: 'post',
    data: sysUserEntry
  })

export const bulkInsertSysUserEntry = (sysUserEntrys) =>
  request({
    url: '/cardasset/credit/sysUserEntry/bulkInsert',
    method: 'post',
    data: sysUserEntrys
  })

export const bulkUpdateSysUserEntry = (sysUserEntrys) =>
  request({
    url: '/cardasset/credit/sysUserEntry/bulkUpdate',
    method: 'post',
    data: sysUserEntrys
  })

export const bulkDeleteSysUserEntry = (sysUserEntrys) =>
  request({
    url: '/cardasset/credit/sysUserEntry/bulkDelete',
    method: 'post',
    data: sysUserEntrys
  })
