package com.emq.model;

import java.util.ArrayList;
import java.util.List;
import com.emq.exception.GISException;
import com.emq.logger.Logger;
import com.emq.ui.formbean.MapFormBean;

/**
 * ��ͼ��������������ݲ����������������
 * <p>
 * 
 * @see com.icss.km.gis.ui.servlet.AbstractMapServlet
 * @see com.icss.km.gis.ui.formbean.MapFormBean
 * @author guqiong
 * @created 2009-9-21
 * @history 2009-10-14 guqiong ���Ӱ뾶��Χ��ѯ����֧�� 2009-10-15 guqiong
 *          ������commandType����������ʹ��GISMapRefreshCommand��ԭʹ��GISMapInitCommand
 *          ����COMMAND_INIT������ΪGISMapInitCommand
 * 
 */
public class GISMapCommandParser {

	private static Logger log = Logger.getLogger(GISMapCommandParser.class);
	/**
	 * ��ʼ������
	 */
	public static final String COMMAND_INIT = "init";
	/**
	 * ��������
	 */
	public static final String COMMAND_RESET = "reset";
	/**
	 * �Ŵ�����
	 */
	public static final String COMMAND_ZOOMOUT = "zoomout";
	/**
	 * ��С����
	 */
	public static final String COMMAND_ZOOMIN = "zoomin";
	/**
	 * ƽ������
	 */
	public static final String COMMAND_MOVE = "move";
	/**
	 * ѡ������
	 */
	public static final String COMMAND_SEARCH = "select";
	/**
	 * ����ͼ��λ����
	 */
	public static final String COMMAND_CENTER_PREVIEW = "centerto";
	/**
	 * �ص���ͼĬ�����ĵ�����
	 */
	public static final String COMMAND_RESET_CENTER = "defaultcenter";
	/**
	 * �л���ͼ����
	 */
	public static final String COMMAND_SWITCH_AREA = "switcharea";

	/**
	 * ���������������ȡָ�����͵�GISMapControlCommandʵ��
	 * 
	 * @param map
	 *            ������Ҫ�����ĵ�ͼ����
	 * @param bean
	 *            �����������
	 * @return GISMapControlCommand ��ͼ��������
	 */
	public static AbstractGISMapControlCommand parse(AbstractGISMap map,
			MapFormBean bean) throws GISException {
		String commandType = bean.getAction();
		if (!bean.isValidate()) {
			log.error("��ͼ ������������Ƿ�");
		}
		if (commandType == null || commandType.equals("")) {
			return new GISMapRefreshCommand(map);
		} else if (commandType.equals(COMMAND_INIT)) {
			return new GISMapInitCommand(map);
		} else if (commandType.equals(COMMAND_RESET)) {
			return new GISMapResetCommand(map);
		} else if (commandType.equals(COMMAND_ZOOMOUT)) {
			return createZoomoutCommand(map, bean);
		} else if (commandType.equals(COMMAND_ZOOMIN)) {
			return createZoominCommand(map, bean);
		} else if (commandType.equals(COMMAND_MOVE)) {
			return createMoveCommand(map, bean);
		} else if (commandType.equals(COMMAND_SEARCH)) {
			return createSearchCommand(map, bean);
		} else if (commandType.equals(COMMAND_CENTER_PREVIEW)) {
			double x = (new Double(bean.getSelectX())).doubleValue();
			double y = (new Double(bean.getSelectY())).doubleValue();
			return new GISMapCenterPreviewCommand(map, x, y);
		} else if (commandType.equals(COMMAND_RESET_CENTER)) {
			return new GISMapResetCenterCommand(map);
		} else if (commandType.equals(COMMAND_SWITCH_AREA)) {
			return new GISMapSwitchCommand(map, bean.getAreaMapId());
		} else {// TODO:������������
			log.error("��֧�ֵ���������:");
			log.error(commandType);
			return null;
		}
	}

	/**
	 * ʵ����ZoomoutGISMapCommand
	 * 
	 * @param map
	 *            ��ͼ����
	 * @param bean
	 *            �������
	 * @return ZoomoutGISMapCommand
	 */
	private static GISMapZoomoutCommand createZoomoutCommand(
			AbstractGISMap map, MapFormBean bean) throws GISException {
		GISMapZoomoutCommand command = null;
		if (bean.getZoomType() != null && !"".equals(bean.getZoomType())) {
			if (bean.getZoomType().equals("point")) { // ���ĵ�Ŵ�
				return new GISMapZoomoutCommand(map,
						new Double(bean.getZoomX()),
						new Double(bean.getZoomY()));
			} else if (bean.getZoomType().equals("rectangle")) {// ���ηŴ�
				Double left = new Double(bean.getZoomoutLeft());
				Double top = new Double(bean.getZoomoutTop());
				Double right = new Double(bean.getZoomoutRight());
				Double down = new Double(bean.getZoomoutDown());
				return new GISMapZoomoutCommand(map, left, top, right, down);
			} else {
				log.error("��֧�ֵķŴ�����:");
				log.error(bean.getZoomType());
				return null;
			}
		} else {
			// Ĭ�ϷŴ�
			command = new GISMapZoomoutCommand(map);
		}
		return command;
	}

	/**
	 * ʵ����GISMapZoominCommand
	 * 
	 * @param map
	 *            ��ͼ����
	 * @param bean
	 *            �������
	 * @return GISMapZoominCommand
	 */
	private static GISMapZoominCommand createZoominCommand(AbstractGISMap map,
			MapFormBean bean) throws GISException {
		GISMapZoominCommand command = null;
		if (bean.getZoomType() != null && !"".equals(bean.getZoomType())) {
			if (bean.getZoomType().equals("point")) { // ָ�����ĵ���С
				return new GISMapZoominCommand(map,
						new Double(bean.getZoomX()),
						new Double(bean.getZoomY()));
			} else {
				log.error("��֧�ֵ���С����:");
				log.error(bean.getZoomType());
				return null;
			}
		} else {
			// Ĭ����С
			command = new GISMapZoominCommand(map);
		}
		return command;
	}

	/**
	 * ʵ����GISMapMoveCommand
	 * 
	 * @param map
	 *            ��ͼ����
	 * @param bean
	 *            �������
	 * @return GISMapMoveCommand
	 */
	private static GISMapMoveCommand createMoveCommand(AbstractGISMap map,
			MapFormBean bean) throws GISException {
		GISMapMoveCommand command = null;
		if ("drag".equals(bean.getMoveType())) { // �϶���ͼ����
			Double startX = new Double(bean.getMoveStartX());
			Double startY = new Double(bean.getMoveStartY());
			Double endX = new Double(bean.getMoveEndX());
			Double endY = new Double(bean.getMoveEndY());
			return new GISMapMoveCommand(map, startX, startY, endX, endY);
		} else if ("left".equals(bean.getMoveType())) { // ��ƽ��
			command = new GISMapMoveCommand(map,
					GISMapMoveCommand.DIRECTION_WEST);
		} else if ("down".equals(bean.getMoveType())) { // ��ƽ��
			command = new GISMapMoveCommand(map,
					GISMapMoveCommand.DIRECTION_SOUTH);
		} else if ("right".equals(bean.getMoveType())) { // ��ƽ��
			command = new GISMapMoveCommand(map,
					GISMapMoveCommand.DIRECTION_EAST);
		} else if ("up".equals(bean.getMoveType())) {// ��ƽ��
			command = new GISMapMoveCommand(map,
					GISMapMoveCommand.DIRECTION_NORTH);
		} else {
			log.error("��֧�ֵ�ƽ������:");
			log.error(bean.getMoveType());
			return null;
		}
		return command;
	}

	/**
	 * ʵ����GISMapSearchCommand
	 * 
	 * @param map
	 *            ��ͼ����
	 * @param bean
	 *            �������
	 * @return GISMapSearchCommand
	 */
	private static GISMapSearchCommand createSearchCommand(AbstractGISMap map,
			MapFormBean bean) throws GISException {
		GISMapSearchCommand command = null;
		if (bean.getSelectType() != null && !"".equals(bean.getSelectType())) {
			if (bean.getSelectType().equals("point")) {// ���ѯ
				return new GISMapSearchCommand(map, new Double(bean
						.getSelectX()), new Double(bean.getSelectY()));
			} else if (bean.getSelectType().equals("rectangle")) {// ���β�ѯ
				Double left = new Double(bean.getSelectLeft());
				Double top = new Double(bean.getSelectTop());
				Double right = new Double(bean.getSelectRight());
				Double down = new Double(bean.getSelectDown());
				return new GISMapSearchCommand(map, left, top, right, down);
			} else if (bean.getSelectType().equals("pk")) {// ������ѯ
				return new GISMapSearchCommand(map, bean.getSelectId().split(
						","));
			} else if (bean.getSelectType().equals("region")) {// ����β�ѯ
				List<Double[]> points = new ArrayList<Double[]>();
				List<String[]> sPoints = bean.getSelectRegionPoints();
				for (int i = 0; i < sPoints.size(); i++) {
					String[] sPoint = sPoints.get(i);
					double x = (new Double(sPoint[0])).doubleValue();
					double y = (new Double(sPoint[1])).doubleValue();
					points.add(new Double[] { x, y });
				}
				return new GISMapSearchCommand(map, points);
			} else if (bean.getSelectType().equals("circle")) {// Բ��Χ��ѯ
				double x = new Double(bean.getSelectX()).doubleValue();
				double y = new Double(bean.getSelectY()).doubleValue();
				double radius = new Double(bean.getSelectRadius())
						.doubleValue();
				return new GISMapSearchCommand(map, radius, x, y);
			} else {
				log.error("��֧�ֵ�ѡ���ѯ����:");
				log.error(bean.getZoomType());
				return null;
			}
		}
		return command;
	}

}
