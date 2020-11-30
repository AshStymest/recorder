package com.example.recorder.Controller;

import com.example.recorder.Service.acceptService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
public class acceptController {
    @Resource
    private acceptService acService;


    /**
     *
     * @param data{username:String,recordname:String,sum:int,encodedString:String,index:int} index从1开始
     * @return
     */
    @PostMapping(value = "/accept",consumes = "application/json")
    public String accept(@RequestBody Map<String,String> data)throws Exception{
        return acService.accept(data);
    }

}
