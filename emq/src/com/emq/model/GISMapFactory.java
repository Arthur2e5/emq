package com.emq.model;
import java.util.List; 

import com.emq.config.MapDef;
import com.emq.config.SystemConfig;
import com.emq.exception.ErrorMsgConstants;
import com.emq.exception.GISException;
import com.emq.logger.Logger;
import com.emq.service.DataAccessController; 
/**
 * ��ͼ������������
 * @author guqiong
 * @created 2009-9-27
 * @history 2009-10-21
 * 			fix bug:map.setId Ӧʹ�õ�ͼ����id
 */
public class GISMapFactory {
	private Logger log = Logger.getLogger(GISMapFactory.class);
	
	private DataAccessController dataAccessController;
	
	public DataAccessController getDataAccessController() {
		return dataAccessController;
	}

	public void setDataAccessController(DataAccessController dataAccessController) {
		this.dataAccessController = dataAccessController;
	}

	
	/**
	 * ������ͼ����
	 * @param mapRefId ��ͼ����id
	 * @return
	 * @throws GISException
	 */
	public AbstractGISMap create(String mapRefId)throws GISException{
		return create(mapRefId, null);
	}
	
	/**
	 * ������ͼ����
	 * @param mapRefId ��ͼ����id
	 * @param orgId ��ͼʹ�û���id
	 * @return
	 * @throws GISException
	 */
	public AbstractGISMap create(String mapRefId, String OrgId)throws GISException{
		MapDef mapDef = SystemConfig.getInstance().getMapDef(mapRefId);
		if(mapDef.isAbstractDef())
			throw new GISException(ErrorMsgConstants.KMGIS_MAP_03);
		else
			return create(mapRefId, mapDef, OrgId);
	}
	
	/**
	 * ������ͼ����
	 * @param mapDef ��ͼ����
	 * @praram mapRefId  ��ͼ����id
	 * @param orgId ��ͼʹ�û���id,Ϊ�ս�ʹ��ϵͳ��ǰ�û�����֯����id
	 * @return
	 * @throws GISException
	 */
	protected AbstractGISMap create(String mapRefId, MapDef mapDef, String OrgId) throws GISException{
		AbstractGISMap map = null;
		try{
			String clazz = mapDef.getClazz();
			map = (AbstractGISMap) Class.forName(clazz).newInstance();			
			String orgCode = OrgId;
			if(orgCode==null || "".equals(orgCode)||"null".equals(orgCode)){
				orgCode=dataAccessController.getCurrentUser().getOrgCode();
			}
			MapDef areaDef = SystemConfig.getInstance().getDefaultMap(orgCode);	
			//���û��Ĭ�ϵĵ�ͼ���ټ��������б���û�������쳣
			if(areaDef == null){			
				List<String> allowed = SystemConfig.getInstance().getAllowAccessMaps().get(orgCode);
				if(allowed == null || allowed.isEmpty()){
					throw new GISException(ErrorMsgConstants.KMGIS_SECURITY_02);
				}else{
					String defId = allowed.get(0); 
					areaDef = SystemConfig.getInstance().getMapDefines().getMapDefById(defId); 
					if(areaDef == null)
						throw new GISException(ErrorMsgConstants.KMGIS_MAP_08);
				}
	 		}
			map.setMapDef(mapDef.mergeFrom(areaDef));
			map.setId(mapRefId);
			return map;
		}catch(Exception e){
			throw new GISException(e);
		}
	}
}
