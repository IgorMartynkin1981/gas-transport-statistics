package ru.alrosa.transport.gastransportstatistics.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.alrosa.transport.gastransportstatistics.services.impl.UserDetailServiceImpl;


/**
 * DTO DAO Authentication
 */

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final UserDetailServiceImpl userService;

    @Autowired
    public SecurityConfiguration(UserDetailServiceImpl userService) {
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests((a) -> a
                                .requestMatchers(
                                        "/moderator_console/**",
                                        "/users/**",
                                        "/plans/**",
                                        "/subdivisions/**",
                                        "/weekly_report/**",
                                        "/user_console/**",
                                        "/resources/**",
                                        "/webapp/**",
                                        "/.vscode/**",
                                        "/css/**",
                                        "/fonts/**",
                                        "/img/**",
                                        "/js/**",
                                        "/pages/**"
                                ).permitAll()
                                .requestMatchers("/moderator_console/**").hasRole("MODERATOR")
                                .requestMatchers("/admin_console/**", "/admin").hasRole("ADMIN")
                )
                .formLogin()
                .and()
                .logout();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userService);
        return authenticationProvider;
    }
}
