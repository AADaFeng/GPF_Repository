package com.zbf.core.entity;

import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;



@Data
public class TiMu implements Serializable {

    @Field
    private  String id;
    @Field
    private String createuserid;
    @Field
    private String tixingid;
    @Field
    private String shitizhuangtai;
    @Field
    private String tikuid;
    @Field
    private String nanduid;
    @Field
    private String laiyuan;
    @Field
    private String tigan;
    @Field
    private String timujiexi;
    @Field
    private String daan;
    @Field
    private String createtime;
}
