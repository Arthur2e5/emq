package com.emq.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.emq.dao.PlantDao;

public class PlantService {

	private PlantDao plantDao;
	
	public PlantDao getPlantDao() {
		return plantDao;
	}

	public void setPlantDao(PlantDao plantDao) {
		this.plantDao = plantDao;
	}

	public Map getPersonById(String personId){
		return plantDao.getPersonById(personId);
	}
	
	public List testExtGrid(){
		Map map = new HashMap();
		map.put("����", "A1");
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
}
