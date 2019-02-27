<template>
  <div>
      <h1>=========复选框的操作=====================================</h1>
      <el-button @click="createone">点击增加一个</el-button>
      <el-button @click="getchecked">点击获取选中</el-button>
      <el-checkbox-group  v-model="checkList">
        <el-row v-for="(item,index) in kuanglist" :key="index" :style="{'margin-bottom':5+'px'}">
          <el-checkbox :label="kuanglist[index]" :style="{'margin-right':10+'px'}"></el-checkbox>
          <el-input :style="{width:400+'px'}" type="text" v-model="inputValues[index]" placeholder="请输入内容" ></el-input>
          <el-button type="danger" icon="el-icon-delete" circle @click="delindex(index)" :style="{'margin-left':10+'px'}"></el-button>
        </el-row>
      </el-checkbox-group>


     <h1>=========单选框的操作===========================</h1>
  </div>
</template>

<script>
    export default {
        name: "testcheckBox",
        data(){
           return{
               checkList:[],//选中的答案
               xuanxiang:["A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"],
               kuanglist:["A"],//产生的选项
               inputValues:[""]
           }
        },
        methods:{
          getchecked(){
             alert("--复选框值-"+JSON.stringify(this.$data.checkList)+"---题干的值==》"+JSON.stringify(this.$data.inputValues))
          },
          delindex(index){//删除一个

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
          createone(){//增加一个
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

          }
        }
    }
</script>

<style scoped>

</style>
