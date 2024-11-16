package com.luv2code.spring_boot_library.config;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf -> csrf.disable());
        http.authorizeHttpRequests(configurer -> configurer
                                  .requestMatchers("/api/books/secure/**",
                                                             "/api/reviews/secure/**")
                                  .authenticated())
                                  .oauth2ResourceServer(oauth2 -> oauth2.jwt());
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
        http.setSharedObject(ContentNegotiationStrategy.class,new HeaderContentNegotiationStrategy());
        //Okta.configureResourceServer401ResponseBody(http);
        http.exceptionHandling(exceptionHandling ->
                exceptionHandling
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)) // 401 Unauthorized with empty body
        );
        return http.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");   // Allow these origins
        config.addAllowedMethod("*");  // Allow these HTTP methods
        config.addAllowedHeader("*");  // Allow these headers
        source.registerCorsConfiguration("/**", config);  // Apply the config to all endpoints
        return source;
    }
}
