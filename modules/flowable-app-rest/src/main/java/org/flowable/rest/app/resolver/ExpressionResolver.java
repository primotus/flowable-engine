package org.flowable.rest.app.resolver;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.Process;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.delegate.BpmnError;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.engine.impl.persistence.entity.ExecutionEntityManager;
import org.flowable.engine.runtime.ActivityInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;


public class ExpressionResolver {



   public Boolean resolve(ExecutionEntity execution, String nodeId){
        //System.out.println(execution.getCurrentFlowElement().getId());

        //ExecutionEntity ex1 = execution.getProcessInstance().getProcessInstance();
        //System.out.println(ex1.getId()+ " -  " +execution.getId());

        /*try {
            ObjectNode payload = (ObjectNode) execution.getVariable("data");

            if ( payload != null ){
                BpmResolveConditionRequest bpmResolveConditionRequest = new BpmResolveConditionRequest();
                bpmResolveConditionRequest.bEntityId = payload.get("bEntityId").asText();
                bpmResolveConditionRequest.nodeId = nodeId;
                RestTemplate restTemplate = new RestTemplate();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<BpmResolveConditionRequest> request =
                        new HttpEntity<BpmResolveConditionRequest>(bpmResolveConditionRequest, headers);
                ResponseEntity<BpmResolveConditionResponse> t =
                        restTemplate.exchange("http://pri-frigga:3333/resolveCondition", HttpMethod.POST, request, BpmResolveConditionResponse.class);

                System.out.println("Status = " + t.getStatusCode());
                System.out.println("BEntityId = " + t.getBody().bEntityId);
                System.out.println("BENodeId = " + t.getBody().nodeId);
                System.out.println("Condition Eval = " + t.getBody().conditionResult);

                return t.getBody().conditionResult;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new BpmnError("CONDITIONAL_ERROR");
        }*/
        return true;
    }

}
