package org.dmly.traveller.geography.jersey.extension;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.api.extension.ExtensionContext.Store;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import java.util.List;

public class JerseyTestExtension implements BeforeEachCallback, AfterEachCallback, ParameterResolver {

    private final List<Class<?>> PARAMETER_TYPES = List.of(WebTarget.class);
    private final Application application;

    public JerseyTestExtension(Application application) {
        this.application = application;
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        Class<?> parametersType = parameterContext.getParameter().getType();
        return PARAMETER_TYPES.contains(parametersType);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        Class<?> parameterType = parameterContext.getParameter().getType();
        return getStore(extensionContext).get(parameterType, parameterType);
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        Store store = getStore(context);
        store.remove(JerseyTest.class, JerseyTest.class).tearDown();
        PARAMETER_TYPES.forEach(store::remove);
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        JerseyTest jerseyTest = new JerseyTest() {
            @Override
            protected Application configure() {
                return application;
            }
        };
        jerseyTest.setUp();
        getStore(context).put(JerseyTest.class, jerseyTest);
        getStore(context).put(WebTarget.class, jerseyTest.target());
    }

    private Store getStore(ExtensionContext context) {
        return context.getStore(ExtensionContext.Namespace.GLOBAL);
    }
}
