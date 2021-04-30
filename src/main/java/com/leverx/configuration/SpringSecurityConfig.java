package com.leverx.configuration;

import com.leverx.enums.Permission;
import com.leverx.enums.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.GET, "/api/**").hasAnyAuthority(Permission.GAMES_READ.getPermission())
                .antMatchers(HttpMethod.POST, "/api/**").hasAnyAuthority(Permission.GAMES_WRITE.getPermission())
                .antMatchers(HttpMethod.DELETE, "/api/**").hasAnyAuthority(Permission.GAMES_WRITE.getPermission())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.builder()
                        .username("admin")
                        .password(passwordEncoder().encode("password"))
                        .authorities(UserRole.ADMIN.getGrantedAuthorities())
                        .build(),

                User.builder()
                        .username("user")
                        .password(passwordEncoder().encode("password"))
                        .authorities(UserRole.TRADER.getGrantedAuthorities())
                        .build()

        );
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
