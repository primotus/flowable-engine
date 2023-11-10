package org.flowable.rest.app.contracts;

public class BpmResolveRequest {
    public String bizEntityID;
    public String nodeId;

    public String executionId;
    public String definitionId;
    public BpmResolveRequest(String bizEntityID, String nodeId, String executionId, String definitionId){
        this.bizEntityID = bizEntityID;
        this.nodeId =  nodeId;;
        this.executionId = executionId;
        this.definitionId = definitionId;
    }
}
