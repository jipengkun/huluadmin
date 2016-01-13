package com.hulu.hospital.dao;

import com.hulu.base.core.dao.BaseDao;
import com.hulu.hospital.bean.HospitalBean;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;


@Repository
public class HospitalDao extends BaseDao{

	public int insert(final HospitalBean hospitalBean) {
		final String sql = "INSERT INTO t_hospital (name,address,description,arbitration,updatetime) " +
				"VALUES (?,?,?,?,now())";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jt.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement preparedStatement = connection.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, hospitalBean.getName());
				preparedStatement.setString(2, hospitalBean.getAddress());
				preparedStatement.setString(3, hospitalBean.getDescription());
				preparedStatement.setString(4, hospitalBean.getArbitration());
				return preparedStatement;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	public int delete(int id){
		final String sql = "DELETE FROM t_hospital WHERE id = ?";
		return jt.update(sql, new Object[]{id});
	}

	public int update(final HospitalBean hospitalBean) {
		final String sql = "UPDATE t_hospital SET name = ?,address = ?,description = ?,arbitration = ?,updatetime = now() WHERE id = ?";
		return jt.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, hospitalBean.getName());
				preparedStatement.setString(2, hospitalBean.getAddress());
				preparedStatement.setString(3, hospitalBean.getDescription());
				preparedStatement.setString(4, hospitalBean.getArbitration());
				preparedStatement.setInt(5, hospitalBean.getId());
				return preparedStatement;
			}
		});
	}

	public List<HospitalBean> list(){
		final String sql = "SELECT * FROM t_hospital";
		return jt.query(sql, new HospitalBeanMapper());
	}

	public HospitalBean get(int id){
		final String sql = "SELECT * FROM t_hospital WHERE id = ?";
		return jt.queryForObject(sql, new Object[]{id}, new HospitalBeanMapper());
	}

	public HospitalBean findByName(final String hospitalName){
		final String sql = "SELECT * FROM t_hospital WHERE name = ?";
		List<HospitalBean> hospitalBeanList = jt.query(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, hospitalName);
				return preparedStatement;
			}
		}, new HospitalBeanMapper());
		if(hospitalBeanList != null &&  hospitalBeanList.size() > 0){
			return  hospitalBeanList.get(0);
		}
		return null;
	}

	protected class HospitalBeanMapper implements RowMapper<HospitalBean> {
		public HospitalBean mapRow(ResultSet rs, int i) throws SQLException {
			HospitalBean hospitalBean = new HospitalBean();
			hospitalBean.setId(rs.getInt("id"));
			hospitalBean.setAddress(rs.getString("address"));
			hospitalBean.setArbitration(rs.getString("arbitration"));
			hospitalBean.setDescription(rs.getString("description"));
			hospitalBean.setName(rs.getString("name"));
			hospitalBean.setUpdatetime(rs.getDate("updatetime"));
			return hospitalBean;
		}

	}


}
