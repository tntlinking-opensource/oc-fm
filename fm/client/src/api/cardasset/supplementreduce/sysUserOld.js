import request from '@/utils/request'

export const getSysUserOldById = (id) =>
  request({
    url: '/cardasset/supplementreduce/sysUserOld/' + id,
    method: 'get'
  })

export const listSysUserOldPage = (search) =>
  request({
    url: '/cardasset/supplementreduce/sysUserOld/list',
    method: 'post',
    data: search
  })

export const listSysUserOldAll = (search) =>
  request({
    url: '/cardasset/supplementreduce/sysUserOld/listAll',
    method: 'post',
    data: search
  })

export const saveSysUserOld = (sysUserOld) =>
  request({
    url: '/cardasset/supplementreduce/sysUserOld/save',
    method: 'post',
    data: sysUserOld
  })

export const deleteSysUserOld = (sysUserOld) =>
  request({
    url: '/cardasset/supplementreduce/sysUserOld/delete',
    method: 'post',
    data: sysUserOld
  })

export const bulkInsertSysUserOld = (sysUserOlds) =>
  request({
    url: '/cardasset/supplementreduce/sysUserOld/bulkInsert',
    method: 'post',
    data: sysUserOlds
  })

export const bulkUpdateSysUserOld = (sysUserOlds) =>
  request({
    url: '/cardasset/supplementreduce/sysUserOld/bulkUpdate',
    method: 'post',
    data: sysUserOlds
  })

export const bulkDeleteSysUserOld = (sysUserOlds) =>
  request({
    url: '/cardasset/supplementreduce/sysUserOld/bulkDelete',
    method: 'post',
    data: sysUserOlds
  })
