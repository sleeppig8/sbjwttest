package org.example.sbjwttest.Config;

import org.example.sbjwttest.Authority.MemberAuthority;
import org.example.sbjwttest.Model.Member;
import org.example.sbjwttest.Service.JwtService;
import org.example.sbjwttest.Service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    @Bean
//    public UserDetailsService inMemoryUserDetailsManager() {
//        UserDetails userDetails = User
//                .withUsername("user1")
//                .password("{noop}111")
//                .authorities("STAFF", "MANAGER","ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(List.of(userDetails));
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public JwtService jwtService(
            @Value("${jwt.secret-key}") String secretKeyStr,
            @Value("${jwt.valid-seconds}") int validSeconds
    ) {
        return new JwtService(secretKeyStr, validSeconds);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
//                .authorizeHttpRequests(requests -> requests
//                        .requestMatchers(HttpMethod.POST, "/register").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/members").hasAuthority("ADMIN")
//                        .requestMatchers(HttpMethod.POST, "/select-course").hasAuthority("STAFF")
//                        .requestMatchers(HttpMethod.PUT, "/courses").hasAuthority("MANAGER")
//                        .requestMatchers(HttpMethod.GET, "/course-feedback").hasAnyAuthority("MANAGER", "ADMIN")
//                        .anyRequest().authenticated()
//                )
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests.anyRequest().permitAll())
                .build();
    }
}
