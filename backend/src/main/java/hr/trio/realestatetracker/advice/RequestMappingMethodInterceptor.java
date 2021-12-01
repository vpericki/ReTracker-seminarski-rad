package hr.trio.realestatetracker.advice;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class RequestMappingMethodInterceptor implements MethodInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestMappingMethodInterceptor.class);

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {

        StringBuilder builder = new StringBuilder();

        builder.append("Method invoked = ").append(methodInvocation.getThis().getClass().getName())
                .append(".").append(methodInvocation.getMethod().getName()).append(" (");
        final Object[] methodArguments = methodInvocation.getArguments();

        if (methodArguments.length != 0) {
            String prefix = "";
            for (Object methodArgument : methodArguments) {
                builder.append(prefix);
                prefix = ",";
                builder.append(methodArgument.toString());
            }
        }
        builder.append(")");
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(builder.toString());
        }
        return methodInvocation.proceed();
    }

}
