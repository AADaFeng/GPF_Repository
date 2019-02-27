<template>
  <div><div style="background: #ffffff"><br><navbar ref="navbar"></navbar><br></div>
    <el-tabs type="border-card" >
      <el-tab-pane  label="成绩分析">
        <chengjifenxi01 :testparerclassfly="testparerclassfly"></chengjifenxi01>
      </el-tab-pane>
      <el-tab-pane label="考试分析">
        <kaoshifenxi01 :testparerclassfly="testparerclassfly"></kaoshifenxi01>
      </el-tab-pane>
      <el-tab-pane label="试卷分析">
        <shijuanfenxi01 :testparerclassfly="testparerclassfly"></shijuanfenxi01>
      </el-tab-pane>
    </el-tabs></div>
</template>

<script>

    import chengjifenxi01 from "./fenxi/chengjifenxi01";
    import Kaoshifenxi01 from "./fenxi/kaoshifenxi01";
    import Shijuanfenxi01 from "./fenxi/shijuanfenxi01";
    export default {
      components: {Shijuanfenxi01, Kaoshifenxi01, chengjifenxi01},
        name: "tongji01guanli",

    }
</script>

<style scoped>

</style>
