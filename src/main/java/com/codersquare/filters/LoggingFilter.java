package com.codersquare.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Component
public class LoggingFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Wrap request and response to capture their content
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        // Record start time for measuring request processing time
        long startTime = System.currentTimeMillis();

        // Continue the filter chain
        filterChain.doFilter(requestWrapper, responseWrapper);

        // Calculate time taken for processing
        long timeTaken = System.currentTimeMillis() - startTime;

        // Retrieve request and response content as strings
        String requestBody = getStringValue(requestWrapper.getContentAsByteArray(),
                request.getCharacterEncoding());
        String responseBody = getStringValue(responseWrapper.getContentAsByteArray(),
                response.getCharacterEncoding());

        // Log the request and response details
        LOGGER.info(
                "FINISHED PROCESSING: METHOD={}; REQUEST URI={}; REQUEST PAYLOAD={}; RESPONSE CODE={}; RESPONSE PAYLOAD={}\n; TIME TAKEN={}",
                request.getMethod(), request.getRequestURI(), requestBody, response.getStatus(), formatResponse(responseBody),
                timeTaken);


        // Copy the response body back to the original response
        responseWrapper.copyBodyToResponse();
    }

    // Utility method to convert byte array to string with specified character encoding
    private String getStringValue(byte[] contentAsByteArray, String characterEncoding) {
        try {
            return new String(contentAsByteArray, 0, contentAsByteArray.length, characterEncoding);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Error converting byte array to string", e);
        }
        return "";
    }

    private String formatResponse(String responseBody) {
        try {
            ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            Object json = mapper.readValue(responseBody, Object.class);
            return mapper.writeValueAsString(json);
        } catch (IOException e) {
            LOGGER.error("Error formatting response", e);
        }
        return responseBody;
    }

}
