package com.emq.dao;

import java.util.List;
import com.emq.dao.jdbc.BaseJdbcDaoImp;
/**
 * �������ݲ�ѯ
 * @author lyt
 * @created 2009-9-22
 */
public class BaseDao extends BaseJdbcDaoImp{

	/**
	 * ����Id��ȡ�ֵ�List
	 * @param String area ����Id
	 * @param String corpCode ������
	 * @return List
	 * @author lyt
	 */
	public List getStreetList(String corpCode,String area){
		List streetList = null;
		if(corpCode.equals("all")&&area.equals("all")){
			streetList = this.getDataList("select distinct jdbm,jdmc from GIS_MS_LICENSE where jdmc is not null");
		}else if(!corpCode.equals("all")&&area.equals("all")){
			streetList = this.getDataList("select distinct jdbm,jdmc from GIS_MS_LICENSE where GSDM ='"+corpCode+"' and jdmc is not null");
		}else{
			streetList = this.getDataList("select distinct jdbm,jdmc from GIS_MS_LICENSE where GSDM ='"+corpCode+"' and dqmc='"+area+"' and jdmc is not null");
		}
		return streetList;
	}
	/**
	 * ��ȡ����List
	 * @return List
	 * @param String corpCode �ع�˾����
	 * @author lyt
	 */
	public List getAreaList(String corpCode){
		List areaList = null;
		if(corpCode.equals("all")){
			areaList = this.getDataList("select distinct dqbm,dqmc from GIS_MS_LICENSE where dqmc is not null");
		}else{
			areaList = this.getDataList("select distinct dqbm,dqmc from GIS_MS_LICENSE where dqmc is not null and GSDM='"+corpCode+"'");
		}
		return areaList;
	}
	
	/**
	 * ��ȡ��Ӫҵ̬List
	 * @return List
	 * @author lyt
	 */
	public List getFareTypeList(){
		return this.getDataList("select distinct jyyt from GIS_MS_LICENSE where jyyt is not null");
	}
	
	/**
	 * ��ȡ��ǰλ��List
	 * @return List
	 * @author lyt
	 */
	public List getLocationList(String orgId){
		return this.getDataList("select distinct ZMJGBM,DTPZH,ZMJGMC from GIS_CON_REGION where QYJB=2 and DTPZH is not null and (zmjgbm in("+orgId+") or (select qyjb from gis_con_region where  zmjgbm in("+orgId+")) = 1) order by zmjgbm");
	}
	
	/**
	 * ��ȡ��·List
	 * @param corpCode
	 * @return List
	 * @author lyt
	 */
	public List getLineList(String corpCode){
		List lineList = null;
		if(corpCode.equals("all")){
			lineList = this.getDataList("select distinct XSXL from GIS_MS_LICENSE where XSXL is not null");
		}else{
			lineList = this.getDataList("select distinct XSXL from GIS_MS_LICENSE where XSXL is not null  and GSDM='"+corpCode+"'");
		}
		return lineList;
	}
	
	/**
	 * ��ȡ�ȼ�List
	 * @return List
	 * @author lyt
	 */
	public List getRankList(){
		return this.getDataList("select distinct XSKHDJ from GIS_MS_LICENSE where XSKHDJ is not null");
	}
	
	/**
	 * ��ȡר���ȼ�List
	 * @return List
	 * @author lyt
	 */
	public List getSpecialRankList(){
		return this.getDataList("select distinct ZMDJ from GIS_MS_LICENSE where ZMDJ is not null");
	}
	
	/**
	 * ��ȡƷ��List
	 * @return List
	 * @author lyt
	 */
	public List getBrdList(){
		return this.getDataList("select distinct BRD,BRDNAME from GIS_SS_3M_BRD where BRD is not null");
	}
	
	/**
	 * ��ȡ���List
	 * @return List
	 * @author lyt
	 */
	public List getCigList(String brd){
		if(brd.equals("all")){
			return this.getDataList("select distinct CIG,CIGNAME from GIS_SS_3M_CIG where CIG is not null");
		}else{
			return this.getDataList("select distinct CIG,CIGNAME from GIS_SS_3M_CIG where CIG is not null and BRD='"+brd+"'");
		}
		
	}
	
	/**
	 * ��ȡ��Ҫ����ƫ�Ƽ��������
	 * @return List
	 * @author lyt
	 */
	public List getRunConverterList(){
		String sql = "select XKZHM,X1,Y1,GSDM from GIS_MS_LICENSE where X=0 and Y=0 and ((X1>102.1 and X1<103.7 and Y1>24.3 and Y1<26.6) or (Y1>102.1 and Y1<103.7 and X1>24.3 and X1<26.6))";
		return getDataList(sql);
	}
	
	/**
	 * ��ȡpc�б�
	 * @return List
	 * @author lyt
	 */
	public List getPcList(String corpCode){
		return this.getDataList("select distinct pc from GIS_MS_LICENSE_CHECK where pc is not null and GSDM='"+corpCode+"'");
	}
}
