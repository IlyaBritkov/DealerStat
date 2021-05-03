package com.leverx.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum UserRole {
    TRADER(Set.of(
            Permission.USERS_UPDATE,

            Permission.GAMES_ADD
    )),
    ADMIN(Set.of(
            Permission.NOT_APPROVED_USERS_READ,
            Permission.USERS_UPDATE,
            Permission.NOT_APPROVED_USERS_UPDATE,

            Permission.GAMES_ADD,
            Permission.GAMES_UPDATE,
            Permission.GAMES_DELETE,

            Permission.NOT_APPROVED_FEEDBACKS_READ,
            Permission.FEEDBACKS_UPDATE
    ));

    private final Set<Permission> permissions;

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
