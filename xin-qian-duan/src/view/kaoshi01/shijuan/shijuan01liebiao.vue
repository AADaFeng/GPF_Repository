<template>
  <div>
    <el-form :inline="true" :model="formInline" class="demo-form-inline">
      <el-form-item label="试卷名称">
        <el-input style="width: 150px" v-model="formInline.shijuanname" placeholder="试卷"></el-input>
      </el-form-item>
      <el-form-item label="试卷分类">
        <el-select style="width: 150px" v-model="formInline.fenleiid" placeholder="从redis中获取试卷分类">
          <el-option label="安全类" value="0"></el-option>
          <el-option label="会计类" value="1"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="试卷类型">
        <el-select style="width: 150px" v-model="formInline.shijuanleixing" placeholder="从redis中获取试卷类型">
          <el-option label="普通试卷" value="0"></el-option>
          <el-option label="随即试卷" value="1"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="试卷状态">
        <el-select style="width: 150px" v-model="formInline.zhuangtai" placeholder="从redis中获取试卷分类">
          <el-option label="开放" value="0"></el-option>
          <el-option label="关闭" value="1"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">搜索</el-button>
      </el-form-item>
    </el-form>

    <!--表格数据展示部分-->
    <el-table
      :data="tableData"
      style="width: 100%">
      <el-table-column prop="shijuanname" label="试卷名称" width="100"></el-table-column>
      <el-table-column prop="zhuangtai" label="试卷状态" width="100"></el-table-column>
      <el-table-column prop="starttime" label="时间开始" width="230"></el-table-column>
      <el-table-column prop="endtime" label="结束时间" width="230"></el-table-column>
      <el-table-column prop="shijian" label="考试时长" width="100"></el-table-column>
      <el-table-column prop="shijuanleixing" label="试卷类型" width="100"></el-table-column>
      <el-table-column prop="zongfen" label="卷面总分" width="100"></el-table-column>
      <el-table-column prop="renshu" label="考试人数" width="100"></el-table-column>
      <el-table-column prop="userid" label="创建人" width="100"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="mini" type="danger"
                     icon="el-icon-circle-plus" >选择</el-button>
        </template>
      </el-table-column>

    </el-table>

    <!--分页组件-->
    <pagefoot ref="pagefoot" @toforpage="toforpage"></pagefoot>
    <el-button size="mini" type="danger"  @click="getDa" >获取表格数据</el-button>
  </div>
</template>

<script>
  import pagefoot from '../../common/pagefoot'
  export default {
    name: "shijuan01liebiao",
    components:{pagefoot},
    data(){
      return{
        starttime:"",
        endtime:"",
        tableData:[],
        formInline:{
          shijuanname:'',
          fenleiid:'',
          shijuanleixing:'',
          zhuangtai:''
        }
      }
    },
    methods:{
      getDa(){
        alert(JSON.stringify(this.$data.tableData))
      },

      onSubmit() {
        console.log('submit!');
      },
      toforpage(pageInfo){//此方法改变页码的时候与改变页面大小的时候触发
        //pageinfo需要包含了查询条件
        //这个ajax请求是为了获取数据,以及总条数
        this.$axios.post(this.domain.serverpath+"shijuan/ShiJuanList").then((response)=>{
          this.$data.tableData=response.data.result.resultList
          //设置总条数
          this.$refs.pagefoot.total=response.data.result.totalCount
        }).catch((error)=>{

        })
      }
    },
    mounted(){

      //这个ajax请求是为了获取数据,以及总条数
      this.$axios.post(this.domain.serverpath+"shijuan/ShiJuanList").then((response)=>{
        this.$data.tableData=response.data.result.resultList
        //设置总条数
        this.$refs.pagefoot.total=response.data.result.totalCount
      }).catch((error)=>{

      })

      this.toforpage()

    },
  }
</script>

<style scoped>

</style>
