package com.hulu.subject.dao;

import com.hulu.base.core.dao.BaseDao;
import com.hulu.subject.bean.SubjectBean;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;


@Repository
public class SubjectDao extends BaseDao{

	public int insert(final SubjectBean subjectBean) {
		final String sql = "INSERT INTO t_subject (subject_name) VALUES (?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jt.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement preparedStatement = connection.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, subjectBean.getSubjectName());
				return preparedStatement;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	public int update(final SubjectBean subjectBean) {
		final String sql = "UPDATE t_subject set subject_name = ? WHERE id = ?";
		return jt.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, subjectBean.getSubjectName());
				preparedStatement.setInt(2, subjectBean.getId());
				return preparedStatement;
			}
		});
	}

	public int delete(int id){
		final String sql = "DELETE FROM t_subject WHERE id = ?";
		return jt.update(sql, new Object[]{id});
	}

	public List<SubjectBean> list(){
		final String sql = "SELECT * FROM t_subject";
		return jt.query(sql, new SubjectBeanMapper());
	}

	public SubjectBean get(int id){
		final String sql = "SELECT * FROM t_subject WHERE id = ?";
		return jt.queryForObject(sql, new Object[]{id}, new SubjectBeanMapper());
	}

	public SubjectBean findByName(final String subjectName){
		final String sql = "SELECT * FROM t_subject WHERE subject_name = ?";
		List<SubjectBean> subjectBeanList = jt.query(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, subjectName);
				return preparedStatement;
			}
		}, new SubjectBeanMapper());
		if(subjectBeanList != null && subjectBeanList.size() > 0){
			return subjectBeanList.get(0);
		}
		return null;
	}

	protected class SubjectBeanMapper implements RowMapper<SubjectBean> {
		public SubjectBean mapRow(ResultSet rs, int i) throws SQLException {
			SubjectBean subjectBean = new SubjectBean();
			subjectBean.setId(rs.getInt("id"));
			subjectBean.setSubjectName(rs.getString("subject_name"));
			return subjectBean;
		}
	}


}
