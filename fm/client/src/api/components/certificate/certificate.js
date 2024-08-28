import request from "@/utils/request";

export const getAbstract = (abstract, offset, limit) =>
  request({
    url:
      "/certificate/getAbstract?data=" +
      abstract +
      "&offset=" +
      offset +
      "&limit=" +
      limit,
    method: "get"
  });

export const addAbstract = abstract =>
  request({
    url: "/certificate/addAbstract?data=" + abstract,
    method: "get"
  });

export const delAbstract = id =>
  request({
    url: "/certificate/delAbstract?id=" + id,
    method: "get"
  });

export const getPeriodList = () =>
  request({
    url: "/certificate/getPeriodList?",
    method: "get"
  });

// 会计期
export const tglGlobalPeriods = () =>
  request({
    url: "/vouchermanager/tglGlobalPeriods/listAll",
    method: "post",
    data: {
      columnName: "intyearmonth",
      limit: "",
      offset: "",
      order: "DESC",
      orderby: "",
      params: [
        { columnName: "intstatus", logic: "AND", queryType: "=", value: "2" }
      ]
    }
  });

// 获取分户的数据
export const getByUqaccountId = id =>
  request({
    url: `/vouchermanager/tglVoucherDetails/getByUqaccountId/${id}`,
    method: "get"
  });

export const tglVoucherMainsSave = data =>
  request({
    url: "/vouchermanager/tglVoucherMains/save",
    method: "POST",
    data: data
  });
