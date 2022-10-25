package org.flowable.rest.app.parser;

import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.*;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.engine.impl.bpmn.parser.BpmnParse;
import org.flowable.engine.parse.BpmnParseHandler;
import org.flowable.engine.runtime.ActivityInstance;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class GenericParseHandler implements BpmnParseHandler {

    @Autowired
    ActivityInstance activityInstance;

    @Override
    public Collection<Class<? extends BaseElement>> getHandledTypes() {
        Set<Class<? extends BaseElement>> types = new HashSet<>();
        types.add(UserTask.class);
        types.add(SequenceFlow.class);
        types.add(ServiceTask.class);
        return types;
    }

    @Override
    public void parse(BpmnParse bpmnParse, BaseElement element) {
        System.out.println("Parsing element: " + element.getId());
    }
}
