<template>
    <div
      v-loading="loading2"
      element-loading-text="拼命加载中"
      element-loading-spinner="el-icon-loading"
      element-loading-background="rgba(0, 0, 0, 0.8)"
      :style="{height:wheight+'px'}">

      <el-form
        :rules="rules" ref="form" :model="form" label-width="80px" :style="{width:'50%'}">

        <el-form-item label="题库名称" prop="tikuname">
          <el-input v-model="form.tikuname"></el-input>
        </el-form-item>
        <el-form-item label="开放状态" prop="tikuzhuangtai">
          <el-select v-model="form.tikuzhuangtai" placeholder="请选择开放状态">
            <el-option label="开放" value="1"></el-option>
            <el-option label="关闭" value="0"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="题库说明" prop="tikushuoming">
          <el-input type="textarea" v-model="form.tikushuoming"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit('form')">保存</el-button>
          <el-button>取消</el-button>
        </el-form-item>
      </el-form>
    </div>
</template>

<script>

    export default {
        name: "xinzengtiku01",
       data(){
         return{
           rules:{//定义验证的规则
             tikuname: [
               { required: true, message: '请输入题库名称', trigger: 'blur' }
             ],
             tikuzhuangtai: [
               { required: true, message: '请选择题库状态', trigger: 'change' }
             ],
             tikushuoming: [
               { required: true, message: '请输入题库说明', trigger: 'blur' }
             ]
           },
           loading2:false,
           form:{},

         }
       },
      methods:{
        onSubmit(reee){
          this.$refs[reee].validate((vali)=>{
            if(vali){
              alert("ok")
            }
          })
          let tikudata=this.$data.form
          //打开加载层
          this.$data.loading2=true
          this.$axios.post(this.domain.serverpath+"tiku/toaddTiKuInfo",JSON.stringify(tikudata)).then((response)=>{
            if(response.data.success=="ok"){
              this.$message({
                duration:2000,
                message: '恭喜你，这是一条成功消息',
                type: 'success'
              });
              //关闭加载层
              this.$data.loading2=false
              //清空表单数据
              this.$data.form={}
              //重新加载数据  【】【】【】【】【】

            }else{
              this.$message({
                message:'错了哦，^^',
                type:'error',
                duration:2000
              });
              //关闭加载层
              this.$data.loading2=false
            }
          })
        }


      },
    }
</script>

<style scoped>

</style>
