<template>
     <div>
       <el-tabs type="border-card"  @tab-click="handleClick">
         <el-tab-pane label="新增题库">
           <xinzengtiku01></xinzengtiku01>
         </el-tab-pane>
         <el-tab-pane label="题库列表">
           <tiku01liebiao ref=""></tiku01liebiao>
         </el-tab-pane>
       </el-tabs>
     </div>
</template>

<script>
   import  xinzengtiku01 from './xinzengtiku01.vue'
   import  tiku01liebiao from './tiku01liebiao.vue'
    export default {
        components:{
            xinzengtiku01,
            tiku01liebiao,
        },
        name: "tiku01guanli",
      data(){
          return{

          }
      },
      methods:{
        handleClick(tab, event) {

        }
      },
    }
</script>

<style scoped>

</style>
<style scoped>
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
  .bg-purple-light {
    background: #e5e9f2;
  }
</style>
