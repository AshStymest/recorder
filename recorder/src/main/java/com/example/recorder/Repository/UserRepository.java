package com.example.recorder.Repository;

import com.example.recorder.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Transient;
import javax.transaction.Transactional;

public interface UserRepository extends JpaRepository<User,Integer> {
    @Modifying
    @Transactional
    @Query(value = "update user set token = ?2 ,dead_line = ?3 where user_id = ?1",nativeQuery = true)
    void updateToken(int userId,String token,Long deadLine);


    @Query(value = "select u.* from user u where u.user_name = ?1",nativeQuery = true)
    User findUserByUsername(String username);
}
