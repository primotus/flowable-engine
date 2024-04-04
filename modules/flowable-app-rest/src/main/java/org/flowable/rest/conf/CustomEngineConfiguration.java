package org.flowable.rest.conf;


import org.flowable.rest.app.listener.PriBpmListener;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
@AutoConfigureAfter(BootstrapConfiguration.class)
public class CustomEngineConfiguration {

    @Bean
    public EngineConfigurationConfigurer<SpringProcessEngineConfiguration> customProcessEngineConfigurer(ApplicationContext context) {
        return (configuration) -> {
            SpringProcessEngineConfiguration cfg = context.getBean(SpringProcessEngineConfiguration.class);
            // Registro del listener del proceso
            PriBpmListener processListener = new PriBpmListener();

            if ( cfg.getEventDispatcher() == null ) {
                cfg.setEventDispatcher(new org.flowable.common.engine.impl.event.FlowableEventDispatcherImpl());
                cfg.getEventDispatcher().addEventListener(processListener);
                System.out.println("Event Dispatcher" + cfg.getEventDispatcher());
            }
        };
    }
}
