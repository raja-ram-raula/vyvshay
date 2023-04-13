package com.sigmify.vb.admin.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p><b>Executive summary:</b> Annotation used to trigger execution of {@link LoggedAspect} for annotated
 * methods.</p>
 * 
 * @author srikanta
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Logged {
    /**
     * <p><b>Executive Summary:</b> Configuration flag, controlling whether to log the annotated method's
     * arguments. {@code true} by default.</p>
     * <p><b>OS/Hardware Dependencies:</b> N/A</p>
     * <p><b>References to any External Specifications:</b> N/A.</p>
     * <p><b>Expected Behavior:</b> Configuration flag, controlling whether to log the annotated method's
     * arguments. {@code true} by default.</p>
     * <p><b>State Transitions:</b> N/A</p>
     * <p><b>Range of Valid Argument Values:</b> N/A</p>
     * <p><b>Null Argument Values:</b>N/A</p>
     * <p><b>Algorithms Defined:</b> N/A</p>
     * <p><b>Allowed Implementation Variances:</b> N/A</p>
     * <p><b>Cause of Exceptions:</b> N/A</p>
     * <p><b>Security Constraints:</b> N/A</p>
     *
     * @return the value of the configuration flag
     */
    boolean logArguments() default true;

    /**
     * <p><b>Executive Summary:</b> Configuration flag, controlling whether to log the annotated method's
     * result. {@code true} by default.</p>
     * <p><b>OS/Hardware Dependencies:</b> N/A</p>
     * <p><b>References to any External Specifications:</b> N/A.</p>
     * <p><b>Expected Behavior:</b> Configuration flag, controlling whether to log the annotated method's
     * result. {@code true} by default.</p>
     * <p><b>State Transitions:</b> N/A</p>
     * <p><b>Range of Valid Argument Values:</b> N/A</p>
     * <p><b>Null Argument Values:</b>N/A</p>
     * <p><b>Algorithms Defined:</b> N/A</p>
     * <p><b>Allowed Implementation Variances:</b> N/A</p>
     * <p><b>Cause of Exceptions:</b> N/A</p>
     * <p><b>Security Constraints:</b> N/A</p>
     *
     * @return the value of the configuration flag
     */
    boolean logResult() default true;
}
