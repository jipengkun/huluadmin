package com.hulu.doctor.dao;

import com.hulu.base.core.dao.BaseDao;
import com.hulu.base.core.dao.Page;
import com.hulu.doctor.bean.DoctorBean;
import com.hulu.hospital.bean.HospitalBean;
import com.hulu.subject.bean.SubjectBean;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


@Repository
public class DoctorDao extends BaseDao{

	public int insert(final DoctorBean doctorBean) {
		final String sql = "INSERT INTO t_doctor (doctor_name,doctor_type,doctor_subject,hospital_id,doctor_adept," +
				"doctor_introduction,price,doctor_headpic,address,updatetime,mobile,address_longitude,address_latitude) VALUES (?,?,?,?,?,?,?,?,?,now(),?,?,?)";
		return jt.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, doctorBean.getDoctorName());
				preparedStatement.setInt(2, doctorBean.getDoctorType());
				preparedStatement.setInt(3, doctorBean.getSubjectId());
				preparedStatement.setInt(4, doctorBean.getHospitalId());
				preparedStatement.setString(5, doctorBean.getDoctorAdept());
				preparedStatement.setString(6, doctorBean.getDoctorIntroduction());
				preparedStatement.setDouble(7, doctorBean.getPrice());
				preparedStatement.setString(8, doctorBean.getDoctorHeadpic());
				preparedStatement.setString(9, doctorBean.getAddress());
				preparedStatement.setString(10, doctorBean.getMobile());
				preparedStatement.setDouble(11, doctorBean.getAddressLongitude());
				preparedStatement.setDouble(12, doctorBean.getAddressLatitude());
				return preparedStatement;
			}
		});
	}

	public int delete(int id){
		final String sql = "DELETE FROM t_doctor WHERE id = ?";
		return jt.update(sql, new Object[]{id});
	}

	public int update(final DoctorBean doctorBean) {
		final String sql = "UPDATE t_doctor SET doctor_name = ?,doctor_type = ?,doctor_subject = ?,hospital_id = ?,doctor_adept = ?," +
				"doctor_introduction = ?,price = ?,doctor_headpic = ?,address = ?,updatetime = now(),mobile = ?, " +
				"address_longitude = ?,address_latitude = ? WHERE id = ?";
		return jt.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, doctorBean.getDoctorName());
				preparedStatement.setInt(2, doctorBean.getDoctorType());
				preparedStatement.setInt(3, doctorBean.getSubjectId());
				preparedStatement.setInt(4, doctorBean.getHospitalId());
				preparedStatement.setString(5, doctorBean.getDoctorAdept());
				preparedStatement.setString(6, doctorBean.getDoctorIntroduction());
				preparedStatement.setDouble(7, doctorBean.getPrice());
				preparedStatement.setString(8, doctorBean.getDoctorHeadpic());
				preparedStatement.setString(9, doctorBean.getAddress());
				preparedStatement.setString(10, doctorBean.getMobile());
				preparedStatement.setDouble(11, doctorBean.getAddressLongitude());
				preparedStatement.setDouble(12, doctorBean.getAddressLatitude());
                preparedStatement.setInt(13, doctorBean.getId());
                return preparedStatement;
			}
		});
	}

	public List<DoctorBean> list(){
		final String sql = "SELECT td.id,td.doctor_name,td.doctor_type,td.doctor_subject,td.hospital_id,td.doctor_adept," +
				"td.doctor_introduction,td.price,td.doctor_headpic,td.address AS doctorAddress,td.updatetime AS doctorUpdatetime," +
				"td.mobile,td.address_longitude,td.address_latitude," +
				"ts.subject_name," +
				"th.name,th.address AS hospitalAddress,th.description,th.arbitration,th.updatetime AS hospitalUpdatetime " +
				"FROM t_doctor td " +
				"LEFT JOIN t_subject ts ON td.doctor_subject = ts.id " +
				"LEFT JOIN t_hospital th ON td.hospital_id = th.id";
		return jt.query(sql, new DoctorBeanFullMapper());
	}

	public List<Map<String,Object>> getSingleColumnList(String columnName){
		final String sql = "SELECT "+columnName+" FROM t_doctor";
		return jt.queryForList(sql);
	}

	public Page listForPage(int pageNum, int pageSize){
		final String sql = "SELECT td.id,td.doctor_name,td.doctor_type,td.doctor_subject,td.hospital_id,td.doctor_adept," +
				"td.doctor_introduction,td.price,td.doctor_headpic,td.address AS doctorAddress,td.updatetime AS doctorUpdatetime," +
				"td.mobile,td.address_longitude,td.address_latitude," +
				"ts.subject_name," +
				"th.name,th.address AS hospitalAddress,th.description,th.arbitration,th.updatetime AS hospitalUpdatetime " +
				"FROM t_doctor td " +
				"LEFT JOIN t_subject ts ON td.doctor_subject = ts.id " +
				"LEFT JOIN t_hospital th ON td.hospital_id = th.id";
		return this.queryForPage(sql, new Object[]{}, pageNum, pageSize, new DoctorBeanFullMapper());
	}

	public DoctorBean get(int id){
		final String sql = "SELECT td.id,td.doctor_name,td.doctor_type,td.doctor_subject,td.hospital_id,td.doctor_adept," +
				"td.doctor_introduction,td.price,td.doctor_headpic,td.address AS doctorAddress,td.updatetime AS doctorUpdatetime," +
				"td.mobile,td.address_longitude,td.address_latitude," +
				"ts.subject_name," +
				"th.name,th.address AS hospitalAddress,th.description,th.arbitration,th.updatetime AS hospitalUpdatetime " +
				"FROM t_doctor td " +
				"LEFT JOIN t_subject ts ON td.doctor_subject = ts.id " +
				"LEFT JOIN t_hospital th ON td.hospital_id = th.id " +
				"WHERE td.id = ?";
		return jt.queryForObject(sql, new Object[]{id}, new DoctorBeanFullMapper());
	}

	public DoctorBean findByMobile(final String mobile){
		final String sql = "SELECT * FROM t_doctor WHERE mobile = ?";
		List<DoctorBean> doctorBeanList = jt.query(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, mobile);
				return preparedStatement;
			}
		}, new DoctorBeanMapper());
		if(doctorBeanList != null &&  doctorBeanList.size() > 0){
			return  doctorBeanList.get(0);
		}
		return null;
	}

	protected class DoctorBeanMapper implements RowMapper<DoctorBean> {
		public DoctorBean mapRow(ResultSet rs, int i) throws SQLException {
			DoctorBean doctorBean = new DoctorBean();
			doctorBean.setAddress(rs.getString("address"));
			doctorBean.setDoctorAdept(rs.getString("doctor_adept"));
			doctorBean.setDoctorHeadpic(rs.getString("doctor_headpic"));
			doctorBean.setDoctorIntroduction(rs.getString("doctor_introduction"));
			doctorBean.setDoctorName(rs.getString("doctor_name"));
			doctorBean.setSubjectId(rs.getInt("doctor_subject"));
			doctorBean.setDoctorType(rs.getInt("doctor_type"));
			doctorBean.setHospitalId(rs.getInt("hospital_id"));
			doctorBean.setId(rs.getInt("id"));
			doctorBean.setMobile(rs.getString("mobile"));
			doctorBean.setPrice(rs.getDouble("price"));
			doctorBean.setUpdateTime(rs.getDate("updatetime"));
			doctorBean.setAddressLongitude(rs.getDouble("address_longitude"));
			doctorBean.setAddressLatitude(rs.getDouble("address_latitude"));

			return doctorBean;
		}

	}

	protected class DoctorBeanFullMapper implements RowMapper<DoctorBean> {
		public DoctorBean mapRow(ResultSet rs, int i) throws SQLException {
			DoctorBean doctorBean = new DoctorBean();
			doctorBean.setAddress(rs.getString("doctorAddress"));
			doctorBean.setDoctorAdept(rs.getString("td.doctor_adept"));
			doctorBean.setDoctorHeadpic(rs.getString("td.doctor_headpic"));
			doctorBean.setDoctorIntroduction(rs.getString("td.doctor_introduction"));
			doctorBean.setDoctorName(rs.getString("td.doctor_name"));
			doctorBean.setSubjectId(rs.getInt("td.doctor_subject"));
			doctorBean.setDoctorType(rs.getInt("td.doctor_type"));
			doctorBean.setHospitalId(rs.getInt("td.hospital_id"));
			doctorBean.setId(rs.getInt("td.id"));
			doctorBean.setMobile(rs.getString("td.mobile"));
			doctorBean.setPrice(rs.getDouble("td.price"));
			doctorBean.setUpdateTime(rs.getDate("doctorUpdatetime"));
			doctorBean.setAddressLongitude(rs.getDouble("td.address_longitude"));
			doctorBean.setAddressLatitude(rs.getDouble("td.address_latitude"));

			SubjectBean subjectBean = new SubjectBean();
			subjectBean.setId(doctorBean.getSubjectId());
			subjectBean.setSubjectName(rs.getString("ts.subject_name"));
			doctorBean.setSubject(subjectBean);

            HospitalBean hospitalBean = new HospitalBean();
            hospitalBean.setId(doctorBean.getHospitalId());
            hospitalBean.setAddress(rs.getString("hospitalAddress"));
            hospitalBean.setArbitration(rs.getString("th.arbitration"));
            hospitalBean.setDescription(rs.getString("th.description"));
            hospitalBean.setName(rs.getString("th.name"));
            hospitalBean.setUpdatetime(rs.getDate("hospitalUpdatetime"));
            doctorBean.setHospitalBean(hospitalBean);

			return doctorBean;
		}

	}

}
