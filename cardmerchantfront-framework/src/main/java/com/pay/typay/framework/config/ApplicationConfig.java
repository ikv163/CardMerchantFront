package com.pay.typay.framework.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 程序注解配置
 *
 * @author js-oswald
 */
@Configuration
// 表示通过aop框架暴露该代理对象,AopContext能够访问
@EnableAspectJAutoProxy(exposeProxy = true)
// 指定要扫描的Mapper类的包的路径
@MapperScan("com.pay.typay.**.mapper")
public class ApplicationConfig {
//    @Bean
//    public TomcatServletWebServerFactory tomcatServletWebServerFactory(Connector connector) {
////        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
////        tomcat.addAdditionalTomcatConnectors(createStandardConnector());
////        return tomcat;
////        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
////            @Override
////            protected void postProcessContext(Context context) {
////                SecurityConstraint securityConstraint = new SecurityConstraint();
////                securityConstraint.setUserConstraint("CONFIDENTIAL");
////                SecurityCollection collection = new SecurityCollection();
////                collection.addPattern("/*");
////                securityConstraint.addCollection(collection);
////                context.addConstraint(securityConstraint);
////            }
////        };
////        tomcat.addAdditionalTomcatConnectors(connector);
////        return tomcat;
//    }
//
//    @Bean
//    public Connector createStandardConnector() {
//        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
//        connector.setSecure(false);
//        connector.setScheme("http");
//        connector.setPort(port);
//        connector.setRedirectPort(httpsPort);
//        return connector;
//    }
//
//    @Value("${http.port}")
//    private Integer port;
//
//    @Value("${server.port}")
//    private Integer httpsPort;
}
