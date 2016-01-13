package com.hulu.user.dao;

import com.hulu.base.core.dao.BaseDao;
import com.hulu.user.bean.UserBean;
import com.hulu.user.bean.UserTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

/**
 * Created by huangyiwei on 15/10/23.
 */
@Repository
public class UserDao extends BaseDao {
    private Logger log = LoggerFactory.getLogger(UserDao.class);

    /**
     * 按手机号码进行查询
     * @param phone
     * @return
     */
    public UserBean queryByPhone(final String phone) {
        final String sql = "SELECT * FROM t_user where phone = ?";
        List<UserBean> userBeanList = jt.query(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, phone);
                return preparedStatement;
            }
        }, new UserBeanMapper());
        if(userBeanList != null){
            return userBeanList.get(0);
        }
        return null;
    }

    /**
     * 按手机号码进行查询
     * @param phone
     * @return
     */
    public UserBean queryByPhoneAndUserType(final String phone,final UserTypeEnum userTypeEnum) {
        final String sql = "SELECT * FROM t_user where phone = ? AND is_admin = ?";
        List<UserBean> userBeanList = jt.query(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, phone);
                preparedStatement.setInt(2, userTypeEnum.val());
                return preparedStatement;
            }
        }, new UserBeanMapper());
        if(userBeanList != null){
            return userBeanList.get(0);
        }
        return null;
    }

    /**
     * 根据ID查询用户
     * @param id
     * @return
     */
    public UserBean queryUserById(String id) {
        final String sql = "SELECT * FROM t_user where id = ?";
        return jt.queryForObject(sql,new Object[]{new Integer(id)}, new UserBeanMapper());
    }

    protected class UserBeanMapper implements RowMapper<UserBean> {
        public UserBean mapRow(ResultSet rs, int i) throws SQLException {
            UserBean user = new UserBean();
            user.setUserid(rs.getInt("id"));
            user.setPhone(rs.getString("phone"));
            user.setNickName(rs.getString("nick_name"));
            user.setIsBindPhone(rs.getString("phone_bind"));
            user.setRegisterTime(rs.getTimestamp("register_time"));
            user.setName(rs.getString("name"));
            user.setAge(rs.getString("age"));
            user.setHeight(rs.getString("height"));
            user.setWeight(rs.getString("weight"));
            user.setSex(rs.getString("sex"));
            user.setPassword(rs.getString("password"));
            user.setHeadpic(rs.getString("headpic"));
            user.setIsAdmin(rs.getInt("is_admin"));
            return user;
        }
    }

}
