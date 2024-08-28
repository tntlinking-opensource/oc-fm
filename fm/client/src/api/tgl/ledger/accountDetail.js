import request from '@/utils/request'

export const getTableName = (search) =>
  request({
    url: '/tgl/ledger/report/accountDetail',
    method: 'post',
    data: search
  })
