<template>
   <div>
     <el-tabs type="border-card"  @tab-click="handleClick">
       <el-tab-pane label="试题新增">
           <editorShiti01></editorShiti01>
       </el-tab-pane>
       <el-tab-pane label="试题管理">
         <xinzengtiku01></xinzengtiku01>
       </el-tab-pane>
       <el-tab-pane label="批量导入试题">
         <tiku01liebiao ref=""></tiku01liebiao>
       </el-tab-pane>
     </el-tabs>
   </div>
</template>

<script>
    import  editorShiti01 from './shiti/editorShiti01.vue'
    export default {
       components:{
         editorShiti01
       },
        name: "xinzengshiti01",
      data(){
          return{

          }
      },
      methods:{// tab标签  切换小卡片时 被选中时触发
        handleClick(tab,event){

        }
      },
    }
</script>

<style scoped>

</style>
