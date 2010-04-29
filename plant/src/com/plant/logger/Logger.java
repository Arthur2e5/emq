package com.plant.logger;

import com.icss.km.base.logger.LoggerAbstract;

public class Logger extends LoggerAbstract {

  /**
   * ������
   * @param name String
   */
  protected Logger(String name) {
    super(name);
  }

  /**
   * �����־��¼��
   * @param name String
   * @return Logger
   */
  public static Logger getLogger(String name) {
    return new Logger(name);
  }

  /**
   * �����־��¼��
   * @param clazz Class
   * @return Logger
   */
  public static Logger getLogger(Class clazz) {
    return getLogger(clazz.getName());
  }

  /**
   * ���Ǹ����debug����������־�������Ϊinfoʱ������¼��־debug��Ϣ
   * @param msg Object
   */
  public void debug(Object msg) {
    if (log.isDebugEnabled()) {
      super.debug(msg);
    }
  }

  public void debug(Object msg, Throwable throwable) {
    if (log.isDebugEnabled()) {
      super.debug(msg, throwable);
    }
  }
  
}
