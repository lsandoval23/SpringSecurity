package com.lsandoval.springsecurity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

// Este sera la clase para la configuracion de spring secutiry
@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig  extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    // Sobreescrimos el metodo de configuracion de la clase WebSecurityConfigurerAdapter
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                // Metodo usado para permitir solicitudes a paths sin autenticacion
                .antMatchers("/", "index", "/css/*", "/js/*")
                .permitAll()
                // Todas las solicitudes
                .anyRequest()
                // Requieren autenticacion
                .authenticated()
                .and()
                // Del tipo basic auth
                .httpBasic();
    }


    // Con este metodo se obtienen los usuarios desde la base de datos y se registran
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {

        // Se registra a los usuarios de tipo in memory database (De manera inicial, solo registramos sus usuarios y contraseñas
        // harcodeados)

        UserDetails annaSmithUser = User.builder()
                .username("annasmith")
                .password(passwordEncoder.encode("password"))
                .roles("STUDENT")
                .build();

        return new InMemoryUserDetailsManager(
          annaSmithUser
        );
    }
}
