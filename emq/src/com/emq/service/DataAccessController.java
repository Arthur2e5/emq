package com.emq.service;

import com.emq.exception.GISException;
import com.emq.model.AccessCtrolable;
import com.emq.model.AuthenticatedUser;

/**
 * ���ݷ��ʿ�����
 * @author guqiong
 * @created 2009-10-29
 */
public interface DataAccessController {
	
	/**
	 * Ȩ�����룬�ؼ��У�11111
	 */
	public static final int ORG_PERM_MASK_CITY = 0x1f;
	/**
	 * Ȩ�����룬���أ�1111
	 */
	public static final int ORG_PERM_MASK_COUNTY = 0xf;
	/**
	 * Ȩ�����룬��������0111
	 */
	public static final int ORG_PERM_MASK_REGION = 0x7;
	/**
	 * Ȩ�����룬�ֵ���0011
	 */
	public static final int ORG_PERM_MASK_STREET = 0x3;
	/**
	 * Ȩ�����룬�ص㣬0001
	 */
	public static final int ORG_PERM_MASK_PLACE = 0x1;

	/**
	 * �Ƿ��������Ŀ��
	 * 
	 * @param user
	 *            �û�
	 * @param targetObjectPermMask
	 *            Ŀ������Ȩ������
	 * @return true:����
	 */
	public boolean isAllowAccess(AuthenticatedUser user,
			int targetObjectPermMask);

	/**
	 * �����һ����Ȩ����
	 * @param permMask
	 * @return
	 */
	public int getHigherPermMask(int permMask);
	
	/**
	 * �����һ����Ȩ����
	 * @param permMask
	 * @return
	 */
	public int getLowerPermMask(int permMask);
	
	/**
	 * ��ȡ��͵�Ȩ����
	 * @return
	 */
	public int getLowestPermMask();
	/**
	 * �Ƿ��������Ŀ��
	 * 
	 * @param user
	 *            �û�
	 * @param targetObject
	 *            Ŀ�����
	 * @return
	 */
	public boolean isAllowAccess(AuthenticatedUser user,
			AccessCtrolable targetObject);
	
	/**
	 * ��ȡ��ǰ���û�
	 * @return
	 */
	public AuthenticatedUser getCurrentUser() throws GISException;
}
