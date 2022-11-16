package ru.alrosa.transport.gastransportstatistics.securityConfigs;

//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;

//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
public class SecurityConfig {
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("Admin").password("pass").roles("ADMIN");
//        auth.inMemoryAuthentication()
//                //.and()
//                .withUser("user").password("pass").roles("USER");
//    }
//
//    private String qwer() {
//        System.out.println("Это работает");
//        return "user";
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests()
//                // .antMatchers("/**").hasRole("ADMIN")
//                .antMatchers("/**").permitAll()
//                //.antMatchers("/**").hasAnyRole()
//                .and()
//                .formLogin();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
//        String result = encoder.encode("myPassword");
//        return NoOpPasswordEncoder.getInstance();
//    }
}
