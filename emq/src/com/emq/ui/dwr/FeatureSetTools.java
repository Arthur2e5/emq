package com.emq.ui.dwr;

import com.emq.exception.GISException;
import com.emq.logger.Logger;
import com.mapinfo.dp.Attribute;
import com.mapinfo.dp.Feature;
import com.mapinfo.dp.FeatureSet;
import com.mapinfo.dp.TableInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List; 

/**
 * ��FeatureSet��ʽ������ת��Ϊ������ʽ
 * <p>
 * FeatureSet��mapxtreme��ͼԪ��¼��
 * 
 * @author guqiong
 * @created 2009-9-25
 * 
 */
public class FeatureSetTools {

	private static Logger log = Logger.getLogger(FeatureSetTools.class);

	/**
	 * ��װFeatureSetΪList<Map<String, String>>
	 * List�����ÿ����¼Ϊһ��map��map��keyΪ������������valueΪֵ
	 * 
	 * @param fs
	 *            ͼԪ����
	 * @fieldDesc �ֶ��������� keyΪkeyΪ��������,valueΪ�ֶ�����
	 * @return List<Map<String, String>> �ֶ�����
	 */
	public static List<Map<String, String>> convert(List fsList,
			Map<String, String> fieldDesc) throws GISException {
		List<Map<String, String>> rows = new ArrayList<Map<String, String>>();
		try {
			for(int i=0;i<fsList.size();i++){
				FeatureSet fs = (FeatureSet)fsList.get(i); 
				TableInfo tableInfo = fs.getTableInfo();
				while (1 == 1) {
					Feature f = fs.getNextFeature();
					Map<String, String> row = new HashMap<String, String>();
					if (f == null)
						break;
					for (String desc : fieldDesc.keySet()) {
						String column = fieldDesc.get(desc);
						String value = getFieldValue(f, tableInfo, column);
						if(desc.equals("ID")&&value.equals("��֤��")){
							row.put(desc, getFieldValue(f, tableInfo, "ID"));
						}else{
							row.put(desc, getFieldValue(f, tableInfo, column));
						}
					}
					rows.add(row);
				}
			}
			return rows;
		} catch (Exception e) {
			throw new GISException(e);
		}
	}

	/**
	 * ��ȡͼԪ���ֶ�ֵ
	 * 
	 * @param f
	 *            ͼԪ
	 * @param tableInfo
	 *            ������Ϣ
	 * @param columnName
	 *            �ֶ�����֧�ֶ��ֶ��ö��ŷָ�, ��ʽcol0,col1,�����ڶ�ֵ������Ŀ��
	 * @return �ֶ�ֵ ֧�ֶ��ֶ��ö��ŷָ�,��ʽvalue0, value1
	 * @throws Exception
	 */
	protected static String getFieldValue(Feature f, TableInfo tableInfo,
			String columnName) throws Exception {
		int colIndex = tableInfo.getColumnIndex(columnName);
		if(colIndex==-1){
			return "";
		}
		Attribute attr = f.getAttribute(colIndex);
		// TODO:��ʽ����,�����������͵�
		String value = attr.isNull() ? "" : attr.getString();
		return value;
	}

	/**
	 * ��װFeatureSetΪxml ��ʽ, �硰<?xml version="1.0" encoding="UTF-8"?><rows><row
	 * id="a"><cell>AA</cell><cell>BB</cell> <cell>CC</cell></row></rows>��,
	 * ˳��colIndexes�е�˳��,FeatureSet������һ����������
	 * 
	 * @param fs
	 *            ͼԪ����
	 * @param colIndexes
	 *            ��Ҫ��װ���ֶ������б���0��ʼ
	 * @return xml���ݣ�ֻ���ֶ������б��е��ֶ�
	 * @throws GISException
	 */
//	public static String convert2Xml(List fsList, List<Integer> colIndexes)
//			throws GISException {
//		XMLConvertor xmlConvertor = new DhtmlGridDataConvertor();
//		return xmlConvertor.convert(fsList, colIndexes);
//	}
}
