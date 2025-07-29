package org.flowable.rest.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.Gateway;
import org.flowable.bpmn.model.SequenceFlow;
import org.flowable.engine.delegate.BpmnError;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.rest.app.contracts.BpmConstant;
import org.flowable.rest.app.contracts.BpmResolveRequest;
import org.flowable.rest.app.contracts.BpmResolveResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

public class PriServiceResolve {

    private final String webHookHost;
    private final String webHookPort;
    private final String webHookPath;

    public PriServiceResolve(String webHookHost, String wehHookPort,  String webHookPath) {
        this.webHookHost = webHookHost;
        this.webHookPort = wehHookPort;
        this.webHookPath = webHookPath;
    }
    protected static final Logger LOGGER = LoggerFactory.getLogger(PriServiceResolve.class);

    public Boolean isGateway(FlowElement currentElement){
        return currentElement instanceof Gateway;
    }

   //static String URL_FRIGGA =  "http://pri-frigga:3333/resolve";
   //static String URL_FRIGGA =  "http://localhost:3333/resolve";
   //static String URL_FRIGGA =  "http://frigga:9902/resolve";
    public <T extends DelegateExecution> BpmResolveResponse exec(T execution, String activityDefID){
        String URL_WEBHOOK = "http://" + this.webHookHost + ":" + this.webHookPort + this.webHookPath + "/expressionHandler";

        HashMap<String, String> extraVariables = new HashMap<>();

        FlowElement currentElement = execution.getCurrentFlowElement();
        if ( this.isGateway(currentElement) ) {
            List<SequenceFlow> flow = ((Gateway) currentElement).getOutgoingFlows().stream().filter(sequenceFlow -> {
                return sequenceFlow.getId().equals(activityDefID);
            }).toList();
            if (flow.isEmpty()){
                BpmResolveResponse t =  new BpmResolveResponse();
                t.ok = false;
                return t;
            }
            extraVariables.put(BpmConstant.SEQUENCE_FLOW_DEF_ID, activityDefID);
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
                execution.getCurrentActivityId(),
                extraVariables);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<BpmResolveRequest> request =
                new HttpEntity<BpmResolveRequest>(bpmResolveRequest, headers);

        LOGGER.info("Sending resolve to WebHook: {}", URL_WEBHOOK);
        ResponseEntity<BpmResolveResponse> response =
                restTemplate.exchange(URL_WEBHOOK, HttpMethod.POST, request, BpmResolveResponse.class);

        if (response.getBody() != null & !response.getBody().ok) {
            throw new BpmnError("RESOLVE_WEBHOOK_ERROR", "Error resolving node with webhook");
        }
        return response.getBody();
    }

}
