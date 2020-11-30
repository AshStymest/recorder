package com.example.recorder.Controller;

import com.example.recorder.Service.listService;
import com.example.recorder.annotations.UserLoginToken;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Map;


@RestController
public class listController {
    @Resource
    private listService ltService;

    @UserLoginToken
    @GetMapping(path="/hello")
    public String hello(){
        return "hi";
    }

    @GetMapping(path = "/list")
    public Map<String,String> list(@RequestParam("username") String username, @RequestParam("start")Timestamp start,@RequestParam("end")Timestamp end){

        return ltService.findList(username,start,end);
    }

}
