package com.hulu.order.dao;

import com.hulu.base.core.dao.BaseDao;
import com.hulu.order.bean.OrderBean;
import com.hulu.order.bean.OrderPayStateEnum;
import com.hulu.subject.bean.SubjectBean;
import com.hulu.user.bean.UserBean;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


@Repository
public class OrderDao extends BaseDao{

	public int delete(int id){
		final String sql = "DELETE FROM t_order WHERE id = ?";
		return jt.update(sql, new Object[]{id});
	}

	public int updateOrderPayState(int orderId, int paystat) {
		final String sql = "UPDATE t_order SET paystat = ? WHERE id = ?";
		return jt.update(sql, new Object[] {paystat,orderId});
	}

	public List<OrderBean> list(){
		final String sql = "SELECT * FROM t_order";
		return jt.query(sql, new OrderBeanMapper());
	}

	public OrderBean get(final int id){
		final String sql = "SELECT * FROM t_order WHERE id = ?";
		List<OrderBean> orderBeanList = jt.query(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, id);
				return preparedStatement;
			}
		}, new OrderBeanMapper());
		if(orderBeanList != null && orderBeanList.size() > 0){
			return orderBeanList.get(0);
		}
		return null;
	}

	public OrderBean findFirstOrderByTimeId(final int timeId){
		final String sql = "SELECT * FROM t_order WHERE time_id = ? AND paystat != 3 ORDER BY create_time LIMIT 0,1";
		List<OrderBean> orderBeanList = jt.query(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, timeId);
				return preparedStatement;
			}
		}, new OrderBeanMapper());
		if(orderBeanList != null && orderBeanList.size() > 0){
			return orderBeanList.get(0);
		}
		return null;
	}

	public List<OrderBean> findOrderByParam(Map<String,Object> param){
		List paramValue = new ArrayList();
		StringBuilder sql = new StringBuilder("SELECT * FROM t_order t WHERE 1=1 ");

		StringBuilder paystateSql = new StringBuilder("");
		for(String key : param.keySet()){
			if(key.startsWith("paystat")){
				if(paystateSql.toString().equals("")){
					paystateSql.append("AND (t.`paystat` = ? ");
				}else{
					paystateSql.append("OR t.`paystat` = ? ");
				}
				paramValue.add(param.get(key));
			}
		}
		if(!paystateSql.toString().equals("")){
			paystateSql.append(")");
			sql.append(paystateSql);
		}

		return jt.query(sql.toString(), paramValue.toArray(), new OrderBeanMapper());
	}

	public List<OrderBean> findOrderByTimeIdForPage(final int timeId){
		final String sql = "SELECT torder.id,torder.order_sequence,torder.user_id,torder.time_id," +
				"torder.doctor_id,torder.order_num,torder.order_person_id,torder.amount," +
				"torder.paystat,torder.create_time,torder.pay_time," +
				"tu.phone,tu.phone_bind,tu.nick_name,tu.password,tu.register_time," +
				"tu.name,tu.age,tu.height,tu.weight,tu.sex,tu.headpic " +
				"FROM t_order torder " +
				"LEFT JOIN t_user tu ON torder.user_id = tu.id " +
				"WHERE time_id = ? ORDER BY pay_time";
		return jt.query(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, timeId);
				return preparedStatement;
			}
		}, new OrderBeanFullMapper());
	}

	protected class OrderBeanMapper implements RowMapper<OrderBean> {
		public OrderBean mapRow(ResultSet rs, int i) throws SQLException {
			OrderBean orderBean = new OrderBean();
			orderBean.setAmount(rs.getDouble("amount"));
			orderBean.setCreateTime(rs.getTimestamp("create_time"));
			orderBean.setDoctorId(rs.getInt("doctor_id"));
			orderBean.setId(rs.getInt("id"));
			orderBean.setOrderNum(rs.getInt("order_num"));
			orderBean.setOrderPersonId(rs.getInt("order_person_id"));
			orderBean.setOrderSequence(rs.getString("order_sequence"));
			orderBean.setPaystat(rs.getInt("paystat"));
			orderBean.setPayTime(rs.getTimestamp("pay_time"));
			orderBean.setTimeId(rs.getInt("time_id"));
			orderBean.setUserId(rs.getInt("user_id"));
			return orderBean;
		}
	}

	protected class OrderBeanFullMapper implements RowMapper<OrderBean> {
		public OrderBean mapRow(ResultSet rs, int i) throws SQLException {
			OrderBean orderBean = new OrderBean();
			orderBean.setId(rs.getInt("torder.id"));
			orderBean.setAmount(rs.getDouble("torder.amount"));
			orderBean.setCreateTime(rs.getTimestamp("torder.create_time"));
			orderBean.setDoctorId(rs.getInt("torder.doctor_id"));
			orderBean.setOrderNum(rs.getInt("torder.order_num"));
			orderBean.setOrderPersonId(rs.getInt("torder.order_person_id"));
			orderBean.setOrderSequence(rs.getString("torder.order_sequence"));
			orderBean.setPaystat(rs.getInt("torder.paystat"));
			orderBean.setPayTime(rs.getTimestamp("torder.pay_time"));
			orderBean.setTimeId(rs.getInt("torder.time_id"));
			orderBean.setUserId(rs.getInt("torder.user_id"));

			UserBean userBean = new UserBean();
			userBean.setUserid(orderBean.getUserId());
			userBean.setPhone(rs.getString("tu.phone"));
			userBean.setNickName(rs.getString("tu.nick_name"));
			userBean.setIsBindPhone(rs.getString("tu.phone_bind"));
			userBean.setRegisterTime(rs.getTimestamp("tu.register_time"));
			userBean.setName(rs.getString("tu.name"));
			userBean.setAge(rs.getString("tu.age"));
			userBean.setHeight(rs.getString("tu.height"));
			userBean.setWeight(rs.getString("tu.weight"));
			userBean.setSex(rs.getString("tu.sex"));
			userBean.setPassword(rs.getString("tu.password"));
			userBean.setHeadpic(rs.getString("tu.headpic"));
			orderBean.setUserBean(userBean);

			return orderBean;
		}
	}


}
