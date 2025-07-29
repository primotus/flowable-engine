package org.flowable.rest.app.contracts;

import java.util.HashMap;

public class BpmResolveRequest {
    //public String bizEntityID;
    public String flExecutionID;

    public String flFlowID;
    public String flProcessID;

    public String flActDefID;

    public HashMap<String, String> extraData;



    public BpmResolveRequest(String flExecutionID, String flFlowID, String flProcessID, String flActDefID, HashMap<String, String> extraData){
        this.flExecutionID =  flExecutionID;;
        this.flFlowID = flFlowID;
        this.flProcessID = flProcessID;
        this.flActDefID = flActDefID;
        this.extraData = extraData;
    }
}
