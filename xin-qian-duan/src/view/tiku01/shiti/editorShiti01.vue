<template>
  <div
    v-loading="loading2"
    element-loading-text="稍等提交中..."
    element-loading-spinner="el-icon-loading"
    element-loading-background="rgba(0, 0, 0, 0.8)"
  ><!-- 试题编辑界面 -->
    <el-form :model="formInline" :rules="rules" ref="ruleForm" class="demo-form-inline">
      <el-form-item label="试题类型:">
        <div style="float: left;width:20px;height:50px"></div>
        <div style="float: left;width:80%">
          <el-select v-model="formInline.shitileixing" placeholder="请选择">
            <el-option

              v-for="item in shititype"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </div>
      </el-form-item>
      <el-form-item label="试题难度:">
        <div style="float: left;width:20px;height:50px"></div>
        <div style="float: left;width:80%">
          <el-rate style="margin-top: 10px" v-model="formInline.nanduid" show-text :texts="texts">
          </el-rate>
        </div>
      </el-form-item>
      <el-form-item label="所属题库:">
        <div style="float: left;width:20px;height:50px"></div>
        <div style="float: left;width:80%">
          <el-select v-model="formInline.tikuid" filterable placeholder="请选择">
            <el-option
              v-for="item in tikus"
              :key="item.value"
              :label="item.tikuname"
              :value="item.id">
            </el-option>
          </el-select>
        </div>
      </el-form-item>

      <el-form-item label="试题来源:">
        <div style="float: left;width:20px;height:50px"></div>
        <div style="float: left;width:80%">
          <el-input style="width: 50%" v-model="formInline.laiyuan" placeholder="试题来源"></el-input>
        </div>
      </el-form-item>
      <el-form-item label="试题状态:">
        <div style="float: left;width:20px;height:50px"></div>
        <div style="float: left;width:80%">
          <el-select v-model="formInline.shitizhuangtai" filterable placeholder="请选择试题状态">
            <el-option
              v-for="item in shitistatus"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </div>
      </el-form-item>
      <el-form-item label="试题fffffff:">
        <div style="float: left;width:20px;height:50px"></div>
        <div style="float: left;width:80%">
          <el-button @click="getUEData">获取编辑器的数据</el-button>
          <script id="bianjiqi3"  type="text/plain"></script>
        </div>
      </el-form-item>

      <el-form-item label="试题题干:">
        <div style="float: left;width:20px;height:50px"></div>
        <div style="float: left;width:80%">
          <script id="bianjiqi1"  type="text/plain"></script>
        </div>
      </el-form-item>

      <el-form-item label="选项设置:">
        <div style="float: left;width:20px;height:50px"></div>
        <div style="float: left;width:80%">
          <el-button @click="createone" style="margin-bottom: 20px">点击增加一个选项</el-button>

          <el-checkbox-group  v-model="checkList">
            <el-row v-for="(item,index) in kuanglist" :key="index" :style="{'margin-bottom':5+'px'}">
              <el-checkbox :label="kuanglist[index]" :style="{'margin-right':10+'px'}"></el-checkbox>
              <el-input style="width: 50%" type="textarea" v-model="inputValues[index]" placeholder="请输入内容"></el-input>
              <el-button type="danger" icon="el-icon-delete" circle @click="delindex(index)" :style="{'margin-left':10+'px'}"></el-button>
            </el-row>
          </el-checkbox-group>
        </div>
      </el-form-item>

      <el-form-item label="试题解析:">
        <div style="float: left;width:20px;height:50px"></div>
        <div style="float: left;width:80%">
          <script id="bianjiqi2" type="text/plain"></script>
        </div>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">立即创建</el-button>
        <el-button>取消</el-button>
      </el-form-item>

    </el-form>


  </div>
</template>

<script>

  export default {
    components:{

    },
    name: "editorshiti",
    data(){
      return{
        loading2:false,
        checkList:[],//选中的答案
        xuanxiang:this.domain.xuanxiang,
        kuanglist:["A","B","C","D"],//复选框初始化的选项
        inputValues:["","","",""],//选项的描述
        texts:this.domain.texts,
        shitistatus:this.domain.shitistatus,
        shititype:this.domain.shititype,
        tikus:[],
        formInline:{
          shitileixing:'',
          nanduid:null,
          tikuid:'',
          laiyuan:'',
          shitizhuangtai:'',
          tigan:'',
        },
        bianjiqi1:null,
        bianjiqi2:null,
        bianjiqi3:null,
        inputValues:["","","",""],
        fuxuankuanglist:["A","B","C","D"]
      }
    },
    methods:{
      getUEData(){
        alert(this.bianjiqi3.getContent()+"----------"+this.bianjiqi1.getContent())
      },
      onSubmit(){//提交数据
        alert("ddd")
        //打开加载层
        this.$data.loading2=true
        //组装数据
        this.$data.formInline.checkList=this.$data.checkList//选中项
        this.$data.formInline.kuanglist=this.$data.kuanglist//题目的选项（A,B。。。。）
        this.$data.formInline.inputValues=this.$data.inputValues//选项的描述
        //从富文本编辑器获取题干数据给form表单添加题干描述  bianjiqi1
        this.$data.formInline.tigan=this.bianjiqi1.getContent()
        //从富文本编辑器获取题目解析数据给form表单添加题干描述  bianjiqi2
        this.$data.formInline.timujiexi=this.bianjiqi2.getContent()
        //请求接口提交数据
        let qs=require("qs")

        this.$axios.post(this.domain.serverpath+'tiku/toAddShiTi',JSON.stringify(this.$data.formInline)).then((response)=>{

          //提交成功关闭加载窗口
          this.$data.loading2=false
        })
      },
      delindex(index){//多选题删除一个
        let faxuanzhong=this.$data.kuanglist.splice(index,1)
        this.$data.inputValues.splice(index,1)
        //取消选中一个start
        if(this.$data.checkList.length>0){
          let check=[];
          for(let i=0;i<this.$data.checkList.length;i++){
            if(this.$data.checkList[i]!=faxuanzhong){
              check.push(this.$data.checkList[i])
            }
          }
          this.$data.checkList=check
          //取消一个end
        }
      },
      createone(){//多选题增加一个
        if(this.$data.kuanglist.length<=25){
          let xx=this.$data.xuanxiang[0]//获取选项标识
          let index1=0;
          while(true){
            if(this.$data.kuanglist.indexOf(xx)==-1){//说明不存在
              xx=this.$data.xuanxiang[index1]
              break
            }else{
              index1++
              xx=this.$data.xuanxiang[index1]
            }
          }
          this.$data.kuanglist.push(xx)
          this.$data.inputValues.push("")
        }
      },
      getTiKu(){//从Redis中获取题库信息
        this.$axios.post(this.domain.serverpath+'tiku/getTikuListFromRedis').then((response)=>{
          this.$data.tikus=response.data.result
        })
      },
      getData(){

      },
      forInitUeditor(){
        //初始化富文本编辑器
        let conf1={initialFrameWidth:900,initialFrameHeight: 160,margin:200}
        window.UE.delEditor('bianjiqi1');//删除编辑器
        this.bianjiqi1 = window.UE.getEditor('bianjiqi1',conf1);//重新创建编辑器
        this.bianjiqi1.addListener('ready',  () => {
          this.bianjiqi1.setContent("")
        })

        let conf2={initialFrameWidth:900,initialFrameHeight: 160}
        window.UE.delEditor('bianjiqi2');//删除编辑器
        this.bianjiqi2 = window.UE.getEditor('bianjiqi2',conf2);//重新创建编辑器
        this.bianjiqi2.addListener('ready',  () => {
          this.bianjiqi2.setContent("")
        })


        let conf3={initialFrameWidth:900,initialFrameHeight: 160,margin:200}
        window.UE.delEditor('bianjiqi3');//删除编辑器
        this.bianjiqi3 = window.UE.getEditor('bianjiqi3',conf3);//重新创建编辑器
        this.bianjiqi3.addListener('ready',  () => {
          this.bianjiqi3.setContent("<p>6666              2019-02-17</p>")
        })

      }
    },
    mounted(){
      document.getElementById("bianjiqi1").style.padding=200;
      this.forInitUeditor();
      //加载题库数据
      this.getTiKu();
    },
    destory(){

    }
  }
</script>

<style scoped>
  .edui-default{
    padding-left: 200px;
  }
  .grid-content {
    border-radius:0px;
    min-height:32px;
    padding-top:10px;
    text-align: center;
  }

  .grid-content1 {
    border-radius:0px;
    min-height:30px;
    padding-top:10px;
    text-align: center;
    border:#dddddd 1px solid;
  }
  .bg-purple-light {
    background: #e5e9f2;
  }
  .bg-purple {
    background: #d3dce6;
  }
</style>
