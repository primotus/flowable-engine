package org.flowable.rest.app.resolver;

import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.rest.app.PriServiceResolve;
import org.flowable.rest.app.contracts.BpmResolveResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExpressionResolver {

    private final String webHookHost;
    private final String webHookPort;
    private final String webHookPath;

    protected static final Logger LOGGER = LoggerFactory.getLogger(ExpressionResolver.class);

    public ExpressionResolver(String webHookHost, String webHookPort,  String webHookPath){
        this.webHookHost = webHookHost;
        this.webHookPort = webHookPort;
        this.webHookPath = webHookPath;

    }

    public Boolean eval(ExecutionEntity execution, String activityDefID){
        if ( execution != null && activityDefID != null ){
            PriServiceResolve priResolver = new PriServiceResolve(this.webHookHost, this.webHookPort,  this.webHookPath);
            BpmResolveResponse response = priResolver.exec(execution, activityDefID);
            LOGGER.info("Resolve for ActivityDefID: {} with state {}", activityDefID, response.conditionResult);
            return response.conditionResult;
        }
        return false;
    }

}