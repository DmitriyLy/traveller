package org.dmly.traveller.presentation.admin.bean.startup;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.*;
import java.util.ArrayList;
import java.util.List;

public class EagerExtension implements Extension {
    private final List<Bean<?>> startupBeans = new ArrayList<>();

    public <T> void collect(@Observes ProcessBean<T> event) {
        if (event.getAnnotated().isAnnotationPresent(Eager.class)
                && event.getAnnotated().isAnnotationPresent(ApplicationScoped.class)) {
            startupBeans.add(event.getBean());
        }
    }

    public void load (@Observes AfterDeploymentValidation event, BeanManager beanManager) {
        for (Bean<?> bean : startupBeans) {
            beanManager.getReference(bean, bean.getBeanClass(), beanManager.createCreationalContext(bean)).toString();
        }
    }
}
