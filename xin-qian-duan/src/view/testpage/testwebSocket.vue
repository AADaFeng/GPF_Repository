<template>
    <div>
       <audio src="../../../static/tishiyin/shizi.mp3" id="shizi"></audio>

    </div>
</template>

<script>
  export default {
    name: "testwebSocket",
    data(){
      return{
        webSocket:null
      }
    },
    methods:{
      webSocketInit(){
        let userid=this.domain.userinfo.userid
        console.log("=====userid=====》"+userid)
        this.$data.webSocket=new WebSocket("ws://localhost:8888/webSocketTest/sendMessage/"+userid);
        this.$data.webSocket.onopen=this.webSocketOpen;
        this.$data.webSocket.onmessage=this.messageWebSocket;
        this.$data.webSocket.onclose=this.closeWebSocket;
        this.$data.webSocket.onerror=this.errorWebSocket;
      },
      webSocketOpen(eventt){//发送发消息
        console.log("-========建立连接======")
        this.$data.webSocket.send("hello，我是前端!");
      },
      messageWebSocket(eventt){//接收消息
       // alert("你好，将来")
        document.getElementById("shizi").play();
        alert(eventt.data)
       // document.getElementById("yiliuyan").pause();

      },closeWebSocket(eventt){
        //关闭通道提示音
        alert("=====关闭=====")

      }
    },
    mounted(){
      //初始化
      this.webSocketInit()
    }
  }
</script>

<style scoped>

</style>
