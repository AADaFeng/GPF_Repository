<template>
    <div>

      <br>
      <el-form :inline="true" :model="formInline" class="demo-form-inline">
        <el-form-item label="用1户名">
          <el-input v-model="formInline.userName" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="formInline.sex" placeholder="性别1">
            <el-option label="男" value="1"></el-option>
            <el-option label="女" value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">查询</el-button>
        </el-form-item>
      </el-form>

      <el-table
        :data="tableData"
        height="485"
        border
        style="width:100%">

        <el-table-column
          prop="userName"
          label="用户名"
          width="180">

          <template slot-scope="scope">
            <el-popover trigger="hover" placement="top">
              <p>用户姓名: {{ scope.row.userName }}</p>
              <p>登录名: {{ scope.row.loginName }}</p>
              <p>性别: {{ scope.row.sex }}</p>
              <div slot="reference" class="name-wrapper">
                <el-tag size="medium">{{ scope.row.userName }}</el-tag>
              </div>
            </el-popover>
          </template>

        </el-table-column>
        <el-table-column
          prop="loginName"
          label="登录名"
          width="180">
        </el-table-column>
        <el-table-column
          prop="sex"
          label="性别">
        </el-table-column>

      </el-table>

      <pagefoot ref="pagefoot" @toforpage="toforpage"></pagefoot>
    </div>
</template>

<script>

    import pagefoot from '../common/pagefoot'

    export default {
        components:{
          pagefoot
        },
        name: "testpagefengzhuang",
        data(){
          return{
            tableData:[],
            formInline:{
              userName:"",
              sex:""
            }
          }
        },
        methods:{
          onSubmit(){
            let pageInfo=this.$data.formInline
            //获取数据
            this.$axios.post(this.domain.serverpath+"user/pageYanshi",JSON.stringify(pageInfo)).then((response)=>{
              this.$data.tableData=response.data.result.resultList
              //设置总条数
              this.$refs.pagefoot.total=response.data.result.totalCount
            }).catch((error)=>{

            })
          },
          toforpage(pageInfo){
            pageInfo.userName=this.$data.formInline.userName
            pageInfo.sex=this.$data.formInline.sex
            //获取数据
            this.$axios.post(this.domain.serverpath+"user/pageYanshi",JSON.stringify(pageInfo)).then((response)=>{
              this.$data.tableData=response.data.result.resultList
              //设置总条数
              this.$refs.pagefoot.total=response.data.result.totalCount
            }).catch((error)=>{

            })
          }
        },
        mounted(){
          //获取数据
          this.$axios.post(this.domain.serverpath+"user/pageYanshi").then((response)=>{
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
