package org.flowable.rest.app.delegate;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.flowable.rest.app.PriServiceResolve;
import org.flowable.rest.app.contracts.BpmResolveResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service("priTaskDelegate")
@Scope("prototype")
public class ServiceTaskClassDelegate implements JavaDelegate {

    @Value("${primotus.frigga.server.port}")
    private String friggaPort;

    @Value("${primotus.frigga.server.host}")
    private String friggaHost;

    protected static final Logger LOGGER = LoggerFactory.getLogger(ServiceTaskClassDelegate.class);

    @Override
    public void execute(DelegateExecution execution) {
        PriServiceResolve resolver = new PriServiceResolve(friggaHost, friggaPort);
        BpmResolveResponse response = resolver.exec(execution, execution.getCurrentFlowElement().getId());
        LOGGER.info("Delegating function to Frigga Resolver: " + response.state);
    }
}
