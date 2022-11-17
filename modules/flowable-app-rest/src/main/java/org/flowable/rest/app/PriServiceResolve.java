package org.flowable.rest.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.Gateway;
import org.flowable.bpmn.model.SequenceFlow;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.rest.app.contracts.BpmConstant;
import org.flowable.rest.app.contracts.BpmProcessPayload;
import org.flowable.rest.app.contracts.BpmResolveRequest;
import org.flowable.rest.app.contracts.BpmResolveResponse;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

public class PriServiceResolve {

    public <T extends DelegateExecution> BpmResolveResponse exec(T execution, String nodeID){
        ObjectNode data = (ObjectNode) execution.getVariable(BpmConstant.PROCESS_VARIABLE_NAME);
        if (data == null) {
            throw new RuntimeException("data was not found in execution " + execution.getId());
        }

        System.out.println("=================================");
        System.out.println(nodeID);
        System.out.println("=================================");

        FlowElement currentElement = execution.getCurrentFlowElement();
        if ( currentElement instanceof Gateway ) {
            List<SequenceFlow> flow = ((Gateway) currentElement).getOutgoingFlows().stream().filter(sequenceFlow -> {
                System.out.println(sequenceFlow.getId());
                System.out.println(nodeID);
                return sequenceFlow.getId().equals(nodeID);
            }).collect(Collectors.toList());
            if ( flow.size() == 0 ){
                BpmResolveResponse t =  new BpmResolveResponse();
                t.state = false;
                t.nodeId = nodeID;
                System.out.println("NodeID is not part of Gateway");
                return t;
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        BpmProcessPayload payload =  mapper.convertValue(data, BpmProcessPayload.class);
        BpmResolveRequest bpmResolveRequest = new BpmResolveRequest(payload.bEntityId, nodeID);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<BpmResolveRequest> request =
                new HttpEntity<BpmResolveRequest>(bpmResolveRequest, headers);
        ResponseEntity<BpmResolveResponse> response =
                restTemplate.exchange("http://pri-frigga:3333/resolve", HttpMethod.POST, request, BpmResolveResponse.class);

        //ResponseEntity<BpmResolveResponse> response =
        //        restTemplate.exchange("http://localhost:3333/resolve", HttpMethod.POST, request, BpmResolveResponse.class);


        BpmResolveResponse t =  response.getBody();

        System.out.println("BEntityId = " + t.bEntityId);
        System.out.println("BENodeId = " + t.nodeId);
        System.out.println("Condition Eval = " + t.state);

        return t;

    }

}
