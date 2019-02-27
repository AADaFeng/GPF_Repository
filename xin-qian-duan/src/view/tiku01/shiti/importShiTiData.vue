<template>
    <div
      v-loading="loading2"
      element-loading-text="拼命处理中....."
      element-loading-spinner="el-icon-loading"
      element-loading-background="rgba(0, 0, 0, 0.8)"
      >
      <div style="float: left;width:50%;">
          <el-form :model="formInline" :rules="rules" ref="formInline" class="demo-form-inline">
              <el-form-item label="试题类型:" prop="shitileixing">
                <div style="float: left;width:20px;height:50px"></div>
                <div style="float: left;width:80%">
                  <el-select v-model="formInline.shitileixing" placeholder="请选择">
                    <el-option
                      v-for="item in shitileixingtype"
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
                    :key="item.tikuname"
                    :label="item.tikuname"
                    :value="item.id">
                  </el-option>
                </el-select>
              </div>
            </el-form-item>

            <el-form-item label="试题来源:" prop="laiyuan">
              <div style="float: left;width:20px;height:50px"></div>
              <div style="float: left;width:80%">
                <el-input style="width:43%" v-model="formInline.laiyuan" placeholder="试题来源"></el-input>
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

          </el-form>

          <br>
      </div>
      <div style="float: left;width:50%;height:280px">


        <el-upload
          ref="elupload1"
          class="upload-demo"
          action="http://localhost:8888/test/toImportExcelData22"
          :before-upload="beoreupload1"
          :auto-upload="autoupload1"
          :data="mydata1"
          :name="namefile1"
          :show-file-list="showlist"
          multiple
          :limit="1"
          :on-exceed="xianzhiwenjiangeshu"
          :file-list="fileList1">
          <el-button size="small" type="primary">点击选择文件</el-button>
          <el-button type="primary" icon="el-icon-download" @click="xiazaimyban">下载Excel模板</el-button>
          <div slot="tip" class="el-upload__tip">只能上传excel文件，且不超过10M</div>
        </el-upload>

      </div>
      <div style="width:100%">
          <el-button type="primary" @click="dianjiupload1">提交数据</el-button>
          <el-button type="primary" plain @click="chongzhi">重-置</el-button>
      </div>
    </div>
</template>

<script>
    export default {
        name: "importShiTiData",
        data(){
           return{
             showlist:true,
             loading2:false,
             mydata1:{},
             fileList1:[],//上传文件
             namefile1:"file",
             texts:this.domain.texts,
             autoupload1:false,
             shitistatus:this.domain.shitistatus,
             shitileixingtype:this.domain.shititype,
             tikus:[],
             formInline:{
               shitileixing:"",
               nanduid:"1",
               tikuid:"",
               laiyuan:"",
               shitizhuangtai:""
             },
             rules:{
               shitileixing:[{ required: true, message: '请选择试题类型', trigger: 'change' }],
               nanduid:[{ required: true, message: '请选择试题难度', trigger: 'click' }],
               tikuid:[{ required: true, message: '请选择试题难度', trigger: 'change' }],
               shitizhuangtai:[{ required: true, message: '请选择试题状态', trigger: 'change' }],
               laiyuan:[{ required: true, message: '请填写试题来源', trigger: 'blur' }]
             }
           }
        },
        methods:{
           getTiKuList(){
             this.$axios.post(this.domain.serverpath+'tiku/getTikuListFromRedis').then((response)=>{
               this.$data.tikus=response.data.result
             })
           },
          xianzhiwenjiangeshu(files, fileList){//限制上传的文件的个数

          },
          beoreupload1(file){//文件上传之前运行

          },
          xiazaimyban(){
               window.location.href=this.domain.serverpath+"tiku/getExceltemplate";
          },
          dianjiupload1(){
            //附带参数            //校验表单
            this.$refs['formInline'].validate((valid) => {//表单必填项的验证
              if(valid){
                this.$data.loading2=true;
                this.$data.mydata1=this.$data.formInline;
                this.$refs.elupload1.submit();
                this.$data.loading2=false;
                this.$notify({
                  title: '成功',
                  message: '处理成功^_^',
                  type: 'success'
                });
              }
            });
          },
          onsuccessfile(response, file, fileList){
            this.$data.loading2=false;
            this.$notify({
              title: '成功',
              message: '处理成功^_^',
              type: 'success'
            });
          },
          onerrorfile(err, file, fileList){
            this.$data.loading2=false;
            this.$notify.error({
              title: '错误',
              message: '处理失败请检查模板数据是否有问题 >@_@<'
            });
          },
          chongzhi(){//重置
             this.$data.formInline={nanduid:"1"};
          }
        },
        mounted(){

        }
    }
</script>

<style scoped>

</style>
