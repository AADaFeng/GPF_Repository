<template>
  <div>
      <el-button @click="getUEData">获取编辑器的数据</el-button>
      <h1>=============================下边是富文本编辑器的测试========================</h1>
      <script id="editor1" type="text/plain"></script>
      <h1>===============================================================0000000========================</h1>
      <script id="editor2" type="text/plain"></script>
  </div>
</template>

<script>
    export default {
        name: "testUEditer",
        data(){
            return{
              editor2:null,
              editor1:null
            }
        },
        methods:{
          getUEData(){
             alert(this.editor1.getContent()+"----------"+this.editor2.getContent())
          }
        },
        mounted () {
            let config1={initialFrameWidth: 400,initialFrameHeight: 160}
            let config2={initialFrameWidth: 400,initialFrameHeight: 160}
            //初始化editor1
            this.editor1 = window.UE.getEditor('editor1',config1);
            this.editor1.addListener('ready',  () => {
              this.editor1.setContent()
            })
            //初始化editor2
            this.editor2 = window.UE.getEditor('editor2',config2);
            this.editor2.addListener('ready',  () => {
              this.editor2.setContent()
            })
        },
        destroyed () {
            this.editor1.destroy()
            this.editor2.destroy()
        }
    }
</script>

<style scoped>

</style>
