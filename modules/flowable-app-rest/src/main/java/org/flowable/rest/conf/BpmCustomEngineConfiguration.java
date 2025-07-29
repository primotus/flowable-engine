package org.flowable.rest.conf;


import org.flowable.rest.app.listener.PriBpmListener;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(BootstrapConfiguration.class)
public class BpmCustomEngineConfiguration {

    @Bean
    public EngineConfigurationConfigurer<SpringProcessEngineConfiguration> customProcessEngineConfigurer(ApplicationContext context) {
        return (configuration) -> {
            SpringProcessEngineConfiguration cfg = context.getBean(SpringProcessEngineConfiguration.class);

            Environment env = context.getBean(Environment.class);
            String webHookHost = env.getProperty("primotus.webhook.server.host");
            String webHookPort = env.getProperty("primotus.webhook.server.port");
            String webHookPath = env.getProperty("primotus.webhook.server.path");

            PriBpmListener processListener = new PriBpmListener(webHookHost, webHookPort, webHookPath);//context.getBean(PriBpmListener.class);
            if ( cfg.getEventDispatcher() == null ) {
                cfg.setEventDispatcher(new org.flowable.common.engine.impl.event.FlowableEventDispatcherImpl());
                cfg.getEventDispatcher().addEventListener(processListener);
                System.out.println("Event Dispatcher" + cfg.getEventDispatcher());
            }
        };
    }
}
