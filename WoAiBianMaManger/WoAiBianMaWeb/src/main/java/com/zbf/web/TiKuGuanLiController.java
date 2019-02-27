package com.zbf.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zbf.common.ResponseResult;
import com.zbf.core.CommonUtils;
import com.zbf.core.entity.TiMu;
import com.zbf.core.page.Page;
import com.zbf.core.utils.FileUploadDownUtils;
import com.zbf.core.utils.UID;
import com.zbf.enmu.MyRedisKey;
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
import java.util.List;
import java.util.Map;

/**
 * 作者：LCG
 * 创建时间：2019/2/14 11:00
 * 描述：
 */
@RequestMapping("tiku")
@RestController
public class TiKuGuanLiController {
    @Autowired
    private TiKuService tiKuService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SolrClient solrClient;

    /**
     * 添加题库信息
     * @param request
     * @return
     */
    @RequestMapping("toaddTiKuInfo")
    public ResponseResult toaddTiKuInfo(HttpServletRequest request){

        ResponseResult responseResult=ResponseResult.getResponseResult ();

        //获取数据
        Map<String, Object> parameterMap = CommonUtils.getParamsJsonMap ( request );
        //存入数据
        try {
            parameterMap.put ( "id",UID.getUUIDOrder () );
            tiKuService.addTiKuInfo ( parameterMap,redisTemplate);
            responseResult.setSuccess ( "ok" );
        }catch (Exception e){
            e.printStackTrace ();
            responseResult.setError ( "error" );
        }

        return responseResult;

    }

    /**
     * 题库列表
     * @param httpServletRequest
     * @return
     */
    @RequestMapping("getTikuList")
    public ResponseResult getTikuList(HttpServletRequest httpServletRequest){

        Map<String, Object> paramsJsonMap = CommonUtils.getParamsJsonMap ( httpServletRequest );

        Page<Map<String,Object>> page=new Page<> ();

        ResponseResult responseResult=ResponseResult.getResponseResult ();
        //设置查询参数
        page.setParams ( paramsJsonMap );

        Page.setPageInfo ( page, paramsJsonMap);

        //
        tiKuService.getTikuList ( page );
        //

        responseResult.setResult ( page );

        return responseResult;

    }

    /**
     * 更新题库信息
     * @param request
     * @return
     */
    @RequestMapping("updateTiKuInfo")
    public ResponseResult updateTiKuInfo(HttpServletRequest request){
        ResponseResult responseResult=ResponseResult.getResponseResult ();
        Map<String, Object> paramsJsonMap = CommonUtils.getParamsJsonMap ( request );
        tiKuService.updateTiKuInfo ( paramsJsonMap );
        responseResult.setSuccess ( "ok" );

        return responseResult;
    }
    /**
     * 删除题库信息
     * @param request
     * @return
     */
    @RequestMapping("deleteTiKuInfo")
    public ResponseResult deleteTiKuInfo(HttpServletRequest request){
        ResponseResult responseResult=ResponseResult.getResponseResult ();
        Map<String, Object> paramsJsonMap = CommonUtils.getParamsJsonMap ( request );
        tiKuService.deleteTiKuInfo ( paramsJsonMap );
        responseResult.setSuccess ( "ok" );

        return responseResult;
    }
    /**
     * 从redis中获取题库列表信息
     * @param request
     * @return
     */
    @RequestMapping("getTikuListFromRedis")
    public ResponseResult getTikuListFromRedis(HttpServletRequest request){

        List<Map<String,Object>> range = redisTemplate.opsForList ().range ( MyRedisKey.TIKU.getKey (), 0, -1 );

        ResponseResult responseResult=ResponseResult.getResponseResult ();

        responseResult.setResult ( range );

        return responseResult;

    }

    /**
     * 手动添加试题
     * @return
     */
    @RequestMapping("toAddShiTi")
    public ResponseResult toAddShiTi(HttpServletRequest request){

        ResponseResult responseResult=ResponseResult.getResponseResult ();

        String canshu = request.getParameter ( "canshu" );

        //获取请求数据
        Map<String, Object> parameterMap = CommonUtils.getParameterMap ( request );

        Map<String, Object> paramsJsonMap = CommonUtils.getParamsJsonMap ( request );
        paramsJsonMap.put ( "id",UID.getUUIDOrder () );

       /* JSON.toJSONString(parameterMap);
        String json=JSON.toJSONString ( parameterMap );

        String  json2=json.substring ( 2,json.length ()-2);

        JSONObject jsonObject = JSON.parseObject ( json2 );*/
        tiKuService.toAddShiTi ( paramsJsonMap );

        return responseResult;

    }

    /**
     * 试题列表
     * @param httpServletRequest
     * @return
     */
    @RequestMapping("getShiTiList")
    public ResponseResult getShiTiList(HttpServletRequest httpServletRequest){
        Map<String, Object> paramsJsonMap = CommonUtils.getParamsJsonMap ( httpServletRequest );
        Page<Map<String,Object>> page=new Page<> ();
        ResponseResult responseResult=ResponseResult.getResponseResult ();
        //设置查询参数
        page.setParams ( paramsJsonMap );
        Page.setPageInfo ( page, paramsJsonMap);
        //
        tiKuService.getShiTiList ( page );
        responseResult.setResult ( page );
        return responseResult;
    }
    /**
     * 删除试题信息
     * @param request
     * @return
     */
    @RequestMapping("deleteShiTiInfo")
    public ResponseResult deleteShiTiInfo(HttpServletRequest request){
        ResponseResult responseResult=ResponseResult.getResponseResult ();
        Map<String, Object> paramsJsonMap = CommonUtils.getParamsJsonMap ( request );
        tiKuService.deleteShiTiInfo ( paramsJsonMap );
        responseResult.setSuccess ( "ok" );

        return responseResult;
    }

    /**
     * 试题列表Solr 查询
     * @param httpServletRequest
     * @return
     */
    @RequestMapping("getSolrShiTiList")
    public ResponseResult getSolrShiTiList(HttpServletRequest httpServletRequest) throws IOException, SolrServerException {
        ResponseResult responseResult = ResponseResult.getResponseResult();
        Page<TiMu> page = new Page<>();
        Map<String, Object> map = CommonUtils.getParamsJsonMap(httpServletRequest);
        Page.setPageInfo(page,map);
        //定义查询条件
        SolrQuery query = new SolrQuery();
        query.setStart((page.getPageNo()-1)*page.getPageSize());//起始索引
        query.setRows(page.getPageSize());//设置页面数据条数
        query.set("q","*:*");

        if(map.get("zonghe")!=null&&!"".equals(map.get("zonghe"))){
            query.addFilterQuery("zonghe:"+map.get("zonghe"));
        }
        if(map.get("shitileixing")!=null&&!"".equals(map.get("shitileixing"))){
            query.addFilterQuery("tixingid:"+map.get("shitileixing"));
        }
        if(map.get("nanduid")!=null&&!"".equals(map.get("nanduid"))){
            query.addFilterQuery("nanduid:"+map.get("nanduid"));
        }
        if(map.get("tikuid")!=null&&!"".equals(map.get("tikuid"))){
            query.addFilterQuery("tikuid:"+map.get("tikuid"));
        }
        if(map.get("shitizhuangtai")!=null&&!"".equals(map.get("shitizhuangtai"))){
            query.addFilterQuery("shitizhuangtai:"+map.get("shitizhuangtai"));
        }
        if(map.get("tigan")!=null&&!"".equals(map.get("tigan"))){
            query.addFilterQuery("tigan:"+map.get("tigan"));
        }

        //执行查询
        QueryResponse queryResponse = solrClient.query(query);
        List<TiMu> beans = queryResponse.getBeans(TiMu.class);
        //获取总条数
        page.setTotalCount((int) queryResponse.getResults().getNumFound());


        //获取响应的结果
        SolrDocumentList results = queryResponse.getResults ();

        page.setResultList(beans);
        responseResult.setResult(page);
        return responseResult;
    }

    /**
     * 下载数据模板【题目】
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("getExceltemplate")
    public void getExceltemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        File excelTemplate = FileUploadDownUtils.getExcelTemplate ( "exceltemplate/timu.xlsx" );
        FileUploadDownUtils.responseFileBuilder ( response,excelTemplate,"数据模板【题目】.xlsx" );
    }
    /**
     * 根据ID获取试题信息
     * @param request
     * @return
     */
    @RequestMapping("getShitiById")
    public ResponseResult getShitiById(HttpServletRequest request){

        ResponseResult responseResult=ResponseResult.getResponseResult ();

        Map<String, Object> parameterMap = CommonUtils.getParameterMap ( request );

        Map<String, Object> shiTiById = tiKuService.getShiTiById ( parameterMap );

        responseResult.setResult ( shiTiById );

        return responseResult;

    }
    /*
     *solr
     *查询并高亮字段
     * 根据题库的获取到整个题库内容
     *      * */
    @RequestMapping("/getSolrShiTi")
    public ResponseResult getSolrShiTi(HttpServletRequest request) throws IOException, SolrServerException {
        ResponseResult responseResult = ResponseResult.getResponseResult();
        Page<TiMu> page = new Page<>();
        Map<String, Object> map = CommonUtils.getParamsJsonMap(request);
        Page.setPageInfo(page,map);
        //定义查询条件
        SolrQuery query = new SolrQuery();
        query.setStart((page.getPageNo()-1)*page.getPageSize());//起始索引
        query.setRows(page.getPageSize());//设置页面数据条数
        query.set("q","*:*");


        if(map.get("zonghe")!=null&&!"".equals(map.get("zonghe"))){
            query.addFilterQuery("zonghe:"+map.get("zonghe"));
        }
        if(map.get("shitileixing")!=null&&!"".equals(map.get("shitileixing"))){
            query.addFilterQuery("tixingid:"+map.get("shitileixing"));
        }
        if(map.get("nanduid")!=null&&!"".equals(map.get("nanduid"))){
            query.addFilterQuery("nanduid:"+map.get("nanduid"));
        }
        if(map.get("tikuid")!=null&&!"".equals(map.get("tikuid"))){
            query.addFilterQuery("tikuid:"+map.get("tikuid"));
        }
        if(map.get("shitizhuangtai")!=null&&!"".equals(map.get("shitizhuangtai"))){
            query.addFilterQuery("shitizhuangtai:"+map.get("shitizhuangtai"));
        }
        if(map.get("tigan")!=null&&!"".equals(map.get("tigan"))){
            query.addFilterQuery("tigan:"+map.get("tigan"));
        }

        if(map.size()>0){
            //设置高亮
            query.setHighlight(true);
            //设置高亮字段
            query.addHighlightField("shitizhuangtai").addHighlightField("createuserid").addHighlightField("tixingid").
                    addHighlightField("tikuid").addHighlightField("nanduid").addHighlightField("laiyuan").
                    addHighlightField("tigan").addHighlightField("daan").addHighlightField("timujiexi").
                    addHighlightField("createtime");
            query.setHighlightSimplePre("<em style='color:red'>");
            query.setHighlightSimplePost("</em>");
        }

        //执行查询
        QueryResponse queryResponse = solrClient.query(query);
        List<TiMu> beans = queryResponse.getBeans(TiMu.class);
        //获取总条数
        page.setTotalCount((int) queryResponse.getResults().getNumFound());
        /*高亮字段替换原有字段*/
        if(map.get("zonghe")!=null&&!"".equals(map.get("zonghe"))){
            Map<String, Map<String, List<String>>> mapMap = queryResponse.getHighlighting();
            for (TiMu bean : beans) {
                mapMap.forEach((k,v)->{
                    if(bean.getId().equals(k)){
                        if(v.get("createtime")!=null){
                            bean.setCreatetime(v.get("createtime").get(0));
                        }
                        if (v.get("createuserid")!=null) {
                            bean.setCreateuserid(v.get("createuserid").get(0));
                        }
                        if (v.get("laiyuan")!=null) {
                            bean.setLaiyuan(v.get("laiyuan").get(0));
                        }
                        if (v.get("nanduid")!=null) {
                            bean.setNanduid(v.get("nanduid").get(0));
                        }
                        if (v.get("tigan")!=null) {
                            bean.setTigan(v.get("tigan").get(0));
                        }
                        if (v.get("tikuid")!=null) {
                            bean.setTikuid(v.get("tikuid").get(0));
                        }
                        if (v.get("timujiexi")!=null) {
                            bean.setTimujiexi(v.get("timujiexi").get(0));
                        }
                        if (v.get("tixingid")!=null) {
                            bean.setTixingid(v.get("tixingid").get(0));
                        }
                        if (v.get("shitizhuangtai")!=null) {
                            bean.setShitizhuangtai(v.get("shitizhuangtai").get(0));
                        }
                        if (v.get("daan")!=null) {
                            bean.setDaan(v.get("daan").get(0));
                        }
                    }
                });
            }
        }
        page.setResultList(beans);
        responseResult.setResult(page);
        return responseResult;
    }
}
