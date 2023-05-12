package com.lsandoval.springsecurity.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserService implements UserDetailsService {

    private final ApplicationUserDao applicationUserDao;

    // Con el qualifier podemos escoger que implementación de ApplicationUserDao es la que se estará
    // usando (Por si tenemos distintos repositorios de base de datos para poder intercambiar
    // sin mucha refactorizacion)
    @Autowired
    public ApplicationUserService(@Qualifier("fake") ApplicationUserDao applicationUserDao) {
        this.applicationUserDao = applicationUserDao;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return applicationUserDao
                .selectApplicationUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", username)));
    }
}
