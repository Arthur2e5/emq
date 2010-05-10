package com.emq.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.emq.dao.PlantDao;
import com.emq.model.CommonCheckbox;
import com.emq.util.Global;

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
	
	public List testCombox(){
		List list = new ArrayList();
		for(int i=0;i<10;i++){
			CommonCheckbox map = new CommonCheckbox();
			map.setCode("1"+i);
			map.setText("��ʾ"+i);
			map.setOtherInfo(i+"bb");
			list.add(map);
		}
		list.add(0,Global.IS_ALL);//�Ƿ���ȫ����������
		list.add(0,Global.HAS_SELECT);// �Ƿ������ѡ����������
		return list;
	}
	
	public List testCombox1(String mm,String pp){
		List list = new ArrayList();
		for(int i=0;i<4;i++){
			CommonCheckbox map = new CommonCheckbox();
			map.setCode("1"+i);
			map.setText("��ʾ"+i);
			map.setOtherInfo(i+"bb");
			list.add(map);
		}
		list.add(0,Global.IS_ALL);//�Ƿ���ȫ����������
		list.add(0,Global.HAS_SELECT);// �Ƿ������ѡ����������
		return list;
	}
}
