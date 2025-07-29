package org.flowable.rest.app.dmn.delegate;

/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.lang.reflect.Method;

import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.impl.el.AbstractFlowableFunctionDelegate;
import org.flowable.rest.app.dmn.delegate.date.DateUtil;


/**
 * A date function mapper that can be used in EL expressions
 *
 * @author Tijs Rademakers
 */
public class ToDateDelegate extends AbstractFlowableFunctionDelegate {

    @Override
    public String prefix() {
        return "pri";
    }

    @Override
    public String localName() {
        return "toDate";
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

