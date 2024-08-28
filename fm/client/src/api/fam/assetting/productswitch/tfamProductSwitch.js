import request from '@/utils/request'

export const getTfamProductSwitchById = (id) =>
  request({
    url: '/assetting/tfamProductSwitch/' + id,
    method: 'get'
  })

export const listTfamProductSwitchPage = (search) =>
  request({
    url: '/assetting/tfamProductSwitch/list',
    method: 'post',
    data: search
  })

export const listTfamProductSwitchAll = (search) =>
  request({
    url: '/assetting/tfamProductSwitch/listAll',
    method: 'post',
    data: search
  })

export const saveTfamProductSwitch = (tfamProductSwitch) =>
  request({
    url: '/assetting/tfamProductSwitch/save',
    method: 'post',
    data: tfamProductSwitch
  })

export const deleteTfamProductSwitch = (tfamProductSwitch) =>
  request({
    url: '/assetting/tfamProductSwitch/delete',
    method: 'post',
    data: tfamProductSwitch
  })

export const bulkInsertTfamProductSwitch = (tfamProductSwitchs) =>
  request({
    url: '/assetting/tfamProductSwitch/bulkInsert',
    method: 'post',
    data: tfamProductSwitchs
  })

export const bulkUpdateTfamProductSwitch = (tfamProductSwitchs) =>
  request({
    url: '/assetting/tfamProductSwitch/bulkUpdate',
    method: 'post',
    data: tfamProductSwitchs
  })

export const bulkDeleteTfamProductSwitch = (tfamProductSwitchs) =>
  request({
    url: '/assetting/tfamProductSwitch/bulkDelete',
    method: 'post',
    data: tfamProductSwitchs
  })
