<template>
 <div>
   <br>
   <!-- 【模糊查询】   记得初始化数据 formInline  -->
   <el-form :inline="true" :model="formInline" class="demo-form-inline">
     <el-form-item label="题库名称">
       <el-input v-model="formInline.tikuname" placeholder="题库名称"></el-input>
     </el-form-item>
     <el-form-item label="题库状态">
       <el-select v-model="formInline.tikuzhuangtai" placeholder="题库状态">
         <el-option label="开放" value="1"></el-option>
         <el-option label="关闭" value="0"></el-option>
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
       prop="tikuname"
       label="题库名称"
       width="180">
     </el-table-column>
     <el-table-column
       v-if="show"
       prop="id"
       label="ID"
       width="180"> <!--show  【隐藏ID】 -->
     </el-table-column>
     <el-table-column
       prop="tikuzhuangtai"
       label="状态"
       width="180">
     </el-table-column>
     <el-table-column
       prop="tikuzhuangtai"
       label="试题数量"
       width="180">
     </el-table-column>
     <el-table-column
       prop="tikushuoming"
       label="题库说明"
       width="180">
     </el-table-column>
     <el-table-column label="操作">
       <template slot-scope="scope">
         <el-button
           size="mini"
           @click="openShuJuFenXi(scope.row)">数据分析</el-button>

         <el-button
           size="mini"
           type="success"
           icon="el-icon-sort-up"
           @click="exportExcelData(scope.row)">试题导出</el-button>

         <el-button size="mini" @click="openForUpdateTiKu(scope.row)">编辑</el-button>
         <el-button size="mini" type="danger"  @click="deleteTiKu(scope.row)">删除</el-button>
       </template>
     </el-table-column>
   </el-table>
   <pagefoot ref="pagefoot" @toforpage="toforpage"></pagefoot> <!--【分页】 000-->


    <!-- 【数据分析】 饼状图 -->
       <!-- 【数据分析】 dialog 对话框 -->
   <el-dialog
     title="编辑题库信息00"
     :visible.sync="dialogVisible"
     width="35%">

     <bingzhuangtu></bingzhuangtu> <!-- 【数据分析】 饼状图加载   -->

     <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
  </span>
   </el-dialog>

   <!--【题库编辑弹出层 】 -->
   <el-dialog
     v-loading="loading3"
     element-loading-text="正在处理中。。。"
     element-loading-spinner="el-icon-loading"
     element-loading-background="rgba(0, 0, 0, 0.8)"

     title="编辑题库信息"
     :visible.sync="dialogVisible2"
     width="50%"
   >
     <!--【题库编辑弹出层 】 表格 -->
     <el-form
       ref="form" :model="form"
       label-width="80px"
       :style="{width:'50%'}">

       <el-form-item label="题库名称">
         <el-input v-model="form.tikuname"></el-input>
         <el-input v-if="show" v-model="form.id"></el-input>
       </el-form-item>
       <el-form-item label="开放状态">
         <el-select v-model="form.tikuzhuangtai" placeholder="请选择开放状态">
           <el-option label="开放" value="1"></el-option>
           <el-option label="关闭" value="0"></el-option>
         </el-select>
       </el-form-item>

       <el-form-item label="题库说明">
         <el-input type="textarea" v-model="form.tikushuoming"></el-input>
       </el-form-item>
     </el-form>
     <el-button @click="dialogVisible2 = false">取 消</el-button>
     <el-button type="primary" @click="saveUpdate">确 定</el-button>
   </el-dialog>


 </div>

</template>

<script>

   import pagefoot from '../common/pagefoot.vue'   //【分页】 工具插件导入 000
   import bingzhuangtu from '../tiku01/tiku01Fenxi.vue' // 【图标绘制】  工具插件导入 111

    export default {
        name: "tiku01liebiao",
      components:{  //  注册  000
        pagefoot,
        bingzhuangtu
      },
      data(){
          return{
            loading3:false,
            dialogVisible:false,
            dialogVisible2:false,
            show:false,
            tableData:[],
            formInline:{
              tikuname:"",
              tikuzhuangtai:""
            },
            form:{
              tikuname:'',
              tikuzhuangtai:"",
              tikushuoming:"",
            },


          }
      },
      methods:{
        deleteTiKu(row){ //  【删除题库信息】
           alert("ddddddddddddd  "+JSON.stringify(row))
              // 通过JSON.stringify(row)   把id 传到后台
          this.$axios.post(this.domain.serverpath+"tiku/deleteTiKuInfo",JSON.stringify(row)).then((response)=>{
                this.$message({
                  duration:2000,
                  message: '恭喜你，操作顺利',
                  type: 'success'
               });
            //重新加载数据  重新刷新下表格
            this.toforpage({})

          })
        },
        saveUpdate(){ //<!--【题库编辑保存更新题库信息 -->
          this.$data.loading3=true
          this.$axios.post(this.domain.serverpath+"tiku/updateTiKuInfo",JSON.stringify(this.$data.form)).then((response)=>{
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
        openForUpdateTiKu(row){//编辑题库信息 <!--【题库编辑】 -->
          this.$data.dialogVisible2=true
            if(row.tikuzhuangtai=="开放"){  // 修改数据  【题库编辑】
               row.tikuzhuangtai="1"
            }else{
               row.tikuzhuangtai="0"
            }
          this.$data.form=row
         },
        openShuJuFenXi(row){//数据分析
           this.$data.dialogVisible=true
        },
        onSubmit(){   // 【模糊查询】 事件
              alert("ccc")
          //定义变量
          let pageInfo=this.$data.formInline

          //获取后台数据
          this.$axios.post(this.domain.serverpath+"tiku/getTikuList",JSON.stringify(pageInfo)).then((response)=>{
            this.$data.tableData=response.data.result.resultList
            //设置总条数
            this.$refs.pagefoot.total=response.data.result.totalCount
          }).catch((error)=>{

          })
        },
        toforpage(pageInfo){  // 【分页】 上下页跳转
          pageInfo.tikuname=this.$data.formInline.tikuname
          pageInfo.tikuzhuangtai=this.$data.formInline.tikuzhuangtai

          //获取后台数据
          this.$axios.post(this.domain.serverpath+"tiku/getTikuList",JSON.stringify(pageInfo)).then((response)=>{
            this.$data.tableData=response.data.result.resultList
            //设置总条数
            this.$refs.pagefoot.total=response.data.result.totalCount
          }).catch((error)=>{

          })
        }

      },
      mounted(){  //钩子函数
        //获取后台数据
        this.$axios.post(this.domain.serverpath+"tiku/getTikuList").then((response)=>{
        this.$data.tableData=response.data.result.resultList
        //设置总条数

        this.$refs.pagefoot.total=response.data.result.totalCount
      }).catch((error)=>{

      })
      }




    }
</script>

<style scoped>

</style>
