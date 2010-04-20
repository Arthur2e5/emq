package com.emq.model;

import com.emq.exception.GISException;

/**
 * ��ʼ����ͼ�����õ�ͼ��Ĭ�ϲ���
 * 
 * @author guqiong
 * @created 2009-9-21
 * @history 2009-10-15	guqiong  fix bug: KMGIS-20 GISMapInitCommand�����õ�ͼ״̬ 
 */
public class GISMapInitCommand extends AbstractGISMapControlCommand {

	public GISMapInitCommand(AbstractGISMap map) {
		super(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.icss.km.gis.model.AbstractGISMapControlCommand#doExecute()
	 */
	public void doExecute() throws GISException {
		this.getGISMap().loadMap();
		//���õ�ͼ״̬����ΪloadMap�л���
		this.getGISMap().reset();
	}
}
