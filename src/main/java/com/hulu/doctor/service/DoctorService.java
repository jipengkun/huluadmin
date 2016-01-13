package com.hulu.doctor.service;

import com.hulu.base.core.dao.Page;
import com.hulu.doctor.bean.DoctorBean;
import com.hulu.doctor.dao.DoctorDao;
import com.hulu.hospital.bean.HospitalBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DoctorService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DoctorDao doctorDao;

	@Transactional(propagation= Propagation.REQUIRED)
	public int addDoctor(DoctorBean doctorBean){
		return doctorDao.insert(doctorBean);
	}

	@Transactional(propagation= Propagation.REQUIRED, readOnly = true)
	public List<DoctorBean> getAllDoctor(){
		List<DoctorBean> hospitalList = doctorDao.list();
		return hospitalList;
	}

	@Transactional(propagation= Propagation.REQUIRED, readOnly = true)
	public Page getDoctorListForPage(int pageNum, int pageSize){
		return doctorDao.listForPage(pageNum, pageSize);
	}

	@Transactional(propagation= Propagation.REQUIRED, readOnly = true)
	public List<String> getDoctorNameList(){
		List<Map<String,Object>> doctorNameMapList = this.doctorDao.getSingleColumnList("doctor_name");
		List<String> doctorList = new ArrayList<String>();
		if(doctorNameMapList != null){
			for(Map<String,Object> doctorNameMap : doctorNameMapList){
				doctorList.add(doctorNameMap.get("doctor_name").toString());
			}
		}
		return doctorList;
	}

	@Transactional(propagation= Propagation.REQUIRED, readOnly = true)
	public DoctorBean getDoctorById(int id){
		return this.doctorDao.get(id);
	}

	@Transactional(propagation= Propagation.REQUIRED)
	public int deleteDoctorById(int id){
		int result = this.doctorDao.delete(id);
		return result;
	}

	@Transactional(propagation= Propagation.REQUIRED)
	public int updateDoctor(DoctorBean doctorBean){
		int result = this.doctorDao.update(doctorBean);
		return result;
	}

	@Transactional(propagation= Propagation.REQUIRED, readOnly = true)
	public DoctorBean findByMobile(String mobile){
		return this.doctorDao.findByMobile(mobile);
	}

}
