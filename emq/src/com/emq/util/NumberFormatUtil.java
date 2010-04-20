package com.emq.util;

/**
 * ���ݸ�ʽ��
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: ������ʣ���������Ϣ�������޹�˾</p>
 *
 * @author chenzhg
 * @version 1.0
 */
import java.text.*;
import java.util.*;
import java.math.*;

import org.apache.log4j.*;

public class NumberFormatUtil {

  private static Logger log = Logger.getLogger(NumberFormatUtil.class);

  public NumberFormatUtil() {
  }

  /**
   * �����������ְ���������ʽ���
   * @param d double
   * @param pattern String
   *       #:��ʾ��������������֣�û����գ�������λ�����ڣ���λ����
   *          �򳬳�����
   *       0:��������������֣�û�в�0
   *          ����С�����м�������0���ͱ�����λ��С����
   *       ���磺  "###.00" -->��ʾ�������ֵ������λС����������λ��
   *                          ��0��������λ����������
   *              "###.0#" -->��ʾ�������ֵ���Ա���һλ����λС����
   *                               ������ʾΪ��һλС����һλ����λС��
   *                               �İ�ԭ����ʾ��������λ���������룻
   *              "###" --->��ʾΪ������С��������������
   *              ".###" -->12.234��ʾΪ.234
   *              "#,###.0#"  -->��ʾ����ÿ��3λ��һ��"��";
   * @param l Locale
   * @return String
   */
  public static String formatNumber(double d, String pattern, Locale l) {
    String s = "";
    try {
      DecimalFormat nf = (DecimalFormat) NumberFormat.getInstance(l);
      nf.applyPattern(pattern);
      s = nf.format(d);

    } catch (Exception e) {
      e.printStackTrace();
      log.debug(" formatNumber is error!");
    }
    return s;

  }

  /**
   * ��ȱʡ���������������ʽ
   * @param d double
   * @param pattern String
   * @return String
   */
  public static String formatNumber(double d, String pattern) {
    return formatNumber(d, pattern, Locale.getDefault());
  }

  /**
   * ��ʽ������
   * @param d double
   * @param pattern String
   *        "\u00A4#,###.00" :��ʾΪ ��1��234��234.10
   * @param l Locale
   * @return String
   */
  public static String formatCurrency(double d, String pattern, Locale l) {
    String s = "";
    try {
      DecimalFormat nf = (DecimalFormat) NumberFormat.getCurrencyInstance(l);
      nf.applyPattern(pattern);
      s = nf.format(d);

    } catch (Exception e) {
      e.printStackTrace();
      log.debug(" formatNumber is error!");
    }
    return s;

  }

  /**
   * ʹ��Ĭ�������ָ����ʽ��ʾ����
   * @param d double
   * @param pattern String
   * @return String
   */
  public static String formatCurrency(double d, String pattern) {
    return formatCurrency(d, pattern, Locale.getDefault());
  }

  /**
   * ʹ��Ĭ�Ϸ�ʽ��ʾ���ң�
   *     ����:��12,345.46  Ĭ�ϱ���2λС������������
   * @param d double
   * @return String
   */
  public static String formatCurrency(double d) {
    String s = "";
    try {
      DecimalFormat nf = (DecimalFormat) NumberFormat.getCurrencyInstance();
      s = nf.format(d);

    } catch (Exception e) {
      e.printStackTrace();
      log.debug(" formatNumber is error!");
    }
    return s;

  }

  /**
   * ��ָ�������ʽ���ٷ���
   * @param d double
   * @param pattern String "##,##.000%"-->��Ҫ���ǡ�%��
   * @param l Locale
   * @return String
   */
  public static String formatPercent(double d, String pattern, Locale l) {
    String s = "";
    try {
      DecimalFormat df = (DecimalFormat) NumberFormat.getPercentInstance(l);
      df.applyPattern(pattern);
      s = df.format(d);
    } catch (Exception e) {
      log.debug("formatPercent is error!");
      log.debug(e);
    }
    return s;
  }

  /**
   * ʹ��Ĭ�������ʽ���ٷ���
   * @param d double
   * @param pattern String
   * @return String
   */
  public static String formatPercent(double d, String pattern) {
    return formatPercent(d, pattern, Locale.getDefault());
  }

  /**
   * ��ʽ���ٷ���
   * @param d double
   * @return String
   */
  public static String formatPercent(double d) {
    String s = "";
    try {
      DecimalFormat df = (DecimalFormat) NumberFormat.getPercentInstance();
      s = df.format(d);
    } catch (Exception e) {
      log.debug("formatPercent is error!");
      log.debug(e);
    }
    return s;
  }

  /**
   * ������ֵĸ�ʽ,��:1,234,567.89
   * @param bd BigDecimal Ҫ��ʽ��������
   * @param format String ��ʽ "###,##0"
   * @return String
   */
  public static String numberFormat(BigDecimal bd, String format) {

    if (bd == null ||"0".equals(bd.toString())) {
      return "";
    }
    //System.out.println("format = " + format);
    if(format.indexOf(".")!=-1){
      int index = format.indexOf(".");
      String sign = format.substring(index-1,index);
      String prefix = format.substring(0,index-1);
      String suffix = format.substring(index+1,format.length());
      if(!sign.equals("0")){
        sign = "0";
        format = prefix + sign + "." + suffix;
      }
    }
    //System.out.println("format = " + format);


    DecimalFormat bf = new DecimalFormat(format);
    return bf.format(bd);
  }

  /**
   * ��ʽ��BigDecimal
   * @param bd BigDecimal
   * @param format String
   * @return BigDecimal
   */
  public static BigDecimal formatBigDecimal(BigDecimal bd, String format) {
    if (bd == null || "0".equals(bd.toString())) {
      return new BigDecimal(0.00);
    }

    String var = numberFormat(bd, format);
    return new BigDecimal(var);
  }


  public static void main(String[] args) {
    BigDecimal value = new BigDecimal("0.456");
    String s = numberFormat(value, "###.00");
    System.out.println(s);
  }

}
