package com.zhangbiao.blog.service;

import com.zhangbiao.blog.dao.UserDao;
import com.zhangbiao.blog.model.User;
import org.mindrot.jbcrypt.BCrypt;

public class UserService {
    private final UserDao userDao = new UserDao();

    public User register(String username,String nickname,String password,String avatar){
        //1.1将密码hash后再保存
        String salt = BCrypt.gensalt();
        String hashpw = BCrypt.hashpw(password,salt);

        //2.将信息保存到数据库中
       return userDao.insert(username, nickname, hashpw,avatar);
    }

    public User login(String username,String password){
        //1.获取登录用户信息
        User user = userDao.selectOneByUsername(username);

        //2.验证密码是否正确

        if(user == null){
            return null;
        }

        if(BCrypt.checkpw(password,user.password)){
            return user;
        }
        return null;

    }
}
