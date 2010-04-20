package com.emq.model;

import com.emq.config.SystemConfig;
import com.emq.exception.GISException;
import com.mapinfo.util.DoublePoint;

/**
 * �Ŵ��ͼ����
 * 
 * @author guqiong
 * @created 2009-9-22
 */
public class GISMapZoomoutCommand extends AbstractGISMapControlCommand {
	/**
	 * ��ͼ����, ��Ļ����ϵ
	 */
	private DoublePoint zoomCenter;
	/**
	 * ���ż���
	 */
	private Double zoom;

	/**
	 * Ĭ�ϷŴ�����
	 * 
	 * @param map
	 */
	public GISMapZoomoutCommand(AbstractGISMap map) throws GISException {
		super(map);
		zoom = new Double(map.getZoom());
		zoom = zoom / SystemConfig.getInstance().getMapDefaultZoomRatio();
	}

	/**
	 * ָ�����ĵ�Ŵ�����
	 * 
	 * @param map
	 *            ��ͼ����
	 * @param x
	 *            ���ĵ�x����, ��Ļ����ϵ
	 * @param y
	 *            ���ĵ�y����, ��Ļ����ϵ
	 */
	public GISMapZoomoutCommand(AbstractGISMap map, Double x, Double y)
			throws GISException {
		super(map);
		zoom = new Double(map.getZoom());
		zoom = zoom / SystemConfig.getInstance().getMapDefaultZoomRatio();
		zoomCenter = new DoublePoint(x, y);
		
	}

	/**
	 * ѡ����ηŴ�����
	 * 
	 * @param map
	 *            map ��ͼ����
	 * @param left
	 *            �������Ͻ�x, ��Ļ����ϵ
	 * @param top
	 *            �������Ͻ�y, ��Ļ����ϵ
	 * @param right
	 *            �������½�x, ��Ļ����ϵ
	 * @param down
	 *            �������½�y, ��Ļ����ϵ
	 * @throws GISException
	 */
	public GISMapZoomoutCommand(AbstractGISMap map, Double left, Double top,
			Double right, Double down) throws GISException {
		super(map);
		zoom = new Double(map.getZoom());
		// TODO:�������ű���
		zoom = zoom / SystemConfig.getInstance().getMapDefaultZoomRatio();
		zoomCenter = new DoublePoint(right - left, down - top);
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
