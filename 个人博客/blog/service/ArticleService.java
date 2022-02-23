package com.zhangbiao.blog.service;

import com.zhangbiao.blog.dao.ArticleDao;
import com.zhangbiao.blog.model.Article;
import com.zhangbiao.blog.model.User;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticleService {
    private final ArticleDao articleDao = new ArticleDao();

    public Map<String,Object> getIndexData(User user){
        //准备数据
        //（1）登录用户信息，已经有了
        //（2）文章列表
        List<Article> articleList = articleDao.selectListByUid(user.uid);
        //（3）查询当前用户的文章总数
        int articleCount = articleDao.selectArticleCountByUid(user.uid);
        //（4）查询当前用户的文章类型总数
        int typeCount = articleDao.selectTypeCountByUid(user.uid);

        HashMap<String,Object> data = new HashMap<>();
        data.put("articleList",articleList);
        data.put("articleCount",articleCount);
        data.put("typeCount",typeCount);

        return data;
    }

    public Map<String, Object> getArticleData(User user, int aid) {

        //（1）根据aid查询文章信息
        Article article = articleDao.selectOneByAid(aid);

        if (article == null){
            return null;
        }

        //（2）查询当前用户的文章总数
        int articleCount = articleDao.selectArticleCountByUid(user.uid);
        //（3）查询当前用户的文章类型总数
        int typeCount = articleDao.selectTypeCountByUid(user.uid);

        HashMap<String,Object> data = new HashMap<>();
        data.put("article",article);
        data.put("articleCount",articleCount);
        data.put("typeCount",typeCount);

        return data;
    }

    public Article publishArticle(User user, String title, String content) {
        Article article = new Article();
        article.title = title;
        article.type = "博客";
        article.content = content;
        article.uid = user.uid;
        article.published_date = LocalDate.now();

        articleDao.insert(article);
        return article;
    }
}
