<template>
   <div>


     <h1>=========================动态的添加生成元素===========================</h1>
     <el-button @click="createyuansu">动态生成元素</el-button>

     <el-checkbox-group v-model="checkList">
       <div v-for="(item,index) in array" :key="index"  style="width:80%">
           <el-row>
               <el-checkbox :label="checkLabels[index]" :true-label="xuanzhongdezhi[index]"></el-checkbox>
               <el-input v-model="inputValues[index]" placeholder="请输入内容" style="width:30%"></el-input>
               <el-button type="danger" icon="el-icon-delete" circle @click="delindex(index)"></el-button>
           </el-row>

       </div>
     </el-checkbox-group>


     <h1>-----------------------------------</h1>
     <el-button @click="yyyy">获取选中</el-button>
     <el-checkbox-group v-model="checkboxGroup6">
       <el-checkbox label="备选项1" ></el-checkbox>
       <el-checkbox label="备选项2" ></el-checkbox>
     </el-checkbox-group>

     <br>

       <h1>=======================柱状图===============================</h1>
       <div id="main" style="width: 600px;height: 400px;"></div>

       <input type="button" @click="tttt" v-model="val">
       <br>
       <h1>=======================饼状图===============================</h1>
       <div id="main2" style="width: 600px;height: 400px;"></div>


       <h1>==========================富文本编辑器============================</h1>
       <Uediter :value="ueditor.value" :config="ueditor.config" ref="ue"></Uediter>
       <el-button @click="returnContent">显示编辑器内容</el-button>
       <el-button @click="getUEContent">获取编辑器内容</el-button>
       <div>{{dat.content}}</div>



   </div>
</template>

<script>

    import Uediter from '../uediter/uediter.vue'

    export default {
        name: "testpage",
        components:{
          Uediter
        },
        data(){
          return{
              checkeds:["A"],//===============
              xuanzhongdezhi:[""],
              checkLabels:["选项A"],
              inputValues:[''],
              array:[{value:"A"}],
              checkList:[],//===========
              checkboxGroup6:[],
              dat: {//UE
                content: 'UE编辑器'
              },
              ueditor: {//UE
                value: '',
                config: {
                  initialFrameWidth: 800,
                  initialFrameHeight: 320
                }
              },
             val:"XXXXX",
             zhu:{
               zdata:['衬衫', '羊毛衫', '雪纺衫', '裤子', '高跟鞋', '袜子','袜子0002'],
               zshu:[5, 20, 36, 10, 10, 20,30]
             },
             pie:{
               pieData:[{value:335, name:'直接访问'},{value:310, name:'邮件营销'},
                         {value:234, name:'联盟广告'},{value:135, name:'视频广告'},{value:1548, name:'搜索引擎'}
                        ],
               pieText:['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']

             }
          }
        },
        methods:{
          yyyy(){
             alert("选中--》"+JSON.stringify(this.$data.checkboxGroup6))
          },
          delindex(index){//根据下标删除数据
             let uu=[]
             this.$data.array.splice(index,1)
             this.$data.inputValues.splice(index,1)
             this.$data.checkLabels.splice(index,1)
             this.$data.xuanzhongdezhi.splice(index,1)
          },
          createyuansu(){
               alert(JSON.stringify("--选中显示-"+this.$data.xuanzhongdezhi)+"--已经选中--"+JSON.stringify(this.$data.checkList)+"--项目--"+JSON.stringify(this.$data.checkLabels.length))
               let uu={value:"B"}
               this.$data.inputValues.push("")
               this.$data.checkLabels.push("选项A"+this.$data.checkLabels.length)
               this.$data.array.push(uu)
               this.$data.xuanzhongdezhi.push("-A"+this.$data.checkLabels.length)
          },
          getUEContent(){
            let yy=this.$refs.ue.getUEContent();
             alert(yy)
          },
          returnContent () {//UE
            this.dat.content = this.$refs.ue.getUEContent()
          },
          tttt(){
             this.$data.zhu.zdata=['衬衫12', '羊毛衫2', '雪纺衫2', '裤子2', '高跟鞋2', '袜子2','袜子22']
             this.$data.zhu.zshu=[52, 23, 6, 10, 10, 20,35]
             this.drow()

             this.$data.pie.pieText=['直接访问','邮件营销','联盟广告','视频广告','搜索引擎','搜索00引擎']
             let uu={value:880, name:'搜索00引擎'}
             this.$data.pie.pieData.push(uu)
             this.drowpie()//饼状图
          },
          drowpie(){//饼状图
            var pieecharts=require('echarts')
            var myChart1 = pieecharts.init(document.getElementById('main2'));
            myChart1.setOption({
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
                data:this.$data.pie.pieText
              },
              series : [
                {
                  name: '访问来源',
                  type: 'pie',
                  radius : '55%',
                  center: ['50%', '60%'],
                  data:this.$data.pie.pieData,
                  itemStyle: {
                    emphasis: {
                      shadowBlur: 10,
                      shadowOffsetX: 0,
                      shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                  }
                }
              ]
            })
          },
          drow(){
            var echarts = require('echarts');
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));
            // 绘制图表
            myChart.setOption({
              title: {
                text: 'ECharts 入门示例02'
              },
              tooltip: {},
              xAxis: {
                data: this.$data.zhu.zdata
              },
              yAxis: {},
              series: [{
                name: '销量',
                type: 'bar',
                data: this.$data.zhu.zshu
              }]
            });
          }
        },
        mounted(){
             //生成图片
            this.drow()
            this.drowpie()

        }
    }
</script>

<style scoped>

</style>
