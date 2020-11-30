package com.example.recorder.Controller;

import com.example.recorder.Entity.User;
import com.example.recorder.Repository.UserRepository;
import com.example.recorder.Service.loginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
public class loginController {

    @Resource
    loginService lgService;

    @PostMapping(value = "/login",consumes = "application/json")
    public Map<String,String> login(@RequestBody Map<String,String> map){
        return lgService.login(map);
    }
}
