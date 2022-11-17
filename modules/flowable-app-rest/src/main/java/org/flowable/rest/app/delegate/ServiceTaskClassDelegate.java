package org.flowable.rest.app.delegate;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.flowable.rest.app.PriServiceResolve;
import org.flowable.rest.app.contracts.BpmResolveResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("priTaskDelegate")
@Scope("prototype")
public class ServiceTaskClassDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        PriServiceResolve resolver = new PriServiceResolve();
        BpmResolveResponse response = resolver.exec(execution, execution.getCurrentFlowElement().getId());
        System.out.println("Delegating function to Frigga Resolver: " + response.state);
    }
}
