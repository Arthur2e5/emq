package com.emq.model;

import com.emq.exception.GISException;
import com.emq.logger.Logger;
import com.mapinfo.util.DoublePoint;

/**
 * ������ͼ���ĵ㶨λ������ͼָ���ĵ�����
 * 
 * @author guqiong
 * @created 2009-9-28
 */
public class GISMapCenterPreviewCommand extends AbstractGISMapControlCommand {
	/**
	 * ����ͼ����, ��������ϵ
	 */
	private DoublePoint newMainMapGISCenter;
	
	private static Logger log = Logger.getLogger(GISMapCenterPreviewCommand.class);

	public GISMapCenterPreviewCommand(AbstractGISMap map) {
		super(map);
	}

	/**
	 * @param map
	 * @param x
	 *            ��������ͼ��x���꣬��Ļ����ϵ
	 * @param y
	 *            ��������ͼ��y���꣬��Ļ����ϵ
	 */
	public GISMapCenterPreviewCommand(AbstractGISMap map, double x, double y)
			throws GISException {
		super(map);
		log.debug("����ͼ����㣺x=" + String.valueOf(x) + ", y=" + String.valueOf(y));
		newMainMapGISCenter = super.getGISMap().convert2PreviewMapPosition(
				new DoublePoint(x, y));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.icss.km.gis.model.AbstractGISMapControlCommand#doExecute()
	 */
	public void doExecute() throws GISException {
		super.getGISMap().centerTo((DoublePoint) newMainMapGISCenter.clone());
	}
}
