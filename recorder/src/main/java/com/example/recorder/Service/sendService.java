package com.example.recorder.Service;

import com.example.recorder.Entity.Record;
import com.example.recorder.Repository.RecordRepository;
import com.example.recorder.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class sendService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecordRepository recordRepository;

    public String send(Map<String,String> data){

        String path = data.get("path");
        String recordname = data.get("recordname");

        Record record = recordRepository.findById(recordname.hashCode()).get();
        int sum = record.getSum();

        return null;
    }
}
