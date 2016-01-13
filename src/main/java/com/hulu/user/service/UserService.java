package com.hulu.user.service;

import com.hulu.user.bean.UserBean;
import com.hulu.user.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by huangyiwei on 15/10/26.
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 按手机号码进行查询
     * @param phone
     * @return
     */
    public UserBean queryByPhone(String phone) {
        return userDao.queryByPhone(phone);
    }

    /**
     * 根据ID查询用户
     * @param id
     * @return
     */
    public UserBean queryUserById(String id) {
        return userDao.queryUserById(id);
    }
}
