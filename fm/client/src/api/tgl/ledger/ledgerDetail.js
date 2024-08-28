import request from '@/utils/request'

export const getTableName = (search) =>
  request({
    url: '/tgl/ledger/report/ledgerDetail',
    method: 'post',
    data: search
  })
