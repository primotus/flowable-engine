package org.flowable.rest.app.dmn.delegate;

import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.impl.el.AbstractFlowableFunctionDelegate;
import org.flowable.rest.app.dmn.delegate.date.DateUtil;

import java.lang.reflect.Method;

public class ToDurationDelegate extends AbstractFlowableFunctionDelegate {

    @Override
    public String prefix() {
        return "pri";
    }

    @Override
    public String localName() {
        return "toDuration";
    }

    @Override
    public Class<?> functionClass() {
        return DateUtil.class;
    }

    @Override
    public Method functionMethod() {
        try {
            return functionClass().getDeclaredMethod(localName(), String.class);
        } catch (Exception e) {
            throw new FlowableException("Error getting method " + localName(), e);
        }
    }

}
