package com.emq.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * ���ڽ�����ͼ���������ļ�
 * 
 * @author guqiong
 * @created 2009-9-27
 */
public class MapDefsParser {

	/**
	 * ����xml�����ļ�,����MapDefines
	 * 
	 * @param instream
	 * @return MapDefines ��ͼ�����༯��
	 * @throws IOException
	 * @throws JDOMException
	 */
	public MapDefines unmarshall(InputStream instream) throws IOException,
			JDOMException {

		if (instream == null)
			throw new IOException("�������Ϊnull");

		MapDefines configs = new MapDefines();

		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(instream);

		Element root = doc.getRootElement();

		// ������ͼ����
		Iterator mapDefs = root.getChildren("map-def").iterator();
		while (mapDefs.hasNext()) {
			Element e = (Element) mapDefs.next();
			configs.addMapDef(this.elementToMapDef(e));
		}
		// ������ͼ��������
		Iterator mapDefRefs = root.getChildren("map-def-ref").iterator();
		while (mapDefRefs.hasNext()) {
			Element e = (Element) mapDefRefs.next();
			configs.addMapDefRef(this.elementToMapDefRef(e));
		}

		// ����ģ�嶨��
		Iterator layerTemplateDefs = root.getChildren("layer-template")
				.iterator();
		while (layerTemplateDefs.hasNext()) {
			Element e = (Element) layerTemplateDefs.next();
			LayerTemplateDef layerTemplateDef = new LayerTemplateDef();
			layerTemplateDef.setId(e.getAttributeValue("id"));
			layerTemplateDef.setDefineFile(e.getText());
			configs.addLayerTemplateDef(layerTemplateDef);
		}

		return configs;
	}

	/**
	 * ��xmlԪ�ؽ���ΪMapDef
	 * 
	 * @param element
	 * @return
	 */
	private MapDef elementToMapDef(Element element) {
		MapDef mapDef = new MapDef();
		mapDef.setId(element.getAttributeValue("id"));
		mapDef.setClazz(element.getAttributeValue("class"));
		mapDef.setParentId(element.getAttributeValue("extends"));
		String value = element.getAttributeValue("abstract");
		if (value != null && "true".equals(value))
			mapDef.setAbstractDef(true);
		else
			mapDef.setAbstractDef(false);

		mapDef.setMainMapDefFile(element.getChildText("main-map-def-file"));
		
		
		value = element.getChildText("main-map-zoom");
		if (value !=null && !"".equals(value))  
			mapDef.setMainMapZoom(new Double(value));
		// TODO:����ȫ������ 

		value = element.getChildText("main-map-img-hight");
		if (value !=null && !"".equals(value))  
			mapDef.setMainMapImgHight(new Integer(value));
		 

		value = element.getChildText("main-map-img-width");
		if (value !=null && !"".equals(value)) 
			mapDef.setMainMapImgWidth(new Integer(value));
	

		mapDef.setPreviewMapDefFile(element
				.getChildText("preview-map-def-file"));

		value = element.getChildText("preview-map-zoom");
		if (value !=null && !"".equals(value)) 
			mapDef.setPreviewMapZoom(new Double(value));
	 

		value = element.getChildText("preview-map-img-hight");
		if (value !=null && !"".equals(value))
			mapDef.setPreviewMapImgHight(new Integer(value));
	

		value = element.getChildText("preview-map-img-width");
		if (value !=null && !"".equals(value))
			mapDef.setPreviewMapImgWidth(new Integer(value));
	

		value = element.getChildText("describe");
		if (value !=null && !"".equals(value))
			mapDef.setDescribe(value);
	

		List e = element.getChildren("spatial-search-layer");
		List spatialSearchLayerDefineList = new ArrayList();
		for(int i=0;i<e.size();i++){
			spatialSearchLayerDefineList.add(elementToSpatialSearchLayerDefine((Element)e.get(i)));
		}
		mapDef.setSpatialSearchLayerDefineList(spatialSearchLayerDefineList);
//		Element e = element.getChild("spatial-search-layer");
//		mapDef
//				.setSpatialSearchLayerDefine(elementToSpatialSearchLayerDefine(e));
		
		List<Element> slElements = element.getChildren("static-layer");
		Map<String, StaticLayerDefine> staticLayerDefs = null;
		if(slElements != null && slElements.size()>0){
			staticLayerDefs = new HashMap<String, StaticLayerDefine>();
			for(int i=0; i< slElements.size(); i++){
				StaticLayerDefine def = elementToStaticLayerDefine(slElements.get(i));
				staticLayerDefs.put(def.getId(), def);
			}
		}
		
		mapDef.setStaticLayerDefines(staticLayerDefs);
		return mapDef;
	}

	/**
	 * ��xmlԪ�ؽ���ΪMapDefRef
	 * 
	 * @param element
	 * @see gis-map
	 * @return SpatialSearchLayerDefine
	 */
	private MapDefRef elementToMapDefRef(Element element) {
		MapDefRef mapDefRef = new MapDefRef();
		mapDefRef.setId(element.getAttributeValue("id"));
		mapDefRef.setDefId(element.getAttributeValue("ref"));
		return mapDefRef;
	}

	/**
	 * ��xmlԪ�ؽ���ΪSpatialSearchLayerDefine
	 * 
	 * @param element
	 * @see gis-map
	 * @return SpatialSearchLayerDefine
	 */
	private SpatialSearchLayerDefine elementToSpatialSearchLayerDefine(
			Element element) {
		if (element == null)
			return null;
		SpatialSearchLayerDefine define = new SpatialSearchLayerDefine();
		define.setTemplate(element.getAttributeValue("template"));
		define.setTableName(element.getAttributeValue("table-name"));
		define.setX(element.getAttributeValue("x"));
		define.setY(element.getAttributeValue("y"));
		define.setPk(element.getAttributeValue("pk").split(","));
		define.setLabel(element.getAttributeValue("label"));
		return define;
	}
	
	/**
	 * ��xmlԪ�ؽ���ΪSpatialSearchLayerDefine
	 * 
	 * @param element
	 * @see gis-map
	 * @return SpatialSearchLayerDefine
	 */
	private StaticLayerDefine elementToStaticLayerDefine(
			Element element) {
		if (element == null)
			return null;
		StaticLayerDefine define = new StaticLayerDefine();
		define.setId(element.getAttributeValue("id"));
		define.setTemplate(element.getAttributeValue("template"));
		define.setTableName(element.getAttributeValue("table-name"));
		define.setX(element.getAttributeValue("x"));
		define.setY(element.getAttributeValue("y"));
		define.setPk(element.getAttributeValue("pk").split(","));
		define.setLabel(element.getAttributeValue("label"));
		return define;
	}
}
