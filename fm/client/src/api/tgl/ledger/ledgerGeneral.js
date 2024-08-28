import request from '@/utils/request'

export const getTableName = (search) =>
  request({
    url: '/tgl/ledger/report/ledgerGeneral',
    method: 'post',
    data: search
  })
