package com.example.recorder.Entity;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

//类说明： user path路径下 每条录音文件对应一个Record的文件夹 存放分割后的录音文件
@Entity
public class Record {
    //录音文件唯一id 由录音文件名字hash生成
    @Id
    private Integer id;

    //录音文件名字 包含在http post请求中
    private String name;

    //分割文件块数
    private Integer sum;

    //文件存储路径 由用户名和RId确定 如用户id为11123 录音文件uid为233 则distpath 为形如 D：/record/11123/233 的字符串
    private String distpath;

    private String username;

    //存储时间
    @Column(name="time",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",insertable = false,updatable = false)
    @Generated(GenerationTime.INSERT)
    private Timestamp time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public String getDistpath() {
        return distpath;
    }

    public void setDistpath(String distpath) {
        this.distpath = distpath;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
