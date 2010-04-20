package com.emq.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import Algorithm.Coords.Converter;
import Algorithm.Coords.Point;
import com.emq.dao.BaseDao;
import com.emq.logger.Logger;
import com.emq.model.CigStoreGISMap;
import com.emq.model.ConditionObject;
import com.emq.model.GISMapFactory;
import com.emq.ui.dwr.CigStoreFinderImpl;
import com.mapinfo.dp.FeatureSet;
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
	 * ��ȡ�ֵ���Ϣ����������ƣ�
	 * @param area ����ID
	 * @param corpCode ������
	 * @return List
	 */
	public List getStreetList(String corpCode,String area){
		return this.baseDao.getStreetList(corpCode,area);
	}
	/**
	 * ��ȡ������Ϣ����������ƣ�
	 * @param String corpCode �ع�˾����
	 * @return List
	 */
	public List getAreaList(String corpCode){
		return this.baseDao.getAreaList(corpCode);
	}
	
	/**
	 * ��ȡ��Ӫҵ̬��Ϣ����������ƣ�
	 * @return List
	 */
	public List getFareTypeList(){
		return this.baseDao.getFareTypeList();
	}
	
	/**
	 * ��ȡ��ǰλ����Ϣ
	 * @param orgId ��֯ID
	 * @return List
	 */
	public List getLocationList(String orgId){
		return this.baseDao.getLocationList(orgId);
	}
	
	/**
	 * ��ȡ��·��Ϣ
	 * @param corpCode
	 * @return List
	 */
	public List getLineList(String corpCode){
		return this.baseDao.getLineList(corpCode);
	}
	
	/**
	 * ��ȡ�ȼ���Ϣ
	 * @return List
	 */
	public List getRankList(){
		return this.baseDao.getRankList();
	}
	
	/**
	 * ��ȡר���ȼ���Ϣ
	 * @return List
	 */
	public List getSpecialRankList(){
		return this.baseDao.getSpecialRankList();
	}
	
	/**
	 * ��ȡƷ����Ϣ
	 * @return List
	 */
	public List getBrdList(){
		return this.baseDao.getBrdList();
	}
	
	/**
	 * ��ȡ�����Ϣ
	 * @return List
	 */
	public List getCigList(String brd){
		return this.baseDao.getCigList(brd);
	}
	
	/**
	 * ��ȡ�ֵ�������
	 * @param area ����ID
	 * @param corpCode ������
	 * @return �ֵ�������html
	 */
	public String getStreetSelect(String corpCode,String area){
		String streetHtml = "<select name='streetName' id='streetName' style='width:96'><option value=''>-��ѡ��-</option><option value='all'>-ȫ��-</option>";
		List streetList = this.getStreetList(corpCode,area);
		for(int i=0;i<streetList.size();i++){
			Map map = (Map)streetList.get(i);
			streetHtml += "<option value='"+map.get("JDMC")+"'>"+map.get("JDMC")+"</option>";
		}
		streetHtml += "</select>";
		return streetHtml;
	}
	
	/**
	 * ��ȡ����������
	 * @param String corpCode �ع�˾����
	 * @return ����������html
	 */
	public String getAreaSelect(String corpCode){
		String areaHtml = "<select name='areaName' id='areaName' onchange='areaChange()' style='width:96'><option value=''>-��ѡ��-</option><option value='all'>-ȫ��-</option>";
		List areaList = this.getAreaList(corpCode);
		for(int i=0;i<areaList.size();i++){
			Map map = (Map)areaList.get(i);
			areaHtml += "<option value='"+map.get("DQMC")+"'>"+map.get("DQMC")+"</option>";
		}
		areaHtml += "</select>";
		return areaHtml;
	}
	
	/**
	 * ��ȡ��Ӫҵ̬������
	 * @return ��Ӫҵ̬������html
	 */
	public String getFareTypeSelect(){
		String areaHtml = "<select name='fareTypeName' id='fareTypeName' style='width:96'><option value=''>-��ѡ��-</option><option value='all'>-ȫ��-</option>";
		List fareTypeList = this.getFareTypeList();
		for(int i=0;i<fareTypeList.size();i++){
			Map map = (Map)fareTypeList.get(i);
			areaHtml += "<option value='"+map.get("JYYT")+"'>"+map.get("JYYT")+"</option>";
		}
		areaHtml += "</select>";
		return areaHtml;
	}
	
	/**
	 * ��ȡ��ǰλ��������
	 * @return ��ǰλ��������html
	 */
	public String getLocationSelect(String orgId){
		System.out.println("orgId="+orgId);
		String locationHtml = "<select name='location' id='location' onchange='locationChange(this.value)'>";
		List orgList = this.getUserOrgList();
		System.out.println("orgList:="+orgList.size());
		if(orgId==null||orgId.equals("")){
//			for(int i=0;i<orgList.size();i++){
//				Organization org = (Organization)orgList.get(i);
//				log.debug("��ǰ�û���֯����:" + org.getOrgcode());
//				System.out.println("��ǰ�û���֯����:="+org.getOrgcode());
//				orgId += "'"+org.getOrgcode()+"',";
//			}
			if(orgId.length()>0){
				orgId = orgId.substring(0, orgId.length()-1);
			}
			if(orgId.length()<1){
				orgId = "'53010000'";
			}
		}
		List locationList = this.getLocationList(orgId);
		for(int i=0;i<locationList.size();i++){
			Map map = (Map)locationList.get(i);
			locationHtml += "<option value='"+map.get("ZMJGBM")+"|"+map.get("DTPZH")+"'>"+map.get("ZMJGMC")+"</option>";
		}
		locationHtml += "</select>";
		return locationHtml;
	}
	
	/**
	 * ��ȡ������������
	 * @return ������������html
	 */
	public String getCorpSelect(){
		
		List orgList = this.getUserOrgList();
		String orgId = "";
//		for(int i=0;i<orgList.size();i++){
//			Organization org = (Organization)orgList.get(i);
//			log.debug("��ǰ�û���֯����:" + org.getOrgcode());
//			orgId += "'"+org.getOrgcode()+"',";
//		}
		if(orgId.length()>0){
			orgId = orgId.substring(0, orgId.length()-1);
		}
		if(orgId.length()<1){
			orgId = "'53010000'";
		}
		List locationList = this.getLocationList(orgId);
		String corpCodeHtml = "<select name='corpCode' id='corpCode' onchange='corpCodeChange(this.value)' style='width:96'><option value=''>-��ѡ��-</option>";
		if(locationList.size()>1){
			corpCodeHtml += "<option value='all'>-ȫ��-</option>";
		}
		for(int i=0;i<locationList.size();i++){
			Map map = (Map)locationList.get(i);
			corpCodeHtml += "<option value='"+map.get("ZMJGBM")+"'>"+map.get("ZMJGMC")+"</option>";
		}
		corpCodeHtml += "</select>";
		return corpCodeHtml;
	}
	
	/**
	 * ��ȡ��·������
	 * @param corpCode
	 * @return ��·������html
	 */
	public String getLineSelect(String corpCode){
		String lineHtml = "<select name='line' id='line' style='width:96'><option value=''>-��ѡ��-</option><option value='all'>-ȫ��-</option>";
		List lineList = this.getLineList(corpCode);
		for(int i=0;i<lineList.size();i++){
			Map map = (Map)lineList.get(i);
			lineHtml += "<option value='"+map.get("XSXL")+"'>"+map.get("XSXL")+"</option>";
		}
		lineHtml += "</select>";
		return lineHtml;
	}
	
	/**
	 * ��ȡ�ȼ�������
	 * @return �ȼ�������html
	 */
	public String getRankSelect(){
		String rankHtml = "<select name='rank' id='rank' style='width:96'><option value=''>-��ѡ��-</option><option value='all'>-ȫ��-</option>";
		List rankList = this.getRankList();
		for(int i=0;i<rankList.size();i++){
			Map map = (Map)rankList.get(i);
			rankHtml += "<option value='"+map.get("XSKHDJ")+"'>"+map.get("XSKHDJ")+"</option>";
		}
		rankHtml += "</select>";
		return rankHtml;
	}
	
	/**
	 * ��ȡר���ȼ�������
	 * @return �ȼ�������html
	 */
	public String getSpecialRankSelect(){
		String specialRankHtml = "<select name='specialRank' id='specialRank' style='width:96'><option value=''>-��ѡ��-</option><option value='all'>-ȫ��-</option>";
		List specialRankList = this.getRankList();
		for(int i=0;i<specialRankList.size();i++){
			Map map = (Map)specialRankList.get(i);
			specialRankHtml += "<option value='"+map.get("ZMDJ")+"'>"+map.get("ZMDJ")+"</option>";
		}
		specialRankHtml += "</select>";
		return specialRankHtml;
	}
	
	/**
	 * ��ȡƷ��������
	 * @return Ʒ��������html
	 */
	public String getBrdSelect(){
		String rankHtml = "<select name='brd' id='brd' onchange='brdChange()' style='width:96'><option value=''>-��ѡ��-</option><option value='all'>-ȫ��-</option>";
		List brdList = this.getBrdList();
		for(int i=0;i<brdList.size();i++){
			Map map = (Map)brdList.get(i);
			rankHtml += "<option value='"+map.get("BRD")+"'>"+map.get("BRDNAME")+"</option>";
		}
		rankHtml += "</select>";
		return rankHtml;
	}
	
	/**
	 * ��ȡ���������
	 * @return ���������html
	 */
	public String getCigSelect(String brd){
		String rankHtml = "<select name='cig' id='cig' style='width:96'><option value=''>-��ѡ��-</option><option value='all'>-ȫ��-</option>";
		List cigList = this.getCigList(brd);
		for(int i=0;i<cigList.size();i++){
			Map map = (Map)cigList.get(i);
			rankHtml += "<option value='"+map.get("CIG")+"'>"+map.get("CIGNAME")+"</option>";
		}
		rankHtml += "</select>";
		return rankHtml;
	}
	
	/**
	 * ���ݲ�ѯ�������в�ѯ
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
	 * ��ȡ����������
	 * @return ����������html
	 */
	public String getPcSelect(String corpCode){
		String rankHtml = "<select name='pc' id='pc' style='width:96'><option value=''>-��ѡ��-</option>";
		List pcList = this.baseDao.getPcList(corpCode);
		for(int i=0;i<pcList.size();i++){
			Map map = (Map)pcList.get(i);
			rankHtml += "<option value='"+map.get("PC")+"'>"+map.get("PC")+"</option>";
		}
		rankHtml += "</select>";
		return rankHtml;
	}
	
}
