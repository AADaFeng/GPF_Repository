<template>
  <div>
    <el-alert
      title="成绩分析"
      type="success"
      :closable="closable"
      description="可以对某一个试卷参加考试的人员的成绩进行分析，分析各个分数段的人数，便于对学员学习概况进行了解">
    </el-alert>
    <!-- 搜索代码信息start -->
    <el-row>
      <el-col :span="4">
        <div class="grid-content bg-purple" >
          <div style="padding-top: 10px;padding-right:30px;text-align: right"><span>选择试卷:</span></div>
        </div>
      </el-col>
      <el-col :span="20">
        <div class="grid-content">
          <div style="padding-top:5px;padding-left: 100px">

            <el-select v-model="shijuanid" filterable placeholder="请选择">
              <el-option
                v-for="item in shijuanoptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>

          </div>
        </div>
      </el-col>
    </el-row>
    <!-- 搜索代码信息end -->
    <!-- 分数区间的代码start -->
    <el-row>
          <el-col :span="4">
            <div class="grid-content bg-purple" >
              <div style="padding-top: 10px;padding-right:30px;text-align: right"><span>添加区间:</span></div>
            </div>
          </el-col>
          <el-col :span="20">
            <div class="grid-content" style="padding-left: 100px">
              <div style="padding-top:5px;">
                <el-button type="primary" @click="createyuansu">点击添加分数区间</el-button>
              </div>
        </div>
      </el-col>
    </el-row>
    <el-row>
          <el-col :span="4">
            <div class="grid-content bg-purple" >
              <div style="padding-top: 10px;padding-right:30px;text-align: right"><span>分数区间:</span></div>
            </div>
          </el-col>
          <el-col :span="20"><!-- 分数区间代码 -->
            <div class="grid-content"  style="padding-top:5px;padding-left: 100px">
              <div >
                  <div v-for="(item,index) in array" :key="index"  style="width:80%">
                    <el-row>
                      从<el-input v-model.number="fenshu1[index]" placeholder="请输入区间分数" style="width:10%"></el-input>
                      至<el-input v-model.number="fenshu2[index]" placeholder="请输入区间分数" style="width:10%"></el-input>
                      <el-button type="danger" icon="el-icon-delete" circle @click="delindex(index)"></el-button>
                    </el-row>
                  </div>
              </div>
              <br>
              <el-button type="primary" @click="kaishitongji">开始统计</el-button>
            </div>
          </el-col>
    </el-row>
    <!-- 分数区间的代码 end-->
    <!-- 分析展示区域start -->
    <!-- 分析展示区域 -->
    <br>
    <br>
    <el-row>
         <div style="padding-left:30%">
         <div id="main" style="width: 500px;height: 500px"></div>
         </div>
    </el-row>
    <!-- 分析展示区域end-->
  </div>
</template>

<script>
  export default {
    name: "chengjifenxi",
    data(){
      return{
        shijuanid:"",
        shijuanoptions:[],
        //添加分数区间的代码
        fenshu1:[0],
        fenshu2:[0],
        array:[{value:"A"}],
        //添加分数区间的代码
        loading:false,
        multiple:false,
        closable:false,
        bingdata:[{name:" ",value:0}],//饼图的数据
        bingtextdata:[" "],//饼图的项的数据
      }
    },
    methods: {

      chushihuabingtu(){//初始化饼状图
        var echarts = require('echarts');
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));
        // 绘制图表
        myChart.setOption({
          title : {text:'成绩分析图',subtext: '',x:'center'},
          tooltip : {trigger: 'item',formatter: "{a} <br/>{b} : {c} ({d}%)"},
          legend: {orient: 'vertical',left: 'left',data:this.$data.bingtextdata},
          series : [{
              name:'人数/百分比',type: 'pie',radius : '55%',center: ['50%', '60%'],
              data:this.$data.bingdata,
              itemStyle: {emphasis: {shadowBlur:10,shadowOffsetX:0,shadowColor: 'rgba(0, 0, 0, 0.5)'}
              }
          }]
        });

      },
      kaishitongji(){//开始统计功能
         //更新饼图的数据数据

      },
      createyuansu(){//添加区间成绩
        let uu={value:"B"}
        this.$data.fenshu1.push(0)
        this.$data.fenshu2.push(0)
        this.$data.array.push(uu)
      },
      delindex(index){//删除一个添加的区间
        let uu=[]
        this.$data.array.splice(index,1)
        this.$data.fenshu1.splice(index,1)
        this.$data.fenshu2.splice(index,1)
      }
    },
    mounted() {
      //初始化图表
      this.chushihuabingtu();
    }
  }
</script>

<style scoped>
  .bg-purple {
    background: #d3dce6;
  }
  .bg-purple-light {
    background: #e5e9f2;
  }
  .grid-content {
    border: #dddddd 1px solid;
    border-radius: 0px;
    min-height: 50px;
  }
</style>
