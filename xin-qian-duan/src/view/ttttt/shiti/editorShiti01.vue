<template>
    <div>

      <!-- 【复选框的操作】----- start -->
      <el-button @click="createone">点击增加一个</el-button>
      <el-button @click="getchecked">点击获取选中</el-button>
      <el-checkbox-group  v-model="checkList">
        <el-row v-for="(item,index) in kuanglist" :key="index" :style="{'margin-bottom':5+'px'}">
          <el-checkbox :label="kuanglist[index]" :style="{'margin-right':10+'px'}"></el-checkbox>
          <el-input :style="{width:400+'px'}" type="text" v-model="inputValues[index]" placeholder="请输入内容" ></el-input>
          <el-button type="danger" icon="el-icon-delete" circle @click="delindex(index)" :style="{'margin-left':10+'px'}"></el-button>
        </el-row>
      </el-checkbox-group>
      <!-- 【复选框的操作】----- end -->

      <!--//【文本编辑器】----- start  -->
      <el-button @click="getData">获取编辑器的数据</el-button>
      <script id="editor11" type="text/plain"></script>
      <h1>=====================</h1>
      <script id="editor22" type="text/plain"></script>
      <!--//【文本编辑器】----- end  -->
    </div>
</template>

<script>
    export default {
        name: "editorShiti01",
      data(){
          return{
            editor11:null, //文本编辑器初始值
            editor22:null,
            checkList:[], //【复选框的操作】  选中的答案
            xuanxiang:["A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"],
            kuanglist:["A"],//产生的选项
            inputValues:[""]


          }
      },
      methods:{
        delindex(index){// 【复选框的操作】 ————  删除一个

          let faxuanzhong=this.$data.kuanglist.splice(index,1)
          //根据下标删除一个
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
        createone(){//【复选框的操作】 ———— 增加一个
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
        getchecked(){ //【复选框的操作】 ————获取编辑器的数据
          alert("--复选框值-"+JSON.stringify(this.$data.checkList)+"---题干的值==》"+JSON.stringify(this.$data.inputValues))
        },
        getData(){//【文本编辑器】 ————获取编辑器的数据
            alert(this.editor11.getContent()+"----------------"+this.editor22.getContent())
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

        }

      },
      mounted(){
        //【文本编辑器】 ———— 初始化编辑器的配置start】
        let config1={initialFrameWidth: 400,initialFrameHeight: 160}
        let config2={initialFrameWidth: 400,initialFrameHeight: 160}
        //【文本编辑器】 ———— 初始化编辑器的配置end

        //【文本编辑器】------ 初始化editor1
        this.editor11 = window.UE.getEditor('editor11',config1);
        this.editor11.addListener('ready',  () => {
          this.editor11.setContent()
        })
        //【文本编辑器】---- 初始化editor2
        this.editor22 = window.UE.getEditor('editor22',config1);
        this.editor22.addListener('ready',  () => {
          //【文本编辑器】---- 给容器设置初始化内容
          this.editor22.setContent("<p>吾问无为谓无无无<img src=\"http://img.baidu.com/hi/jx2/j_0024.gif\"/></p>")
        })
      },
      destory(){
       //【文本编辑器】 --- 容器摧毁
        //摧毁时间：  当前页面执行完 到 下个页面执行时之间
        this.editor11.destroy()
        this.editor22.destroy()
      }
    }
</script>

<style scoped>

</style>
