package com.geeke.cardasset.supplementreduce.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * BigDecimal工具类
 * @author yutao.jin
 * @since 2014-2-17
 */
public class BigDecimalUtils {
	
	/**
	 * NumberFormat 格式化默认式样
	 */
	private static final NumberFormat DEFAULT_NUMBERFORMAT = new DecimalFormat("#0.00");
	
	/**
	 * BigDecimal 数值取整方案
	 */
	private static final Integer BIGDECIMAL_TYPE = BigDecimal.ROUND_HALF_UP;
	
	/**
	 * 保留位数
	 */
	private static final Integer KEEP_NUMBER = 2;
	
	/**
	 * 默认返回值
	 */
	private static final Double RETURN_NUMBER = 0.00d;
	

	private BigDecimalUtils(){
		
	}
	
	/**
	 * BigDecimal 初始化BigDecimal 内部调用
	 * @param value
	 * @return
	 */
	private static BigDecimal getBigDecimalInstance(String value){
		return new BigDecimal(value);
	}
	
	/**
	 * BigDecimal格式化，返回值为向上取整的2位小数的double值,若传入的数字为正数,返回值为xx.0格式 若想要x.00的格式 请调用formatBigDecimalToString方法
	 * @eg <code>
	 * 				BigDecimal b = new BigDecimal(2.321);<br>
	 * 				double d = BigDecimalUtils.formatBigDecimalToDouble(b);<br>
	 * 				return d;
	 * 	   </code><br>
	 *     d 返回2.33
	 * @author yutao.jin
	 * @since 2014-2-17
	 * @param bigDecimal
	 * @return double
	 */
	public static double formatBigDecimalToDouble(BigDecimal bigDecimal){
		if(null==bigDecimal)return RETURN_NUMBER;
		return bigDecimal.setScale(KEEP_NUMBER, BIGDECIMAL_TYPE).doubleValue();
	}
	
	/**
	 * BigDecimal格式化，返回值为向上取整的2位小数的double值,返回值为xx.0格式 若想要x.00的格式 请调用formatBigDecimalToString方法
	 * @eg <code>
	 * 				Double b = new Double(2.321);<br>
	 * 				double d = BigDecimalUtils.formatBigDecimalToDouble(b);<br>
	 * 				return d;
	 * 	   </code><br>
	 *     d 返回2.33
	 * @author yutao.jin
	 * @since 2014-2-17
	 * @return double
	 */
	public static double formatBigDecimalToDouble(Double num){
		if(num == null) return RETURN_NUMBER;
		return formatBigDecimalToDouble(getBigDecimalInstance(num.toString()));
	}
	
	/**
	 * BigDecimal格式化，返回值为向上取整的2位小数的double值,返回值为xx.0格式 若想要x.00的格式 请调用formatBigDecimalToString方法
	 * @eg <code>
	 * 				try{ <br>
	 * 					String b = new String(2.321);<br>
	 * 					double d = BigDecimalUtils.formatBigDecimalToDouble(b);<br>
	 * 					return d;<br>
	 *              }catch(RuntimeException e){<br>
	 *                 //TODO 错误处理<br>
	 *              }<br>
	 * 	   </code><br>
	 *     d 返回2.33
	 * @author yutao.jin
	 * @since 2014-2-17
	 * @return double
	 */
	public static double formatBigDecimalToDouble(String num) throws RuntimeException{
		if(num == null || "".equals(num)) return RETURN_NUMBER;
		Pattern pattern = Pattern.compile("^(-|\\+)?[0-9]+(.[0-9]+)?"); //正则表达式 表示只能输入数字
		Matcher isNum = pattern.matcher(num);
		if(!isNum.matches()) throw new RuntimeException("请传入正确的数字");
		return formatBigDecimalToDouble(getBigDecimalInstance(num));
	}

	
	/**
	 * BigDecimal格式化，返回值为保留2位小数的字符串
	 * @author yutao.jin
	 * @since 2014-2-17
	 * @param bigDecimal
	 * @return
	 */
	public static String formatBigDecimalToString(BigDecimal bigDecimal){
		return DEFAULT_NUMBERFORMAT.format(formatBigDecimalToDouble(bigDecimal));
	}
	
	/**
	 * double格式化，返回值为保留2位小数的字符串
	 * @author yutao.jin
	 * @since 2014-2-17
	 * @return
	 */
	public static String formatBigDecimalToString(double value) {
		return DEFAULT_NUMBERFORMAT.format(formatBigDecimalToDouble(value));
	}
	
	/**
	 * String格式化，返回值为保留2位小数的字符串
	 * @eg <code>
	 * 				try{ <br>
	 * 					String b = new String(2.321);<br>
	 * 					String d = BigDecimalUtils.formatBigDecimalToString(b);<br>
	 * 					return d;<br>
	 *              }catch(RuntimeException e){<br>
	 *                 //TODO 错误处理<br>
	 *              }<br>
	 * 	   </code><br>
	 *     d 返回2.33
	 * @author yutao.jin
	 * @since 2014-2-17
	 * @return
	 */
	public static String formatBigDecimalToString(String value)  throws RuntimeException{
		return DEFAULT_NUMBERFORMAT.format(formatBigDecimalToDouble(value));
	}
	
	public static double sub(Number value1, Number value2) {  
        BigDecimal b1 = new BigDecimal(Double.toString(value1.doubleValue()));  
        BigDecimal b2 = new BigDecimal(Double.toString(value2.doubleValue()));  
        return b1.subtract(b2).doubleValue();
    }

	public static void main(String[] args) {
		String ss = "1.0141";
		System.out.print(formatBigDecimalToDouble(ss));
	}
}
