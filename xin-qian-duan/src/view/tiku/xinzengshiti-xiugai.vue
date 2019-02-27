<template>
  <div>
    <el-alert
      title="试题修改"
      type="success"
      description="题库管理->修改试题"
      :closable="false"
    >
    </el-alert>

    <el-tabs type="border-card"  @tab-click="handleClick">
      <el-tab-pane label="试题修改">
        <editorShitiXiuGai></editorShitiXiuGai>
      </el-tab-pane>
      <el-tab-pane label="试题管理">
         <shitiliebiao></shitiliebiao>
      </el-tab-pane>
      <el-tab-pane label="批量导入试题">

      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
  import editorShitiXiuGai from './shiti/editorShitiXiuGai.vue'
  import shitiliebiao from './shiti/shitiliebiao.vue'

  export default {
    components:{
      editorShitiXiuGai,
      shitiliebiao
    },
    name: "xinzengshiti",
    data(){
      return{

      }
    },
    methods:{
      handleClick(tab,event){
        if(tab.$options.propsData.label=="新增试题"){

        }
      }
    }
  }
</script>

<style scoped>

</style>
