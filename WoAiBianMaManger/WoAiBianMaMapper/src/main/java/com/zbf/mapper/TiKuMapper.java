package com.zbf.mapper;

import com.zbf.core.page.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 作者：LCG
 * 创建时间：2019/2/14 11:01
 * 描述：
 */
@Mapper
public interface TiKuMapper {

    /**
     * 按数题库导出数据
     * @param page
     * @return
     */
    public List<Map<String,Object>> getShitiDataListByTiKu(Page<Map<String,Object>> page);

    public int addTiKuInfo(Map<String,Object> map);

    public List<Map<String,Object>> getTikuList(Page<Map<String,Object>> page);

    public int updateTiKuInfo(Map<String,Object> map);

    public  int deleteTiKuInfo(Map<String,Object> map);

    public int toAddShiTi(Map<String,Object> paramsJsonMap);
    //试题列表
    public List<Map<String,Object>> getShiTiList(Page<Map<String,Object>> page);
    //删除试题
    public  int deleteShiTiInfo(Map<String,Object> map);

    public Map<String,Object> getShiTiById(Map<String,Object> map);
}
