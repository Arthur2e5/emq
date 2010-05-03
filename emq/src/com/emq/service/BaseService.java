package com.emq.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import Algorithm.Coords.Converter;
import Algorithm.Coords.Point;
import com.emq.dao.BaseDao;
import com.emq.exception.ErrorMsgConstants;
import com.emq.logger.Logger;
import com.emq.model.CigStoreGISMap;
import com.emq.model.ConditionObject;
import com.emq.model.GISMapFactory;
import com.emq.ui.dwr.CigStoreFinderImpl;
import com.mapinfo.util.DoublePoint;
/**
 * ����ҵ���߼�����
 * @author lyt
 * @created 2009-9-22
 */
public class BaseService {
	
	private CigStoreGISMap target = null;
	
	private GISMapFactory gisMapFactory;
	
	public GISMapFactory getGisMapFactory() {
		return gisMapFactory;
	}
	public void setGisMapFactory(GISMapFactory gisMapFactory) {
		this.gisMapFactory = gisMapFactory;
	}
	
	private static Logger log = Logger.getLogger(BaseService.class);
	
	private BaseDao baseDao;


	public BaseDao getBaseDao() {
		return baseDao;
	}
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	/**
	 * ��ȡR1��¼��Ա����
	 * @return String
	 */
	public String getUserId()  {
//		Context ctx = null;
//		if (ctx == null) {
//			try {
//				ctx = Context.getInstance();
//			} catch (Exception e) {
//				//e.printStackTrace(); //�����в��ڿ���̨���
//			}
//		}
//		if (ctx != null) {
//			try {
//				UserInfo userInfo = ctx.getCurrentLoginInfo();
//				log.debug("��ǰ�û�ID:" + userInfo.getUserID());
//				return userInfo.getUserID();
//			} catch (Exception e) {
//				 //e.printStackTrace();
//				 //log.error(e.getMessage());
//			}
//		} else {
//			log.error("���ã���¼��Ϣ���ڣ����˳����µ�¼��");
//		}
		return "dev_icss";
	}
	
	/**
	 * ��ȡR1��¼��Ա��֯����
	 * @return String
	 */
	public List getUserOrgList()  {
//		Context ctx = null;
//		if (ctx == null) {
//			try {
//				ctx = Context.getInstance();
//			} catch (Exception e) {
//				System.out.println(e.getMessage());
//				e.printStackTrace(); //�����в��ڿ���̨���
//			}
//		}
//		if (ctx != null) {
//			try {
//				UserInfo userInfo = ctx.getCurrentLoginInfo();
//				log.debug("��ǰ�û�ID:" + userInfo.getUserID());
//				List orgList = ctx.getCurrentOrganization();
//				return orgList;
//			} catch (Exception e) {
//				e.printStackTrace();
//				log.error(e.getMessage());
//			}
//		} else {
//			log.error("���ã���¼��Ϣ���ڣ����˳����µ�¼��");
//		}
		return new ArrayList();
	}

	/**
	 * ��ȡR1��¼��Ա����
	 * @return String
	 */
	public String getUserName() {
//		Context ctx = null;
//		if (ctx == null) {
//			log.debug("��ʼ��ʼ��RONE��Ϣ");
//			try {
//				ctx = Context.getInstance();
//			} catch (Exception e) {
//				e.printStackTrace(); //�����в��ڿ���̨���
//			}
//		}
//
//		if (ctx != null) {
//			try {
//				UserInfo userInfo = ctx.getCurrentLoginInfo();
//				//log.debug("��ǰ�û�:" + userInfo.getName());
//				return userInfo.getName();
//			} catch (Exception e) {
//				//e.printStackTrace();
//				//log.error(e.getMessage());
//			}
//		} else {
//			log.error("���ã���¼��Ϣ���ڣ����˳����µ�¼��");
//		}
		return "�����û�";
	}
	
	/**
	 * ���ݲ�ѯ�������е�ͼ��ѯ
	 * @param mapId
	 * @param startx
	 * @param starty
	 * @param newx
	 * @param newy
	 * @param session
	 * @return
	 */
	public void findByCondition(String mapId,ConditionObject co,HttpSession session){
		CigStoreFinderImpl cf = new CigStoreFinderImpl();
		try{
			cf.highlightByCondition(mapId,co, session);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * �Զ�����ƫ�Ƽ���
	 */
	public void runConverter(){
		List sqlList = new ArrayList();
		List dataList = baseDao.getRunConverterList();
		System.out.println("һ����"+dataList.size()+"��Ҫ����ƫ������!");
		try {
			target = (CigStoreGISMap) gisMapFactory.create("CigStoreMap");
			target.loadMap();
			Converter converter = new Converter();
			for(int i=0;i<dataList.size();i++){
				Map map = (Map)dataList.get(i);
				Double x = (Double)map.get("X1");
				Double y = (Double)map.get("Y1");
				Point p = null;
				DoublePoint dp = null;
				if(x<y){
					p = converter.getEncryPoint(y,x); 
					dp = new DoublePoint(y,x);
					sqlList.add("update GIS_MS_LICENSE set X="+p.getY()+",Y="+p.getX()+" where XKZHM='"+map.get("XKZHM")+"'");
				}else{
					p = converter.getEncryPoint(x,y); 
					dp = new DoublePoint(x,y);
					sqlList.add("update GIS_MS_LICENSE set X="+p.getX()+",Y="+p.getY()+" where XKZHM='"+map.get("XKZHM")+"'");
				}
//				������Χ����ɵ�����
				boolean within = target.within(dp,(String)map.get("GSDM"));
				if(!within){
					String sql = "update gis_ms_license_check set sfxs=1,lrcdsj=getdate() where xkzhm='"+map.get("XKZHM")+"'";
					sqlList.add(sql);
				}
			}
			baseDao.exeUpdateSqlByBach(sqlList);
			System.out.println("����ƫ���������!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �޸�xy����
	 * @param po 
	 */
	public boolean updateCheckPoint(Double x,Double y,String xkzhm){
		boolean mark = true;
		try{
			Map pointMap = this.getBaseDao().getDataForMap("select x,y,x1,y1 from GIS_MS_LICENSE_CHECK where XKZHM='"+xkzhm+"'");
			Double x_temp = (Double)pointMap.get("X")-(Double)pointMap.get("X1");
			Double y_temp = (Double)pointMap.get("Y")-(Double)pointMap.get("Y1");
			Double x1 = x-x_temp;
		    Double y1 = y-y_temp;;
			this.getBaseDao().exeUpdateSql("update GIS_MS_LICENSE_CHECK set x="+x+",y="+y+",x1="+x1+",y1="+y1+" where XKZHM='"+xkzhm+"'");
		} catch (Exception e) {
			mark = false;
			e.printStackTrace();
		}
		return mark;
	}
	
	/**
	 * ����ִ��SQL
	 * @param sqlList
	 * @return
	 */
	public boolean exeUpdateSqlByBach(List sqlList){
		boolean mark = true;
		try{
			this.baseDao.exeUpdateSqlByBach(sqlList);
		}catch(Exception e){
			return false;
		}
		return mark;
	}
	
}
