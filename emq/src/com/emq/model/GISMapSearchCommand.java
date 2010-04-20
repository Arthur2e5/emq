package com.emq.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.emq.exception.ErrorMsgConstants;
import com.emq.exception.GISException;
import com.emq.logger.Logger;
import com.mapinfo.util.DoublePoint;
import com.mapinfo.util.DoubleRect;

/**
 * ��ͼ��ѯ����
 * 
 * @author guqiong
 * @created 2009-9-23
 * @history 2009-10-14 guqiong ���Ӱ뾶��Χ��ѯ֧��
 * 			2009-10-19 guqiong ����֧�ֶ�ֵ������ѯ
 * 
 */
public class GISMapSearchCommand extends AbstractGISMapControlCommand {

	Logger log = Logger.getLogger(GISMapSearchCommand.class);
	/**
	 * ��ѯ������, ��Ļ����ϵ
	 */
	private DoublePoint point;	
	/**
	 * �뾶����Ļ����ϵ
	 */
	private double radius;
	/**
	 * ��ѯ���η�Χ, ��Ļ����ϵ
	 */
	private DoubleRect rectangle;
	/**
	 * ��ѯ������
	 */
	private String[] pk;
	/**
	 * ��ѯ�Ķ���ζ���, ��Ļ����ϵ
	 */
	private List<DoublePoint> regionPoints;
	/**
	 * ��ѯ����
	 */
	private Integer searchType;
	/**
	 * ��ѯ����-���ѯ
	 */
	private static final int SEARCH_POINT = 1;
	/**
	 * ��ѯ����-���η�Χ��ѯ
	 */
	private static final int SEARCH_RECTANGLE = 2;
	/**
	 * ��ѯ����-����ͼԪ������ѯ
	 */
	private static final int SEARCH_BY_PK = 3;
	/**
	 * ��ѯ����-����η�Χ��ѯ
	 */
	private static final int SEARCH_REGION = 4;
	/**
	 * ��ѯ����-�뾶��Χ��ѯ
	 */
	private static final int SEARCH_CIRCLE = 5;

	/**
	 * ����������ѯ��
	 * @deprecated 2009-10-19
	 * �����GISMapSearchCommand(AbstractGISMap map, String[] pk) 
	 * @param map
	 * @param pk
	 */
	public GISMapSearchCommand(AbstractGISMap map, String pk) {
		this(map, new String[]{pk});
	}
	
	public GISMapSearchCommand(AbstractGISMap map, String[] pk) {
		super(map);
		this.pk = pk.clone();
		searchType = SEARCH_BY_PK;
	}
	
	private SearchableGISMap getMap(){
		return (SearchableGISMap) super.map;
	}

	/**
	 * ������ѯ����
	 * 
	 * @param map
	 *            ��ͼ����
	 * @param x
	 *            ѡ����x����, ��Ļ����ϵ
	 * @param y
	 *            ѡ����y����, ��Ļ����ϵ
	 */
	public GISMapSearchCommand(AbstractGISMap map, Double x, Double y) {
		super(map);
		point = new DoublePoint(x, y);
		searchType = SEARCH_POINT;
	}

	/**
	 * ����뾶��Χ��ѯ����
	 * 
	 * @param map
	 *            ��ͼ����
	 * @param radius
	 *            �뾶, ��Ļ����ϵ
	 * @param x
	 *            Բ�ĵ�x����, ��Ļ����ϵ
	 * @param y
	 *            Բ�ĵ�y����, ��Ļ����ϵ
	 */
	public GISMapSearchCommand(AbstractGISMap map, Double radius, Double x,
			Double y) {
		super(map);
		point = new DoublePoint(x, y);
		this.radius = radius;
		searchType = SEARCH_CIRCLE;
	}

	/**
	 * �������β�ѯ������
	 * 
	 * @param map
	 *            ��ͼ����
	 * @param points
	 *            ����εĶ�������, ��Ļ����ϵ
	 */
	public GISMapSearchCommand(AbstractGISMap map, List<Double[]> points) {
		super(map);
		regionPoints = new ArrayList<DoublePoint>();
		Iterator<Double[]> iter = points.iterator();
		while (iter.hasNext()) {
			Double[] p = iter.next();
			double x = p[0].doubleValue();
			double y = p[1].doubleValue();
			regionPoints.add(new DoublePoint(x, y));
		}
		this.searchType = SEARCH_REGION;
	}

	/**
	 * ������β�ѯ����
	 * 
	 * @param map
	 *            ��ͼ����
	 * @param x0
	 *            �������Ͻ�x����, ��Ļ����ϵ
	 * @param y0
	 *            �������Ͻ�y����, ��Ļ����ϵ
	 * @param x1
	 *            �������½�x����, ��Ļ����ϵ
	 * @param y1
	 *            �������½�y����, ��Ļ����ϵ
	 */
	public GISMapSearchCommand(AbstractGISMap map, Double x0, Double y0,
			Double x1, Double y1) {
		super(map);
		rectangle = new DoubleRect(x0, y0, x1, y1);
		searchType = SEARCH_RECTANGLE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.icss.km.gis.model.AbstractGISMapControlCommand#doExecute()
	 */
	public void doExecute() throws GISException {
		if(!(map instanceof SearchableGISMap))
			throw new GISException(ErrorMsgConstants.KMGIS_MAP_04);		
		try {
			switch (searchType.intValue()) {
			case SEARCH_POINT:
				getMap().searchAndHighlight((DoublePoint) point.clone());
				break;
			case SEARCH_RECTANGLE:
				getMap().searchAndHighlight((DoubleRect) rectangle.clone());
				break;
			case SEARCH_BY_PK:
				getMap().searchAndHighlight(pk);
				break;
			case SEARCH_REGION:
				getMap().searchAndHighlight(regionPoints);
				break;
			case SEARCH_CIRCLE:
				getMap().searchAndHighlight((DoublePoint) point.clone(), radius);
				break;
			// ��Ӧִ������
			default:
				log.error("��֧�ֵĲ�ѯ���ͣ�searchType=");
				log.error(searchType);
			}
		} catch (GISException e) {
			log.error(e);
			throw new GISException(e);
		}
	}

}
