package org.flowable.rest.app.resolver;

import org.flowable.common.engine.api.variable.VariableContainer;
import org.flowable.common.engine.impl.javax.el.CompositeELResolver;
import org.flowable.common.engine.impl.javax.el.ELContext;
import org.flowable.common.engine.impl.javax.el.ELResolver;
import org.flowable.engine.impl.delegate.invocation.DefaultDelegateInterceptor;
import org.flowable.engine.impl.el.ProcessExpressionManager;
import org.flowable.engine.impl.interceptor.DelegateInterceptor;
import org.flowable.variable.service.impl.el.VariableScopeExpressionManager;

import java.util.List;
import java.util.Map;

public class CustomExpressionManager extends ProcessExpressionManager {

    public CustomExpressionManager() {
        this(null);
    }

    public CustomExpressionManager(DelegateInterceptor delegateInterceptor, Map<Object, Object> beans) {
        super(delegateInterceptor, beans);
    }

    public CustomExpressionManager(Map<Object, Object> beans) {
        super(beans);
    }

    @Override
    protected List<ELResolver> createDefaultElResolvers() {
        List<ELResolver> resolvers = super.createDefaultElResolvers();
        resolvers.add(new FlowELResolver());
        return resolvers;
    }
}