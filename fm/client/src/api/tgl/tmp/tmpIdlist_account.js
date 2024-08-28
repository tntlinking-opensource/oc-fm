import request from '@/utils/request'

export const listTmpIdlistPage = (search) =>
  request({
    url: '/tgl/tmp/tmpIdlist/list',
    method: 'post',
    data: search
  })

export const listTmpIdlistAll = (search) =>
  request({
    url: '/tgl/tmp/tmpIdlist/listAll',
    method: 'post',
    data: search
  })

export const saveTmpIdlist = (tmpIdlist) =>
  request({
    url: '/tgl/tmp/tmpIdlist/save',
    method: 'post',
    data: tmpIdlist
  })

