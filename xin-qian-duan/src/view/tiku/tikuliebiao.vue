<template>
    <div  v-loading="loading">
      <br>
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
          <el-button type="primary" @click="onSubmit">查-询</el-button>
        </el-form-item>
      </el-form>
      <el-table
        :highlight-current-row="hcr"
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
          width="180">
        </el-table-column>
        <el-table-column
          v-if="show"
          prop="tikuid"
          label="TIKUID"
          width="180">
        </el-table-column>
        <el-table-column
          prop="tikuzhuangtai"
          label="状态"
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
              type="primary"
              icon="el-icon-upload2" @click="daochutiku(scope.row)">题库试题导出</el-button>

            <el-button
              size="mini"
              @click="openForUpdateTiKu(scope.row)"
              >编辑</el-button>
            <el-button
              size="mini"
              type="danger"
              >删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagefoot ref="pagefoot" @toforpage="toforpage"></pagefoot>

      <!-- 数据分析 -->
      <el-dialog
        title="题库题型分析"
        :visible.sync="dialogVisible1"
        width="50%"
        >

        <bingzhuangtu ref="bingtu"></bingzhuangtu>

        <el-button @click="dialogVisible1 = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible1 = false">确 定</el-button>
      </span>
      </el-dialog>


      <el-dialog

        v-loading="loading3"
        element-loading-text="正在处理中。。。"
        element-loading-spinner="el-icon-loading"
        element-loading-background="rgba(0, 0, 0, 0.8)"

        title="编辑题库信息"
        :visible.sync="dialogVisible2"
        width="50%"
      >

        <el-form
          ref="form" :model="form"
          label-width="80px"
          :style="{width:'50%'}">

          <el-form-item label="题库名称-">
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
        </span>
      </el-dialog>

    </div>
</template>

<script>
    import pagefoot from '../common/pagefoot.vue'
    import bingzhuangtu from './tikuFenxi.vue'
    import addtiku from './xinzengtiku.vue'



    export default {
        components:{
          pagefoot,
          bingzhuangtu,
          addtiku
        },
        name: "tikuliebiao",
        data(){
          return{
            hcr:true,
            loading:false,
            loading3:false,
            dialogVisible1:false,
            dialogVisible2:false,
            show:false,
            tableData:[],
            formInline:{
              tikuname:"",
              tikuzhuangtai:""
            },
            form:{
              tikuzhuangtai:"",
              tikuname:"",
              tikushuoming:""
            }
          }
        },
        methods:{
          saveUpdate(){//保存更新题库信息
              this.$data.loading3=true
              this.$axios.post(this.domain.serverpath+'tiku/updateTiKuInfo',JSON.stringify(this.$data.form)).then((response)=>{
                this.$message({
                  duration:2000,
                  message: '恭喜你，这是一条成功消息',
                  type: 'success'
                });
                this.$data.loading3=false
                //清空表单
                this.$data.form={}
                //关闭弹出层
                this.$data.dialogVisible2=false
                //重新加载数据
                this.toforpage({})
              })
          },
          openShuJuFenXi(row){//数据分析
              this.$data.dialogVisible1=true;
              //打开加载层
              this.$data.loading=true;
              this.$axios.post(this.domain.serverpath+'tiku/toFenxiTiku',JSON.stringify({tikuid:row.id})).then((response)=>{
                this.$refs.bingtu.setBingTuData(response.data.result)
                this.$data.loading=false;
              })
          },
          openForUpdateTiKu(row){//编辑题库信息
              this.$data.dialogVisible2=true
              if(row.tikuzhuangtai=="开放"){
                 row.tikuzhuangtai="1"
              }else{
                 row.tikuzhuangtai="0"
              }
              this.$data.form=row
          },
          onSubmit(){

          },
          toforpage(pageInfo){
            pageInfo.tikuname=this.$data.formInline.tikuname
            pageInfo.tikuzhuangtai=this.$data.formInline.tikuzhuangtai
            //获取数据
            this.$axios.post(this.domain.serverpath+"tiku/getTikuList",JSON.stringify(pageInfo)).then((response)=>{
              this.$data.tableData=response.data.result.resultList
              //设置总条数
              this.$refs.pagefoot.total=response.data.result.totalCount
            }).catch((error)=>{

            })
          },
          flashPage(){
             this.$refs.pagefoot.currentPageChange(1)
          },
          daochutiku(row){//导出题库试题 this.domain.serverpath+'test/exportExcelData'
             this.$data.loading=true;
             window.location.href=this.domain.serverpath+'test/exportExcelData?tikuid='+row.tikuid
             this.$notify.info({
              title: '消息',
              message: '正在导出数据,可能比较慢，请耐心等待^_^'
             });
             this.$data.loading=false;
          }
        },
        mounted(){
            //获取数据
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
