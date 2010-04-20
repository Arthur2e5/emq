package com.emq.dao.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import com.emq.dao.BaseJdbcDao;
import com.emq.dao.jdbc.AbstractJdbcBaseDao;
import com.emq.logger.Logger;
import com.emq.ui.servlet.AbstractMapServlet;
/**
 * �������ݲ�ѯ
 * @author lyt
 * @created 2009-9-22
 */
public class BaseJdbcDaoImp extends AbstractJdbcBaseDao implements BaseJdbcDao {
	private static Logger log = Logger.getLogger(BaseJdbcDaoImp.class);
	/**
	 * ����sql��ȡ����
	 * @param sql
	 * @return List
	 * @author lyt
	 */
	public List getDataList(final String sql){
		List dataList = null;
		try {
			dataList = this.queryForList(sql);	
		} catch (DataAccessException e) {
			e.printStackTrace();
			log.debug(e.getMessage());
		}
		if (dataList == null) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * ִ��updatesql
	 * @param sql
	 * @return List
	 * @author lyt
	 */
	public boolean exeUpdateSql(String sql){
		boolean mark = true;
		try {
			this.update(sql);
		} catch (DataAccessException e) {
			mark = false;
			e.printStackTrace();
			log.debug(e.getMessage());
		}
		return mark;
	}
	
	/**
	 * ����ִ��sql
	 * @param sqlList
	 * @author lyt
	 */
	public void exeUpdateSqlByBach(List sqlList) throws SQLException {
		try {
			if(sqlList.size()>0){
				this.getConnection().setAutoCommit(false);
				String[] sql = new String[sqlList.size()];
				for(int i=0;i<sqlList.size();i++){
					sql[i] = (String)sqlList.get(i);
				}
				this.getJdbcTemplate().batchUpdate(sql);
				this.getConnection().commit();
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			this.getConnection().rollback();
		}
	}
	/**
	 * ����sql��ȡΨһ�е�Map����
	 * @param sql
	 * @return Map
	 * @author lyt
	 */
	public Map getMapData(String sql){
		Map data = null;
		try {
			data = (Map)this.getDataForMap(sql);	
		} catch (DataAccessException e) {
			e.printStackTrace();
			log.debug(e.getMessage());
		}
		if (data == null) {
			return new HashMap();
		}
		return data;
	}
	
	
}
