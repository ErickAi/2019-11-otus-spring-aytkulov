package ru.otus.booklibrary.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import ru.otus.booklibrary.domain.Role;

@Configuration
@RequiredArgsConstructor
@EnableResourceServer
@Order(3)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http                            // @formatter:off
          .authorizeRequests()
            .antMatchers("/admin/**").hasAuthority(Role.ROLE_ADMIN.getAuthority())
            .antMatchers(HttpMethod.POST).hasAuthority(Role.ROLE_ADMIN.getAuthority())
            .antMatchers(HttpMethod.PUT).hasAuthority(Role.ROLE_ADMIN.getAuthority())
            .antMatchers(HttpMethod.DELETE).hasAuthority(Role.ROLE_ADMIN.getAuthority())
            .anyRequest().authenticated()
        ;
    }                                   // @formatter:on
}
