package com.plant.advice;

import java.lang.reflect.Method;
import org.aopalliance.intercept.*;
import com.plant.logger.Logger;


public class LogAroundAdvice implements MethodInterceptor {

  /**
   * ��־��¼��
   */
  protected static final Logger log = Logger.getLogger(LogAroundAdvice.class);

  /**
   * ������
   */
  public LogAroundAdvice() {
  }

  /**
   * invoke
   * @param methodInvocation MethodInvocation
   * @return Object
   * @throws Throwable
   */
  public Object invoke(MethodInvocation methodInvocation) throws Throwable {
    Method method = methodInvocation.getMethod();
    log.info("��ʼ���÷���:" + method.getName());
    Object obj = methodInvocation.proceed();
    log.info("���÷���:" + method.getName() + "������");
    return obj;
  }
}
