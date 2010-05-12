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

	public List getTreeNode(int pN) {
		List mapList = new ArrayList();
		if (pN == -1) {
			Map map = new HashMap();
			map.put("id", 1);
			map.put("text", "��һ��һ");
			map.put("nid", "1_1");
			mapList.add(map);
			// map.put("oid", "��һ��һ");
			Map map1 = new HashMap();
			map1.put("id", 2);
			map1.put("text", "��һ���");
			map.put("nid", "1_2");
			mapList.add(map1);
		} else if (pN == 1) {
			Map map = new HashMap();
			map.put("id", 11);
			map.put("text", "�ڶ���һ");
			map.put("nid", "2_1");
			map.put("leaf", true);
			mapList.add(map);
		} else if (pN == 2) {
			Map map = new HashMap();
			map.put("id", 12);
			map.put("text", "�ڶ����");
			map.put("nid", "2_2");
			map.put("leaf", true);
			mapList.add(map);
		}
		return mapList;
	}
}
