package com.emq.ui.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import com.emq.logger.Logger;

/**
 * ��request���������,������ת������ͼ�����servlet,��ˣ�RequestMappingFilterӦ��Ϊ���һ��Filter����web.xml�С�
 * <p>
 * ������ͼ����ĵ�һ���,�ж�������Ǵ�������ͼ��������ͼ������ȡ����ĵ�ͼ����id, ����ͼ����idת������Ӧ��servlet��
 * <p>
 * ��ʾ����ͼ��
 * <p>
 * http://������/Ӧ����/gis/main-map/��ͼ����id
 * <p>
 * ��ʾ���Ե�ͼ:
 * <p>
 * http://������/Ӧ����/gis/preview-map/��ͼ����id
 * <p>
 * ��ͼ����id��
 * <p>
 * �������ļ��ж��壬��src/gis-map.xml��
 * 
 * @author guqiong
 * @created 2009-9-27
 */
public class RequestMappingFilter implements Filter {
	private static Logger log = Logger.getLogger(RequestMappingFilter.class);
	/**
	 * ����ͼurlǰ׺
	 */
	private static final String MAIN_MAP_URL_PREF = "/gis/main-map";

	/**
	 * ���Ե�ͼurlǰ׺
	 */
	private static final String PREVIEW_MAP_URL_PREF = "/gis/preview-map";

	/**
	 * ����ͼservlet
	 */
	private static final String MAIN_MAP_SERVLET = "/servlet/MainMapServlet";

	/**
	 * ����ͼservlet
	 */
	private static final String PREVIEW_MAP_SERVLET = "/servlet/PreviewMapServlet";

	/**
	 * ��ͼ����id����request�е�key
	 */
	public static final String MAP_REF_ID_KEY = "KMGIS.MAP_REF_ID";

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		log.debug("RequestMappingFilter enter:");
		HttpServletRequest request = (HttpServletRequest) req;
		String servlet = request.getRequestURI();
		String MapRefID = null;
		log.debug("servlet:");
		log.debug(servlet);
		if (servlet != null && servlet.trim().length() > 1) {
			if (request.getContextPath() != null) {
				servlet = servlet.substring(request.getContextPath().length());
			}
			log.debug("servlet:");
			log.debug(servlet);
			// ���url��ַ�ǵ�������ͼ��ת��������ͼ�����servlet
			if (servlet.indexOf(MAIN_MAP_URL_PREF) != -1) {
				int i = servlet.indexOf(MAIN_MAP_URL_PREF);
				MapRefID = servlet
						.substring(i + MAIN_MAP_URL_PREF.length() + 1);
				String forward = MAIN_MAP_SERVLET;
				request.setAttribute(MAP_REF_ID_KEY, MapRefID);
				
				log.debug("forward:"); 
				log.debug(forward);
				log.debug("MapRefID:");
				log.debug(MapRefID);
				req.getRequestDispatcher(forward).forward(req, res);
				return;
			}
			// ���url��ַ�ǵ������Ե�ͼ��ת�������Ե�ͼ�����servlet
			if (servlet.indexOf(PREVIEW_MAP_URL_PREF) != -1) {
				int i = servlet.indexOf(PREVIEW_MAP_URL_PREF);
				MapRefID = servlet.substring(i + PREVIEW_MAP_URL_PREF.length()
						+ 1);
				String forward = PREVIEW_MAP_SERVLET;
				request.setAttribute(MAP_REF_ID_KEY, MapRefID);
				req.getRequestDispatcher(forward).forward(req, res);
				return;
			}
		}
		chain.doFilter(req, res);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig config) throws ServletException {
		log.debug("RequestMappingFilter INIT");
	}

}
