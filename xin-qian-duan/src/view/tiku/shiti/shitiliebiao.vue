<template>
    <div >
      <br>
      <el-form :inline="true" :model="formInline" class="demo-form-inline">
        <el-form-item label="所属题库:" style="width:20%">
            <el-select v-model="formInline.tikuid" style="width:170px" filterable placeholder="请选择">
              <el-option
                v-for="item in tikus"
                :key="item.value"
                :label="item.tikuname"
                :value="item.id">
              </el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="试题类型:" prop="shitileixing" style="width:20%">
            <el-select v-model="formInline.shitileixing" placeholder="请选择" style="width:170px">
              <el-option
                v-for="item in shititype"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>

        </el-form-item>
        <el-form-item label="试题难度:" style="width:20%">
            <el-select v-model="formInline.nanduid" style="width:170px" placeholder="请选择">
              <el-option
                v-for="item in textsList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>

        </el-form-item>

        <el-form-item label="试题状态:" style="width:20%">

            <el-select v-model="formInline.shitizhuangtai" style="width:170px" placeholder="请选择">
              <el-option
                v-for="item in shitistatus"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>

        </el-form-item>

          <el-button type="primary" @click="qingkong">重-置</el-button>

          <el-button type="primary" >查-询</el-button>

        <el-form-item label="综合搜索:" style="width:100%">
          <div>
              <el-input placeholder="请输入关键字/题库名称/题干/时间/创建人" :style="{width:435+'px'}"  v-model="zonghesousuo">
              <el-button slot="append" type="info" plain icon="el-icon-search" @click="searcheinfo">搜索</el-button>
            </el-input>
          </div>
        </el-form-item>

      </el-form>
      <el-table
        border
        :highlight-current-row="hcr"
        :data="tableData"
        style="width:100%;margin-bottom:5px">
        <el-table-column
          type="selection"
          width="55"
          prop="id">
        </el-table-column>

        <el-table-column
          prop="tikuname"
          label="题库名称"
          width="100">
        </el-table-column>
        <el-table-column
          prop="shitileixing"
          label="试题类型"
          width="100">
        </el-table-column>
        <el-table-column
          v-if="tixingidShow"
          prop="tixingid"
          label="试题类型ID"
          width="100">
        </el-table-column>
        <el-table-column
          prop="shitizhuangtai"
          label="试题状态"
          width="100">
        </el-table-column>
        <el-table-column
          prop="shitizhuangtai"
          label="试题数量"
          width="80">
        </el-table-column>
        <el-table-column
          prop="tigan"
          label="试题题干"
          width="200">
        </el-table-column>
        <el-table-column
          prop="nanduid"
          label="试题难度"
          width="80">
        </el-table-column>
        <el-table-column
          prop="createuser"
          label="创建人"
          width="200">
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">

            <el-button
              size="mini"
              >预览</el-button>

            <el-button
              size="mini"
              type="primary"
              icon="el-icon-edit"
              @click="xiugaishiti(scope.row)"
              >编辑</el-button>

            <el-button
              size="mini"
              type="danger"
            >删除</el-button>

          </template>
        </el-table-column>
      </el-table>

      <pagefoot ref="pagefoot" @toforpage="toforpage"></pagefoot>

      <!-- 编辑试题-->
      <el-dialog
        title="编辑试题"
        :visible.sync="dialogVisible1"
        width="80%"
        @opened="fuzhihuixian"
      >
        <editorShitiXiuGai ref="shitigengxin" :tixingid="tixingid"></editorShitiXiuGai>

        </span>
      </el-dialog>
    </div>
</template>

<script>
    import pagefoot from '../../common/pagefoot.vue'
    import editorShitiXiuGai from './editorShitiXiuGai.vue'
    export default {
        components:{
          pagefoot,
          editorShitiXiuGai
        },
        name: "shitiliebiao",
        data(){
          return{
            zonghesousuo:"",
            tixingidShow:false,
            tixingid:"",
            dialogVisible1:false,
            hcr:true,
            shititype:this.domain.shititype,//试题类型
            shitistatus:this.domain.shitistatus,//试题状态
            textsList:this.domain.textsList,//试题难度
            tikus:[],
            tableData:[],
            formInline:{
               tikuid:"",
               shitileixing:"",
               nanduid:"",
               shitizhuangtai:"",
            }
          }
        },
        methods:{
          qingkong(){
             this.$data.formInline={};
          },
          xiugaishiti(row){
             this.domain.userinfo.shitiid=row.id;
             this.domain.userinfo.tixingid2=row.tixingid;
             this.$data.dialogVisible1=true;
          },
          toforpage(pageInfo){
            pageInfo.tikuid=this.$data.formInline.tikuid;
            pageInfo.shitileixing=this.$data.formInline.shitileixing
            pageInfo.nanduid=this.$data.formInline.nanduid
            pageInfo.shitizhuangtai=this.$data.formInline.shitizhuangtai
            //获取数据
            let qs=require("qs")
            this.$axios.post(this.domain.serverpath+"tiku/togetShitiList",JSON.stringify(pageInfo)).then((response)=>{
              this.$data.tableData=response.data.result.resultList
              //设置总条数
              this.$refs.pagefoot.total=response.data.result.totalCount
            }).catch((error)=>{

            })
          },
          getTiKu(){//从Redis中获取题库信息
            this.$axios.post(this.domain.serverpath+'tiku/getTikuListFromRedis').then((response)=>{
              this.$data.tikus=response.data.result
            })
          },
          fuzhihuixian(){//试题编辑的回显
            //进行赋值
            this.$refs.shitigengxin.getTiMuById();
            //试题编辑题型变更框
            this.$refs.shitigengxin.shitileixingchangeeditor()

          },
          searcheinfo(){//综合搜索的方法

          }
        },
        mounted(){
          //获取题库
           this.getTiKu();
           //初始化分页
           this.toforpage(this.$data.formInline);
        }
    }
</script>

<style scoped>

</style>
