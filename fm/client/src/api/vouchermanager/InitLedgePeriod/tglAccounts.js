import request from "@/utils/request";

export const getTglAccountsById = id =>
  request({
    url: "/vouchermanager/InitLedgePeriod/tglAccounts/" + id,
    method: "get"
  });

export const listTglAccountsPage = search =>
  request({
    url: "/vouchermanager/InitLedgePeriod/tglAccounts/list",
    method: "post",
    data: search
  });

export const listTglAccountsAll = search =>
  request({
    url: "/vouchermanager/InitLedgePeriod/tglAccounts/listAll",
    method: "post",
    data: search
  });

export const saveTglAccounts = tglAccounts =>
  request({
    url: "/vouchermanager/InitLedgePeriod/tglAccounts/save",
    method: "post",
    data: tglAccounts
  });

export const deleteTglAccounts = tglAccounts =>
  request({
    url: "/vouchermanager/InitLedgePeriod/tglAccounts/delete",
    method: "post",
    data: tglAccounts
  });

export const bulkInsertTglAccounts = tglAccountss =>
  request({
    url: "/vouchermanager/InitLedgePeriod/tglAccounts/bulkInsert",
    method: "post",
    data: tglAccountss
  });

export const bulkUpdateTglAccounts = tglAccountss =>
  request({
    url: "/vouchermanager/InitLedgePeriod/tglAccounts/bulkUpdate",
    method: "post",
    data: tglAccountss
  });

export const bulkDeleteTglAccounts = tglAccountss =>
  request({
    url: "/vouchermanager/InitLedgePeriod/tglAccounts/bulkDelete",
    method: "post",
    data: tglAccountss
  });


  // 停用
  export const updateflagclose = ids =>
  request({
    url: "/vouchermanager/InitLedgePeriod/tglAccounts/updateflagclose",
    method: "post",
    data: ids
  });

  // 启用
  export const updateflagstart = ids =>
  request({
    url: "/vouchermanager/InitLedgePeriod/tglAccounts/updateflagstart",
    method: "post",
    data: ids
  });

export const getSubjectlevelList = search =>
  request({
    url: "/vouchermanager/InitLedgePeriod/tglAccounts/listSubjectlevel",
    method: "post",
    data: ''
  });
