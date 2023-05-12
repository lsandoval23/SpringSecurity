package com.lsandoval.springsecurity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

import static com.lsandoval.springsecurity.security.ApplicationUserPermission.COURSE_WRITE;
import static com.lsandoval.springsecurity.security.ApplicationUserRole.*;

// Este sera la clase para la configuracion de spring secutiry
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig  extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    // Sobreescrimos el metodo de configuracion de la clase WebSecurityConfigurerAdapter
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
                // Metodo usado para permitir solicitudes a paths sin autenticacion
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                // aumentando el metodo hasRole a los antMatchers podemos restringir endpoints a ciertos roles.
                .antMatchers("/api/**/").hasRole(STUDENT.name())
                .anyRequest()
                // Requieren autenticacion
                .authenticated()
                .and()
                // Cambiamos a form based authentication
                .formLogin()
                    // Configuramos una ruta para implementar una vista de login customizada
                    .loginPage("/login")
                    .permitAll()
                    // Definimos un endpoint para redireccionar luego de un login exitoso
                    .defaultSuccessUrl("/courses", true)
                    // Podemos darle un nombre diferente a los parametros de usuario y contraseña que se pasan en el formulario
                    // Se dejan en su valor por defecto
                    .passwordParameter("password")
                    .usernameParameter("username")
                .and()
                .rememberMe()
                    // Configuramos el tiempo de duración del cookie remember me a 21 dias
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                    // Definimos una llave secreta diferente a la que spring security tiene por defecto
                    // para la generación del md5 del cookie remember me
                    .key("secretkey")
                    .rememberMeParameter("remember-me")
                .and()
                .logout()
                    // Endpoint para el url de logout
                    .logoutUrl("/logout")
                    // Si tenemos el token csrf desactivado, la solicitud puede de ser de cualquier tipo
                    // En caso se tenga activada, usar el metodo POST
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                    // Eliminamos cookies, se cierra sesion
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID", "remember-me")
                    .logoutSuccessUrl("/login");
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
//                .roles(STUDENT.name())  // ROLE_STUDENT
                .authorities(STUDENT.getGrantedAuthorities())
                .build();

        UserDetails lindaUser = User.builder()
                .username("linda")
                .password(passwordEncoder.encode("password123"))
//                .roles(ADMIN.name()) // ROLE_ADMIN
                // Podemos agregar authorities, y dentro el rol (RECONOCE QUE ES UN ROL YA QUE TIENE EL FORMATO ROL_...)
                .authorities(ADMIN.getGrantedAuthorities())
                .build();

        UserDetails tomUser = User.builder()
                .username("tom")
                .password(passwordEncoder.encode("password123"))
//                .roles(ADMINTRAINEE.name()) // ROLE_ADMINTRAINEE
                .authorities(ADMINTRAINEE.getGrantedAuthorities())
                .build();



        return new InMemoryUserDetailsManager(
                annaSmithUser,
                lindaUser,
                tomUser
        );
    }
}
