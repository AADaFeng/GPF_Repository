<template>
    <div>

      <!-- 【模糊查询】   记得初始化数据 formInline  -->
      <el-form :inline="true" :model="formInline" class="demo-form-inline">
        <el-form-item label="关键词">
          <el-input v-model="formInline.zonghe" placeholder="关键词"></el-input>
        </el-form-item>
        <el-form-item label="所属题库">
          <el-select v-model="formInline.tikuid" placeholder="所属题库">
            <el-option
              v-for="item in tikus"
              :key="item.value"
              :label="item.tikuname"
              :value="item.id">
            </el-option>

          </el-select>
        </el-form-item>
        <el-form-item label="试题类型">
          <el-select v-model="formInline.shitileixing" placeholder="请选择">
            <el-option
              v-for="item in shititype"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="试题难度">
          <el-select v-model="formInline.nanduid" placeholder="试题难度">
            <el-option
              v-for="item in textss"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="试题状态">
          <el-select v-model="formInline.shitizhuangtai" placeholder="试题状态">
            <el-option
              v-for="item in shitistatus"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="排序">
          <el-select v-model="formInline.paixu" placeholder="排序">
            <el-option label="升序" value="1"></el-option>
            <el-option label="降序" value="0"></el-option>

          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">查询</el-button> <!-- 【模糊查询】 事件   -->
        </el-form-item>
      </el-form>
      <!-- 【表格】   -->
      <el-table
        :data="tableData"
        height="413"
        style="width:100%;margin-bottom:5px">
        <el-table-column
          prop="id"
          label="ID"
          width="180"> <!--show  【隐藏ID】 -->
        </el-table-column>
        <el-table-column
          prop="tikuid"
          label="所属题库"
          width="180">
        </el-table-column>
        <el-table-column
          prop="tixingid"
          label="试题类型"
          width="180">
        </el-table-column>
        <el-table-column
          prop="nanduid"
          label="试题难度"
          width="180">
        </el-table-column>
        <el-table-column
          prop="shitizhuangtai"
          label="试题状态"
          width="180">
        </el-table-column>
        <el-table-column
          prop="tigan"
          label="试题题干"
          width="180">
        </el-table-column>
        <el-table-column
          prop="createuserid"
          label="创建人"
          width="180">
        </el-table-column>
        <el-table-column
          v-if="show"
          prop="daan"
          label="答案"
          width="180">
        </el-table-column>
        <el-table-column
          v-if="show"
          prop="timujiexi"
          label="解析"
          width="180">
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="selectShiti(scope.row)">预览</el-button>
            <el-button size="mini" @click="openForUpdateShiTi(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger"  @click="deleteShiTi(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagefoot ref="pagefoot" @toforpage="toforpage"></pagefoot> <!--【分页】 000-->

      <!--【试题预览 弹出层 】 -->
      <el-dialog
        v-loading="loading3"
        element-loading-text="正在处理中。。。"
        element-loading-spinner="el-icon-loading"
        element-loading-background="rgba(0, 0, 0, 0.8)"

        title="试题预览信息"
        :visible.sync="dialogVisible2"
        width="50%"
      >
        <!--【试题预览弹出层 】 表格 -->
        <el-form
          ref="form" :model="form"
          label-width="80px"
          :style="{width:'50%'}">
          <el-form-item label="试题类型">
            <el-input v-model="form.tixingid"></el-input>

          </el-form-item>
          <el-form-item label="试题难度">
            <el-input v-model="form.nanduid"></el-input>

          </el-form-item>
          <el-form-item label="题库名称">
            <el-input v-model="form.tikuid"></el-input>

          </el-form-item>
          <el-form-item label="试题状态">
            <el-input v-model="form.shitizhuangtai"></el-input>
            <el-input v-if="show" v-model="form.id"></el-input>
          </el-form-item>
          <el-form-item label="试题题干">
            <el-input v-model="form.tigan"></el-input>

          </el-form-item>
          <el-form-item label="选项设置">
            <el-input v-model="form.tikuname"></el-input>

          </el-form-item>

          <el-form-item label="题库答案">
            <el-input type="textarea" v-model="form.daan"></el-input>
          </el-form-item>

          <el-form-item label="答案解析">
            <el-input type="textarea" v-model="form.timujiexi"></el-input>
          </el-form-item>
        </el-form>
        <el-button @click="dialogVisible2 = false">返 回</el-button>
      </el-dialog>

      <!-- 【编辑试题】-->
      <el-dialog
        title="编辑试题"
        :visible.sync="dialogVisible1"
        width="80%"
        @opened="fuzhihuixian"
      >
        <editorShitiXiuGai ref="shitigengxin"  :timuidd="timuidd" :tixingid="tixingid"></editorShitiXiuGai>
      </el-dialog>
    </div>
</template>

<script>
  import pagefoot from '../../common/pagefoot.vue'   //【分页】 工具插件导入 000
  import editorShitiXiuGai from './editorShitiXiuGai.vue'

    export default {
      components:{
        pagefoot,
        editorShitiXiuGai
      },
        name: "shiti01guanli",
      data(){
          return{
            loading3:false,
            textss:this.domain.textss, //试题难度
            shititype:this.domain.shititype, //试题类型
            shitistatus:this.domain.shitistatus,//试题状态
            tableData:[],
            tikus:[],
            show:false,
            dialogVisible1:false,
            dialogVisible2:false,
            formInline:{
              tikuid:'',
              shitileixing:'',
              zonghe:"",
              nanduid:"",
              shitizhuangtai:"",
              paixu:"",
            },
            form:{
              tixingname:'',
              nandu:'',
              tikuname:'',
              shititype:'',
              shitizhuangtai:'',
              tigan:'',
              tikuname:'',
              daan:"",
              timujiexi:"",
            },

          }
      },
      methods:{
        getTiKu(){//从Redis中获取题库信息
          this.$axios.post(this.domain.serverpath+'tiku/getTikuListFromRedis').then((response)=>{
            this.$data.tikus=response.data.result
          })
        },
        onSubmit(){
          alert("asas")
          //定义变量
          let pageInfo=this.$data.formInline
          //获取后台数据
          this.$axios.post(this.domain.serverpath+"tiku/getSolrShiTiList",JSON.stringify(pageInfo)).then((response)=>{
            this.$data.tableData=response.data.result.resultList
            //设置总条数
            this.$refs.pagefoot.total=response.data.result.totalCount
          }).catch((error)=>{

          })
        },
        toforpage(pageInfo){  // 【分页】 上下页跳转
          pageInfo.tikuid=this.$data.formInline.tikuid;
          pageInfo.shitileixing=this.$data.formInline.shitileixing
          pageInfo.nanduid=this.$data.formInline.nanduid
          pageInfo.shitizhuangtai=this.$data.formInline.shitizhuangtai
          //获取后台数据
          this.$axios.post(this.domain.serverpath+"tiku/getSolrShiTiList").then((response)=>{
            this.$data.tableData=response.data.result.resultList
            //设置总条数
            this.$refs.pagefoot.total=response.data.result.totalCount
          }).catch((error)=>{

          })
        },
        selectShiti(row){
          alert("sssdd"+JSON.stringify(row))
          this.$data.dialogVisible2=true
        //****************************
          if(row.shitizhuangtai=="1"){  //状态 预览数据  【试题编辑】
            row.shitizhuangtai="开放"
          }else{
            row.shitizhuangtai="关闭"
          }
          if(row.tixingid=="1"){  //题型  【试题编辑】
            row.tixingid="单选题"
          }else if(row.tixingid=="2"){
            row.tixingid="多选题"
          }else if(row.tixingid=="3"){
            row.tixingid="判断题"
          }else if(row.tixingid=="4"){
            row.tixingid="填空题"
          }else{
            row.tixingid="问答题"
          }
          if(row.nanduid=="1"){  //难度  【试题编辑】
            row.nanduid="简单"
          }else if(row.nanduid=="2"){
            row.nanduid="一般"
          }else if(row.nanduid=="3"){
            row.nanduid="稍难"
          }else if(row.nanduid=="4"){
            row.nanduid="困难"
          }else{
            row.nanduid="汗颜难"
          }
          //****************************
          this.$data.form=row
        },
        openForUpdateShiTi(row){
        // alert("进入编辑"+JSON.stringify(row))
         // this.$refs.editorShitiXiuGai.formInline=row
          this.$data.dialogVisible1=true

          //this.$data.form=row
        },
        saveUpdate(){ //<!--【题库编辑保存更新题库信息 -->
          this.$data.loading3=true
          this.$axios.post(this.domain.serverpath+"tiku/updateShiTiInfo",JSON.stringify(this.$data.form)).then((response)=>{
            this.$message({
              duration:2000,
              message: '恭喜你，这是一条成功消息',
              type: 'success'
            });
            this.$data.loading3=true
            //清空表单
            this.$data.form={}
            //关闭弹出层
            this.$data.dialogVisible2=false
            //重新加载数据
            this.toforpage({})

          })

        },
        deleteShiTi(row){
          // 通过JSON.stringify(row)   把id 传到后台
          this.$axios.post(this.domain.serverpath+"tiku/deleteShiTiInfo",JSON.stringify(row)).then((response)=>{
            this.$message({
              duration:2000,
              message: '恭喜你，操作顺利',
              type: 'success'
            });
            //重新加载数据  重新刷新下表格
            this.toforpage({})

          })
        },
        fuzhihuixian(){//试题编辑的回显
          //进行赋值
          this.$refs.shitigengxin.getTiMuById();
          //试题编辑题型变更框
          this.$refs.shitigengxin.shitileixingchangeeditor()

        },
      },
      mounted(){
        //获取后台数据
        this.$axios.post(this.domain.serverpath+"tiku/getSolrShiTiList").then((response)=>{
          this.$data.tableData=response.data.result.resultList
          //设置总条数
          this.$refs.pagefoot.total=response.data.result.totalCount
        }).catch((error)=>{

        });
        //加载题库数据
        this.getTiKu();
        //初始化分页
        this.toforpage(this.$data.formInline);

      }
    }
</script>

<style scoped>

</style>
