package com.hulu.doctor.bean;

import com.hulu.hospital.bean.HospitalBean;
import com.hulu.subject.bean.SubjectBean;

import java.util.Date;

/**
 * 医生 Bean
 * @author pengkunj
 */
public class DoctorBean {
	/**
	 * 医生id
	 */
	private Integer id;
	
	/**
	 * 医生名字
	 */
	private String doctorName;
	
	/**
	 * 医生类型
	 * 1 国医
	 * 2 专家
	 */
	private Integer doctorType;

	/**
	 * 科室ID
	 */
	private Integer subjectId;

	/**
	 * 科室
	 */
	private SubjectBean subject;

	/**
	 * 医院ID
	 */
	private Integer hospitalId;

	/**
	 * 医院
	 */
	private HospitalBean hospitalBean;
	
	/**
	 * 擅长
	 */
	private String doctorAdept;

	/**
	 * 医生简介
	 */
	private String doctorIntroduction;

	/**
	 * 价格
	 */
	private Double price;

	/**
	 * 头像文件名
	 */
	private String doctorHeadpic;
	
	/**
	 * 接诊地址
	 */
	private String address;

	/**
	 * 更新时间
	 */
	private Date updateTime;

    /**
     * 联系电话
     */
	private String mobile;

	/**
	 * 出诊地址经度
	 */
	private Double addressLongitude;

	/**
	 * 出诊地址纬度
	 */
	private Double addressLatitude;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public Integer getDoctorType() {
		return doctorType;
	}

	public void setDoctorType(Integer doctorType) {
		this.doctorType = doctorType;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public Integer getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(Integer hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getDoctorAdept() {
		return doctorAdept;
	}

	public void setDoctorAdept(String doctorAdept) {
		this.doctorAdept = doctorAdept;
	}

	public String getDoctorIntroduction() {
		return doctorIntroduction;
	}

	public void setDoctorIntroduction(String doctorIntroduction) {
		this.doctorIntroduction = doctorIntroduction;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getDoctorHeadpic() {
		return doctorHeadpic;
	}

	public void setDoctorHeadpic(String doctorHeadpic) {
		this.doctorHeadpic = doctorHeadpic;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public SubjectBean getSubject() {
		return subject;
	}

	public void setSubject(SubjectBean subject) {
		this.subject = subject;
	}

	public HospitalBean getHospitalBean() {
		return hospitalBean;
	}

	public void setHospitalBean(HospitalBean hospitalBean) {
		this.hospitalBean = hospitalBean;
	}

	public Double getAddressLongitude() {
		return addressLongitude;
	}

	public void setAddressLongitude(Double addressLongitude) {
		this.addressLongitude = addressLongitude;
	}

	public Double getAddressLatitude() {
		return addressLatitude;
	}

	public void setAddressLatitude(Double addressLatitude) {
		this.addressLatitude = addressLatitude;
	}
}
