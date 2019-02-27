<template>
  <div><div style="background: #ffffff"><br></div>
    <el-tabs type="border-card" >
      <el-tab-pane  label="创建试卷">
        <createShijuan01 ></createShijuan01>
      </el-tab-pane>
      <el-tab-pane label="快速创建试卷">
        <qcreateShijuan01 ></qcreateShijuan01>
      </el-tab-pane>
      <el-tab-pane label="试卷展示">
        <shijuan01liebiao ></shijuan01liebiao>
      </el-tab-pane>
    </el-tabs></div>
</template>

<script>
  import createShijuan01 from './shijuan/createShijuan01'
  import qcreateShijuan01 from './shijuan/qcreateShijuan01'
  import shijuan01liebiao from './shijuan/shijuan01liebiao'
  export default {
    components:{
      createShijuan01,
      qcreateShijuan01,
      shijuan01liebiao
    },
    name: "shijuan01guanli",
    data(){
      return{

      }
    },
    }
</script>

<style scoped>

</style>
