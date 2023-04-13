package com.sigmify.vb.admin.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * <p><b>Executive summary:</b>Aspect used to log calls to methods annotated with {@link Logged}. This aspect will log the
 * beginning of the annotated method's call, its end, and any exceptions it throws. Based on the
 * configuration set on the {@link Logged} annotation, on method level, it will also log the
 * method's arguments and its result.</p>
 * 
 * @author srikanta
 */
@Aspect
@Component
@Profile("!unit-test")
public class LoggedAspect {
  private static final String BEGIN_TEMPLATE = "{}.{}() BEGIN";
  private static final String BEGIN_TEMPLATE_WITH_ARGS = BEGIN_TEMPLATE + " - {}";

  private static final String END_TEMPLATE = "{}.{}() END";
  private static final String END_TEMPLATE_WITH_RESULT = END_TEMPLATE + " - {}";

  private static final String EXCEPTION_TEMPLATE = "{}.{}() EXCEPTION";

  @Before(value = "@annotation(logged)")
  public void before(JoinPoint joinPoint, Logged logged) {
    final Class<?> interceptedClass = joinPoint.getTarget().getClass();
    final Logger classLogger = LoggerFactory.getLogger(interceptedClass);

    if (logged.logArguments()) {
      classLogger.info(BEGIN_TEMPLATE_WITH_ARGS, interceptedClass.getSimpleName(),
          joinPoint.getSignature().getName(), joinPoint.getArgs());
    } else {
      classLogger.info(BEGIN_TEMPLATE, interceptedClass.getSimpleName(),
          joinPoint.getSignature().getName());
    }
  }

  @AfterReturning(value = "@annotation(logged)", returning = "result")
  public void afterReturningClassLevel(JoinPoint joinPoint, Logged logged, Object result) {
    final Class<?> interceptedClass = joinPoint.getTarget().getClass();
    final Logger classLogger = LoggerFactory.getLogger(interceptedClass);

    if (logged.logResult()) {
      classLogger.info(END_TEMPLATE_WITH_RESULT, interceptedClass.getSimpleName(),
          joinPoint.getSignature().getName(), result);
    } else {
      classLogger.info(END_TEMPLATE, interceptedClass.getSimpleName(),
          joinPoint.getSignature().getName());
    }
  }

  @AfterThrowing(value = "@annotation(logged)", throwing = "throwable")
  public void afterThrowing(JoinPoint joinPoint, Logged logged, Throwable throwable) {
    final Class<?> interceptedClass = joinPoint.getTarget().getClass();
    final Logger classLogger = LoggerFactory.getLogger(interceptedClass);

    classLogger.error(EXCEPTION_TEMPLATE, interceptedClass.getSimpleName(),
        joinPoint.getSignature().getName(), throwable);
  }
}
