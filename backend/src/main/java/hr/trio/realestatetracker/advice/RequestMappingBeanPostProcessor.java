package hr.trio.realestatetracker.advice;

import java.lang.reflect.Method;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.AbstractAdvisingBeanPostProcessor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.DynamicMethodMatcherPointcut;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
public class RequestMappingBeanPostProcessor extends AbstractAdvisingBeanPostProcessor implements InitializingBean {

    @Override
    public void afterPropertiesSet() {

        final Pointcut pointcut = new DynamicMethodMatcherPointcut() {

            @Override
            public ClassFilter getClassFilter() {
                return clazz -> clazz.isAnnotationPresent(RestController.class);
            }

            @Override
            public boolean matches(Method method, Class<?> targetClass, Object... args) {
                return getParameterIndex(method);
            }
        };

        this.advisor = new DefaultPointcutAdvisor(pointcut, new RequestMappingMethodInterceptor());
    }

    private boolean getParameterIndex(final Method method) {
        RequestMapping annotation = AnnotationUtils.findAnnotation(method, RequestMapping.class);
        return annotation != null;
    }

}