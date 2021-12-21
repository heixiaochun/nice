package com.example.nice.common.config;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * tomcat端口监听配置，支持多配置一个http端口
 * 当设置了server.anotherHttpPort参数时，即可根据配置的端口号，多启用一个HTTP端口
 *
 * @author heixiaochun
 */
@Configuration
@ConditionalOnProperty(name = "server.anotherHttpPort")
public class TomcatConfig {

    @Value(("${server.anotherHttpPort}"))
    private int anotherHttpPort;

    @Bean
    public WebServerFactoryCustomizer<WebServerFactory> containerCustomizer() {
        return container -> {
            if (container instanceof TomcatServletWebServerFactory) {
                TomcatServletWebServerFactory containerFactory = (TomcatServletWebServerFactory) container;
                Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
                connector.setPort(anotherHttpPort);
                containerFactory.addAdditionalTomcatConnectors(connector);
            }
        };
    }

}