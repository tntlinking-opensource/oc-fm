import request from '@/utils/request'

export const getTfamAssetCheckplanMainById = (id) =>
  request({
    url: '/checkplan/checkplanmain/tfamAssetCheckplanMain/' + id,
    method: 'get'
  })

export const listTfamAssetCheckplanMainPage = (search) =>
  request({
    url: '/checkplan/checkplanmain/tfamAssetCheckplanMain/list',
    method: 'post',
    data: search
  })

export const listTfamAssetCheckplanMainAll = (search) =>
  request({
    url: '/checkplan/checkplanmain/tfamAssetCheckplanMain/listAll',
    method: 'post',
    data: search
  })

export const saveTfamAssetCheckplanMain = (tfamAssetCheckplanMain) =>
  request({
    url: '/checkplan/checkplanmain/tfamAssetCheckplanMain/save',
    method: 'post',
    data: tfamAssetCheckplanMain
  })

export const deleteTfamAssetCheckplanMain = (tfamAssetCheckplanMain) =>
  request({
    url: '/checkplan/checkplanmain/tfamAssetCheckplanMain/delete',
    method: 'post',
    data: tfamAssetCheckplanMain
  })

export const bulkInsertTfamAssetCheckplanMain = (tfamAssetCheckplanMains) =>
  request({
    url: '/checkplan/checkplanmain/tfamAssetCheckplanMain/bulkInsert',
    method: 'post',
    data: tfamAssetCheckplanMains
  })

export const bulkUpdateTfamAssetCheckplanMain = (tfamAssetCheckplanMains) =>
  request({
    url: '/checkplan/checkplanmain/tfamAssetCheckplanMain/bulkUpdate',
    method: 'post',
    data: tfamAssetCheckplanMains
  })

export const bulkDeleteTfamAssetCheckplanMain = (tfamAssetCheckplanMains) =>
  request({
    url: '/checkplan/checkplanmain/tfamAssetCheckplanMain/bulkDelete',
    method: 'post',
    data: tfamAssetCheckplanMains
  })

  // 查询资产数量和单位
export const getByStatusAndCategory = (tfamAssetCheckplanMain) =>
  request({
    url: '/checkplan/checkplanmain/tfamAssetCheckplanMain/getByStatusAndCategory',
    method: 'post',
    data: tfamAssetCheckplanMain
  })

    // 修改资产
export const getTfamAssetCheckplandetail = (tfamAssetCheckplanMain) =>
  request({
    url: '/checkplan/checkplanmain/tfamAssetCheckplanMain/detail',
    method: 'post',
    data: tfamAssetCheckplanMain
  })

      // 下载
export const downloadCheckplandetail = (params) =>
  request({
    url: '/checkplan/checkplanmain/tfamAssetCheckplanMain/download',
    method: 'post',
    data: params,
    responseType: 'blob'
  })
  
