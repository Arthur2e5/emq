package com.emq.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * �������ݲ�ѯ
 * @author lyt
 * @created 2009-9-22
 */
public interface BaseJdbcDao {
	/**
	 * ����sql��ȡ����
	 * @param sql
	 * @return List
	 * @author lyt
	 */
	public List getDataList(String sql);
	/**
	 * ִ��updatesql
	 * @param sql
	 * @return List
	 * @author lyt
	 */
	public boolean exeUpdateSql(String sql);
	/**
	 * ִ�д洢����
	 * @param sql
	 * @return List
	 * @author lyt
	 */
	public List execute_proc(String sql);
	/**
	 * ����ִ��sql
	 * @param sqlList
	 * @author lyt
	 */
	public void exeUpdateSqlByBach(List sqlList) throws SQLException;
	/**
	 * ����sql��ȡΨһ�е�Map����
	 * @param sql
	 * @return Map
	 * @author lyt
	 */
	public Map getMapData(String sql);
	/**
	 * ִ�д��f����״ֵ̬
	 * @param sql
	 * @return String[2] ����״ֵ̬������
	 * @author lyt
	 */
	public String[] executeProcByReturn(String sql);
	
}
