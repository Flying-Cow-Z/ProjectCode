package com.zhangbiao.blog.dao;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.zhangbiao.blog.model.User;
import com.zhangbiao.blog.util.DBUtil;

import java.sql.*;

public class UserDao {
    public User insert(String username, String nickname, String password, String avatar) {
        String sql = "INSERT INTO users (username, nickname, password, avatar) VALUES (?, ?, ?, ?)";

        try (Connection c = DBUtil.connection()){
            try (PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                ps.setString(1,username);
                ps.setString(2,nickname);
                ps.setString(3,password);
                ps.setString(4,avatar);
                System.out.println("DEBUG:执行 SQL：" + ps);

                ps.executeUpdate();

                try  (ResultSet rs = ps.getGeneratedKeys()){
                    rs.next();
                    int uid = rs.getInt(1);

                    return new User(uid,username,nickname,password,avatar);
                }

            }
        }catch (SQLException exc){
            if (exc instanceof MySQLIntegrityConstraintViolationException){
                //由于用户名重复所导致的问题
                return null;
            }
            throw new RuntimeException(exc);
        }
    }

    public User selectOneByUsername(String username) {
        String sql = "SELECT uid,username,nickname,password ,avatar FROM users WHERE username = ?";
        try (Connection c = DBUtil.connection()){
            try (PreparedStatement ps = c.prepareStatement(sql)){
                ps.setString(1,username);

                try (ResultSet rs = ps.executeQuery()){
                    if (!rs.next()){
                        return null;
                    }
                    return new User(
                            rs.getInt("uid"),
                            rs.getString("username"),
                            rs.getString("nickname"),
                            rs.getString("password"),
                            rs.getString("avatar")
                    );
                }
            }
        }catch (SQLException exc){
            throw new RuntimeException(exc);
        }

    }
}
