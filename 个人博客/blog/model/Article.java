package com.zhangbiao.blog.model;

import com.zhangbiao.blog.util.DBUtil;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Article {
    public Integer aid;
    public Integer uid;
    public String title;
    public String type;
    public String content;
    public LocalDate published_date;

    public Article(){}

    public Article(int aid, int uid, String title, String type, String content, LocalDate published_date) {
        this.aid = aid;
        this.uid = uid;
        this.title = title;
        this.type = type;
        this.content = content;
        this.published_date = published_date;
    }

    public String getPublished_date(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return published_date.format(formatter);
    }

    public String getDesc(){
        //从正文中截取100个字符，作为文章的简要概述
        //如果长度不够100，以文章长度为准

        int length = Integer.min(content.length(),100);
        return content.substring(0,length);
    }
}
