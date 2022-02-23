package com.zhangbiao.blog.servlet;

import com.zhangbiao.blog.model.User;
import com.zhangbiao.blog.service.ArticleService;
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
import java.util.Map;

@WebServlet("/article.html")
public class ArticleServlet extends HttpServlet {

    private final ArticleService articleService = new ArticleService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.读取文章id
        req.setCharacterEncoding("utf-8");
        String aidStr = req.getParameter("aid");
        if (aidStr == null){
            resp.sendError(400,"请使用正确的文章id");
            return;
        }

        int aid;
        try{
            aid = Integer.parseInt(aidStr);
        }catch (NumberFormatException exc){
            resp.sendError(400,"请使用正确的文章id");
            return;
        }

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
        //查询详情页所有数据
        Map<String,Object> articleData = articleService.getArticleData(user,aid);
        if (articleData == null){
            resp.sendError(404,"aid对应的文章不存在");
            return;
        }

        //使用模板技术，生成最终的正文
        ServletContext servletContext = req.getServletContext();
        WebContext webContext = new WebContext(req,resp,servletContext);
        webContext.setVariable("user",user);
        webContext.setVariables(articleData);

        TemplateEngine engine = (TemplateEngine) servletContext.getAttribute("engine");
        String body =  engine.process("article",webContext);

        //写入正文
        writer.printf(body);
    }
}
