<template>
   <div>
     <el-tabs type="border-card"  @tab-click="handleClick">
       <el-tab-pane label="试题新增">
           <editorShiti01></editorShiti01>
       </el-tab-pane>
       <el-tab-pane label="试题管理">
         <shiti01guanli></shiti01guanli>
       </el-tab-pane>
       <el-tab-pane label="批量导入试题">
         <importshitidata ref="importshiti"></importshitidata>
       </el-tab-pane>
     </el-tabs>
   </div>
</template>

<script>
    import  editorShiti01 from './shiti/editorShiti01.vue'
    import  shiti01guanli from './shiti/shiti01guanli.vue'
    import importshitidata from './shiti/importShiTiData.vue'

    export default {
       components:{
         editorShiti01,
         shiti01guanli,
         importshitidata
       },
        name: "xinzengshiti01",
      data(){
          return{

          }
      },
      methods:{// tab标签  切换小卡片时 被选中时触发
        handleClick(tab,event){
          if(tab.$options.propsData.label=="新增试题"){

          }else if(tab.$options.propsData.label=="批量导入试题"){
            this.$refs.importshiti.getTiKuList()
          }
        }
      },
    }
</script>

<style scoped>

</style>
