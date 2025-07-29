package org.flowable.rest.app.listener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.flowable.common.engine.api.delegate.event.*;
import org.flowable.common.engine.impl.el.FlowableElContext;
import org.flowable.engine.delegate.event.FlowableActivityEvent;
import org.flowable.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.*;

import java.util.List;

/**
 * MixIn to ignore properties of the ExecutionEntityImpl class for circular reference
 */
abstract class ExecutionEntityImplMixIn {
    @JsonIgnore
    protected ExecutionEntityImpl processInstance;

    /** the parent execution */
    @JsonIgnore
    protected ExecutionEntityImpl parent;

    /** nested executions representing scopes or concurrent paths */
    @JsonIgnore
    protected List<ExecutionEntityImpl> executions;

    @JsonIgnore
    /** super execution, not-null if this execution is part of a subprocess */
    protected ExecutionEntityImpl superExecution;

    @JsonIgnore
    /** reference to a subprocessinstance, not-null if currently subprocess is started from this execution */
    protected ExecutionEntityImpl subProcessInstance;

    @JsonIgnore
    protected ExecutionEntityImpl rootProcessInstance;

    @JsonIgnore
    protected FlowableElContext cachedElContext;
}


/**
 * Listener to send BPM events to Frigga
 */
public class PriBpmListener implements FlowableEventListener {

    protected static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PriBpmListener.class);
    //TODO move this constant to a configuration file
    //static String URL_FRIGGA =  "http://pri-frigga:3333/eventHandler";
    //static String URL_FRIGGA =  "http://localhost:3333/eventHandler";
    //static String URL_FRIGGA =  "http://pri-frigga:3333/eventHandler";
    //static String URL_FRIGGA =  "http://frigga:9902/eventHandler";

    private final String webHookHost;
    private final String webHookPort;
    private final String webHookPath;

    private final ObjectMapper mapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    public PriBpmListener(String webHookHost, String webHookPort,  String webHookPath) {
        this.webHookHost = webHookHost;
        this.webHookPort = webHookPort;
        this.webHookPath = webHookPath;
        this.mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        //Configure the ObjectMapper to ignore properties of the ExecutionEntityImpl class for circular reference
        this.mapper.addMixIn(ExecutionEntityImpl.class, ExecutionEntityImplMixIn.class);
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(mapper);
        this.restTemplate.getMessageConverters().add(0, converter);
        this.mapper.addMixIn(ExecutionEntityImpl.class, ExecutionEntityImplMixIn.class);
    }

    @Override
    public void onEvent(FlowableEvent event) {

        String URL_WEBHOOK = "http://" + this.webHookHost + ":" + this.webHookPort + this.webHookPath + "/eventHandler";

        if (event.getType() == FlowableEngineEventType.ACTIVITY_STARTED) {
            FlowableActivityEvent activityEvent = (FlowableActivityEvent) event;
            LOGGER.info("Activity {} started", activityEvent.getActivityId());
        }

        if (event.getType() == FlowableEngineEventType.ACTIVITY_COMPLETED) {
            FlowableActivityEvent activityEvent = (FlowableActivityEvent) event;
            LOGGER.info("Activity {} started", activityEvent.getActivityId());
        }

        if (event.getType() == FlowableEngineEventType.ENTITY_DELETED) {
            FlowableEntityEvent entity = (FlowableEntityEvent) event;
            LOGGER.info("Entity {} started", entity.getEntity().getClass().getSimpleName());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<FlowableEvent> request =
                new HttpEntity<FlowableEvent>(event, headers);

        try {
            LOGGER.info("Sending event to WEB HOOK: {}", URL_WEBHOOK);
            // Send the event to WEB HOOK
            ResponseEntity<Void> response = this.restTemplate.exchange(URL_WEBHOOK, HttpMethod.POST, request, Void.class);
        }catch (Exception e){
            LOGGER.error("The WEB HOOK service is not available to dispatch the following event: {} - {}", event.getType(), e.getMessage());
        }
    }

    @Override
    public boolean isFailOnException() {
        // Aquí puedes definir si el proceso debe detenerse cuando este listener lanza una excepción
        return false;
    }

    @Override
    public boolean isFireOnTransactionLifecycleEvent() {
        // Retornar true si deseas que este listener sea invocado en eventos de ciclo de vida de transacción
        return false;
    }

    @Override
    public String getOnTransaction() {
        // Puede ser usado para especificar en qué parte del ciclo de vida de la transacción este listener debe ser invocado
        return null;
    }
}
