package com.hulu.doctor.web;

import com.hulu.base.core.dao.Page;
import com.hulu.base.core.web.BaseAction;
import com.hulu.base.eazyui.bean.EasyUIComboBox;
import com.hulu.base.eazyui.bean.EasyUIDateGrid;
import com.hulu.base.springmvc.annotation.Login;
import com.hulu.doctor.bean.DoctorBean;
import com.hulu.doctor.service.DoctorService;
import com.hulu.hospital.bean.HospitalBean;
import com.hulu.util.JsonUtils;
import com.hulu.util.OSSObjectUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("doctor")
public class DoctorAction extends BaseAction {
	@Autowired
	private DoctorService doctorService;

	@Login(Login.ResultTypeEnum.page)
	@RequestMapping("/toListPage")
	public String toListPage() {
		logger.debug("redirect to /doctor/list.jsp");
		return "doctor/list";
	}

	@Login(Login.ResultTypeEnum.json)
	@RequestMapping("/add")
	@ResponseBody
	public String add(DoctorBean doctorBean) {
		this.doctorService.addDoctor(doctorBean);
		return "success";
	}

	@Login(Login.ResultTypeEnum.json)
	@RequestMapping("/uploadDoctorHeadImg")
	@ResponseBody
	public String uploadDoctorHeadImg(HttpServletRequest request,HttpServletResponse response,MultipartFile doctorHeadImg) {
		StringBuilder sbResult = new StringBuilder();
		try {
			// 获取文件名
			String filename = doctorHeadImg.getOriginalFilename();
			// 保存后的文件名
			String fileNamePrefix = DateFormatUtils.format(new Date(),"yyyyMMddHHmmssSSS");
			String saveName = fileNamePrefix + filename.substring(filename.lastIndexOf("."));

			OSSObjectUtils.uploadFile(OSSObjectUtils.BUCKET, saveName, doctorHeadImg.getSize(),
					doctorHeadImg.getInputStream(), OSSObjectUtils.DOCTOR_IMAGE_FLODER);

			sbResult.append("{");
			sbResult.append("state:\"success\"");
			sbResult.append(",msg:\"文件大小:" + doctorHeadImg.getSize() + ",文件名:" + filename + "\"");
			sbResult.append(",fileName:\""+ saveName +"\"");
			sbResult.append("}");
		}catch (Exception e) {
			logger.error("上传医生头像发生错误！", e);
			sbResult.append("{");
			sbResult.append("state:\"error\"");
			sbResult.append(",error:\"上传医生头像发生错误！\"");
			sbResult.append("}");
		}
		return sbResult.toString();
	}

	@Login(Login.ResultTypeEnum.json)
	@RequestMapping("/validateMobile")
	@ResponseBody
	public String validateMobile(String mobile,@RequestParam(value="doctorId", defaultValue="-1")int doctorId){
		DoctorBean doctorBean = this.doctorService.findByMobile(mobile);
		if(doctorBean != null && doctorBean.getId() != doctorId){
			return "false";
		}else{
			return "true";
		}
	}

	@Login(Login.ResultTypeEnum.json)
	@RequestMapping("/showList")
	@ResponseBody
	public String showList() {
		EasyUIDateGrid easyUIDateGrid = new EasyUIDateGrid(doctorService.getAllDoctor());
		return JsonUtils.ObjtoJson(easyUIDateGrid);
	}

	@Login(Login.ResultTypeEnum.json)
	@RequestMapping("/showDoctorNameComboBoxList")
	@ResponseBody
	public String showDoctorNameComboBoxList() {
		List<EasyUIComboBox> easyUIComboBoxList = new ArrayList<EasyUIComboBox>();
		List<String> doctorNameList = this.doctorService.getDoctorNameList();
		for(String doctorName : doctorNameList){
			EasyUIComboBox easyUIComboBox = new EasyUIComboBox(doctorName,doctorName);
			easyUIComboBoxList.add(easyUIComboBox);
		}
		return JsonUtils.ObjtoJson(easyUIComboBoxList);
	}

	@Login(Login.ResultTypeEnum.json)
	@RequestMapping("/showListForPage")
	@ResponseBody
	public String showListForPage(int page, int rows) {
		Page doctorPage = this.doctorService.getDoctorListForPage(page,rows);
		EasyUIDateGrid easyUIDateGrid = new EasyUIDateGrid(doctorPage.getList(), doctorPage.getTotalRows());
		return JsonUtils.ObjtoJson(easyUIDateGrid);
	}

	@Login(Login.ResultTypeEnum.json)
	@RequestMapping("/getInfoById")
	@ResponseBody
	public String getInfoById(int id) {
		DoctorBean hospitalBean = this.doctorService.getDoctorById(id);
		return JsonUtils.ObjtoJson(hospitalBean);
	}

	@Login(Login.ResultTypeEnum.json)
	@RequestMapping("/update")
	@ResponseBody
	public String update(DoctorBean doctorBean) {
		this.doctorService.updateDoctor(doctorBean);
		return "success";
	}

	@Login(Login.ResultTypeEnum.json)
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(int id) {
		this.doctorService.deleteDoctorById(id);
		return "success";
	}
}
