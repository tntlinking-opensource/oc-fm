const date = new Date()

/**
 * 常用常量
 */
export const COMM_CONST = {
  // 新增
  ADD: "add",
  // 编辑
  EDIT: "edit",
  // 删除
  DELETE: "delete",
  // 查询
  VIEW: "view",
  // 重置
  REST: "rest",
  // 导出/下载
  EXPORT: "export",
  // 正常
  PRIMARY: "primary",
  // 成功
  SUCCESS: "success",
  // 警告
  WARNING: "warning",
  // 危险
  DANGER: "danger",
  // 无权限控制
  NONE_PERMI: ["none"],
  // 搜索、查询图标
  SEARCH_ICON: "vxe-icon-search",
  // 发送、发起、派单图标
  SEND_ICON: "vxe-icon-send-fill",
  // 新增、添加图标
  ADD_ICON: "vxe-icon-add",
  // 修改图标
  EDIT_ICON: "vxe-icon-edit",
  // 删除图标
  DELETE_ICON: "vxe-icon-delete-fill",
  // 重置图标
  REPEAT_ICON: "vxe-icon-repeat",
  // 警告图标
  WARNING_ICON: "vxe-icon-warning-triangle",
  // 刷新图标
  REFRESH_ICON: "vxe-icon-refresh",
  // 导出、下载
  DOWNLOAD_ICON: "vxe-icon-download",
  // 导入、上传
  UPLOAD_ICON: "vxe-icon-upload",
  // 文本文件图标
  FILE_ICON: "vxe-icon-file-txt",
  // 保存
  SAVE_ICON: "vxe-icon-save",
  // 锁定
  LOCK_ICON: "vxe-icon-lock",
  // 解锁
  UNLOCK_ICON: "vxe-icon-unlock",
  // 信息图标
  INFO_ICON:"vxe-icon-info-circle",
  // 问题、问号图标
  QUESTION_ICON:"vxe-icon-question-circle",
  // 上个月
  YESTERDATE:new Date(date.getFullYear(),date.getMonth()-1,1),
  // 常量时间
  DEFDATE:new Date()
};

export function GET_OPERATOR(str) {
  return str.indexOf("+") > -1
    ? "+"
    : str.indexOf("-") > -1
    ? "-"
    : str.indexOf("*") > -1
    ? "*"
    : "/";
}

export function GET_LOGICAL(code) {
  return code.indexOf(">") > -1
    ? ">"
    : code.indexOf("<") > -1
    ? "<"
    : code.indexOf("=") > -1
    ? "="
    : "!=";
}

export const defTablePage = {
  total: 0,
  currentPage: 1,
  pageSize: 20,
};
