package com.emq.dao;

import java.util.List;

import com.emq.model.pojo.GisConRegion;

/**
 * GisConRegion ��ͼ-ר��������ϵ�����ݷ��ʽӿ�
 * 
 * @author guqiong
 * 
 */
public interface GisConRegionDao {
	/**
	 * ��ȡ��ͼ-ר��������ϵ(Ĭ�ϵģ���Ч��)
	 * 
	 * @param zmjgbm
	 *            ר����������
	 * @param dqpzh
	 *            ��ͼ���ú�      
	 * @return
	 */
	public GisConRegion getUsedGisConRegion(String zmjgbm, String dqpzh);
	
	/**
	 * ��ȡ��ͼ-ר��������ϵ�б�(��Ч��)
	 * 
	 * @param zmjgbm
	 *            ר����������
	 * @param dqpzh
	 *            ��ͼ���ú�
	 * @return 
	 */
	public List<GisConRegion> getValidGisConRegions();
	
	/**
	 * ��ȡ��ͼ-ר��������ϵ�б�(Ĭ�ϵ�)
	 * 
	 * @param zmjgbm
	 *            ר����������
	 * @param dqpzh
	 *            ��ͼ���ú�
	 * @return 
	 */
	public List<GisConRegion> getUsedGisConRegions();

}
