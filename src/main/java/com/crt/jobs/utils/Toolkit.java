package com.crt.jobs.utils;

import java.math.BigDecimal;

public class Toolkit {

	public static boolean isEmpty(String s) {
		if (null != s && s.trim().length() > 0) {
			return false;
		}
		return true;
	}

	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 提供精确的减法运算。
	 * 
	 * @param v1
	 *            被减数
	 * @param v2
	 *            减数
	 * @return 两个参数的差
	 */

	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param v1
	 *            被乘数
	 * @param v2
	 *            乘数
	 * @return 两个参数的积
	 */

	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 
	 */
	public static boolean isIpV4Address(String value) {
		if (isEmpty(value)) {
			return false;
		}
		try {
			String[] parts = value.toString().split("[.]");
			if (parts.length != 4) {
				return false;
			}

			for (int i = 0; i < parts.length; i++) {
				int p = Integer.valueOf(parts[i]);
				if (p < 0 || p > 255) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// 默认除法运算精度
	private static final int DEF_DIV_SCALE = 2;

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static boolean toBoolean(Boolean b) {
		if (b == null) {
			return false;
		}
		return b.booleanValue();
	}

	public static boolean toBoolean(Boolean b, boolean defaultValue) {
		if (b == null) {
			return defaultValue;
		}
		return b.booleanValue();
	}

	public static Boolean toBoolean(String s) {
		if (s == null) {
			return null;
		}
		return toBoolean(s, false);
	}

	public static Boolean toBoolean(String s, boolean defaultValue) {
		if (s == null) {
			return defaultValue;
		}
		if ("true".equals(s)) {
			return true;
		}
		return false;
	}

}
