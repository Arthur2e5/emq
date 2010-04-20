package com.emq.model;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.emq.config.MapDef;
import com.emq.config.SystemConfig;
import com.emq.exception.ErrorMsgConstants;
import com.emq.exception.GISException;
import com.emq.logger.Logger;
import com.mapinfo.dp.Attribute;
import com.mapinfo.dp.Feature;
import com.mapinfo.dp.FeatureSet;
import com.mapinfo.dp.PointGeometry;
import com.mapinfo.dp.PrimaryKey;
import com.mapinfo.dp.QueryParams;
import com.mapinfo.dp.TableInfo;
import com.mapinfo.graphics.Rendition;
import com.mapinfo.graphics.RenditionImpl;
import com.mapinfo.mapj.FeatureLayer;
import com.mapinfo.mapj.MapJ;
import com.mapinfo.mapj.MissingPrimaryKeyException;
import com.mapinfo.mapj.Selection;
import com.mapinfo.mapxtreme.client.MapXtremeImageRenderer;
import com.mapinfo.theme.OverrideTheme;
import com.mapinfo.theme.SelectionTheme;
import com.mapinfo.theme.Theme;
import com.mapinfo.unit.Distance;
import com.mapinfo.unit.LinearUnit;
import com.mapinfo.util.DoublePoint;
import com.mapinfo.util.DoubleRect;
import com.mapinfo.xmlprot.mxtj.ImageRequestComposer;
import com.mapinfo.xmlprot.mxtj.Rendering;

/**
 * ����ĵ�ͼ����ģ��
 * <p>
 * AbstractGISMap������ͼ�����Ե�ͼ��װ��һ���ṩһ������ͼ������ͼ�ĵ�ͼ������
 * <P>
 * AbstractGISMap�ṩ�����ͼ��ͨ�÷�����������ͼ��صĲ�����������ʵ�֡�
 * <p>
 * AbstractGISMap��ע�����ͼ���޹صĲ������ͼ���ĵ㡢���ż��𡢼��أ�������ģ�巽���� ��������Ҫ
 * ��ѯ������ͼ�㡢���ԡ������ķ�ʽ����Ҫ��������ָ����
 * <P>
 * ���棺�Ե�ͼ�����ĵ����ʱ����ʹ��centerto������centerto���������Ƿ�������ͼ���ĵ�������ͼͬ����
 * 
 * @author guqiong
 * @created 2009-9-21
 * @history 2009-10-14 guqiong ���Ӱ뾶��Χ��ѯ���� ����ͼ��������ͼ���ĵ�ͬ�� 2009-10-15 guqiong fix
 *          bug: KMGIS-20 ����clean����������reset�����н��е��� 2009-10-19 guqiong
 *          ����֧�ֶ�ֵ�����Ĳ�ѯ���� �������õ�ͼĬ�����ĵ����� 2009-10-22 guqiong
 *          �����в�ѯ������������SearchableGISMap
 *          2009-12-25 liuyt����createThemeBySelect��������ѡ���ѯ������ԭ����createTheme��ͬʱ�޸�createTheme�����ѯ������ͼ������
 *          �÷��������Զ��������ű���
 */
public abstract class AbstractGISMap {
	private static Logger log = Logger.getLogger(AbstractGISMap.class);

	/**
	 * ��ͼ����id = ��ͼ����id
	 */
	private String id;
	/**
	 * �󶨵�����ͼ
	 */
	protected MapJ bindedMap = null;
	/**
	 * �󶨵����Ե�ͼ
	 */
	protected MapJ bindedPreviewMap = null;
	/**
	 * ��ͼ�Ƿ��Ѽ���
	 */
	private boolean isMapLoaded = false;
	/**
	 * ��ʼ����ͼ����,��������ϵ
	 */
	private DoublePoint initMainMapCenter = null;
	/**
	 * ����ͼ���ͼƬ���
	 */
	private Integer mainImgWidth = null;
	/**
	 * ����ͼ���ͼƬ�߶�
	 */
	private Integer mainImgHight = null;
	/**
	 * ���Ե�ͼ���ͼƬ���
	 */
	private Integer previewImgWidth = null;
	/**
	 * ���Ե�ͼ���ͼƬ�߶�
	 */
	private Integer previewImgHight = null;
	/**
	 * ��ͼ����
	 */
	private MapDef mapDef = null;

	/**
	 * �Ƿ���Ч�ĵ�ͼ���ŵȼ�
	 * 
	 * @param zoom
	 *            ��ͼ���ŵȼ�
	 * @return
	 */
	protected abstract boolean isValidZoom(Double zoom);
	
	/**
	 * �������ż�������Ƿ���ʾ��
	 * 
	 * @param zoom
	 *            ��ͼ���ŵȼ�
	 * @throws GISException
	 */
	public abstract void chageLayerTheme(Double zoom) throws GISException;

	/**
	 * ��ȡ��������ʽ
	 * 
	 * @return
	 */
	protected abstract Rendition getHighlightRend();
	/**
	 * ��ȡ���θ�������ʽ�����Ѹ����Ľ�����ٴ�ѡ�񵥸������
	 * 
	 * @return
	 */
	protected abstract Rendition getSecondHighlightRend();

	/**
	 * ������ʾͼԪ��ע
	 */
	protected abstract void setAutoLabelOn(boolean labelEnabled);

	/**
	 * ��ȡ���Ե�ͼ���ͼƬ���
	 * 
	 * @return
	 */
	public Integer getPreviewImgWidth() {
		return previewImgWidth;
	}

	/**
	 * �������Ե�ͼ���ͼƬ���
	 * 
	 * @param previewImgWidth
	 */
	public void setPreviewImgWidth(Integer previewImgWidth) {
		this.previewImgWidth = previewImgWidth;
	}

	/**
	 * ��ȡ���Ե�ͼ���ͼƬ�߶�
	 * 
	 * @return
	 */
	public Integer getPreviewImgHight() {
		return previewImgHight;
	}

	/**
	 * �������Ե�ͼ���ͼƬ�߶�
	 * 
	 * @param previewImgHight
	 */
	public void setPreviewImgHight(Integer previewImgHight) {
		this.previewImgHight = previewImgHight;
	}

	/**
	 * ��ȡ����ͼ���ͼƬ���
	 * 
	 * @return
	 */
	public Integer getMainImgWidth() {
		return mainImgWidth;
	}

	/**
	 * ��������ͼ���ͼƬ���
	 * 
	 * @param mainImgWidth
	 */
	public void setMainImgWidth(Integer mainImgWidth) {
		this.mainImgWidth = mainImgWidth;
	}

	/**
	 * ��ȡ����ͼ���ͼƬ�߶�
	 * 
	 * @return
	 */
	public Integer getMainImgHight() {
		return mainImgHight;
	}

	/**
	 * ��������ͼ���ͼƬ�߶�
	 * 
	 * @param mainImgHight
	 */
	public void setMainImgHight(Integer mainImgHight) {
		this.mainImgHight = mainImgHight;
	}

	/**
	 * ��ʼ����ͼ���ģ���ͼ����ϵ
	 * 
	 * @return DoublePoint
	 */
	public DoublePoint getInitMainMapCenter() {
		return (DoublePoint) initMainMapCenter.clone();
	}

	public AbstractGISMap(MapDef def) {
		setMapDef(def);
	}

	public AbstractGISMap() {

	}

	/**
	 * ��ȡ��ͼ������
	 * 
	 * @return
	 */
	public MapDef getMapDef() {
		return mapDef;
	}

	/**
	 * ���õ�ͼ������
	 * 
	 * @param mapDef
	 *            ��ͼ����
	 */
	public void setMapDef(MapDef mapDef) {
		this.mapDef = mapDef;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * ��ȡ��ͼ��Ļ��Χ
	 * 
	 * @return
	 */
	public DoubleRect getDeviceBounds() {
		return (DoubleRect) bindedMap.getDeviceBounds().clone();
	}

	/**
	 * ��ȡ��ͼ����Ļ��������
	 * 
	 * @return
	 * @throws GISException
	 */
	public DoublePoint getScreenCenter() throws GISException {
		try {
			return bindedMap.transformNumericToScreen((DoublePoint) bindedMap
					.getCenter().clone());
		} catch (Exception e) {
			throw new GISException(e);
		}
	}

	/**
	 * ���õ�ͼ����Ļ��������
	 * 
	 * @param screenPoint
	 *            ��Ļ��������
	 * @throws GISException
	 */
	public void setScreenCenter(DoublePoint screenPoint) throws GISException {
		try {
			this.centerTo(bindedMap.transformScreenToNumeric(screenPoint));
		} catch (Exception e) {
			throw new GISException(e);
		}
	}

	/**
	 * �ƶ���ͼ���ĵ㣬����ͼ������ͼͬ��
	 * 
	 * @param gisPoint
	 *            �����꣬��ͼ����ϵ
	 * @throws GISException
	 */
	public void centerTo(DoublePoint gisPoint) throws GISException {
		try {
			this.bindedMap.setCenter((DoublePoint) gisPoint.clone());
			this.bindedPreviewMap.setCenter((DoublePoint) gisPoint.clone());
		} catch (Exception e) {
			throw new GISException(e);
		}
	}

	/**
	 * ���õ�ͼ���ŵȼ�
	 * 
	 * @return
	 * @throws GISException
	 */
	public double getZoom() throws GISException {
		try {
			return bindedMap.getZoom();
		} catch (Exception e) {
			throw new GISException(e);
		}
	}

	/**
	 * ���õ�ͼ���ŵȼ�
	 * 
	 * @param zoom
	 *            ��ͼ���ŵȼ�
	 * @throws GISException
	 */
	public void setZoom(Double zoom) throws GISException {
		try {
			if (isValidZoom(zoom)) {
				bindedMap.setZoom(zoom.doubleValue());
				this.bindedPreviewMap.setZoom(zoom.doubleValue() * 2);
			}
			chageLayerTheme(zoom);
		} catch (Exception e) {
			throw new GISException(e);
		}
	}
	
	

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	/**
	 * ��Ⱦ��ͼ���������
	 * 
	 * @param map
	 *            ��Ҫ��Ⱦ�ĵ�ͼ
	 * @param outStream
	 *            �������
	 * @param imageWidth
	 *            �����ͼƬ���
	 * @param imageHight
	 *            �����ͼƬ�߶�
	 * @throws GISException
	 */
	private void render(MapJ map, OutputStream outStream, Integer imageWidth,
			Integer imageHight) throws GISException {
		map.setDeviceBounds(new DoubleRect(0, 0, imageWidth.doubleValue(),
				imageHight.doubleValue()));
		MapXtremeImageRenderer rr = new MapXtremeImageRenderer(SystemConfig
				.getInstance().getMxtURL());
		try {
			ImageRequestComposer imgRequest = ImageRequestComposer.create(map,
					256, Color.white, SystemConfig.getInstance().getMapMime());
			imgRequest.setRendering(Rendering.SPEED);
			rr.render(imgRequest);
			rr.toStream(outStream);
		} catch (Exception e) {
			log.error("��Ⱦ��ͼʧ��", e);
			log.error("��Ⱦ��ͼ������: ");
			log.error(SystemConfig.getInstance().getMxtURL());
			throw new GISException(e);
		}
		rr.dispose();
	}

	/**
	 * �����ͼ��ʼ��������Ķ���
	 * 
	 * @throws GISException
	 */
	protected void clean() throws GISException {

	}

	/**
	 * �ص���ʼ״̬
	 * 
	 * @throws GISException
	 */
	public void reset() throws GISException {
		try {
			this.clean();
			initMapStates(mapDef);
			setAutoLabelOn(true);
		} catch (Exception e) {
			throw new GISException(e);
		}
	}

	/**
	 * �������ò�����ʼ����ͼ״̬
	 * 
	 * @param mapDef
	 *            ��ͼ����
	 * @throws GISException
	 */
	protected void initMapStates(MapDef mapDef) throws GISException {
		try {
			bindedMap.setDistanceUnits(LinearUnit.kilometer);
			bindedPreviewMap.setDistanceUnits(LinearUnit.kilometer);
			bindedMap.setZoom(mapDef.getMainMapZoom().doubleValue());
			bindedPreviewMap.setZoom(mapDef.getPreviewMapZoom().doubleValue());
			this.centerTo((DoublePoint) initMainMapCenter.clone());
			this.setMainImgHight(mapDef.getMainMapImgHight());
			this.setMainImgWidth(mapDef.getMainMapImgWidth());
			this.setPreviewImgHight(mapDef.getPreviewMapImgHight());
			this.setPreviewImgWidth(mapDef.getPreviewMapImgWidth());
		} catch (Exception e) {
			throw new GISException(e);
		}
	}

	/**
	 * ���ص�ͼ
	 * 
	 * @param mapDef
	 * @throws GISException
	 */
	public synchronized void reloadMap(MapDef mapDef) throws GISException {
		this.setMapDef(mapDef);
		isMapLoaded = false;
		loadMap();
	}

	/**
	 * ���ص�ͼ,��ʼ����ͼ����
	 * 
	 * @param mapDef
	 *            ��ͼ�����ļ���(ȫ·��)
	 * @throws GISException
	 */
	public synchronized void loadMap() throws GISException {
		if (!isMapLoaded) {
			try {
				this.bindedMap = new MapJ();
				this.bindedPreviewMap = new MapJ();

				this.bindedMap.loadMapDefinition(mapDef.getMainMapDefFile());
				this.bindedPreviewMap.loadMapDefinition(mapDef
						.getPreviewMapDefFile());

				loadCenter();

				this.initMapStates(mapDef);
				isMapLoaded = true;

			} catch (IOException e) {
				log.error("��ȡ��ͼ�����ļ�ʧ�ܣ�");
				log.error(mapDef.getMainMapDefFile());
				log.error(mapDef.getPreviewMapDefFile());
				throw new GISException(e);
			} catch (Exception e) {
				log.error("��ͼ��ʼ��ʧ�ܣ�");
				log.error(mapDef.getMainMapDefFile());
				log.error(mapDef.getPreviewMapDefFile());
				throw new GISException(e);
			}
		}
	}

	/**
	 * ���ص�ͼ���ĵ�
	 * 
	 * @throws Exception
	 */
	private void loadCenter() throws Exception {
		// �������ָ�������ĵ��ʹ�ã�����ʹ�õ�ͼĬ��ֵ
		if (mapDef.getX() != null && mapDef.getY() != null) {
			initMainMapCenter = new DoublePoint(mapDef.getX(), mapDef.getY());

		} else {
			initMainMapCenter = (DoublePoint) bindedMap.getCenter().clone();
		}
		this.bindedMap.setCenter((DoublePoint) initMainMapCenter.clone());
		this.bindedPreviewMap
				.setCenter((DoublePoint) initMainMapCenter.clone());
	}

	/**
	 * ��Ⱦ����ͼ���������
	 * 
	 * @param outStream
	 *            ͼƬ��
	 * @param imageWidth
	 *            ���ͼƬ���
	 * @param imageHight
	 *            ���ͼƬ�߶�
	 * @throws GISException
	 */
	public void renderMainMap(OutputStream outStream, Integer imageWidth,
			Integer imageHight) throws GISException {
		if (imageWidth == null)
			imageWidth = this.getMainImgWidth();
		this.setMainImgWidth(imageWidth);

		if (imageHight == null)
			imageHight = this.getMainImgHight();
		this.setMainImgHight(imageHight);

		render(this.bindedMap, outStream, imageWidth, imageHight);
	}

	/**
	 * ��ȾԤ����ͼ���������
	 * 
	 * @param outStream
	 *            ͼƬ��
	 * @param imageWidth
	 *            ���ͼƬ���
	 * @param imageHight
	 *            ���ͼƬ�߶�
	 * @throws GISException
	 */
	public void renderPreviewMap(OutputStream outStream, Integer imageWidth,
			Integer imageHight) throws GISException {
		if (imageWidth == null)
			imageWidth = this.getPreviewImgWidth();
		this.setPreviewImgWidth(imageWidth);

		if (imageHight == null)
			imageHight = this.getPreviewImgHight();
		this.setPreviewImgHight(imageHight);
		render(this.bindedPreviewMap, outStream, imageWidth, imageHight);
	}

	/**
	 * ˢ�µ�ͼ
	 * 
	 * @throws Exception
	 */
	public void refresh() throws GISException {
		try {

		} catch (Exception e) {
			log.error("map zoom: ");
			log.error(this.getZoom());
			log.error("map screenCenter: ");
			log.error(this.getScreenCenter());
			throw new GISException(ErrorMsgConstants.KMGIS_MAP_01, e);
		}
	}

	/**
	 * ����Ļ����ת���ɵ�ͼ������
	 * 
	 * @param map
	 * @param rectangle
	 *            ��Ļ���η�Χ����
	 * @return DoubleRect ���η�Χ���꣬��ͼ����ϵ
	 * @throws GISException
	 */
	protected DoubleRect convert2MapPosition(MapJ map, DoubleRect rectangle)
			throws GISException {
		try {
			return map.transformScreenToNumeric(rectangle);
		} catch (Exception e) {
			throw new GISException(e);
		}
	}

	/**
	 * ����ͼ����Ļ����ת���ɵ�ͼ����
	 * 
	 * @param map
	 *            MapJ
	 * @param point
	 *            ��Ļ������
	 * @return DoublePoint �����꣬��ͼ����ϵ
	 * @throws GISException
	 */
	protected DoublePoint convert2MapPosition(MapJ map, DoublePoint point)
			throws GISException {
		try {
			return map.transformScreenToNumeric(point);
		} catch (Exception e) {
			throw new GISException(e);
		}
	}

	/**
	 * ������ͼ����Ļ����ת���ɵ�ͼ����
	 * 
	 * @param point
	 *            ��Ļ�����
	 * @return �����꣬��ͼ����ϵ
	 * @throws Exception
	 */
	public DoublePoint convert2MainMapPosition(DoublePoint point)
			throws GISException {
		return this.convert2MapPosition(this.bindedMap, point);
	}

	/**
	 * �����Ե�ͼ����Ļ����ת���ɵ�ͼ����
	 * 
	 * @param point��Ļ�����
	 * @return �����꣬��ͼ����ϵ
	 * @throws Exception
	 */
	public DoublePoint convert2PreviewMapPosition(DoublePoint point)
			throws GISException {
		return this.convert2MapPosition(this.bindedPreviewMap, point);
	}

	/**
	 * ����ͼ�ĵ�������ת������Ļ����
	 * 
	 * @param gisPoint
	 *            ���������
	 * @return
	 * @throws Exception
	 */
	public DoublePoint convert2MainMapScreen(DoublePoint gisPoint)
			throws GISException {
		try {
			return this.bindedMap.transformNumericToScreen(gisPoint);
		} catch (Exception e) {
			throw new GISException(e);
		}
	}

	/**
	 * ���Ե�ͼ�ĵ�������ת������Ļ����
	 * 
	 * @param gisPoint
	 *            ���������
	 * @return
	 * @throws Exception
	 */
	public DoublePoint convert2PreviewMapScreen(DoublePoint gisPoint)
			throws GISException {
		try {
			return this.bindedPreviewMap.transformNumericToScreen(gisPoint);
		} catch (Exception e) {
			throw new GISException(e);
		}
	}

	/**
	 * ��λ��ͼ���ĵ���ͼԪ���ڵ�λ��
	 * 
	 * @param feature
	 *            ͼԪ
	 */
	protected void locationBy(Feature feature) throws GISException {
		try {
			DoublePoint point = feature.getGeometry().getBounds().center();
			this.centerTo(point);
		} catch (Exception e) {
			throw new GISException(e);
		}

	}

	/**
	 * ��������ͼ(ѡ���ѯ��)
	 * 
	 * @param themeName
	 *            ��������
	 * @param featureSet
	 *            ͼԪ����
	 * @param layer
	 *            ͼ��
	 * @throws GISException
	 */
	protected void createThemeBySelect(String themeName, FeatureSet featureSet,
			FeatureLayer layer,Rendition rendition) throws GISException {
		try {

			Double zoom = this.bindedMap.getDistanceUnits().convert(
					layer.getMaxZoom().getScalarValue(),
					layer.getMaxZoom().getLinearUnit());
			// ��������ɼ���Χ
			if (this.bindedMap.getZoom() > zoom)
				this.setZoom(new Double(zoom.intValue()));

			featureSet.rewind();
			layer.setSelectable(true);
			layer.setEnabled(true);
			// ��������ͼ
			//System.out.println(featureSet.getTableInfo().getColumnType(featureSet.getTableInfo().getColumnIndex("ID")));
			SelectionTheme selectTheme = new SelectionTheme(themeName);
			Selection selection = new Selection(); 
			selection.add(featureSet);

			selectTheme.setSelection(selection);
			selectTheme.setRendition(rendition);

			int themeCounts = layer.getThemeList().size();
			// ��ɾ�����������ͼ����
			if (themeCounts > 0) {
				removeTheme(layer, themeName);
				layer.getThemeList().insert(selectTheme, 0);
			} else
				layer.getThemeList().add(selectTheme);
		} catch (MissingPrimaryKeyException e) {
			throw new GISException(e);
		} catch (Exception e) {
			throw new GISException(e);
		}
	}
	
	/**
	 * ��������ͼ
	 * 
	 * @param themeName
	 *            ��������
	 * @param featureSet
	 *            ͼԪ����
	 * @param layer
	 *            ͼ��
	 * @throws GISException
	 */
	protected double createTheme(String themeName, FeatureSet featureSet,
			FeatureLayer layer,Rendition rendition) throws GISException {
		try {

//			����һ���ɼ���Χ
			featureSet.rewind();
			Feature f = null;
			double max_x = 0;
			double max_y = 0;
			double min_x = 1000;
			double min_y = 1000;
			do{
				f = featureSet.getNextFeature(); 
				if(f==null)
					break;
				PointGeometry pGeometry = (PointGeometry) f.getGeometry();
				DoublePoint gisPoint = pGeometry.getPoint(null);
				double x = gisPoint.x;
				double y = gisPoint.y;
				if((x>102.1 && x<103.7 && y>24.3 && y<26.6)||(y>102.1 && y<103.7 && x>24.3 && x<26.6)){
//					����
					if(x>max_x){
						max_x = x;
					}
					if(y>max_y){
						max_y = y;
					}
					if(x<min_x){
						min_x = x;
					}
					if(y<min_y){
						min_y = y;
					}
				}
			}while(true);
			featureSet.rewind(); 
			
			//�����������ľ��룬Ҳ���ǿ��ӷ�Χ 
			double distance;	
			DoublePoint p1 = new DoublePoint(min_x, min_y);
			DoublePoint p2 = new DoublePoint(max_x, max_y);
			distance = this.distance(p1, p2); 

			if (distance < 2.5) {
			  distance = 2.5;
			}
			// �ı��ͼ���ӷ�Χ
			double newZoom = distance;
			this.setZoom(newZoom);
			// ͬ�����ĵ�
			double center_x = (max_x - min_x) / 2 + min_x;
			double center_y = (max_y - min_y) / 2 + min_y;
			DoublePoint center = new DoublePoint(center_x, center_y);
			this.bindedMap.setCenter(center);
			// ͬ��ͼ����ӷ�Χ
			if (distance < 5) {
				distance = 5;
			}
			layer.setMaxZoom(new Distance(distance * 1000 + 0.1,layer.getMaxZoom().getLinearUnit()));
			featureSet.rewind();
			layer.setSelectable(true);
			layer.setEnabled(true);
			// ��������ͼ
			SelectionTheme selectTheme = new SelectionTheme(themeName);
			Selection selection = new Selection(); 
			selection.add(featureSet);

			selectTheme.setSelection(selection);
			selectTheme.setRendition(rendition);

			int themeCounts = layer.getThemeList().size();
			// ��ɾ�����������ͼ����
			if (themeCounts > 0) {
				removeTheme(layer, themeName);
				layer.getThemeList().insert(selectTheme, 0);
			} else
				layer.getThemeList().add(selectTheme);
			return distance;
		} catch (MissingPrimaryKeyException e) {
			throw new GISException(e);
		} catch (Exception e) {
			throw new GISException(e);
		}
	}

	/**
	 * �������ű�����ȡPOI��Ⱦ
	 * @param layer
	 * @param distance
	 */
	public void getPoiLayer(FeatureLayer layer,double distance){
		Iterator themes = layer.getThemeList().iterator();
		while (themes.hasNext()) {
			Theme theme = (Theme) themes.next();
			if (theme instanceof OverrideTheme){
				OverrideTheme myOTheme = new OverrideTheme(getPoiRend(distance), theme.getName());
				themes.remove();
				layer.getThemeList().add(myOTheme);
				break;
			}
				
		}
	}
	
	/**
	 * �������ű�����ȡδѡ�����ۻ���ʽ
	 */
	protected Rendition getPoiRend(double distance) {
		Rendition rend = new RenditionImpl(); 
		rend.setValue(Rendition.FONT_FAMILY, "MapInfo Symbols"); 
		rend.setValue(Rendition.FONT_SIZE, 12);  
		rend.setValue(Rendition.SYMBOL_MODE, Rendition.SymbolMode.FONT); 
		rend.setValue(Rendition.SYMBOL_STRING, "8"); 
		rend.setValue(Rendition.SYMBOL_FOREGROUND, new Color(99,101,255));
		if(distance>5.1){
			rend.setValue(Rendition.SYMBOL_BACKGROUND_OPACITY,0.0);
			rend.setValue(Rendition.SYMBOL_FOREGROUND_OPACITY,0.0);
		}else{
			rend.setValue(Rendition.SYMBOL_BACKGROUND_OPACITY,1.0);
			rend.setValue(Rendition.SYMBOL_FOREGROUND_OPACITY,1.0);
		}
	    return rend;
	}
	
	/**
	 * �������ű�����ȡδѡ����֤����ʽ
	 */
	protected Rendition getNoLincesePoiRend(double distance) {
		Rendition rend = new RenditionImpl(); 
		rend.setValue(Rendition.FONT_FAMILY, "MapInfo Symbols"); 
		rend.setValue(Rendition.FONT_SIZE, 12);  
		rend.setValue(Rendition.SYMBOL_MODE, Rendition.SymbolMode.FONT); 
		rend.setValue(Rendition.SYMBOL_STRING, "!"); 
		rend.setValue(Rendition.SYMBOL_FOREGROUND, new Color(99,101,255));
		if(distance>5.1){
			rend.setValue(Rendition.SYMBOL_BACKGROUND_OPACITY,0.0);
			rend.setValue(Rendition.SYMBOL_FOREGROUND_OPACITY,0.0);
		}else{
			rend.setValue(Rendition.SYMBOL_BACKGROUND_OPACITY,1.0);
			rend.setValue(Rendition.SYMBOL_FOREGROUND_OPACITY,1.0);
		}
	    return rend;
	}
	
	/**
	 * ɾ������ͼ
	 * 
	 * @param layer
	 *            ͼ��
	 * @param themeName
	 *            ������
	 */
	public void removeTheme(FeatureLayer layer, String themeName) {
		Iterator themes = layer.getThemeList().iterator();
		while (themes.hasNext()) {
			Theme theme = (Theme) themes.next();
			if (theme instanceof SelectionTheme) {
				if (theme.getName().equals(themeName))
					themes.remove();
			}
		}
	}

	/**
	 * ��ȡͼ������
	 * 
	 * @param layer
	 * @return PrimaryKey
	 * @throws GISException
	 */
	protected PrimaryKey getPk(FeatureLayer layer) throws GISException {
		try {
			TableInfo tableInfo = layer.getTableInfo();
			int[] pkColumnIndexes = tableInfo.getPrimaryKeyInfo();
			Attribute[] pk = new Attribute[pkColumnIndexes.length];
			for (int i = 0; i < pkColumnIndexes.length; i++) {
				String columnName = tableInfo.getColumnName(i);
				Attribute attrPk = new Attribute(columnName);
				pk[i] = attrPk;
			}
			return new PrimaryKey(pk);
		} catch (Exception e) {
			log.error("��ȡͼ������ʧ�ܣ�");
			throw new GISException(e);
		}
	}

	/**
	 * ��ȡͼ�����е�������
	 * 
	 * @param layer
	 *            ͼ��
	 * @return List ����list
	 * @throws Exception
	 */
	protected List<String> getAllColumns(FeatureLayer layer) throws Exception {
		TableInfo tableInfo = layer.getTableInfo();
		List<String> columns = new ArrayList<String>();
		for (int i = 0; i < tableInfo.getColumnCount(); i++)
			columns.add(tableInfo.getColumnName(i));
		return columns;
	}

	/**
	 * ���õ�ͼ���ĵ㵽Ĭ��λ��
	 * 
	 * @throws GISException
	 */
	public void resetCenter() throws GISException {
		this.centerTo(this.initMainMapCenter);
	}

	/**
	 * ���õ�ͼ���ż���
	 * 
	 * @throws GISException
	 */
	public void resetZoom() throws GISException {
		this.setZoom(this.mapDef.getMainMapZoom());
	}

	/**
	 * ������������
	 * 
	 * @param gisPoint0
	 *            gis�����
	 * @param gisPoint1
	 *            gis�����
	 * @return
	 */
	public double distance(DoublePoint gisPoint0, DoublePoint gisPoint1) {
		return this.bindedMap.sphericalDistance(gisPoint0, gisPoint1);
	}

	/**
	 * �жϵ��Ƿ���ָ����������
	 * 
	 * @param gisPoint
	 *            gis�����
	 * @param areaLayerName
	 *            ����ͼ������
	 * @param areaColumn
	 *            ����������
	 * @param areaValue
	 *            ָ������������
	 * @return
	 * @throws GISException
	 */
	public boolean within(DoublePoint gisPoint, String areaLayerName,
			String areaColumn, String areaValue) throws GISException {
		try {
			FeatureLayer layer = (FeatureLayer) this.bindedMap.getLayers().get(
					areaLayerName);
			List<String> columns = getAllColumns(layer);
			TableInfo tableInfo = layer.getTableInfo();
			int columnIndex = tableInfo.getColumnIndex(areaColumn);
			int columnType = tableInfo.getColumnType(columnIndex);
			FeatureSet withinFeatures = layer.searchAtPoint(columns, gisPoint,
					QueryParams.ALL_PARAMS);
			Feature f = null;
			do {
				f = withinFeatures.getNextFeature();
				if (f == null)
					break;

				String findAreaId;
				NumberFormat nf = NumberFormat.getNumberInstance();
				nf.setMaximumFractionDigits(0);
				switch (columnType) {
				case TableInfo.COLUMN_TYPE_FLOAT:
					findAreaId = nf.format(f.getAttribute(columnIndex)
							.getFloat());
				case TableInfo.COLUMN_TYPE_DOUBLE:
				case TableInfo.COLUMN_TYPE_DECIMAL:
					findAreaId = nf.format(f.getAttribute(columnIndex)
							.getDouble());
					break;
				default:
					findAreaId = f.getAttribute(columnIndex).getString();

				}
				if (areaValue.equals(findAreaId))
					return true;
			} while (true);

		} catch (Exception e) {
			throw new GISException(e);
		}
		return false;
	}

	/**
	 * �Ƿ���ȷ������ͼԪ
	 * 
	 * @param f
	 *            ��ͼԪ
	 * @throws GISException
	 */
	protected abstract boolean isValidFeature(Feature f, TableInfo tableInfo)
			throws GISException;
}
