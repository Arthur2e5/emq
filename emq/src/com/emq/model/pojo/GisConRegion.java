package com.emq.model.pojo;

/**
 * ӳ���GisConRegion
 * @author guqiong
 * @created 2009-10-30
 */
public class GisConRegion {
	
	/**
	 * ר����������
	 */
	private String zmjgbm;
	/**
	 * ר����������
	 */
	private String zmjgmc;
	/**
	 * ���򼶱�
	 */
	private Integer qyjb;
	/**
	 * ��ͼ���ú�
	 */
	private String dtpzh;
	/**
	 * ��ͼ����
	 */
	private String dtmc;
	/**
	 * ��ͼ���ĵ�x����
	 */
	private Double x;
	/**
	 * ��ͼ���ĵ�y����
	 */
	private Double y;
	/**
	 * Ĭ�ϱ�־
	 */
	private Boolean mrbz;
	
	/**
	 * ��Ч��־
	 */
	private Boolean yxbz;
	
	public Integer getQyjb() {
		return qyjb;
	}
	public void setQyjb(Integer qyjb) {
		this.qyjb = qyjb;
	}
	
	public String getZmjgbm() {
		return zmjgbm;
	}
	public void setZmjgbm(String zmjgbm) {
		this.zmjgbm = zmjgbm;
	}
	public String getZmjgmc() {
		return zmjgmc;
	}
	public void setZmjgmc(String zmjgmc) {
		this.zmjgmc = zmjgmc;
	}
 
 
	public String getDtpzh() {
		return dtpzh;
	}
	public void setDtpzh(String dtpzh) {
		this.dtpzh = dtpzh;
	}
	public String getDtmc() {
		return dtmc;
	}
	public void setDtmc(String dtmc) {
		this.dtmc = dtmc;
	}
	public Double getX() {
		return x;
	}
	public void setX(Double x) {
		this.x = x;
	}
	public Double getY() {
		return y;
	}
	public void setY(Double y) {
		this.y = y;
	}
	 
	public Boolean getYxbz() {
		return yxbz;
	}
	public void setYxbz(Boolean yxbz) {
		this.yxbz = yxbz;
	}
	
	public Boolean getMrbz() {
		return mrbz;
	}
	public void setMrbz(Boolean mrbz) {
		this.mrbz = mrbz;
	}
	
 
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dtmc == null) ? 0 : dtmc.hashCode());
		result = prime * result + ((dtpzh == null) ? 0 : dtpzh.hashCode());
		result = prime * result + ((mrbz == null) ? 0 : mrbz.hashCode());
		result = prime * result + ((qyjb == null) ? 0 : qyjb.hashCode());
		result = prime * result + ((x == null) ? 0 : x.hashCode());
		result = prime * result + ((y == null) ? 0 : y.hashCode());
		result = prime * result + ((yxbz == null) ? 0 : yxbz.hashCode());
		result = prime * result + ((zmjgbm == null) ? 0 : zmjgbm.hashCode());
		result = prime * result + ((zmjgmc == null) ? 0 : zmjgmc.hashCode());
		return result;
	}
	 
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GisConRegion other = (GisConRegion) obj;
		if (dtmc == null) {
			if (other.dtmc != null)
				return false;
		} else if (!dtmc.equals(other.dtmc))
			return false;
		if (dtpzh == null) {
			if (other.dtpzh != null)
				return false;
		} else if (!dtpzh.equals(other.dtpzh))
			return false;
		if (mrbz == null) {
			if (other.mrbz != null)
				return false;
		} else if (!mrbz.equals(other.mrbz))
			return false;
		if (qyjb == null) {
			if (other.qyjb != null)
				return false;
		} else if (!qyjb.equals(other.qyjb))
			return false;
		if (x == null) {
			if (other.x != null)
				return false;
		} else if (!x.equals(other.x))
			return false;
		if (y == null) {
			if (other.y != null)
				return false;
		} else if (!y.equals(other.y))
			return false;
		if (yxbz == null) {
			if (other.yxbz != null)
				return false;
		} else if (!yxbz.equals(other.yxbz))
			return false;
		if (zmjgbm == null) {
			if (other.zmjgbm != null)
				return false;
		} else if (!zmjgbm.equals(other.zmjgbm))
			return false;
		if (zmjgmc == null) {
			if (other.zmjgmc != null)
				return false;
		} else if (!zmjgmc.equals(other.zmjgmc))
			return false;
		return true;
	}
	 
}
