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


@WebServlet("/do-login")
public class DoLoginServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.读取用户信息
        req.setCharacterEncoding("utf-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        //3.至少区分三种情况
        //（1）成功  user != null
        //（2）用户名不存在 user == null
        //（3）抛异常,交个tomcat处理

        User user = userService.login(username,password);

        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        if(user != null) {
            if(BCrypt.checkpw(password,user.password)){
                HttpSession session = req.getSession();
                session.setAttribute("currentUser",user);
                writer.printf("<p>登录成功，点击<a href='/'>此处</a>跳转到首页</p>\r\n");
                return;
            }
        }
        writer.printf("<p>登录失败，点击<a href='/login.html'>此处</a>重新登录</p>\r\n");
    }
}
