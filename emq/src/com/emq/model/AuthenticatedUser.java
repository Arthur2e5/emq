package com.emq.model;

/**
 * ����֤�û�
 * @author guqiong
 * @created 2009-10-29
 */
public interface  AuthenticatedUser  {
	/**
	 * ��ȡ�û����е�Ȩ������
	 * @return 
	 */
	public Integer getPermissonMask();
	/**
	 * ��ȡ�û����ڵ���֯��������
	 * @return
	 */
	public String getOrgCode();
}
