package com.emq.config;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.emq.logger.Logger;

/**
 * ��ͼ���弯��
 * 
 * @author guqiong
 * @created 2009-9-27
 */
public class MapDefines {

	Logger log = Logger.getLogger(MapDefines.class);
	/**
	 * ������еĵ�ͼ����
	 */
	private Map<String, MapDef> mapDefs = null;
	/**
	 * ������еĵ�ͼ��������
	 */
	private Map<String, MapDefRef> mapDefRefs = null;
	/**
	 * ͼ��ģ�嶨��
	 */
	private Map<String, LayerTemplateDef> layerTemplateDefs = null;

	/**
	 * ����ڲ�����
	 */
	public void clean(){
		if(mapDefs!=null){
			mapDefs.clear();
			mapDefs = null;
		}
		if(mapDefRefs!=null){
			mapDefRefs.clear();
			mapDefRefs = null;
		}
		if(layerTemplateDefs!=null){
			layerTemplateDefs.clear();
			layerTemplateDefs = null;
		}
	}
	
	public MapDefines() {
		mapDefs = new HashMap<String, MapDef>();
		mapDefRefs = new HashMap<String, MapDefRef>();
		layerTemplateDefs = new HashMap<String, LayerTemplateDef>();
	}

	public Collection<MapDefRef> getMapDefRefs() {
		return mapDefRefs.values();
	}

	public Collection<MapDef> getMapDefs() {
		return mapDefs.values();
	}

	public Collection<LayerTemplateDef> getLayerTemplateDefs() {
		return layerTemplateDefs.values();
	}

	/**
	 * �����ͼ����
	 * 
	 * @param mapDef
	 *            ��ͼ����
	 */
	public void addMapDef(MapDef mapDef) {
		String id = mapDef.getId();
		if (mapDefs.containsKey(id)) {
			log.error("�ظ�����ĵ�ͼid��");
			log.error(id);
		}

		this.mapDefs.put(id, mapDef);
	}

	/**
	 * �Ƴ���ͼ����
	 * 
	 * @param mapDef
	 *            ��ͼ����
	 */
	public void removeMapDef(MapDef mapDef) {
		this.mapDefs.remove(mapDef);
	}

	/**
	 * �����ͼ��������
	 * 
	 * @param mapDef
	 *            ��ͼ��������
	 */
	public void addMapDefRef(MapDefRef mapDefRef) {
		String id = mapDefRef.getId();
		if (mapDefRefs.containsKey(id)) {
			log.error("�ظ�����ĵ�ͼ����id��");
			log.error(id);
		}

		this.mapDefRefs.put(id, mapDefRef);
	}

	/**
	 * �Ƴ���ͼ��������
	 * 
	 * @param mapDef
	 *            ��ͼ��������
	 */
	public void removeMapDefRef(MapDefRef mapDefRef) {
		this.mapDefRefs.remove(mapDefRef);
	}
	
	/**
	 * ����ģ�嶨��
	 * 
	 * @param layerTemplateDef
	 *            ͼ��ģ�嶨��
	 */
	public void addLayerTemplateDef(LayerTemplateDef layerTemplateDef) {
		String id = layerTemplateDef.getId();
		if (layerTemplateDefs.containsKey(id)) {
			log.error("�ظ������ͼ��ģ�嶨��id��");
			log.error(id);
		}

		this.layerTemplateDefs.put(id, layerTemplateDef);
	}

	/**
	 * �Ƴ�ͼ��ģ�嶨��
	 * 
	 * @param layerTemplateDef
	 *              ͼ��ģ�嶨��
	 */
	public void removeLayerTemplateDef(LayerTemplateDef layerTemplateDef) {
		this.layerTemplateDefs.remove(layerTemplateDef);
	}


	/**
	 * ��ȡ��ͼ����
	 * 
	 * @param refid
	 *            ��ͼ����id
	 * @return MapDef
	 */
	public MapDef getMapDef(String refid) {
		MapDefRef ref = this.mapDefRefs.get(refid);
		return this.mapDefs.get(ref.getDefId());
	}

	/**
	 * ��ȡ��ͼ����
	 * 
	 * @param id
	 *            ��ͼ����id
	 * @return MapDef
	 */
	public MapDef getMapDefById(String id) {
		return this.mapDefs.get(id);
	}

	/**
	 * ��ȡͼ��ģ�嶨��
	 * 
	 * @param id
	id *            ģ��id
	 * @return
	 */
	public LayerTemplateDef getLayerTemplateDef(String id) {
		return this.layerTemplateDefs.get(id);
	}

}
