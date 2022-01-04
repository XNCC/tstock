package com.vbqncc.tstock.tstock;

import org.apache.catalina.connector.Connector;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.vbqncc.tstock.tstock.mapper")
@EnableScheduling
public class TstockApplication {

    public static void main(String[] args) {
        SpringApplication.run(TstockApplication.class, args);
    }

    // 获取配置端口
//    @Value("${http.port}")
//    private Integer httpPort;

//    @Bean
//    public ServletWebServerFactory servletContainer() {
//        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
//        // 添加http
//        tomcat.addAdditionalTomcatConnectors(createStandardConnector());
//        return tomcat;
//    }

    /**
     * 配置http
     *
     * @return connector
     */
//    private Connector createStandardConnector() {
//        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//        connector.setPort(httpPort);
//        return connector;
//    }
}
