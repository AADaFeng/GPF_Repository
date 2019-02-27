package com.zbf.web;

import com.alibaba.fastjson.JSON;
import com.zbf.common.ResponseResult;
import com.zbf.core.CommonUtils;
import com.zbf.core.entity.TiMu;
import com.zbf.core.page.Page;
import com.zbf.core.utils.FileUploadDownUtils;
import com.zbf.core.utils.UID;
import com.zbf.enmu.MyRedisKey;
import com.zbf.service.ShiJuanService;
import com.zbf.service.TiKuService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;


@RequestMapping("shijuan")
@RestController
public class ShiJuanGuanLiController {
    @Autowired
    private TiKuService tiKuService;

    @Autowired
    private ShiJuanService shiJuanService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SolrClient solrClient;

    /**
     * 添加试卷信息
     * @param request
     * @return
     */
    @RequestMapping("toaddShiJuanInfo")
    public ResponseResult toaddShiJuanInfo(HttpServletRequest request){

        //存入数据
        ResponseResult responseResult=ResponseResult.getResponseResult();
        //获取数据
        Map<String,Object> paramsJsonMap= CommonUtils.getParamsJsonMap(request);
        try {
            paramsJsonMap.put ( "id",UID.getUUIDOrder () );
            shiJuanService.addShiJuanInfo ( paramsJsonMap);
            responseResult.setSuccess ( "ok" );
        }catch (Exception e){
            e.printStackTrace ();
            responseResult.setError ( "error" );
        }
        return responseResult;

    }

    /**
     * 试卷列表
     * @param httpServletRequest
     * @return
     */
    @RequestMapping("ShiJuanList")
    public ResponseResult ShiJuanList(HttpServletRequest httpServletRequest){

        Map<String, Object> paramsJsonMap = CommonUtils.getParamsJsonMap ( httpServletRequest );

        Page<Map<String,Object>> page=new Page<> ();

        ResponseResult responseResult=ResponseResult.getResponseResult ();
        //设置查询参数
        page.setParams ( paramsJsonMap );
        Page.setPageInfo ( page, paramsJsonMap);
        //
        shiJuanService.ShiJuanList ( page );
        //
        responseResult.setResult ( page );

        return responseResult;

    }

    /**从redis加载试卷数据
     *
     * @param request
     * @return
     */
    @RequestMapping("getShiJuanData")
    public  ResponseResult getShiJuanData(HttpServletRequest request){
        //获取ResopnResult数据
        ResponseResult responseResult=ResponseResult.getResponseResult();
        //从 redis 获取数据
        List<Map<String,Object>> rang= redisTemplate.opsForList().range(MyRedisKey.SHI_JUAN.getKey(),0,-1);
        //将加载的试卷数据加入到ResponseResult
        responseResult.setResult(rang);
        //返回结果
        return responseResult;
    }



    /**
     * 根据 试卷按照分数区间段 进行分数统计
     * @param request
     * @return
     */
    @RequestMapping("getScoreRangData")
    public ResponseResult getScoreRangData(HttpServletRequest request){
        //获取ResponseResult
        ResponseResult responseResult=ResponseResult.getResponseResult ();
        //获取参数
        Map<String, Object> parameterMap = CommonUtils.getParameterMap ( request );
        //区间值的开始部分
        List<Integer> fenshu1 = JSON.parseObject ( parameterMap.get ( "fenshu1" ).toString (), List.class );
        //区间值的结束部分
        List<Integer> fenshu2 = JSON.parseObject ( parameterMap.get ( "fenshu2" ).toString (), List.class );
        List<Map<String,Object>> listbingdata=new ArrayList<>(  );
        List<String> listbingdatatext=new ArrayList<> (  );

        for(int i=0;i<fenshu1.size ();i++){
            Set shijuanid = redisTemplate.opsForZSet ().rangeByScore ( parameterMap.get ( "shijuanid" ).toString (), fenshu1.get ( i ), fenshu2.get ( i ) );
            Map<String,Object> map=new HashMap<>(  );
            String name=""+fenshu1.get ( i )+"-"+ fenshu2.get ( i );
            map.put ( "name",name);
            map.put ( "value",shijuanid.size ());
            listbingdata.add ( map );
            listbingdatatext.add ( name );
        }

        Map<String,Object> mapdata=new HashMap<> (  );

        mapdata.put ( "listbingdata" ,listbingdata);
        mapdata.put ( "listbingdatatext" ,listbingdatatext);

        responseResult.setResult ( mapdata );

        return responseResult;
    }

    //获取用户信息
    @RequestMapping("getUserInfo")
    public  ResponseResult getUserInfo(HttpServletRequest request){
        ResponseResult responseResult=ResponseResult.getResponseResult();
        Map<String,Object>  map=CommonUtils.getParamsJsonMap(request);
        Page<Map<String,Object>> page=new Page<>();

        Page.setPageInfo(page,map);

        shiJuanService.getUserInfo(page);
        responseResult.setResult(page);
        return responseResult;
    }

}
