package com.zhangbiao.blog.servlet;


import com.zhangbiao.blog.model.Article;
import com.zhangbiao.blog.model.User;
import com.zhangbiao.blog.service.ArticleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@WebServlet("/save-article.html")
public class SaveArticleServlet extends HttpServlet {
    private final ArticleService articleService = new ArticleService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String title = req.getParameter("title");
        String content = req.getParameter("content");


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

        //不考虑发表失败的情况了
        Article article = articleService.publishArticle(user,title,content);

        //直接重定向到文章详情页
        resp.sendRedirect("/article.html?aid=" + article.aid);
    }
}
