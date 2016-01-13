package com.hulu.subject.web;

import com.hulu.base.core.web.BaseAction;
import com.hulu.base.eazyui.bean.EasyUIComboBox;
import com.hulu.base.eazyui.bean.EasyUIDateGrid;
import com.hulu.base.springmvc.annotation.Login;
import com.hulu.hospital.bean.HospitalBean;
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

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("subject")
public class SubjectAction extends BaseAction {
	@Autowired
	private SubjectService subjectService;

	@Login(Login.ResultTypeEnum.page)
	@RequestMapping("/toListPage")
	public String toListPage() {
		logger.debug("redirect to /subject/list.jsp");
		return "subject/list";
	}

	@Login(Login.ResultTypeEnum.json)
	@RequestMapping("/showList")
	@ResponseBody
	public String showList() {
		List<SubjectBean> subjectBeanList = this.subjectService.getAllSubject();
		return JsonUtils.ObjtoJson(new EasyUIDateGrid(subjectBeanList));
	}

	@Login(Login.ResultTypeEnum.json)
	@RequestMapping("/showComboBoxList")
	@ResponseBody
	public String showComboBoxList(@RequestParam(value="currentId", defaultValue="-1") int currentId) {
		List<SubjectBean> subjectBeanList = this.subjectService.getAllSubject();
		List<EasyUIComboBox> easyUIComboBoxList = new ArrayList<EasyUIComboBox>();
		for(SubjectBean subjectBean : subjectBeanList){
			EasyUIComboBox easyUIComboBox = new EasyUIComboBox(subjectBean.getId() + "", subjectBean.getSubjectName());
			if(currentId  > 0 && subjectBean.getId() == currentId){
				easyUIComboBox.setSelected(true);
			}
			easyUIComboBoxList.add(easyUIComboBox);
		}
		return JsonUtils.ObjtoJson(easyUIComboBoxList);
	}

	@Login(Login.ResultTypeEnum.json)
	@RequestMapping("/add")
	@ResponseBody
	public String add(SubjectBean subjectBean) {
		this.subjectService.addSubject(subjectBean);
		return "success";
	}

	@Login(Login.ResultTypeEnum.json)
	@RequestMapping("/getInfoById")
	@ResponseBody
	public String getInfoById(int id) {
		SubjectBean subjectBean = this.subjectService.getSubjectById(id);
		return JsonUtils.ObjtoJson(subjectBean);
	}

	@Login(Login.ResultTypeEnum.json)
	@RequestMapping("/update")
	@ResponseBody
	public String update(SubjectBean subjectBean) {
		this.subjectService.updateSubject(subjectBean);
		return "success";
	}

	@Login(Login.ResultTypeEnum.json)
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(int id) {
		this.subjectService.deleteSubjectById(id);
		return "success";
	}

	@Login(Login.ResultTypeEnum.json)
	@RequestMapping("/validateName")
	@ResponseBody
	public String validateName(String name,@RequestParam(value="subjectId", defaultValue="-1")int subjectId){
		SubjectBean subjectBean = this.subjectService.findByName(name);
		if(subjectBean != null && subjectBean.getId() != subjectId){
			return "false";
		}else{
			return "true";
		}
	}
}
