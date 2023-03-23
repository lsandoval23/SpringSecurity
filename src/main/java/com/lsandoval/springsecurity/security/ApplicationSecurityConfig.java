package com.lsandoval.springsecurity.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// Este sera la clase para la configuracion de spring secutiry
@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig  extends WebSecurityConfigurerAdapter {

    // Sobreescrimos el metodo de configuracion de la clase WebSecurityConfigurerAdapter
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                // Metodo usado para permitir solicitudes a paths sin autenticacion
                .antMatchers("/")
                .permitAll()
                // Todas las solicitudes
                .anyRequest()
                // Requieren autenticacion
                .authenticated()
                .and()
                // Del tipo basic auth
                .httpBasic();
    }
}
