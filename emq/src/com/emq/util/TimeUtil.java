package com.emq.util;

import java.text.*;
import java.util.*;
import com.emq.exception.UtilException;

public class TimeUtil {

  /**
   * ��Date��������ת����String����"����"��ʽ
   * java.sql.Date,java.sql.Timestamp������java.util.Date���͵�����
   * @param date Date
   * @param format String
   *               "2003-01-01"��ʽ
   *               "yyyy��M��d��"
   *               "yyyy-MM-dd HH:mm:ss"��ʽ
   * @return String
   */
  public static String dateToString(java.util.Date date, String format) {
    if (date == null || format == null) {
      return null;
    }
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    String str = sdf.format(date);
    return str;
  }

  /**
   * ��String��������ת����java.utl.Date����"2003-01-01"��ʽ
   * @param str String String��������
   * @param format String ʱ���ʽ
   * @return Date
   * @throws UtilException
   */
  public static java.util.Date stringToUtilDate(String str, String format) throws
          UtilException {
    if (str == null || format == null) {
      return null;
    }

    SimpleDateFormat sdf = new SimpleDateFormat(format);
    java.util.Date date = null;
    try {
      date = sdf.parse(str);
    } catch (ParseException pe) {
      throw new UtilException(pe.getMessage());
    }
    return date;
  }

  /**
   * ��String��������ת����java.sql.Date����"2003-01-01"��ʽ
   * @param str String String��������
   * @param format String ʱ���ʽ
   * @return Date
   * @throws UtilException
   */
  public static java.sql.Date stringToSqlDate(String str, String format) throws
          UtilException {
    if (str == null || format == null) {
      return null;
    }

    SimpleDateFormat sdf = new SimpleDateFormat(format);
    java.util.Date date = null;
    try {
      date = sdf.parse(str);
    } catch (ParseException pe) {
      throw new UtilException(pe.getMessage());
    }
    return new java.sql.Date(date.getTime());
  }

  /**
   * ��String��������ת����java.sql.Date����"2003-01-01"��ʽ
   * @param str String String��������
   * @param format String ʱ���ʽ
   * @return Timestamp
   * @throws UtilException
   */
  public static java.sql.Timestamp stringToTimestamp(String str, String format) throws
          UtilException {
    if (str == null || format == null) {
      return null;
    }

    SimpleDateFormat sdf = new SimpleDateFormat(format);
    java.util.Date date = null;
    try {
      date = sdf.parse(str);
    } catch (ParseException pe) {
      throw new UtilException(pe.getMessage());
    }
    return new java.sql.Timestamp(date.getTime());
  }

  /**
   * ��java.util.Date����ת����java.sql.Date����
   * @param date Date
   * @return ��ʽ�����java.sql.Date
   */
  public static java.sql.Date toSqlDate(Date date) {
    if (date == null) {
      return null;
    }
    return new java.sql.Date(date.getTime());
  }

  /**
   * ��ʱ���ַ���ת��Ϊʱ���ʽ string to string
   * @param str String String��������
   * @param oldformat String
   * @param newformat String
   * @return String
   * @throws UtilException
   */
  public static String toDateString(String str, String oldformat,
                                    String newformat) throws UtilException {
    return dateToString(stringToUtilDate(str, oldformat), newformat);
  }

  /**
   * ������ת��Ϊ����
   * @param calendar Calendar
   * @return Date
   */
  public static java.util.Date converToDate(java.util.Calendar calendar) {
    return calendar.getTime();
  }

  /**
   * ������ת��Ϊ����
   * @param date Date
   * @return Calendar
   */
  public static java.util.Calendar converToCalendar(java.util.Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return calendar;
  }

  /**
   * ��ô�ĳ�쿪ʼ�����˼��꼸�¼��ռ�ʱ���ּ���������Ƕ���
   * ���꼸�¼��ռ�ʱ���ּ������Ϊ����
   * @param date Date
   * @param year int
   * @param month int
   * @param day int
   * @param hour int
   * @param min int
   * @param sec int
   * @return Date
   */
  public static java.util.Date modifyDate(java.util.Date date, int year,
                                          int month, int day, int hour,
                                          int min, int sec) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(Calendar.YEAR, year);
    cal.add(Calendar.MONTH, month);
    cal.add(Calendar.DATE, day);
    cal.add(Calendar.HOUR, hour);
    cal.add(Calendar.MINUTE, min);
    cal.add(Calendar.SECOND, sec);

    return cal.getTime();

  }

  /**
   * ȡ�õ�ǰ����ʱ��
   * @param i int
   *        1:year
   *        2:month
   *        3:day
   * @return int
   */
  public static int getCurTime(int i) {
    if (i == 1) {
      return java.util.Calendar.getInstance().get(Calendar.YEAR);
    } else if (i == 2) {
      return java.util.Calendar.getInstance().get(Calendar.MONTH) + 1;
    } else if (i == 3) {
      return java.util.Calendar.getInstance().get(Calendar.DATE);
    }
    return 0;
  }

  /**
   * �ж�String���������Ƿ��ڵ�ǰʱ��֮��
   * @param time String String��������
   * @param format String ʱ���ʽ
   * @return boolean
   * @throws UtilException
   */
  public static boolean isAfterCurrentTime(String time, String format) throws
          UtilException {
    java.util.Date date = stringToUtilDate(time, format);
    if (new java.util.Date().after(date)) {
      return false;
    }
    return true;
  }

  /**
   * ��ȡĳ��ĳ�µ����������ȡ2006��2�·�����
   * @param yy String ��
   * @param mm String ��
   * @return int
   */
  public static int getMonthDays(String yy, String mm) {
    Calendar cal = Calendar.getInstance();
    int y = Integer.parseInt(yy);
    int m = Integer.parseInt(mm);
    cal.set(y, m - 1, 1);
    return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
  }

  /**
   * ��ȡ��ǰ�·�ǰ��interval����
   * @param year int ��
   * @param month int ��
   * @param interval int ���
   * @return List
   */
  public static List getTimeObjectList(int year, int month, int interval) {
    List timeObjectList = new ArrayList();
    if (interval > month) { // �·ݴ��ڼ��
      for (int i = 12 - (interval - month) + 1; i <= 12; i++) {
        TimeObject obj = new TimeObject();
        obj.setYear(String.valueOf(year - 1));
        obj.setMonth(String.valueOf(i));
        timeObjectList.add(obj);
      }

      for (int i = 1; i <= month; i++) {
        TimeObject obj = new TimeObject();
        obj.setYear(String.valueOf(year));
        obj.setMonth(String.valueOf(i));
        timeObjectList.add(obj);
      }
    } else { // ��������·�
      for (int i = (month - interval + 1); i <= month; i++) {
        TimeObject obj = new TimeObject();
        obj.setYear(String.valueOf(year));
        obj.setMonth(String.valueOf(i));
        timeObjectList.add(obj);
      }
    }
    return timeObjectList;
  }

  /**
   * �����·ݻ�ȡ����ֵ
   * @param month int
   * @return int
   * @throws UtilException
   */
  public static int getQuarter(int month) throws UtilException {
    if (month <= 0 && month > 12)
      throw new UtilException("�봫����ȷ���·�ֵ����ȷ���·�ֵΪ:1-12����ǰ�·ݲ���ֵΪ:" + month);
    if (month > 0 && month < 4)
      return 1;
    else if (month > 3 && month < 7)
      return 2;
    else if (month > 6 && month < 10)
      return 3;
    else
      return 4;
  }

  /**
   * ������ʱ���ʽ����2006
   * @param year String
   * @return String
   */
  public static String createYear(String year) {
    return createTime(year, "", "", 1);
  }

  /**
   * ��������ʱ���ʽ����:2006-12
   * @param year String
   * @param month String
   * @return String
   */
  public static String createYearMonth(String year, String month) {
    return createTime(year, month, "", 2);
  }

  /**
   * ����������ʱ���ʽ����:2006-06-01
   * @param year String
   * @param month String
   * @param day String
   * @return String
   */
  public static String createYearMonthDay(String year, String month, String day) {
    return createTime(year, month, day, 3);
  }

  /**
   * ���ݲ�������ʱ���ַ���
   * @param year String ��
   * @param month String ��
   * @param day String ��
   * @param type int 1:�� 2:���� 3:������
   * @return String
   */
  public static String createTime(String year, String month, String day,
                                  int type) {
    String time = "";
    if (month != null && !"".equals(month) && Integer.parseInt(month) < 10 &&
        !month.startsWith("0")) {
      month = "0" + month;
    }
    if (day != null && !"".equals(day) && Integer.parseInt(day) < 10 &&
        !day.startsWith("0"))
      day = "0" + day;
    switch (type) {
    case 1: { // ��
      time = year;
      break;
    }
    case 2: { // ��
      time = year + "-" + month;
      break;
    }
    case 3: { // ��
      time = year + "-" + month + "-" + day;
      break;
    }
    default: {
    }
    }
    return time;
  }

  /**
   * ��ȡ����ͼչ�ֱ���
   * @param timeType String ʱ�����ͣ�ö�٣�year:��;month:�£�
   * @param startY String ��ʼ��
   * @param startM String ��ʼ��
   * @param endY String ������
   * @param endM String ������
   * @return String
   */
  public static String createTimeTitle(String timeType, String startY, String startM,
                                String endY, String endM) {
    StringBuffer timeTitle = new StringBuffer();
    if (timeType.equals("year")) { // ��
      if (Integer.parseInt(startY) == Integer.parseInt(endY)) {
        timeTitle.append(startY).append("��");
      } else {
        timeTitle.append(startY).append("��");
        timeTitle.append("-");
        timeTitle.append(endY).append("��");
      }
      return timeTitle.toString();
    } else if (timeType.equals("month")) { // ��
      if (Integer.parseInt(startY) == Integer.parseInt(endY)) {
        timeTitle.append(startY).append("��");
        timeTitle.append(startM).append("��");
        timeTitle.append("-");
        timeTitle.append(endM).append("��");
      } else {
        timeTitle.append(startY).append("��");
        timeTitle.append(startM).append("��");
        timeTitle.append("-");
        timeTitle.append(endY).append("��");
        timeTitle.append(endM).append("��");
      }
      return timeTitle.toString();
    }
    return "";
  }

  public static void main(String[] args) {
    String time = "2005-12-30 00:00:00.00000";
    String format = "yyyy-MM-dd hh:mm:ss";
    System.out.println(TimeUtil.dateToString(new java.util.Date(), format));
  }
}
