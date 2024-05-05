package br.com.sistemaescolar.infra.security;

import br.com.sistemaescolar.domain.user.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SecurityConfigurations {
    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()

                        .requestMatchers(HttpMethod.POST, "/aluno").hasRole(UserRole.ADMIN.getRole())
                        .requestMatchers(HttpMethod.GET, "/aluno").hasRole(UserRole.ADMIN.getRole())

                        .requestMatchers(HttpMethod.POST, "/disciplina").hasRole(UserRole.ADMIN.getRole())
                        .requestMatchers(HttpMethod.GET, "/disciplina").hasRole(UserRole.ADMIN.getRole())

                        .requestMatchers(HttpMethod.GET, "/nota").hasRole(UserRole.ADMIN.getRole())
                        .requestMatchers(HttpMethod.POST, "/nota").hasRole(UserRole.ADMIN.getRole())
                        .requestMatchers(HttpMethod.DELETE, "/nota/delete").hasRole(UserRole.ADMIN.getRole())
                        .requestMatchers(HttpMethod.GET, "/nota/all").hasRole(UserRole.ADMIN.getRole())

                        .requestMatchers(HttpMethod.GET, "/historico").permitAll()

                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



}
