package com.emq.model;
 
/**
 * ͨ��R1��֤���û�
 * @author guqiong
 * @created 2009-10-29
 */
public class R1AuthenticatedUser implements AuthenticatedUser {
 
	private static final long serialVersionUID = 1L;

	/**
	 * �û���֯�������
	 */
	private String orgCode;	
	/**
	 * Ȩ������
	 */
	private Integer permissonMask;	
 
	
	public R1AuthenticatedUser(String orgCode, int permissonMask){
		this.orgCode = orgCode;
		this.permissonMask = permissonMask;
	}
	
	public R1AuthenticatedUser(){
		
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	public Integer getPermissonMask() {
		return permissonMask;
	}
	public void setPermissonMask(Integer permissonMask) {
		this.permissonMask = permissonMask;
	}

	 
}
