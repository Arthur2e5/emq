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

	public List getCommonTreeNode(String nodeId, String treeLevel,
			String[] treeType, String otherInfo) {
		List mapList = new ArrayList();
		
		return mapList;
	}
	
   public List getGdj(String nodeId,String treeLevel,String nextLevel,boolean ifleaf){
	   List mapList = new ArrayList();
		//������������,ʵ�ʴӿ���ȡ
		Map map = new HashMap();
		map.put("id", "1");
		map.put("text", "�������");
		//map.put("nextLevel", nextLevel);
		map.put("leaf", ifleaf);
		map.put("treeLevel", nextLevel);
		Map map1 = new HashMap();
		map1.put("id", "2");
		map1.put("text", "���������");
		//map1.put("nextLevel", nextLevel);
		map1.put("leaf", ifleaf);
		map1.put("treeLevel", nextLevel);
		mapList.add(map);
		mapList.add(map1);
		return mapList;
   }
   
   public List getGdjXqs(String nodeId,String treeLevel,String nextLevel,boolean ifleaf){
	   List mapList = new ArrayList();
		//������������,ʵ�ʴӿ���ȡ
	   if(nodeId.equals("1")){
	         //������������,ʵ�ʴӿ���ȡ
				Map map = new HashMap();
				map.put("id", "1_1");
				map.put("text", "����");
				//map.put("nextLevel", nextLevel);
				map.put("leaf", ifleaf);
				map.put("treeLevel", nextLevel);
				Map map1 = new HashMap();
				map1.put("id", "1_2");
				map1.put("text", "�¹�");
				//map1.put("nextLevel",nextLevel);
				map1.put("leaf", ifleaf);
				map1.put("treeLevel", nextLevel);
				mapList.add(map);
				mapList.add(map1);
			}else if(nodeId.equals("2")){
				//������������,ʵ�ʴӿ���ȡ
				Map map = new HashMap();
				map.put("id", "2_1");
				map.put("text", "�ų�");
				//map.put("nextLevel", nextLevel);
				map.put("leaf", ifleaf);
				map.put("treeLevel", nextLevel);
				Map map1 = new HashMap();
				map1.put("id", "2_2");
				map1.put("text", "��ɽ");
				//map1.put("nextLevel", nextLevel);
				map1.put("leaf", ifleaf);
				map1.put("treeLevel", nextLevel);
				mapList.add(map);
				mapList.add(map1);
			}
		return mapList;
   }
   public List getGdjXqsBdz(String nodeId,String treeLevel,String nextLevel,boolean ifleaf){
	   List mapList = new ArrayList();
	   if(nodeId.equals("1_1")){
	         //������������,ʵ�ʴӿ���ȡ
				Map map = new HashMap();
				map.put("id", "1_1_1");
				map.put("text", "���վ����1");
			//	map.put("nextLevel", nextLevel);
				map.put("leaf", ifleaf);
				map.put("treeLevel", nextLevel);
				Map map1 = new HashMap();
				map1.put("id", "1_1_2");
				map1.put("text", "���վ����2");
			//	map1.put("nextLevel", nextLevel);
				map1.put("leaf", ifleaf);
				map1.put("treeLevel", nextLevel);
				mapList.add(map);
				mapList.add(map1);
			}else if(nodeId.equals("1_2")){
				Map map = new HashMap();
				map.put("id", "1_2_1");
				map.put("text", "���վ�¹�1");
				//map.put("nextLevel", nextLevel);
				map.put("leaf", ifleaf);
				map.put("treeLevel", nextLevel);
				Map map1 = new HashMap();
				map1.put("id", "1_2_2");
				map1.put("text", "���վ�¹�2");
				//map1.put("nextLevel", nextLevel);
				map1.put("leaf", ifleaf);
				map1.put("treeLevel", nextLevel);
				mapList.add(map);
				mapList.add(map1);
			}else if(nodeId.equals("2_1")){
				Map map = new HashMap();
				map.put("id", "2_1_1");
				map.put("text", "���վ�ų�1");
				//map.put("nextLevel",nextLevel);
				map.put("leaf", ifleaf);
				map.put("treeLevel", nextLevel);
				Map map1 = new HashMap();
				map1.put("id", "2_1_2");
				map1.put("text", "���վ�ų�2");
				//map1.put("nextLevel", nextLevel);
				map1.put("leaf", ifleaf);
				map1.put("treeLevel", nextLevel);
				mapList.add(map);
				mapList.add(map1);
			}else if(nodeId.equals("2_2")){
				Map map = new HashMap();
				map.put("id", "2_2_1");
				map.put("text", "���վ��ɽ1");
				//map.put("nextLevel", nextLevel);
				map.put("leaf", ifleaf);
				map.put("treeLevel", nextLevel);
				Map map1 = new HashMap();
				map1.put("id", "2_2_2");
				map1.put("text", "���վ��ɽ2");
				//map1.put("nextLevel", nextLevel);
				map1.put("leaf", ifleaf);
				map1.put("treeLevel", nextLevel);
				mapList.add(map);
				mapList.add(map1);
			}
	   return mapList;
   }
}
