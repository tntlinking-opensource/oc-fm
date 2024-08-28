import request from '@/utils/request'

export const getTfamDefaultSettingsById = (id) =>
  request({
    url: '/assetting/tfamDefaultSettings/' + id,
    method: 'get'
  })

export const listTfamDefaultSettingsPage = (search) =>
  request({
    url: '/assetting/tfamDefaultSettings/list',
    method: 'post',
    data: search
  })

export const listTfamDefaultSettingsAll = (search) =>
  request({
    url: '/assetting/tfamDefaultSettings/listAll',
    method: 'post',
    data: search
  })

export const saveTfamDefaultSettings = (tfamDefaultSettings) =>
  request({
    url: '/assetting/tfamDefaultSettings/save',
    method: 'post',
    data: tfamDefaultSettings
  })

export const deleteTfamDefaultSettings = (tfamDefaultSettings) =>
  request({
    url: '/assetting/tfamDefaultSettings/delete',
    method: 'post',
    data: tfamDefaultSettings
  })

export const bulkInsertTfamDefaultSettings = (tfamDefaultSettingss) =>
  request({
    url: '/assetting/tfamDefaultSettings/bulkInsert',
    method: 'post',
    data: tfamDefaultSettingss
  })

export const bulkUpdateTfamDefaultSettings = (tfamDefaultSettingss) =>
  request({
    url: '/assetting/tfamDefaultSettings/bulkUpdate',
    method: 'post',
    data: tfamDefaultSettingss
  })

export const bulkDeleteTfamDefaultSettings = (tfamDefaultSettingss) =>
  request({
    url: '/assetting/tfamDefaultSettings/bulkDelete',
    method: 'post',
    data: tfamDefaultSettingss
  })
export const defaultsettingsave = (data) =>
  request({
    url: '/assetting/tfamDefaultSettings/defaultsettingsave',
    method: 'post',
    data: data
  })
export const getData = (data) =>
  request({
    url: '/assetting/tfamDefaultSettings/getData',
    method: 'post',
    data: data
  })
