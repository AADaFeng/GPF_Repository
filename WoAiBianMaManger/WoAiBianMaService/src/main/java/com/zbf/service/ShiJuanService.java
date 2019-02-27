package com.zbf.service;

import com.zbf.core.page.Page;
import com.zbf.mapper.ShiJuanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Component
public class ShiJuanService {

    @Autowired
    private ShiJuanMapper shiJuanMapper;

    @Transactional
    public void addShiJuanInfo(Map<String,Object> paramsJsonMap) {
         shiJuanMapper.addShiJuanInfo ( paramsJsonMap );
    }

    @Transactional
    public void ShiJuanList(Page<Map<String,Object>> page) {
        List<Map<String,Object>> list=shiJuanMapper.ShiJuanList ( page );

        list.forEach((item)->{
            if(item.get("zhuangtai").toString().equals("1")){
                item.put("zhuangtai","开放");
            }else {
                item.put("zhuangtai","关闭");
            }
        });
        list.forEach((item)->{
            if (item.get("shijuanleixing").toString().equals("0")){
                item.put("shijuanleixing","随机试卷");
            }else{
                item.put("shijuanleixing","普通试卷");
            }
        });
        page.setResultList(list);
    }


    @Transactional
    public void getUserInfo(Page<Map<String,Object>> page) {
        List<Map<String,Object>> list=shiJuanMapper.getUserInfo(page);
        page.setResultList(list);
    }
}
