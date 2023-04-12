package com.lsandoval.springsecurity.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.lsandoval.springsecurity.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {

    // 4 tipos de objetos para los roles y sus permisos asociados

    STUDENT(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(COURSE_READ, COURSE_WRITE, STUDENT_READ, STUDENT_WRITE)),
    ADMINTRAINEE(Sets.newHashSet(COURSE_READ, STUDENT_READ));

    // Atributps
    private final Set<ApplicationUserPermission> permissions;

    // Constructor
    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    // Getter
    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    // Transformamos los string del atributo permissions a un objeto de la clase SimpleGrantedAuthority.
    // Ademas añadimos el role con el formato que usa la clase User (ROLE_)
    // Esto se lo pasamos al builder authorites
    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {

        Set<SimpleGrantedAuthority> permissions = this.getPermissions()
                                                        .stream()
                                                        .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                                                        .collect(Collectors.toSet());

        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return permissions;

    }


}
