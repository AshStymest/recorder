package com.example.recorder.Service;

import com.example.recorder.Entity.Record;
import com.example.recorder.Repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class listService {

    @Autowired
    private RecordRepository recordRepository;

    //返回用户目录下所有录音文件名称及路径
    public Map<String,String> findList(String username, Timestamp start,Timestamp end){

        //kv键值对 存储<录音名称,文件路径>
        Map<String,String> map = new HashMap<>();

        String st = start.toString().replace("//..*","");
        String en = end.toString().replace("//..*","");

        /*File[] arr = file.listFiles();

        //确定录音文件的格式后，可能需要修改，连带存储的时候也要改，在acceptService中...  可能还需要前端对返回的url处理一下
        for(File e:arr){
            Record record = recordRepository.findById(Integer.parseInt(e.getName())).get();
            map.put(record.getName(),record.getDistpath()+"//record.m4a");*/
        List<Record> list = recordRepository.findRecordByUsernameAndTime(username,st,en);




        for(Record record : list){
            map.put(record.getName(),record.getDistpath()+"//"+record.getName());
        }

        return map;
    }
}
