import request from '@/utils/request'

export const getTableName = (search) =>
  request({
    url: '/tgl/ledger/report/ledgerBook',
    method: 'post',
    data: search
  })
