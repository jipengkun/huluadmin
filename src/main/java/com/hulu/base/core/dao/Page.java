package com.hulu.base.core.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *@Description: 分页实体类
 *@Author: pengkunj
 *@Version: 1.1.0
 */
public class Page{
	/**
	 * 当前页
	 */
	private int currentPage;
	/**
	 * 每页数据量，默认为10
	 */
	private int pageSize = 10;
	/**
	 * 总记录数
	 */
	private int totalRows;
	/**
	 * 总页数
	 */
	private int totalPage;
	/**
	 * 起始行数
	 */
    private int startIndex;
    /**
     * 结束行数
     */
    private int lastIndex;
	
	/**
	 * 数据
	 */
	@SuppressWarnings("rawtypes")
	private List list ;
	
	/**
	 * 当前页记录数
	 */
	private int currPageListCnt;
	
	/**
	 * 默认构造
	 */
	public Page() {
		
	}


	public int getCurrentPage() {
		return currentPage;
	}


	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}


	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public int getTotalRows() {
		return totalRows;
	}


	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}


	public int getTotalPage() {
		return totalPage;
	}


	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}


	public int getStartIndex() {
		return startIndex;
	}


	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}


	public int getLastIndex() {
		return lastIndex;
	}


	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}


	@SuppressWarnings("rawtypes")
	public List getList() {
		return list;
	}


	@SuppressWarnings("rawtypes")
	public void setList(List list) {
		this.list = list;
		if(list != null) {
			currPageListCnt = list.size();
		}
	}
	public int getCurrPageListCnt() {
		return currPageListCnt;
	}

	public void setCurrPageListCnt(int currPageListCnt) {
		this.currPageListCnt = currPageListCnt;
	}
	
	public Map<String, Object> toMap() {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("currentPage", currentPage);
		retMap.put("pageSize", pageSize);
		retMap.put("totalRows", totalRows);
		retMap.put("totalPage", totalPage);
		retMap.put("startIndex", startIndex);
		retMap.put("lastIndex", lastIndex);
		retMap.put("list", list);
		retMap.put("currPageListCnt", currPageListCnt);
		
		return retMap;
	}
	
}