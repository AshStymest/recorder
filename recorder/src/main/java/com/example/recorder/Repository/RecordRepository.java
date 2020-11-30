package com.example.recorder.Repository;

import com.example.recorder.Entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;


public interface RecordRepository extends JpaRepository<Record,Integer> {

    @Query(value = "select r.* from record r where r.username = ?1  and r.time >= ?2 and r.time <= ?3",nativeQuery = true)
    List<Record> findRecordByUsernameAndTime(String username, String start, String end);

}
