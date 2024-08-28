import request from '@/utils/request'

export const getTableName = (search) =>
  request({
    url: '/tgl/ledger/report/accountBook',
    method: 'post',
    data: search
  })
