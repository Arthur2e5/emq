package com.emq.dao;

import java.util.List;

import com.emq.model.ConditionObject;
import com.mapinfo.dp.Attribute;
import com.mapinfo.dp.PrimaryKey;
import com.mapinfo.dp.QueryParams;
import com.mapinfo.dp.VectorGeometry;
import com.mapinfo.dp.jdbc.QueryBuilder;
import com.mapinfo.dp.jdbc.SpatialQueryDef;
import com.mapinfo.mapj.Layer;
import com.mapinfo.mapj.MapJ;
import com.mapinfo.util.DoublePoint;
import com.mapinfo.util.DoubleRect;

/**
 * ʵ��QueryBuilder ֧�ֶԾ���ר����ͼ��ʹ��sql��ѯ
 * 
 * @author guqiong
 * @created 2009-9-25
 * @history 2009-10-19 guqiong ������all��������Ϊ����
 * 			2009-10-21 guqiong ֧�ְ�֤����������֤��״̬
 * 			2009-10-23 guqiong ֧�־�Ӫ��ַ����
 */
@SuppressWarnings("deprecation")
public class CigStoreQueryBuilder implements QueryBuilder {
	// ����ר�����ѯ����
	private ConditionObject co;
	
//	 ����ר�����ѯ����
	private String tableName;
	
	public CigStoreQueryBuilder(ConditionObject co, String tableName) {
		this.co = co;
		this.tableName = tableName;
	}

	public CigStoreQueryBuilder() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mapinfo.dp.jdbc.QueryBuilder#queryInRectangle(com.mapinfo.mapj.MapJ,
	 * com.mapinfo.mapj.Layer, com.mapinfo.dp.jdbc.SpatialQueryDef,
	 * java.lang.String[], com.mapinfo.dp.QueryParams,
	 * com.mapinfo.util.DoubleRect)
	 */
	public SpatialQueryDef queryInRectangle(MapJ mapj, Layer layer,
			SpatialQueryDef queryDef, String[] columnNames,
			QueryParams queryParams, DoubleRect rect) throws Exception {
		return queryDef;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mapinfo.dp.jdbc.QueryBuilder#queryInRegion(com.mapinfo.mapj.MapJ,
	 * com.mapinfo.mapj.Layer, com.mapinfo.dp.jdbc.SpatialQueryDef,
	 * java.lang.String[], com.mapinfo.dp.QueryParams,
	 * com.mapinfo.dp.VectorGeometry)
	 */
	public SpatialQueryDef queryInRegion(MapJ mapj, Layer layer,
			SpatialQueryDef queryDef, String[] columnNames,
			QueryParams queryParams, VectorGeometry region) throws Exception {
		return queryDef;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mapinfo.dp.jdbc.QueryBuilder#queryInRadius(com.mapinfo.mapj.MapJ,
	 * com.mapinfo.mapj.Layer, com.mapinfo.dp.jdbc.SpatialQueryDef,
	 * java.lang.String[], com.mapinfo.dp.QueryParams,
	 * com.mapinfo.util.DoublePoint, double)
	 */
	public SpatialQueryDef queryInRadius(MapJ mapj, Layer layer,
			SpatialQueryDef queryDef, String[] columnNames,
			QueryParams queryParams, DoublePoint point, double radius)
			throws Exception {
		return queryDef;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mapinfo.dp.jdbc.QueryBuilder#queryAtPoint(com.mapinfo.mapj.MapJ,
	 * com.mapinfo.mapj.Layer, com.mapinfo.dp.jdbc.SpatialQueryDef,
	 * java.lang.String[], com.mapinfo.dp.QueryParams,
	 * com.mapinfo.util.DoublePoint)
	 */
	public SpatialQueryDef queryAtPoint(MapJ mapj, Layer layer,
			SpatialQueryDef queryDef, String[] columnNames,
			QueryParams queryParams, DoublePoint point) throws Exception {
		return queryDef;
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mapinfo.dp.jdbc.QueryBuilder#queryAll(com.mapinfo.mapj.MapJ,
	 * com.mapinfo.mapj.Layer, com.mapinfo.dp.jdbc.SpatialQueryDef,
	 * java.lang.String[], com.mapinfo.dp.QueryParams)
	 */
	public SpatialQueryDef queryAll(MapJ mapj, Layer layer,
			SpatialQueryDef queryDef, String[] columnNames,
			QueryParams queryParams) {
		if (co != null) {
			StringBuffer sql = new StringBuffer(
			"select * from "+tableName+" m where  ");
			String where = buildSqlWhere(co);
			sql.append(where);
			SpatialQueryDef result = new SpatialQueryDef(sql.toString(),
					queryDef.getSpatialQueryMetaData());
			return result;
		} else
			return queryDef;
	}

	/**
	 * ����where���
	 * @param co ��������
	 * @return sql��������where�� �ؼ���
	 */
	public static String buildSqlWhere(ConditionObject co) {
		StringBuffer where = new StringBuffer(" 1=1 ");
		// ���֤��
		if (co.mayBeUsed(co.getLicenceNo())) {
			where.append(" and m.XKZHM like '%" + co.getLicenceNo() + "%'");
		}
		// �̵�����
		if (co.mayBeUsed(co.getStoreName())) {
			where.append(" and m.KHMC like '%" + co.getStoreName() + "%'");
		}
		// ��������
		if (co.mayBeUsed(co.getCorporationName())) {
			where.append(" and m.FR like '%" + co.getCorporationName() + "%'");
		}
		// ��������
		if (co.mayBeUsed(co.getAreaName())) {
			where.append(" and m.DQMC = '" + co.getAreaName() + "'");
		}
		// �ֵ�����
		if (co.mayBeUsed(co.getStreetName())) {
			where.append(" and m.JDMC = '" + co.getStreetName() + "'");
		}
		// ��Ӫҵ̬
		if (co.mayBeUsed(co.getFareTypeName())) {
			where.append(" and m.JYYT like '%" + co.getFareTypeName() + "%'");
		}
		// ��֤��ʼ����
		if (co.mayBeUsed(co.getPaperTime_start())) {
			where.append(" and m.BZRQ >= CONVERT(datetime,'" + co.getPaperTime_start() + "',101) ");
		}
		// ��֤��������
		if (co.mayBeUsed(co.getPaperTime_end())) {
			where.append(" and m.BZRQ <= CONVERT(datetime,'" + co.getPaperTime_end() + "',101) ");
		}
		// ֤��״̬
		if (co.mayBeUsed(co.getLicenceState())) {
			where.append(" and m.ZJZT = '" + co.getLicenceState() + "'");
		}
		// ��Ӫ��ַ
		if(co.mayBeUsed(co.getFareAddress())){
			where.append(" and m.JYDZ like '%" + co.getFareAddress() + "%'");
		}
		// ��˾����
		if(co.mayBeUsed(co.getCorpCode())){
			where.append(" and m.gsdm = '" + co.getCorpCode() + "'");
		}
		// ��·
		if(co.mayBeUsed(co.getLine())){
			where.append(" and m.XSXL = '" + co.getLine() + "'");
		}
		// �ȼ�
		if(co.mayBeUsed(co.getRank())){
			where.append(" and m.XSKHDJ = '" + co.getRank() + "'");
		}
        //ר���ȼ�
		if(co.mayBeUsed(co.getSpecialRank())){
			where.append(" and m.ZMDJ = '" + co.getSpecialRank() + "'");
		}
		// ����ҵ̬
		if(co.mayBeUsed(co.getSellType())){
			where.append(" and m.LSYT = '" + co.getSellType() + "'");
		}
		// �г�����
		if(co.mayBeUsed(co.getMarkType())){
			where.append(" and m.SCLX = '" + co.getMarkType() + "'");
		}
		// ��Ӫ��ģ
		if(co.mayBeUsed(co.getFareSize())){
			where.append(" and m.JYGM = '" + co.getFareSize() + "'");
		}
		// ע����ʼ����
		if (co.mayBeUsed(co.getLogoutTime_start())) {
			where.append(" and m.YXRQ >= CONVERT(datetime,'" + co.getLogoutTime_start() + "',101) ");
		}
		// ע����������
		if (co.mayBeUsed(co.getLogoutTime_end())) {
			where.append(" and m.YXRQ <= CONVERT(datetime,'" + co.getLogoutTime_end() + "',101) ");
		}
        // ����������
		if (co.getNoOrderMonth()!=0) {
			where.append(" and datediff(mm,m.DHSJ,GETDATE())>="+co.getNoOrderMonth());
		}
//		����
		if (co.getPc()!=0) {
			where.append(" and m.pc="+co.getPc());
		}
		return where.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mapinfo.dp.jdbc.QueryBuilder#queryByAttribute(com.mapinfo.mapj.MapJ,
	 * com.mapinfo.mapj.Layer, com.mapinfo.dp.jdbc.SpatialQueryDef,
	 * java.lang.String[], com.mapinfo.dp.QueryParams, java.lang.String,
	 * com.mapinfo.dp.Attribute)
	 */
	public SpatialQueryDef queryByAttribute(MapJ mapj, Layer layer,
			SpatialQueryDef queryDef, String[] columnNames,
			QueryParams queryParams, String searchColumn, Attribute attribute)
			throws Exception {
		return queryDef;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mapinfo.dp.jdbc.QueryBuilder#queryByAttributes(com.mapinfo.mapj.MapJ,
	 * com.mapinfo.mapj.Layer, com.mapinfo.dp.jdbc.SpatialQueryDef,
	 * java.util.List, com.mapinfo.dp.QueryParams, java.util.List,
	 * java.util.List, java.util.List)
	 */
	public SpatialQueryDef queryByAttributes(MapJ mapj, Layer layer,
			SpatialQueryDef queryDef, List columnNames,
			QueryParams queryParams, List attNames, List attOperators,
			List attValues) throws Exception {
		return queryDef;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mapinfo.dp.jdbc.QueryBuilder#queryByPrimaryKey(com.mapinfo.mapj.MapJ,
	 * com.mapinfo.mapj.Layer, com.mapinfo.dp.jdbc.SpatialQueryDef,
	 * java.lang.String[], com.mapinfo.dp.QueryParams, java.lang.String[],
	 * com.mapinfo.dp.PrimaryKey[])
	 */
	public SpatialQueryDef queryByPrimaryKey(MapJ mapj, Layer layer,
			SpatialQueryDef queryDef, String[] columnNames,
			QueryParams queryParams, String[] idColumns, PrimaryKey[] keys)
			throws Exception {
		return queryDef;
	}

}
