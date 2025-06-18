package edu.entra21.fiberguardian.configuration;

import org.apache.catalina.connector.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SslRedirectConfig {

    private static final Logger logger = LoggerFactory.getLogger(SslRedirectConfig.class);

    @Bean
    WebServerFactoryCustomizer<TomcatServletWebServerFactory> servletContainerCustomizer() {
        return factory -> {
            try {
                factory.addAdditionalTomcatConnectors(redirectConnector());
                logger.info("HTTP connector added on port 8080, redirecting to 8443");
            } catch (Exception e) {
                logger.error("Failed to add HTTP connector", e);
                throw new RuntimeException("Failed to configure HTTP connector", e);
            }
        };
    }

    private Connector redirectConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setPort(8080);
        connector.setSecure(false);
        connector.setRedirectPort(8443);
        return connector;
    }
}
