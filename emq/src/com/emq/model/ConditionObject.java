package com.emq.model;

/**
 * ��ѯ��������
 * 
 * @author lyt
 * @created 2009-9-22 ע�⣺����Ϊ''����null����'all'��ʱ����Ϊ��������
 * @history 2009-10-21 guqiong add ���֤״̬ licenceStat
 */
public class ConditionObject implements Cloneable {

	/**
	 * ���֤���
	 */
	private String licenceNo;
	/**
	 * �̵�����
	 */
	private String storeName;
	/**
	 * ���˴���
	 */
	private String corporationName;
	/**
	 * �ֵ�����
	 */
	private String streetName;
	/**
	 * ��������
	 */
	private String areaName;
	/**
	 * ��Ӫҵ̬����
	 */
	private String fareTypeName;
	/**
	 * ��Ӫ��ַ
	 */
	private String fareAddress;

	/**
	 * ��·
	 */
	private String line;
	/**
	 * �ȼ�
	 */
	private String rank;
	/**
	 * ר���ȼ�
	 */
	private String specialRank;
	/**
	 * ��֤��ʼʱ��
	 */
	private String paperTime_start;
	/**
	 * ��֤����ʱ��
	 */
	private String paperTime_end;
	/**
	 * ���֤״̬ 1������ 5��ע�� 6��ͣҵ7��Ъҵ
	 */
	private String licenceState;

	/**
	 * Ʒ�Ʊ���
	 */
	private String brd;

	/**
	 * ������
	 */
	private String cig;
	
	/**
	 * �ع�˾����
	 */
	private String corpCode;
	
	/**
	 * ����ҵ̬
	 */
	private String sellType;
	
	/**
	 * �г�����
	 */
	private String markType;
	
	/**
	 * ��Ӫ��ģ
	 */
	private String fareSize;
	
	/**
	 * ע����ʼʱ��
	 */
	private String logoutTime_start;
	/**
	 * ע������ʱ��
	 */
	private String logoutTime_end;
	
	/**
	 * ����������
	 */
	private int noOrderMonth;
	
	/**
	 * ����
	 */
	private int pc;
	
	/**
	 * ��������:��У������(1),���ϴ�У������(2),���У������(3)
	 */
	private String dataType;

	public int getNoOrderMonth() {
		return noOrderMonth;
	}

	public void setNoOrderMonth(int noOrderMonth) {
		this.noOrderMonth = noOrderMonth;
	}

	public String getLicenceState() {
		return licenceState;
	}

	public void setLicenceState(String licenceState) {
		this.licenceState = licenceState;
	}

	public String getCorporationName() {
		return corporationName;
	}

	public void setCorporationName(String corporationName) {
		this.corporationName = corporationName;
	}

	public String getLicenceNo() {
		return licenceNo;
	}

	public void setLicenceNo(String licenceNo) {
		this.licenceNo = licenceNo;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getFareTypeName() {
		return fareTypeName;
	}

	public void setFareTypeName(String fareTypeName) {
		this.fareTypeName = fareTypeName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getFareAddress() {
		return fareAddress;
	}

	public void setFareAddress(String fareAddress) {
		this.fareAddress = fareAddress;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getPaperTime_end() {
		return paperTime_end;
	}

	public void setPaperTime_end(String paperTime_end) {
		this.paperTime_end = paperTime_end;
	}

	public String getPaperTime_start() {
		return paperTime_start;
	}

	public void setPaperTime_start(String paperTime_start) {
		this.paperTime_start = paperTime_start;
	}

	public String getBrd() {
		return brd;
	}

	public void setBrd(String brd) {
		this.brd = brd;
	}

	public String getCig() {
		return cig;
	}

	public void setCig(String cig) {
		this.cig = cig;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * �Ƿ��������
	 * 
	 * @param param
	 * @return
	 */
	public boolean mayBeUsed(String param) {
		return !(param == null || "".equals(param) || "all".equals(param));
	}

	public String getCorpCode() {
		return corpCode;
	}

	public void setCorpCode(String corpCode) {
		this.corpCode = corpCode;
	}

	public String getFareSize() {
		return fareSize;
	}

	public void setFareSize(String fareSize) {
		this.fareSize = fareSize;
	}

	public String getMarkType() {
		return markType;
	}

	public void setMarkType(String markType) {
		this.markType = markType;
	}

	public String getSellType() {
		return sellType;
	}

	public void setSellType(String sellType) {
		this.sellType = sellType;
	}

	public String getLogoutTime_end() {
		return logoutTime_end;
	}

	public void setLogoutTime_end(String logoutTime_end) {
		this.logoutTime_end = logoutTime_end;
	}

	public String getLogoutTime_start() {
		return logoutTime_start;
	}

	public void setLogoutTime_start(String logoutTime_start) {
		this.logoutTime_start = logoutTime_start;
	}

	public String getSpecialRank() {
		return specialRank;
	}

	public void setSpecialRank(String specialRank) {
		this.specialRank = specialRank;
	}

	public int getPc() {
		return pc;
	}

	public void setPc(int pc) {
		this.pc = pc;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

}
