import request from '@/utils/request'

export const getAssettingOrgDepartmentById = (id) =>
  request({
    url: '/assetting/assettingOrgDepartment/' + id,
    method: 'get'
  })

export const listAssettingOrgDepartmentPage = (search) =>
  request({
    url: '/assetting/assettingOrgDepartment/list',
    method: 'post',
    data: search
  })

export const listAssettingOrgDepartmentAll = (search) =>
  request({
    url: '/assetting/assettingOrgDepartment/listAll',
    method: 'post',
    data: search
  })

export const saveAssettingOrgDepartment = (assettingOrgDepartment) =>
  request({
    url: '/assetting/assettingOrgDepartment/save',
    method: 'post',
    data: assettingOrgDepartment
  })

export const deleteAssettingOrgDepartment = (assettingOrgDepartment) =>
  request({
    url: '/assetting/assettingOrgDepartment/delete',
    method: 'post',
    data: assettingOrgDepartment
  })

export const bulkInsertAssettingOrgDepartment = (assettingOrgDepartments) =>
  request({
    url: '/assetting/assettingOrgDepartment/bulkInsert',
    method: 'post',
    data: assettingOrgDepartments
  })

export const bulkUpdateAssettingOrgDepartment = (assettingOrgDepartments) =>
  request({
    url: '/assetting/assettingOrgDepartment/bulkUpdate',
    method: 'post',
    data: assettingOrgDepartments
  })

export const bulkDeleteAssettingOrgDepartment = (assettingOrgDepartments) =>
  request({
    url: '/assetting/assettingOrgDepartment/bulkDelete',
    method: 'post',
    data: assettingOrgDepartments
  })
