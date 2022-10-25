package org.flowable.rest.app.contracts;

import org.flowable.bpmn.model.FlowElement;

public class BpmResolveRequest {
    public String bEntityId;
    public String nodeId;
    public BpmResolveRequest(String bEntityId, String nodeId){
        this.bEntityId = bEntityId;
        this.nodeId =  nodeId;
    }
}
