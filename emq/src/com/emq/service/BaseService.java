package com.emq.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import Algorithm.Coords.Converter;
import Algorithm.Coords.Point;
import com.emq.dao.BaseDao;
import com.emq.exception.ErrorMsgConstants;
import com.emq.logger.Logger;
import com.emq.model.CigStoreGISMap;
import com.emq.model.ConditionObject;
import com.emq.model.GISMapFactory;
import com.emq.ui.dwr.CigStoreFinderImpl;
import com.emq.util.TimeUtil;
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
	
	/**
	 * ִ��UpdateSQL
	 * @param sqlList
	 * @return
	 */
	public boolean exeUpdateSql(String sql){
		boolean mark = true;
		try{
			this.baseDao.execute(sql);
		}catch(Exception e){
			return false;
		}
		return mark;
	}
	
	/**
	 * Excel�����¼�����ID
	 * @param sqlList
	 * @return
	 */
	public String getImportMaxId(){
		String maxIdStr = "1";
		try{
			Map maxId = this.baseDao.getMapData("select max(FILE_ID) as MAXID from EMQ_BOOK_IMPORT");
			if(maxId!=null||maxId.size()==1){
				maxIdStr = String.valueOf(new Integer(maxId.get("MAXID").toString()).intValue()+1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return maxIdStr;
	}
	
	/**
	 * ����̨��Excel
	 * @param dataList
	 */
	public String createPassBookExcel(List dataList){
		String fileName = TimeUtil.dateToString(new Date(),"yyyyMMddHHmmss")+".xls";
		try {
			WritableWorkbook book=Workbook.createWorkbook(new File(fileName));
			WritableSheet sheet=book.createSheet("sheet1",0);
			for(int i=0;i<71;i++){
				sheet.setColumnView(i,20);
			}
			WritableCellFormat contentFromart = new WritableCellFormat(NumberFormats.TEXT); 
			WritableFont font1 = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD);
			WritableFont datafont = new WritableFont(WritableFont.ARIAL,10,WritableFont.NO_BOLD); 
			WritableCellFormat wf_head = new WritableCellFormat(font1);
			wf_head.setBorder(Border.ALL, BorderLineStyle.THIN);
			WritableCellFormat wf_data = new WritableCellFormat(datafont);
			wf_data.setBorder(Border.ALL, BorderLineStyle.THIN);
			wf_data.setAlignment(jxl.format.Alignment.CENTRE);
			wf_data.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			wf_head.setAlignment(jxl.format.Alignment.CENTRE);
			wf_head.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			sheet.addCell(new Label(0,0,"��վ����",wf_head));
			sheet.addCell(new Label(1,0,"�ؿڼ�����",wf_head));
			sheet.addCell(new Label(9,0,"���ܱ���Ϣ",wf_head));
			sheet.addCell(new Label(27,0,"��ѹ��������Ϣ",wf_head));
			sheet.addCell(new Label(41,0,"������������Ϣ",wf_head));
			sheet.addCell(new Label(55,0,"ʧѹ��ʱ����Ϣ",wf_head));
			sheet.addCell(new Label(59,0,"GPSʱ����Ϣ",wf_head));
			sheet.addCell(new Label(63,0,"���λ�·��Ϣ",wf_head));
			sheet.addCell(new Label(1,1,"������λ",wf_head));
			sheet.addCell(new Label(2,1,"����������",wf_head));
			sheet.addCell(new Label(3,1,"�Բ����������",wf_head));
			sheet.addCell(new Label(4,1,"�Բ�����㰲װ�ص�",wf_head));
			sheet.addCell(new Label(5,1,"���������",wf_head));
			sheet.addCell(new Label(6,1,"����������",wf_head));
			sheet.addCell(new Label(7,1,"�ؿ�����",wf_head));
			sheet.addCell(new Label(8,1,"���ܱ�����",wf_head));
			sheet.addCell(new Label(9,1,"�ͺ�",wf_head));
			sheet.addCell(new Label(10,1,"����",wf_head));
			sheet.addCell(new Label(11,1,"����",wf_head));
			sheet.addCell(new Label(12,1,"�������",wf_head));
			sheet.addCell(new Label(13,1,"���߷�ʽ",wf_head));
			sheet.addCell(new Label(14,1,"���ѹ(V)",wf_head));
			sheet.addCell(new Label(15,1,"�����(A)",wf_head));
			sheet.addCell(new Label(16,1,"׼ȷ�ȵȼ�",wf_head));
			sheet.addCell(new Label(17,1,"ͨѶ�ӿ�",wf_head));
			sheet.addCell(new Label(18,1,"ͨѶ��ʽ",wf_head));
			sheet.addCell(new Label(19,1,"�ɼ����ͺ�",wf_head));
			sheet.addCell(new Label(20,1,"�ɼ�������",wf_head));
			sheet.addCell(new Label(21,1,"TV���",wf_head));
			sheet.addCell(new Label(22,1,"TA���",wf_head));
			sheet.addCell(new Label(23,1,"����",wf_head));
			sheet.addCell(new Label(24,1,"������ʱ�� ",wf_head));
			sheet.addCell(new Label(25,1,"��������� ",wf_head));
			sheet.addCell(new Label(26,1,"�ͺ�",wf_head));
			sheet.addCell(new Label(27,1,"����",wf_head));
			sheet.addCell(new Label(28,1,"����",wf_head));
			sheet.addCell(new Label(29,1,"���",wf_head));
			sheet.addCell(new Label(30,1,"���",wf_head));
			sheet.addCell(new Label(31,1,"һ�ζ��ѹ(kV)",wf_head));
			sheet.addCell(new Label(32,1,"���ζ��ѹ(V)",wf_head));
			sheet.addCell(new Label(33,1,"�����������θ���(VA)",wf_head));
			sheet.addCell(new Label(34,1,"׼ȷ�ȵȼ�",wf_head));
			sheet.addCell(new Label(35,1,"����TV����(ĸ��/��·)",wf_head));
			sheet.addCell(new Label(36,1,"�Ƿ�ר������",wf_head));
			sheet.addCell(new Label(37,1,"�Ƿ�ר��TV",wf_head));
			sheet.addCell(new Label(38,1,"������ʱ��",wf_head));
			sheet.addCell(new Label(39,1,"���������",wf_head));
			sheet.addCell(new Label(40,1,"�ͺ�",wf_head));
			sheet.addCell(new Label(41,1,"����",wf_head));
			sheet.addCell(new Label(42,1,"����",wf_head));
			sheet.addCell(new Label(43,1,"���",wf_head));
			sheet.addCell(new Label(44,1,"���",wf_head));
			sheet.addCell(new Label(45,1,"һ�ζ����(A)",wf_head));
			sheet.addCell(new Label(46,1,"���ζ����(A)",wf_head));
			sheet.addCell(new Label(47,1,"�����������θ���(VA)",wf_head));
			sheet.addCell(new Label(48,1,"׼ȷ�ȵȼ�",wf_head));
			sheet.addCell(new Label(49,1,"����TA���ʣ�����/�׹ܣ�",wf_head));
			sheet.addCell(new Label(50,1,"�Ƿ�ר������",wf_head));
			sheet.addCell(new Label(51,1,"�Ƿ�ר��TA",wf_head));
			sheet.addCell(new Label(52,1,"������ʱ��",wf_head));
			sheet.addCell(new Label(53,1,"���������",wf_head));
			sheet.addCell(new Label(54,1,"�ͺ�",wf_head));
			sheet.addCell(new Label(55,1,"����",wf_head));
			sheet.addCell(new Label(56,1,"����",wf_head));
			sheet.addCell(new Label(57,1,"���",wf_head));
			sheet.addCell(new Label(58,1,"�ͺ�",wf_head));
			sheet.addCell(new Label(59,1,"����",wf_head));
			sheet.addCell(new Label(60,1,"����",wf_head));
			sheet.addCell(new Label(61,1,"���",wf_head));
			sheet.addCell(new Label(62,1,"TV���λ�·���߽����(mm2)",wf_head));
			sheet.addCell(new Label(63,1,"TV���λ�·���ȣ�m��",wf_head));
			sheet.addCell(new Label(64,1,"TA���λ�·���߽����(mm2)",wf_head));
			sheet.addCell(new Label(65,1,"TV����ѹ��������ʱ��",wf_head));
			sheet.addCell(new Label(66,1,"TV����ѹ�����������",wf_head));
			sheet.addCell(new Label(67,1,"TV���θ���������ʱ��",wf_head));
			sheet.addCell(new Label(68,1,"TV���θ������������",wf_head));
			sheet.addCell(new Label(69,1,"TA���θ���������ʱ��",wf_head));
			sheet.addCell(new Label(70,1,"TA���θ������������",wf_head));
			sheet.mergeCells(0, 0, 0, 1);
			sheet.mergeCells(1, 0, 7, 0);
			sheet.mergeCells(8, 0, 25, 0);
			sheet.mergeCells(26, 0, 39, 0);
			sheet.mergeCells(40, 0, 53, 0);
			sheet.mergeCells(54, 0, 57, 0);
			sheet.mergeCells(58, 0, 61, 0);
			sheet.mergeCells(62, 0, 70, 0);
			for(int i=0;i<dataList.size();i++){
				int dataRow = i+2;
				Map map = (Map)dataList.get(i);
				sheet.addCell(new Label(0,dataRow,map.get("STATION_NAME").toString(),wf_data));
				sheet.addCell(new Label(1,dataRow,map.get("BELONG_UNIT_NAME").toString(),wf_data));
				sheet.addCell(new Label(2,dataRow,map.get("MEASURE_POINT_NAME").toString(),wf_data));
				sheet.addCell(new Label(3,dataRow,map.get("SUB_POINT_NAME").toString(),wf_data));
				sheet.addCell(new Label(4,dataRow,map.get("SUB_POINT_ADDRESS").toString(),wf_data));
				sheet.addCell(new Label(5,dataRow,map.get("MEASURE_POINT_TYPE").toString(),wf_data));
				sheet.addCell(new Label(6,dataRow,map.get("MEASURE_POINT_KIND").toString(),wf_data));
				sheet.addCell(new Label(7,dataRow,map.get("PASS_TYPE").toString(),wf_data));
				sheet.addCell(new Label(8,dataRow,map.get("E_CONFIGURE").toString(),wf_data));
				sheet.addCell(new Label(9,dataRow,map.get("E_MODEL").toString(),wf_data));
				sheet.addCell(new Label(10,dataRow,map.get("E_TYPE").toString(),wf_data));
				sheet.addCell(new Label(11,dataRow,map.get("E_MANUFACTURER").toString(),wf_data));
				
//				sheet.addCell(new Label(12,dataRow,map.get("E_FACTORY_NUMBER").toString(),contentFromart));
				if(map.get("E_FACTORY_NUMBER")!=null&&!map.get("E_FACTORY_NUMBER").equals("")){
					sheet.addCell(new jxl.write.Number(12,dataRow,new Double(map.get("E_FACTORY_NUMBER").toString()),wf_data));
				}else{
					sheet.addCell(new Label(12,dataRow,map.get("E_FACTORY_NUMBER").toString(),wf_data));
				}
				
				sheet.addCell(new Label(13,dataRow,map.get("E_CONNECTION").toString(),wf_data));
				sheet.addCell(new Label(14,dataRow,map.get("E_TENSION").toString(),wf_data));
				sheet.addCell(new Label(15,dataRow,map.get("E_ELECTRICITY").toString(),wf_data));
				sheet.addCell(new Label(16,dataRow,map.get("E_RANK").toString(),wf_data));
				sheet.addCell(new Label(17,dataRow,map.get("E_MESSAGE_INTERFACE").toString(),wf_data));
				sheet.addCell(new Label(18,dataRow,map.get("E_MESSAGE_TYPE").toString(),wf_data));
				sheet.addCell(new Label(19,dataRow,map.get("E_COL_MODEL").toString(),wf_data));
				sheet.addCell(new Label(20,dataRow,map.get("E_COL_FACTORY").toString(),wf_data));
				sheet.addCell(new Label(21,dataRow,map.get("E_TV_RATE").toString(),wf_data));
				sheet.addCell(new Label(22,dataRow,map.get("E_TA_RATE").toString(),wf_data));
				sheet.addCell(new Label(23,dataRow,map.get("E_MULTIPLE").toString(),wf_data));
				sheet.addCell(new Label(24,dataRow,map.get("E_CHECK_TIME").toString(),wf_data));
				sheet.addCell(new Label(25,dataRow,map.get("E_CHECK_RESULT").toString(),wf_data));
				sheet.addCell(new Label(26,dataRow,map.get("T_MODEL").toString(),wf_data));
				sheet.addCell(new Label(27,dataRow,map.get("T_TYPE").toString(),wf_data));
				sheet.addCell(new Label(28,dataRow,map.get("T_FACTORY").toString(),wf_data));
				sheet.addCell(new Label(29,dataRow,map.get("T_DISCREPANCY").toString(),wf_data));
				
//				sheet.addCell(new Label(30,dataRow,map.get("T_UNMBER").toString(),contentFromart));
				if(map.get("T_UNMBER")!=null&&!map.get("T_UNMBER").equals("")){
					sheet.addCell(new jxl.write.Number(30,dataRow,new Double(map.get("T_UNMBER").toString()),wf_data));
				}else{
					sheet.addCell(new Label(30,dataRow,map.get("T_UNMBER").toString(),wf_data));
				}
				
				sheet.addCell(new Label(31,dataRow,map.get("T_TENSION_FIRST").toString(),wf_data));
				sheet.addCell(new Label(32,dataRow,map.get("T_TENSION_SECOND").toString(),wf_data));
				sheet.addCell(new Label(33,dataRow,map.get("T_TENSION_SECOND_CHARGE").toString(),wf_data));
				sheet.addCell(new Label(34,dataRow,map.get("T_RANK").toString(),wf_data));
				sheet.addCell(new Label(35,dataRow,map.get("T_TV_KIND").toString(),wf_data));
				sheet.addCell(new Label(36,dataRow,map.get("T_IS_SPECIAL").toString(),wf_data));
				sheet.addCell(new Label(37,dataRow,map.get("T_IS_SPECIAL_TV").toString(),wf_data));
				sheet.addCell(new Label(38,dataRow,map.get("T_CHECK_TIME").toString(),wf_data));
				sheet.addCell(new Label(39,dataRow,map.get("T_CHECK_RESULT").toString(),wf_data));
				sheet.addCell(new Label(40,dataRow,map.get("EI_MODEL").toString(),wf_data));
				sheet.addCell(new Label(41,dataRow,map.get("EI_TYPE").toString(),wf_data));
				sheet.addCell(new Label(42,dataRow,map.get("EI_MANUFACTURER").toString(),wf_data));
				sheet.addCell(new Label(43,dataRow,map.get("EI_DISCREPANCY").toString(),wf_data));
				
//				sheet.addCell(new Label(44,dataRow,map.get("EI_UNMBER").toString(),contentFromart));
				if(map.get("EI_UNMBER")!=null&&!map.get("EI_UNMBER").equals("")){
					sheet.addCell(new jxl.write.Number(44,dataRow,new Double(map.get("EI_UNMBER").toString()),wf_data));
				}else{
					sheet.addCell(new Label(44,dataRow,map.get("EI_UNMBER").toString(),wf_data));
				}
				
				sheet.addCell(new Label(45,dataRow,map.get("EI_RATED_TENSION_FIRST").toString(),wf_data));
				sheet.addCell(new Label(46,dataRow,map.get("EI_RATED_TENSION_SECOND").toString(),wf_data));
				sheet.addCell(new Label(47,dataRow,map.get("EI_RATED_TENSION_SECOND_CHARGE").toString(),wf_data));
				sheet.addCell(new Label(48,dataRow,map.get("EI_VERACITY_RANK").toString(),wf_data));
				sheet.addCell(new Label(49,dataRow,map.get("EI_TV_KIND").toString(),wf_data));
				sheet.addCell(new Label(50,dataRow,map.get("EI_IS_SPECIAL").toString(),wf_data));
				sheet.addCell(new Label(51,dataRow,map.get("EI_IS_SPECIAL_TV").toString(),wf_data));
				sheet.addCell(new Label(52,dataRow,map.get("EI_CHECK_TIME").toString(),wf_data));
				sheet.addCell(new Label(53,dataRow,map.get("EI_CHECK_RESULT").toString(),wf_data));
				sheet.addCell(new Label(54,dataRow,map.get("LT_MODEL").toString(),wf_data));
				sheet.addCell(new Label(55,dataRow,map.get("LT_TYPE").toString(),wf_data));
				sheet.addCell(new Label(56,dataRow,map.get("LT_MANUFACTURER").toString(),wf_data));
				
//				sheet.addCell(new Label(57,dataRow,map.get("LT_UNMBER").toString(),contentFromart));
				if(map.get("LT_UNMBER")!=null&&!map.get("LT_UNMBER").equals("")){
					sheet.addCell(new jxl.write.Number(57,dataRow,new Double(map.get("LT_UNMBER").toString()),wf_data));
				}else{
					sheet.addCell(new Label(57,dataRow,map.get("LT_UNMBER").toString(),wf_data));
				}
				
				sheet.addCell(new Label(58,dataRow,map.get("GPS_MODEL").toString(),wf_data));
				sheet.addCell(new Label(59,dataRow,map.get("GPS_TYPE").toString(),wf_data));
				sheet.addCell(new Label(60,dataRow,map.get("GPS_MANUFACTURER").toString(),wf_data));
				
//				sheet.addCell(new Label(61,dataRow,map.get("GPS_UNMBER").toString(),contentFromart));
				if(map.get("GPS_UNMBER")!=null&&!map.get("GPS_UNMBER").equals("")){
					sheet.addCell(new jxl.write.Number(61,dataRow,new Double(map.get("GPS_UNMBER").toString()),wf_data));
				}else{
					sheet.addCell(new Label(61,dataRow,map.get("GPS_UNMBER").toString(),wf_data));
				}
				
				sheet.addCell(new Label(62,dataRow,map.get("TV_AREA").toString(),wf_data));
				sheet.addCell(new Label(63,dataRow,map.get("TV_LENGTH").toString(),wf_data));
				sheet.addCell(new Label(64,dataRow,map.get("TA_LENGTH").toString(),wf_data));
				sheet.addCell(new Label(65,dataRow,map.get("TV_TIME").toString(),wf_data));
				sheet.addCell(new Label(66,dataRow,map.get("TV_RESULT").toString(),wf_data));
				sheet.addCell(new Label(67,dataRow,map.get("TV_CHARGE_TIME").toString(),wf_data));
				sheet.addCell(new Label(68,dataRow,map.get("TV_CHARGE_RESULT").toString(),wf_data));
				sheet.addCell(new Label(69,dataRow,map.get("TA_CHARGE_TIME").toString(),wf_data));
				sheet.addCell(new Label(70,dataRow,map.get("TA_CHARGE_RESULT").toString(),wf_data));
			}
			for(int i=0;i<=70;i++){
				int startRow = 2;
				int endRow = 2;
				boolean flag = false;
				for(int j=0;j<dataList.size();j++){
					if(sheet.getCell(i, j+2).getContents().equals(sheet.getCell(i, j+3).getContents())){
						endRow ++;
					}else{
						flag = true;
					}
					if(flag){
						if(startRow!=(j+2)){
							sheet.mergeCells(i, startRow, i, j+2);
						}
						startRow = j+3;
						flag = false;
					}
				}
			}
			book.write(); 
			book.close(); 
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return fileName;
	}
	
	/**
	 * �����ϴ��ļ�ID��ȡ�ļ�����·��
	 * @param id
	 * @return
	 */
	public String getDownLoadFile(Integer id){
		Map map = baseDao.getDataForMap("select FILE_PATH from EMQ_BOOK_IMPORT where id="+id);
		return map.get("FILE_PATH").toString();
	}
	
}
