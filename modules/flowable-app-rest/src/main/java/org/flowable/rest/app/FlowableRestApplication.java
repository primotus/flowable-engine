/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.flowable.rest.app;

//import org.flowable.rest.conf.CustomEngineConfiguration;
import org.flowable.rest.app.properties.RestAppProperties;
import org.flowable.rest.app.resolver.ExpressionResolverConfig;
import org.flowable.rest.conf.BootstrapConfiguration;
import org.flowable.rest.conf.CustomEngineConfiguration;
import org.flowable.rest.conf.DevelopmentConfiguration;
import org.flowable.rest.conf.SecurityConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;

/**
 * @author Filip Hrisafov
 */
@EnableConfigurationProperties({
        RestAppProperties.class
})
@Import({
        BootstrapConfiguration.class,
        SecurityConfiguration.class,
        DevelopmentConfiguration.class,
        ExpressionResolverConfig.class,
        //TestExpressionResolver.class,
        CustomEngineConfiguration.class


})
@SpringBootApplication(proxyBeanMethods = false)
public class FlowableRestApplication extends SpringBootServletInitializer {

    protected static final Logger LOGGER = LoggerFactory.getLogger(FlowableRestApplication.class);

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        LOGGER.info("Primotus Rest App Flowable on startup");
        super.onStartup(servletContext);
    }
    public static void main(String[] args) {
        SpringApplication.run(FlowableRestApplication.class, args);
    }

//    @Bean
//    public WebMvcConfigurer swaggerDocsConfigurer() {
//        return new WebMvcConfigurer() {
//
//            @Override
//            public void addViewControllers(ViewControllerRegistry registry) {
//                registry.addViewController("/docs").setViewName("redirect:/docs/");
//                registry.addViewController("/docs/").setViewName("forward:/docs/index.html");
//            }
//        };
//    }
//
//    @Bean
//    public GrantedAuthorityDefaults grantedAuthorityDefaults(RestAppProperties commonAppProperties) {
//        return new GrantedAuthorityDefaults(commonAppProperties.getRolePrefix());
//    }
}
