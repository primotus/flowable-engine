package org.flowable.rest.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.rest.app.contracts.BpmConstant;
import org.flowable.rest.app.contracts.BpmProcessPayload;
import org.flowable.rest.app.contracts.BpmResolveRequest;
import org.flowable.rest.app.contracts.BpmResolveResponse;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class PriServiceResolve {

    public <T extends DelegateExecution> BpmResolveResponse exec(T execution){
        ObjectNode data = (ObjectNode) execution.getVariable(BpmConstant.PROCESS_VARIABLE_NAME);
        if (data == null) {
            throw new RuntimeException("data was not found in execution " + execution.getId());
        }

        FlowElement currentFlowElement = execution.getCurrentFlowElement();
        ObjectMapper mapper = new ObjectMapper();
        BpmProcessPayload payload =  mapper.convertValue(data, BpmProcessPayload.class);
        BpmResolveRequest bpmResolveRequest = new BpmResolveRequest(payload.bEntityId, currentFlowElement.getId());

        //RestTemplate restTemplate = new RestTemplate();
        //HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(MediaType.APPLICATION_JSON);
        //HttpEntity<BpmResolveRequest> request =
        //        new HttpEntity<BpmResolveRequest>(bpmResolveRequest, headers);
        //ResponseEntity<BpmResolveResponse> t =
        //        restTemplate.exchange("http://pri-frigga:3333/resolve", HttpMethod.POST, request, BpmResolveResponse.class);

        BpmResolveResponse t = new BpmResolveResponse();
        t.state = true;
        t.bEntityId = bpmResolveRequest.bEntityId;
        t.nodeId = bpmResolveRequest.nodeId;


        //System.out.println("Status = " + t.getStatusCode());
        System.out.println("BEntityId = " + t.bEntityId);
        System.out.println("BENodeId = " + t.nodeId);
        System.out.println("Condition Eval = " + t.state);



        return t;

    }

}
