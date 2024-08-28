package com.geeke.cardasset.supplementreduce.util;


import com.alibaba.fastjson.JSONObject;

public class DepUtils
{
    //月折旧额
    public static final String DEPAMOUNT = "depamount";
    //月折旧率
    public static final String DEPREAT = "depreat";
    //净值
    public static final String DEPVALUE = "depvalue";
    //折旧累计
    public static final String DEPACCOUNT = "depaccount";
    //剩余月数
    public static final String DEPMONTH = "depmonth";
    //愈龄标志
    public static final String DEPFLAG = "depflag";

    /**
     * 每月折旧 平均年限法
     * @param value 原值
     * @param netvalue 净值
     * @param salvage 残值
     * @param salvagerate 残值率
     * @param accudep 累计折旧
     * @param naturemonth 预计使用月数
     * @param servicemonth 剩余使用月数
     * @return EntityMap {depamount:月折旧额，depreat:月折旧率, depvalue:净值, depaccount:折旧累计,
     *          depmonth:剩余月数, depflag:愈龄标志（1-愈龄、0-未愈龄）}
     */
    public static JSONObject dep_ag(double value, double netvalue, double salvage, double salvagerate,
                                   double accudep, int naturemonth, int servicemonth )
    {
        double depreat = BigDecimalUtils.formatBigDecimalToDouble((100 - salvagerate) / naturemonth);
        double depamount = BigDecimalUtils.formatBigDecimalToDouble((100 - salvagerate) / naturemonth / 100 * value);
        int depmonth = servicemonth - 1;
        int depflag = 0;
        if (netvalue - salvage <= depamount || depmonth == 0)
        {
            depamount = BigDecimalUtils.formatBigDecimalToDouble(netvalue - salvage);
            if (value - salvage!=0){
                depreat = BigDecimalUtils.formatBigDecimalToDouble((netvalue - salvage) / (value - salvage) * 100);
            }else {
                depreat=0;
            }

            depmonth = 0;
            depflag = 1;
        }
        double depvalue = BigDecimalUtils.formatBigDecimalToDouble(netvalue - depamount);
        double depaccount = BigDecimalUtils.formatBigDecimalToDouble(accudep + depamount);

        JSONObject resMap = new JSONObject();
        resMap.put(DEPAMOUNT, depamount);
        resMap.put(DEPREAT, depreat);
        resMap.put(DEPVALUE, depvalue);
        resMap.put(DEPACCOUNT, depaccount);
        resMap.put(DEPMONTH, depmonth);
        resMap.put(DEPFLAG, depflag);
        return resMap;
    }

    /**
     * 每月折旧 双倍余额递减法
     * @param value 原值
     * @param netvalue 净值
     * @param salvage 残值
     * @param accudep 累计折旧
     * @param yearaccudep 截止上年折旧累计
     * @param naturemonth 预计使用月数
     * @param servicemonth 剩余使用月数
     * @param usedmonth 已使用月数
     * @param dbflag 标志 是否已转换为平均年限(0-未转换，1-已转换)
     * @return EntityMap {depamount:月折旧额，depreat:月折旧率, depvalue:净值, depaccount:折旧累计,
     *          depmonth:剩余月数, depflag:愈龄标志（1-愈龄、0-未愈龄）, dbflag: 折旧方式转换标志(0-未转换，1-已转换)}
     */
    public static JSONObject dep_db(double value, double netvalue, double salvage, double accudep,
                                   double yearaccudep, int naturemonth, int servicemonth, int usedmonth, int dbflag )
    {
        double depreat = 0.00d;
        double depamount = 0.00d;
        //预计使用年数
        double naturyears = Math.ceil(naturemonth / 12);
        //年初固定资产折余价值
        double yearvalue = value - yearaccudep;
        //年折旧额
        double yeardep = BigDecimalUtils.formatBigDecimalToDouble(yearvalue * 2 / naturyears);
        //年初固定资产折余价值 - 本年年折旧额（双倍余额）>残值
        double ifvalue = yearvalue - yeardep;
        //当前是否最后一年
        boolean iflastyear = usedmonth % 12 + servicemonth <= 12 ? true : false;
        //没有转换成平均年限法 且 年初固定资产折余价值 - 本年年折旧额（双倍余额）>残值 且 不是最后一年
//        if (0 == dbflag && ifvalue > salvage && !iflastyear)
        //双倍余额递减法 转换折旧方法标志 改为servicemonth剩余使用月数小于2年(24个月)变更为//平均年限法
        if (0 == dbflag && servicemonth > 24 && !iflastyear)
        {//双倍余额递减法
            depamount = BigDecimalUtils.formatBigDecimalToDouble(yeardep / 12);
            depreat = BigDecimalUtils.formatBigDecimalToDouble(2 / naturyears / 12 * 100);
        }
        else
        {//平均年限法
            depamount = BigDecimalUtils.formatBigDecimalToDouble((yearvalue - salvage) / servicemonth);
            depreat = BigDecimalUtils.formatBigDecimalToDouble((yearvalue - salvage) / servicemonth / value);
            dbflag = 1;
        }
        int depmonth = servicemonth - 1;
        int depflag = 0;
        if (netvalue - salvage <= depamount || depmonth == 0)
        {
            depamount = BigDecimalUtils.formatBigDecimalToDouble(netvalue - salvage);
            depreat = BigDecimalUtils.formatBigDecimalToDouble((netvalue - salvage) / (value - salvage) * 100);
            depmonth = 0;
            depflag = 1;
        }
        double depvalue = BigDecimalUtils.formatBigDecimalToDouble(netvalue - depamount);
        double depaccount = BigDecimalUtils.formatBigDecimalToDouble(accudep + depamount);

        JSONObject resMap = new JSONObject();
        resMap.put(DEPAMOUNT, depamount);
        resMap.put(DEPREAT, depreat);
        resMap.put(DEPVALUE, depvalue);
        resMap.put(DEPACCOUNT, depaccount);
        resMap.put(DEPMONTH, depmonth);
        resMap.put(DEPFLAG, depflag);
        resMap.put("dbflag", dbflag);
        return resMap;
    }

    /**
     * 每月折旧 年数总和法
     * @param value 原值
     * @param netvalue 净值
     * @param salvage 残值
     * @param accudep 折旧累计
     * @param naturemonth 预计使用月数
     * @param servicemonth 剩余使用月数
     * @return EntityMap {depamount:月折旧额，depreat:月折旧率, depvalue:净值, depaccount:折旧累计,
     *          depmonth:剩余月数, depflag:愈龄标志（1-愈龄、0-未愈龄）}
     */
    public static JSONObject dep_sy(double value, double netvalue, double salvage,
                                   double accudep, int naturemonth, int servicemonth)
    {
        //预计使用年数
        double naturyears = Math.ceil(naturemonth / 12);
        //预计年数总和
        double sumyears = (naturyears + 1) * naturyears / 2;
        //剩余年数
        double serviceyears = Math.ceil(servicemonth / 12);

        double depreat = BigDecimalUtils.formatBigDecimalToDouble(serviceyears / sumyears / 12 * 100);
        double depamount = BigDecimalUtils.formatBigDecimalToDouble((value - salvage) * (serviceyears / sumyears / 12));
        int depmonth = servicemonth - 1;
        int depflag = 0;
        if (netvalue - salvage <= depamount || depmonth == 0)
        {
            depamount = BigDecimalUtils.formatBigDecimalToDouble(netvalue - salvage);
            depreat = BigDecimalUtils.formatBigDecimalToDouble((netvalue - salvage) / (value - salvage) * 100);
            depmonth = 0;
            depflag = 1;
        }
        double depvalue = BigDecimalUtils.formatBigDecimalToDouble(netvalue - depamount);
        double depaccount = BigDecimalUtils.formatBigDecimalToDouble(accudep + depamount);

        JSONObject resMap = new JSONObject();
        resMap.put(DEPAMOUNT, depamount);
        resMap.put(DEPREAT, depreat);
        resMap.put(DEPVALUE, depvalue);
        resMap.put(DEPACCOUNT, depaccount);
        resMap.put(DEPMONTH, depmonth);
        resMap.put(DEPFLAG, depflag);
        return resMap;
    }

    /**
     * 每月折旧 工作量法
     * @param value 原值
     * @param netvalue 净值
     * @param salvage 残值
     * @param accudep 折旧累计
     * @param allworkloan 总工作量
     * @param workloan 本期工作量
     * @param servicemonth 剩余使用月数
     * @return EntityMap {depamount:月折旧额，depreat:月折旧率, depvalue:净值, depaccount:折旧累计,
     *          depmonth:剩余月数, depflag:愈龄标志（1-愈龄、0-未愈龄）}
     */
    public static JSONObject dep_wl(double value, double netvalue, double salvage, double accudep,
                                   double allworkloan, double workloan, int servicemonth )
    {
        double depreat = BigDecimalUtils.formatBigDecimalToDouble(workloan / allworkloan * 100);
        double depamount = BigDecimalUtils.formatBigDecimalToDouble((value - salvage) / allworkloan * workloan);
        int depmonth = servicemonth - 1;
        int depflag = 0;
        if (netvalue - salvage <= depamount || depmonth == 0)
        {
            depamount = BigDecimalUtils.formatBigDecimalToDouble(netvalue - salvage);
            depreat = BigDecimalUtils.formatBigDecimalToDouble((netvalue - salvage) / (value - salvage) * 100);
            depmonth = 0;
            depflag = 1;
        }
        double depvalue = BigDecimalUtils.formatBigDecimalToDouble(netvalue - depamount);
        double depaccount = BigDecimalUtils.formatBigDecimalToDouble(accudep + depamount);
        JSONObject resMap = new JSONObject();
        resMap.put(DEPAMOUNT, depamount);
        resMap.put(DEPREAT, depreat);
        resMap.put(DEPVALUE, depvalue);
        resMap.put(DEPACCOUNT, depaccount);
        resMap.put(DEPMONTH, depmonth);
        resMap.put(DEPFLAG, depflag);
        return resMap;
    }

    /**
     * 工作量法 补提折旧和折旧预测用
     * @param value 原值
     * @param netvalue 净值
     * @param salvage 残值
     * @param accudep 折旧累计
     * @param naturemonth 预计使用月数
     * @param servicemonth 剩余使用月数
     * @return EntityMap {depamount:月折旧额，depreat:月折旧率, depvalue:净值, depaccount:折旧累计,
     *          depmonth:剩余月数, depflag:愈龄标志（1-愈龄、0-未愈龄）}
     */
    public static JSONObject dep_wl2(double value, double netvalue, double salvage, double accudep,
                                    int naturemonth, int servicemonth )
    {
        double depreat = BigDecimalUtils.formatBigDecimalToDouble(1.00d / naturemonth * 100);
        double depamount = BigDecimalUtils.formatBigDecimalToDouble((value - salvage) / naturemonth);
        int depmonth = servicemonth - 1;
        int depflag = 0;
        if (netvalue - salvage <= depamount || depmonth == 0)
        {
            depamount = BigDecimalUtils.formatBigDecimalToDouble(netvalue - salvage);
            depreat = BigDecimalUtils.formatBigDecimalToDouble((netvalue - salvage) / (value - salvage) * 100);
            depmonth = 0;
            depflag = 1;
        }
        double depvalue = BigDecimalUtils.formatBigDecimalToDouble(netvalue - depamount);
        double depaccount = BigDecimalUtils.formatBigDecimalToDouble(accudep + depamount);
        JSONObject resMap = new JSONObject();
        resMap.put(DEPAMOUNT, depamount);
        resMap.put(DEPREAT, depreat);
        resMap.put(DEPVALUE, depvalue);
        resMap.put(DEPACCOUNT, depaccount);
        resMap.put(DEPMONTH, depmonth);
        resMap.put(DEPFLAG, depflag);
        return resMap;
    }
}
