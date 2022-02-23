package com.zhangbiao.blog.servlet;

import com.zhangbiao.blog.dao.UserDao;
import com.zhangbiao.blog.model.User;
import com.zhangbiao.blog.service.UserService;
import org.mindrot.jbcrypt.BCrypt;

import javax.jws.soap.SOAPBinding;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/do-register")
public class DoRegisterServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.读取用户信息
        req.setCharacterEncoding("utf-8");
        String username = req.getParameter("username");
        String nickname = req.getParameter("nickname");
        String password = req.getParameter("password");
        String avatar = req.getParameter("avatar");

        User user = userService.register(username,nickname,password,avatar);
        //至少区分三种情况
        //（1）注册成功  user != null
        //（2）用户名重复导致注册失败 user == null
        //（3）系统操作导致注册失败  抛异常,交个tomcat处理，自动生成500页面

        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        if(user != null) {
            HttpSession session = req.getSession();
            session.setAttribute("currentUser",user);
            writer.printf("<p>注册成功，点击<a href='/login.html'>此处</a>登录</p>\r\n");
        }else {
            writer.printf("<p>注册失败，用户名重复，点击<a href='/register.html'>此处</a>重写注册</p>\r\n");
        }


    }
}
