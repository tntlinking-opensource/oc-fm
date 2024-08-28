import request from '@/utils/request'

export const getBookStatus = () =>
  request({
    url: '/ChronologicalAccount/getAccountingBookStatus/',
    method: 'get'
  })

export const getHkontLevel = () =>
  request({
    url: '/ChronologicalAccount/getHkontLevel/',
    method: 'get'
  })

export const queryHkont = () =>
  request({
    url: '/ChronologicalAccount/queryHkontList/',
    method: 'get'
  })

export const getCompanyList = (searchParams) =>

  request({
    url: '/org/company/list',
    method: 'post',
    data:searchParams
  })
