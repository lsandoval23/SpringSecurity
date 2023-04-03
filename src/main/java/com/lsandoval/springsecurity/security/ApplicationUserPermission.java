package com.lsandoval.springsecurity.security;

public enum ApplicationUserPermission {

    // Objetos de la clase enum (Solo se pueden instanciar estos 4 tipos)
    STUDENT_READ("student:read"),
    STUDENT_WRITE("student:write"),
    COURSE_READ("course:read"),
    COURSE_WRITE("course:write");

    // Atributo del enum
    private final String permission;

    // Constructor del enum
    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    // Getter
    public String getPermission() {
        return permission;
    }
}
