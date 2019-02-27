<template>
    <div>
      <el-alert
        title="题库管理"
        type="success"
        description="文字说明文字说明文字说明文字说明文字说明文字说明"
        :closable="false"
      >
      </el-alert>
      <el-tabs type="border-card"  @tab-click="handleClick">
        <el-tab-pane label="新增题库">
               <xinzengtiku></xinzengtiku>
        </el-tab-pane>
        <el-tab-pane label="题库列表">
               <tikuliebiao ref="tikuliebiao"></tikuliebiao>
        </el-tab-pane>
      </el-tabs>
    </div>
</template>

<script>
    import xinzengtiku from './xinzengtiku.vue'
    import tikuliebiao from './tikuliebiao.vue'
    export default {
        components:{
          xinzengtiku,
          tikuliebiao
        },
        name: "tikuguanli",
        data(){
          return{

          }
        },
        methods:{
          handleClick(tab, event){
             if(tab.$options.propsData.label=="题库列表"){
                 this.$refs.tikuliebiao.flashPage()
             }
          }
        }
    }

</script>

<style scoped>
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
  .bg-purple-light {
    background: #e5e9f2;
  }
</style>
