package com.emq.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.emq.config.LayerTemplateDef;
import com.emq.config.MapDef;
import com.emq.config.SpatialSearchLayerDefine;
import com.emq.config.StaticLayerDefine;
import com.emq.config.SystemConfig;
import com.emq.exception.ErrorMsgConstants;
import com.emq.exception.GISException;
import com.emq.logger.Logger;
import com.mapinfo.coordsys.CoordSys;
import com.mapinfo.dp.AttOperator;
import com.mapinfo.dp.AttTuple;
import com.mapinfo.dp.Attribute;
import com.mapinfo.dp.DataProviderHelper;
import com.mapinfo.dp.DataProviderRef;
import com.mapinfo.dp.Feature;
import com.mapinfo.dp.FeatureSet;
import com.mapinfo.dp.PassThroughQueryException;
import com.mapinfo.dp.PrimaryKey;
import com.mapinfo.dp.QueryParams;
import com.mapinfo.dp.RenditionType;
import com.mapinfo.dp.SearchType;
import com.mapinfo.dp.TableDescHelper;
import com.mapinfo.dp.jdbc.QueryBuilder;
import com.mapinfo.dp.jdbc.xy.XYDataProviderHelper;
import com.mapinfo.dp.jdbc.xy.XYTableDescHelper;
import com.mapinfo.dp.util.RewindableFeatureSet;
import com.mapinfo.labeltheme.LabelTheme;
import com.mapinfo.mapj.FeatureLayer;
import com.mapinfo.mapj.MapJ;
import com.mapinfo.mapxtreme.client.MapXtremeDataProviderRef;
import com.mapinfo.theme.OverrideTheme;
import com.mapinfo.theme.Theme;
import com.mapinfo.util.DoublePoint;
import com.mapinfo.util.DoubleRect;
import com.mapinfo.util.VisibilityConstraints;

/**
 * ֧�ֲ�ѯ�ĵ�ͼ�������ṩ�Ե�ͼ��ѯ�Ķ��ַ�����
 * 
 * @author guqiong
 * @created 2009-10-22
 */
public abstract class SearchableGISMap extends AbstractGISMap {
	private static Logger log = Logger.getLogger(SearchableGISMap.class);

	/**
	 * ���ڿռ�������ѯ����ʾ
	 */
	List spatialSearchLayer = null;
	//protected FeatureLayer spatialSearchLayer = null;
	/**
	 * �������������ѯ������
	 */
	List conditionSearchLayer = null;
	//protected FeatureLayer conditionSearchLayer = null;
	/**
	 * ��ѯͼ���Ƿ��Ѽ���
	 */
	private boolean layerLoaded = false;
	/**
	 * �ռ��ѯͼ������
	 */
	private final String SPATIAL_SEARCH_LAYER_NAME = "SPATIAL_SEARCH_POI";
	/**
	 * ���������ѯ��ѯͼ������
	 */
	private final String CONDITION_SEARCH_LAYER_NAME = "CONDITION_SEARCH_POI";
	/**
	 * ��ѯ�����������
	 */
	private final String SEARCH_THEME_NAME = "SEARCH_THEME";	
	/**
	 * ����ѡ����������
	 */
	private final String PK_SELELCT_THEME_NAME = "PK_SELECT_THEME";

	/**
	 * �����ͼ����
	 * 
	 * @param def
	 *            ��ͼ����
	 */
	public SearchableGISMap(MapDef def) {
		super(def);
	}

	public SearchableGISMap() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.icss.km.gis.model.AbstractGISMap#loadMap()
	 */
	public synchronized void loadMap() throws GISException {
		super.loadMap();
		if (!this.layerLoaded) {
			try {
				conditionSearchLayer = this.creatSqlLayer(CONDITION_SEARCH_LAYER_NAME);
				spatialSearchLayer = this.createTableLayer(SPATIAL_SEARCH_LAYER_NAME);
				createStaticLayers();
				copyTemlateTheme();	
				layerLoaded = true;
			} catch (Exception e) {
				log.error("���ز�ѯͼ��ʧ�ܣ�");
				throw new GISException(e);
			}
		}else{
			try {
				FeatureLayer layer = (FeatureLayer) bindedMap.getLayers().get(CONDITION_SEARCH_LAYER_NAME);
				if(layer == null){
					conditionSearchLayer = this.creatSqlLayer(CONDITION_SEARCH_LAYER_NAME); 
				}
				layer = (FeatureLayer) bindedMap.getLayers().get(SPATIAL_SEARCH_LAYER_NAME);
				if(layer == null){
					spatialSearchLayer = this.createTableLayer(SPATIAL_SEARCH_LAYER_NAME);
				}
				copyTemlateTheme();	
				createStaticLayers();
			}catch(Exception e){
				log.error("���¼��ز�ѯͼ��ʧ�ܣ�");
				throw new GISException(e);
			}
		}
	}

	/**
	 * ����ģ�����ʽ
	 * @throws IOException
	 * @throws Exception
	 */
	private void copyTemlateTheme() throws IOException, Exception {
		List spatialSearchLayerDefineList = this.getMapDef().getSpatialSearchLayerDefineList();
		for(int i=0;i<spatialSearchLayerDefineList.size();i++){
			SpatialSearchLayerDefine def = (SpatialSearchLayerDefine)spatialSearchLayerDefineList.get(i);
			if(def != null){
				MapJ template = new MapJ();
				LayerTemplateDef layerTemplateDef = SystemConfig.getInstance().getMapDefines().getLayerTemplateDef(def.getTemplate());
				template.loadMapDefinition(layerTemplateDef.getDefineFile());
				FeatureLayer templateLayer = (FeatureLayer) template.getLayers().get(def.getTemplate());
				copyTheme((FeatureLayer)this.spatialSearchLayer.get(i), templateLayer);
				copyTheme((FeatureLayer)this.conditionSearchLayer.get(i), templateLayer);
				copyVisibility((FeatureLayer)this.spatialSearchLayer.get(i), templateLayer);
			}
		}
//		SpatialSearchLayerDefine def = this.getMapDef().getSpatialSearchLayerDefine();
//		if(def != null){
//			MapJ template = new MapJ();
//			LayerTemplateDef layerTemplateDef = SystemConfig.getInstance().getMapDefines().getLayerTemplateDef(def.getTemplate());
//			template.loadMapDefinition(layerTemplateDef.getDefineFile());
//			FeatureLayer templateLayer = (FeatureLayer) template.getLayers().get(def.getTemplate());
//		 	copyTheme(this.spatialSearchLayer, templateLayer);
//		 	copyTheme(this.conditionSearchLayer, templateLayer); 
//		 	copyVisibility(this.spatialSearchLayer, templateLayer);
//		}
	}
	/**
	 * ����ģ��Ŀ�����
	 * @param dest Ŀ��ͼ��
	 * @param templateLayer ģ��ͼ��
	 * @throws Exception
	 */
	private void copyVisibility(FeatureLayer dest, FeatureLayer templateLayer)throws Exception{
		dest.setVisibilityConstraints(templateLayer.getVisibilityConstraints());
	}
	
	
	/**
	 * ����ģ�����ʽ
	 * @param dest Ŀ��ͼ��
	 * @param templateLayer ģ��ͼ��
	 * @throws Exception
	 */
	private void copyTheme(FeatureLayer dest, FeatureLayer templateLayer) throws Exception {
		// ��ע��ʽ
		Iterator labelThemes = templateLayer.getLabelThemeList().iterator();
		dest.getLabelThemeList().removeAll(); 
		while (labelThemes.hasNext()) {
			dest.getLabelThemeList().add(
					(LabelTheme) labelThemes.next()); 
		}
		// ͼԪ��ʽ
		Iterator themes = templateLayer.getThemeList().iterator();
		dest.getThemeList().removeAll(); 
		while (themes.hasNext()) {
			Theme theme = (Theme) themes.next();
			if (theme instanceof OverrideTheme) {
				dest.getThemeList().add(theme); 
			}
		} 
	}

	/**
	 * ��ȡ��Ҫ���в�ѯ��ͼ��,���ڵ㡢���Ρ������ѡ��ȿռ��ѯ
	 * 
	 * @return
	 */
	protected List getSpatialSearchLayer() {
		return this.spatialSearchLayer;
	}

	/**
	 * ��ȡ��Ҫ���в�ѯ��ͼ��,����ʹ��QueryBuilder�Ĳ�ѯ
	 * 
	 * @return
	 */
	protected List getQuerySearchLayer() {
		return this.conditionSearchLayer;
	}

	/**
	 * ��ȡ��Ҫ��ѯ��ͼ����������,��֧�ֵ�ֵ����
	 * 
	 * @deprecated 2009-10-19 ͼ���������ƽ���ͼ���ȡ;
	 * @return
	 */
	protected String getSpetialSearchLayerPKName() {
		return "XKZHM";
	}

	/**
	 * ��ͼ��������������ѯ
	 * 
	 * @param ConditionObject
	 * @return ����������ͼԪ����
	 * @throws GISException
	 */
	public abstract List searchByCondition(Object conditionObject,String tableName)
			throws GISException;

	/**
	 * �����ͼ��ʼ��������Ķ������ѯ���theme
	 * 
	 * @throws GISException
	 */
	protected void clean() throws GISException {
		for(int i=0;i<this.getSpatialSearchLayer().size();i++){
			super.removeTheme((FeatureLayer)this.getSpatialSearchLayer().get(i), SEARCH_THEME_NAME);
			super.removeTheme((FeatureLayer)this.getSpatialSearchLayer().get(i), PK_SELELCT_THEME_NAME);
		}
	}

	/**
	 * ����xy�����ͼ��,���ڱ�
	 * 
	 * @return
	 */
	protected List createTableLayer(String name) throws Exception {
		List layerList = new ArrayList();
		List spatialSearchLayerDefineList = this.getMapDef().getSpatialSearchLayerDefineList();
		for(int i=0;i<spatialSearchLayerDefineList.size();i++){
			DataProviderHelper dataProviderHelper = getDataProviderHelper();

			SpatialSearchLayerDefine spatialSearchLayerDefine = (SpatialSearchLayerDefine)spatialSearchLayerDefineList.get(i);
			String[] idColumn = spatialSearchLayerDefine.getPk();
			String x = spatialSearchLayerDefine.getX();
			String y = spatialSearchLayerDefine.getY();
			String table = spatialSearchLayerDefine.getTableName();

			TableDescHelper tableDescHelper1 = new XYTableDescHelper(table,
					SystemConfig.getInstance().getProperty("jdbc.owner"), false, x, y, null,
					RenditionType.none, null, RenditionType.none, idColumn,
					CoordSys.longLatWGS84);

			DataProviderRef dataProviderRef = new MapXtremeDataProviderRef(
					dataProviderHelper, SystemConfig.getInstance().getMxtURL());

			FeatureLayer layer = (FeatureLayer) bindedMap.getLayers().insertLayer(
					dataProviderRef, tableDescHelper1, 0, name);
			layerList.add(layer);
		}
		return layerList;
	}
//	protected FeatureLayer createTableLayer(String name) throws Exception {
//		DataProviderHelper dataProviderHelper = getDataProviderHelper();
//
//		SpatialSearchLayerDefine spatialSearchLayerDefine = this.getMapDef()
//				.getSpatialSearchLayerDefine();
//		String[] idColumn = spatialSearchLayerDefine.getPk();
//		String x = spatialSearchLayerDefine.getX();
//		String y = spatialSearchLayerDefine.getY();
//		String table = spatialSearchLayerDefine.getTableName();
//
//		TableDescHelper tableDescHelper1 = new XYTableDescHelper(table,
//				SystemConfig.getInstance().getProperty("jdbc.owner"), false, x, y, null,
//				RenditionType.none, null, RenditionType.none, idColumn,
//				CoordSys.longLatWGS84);
//
//		DataProviderRef dataProviderRef = new MapXtremeDataProviderRef(
//				dataProviderHelper, SystemConfig.getInstance().getMxtURL());
//
//		FeatureLayer layer = (FeatureLayer) bindedMap.getLayers().insertLayer(
//				dataProviderRef, tableDescHelper1, 0, name);
//
//		return layer;
//	}
	
	/**
	 * ������̬��ʾͼ��
	 * @return
	 */
	protected void createStaticLayers() throws Exception {
		Map<String,StaticLayerDefine> staticLayerDefines = this.getMapDef().getStaticLayerDefines();
		if(staticLayerDefines == null)
			return;
		for(StaticLayerDefine def: staticLayerDefines.values()){
			createStaticLayer(def);
		}
	}
	
	/**
	 * ����xy����ľ�̬��ʾͼ��,���ڱ�
	 * @param id ͼ��id
	 * @return
	 */
	protected FeatureLayer createStaticLayer(StaticLayerDefine staticLayerDefine) throws Exception {
		DataProviderHelper dataProviderHelper = getDataProviderHelper();
 
		String[] idColumn = staticLayerDefine.getPk();
		String x = staticLayerDefine.getX();
		String y = staticLayerDefine.getY();
		String table = staticLayerDefine.getTableName();
		String label = staticLayerDefine.getLabel();
		TableDescHelper tableDescHelper1 = new XYTableDescHelper(table,
				SystemConfig.getInstance().getProperty("jdbc.owner"), false, x, y, null,
				RenditionType.none, null, RenditionType.none, idColumn,
				CoordSys.longLatWGS84);

		DataProviderRef dataProviderRef = new MapXtremeDataProviderRef(
				dataProviderHelper, SystemConfig.getInstance().getMxtURL());

		FeatureLayer layer = (FeatureLayer) bindedMap.getLayers().insertLayer(
				dataProviderRef, tableDescHelper1, 2, staticLayerDefine.getId());
		
		LayerTemplateDef layerTemplateDef = SystemConfig.getInstance().getMapDefines().getLayerTemplateDef(staticLayerDefine.getTemplate());
		MapJ template = new MapJ();
		template.loadMapDefinition(layerTemplateDef.getDefineFile());
		FeatureLayer templateLayer = (FeatureLayer) template.getLayers().get(staticLayerDefine.getTemplate());
	 	copyTheme(layer, templateLayer); 
	 	copyVisibility(layer, templateLayer);
	 	layer.getLabelProperties().setLabelExpression(label);
		layer.setAutoLabel(true);
		return layer;
	}

	/**
	 * ����xy�����ͼ�㣬����sql��ѯ
	 * 
	 * @return
	 */
	protected List creatSqlLayer(String name) throws Exception {
		List layerList = new ArrayList();
		List spatialSearchLayerDefineList = this.getMapDef().getSpatialSearchLayerDefineList();
		for(int i=0;i<spatialSearchLayerDefineList.size();i++){
			DataProviderHelper dataProviderHelper = getDataProviderHelper();

			SpatialSearchLayerDefine spatialSearchLayerDefine = (SpatialSearchLayerDefine)spatialSearchLayerDefineList.get(i);
			String[] idColumn = spatialSearchLayerDefine.getPk();
			String x = spatialSearchLayerDefine.getX();
			String y = spatialSearchLayerDefine.getY();
			String table = spatialSearchLayerDefine.getTableName(); 
			String sql = "select * from " + table + " where 1=0 ";

			TableDescHelper tableDescHelper2 = new XYTableDescHelper(sql, idColumn,
					x, y, null, RenditionType.none, null, RenditionType.none,
					CoordSys.longLatWGS84);

			DataProviderRef dataProviderRef = new MapXtremeDataProviderRef(
					dataProviderHelper, SystemConfig.getInstance().getMxtURL());

			FeatureLayer layer = (FeatureLayer) bindedMap.getLayers().insertLayer(
					dataProviderRef, tableDescHelper2, 0, name);
			// ���ص�ͼ��
			VisibilityConstraints visibility = new VisibilityConstraints();
			visibility.setEnabled(false);
			layer.setVisibilityConstraints(visibility);
			layerList.add(layer);
		}
		return layerList;
	}
//	protected FeatureLayer creatSqlLayer(String name) throws Exception {
//		DataProviderHelper dataProviderHelper = getDataProviderHelper();
//
//		SpatialSearchLayerDefine spatialSearchLayerDefine = this.getMapDef()
//				.getSpatialSearchLayerDefine();
//		String[] idColumn = spatialSearchLayerDefine.getPk();
//		String x = spatialSearchLayerDefine.getX();
//		String y = spatialSearchLayerDefine.getY();
//		String table = spatialSearchLayerDefine.getTableName(); 
//		String sql = "select * from " + table + " where 1=0 ";
//
//		TableDescHelper tableDescHelper2 = new XYTableDescHelper(sql, idColumn,
//				x, y, null, RenditionType.none, null, RenditionType.none,
//				CoordSys.longLatWGS84);
//
//		DataProviderRef dataProviderRef = new MapXtremeDataProviderRef(
//				dataProviderHelper, SystemConfig.getInstance().getMxtURL());
//
//		FeatureLayer layer = (FeatureLayer) bindedMap.getLayers().insertLayer(
//				dataProviderRef, tableDescHelper2, 0, name);
//		// ���ص�ͼ��
//		VisibilityConstraints visibility = new VisibilityConstraints();
//		visibility.setEnabled(false);
//		layer.setVisibilityConstraints(visibility);
//		return layer;
//	}

	/**
	 * ����DataProviderHelper
	 * @return
	 */
	protected DataProviderHelper getDataProviderHelper() {
	//	String jdbcDriver = SystemConfig.getInstance().getProperty("jdbc.driver");
	//	String jdbcURL = SystemConfig.getInstance().getProperty("jdbc.url");
	//	String userName = SystemConfig.getInstance().getProperty("jdbc.username");
	//	String password = SystemConfig.getInstance().getProperty("jdbc.password");
	//	DataProviderHelper dataProviderHelper = new XYDataProviderHelper(
	//			jdbcDriver, jdbcURL, userName, password);
		DataProviderHelper dataProviderHelper = new XYDataProviderHelper(
				"jdbc:mipool:kmgiss", null, null);
	 
		return dataProviderHelper;
	}

	/**
	 * ���η�Χ��ѯ����������ʾͼԪ
	 * 
	 * @param rectangle
	 *            ���η�Χ ��Ļ����ϵ
	 */
	public void searchAndHighlight(DoubleRect rect) throws GISException {
		FeatureSet featureSet = null;
		try {
			//FeatureLayer layer = this.getSpatialSearchLayer();
			List layer = this.getSpatialSearchLayer();
			for(int i=0;i<layer.size();i++){
				featureSet = searchByRect((FeatureLayer)layer.get(i), rect);
				removeTheme((FeatureLayer)layer.get(i), PK_SELELCT_THEME_NAME);
				createThemeBySelect(SEARCH_THEME_NAME, featureSet, (FeatureLayer)layer.get(i), getHighlightRend());
			}

		} catch (Exception e) {
			log.error("��ѯͼ��ʱ���ִ���:", e);
			throw new GISException(e);
		} finally {
			if (featureSet != null) {
				try {
					featureSet.dispose();
				} catch (Exception e) {
					log.error(e);
				}
			}
		}
	}

	/**
	 * ����η�Χ��ѯ����������ʾͼԪ
	 * 
	 * @param regionPoints
	 *            ����ζ��� ��Ļ����ϵ
	 */
	public void searchAndHighlight(List<DoublePoint> regionPoints)
			throws GISException {
		FeatureSet featureSet = null;
		try {
			List layer = this.getSpatialSearchLayer();
			for(int i=0;i<layer.size();i++){
				featureSet = this.searchByRegion((FeatureLayer)layer.get(i), regionPoints);
				removeTheme((FeatureLayer)layer.get(i), PK_SELELCT_THEME_NAME);
				createThemeBySelect(SEARCH_THEME_NAME, featureSet, (FeatureLayer)layer.get(i), getHighlightRend());
			}
//			FeatureLayer layer = this.getSpatialSearchLayer();
//			featureSet = this.searchByRegion(layer, regionPoints);
//			removeTheme(layer, PK_SELELCT_THEME_NAME);
//			createThemeBySelect(SEARCH_THEME_NAME, featureSet, layer, getHighlightRend());

		} catch (Exception e) {
			log.error("��ѯͼ��ʱ���ִ���:", e);
			throw new GISException(e);
		} finally {
			if (featureSet != null) {
				try {
					featureSet.dispose();
				} catch (Exception e) {
					log.error(e);
				}
			}
		}
	}

	/**
	 *����ͼԪ������ѯ����������ʾͼԪ, ��ͼ��λ֮��ѯ�����
	 * 
	 * @deprecated 2009-10-19 ��searchAndHighlight(String[] pk)���
	 * @param pk
	 *            ͼԪ����
	 */
	public void searchAndHighlight(String pk) throws GISException {
		String[] arrPk = new String[] { pk };
		searchAndHighlight(arrPk);
	}

	/**
	 *����ͼԪ������ѯ����������ʾͼԪ, ��ͼ��λ֮��ѯ�����
	 * 
	 * @param pk
	 *            ͼԪ����
	 */
	public void searchAndHighlight(String[] pk) throws GISException {
		FeatureSet featureSet = null;
		try {
			List layer = this.getSpatialSearchLayer();
			for(int i=0;i<layer.size();i++){
				featureSet = this.searchByPk((FeatureLayer)layer.get(i), pk);
				createThemeBySelect(PK_SELELCT_THEME_NAME, featureSet, (FeatureLayer)layer.get(i), this.getSecondHighlightRend());
				featureSet.rewind();
				Feature firstCigStore = featureSet.getNextFeature();
				if (firstCigStore != null) {
					this.locationBy(firstCigStore);
				} else {
					log.error("��ѯ�����ڵ�ͼ������: ");
					log.error(pk);
				}
			}
//			FeatureLayer layer = this.getSpatialSearchLayer();
//			featureSet = searchByPk(layer, pk);
//			createThemeBySelect(PK_SELELCT_THEME_NAME, featureSet, layer, this.getSecondHighlightRend());
//			featureSet.rewind();
//			Feature firstCigStore = featureSet.getNextFeature();
//			if (firstCigStore != null) {
//				this.locationBy(firstCigStore);
//			} else {
//				log.error("��ѯ�����ڵ�ͼ������: ");
//				log.error(pk);
//			}
		} catch (Exception e) {
			log.error("��ѯͼ��ʱ���ִ���:", e);
			throw new GISException(e);
		} finally {
			if (featureSet != null) {
				try {
					featureSet.dispose();
				} catch (Exception e) {
					log.error(e);
				}
			}
		}
	}

	/**
	 * ���ѯ����������ʾͼԪ
	 * 
	 * @param point
	 *            ������ ��Ļ����ϵ
	 */
	public void searchAndHighlight(DoublePoint p) throws GISException {
		FeatureSet featureSet = null;
		try {
			List layer = this.getSpatialSearchLayer();
			for(int i=0;i<layer.size();i++){
				featureSet = this.searchByPoint((FeatureLayer)layer.get(i), p);
				removeTheme((FeatureLayer)layer.get(i), PK_SELELCT_THEME_NAME);
				createThemeBySelect(SEARCH_THEME_NAME, featureSet, (FeatureLayer)layer.get(i), getHighlightRend());
			}
//			FeatureLayer layer = getSpatialSearchLayer();
//			featureSet = searchByPoint(layer, p);
//			removeTheme(layer, PK_SELELCT_THEME_NAME);
//			createTheme(SEARCH_THEME_NAME, featureSet, layer, getHighlightRend());
		} catch (Exception e) {
			log.error("��ѯͼ��ʱ���ִ���:", e);
			throw new GISException(e);
		} finally {
			if (featureSet != null) {
				try {
					featureSet.dispose();
				} catch (Exception e) {
					log.error(e);
				}
			}
		}
	}

	/**
	 * �뾶��Χ��ѯ��������
	 * 
	 * @param p
	 *            Բ�ĵ����꣬��Ļ����ϵ
	 * @param radius
	 *            �뾶����Ļ����ϵ
	 * @throws GISException
	 */
	public void searchAndHighlight(DoublePoint p, double radius)
			throws GISException {
		FeatureSet featureSet = null;
		try {
			List layer = this.getSpatialSearchLayer();
			for(int i=0;i<layer.size();i++){
				featureSet = this.searchByRadius((FeatureLayer)layer.get(i), p, radius);
				removeTheme((FeatureLayer)layer.get(i), PK_SELELCT_THEME_NAME);
				createThemeBySelect(SEARCH_THEME_NAME, featureSet, (FeatureLayer)layer.get(i), getHighlightRend());
			}
//			FeatureLayer layer = getSpatialSearchLayer();
//			featureSet = this.searchByRadius(layer, p, radius);
//			removeTheme(layer, PK_SELELCT_THEME_NAME);
//			createThemeBySelect(SEARCH_THEME_NAME, featureSet, layer, getHighlightRend());
		} catch (Exception e) {
			log.error("��ѯͼ��ʱ���ִ���:", e);
			throw new GISException(e);
		} finally {
			if (featureSet != null) {
				try {
					featureSet.dispose();
				} catch (Exception e) {
					log.error(e);
				}
			}
		}
	}

	/**
	 * ������ѯ����������ʾͼԪ,ͬʱ����ͼ���Ķ�λ������ĵ�һ��ͼԪ
	 * 
	 * @param conditionObject
	 *            ��������
	 */
	public void searchAndHighlight(Object conditionObject,String tableName) throws GISException {
		List featureSet = null;
		try {
			//���Ĳ�ѯ���������ʹ�õ�ͼ��
			List layer = getSpatialSearchLayer();
			for(int i=0;i<layer.size();i++){
				featureSet = this.searchByCondition(conditionObject,tableName);
				for(int j=0;j<featureSet.size();j++){
					Feature first = ((FeatureSet)featureSet.get(j)).getNextFeature();
					if (first != null) {
						locationBy(first);
						((FeatureSet)featureSet.get(j)).rewind();
					}
					removeTheme((FeatureLayer)layer.get(i), PK_SELELCT_THEME_NAME);
					double distance = createTheme(SEARCH_THEME_NAME, (FeatureSet)featureSet.get(j), (FeatureLayer)layer.get(i), getHighlightRend());
					getPoiLayer((FeatureLayer)layer.get(i),distance);
				}
				
			}
//			FeatureLayer layer = getSpatialSearchLayer();
//			featureSet = this.searchByCondition(conditionObject);
//			Feature first = featureSet.getNextFeature();
//			if (first != null) {
//				locationBy(first);
//				// �ָ��α굽��ʼλ��
//				featureSet.rewind();
//			}
//			removeTheme(layer, PK_SELELCT_THEME_NAME);
//			double distance = createTheme(SEARCH_THEME_NAME, featureSet, layer, getHighlightRend());
//			getPoiLayer(layer,distance);
		} catch (Exception e) {
			log.error("��ѯͼ��ʱ���ִ���:", e);
			throw new GISException(e);
		} finally {
			if (featureSet != null) {
				try {
					for(int i=0;i<featureSet.size();i++){
						((FeatureSet)featureSet.get(i)).dispose();
					}
					
				} catch (Exception e) {
					log.error(e);
				}
			}

		}
	}
	
	/**
	 * ��ͼ���в�ѯָ������ֵ��ͼԪ
	 * 
	 * @deprecated 2009-10-19 ʹ��FeatureSet searchByPk(FeatureLayer layer,
	 *             String[] pkColumns, String[] pk)����
	 * @param layer
	 *            ͼ��
	 * @param pkColumn
	 *            ������
	 * @param pk
	 *            ����ֵ
	 * @return FeatureSet ͼԪ����
	 * @throws GISException
	 */
	protected FeatureSet searchByPk(FeatureLayer layer, String pkColumn,
			String pk) throws GISException {
  		return this.searchByPk(layer, new String[]{pk});
 	}

	/**
	 * ��ͼ���в�ѯָ������ֵ��ͼԪ
	 * 
	 * @param layer
	 *            ͼ��
	 * @param pk
	 *            ����ֵ,λ��˳����ͼ�㶨��ʱ��˳��
	 * @return FeatureSet ͼԪ����
	 * @throws GISException
	 */
	protected FeatureSet searchByPk(FeatureLayer layer, String[] pk)
			throws GISException {
		try {
			List<String> columns = getAllColumns(layer);
			List pkNames = new ArrayList();
			List pkValues = new ArrayList();
			List operators = new ArrayList();
			PrimaryKey layerPk = this.getPk(layer);
			int cols = layerPk.getAttributeCount();
			if (cols != pk.length) {
				throw new GISException(ErrorMsgConstants.EMQ_MAP_05);
			}
			for (int i = 0; i < cols; i++) {
				pkNames.add(layerPk.getAttribute(i).getString());
				pkValues.add(new AttTuple(new Attribute(pk[i])));
				operators.add(AttOperator.eq);
			}
			FeatureSet result = layer.searchByAttributes(columns, pkNames,
					operators, pkValues, QueryParams.ALL_PARAMS);
			return afterSearch(layer, new RewindableFeatureSet(result));
		} catch (PassThroughQueryException e) {
			throw new GISException(e);
		} catch (Exception e) {
			throw new GISException(e);
		}

	}

	/**
	 * ��ͼ���в�ѯָ�����ͼԪ
	 * 
	 * @param layer
	 *            ͼ��
	 * @param point
	 *            ָ���ĵ� ��Ļ����ϵ
	 * @return FeatureSet ͼԪ����
	 * @throws GISException
	 */
	protected FeatureSet searchByPoint(FeatureLayer layer, DoublePoint point)
			throws GISException {
		try {
			List<String> columns = getAllColumns(layer);
			FeatureSet result = layer.searchAtPoint(columns, bindedMap
					.transformScreenToNumeric(point), QueryParams.ALL_PARAMS);
			return afterSearch(layer, new RewindableFeatureSet(result));
		} catch (PassThroughQueryException e) {
			throw new GISException(e);
		} catch (Exception e) {
			throw new GISException(e);
		}
	}

	/**
	 * ��ѯָ����뾶��Χ�ڵ�ͼԪ
	 * 
	 * @param point
	 *            ָ���ĵ㣬��Ļ����ϵ
	 * @param radius
	 *            �뾶����Ļ����ϵ
	 * @return
	 * @throws GISException
	 */
	public List searchByRadius(DoublePoint point, double radius)
			throws GISException {
		List featureSetList = new ArrayList();
		for(int i=0;i<this.getSpatialSearchLayer().size();i++){
			featureSetList.add(this.searchByRadius((FeatureLayer)this.getSpatialSearchLayer().get(i), point, radius));
		}
		return featureSetList;
	}

	/**
	 * ��ͼ���в�ѯָ����뾶��Χ�ڵ�ͼԪ
	 * 
	 * @param layer
	 *            ͼ��
	 * @param point
	 *            ָ���ĵ㣬��Ļ����ϵ
	 * @param radius
	 *            �뾶����Ļ����ϵ
	 * @return
	 * @throws GISException
	 */
	protected FeatureSet searchByRadius(FeatureLayer layer, DoublePoint point,
			double radius) throws GISException {
		try {
			List<String> columns = getAllColumns(layer);
			// �����ͼ�����µİ뾶
			DoublePoint tmpPoint = new DoublePoint(point.x + radius, point.y);
			double distance = bindedMap.cartesianDistance(bindedMap
					.transformScreenToNumeric(point), bindedMap
					.transformScreenToNumeric(tmpPoint));

			FeatureSet result = layer.searchWithinRadius(columns, bindedMap
					.transformScreenToNumeric(point), distance, bindedMap
					.getDistanceUnits(), QueryParams.ALL_PARAMS);
			return afterSearch(layer, new RewindableFeatureSet(result));
		} catch (PassThroughQueryException e) {
			throw new GISException(e);
		} catch (Exception e) {
			throw new GISException(e);
		}
	}

	/**
	 * ��ͼ���в�ѯָ�����η�Χ�ڵ�ͼԪ
	 * 
	 * @param layer
	 *            ͼ��
	 * @param rectangle
	 *            ���η�Χ����Ļ����ϵ
	 * @return
	 * @throws GISException
	 */
	protected FeatureSet searchByRect(FeatureLayer layer, DoubleRect rectangle)
			throws GISException {
		try {

			List<String> columns = getAllColumns(layer);

			FeatureSet result = layer.searchWithinRectangle(columns, bindedMap
					.transformScreenToNumeric(rectangle),
					QueryParams.ALL_PARAMS);
			return afterSearch(layer, new RewindableFeatureSet(result));

		} catch (PassThroughQueryException e) {
			throw new GISException(e);
		} catch (Exception e) {
			throw new GISException(e);
		}
	}

	/**
	 * ��ͼ���в�ѯָ������η�Χ�ڵ�ͼԪ
	 * 
	 * @param layer
	 *            ͼ��
	 * @param regionPoints
	 *            ����ζ���,��Ļ����ϵ
	 * @return
	 * @throws GISException
	 */
	protected FeatureSet searchByRegion(FeatureLayer layer,
			List<DoublePoint> regionPoints) throws GISException {
		try {

			List<String> columns = getAllColumns(layer);
			// ������regionPoints�еĵ���Ϣ����gisPoint��˳��Ϊx0,y0,x1,y1
			// ע�⣬��Ҫʹ�ö�ά���鴫��mapx
			double[] gisPoint = new double[regionPoints.size() * 2];
			int index = 0;
			for (int i = 0; i < regionPoints.size(); i++) {
				DoublePoint p = bindedMap.transformScreenToNumeric(regionPoints
						.get(i));
				gisPoint[index] = p.x;
				index++;
				gisPoint[index] = p.y;
				index++;
			}

			if (gisPoint.length < 3) {
				log.error("����ζ�����С��3��");
				return null;
			}

			QueryParams queryParams = new QueryParams(SearchType.entire, true,
					false, true, true, false);
			Feature searchFeature = this.bindedMap
					.getFeatureFactory()
					.createRegion(gisPoint, null, null, null, this.getPk(layer));
			
			FeatureSet result = layer.searchWithinRegion(columns,
					searchFeature.getGeometry(), queryParams);
			return afterSearch(layer, new RewindableFeatureSet(result));

		} catch (PassThroughQueryException e) {
			throw new GISException(e);
		} catch (Exception e) {
			throw new GISException(e);
		}
	}

	/**
	 * ��ͼ���в�ѯָ��������ͼԪ
	 * 
	 * @param layer
	 *            ͼ��
	 * @param queryBuilder
	 *            ��ѯ����
	 * @return
	 * @throws GISException
	 */
	protected FeatureSet searchByQuery(FeatureLayer layer,
			QueryBuilder queryBuilder) throws GISException {
		try {
			List<String> columns = getAllColumns(layer);
			layer.setQueryBuilder(queryBuilder);
			FeatureSet result = layer
					.searchAll(columns, QueryParams.ALL_PARAMS);
			return afterSearch(layer, new RewindableFeatureSet(result));
		} catch (PassThroughQueryException e) {
			throw new GISException(e);
		} catch (Exception e) {
			throw new GISException(e);
		}
	}

	/**
	 * ��ѯָ������ֵ��ͼԪ��Ϣ
	 * 
	 * @deprecated 2009-10-19ʹ��FeatureSet searchById(String[] id)���
	 * @param id
	 *            ͼԪid
	 * @return
	 * @throws GISException
	 */
	public List searchById(String id) throws GISException {
		List featureSetList = new ArrayList();
		for(int i=0;i<this.getSpatialSearchLayer().size();i++){
			featureSetList.add(this.searchById(new String[] { id }));
		}
		return featureSetList;
	}

	/**
	 * ��ѯָ������ֵ��ͼԪ��Ϣ
	 * 
	 * @param id
	 *            ͼԪ����ֵ
	 * @return
	 * @throws GISException
	 */
	public List searchById(String[] id) throws GISException {
		List featureSetList = new ArrayList();
		for(int i=0;i<this.getSpatialSearchLayer().size();i++){
			featureSetList.add(searchByPk((FeatureLayer)this.getSpatialSearchLayer().get(i), id));
		}
		return featureSetList;
	}

	/**
	 * ��ѯָ��������ͼԪ��Ϣ
	 * 
	 * @param point
	 *            �����,��Ļ����ϵ
	 * @return ���������λ�õ�ͼԪ���ϣ����Զ��
	 * @throws GISException
	 */
	public List searchByPoint(DoublePoint point) throws GISException {
		List featureSetList = new ArrayList();
		for(int i=0;i<this.getSpatialSearchLayer().size();i++){
			featureSetList.add(searchByPoint((FeatureLayer)this.getSpatialSearchLayer().get(i), point));
		}
		return featureSetList;
	}

	/**
	 * ��ѯָ�����η�Χ�ڵ�ͼԪ��Ϣ
	 * 
	 * @param rectangle
	 *            ����,��Ļ����ϵ
	 * @return ���η�Χ�ڵ�ͼԪ����
	 * @throws GISException
	 */
	public List searchByRect(DoubleRect rectangle) throws GISException {
		List featureSetList = new ArrayList();
		for(int i=0;i<this.getSpatialSearchLayer().size();i++){
			featureSetList.add(searchByRect((FeatureLayer)this.getSpatialSearchLayer().get(i), rectangle));
		}
		return featureSetList;
	}

	/**
	 * ��ѯָ������η�Χ�ڵ�ͼԪ��Ϣ
	 * 
	 * @param regionPoints
	 *            ����ζ���,��Ļ����ϵ
	 * @return ����η�Χ�ڵ�ͼԪ����
	 * @throws GISException
	 */
	public List searchByRegion(List<DoublePoint> regionPoints)
			throws GISException {
		List featureSetList = new ArrayList();
		for(int i=0;i<this.getSpatialSearchLayer().size();i++){
			featureSetList.add(searchByRegion((FeatureLayer)this.getSpatialSearchLayer().get(i), regionPoints));
		}
		return featureSetList;
	} 
	
	/**
	 * ��ѯǰ�����������า��
	 * @param layer ��ѯͼ��
	 */
	protected void beforeSearch(final FeatureLayer layer) throws GISException {
		
	}
	
	/**
	 * ��ѯ�����������า��
	 * @param layer ��ѯͼ��
	 * @param fs ��ѯ�����
	 */
	protected FeatureSet afterSearch(final FeatureLayer layer, final FeatureSet fs) throws GISException {
		return fs;
	}
	
	/**
	 * �������ż�������Ƿ���ʾ��
	 * 
	 * @param zoom
	 *            ��ͼ���ŵȼ�
	 * @throws GISException
	 */
	public void chageLayerTheme(Double zoom) throws GISException{
		for(int i=0;i<getSpatialSearchLayer().size();i++){
			this.getPoiLayer((FeatureLayer)getSpatialSearchLayer().get(i), zoom);
		}
	}
}
