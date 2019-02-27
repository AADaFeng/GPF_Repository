<template>
    <div>

       <el-button @click="danxuancreate">单击添加</el-button>
       <el-button @click="huoqu">单击获取</el-button>
       <el-button @click="daochuexcel">单击导出excel</el-button>

       <br>

      <el-radio-group v-model="danxuancheck">
         <el-row v-for="(item,index) in danxuanlist" :key="index">
           <el-radio :label="item" >{{item}}</el-radio>
           <el-input :style="{width:400+'px','margin-left':10+'px'}" type="text" v-model="danxianmiaoshu[index]" placeholder="请描述选项" ></el-input>
           <el-button type="danger" icon="el-icon-delete" circle @click="deldanxuanindex(index)" :style="{'margin-left':10+'px'}"></el-button>
         </el-row>
      </el-radio-group>

      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">


        <el-form-item label="活动名称" prop="name">
          <el-input v-model="ruleForm.name"></el-input>
        </el-form-item>
        <el-form-item label="活动区域" prop="region">
          <el-select v-model="ruleForm.region" placeholder="请选择活动区域">
            <el-option label="区域一" value="shanghai"></el-option>
            <el-option label="区域二" value="beijing"></el-option>
          </el-select>
        </el-form-item>


        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>






    </div>
</template>

<script>
    export default {
        name: "testDanXuan",
        data(){
           return{

             rules:{//定义验证的规则
               name: [
                 { required: true, message: '请输入活动名称', trigger: 'blur' }
               ],
               region: [
                 { required: true, message: '请选择活动区域', trigger: 'change' }
               ]
             },
             ruleForm:{
               name:null,
               region:null
             },
               danxuancheck:null,//单选选中的答案
               xuanxiang:["A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"],
               danxuanlist:["A","B","C","D"],
               danxianmiaoshu:["","","",""]
           }
        },
        methods:{
          submitForm(reee){//表单验证案例
              this.$refs[reee].validate((vali)=>{
                 if(vali){
                      alert("ok")
                 }
              })
          },
          huoqu(){
             alert(this.$data.danxuancheck+"----描述-----"+JSON.stringify(this.$data.danxianmiaoshu))
          },
          deldanxuanindex(index){//单选的删除
            let  xuanzhong=this.$data.danxuanlist.splice(index,1)
            this.$data.danxianmiaoshu.splice(index,1)
            //取消选中一个start
            if(this.$data.danxuancheck==xuanzhong){

              this.$data.danxuancheck=null
              //取消一个end
            }
          },
          daochuexcel(){
            window.location.href = this.domain.serverpath+'test/daochuexcel';

          },
          danxuancreate(){
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
          }
        }
    }
</script>

<style scoped>

</style>
