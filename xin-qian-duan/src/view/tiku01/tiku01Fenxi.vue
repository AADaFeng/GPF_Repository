<template>
    <div>

      <el-row><!-- 栅格布局。-->
        <el-col :span="4"><div class="grid-content bg-purple">题库名称 pppp</div></el-col>
        <el-col :span="12">{{pppp}}</el-col>
      </el-row>
      <el-row><!-- 栅格布局。-->
        <el-col :span="4"><div class="grid-content bg-purple">题库状态</div></el-col>
        <el-col :span="12">{{pppp}}</el-col>
      </el-row>
      <el-row><!-- 栅格布局。-->
        <el-col :span="4"><div class="grid-content bg-purple">试题数量</div></el-col>
        <el-col :span="12">{{pppp}}</el-col>
      </el-row>
      <br>
      <div id="bingzhuangtu" style="width:450px;height:450px"></div>

    </div>
</template>

<script>
    export default {
        name: "tiku01Fenxi",
      data(){
          return{
            pppp:"33333333333"

          }
      },
      methods:{
           showTubiao(){//饼状图  // 【图标绘制】
             var echarts = require('echarts');

// 基于准备好的dom，初始化echarts实例
             var myChart = echarts.init(document.getElementById('bingzhuangtu'));

             let options={
               title : {
                 text: '某站点用户访问来源',
                 subtext: '纯属虚构',
                 x:'center'
               },
               tooltip : {
                 trigger: 'item',
                 formatter: "{a} <br/>{b} : {c} ({d}%)"
               },
               legend: {
                 orient: 'vertical',
                 left: 'left',
                 data: ['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
               },
               series : [
                 {
                   name: '访问来源',
                   type: 'pie',
                   radius : '55%',
                   center: ['50%', '60%'],
                   data:[
                     {value:335, name:'直接访问'},
                     {value:310, name:'邮件营销'},
                     {value:234, name:'联盟广告'},
                     {value:135, name:'视频广告'},
                     {value:1548, name:'搜索引擎'}
                   ],
                   itemStyle: {
                     emphasis: {
                       shadowBlur: 10,
                       shadowOffsetX: 0,
                       shadowColor: 'rgba(0, 0, 0, 0.5)'
                     }
                   }
                 }
               ]
             }
             // 绘制图表
             myChart.setOption(options);
           }
      },
      mounted(){
          this.showTubiao()
      },
    }
</script>

<style scoped>    /*栅格样式设置*/
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
  .bg-purple {
    background: #d3dce6;
  }

</style>
