package com.plant.config;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import com.plant.logger.Logger;
import java.util.List;
import java.util.Map;
/**
 * ϵͳ���ò���������<P>����
 * ȫ���Ե�ϵͳ����Ӧ����kmgis.properties�ļ���
 * ���ڵ�ͼ�����ϵͳ��������gis-map.xml�ļ���
 * @author guqiong
 * @created 2009-9-21
 * @history
 * 			2009-10-26 guqiong add getProperty
 */
public class SystemConfig {
 	/**
 	 * ϵͳ�����ļ�·��
 	 */
 	private final String default_config = "/kmgis.properties";
	/**
	 * ��ͼ����·�� 
	 */
	private final String gis_map_config = "/gis-map.xml";

	private static Logger log = Logger.getLogger(SystemConfig.class);
	/**
	 * �������Լ���
	 */
	private  Properties config;
	/**
	 * ��ͼ���弯��
	 */
	private  MapDefines mapDefs = null;
	
	/**
	 * ��ͼȨ��, key=��֯��������, value = ��ͼ�������б�
	 */
	private  Map<String, List<String>> allowAccessMaps = new HashMap<String, List<String>>();


	/**
	 * ��֯������Ĭ�ϵ�ͼ��ϵ, key=��֯��������, value = ��ͼ����
	 */
	private  Map<String, MapDef> orgMapMappings = new HashMap<String, MapDef>();

	private static SystemConfig instance ;
	
	private SystemConfig(){
		
	}
	public Map<String, List<String>> getAllowAccessMaps() {
		return allowAccessMaps;
	}

	public void setAllowAccessMaps(Map<String, List<String>> allowAccessMaps) {
		this.allowAccessMaps = allowAccessMaps;
	}
	
	/**
	 * ��ȡSystemConfigʵ��
	 * @return
	 */
	public static SystemConfig getInstance(){
		if(instance == null)
			instance = new SystemConfig();
		return instance;
	}
	/**
	 * ���ص�ͼ����֯������ϵ
	 */
	public void loadOrgMapMappings(List<GisConRegion> gisConRegionList){
		//���ص�ͼ���ĵ�����
		for(GisConRegion gisConRegion: gisConRegionList){
			MapDef mapDef = this.mapDefs.getMapDefById(gisConRegion.getDtpzh());
			if(mapDef!=null){
				mapDef.setX(gisConRegion.getX());
				mapDef.setY(gisConRegion.getY());
				String orgCode = gisConRegion.getZmjgbm();	
				//Ĭ�ϵĵ�ͼ
				if(gisConRegion.getMrbz() && gisConRegion.getYxbz())
					orgMapMappings.put(orgCode, mapDef);
				//������ʵĵ�ͼ
				if(allowAccessMaps.get(orgCode) == null)
					allowAccessMaps.put(orgCode, new ArrayList<String>());
				else
					allowAccessMaps.get(orgCode).add(mapDef.getId());
			}
		}
	}
	
	/**
	 * ��ȡ�����ļ�����ʼ��
	 */
	public void init(){
		config = new Properties();
		try {
			// ����Ĭ�����ò���
			Class<?> config_class = Class
					.forName("com.icss.km.gis.config.SystemConfig");
			InputStream is = config_class.getResourceAsStream(default_config);
			config.load(is);
			// ��ӡ����for debug
			Enumeration<Object> keys = config.keys();
			while (keys.hasMoreElements()) {
				String key = (String) keys.nextElement();
				log.debug(key + "=" + config.getProperty(key));
			}
			
			//���ص�ͼ����
			if (mapDefs == null) {
				try {
					is = SystemConfig.class
							.getResourceAsStream(gis_map_config);
					MapDefsParser parser = new MapDefsParser();
					mapDefs = parser.unmarshall(is);

				} catch (Exception e) {
					log.error("������ͼ�����ļ�ʱ��������:", e);
					log.error(gis_map_config);
				} finally{
					if(is!=null)
						try{
							is.close();
						}catch(Exception e){
							log.error(e);
						}
				}

			}
			

		} catch (Exception e) {
			log.error("ϵͳ����ʱ��ȡ���ò���ʧ�ܣ� ", e);
			log.error("���ò����ļ��� ");
			log.error(default_config);
		}
	}
	
	/**
	 * ����ڲ�����
	 */
	public void clean(){
		if(config!=null){
			config.clear();
			config = null;
		}
		if(mapDefs!=null){
			mapDefs.clean();
			mapDefs = null;
		}
		if(allowAccessMaps != null){
			allowAccessMaps.clear();
			allowAccessMaps = null;
		}
		if(orgMapMappings != null){
			orgMapMappings.clear();
			orgMapMappings = null;
		}
		instance = null;
	}
	 
	 

	/**
	 * ��ȡ��֯����Ĭ��ʹ�õĵ�ͼ
	 * @param orgCode ��֯��������
	 * @return
	 */
	public  MapDef getDefaultMap(String orgCode){
		 return orgMapMappings.get(orgCode);		 
	}
	 
	/**
	 * ��ȡ��ͼ���ͼƬ����
	 * 
	 * @return
	 */
	public  String getMapMime() {
		return config.getProperty("map.mime");
	}

	/**
	 * ��ȡԶ����Ⱦ��������Servlet��ַ
	 * 
	 * @return
	 */
	public  String getMxtURL() {
		return config.getProperty("map.mxtURL");
	}

	/**
	 * ��ȡĬ�ϵĵ�ͼ���ű���
	 * @return
	 */
	public  Double getMapDefaultZoomRatio(){
		return new Double(config.getProperty("map.default_zoom_ratio"));
	}
	
	/**
	 * ��ȡĬ�ϵĵ�ͼ�ƶ�����
	 * @return
	 */
	public  Double getMapDefaultMoveRatio(){
		return new Double(config.getProperty("map.default_move_ratio"));
	}
	
	/**
	 * ��ȡ��ͼ���弯
	 * @return MapDefines
	 */
	public  MapDefines getMapDefines() {
		
		return mapDefs;
	}
	
	/**
	 * ��ȡ��ͼ����
	 * @param refid ��ͼ��������id
	 * @return MapDef
	 */
	public  MapDef getMapDef(String refid){
		return getMapDefines().getMapDef(refid);
	}
	
	/**
	 * ��ȡ����ֵ
	 * @param propName ��������
	 * @return
	 */
	public  String getProperty(String propName){
		return config.getProperty(propName);
	}
	
}
