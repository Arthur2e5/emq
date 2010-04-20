package com.emq.ui.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.emq.exception.GISException;
import com.emq.logger.Logger;
import com.emq.model.AbstractGISMap;
import com.emq.model.AbstractGISMapControlCommand;
import com.emq.model.GISMapCommandParser;
import com.emq.model.GISMapFactory;
import com.emq.model.GISMapInitCommand;
import com.emq.ui.filter.RequestMappingFilter;
import com.emq.ui.formbean.MapFormBean;

/**
 * ��ͼ����servlet
 * <p>
 * AbstractMapServlet���������������������GISMapCommandParser��������������ִ�е�ͼ���������Ը��µ�ͼ״̬��
 * <p>
 * AbstractMapServlet�����ͼƬ��ѡ�񽻸����࣬����������������ͼ��������ͼ��
 * 
 * @author guqiong
 * @created 2009-9-21
 */
public abstract class AbstractMapServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(AbstractMapServlet.class);

	/* url�������� */
	/**
	 * ��ͼ������������
	 */
	private static final String ACTION = "action";
	/**
	 * ��������
	 */
	private static final String ZOOM_TYPE = "zoomtype";
	/**
	 * ��x����
	 */
	private static final String POINT_X = "x";
	/**
	 * ��y����
	 */
	private static final String POINT_Y = "y";
	/**
	 * �������Ͻ�x����
	 */
	private static final String RECTANGLE_LEFT = "left";
	/**
	 * �������Ͻ�Y����
	 */
	private static final String RECTANGLE_TOP = "top";
	/**
	 * �������½�x����
	 */
	private static final String RECTANGLE_RIGHT = "right";
	/**
	 * �������½�y����
	 */
	private static final String RECTANGLE_DOWN = "down";
	/**
	 * ƽ�����x����
	 */
	private static final String START_X = "startx";
	/**
	 * ƽ�����y����
	 */
	private static final String START_Y = "starty";
	/**
	 * ƽ���յ�x����
	 */
	private static final String END_X = "endx";
	/**
	 * ƽ���յ�y����
	 */
	private static final String END_Y = "endy";
	/**
	 * ����ζ�����
	 */
	private static final String REGION_PCOUNT = "pcount";
	/**
	 * ����ζ����ַ���
	 */
	private static final String REGION_POINTS = "points";
	/**
	 * ѡ���ͼԪ����
	 */
	private static final String SELELCT_TYPE = "selecttype";
	/**
	 * ƽ������
	 */
	private static final String MOVE_TYPE = "movetype";
	/**
	 * ѡ�е�ͼԪ����
	 */
	private static final String ID = "id";	
	/**
	 * �뾶
	 */
	private static final String RADIUS = "radius";		
	/**
	 * �����ͼid
	 */
	private static final String AREA_MAP_ID = "areamapid";
	/**
	 * ��ͼʹ�õ�λid
	 */
	private static final String ORG_ID = "orgId";
	
	/**
	 * ��ͼ�����ڻỰ�еļ���ǰ׺�������ظ�
	 * <p>
	 * ������ȫ��Ϊ��ǰ׺+maprefid, ����KMGIS.MAP.CigStoreMap
	 */
	public static final String MAP_IN_SESSION_KEY_PREF = "KMGIS.MAP.";

	public AbstractMapServlet() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#destroy()
	 */
	public void destroy() {
		super.destroy();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ��ȡ��ͼ���Ʋ�������
		MapFormBean requestForm = getRequestForm(request);
		String mapRefId = requestForm.getMapRefId();
		String orgId = requestForm.getOrgId();
		try {
			AbstractGISMap cigStoreMap = null;
			// ִ�е�ͼ����,����Ự��ʱ��session��û�е�ͼ������,��ִ�г�ʼ������
			ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());

			GISMapFactory gisMapFactory = (GISMapFactory) ctx.getBean("gisMapFactory");			
			if (isMapInSession(request.getSession(), mapRefId)) {
				cigStoreMap = getMap(request.getSession(), mapRefId, gisMapFactory);
				AbstractGISMapControlCommand command = GISMapCommandParser
						.parse(cigStoreMap, requestForm);
				log.debug(request.getRequestURL()+ "?"  + request.getQueryString());
				log.debug(requestForm.getMapRefId());
				log.debug("ִ������: ");
				log.debug(command.getClass().getName());
				log.debug("���������");
				log.debug("requestForm��");
				log.debug(requestForm.toString());
				cigStoreMap = command.execute();
			} else {
				cigStoreMap = getMap(request.getSession(), mapRefId, orgId, gisMapFactory);
				AbstractGISMapControlCommand command = new GISMapInitCommand(
						cigStoreMap);
				command.execute();
			}
			// �����ͼ
			render(cigStoreMap, request, response);
		} catch (GISException e) {
			String errMsg = "ִ�е�ͼ����ʧ��: action=";
			errMsg += requestForm.getAction() == null ? " null " : requestForm
					.getAction();
			log.error(errMsg);
			// TODO:��ת��������Ϣҳ��
			throw new ServletException(e);
		}
	}

	/**
	 * ��Ⱦ��ͼ�����
	 * 
	 * @param map
	 *            ��ͼ����
	 * @param request
	 * @param response
	 * @throws GISException
	 */
	protected abstract void render(AbstractGISMap map,
			HttpServletRequest request, HttpServletResponse response)
			throws GISException;

	/**
	 * ���������ֵ����MapFormBean����
	 * 
	 * @param request
	 * @return
	 */
	protected MapFormBean getRequestForm(HttpServletRequest request) {
		MapFormBean bean = new MapFormBean();
		bean.setMapRefId((String) request
				.getAttribute(RequestMappingFilter.MAP_REF_ID_KEY));
		bean.setAction(request.getParameter(ACTION));
		bean.setZoomType(request.getParameter(ZOOM_TYPE));
		bean.setZoomX(request.getParameter(POINT_X));
		bean.setZoomY(request.getParameter(POINT_Y));
		bean.setZoomoutLeft(request.getParameter(RECTANGLE_LEFT));
		bean.setZoomoutRight(request.getParameter(RECTANGLE_RIGHT));
		bean.setZoomoutTop(request.getParameter(RECTANGLE_TOP));
		bean.setZoomoutDown(request.getParameter(RECTANGLE_DOWN));
		bean.setMoveType(request.getParameter(MOVE_TYPE));
		bean.setMoveStartX(request.getParameter(START_X));
		bean.setMoveStartY(request.getParameter(START_Y));
		bean.setMoveEndX(request.getParameter(END_X));
		bean.setMoveEndY(request.getParameter(END_Y));
		bean.setSelectType(request.getParameter(SELELCT_TYPE));
		bean.setSelectLeft(request.getParameter(RECTANGLE_LEFT));
		bean.setSelectRight(request.getParameter(RECTANGLE_RIGHT));
		bean.setSelectTop(request.getParameter(RECTANGLE_TOP));
		bean.setSelectDown(request.getParameter(RECTANGLE_DOWN));
		bean.setSelectX(request.getParameter(POINT_X));
		bean.setSelectY(request.getParameter(POINT_Y));
		bean.setSelectId(request.getParameter(ID));
		bean.setSelectRadius(request.getParameter(RADIUS));
		bean.setAreaMapId(request.getParameter(AREA_MAP_ID));
		bean.setOrgId(request.getParameter(ORG_ID));
		// ���ݶ���ζ�������ȡ�����������
		String sPcount = request.getParameter(REGION_PCOUNT);
		String points = request.getParameter(REGION_POINTS);
		List<String[]> selectRegionPoints = new ArrayList<String[]>();
		if (sPcount != null) {
			Integer pcount = new Integer(sPcount);
			bean.setSelectRegionPCount(sPcount);
			String[] regionPoints = points.split(";");
			if (pcount.intValue() != regionPoints.length)
				log.error("����ζ�������ʵ�ʲ�һ��");
			for (int i = 0; i < regionPoints.length; i++) {
				String[] point = regionPoints[i].split(",");
				selectRegionPoints.add(point);
			}
			bean.setSelectRegionPoints(selectRegionPoints);
		}
		return bean;
	}

	/**
	 * ��ȡsession�еĵ�ͼ����,���session��û�о��½�һ��
	 * 
	 * @param session
	 *            HttpSession
	 * @return AbstractGISMap
	 */
	private synchronized AbstractGISMap getMap(HttpSession session,
			String mapRefId, GISMapFactory gisMapFactory) throws GISException {
		return this.getMap(session, mapRefId, null, gisMapFactory);
	}
	
	/**
	 * ��ȡsession�еĵ�ͼ����,���session��û�о��½�һ��
	 * 
	 * @param session
	 *            HttpSession
	 * @param mapRefId
	 * 			��ͼ����id
	 * @param orgId
	 * 			��ͼʹ�õ���֯����id,Ϊ�ձ�ʾ�������ļ���ȡ
	 * @return AbstractGISMap
	 */
	private synchronized AbstractGISMap getMap(HttpSession session,
			String mapRefId, String orgId, GISMapFactory gisMapFactory) throws GISException {
		String key = MAP_IN_SESSION_KEY_PREF + mapRefId;
		AbstractGISMap map = (AbstractGISMap) session.getAttribute(key);
		if (map == null) {
			if(orgId == null || "".equals(orgId))
				map = gisMapFactory.create(mapRefId);
			else
				map = gisMapFactory.create(mapRefId, orgId);
			session.setAttribute(key, map);
		}
		return map;
	}

	/**
	 * ��ͼ�����Ƿ��ڻỰ��
	 * 
	 * @param session
	 *            HttpSession
	 * @return
	 */
	private synchronized boolean isMapInSession(HttpSession session,
			String mapRefId) {
		String key = MAP_IN_SESSION_KEY_PREF + mapRefId;
		return session.getAttribute(key) == null ? false : true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#init()
	 */
	public void init() throws ServletException {

	}

	/**
	 * ��ȡ���ͼƬ���
	 * 
	 * @param request
	 * @return
	 */
	protected Integer getOutImageWidth(HttpServletRequest request) {
		String imageWidth = request.getParameter("imagewidth");
		if (imageWidth == null || "".equals(imageWidth)) {
			log.error("����û��ָ��ͼƬ�Ŀ�ȣ�");
			log.error(request.getRequestURL());
			return null;
		} else
			return new Integer(imageWidth);
	}

	/**
	 * ��ȡ���ͼƬ�߶�
	 * 
	 * @param request
	 * @return
	 */
	protected Integer getOutImageHeight(HttpServletRequest request) {
		String imageHeight = request.getParameter("imageheight");
		if (imageHeight == null || "".equals(imageHeight)) {
			log.error("����û��ָ��ͼƬ�ĸ߶ȣ�");
			log.error(request.getRequestURL());
			return null;
		} else
			return new Integer(imageHeight);
	}

}
