package com.booking.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Configuration class for setting up Cross-Origin Resource Sharing (CORS) mappings.
 * This configuration allows the application to handle requests from different origins.
 *
 * <p>Key configurations:</p>
 * <ul>
 *   <li>Allows all origins by setting {@code allowedOrigins("*")}.</li>
 *   <li>Permits HTTP methods such as GET, POST, PUT, PATCH, DELETE, and OPTIONS.</li>
 *   <li>Allows all headers by setting {@code allowedHeaders("*")}.</li>
 *   <li>Enables credentials sharing by setting {@code allowCredentials(true)}.</li>
 * </ul>
 *
 * <p>Note: Allowing all origins and headers with credentials sharing enabled can pose
 * security risks. Ensure this configuration is appropriate for your use case.</p>
 *
 * @see org.springframework.web.servlet.config.annotation.CorsRegistry
 */
@Configuration
public class CorsConfig {

    private String frontendUrl = "*";
    
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Arrays.asList(frontendUrl));
        config.addAllowedHeader("*");
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS", "HEAD"));
        source.registerCorsConfiguration("/**", config);
        config.setMaxAge(3600L);
        return new CorsFilter(source);
    }
}