import request from '@/utils/request'

export const getAssettingSysUserById = (id) =>
  request({
    url: '/assetting/assettingSysUser/' + id,
    method: 'get'
  })

export const listAssettingSysUserPage = (search) =>
  request({
    url: '/assetting/assettingSysUser/list',
    method: 'post',
    data: search
  })

export const listAssettingSysUserAll = (search) =>
  request({
    url: '/assetting/assettingSysUser/listAll',
    method: 'post',
    data: search
  })

export const saveAssettingSysUser = (assettingSysUser) =>
  request({
    url: '/assetting/assettingSysUser/save',
    method: 'post',
    data: assettingSysUser
  })

export const deleteAssettingSysUser = (assettingSysUser) =>
  request({
    url: '/assetting/assettingSysUser/delete',
    method: 'post',
    data: assettingSysUser
  })

export const bulkInsertAssettingSysUser = (assettingSysUsers) =>
  request({
    url: '/assetting/assettingSysUser/bulkInsert',
    method: 'post',
    data: assettingSysUsers
  })

export const bulkUpdateAssettingSysUser = (assettingSysUsers) =>
  request({
    url: '/assetting/assettingSysUser/bulkUpdate',
    method: 'post',
    data: assettingSysUsers
  })

export const bulkDeleteAssettingSysUser = (assettingSysUsers) =>
  request({
    url: '/assetting/assettingSysUser/bulkDelete',
    method: 'post',
    data: assettingSysUsers
  })
