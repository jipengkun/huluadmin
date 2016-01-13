package com.hulu.system.core;

import com.hulu.hospital.service.HospitalService;
import com.hulu.subject.service.SubjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by huangyiwei on 15/10/27.
 */
@Service
public class InitRedisCache {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private SubjectService subjectService;

    @PostConstruct
    public void init(){
        logger.info("开始初始化Redis缓存...");
        try {
            boolean hospitalSuccess = hospitalService.updateRedisHospitalList();
            logger.info("初始化医院缓存成功!");
        } catch (Exception e) {
            logger.info("初始化医院缓存失败!", e);
        }
        try {
            boolean subjectSuccess = subjectService.updateRedisSubjectList();
            logger.info("初始化科室缓存成功！");
        } catch (Exception e) {
            logger.info("初始化科室缓存失败！", e);
        }
        logger.info("初始化Redis缓存完成！");
    }
}
