import request from '@/utils/request'

export const getAccountLedgetypeById = (id) =>
  request({
    url: '/vouchermanager/accountLedgetype/' + id,
    method: 'get'
  })

export const listAccountLedgetypePage = (search) =>
  request({
    url: '/vouchermanager/accountLedgetype/list',
    method: 'post',
    data: search
  })

export const listAccountLedgetypeAll = (search) =>
  request({
    url: '/vouchermanager/accountLedgetype/listAll',
    method: 'post',
    data: search
  })

export const saveAccountLedgetype = (accountLedgetype) =>
  request({
    url: '/vouchermanager/accountLedgetype/save',
    method: 'post',
    data: accountLedgetype
  })

export const deleteAccountLedgetype = (accountLedgetype) =>
  request({
    url: '/vouchermanager/accountLedgetype/delete',
    method: 'post',
    data: accountLedgetype
  })

export const bulkInsertAccountLedgetype = (accountLedgetypes) =>
  request({
    url: '/vouchermanager/accountLedgetype/bulkInsert',
    method: 'post',
    data: accountLedgetypes
  })

export const bulkUpdateAccountLedgetype = (accountLedgetypes) =>
  request({
    url: '/vouchermanager/accountLedgetype/bulkUpdate',
    method: 'post',
    data: accountLedgetypes
  })

export const bulkDeleteAccountLedgetype = (accountLedgetypes) =>
  request({
    url: '/vouchermanager/accountLedgetype/bulkDelete',
    method: 'post',
    data: accountLedgetypes
  })
