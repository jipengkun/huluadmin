package com.hulu.hospital.web;

import com.hulu.base.core.web.BaseAction;
import com.hulu.base.eazyui.bean.EasyUIComboBox;
import com.hulu.base.eazyui.bean.EasyUIDateGrid;
import com.hulu.base.springmvc.annotation.Login;
import com.hulu.hospital.bean.HospitalBean;
import com.hulu.hospital.service.HospitalService;
import com.hulu.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("hospital")
public class HospitalAction extends BaseAction {
	@Autowired
	private HospitalService hospitalService;

	@Login(Login.ResultTypeEnum.page)
	@RequestMapping("/toListPage")
	public String toListPage() {
		logger.debug("redirect to /hospital/list.jsp");
		return "hospital/list";
	}

	@Login(Login.ResultTypeEnum.json)
	@RequestMapping("/showList")
	@ResponseBody
	public String showList() {
		EasyUIDateGrid easyUIDateGrid = new EasyUIDateGrid(hospitalService.getAllHospital());
		return JsonUtils.ObjtoJson(easyUIDateGrid);
	}

	@Login(Login.ResultTypeEnum.json)
	@RequestMapping("/showComboBoxList")
	@ResponseBody
	public String showComboBoxList(@RequestParam(value="currentId", defaultValue="-1") int currentId) {
		List<HospitalBean> hospitalBeanList = this.hospitalService.getAllHospital();
		List<EasyUIComboBox> easyUIComboBoxList = new ArrayList<EasyUIComboBox>();
		for(HospitalBean hospitalBean : hospitalBeanList){
			EasyUIComboBox easyUIComboBox = new EasyUIComboBox(hospitalBean.getId() + "", hospitalBean.getName());
			if(currentId  > 0 && hospitalBean.getId() == currentId){
				easyUIComboBox.setSelected(true);
			}
			easyUIComboBoxList.add(easyUIComboBox);
		}
		return JsonUtils.ObjtoJson(easyUIComboBoxList);
	}

	@Login(Login.ResultTypeEnum.json)
	@RequestMapping("/add")
	@ResponseBody
	public String add(HospitalBean hospitalBean) {
		this.hospitalService.addHospital(hospitalBean);
		return "success";
	}

	@Login(Login.ResultTypeEnum.json)
	@RequestMapping("/getInfoById")
	@ResponseBody
	public String getInfoById(int id) {
		HospitalBean hospitalBean = this.hospitalService.getHospitalById(id);
		return JsonUtils.ObjtoJson(hospitalBean);
	}

	@Login(Login.ResultTypeEnum.json)
	@RequestMapping("/update")
	@ResponseBody
	public String update(HospitalBean hospitalBean) {
		this.hospitalService.updateHospital(hospitalBean);
		return "success";
	}

	@Login(Login.ResultTypeEnum.json)
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(int id) {
		this.hospitalService.deleteHospitalById(id);
		return "success";
	}

	@Login(Login.ResultTypeEnum.json)
	@RequestMapping("/validateName")
	@ResponseBody
	public String validateName(String name,@RequestParam(value="hospitalId", defaultValue="-1")int hospitalId){
		HospitalBean hospitalBean = this.hospitalService.findByName(name);
		if(hospitalBean != null && hospitalBean.getId() != hospitalId){
			return "false";
		}else{
			return "true";
		}
	}

}
