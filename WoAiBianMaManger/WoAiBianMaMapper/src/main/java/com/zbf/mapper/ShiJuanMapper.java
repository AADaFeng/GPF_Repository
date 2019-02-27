package com.zbf.mapper;

import com.zbf.core.page.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ShiJuanMapper {

    public   int addShiJuanInfo(Map<String,Object> map);
    //试卷列表
    public List<Map<String,Object>> ShiJuanList(Page<Map<String,Object>> page);

    //获取用户信息
    public List<Map<String,Object>> getUserInfo(Page<Map<String,Object>> page);
}
