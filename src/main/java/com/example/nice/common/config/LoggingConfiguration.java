package com.example.nice.common.config;

import com.example.nice.common.logging.RequestResponseLoggingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * logging configuration
 *
 * @author heixiaochun
 */
@Configuration
public class LoggingConfiguration {

    @Bean
    public RequestResponseLoggingFilter requestLoggingFilter() {
        RequestResponseLoggingFilter filter = new RequestResponseLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludeHeaders(true);
        filter.setIncludePayload(true);
        filter.setAfterMessagePrefix("REQUEST DATA : ");
        return filter;
    }

}
