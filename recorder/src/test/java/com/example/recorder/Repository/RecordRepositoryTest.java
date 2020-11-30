package com.example.recorder.Repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RecordRepositoryTest {

    @Autowired
    RecordRepository recordRepository;

    @Test
    public void findbyid(){
        System.out.println(recordRepository.findById(104263205).get().getName());
    }

}