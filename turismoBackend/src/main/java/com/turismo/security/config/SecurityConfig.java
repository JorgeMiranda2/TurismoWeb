package com.turismo.security.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.turismo.security.routes.roles.RouteModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.io.IOException;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("classpath:JsonRoutes.json")
    private Resource routeConfigResource;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        List<RouteModel> routes = loadRoutesFromJson();

        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors
                        .configurationSource(request -> {
                            CorsConfiguration corsConfig = new CorsConfiguration();
                            corsConfig.addAllowedOrigin("/**");
                            corsConfig.addAllowedOrigin("https://localhost:8080");
                            corsConfig.addAllowedOrigin("http://localhost:5173");
                            corsConfig.addAllowedHeader("*");
                            corsConfig.addExposedHeader("Access-Control-Allow-Origin");
                            corsConfig.addExposedHeader("Access-Control-Allow-Credentials");
                            corsConfig.addAllowedMethod("*");
                            corsConfig.setAllowCredentials(true);
                            corsConfig.setMaxAge(3600L);
                            return corsConfig;
                        }))
                .authorizeHttpRequests(authRequest -> {
                    authRequest
                            .requestMatchers("/auth/**").permitAll();
                    for (RouteModel routeModel : routes) {
                        String[] roles = routeModel.getRoles().toArray(new String[0]);
                        authRequest
                                .requestMatchers(HttpMethod.valueOf(routeModel.getMethod()), routeModel.getRoute())
                                .hasAnyAuthority(roles);
                    }
                    authRequest.anyRequest().authenticated();
                }).sessionManagement(sessionManager->
                        sessionManager
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                .build();

    }


    private List<RouteModel> loadRoutesFromJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(routeConfigResource.getInputStream(), new TypeReference<List<RouteModel>>() {});
    }

}
