import request from '@/utils/request'

export const getSysUserById = (id) =>
  request({
    url: '/vouchermanager/sysUser/' + id,
    method: 'get'
  })

export const listSysUserPage = (search) =>
  request({
    url: '/vouchermanager/sysUser/list',
    method: 'post',
    data: search
  })

export const listSysUserAll = (search) =>
  request({
    url: '/vouchermanager/sysUser/listAll',
    method: 'post',
    data: search
  })

export const saveSysUser = (sysUser) =>
  request({
    url: '/vouchermanager/sysUser/save',
    method: 'post',
    data: sysUser
  })

export const deleteSysUser = (sysUser) =>
  request({
    url: '/vouchermanager/sysUser/delete',
    method: 'post',
    data: sysUser
  })

export const bulkInsertSysUser = (sysUsers) =>
  request({
    url: '/vouchermanager/sysUser/bulkInsert',
    method: 'post',
    data: sysUsers
  })

export const bulkUpdateSysUser = (sysUsers) =>
  request({
    url: '/vouchermanager/sysUser/bulkUpdate',
    method: 'post',
    data: sysUsers
  })

export const bulkDeleteSysUser = (sysUsers) =>
  request({
    url: '/vouchermanager/sysUser/bulkDelete',
    method: 'post',
    data: sysUsers
  })
