package org.flowable.rest.app.resolver;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ExpressionResolverConfig {

    @Bean
    public ExpressionResolver priFlowExpression(ApplicationContext context){
        Environment env = context.getBean(Environment.class);
        String webHookHost = env.getProperty("primotus.webhook.server.host");
        String webHookPort = env.getProperty("primotus.webhook.server.port");
        String webHookPath = env.getProperty("primotus.webhook.server.path");
        return new ExpressionResolver(webHookHost, webHookPort,  webHookPath);
    }

}