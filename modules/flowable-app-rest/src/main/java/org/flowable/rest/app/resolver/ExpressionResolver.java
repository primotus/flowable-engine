package org.flowable.rest.app.resolver;

import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.rest.app.PriServiceResolve;
import org.flowable.rest.app.contracts.BpmResolveResponse;


public class ExpressionResolver {

   public Boolean eval(ExecutionEntity execution, String nodeId){

        if ( execution != null && nodeId != null ){
            PriServiceResolve priResolver = new PriServiceResolve();
            BpmResolveResponse response = priResolver.exec(execution, nodeId);
            return ( response != null && response.state );
        }
        return false;
    }

}
