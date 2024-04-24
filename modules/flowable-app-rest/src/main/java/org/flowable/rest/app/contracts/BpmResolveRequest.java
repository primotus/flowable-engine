package org.flowable.rest.app.contracts;

public class BpmResolveRequest {
    //public String bizEntityID;
    public String flExecutionID;

    public String flFlowID;
    public String flProcessID;

    public String flActDefID;
    public BpmResolveRequest(String flExecutionID, String flFlowID, String flProcessID, String flActDefID){
        this.flExecutionID =  flExecutionID;;
        this.flFlowID = flFlowID;
        this.flProcessID = flProcessID;
        this.flActDefID = flActDefID;
    }
}
