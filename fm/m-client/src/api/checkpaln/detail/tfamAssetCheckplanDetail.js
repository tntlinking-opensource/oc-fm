import request from '@/utils/request'

export const getTfamAssetCheckplanDetailById = (id) =>
  request({
    url: '/checkpaln/detail/tfamAssetCheckplanDetail/' + id,
    method: 'get'
  })

export const listTfamAssetCheckplanDetailPage = (search) =>
  request({
    url: '/checkpaln/detail/tfamAssetCheckplanDetail/list',
    method: 'post',
    data: search
  })

export const listTfamAssetCheckplanDetailAll = (search) =>
  request({
    url: '/checkpaln/detail/tfamAssetCheckplanDetail/listAll',
    method: 'post',
    data: search
  })

export const saveTfamAssetCheckplanDetail = (tfamAssetCheckplanDetail) =>
  request({
    url: '/checkpaln/detail/tfamAssetCheckplanDetail/save',
    method: 'post',
    data: tfamAssetCheckplanDetail
  })

export const deleteTfamAssetCheckplanDetail = (tfamAssetCheckplanDetail) =>
  request({
    url: '/checkpaln/detail/tfamAssetCheckplanDetail/delete',
    method: 'post',
    data: tfamAssetCheckplanDetail
  })

export const bulkInsertTfamAssetCheckplanDetail = (tfamAssetCheckplanDetails) =>
  request({
    url: '/checkpaln/detail/tfamAssetCheckplanDetail/bulkInsert',
    method: 'post',
    data: tfamAssetCheckplanDetails
  })

export const bulkUpdateTfamAssetCheckplanDetail = (tfamAssetCheckplanDetails) =>
  request({
    url: '/checkpaln/detail/tfamAssetCheckplanDetail/bulkUpdate',
    method: 'post',
    data: tfamAssetCheckplanDetails
  })

export const bulkDeleteTfamAssetCheckplanDetail = (tfamAssetCheckplanDetails) =>
  request({
    url: '/checkpaln/detail/tfamAssetCheckplanDetail/bulkDelete',
    method: 'post',
    data: tfamAssetCheckplanDetails
  })

  // 查询资产盘点列表
export const selectPlanByPerson = (tfamAssetCheckplanDetails) =>
  request({
    url: '/checkplan/checkplanmain/tfamAssetCheckplanMain/selectPlanByPerson',
    method: 'post',
    data: tfamAssetCheckplanDetails
  })

  // 提交
export const personCheck = (tfamAssetCheckplanDetails) =>
  request({
    url: '/checkplan/checkplanmain/tfamAssetCheckplanMain/personCheck',
    method: 'post',
    data: tfamAssetCheckplanDetails
  })

  // 上传
export const saveCheckAttach = (tfamAssetCheckplanDetails) =>
  request({
    url: '/checkplan/checkplanmain/tfamAssetCheckplanMain/saveCheckAttach',
    method: 'post',
    data: tfamAssetCheckplanDetails
  })

  // 负责人
  export const listUserAll = (search) =>
  request({
    url: '/admin/user/listAll',
    method: 'post',
    data: search
  })

  // 部门
  export const treeDepartment = (search) =>
  request({
    url: '/org/department/tree',
    method: 'post',
    data: search
  })

  // 查询卡片信息
  export const getTfamAssetById = (id) =>
  request({
    url: '/fixasset/asset/tfamAsset/' + id,
    method: 'get'
  })
