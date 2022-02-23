package com.zhangbiao.blog.model;

public class User {
    public Integer uid;
    public String username;
    public String nickname;
    public String password;
    public String avatar;

    public User(){}

    public User(Integer uid, String username, String nickname, String password, String avatar) {
        this.uid = uid;
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
