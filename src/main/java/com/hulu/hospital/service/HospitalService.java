package com.hulu.hospital.service;

import com.hulu.hospital.bean.HospitalBean;
import com.hulu.hospital.dao.HospitalDao;
import com.hulu.subject.bean.SubjectBean;
import com.hulu.util.RedisUtil;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HospitalService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HospitalDao hospitalDao;

	@Autowired
	private RedisUtil redisUtil;

	@Transactional(propagation= Propagation.REQUIRED)
	public int addHospital(HospitalBean hospitalBean){
		int result = hospitalDao.insert(hospitalBean);
		updateRedisHospitalList();
		return result;
	}

	@Transactional(propagation= Propagation.REQUIRED, readOnly = true)
	public List<HospitalBean> getAllHospital(){
		List<HospitalBean> hospitalList = null;
		if(redisUtil.exists(HospitalBean.HOSPITAL_ALL_LIST)){
			hospitalList = (List<HospitalBean>) redisUtil.getJsonList(HospitalBean.HOSPITAL_ALL_LIST,
					new TypeReference<List<HospitalBean>>(){});
		}else{
			hospitalList = hospitalDao.list();
			updateRedisHospitalList();
		}
		return hospitalList;
	}

	@Transactional(propagation= Propagation.REQUIRED, readOnly = true)
	public HospitalBean getHospitalById(int id){
		return this.hospitalDao.get(id);
	}

	@Transactional(propagation= Propagation.REQUIRED)
	public int deleteHospitalById(int id){
		int result = this.hospitalDao.delete(id);
		updateRedisHospitalList();
		return result;
	}

	@Transactional(propagation= Propagation.REQUIRED)
	public int updateHospital(HospitalBean hospitalBean){
		int result = this.hospitalDao.update(hospitalBean);
		updateRedisHospitalList();
		return result;
	}

	@Transactional(propagation= Propagation.REQUIRED, readOnly = true)
	public boolean updateRedisHospitalList(){
		return redisUtil.set(HospitalBean.HOSPITAL_ALL_LIST, hospitalDao.list());
	}

    @Transactional(propagation= Propagation.REQUIRED, readOnly = true)
    public HospitalBean findByName(String hospitalName){
        return this.hospitalDao.findByName(hospitalName);
    }

}
