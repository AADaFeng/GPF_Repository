<template>
    <div>
      <!--表格数据展示部分-->
      <el-table
        :data="tableData"
        style="width: 100%">
        <el-table-column prop="id" label="ID" width="100"></el-table-column>
        <el-table-column prop="userName" label="用户名" width="100"></el-table-column>
        <el-table-column prop="zhenName" label="真实姓名" width="100"></el-table-column>
     <!--   <el-table-column prop="IDcard" label="证件编号" width="230"></el-table-column>
        <el-table-column prop="bumenid" label="所属部门" width="230"></el-table-column>
        <el-table-column prop="userzhuangtai" label="用户状态" width="100"></el-table-column>
        <el-table-column prop="logintime" label="最后登录时间" width="100"></el-table-column>-->

        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="mini" type="primary"   icon="el-icon-circle-plus" @click="addData(scope.row)"  >选择</el-button>
          </template>
        </el-table-column>

      </el-table>
      <!--分页组件-->
      <pagefoot ref="pagefoot" @toforpage="toforpage"></pagefoot>
      <el-button size="mini" type="danger"   >获取表格数据</el-button>
    </div>
</template>

<script>
  import pagefoot from '../../common/pagefoot'
    export default {
        name: "userInfo",
      components:{pagefoot},
      data(){
          return{
            tableData:[],
          }
      },
      props:{
        userList:String
      },
      methods:{
        addData(row){
          console.log(this.$parent.userLsit);
          alert(JSON.stringify(this.$data.userLsit))
          alert(JSON.stringify( this.$parent.userLsit))
          //alert(JSON.stringify(this.$data.tableData))
          alert(JSON.stringify(row.userName))

        },
        toforpage(pageInfo){//此方法改变页码的时候与改变页面大小的时候触发
          //pageinfo需要包含了查询条件
          //这个ajax请求是为了获取数据,以及总条数

          this.$axios.post(this.domain.serverpath+"shijuan/getUserInfo").then((response)=>{
            this.$data.tableData=response.data.result.resultList
            //设置总条数
            this.$refs.pagefoot.total=response.data.result.totalCount
          }).catch((error)=>{

          })
        }
      },
      mounted(){

        //这个ajax请求是为了获取数据,以及总条数
        this.$axios.post(this.domain.serverpath+"shijuan/getUserInfo").then((response)=>{
          this.$data.tableData=response.data.result.resultList
          //设置总条数
          this.$refs.pagefoot.total=response.data.result.totalCount
        }).catch((error)=>{

        })

        this.toforpage({})

      }
    }
</script>

<style scoped>

</style>
