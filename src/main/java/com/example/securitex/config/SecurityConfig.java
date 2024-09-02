package com.example.securitex.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Autowired
        UserDetailsService userDetailsService;

         @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

            System.out.println("SecurityFilterChain");
            return  http.csrf(token -> token.disable())
                    .headers(httpSecurityHeadersConfigurer -> {
                        httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable);
                    })
                    .authorizeHttpRequests(request -> { request
                            .requestMatchers("/api/register", "/api/login", "/h2-console/**")
                            .permitAll()
                            .anyRequest()
                            .authenticated();
                    })
                    .httpBasic(Customizer.withDefaults())
                    .build();
        }

        @Bean
        public AuthenticationProvider authenticationProvider() {

            System.out.println("AuthenticationProvider");
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//            System.out.println("DaoAuthenticationProvider" + provider);
            provider.setUserDetailsService(userDetailsService);
            provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
//            System.out.println(userDetailsService);
            return provider;

        }


        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
            System.out.println("AuthenticationManager");
             return authConfig.getAuthenticationManager();
        }



}
