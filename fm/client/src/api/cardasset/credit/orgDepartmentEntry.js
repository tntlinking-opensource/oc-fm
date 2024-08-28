import request from '@/utils/request'

export const getOrgDepartmentEntryById = (id) =>
  request({
    url: '/cardasset/credit/orgDepartmentEntry/' + id,
    method: 'get'
  })

export const listOrgDepartmentEntryPage = (search) =>
  request({
    url: '/cardasset/credit/orgDepartmentEntry/list',
    method: 'post',
    data: search
  })

export const listOrgDepartmentEntryAll = (search) =>
  request({
    url: '/cardasset/credit/orgDepartmentEntry/listAll',
    method: 'post',
    data: search
  })

export const saveOrgDepartmentEntry = (orgDepartmentEntry) =>
  request({
    url: '/cardasset/credit/orgDepartmentEntry/save',
    method: 'post',
    data: orgDepartmentEntry
  })

export const deleteOrgDepartmentEntry = (orgDepartmentEntry) =>
  request({
    url: '/cardasset/credit/orgDepartmentEntry/delete',
    method: 'post',
    data: orgDepartmentEntry
  })

export const bulkInsertOrgDepartmentEntry = (orgDepartmentEntrys) =>
  request({
    url: '/cardasset/credit/orgDepartmentEntry/bulkInsert',
    method: 'post',
    data: orgDepartmentEntrys
  })

export const bulkUpdateOrgDepartmentEntry = (orgDepartmentEntrys) =>
  request({
    url: '/cardasset/credit/orgDepartmentEntry/bulkUpdate',
    method: 'post',
    data: orgDepartmentEntrys
  })

export const bulkDeleteOrgDepartmentEntry = (orgDepartmentEntrys) =>
  request({
    url: '/cardasset/credit/orgDepartmentEntry/bulkDelete',
    method: 'post',
    data: orgDepartmentEntrys
  })
