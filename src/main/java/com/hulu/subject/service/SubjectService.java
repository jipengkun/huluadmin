package com.hulu.subject.service;

import com.hulu.subject.bean.SubjectBean;
import com.hulu.subject.dao.SubjectDao;
import com.hulu.util.RedisUtil;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.Subject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SubjectService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SubjectDao subjectDao;

	@Autowired
	private RedisUtil redisUtil;

	@Transactional(propagation= Propagation.REQUIRED)
	public int addSubject(SubjectBean subjectBean){
		int result = subjectDao.insert(subjectBean);
		updateRedisSubjectList();
		return result;
	}

	@Transactional(propagation= Propagation.REQUIRED, readOnly = true)
	public List<SubjectBean> getAllSubject(){
		List<SubjectBean> subjectList = null;
		if(redisUtil.exists(SubjectBean.SUBJECT_ALL_LIST)){
			subjectList = (List<SubjectBean>)redisUtil.getJsonList(SubjectBean.SUBJECT_ALL_LIST,
					new TypeReference<List<SubjectBean>>(){});
		}else{
			subjectList = subjectDao.list();
			updateRedisSubjectList();
		}
		return subjectList;
	}

	@Transactional(propagation= Propagation.REQUIRED, readOnly = true)
	public Map<Integer,SubjectBean> getAllSubjectToMap(){
		List<SubjectBean> subjectList = this.getAllSubject();
		Map<Integer,SubjectBean> subjectBeanMap = new HashMap<Integer,SubjectBean>();
		for(SubjectBean subjectBean : subjectList){
			subjectBeanMap.put(subjectBean.getId(), subjectBean);
		}
		return subjectBeanMap;
	}

	@Transactional(propagation= Propagation.REQUIRED, readOnly = true)
	public SubjectBean getSubjectById(int id){
		return this.getAllSubjectToMap().get(id);
	}

	@Transactional(propagation= Propagation.REQUIRED)
	public int deleteSubjectById(int id){
		int result = this.subjectDao.delete(id);
		updateRedisSubjectList();
		return result;
	}

	@Transactional(propagation= Propagation.REQUIRED)
	public int updateSubject(SubjectBean subjectBean){
		int result = this.subjectDao.update(subjectBean);
		updateRedisSubjectList();
		return result;
	}

	@Transactional(propagation= Propagation.REQUIRED, readOnly = true)
	public boolean updateRedisSubjectList(){
		return redisUtil.set(SubjectBean.SUBJECT_ALL_LIST, subjectDao.list());
	}

	@Transactional(propagation= Propagation.REQUIRED, readOnly = true)
	public SubjectBean findByName(String subjectName){
		return this.subjectDao.findByName(subjectName);
	}

}
