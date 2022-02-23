package com.zhangbiao.blog.dao;

import com.zhangbiao.blog.model.Article;
import com.zhangbiao.blog.util.DBUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ArticleDao {
    public List<Article> selectListByUid(int uid) {
        List<Article> articleList = new ArrayList<>();
        String sql = "SELECT aid, title, type, content, published_date FROM articles WHERE uid = ? ORDER BY published_date DESC";
        try (Connection c = DBUtil.connection()){
            try (PreparedStatement ps = c.prepareStatement(sql)){
                ps.setInt(1,uid);
                try (ResultSet rs = ps.executeQuery()){
                    while (rs.next()){
                        int aid = rs.getInt("aid");
                        String title = rs.getString("title");
                        String type = rs.getString("type");
                        String content = rs.getString("content");
                        // 需要从 java.sql.Date 转成 java.time.LocalDate 类型
                        LocalDate published_date = rs.getDate("published_date").toLocalDate();

                        Article article = new Article(aid,uid,title,type,content,published_date);

                        articleList.add(article);
                    }
                }
            }
        }catch (SQLException exc){
            throw new RuntimeException(exc);
        }
        return articleList;
    }

    public int selectArticleCountByUid(int uid) {
        String sql = "SELECT COUNT(*) FROM articles WHERE uid = ?";
        try (Connection c = DBUtil.connection()){
            try (PreparedStatement ps = c.prepareStatement(sql)){
                ps.setInt(1,uid);
                try (ResultSet rs = ps.executeQuery()){
                    rs.next();
                    return rs.getInt(1);
                }
            }
        }catch (SQLException exc){
            throw new RuntimeException(exc);
        }
    }

    public int selectTypeCountByUid(int uid) {
        String sql = "SELECT COUNT(DISTINCT type) FROM articles WHERE uid = ?";
        try (Connection c = DBUtil.connection()){
            try (PreparedStatement ps = c.prepareStatement(sql)){
                ps.setInt(1,uid);
                try (ResultSet rs = ps.executeQuery()){
                    rs.next();
                    return rs.getInt(1);
                }
            }
        }catch (SQLException exc){
            throw new RuntimeException(exc);
        }
    }

    //aid是不是当前用户发表的
    public Article selectOneByAid(int aid) {
        String sql = "SELECT uid,title,type,content,published_date FROM articles WHERE aid=?";
        try (Connection c = DBUtil.connection()){
            try (PreparedStatement ps = c.prepareStatement(sql)){

                ps.setInt(1,aid);
                try (ResultSet rs = ps.executeQuery()){
                    if (!rs.next()){
                        return null;
                    }
                    int uid = rs.getInt("uid");
                    String title = rs.getString("title");
                    String type = rs.getString("type");
                    String content = rs.getString("content");
                    // 需要从 java.sql.Date 转成 java.time.LocalDate 类型
                    LocalDate published_date = rs.getDate( "published_date").toLocalDate();

                    return new Article(aid,uid,title,type,content,published_date);
                }
                }
        } catch (SQLException exc){
            throw new RuntimeException(exc);
        }
    }

    public void insert(Article article) {
        String sql = "INSERT INTO articles (uid,title,type,content,published_date) VALUES (?,?,?,?,?)";
        try (Connection c = DBUtil.connection()){
            try (PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

                ps.setInt(1,article.uid);
                ps.setString(2,article.title);
                ps.setString(3,article.type);
                ps.setString(4,article.content);
                ps.setDate(5,Date.valueOf(article.published_date));

                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()){
                    rs.next();
                    article.aid = rs.getInt(1);
                }
            }
        } catch (SQLException exc){
            throw new RuntimeException(exc);
        }
    }
}
