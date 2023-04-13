package com.sigmify.vb.booking.util;

import javax.servlet.http.HttpServletRequest;

import com.sigmify.vb.booking.exception.BookingExceptionHandler;

/**
 * <p><b>Executive summary:</b>For getting the  path before proxy from request.</p>
 *
 * @author Srikanta
 */

public class PathUtil {
    private static final String FORWARDED_PROXY = "x-forwarded-prefix";
    
    /**
     * <p><b>Executive Summary:</b>Getting the  path before proxy.</p>
     * <p><b>OS/Hardware Dependencies:</b> N/A</p>
     * <p><b>References to any External Specifications:</b> N/A.</p>
     * <p><b>Expected Behavior:</b>NA.</p>
     * <p><b>State Transitions:</b> N/A</p>
     * <p><b>Range of Valid Argument Values:</b> N/A</p>
     * <p><b>Null Argument Values:</b> N/A</p>
     * <p><b>Algorithms Defined:</b> N/A</p>
     * <p><b>Allowed Implementation Variances:</b> N/A</p>
     * <p><b>Cause of Exceptions:</b> NA.</p>
     * <p><b>Security Constraints:</b> N/A</p>
     *
     * @param httpServletRequest the http servlet request
     * @return the path before proxy
     * @see HttpServletRequest
     * @see AdminExceptionHandler
     */
    public static String getPathBeforeProxy(HttpServletRequest httpServletRequest) {
        StringBuilder stringBuilder = new StringBuilder();
        String proxyPath = httpServletRequest.getHeader(FORWARDED_PROXY);
        if (proxyPath != null) {
            stringBuilder.append(proxyPath);
        }
        stringBuilder.append(httpServletRequest.getRequestURI());
        return stringBuilder.toString();
    }
}
