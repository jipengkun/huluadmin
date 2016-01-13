package com.hulu.base.core.dao;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


/**
 * @author pengkunj
 */
public class BaseDao{

	@Autowired
	protected JdbcTemplate jt;

	public JdbcTemplate getJt() {
		return jt;
	}

	public void setJt(JdbcTemplate jt) {
		this.jt = jt;
	}

	/**
	 * 取分页sql
	 * @param querySql
	 * @param page
	 * @return
	 */
	public String getPageSql(String querySql,Page page) {

		return this.getPageSql(querySql, page.getCurrentPage(), page.getPageSize());
	}

	/**
	 * 取分页sql
	 * @param querySql 未加分页代码的sql
	 * @param pageNo 开始页（From 1）
	 * @param pageSize 页size
	 * @return
	 */
	public String getPageSql(String querySql, int pageNo, int pageSize) {
		String result = "";
		result = querySql + " limit " + ((pageNo - 1) * pageSize) + "," + pageSize;
		return result;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Page queryForPage(String sql, Object[] args,int pageNo,int pageSize,RowMapper rowMapper){
		Page page = new Page();
		//page.setCurrentPage(pageNo);
		page.setPageSize(pageSize);
		StringBuffer totalSQL = new StringBuffer(" SELECT count(*) FROM ( ");
		totalSQL.append(sql);
		totalSQL.append(" ) totalTable ");
		int totalRows = jt.queryForObject(totalSQL.toString(), args, Integer.class);
		page.setTotalRows(totalRows);
		page.setTotalPage(getTotalPages(totalRows,pageSize));
		if(pageNo > page.getTotalPage()){
			pageNo = page.getTotalPage();
		}if(pageNo < 1){
			pageNo = 1;
		}
		page.setCurrentPage(pageNo);
		page.setStartIndex((page.getCurrentPage()-1) * page.getPageSize());
		String listSql =  getPageSql(sql, page);
		if(rowMapper == null){
			page.setList(jt.queryForList(listSql, args));
		}else {
			page.setList(jt.query(listSql, args, rowMapper));
		}
		return page;
	}

	Page queryForPage(String sql,Object[] args,int pageNo,int pageSize){
		return queryForPage(sql, args, pageNo, pageSize,null);
	}

	Page queryForPage(String sql,Object[] args,int pageNo){
		return queryForPage(sql, args, pageNo, new Page().getPageSize(),null);
	}

	@SuppressWarnings("rawtypes")
	Page queryForPage(String sql,Object[] args,int pageNo,RowMapper rowMapper){
		return queryForPage(sql, args, pageNo, new Page().getPageSize(),rowMapper);
	}
	/**
	 * 获取最大页数
	 * @param totalRows
	 * @param pageSize
	 * @return
	 */
	private int getTotalPages(int totalRows, int pageSize) {
		if(pageSize < 1){
			return 0;
		}
		if(totalRows == 0){
			return 1;
		}
		return (totalRows + pageSize -1) / pageSize;
	}


}
