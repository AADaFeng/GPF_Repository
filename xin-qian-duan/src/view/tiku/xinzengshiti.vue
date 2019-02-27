<template>
  <div>
    <el-alert
      title="新增试题"
      type="success"
      description="文字说明文字说明文字说明文字说明文字说明文字说明"
      :closable="false"
      >
    </el-alert>

    <el-tabs type="border-card"  @tab-click="handleClick">
      <el-tab-pane label="新增试题">
          <editorshiti ref="editorshiti" ></editorshiti>
      </el-tab-pane>
      <el-tab-pane label="试题管理">
          <shitiliebiao></shitiliebiao>
      </el-tab-pane>
      <el-tab-pane label="批量导入试题">
          <importshitidata ref="importshiti"></importshitidata>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
    import editorshiti from './shiti/editorShiti.vue'
    import shitiliebiao from './shiti/shitiliebiao.vue'
    import importshitidata from './shiti/importShiTiData.vue'
    export default {
        components:{
            editorshiti,
            shitiliebiao,
            importshitidata
        },
        name: "xinzengshiti",
        data(){
           return{

           }
        },
        methods:{
          handleClick(tab,event){
             if(tab.$options.propsData.label=="新增试题"){

             }else if(tab.$options.propsData.label=="批量导入试题"){
                 this.$refs.importshiti.getTiKuList()
             }
          }
        }
    }
</script>

<style scoped>

</style>
