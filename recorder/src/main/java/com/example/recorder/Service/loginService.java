package com.example.recorder.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.recorder.Entity.User;
import com.example.recorder.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class loginService {
    @Autowired
    private UserRepository userRepository;

    public Map<String,String> login(Map<String,String> map){
        Map<String,String> tempMap = new HashMap<>();
        User user = userRepository.findUserByUsername(map.get("username"));
        if(user == null){
            tempMap.put("message","用户不存在，请先注册");
            return tempMap;
        }else{
            if(!user.getPassword().equals(map.get("password"))){
                tempMap.put("message","密码错误");
                return tempMap;
            }else{
                String token = getToken(user);
                userRepository.updateToken(user.getUserId(),token,new Date().getTime()+7*24*3600*1000L);
                tempMap.put("message","登陆成功");
                tempMap.put("token",token);
                return tempMap;
            }
        }
    }

    public String getToken(User user){
        String token = "";
        Long deadLine = new Date().getTime();
        token = JWT.create().withAudience(user.getUserId()+"")
                .sign(Algorithm.HMAC256(deadLine+""));
        return token;
    }
}
