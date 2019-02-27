package com.zbf.service;

import com.alibaba.fastjson.JSON;
import com.zbf.core.page.Page;
import com.zbf.core.utils.UID;
import com.zbf.enmu.MyRedisKey;
import com.zbf.mapper.TiKuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 作者：LCG
 * 创建时间：2019/2/14 11:05
 * 描述：
 */
@Component
public class TiKuService {
    @Autowired
    private TiKuMapper tiKuMapper;

    @Transactional
    public int addTiKuInfo(Map<String,Object> map, RedisTemplate redisTemplate){
        redisTemplate.opsForList().rightPush(MyRedisKey.TIKU.getKey (),map);

        return tiKuMapper.addTiKuInfo ( map );
    }
    /**
     * 获取题库信息
     * @param map
     */
    public void getTikuList(Page<Map<String,Object>> page){

         List<Map<String,Object>> list=tiKuMapper.getTikuList(page);

         list.forEach ( (item)->{
             if(item.get ( "tikuzhuangtai" ).toString ().equals ( "1" )){
                 item.put ( "tikuzhuangtai","开放");
             }else{
                 item.put ( "tikuzhuangtai","关闭");
             }
         } );

         page.setResultList ( list );
    }

    /**
     * 更新题库信息
     * @param map
     */
    @Transactional
    public void updateTiKuInfo(Map<String,Object> map){
        tiKuMapper.updateTiKuInfo ( map );
    }

    /**
     * 删除题库信息
     * @param map
     */
    @Transactional
    public void deleteTiKuInfo(Map<String,Object> map){
        tiKuMapper.deleteTiKuInfo ( map );
    }

    /**
     * 手动添加试题
     * @param paramsJsonMap
     */
    @Transactional
    public void toAddShiTi(Map<String,Object> paramsJsonMap) {

        tiKuMapper.toAddShiTi(paramsJsonMap);
    }

    /**
     * 试题列表
     * @param page
     */
    public void getShiTiList(Page<Map<String,Object>> page) {
        List<Map<String,Object>> list=tiKuMapper.getShiTiList(page);

        list.forEach ( (item)->{
            if(item.get ( "shitizhuangtai" ).toString ().equals ( "1" )){
                item.put ( "shitizhuangtai","开放");
            }else{
                item.put ( "shitizhuangtai","关闭");
            }
        } );

        page.setResultList ( list );

    }


    /**
     * 删除试题信息
     * @param map
     */
    @Transactional
    public void deleteShiTiInfo(Map<String,Object> map){
        tiKuMapper.deleteShiTiInfo ( map );
    }

    /**
     * 根据ID获取试题信息
     * @param map
     * @return
     */
    public Map<String,Object> getShiTiById(Map<String,Object> map){
        Map<String, Object> params = tiKuMapper.getShiTiById ( map );
        if(params.get ( "tixingid" )!=null&&params.get ( "tixingid" ).toString ().equals ( "1" )){//单选题
            //试题的选项编号
            params.put ( "xuanxiangbianhao",JSON.toJSONString ( JSON.parse (params.get ( "xuanxiangbianhao" ).toString () )));
            //试题的选项描述
            params.put ( "xuanxiangmiaoshu",JSON.toJSONString ( JSON.parse (params.get ( "xuanxiangmiaoshu" ).toString () )));

        }else if(params.get ( "tixingid" )!=null&&params.get ( "tixingid" ).toString ().equals ( "2" )){//多选题
            //试题答案
            params.put ( "daan",JSON.toJSONString ( JSON.parse (params.get ( "daan" ).toString () )) );
            //试题的选项编号
            params.put ( "xuanxiangbianhao",JSON.toJSONString ( JSON.parse (params.get ( "xuanxiangbianhao" ).toString () )));
            //试题的选项描述
            params.put ( "xuanxiangmiaoshu",JSON.toJSONString ( JSON.parse (params.get ( "xuanxiangmiaoshu" ).toString () )));

        }else if(params.get ( "tixingid" )!=null&&params.get ( "tixingid" ).toString ().equals ( "3" )){//判断题
            //试题的判断描述
            Object miaoshu=params.get ( "xuanxiangmiaoshu" )!=null?params.get ( "xuanxiangmiaoshu" ):"";
            params.put ( "xuanxiangmiaoshu",JSON.toJSONString ( JSON.parse (miaoshu.toString () )) );

        }

        return params;
    }
}
