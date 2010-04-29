package com.plant.util;

import java.math.BigDecimal;

/**
 * <p>Title: java.math.BigDecimal������</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: ������ʣ���������Ϣ�������޹�˾</p>
 * @author fengr
 * @version 1.0
 */
public class BigDecimalUtil {

  public BigDecimalUtil() {
  }

  /**
   * �ӷ�
   * @param first BigDecimal
   * @param last BigDecimal
   * @return BigDecimal
   */
  public static BigDecimal add(BigDecimal first, BigDecimal last) {
    return add(first, last, "##.00");
  }

  /**
   * ��ʽ���ӷ�
   * @param first BigDecimal
   * @param last BigDecimal
   * @param format String ##.00 ��λС�������㲹��
   * @return BigDecimal
   */
  public static BigDecimal add(BigDecimal first, BigDecimal last, String format) {
    BigDecimal result = new BigDecimal("0.00");
    if (first == null) {
      first = new BigDecimal("0.00");
    }
    if (last == null) {
      last = new BigDecimal("0.00");
    }
    result = first.add(last);
    result = new BigDecimal(NumberFormatUtil.numberFormat(result, format));
    return result;
  }


  /**
   * ����
   * @param first BigDecimal
   * @param last BigDecimal
   * @return BigDecimal
   */
  public static BigDecimal subtract(BigDecimal first, BigDecimal last) {
    return subtract(first, last, "##.00");
  }

  /**
   * ��ʽ������
   * @param first BigDecimal
   * @param last BigDecimal
   * @param format String ##.00 ��λС�������㲹��
   * @return BigDecimal
   */
  public static BigDecimal subtract(BigDecimal first, BigDecimal last,
                                    String format) {
    BigDecimal result = new BigDecimal("0.00");
    if (first == null) {
      first = new BigDecimal("0.00");
    }
    if (last == null) {
      last = new BigDecimal("0.00");
    }
    result = first.subtract(last);
    result = new BigDecimal(NumberFormatUtil.numberFormat(result, format));
    return result;
  }

  /**
   * �˷�
   * @param first BigDecimal
   * @param last BigDecimal
   * @return BigDecimal
   */
  public static BigDecimal multiply(BigDecimal first, BigDecimal last) {
    return multiply(first, last, "##.00");
  }

  /**
   * ��ʽ���˷�
   * @param first BigDecimal
   * @param last BigDecimal
   * @param format String ##.00 ��λС�������㲹��
   * @return BigDecimal
   */
  public static BigDecimal multiply(BigDecimal first, BigDecimal last,
                                    String format) {
    BigDecimal result = new BigDecimal("0.00");
    if (first == null) {
      first = new BigDecimal("0.00");
    }
    if (last == null) {
      last = new BigDecimal("0.00");
    }
    result = first.multiply(last);
    result = new BigDecimal(NumberFormatUtil.numberFormat(result, format));
    return result;
  }


  /**
   * ����
   * @param first BigDecimal
   * @param last BigDecimal
   * @param scale int
   * @param roundingMode int
   * @return BigDecimal
   */
  public static BigDecimal divide(BigDecimal first, BigDecimal last, int scale,
                                  int roundingMode) {
    BigDecimal result = new BigDecimal("0.00");
    if (first == null) {
      first = new BigDecimal("0.00");
    }
    if (last == null) {
      last = new BigDecimal("0.00");
    }

    if (first.floatValue() == 0 && last.floatValue() == 0) {
      result = new BigDecimal("0.00");
    }

    if (first.floatValue() != 0 && last.floatValue() == 0) {
      result = new BigDecimal("1.00");
    }

    if (last.floatValue() != 0) {
      result = first.divide(last, scale, roundingMode);
    }
    return result;
  }

  /**
   * ����ٷ��ʣ�����%��
   * @param dividend BigDecimal ������
   * @param divisor BigDecimal ����
   * @param scale int С����λ��
   * @param roundingMode int
   * @return BigDecimal
   */
  public static BigDecimal getPercent(BigDecimal dividend, BigDecimal divisor,
                                      int scale, int roundingMode) {
    BigDecimal result = new BigDecimal("0.00");
    if (divisor == null || divisor.doubleValue() == 0.00)
      return result;
    result = multiply(divide(dividend, divisor, scale + 2, roundingMode), new BigDecimal(100));
    return result;
  }

  /**
   * �ַ���У��
   * @param value BigDecimal
   * @return boolean
   */
  public static boolean isAvalid(BigDecimal value) {
    String t_value = String.valueOf(value);
    if (t_value == null || t_value.equals("") || t_value.equals("null")) {
      return false;
    } else {
      return true;
    }
  }

}
