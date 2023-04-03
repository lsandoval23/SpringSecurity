package com.lsandoval.springsecurity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig {

    /**
     *  Al momento de generar una contraseña al usuario, spring security pide un codificador obligatoriamente.
     *  La interfaz password enconder devuelve una instancia de alguna clase concreta que implementa a esta interfaz.
     *
     *
     *
     * */

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }
}
