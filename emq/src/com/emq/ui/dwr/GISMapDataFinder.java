package com.emq.ui.dwr;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import com.emq.exception.GISException; 

/**
 * ͼ����Ϣ��dwr��ѯ�ӿ�
 * @author guqiong
 * @create 2009-10-20
 */
public interface GISMapDataFinder {

	/**
	 * ��ָ��������ͼԪ���и���,�ı�Ự�еĵ�ͼ״̬
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
			Object co, HttpSession session) throws GISException;

	/**
	 * ��ȡָ��������ͼԪ��Ϣ�б�
	 * 
	 * @param mapDefRefId
	 *            ��ͼ��������id
	 * @param co
	 *            ��ѯ��������
	 * @param session
	 *            �Ự
	 * @return ͼԪ��Ϣ�б�ÿ��ͼԪ��ϢΪһ��Map����Ÿ�ʽkey=�ֶ���������value=�ֶ�ֵ
	 * 
	 * 
	 */
	public abstract List<Map<String, String>> findByCondition(
			String mapDefRefId, Object co, HttpSession session)
			throws GISException;
	/**
	 * ��ȡָ���ص��ͼԪ��Ϣ�б�
	 * <P>
	 * ��ָ���ĵط���ͼԪʱ����������Ϣ�� ���򷵻ؿ��б�
	 * 
	 * @param mapDefRefId
	 *            ��ͼ��������id
	 * @param session
	 *            �Ự
	 * @param x
	 *            x����, ��Ļ����ϵ
	 * @param y
	 *            y����, ��Ļ����ϵ
	 * @return ͼԪ��Ϣ�б�ÿ��ͼԪ��ϢΪһ��Map����Ÿ�ʽkey=�ֶ���������value=�ֶ�ֵ
	 */
	public abstract List<Map<String, String>> findByPoint(String mapDefRefId,
			String x, String y, HttpSession session) throws GISException;

	/**
	 * ��ȡָ�����ĵ�뾶��Χ�ڵ�ͼԪ��Ϣ�б�
	 * <P>
	 * ��ָ���ĵط���ͼԪʱ����������Ϣ�� ���򷵻ؿ��б�
	 * 
	 * @param mapDefRefId
	 *            ��ͼ��������id
	 * @param session
	 *            �Ự
	 * @param x
	 *            x����, ��Ļ����ϵ
	 * @param y
	 *            y����, ��Ļ����ϵ
	 * @param y
	 *            �뾶, ��Ļ����ϵ
	 * @return ͼԪ��Ϣ�б�ÿ��ͼԪ��ϢΪһ��Map����Ÿ�ʽkey=�ֶ���������value=�ֶ�ֵ
	 */
	public abstract List<Map<String, String>> findByRadius(String mapDefRefId,
			String x, String y, String radius, HttpSession session)
			throws GISException;

	/**
	 * ��ȡ���η�Χ�ڵ�ͼԪ��Ϣ�б�
	 * <P>
	 * ��ָ���ĵط���ͼԪʱ����������Ϣ�� ���򷵻ؿ��б�
	 * 
	 * @param mapDefRefId
	 *            ��ͼ��������id
	 * @param x0
	 *            �������Ͻ�x����, ��Ļ����ϵ
	 * @param y0
	 *            �������Ͻ�y����, ��Ļ����ϵ
	 * @param x1
	 *            �������½�x����, ��Ļ����ϵ
	 * @param y1
	 *            �������½�y����, ��Ļ����ϵ
	 * @param session
	 *            �Ự
	 * @return
	 * @throws GISException
	 */
	public abstract List<Map<String, String>> findByRect(String mapDefRefId,
			String x0, String y0, String x1, String y1, HttpSession session)
			throws GISException;

	/**
	 * ��ȡָ������������ڵ�ͼԪ��Ϣ�б�
	 * 
	 * @param mapDefRefId
	 *            ��ͼ��������id
	 * @param pcount
	 *            ����εĶ�����
	 * @param session
	 *            �Ự
	 * @param encodedPoints
	 *            ����ζ����ʽ������ʽ x0,y0;x1,y1;...xn,yn ÿ��xy���ö��ŷָ������xy���÷ֺŷָ�
	 * @return ͼԪ��Ϣ�б�ÿ��ͼԪ��ϢΪһ��Map����Ÿ�ʽkey=�ֶ���������value=�ֶ�ֵ
	 * 
	 */
	public abstract List<Map<String, String>> findByRegion(String mapDefRefId,
			String pcount, String encodedPoints, HttpSession session)
			throws GISException;

	/**
	 * ��ȡָ��������ͼԪ��Ϣ�б�
	 * 
	 * @param mapDefRefId
	 *            ��ͼ��������id
	 * @param returnColumns
	 *            ��Ҫ���ص������У���ʽ���ö��ŷָ����硰column1,column2��, ��ʹ�õı���ʵ�ִ˽ӿڵ������
	 * @param co
	 *            ��ѯ��������
	 * @param session
	 *            �Ự
	 * @return ͼԪ��Ϣ�б�xml��ʽ, �硰<?xml version="1.0"
	 *         encoding="UTF-8"?><rows><row
	 *         id="a"><cell>AA</cell><cell>BB</cell>
	 *         <cell>CC</cell></row></rows>��
	 * 
	 * 
	 */
	public abstract String findXmlByCondition(String mapDefRefId,
			String returnColumns, Object co, HttpSession session)
			throws GISException;

	/**
	 * ��ȡָ���ص��ͼԪ��Ϣ�б�
	 * <P>
	 * ��ָ���ĵط���ͼԪʱ����������Ϣ�� ���򷵻ؿ��б�
	 * 
	 * @param mapDefRefId
	 *            ��ͼ��������id
	 * @param returnColumns
	 *            ��Ҫ���ص������У���ʽ���ö��ŷָ����硰column1,column2��, ��ʹ�õı���ʵ�ִ˽ӿڵ������
	 * @param session
	 *            �Ự
	 * @param x
	 *            x����, ��Ļ����ϵ
	 * @param y
	 *            y����, ��Ļ����ϵ
	 * @return ͼԪ��Ϣ�б�xml��ʽ��xml��ʽ, �硰<?xml version="1.0"
	 *         encoding="UTF-8"?><rows><row
	 *         id="a"><cell>AA</cell><cell>BB</cell>
	 *         <cell>CC</cell></row></rows>��
	 */
	public abstract String findXmlByPoint(String mapDefRefId,
			String returnColumns, String x, String y, HttpSession session)
			throws GISException;

	/**
	 * ��ȡָ�����ĵ�뾶��Χ�ڵ�ͼԪ��Ϣ�б�
	 * <P>
	 * ��ָ���ĵط���ͼԪʱ����������Ϣ�� ���򷵻ؿ��б�
	 * 
	 * @param mapDefRefId
	 *            ��ͼ��������id
	 * @param returnColumns
	 *            ��Ҫ���ص������У���ʽ���ö��ŷָ����硰column1,column2��, ��ʹ�õı���ʵ�ִ˽ӿڵ������
	 * @param session
	 *            �Ự
	 * @param x
	 *            x����, ��Ļ����ϵ
	 * @param y
	 *            y����, ��Ļ����ϵ
	 * @param y
	 *            �뾶, ��Ļ����ϵ
	 * @return ͼԪ��Ϣ�б�xml��ʽ ��xml��ʽ, �硰<?xml version="1.0"
	 *         encoding="UTF-8"?><rows><row
	 *         id="a"><cell>AA</cell><cell>BB</cell>
	 *         <cell>CC</cell></row></rows>��
	 */
	public abstract String findXmlByRadius(String mapDefRefId,
			String returnColumns, String x, String y, String radius,
			HttpSession session) throws GISException;

	/**
	 * ��ȡ���η�Χ�ڵ�ͼԪ��Ϣ�б�
	 * <P>
	 * ��ָ���ĵط���ͼԪʱ����������Ϣ�� ���򷵻ؿ��б�
	 * 
	 * @param mapDefRefId
	 *            ��ͼ��������id
	 * @param returnColumns
	 *            ��Ҫ���ص������У���ʽ���ö��ŷָ����硰column1,column2��, ��ʹ�õı���ʵ�ִ˽ӿڵ������
	 * @param x0
	 *            �������Ͻ�x����, ��Ļ����ϵ
	 * @param y0
	 *            �������Ͻ�y����, ��Ļ����ϵ
	 * @param x1
	 *            �������½�x����, ��Ļ����ϵ
	 * @param y1
	 *            �������½�y����, ��Ļ����ϵ
	 * @param session
	 *            �Ự
	 * @return ͼԪ��Ϣ�б�xml��ʽ��xml��ʽ, �硰<?xml version="1.0"
	 *         encoding="UTF-8"?><rows><row
	 *         id="a"><cell>AA</cell><cell>BB</cell>
	 *         <cell>CC</cell></row></rows>��
	 * @throws GISException
	 */
	public abstract String findXmlByRect(String mapDefRefId,
			String returnColumns, String x0, String y0, String x1, String y1,
			HttpSession session) throws GISException;

	/**
	 * ��ȡָ������������ڵ�ͼԪ��Ϣ�б�
	 * 
	 * @param mapDefRefId
	 *            ��ͼ��������id
	 * @param returnColumns
	 *            ��Ҫ���ص������У���ʽ���ö��ŷָ����硰column1,column2��, ��ʹ�õı���ʵ�ִ˽ӿڵ������
	 * @param pcount
	 *            ����εĶ�����
	 * @param session
	 *            �Ự
	 * @param encodedPoints
	 *            ����ζ����ʽ������ʽ x0,y0;x1,y1;...xn,yn ÿ��xy���ö��ŷָ������xy���÷ֺŷָ�
	 * @return ͼԪ��Ϣ�б�xml��ʽ��xml��ʽ, �硰<?xml version="1.0"
	 *         encoding="UTF-8"?><rows><row
	 *         id="a"><cell>AA</cell><cell>BB</cell>
	 *         <cell>CC</cell></row></rows>��
	 * 
	 */
	public abstract String findXmlByRegion(String mapDefRefId,
			String returnColumns, String pcount, String encodedPoints,
			HttpSession session) throws GISException;

	/**
	 * ��ȡָ��id��ͼԪ��Ϣ
	 * 
	 * @param mapDefRefId
	 *            ��ͼ��������id
	 * @param returnColumns
	 *            ��Ҫ���ص������У���ʽ���ö��ŷָ����硰column1,column2��, ��ʹ�õı���ʵ�ִ˽ӿڵ������
	 * @param id
	 *            ����ֵ,��ֵ�����ɶ��ŷָ�
	 * @param session
	 *            �Ự
	 * @return ͼԪ��Ϣ�б�xml��ʽ��xml��ʽ, �硰<?xml version="1.0"
	 *         encoding="UTF-8"?><rows><row
	 *         id="a"><cell>AA</cell><cell>BB</cell>
	 *         <cell>CC</cell></row></rows>��
	 * @throws GISException
	 */
	public abstract String findXmlById(String mapDefRefId,
			String returnColumns, String id, HttpSession session)
			throws GISException;
}
