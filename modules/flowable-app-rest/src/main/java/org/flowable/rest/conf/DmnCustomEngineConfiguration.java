package org.flowable.rest.conf;

import org.flowable.common.engine.api.delegate.FlowableFunctionDelegate;
import org.flowable.common.engine.api.variable.VariableContainer;
import org.flowable.common.engine.impl.el.ExpressionManager;
import org.flowable.dmn.engine.impl.cfg.StandaloneDmnEngineConfiguration;
import org.flowable.dmn.engine.impl.el.ELExecutionContext;
import org.flowable.dmn.spring.SpringDmnEngineConfiguration;
import org.flowable.rest.app.dmn.delegate.*;
import org.flowable.rest.app.listener.PriBpmListener;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;


@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(BootstrapConfiguration.class)
public class DmnCustomEngineConfiguration {


    @Bean
    public EngineConfigurationConfigurer<SpringDmnEngineConfiguration> customDmnEngineConfigurer(ApplicationContext context) {
        return (configuration) -> {
            SpringDmnEngineConfiguration cfg = context.getBean(SpringDmnEngineConfiguration.class);

            List<FlowableFunctionDelegate> customFunctionDelegates = new ArrayList<>();
            customFunctionDelegates.add(new IsAfterDateDelegate());
            customFunctionDelegates.add(new ToDateDelegate());
            customFunctionDelegates.add(new ToDateTimeDelegate());
            customFunctionDelegates.add(new CollectionOf());
            customFunctionDelegates.add(new ToDateNowDelegate());
            customFunctionDelegates.add(new ToDurationDelegate());

            configuration.setCustomFlowableFunctionDelegates(customFunctionDelegates);
        };
    }
}