package com.eazyschool.eazyschool.config;


import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf -> csrf
                        .ignoringRequestMatchers("/saveMsg")
                        .ignoringRequestMatchers("/public/**"))
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/dashboard").authenticated()
                        .requestMatchers("/displayMessages").hasRole("ADMIN")
                        .requestMatchers("/closeMsg/**").hasRole("ADMIN")
                        .requestMatchers("/","/home").permitAll()
                        .requestMatchers("/contact").permitAll()
                        .requestMatchers("/about").permitAll()
                        .requestMatchers("/courses").permitAll()
                        .requestMatchers("/holidays/**").permitAll()
                        .requestMatchers("/saveMsg").permitAll()
                        .requestMatchers("/assets/**").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/public/**").permitAll()
                        .requestMatchers("/logout").permitAll()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .defaultSuccessUrl("/dashboard")
                        .failureUrl("/login?error")
                        .permitAll()

                ).logout(logout -> logout // So the thing is we have enabled CSRF
                        // which because of which this default configuration will
                        // not work we have to make a dedicated one ourselves so
                        // creating a logout controller
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .permitAll())
                .httpBasic(Customizer.withDefaults());

        return httpSecurity.build();
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager()  {
        UserDetails user = User.withDefaultPasswordEncoder( )
                .username("user")
                .password("user")
                .roles("USER")
                .build();

        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles("USER","ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin,user);
    }
}