package com.emq.dao;

import java.util.Map;
import com.emq.dao.jdbc.BaseJdbcDaoImp;

public class PlantDao extends BaseJdbcDaoImp{

	/**
	 * ������Ա��Ż�ȡ��Ա��Ϣ
	 * @param userId
	 * @return
	 */
	public Map getPersonById(String personId){
		Map person =  this.getDataForMap("select personName,personId,password from plant_person where personId='"+personId+"'");
		return person;
	}
}
