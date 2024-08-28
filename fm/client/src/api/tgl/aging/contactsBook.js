import request from '@/utils/request'

export const listContactsBookPage = (map) =>
  request({
    url: '/tgl/contactsbook/list',
    method: 'post',
    data: map
  })

export const uploadContactsBook = (map) =>
  request({
    url: '/tgl/contactsbook/export',
    method: 'post',
    data: map,
    responseType: 'blob'
  })

