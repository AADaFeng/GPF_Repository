<template>
  <div><!--:inline="true"-->
    <el-form ref="form"  :model="form" label-width="100px">
      <el-form-item label="试卷名称:">
        <el-col :span="23">
          <el-input v-model="form.shijuanname" style="width: 50%"></el-input>
        </el-col>
      </el-form-item>

      <br>
      <el-form-item label="试卷分类:">
        <el-select v-model="form.fenleiid" placeholder="请选择">
          <el-option v-for="(item,index) in testparerclassfly" :key="index" :label="item.shijuanfenlei" :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="试卷状态:">
        <el-select v-model="form.zhuangtai">
          <el-option label="开放" value="0"></el-option>
          <el-option label="关闭" value="1"></el-option>
        </el-select>
      </el-form-item>
      <br>
      <el-form-item label="开始时间:">
        <el-date-picker type="datetime" placeholder="请选择" v-model="form.starttime" @change="timeChange" style="width: 50%;"></el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间:">
        <el-date-picker type="datetime" placeholder="请选择" v-model="form.endtime" @change="timeChange" style="width: 50%;"></el-date-picker>
      </el-form-item>
      <br>
      <el-form-item label="成绩公布时间:">
        <el-date-picker type="datetime" placeholder="请选择" v-model="form.opentime" style="width: 50%;"></el-date-picker>
      </el-form-item>
      <el-form-item label="考试时长:">
        <el-input disabled="disabled"  v-model="form.shijian" style="width: 50%;">
          <i slot="suffix">分钟</i>
        </el-input>
      </el-form-item>
      <br>
      <el-form-item label="公布答案:">
        <el-select v-model="form.havedaan" placeholder="请选择">
          <el-option label="是" value="0"></el-option>
          <el-option label="否" value="1"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="显示形式:">
        <el-select placeholder="请选择">
          <el-option label="整卷展示" value="shanghai"></el-option>
          <el-option label="单题展示" value="beijing"></el-option>
        </el-select>
      </el-form-item>
      <br>
      <el-form-item label="试卷类型:">
        <el-select v-model="form.shijuanleixing" placeholder="请选择">
          <el-option label="普通试卷" value="0"></el-option>
          <el-option label="随机试卷" value="1"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="试题排序顺序:">
        <el-select v-model="form.shitishunxu" placeholder="请选择">
          <el-option label="原始顺序" value="0"></el-option>
          <el-option label="随机顺序" value="1"></el-option>
        </el-select>
      </el-form-item>
      <br>
      <el-form-item label="试卷说明:">
        <el-input type="textarea"
                  :rows="5"
                  style="width:750px"
                  placeholder="请输入内容"
                  v-model="form.shuoming"></el-input>
      </el-form-item>
      <br>
      <el-form-item label="目标部门">
        <el-select v-model="form.bumen" placeholder="请选择">
          <el-option
            v-for="item in bumen"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
        <el-button>清空</el-button>
      </el-form-item>
      <br>
      <el-form-item label="目标用户">
        <el-alert
          title="用户1"
          type="success"
          style="width: 8%;"
          show-icon="showicon">

          <span v-model="userList"></span>

        </el-alert>
        <el-button @click="dialogVisible = true">选择用户</el-button>
        <el-button>清空</el-button>
      </el-form-item>
      <br>
      <el-form-item>
        <el-button type="primary" @click="onSubmit('form')">提交</el-button>
        <el-button>取消</el-button>
      </el-form-item>
    </el-form>

    <el-dialog
      title="选择用户"
      :visible.sync="dialogVisible"
      width="40%"
      :before-close="handleClose">

      <userInfo > </userInfo>

      <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
       </span>
    </el-dialog>

  </div>
</template>

<script>
  import  userInfo from './userInfo.vue'

  export default {
    components:{
      userInfo
    },
    props:{
      testparerclassfly:{
        type:Array
      }
    },
    data() {
      return {
        userList:"ggggg",
        showicon:true,
        dialogVisible: false,
        options:[],
        loading: false,
        bumen:this.domain.bumen,
        form: {
          shijuanname: '',
          fenleiid: '',
          zhuangtai: '',
          starttime: null,
          endtime: null,
          opentime: null,
          shijian: '',
          havedaan: '',
          shijuanleixing: '',
          shitishunxu: '',
          shuoming: '',
          bumen:''
        }
      }
    },
    methods: {
      handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
      },
      onSubmit(){
        alert(JSON.stringify(this.$data.form))
        let shijuandata=this.$data.form
        //打开加载层
        this.$data.loading2=true
        this.$axios.post(this.domain.serverpath+"shijuan/toaddShiJuanInfo",JSON.stringify(shijuandata)).then((response)=>{
          if(response.data.success=="ok"){
            this.$message({
              duration:2000,
              message: '恭喜你，这是一条成功消息',
              type: 'success'
            });
            //清空表单数据
            this.$data.form={}
            //重新加载数据  【】【】【】【】【】
          }else{
            this.$message({
              message:'错了哦，^^',
              type:'error',
              duration:2000
            });
          }
        })
      },

      timeChange(){
        if (this.form.starttime != null && this.form.endtime != null ) {
          //根据时间字符串(格式是  xxxx/xx/xx xx:xx:xx,否则需要转换),创建date对象.
          let datebegin=new Date(this.form.starttime)
          let dateend=new Date(this.form.endtime)
          //得到时间差(毫秒数)
          let datediff=dateend-datebegin
          //对输入框进行赋值
          this.form.shijian=datediff/1000/60

        }
      }
    },
    remoteMethod(query) {
      if (query !== '') {
        this.loading = true;
        this.$axios.post(this.domain.serverpath + "tiku/getTiKus").then((response) => {
          this.loading=false;
          this.options=response.data.result.filter(item=>{
            return item.tikuname.indexOf(query) > -1;
          })

        }).catch((error) => {

        })
      } else {
        this.options4 = [];
      }
    }
  }
</script>

<style scoped>

</style>
