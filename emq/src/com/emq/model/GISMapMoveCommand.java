package com.emq.model;

import com.emq.config.SystemConfig;
import com.emq.exception.GISException;
import com.mapinfo.util.DoublePoint;
import com.mapinfo.util.DoubleRect;

/**
 * ƽ�Ƶ�ͼ����
 * 
 * @author guqiong
 * @created 2009-9-22
 */
public class GISMapMoveCommand extends AbstractGISMapControlCommand {
	/**
	 *  ��ͼ����, ��Ļ����ϵ
	 */
	private DoublePoint newCenter;
	/**
	 * �ƶ�����-��
	 */
	public static final int DIRECTION_EAST = 1;
	/**
	 * �ƶ�����-����
	 */
	public static final int DIRECTION_SOUTH = 2;
	/**
	 * �ƶ�����-����
	 */
	public static final int DIRECTION_WEST = 3;
	/**
	 * �ƶ�����-��
	 */
	public static final int DIRECTION_NORTH = 4;

	/**
	 * ���춨���ƶ�����
	 * 
	 * @param map
	 *            ��ͼ����
	 * @param direction
	 *            ָ���ķ���
	 */
	public GISMapMoveCommand(AbstractGISMap map, int direction)
			throws GISException {
		super(map);
		DoublePoint center = super.getGISMap().getScreenCenter();
		DoubleRect deviceBounds = getGISMap().getDeviceBounds();
		// ����Ĭ�ϵ�x�����ƶ�����
		double moveXDistance = deviceBounds.width()
				* SystemConfig.getInstance().getMapDefaultMoveRatio();
		// ����Ĭ�ϵ�y�����ƶ�����
		double moveYDistance = deviceBounds.height()
				* SystemConfig.getInstance().getMapDefaultMoveRatio();

		switch (direction) {
		case DIRECTION_EAST:
			newCenter = new DoublePoint(center.x + moveXDistance, center.y);
			break;
		case DIRECTION_SOUTH:
			newCenter = new DoublePoint(center.x, center.y + moveYDistance);
			break;
		case DIRECTION_WEST:
			newCenter = new DoublePoint(center.x - moveXDistance, center.y);
			break;
		case DIRECTION_NORTH:
			newCenter = new DoublePoint(center.x, center.y - moveYDistance);
			break;

		}
	}

	/**
	 * �����϶�����
	 * 
	 * @param map
	 *            ��ͼ����
	 * @param startX
	 *            �ƶ����X����
	 * @param startX
	 *            �ƶ����Y����
	 * @param endX
	 *            �ƶ��յ�X����
	 * @param endY
	 *            �ƶ��յ�Y����
	 */
	public GISMapMoveCommand(AbstractGISMap map, double startX, double startY,
			double endX, double endY) throws GISException {
		super(map);
		DoublePoint center = super.getGISMap().getScreenCenter();
		// ����Ĭ�ϵ�x�����ƶ�����
		double moveXDistance = endX - startX;
		// ����Ĭ�ϵ�y�����ƶ�����
		double moveYDistance = endY - startY;
		newCenter = new DoublePoint(center.x - moveXDistance, center.y
				- moveYDistance);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.icss.km.gis.model.AbstractGISMapControlCommand#doExecute()
	 */
	public void doExecute() throws GISException {
		super.getGISMap().setScreenCenter(
				new DoublePoint(newCenter.x, newCenter.y));
	}

}
