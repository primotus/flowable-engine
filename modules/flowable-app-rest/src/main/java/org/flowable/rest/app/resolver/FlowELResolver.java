package org.flowable.rest.app.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.flowable.common.engine.api.variable.VariableContainer;
import org.flowable.common.engine.impl.el.VariableContainerELResolver;
import org.flowable.common.engine.impl.javax.el.ELContext;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.rest.app.PriServiceResolve;
import org.flowable.rest.app.contracts.BpmConstant;
import org.flowable.rest.app.contracts.BpmProcessPayload;
import org.flowable.rest.app.contracts.BpmResolveRequest;
import org.flowable.rest.app.contracts.BpmResolveResponse;

public class FlowELResolver extends VariableContainerELResolver {

    @Override
    public Object getValue(ELContext context, Object base, Object property) {
        VariableContainer variableContainer = getVariableContainer(context);

        if (base == null && BpmConstant.FLOW_EXPRESSION.equals(property) && variableContainer instanceof ExecutionEntity) {

            System.out.println(property);
            System.out.println(base);

            PriServiceResolve priResolver = new PriServiceResolve();
            BpmResolveResponse response = priResolver.exec((ExecutionEntity) variableContainer);
            context.setPropertyResolved(true);
            return response.state;
        }
        return true;
    }

    @Override
    public boolean isReadOnly(ELContext context, Object base, Object property) {
        return true;
    }
}
