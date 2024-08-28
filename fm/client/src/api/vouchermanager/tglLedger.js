import request from "@/utils/request";

export const getTglLedgerById = id =>
  request({
    url: "/vouchermanager/tglLedger/" + id,
    method: "get"
  });

export const listTglLedgerPage = search =>
  request({
    url: "/vouchermanager/tglLedger/list",
    method: "post",
    data: search
  });

export const listCompanyLedgerByLedgety = search =>
  request({
    url: "/vouchermanager/tglLedger/listCompanyLedger",
    method: "post",
    data: search
  });

export const listTglLedgerAll = search =>
  request({
    url: "/vouchermanager/tglLedger/listAll",
    method: "post",
    data: search
  });

export const saveTglLedger = tglLedger =>
  request({
    url: "/vouchermanager/tglLedger/save",
    method: "post",
    data: tglLedger
  });

export const deleteTglLedger = tglLedger =>
  request({
    url: "/vouchermanager/tglLedger/delete",
    method: "post",
    data: tglLedger
  });

export const bulkInsertTglLedger = tglLedgers =>
  request({
    url: "/vouchermanager/tglLedger/bulkInsert",
    method: "post",
    data: tglLedgers
  });

export const bulkUpdateTglLedger = tglLedgers =>
  request({
    url: "/vouchermanager/tglLedger/bulkUpdate",
    method: "post",
    data: tglLedgers
  });

export const bulkDeleteTglLedger = tglLedgers =>
  request({
    url: "/vouchermanager/tglLedger/bulkDelete",
    method: "post",
    data: tglLedgers
  });
export const listTglLedgerTree = () =>
  request({
    url: "/vouchermanager/tglLedgetype/listAll",
    method: "post",
    data: {
      params: []
    }
  });
