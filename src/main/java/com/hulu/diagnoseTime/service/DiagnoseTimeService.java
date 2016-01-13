package com.hulu.diagnoseTime.service;

import com.hulu.base.core.dao.Page;
import com.hulu.diagnoseTime.bean.DiagnoseTimeBean;
import com.hulu.diagnoseTime.dao.DiagnoseTimeDao;
import com.hulu.diagnoseTime.exception.CannotUpdateDiagnoseNumBalace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class DiagnoseTimeService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DiagnoseTimeDao diagnoseTimeDao;

	@Transactional(propagation= Propagation.REQUIRED)
	public int addDiagnoseTime(DiagnoseTimeBean diagnoseTimeBean) {
		return diagnoseTimeDao.insert(diagnoseTimeBean);
	}

	@Transactional(propagation= Propagation.REQUIRED)
	public int deleteDiagnoseTimeById(int id){
		return diagnoseTimeDao.delete(id);
	}

	@Transactional(propagation= Propagation.REQUIRED)
	public int updateDiagnoseTime(DiagnoseTimeBean diagnoseTimeBean) {
		return diagnoseTimeDao.update(diagnoseTimeBean);
	}

	@Transactional(propagation= Propagation.REQUIRED, readOnly = true)
	public List<DiagnoseTimeBean> getDiagnoseTimeList(){
		return diagnoseTimeDao.list();
	}

	@Transactional(propagation= Propagation.REQUIRED, readOnly = true)
	public Page getDiagnoseTimeListForPage(int pageNum, int pageSize, Map<String,String[]> param){
		return diagnoseTimeDao.queryForPage(pageNum, pageSize, param);
	}

	@Transactional(propagation= Propagation.REQUIRED, readOnly = true)
	public DiagnoseTimeBean getDiagnoseTimeById(int id){
		return diagnoseTimeDao.get(id);
	}

	@Transactional(propagation= Propagation.REQUIRED, readOnly = true)
	public List<DiagnoseTimeBean> findByDoctorId(int id){
		return diagnoseTimeDao.findByDoctorId(id);
	}

	@Transactional(propagation= Propagation.REQUIRED, readOnly = true)
	public DiagnoseTimeBean findByDoctorIdAnd(Date diagnoseTimeVisDate,int id){
		return diagnoseTimeDao.findByDoctorIdAnd(diagnoseTimeVisDate,id);
	}

	/**
	 * 订单状态发生变化后将该订单对应的出诊记录名额总数减去支付中、支付成功、未支付的订单总和
	 * @param diagnoseId
	 * @return
	 */
	@Transactional(propagation= Propagation.REQUIRED)
	public void updateDiagnoseNumbalance (int diagnoseId) {
		int changeSize = diagnoseTimeDao.updateDiagnoseNumbalance(diagnoseId);
		if(changeSize == 0) {
			throw new CannotUpdateDiagnoseNumBalace();
		}
	}

}
