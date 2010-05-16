package com.emq.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.emq.Global;
import com.emq.dao.PlantDao;

public class PlantService extends BaseService {

	private PlantDao plantDao;

	public PlantDao getPlantDao() {
		return plantDao;
	}

	public void setPlantDao(PlantDao plantDao) {
		this.plantDao = plantDao;
	}

	public Map getPersonById(String personId) {
		return plantDao.getPersonById(personId);
	}

	public List testExtGrid() {
		Map map = new HashMap();
		map.put("����", "\\cmpCtrol\\2-2.png");
		map.put("�Ա�", "��");
		map.put("����", "7");
		map.put("ѧ��", "4");
		map.put("����״��", "4");
		map.put("סַ", "3");
		map.put("��˾", "2");
		map.put("ְҵ", "1");
		map.put("����", "6");
		map.put("��������", "sdf");
		map.put("���˷���", "tt");
		List mapList = new ArrayList();
		mapList.add(map);
		return mapList;
	}

	public String getDataList() {
		List dataList = new ArrayList();
		dataList = plantDao.getDataList("select * from EMQ_PASS_BOOK");
		return this.createPassBookExcel(dataList);
	}

	public List testCombox() {
		List list = new ArrayList();
		list.add(Global.HAS_SELECT);
		// list.add(Global.IS_ALL);
		for (int i = 0; i < 10; i++) {
			Map map = new HashMap();
			map.put("code", i);
			map.put("text", "��ʾ" + i);
			map.put("otherInfo", i + "bb");
			list.add(map);
		}
		return list;
	}

	public List getInstanceExtGrid(int num) {
		List mapList = new ArrayList();
		for (int i = 0; i < num; i++) {
			Map map = new HashMap();
			map.put("����", "A1" + i);
			map.put("�Ա�", "��" + i);
			map.put("����", "7" + i);
			map.put("ѧ��", "4" + i);
			map.put("����״��", "4" + i);
			map.put("סַ", "3" + i);
			map.put("��˾", "2" + i);
			map.put("ְҵ", "1" + i);
			map.put("����", "6" + i);
			map.put("��������", "sdf" + i);
			map.put("���˷���", "tt" + i);
			mapList.add(map);
		}

		return mapList;
	}

	/**
	 * 
	 * @param nodeId
	 * @param treeLevel
	 * @param treeType
	 * @param otherInfo 
	 * treeTypeָ����������
	 * otherInfo��¼�ڵ��������Ϣ,û��������ָ��,����Ϊ�ա�
	 * nodeIdΪ�ڵ��Ψһ��־,��nodeId='-1'��ʾΪ���ĸ���
	 */

	public List getCommonTreeNode(String nodeId, String treeLevel,
			String treeType, String otherInfo) {
		List mapList = new ArrayList();
		if(treeType.trim().equals(Global.TREE_TYPE_1)){
			//(�����+������+���վ)
			mapList = this.getTreeType1Data(nodeId, treeLevel, otherInfo);
		}else if(treeType.trim().equals(Global.TREE_TYPE_2)){
            //(�����+ά������+��Ա)
		}else if(treeType.trim().equals(Global.TREE_TYPE_3)){
            //(�õ继+���վ)
		}else if(treeType.trim().equals(Global.TREE_TYPE_4)){
            //(��·+���վ)
		}
		return mapList;
	}
	
	/**
	 * 
	 * @param nodeId
	 * @param treeLevel
	 * @param otherInfo
	 * @return(�����+������+���վ)���ι���
	 */
	private List getTreeType1Data(String nodeId, String treeLevel,String otherInfo){
		List mapList = new ArrayList();
		if(treeLevel.equals(Global.TREE_ROOT)){
			//����,�¼�Ӧ��ȡ�����
			mapList = this.getPowerSupplyOffice(Global.TREE_LEVEL_1_1,false);
		}else if(treeLevel.equals(Global.TREE_LEVEL_1_1)){
			//�����,�¼�Ӧ��ȡ������
			mapList = this.getProvinceByPowerSupplyOffice(nodeId, Global.TREE_LEVEL_1_2, false);
		}else if(treeLevel.equals(Global.TREE_LEVEL_1_2)){
			//������,�¼�Ӧ��ȡ���վ
			mapList = this.getTransformerSubstationByPro(nodeId, treeLevel, true);
		}
		return mapList;
	}
	
	/**
	 * ȡ����ַ���
	 * @param treeLevel
	 * @param isLeaf
	 */
	private List getPowerSupplyOffice(String treeLevel,boolean isLeaf){
		List mapList = new ArrayList();
		//������������,ʵ�ʴӿ���ȡ
		Map map = new HashMap();
		map.put("id", "1");
		map.put("text", "�������");
		map.put("nid", "����");
		map.put("leaf", isLeaf);
		map.put("treeLevel", treeLevel);
		Map map1 = new HashMap();
		map1.put("id", "2");
		map1.put("text", "���������");
		map1.put("nid", "����");
		map1.put("leaf", isLeaf);
		map1.put("treeLevel", treeLevel);
		mapList.add(map);
		mapList.add(map1);
		return mapList;
	}
	
	/**
	 * ������µ�������
	 */
	public List getProvinceByPowerSupplyOffice(String nodeId,String treeLevel,boolean isLeaf){
		List mapList = new ArrayList();
		if(nodeId.equals("1")){
         //������������,ʵ�ʴӿ���ȡ
			Map map = new HashMap();
			map.put("id", "1_1");
			map.put("text", "����");
			map.put("nid", "����");
			map.put("leaf", isLeaf);
			map.put("treeLevel", treeLevel);
			map.put("id", "1_2");
			map.put("text", "�¹�");
			map.put("nid", "����");
			map.put("leaf", isLeaf);
			map.put("treeLevel", treeLevel);
			mapList.add(map);
		}else if(nodeId.equals("2")){
			//������������,ʵ�ʴӿ���ȡ
			Map map = new HashMap();
			map.put("id", "2_1");
			map.put("text", "�ų�");
			map.put("nid", "����");
			map.put("leaf", isLeaf);
			map.put("treeLevel", treeLevel);
			map.put("id", "2_2");
			map.put("text", "��ɽ");
			map.put("nid", "����");
			map.put("leaf", isLeaf);
			map.put("treeLevel", treeLevel);
			mapList.add(map);
		}
		return mapList;
	}
	
	public List getTransformerSubstationByPro(String nodeId,String treeLevel,boolean isLeaf){
		List mapList = new ArrayList();
		//���ݿ��������id����ȡ�����ݲ����ж�
		if(nodeId.equals("1_1")){
	         //������������,ʵ�ʴӿ���ȡ
				Map map = new HashMap();
				map.put("id", "1_1_1");
				map.put("text", "���վ����1");
				map.put("nid", "����");
				map.put("leaf", isLeaf);
				map.put("treeLevel", treeLevel);
				Map map1 = new HashMap();
				map1.put("id", "1_1_2");
				map1.put("text", "���վ����2");
				map1.put("nid", "����");
				map1.put("leaf", isLeaf);
				map1.put("treeLevel", treeLevel);
				mapList.add(map);
				mapList.add(map1);
			}else if(nodeId.equals("1_2")){
				Map map = new HashMap();
				map.put("id", "1_2_1");
				map.put("text", "���վ�¹�1");
				map.put("nid", "����");
				map.put("leaf", isLeaf);
				map.put("treeLevel", treeLevel);
				Map map1 = new HashMap();
				map1.put("id", "1_2_2");
				map1.put("text", "���վ�¹�2");
				map1.put("nid", "����");
				map1.put("leaf", isLeaf);
				map1.put("treeLevel", treeLevel);
				mapList.add(map);
				mapList.add(map1);
			}else if(nodeId.equals("2_1")){
				Map map = new HashMap();
				map.put("id", "2_1_1");
				map.put("text", "���վ�ų�1");
				map.put("nid", "����");
				map.put("leaf", isLeaf);
				map.put("treeLevel", treeLevel);
				Map map1 = new HashMap();
				map1.put("id", "2_1_2");
				map1.put("text", "���վ�ų�2");
				map1.put("nid", "����");
				map1.put("leaf", isLeaf);
				map1.put("treeLevel", treeLevel);
				mapList.add(map);
				mapList.add(map1);
			}else if(nodeId.equals("2_2")){
				Map map = new HashMap();
				map.put("id", "2_2_1");
				map.put("text", "���վ��ɽ1");
				map.put("nid", "����");
				map.put("leaf", isLeaf);
				map.put("treeLevel", treeLevel);
				Map map1 = new HashMap();
				map1.put("id", "2_2_2");
				map1.put("text", "���վ��ɽ2");
				map1.put("nid", "����");
				map1.put("leaf", isLeaf);
				map1.put("treeLevel", treeLevel);
				mapList.add(map);
				mapList.add(map1);
			}
		return mapList;
	}
}
