package org.flowable.rest.app.dmn.delegate;

import java.lang.reflect.Method;
import java.util.Date;

import org.flowable.common.engine.impl.el.AbstractFlowableFunctionDelegate;
import org.flowable.rest.app.dmn.delegate.date.DateUtil;

public class IsAfterDateDelegate extends AbstractFlowableFunctionDelegate {

    @Override
    public String prefix() {
        return "pri:date";
    }

    @Override
    public String localName() {
        return "isAfter";
    }

    @Override
    public Class<?> functionClass() {
        return DateUtil.class;
    }

    @Override
    public Method functionMethod() {
        return getSingleObjectParameterMethod();
    }
}
