package com.zhangbiao.blog.servlet;

import com.zhangbiao.blog.model.User;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/edit.html")
public class EditorFormServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取当前登录用户
        User user = null;
        HttpSession session = req.getSession(false);
        if(session != null) {
            user = (User) session.getAttribute("currentUser");
        }
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();

        if (user == null){
            //用户没登录

            writer.printf("<p>登陆后才能使用，点击<a href='/login.html'>此处</a>进行登录</p>\r\n");
            return;
        }

        //使用模板技术，生成最终的正文
        ServletContext servletContext = req.getServletContext();
        WebContext webContext = new WebContext(req,resp,servletContext);
        webContext.setVariable("user",user);

        TemplateEngine engine = (TemplateEngine) servletContext.getAttribute("engine");
        String body =  engine.process("edit",webContext);

        //写入正文
        writer.println(body);
    }
}
