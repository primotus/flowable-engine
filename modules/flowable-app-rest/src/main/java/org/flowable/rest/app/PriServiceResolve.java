package org.flowable.rest.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.Gateway;
import org.flowable.bpmn.model.SequenceFlow;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.rest.app.contracts.BpmConstant;
import org.flowable.rest.app.contracts.BpmProcessPayload;
import org.flowable.rest.app.contracts.BpmResolveRequest;
import org.flowable.rest.app.contracts.BpmResolveResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class PriServiceResolve {

    private String friggaHost;
    private String friggaPort;
    public PriServiceResolve(String frigaHost, String friggaPort) {
        this.friggaHost = frigaHost;
        this.friggaPort = friggaPort;
    }
    protected static final Logger LOGGER = LoggerFactory.getLogger(PriServiceResolve.class);

   //static String URL_FRIGGA =  "http://pri-frigga:3333/resolve";
   //static String URL_FRIGGA =  "http://localhost:3333/resolve";
   //static String URL_FRIGGA =  "http://frigga:9902/resolve";
    public <T extends DelegateExecution> BpmResolveResponse exec(T execution, String nodeID){
        String URL_FRIGGA = "http://" + this.friggaHost + ":" + this.friggaPort + "/resolve";

        FlowElement currentElement = execution.getCurrentFlowElement();
        if ( currentElement instanceof Gateway ) {
            List<SequenceFlow> flow = ((Gateway) currentElement).getOutgoingFlows().stream().filter(sequenceFlow -> {
                return sequenceFlow.getId().equals(nodeID);
            }).collect(Collectors.toList());
            if ( flow.size() == 0 ){
                BpmResolveResponse t =  new BpmResolveResponse();
                t.state = false;
                t.nodeId = nodeID;
                return t;
            }
        }

        ObjectMapper mapper = new ObjectMapper();

        LOGGER.info("execution.getCurrentFlowElement().getId() {}",execution.getCurrentFlowElement().getId());
        LOGGER.info("execution.getCurrentActivityId() {}",execution.getCurrentActivityId());


        LOGGER.info("Process ID: {}", execution.getProcessDefinitionId());
        LOGGER.info("Flow ID: {}", execution.getProcessInstanceId());
        LOGGER.info("Flow Node ID: {}", execution.getId());

        BpmResolveRequest bpmResolveRequest = new BpmResolveRequest(
                execution.getId(),
                execution.getProcessInstanceId(),
                execution.getProcessDefinitionId(),
                execution.getCurrentActivityId());

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<BpmResolveRequest> request =
                new HttpEntity<BpmResolveRequest>(bpmResolveRequest, headers);

        LOGGER.info("Sending resolve to Frigga: {}", URL_FRIGGA);

        ResponseEntity<BpmResolveResponse> response =
                restTemplate.exchange(URL_FRIGGA, HttpMethod.POST, request, BpmResolveResponse.class);

        if (response.getBody() != null & !response.getBody().state) {
            throw new RuntimeException("Error in Frigga");
        }

        BpmResolveResponse t =  response.getBody();

        System.out.println("BE - bizEntityID = " + t.bizEntityID);
        System.out.println("BE - NodeId = " + t.nodeId);
        System.out.println("BE - Condition Eval = " + t.state);

        return t;

    }

}
