package com.emq.ui.dwr;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.emq.exception.GISException;
import com.emq.model.ConditionObject;

/**
 * ����ר����dwr��ѯ�ӿ�
 * 
 * @author guqiong
 * @created 2009-9-21
 * @history 2009-10-14 guqiong ���Ӱ뾶��Χ��ѯ���� 2009-10-16 guqiong ���ӷ���xml���ݵĲ�ѯ����
 *          <p>
 *          findXmlByCondition
 *          <p>
 *          findXmlByPoint
 *          <p>
 *          findXmlByRadius
 *          <p>
 *          findXmlByRect
 *          <p>
 *          findXmlByRegion ע�⣺dwr��֧�ַ������� 2009-10-20 guqiong add: findXmlById
 *          2009-10-20 guqiong ��ȡͨ�÷�����GISMapDataFinder
 */
public interface CigStoreFinder extends GISMapDataFinder {

	/**
	 * ��ָ�������ľ���ר������и���,�ı�Ự�еĵ�ͼ״̬
	 * 
	 * @param mapDefRefId
	 *            ��ͼ��������id
	 * @param co
	 *            ��ѯ��������
	 * @param session
	 *            �Ự
	 * @return null
	 * 
	 */
	public abstract void highlightByCondition(String mapDefRefId,
			ConditionObject co, HttpSession session) throws GISException;

	/**
	 * ��ȡָ�������ľ���ר������Ϣ�б�
	 * 
	 * @param mapDefRefId
	 *            ��ͼ��������id
	 * @param co
	 *            ��ѯ��������
	 * @param session
	 *            �Ự
	 * @return ����ר������Ϣ�б�ÿ�����۵���ϢΪһ��Map����Ÿ�ʽkey=�ֶ���������value=�ֶ�ֵ
	 * 
	 * 
	 */
	public abstract List<Map<String, String>> findByCondition(
			String mapDefRefId, ConditionObject co, HttpSession session)
			throws GISException;

	/**
	 * ��ȡָ�������ľ���ר������Ϣ�б�
	 * 
	 * @param mapDefRefId
	 *            ��ͼ��������id
	 * @param returnColumns
	 *            ��Ҫ���ص������У���ʽ���ö��ŷָ����硰column1,column2��, ��ʹ�õı���ʵ�ִ˽ӿڵ������
	 * @param co
	 *            ��ѯ��������
	 * @param session
	 *            �Ự
	 * @return ����ר������Ϣ�б�xml��ʽ, �硰<?xml version="1.0"
	 *         encoding="UTF-8"?><rows><row
	 *         id="a"><cell>AA</cell><cell>BB</cell>
	 *         <cell>CC</cell></row></rows>��
	 * 
	 * 
	 */
	public String findXmlByCondition(String mapDefRefId, String returnColumns,
			ConditionObject co, HttpSession session) throws GISException;

}