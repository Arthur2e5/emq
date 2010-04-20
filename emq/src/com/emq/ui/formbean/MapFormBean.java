package com.emq.ui.formbean;

import java.util.List;

/**
 * ��װUI�Ե�ͼ��������������
 * 
 * @author guqiong
 * @created 2009-9-21
 * @history 2009-10-14 guqiong ���Ӱ뾶����
 * @history 2009-10-15 add toString method
 */
public class MapFormBean implements Cloneable {
	/**
	 * ��ͼ����id
	 */
	private String mapRefId;
	/**
	 * ��Ҫִ�еĶ���
	 */
	private String action;
	/**
	 * ��������
	 */
	private String zoomType;
	/**
	 * ����ʱ�����ĵ�x����,��Ļ����ϵ
	 */
	private String zoomX;
	/**
	 * ����ʱ�����ĵ�y����,��Ļ����ϵ
	 */
	private String zoomY;
	/**
	 * �������ŵ����Ͻ�x����,��Ļ����ϵ
	 */
	private String zoomoutLeft;
	/**
	 * �������ŵ����Ͻ�y����,��Ļ����ϵ
	 */
	private String zoomoutTop;
	/**
	 * �������ŵ����½�x����,��Ļ����ϵ
	 */
	private String zoomoutRight;
	/**
	 * �������ŵ����½�y����,��Ļ����ϵ
	 */
	private String zoomoutDown;
	/**
	 * ƽ������
	 */
	private String moveType;
	/**
	 * ƽ�Ƶ����x����,��Ļ����ϵ
	 */
	private String moveStartX;
	/**
	 * ƽ�Ƶ��յ�x����,��Ļ����ϵ
	 */
	private String moveEndX;
	/**
	 * ƽ�Ƶ����y����,��Ļ����ϵ
	 */
	private String moveStartY;
	/**
	 * ƽ�Ƶ��յ�y����,��Ļ����ϵ
	 */
	private String moveEndY;
	/**
	 * ѡ������
	 */
	private String selectType;
	/**
	 * ѡ����x����,��Ļ����ϵ
	 */
	private String selectX;
	/**
	 * ѡ����y����,��Ļ����ϵ
	 */
	private String selectY;
	/**
	 * ѡ��Բ�İ뾶,��Ļ����ϵ
	 */
	private String selectRadius;
	/**
	 * ѡ����ε����Ͻ�x����,��Ļ����ϵ
	 */
	private String selectLeft;
	/**
	 * ѡ����ε����Ͻ�y����,��Ļ����ϵ
	 */
	private String selectTop;
	/**
	 * ѡ����ε����½�x����,��Ļ����ϵ
	 */
	private String selectRight;
	/**
	 * ѡ����ε����½�y����,��Ļ����ϵ
	 */
	private String selectDown;
	/**
	 * ѡ�����εĶ������꼯��,��Ļ����ϵ
	 */
	private List<String[]> selectRegionPoints;
	/**
	 * ѡ�����εĶ�����
	 */
	private String selectRegionPCount;
	/**
	 * ѡ���ͼԪ����
	 */
	private String selectId;
	/**
	 * �����ͼid
	 */
	private String areaMapId;
	
	/**
	 * ��ͼʹ�û���id
	 */
	private String orgId;

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getAreaMapId() {
		return areaMapId;
	}

	public void setAreaMapId(String areaMapId) {
		this.areaMapId = areaMapId;
	}

	public String getMapRefId() {
		return mapRefId;
	}

	public void setMapRefId(String mapRefId) {
		this.mapRefId = mapRefId;
	}

	public String getSelectId() {
		return selectId;
	}

	public void setSelectId(String selectId) {
		this.selectId = selectId;
	}

	public String getAction() {
		return action;
	}

	public String getMoveType() {
		return moveType;
	}

	public void setMoveType(String moveType) {
		this.moveType = moveType;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getZoomType() {
		return zoomType;
	}

	public void setZoomType(String zoomType) {
		this.zoomType = zoomType;
	}

	public String getZoomX() {
		return zoomX;
	}

	public void setZoomX(String zoomX) {
		this.zoomX = zoomX;
	}

	public String getZoomY() {
		return zoomY;
	}

	public void setZoomY(String zoomY) {
		this.zoomY = zoomY;
	}

	public String getZoomoutLeft() {
		return zoomoutLeft;
	}

	public void setZoomoutLeft(String zoomoutLeft) {
		this.zoomoutLeft = zoomoutLeft;
	}

	public String getZoomoutTop() {
		return zoomoutTop;
	}

	public void setZoomoutTop(String zoomoutTop) {
		this.zoomoutTop = zoomoutTop;
	}

	public String getZoomoutRight() {
		return zoomoutRight;
	}

	public void setZoomoutRight(String zoomoutRight) {
		this.zoomoutRight = zoomoutRight;
	}

	public String getZoomoutDown() {
		return zoomoutDown;
	}

	public void setZoomoutDown(String zoomoutDown) {
		this.zoomoutDown = zoomoutDown;
	}

	public String getMoveStartX() {
		return moveStartX;
	}

	public void setMoveStartX(String moveStartX) {
		this.moveStartX = moveStartX;
	}

	public String getMoveEndX() {
		return moveEndX;
	}

	public void setMoveEndX(String moveEndX) {
		this.moveEndX = moveEndX;
	}

	public String getMoveStartY() {
		return moveStartY;
	}

	public void setMoveStartY(String moveStartY) {
		this.moveStartY = moveStartY;
	}

	public String getMoveEndY() {
		return moveEndY;
	}

	public void setMoveEndY(String moveEndY) {
		this.moveEndY = moveEndY;
	}

	public String getSelectType() {
		return selectType;
	}

	public void setSelectType(String selectType) {
		this.selectType = selectType;
	}

	public String getSelectX() {
		return selectX;
	}

	public void setSelectX(String selectX) {
		this.selectX = selectX;
	}

	public String getSelectY() {
		return selectY;
	}

	public void setSelectY(String selectY) {
		this.selectY = selectY;
	}
	
	public String getSelectRadius() {
		return selectRadius;
	}

	public void setSelectRadius(String selectRadius) {
		this.selectRadius = selectRadius;
	}

	public String getSelectLeft() {
		return selectLeft;
	}

	public void setSelectLeft(String selectLeft) {
		this.selectLeft = selectLeft;
	}

	public String getSelectTop() {
		return selectTop;
	}

	public void setSelectTop(String selectTop) {
		this.selectTop = selectTop;
	}

	public String getSelectRight() {
		return selectRight;
	}

	public void setSelectRight(String selectRight) {
		this.selectRight = selectRight;
	}

	public String getSelectDown() {
		return selectDown;
	}

	public void setSelectDown(String selectDown) {
		this.selectDown = selectDown;
	}

	public List<String[]> getSelectRegionPoints() {
		return selectRegionPoints;
	}

	public void setSelectRegionPoints(List<String[]> selectRegionPoints) {
		this.selectRegionPoints = selectRegionPoints;
	}

	public String getSelectRegionPCount() {
		return selectRegionPCount;
	}

	public void setSelectRegionPCount(String selectRegionPCount) {
		this.selectRegionPCount = selectRegionPCount;
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	/**
	 * ������������Ƿ�Ϸ�
	 * 
	 * @return
	 */
	public boolean isValidate() {
		// TODO: ����������ԺϷ���
		return true;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("mapRefId: ");
		sb.append(mapRefId);
		sb.append("; ");
		sb.append("action: ");
		sb.append(action);
		sb.append("; ");
		sb.append("zoomType: ");
		sb.append(zoomType);
		sb.append("; ");
		sb.append("zoomX: ");
		sb.append(zoomX);
		sb.append("; ");
		sb.append("zoomY: ");
		sb.append(zoomY);
		sb.append("; ");
		sb.append("zoomoutLeft: ");
		sb.append(zoomoutLeft);
		sb.append("; ");
		sb.append("zoomoutTop: ");
		sb.append(zoomoutTop);
		sb.append("; ");
		sb.append("zoomoutRight: ");
		sb.append(zoomoutRight);
		sb.append("; ");
		sb.append("zoomoutDown: ");
		sb.append(zoomoutDown);
		sb.append("; ");
		sb.append("moveType: ");
		sb.append(moveType);
		sb.append("; ");
		sb.append("moveStartX: ");
		sb.append(moveStartX);
		sb.append("; ");
		sb.append("moveEndX: ");
		sb.append(moveEndX);
		sb.append("; ");
		sb.append("moveStartY: ");
		sb.append(moveStartY);
		sb.append("; ");
		sb.append("moveEndY: ");
		sb.append(moveEndY);
		sb.append("; ");
		sb.append("selectType: ");
		sb.append(selectType);
		sb.append("; ");
		sb.append("selectX: ");
		sb.append(selectX);
		sb.append("; ");
		sb.append("selectY: ");
		sb.append(selectY);
		sb.append("; ");
		sb.append("selectRadius: ");
		sb.append(selectRadius);
		sb.append("; ");
		sb.append("selectLeft: ");
		sb.append(selectLeft);
		sb.append("; ");
		sb.append("selectTop: ");
		sb.append(selectTop);
		sb.append("; ");
		sb.append("selectRight: ");
		sb.append(selectRight);
		sb.append("; ");
		sb.append("selectDown: ");
		sb.append(selectDown);
		sb.append("; ");
		sb.append("selectRegionPoints: ");
		sb.append(selectRegionPoints);
		sb.append("; ");
		sb.append("selectId: ");
		sb.append(selectId);
		sb.append("; ");
		sb.append("selectRegionPCount: ");
		sb.append(selectRegionPCount);
		sb.append("; ");
		return sb.toString();		
	}

}
