<template>
  <div
    v-loading="loading2"
    element-loading-text="稍等提交中..."
    element-loading-spinner="el-icon-loading"
    element-loading-background="rgba(0, 0, 0, 0.8)"
  ><!-- 试题编辑界面 -->
    <el-form :model="formInline" :rules="rules" ref="formInline" class="demo-form-inline">

      <el-form-item label="试题类型:" prop="shitileixing">
        <div style="float: left;width:20px;height:50px"></div>
        <div style="float: left;width:80%">
          <el-select @change="shitileixingchangeeditor" v-model="formInline.shitileixing" placeholder="请选择">
            <el-option
              v-for="item in shititype"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </div>
      </el-form-item>
      <el-form-item label="试题难度:" prop="nanduid">
        <div style="float: left;width:20px;height:50px"></div>
        <div style="float: left;width:80%">

          <el-rate style="margin-top: 10px" v-model="formInline.nanduid" show-text :texts="texts">
          </el-rate>

        </div>
      </el-form-item>
      <el-form-item label="所属题库:" prop="tikuid">
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

      <el-form-item label="试题来源:" prop="laiyuan">
        <div style="float: left;width:20px;height:50px"></div>
        <div style="float: left;width:80%">
          <el-input style="width: 50%" v-model="formInline.laiyuan" placeholder="试题来源"></el-input>
        </div>
      </el-form-item>
      <el-form-item label="试题状态:" prop="shitizhuangtai">
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

      <el-form-item label="试题题干:" prop="tigan" style="margin-left:10px">
        <div style="float: left;width:20px;height:50px"></div>
        <div style="float: left;width:80%">
          <script id="bianjiqi10"  type="text/plain"></script>
        </div>
      </el-form-item>

      <el-form-item label="选项设置:" v-if="show1" prop="xuanxiangshezhi"><!-- 单选框 -->
        <div style="float: left;width:20px;height:50px"></div>
        <div style="float: left;width:80%">
          <el-button @click="danxuancreate" type="primary" plain style="margin-bottom: 20px">点击增加一个选项</el-button>
          <br>
          <el-radio-group v-model="danxuancheck">
            <el-row v-for="(item,index) in danxuanlist" :key="index" :style="{'margin-bottom':5+'px'}">
              <el-radio :label="item" :style="{'margin-right':10+'px'}">{{item}}</el-radio>
              <el-input style="width:500px" type="textarea" v-model="danxianmiaoshu[index]" placeholder="请描述选项" ></el-input>
              <el-tooltip class="item" effect="dark" content="单击删除这一行" placement="right">
                <el-button type="danger" icon="el-icon-delete" circle @click="deldanxuanindex(index)" :style="{'margin-left':10+'px'}"></el-button>
              </el-tooltip>
            </el-row>
          </el-radio-group>
        </div>
      </el-form-item>

      <el-form-item label="选项设置:" v-if="show2"><!-- 复选框 -->
        <div style="float: left;width:20px;height:50px"></div>
        <div style="float: left;width:80%">
          <el-button @click="createone" type="primary" plain style="margin-bottom: 20px">点击增加一个选项</el-button>

          <el-checkbox-group  v-model="checkList">
            <el-row v-for="(item,index) in kuanglist" :key="index" :style="{'margin-bottom':5+'px'}">
              <el-checkbox :label="kuanglist[index]" :style="{'margin-right':10+'px'}"></el-checkbox>
              <el-input style="width: 50%" type="textarea"  v-model="inputValues[index]" placeholder="请输入内容"></el-input>
              <el-tooltip class="item" effect="dark" content="单击删除这一行" placement="right">
                <el-button type="danger" icon="el-icon-delete" circle @click="delindex(index)" :style="{'margin-left':10+'px'}"></el-button>
              </el-tooltip>
            </el-row>
          </el-checkbox-group>
        </div>
      </el-form-item>

      <el-form-item label="选项设置:" v-if="show3"><!-- 判断题 -->
        <div style="float: left;width:20px;height:50px"></div>
        <div style="float: left;width:80%">
          <el-row style="margin-bottom: 10px">
            <el-radio v-model="panduancheck" label="1" border>对</el-radio>
          </el-row>
          <el-row>
            <el-radio v-model="panduancheck" label="0" border>错</el-radio>
          </el-row>
        </div>
      </el-form-item>


      <el-form-item label="答案设置:" v-if="show5" prop="wenda"><!-- 判断题 -->
        <div style="float: left;width:20px;height:50px"></div>
        <div style="float: left;width:80%">
          <el-input style="width: 80%;" type="textarea" v-model="formInline.wendadaan" placeholder="请输入问答答案内容"></el-input>
        </div>
      </el-form-item>


      <el-form-item label="试题解析:" prop="shitijiexi" style="margin-left:10px">
        <div style="float: left;width:20px;height:50px"></div>
        <div style="float: left;width:80%">
          <script id="bianjiqi20" type="text/plain"></script>
        </div>
      </el-form-item>
      <br>
      <el-form-item>
        <div style="float: left;width:20px;height:50px"></div>
        <div style="float: left;width:80%;padding-left:6%">
          <el-button type="primary" @click="onSubmit">保存</el-button>
          <el-button>取消</el-button>
        </div>
      </el-form-item>

    </el-form>


  </div>
</template>

<script>

  export default {
    components:{

    },
    props:{
       tixingid:"",
       timuidd:{},

    },
    name: "shitixiugai",
    data(){
      return{
        show1:true,
        show2:false,
        show3:false,
        show4:false,
        show5:false,
        loading2:false,
        wenda:"",
        danxuancheck:"A",//单选选中的答案
        panduancheck:"1",//判断题选中的答案  对
        panduanxiang:["对","错"],
        wenda:"",//问答题的答案
        danxuanlist:["A","B","C","D"],//单选初始化选项
        danxianmiaoshu:["","","",""],//单选的初始化选项描述
        checkList:["A"],//复选选中的答案
        xuanxiang:this.domain.xuanxiang,
        kuanglist:["A","B","C","D"],//复选框初始化的选项
        inputValues:["","","",""],//复选选项的描述
        texts:this.domain.texts,
        shitistatus:this.domain.shitistatus,
        shititype:this.domain.shititype,
        tikus:[],
        formInline:{
          wendadaan:"",//当试题类型是问答题时候起作用
          shitileixing:"",
          tixingid:"5",
          nanduid:1,
          tikuid:'',
          laiyuan:'',
          shitizhuangtai:'',
          tigan:'',
        },
        bianjiqi10:null,
        bianjiqi20:null,
        inputValues:["","","",""],
        rules:{
          shitileixing:[{ required: true, message: '请选择试题类型', trigger: 'change' }],
          nanduid:[{ required: true, message: '请选择试题难度', trigger: 'click' }],
          tikuid:[{ required: true, message: '请选择试题难度', trigger: 'change' }],
          shitizhuangtai:[{ required: true, message: '请选择试题状态', trigger: 'change' }],
          laiyuan:[{ required: true, message: '请填写试题来源', trigger: 'blur' }],
          tigan:[{ required: false, message: '请填写题干信息',trigger: 'change' }],
          shitijiexi:[{ required: false, message: '请填写试题解析',trigger: 'click' }]
        }
      }
    },
    methods:{
      zizujian01(){ //子类调用父类的组件
        this.$emit("ttt")
      },
      shitileixingchangeeditor(){//试题类型改变的时候触发
        let stlx=this.$data.formInline.shitileixing
        if(this.domain.userinfo.tixingid2!=null&&this.domain.userinfo.tixingid2!=""){
          stlx=this.domain.userinfo.tixingid2;
        }
        if(stlx=="1"){//单选
          this.$data.show1=true
          this.$data.show2=false
          this.$data.show3=false
          this.$data.show4=false
          this.$data.show5=false
        }else if(stlx=="2"){//多选
          this.$data.show1=false
          this.$data.show2=true
          this.$data.show3=false
          this.$data.show4=false
          this.$data.show5=false
        }else if(stlx=="3"){//判断
          this.$data.show1=false
          this.$data.show2=false
          this.$data.show3=true
          this.$data.show4=false
          this.$data.show5=false
        }else if(stlx=="4"){//填空题
          this.$data.show1=false
          this.$data.show2=false
          this.$data.show3=false
          this.$data.show4=true
          this.$data.show5=false
        }else if(stlx=="5"){//问答题
          this.$data.show1=false
          this.$data.show2=false
          this.$data.show3=false
          this.$data.show4=false
          this.$data.show5=true
        }
        if(this.domain.userinfo.tixingid2!=null&&this.domain.userinfo.tixingid2!=""){
          this.domain.userinfo.tixingid2="";
        }
      },
      onSubmit(){//提交数据
        this.$refs['formInline'].validate((valid) => {//表单必填项的验证
          if (valid) {
            //打开加载层
            this.$data.loading2=true
            //组装数据
            if(this.$data.formInline.shitileixing=="2"){//复选框
              this.$data.formInline.checkList=JSON.stringify(this.$data.checkList)//选中项
              this.$data.formInline.kuanglist=JSON.stringify(this.$data.kuanglist)//题目的选项（A,B。。。。）
              this.$data.formInline.inputValues=JSON.stringify(this.$data.inputValues)//选项的描述
            }
            if(this.$data.formInline.shitileixing=="1"){//单选框
              this.$data.formInline.danxuancheck=this.$data.danxuancheck//选中项
              this.$data.formInline.danxuanlist=JSON.stringify(this.$data.danxuanlist)//题目的选项（A,B。。。。）
              this.$data.formInline.danxuanmiaoshu=JSON.stringify(this.$data.danxianmiaoshu)//选项的描述
              alert(JSON.stringify(this.$data.formInline.danxuanmiaoshu))
            }
            if(this.$data.formInline.shitileixing=="3"){//判断题
              this.$data.formInline.panduancheck=this.$data.panduancheck//选中项
              this.$data.formInline.panduanxiang=this.$data.panduanxiang//判断的描述
            }
            //从富文本编辑器获取题干数据给form表单添加题干描述  bianjiqi10
            this.$data.formInline.tigan=this.bianjiqi10.getContent()
            //从富文本编辑器获取题目解析数据给form表单添加题干描述  bianjiqi20
            this.$data.formInline.timujiexi=this.bianjiqi20.getContent()
            //设置用户ID
            this.$data.formInline.userid=this.domain.userinfo.userid
            //请求接口提交数据
            let qs=require("qs")
            this.$axios.post(this.domain.serverpath+'tiku/toAddShiTi',qs.stringify(this.$data.formInline)).then((response)=>{
              //提交成功关闭加载窗口
              this.$data.loading2=false
            })
          } else {
            console.log('error submit!!');
            //提示信息
            this.$notify({
              title: '警告',
              message: '有必填项没有填写，请检查带‘*’的项^_^',
              type: 'warning'
            });
            return false;
          }
        });
      },
      deldanxuanindex(index){//单选的删除
        let  xuanzhong=this.$data.danxuanlist.splice(index,1)
        this.$data.danxianmiaoshu.splice(index,1)
        //取消选中一个start
        if(this.$data.danxuancheck==xuanzhong){

          this.$data.danxuancheck=null
          //取消一个end
        }
      },danxuancreate(){
        if(this.$data.danxuanlist.length<=25){
          let xx=this.$data.xuanxiang[0]//获取选项标识
          let index1=0;
          while(true){
            if(this.$data.danxuanlist.indexOf(xx)==-1){//说明不存在
              xx=this.$data.xuanxiang[index1]
              break
            }else{
              index1++
              xx=this.$data.xuanxiang[index1]
            }
          }
          this.$data.danxuanlist.push(xx)
          this.$data.danxianmiaoshu.push("")
        }
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
        window.UE.delEditor('bianjiqi10');//删除编辑器
        this.bianjiqi10 = window.UE.getEditor('bianjiqi10',conf1);//重新创建编辑器
        this.bianjiqi10.addListener('ready',  () => {
          this.bianjiqi10.setContent("编辑题干信息")
        })

        let conf2={initialFrameWidth:900,initialFrameHeight: 160}
        window.UE.delEditor('bianjiqi20');//删除编辑器
        this.bianjiqi20 = window.UE.getEditor('bianjiqi20',conf2);//重新创建编辑器
        this.bianjiqi20.addListener('ready',  () => {
          this.bianjiqi20.setContent("编辑试题解析")
        })

      },
      /*getTiMuById(){
        //查询题目数据-进行赋值回显
        let params={id:this.domain.userinfo.shitiid}
        let qs=require("qs")
        this.$axios.post(this.domain.serverpath+"tiku/getShitiById",qs.stringify(params)).then((response)=>{
          let shiti=response.data.result;
          if(shiti.tixingid!=null&&shiti.tixingid!=""){
            shiti.tixingid=JSON.stringify(shiti.tixingid).trim()
            this.domain.userinfo.shitileixing=shiti.tixingid;
          }
          this.$data.formInline=shiti;
          this.$data.formInline.shitileixing=shiti.tixingid

          if(shiti.tixingid=="1"){//单选
            //选中试题类型
            this.$data.danxuanlist=JSON.parse(shiti.xuanxiangbianhao)
            this.$data.danxianmiaoshu=JSON.parse(shiti.xuanxiangmiaoshu)
          }else if(shiti.tixingid=="2"){//-复选
            this.$data.kuanglist=JSON.parse(shiti.xuanxiangbianhao)
            this.$data.checkList=JSON.parse(shiti.daan)
            this.$data.inputValues=JSON.parse(shiti.xuanxiangmiaoshu)
          }else if(shiti.tixingid=="3"){//判断
            this.$data.panduancheck=shiti.daan.toString()
            this.$data.panduanxiang=JSON.parse(shiti.xuanxiangmiaoshu)
          }
          //题干的回显
          this.bianjiqi10.setContent(shiti.tigan.toString())
          //解析的回显
          this.bianjiqi20.setContent(shiti.timujiexi.toString())
        })
      }*/
    },
    mounted(){
      alert(JSON.stringify(this.$data.formInline))
      document.getElementById("bianjiqi10").style.padding=200;
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
