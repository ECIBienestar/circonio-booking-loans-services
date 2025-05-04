package com.booking.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

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

    /**
     * Configures CORS (Cross-Origin Resource Sharing) mappings for the application.
     *
     * @param registry the {@link CorsRegistry} to configure CORS mappings
     * 
     * <p>This method allows all origins, HTTP methods (GET, POST, PUT, PATCH, DELETE, OPTIONS),
     * and headers for cross-origin requests. Additionally, it enables credentials to be included
     * in cross-origin requests.</p>
     */
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "PATCH","DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}