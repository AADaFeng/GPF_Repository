package com.zbf.zhongjian.webSocket;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
//该注解 调用当前类中标注 Bean 所有的方法，获取实例加到容器中
public class WebSocketConfigTest {
   //推送的导出类

    @Bean
   public ServerEndpointExporter getServerEndpointExporter(){
       return  new ServerEndpointExporter();
   }

}
