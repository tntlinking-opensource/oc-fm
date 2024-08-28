import request, { setUrlParams } from "@/utils/request";

export const listTmpIdlistPage = search =>
  request({
    url: "/handler/grouptenant/list",
    method: "post",
    data: search
  });

export const editTmpIdlist = form =>
  request({
    url: "/handler/grouptenant/groupedit",
    method: "post",
    data: setUrlParams([{ key: "jsonString", val: JSON.stringify(form) }])
  });

export const saveTmpIdlist = form =>
  request({
    url: "/handler/grouptenant/add",
    method: "post",
    data: setUrlParams([{ key: "jsonString", val: JSON.stringify(form) }])
  });

export const deleteTmp = tmp =>
  request({
    url: "/handler/grouptenant/delete",
    method: "delete",
    data: setUrlParams([{ key: "accountid", val: tmp }])
  });

export const synchronizeAccounts = form =>
  request({
    url: "/handler/grouptenant/synchronizeAccounts",
    method: "post",
    data: setUrlParams([
      { key: "tenantidList", val: "111" },
      { key: "jsonString", val: form }
    ])
  });

export const initializationAccounts = form =>
  request({
    url: "/handler/grouptenant/initializationAccounts",
    method: "post",
    data: setUrlParams([
      { key: "tenantidList", val: "111" }
    ])
  });

export const exportAccountInfo = (formData) =>
  request({
    url: '/handler/grouptenant/exportAccountInfo',
    method: 'post',
    data: formData,
    responseType: 'blob'
  })
