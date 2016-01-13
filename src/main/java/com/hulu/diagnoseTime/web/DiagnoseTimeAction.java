package com.hulu.diagnoseTime.web;

import com.hulu.base.core.dao.Page;
import com.hulu.base.core.web.BaseAction;
import com.hulu.base.eazyui.bean.EasyUIDateGrid;
import com.hulu.base.springmvc.annotation.Login;
import com.hulu.diagnoseTime.bean.DiagnoseTimeBean;
import com.hulu.diagnoseTime.service.DiagnoseTimeService;
import com.hulu.subject.bean.SubjectBean;
import com.hulu.subject.service.SubjectService;
import com.hulu.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("diagnoseTime")
public class DiagnoseTimeAction extends BaseAction {
	@Autowired
	private DiagnoseTimeService diagnoseTimeService;

	@Autowired
	private SubjectService subjectService;

	@Login(Login.ResultTypeEnum.page)
	@RequestMapping("/toListPage")
	public String toListPage() {
		logger.debug("redirect to /diagnoseTime/list.jsp");
		return "diagnoseTime/list";
	}

	@Login(Login.ResultTypeEnum.json)
	@ResponseBody
	@RequestMapping("/showList")
	public String showList() {
		List<DiagnoseTimeBean> diagnoseTimeBeanList = this.diagnoseTimeService.getDiagnoseTimeList();
		Map<Integer,SubjectBean> subjectBeanMap = this.subjectService.getAllSubjectToMap();
		if(diagnoseTimeBeanList != null){
			for(DiagnoseTimeBean diagnoseTimeBean : diagnoseTimeBeanList){
				diagnoseTimeBean.getDoctorBean().setSubject(
						subjectBeanMap.get(diagnoseTimeBean.getDoctorBean().getSubjectId())
				);
			}
		}
		return JsonUtils.ObjtoJson(new EasyUIDateGrid(diagnoseTimeBeanList));
	}

	@Login(Login.ResultTypeEnum.json)
	@ResponseBody
	@RequestMapping("/showListForPage")
	public String showListForPage(int page, int rows, HttpServletRequest request) {
		Page diagnoseTimePage = this.diagnoseTimeService.getDiagnoseTimeListForPage(page, rows, request.getParameterMap());
		List<DiagnoseTimeBean> diagnoseTimeBeanList = diagnoseTimePage.getList();
		Map<Integer,SubjectBean> subjectBeanMap = this.subjectService.getAllSubjectToMap();
		if(diagnoseTimeBeanList != null){
			for(DiagnoseTimeBean diagnoseTimeBean : diagnoseTimeBeanList){
				diagnoseTimeBean.getDoctorBean().setSubject(
						subjectBeanMap.get(diagnoseTimeBean.getDoctorBean().getSubjectId())
				);
			}
		}
		return JsonUtils.ObjtoJson(new EasyUIDateGrid(diagnoseTimeBeanList, diagnoseTimePage.getTotalRows()));
	}

	@Login(Login.ResultTypeEnum.json)
	@RequestMapping("/add")
	@ResponseBody
	public String add(DiagnoseTimeBean diagnoseTimeBean) {
		this.diagnoseTimeService.addDiagnoseTime(diagnoseTimeBean);
		return "success";
	}

	@Login(Login.ResultTypeEnum.json)
	@RequestMapping("/validateDiagnoseTime")
	@ResponseBody
	public String validateDiagnoseTime(Date diagnoseTimeVisDate,@RequestParam(value="doctorId", defaultValue="-1")int doctorId){
		DiagnoseTimeBean doctorBean = diagnoseTimeService.findByDoctorIdAnd(diagnoseTimeVisDate,doctorId);
		if(doctorBean != null && doctorBean.getId() != doctorId){
			return "false";
		}else{
			return "true";
		}
	}

	@Login(Login.ResultTypeEnum.json)
	@RequestMapping("/showListByDoctorId")
	@ResponseBody
	public String showListByDoctorId(int doctorId) {
		EasyUIDateGrid easyUIDateGrid = new EasyUIDateGrid(diagnoseTimeService.findByDoctorId(doctorId));
		return JsonUtils.ObjtoJson(easyUIDateGrid);
	}

	@Login(Login.ResultTypeEnum.json)
	@RequestMapping("/getInfoById")
	@ResponseBody
	public String getInfoById(int id) {
		DiagnoseTimeBean diagnoseTimeBean = this.diagnoseTimeService.getDiagnoseTimeById(id);
		return JsonUtils.ObjtoJson(diagnoseTimeBean);
	}

	@Login(Login.ResultTypeEnum.json)
	@RequestMapping("/update")
	@ResponseBody
	public String update(DiagnoseTimeBean diagnoseTimeBean) {
		this.diagnoseTimeService.updateDiagnoseTime(diagnoseTimeBean);
		return "success";
	}

	@Login(Login.ResultTypeEnum.json)
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(int id) {
		this.diagnoseTimeService.deleteDiagnoseTimeById(id);
		return "success";
	}
}
