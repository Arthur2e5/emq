package com.emq.model;

import com.emq.config.SystemConfig;
import com.emq.exception.GISException;
import com.mapinfo.util.DoublePoint;

/**
 * ��С��ͼ����
 * 
 * @author guqiong
 * @created 2009-9-22
 */
public class GISMapZoominCommand extends AbstractGISMapControlCommand {
	/**
	 *  ��ͼ����, ��Ļ����ϵ
	 */
	private DoublePoint zoomCenter;
	/**
	 *  ���ż���
	 */
	private Double zoom;

	/**
	 * Ĭ����С����
	 * 
	 * @param map
	 *            ��ͼ����
	 */
	public GISMapZoominCommand(AbstractGISMap map) throws GISException {
		super(map);
		zoom = new Double(map.getZoom());
		zoom = zoom * SystemConfig.getInstance().getMapDefaultZoomRatio();
	}

	/**
	 * ָ�����ĵ���С����
	 * 
	 * @param map
	 *            ��ͼ����
	 * @param x
	 *            ���ĵ�x����, ��Ļ����ϵ
	 * @param y
	 *            ���ĵ�y����, ��Ļ����ϵ
	 */
	public GISMapZoominCommand(AbstractGISMap map, Double x, Double y)
			throws GISException {
		super(map);
		zoom = new Double(map.getZoom());
		zoom = zoom * SystemConfig.getInstance().getMapDefaultZoomRatio();
		zoomCenter = new DoublePoint(x, y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.icss.km.gis.model.AbstractGISMapControlCommand#doExecute()
	 */
	public void doExecute() throws GISException {
		super.getGISMap().setZoom(zoom.doubleValue());
		if (zoomCenter != null)
			super.getGISMap().setScreenCenter(
					new DoublePoint(zoomCenter.x, zoomCenter.y));
	}

}
