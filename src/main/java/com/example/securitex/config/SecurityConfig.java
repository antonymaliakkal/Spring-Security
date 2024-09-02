package com.example.securitex.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
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
                    .authorizeHttpRequests(request -> {
                            request.requestMatchers("/h2-console").permitAll();
                            request.anyRequest().authenticated();
                    })
                    .httpBasic(Customizer.withDefaults())
                    .build();
        }

        @Bean
        public AuthenticationProvider authenticationProvider() {

            System.out.println("AuthenticationProvider");
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
            provider.setUserDetailsService(userDetailsService);

            provider.setPasswordEncoder(new BCryptPasswordEncoder(12));

            return provider;

        }




}
