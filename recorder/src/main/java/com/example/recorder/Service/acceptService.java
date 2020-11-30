package com.example.recorder.Service;

import com.example.recorder.Entity.Record;
import com.example.recorder.Repository.RecordRepository;
import com.example.recorder.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class acceptService {
    public static Map<String,Integer> map = new HashMap<String,Integer>();
    public static String dist = "D://record";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecordRepository recordRepository;

    public String accept(Map<String,String> data)throws Exception{
        String username = data.get("username");
        String recordname = data.get("recordname");
        int sum = Integer.parseInt(data.get("sum"));
        int index = Integer.parseInt(data.get("index"));
        String encodedString = data.get("encodedString");

        int hashcode = recordname.hashCode();

        /**
         * 确保本地有record文件夹
         */

        File file2 = new File(dist);
        if(!file2.exists()){
            file2.mkdir();
        }
        /**
         * 在没有登陆的情况下，先新建一个username文件夹，写完登陆以后可以删除
         */
        File file = new File(dist+"//"+username);
        if(!file.exists()){
            file.mkdir();
        }

        /**
         * 保存到数据库record表
         */
        if(!recordRepository.findById(hashcode).isPresent()) {
            Record record = new Record();
            record.setDistpath(dist + "//" + username + "//" + hashcode);
            record.setName(recordname);
            record.setId(hashcode);
            record.setSum(sum);
            record.setUsername(username);
            recordRepository.save(record);

            //在map中存一个新的key
            map.put(recordname,0);
        }

        //保存url转码和base64转码后的字符串到录音文件夹中，用于合并
        saveCode(encodedString,dist + "//" + username + "//" + hashcode,index);
        map.put(recordname,map.get(recordname)+1);

        //判断是否该条录音是否满足合并条件
        if(map.get(recordname)==sum){
            map.remove(recordname);
            merge(dist + "//" + username + "//" + hashcode,sum,recordname);
        }
        return "success";
    }

    /**
     * 保存url转码和base64转码后的字符串到录音文件夹中，用于合并
     * @param encodedString
     * @param path
     * @param index
     * @throws Exception
     */
    public void saveCode (String encodedString,String path,int index)throws Exception{
        File file2 = new File(path);
        if(!file2.exists()){
            file2.mkdir();
        }
        File file = new File(path+"\\"+index+".txt");
        byte[] buffer = encodedString.getBytes();
        FileOutputStream out = new FileOutputStream(file);
        out.write(buffer);
        out.close();
    }

    /**
     * 合并转码字符串得到录音文件如xxxx.mp4...
     * @param path
     */
    public void merge(String path,int sum,String recordname)throws Exception{
        File mp4file = new File(path+"\\"+recordname);
        FileOutputStream out = new FileOutputStream(mp4file);
        for(int i=1;i<=sum;i++){
            File file = new File(path+"\\"+i+".txt");
            FileInputStream in = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            in.read(buffer);
            in.close();
            String str = new String(buffer);
            //第一层解码 URL
            String str1 = URLDecoder.decode(str,"UTF-8");
            //第二层解码 base64
            byte[] buffer2 = new BASE64Decoder().decodeBuffer(str1);
            //写入m4a文件
            out.write(buffer2);
        }
        out.close();
    }
}
