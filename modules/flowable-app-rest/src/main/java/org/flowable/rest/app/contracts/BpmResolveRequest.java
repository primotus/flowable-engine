package org.flowable.rest.app.contracts;

import org.flowable.bpmn.model.FlowElement;

public class BpmResolveRequest {
    public String bEntityId;
    public String nodeId;

    public String executionId;
    public String definitionId;
    public BpmResolveRequest(String bEntityId, String nodeId, String executionId, String definitionId){
        this.bEntityId = bEntityId;
        this.nodeId =  nodeId;;
        this.executionId = executionId;
        this.definitionId = definitionId;
    }
}
