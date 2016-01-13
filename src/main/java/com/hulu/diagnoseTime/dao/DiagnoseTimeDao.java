package com.hulu.diagnoseTime.dao;

import com.hulu.base.core.dao.BaseDao;
import com.hulu.base.core.dao.Page;
import com.hulu.diagnoseTime.bean.DiagnoseTimeBean;
import com.hulu.diagnoseTime.exception.CannotUpdateDiagnoseNumBalace;
import com.hulu.doctor.bean.DoctorBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.sql.Date;
import java.util.*;


@Repository
public class DiagnoseTimeDao extends BaseDao{

	public int insert(final DiagnoseTimeBean diagnoseTimeBean) {
		final String sql = "INSERT INTO t_diagnose_time (doctor_id,vis_date,vis_time,totality,num_balance," +
				"update_time) VALUES (?,?,?,?,?,now())";
		return jt.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, diagnoseTimeBean.getDoctorId());
				preparedStatement.setDate(2, new Date(diagnoseTimeBean.getVisDate().getTime()));
				preparedStatement.setInt(3, diagnoseTimeBean.getVisTime());
				preparedStatement.setInt(4, diagnoseTimeBean.getTotality());
				preparedStatement.setInt(5, diagnoseTimeBean.getTotality());
				return preparedStatement;
			}
		});
	}

	public int delete(int id){
		final String sql = "DELETE FROM t_diagnose_time WHERE id = ?";
		return jt.update(sql, new Object[]{id});
	}

	public int update(final DiagnoseTimeBean diagnoseTimeBean) {
		final String sql = "UPDATE t_diagnose_time SET doctor_id = ?,vis_date = ?,vis_time = ?,totality = ?,num_balance = ?," +
				"update_time = now() WHERE id = ?";
		return jt.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, diagnoseTimeBean.getDoctorId());
				preparedStatement.setDate(2, new Date(diagnoseTimeBean.getVisDate().getTime()));
				preparedStatement.setInt(3, diagnoseTimeBean.getVisTime());
				preparedStatement.setInt(4, diagnoseTimeBean.getTotality());
				preparedStatement.setInt(5, diagnoseTimeBean.getNumBalance());
                preparedStatement.setInt(6, diagnoseTimeBean.getId());
                return preparedStatement;
			}
		});
	}

	public List<DiagnoseTimeBean> list(){
		final String sql = "SELECT tdt.id,tdt.doctor_id,tdt.vis_date,tdt.vis_time,tdt.totality,tdt.num_balance,tdt.update_time," +
				"td.doctor_name,td.doctor_type,td.doctor_subject,td.hospital_id,td.doctor_adept," +
				"td.doctor_introduction,td.price,td.doctor_headpic,td.address,td.updatetime,td.mobile " +
				"FROM t_diagnose_time tdt " +
				"LEFT JOIN t_doctor td ON tdt.doctor_id = td.id ";
		return jt.query(sql, new DiagnoseTimeFullMapper());
	}

	public Page queryForPage(int pageNum, int pageSize, Map<String,String[]> param){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT tdt.id,tdt.doctor_id,tdt.vis_date,tdt.vis_time,tdt.totality,tdt.num_balance,tdt.update_time,");
		sql.append("td.doctor_name,td.doctor_type,td.doctor_subject,td.hospital_id,td.doctor_adept,");
		sql.append("td.doctor_introduction,td.price,td.doctor_headpic,td.address,td.updatetime,td.mobile " );
		sql.append("FROM t_diagnose_time tdt ");
		sql.append("LEFT JOIN t_doctor td ON tdt.doctor_id = td.id ");
		sql.append("WHERE td.id IS NOT NULL ");
		List<Object> paramList = new ArrayList<Object>();

		if (param.get("serchByDoctorName") != null && StringUtils.isNotBlank(param.get("serchByDoctorName")[0])){
			sql.append("AND td.doctor_name like ? ");
			paramList.add("%" + param.get("serchByDoctorName")[0].trim() + "%");
		}
		if (param.get("serchByDiagnoseTimeState") != null && StringUtils.isNotBlank(param.get("serchByDiagnoseTimeState")[0])){
			int diagnoseTimeState = Integer.parseInt(param.get("serchByDiagnoseTimeState")[0]);
			if(diagnoseTimeState != 1){
				String tomorroyDateTime = DateFormatUtils.format(new java.util.Date(), "yyyy-MM-dd");
				if(diagnoseTimeState == 2){
					sql.append("AND tdt.vis_date >= ? ");
				}else if(diagnoseTimeState == 3){
					sql.append("AND tdt.vis_date < ? ");
				}
				paramList.add(tomorroyDateTime);
			}
		}
		return this.queryForPage(sql.toString(), paramList.toArray(), pageNum, pageSize, new DiagnoseTimeFullMapper());
	}

	public DiagnoseTimeBean get(int id){
		final String sql = "SELECT tdt.id,tdt.doctor_id,tdt.vis_date,tdt.vis_time,tdt.totality,tdt.num_balance,tdt.update_time," +
				"td.doctor_name,td.doctor_type,td.doctor_subject,td.hospital_id,td.doctor_adept," +
				"td.doctor_introduction,td.price,td.doctor_headpic,td.address,td.updatetime,td.mobile " +
				"FROM t_diagnose_time tdt " +
				"LEFT JOIN t_doctor td ON tdt.doctor_id = td.id " +
				"WHERE tdt.id = ?";
		return jt.queryForObject(sql, new Object[]{id}, new DiagnoseTimeFullMapper());
	}

	public List<DiagnoseTimeBean> findByDoctorId(int id){
		final String sql = "SELECT tdt.id,tdt.doctor_id,tdt.vis_date,tdt.vis_time,tdt.totality,tdt.num_balance,tdt.update_time," +
				"td.doctor_name,td.doctor_type,td.doctor_subject,td.hospital_id,td.doctor_adept," +
				"td.doctor_introduction,td.price,td.doctor_headpic,td.address,td.updatetime,td.mobile " +
				"FROM t_diagnose_time tdt " +
				"LEFT JOIN t_doctor td ON tdt.doctor_id = td.id " +
				"WHERE tdt.doctor_id = ?";
		return jt.query(sql, new Object[]{id}, new DiagnoseTimeFullMapper());
	}

	public DiagnoseTimeBean findByDoctorIdAnd(java.util.Date diagnoseTimeVisDate,int id){
		final String sql = "SELECT * FROM  t_diagnose_time " +
				"WHERE doctor_id = ? and vis_date = ?";
		List<DiagnoseTimeBean> diagnoseTimeBeanList = jt.query(sql,
				new Object[]{id, diagnoseTimeVisDate}, new DiagnoseTimeMapper());
		if(diagnoseTimeBeanList != null && diagnoseTimeBeanList.size() > 0){
			return diagnoseTimeBeanList.get(0);
		}
		return null;
	}

	/**
	 * 订单状态发生变化后将该订单对应的出诊记录名额总数减去支付中、支付成功、未支付的订单总和
	 * @param diagnoseId
	 * @return
	 */
	public int updateDiagnoseNumbalance (int diagnoseId) {
		final String sql = "UPDATE t_diagnose_time dt SET dt.`num_balance` = dt.`totality` - (" +
				"SELECT COUNT(o.`id`) FROM `t_order` o WHERE o.`time_id` = dt.`id` AND (o.`paystat` = 1 OR o.`paystat` = 3 OR o.`paystat` = 4)" +
				") WHERE dt.`id` = ? AND dt.`totality` - (" +
				"SELECT COUNT(o.`id`) FROM `t_order` o WHERE o.`time_id` = dt.`id` AND (o.`paystat` = 1 OR o.`paystat` = 3 OR o.`paystat` = 4)" +
				") > -1;";
		return jt.update(sql, diagnoseId);
	}

	protected class DiagnoseTimeMapper implements RowMapper<DiagnoseTimeBean> {
		public DiagnoseTimeBean mapRow(ResultSet rs, int i) throws SQLException {
			DiagnoseTimeBean diagnoseTimeBean = new DiagnoseTimeBean();
			diagnoseTimeBean.setDoctorId(rs.getInt("doctor_id"));
			diagnoseTimeBean.setId(rs.getInt("id"));
			diagnoseTimeBean.setNumBalance(rs.getInt("num_balance"));
			diagnoseTimeBean.setTotality(rs.getInt("totality"));
			diagnoseTimeBean.setUpdateTime(rs.getDate("update_time"));
			diagnoseTimeBean.setVisDate(rs.getDate("vis_date"));
			diagnoseTimeBean.setVisTime(rs.getInt("vis_time"));
			return diagnoseTimeBean;
		}
	}

	protected class DiagnoseTimeFullMapper implements RowMapper<DiagnoseTimeBean> {
		public DiagnoseTimeBean mapRow(ResultSet rs, int i) throws SQLException {
			DiagnoseTimeBean diagnoseTimeBean = new DiagnoseTimeBean();
			diagnoseTimeBean.setDoctorId(rs.getInt("tdt.doctor_id"));
			diagnoseTimeBean.setId(rs.getInt("tdt.id"));
			diagnoseTimeBean.setNumBalance(rs.getInt("tdt.num_balance"));
			diagnoseTimeBean.setTotality(rs.getInt("tdt.totality"));
			diagnoseTimeBean.setUpdateTime(rs.getDate("tdt.update_time"));
			diagnoseTimeBean.setVisDate(rs.getDate("tdt.vis_date"));
			diagnoseTimeBean.setVisTime(rs.getInt("tdt.vis_time"));

			DoctorBean doctorBean = new DoctorBean();
			doctorBean.setAddress(rs.getString("td.address"));
			doctorBean.setDoctorAdept(rs.getString("td.doctor_adept"));
			doctorBean.setDoctorHeadpic(rs.getString("td.doctor_headpic"));
			doctorBean.setDoctorIntroduction(rs.getString("td.doctor_introduction"));
			doctorBean.setDoctorName(rs.getString("td.doctor_name"));
			doctorBean.setSubjectId(rs.getInt("td.doctor_subject"));
			doctorBean.setDoctorType(rs.getInt("td.doctor_type"));
			doctorBean.setHospitalId(rs.getInt("td.hospital_id"));
			doctorBean.setId(diagnoseTimeBean.getDoctorId());
			doctorBean.setMobile(rs.getString("td.mobile"));
			doctorBean.setPrice(rs.getDouble("td.price"));
			doctorBean.setUpdateTime(rs.getDate("td.updatetime"));
			diagnoseTimeBean.setDoctorBean(doctorBean);

			return diagnoseTimeBean;
		}

	}

}
