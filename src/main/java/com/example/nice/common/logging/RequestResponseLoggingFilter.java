package com.example.nice.common.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * 请求返回报文的记录过滤器
 * 参考:org.springframework.web.filter.AbstractRequestLoggingFilter
 *
 * @author heixiaochun
 */
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger("WEB_LOGGER");

    /**
     * form表单类型请求
     */
    private static final String FORM_CONTENT_TYPE = "application/x-www-form-urlencoded";

    private boolean includeQueryString = false;

    private boolean includeHeaders = false;

    private boolean includePayload = false;

    /**
     * Set whether the query string should be included in the log message.
     * <p>Should be configured using an {@code <init-param>} for parameter name
     * "includeQueryString" in the filter definition in {@code web.xml}.
     */
    public void setIncludeQueryString(boolean includeQueryString) {
        this.includeQueryString = includeQueryString;
    }

    /**
     * Return whether the query string should be included in the log message.
     */
    protected boolean isIncludeQueryString() {
        return this.includeQueryString;
    }

    /**
     * Set whether the request headers should be included in the log message.
     * <p>Should be configured using an {@code <init-param>} for parameter name
     * "includeHeaders" in the filter definition in {@code web.xml}.
     *
     * @since 4.3
     */
    public void setIncludeHeaders(boolean includeHeaders) {
        this.includeHeaders = includeHeaders;
    }

    /**
     * Return whether the request headers should be included in the log message.
     *
     * @since 4.3
     */
    protected boolean isIncludeHeaders() {
        return this.includeHeaders;
    }

    /**
     * Set whether the request payload (body) should be included in the log message.
     * <p>Should be configured using an {@code <init-param>} for parameter name
     * "includePayload" in the filter definition in {@code web.xml}.
     *
     * @since 3.0
     */
    public void setIncludePayload(boolean includePayload) {
        this.includePayload = includePayload;
    }

    /**
     * Return whether the request payload (body) should be included in the log message.
     *
     * @since 3.0
     */
    protected boolean isIncludePayload() {
        return this.includePayload;
    }

    /**
     * The default value is "false" so that the filter may log a "before" message
     * at the start of request processing and an "after" message at the end from
     * when the last asynchronously dispatched thread is exiting.
     */
    @Override
    protected boolean shouldNotFilterAsyncDispatch() {
        return false;
    }

    /**
     * Forwards the request to the next filter in the chain and delegates down to the subclasses
     * to perform the actual request logging both before and after the request is processed.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        boolean isFirstRequest = !isAsyncDispatch(request);
        HttpServletRequest requestToUse = request;
        HttpServletResponse responseToUse = response;

        if (isIncludePayload() && isFirstRequest && !(request instanceof CachedBodyHttpServletRequest)) {
            // 使用可多次读取的ServletRequest进行包装
            requestToUse = new CachedBodyHttpServletRequest(request);
        }

        if (!(response instanceof ContentCachingResponseWrapper)) {
            // 使用可多次读取的ServletResponse进行包装
            responseToUse = new ContentCachingResponseWrapper(response);
        }

        if (isFirstRequest) {
            // 打印请求报文
            printRequestLog(requestToUse);
        }
        try {
            filterChain.doFilter(requestToUse, responseToUse);
        } finally {
            if (!isAsyncStarted(requestToUse)) {
                ContentCachingResponseWrapper responseWrapper = (ContentCachingResponseWrapper) responseToUse;
                // 打印返回报文
                printResponseLog(responseWrapper);
                // 重新返回流
                responseWrapper.copyBodyToResponse();
            }
        }
    }

    /**
     * 记录请求的日志信息
     */
    private void printRequestLog(HttpServletRequest request) {
        // 记录请求URL
        StringBuilder uri = new StringBuilder();
        uri.append("Request URL: ");
        uri.append(request.getMethod()).append(" ");
        uri.append(request.getRequestURI());
        if (this.isIncludeQueryString()) {
            String queryString = request.getQueryString();
            if (queryString != null) {
                uri.append('?').append(queryString);
            }
        }
        logger.info(uri.toString());

        // 请求头
        if (isIncludeHeaders()) {
            HttpHeaders headers = new ServletServerHttpRequest(request).getHeaders();
            logger.info("Headers: {}", headers);
        }

        // 请求报文
        if (isIncludePayload()) {
            if (isFormPost(request)) {
                logger.info("ParameterMap: {}", request.getParameterMap());
            } else {
                String payload = getMessagePayload(request);
                if (payload != null) {
                    logger.info("Payload: {}", payload);
                }
            }
        }
    }


    /**
     * Extracts the message payload portion of the message created by
     * {@link #isIncludePayload()} returns true.
     */
    @Nullable
    protected String getMessagePayload(HttpServletRequest request) {
        try (InputStream is = request.getInputStream()) {
            return StreamUtils.copyToString(is, StandardCharsets.UTF_8);
        } catch (IOException e) {
            return "[unknown]";
        }
    }

    /**
     * 判断是否为表单提交类型请求 application/x-www-form-urlencoded
     */
    private boolean isFormPost(HttpServletRequest request) {
        String contentType = request.getContentType();
        return (contentType != null && contentType.contains(FORM_CONTENT_TYPE) &&
                HttpMethod.POST.matches(request.getMethod()));
    }

    /**
     * 记录返回的日志信息
     */
    private void printResponseLog(ContentCachingResponseWrapper responseWrapper) {
        String responseStr = new String(responseWrapper.getContentAsByteArray(),
                StandardCharsets.UTF_8);
        logger.info("Response: {}", responseStr);
    }

}
