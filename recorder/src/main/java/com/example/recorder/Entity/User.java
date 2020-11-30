package com.example.recorder.Entity;

import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {
    //用户唯一id，由用户名hash生成 根据id生成存储路径
    @Id
    @Column(name = "user_id")
    private Integer userId;

    //用户名 用于登陆 取用户id
    @Column(name = "user_name")
    private String userName;

    //用户密码 用于登陆
    private String password;

    //保存的token，用于对比验证
    private String token;

    //保存token失效时间（ms）
    @Column(name = "dead_line")
    private Long deadLine;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Long deadLine) {
        this.deadLine = deadLine;
    }
}
