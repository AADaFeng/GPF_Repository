<template>
  <div>
      <div id="bingzhuangtu" style="width:350px;height:400px"></div>
  </div>
</template>

<script>
    export default {
        name: "tikufenxi",
        data(){
          return{
             tubiaoText:"",
             datau:[],
             tubiaoData:[]
          }
        },
        methods:{
            setBingTuData(databing){
                this.$data.tubiaoData=databing.tubiaoData;
                this.$data.datau=databing.data;
                this.$data.tubiaoText="该库的总试题量-"+databing.countnum;
                //渲染视图
                this.showTubiao()
            },
            showTubiao(){//饼状图
              var echarts = require('echarts');
              // 基于准备好的dom，初始化echarts实例
              var myChart = echarts.init(document.getElementById('bingzhuangtu'));
              //options
              let options={
                title : {
                  text:this.$data.tubiaoText,
                  subtext: '',
                  x:'center'
                },
                tooltip : {
                  trigger: 'item',
                  formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                legend: {
                  orient: 'vertical',
                  left: 'left',
                  data:this.$data.datau
                },
                series : [
                  {
                    name: '访问来源',
                    type: 'pie',
                    radius : '40%',
                    center: ['60%', '50%'],
                    data:this.$data.tubiaoData,
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
        }
    }
</script>

<style scoped>
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
  .bg-purple {
    background: #d3dce6;
  }
</style>
