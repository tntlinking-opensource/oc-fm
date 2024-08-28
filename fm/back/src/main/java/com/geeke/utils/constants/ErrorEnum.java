package com.geeke.utils.constants;

/**
 * @author: lys
 * @date: 2017/10/24 10:16
 */
public enum ErrorEnum {
	
	/*
	 * 警告类信息
	 */
	E_50001("50001", "保存数据重复"),
    E_50002("50001", "同时修改数据冲突"),
	
    /*
     * 错误信息
     * */
    E_400("400", "请求处理异常，请稍后再试"),
    E_500("500", "请求方式有误,请检查 GET/POST"),
    E_501("501", "请求路径不存在"),
    E_502("502", "权限不足"),
    E_10008("10008", "角色删除失败,尚有用户属于此角色"),
    E_10009("10009", "账户已存在"),
    E_10010("10010", "登录账号或密码错误"),
    E_10011("10011", "账号已经禁用"),
    E_10012("10012", "账户验证失败"),
    
    E_20011("20011", "登陆已过期或未登录,请重新登陆"),
    
    E_30001("30001", "流程引擎异常"),
    E_30002("30002", "流程引擎警告"),

    E_60001("60001", "包含非法字符，请求错误"),

    // Dataease 接口错误
    E_80001("80001", "Dataease 接口错误"),
    E_80002("80002", "Dataease 数据加密失败"),

    E_90003("90003", "缺少必填参数"),

    E_100001("100001","资产审核状态缺项,请联系管理员"),

    E_100002("100002","资产审核异常"),

    E_100003("100003","只能对[新增待审核]的卡片进行复制"),

    E_100004("100004","任务已执行，无法修改"),
    E_100005("100005","资产无效，请确认该资产有效性"),
    E_100006("100006","资产已盘点，无需继续盘点"),
    ;

    private String errorCode;

    private String errorMsg;

    ErrorEnum() {
    }

    ErrorEnum(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
