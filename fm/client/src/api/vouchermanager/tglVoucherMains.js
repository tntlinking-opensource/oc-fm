import request from "@/utils/request";

export const getTglVoucherMainsById = id =>
  request({
    url: "/vouchermanager/tglVoucherMains/" + id,
    method: "get"
  });

export const listTglVoucherMainsPage = search =>
  request({
    url: "/vouchermanager/tglVoucherMains/list",
    method: "post",
    data: search
  });
export const xjlllistPage = search =>
  request({
    url: "/vouchermanager/tglVoucherMains/xjlllistPage",
    method: "post",
    data: search
  });

export const listTglVoucherMainsAll = search =>
  request({
    url: "/vouchermanager/tglVoucherMains/listAll",
    method: "post",
    data: search
  });

export const saveTglVoucherMains = tglVoucherMains =>
  request({
    url: "/vouchermanager/tglVoucherMains/save",
    method: "post",
    data: tglVoucherMains
  });

export const deleteTglVoucherMains = tglVoucherMains =>
  request({
    url: "/vouchermanager/tglVoucherMains/delete",
    method: "post",
    data: tglVoucherMains
  });

export const bulkInsertTglVoucherMains = tglVoucherMainss =>
  request({
    url: "/vouchermanager/tglVoucherMains/bulkInsert",
    method: "post",
    data: tglVoucherMainss
  });

export const bulkUpdateTglVoucherMains = tglVoucherMainss =>
  request({
    url: "/vouchermanager/tglVoucherMains/bulkUpdate",
    method: "post",
    data: tglVoucherMainss
  });

export const bulkDeleteTglVoucherMains = tglVoucherMainss =>
  request({
    url: "/vouchermanager/tglVoucherMains/bulkDelete",
    method: "post",
    data: tglVoucherMainss
  });
/**
 * 凭证出纳
 * @param entitys
 * @param vouchercheckdate
 */
export const voucherCash = (entitys, vouchercheckdate) =>
  request({
    url: "/vouchermanager/tglVoucherMains/cash/" + vouchercheckdate,
    method: "post",
    data: entitys
  });
/**
 * 凭证审核
 * @param entitys
 * @param vouchercheckdate
 */
export const voucherCheck = (entitys, vouchercheckdate) =>
  request({
    url: "/vouchermanager/tglVoucherMains/check/" + vouchercheckdate,
    method: "post",
    data: entitys
  });
/**
 * 凭证记账
 * @param entitys
 * @param vouchercheckdate
 */
export const voucherEnd = (entitys, vouchercheckdate) =>
  request({
    url: "/vouchermanager/tglVoucherMains/end/" + vouchercheckdate,
    method: "post",
    data: entitys
  });
/**
 * 凭证反出纳
 * @param entitys
 */
export const unVoucherCash = entitys =>
  request({
    url: "/vouchermanager/tglVoucherMains/uncash",
    method: "post",
    data: entitys
  });
/**
 * 凭证反审核
 * @param entitys
 */
export const unVoucherCheck = entitys =>
  request({
    url: "/vouchermanager/tglVoucherMains/uncheck",
    method: "post",
    data: entitys
  });
/**
 * 凭证反记账
 * @param entitys
 */
export const unVouCherEnd = entitys =>
  request({
    url: "/vouchermanager/tglVoucherMains/unEnd",
    method: "post",
    data: entitys
  });
export const autoconvert = entitys =>
  request({
    url: "/vouchermanager/tglVoucherMains/autoconvert",
    method: "post",
    data: entitys
  });

// 会计期
export const selectPeriod = () =>
  request({
    url: "/tgl/global/globalPeriods/selectPeriod",
    method: "get",
    data: {
      // 查询条件   业务表设置的筛选条件
      params: []
    }
  });

// 凭证字号
export const tglVoucherNumberings = () =>
  request({
    url: "/vouchermanager/tglVoucherNumberings/listAll",
    method: "post",
    data: {
      // 查询条件   业务表设置的筛选条件
      params: []
    }
  });
