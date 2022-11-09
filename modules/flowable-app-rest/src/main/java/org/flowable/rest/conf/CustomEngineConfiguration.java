package org.flowable.rest.conf;


import org.flowable.common.engine.impl.cfg.SpringBeanFactoryProxyMap;
import org.flowable.common.engine.impl.el.DefaultExpressionManager;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.parse.BpmnParseHandler;
import org.flowable.rest.app.parser.GenericParseHandler;
import org.flowable.rest.app.resolver.CustomExpressionManager;
import org.flowable.rest.app.resolver.FlowELResolver;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.flowable.spring.boot.ProcessEngineAutoConfiguration;
import org.flowable.spring.boot.RestApiAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;

import java.util.*;

//@Configuration
////@AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE)
////@AutoConfigureBefore(ProcessEngineAutoConfiguration.class)
//public class CustomEngineConfiguration {
//
//    @Bean
//    public org.flowable.spring.boot.EngineConfigurationConfigurer<SpringProcessEngineConfiguration> customTaskSLAProcessEngineConfigurationConfigurer() {
//        return processEngineConfiguration -> {
//            //List<BpmnParseHandler> parseHandlers = processEngineConfiguration.getPostBpmnParseHandlers();
//            //if (parseHandlers == null) {
//            //    parseHandlers = new ArrayList<>();
//           // }
//           // parseHandlers.add(new GenericParseHandler());
//           // processEngineConfiguration.setPostBpmnParseHandlers(parseHandlers);
//
//            CustomExpressionManager defaultExpressionManager = new CustomExpressionManager();
//            //defaultExpressionManager.addPostDefaultResolver(new FlowELResolver());
//            processEngineConfiguration.setExpressionManager(defaultExpressionManager);
//
//
//        };
//    }
//}
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(RestApiAutoConfiguration.class)
public class CustomEngineConfiguration {

    @Bean
    public EngineConfigurationConfigurer<SpringProcessEngineConfiguration> customProcessEngineConfigurer(ApplicationContext context) {
        return (configuration) -> {
            CustomExpressionManager defaultExpressionManager = new CustomExpressionManager();
            defaultExpressionManager.setBeans(new SpringBeanFactoryProxyMap(context));
            configuration.setExpressionManager(defaultExpressionManager);
        };
    }
}