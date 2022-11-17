package org.flowable.rest.app.resolver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExpressionResolverConfig {

    @Bean
    public ExpressionResolver priFlowExpression(){
        return new ExpressionResolver();
    }

}
